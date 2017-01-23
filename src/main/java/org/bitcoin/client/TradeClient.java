package org.bitcoin.client;

import org.bitcoin.client.strategy.TendencyStrategyParam;
import org.bitcoin.client.strategy.impl.TendencyGuessFeeOne;
import org.bitcoin.client.strategy.impl.TendencyGuessFeeThree;
import org.bitcoin.client.strategy.impl.TendencyGuessFeeTwo;
import org.bitcoin.client.strategy.impl.TendencyStrategy;
import org.bitcoin.market.bean.Symbol;

import java.io.IOException;

/**
 * Created by handong on 17/1/16.
 */
public class TradeClient {

    public static void main(String[] args) throws IOException {
//        runBtcAndTendencyHalfHour();
//
//        runScAndTwo();
        runZmcAndTendency5Min();

        runBtcAndTendency5Min();
    }

    /**
     * test 1 1/01-1/22 get 2467 35%
     */
    private static void runZmcAndTendency5Min() throws IOException {
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
        TendencyStrategy strategy = new TendencyGuessFeeOne(param, false);
        TendencyStrategyThread tradeThread = new TendencyStrategyThread(strategy);
        tradeThread.start();
//        strategy.tendency();
    }

    public static void runBtcAndTendency5Min() {
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
        TendencyStrategy strategy = new TendencyGuessFeeOne(param, false);
        TendencyStrategyThread tradeThread = new TendencyStrategyThread(strategy);
        tradeThread.start();
//        strategy.tendency();
    }

    private static void runScAndTwo() throws IOException {
        TendencyStrategyParam param = new TendencyStrategyParam();
        param.setSymbol(Symbol.sc);
        param.setLimitCount(100);
        param.setTendencyTime(60);
        param.setSellRate(0.005);
        param.setBuyRate(0.01);
        param.setTendencyType(TendencyStrategy.TENDENCY_TYPE_MIN);
        TendencyStrategy strategyTow = new TendencyGuessFeeTwo(param, false);

        TendencyStrategyThread tradeThread = new TendencyStrategyThread(strategyTow);
        tradeThread.start();
    }
}
