package model;

/**
 * Created by handong on 17/5/27.
 */
public class ZhuanCoin {
    private String basic;
    private String target;

    private double rate;


    public String getBasic() {
        return basic;
    }

    public void setBasic(String basic) {
        this.basic = basic.toLowerCase();
    }
}
