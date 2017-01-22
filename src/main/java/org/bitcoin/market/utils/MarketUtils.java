package org.bitcoin.market.utils;

import org.bitcoin.market.bean.Kline;

/**
 * Created by classic1999 on 14-3-27.
 */
public class MarketUtils {
    public static double avgPrice(Kline kline) {
        return (kline.getOpen() + kline.getClose() + kline.getHigh() + kline.getLow()) / 4;

    }
}
