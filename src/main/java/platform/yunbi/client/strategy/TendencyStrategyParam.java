package platform.yunbi.client.strategy;

/**
 * Created by handong on 17/1/16.
 */
public class TendencyStrategyParam extends StrategyParam {

    private int tendencyTime;
    private int limitCount;
    private int tendencyType;

    public int getTendencyTime() {
        return tendencyTime;
    }

    public void setTendencyTime(int tendencyTime) {
        this.tendencyTime = tendencyTime;
    }

    public int getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(int limitCount) {
        this.limitCount = limitCount;
    }

    public int getTendencyType() {
        return tendencyType;
    }

    public void setTendencyType(int tendencyType) {
        this.tendencyType = tendencyType;
    }
}
