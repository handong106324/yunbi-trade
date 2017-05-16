package bt.yunbi.market.bean;

/**
 * Created by lichang on 14-2-26.
 */

public enum Symbol {
//    btc {
//        @Override
//        public boolean isBtc() {
//            return true;
//        }
//
//        @Override
//        public boolean isAns() {
//            return false;
//        }
//
//        @Override
//        public boolean isBts() {
//            return false;
//        }
//
//        @Override
//        public boolean isEth() {
//            return false;
//        }
//        @Override
//        public boolean isSc() {
//            return true;
//        }
//
//        public boolean isGnt() {
//            return false;
//        }
//
//        @Override
//        public boolean isCny() {
//            return false;
//        }
//    },
    gnt {
        @Override
        public boolean isBtc() {
            return false;
        }

        @Override
        public boolean isGnt() {
            return true;
        }
        @Override
        public boolean isAns() {
            return false;
        }

        @Override
        public boolean isBts() {
            return false;
        }

        @Override
        public boolean isEth() {
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
    },
    sc {
        @Override
        public boolean isBtc() {
            return true;
        }

        @Override
        public boolean isSc() {
            return true;
        }

        public boolean isGnt() {
            return false;
        }

        @Override
        public boolean isAns() {
            return false;
        }

        @Override
        public boolean isBts() {
            return false;
        }

        @Override
        public boolean isEth() {
            return false;
        }

        @Override
        public boolean isCny() {
            return false;
        }
    },
    ans {
        @Override
        public boolean isBtc() {
            return false;
        }

        @Override
        public boolean isAns() {
            return true;
        }

        @Override
        public boolean isBts() {
            return false;
        }

        @Override
        public boolean isEth() {
            return false;
        }
        @Override
        public boolean isSc() {
            return true;
        }

        public boolean isGnt() {
            return false;
        }

        @Override
        public boolean isCny() {
            return false;
        }
    },
    eth {
        @Override
        public boolean isBtc() {
            return false;
        }
        @Override
        public boolean isSc() {
            return true;
        }

        public boolean isGnt() {
            return false;
        }

        @Override
        public boolean isAns() {
            return false;
        }

        @Override
        public boolean isBts() {
            return false;
        }

        @Override
        public boolean isEth() {
            return true;
        }

        @Override
        public boolean isCny() {
            return false;
        }
    },
    cny {
        @Override
        public boolean isBtc() {
            return false;
        }
        @Override
        public boolean isSc() {
            return false;
        }

        public boolean isGnt() {
            return false;
        }

        @Override
        public boolean isAns() {
            return false;
        }

        @Override
        public boolean isBts() {
            return false;
        }

        @Override
        public boolean isEth() {
            return false;
        }

        @Override
        public boolean isCny() {
            return true;
        }
    },
    bts {
        @Override
        public boolean isBtc() {
            return false;
        }
        @Override
        public boolean isSc() {
            return false;
        }

        public boolean isGnt() {
            return false;
        }

        @Override
        public boolean isAns() {
            return false;
        }

        @Override
        public boolean isBts() {
            return true;
        }

        @Override
        public boolean isEth() {
            return false;
        }

        @Override
        public boolean isCny() {
            return false;
        }
    },
    dgd {
        @Override
        public boolean isBtc() {
            return false;
        }
        @Override
        public boolean isSc() {
            return false;
        }

        public boolean isGnt() {
            return false;
        }

        @Override
        public boolean isAns() {
            return false;
        }

        @Override
        public boolean isBts() {
            return false;
        }
        public boolean isDgd() {
            return true;
        }

        @Override
        public boolean isEth() {
            return false;
        }

        @Override
        public boolean isCny() {
            return false;
        }
    },
    etc {
        @Override
        public boolean isBtc() {
            return false;
        }
        @Override
        public boolean isSc() {
            return false;
        }

        public boolean isGnt() {
            return false;
        }

        @Override
        public boolean isAns() {
            return false;
        }

        @Override
        public boolean isBts() {
            return false;
        }

        public boolean isEtc(){return true;}
        @Override
        public boolean isEth() {
            return false;
        }

        @Override
        public boolean isCny() {
            return false;
        }
    };

    public abstract boolean isBtc();
    public abstract boolean isSc();

    public abstract boolean isGnt();

    public abstract boolean isAns();
    public abstract boolean isBts();

    public abstract boolean isEth();

    public abstract boolean isCny();

    public static Symbol get(String code) {
        for (Symbol symbol : Symbol.values()) {
            if (code.startsWith(symbol.name())) {
                return symbol;
            }
        }
        return null;
    }
}

