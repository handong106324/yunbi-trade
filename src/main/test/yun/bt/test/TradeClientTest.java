package yun.bt.test;

import platform.biter.BiterMarketClient;
import platform.bittrex.BittrexMarketClient;
import platform.yunbi.HttpUtil;

import platform.btc38.Btc38Client;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import platform.yunbi.client.TendencyStrategyThread;
import platform.yunbi.client.strategy.TendencyStrategyParam;
import platform.yunbi.client.strategy.impl.TendencyGuessFeeOne;
import platform.yunbi.client.strategy.impl.TendencyStrategy;
import platform.yunbi.market.bean.Market;
import platform.yunbi.market.bean.Symbol;
import test.BasicTestRunnable;
import test.YunBiAndBittresRunnable;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by handong on 17/1/23.
 */
public class TradeClientTest {

    public static void main(String[] args) {

        YunBiAndBittresRunnable thread = new YunBiAndBittresRunnable();
        ExecutorService threadPool = Executors.newCachedThreadPool();
        CompletionService<Integer> executor = new ExecutorCompletionService<Integer>(threadPool);

//        executor.submit(thread, 1);

//
        try {
            executor.submit(getTendencyStrategyThread(Symbol.sc),1);
            executor.submit(getTendencyStrategyThread(Symbol.ans),1);
            executor.submit(getTendencyStrategyThread(Symbol.bts),1);
            executor.submit(getTendencyStrategyThread(Symbol.gnt),1);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static TendencyStrategyThread getTendencyStrategyThread(Symbol symbol) throws IOException {
        TendencyStrategy strategy = getTendencyStrategy(symbol);
        return new TendencyStrategyThread(strategy);
    }

    private static TendencyStrategy getTendencyStrategy(Symbol symbol) throws IOException {
        TendencyStrategyParam param = new TendencyStrategyParam();
        param.setSymbol(symbol);
        int min = 5;
        param.setLimitCount(10 * 24 * 60 / min);
        param.setTendencyTime(min);
        param.setSellRate(0.01);
        param.setBuyRate(0.003);
        param.setTendencyType(TendencyStrategy.TENDENCY_TYPE_MIN);
        TendencyStrategy strategy = new TendencyGuessFeeOne(param, true, Market.PeatioCNY);
        int sell = strategy.tendency();

        param.setDownTimeForBuy(0-sell);
        param.setUpTimeForSell(sell);
        param.setCost(10);
        return strategy;
    }

    @Test
    public void checkBtc38AndBittrex() {
        Btc38Client btc38Client = new Btc38Client();
        Set<String> set = btc38Client.getMarketInfo().keySet();
        BittrexMarketClient bittrexMarketClient = new BittrexMarketClient();

        Map<String, List<String>> conMap = new HashMap<>();
        JSONObject market = btc38Client.getMarketInfo();
        double USDT_BTC = Double.parseDouble(bittrexMarketClient.getChainCoin("USDT-BTC").getLast());
        ;
        Set<String> bitSet = new HashSet<>();
        List<String> pirs = bittrexMarketClient.getPairs();
        for (String str : pirs) {
            if (str.startsWith("BTC-") || str.startsWith("ETH-")) {
                bitSet.add(str.substring("BTc-".length()));
                addMap(conMap, str.substring("btc-".length()).toLowerCase(), str);
            }

        }

        for (String str : set) {
            if (bitSet.contains(str.toUpperCase())) {
                double btcPrice = market.getJSONObject(str).getJSONObject("ticker").getDouble("last");
                List<String> list = conMap.get(str);
                if (null != list) {
                    System.out.print("比特时代{[" + str + "=" + btcPrice + "]}");

                    System.out.print(" {");
                    for (String l : list) {
                        double bPrice = Double.parseDouble(bittrexMarketClient.getChainCoin(l).getLast());
                        double pp = bPrice * USDT_BTC * 6.8804;
                        System.out.print("  [" + l + "=" + pp + "]");

                        System.out.print(" --- [" + (btcPrice - pp) / btcPrice + "]");
                    }
                    System.out.println("}");
                }

            }
        }
    }

    @Test
    public void checkBiterAndBittrex() {
        BiterMarketClient biterMarketClient = new BiterMarketClient();
        JSONObject market = biterMarketClient.getMarketInfo();
        Set<String> set = biterMarketClient.getMarketInfo().keySet();
        BittrexMarketClient bittrexMarketClient = new BittrexMarketClient();

        double btcRePrice = market.getJSONObject("btc_cny").getDouble("rate");
        Map<String, List<String>> conMap = new HashMap<>();

        double USDT_BTC = Double.parseDouble(bittrexMarketClient.getChainCoin("USDT-BTC").getLast());
        ;
        Set<String> bitSet = new HashSet<>();
        List<String> pirs = bittrexMarketClient.getPairs();
        for (String str : pirs) {
            if (str.startsWith("BTC-") || str.startsWith("ETH-")) {
                bitSet.add(str.substring("BTc-".length()).toLowerCase());
                addMap(conMap, str.substring("btc-".length()).toLowerCase(), str);
            }

        }

        for (String str : set) {

            String simple = "";
            if (str.contains("_cny") || str.contains("_btc")) {
                simple = str.substring(0, str.length() - 4);
            }
            if (bitSet.contains(simple)) {
                double btcPrice = market.getJSONObject(str).getDouble("rate");
                if (str.endsWith("_btc")) {
                    btcPrice = btcPrice * btcRePrice;
                }
                List<String> list = conMap.get(simple);
                if (null != list) {
//                    System.out.print("比特er{["+str+"="+btcPrice+"]}");
//
//                    System.out.print(" {");
                    for (String l : list) {
                        try {
                            double bPrice = Double.parseDouble(bittrexMarketClient.getChainCoin(l).getLast());
                            double pp = bPrice * USDT_BTC * 6.8804;
//                        System.out.print("  ["+l+"="+ pp +"]");
//
                            if ((btcPrice - pp) / btcPrice < 0.01) {
                                System.out.print("比特er{[" + str + "=" + btcPrice + "]}");
                                System.out.print("  [" + l + "=" + pp + "]");
                                System.out.print(" --- [" + (btcPrice - pp) / btcPrice + "]");
                                System.out.println();
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
//                        System.out.print(" --- ["+ (btcPrice - pp)/btcPrice +"]");
                    }
//                    System.out.println("}");
                }

            }
        }
    }

    @Test
    public void checkYunAndBittrex() {
        BittrexMarketClient bittrexMarketClient = new BittrexMarketClient();

        while (true) {

            try {

                double USDT_BTC = Double.parseDouble(bittrexMarketClient.getChainCoin("USDT-BTC").getLast());
                ;
                double ETH_BTC = Double.parseDouble(bittrexMarketClient.getChainCoin("BTC-ETH").getAskSell());
                ;
                double SC_BTC = Double.parseDouble(bittrexMarketClient.getChainCoin("BTC-SC").getBidBuy());

//             System.out.println(SC_BTC_S + "B网SC   --:" + 6.8828 *  SC_BTC_S * USDT_BTC +" ETH=" + 6.8828 * USDT_BTC * ETH_BTC +" ETH个数=" + 100000 * SC_BTC_S/ETH_BTC);
                System.out.println(SC_BTC + "B网SC:" + 6.8828 * SC_BTC * USDT_BTC + " ETH=" + 6.8828 * USDT_BTC * ETH_BTC + " ETH个数=" + 100000 * SC_BTC / ETH_BTC);

                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void checkYunAndBter() {
        BiterMarketClient biterMarketClient = new BiterMarketClient();
        JSONObject market = biterMarketClient.getMarketInfo();

        Set<String> yunKeySet = new HashSet<>();
        yunKeySet.add("sc");
        yunKeySet.add("ans");
        yunKeySet.add("eth");
        yunKeySet.add("etc");
        yunKeySet.add("dgd");
        yunKeySet.add("gnt");
        yunKeySet.add("qtum");
        yunKeySet.add("bts");
        yunKeySet.add("zec");
        yunKeySet.add("rep");

        BasicTestRunnable runnable = new BasicTestRunnable() {
            @Override
            public void run() {
                return;
            }
        };

        while (true) {
            try {
                double scPrice = runnable.getBuyPrice(Symbol.sc);
                double bterCny = market.getJSONObject("eth_cny").getDouble("rate");
                System.out.println(22 * bterCny / scPrice);
                Thread.sleep(1000);
            } catch (Exception e) {

            }
        }
//        for (Map.Entry<String,Object> entry : market.entrySet()) {
//            String[] keys = entry.getKey().split(("_"));
//            if (yunKeySet.contains(keys[0]) && keys[1].equals("cny")){
//                System.out.println(entry.getKey() +":" + ((JSONObject)entry.getValue()).getString("rate") +"  --》" +
//                runnable.getBuyPrice(Symbol.get(keys[0])));
//            }
//        }
    }

    public void addMap(Map<String, List<String>> map, String key, String value) {
        List<String> list = map.get(key);
        if (null == list) {
            list = new ArrayList<>();
            map.put(key, list);
        }

        if (!list.contains(value)) {
            list.add(value);
        }
    }

    @Test
    public void getBal() {
        String url = "https://bittrex.com/api/v1.1/account/getbalances?apikey=d43d8ba520784fc9b8a56c193bb8dcb7&nonce=" + new Date().getTime();
        System.out.println(HttpUtil.doGet(url));
    }

}
