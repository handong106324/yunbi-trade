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
public class ShortStrategy extends AbsStrategy {

    public ShortStrategy(StrategyParam param) throws IOException {
        super(param);
        this.fileWriter = new FileWriter(new File(param.getSymbol().name() +".text"));
    }
    private double totalRate = 1;
    private double lastTicker = 0;

    private double sellPrice =0;

    @Override
    public void firstBuy() throws IOException {
        /**
         * logic
         *     1: 上升并且连续feeDown 大于 10
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
            java.lang.Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void sell() throws IOException {
        boolean flag = true;

        UpDownFlag upDownFlag = new UpDownFlag();
        while (flag) {
            sleep();
            double currentPrice = getTicker();
            if (currentPrice == lastTicker) {
                continue;
            }
            upDownFlag.check(currentPrice, lastTicker);

            lastTicker = currentPrice;
            boolean zhisun = upDownFlag.isUp() && upDownFlag.isContinueDown() && (currentPrice - getBuyPrice())/getBuyPrice() > getStrategyParam().getSellRate() * 2;
            boolean yingli =  (!upDownFlag.isUp() &&  (currentPrice - getBuyPrice())/getBuyPrice() > getStrategyParam().getSellRate());
            //盈利 回头卖
            if (zhisun || yingli) {
                totalRate *= ( 1 + (currentPrice - getBuyPrice())/getBuyPrice());
                log("卖出" +(zhisun?"zhisun":"盈利")+",sell=" + currentPrice +" 盈利=" +(currentPrice - getBuyPrice())/getBuyPrice() +
                " total:" + (totalRate));
                writeFile("卖出盈利,price=" + currentPrice +" 盈利=" +(currentPrice - getBuyPrice())/getBuyPrice() +
                        " total:" + (totalRate));
                flag = false;
                sellPrice = currentPrice;
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
            log(upDownFlag.isUp() + "[" + sellPrice +"->" + currentPrice +"]");
            if (downAndFeeFive(upDownFlag)|| downAndSmallerThanBuyRate(currentPrice, upDownFlag)) {
                log("买入,price=" + currentPrice);
                writeFile("买入,price=" + currentPrice);
                setBuyPrice(currentPrice);
                flag = false;
            }

        }
    }

    private boolean downAndSmallerThanBuyRate(double currentPrice, UpDownFlag upDownFlag) {
        boolean is = upDownFlag.isUp() && (sellPrice - currentPrice) /sellPrice > getStrategyParam().getBuyRate();
        if (is) {
            log("降大于" + getStrategyParam().getBuyRate() +" 并且有回升");
        }
        return is;
    }

    private boolean downAndFeeFive(UpDownFlag upDownFlag) {
        boolean is = upDownFlag.isUp() && upDownFlag.getFeeDown() > 5;
        if (is) {
            log("连续降5次,并且回升");
        }
        return is;
    }


}
