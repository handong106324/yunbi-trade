package log;

import com.alibaba.fastjson.JSONObject;
import platform.yunbi.client.strategy.StrategyParam;
import platform.yunbi.client.strategy.impl.EasyStrategy;
import platform.yunbi.market.AbstractMarketApi;
import platform.yunbi.market.MarketApiFactory;
import platform.yunbi.market.bean.Market;
import platform.yunbi.market.bean.Symbol;
import platform.yunbi.market.bean.SymbolPair;

import java.io.IOException;

/**
 * Created by handong on 17/5/31.
 */
public class LogRecordRunnable implements Runnable{


    private Symbol symbol;

    public LogRecordRunnable(Symbol symbol) {
        this.symbol = symbol;
    }
    @Override
    public void run() {
        logRecord();
    }

    private void logRecord() {
        StrategyParam param = new StrategyParam();
        param.setSymbol(symbol);
        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);

        try {
            EasyStrategy easyStrategy = new EasyStrategy(param);
            double lastBid = 0;
            double lastAsk = 0;
            while (true) {
                SymbolPair pair = new SymbolPair(symbol, Symbol.cny);

                try {

                    JSONObject depth = market.get_depth(pair, true);

                    JSONObject ask = depth.getJSONArray("asks").getJSONObject(0);
                    JSONObject bid = depth.getJSONArray("bids").getJSONObject(0);
                    double askPrice = ask.getDouble("price");
                    double bidPrice = bid.getDouble("price");
                    if (lastAsk == askPrice && lastBid == bidPrice) {
                        continue;
                    }

                    lastAsk = askPrice;
                    lastBid = bidPrice;
                    easyStrategy.writeFile(askPrice + "-" + bidPrice);

                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
