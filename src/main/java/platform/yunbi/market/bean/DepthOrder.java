package platform.yunbi.market.bean;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by handong on 17/6/20.
 */
public class DepthOrder {
    private List<DepthTrade> asks = new ArrayList<>();

    private List<DepthTrade> bids = new ArrayList<>();

    public DepthOrder(JSONObject depth) {
        JSONArray askList = depth.getJSONArray("asks");
        JSONArray bidList = depth.getJSONArray("bids");

        for (Object object : askList) {
            JSONObject jsonObject = (JSONObject)object;
            asks.add(JSONObject.toJavaObject(jsonObject, DepthTrade.class));
        }

        for (Object object : bidList) {
            JSONObject jsonObject = (JSONObject)object;
            bids.add(JSONObject.toJavaObject(jsonObject, DepthTrade.class));
        }
    }

    public List<DepthTrade> getAsks() {
        return asks;
    }

    public void setAsks(List<DepthTrade> asks) {
        this.asks = asks;
    }

    public List<DepthTrade> getBids() {
        return bids;
    }

    public void setBids(List<DepthTrade> bids) {
        this.bids = bids;
    }
}
