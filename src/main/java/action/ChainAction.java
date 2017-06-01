package action;

/**
 * Created by handong on 17/5/26.
 */
public enum  ChainAction {
    BAN_ZHUAN("transfer","搬砖"),
    DUAN_XIAN_FIVE_MIN("SHORT-FIVE-MIN", "五分钟短线"),
    DUAN_XIAN_FIFTEE_MIN("SHORT-FIFTEEN-MIN", "十五分钟五分钟短线"),
    DUAN_XIAN_THRITY_MIN("SHORT-THRITY-MIN", "三十分钟五分钟短线"),
    ;

    private String name;

    private String label;

    ChainAction(String name, String label) {
        this.name = name;
        this.label = label;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
