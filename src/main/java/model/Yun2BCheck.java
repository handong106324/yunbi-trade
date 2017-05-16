package model;

/**
 * Created by handong on 17/5/15.
 * 云币到bittrex的策略
 * 计算 eth 价格
 */
public class Yun2BCheck {
    public static double check(double yunEth,double yunTarPrice,
                               double bEthTarRate,double btcTarRate,
                                boolean byBtc,double ethBtcRate) {
        double preCount = 0.999;
        double count = bEthTarRate * preCount;
        if (byBtc) {
            count = preCount * ethBtcRate * btcTarRate;
        }
        double yunCount = yunEth/yunTarPrice;
        return (count - yunCount)/ yunCount;
    }
}
