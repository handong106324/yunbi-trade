package streatgy;

import action.Statistics;
import action.Tendency;
import platform.yunbi.market.bean.Kline;

import java.util.List;

/**
 * 五分钟断线策略
 * 判断最近一日内总趋势double
 * 判断最近12小时内总趋势
 * 判断最近6小时内总趋势
 * 判断最近2小时内总趋势
 * 判断最近1小时内总趋势
 * 判断30分钟内总趋势
 * Created by handong on 17/5/26.
 */
public class FiveMinShortStreatgyStatics {
    private Tendency oneDayTendency;

    private Tendency halfDayTendency;

    private Tendency sixHourTendency;

    private Tendency twoHourTendency;

    private Tendency oneHourTendency;

    private Tendency halfHourTendency;

    public Tendency getOneDayTendency() {
        return oneDayTendency;
    }

    public void setOneDayTendency(Tendency oneDayTendency) {
        this.oneDayTendency = oneDayTendency;
    }

    public Tendency getHalfDayTendency() {
        return halfDayTendency;
    }

    public void setHalfDayTendency(Tendency halfDayTendency) {
        this.halfDayTendency = halfDayTendency;
    }

    public Tendency getSixHourTendency() {
        return sixHourTendency;
    }

    public void setSixHourTendency(Tendency sixHourTendency) {
        this.sixHourTendency = sixHourTendency;
    }

    public Tendency getTwoHourTendency() {
        return twoHourTendency;
    }

    public void setTwoHourTendency(Tendency twoHourTendency) {
        this.twoHourTendency = twoHourTendency;
    }

    public Tendency getOneHourTendency() {
        return oneHourTendency;
    }

    public void setOneHourTendency(Tendency oneHourTendency) {
        this.oneHourTendency = oneHourTendency;
    }

    public Tendency getHalfHourTendency() {
        return halfHourTendency;
    }

    public void setHalfHourTendency(Tendency halfHourTendency) {
        this.halfHourTendency = halfHourTendency;
    }

    public void compute(List<Kline> oneDayKlines, Kline oneDayKline) {
        this.oneDayTendency = new Tendency();
        oneDayTendency.compute(oneDayKlines, oneDayKline);


    }
}
