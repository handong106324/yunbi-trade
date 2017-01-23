package org.bitcoin.client;

import org.bitcoin.client.strategy.TendencyStrategyParam;
import org.bitcoin.client.strategy.impl.TendencyStrategy;
import org.bitcoin.market.utils.DateUtil;

import java.util.Date;

/**
 * Created by handong on 17/1/22.
 */
public class TendencyStrategyThread extends Thread{
    private TendencyStrategy strategy;

    public TendencyStrategyThread(TendencyStrategy strategy) {
        this.strategy = strategy;
    }
    @Override
    public void run() {
        runTrade(strategy);
    }

    private void runTrade(TendencyStrategy strategy) {
        double money = 0;
        double cost = 0;
        int time = ((TendencyStrategyParam)strategy.getStrategyParam()).getTendencyTime();
        long start = System.currentTimeMillis();
        long during = time * getTimeDuring();
        long waitTime =(during - start % (time * getTimeDuring()));
        while (true) {

            log(DateUtil.format(new Date()) + " wait =" + waitTime + " (" + waitTime /getTimeDuring() +")");
            try {
                Thread.sleep(waitTime);
                log(DateUtil.format(new Date()) + " 等待结束 真实运行 - ");

                int res = strategy.tendency().getResult();
                if (res == 1) {
                    double ask = strategy.getFirstAsk();
                    money -= ask;
                    if (cost == 0) {
                        cost = ask;
                    }
                    log("buy :" + ask);
                    strategy.writeFile("buy:" + ask);
                } else if (res == -1) {
                    double bid = strategy.getFirstBid();
                    money += bid;
                    log("sell :" + bid +" get=" + money );
                    strategy.writeFile("sell :" + bid +" get=" + money);
                } else {
                    log("non");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void log(String log) {
        System.out.println(strategy.getStrategyParam().getSymbol() + " : " +log);
    }

    public long getTimeDuring() {
        int type =  ((TendencyStrategyParam) strategy.getStrategyParam()).getTendencyType();
        if (TendencyStrategy.TENDENCY_TYPE_HOUR == type){
            return 60 * 60 * 1000;
        } else if (TendencyStrategy.TENDENCY_TYPE_MIN == type) {
            return 60 * 1000;
        } else {
            return 24 * 60 * 60 * 1000;
        }
    }
}
