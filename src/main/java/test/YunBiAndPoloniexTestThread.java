package test;

import platform.yunbi.market.bean.Symbol;
import model.ChainCoin;
import org.apache.commons.lang.StringUtils;
import platform.poloniex.PoloniexMarketClient;

import java.util.List;
import java.util.Map;

/**
 * Created by handong on 17/5/16.
 */
public class YunBiAndPoloniexTestThread extends BasicTestRunnable{

    @Override
    public void run() {
        PoloniexMarketClient client = new PoloniexMarketClient();
        double yunEthPrice = getBuyPrice(Symbol.eth);//云币网买eth的花费
        System.out.println("云币买价格=" + yunEthPrice);

        Map<String, ChainCoin> map = client.getAllCoinInfo();
        List<String> pairs = client.getPairs();
        double btcEthRate = Double.parseDouble(map.get("BTC_ETH").getBidBuy());

        double canRate = 0.07;
        Symbol[] symbols = Symbol.values();
        for (Symbol symbol : symbols) {
            if (symbol.name().equals("eth")) {
                continue;
            }
            double tarPrice = getBuyPrice(symbol);
            if (0.0 == tarPrice) {
                continue;
            }

            double yunCount = yunEthPrice / tarPrice;

            double bCountToEth,bCount = 0;
            String marketName = in(symbol, pairs, true, "_");
            if (StringUtils.isNotBlank(marketName)) {
                bCountToEth = Double.parseDouble(map.get(marketName).getAskSell());
                bCount = 1 / bCountToEth;

                System.out.println(marketName + ":" + (bCount - yunCount) / yunCount +"[" + bCountToEth + "]");

                if (bCount > yunCount && (bCount - yunCount) / yunCount > canRate) {
                    System.out.print(" ---ETH 可以搬砖 ---" + (bCount - yunCount) / yunCount);
                }

            }


            marketName = in(symbol, pairs, false, "_");
            if (StringUtils.isNotBlank(marketName)) {
                double btcTarRate = Double.parseDouble(map.get(marketName).getAskSell());
                bCount = btcEthRate / btcTarRate;

                if (bCount > yunCount && (bCount - yunCount) / yunCount > canRate) {
                    System.out.print(" ---BTC 可以搬砖 ---" + (bCount - yunCount) / yunCount);
                }
            }
        }
    }
}
