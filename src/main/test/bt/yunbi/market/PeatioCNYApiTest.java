package bt.yunbi.market;

import com.alibaba.fastjson.JSONObject;
import bt.yunbi.common.FiatConverter;
import bt.yunbi.market.bean.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PeatioCNYApiTest {

    private AppAccount getAppAccount() {
        AppAccount appAccount = new AppAccount();
        appAccount.setId(1L);
        appAccount.setAccessKey("key"); // todo 替换为access_key
        appAccount.setSecretKey("sec"); // todo 替换为secret_key
        return appAccount;
    }

    @Test
    public void testBuyAndCancel() throws Exception {

        Double amount = 0.01;
        Double price = 10.0; // usd
        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        Long orderId = market.buy(getAppAccount(), amount, price, new SymbolPair(Symbol.eth, Symbol.cny));
        BitOrder order = market.getOrder(getAppAccount(), orderId, null);
        assertNotNull(order);
        assertEquals(OrderStatus.none, order.getStatus());
        assertEquals(amount, order.getOrderAmount());
        assertEquals(new Double("0.0"), order.getProcessedAmount());
        market.cancel(getAppAccount(), orderId, null);
        order = market.getOrder(getAppAccount(), orderId, null);
        assertNotNull(order);
        assertEquals(OrderStatus.cancelled, order.getStatus());
    }

    @Test
    public void testSellAndCancel() throws Exception {

        Double amount = 0.01;
        Double price = 10000.0; // usd
        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        Long orderId = market.sell(getAppAccount(), amount, price, new SymbolPair(Symbol.eth, Symbol.cny));
        BitOrder order = market.getOrder(getAppAccount(), orderId, null);
        assertNotNull(order);
        assertEquals(OrderStatus.none, order.getStatus());
        assertEquals(amount, order.getOrderAmount());
        assertEquals(new Double(0.0), order.getProcessedAmount());
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
        double ticker = abstractMarketApi.ticker(new SymbolPair(Symbol.eth, Symbol.cny));
        assertTrue(ticker > 0.0);

    }

    @Test
    public void testDepth() throws Exception {

        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        JSONObject depth = market.get_depth(new SymbolPair(Symbol.gnt, Symbol.cny), true);
        System.out.println(depth.containsKey("asks"));
        System.out.println(depth.containsKey("bids"));

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
