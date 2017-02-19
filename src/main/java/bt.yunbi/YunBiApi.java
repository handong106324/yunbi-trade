package bt.yunbi;

import com.alibaba.fastjson.JSONObject;
import org.bitcoin.common.FiatConverter;
import org.bitcoin.market.AbstractMarketApi;
import org.bitcoin.market.MarketApiFactory;
import org.bitcoin.market.bean.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class YunBiApi {

    private static AppAccount getAppAccount() {
        AppAccount appAccount = new AppAccount();
        appAccount.setId(1L);
        appAccount.setAccessKey("key"); // todo 替换为access_key
        appAccount.setSecretKey("skey"); // todo 替换为secret_key
        return appAccount;
    }

    public BitOrder buy(Double amount, Double price, Symbol symbol, Market marketKey) throws Exception {

        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(marketKey);
        Long orderId = market.buy(getAppAccount(), amount, price, new SymbolPair(symbol, Symbol.cny));
        BitOrder order = market.getOrder(getAppAccount(), orderId, null);
        System.out.println(order);
        return order;
    }

    public static BitOrder sell(Double amount, Double price, Symbol symbol, Market marketKey) throws Exception {

        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(marketKey);
        Long orderId = market.sell(getAppAccount(), amount, price, new SymbolPair(symbol, Symbol.cny));
        BitOrder order = market.getOrder(getAppAccount(), orderId, null);
        assertNotNull(order);
        System.out.println(order);
        return order;
    }

    public static void cancel(Long orderId, Market marketKey) {
        BitOrder order;
        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(marketKey);

        market.cancel(getAppAccount(), orderId, null);
        order = market.getOrder(getAppAccount(), orderId, null);
        assertNotNull(order);
        assertEquals(OrderStatus.cancelled, order.getStatus());
    }

    @Test
    public void testGetInfo() throws Exception {
        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        Asset asset = market.getInfo(getAppAccount());
        assertNotNull(asset);
    }

//    @Test
//    public void testGetOrder() throws Exception {
//
//        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
//        Long orderId = 434669L;
//        BitOrder order = market.getOrder(getAppAccount(), orderId, new SymbolPair(Symbol.btc, Symbol.usd));
//        assertNotNull(order);
//    }

//    @Test
//    public void testGetRunningOrder() throws Exception {
//
//        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
//        List<BitOrder> bitOrders = market.getRunningOrders(getAppAccount());
//        assertTrue(bitOrders.size() > 0);
//    }
//


    @Test
    public void testGetKline5Min() throws Exception {
        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);


    }


    @Test
    public void testTicker() throws Exception {

        AbstractMarketApi abstractMarketApi = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        double ticker = abstractMarketApi.ticker(new SymbolPair(Symbol.btc, Symbol.cny));
        assertTrue(ticker > 0.0);

    }

    @Test
    public void testDepth() throws Exception {

        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        JSONObject depth = market.get_depth(new SymbolPair(Symbol.zmc, Symbol.cny), true);
        assertTrue(depth.containsKey("asks"));
        assertTrue(depth.containsKey("bids"));

    }


    private void convertToUsd(AbstractMarketApi market, Kline kline) {
        if (!market.getMarket().isUsd()) {
            kline.setOpen(FiatConverter.toUsd(kline.getOpen(), kline.getDatetime()));
            kline.setHigh(FiatConverter.toUsd(kline.getHigh(), kline.getDatetime()));
            kline.setLow(FiatConverter.toUsd(kline.getLow(), kline.getDatetime()));
            kline.setClose(FiatConverter.toUsd(kline.getClose(), kline.getDatetime()));
            kline.setVwap(FiatConverter.toUsd(kline.getVwap(), kline.getDatetime()));
        }
    }
}
