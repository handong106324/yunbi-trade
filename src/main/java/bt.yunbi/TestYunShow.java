package bt.yunbi;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.sourceforge.pinyin4j.PinyinHelper;
import bt.yunbi.market.bean.BitOrder;
import bt.yunbi.market.bean.Market;
import bt.yunbi.market.bean.Symbol;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by handong on 17/1/13.
 * 1: 两种方式
 *      1: 直接页面通过js读取
 *      2: java代码读取直接分析
 *
 */
public class TestYunShow {
    @Test
    public void queryBtc() throws ParseException {
        String html = postHtml(YunBiMarcket.BTC);

//        String allCurrencies = getContent(html,"gon.currencies=",";gon.fiat_currency=");
//        System.out.println(JSON.parseObject(allCurrencies));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse("1933-12-12 12:12:12");
        System.out.println(new Date().getTime());
        System.out.println(date.getTime());

        String st = "gon.trades=";
        String et = ";gon.config=";
        String tradeList = getContent(html, st, et);//成交列表
        JSONArray jsonArray = JSON.parseArray(tradeList);
        for (Object jsonObject : jsonArray) {
            if (JSONObject.class == jsonObject.getClass()) {
                JSONObject data = (JSONObject) jsonObject;
                System.out.println(data.get("price") + getBlank(data.getString("price").length(), " ")
                + data.get("amount") +  getBlank(data.getString("amount").length(), " ") + data.get("type"));
            }
        }
    }

    private String getBlank(int amount, String s) {
        int len = 8 - amount;
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0;i < len; i++) {
            stringBuffer.append(" ");
        }
        return stringBuffer.toString();
    }


    @Test
    public void queryZmc() throws Exception {
        String html = postHtml(YunBiMarcket.BTC);
        JSONObject allTickets = JSONObject.parseObject(getContent(html, "gon.tickers=", ";gon.asks"));
        Map<String,Double> bidMap = new HashMap<>();
        bidMap.put("zmccny", 4.35);
//        bidMap.put("btccny", 6790.00);
        for (Map.Entry entry : allTickets.entrySet()) {
            if (!entry.getKey().equals("zmccny") &&
                    !entry.getKey().equals("btccny")
//                    && !entry.getKey().equals("sccny")
                    ) {
                continue;
            }
//            System.out.println(entry.getValue());
            System.out.println(recordInfo((String) entry.getKey(), (JSONObject) entry.getValue(),
                    bidMap.get(entry.getKey()), Symbol.get((String) entry.getKey()), Market.PeatioCNY));
            System.out.println();
        }

//
//        JSONArray sellList = JSONArray.parseArray(getContent(html, "gon.asks=", ";gon.bids"));
//        JSONArray buyList = JSONArray.parseArray(getContent(html, "gon.bids=", ";gon.trades"));
//
//        System.out.println("sell  = " + sellList.get(0));
//        System.out.println("buy  = " + buyList.get(0));
    }


    private String recordInfo(String key, JSONObject jsonObject, Double yourBid,
                              Symbol symbol, Market market) throws Exception {

        if (yourBid == null) {
            yourBid = 0d;
        }
        double currentPrice = jsonObject.getDouble("last");
        double tendency = jsonObject.getDouble("open") - currentPrice;
        String totalTendy = " 平 ";
        if (tendency > 0) {
            totalTendy = " 降 ";
        } else if (tendency < 0){
            totalTendy = " 升 ";
        }


        String currentSellTend ="", currentBuyTend ="";
        if (yourBid == 0) {
            currentSellTend = " sell =" + jsonObject.getDouble("buy");
            currentBuyTend = " buy need =" + jsonObject.getDouble("sell");
            double cuSellTend = currentPrice - jsonObject.getDouble("sell");

            if (cuSellTend > 0 ) {
                currentSellTend += " 可买";
            }

        } else {
            double sellPrice = jsonObject.getDouble("buy");
            double cuBuyTend = yourBid - sellPrice;
            double get = jsonObject.getDouble("buy") - yourBid;
            if (cuBuyTend < 0) {
                currentBuyTend += " 可卖 " + get +" r=" + get/yourBid;
//                BitOrder order = sell(0.0001, sellPrice + 800, symbol, market);
//                int index = 0;
//                while (order.getStatus().isRunning()) {
//                    Thread.sleep(1000);
//                    if (order.getStatus().isComplete()) {
//                        System.out.println("卖出");
//                        break;
//                    }
//                    if (index == 20) {
//                        YunBiApi.cancel(order.getId(), market);
//                        break;
//                    }
//                    index ++;
//                }
            }
        }



        return key +" 总:" + totalTendy + " current=" + currentPrice +" " + currentBuyTend +" " + currentSellTend;
    }

    private BitOrder sell(Double amount, Double price, Symbol symbol, Market market) throws Exception {
        return YunBiApi.sell(amount,price,symbol,market);
    }

    private String getContent(String html, String st, String et) {
        int index = html.indexOf(st);
        int end = html.indexOf(et);
        return html.substring(index + st.length(), end);
    }

    private String postHtml(String url) {
        return HttpUtil.doGet(url);
    }

    @Test
    public void login() {
        String[] c = PinyinHelper.toTongyongPinyinStringArray('不');
         System.out.println(c);
    }
}
