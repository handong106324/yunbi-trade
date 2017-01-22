package org.bitcoin.client;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.bitcoin.common.DoubleUtils;
import org.bitcoin.market.AbstractMarketApi;
import org.bitcoin.market.MarketApiFactory;
import org.bitcoin.market.bean.Market;
import org.bitcoin.market.bean.Symbol;
import org.bitcoin.market.bean.SymbolPair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lijing on 17/1/14.
 */
public class TestCollectionBTCInfo {

    public static void main(String[] args) {

        try {
            FileWriter fileWriter = new FileWriter(new File("bc.text"));
            TestCollectionBTCInfo btcInfo = new TestCollectionBTCInfo();
            btcInfo.setFileWriter(fileWriter);
            int time = 0;
            while (true) {
                Thread.sleep(5000);
                btcInfo.collect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    FileWriter fileWriter;
    boolean downRateBidFlag = false;

    double total = 1;
    private double buyPrice = 0;

    private double askPriceStart = 0;

    AbstractMarketApi abstractMarketApi = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);


    public void collect() throws IOException {

        double ticker = abstractMarketApi.ticker(new SymbolPair(Symbol.btc, Symbol.cny));

        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        JSONObject depth = market.get_depth(new SymbolPair(Symbol.btc, Symbol.cny), true);
        JSONArray asks = depth.getJSONArray("asks");
        JSONArray bids = depth.getJSONArray("bids");


        double ask = asks.getJSONObject(0).getDouble("price");
        double bid = bids.getJSONObject(0).getDouble("price");
        String res = "";
        if (askPriceStart == 0) {
            askPriceStart = ask;
        }

        if (buyPrice == 0) {// ready to buy
            double tRate = (ask - askPriceStart)/askPriceStart;

            if (isUp(ask, askPriceStart) && Math.abs(tRate) > 0.01) {
                writeFile("buy:" + tRate +";money=" + bid );
                total *= (1 + tRate);
                buyPrice = ask;
                System.out.println("-----buy----" + buyPrice);
            }
            res += (isUp(ask, askPriceStart)?"up":"down")+" - rate:" + (tRate > 0.001 ?DoubleUtils.toFourDecimal(tRate):0);

        } else if (buyPrice > 0) {//已经持有:上升0.013 并且有跌0.002的情况卖
            double tRate = (bid - buyPrice) / buyPrice;
            if(0.002 < Math.abs(tRate)) {
                downRateBidFlag = true;
            }
            if ((isUp(bid, buyPrice) &&  Math.abs(tRate) > 0.01 && downRateBidFlag) ||
                    (!isUp(bid, buyPrice) && Math.abs(tRate) > 0.004)){
                writeFile("sell:" + tRate +";money=" + bid );
                downRateBidFlag = false;
                total *= (1 + tRate);
                buyPrice = 0;
                System.out.println(total);
                res += "up:" + isUp(bid, buyPrice) +" rate=" + tRate +" - sell";

            } else {
                res += "up:" + isUp(bid, buyPrice) +" rate" + tRate;
            }

        }

        System.out.println("res:" + res);
        writeFile(ticker, ask, bid);

    }

    private boolean isUp(double currentPrice, double lastPrice) {
        return currentPrice > lastPrice;
    }

    private void writeFile(double ticker, double ask, double bid) throws IOException {
        fileWriter.write(simpleDateFormat.format(new Date()) + "   " + ticker + "   " + ask + "    " + bid);
        fileWriter.write("\n");
        fileWriter.flush();
    }

    private void writeFile(String ticker) throws IOException {
        fileWriter.write(ticker + "\n");
        fileWriter.flush();

    }

    public void setFileWriter(FileWriter fileWriter) {
        this.fileWriter = fileWriter;
    }
}
