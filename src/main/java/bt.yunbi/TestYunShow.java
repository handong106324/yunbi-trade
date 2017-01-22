package bt.yunbi;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.junit.Test;

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
    public void getTestHtml() {
        String html = postHtml(YunBiMarcket.BTC);

        String allCurrencies = getContent(html,"gon.currencies=",";gon.fiat_currency=");
        System.out.println(JSON.parseObject(allCurrencies));

        String st = "gon.trades=";
        String et = ";gon.config=";
        String tradeList = getContent(html, st, et);//成交列表
        JSONArray jsonArray = JSON.parseArray(tradeList);
        System.out.println(jsonArray);
//
//        JSONObject allTickets = JSONObject.parseObject(getContent(html, "gon.tickers=", ";gon.asks"));
//        JSONArray sellList = JSONArray.parseArray(getContent(html, "gon.asks=", ";gon.bids"));
//        JSONArray buyList = JSONArray.parseArray(getContent(html, "gon.bids=", ";gon.trades"));
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

    }
}
