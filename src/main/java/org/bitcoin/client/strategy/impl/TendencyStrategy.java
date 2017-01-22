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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by handong on 17/1/20.
 * 首次测试1.01 - 1.22 盈利 2467
 */
public abstract class TendencyStrategy extends AbsStrategy{

    public static final int TENDENCY_TYPE_MIN = 0;
    public static final int TENDENCY_TYPE_HOUR = 1;
    public static final int TENDENCY_TYPE_DATE = 2;

    private boolean canBuy = true;

    private double cost = 0;

    private double lastBuyPrice;

    public TendencyStrategy(TendencyStrategyParam param) {
        super(param);
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

    public TendencyResult tendency() {

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

            return tendcy(klines);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private TendencyResult tendcy(List<Kline> klines) {
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
        return result;
    }

    public boolean isCanBuy() {
        return canBuy;
    }

    public void setCanBuy(boolean canBuy) {
        this.canBuy = canBuy;
    }

    public abstract void guess(TendencyResult result, Kline kline);

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getLastBuyPrice() {
        return lastBuyPrice;
    }

    public void setLastBuyPrice(double lastBuyPrice) {
        this.lastBuyPrice = lastBuyPrice;
    }
}
