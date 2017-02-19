package org.bitcoin.client.strategy.impl;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import org.bitcoin.client.strategy.TendencyResult;
import org.bitcoin.client.strategy.TendencyStrategyParam;
import org.bitcoin.common.DoubleUtils;
import org.bitcoin.market.bean.Kline;
import org.bitcoin.market.utils.DateUtil;

import java.io.IOException;

/**
 * Created by handong on 17/1/22.
 */
public class TendencyGuessFeeOne extends TendencyStrategy {

    private int during =0;
    public TendencyGuessFeeOne(TendencyStrategyParam param, boolean hasLog) throws IOException {
        super(param, hasLog);

    }

    @Override
    public void guess(TendencyResult result, Kline kline) {
        int res = 0;
        int currentTendency = result.getCurrentTendency();
        during ++;
        if(isHasLog()) log(currentTendency+"");
        if (currentTendency == getStrategyParam().getDownTimeForBuy()
                && isCanBuy()
                ) {

            double buyPrice = kline.getOpen();//开具买

            if (0 == getMoney()) {
                setMoney(getCost());
            }
            setAmount(getMoney()/(buyPrice * 1.001));

            setLastBuyPrice(buyPrice * 1.001);
            setMoney(getMoney()/1.001);
            if (isHasLog()) {
                log(DateUtil.format(kline.getDatetime()) + " buy "+getStrategyParam().getDownTimeForBuy()+"=" + getAmount() +" * " + (buyPrice * 1.001)
                + getCost() +"=" +getMoney());
            }
            setCanBuy(false);
            res = 1;
            during = 0;
        }


        //盈利
        if (upMoney(currentTendency, kline) ||
                ((currentTendency < 0 && during > 3) && currentTendency == getStrategyParam().getUpTimeForSell() && !isCanBuy())) {
//            setMoney(getMoney() + kline.getClose() * 0.999);
            double sellPrice = getAmount() * kline.getClose() * 0.999;
            setMoney(sellPrice);
            if (isHasLog()) {
                log(DateUtil.format(kline.getDatetime()) + " sell: "+ getStrategyParam().getUpTimeForSell()
                        + getAmount() + "=" + kline.getClose() * 0.999 +" total = " + getMoney());
                log("rate = " + DoubleUtils.toFourDecimal((getMoney() - getCost()) / getCost()) + " get=" + (getMoney() - getCost()));

                log("\n");
            }
            setCanBuy(true);
            res = -1;
        }

        setResult(res);

    }


    private boolean upMoney(double currentTendency, Kline kline) {

        return currentTendency == getStrategyParam().getUpTimeForSell() && !isCanBuy() && getLastBuyPrice() > 0
                && ((Math.abs(kline.getClose() - getLastBuyPrice())/getLastBuyPrice()) > getStrategyParam().getSellRate());
    }


}
