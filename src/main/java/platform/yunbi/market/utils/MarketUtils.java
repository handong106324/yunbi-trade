package platform.yunbi.market.utils;

import platform.yunbi.market.bean.Kline;

/**
 * Created by classic1999 on 14-3-27.
 */
public class MarketUtils {
    public static double avgPrice(Kline kline) {
        return (kline.getOpen() + kline.getClose() + kline.getHigh() + kline.getLow()) / 4;

    }
}
