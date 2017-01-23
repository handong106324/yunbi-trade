package org.bitcoin.client.strategy;

/**
 * Created by handong on 17/1/20.
 */
public class TendencyResult {
    private int changeTimes;

    private int currentTendency;

    private int upTimes;

    private int downTimes;
    private int twoUpTimes;
    private int threeUpTimes;
    private int oneUpTimes;
    private int threeDownTime;
    private int oneDownTimes;
    private int twoDownTime;
    private int fourUpTimes;
    private int fourDownTime;
    private int fiveUpTimes;
    private int fiveDownTime;
    private int sixUpTimes;
    private int sixDownTime;
    private int sevenUpTimes;
    private int sevenDownTime;
    private int eightUpTimes;
    private int eightDownTime;
    private int nineUpTimes;
    private int nineDownTime;
    private int tenUpTimes;
    private int tenDownTime;

    public int getFiveUpTimes() {
        return fiveUpTimes;
    }

    public void setFiveUpTimes(int fiveUpTimes) {
        this.fiveUpTimes = fiveUpTimes;
    }

    public int getFiveDownTime() {
        return fiveDownTime;
    }

    public void setFiveDownTime(int fiveDownTime) {
        this.fiveDownTime = fiveDownTime;
    }

    public int getSixUpTimes() {
        return sixUpTimes;
    }

    public void setSixUpTimes(int sixUpTimes) {
        this.sixUpTimes = sixUpTimes;
    }

    public int getSixDownTime() {
        return sixDownTime;
    }

    public void setSixDownTime(int sixDownTime) {
        this.sixDownTime = sixDownTime;
    }

    public int getSevenUpTimes() {
        return sevenUpTimes;
    }

    public void setSevenUpTimes(int sevenUpTimes) {
        this.sevenUpTimes = sevenUpTimes;
    }

    public int getSevenDownTime() {
        return sevenDownTime;
    }

    public void setSevenDownTime(int sevenDownTime) {
        this.sevenDownTime = sevenDownTime;
    }

    public int getEightUpTimes() {
        return eightUpTimes;
    }

    public void setEightUpTimes(int eightUpTimes) {
        this.eightUpTimes = eightUpTimes;
    }

    public int getEightDownTime() {
        return eightDownTime;
    }

    public void setEightDownTime(int eightDownTime) {
        this.eightDownTime = eightDownTime;
    }

    public int getNineUpTimes() {
        return nineUpTimes;
    }

    public void setNineUpTimes(int nineUpTimes) {
        this.nineUpTimes = nineUpTimes;
    }

    public int getNineDownTime() {
        return nineDownTime;
    }

    public void setNineDownTime(int nineDownTime) {
        this.nineDownTime = nineDownTime;
    }

    public int getTenUpTimes() {
        return tenUpTimes;
    }

    public void setTenUpTimes(int tenUpTimes) {
        this.tenUpTimes = tenUpTimes;
    }

    public int getTenDownTime() {
        return tenDownTime;
    }

    public void setTenDownTime(int tenDownTime) {
        this.tenDownTime = tenDownTime;
    }

    public void compute(int tendecny, double vwap) {
        changeTimes++;
        if (tendecny == 0) {
            return;
        } else if (tendecny < 0) {//down

            downTimes++;
            //下降
            if (currentTendency > 0) {
                if (2 == currentTendency) {
                    twoUpTimes++;
                }

                if (3 <= currentTendency) {
                    threeUpTimes++;
                }

                if (1 == currentTendency) {
                    oneUpTimes++;
                }
                if (4 == currentTendency) {
                    fourUpTimes++;
                }
                if (5 == currentTendency) {
                    fiveUpTimes++;
                }
                if (6 == currentTendency) {
                    sixUpTimes++;
                }
                if (7 == currentTendency) {
                    sevenUpTimes++;
                }
                if (8 == currentTendency) {
                    eightUpTimes++;
                }
                if (9 == currentTendency) {
                    nineUpTimes++;
                }
                if (10 == currentTendency) {
                    tenUpTimes++;
                }

                currentTendency = 0;
            }
            currentTendency--;

        } else {

            upTimes++;
            if (currentTendency < 0) {
                if (-1 == currentTendency) {
                    oneDownTimes++;
                }
                if (-2 == currentTendency) {
                    twoDownTime++;
                }
                if (-3 >= currentTendency) {
                    threeDownTime++;
                }

                if (-4 == currentTendency) {
                    fourDownTime++;
                }
                if (-5 == currentTendency) {
                    fiveDownTime++;
                }
                if (-6 == currentTendency) {
                    sixDownTime++;
                }
                if (-7 == currentTendency) {
                    sevenDownTime++;
                }
                if (-8 == currentTendency) {
                    eightDownTime++;
                }
                if (-9 == currentTendency) {
                    nineDownTime++;
                }
                if (-10 == currentTendency) {
                    tenDownTime++;
                }
                currentTendency = 0;

            }

            currentTendency++;
        }

    }

    public int getCurrentTendency() {
        return currentTendency;
    }

    public void setCurrentTendency(int currentTendency) {
        this.currentTendency = currentTendency;
    }

    public int getUpTimes() {
        return upTimes;
    }

    public void setUpTimes(int upTimes) {
        this.upTimes = upTimes;
    }

    public int getDownTimes() {
        return downTimes;
    }

    public void setDownTimes(int downTimes) {
        this.downTimes = downTimes;
    }

    public int getTwoUpTimes() {
        return twoUpTimes;
    }

    public void setTwoUpTimes(int twoUpTimes) {
        this.twoUpTimes = twoUpTimes;
    }

    public int getThreeUpTimes() {
        return threeUpTimes;
    }

    public void setThreeUpTimes(int threeUpTimes) {
        this.threeUpTimes = threeUpTimes;
    }

    public int getOneUpTimes() {
        return oneUpTimes;
    }

    public void setOneUpTimes(int oneUpTimes) {
        this.oneUpTimes = oneUpTimes;
    }

    public int getThreeDownTime() {
        return threeDownTime;
    }

    public void setThreeDownTime(int threeDownTime) {
        this.threeDownTime = threeDownTime;
    }

    public int getOneDownTimes() {
        return oneDownTimes;
    }

    public void setOneDownTimes(int oneDownTimes) {
        this.oneDownTimes = oneDownTimes;
    }

    public int getTwoDownTime() {
        return twoDownTime;
    }

    public void setTwoDownTime(int twoDownTime) {
        this.twoDownTime = twoDownTime;
    }

    public int getChangeTimes() {
        return changeTimes;
    }

    public void setChangeTimes(int changeTimes) {
        this.changeTimes = changeTimes;
    }

    public int getFourUpTimes() {
        return fourUpTimes;
    }

    public void setFourUpTimes(int fourUpTimes) {
        this.fourUpTimes = fourUpTimes;
    }

    public int getFourDownTime() {
        return fourDownTime;
    }

    public void setFourDownTime(int fourDownTime) {
        this.fourDownTime = fourDownTime;
    }
}
