package bt.yunbi.market.bean;


import bt.yunbi.common.DoubleUtils;

import java.util.Date;

/**
 * Created by lichang on 14-2-24.
 */
public class Kline {
    private Date datetime;
    private Market market;

    private Long timestamp;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double volume;
//    private Symbol symbol = Symbol.btc;
    private Double vwap;

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

//    public void setSymbol(Symbol symbol) {
//        this.symbol = symbol;
//    }
//    public Symbol getSymbol(){
//        return this.symbol;
//    }

    public Double getVwap() {
        return vwap;
    }

    public void setVwap(Double vwap) {
        this.vwap = vwap;
    }

    @Override
    public String toString() {
        return //DoubleUtils.toFourDecimal(vwap) +
//                " " + open +
                " " + DoubleUtils.toFourDecimal(high - low) +
                " " + DoubleUtils.toFourDecimal(open - close) +
//                " " + close +
                " " + volume;
    }
}
