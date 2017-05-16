package biter;

import bt.yunbi.HttpUtil;
import client.BanZhuanMarketClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by handong on 17/5/15.
 */
public class BiterMarketClient implements BanZhuanMarketClient {

    @Override
    public JSONObject getMarketInfo() {

        String url = "http://data.bter.com/api2/1/marketlist";
        JSONArray array =  JSON.parseObject(HttpUtil.doGet(url)).getJSONArray("data");
        JSONObject data = new JSONObject();
        for (Object jsonObject : array) {
            JSONObject jsonObject1 = (JSONObject) jsonObject;
            data.put(jsonObject1.getString("pair"), jsonObject1);
        }

        return data;
    }

    public JSONArray getMarketPairs() {
        String biterUrl = "http://data.bter.com/api2/1/pairs";
        String res = HttpUtil.doGet(biterUrl);
        return JSON.parseArray(res);
    }
}
