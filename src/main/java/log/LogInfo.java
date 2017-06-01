package log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by handong on 17/5/31.
 */
public class LogInfo {
    private String date;

    private Double ticker;

    private Double count;

    private List<LogInfo> sellList = new ArrayList<>();

    private List<LogInfo> buyList = new ArrayList<>();

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getTicker() {
        return ticker;
    }

    public void setTicker(Double ticker) {
        this.ticker = ticker;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public List<LogInfo> getSellList() {
        return sellList;
    }

    public void setSellList(List<LogInfo> sellList) {
        this.sellList = sellList;
    }

    public List<LogInfo> getBuyList() {
        return buyList;
    }

    public void setBuyList(List<LogInfo> buyList) {
        this.buyList = buyList;
    }
}
