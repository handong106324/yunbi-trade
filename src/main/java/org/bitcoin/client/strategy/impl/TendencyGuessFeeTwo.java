package org.bitcoin.client.strategy.impl;

import org.bitcoin.client.strategy.TendencyResult;
import org.bitcoin.client.strategy.TendencyStrategyParam;
import org.bitcoin.market.bean.Kline;
import org.bitcoin.market.utils.DateUtil;

/**
 * Created by handong on 17/1/22.
 */
public class TendencyGuessFeeTwo extends TendencyStrategy {

    public TendencyGuessFeeTwo(TendencyStrategyParam param, boolean hasLog) {
        super(param, hasLog);

    }

    @Override
    public void guess(TendencyResult result, Kline kline) {
        int res = 0;
        int currentTendency = result.getCurrentTendency();
        if (currentTendency == -2 && isCanBuy()) {
            setMoney(getMoney() - kline.getOpen());

            setLastBuyPrice(kline.getOpen());
            if (isHasLog()) {
                log(DateUtil.format(kline.getDatetime()) + " feeDown two and buy " + (kline.getOpen()) + " and left:" + getMoney());
            }
            setCanBuy(false);
            res = 1;
        }


        //盈利
        if (upMoney(currentTendency, kline)
                ||
                ((currentTendency == -3 && !isCanBuy()))
                ) {
            setMoney(getMoney() + kline.getOpen() * 0.999);
            if (isHasLog()) {
                log(DateUtil.format(kline.getDatetime()) + " feeDown two and sell then get:" + kline.getOpen() + " left :" + getMoney());
                log("rate = " + (getMoney() - 10000) / getCost() + " get=" + (getMoney() - 10000));

            }
            setCanBuy(true);
            res = -1;
        }

        setResult(res);

    }

    private boolean upMoney(double currentTendency, Kline kline) {

        return currentTendency == 2 && !isCanBuy() && getLastBuyPrice() > 0
                && (((kline.getOpen() - getLastBuyPrice())/getLastBuyPrice()) > 0.015);
    }


}
