package platform.yunbi.client.strategy;

import platform.yunbi.market.bean.Symbol;

/**
 * Created by handong on 17/1/16.
 */
public class StrategyParam {

    private Symbol symbol = Symbol.gnt;

    private double buyRate;//购买比例,超过多少比例可以买。与卖出价比,或者

    private double sellRate;//卖出比例, 与买价比

    private int downTimeForBuy;//连续降价次数,可以买

    private int upTimeForSell;//连续升价可以卖

    private int cost;

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public double getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(double buyRate) {
        this.buyRate = buyRate;
    }

    public double getSellRate() {
        return sellRate;
    }

    public void setSellRate(double sellRate) {
        this.sellRate = sellRate;
    }

    public int getDownTimeForBuy() {
        return downTimeForBuy;
    }

    public void setDownTimeForBuy(int downTimeForBuy) {
        this.downTimeForBuy = downTimeForBuy;
    }

    public int getUpTimeForSell() {
        return upTimeForSell;
    }

    public void setUpTimeForSell(int upTimeForSell) {
        this.upTimeForSell = upTimeForSell;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }
}
