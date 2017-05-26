package test;

import model.BanZhuanInfo;
import model.ChainCoin;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.*;

/**
 * Created by handong on 17/5/17.
 */
public class MarketCheckClient {

    @Test
    public void collection() {

        List<String> list = new ArrayList<>();
        MarketInfoCenter center = new MarketInfoCenter();
        Map<String, BanZhuanInfo> banZhuanInfoMap = center.getMarketBanZhuanInfo();

        Iterator iterator = banZhuanInfoMap.values().iterator();
        while (iterator.hasNext()) {
            BanZhuanInfo banZhuanInfo = (BanZhuanInfo) iterator.next();
            if (null == banZhuanInfo.getBittrexCoin() || banZhuanInfo.getChinaCoinList().isEmpty()) {
                continue;
            }

            ChainCoin bittrexCoin = banZhuanInfo.getBittrexCoin();
            double b = Double.parseDouble(bittrexCoin.getLast());

            for (ChainCoin coin : banZhuanInfo.getChinaCoinList()) {
                if (StringUtils.isBlank(coin.getAskSell()) && StringUtils.isBlank(coin.getLast()) &&
                        StringUtils.isBlank(coin.getBidBuy())) {
                    continue;
                }
                double dd = Double.parseDouble(StringUtils.isNotBlank(coin.getLast())?coin.getLast():
                        (StringUtils.isBlank(coin.getAskSell())?coin.getAskSell():coin.getBidBuy()));
                double get = (dd - b)/dd;
                System.out.println("       >>"+coin.getPlateForm() +"-" + coin.getKey() +"=" + get);

                if (get < 0.01 && !coin.getKey().endsWith("_btc")) {
                    list.add(coin.getPlateForm() +"-" + coin.getKey() +"=" + get);
                }
            }
        }
        System.out.println("---------keyi baz");
        for (String str : list) {
            System.out.println(str);
        }
    }

}
