package platform.yunbi.market.bean;

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
    },OKCOIN {
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

    public static Market get(String key) {
        for (Market market : Market.values()) {
            if (key.startsWith(market.name())) {
                return market;
            }
        }
        return null;
    }
}

