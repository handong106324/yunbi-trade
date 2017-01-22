package org.bitcoin.client;

import org.bitcoin.client.strategy.AbsStrategy;
import org.bitcoin.client.strategy.StrategyParam;
import org.bitcoin.common.DoubleUtils;
import org.bitcoin.market.AbstractMarketApi;
import org.bitcoin.market.MarketApiFactory;
import org.bitcoin.market.bean.Market;
import org.bitcoin.market.bean.Symbol;
import org.bitcoin.market.bean.SymbolPair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by handong on 17/1/16.
 */
public class BasicMoneyStrategy extends AbsStrategy {

    public BasicMoneyStrategy(StrategyParam param) throws IOException {
        super(param);
        this.fileWriter = new FileWriter(new File(param.getSymbol().name() +".text"));
    }
    FileWriter fileWriter;
    private boolean isUp = false;
    private double totalRate = 1;
    private double lastTicker = 0;
    @Override
    public void firstBuy() throws IOException {
        /**
         * logic
         *     第一次查询出的ticker为参照物
         */

        if (lastTicker == 0) {
            lastTicker = getTicker();
            setBuyPrice(lastTicker);
        }

        boolean flag = true;
        UpDownFlag upDownFlag = new UpDownFlag();
        while (flag) {
            sleep();
            double ticker = getTicker();
            if (ticker == lastTicker) {
                continue;
            }
            upDownFlag.check(ticker, lastTicker);
            log(getBuyPrice() +"->" + lastTicker +" -> " + ticker +"["+upDownFlag.getFeeDown()+"]");

            lastTicker = ticker;
            if ((upDownFlag.isUp() && upDownFlag.isContinueDown()) ||
                    (upDownFlag.isUp() && upDownFlag.isContinueUp()) ||
                    (!upDownFlag.isUp() && (getBuyPrice() - ticker)/getBuyPrice() > getStrategyParam().getSellRate()) ||
                    (!upDownFlag.isUp() && upDownFlag.isContinueUp())) {
                setBuyPrice(ticker);
                writeFile("买入:" + ticker);
                log("buy -- " + ticker + " --");
                flag = false;
            }
        }
    }

    private void sleep() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void sell() throws IOException {
        boolean flag = true;
        while (flag) {
            sleep();
            double currentPrice = getTicker();
            if (currentPrice == lastTicker) {
                continue;
            }
            isUp = currentPrice > lastTicker;
            lastTicker = currentPrice;
            //盈利
            if (isUp && (currentPrice -getBuyPrice())/getBuyPrice() > getStrategyParam().getSellRate()) {
                totalRate *= ( 1 + (currentPrice - getBuyPrice())/getBuyPrice());
                log("卖出盈利,sell=" + currentPrice +" 盈利=" +(currentPrice - getBuyPrice())/getBuyPrice() +
                " total:" + (totalRate));
                writeFile("卖出盈利,price=" + currentPrice +" 盈利=" +(currentPrice - getBuyPrice())/getBuyPrice() +
                        " total:" + (totalRate));
                flag = false;
            }
        }
    }

    @Override
    public void buy() throws IOException {
        double currentPrice = getTicker();
        lastTicker = currentPrice;
        boolean flag = true;
        UpDownFlag upDownFlag = new UpDownFlag();
        while (flag) {
            sleep();
            currentPrice = getTicker();

            if (currentPrice == lastTicker) {
                continue;
            }
            upDownFlag.check(currentPrice, lastTicker);
            lastTicker = currentPrice;
            log(getBuyPrice() +"->" + lastTicker +" -> " + currentPrice +"["+upDownFlag.getFeeDown()+"]");

            if ((upDownFlag.isUp() && upDownFlag.isContinueDown()) ||
                    (upDownFlag.isUp() && upDownFlag.isContinueUp()) ||
                    (!upDownFlag.isUp() && (getBuyPrice() - currentPrice)/getBuyPrice() > getStrategyParam().getSellRate()) ||
                    (!upDownFlag.isUp() && upDownFlag.isContinueUp())) {
                setBuyPrice(currentPrice);
                writeFile("买入:" + currentPrice);
                log("buy -- " + currentPrice + " --");
                flag = false;
            }

        }
    }

}
