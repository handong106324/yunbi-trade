package org.bitcoin.client.strategy.impl;

import org.bitcoin.market.utils.DateUtil;
import org.bitcoin.client.strategy.TendencyResult;
import org.bitcoin.client.strategy.TendencyStrategyParam;
import org.bitcoin.market.bean.Kline;

/**
 * Created by handong on 17/1/22.
 */
public class TendencyGuessFeeThree extends TendencyStrategy {
    public TendencyGuessFeeThree(TendencyStrategyParam param) {
        super(param);
    }

    @Override
    public void guess(TendencyResult result, Kline kline) {
        int res = 0;
        int currentTendency = result.getCurrentTendency();
        if (currentTendency == -3 && isCanBuy()) {
            result.setMoney(result.getMoney() - kline.getOpen());

            if (getCost() == 0) {
                setCost(kline.getOpen());
            }
            setLastBuyPrice(kline.getOpen());
//            log(DateUtil.format(kline.getDatetime()) + " feeDown three and buy " + (kline.getOpen()) +" and left:" + result.getMoney());
            setCanBuy(false);
            res = 1;
        }


        //盈利
        if (upMoney(currentTendency, kline) || (
                (currentTendency == -4 && !isCanBuy()))) {
            result.setMoney(result.getMoney() + kline.getOpen());
//            log(DateUtil.format(kline.getDatetime()) + " feeDown Three and sell then get:" + kline.getOpen() +" left :" + result.getMoney());
//            log("rate = "+(result.getMoney() - 10000)/getCost() + " get=" + (result.getMoney() - 10000));
            setCanBuy(true);
            res = -1;
        }

        result.setResult(res);

    }

    private boolean upMoney(double currentTendency, Kline kline) {

        return currentTendency == 3 && !isCanBuy() && getLastBuyPrice() > 0
                && (((kline.getOpen() - getLastBuyPrice())/getLastBuyPrice()) > 0.015);
    }


}
