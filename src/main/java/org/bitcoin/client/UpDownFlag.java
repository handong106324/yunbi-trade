package org.bitcoin.client;

import org.bitcoin.common.DoubleUtils;

/**
 * Created by handong on 17/1/18.
 */
public class UpDownFlag {
    private int feeDown;
    private int feeUp;
    private boolean continueDown = false;
    private boolean continueUp  = false;
    private boolean isUp = false;
    public void check(double ticker, double lastTicker) {
        if (ticker < lastTicker) {
            feeDown ++;
            continueDown = feeDown > 5;
            feeUp =0;
        } else if(ticker > lastTicker) {
            feeUp ++;
            feeDown =0;
            continueUp = feeUp > 5;
        }
        isUp = lastTicker < ticker;

    }

    public int getFeeDown() {
        return feeDown;
    }

    public void setFeeDown(int feeDown) {
        this.feeDown = feeDown;
    }

    public int getFeeUp() {
        return feeUp;
    }

    public void setFeeUp(int feeUp) {
        this.feeUp = feeUp;
    }

    public boolean isContinueDown() {
        return continueDown;
    }

    public void setContinueDown(boolean continueDown) {
        this.continueDown = continueDown;
    }

    public boolean isContinueUp() {
        return continueUp;
    }

    public void setContinueUp(boolean continueUp) {
        this.continueUp = continueUp;
    }

    public boolean isUp() {
        return isUp;
    }

    public void setUp(boolean up) {
        isUp = up;
    }

}
