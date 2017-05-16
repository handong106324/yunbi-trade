package btc38;

import bt.yunbi.HttpUtil;
import client.BanZhuanMarketClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by handong on 17/5/15.
 */
public class Btc38Client implements BanZhuanMarketClient{

    @Override
    public JSONObject getMarketInfo() {
        String btc38 = "http://api.btc38.com/v1/ticker.php?c=all&mk_type=cny";
        String res =  HttpUtil.doGet(btc38);
        return JSON.parseObject(res);
    }
}
