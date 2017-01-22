package org.bitcoin.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class FiatConverter {
    private static final Logger LOG = LoggerFactory.getLogger(FiatConverter.class);
    private final static double USD2CNY = 6.2;

    public static double toUsd(Double cny) {
        return toUsd(cny, new Date());
    }

    public static double toUsd(Double cny, Date date) {
        if (cny == null || cny == 0.0) {
            return 0.0;
        }
        double usd = cny / getRate(date);
        return DoubleUtils.toFourDecimal(usd);
    }

    private static Double getRate(Date date) {
        return USD2CNY;
    }


    public static Double toCNY(Double usd) {
        if (usd == null || usd == 0.0) {
            return 0.0;
        }
        double cny = usd * getRate(new Date());
        return DoubleUtils.toFourDecimal(cny);
    }
}
