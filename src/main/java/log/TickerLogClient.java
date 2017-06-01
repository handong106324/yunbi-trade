package log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import platform.com.okcoin.rest.StringUtil;
import platform.yunbi.YunBiApi;
import platform.yunbi.client.strategy.StrategyParam;
import platform.yunbi.client.strategy.impl.EasyStrategy;
import platform.yunbi.market.AbstractMarketApi;
import platform.yunbi.market.MarketApiFactory;
import platform.yunbi.market.bean.Kline;
import platform.yunbi.market.bean.Market;
import platform.yunbi.market.bean.Symbol;
import platform.yunbi.market.bean.SymbolPair;
import test.YunBiAndBittresRunnable;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 记录云币网 币的每秒信息
 * 记录:   当前成交价格  、数量
 *        当前N档买卖价格与数量
 * Created by handong on 17/5/31.
 */
public class TickerLogClient {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        CompletionService<Integer> executor = new ExecutorCompletionService<Integer>(threadPool);

        executor.submit(new LogRecordRunnable(Symbol.ans), 1);
        executor.submit(new LogRecordRunnable(Symbol.sc), 1);
        executor.submit(new LogRecordRunnable(Symbol.bts), 1);
        executor.submit(new LogRecordRunnable(Symbol.gnt), 1);


    }



    private static void depth(AbstractMarketApi market, SymbolPair pair) {
        JSONObject depth = market.get_depth(pair, true);
        JSONArray asks = depth.getJSONArray("asks");
        JSONArray bids = depth.getJSONArray("bids");

        JSONObject ask = asks.getJSONObject(0);
        JSONObject bid = bids.getJSONObject(0);

        double askAmount = ask.getDouble("amount");
        double askPrice = ask.getDouble("price");
        double bidAmount = bid.getDouble("amount");
        double bidPrice = bid.getDouble("price");
    }

    @Test
    public void checkAns(){
        String ansStr = "6.723,6.72,6.8,6.884,6.883,6.72,6.883,6.885,6.723,6.725,6.724,6.726,6.725,6.8,6.73,6.8,6.73,6.731,6.736,6.75,6.845,6.8,6.844,6.8,6.844,6.845,6.841,6.842,6.841,6.84,6.8,6.84,6.76,6.84,6.8,6.77,6.734,6.833,6.735,6.8,6.734,6.736,6.777,6.736,6.737,6.77,6.738,6.739,6.734,6.77,6.734,6.733,6.734,6.733,6.732,6.731,6.73,6.724,6.723,6.72,6.71,6.705,6.704,6.703,6.702,6.7,6.689,6.7,6.689,6.7,6.689,6.72,6.701,6.719,6.72,6.719,6.702,6.7,6.718,6.8,6.75,6.79,6.82,6.819,6.818,6.819,6.8,6.79,6.819,6.82,6.819,6.81,6.833,6.812,6.761,6.762,6.765,6.83,6.833,6.8,6.833,6.813,6.829,6.828,6.829,6.83,6.834,6.839,6.801,6.8,6.839,6.84,6.81,6.84,6.81,6.84,6.81,6.845,6.811,6.812,6.813,6.8,6.801,6.84,6.802,6.801,6.802,6.801,6.802,6.801,6.802,6.801,6.84,6.801,6.8,6.768,6.799,6.8,6.798,6.768,6.798,6.797,6.798,6.8,6.771,6.77,6.766,6.765,6.761,6.722,6.7,6.765,6.669,6.667,6.66,6.653,6.65,6.624,6.62,6.617,6.651,6.65,6.651,6.8,6.798,6.797,6.79,6.798,6.8,6.81,6.848,6.849,6.87,7.0,7.04,7.0,7.04,7.049,7.04,7.049,7.0,6.9,7.086,7.1,7.05,7.0,7.06,7.09,7.0,7.048,7.0,7.01,7.0,6.96,7.0,6.95,6.91,6.95,6.91,6.999,7.0,7.038,7.039,6.804,7.0,6.86,7.0,6.86,7.0,6.86,7.0,6.9,6.91,7.0,6.912,6.999,7.0,7.03,7.0,7.03,7.0,6.92,7.0,7.02,7.0,6.917,7.0,6.917,6.918,6.92,6.918,6.917,7.0,6.917,6.93,6.999,7.0,6.95,7.0,7.02,7.03,7.038,7.035,7.039,7.04,7.039,7.028,7.0,6.96,6.94,6.919,6.92,6.953,6.957,6.955,6.965,6.959,6.957,6.999,7.0,6.99,7.0,6.955,6.954,6.956,7.0,6.96,6.958,6.949,6.916,6.902,6.903,6.91,6.906,6.905,6.946,6.906,6.946,7.0,6.999,6.99,6.911,6.913,7.0,7.038,7.001,7.04,7.001,7.1,7.14,7.001,7.1,7.148,7.099,7.15,7.17,7.15,7.149,7.1,7.01,7.1,7.147,7.1,7.01,7.1,7.06,7.1,7.145,7.1,7.062,7.1,7.062,7.09,7.1,7.099,7.1,7.18,7.15,7.062,7.019,7.062,7.018,7.167,7.15,7.148,7.149,7.14,7.019,7.02,7.019,7.137,7.02,7.019,7.109,7.099,7.03,7.099,7.1,7.021,7.022,7.021,7.022,7.021,7.02,7.018,7.01,7.001,7.0,6.925,7.0,6.92,6.93,6.907,6.922,6.924,6.927,6.999,6.928,6.93,6.94,6.998,6.93,6.999,7.0,6.96,6.961,7.0,6.981,6.961,7.0,7.022,7.0,7.01,7.0,7.022,7.021,7.08,7.1,7.021,7.1,7.099,7.1,7.107,7.101,7.108,7.109,7.108,7.109,7.11,7.109,7.102,7.109,7.11,7.136,7.137,7.14,7.12,7.15,7.155,7.15,7.168,7.2,7.23,7.21,7.249,7.25,7.26,7.27,7.249,7.25,7.288,7.25,7.28,7.288,7.289,7.288,7.29,7.298,7.3,7.333,7.301,7.333,7.35,7.4,7.41,7.45,7.5,7.48,7.5,7.48,7.45,7.48,7.499,7.498,7.45,7.401,7.48,7.499,7.44,7.45,7.403,7.402,7.4,7.402,7.4,7.333,7.3,7.333,7.242,7.237,7.238,7.28,7.398,7.28,7.39,7.398,7.399,7.39,7.399,7.39,7.29,7.285,7.284,7.282,7.285,7.284,7.24,7.284,7.239,7.01,7.239,7.1,7.282,7.281,7.275,7.2,7.201,7.2,7.27,7.2,7.25,7.2,7.25,7.161,7.22,7.24,7.18,7.24,7.18,7.238,7.185,7.186,7.187,7.185,7.2,7.188,7.189,7.188,7.2,7.189,7.2,7.188,7.186,7.189,7.188,7.199,7.188,7.199,7.181,7.188,7.181,7.188,7.181,7.188,7.187,7.182,7.18,7.112,7.113,7.114,7.15,7.176,7.178,7.179,7.178,7.16,7.178,7.15,7.178,7.15,7.16,7.15,7.151,7.15,7.116,7.111,7.115,7.111,7.148,7.155,7.111,7.147,7.11,7.111,7.148,7.15,7.18,7.111,7.11,7.113,7.106,7.001,7.0,6.904,6.9,6.904,6.9,6.86,6.87,7.297,7.34,7.2,7.1,7.0,6.906,7.198,6.92,7.198,7.2,7.1,7.0,7.199,7.2,6.938,7.2,7.18,7.17,7.16,7.18,7.16,7.1,7.199,7.1,7.199,7.19,7.11,7.16,7.11,7.1,7.008,7.011,7.033,7.034,7.159,7.036,7.1,7.159,7.06,7.15,7.08,7.15,7.08,7.145,7.081,7.08,7.143,7.08,7.09,7.143,7.091,7.143,7.101,7.1,7.143,7.1,7.143,7.1,7.143,7.091,7.143,7.101,7.143,7.1,7.09,7.081,7.08,7.135,7.081,7.08,7.135,7.041,7.134,7.135,7.043,7.135,7.045,7.135,7.045,7.135,7.046,7.052,7.05,7.04,7.01,6.91,7.1,7.09,7.08,6.95,7.07,7.06,7.07,7.06,7.07,7.06,7.07,7.0,7.08,7.0,7.05,7.04,7.02,7.022,7.01,7.0,7.01,7.0,7.04,7.0,6.98,7.0,6.98,6.96,6.98,6.96,6.95,6.96,6.95,6.947,6.932,6.95,6.93,6.92,6.91,6.906,6.91,6.95,6.91,6.9,6.891,6.899,6.88,6.899,6.9,6.899,6.9,6.88,6.98,6.97,6.98,7.0,7.02,7.05,7.02,7.018,7.02,7.05,7.095";
        String[] ansArray = StringUtils.split(ansStr, ",");

//        TreeMap<Double, Integer> treeSet = new TreeMap<>();

        int tendAdd = 50;
        int tendRemove = 50;
        double higher = 0;
        double lower  = 0;
        double temp = 0;
        int flag = 0;
        boolean buied = false;
        double total = 0;
        double last = 0;
        int index =0;
        for (String price : ansArray) {
            index ++;
            double p = Double.parseDouble(price);
//
            if (last > 0)  {
                if (last - p > 0) {
                    tendAdd ++;
                    tendRemove --;
                } else if (last - p < 0) {
                    tendAdd --;
                    tendRemove ++;
                }
            }
            last = p;

            if (p > higher) {
                higher = p;
                flag = 1;
            } else if (p < lower || (lower == 0 && p < higher)) {
                lower = p;
                flag = -1;
            } else {
                flag = 0;
            }

            if ((higher - lower)/higher > 0.02 && lower > 0) {
                if(flag == -1){//下降
                    System.out.println("买入 " + higher +"-" + lower + " : "+ tendAdd + " " + tendRemove);
                    higher = 0;
                    lower = 0;
                } else if(flag == 1) {//上升
                    System.out.println("卖出 "+ lower +"-" + higher +" : " + tendAdd +" " + tendRemove);
                    higher = 0;
                    lower = 0;
                } else {

                }
            }
//            putAndCount(price, treeSet);
        }

//        for (Map.Entry<Double, Integer> entry : treeSet.entrySet()) {
//            System.out.println(entry.getKey() +":" + entry.getValue());
//        }
    }

    private void putAndCount(String price, TreeMap<Double,Integer> treeSet) {
        if (StringUtils.isBlank(price)) return;

        Double range = Double.parseDouble(price) * 10;
        double key = range.intValue()/10d;
        Integer integer = treeSet.get(key);
        if (null == integer) {
            integer = 0;
        }
        integer ++;
        treeSet.put(key, integer);
    }
}
