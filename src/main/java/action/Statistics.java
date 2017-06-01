package action;

import platform.yunbi.market.bean.Kline;

import java.util.List;

/**
 * Created by handong on 17/5/26.
 */
public class Statistics {
    private int downTimes;

    private int upTimes;

    private int lastContinueDownTimes;

    private int lastContinueUpTimes;

    public int getDownTimes() {
        return downTimes;
    }

    public void setDownTimes(int downTimes) {
        this.downTimes = downTimes;
    }

    public int getUpTimes() {
        return upTimes;
    }

    public void setUpTimes(int upTimes) {
        this.upTimes = upTimes;
    }

    public int getLastContinueDownTimes() {
        return lastContinueDownTimes;
    }

    public void setLastContinueDownTimes(int lastContinueDownTimes) {
        this.lastContinueDownTimes = lastContinueDownTimes;
    }

    public int getLastContinueUpTimes() {
        return lastContinueUpTimes;
    }

    public void setLastContinueUpTimes(int lastContinueUpTimes) {
        this.lastContinueUpTimes = lastContinueUpTimes;
    }

    public void statis(List<Kline> klines) {
        Kline last = null;
        boolean continueUp = false;
        boolean continueDown = false;
        for (Kline kline : klines) {
            if (last == null) {
                last = kline;
                continue;
            }

            if (kline.getOpen() > kline.getClose()) {
                this.downTimes += 1;
                if (!continueDown){
                    continueDown = true;
                    this.lastContinueDownTimes = 1;
                }
                continueUp = false;
            } else if (kline.getOpen() < kline.getClose()) {
                this.upTimes += 1;
                continueDown = false;
                if(!continueUp){
                    continueUp = true;
                    this.lastContinueUpTimes = 1;
                }
            }
        }
    }
}
