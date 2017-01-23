package org.bitcoin.client.strategy;

import org.bitcoin.market.bean.Symbol;

/**
 * Created by handong on 17/1/16.
 */
public class StrategyParam {

    private Symbol symbol = Symbol.btc;
    private double buyRate;
    private double sellRate;
    private int downTimeForBuy;
    private int timeForSell;
    private int upTime;
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

    public int getTimeForSell() {
        return timeForSell;
    }

    public void setTimeForSell(int timeForSell) {
        this.timeForSell = timeForSell;
    }

    public int getUpTime() {
        return upTime;
    }

    public void setUpTime(int upTime) {
        this.upTime = upTime;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }
}
