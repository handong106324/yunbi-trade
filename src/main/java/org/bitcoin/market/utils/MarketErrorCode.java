package org.bitcoin.market.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by classic1999 on 14-3-30.
 */
public enum MarketErrorCode {
    manyTimes {
        @Override
        public String getMsg() {
            return "请求次数过多";
        }

        @Override
        public boolean isRepeat() {
            return true;
        }
    }, invalidTime {
        @Override
        public String getMsg() {
            return "无效的提交时间";
        }

        @Override
        public boolean isRepeat() {
            return true;
        }
    }, buyHigherCurrent {
        @Override
        public String getMsg() {
            return "买入价格不能高于现价";
        }

        @Override
        public boolean isRepeat() {
            return false;
        }
    }, sellLowerCurrent {
        @Override
        public String getMsg() {
            return "卖出价格不能低于现价";
        }

        @Override
        public boolean isRepeat() {
            return false;
        }
    }, notEnoughBtc {
        @Override
        public String getMsg() {
            return "没有足够的比特币";
        }

        @Override
        public boolean isRepeat() {
            return false;
        }
    }, notEnoughCash {
        @Override
        public String getMsg() {
            return "没有足够的现金";
        }

        @Override
        public boolean isRepeat() {
            return false;
        }
    };

    public abstract String getMsg();

    public abstract boolean isRepeat();


    public static MarketErrorCode getForPeatioCNY(JSONObject response) {
        JSONObject error = response.getJSONObject("error");
        int code = error.getInteger("code");
        String msg = error.getString("message");
        switch (code) {
            case 10001:
                return manyTimes;
            case 2002:
                return notEnoughBtc;
            default:
                throw new RuntimeException("code:" + code + " message:" + msg);
        }

    }


}
