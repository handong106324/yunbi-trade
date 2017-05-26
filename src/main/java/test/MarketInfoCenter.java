package test;

import platform.biter.BiterMarketClient;
import platform.bittrex.BittrexMarketClient;
import platform.yunbi.market.AbstractMarketApi;
import platform.yunbi.market.MarketApiFactory;
import platform.yunbi.market.bean.Market;
import platform.yunbi.market.bean.Symbol;
import platform.yunbi.market.bean.SymbolPair;
import platform.btc38.Btc38Client;
import com.alibaba.fastjson.JSONObject;
import model.BanZhuanInfo;
import model.ChainCoin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by handong on 17/5/17.
 */
public class MarketInfoCenter {
    private MarketInfo yunbiMarket;
    private MarketInfo bit38Market;
    private MarketInfo biterMarket;

    private MarketInfo bitterxMarket;

    private Set<String> marketSet = new HashSet<>();
    private Map<String, BanZhuanInfo> marketBanZhuanInfo = new HashMap<>();
    public MarketInfoCenter(){
        init();
    }

    private void init() {
        try {
            initYunBi();
            initBiter();
            initBtc38();
            initBitterx();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initBitterx() {
        BittrexMarketClient client = new BittrexMarketClient();
        Map<String, ChainCoin> coinMap = client.getAllCoinInfo();
        ChainCoin usdCoin = coinMap.get("USDT-BTC");
        ChainCoin usdCoinEth = coinMap.get("USDT-ETH");
        for (Map.Entry<String, ChainCoin> entry : coinMap.entrySet()) {
            String marketName = entry.getKey();
            String uninKey = "";
            double zh = 6.8804d;
            if (marketName.startsWith("BTC-")) {
                uninKey = marketName.substring(4, marketName.length());
                zh *= Double.parseDouble(usdCoin.getLast());
            } else if (marketName.startsWith("ETH-")) {
                uninKey = marketName.substring(4, marketName.length());
                zh *= Double.parseDouble(usdCoinEth.getLast());
            } else {
                continue;
            }

            marketSet.add(uninKey.toLowerCase());
            ChainCoin coin = entry.getValue();
            coin.setPlateForm("Bitterx");
            coin.setLast(String.valueOf(Double.parseDouble(coin.getLast()) * zh));
            coin.setAskSell(String.valueOf(Double.parseDouble(coin.getAskSell()) * zh));
            coin.setBidBuy(String.valueOf(Double.parseDouble(coin.getBidBuy()) * zh));
            BanZhuanInfo banZhuanInfo = getBanZhuan(uninKey.toLowerCase());
            banZhuanInfo.setBittrexCoin(coin);
        }
    }

    private ChainCoin yunBiCoin(Symbol symbol) {
        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);

        ChainCoin coin = new ChainCoin();
        JSONObject depth = market.get_depth(new SymbolPair(symbol, Symbol.cny), true);
        if (null == depth || null == depth.getJSONArray("asks")) {
            return null;
        }
        String yunBuy = String.valueOf(depth.getJSONArray("asks").getJSONObject(0).getDouble("price"));
        String yunSell = String.valueOf(depth.getJSONArray("bids").getJSONObject(0).getDouble("price"));
        coin.setAskSell(yunBuy);
        coin.setBidBuy(yunSell);
        coin.setLast(yunBuy);
        coin.setPlateForm("YunBi");
        coin.setKey(symbol.name());
        return coin;
    }

    private BanZhuanInfo getBanZhuan(String key) {
        BanZhuanInfo banZhuanInfo = marketBanZhuanInfo.get(key);
        if (null == banZhuanInfo) {
            banZhuanInfo = new BanZhuanInfo();
            marketBanZhuanInfo.put(key, banZhuanInfo);
        }
        return banZhuanInfo;
    }
    private void initBtc38() {
        JSONObject jsonObject = new Btc38Client().getMarketInfo();

        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            String ticker1 = (((JSONObject) entry.getValue()).getJSONObject("ticker")).getString("last");
            String bid = (((JSONObject) entry.getValue()).getJSONObject("ticker")).getString("buy");
            String ask = (((JSONObject) entry.getValue()).getJSONObject("ticker")).getString("sell");

            ChainCoin coin = new ChainCoin();
            coin.setPlateForm("Btc38");
            coin.setBidBuy(bid);
            coin.setKey(entry.getKey());
            coin.setAskSell(ask);
            coin.setLast(ticker1);
            marketSet.add(entry.getKey());
            BanZhuanInfo banZhuanInfo = getBanZhuan(entry.getKey());
            banZhuanInfo.getChinaCoinList().add(coin);
        }
    }

    private void initBiter() {
        BiterMarketClient biterMarketClient = new BiterMarketClient();
        JSONObject biterData = biterMarketClient.getMarketInfo();

        for (Map.Entry<String,Object> entry : biterData.entrySet()) {
            String symbol = ((JSONObject) biterData.get(entry.getKey())).getString("symbol").toLowerCase();
            String pair = ((JSONObject) biterData.get(entry.getKey())).getString("pair").toLowerCase();
            String rate =  biterData.getJSONObject(entry.getKey()).getString("rate");

            marketSet.add(symbol);
            ChainCoin coin = new ChainCoin();
            coin.setPlateForm("Biter");
            coin.setKey(pair);
            coin.setLast(rate);

            BanZhuanInfo banZhuanInfo = marketBanZhuanInfo.get(symbol);
            if (null == banZhuanInfo) {
                banZhuanInfo = new BanZhuanInfo();
                marketBanZhuanInfo.put(symbol, banZhuanInfo);
            }

            banZhuanInfo.getChinaCoinList().add(coin);
        }
    }

    private void initYunBi() {
        Symbol[] symbols = Symbol.values();
        for (Symbol symbol : symbols) {
            marketSet.add(symbol.name());
            BanZhuanInfo banZhuanInfo = getBanZhuan(symbol.name());
            ChainCoin yunBiCoin = yunBiCoin(symbol);
            if (null == yunBiCoin) {
                continue;
            }
            banZhuanInfo.getChinaCoinList().add(yunBiCoin);
        }
    }

    public MarketInfo getYunbiMarket() {
        return yunbiMarket;
    }

    public void setYunbiMarket(MarketInfo yunbiMarket) {
        this.yunbiMarket = yunbiMarket;
    }

    public MarketInfo getBit38Market() {
        return bit38Market;
    }

    public void setBit38Market(MarketInfo bit38Market) {
        this.bit38Market = bit38Market;
    }

    public MarketInfo getBiterMarket() {
        return biterMarket;
    }

    public void setBiterMarket(MarketInfo biterMarket) {
        this.biterMarket = biterMarket;
    }

    public MarketInfo getBitterxMarket() {
        return bitterxMarket;
    }

    public void setBitterxMarket(MarketInfo bitterxMarket) {
        this.bitterxMarket = bitterxMarket;
    }


    public Set<String> getMarketSet() {
        return marketSet;
    }

    public Map<String,BanZhuanInfo> getMarketBanZhuanInfo(){
        return marketBanZhuanInfo;
    }
}
