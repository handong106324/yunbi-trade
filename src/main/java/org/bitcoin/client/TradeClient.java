package org.bitcoin.client;

import org.bitcoin.client.strategy.AbsStrategy;
import org.bitcoin.client.strategy.StrategyParam;
import org.bitcoin.client.strategy.TendencyStrategyParam;
import org.bitcoin.client.strategy.impl.TendencyGuessFeeThree;
import org.bitcoin.client.strategy.impl.TendencyGuessFeeTwo;
import org.bitcoin.client.strategy.impl.TendencyStrategy;
import org.bitcoin.market.bean.Symbol;

import java.io.IOException;

/**
 * Created by handong on 17/1/16.
 */
public class TradeClient {

    private AbsStrategy absStrategy;
    public TradeClient(AbsStrategy strategy) {
        this.absStrategy = strategy;
    }

    public void execute() throws IOException {
        absStrategy.trade();
    }

    public static void main(String[] args) throws IOException {
//        runZmcAndShort();
//
//        runEtcAndShort();
//
//        runBtcAndShort();


        runBtcAndTendencyHalfHour();

        runScAndTwo();

    }

    /**
     * test 1 1/01-1/22 get 2467 35%
     */
    private static void runBtcAndTendencyHalfHour() throws IOException {
        TendencyStrategyParam param = new TendencyStrategyParam();
        param.setSymbol(Symbol.btc);
        param.setLimitCount(100);
        param.setTendencyTime(30);
        param.setTendencyType(TendencyStrategy.TENDENCY_TYPE_MIN);
        TendencyStrategy strategy = new TendencyGuessFeeThree(param);
        TendencyStrategyThread tradeThread = new TendencyStrategyThread(strategy);
        tradeThread.start();
    }

    private static void runScAndTwo() throws IOException {
        TendencyStrategyParam param = new TendencyStrategyParam();
        param.setSymbol(Symbol.sc);
        param.setLimitCount(100);
        param.setTendencyTime(60);
        param.setTendencyType(TendencyStrategy.TENDENCY_TYPE_MIN);
        TendencyStrategy strategyTow = new TendencyGuessFeeTwo(param);

        TendencyStrategyThread tradeThread = new TendencyStrategyThread(strategyTow);
        tradeThread.start();
    }

    private static void runEtcAndShort() throws IOException {
        StrategyParam param = new StrategyParam();
        param.setBuyRate(0.015);
        param.setSellRate(0.01);
        param.setSymbol(Symbol.etc);
        AbsStrategy absStrategy = new BasicMoneyStrategy(param);
        TradeClient client = new TradeClient(absStrategy);
        new TradeThread(client).start();
    }

    private static void runZmcAndShort() throws IOException {
        StrategyParam param = new StrategyParam();
        param.setBuyRate(0.015);
        param.setSellRate(0.01);
        param.setSymbol(Symbol.zmc);
        AbsStrategy absStrategy = new BasicMoneyStrategy(param);
        TradeClient client = new TradeClient(absStrategy);
        new TradeThread(client).start();
    }
    private static void runBtcAndShort() throws IOException {
        StrategyParam param = new StrategyParam();
        param.setBuyRate(0.015);
        param.setSellRate(0.01);
        param.setSymbol(Symbol.btc);
        AbsStrategy absStrategy = new ShortStrategy(param);
        TradeClient client = new TradeClient(absStrategy);
        new TradeThread(client).start();
    }
}
