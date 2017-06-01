package platform.yunbi;

import platform.yunbi.market.bean.Kline;

import java.util.List;

/**
 * Created by handong on 17/5/31.
 */
public class GuessTemplate {

    public void statics(List<Kline> history) {
        int canSellCount = 0;
        int canBuyCount = 0;
        int total = 0;
        for (Kline kline :history) {
            double rate = kline.getClose() - kline.getOpen();
            if (Math.abs(rate) > 0.02) {
                if (rate > 0) {
                    canBuyCount ++;
                } else {
                    canSellCount ++;
                }
            }
            total ++;
        }

        System.out.println("up=" + canBuyCount +" down = " + canSellCount +" total=" +total);
    }
}
