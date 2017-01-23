package yun.bt.test;

import org.bitcoin.client.*;
import org.bitcoin.client.strategy.TendencyStrategyParam;
import org.bitcoin.client.strategy.impl.TendencyGuessFeeOne;
import org.bitcoin.client.strategy.impl.TendencyGuessFeeThree;
import org.bitcoin.client.strategy.impl.TendencyGuessFeeTwo;
import org.bitcoin.client.strategy.impl.TendencyStrategy;
import org.bitcoin.market.bean.Symbol;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by handong on 17/1/23.
 */
public class TradeClientTest {


    @Test
    public void selectStrategy() throws IOException {
        TendencyStrategyParam param = new TendencyStrategyParam();
        param.setSymbol(Symbol.zmc);
        int min = 5;
        param.setLimitCount(7 * 24 * 60 /min);
        param.setTendencyTime(min);
        param.setSellRate(0.005);
        param.setBuyRate(0.01);
        param.setTimeForSell(-3);
        param.setDownTimeForBuy(-1);
        param.setUpTime(1);
        param.setCost(100);

        param.setTendencyType(TendencyStrategy.TENDENCY_TYPE_MIN);
        TendencyStrategy strategy = new TendencyGuessFeeOne(param, true);
//        TendencyStrategyThread tradeThread = new TendencyStrategyThread(strategy);
//        tradeThread.start();
        strategy.tendency();
    }

}
