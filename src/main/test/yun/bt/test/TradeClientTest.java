package yun.bt.test;

import biter.BiterMarketClient;
import bittrex.BittrexMarketClient;
import bt.yunbi.client.TendencyStrategyThread;
import bt.yunbi.market.AbstractMarketApi;
import bt.yunbi.market.MarketApiFactory;
import bt.yunbi.market.bean.Market;
import bt.yunbi.market.bean.SymbolPair;
import btc38.Btc38Client;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.rest.HttpUtilManager;
import model.ChainCoin;
import org.apache.commons.lang.StringUtils;
import bt.yunbi.client.strategy.TendencyStrategyParam;
import bt.yunbi.client.strategy.impl.TendencyGuessFeeOne;
import bt.yunbi.client.strategy.impl.TendencyStrategy;
import bt.yunbi.market.bean.Symbol;
import org.apache.http.HttpException;
import org.junit.Test;
import poloniex.PoloniexMarketClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by handong on 17/1/23.
 */
public class TradeClientTest {

    @Test
    public void checkYunAndBittrex() {
        BittrexMarketClient client = new BittrexMarketClient();
        double yunEthPrice = getBuyPrice(Symbol.eth);//云币网买eth的花费
        System.out.println("云币买价格=" + yunEthPrice);

        List<String> pairs = client.getPairs();
        double btcEthRate = Double.parseDouble(client.getChainCoin("BTC-ETH").getBidBuy());

        double canRate = 0.07;
        Symbol[] symbols = Symbol.values();
        for (Symbol symbol : symbols) {
            if (symbol.name().equals("eth")) {
                continue;
            }
            double tarPrice = getBuyPrice(symbol);
            if (0.0 == tarPrice) {
                continue;
            }

            double yunCount = yunEthPrice / tarPrice;

            double bCountToEth,bCount = 0;
            String marketName = in(symbol, pairs, true, "-");
            if (StringUtils.isNotBlank(marketName)) {
                bCountToEth = Double.parseDouble(client.getChainCoin(marketName).getAskSell());
                bCount = 1 / bCountToEth;

                if (bCount > yunCount && (bCount - yunCount) / yunCount > canRate) {
                    System.out.print(" ---ETH 可以搬砖 ---" + (bCount - yunCount) / yunCount);
                }

            }


            marketName = in(symbol, pairs, false, "-");
            if (StringUtils.isNotBlank(marketName)) {
                double btcTarRate = Double.parseDouble(client.getChainCoin(marketName).getAskSell());
                bCount = btcEthRate / btcTarRate;
                if (bCount > yunCount && (bCount - yunCount) / yunCount > canRate) {
                    System.out.print(" ---BTC 可以搬砖 ---" + (bCount - yunCount) / yunCount);
                }
            }
        }
    }
    @Test
    public void checkYunAndPonx() {
        PoloniexMarketClient client = new PoloniexMarketClient();
        double yunEthPrice = getBuyPrice(Symbol.eth);//云币网买eth的花费
        System.out.println("云币买价格=" + yunEthPrice);

        Map<String, ChainCoin> map = client.getAllCoinInfo();
        List<String> pairs = client.getPairs();
        double btcEthRate = Double.parseDouble(map.get("BTC_ETH").getBidBuy());

        double canRate = 0.07;
        Symbol[] symbols = Symbol.values();
        for (Symbol symbol : symbols) {
            if (symbol.name().equals("eth")) {
                continue;
            }
            double tarPrice = getBuyPrice(symbol);
            if (0.0 == tarPrice) {
                continue;
            }

            double yunCount = yunEthPrice / tarPrice;

            double bCountToEth,bCount = 0;
            String marketName = in(symbol, pairs, true, "_");
            if (StringUtils.isNotBlank(marketName)) {
                bCountToEth = Double.parseDouble(map.get(marketName).getAskSell());
                bCount = 1 / bCountToEth;

                System.out.println(marketName + ":" + (bCount - yunCount) / yunCount +"[" + bCountToEth + "]");

                if (bCount > yunCount && (bCount - yunCount) / yunCount > canRate) {
                    System.out.print(" ---ETH 可以搬砖 ---" + (bCount - yunCount) / yunCount);
                }

            }


            marketName = in(symbol, pairs, false, "_");
            if (StringUtils.isNotBlank(marketName)) {
                double btcTarRate = Double.parseDouble(map.get(marketName).getAskSell());
                bCount = btcEthRate / btcTarRate;
//                System.out.println(marketName + ":" + (bCount - yunCount) / yunCount +"[" + bCountToEth + "]");

                if (bCount > yunCount && (bCount - yunCount) / yunCount > canRate) {
                    System.out.print(" ---BTC 可以搬砖 ---" + (bCount - yunCount) / yunCount);
                }
            }
        }
    }

    private String in(Symbol symbol, List<String> pairs, boolean b, String sp) {

        if (b) {
            for (String mar : pairs) {
                if (mar.startsWith("ETH" + sp) && mar.toLowerCase().endsWith(sp + symbol.name())) {
                    return mar;
                }
            }
        } else {
            for (String mar : pairs) {
                if (mar.startsWith("BTC" + sp) && mar.toLowerCase().endsWith(sp + symbol.name())) {
                    return mar;
                }
            }
        }
        return "";
    }

    public double getBuyPrice(Symbol symbol) {
        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        JSONObject depth = market.get_depth(new SymbolPair(symbol, Symbol.cny), true);
        if (null == depth || null == depth.getJSONArray("asks")) {
            return 0.0;
        }
        String yunBuy = String.valueOf(depth.getJSONArray("asks").getJSONObject(0).getDouble("price"));
        return Double.parseDouble(yunBuy);
    }


    @Test
    public void getMarketInfo() {
        //比特儿
        BiterMarketClient biterMarketClient = new BiterMarketClient();
        JSONObject biterData = biterMarketClient.getMarketInfo();

        //比特时代
        JSONObject jsonObject = new Btc38Client().getMarketInfo();

        List<String> allHasBi = new ArrayList<>();
        //
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            if (biterData.keySet().contains(entry.getKey() + "_cny")) {
                double ticker1 = Double.parseDouble((((JSONObject) entry.getValue()).getJSONObject("ticker")).getString("last"));
                double ticker2 = Double.parseDouble(((JSONObject) biterData.get(entry.getKey() + "_cny")).getString("rate"));
                double rate = 0.0;
                if (ticker1 > ticker2) {
                    rate = ticker2 / ticker1;
                } else {
                    rate = ticker1 / ticker2;
                }
                System.out.println(entry.getKey() + " = " + rate + " [" + ticker1 + " " + ticker2 + " " + (rate < 0.93 ? "可搬砖" : ""));
                if (rate < 0.93) {
                    allHasBi.add(entry.getKey());
                }
            }
        }

    }

    @Test
    public void testBanz() {
        try {
            String dataBtcEth = HttpUtilManager.getInstance().requestHttpGet("https://bittrex.com/api/v1.1/public/getmarketsummary?market=btc-eth",
                    "", "");
            String dataBtcAns = HttpUtilManager.getInstance().requestHttpGet("https://bittrex.com/api/v1.1/public/getmarketsummary?market=btc-ans",
                    "", "");

            JSONObject btcEth = JSON.parseObject(dataBtcEth);
            JSONObject btcAns = JSON.parseObject(dataBtcAns);

            double countAns = btcEth.getJSONArray("result").getJSONObject(0).getDouble("Last")
                    / btcAns.getJSONArray("result").getJSONObject(0).getDouble("Last");

            double countAnsBid = btcEth.getJSONArray("result").getJSONObject(0).getDouble("Bid")
                    / btcAns.getJSONArray("result").getJSONObject(0).getDouble("Ask");

            double countOldAns = (633.78 / 3.359);


            System.out.println(countAns + " : " + countOldAns + " = " + (countAns - countOldAns) / countOldAns);
            System.out.println(countAnsBid + " : " + countOldAns + " = " + (countAnsBid - countOldAns) / countOldAns);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void selectStrategy() throws IOException {
        TendencyStrategyParam param = new TendencyStrategyParam();
        param.setSymbol(Symbol.eth);
        int min = 30;
        param.setLimitCount(24 * 60 / min);
        param.setTendencyTime(min);
        param.setSellRate(0.005);
        param.setBuyRate(0.01);
        param.setDownTimeForBuy(-4);
        param.setUpTimeForSell(3);
        param.setCost(10000);

        param.setTendencyType(TendencyStrategy.TENDENCY_TYPE_MIN);
        TendencyGuessFeeOne strategy = new TendencyGuessFeeOne(param, true, Market.PeatioCNY);
//        strategy.tendency();
        TendencyStrategyThread tendencyStrategyThread = new TendencyStrategyThread(strategy);
        tendencyStrategyThread.start();
        System.out.println("--4卖 3 买 30分钟:" + (strategy.getMoney() - strategy.getCost()));

//
//        param.setDownTimeForBuy(-3);
//        param.setUpTimeForSell(2);
//        strategy = new TendencyGuessFeeOne(param, true, Market.PeatioCNY);
//        strategy.tendency();
//        System.out.println("--2卖 3 买-- 30分钟 :" + (strategy.getMoney() - strategy.getCost()));
//
//
//
//        min = 60;
//        param.setLimitCount(5 * 24 * 60 /min);
//
//        param.setDownTimeForBuy(-3);
//        param.setUpTimeForSell(2);
//        strategy = new TendencyGuessFeeOne(param, true, Market.PeatioCNY);
//        strategy.tendency();
//        System.out.println("--2卖 3 买-- 60分钟 :"+ (strategy.getMoney() - strategy.getCost()));
//
//
//        param.setDownTimeForBuy(-4);
//        param.setUpTimeForSell(3);
//        strategy = new TendencyGuessFeeOne(param, true, Market.PeatioCNY);
//        strategy.tendency();
//        System.out.println("--3卖 4 买-- 60分钟:" + (strategy.getMoney() - strategy.getCost()));

    }
//
//    @Test
//    public void selectStrategyOk() throws IOException {
//        TendencyStrategyParam param = new TendencyStrategyParam();
//        param.setSymbol(Symbol.btc);
//        int min = 30;
//        param.setLimitCount(5 * 24 * 60 /min);
//        param.setTendencyTime(min);
//        param.setSellRate(0.005);
//        param.setBuyRate(0.01);
//        param.setDownTimeForBuy(-4);
//        param.setUpTimeForSell(3);
//        param.setCost(10000);
//
//        param.setTendencyType(TendencyStrategy.TENDENCY_TYPE_MIN);
//        TendencyGuessFeeTwo strategy = new TendencyGuessFeeTwo(param, true);
//        strategy.tendency();
//        System.out.println("--4卖 3 买 30分钟:" + (strategy.getMoney() - strategy.getCost()));
//
//
//        param.setDownTimeForBuy(-3);
//        param.setUpTimeForSell(2);
//        strategy = new TendencyGuessFeeOne(param, true);
//        strategy.tendency();
//        System.out.println("--2卖 3 买-- 30分钟 :" + (strategy.getMoney() - strategy.getCost()));
//
//
//
//        min = 60;
//        param.setLimitCount(5 * 24 * 60 /min);
//
//        param.setDownTimeForBuy(-3);
//        param.setUpTimeForSell(2);
//        strategy = new TendencyGuessFeeOne(param, true);
//        strategy.tendency();
//        System.out.println("--2卖 3 买-- 30分钟 :"+ (strategy.getMoney() - strategy.getCost()));
//
//
//        param.setDownTimeForBuy(-4);
//        param.setUpTimeForSell(3);
//        strategy = new TendencyGuessFeeOne(param, true);
//        strategy.tendency();
//        System.out.println("--3卖 4 买-- 30分钟:" + (strategy.getMoney() - strategy.getCost()));
//
//    }

    @Test
    public void runBtcAndTendency5Min() throws IOException {
        TendencyStrategyParam param = new TendencyStrategyParam();
        param.setSymbol(Symbol.eth);
        int min = 30;
        param.setLimitCount(10 * 24 * 60 / min);
        param.setTendencyTime(min);
        param.setSellRate(0.005);
        param.setBuyRate(0.01);
        param.setDownTimeForBuy(-3);
        param.setUpTimeForSell(3);
        param.setCost(10000);

        param.setTendencyType(TendencyStrategy.TENDENCY_TYPE_MIN);
        TendencyStrategy strategy = new TendencyGuessFeeOne(param, true, Market.PeatioCNY);
        strategy.tendency();
        System.out.println(strategy.getResult());
    }

    @Test
    public void test() {
        String[] ss = StringUtils.split("ss,", ",");
        System.out.println(ss.length);
        System.out.println(getFieldValue("ss,", 1));
    }

    private String getFieldValue(String valTemp, int i) {
        String[] vals = StringUtils.split((valTemp), ",");
        if (vals.length > i) {
            return vals[i];
        } else {
            return "";
        }
    }
}
