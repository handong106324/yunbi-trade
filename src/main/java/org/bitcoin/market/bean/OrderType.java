package org.bitcoin.market.bean;

/**
 * Created by classic1999 on 14-4-17.
 */
public enum OrderType {
    Market {
        @Override
        public boolean isMargin() {
            return false;
        }

        @Override
        public boolean isMarket() {
            return true;
        }

        @Override
        public boolean isLimit() {
            return false;
        }
    }, Limit {
        @Override
        public boolean isMargin() {
            return false;
        }

        @Override
        public boolean isMarket() {
            return false;
        }

        @Override
        public boolean isLimit() {
            return true;
        }
    }, Stop {
        @Override
        public boolean isMargin() {
            return false;
        }

        @Override
        public boolean isMarket() {
            return false;
        }

        @Override
        public boolean isLimit() {
            return false;
        }
    }, TrailingStop {
        @Override
        public boolean isMargin() {
            return false;
        }

        @Override
        public boolean isMarket() {
            return false;
        }

        @Override
        public boolean isLimit() {
            return false;
        }
    }, MarginLimit {
        @Override
        public boolean isMargin() {
            return true;
        }

        @Override
        public boolean isMarket() {
            return false;
        }

        @Override
        public boolean isLimit() {
            return true;
        }
    }, MarginMarket {
        @Override
        public boolean isMargin() {
            return true;
        }

        @Override
        public boolean isMarket() {
            return true;
        }

        @Override
        public boolean isLimit() {
            return false;
        }
    };

    public abstract boolean isMargin();
    public abstract boolean isMarket();
    public abstract boolean isLimit();

}
