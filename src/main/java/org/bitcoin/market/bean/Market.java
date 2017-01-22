package org.bitcoin.market.bean;

import java.util.TimeZone;

/**
 * Created by lichang on 14-2-26.
 */

public enum Market {

    PeatioCNY {
        @Override
        public boolean isUsd() {
            return false;
        }

        @Override
        public TimeZone getTimeZone() {
            return TimeZone.getTimeZone("GMT+8");
        }
    },ZMCCNY {
        @Override
        public boolean isUsd() {
            return false;
        }

        @Override
        public TimeZone getTimeZone() {
            return TimeZone.getTimeZone("GMT+8");
        }
    };

    public abstract boolean isUsd();

    public abstract TimeZone getTimeZone();

}

