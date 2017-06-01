package action;

import platform.yunbi.market.bean.Kline;

import java.util.List;

/**
 * 趋势判断
 *  总趋势
 *
 * Created by handong on 17/5/26.
 */
public class Tendency {
    /**
     * 总趋势,判断当前买或者卖的可能性
     * -1 : 跌
     * 0  : 横盘
     * 1  : 涨
     * @return
     */
    private int tendency;

    private int nextTendency;

    /**
     * 幅度 百分比
     * @return
     */
    private double rate;

    /**
     * 总量
     * @return
     */
    private double volume;

    private Statistics statistics;

    public int getTendency() {
        return tendency;
    }

    public void setTendency(int tendency) {
        this.tendency = tendency;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public void compute(List<Kline> klines, Kline totalKline) {

        if (totalKline.getOpen() > totalKline.getClose()) {
            this.tendency = 1;
        } else if (totalKline.getOpen() == totalKline.getClose()) {
            this.tendency = 0;
        } else {
            this.tendency = -1;
        }

        this.rate = (totalKline.getClose() - totalKline.getOpen())/totalKline.getOpen();
        this.volume = totalKline.getVolume();

        computeInner(klines);
    }

    private void computeInner(List<Kline> klines) {
        if (statistics == null) {
            statistics = new Statistics();
        }

        statistics.statis(klines);
    }

    public int getNextTendency() {
        return nextTendency;
    }

    public void setNextTendency(int nextTendency) {
        this.nextTendency = nextTendency;
    }
}
