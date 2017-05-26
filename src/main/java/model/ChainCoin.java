package model;

/**
 * Created by handong on 17/5/15.
 */
public class ChainCoin {
    private String key;
    private String last;
    private String askSell;
    private String bidBuy;
    private String plateForm;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getAskSell() {
        return askSell;
    }

    public void setAskSell(String askSell) {
        this.askSell = askSell;
    }

    public String getBidBuy() {
        return bidBuy;
    }

    public void setBidBuy(String bidBuy) {
        this.bidBuy = bidBuy;
    }

    @Override
    public String toString() {
        return "ChainCoin{" +
                "bidBuy='" + bidBuy + '\'' +
                ", askSell='" + askSell + '\'' +
                ", last='" + last + '\'' +
                ", key='" + key + '\'' +
                '}';
    }

    public String getPlateForm() {
        return plateForm;
    }

    public void setPlateForm(String plateForm) {
        this.plateForm = plateForm;
    }
}
