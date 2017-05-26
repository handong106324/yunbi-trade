package test;

import platform.yunbi.market.AbstractMarketApi;
import platform.yunbi.market.MarketApiFactory;
import platform.yunbi.market.bean.Market;
import platform.yunbi.market.bean.Symbol;
import platform.yunbi.market.bean.SymbolPair;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by handong on 17/5/16.
 */
public abstract class BasicTestRunnable implements Runnable {
    public String in(Symbol symbol, List<String> pairs, boolean b, String sp) {

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

}
