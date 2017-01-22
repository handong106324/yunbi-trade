package org.bitcoin.market.bean;

/**
 * Created by lichang on 14-2-26.
 */

public enum Symbol {
    btc {
        @Override
        public boolean isBtc() {
            return true;
        }

        @Override
        public boolean isLtc() {
            return false;
        }

        @Override
        public boolean isEtc() {
            return false;
        }

        @Override
        public boolean isUsd() {
            return false;
        }
        @Override
        public boolean isSc() {
            return true;
        }

        public boolean isZmc() {
            return false;
        }

        @Override
        public boolean isCny() {
            return false;
        }
    },zmc {
        @Override
        public boolean isBtc() {
            return false;
        }

        @Override
        public boolean isZmc() {
            return true;
        }
        @Override
        public boolean isLtc() {
            return false;
        }

        @Override
        public boolean isEtc() {
            return false;
        }

        @Override
        public boolean isUsd() {
            return false;
        }

        @Override
        public boolean isSc() {
            return true;
        }
        @Override
        public boolean isCny() {
            return false;
        }
    },sc {
        @Override
        public boolean isBtc() {
            return true;
        }

        @Override
        public boolean isSc() {
            return true;
        }

        public boolean isZmc() {
            return false;
        }

        @Override
        public boolean isLtc() {
            return false;
        }

        @Override
        public boolean isEtc() {
            return false;
        }

        @Override
        public boolean isUsd() {
            return false;
        }

        @Override
        public boolean isCny() {
            return false;
        }
    }, ltc {
        @Override
        public boolean isBtc() {
            return false;
        }

        @Override
        public boolean isLtc() {
            return true;
        }

        @Override
        public boolean isEtc() {
            return false;
        }

        @Override
        public boolean isUsd() {
            return false;
        }
        @Override
        public boolean isSc() {
            return true;
        }

        public boolean isZmc() {
            return false;
        }

        @Override
        public boolean isCny() {
            return false;
        }
    }, usd {
        @Override
        public boolean isBtc() {
            return false;
        }
        @Override
        public boolean isSc() {
            return true;
        }

        public boolean isZmc() {
            return false;
        }

        @Override
        public boolean isLtc() {
            return false;
        }

        @Override
        public boolean isEtc() {
            return false;
        }

        @Override
        public boolean isUsd() {
            return true;
        }

        @Override
        public boolean isCny() {
            return false;
        }
    }, cny {
        @Override
        public boolean isBtc() {
            return false;
        }
        @Override
        public boolean isSc() {
            return false;
        }

        public boolean isZmc() {
            return false;
        }

        @Override
        public boolean isLtc() {
            return false;
        }

        @Override
        public boolean isEtc() {
            return false;
        }

        @Override
        public boolean isUsd() {
            return false;
        }

        @Override
        public boolean isCny() {
            return true;
        }
    }, etc {
        @Override
        public boolean isBtc() {
            return false;
        }
        @Override
        public boolean isSc() {
            return false;
        }

        public boolean isZmc() {
            return false;
        }

        @Override
        public boolean isLtc() {
            return false;
        }

        @Override
        public boolean isEtc() {
            return true;
        }

        @Override
        public boolean isUsd() {
            return false;
        }

        @Override
        public boolean isCny() {
            return false;
        }
    };

    public abstract boolean isBtc();
    public abstract boolean isSc();

    public abstract boolean isZmc();

    public abstract boolean isLtc();
    public abstract boolean isEtc();

    public abstract boolean isUsd();

    public abstract boolean isCny();
}

