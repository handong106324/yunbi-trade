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
    public void runBtcAndTendencyHalfHour() throws IOException {
        TendencyStrategyParam param = new TendencyStrategyParam();
        param.setSymbol(Symbol.btc);
        int min = 5;
        param.setLimitCount(24 * 60 /min);
        param.setTendencyTime(min);
        param.setSellRate(0.005);
        param.setBuyRate(0.01);
        param.setTendencyType(TendencyStrategy.TENDENCY_TYPE_MIN);
        TendencyStrategy strategy = new TendencyGuessFeeThree(param, true);
//        TendencyStrategyThread tradeThread = new TendencyStrategyThread(strategy);
//        tradeThread.start();
        strategy.tendency();
    }
    @Test
    public void runBtcAndTendencyHalfHourForOne() throws IOException {
        TendencyStrategyParam param = new TendencyStrategyParam();
        param.setSymbol(Symbol.btc);
        int min = 5;
        param.setLimitCount(1 * 24 * 60 /min);
        param.setTendencyTime(min);
        param.setSellRate(0.005);
        param.setBuyRate(0.01);
        param.setTimeForSell(-4);
        param.setDownTimeForBuy(-4);
        param.setUpTime(4);
        param.setCost(10000);

        param.setTendencyType(TendencyStrategy.TENDENCY_TYPE_MIN);
        TendencyStrategy strategy = new TendencyGuessFeeOne(param, true);
//        TendencyStrategyThread tradeThread = new TendencyStrategyThread(strategy);
//        tradeThread.start();
        strategy.tendency();
    }

    @Test
    public void runScAndTwo() throws IOException {
        TendencyStrategyParam param = new TendencyStrategyParam();
        param.setSymbol(Symbol.sc);
        param.setLimitCount(100);
        param.setTendencyTime(60);
        param.setSellRate(0.005);
        param.setBuyRate(0.01);
        param.setTendencyType(TendencyStrategy.TENDENCY_TYPE_MIN);
        TendencyStrategy strategyTow = new TendencyGuessFeeTwo(param, true);

       strategyTow.tendency();
    }


}
