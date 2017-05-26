package test;

import platform.bittrex.BittrexMarketClient;
import platform.yunbi.market.bean.Symbol;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Created by handong on 17/5/16.
 */
public class YunBiAndBittresRunnable extends BasicTestRunnable {
    @Override
    public void run() {
        BittrexMarketClient client = new BittrexMarketClient();
        while (true) {
            try {
                double yunEthPrice = getBuyPrice(Symbol.eth);//云币网买eth的花费

                List<String> pairs = client.getPairs();
                double btcEthRate = Double.parseDouble(client.getChainCoin("BTC-ETH").getBidBuy());

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

                    double bCountToEth, bCount = 0;
                    String marketName = in(symbol, pairs, true, "-");
                    if (StringUtils.isNotBlank(marketName)) {
                        bCountToEth = Double.parseDouble(client.getChainCoin(marketName).getAskSell());
                        bCount = 1 / bCountToEth;

                        if (yunCount > 0 && bCount > yunCount && (bCount - yunCount) / yunCount > canRate) {
                            System.out.print(" ---ETH 可以搬砖 ---" + (bCount - yunCount) / yunCount);
                        }

                    }


                    marketName = in(symbol, pairs, false, "-");
                    if (StringUtils.isNotBlank(marketName)) {
                        double btcTarRate = Double.parseDouble(client.getChainCoin(marketName).getAskSell());
                        bCount = btcEthRate / btcTarRate;
                        if (yunCount > 0 && bCount > yunCount && (bCount - yunCount) / yunCount > canRate) {
                            System.out.print(" ---BTC 可以搬砖 ---" + (bCount - yunCount) / yunCount);
                        }
                    }
                }

                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
