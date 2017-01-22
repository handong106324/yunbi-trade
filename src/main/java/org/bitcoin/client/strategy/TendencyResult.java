package org.bitcoin.client.strategy;

/**
 * Created by handong on 17/1/20.
 */
public class TendencyResult {

    boolean buyStatus = true;
    private int result;

    double get;

    private int changeTimes;

    private double money = 10000;

    private int jugeOk;
    private int jugeFail;

    private int currentTendency;

    private int upTimes;

    private int downTimes;
    private int twoUpTimes;
    private int threeUpTimes;
    private int oneUpTimes;
    private int threeDownTime;
    private int oneDownTimes;
    private int twoDownTime;
    public void compute(int tendecny, double vwap) {
        changeTimes ++;
//        log(tendecny +" " + currentTendency);
        if (tendecny ==0) {
//            if (result == 0) {
//                jugeOk ++;
//            } else {
//                jugeFail ++;
//            }
            return;
        } else if (tendecny < 0) {//down

            downTimes ++;
            //下降
            if (currentTendency >0) {
                if (2 == currentTendency) {
                    twoUpTimes ++;
                }

                if (3 <= currentTendency) {
                    threeUpTimes ++;
                }

                if (1 == currentTendency) {
                    oneUpTimes ++;
                }
                if (result < 0) {
                    jugeOk ++;
                } else {
                    jugeFail ++;
                }
//                countMoney(vwap);
                currentTendency = 0;
            }
            currentTendency --;

        } else {

            upTimes ++;
            if (currentTendency < 0) {
                if (-1 == currentTendency) {
                    oneDownTimes ++;
                }
                if (-2 == currentTendency) {
                    twoDownTime ++;
                }
                if (-3 >= currentTendency) {
                    threeDownTime ++;
                }
                if (result > 0) {
                    jugeOk ++;
                } else {
                    jugeFail ++;
                }
//                countMoney(vwap);
                currentTendency = 0;

            }

            currentTendency ++;
        }

    }

    private void countMoney(double vwap) {

        if (result == 1) {
            money -= vwap;
//            log("买入: money + " + vwap);
            buyStatus = false;
        } else if(result == -1) {
            money += vwap;
//            log("卖出: money - " + vwap);
            buyStatus = true;
            get = (money - 10000);
        }
    }

    public void juge() {

        if (changeTimes < 10) {
            return;
        }
        if (currentTendency == 3) {//连续三次直接卖出
            log("三连升卖出");
            result = -1;
            return;
        }

        if (currentTendency == -3) {//三连跌买入
            log("三连跌买入");
            result = 1;
            return;
        }
//
//        if (currentTendency == 2) {//两连升并且连胜次数大于5,不做任何动作
//            boolean agaigUp = false;
//            int rate= countRate(threeUpTimes * 100, twoUpTimes + threeUpTimes);
//            if (rate > 60) {
//                agaigUp = true;
//                log("等:两连升,并且三连胜的概率:"+ rate+ " tow=" + twoUpTimes + " three=" + threeUpTimes);
//            } else {
//                log("卖:两连升,并且三连胜的概率:"+ rate+ " tow=" + twoUpTimes + " three=" + threeUpTimes);
//            }
//
//            result = agaigUp?0:-1;
//            return;
//        }
//        if (currentTendency == -2) {//两连降
//            boolean wait = false;
//            int rate = countRate(threeDownTime,twoDownTime + threeDownTime);
//            if (rate >= 60) {
//                wait = true;
//                log("等:两连跌,并且三连跌的概率:"+ rate+ " tow=" + twoDownTime + " three=" + threeDownTime);
//            } else {
//                log("买:两连跌,并且三连跌的概率:"+ rate+ " tow=" + twoDownTime + " three=" + threeDownTime);
//            }
//
//            result = wait?0:1;
//            return;
//        }
//
//        if (currentTendency == 1) {//升并且再次升的概率大于40,不做任何动作
//            boolean agaigUp = false;
//            int rate = countRate(twoUpTimes + threeUpTimes,totalUp());
//            if (rate >= 60) {
//                agaigUp = true;
//                log("等:升,并且二三连胜的概率:"+ rate + " tow=" + twoUpTimes + " three=" + threeUpTimes);
//            } else {
//                log("卖:升,并且二三连胜的概率:"+ rate + " tow=" + twoUpTimes + " three=" + threeUpTimes);
//            }
//
//            result = agaigUp?0:-1;
//            return;
//        }
//
//        if (currentTendency == -1) {
//            boolean wait = false;
//            int rate = countRate(twoDownTime + threeDownTime,totalDown());
//            if (rate > 60) {
//                wait = false;
//                log("等:降,并且二三连降的概率:"+ rate+ " tow=" + twoDownTime + " three=" + threeDownTime);
//            }else {
//                log("买:降,并且二三连降的概率:"+ rate+ " tow=" + twoDownTime + " three=" + threeDownTime);
//            }
//            result = wait?0:1;
//            return;
//        }
        result = 0;
    }

    private int countRate(int threeUpTimes, int total) {
        if (0 == total) return 0;
        return threeUpTimes * 100/total;
    }

    private int totalDown() {
        int total = oneDownTimes + twoDownTime + threeDownTime;
        return total <= 10? 1000:total;
    }
    private int totalUp() {
        int total = oneUpTimes + twoUpTimes + threeUpTimes;
        return total <= 10? 1000:total;

    }

    public void log(String str) {
        System.out.println(str);
    }


    public int getResult() {
//        log("预测:" + result +" 历史成功率:千分之" + jugeOk * 1000/(jugeFail + jugeOk) +
//        " down=" + downTimes +" up = " + upTimes +" get=" + get) ;
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }


    public int guress() {

        if (currentTendency == 2) {
            return -1;
        }

        if (currentTendency == -2) {
            return 1;
        }

        if ((currentTendency == -3)) {
            return 1;
        }

        if (currentTendency == 3) {
            return  -1;
        }
        return 0;
    }

    public boolean isBuyStatus() {
        return buyStatus;
    }

    public void setBuyStatus(boolean buyStatus) {
        this.buyStatus = buyStatus;
    }

    public double getGet() {
        return get;
    }

    public void setGet(double get) {
        this.get = get;
    }

    public int getChangeTimes() {
        return changeTimes;
    }

    public void setChangeTimes(int changeTimes) {
        this.changeTimes = changeTimes;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getJugeOk() {
        return jugeOk;
    }

    public void setJugeOk(int jugeOk) {
        this.jugeOk = jugeOk;
    }

    public int getJugeFail() {
        return jugeFail;
    }

    public void setJugeFail(int jugeFail) {
        this.jugeFail = jugeFail;
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
}
