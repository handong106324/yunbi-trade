package test;

import platform.biter.BiterMarketClient;
import platform.btc38.Btc38Client;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by handong on 17/5/16.
 */
public class BiterAndBtcsdTestThread extends BasicTestRunnable{

    @Override
    public void run() {
        //比特儿
        BiterMarketClient biterMarketClient = new BiterMarketClient();
        JSONObject biterData = biterMarketClient.getMarketInfo();

        //比特时代
        JSONObject jsonObject = new Btc38Client().getMarketInfo();

        List<String> allHasBi = new ArrayList<>();
        //
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            if (biterData.keySet().contains(entry.getKey() + "_cny")) {
                double ticker1 = Double.parseDouble((((JSONObject) entry.getValue()).getJSONObject("ticker")).getString("last"));
                double ticker2 = Double.parseDouble(((JSONObject) biterData.get(entry.getKey() + "_cny")).getString("rate"));
                double rate = 0.0;
                if (ticker1 > ticker2) {
                    rate = ticker2 / ticker1;
                } else {
                    rate = ticker1 / ticker2;
                }
                System.out.println(entry.getKey() + " = " + rate + " [" + ticker1 + " " + ticker2 + " " + (rate < 0.93 ? "可搬砖" : ""));
                if (rate < 0.93) {
                    allHasBi.add(entry.getKey());
                }
            }
        }

    }
}
