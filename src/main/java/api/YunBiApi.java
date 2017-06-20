package api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import platform.yunbi.market.AbstractMarketApi;
import platform.yunbi.market.MarketApiFactory;
import platform.yunbi.market.bean.*;

import java.util.List;

import static org.junit.Assert.*;

public class YunBiApi {
    public static AppAccount getAppAccount() {

        AppAccount account = new AppAccount();
        account.setAccessKey("geTP9APdBwIN535LERMI614xIvtFTgIZo06p5orY");
        account.setSecretKey("JWBPTgCrYmsENodw6A8SlxP5CMx0tGebro617ruF");
        account.setClientId("yunbi");
        account.setEmail("442835857@qq.com");
        return account;
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


    public JSONArray getAccountInfo() throws Exception {
        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        return market.getInfo(getAppAccount());
    }

    public BitOrder testBitOrder(Symbol symbol, Long orderId) throws Exception {
        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        return market.getOrder(getAppAccount(), orderId, new SymbolPair(symbol, Symbol.cny));
    }

    public List<BitOrder> getRunningOrder() throws Exception {

        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        List<BitOrder> bitOrders = market.getRunningOrders(getAppAccount());
        return bitOrders;
    }


    public double getTicker(Symbol symbol) throws Exception {

        AbstractMarketApi abstractMarketApi = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        double ticker = abstractMarketApi.ticker(new SymbolPair(symbol, Symbol.cny));
        return ticker;
    }

    public DepthOrder getDepth() throws Exception {
        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        JSONObject depth = market.get_depth(new SymbolPair(Symbol.gnt, Symbol.cny), true);
        return new DepthOrder(depth);
    }

}
