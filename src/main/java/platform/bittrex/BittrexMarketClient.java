package platform.bittrex;

import client.BanZhuanMarketClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import model.ChainCoin;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by handong on 17/5/15.
 */
public class BittrexMarketClient implements BanZhuanMarketClient {
    private List<String> pairs = new ArrayList<>();

    @Override
    public JSONObject getMarketInfo() {
        return null;
    }

    @Override
    public String getSp() {
        return "-";
    }

    public Map<String, ChainCoin> getAllCoinInfo() {
        List<String> array = getPairs();
        Map<String, ChainCoin> map = new HashMap<>();
        for (String o : array) {
            ChainCoin chainCoin = getChainCoin(o);
            if (chainCoin == null) continue;
            map.put(o, chainCoin);
        }

        return map;
    }

    public List getPairs() {
        if (pairs.isEmpty()) {
            BittrexCommunicator communicator  = new BittrexCommunicator("BTC-LTC","","");
            JSONArray array =  communicator.getMarkets();
            for (Object o : array) {
                String mrk = ((JSONObject)o).getString("MarketName");
                if (!pairs.contains(mrk)) {
                    pairs.add(mrk);
                }
            }
        }

        return pairs;
    }

    public ChainCoin getChainCoin(String market) {
        BittrexCommunicator communicatorTemp  = new BittrexCommunicator(market,"","");
        JSONObject tickerData = communicatorTemp.getTicker();
        if (null == tickerData) {
            return null;
        }
        ChainCoin chainCoin = new ChainCoin();
        chainCoin.setAskSell(tickerData.getString("Ask"));
        chainCoin.setBidBuy(tickerData.getString("Bid"));
        chainCoin.setLast(tickerData.getString("Last"));
        chainCoin.setKey(market);
        if (StringUtils.isBlank(chainCoin.getAskSell())
                || StringUtils.isBlank(chainCoin.getBidBuy())
            ||StringUtils.isBlank(chainCoin.getLast())) {
            return null;
        }
        return chainCoin;
    }

    @Test
    public void testShowMarket() {

        Map<String,ChainCoin> map = getAllCoinInfo();
        for (Map.Entry<String,ChainCoin> entry : map.entrySet()) {
            System.out.println(entry.getValue());
        }
    }
}
