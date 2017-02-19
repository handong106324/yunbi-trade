package yun.bt.test;

import org.apache.commons.lang.StringUtils;
import org.bitcoin.client.strategy.TendencyStrategyParam;
import org.bitcoin.client.strategy.impl.TendencyGuessFeeOne;
import org.bitcoin.client.strategy.impl.TendencyStrategy;
import org.bitcoin.market.bean.Symbol;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by handong on 17/1/23.
 */
public class TradeClientTest {


    @Test
    public void selectStrategy() throws IOException {
        TendencyStrategyParam param = new TendencyStrategyParam();
        param.setSymbol(Symbol.btc);
        int min = 60;
        param.setLimitCount(5 * 24 * 60 /min);
        param.setTendencyTime(min);
        param.setSellRate(0.005);
        param.setBuyRate(0.01);
        param.setDownTimeForBuy(-4);
        param.setUpTimeForSell(3);
        param.setCost(10000);

        param.setTendencyType(TendencyStrategy.TENDENCY_TYPE_MIN);
        TendencyStrategy strategy = new TendencyGuessFeeOne(param, true);
        strategy.tendency();

    }

    @Test
    public void runBtcAndTendency5Min() throws IOException {
        TendencyStrategyParam param = new TendencyStrategyParam();
        param.setSymbol(Symbol.btc);
        int min = 30;
        param.setLimitCount(10 * 24 * 60 /min);
        param.setTendencyTime(min);
        param.setSellRate(0.005);
        param.setBuyRate(0.01);
        param.setDownTimeForBuy(-3);
        param.setUpTimeForSell(3);
        param.setCost(10000);

        param.setTendencyType(TendencyStrategy.TENDENCY_TYPE_MIN);
        TendencyStrategy strategy = new TendencyGuessFeeOne(param, true);
        strategy.tendency();
        System.out.println(strategy.getResult());
    }

    @Test
    public void test() {
        String[] ss = StringUtils.split("ss,", ",");
        System.out.println(ss.length);
        System.out.println(getFieldValue("ss,",1));
    }
    private String getFieldValue(String valTemp, int i) {
        String[] vals = StringUtils.split((valTemp), ",");
        if (vals.length > i) {
            return vals[i];
        } else {
            return "";
        }
    }
}
