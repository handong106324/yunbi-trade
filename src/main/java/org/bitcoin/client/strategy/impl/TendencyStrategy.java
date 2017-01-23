package org.bitcoin.client.strategy.impl;

import org.bitcoin.client.strategy.AbsStrategy;
import org.bitcoin.client.strategy.TendencyResult;
import org.bitcoin.client.strategy.TendencyStrategyParam;
import org.bitcoin.market.AbstractMarketApi;
import org.bitcoin.market.MarketApiFactory;
import org.bitcoin.market.bean.Kline;
import org.bitcoin.market.bean.Market;
import org.bitcoin.market.utils.DateUtil;

import java.io.IOException;
import java.util.List;

/**
 * Created by handong on 17/1/20.
 * 首次测试1.01 - 1.22 盈利 2467
 */
public abstract class TendencyStrategy extends AbsStrategy{

    public static final int TENDENCY_TYPE_MIN = 0;
    public static final int TENDENCY_TYPE_HOUR = 1;
    public static final int TENDENCY_TYPE_DATE = 2;
    private double money;
    private int result;


    private double amount =0;
    private boolean hasLog = false;
    private boolean canBuy = true;

    private double lastBuyPrice;

    public TendencyStrategy(TendencyStrategyParam param, boolean hasLog) {
        super(param);
        this.hasLog = hasLog;
    }

    @Override
    public void firstBuy() throws IOException {

    }

    @Override
    public void sell() throws IOException {

    }

    @Override
    public void buy() throws IOException {

    }

    public TendencyStrategy tendency() {

        try {
            AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);

            List<Kline> klines;
            TendencyStrategyParam tendencyStrategyParam = (TendencyStrategyParam) getStrategyParam();
            int tp = ((TendencyStrategyParam)getStrategyParam()).getTendencyType();
            if (tp == TENDENCY_TYPE_MIN) {

                klines = market.getKlineMin(getStrategyParam().getSymbol(),
                        tendencyStrategyParam.getTendencyTime(), tendencyStrategyParam.getLimitCount());
            } else if (tp == TENDENCY_TYPE_HOUR) {
                klines = market.getKlineHour(getStrategyParam().getSymbol(),
                        tendencyStrategyParam.getTendencyTime(), tendencyStrategyParam.getLimitCount());
            }else {
                klines = market.getKlineDate(getStrategyParam().getSymbol(),
                        tendencyStrategyParam.getTendencyTime(), tendencyStrategyParam.getLimitCount());
            }
            String st = DateUtil.format(klines.get(0).getDatetime());
            String et = DateUtil.format(klines.get(((TendencyStrategyParam) getStrategyParam()).getLimitCount() -1).getDatetime());

            log(st +" -> " + et + ":\n");

            return tendcy(klines);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private TendencyStrategy tendcy(List<Kline> klines) {
        TendencyResult result = new TendencyResult();
        for (Kline kline : klines) {
            double cu = kline.getOpen() - kline.getClose();
            if (cu == 0) {
                continue;
            }
            int td = cu > 0 ? 1:-1;
            result.compute(td, kline.getOpen().doubleValue());
            guess(result, kline);
        }
        log("one down = " + result.getOneDownTimes() +" up:" + result.getOneUpTimes());
        log("two down = " + result.getTwoDownTime() +" up:" + result.getTwoUpTimes());
        log("three down = " + result.getThreeDownTime() +" up:" + result.getThreeUpTimes());
        log("four down = " + result.getFourDownTime() +" up:" + result.getFourUpTimes());
        log("five down = " + result.getFiveDownTime() +" up:" + result.getFiveUpTimes());
        log("six down = " + result.getSixDownTime() +" up:" + result.getSixUpTimes());
        log("seven down = " + result.getSevenDownTime() +" up:" + result.getSevenUpTimes());
        log("eight down = " + result.getEightDownTime() +" up:" + result.getTenUpTimes());
        log("nine down = " + result.getNineDownTime() +" up:" + result.getNineUpTimes());

        return this;
    }

    public boolean isCanBuy() {
        return canBuy;
    }

    public void setCanBuy(boolean canBuy) {
        this.canBuy = canBuy;
    }

    public abstract void guess(TendencyResult result, Kline kline);

    public double getLastBuyPrice() {
        return lastBuyPrice;
    }

    public void setLastBuyPrice(double lastBuyPrice) {
        this.lastBuyPrice = lastBuyPrice;
    }

    public boolean isHasLog() {
        return hasLog;
    }

    public void setHasLog(boolean hasLog) {
        this.hasLog = hasLog;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getResult() {
        return result;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getCost() {
        return getStrategyParam().getCost();
    }
}
