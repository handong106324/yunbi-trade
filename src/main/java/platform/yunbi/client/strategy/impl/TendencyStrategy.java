package platform.yunbi.client.strategy.impl;

import platform.yunbi.client.strategy.AbsStrategy;
import platform.yunbi.client.strategy.TendencyResult;
import platform.yunbi.client.strategy.TendencyStrategyParam;
import platform.yunbi.market.AbstractMarketApi;
import platform.yunbi.market.bean.Kline;
import platform.yunbi.market.bean.Market;
import platform.yunbi.market.utils.DateUtil;

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

    private double canBuyLine = 0;
    private double lastFiveMin = 0;
    private double avageMin = 0;
    private double amount =0;
    private boolean hasLog = false;
    private boolean canBuy = true;

    private double lastBuyPrice;
    private TendencyResult tendResult;

    public TendencyStrategy(TendencyStrategyParam param, boolean hasLog, Market market) throws IOException {
        super(param);
        this.hasLog = hasLog;
        setMarketInstacne(market);
    }


    public int tendency() {

        try {
            AbstractMarketApi market = getMarket();

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

            return tendcy(klines);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 统计上升和下跌
     * @param klines
     * @return
     */
    private int tendcy(List<Kline> klines) {
        tendResult = new TendencyResult();
        double totalMin = 0;

        for (Kline kline : klines) {

            double cu = kline.getOpen() - kline.getClose();
            if (cu == 0) {
                continue;
            }
            totalMin += kline.getLow();
            int td = cu > 0 ? 1:-1;
            tendResult.compute(td, kline.getOpen().doubleValue());
            if (hasLog) {
                guess(tendResult, kline);
            }
        }
        if (hasLog) {
            log("one down = " + tendResult.getOneDownTimes() +" up:" + tendResult.getOneUpTimes());
            log("two down = " + tendResult.getTwoDownTime() +" up:" + tendResult.getTwoUpTimes());
            log("three down = " + tendResult.getThreeDownTime() +" up:" + tendResult.getThreeUpTimes());
            log("four down = " + tendResult.getFourDownTime() +" up:" + tendResult.getFourUpTimes());
            log("five down = " + tendResult.getFiveDownTime() +" up:" + tendResult.getFiveUpTimes());
            log("six down = " + tendResult.getSixDownTime() +" up:" + tendResult.getSixUpTimes());
            log("seven down = " + tendResult.getSevenDownTime() +" up:" + tendResult.getSevenUpTimes());
            log("eight down = " + tendResult.getEightDownTime() +" up:" + tendResult.getTenUpTimes());
            log("nine down = " + tendResult.getNineDownTime() +" up:" + tendResult.getNineUpTimes());
        }

        this.avageMin = totalMin/klines.size();
        countLastFiveKline(klines);
        return this.getTendResult().getBestTend();
    }

    private void countLastFiveKline(List<Kline> klines) {
        double to = 0;
        int size = klines.size();
        to += klines.get(size - 1).getLow();
        to += klines.get(size - 2).getLow();
        to += klines.get(size - 3).getLow();
        to += klines.get(size - 4).getLow();
        to += klines.get(size - 5).getLow();
        this.lastFiveMin = to /5;
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

    public TendencyResult getTendResult(){
        return tendResult;
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

    public double getCanBuyLine() {
        return canBuyLine;
    }

    public void setCanBuyLine(double canBuyLine) {
        this.canBuyLine = canBuyLine;
    }

    public double getAvageMin() {
        return avageMin;
    }

    public void setAvageMin(double avageMin) {
        this.avageMin = avageMin;
    }

    public double getLastFiveMin() {
        return lastFiveMin;
    }

    public void setLastFiveMin(double lastFiveMin) {
        this.lastFiveMin = lastFiveMin;
    }
}
