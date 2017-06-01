package test;

import org.junit.Test;
import platform.yunbi.GuessTemplate;
import platform.yunbi.market.AbstractMarketApi;
import platform.yunbi.market.MarketApiFactory;
import platform.yunbi.market.bean.Kline;
import platform.yunbi.market.bean.Market;
import platform.yunbi.market.bean.Symbol;
import platform.yunbi.market.bean.SymbolPair;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.util.List;

/**
 * Created by handong on 17/5/31.
 */
public class YunGuessTest {
    @Test
    public void testStatics() {
        AbstractMarketApi abstractMarketApi = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        GuessTemplate template = new GuessTemplate();
        try {
            System.out.println("--------sc--------");

            List<Kline> klineList = abstractMarketApi.getKlineMin(Symbol.sc,5, 200);
            template.statics(klineList);
System.out.println("--------gnt--------");
            klineList = abstractMarketApi.getKlineMin(Symbol.gnt,5, 200);
            template.statics(klineList);
            System.out.println("--------bts--------");

            klineList = abstractMarketApi.getKlineMin(Symbol.bts,5, 200);
            template.statics(klineList);
            System.out.println("--------ans--------");

            klineList = abstractMarketApi.getKlineMin(Symbol.ans,5, 200);
            template.statics(klineList);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
