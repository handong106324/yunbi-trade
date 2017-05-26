package client;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by handong on 17/5/15.
 */
public interface BanZhuanMarketClient {
    JSONObject getMarketInfo();
    String getSp();
}
