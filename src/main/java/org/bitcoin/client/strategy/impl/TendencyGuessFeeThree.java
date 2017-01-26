package org.bitcoin.client.strategy.impl;

import org.bitcoin.market.utils.DateUtil;
import org.bitcoin.client.strategy.TendencyResult;
import org.bitcoin.client.strategy.TendencyStrategyParam;
import org.bitcoin.market.bean.Kline;

import java.io.IOException;

/**
 * Created by handong on 17/1/22.
 */
public class TendencyGuessFeeThree extends TendencyStrategy {
    public TendencyGuessFeeThree(TendencyStrategyParam param, boolean hasLog) throws IOException {
        super(param, hasLog);
    }

    @Override
    public void guess(TendencyResult result, Kline kline) {
        int res = 0;
        int currentTendency = result.getCurrentTendency();
        if (currentTendency == -3 && isCanBuy()) {
            setMoney(getMoney() - kline.getClose());

            setLastBuyPrice(kline.getClose());
            if (isHasLog()) {
                log(DateUtil.format(kline.getDatetime()) + " feeDown three and buy "
                        + (kline.getOpen()) +" and left:" + getMoney());
            }
            setCanBuy(false);
            res = 1;
        }


        //盈利
        if (upMoney(currentTendency, kline)
                || ((currentTendency == -4 && !isCanBuy()))) {
            setMoney(getMoney() + kline.getClose());
            if (isHasLog()) {
                log(DateUtil.format(kline.getDatetime()) + " feeDown Three and sell then get:"
                        + kline.getClose() +" left :" + getMoney());
                log("rate = "+(getMoney() - 10000)/getCost() + " get=" + (getMoney() - 10000));
            }
            setCanBuy(true);
            res = -1;
        }

        setResult(res);

    }

    private boolean upMoney(double currentTendency, Kline kline) {

        return currentTendency == 3 && !isCanBuy() && getLastBuyPrice() > 0
                && (((kline.getClose() - getLastBuyPrice())/getLastBuyPrice()) > getStrategyParam().getSellRate());
    }


}
