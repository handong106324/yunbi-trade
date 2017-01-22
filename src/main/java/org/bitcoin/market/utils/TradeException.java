package org.bitcoin.market.utils;

/**
 * Created by classic1999 on 14-3-30.
 */

public class TradeException extends RuntimeException {
    private MarketErrorCode code;

    public TradeException(MarketErrorCode code) {
        super("error code:" + code);
        this.code = code;
    }

    public TradeException(String message, MarketErrorCode code) {
        super(message);
        this.code = code;
    }

    public TradeException(String message, Throwable cause, MarketErrorCode code) {
        super(message, cause);
        this.code = code;
    }

    public TradeException(Throwable cause, MarketErrorCode code) {
        super(cause);
        this.code = code;
    }

    public MarketErrorCode getCode() {
        return code;
    }

    public void setCode(MarketErrorCode code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "TradeException{" +
                "code=" + code +
                '}';
    }
}
