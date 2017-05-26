package platform.yunbi.market;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import platform.yunbi.common.FiatConverter;
import platform.yunbi.common.HttpUtils;
import platform.yunbi.market.bean.*;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by lichang on 14-2-8.
 */
public abstract class AbstractMarketApi {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractMarketApi.class);

    private Currency currency = Currency.CNY;
    private static final long MARKET_EXPIRATION_TIME = 120L;
    private static final int MAX_DEPTH_LEN = 300;

    Market market;
    long lastUpdate = 0L;
    long depth_updated = 10L;

    public AbstractMarketApi(Currency currency, Market market) {
        this.currency = currency;
        this.market = market;
        this.init_depth();
    }

    public Long buy(AppAccount appAccount, double amount, double price, SymbolPair symbolPair) {
        return buy(appAccount, amount, price, symbolPair, OrderType.Limit);
    }

    public abstract Long buy(AppAccount appAccount, double amount, double price, SymbolPair symbolPair, OrderType orderType);

    public Long sell(AppAccount appAccount, double amount, double price, SymbolPair symbolPair) {
        return sell(appAccount, amount, price, symbolPair, OrderType.Limit);
    }

    public abstract Long sell(AppAccount appAccount, double amount, double price, SymbolPair symbolPair, OrderType orderType);

    public abstract void cancel(AppAccount appAccount, Long orderId, SymbolPair symbolPair);

    public Long replace(AppAccount appAccount, Long orderId, double amount, double price, OrderSide orderSide, SymbolPair symbolPair, OrderType orderType) {
        cancel(appAccount, orderId, symbolPair);
        if (OrderSide.buy.equals(orderSide)) {
            return buy(appAccount, amount, price, symbolPair, orderType);
        } else if (OrderSide.sell.equals(orderSide)) {
            return sell(appAccount, amount, price, symbolPair, orderType);
        }
        LOG.error("appAccount:{} orderId:{} amount:{} price:{} side:{} pair:{}", appAccount, orderId,
                amount, price, orderSide, symbolPair);
        throw new RuntimeException("replace error orderSide error:" + orderSide);
    }

    public Long replace(AppAccount appAccount, Long orderId, double amount, double price, OrderSide orderSide, SymbolPair symbolPair) {
        return replace(appAccount, orderId, amount, price, orderSide, symbolPair, OrderType.Limit);
    }

    public JSONObject get_depth(SymbolPair symbolPair, boolean force_update) {
        JSONObject depth;
        long timediff = System.currentTimeMillis() / 1000 - this.depth_updated;
        if (!force_update && timediff < this.depth_updated) {
            sleep((int) ((this.depth_updated - timediff) * 1000));
        }
        depth = this.ask_update_depth(symbolPair);
        timediff = System.currentTimeMillis() / 1000 - this.depth_updated;
        if (timediff > MARKET_EXPIRATION_TIME) {
            LOG.warn("Market: {} order book is expired", this.getMarket());
            return this.init_depth();

        }
        return depth;
    }

    private JSONObject init_depth() {
        String depthStr = "{'asks': [{'price': 0, 'amount': 0}], 'bids': [{'price': 0, 'amount': 0}]}";
        return JSONArray.parseObject(depthStr);
    }

    private JSONObject ask_update_depth(SymbolPair symbolPair) {
        try {
            JSONObject depth = this.update_depth(symbolPair);
            this.fix_depth(depth);
            this.depth_updated = System.currentTimeMillis() / 1000;
            return depth;
        } catch (Exception e) {
            LOG.error("Can't update market:" + this.getMarket(), e.getMessage());
        }

        return null;
    }

    private void fix_depth(JSONObject depth) {
        JSONArray asks = depth.getJSONArray("asks");
        JSONArray bids = depth.getJSONArray("bids");

        if (asks.size() > MAX_DEPTH_LEN) {
            for (int i = asks.size() - 1; i >= MAX_DEPTH_LEN; i--) {
                asks.remove(i);
            }
        }
        if (bids.size() > MAX_DEPTH_LEN) {
            for (int i = bids.size() - 1; i >= MAX_DEPTH_LEN; i--) {
                bids.remove(i);
            }
        }

        if (asks.size() < 2 || bids.size() < 2) {
            return;
        }
        double ask1 = asks.getJSONObject(0).getDouble("price");
        double ask2 = asks.getJSONObject(1).getDouble("price") * 1.0005;
        List<JSONObject> removeBids = new ArrayList<JSONObject>();
        for (int i = 0; i < bids.size() - 2; i++) {
            JSONObject order = bids.getJSONObject(i);
            double price = order.getDouble("price");
            if (price > ask1 && price > ask2) {
                removeBids.add(order);
                LOG.warn("Market: {} order has abnormal bids data:{}, aks2:{}", this.getMarket(), order, ask2);
            } else {
                break;
            }
        }
        bids.removeAll(removeBids);

        if (bids.size() < 2) {
            return;
        }
        double bid1 = bids.getJSONObject(0).getDouble("price");
        double bid2 = bids.getJSONObject(1).getDouble("price") * 0.9995;
        List<JSONObject> removeAsks = new ArrayList<JSONObject>();
        for (int i = 0; i < asks.size() - 2; i++) {
            JSONObject order = asks.getJSONObject(i);
            double price = order.getDouble("price");
            if (price < bid1 && price < bid2) {
                removeAsks.add(order);
                LOG.warn("Market: {} order has abnormal asks data:{}, aks2:{}", this.getMarket(), order, bid2);
            } else {
                break;
            }
        }
        asks.removeAll(removeAsks);


    }

    protected void convert_to_usd(JSONObject depth) {

        if (this.currency == Currency.USD) {
            return;
        }
        String[] directions = {"asks", "bids"};
        for (String direction : directions) {
            JSONArray orders = depth.getJSONArray(direction);
            for (Object anOrdersResponse : orders) {
                JSONObject order = (JSONObject) anOrdersResponse;
                order.put("price", FiatConverter.toUsd(order.getDouble("price")));
            }
        }
    }


    public abstract JSONObject update_depth(SymbolPair symbol);


    public abstract Asset getInfo(AppAccount appAccount);

    public abstract List<Kline> getKlineDate(Symbol symbol, int days, int limitCount) throws IOException, ParseException;

    public abstract List<Kline> getKlineMin(Symbol symbol, int mins, int limitCount) throws IOException, ParseException;

    public abstract List<Kline> getKlineHour(Symbol symbol, int hours, int limitCount) throws IOException, ParseException;

    public Market getMarket() {
        return this.market;
    }

    public abstract Double ticker(SymbolPair symbol) throws IOException;

    public abstract BitOrder getOrder(AppAccount appAccount, Long orderId, SymbolPair symbolPair);

    public abstract List<BitOrder> getRunningOrders(AppAccount appAccount);

    Long createNonce() {
        return System.currentTimeMillis() / 1000;
    }

    public abstract Double getTransactionFee();

    public abstract Double getWithdrawalFee();

    public abstract Double getDepositFee();


    public Double getWholeFee() {
        return getDepositFee() + getWithdrawalFee() + getTransactionFee();
    }


    protected void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e1) {
            // ignore
        }
    }

    protected JSONObject format_depth(JSONObject data) {
        JSONArray bids = this.sort_and_format(data.getJSONArray("bids"), true);
        JSONArray asks = this.sort_and_format(data.getJSONArray("asks"), false);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("asks", asks);
        jsonObject.put("bids", bids);
        return jsonObject;
    }

    public String parse_json_str(String url) throws IOException {
        Document objectDoc = HttpUtils.getConnectionForGet(url).ignoreContentType(true).timeout(15000).get();
        return objectDoc.body().text();
    }

    protected JSONArray sort_and_format(JSONArray jsonArray, boolean reverse) {

        if (reverse) {
            Collections.sort(jsonArray, new ReversePriceComparator());
        } else {
            Collections.sort(jsonArray, new PriceComparator());

        }
        JSONArray jsonArray1 = new JSONArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONArray jsonArray2 = jsonArray.getJSONArray(i);
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("price", Double.parseDouble(jsonArray2.get(0).toString()));
            jsonObject1.put("amount", Double.parseDouble(jsonArray2.get(1).toString()));
            jsonArray1.add(jsonObject1);

        }
        return jsonArray1;
    }

    public abstract List<Kline> getKlines(String url, Symbol symbol) throws ParseException, IOException;

    class PriceComparator implements Comparator<Object> {
        public int compare(Object member1, Object member2) {
            //compareTo，两个对象属性之间的比较，返回负数，0和正数
            return Double.compare(Double.parseDouble(((JSONArray) member1).get(0).toString()),
                    Double.parseDouble(((JSONArray) member2).get(0).toString()));
        }
    }

    class ReversePriceComparator implements Comparator<Object> {
        public int compare(Object member1, Object member2) {
            //compareTo，两个对象属性之间的比较，返回负数，0和正数
            return Double.compare(Double.parseDouble(((JSONArray) member2).get(0).toString()),
                    Double.parseDouble(((JSONArray) member1).get(0).toString()));

        }
    }

}

