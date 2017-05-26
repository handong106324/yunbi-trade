package platform.poloniex;

import platform.yunbi.HttpUtil;
import client.BanZhuanMarketClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import model.ChainCoin;

import java.util.*;

/**
 * Created by handong on 17/5/16.
 */
public class PoloniexMarketClient implements BanZhuanMarketClient{
    @Override
    public JSONObject getMarketInfo() {
        return JSON.parseObject(HttpUtil.doGet("https://poloniex.com/public?command=returnTicker"));
    }

    @Override
    public String getSp() {
        return "_";
    }

    public List<String> getPairs(){
        List<String> list = new ArrayList<>();
        JSONObject data = getMarketInfo();
        Set<String> set = data.keySet();
        for (String s:set) {
            list.add(s);
        }
        return list;
    }

    public Map<String, ChainCoin> getAllCoinInfo() {
        JSONObject allData = getMarketInfo();
        Map<String, ChainCoin> map = new HashMap<>();

        for (Map.Entry<String,Object> entry : allData.entrySet()) {
            String key = entry.getKey();
            JSONObject data = (JSONObject) entry.getValue();
            ChainCoin chainCoin = getChainCoin(data);
            if (chainCoin == null) continue;
            chainCoin.setKey(key);
            map.put(key, chainCoin);
        }

        return map;
    }

    public ChainCoin getChainCoin(JSONObject tickerData) {

        ChainCoin chainCoin = new ChainCoin();
        chainCoin.setAskSell(tickerData.getString("lowestAsk"));
        chainCoin.setBidBuy(tickerData.getString("highestBid"));
        chainCoin.setLast(tickerData.getString("last"));
        return chainCoin;
    }
}
