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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by handong on 17/1/16.
 * pri:
 * 	
 */
public class TwoPhaseStrategy extends AbsStrategy {

    public TwoPhaseStrategy(StrategyParam param) throws IOException {
        super(param);
        this.fileWriter = new FileWriter(new File("zmc.text"));
    }
    FileWriter fileWriter;
    private boolean isUp = false;
    private double totalRate = 1;
    private double lastTicker = 0;
    private double min = 0;
    private long time = System.currentTimeMillis();

    @Override
    public void firstBuy() throws IOException {
        /**
         * logic
         *     第一次查询出的ticker为参照物
         */
    	List<Double> historys = new ArrayList();
    	lastTicker = getTicker();
        min = lastTicker;
        double bPrice = lastTicker;
        historys.add(lastTicker);
        boolean flag = true;
        while (flag) {
            sleep();
            double ticker = getTicker();
            historys.add(ticker);
            boolean isUp = ticker > lastTicker;
            log("价格:" + lastTicker +" -> " + ticker);
            lastTicker = ticker;

            if (isUp && ((ticker - bPrice)/bPrice) > getStrategyParam().getBuyRate() ) {
                setBuyPrice(ticker);
                log("买入" + ticker);
                flag = false;
            } else {
                lastTicker = ticker;
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
        while (flag) {
            sleep();
            double currentPrice = getTicker();
            boolean isUpTwo = checkUpTwo(currentPrice);
            lastTicker = currentPrice;
            //盈利 回头卖
            if (!isUp && (currentPrice - getBuyPrice())/getBuyPrice() > getStrategyParam().getSellRate()) {
                totalRate *= ( 1 + (currentPrice - getBuyPrice())/getBuyPrice());
                log("卖出盈利,sell=" + currentPrice +" 盈利=" +(currentPrice - getBuyPrice())/getBuyPrice() +
                " total:" + (totalRate));
                writeFile("卖出盈利,price=" + currentPrice +" 盈利=" +(currentPrice - getBuyPrice())/getBuyPrice() +
                        " total:" + (totalRate));
                flag = false;
                setBuyPrice(currentPrice);
            }
        }
    }

    private boolean checkUpTwo(double currentPrice) {
//		if (System.currentTimeMillis() - time > getStrategyParam().getDuring()) {
//
//		}
		return false;
	}


	@Override
    public void buy() throws IOException {
        double currentPrice = getTicker();
        lastTicker = currentPrice;
        boolean flag = true;
        while (flag) {
            sleep();
            currentPrice = getTicker();

            if (currentPrice > lastTicker) {
                isUp = true;
            } else {
                isUp = false;
            }
            log(isUp +":" + DoubleUtils.toFourDecimal(currentPrice - getBuyPrice())/getBuyPrice());
            if (isUp && (getBuyPrice() - currentPrice) /getBuyPrice() > getStrategyParam().getBuyRate()) {
                log("买入,price=" + currentPrice);
                writeFile("买入,price=" + currentPrice);
                setBuyPrice(currentPrice);
                flag = false;
            }

        }
    }

}
