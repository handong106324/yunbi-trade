package platform.yunbi.client;


/**
 * Created by handong on 17/1/22.
 */
public class TendencyStrategyThread implements Runnable{
    private platform.yunbi.client.strategy.impl.TendencyStrategy strategy;

    public TendencyStrategyThread(platform.yunbi.client.strategy.impl.TendencyStrategy strategy) {
        this.strategy = strategy;
    }
    @Override
    public void run() {
        runTrade(strategy);
    }

    private void runTrade(platform.yunbi.client.strategy.impl.TendencyStrategy strategy) {
        double money = 0;
        double cost = 0;
        int time = ((platform.yunbi.client.strategy.TendencyStrategyParam)strategy.getStrategyParam()).getTendencyTime();
        long start = System.currentTimeMillis();
        long during = time * getTimeDuring();
        long waitTime =(during - start % (time * getTimeDuring()));
        boolean isBuy = true;
        while (true) {

            try {
                int res = strategy.getResult();
                if (res == 1 && isBuy) {
                    double ask = strategy.getFirstAsk();
                    money -= ask;
                    if (cost == 0) {
                        cost = ask;
                    }
                    log("buy :" + ask);
                    strategy.writeFile("buy:" + ask);
                    isBuy = false;
                } else if (res == -1 && !isBuy) {
                    double bid = strategy.getFirstBid();
                    money += bid;
                    log("sell :" + bid +" get=" + money );
                    strategy.writeFile("sell :" + bid +" get=" + money);
                    isBuy = true;
                } else {
//                    System.out.print(strategy.getStrategyParam().getSymbol().name() + " non ");
                }

                Thread.sleep(waitTime);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void log(String log) {
        System.out.println(strategy.getStrategyParam().getSymbol() + " : " +log);
    }

    public long getTimeDuring() {
        int type =  ((platform.yunbi.client.strategy.TendencyStrategyParam) strategy.getStrategyParam()).getTendencyType();
        if (platform.yunbi.client.strategy.impl.TendencyStrategy.TENDENCY_TYPE_HOUR == type){
            return 60 * 60 * 1000;
        } else if (platform.yunbi.client.strategy.impl.TendencyStrategy.TENDENCY_TYPE_MIN == type) {
            return 60 * 1000;
        } else {
            return 24 * 60 * 60 * 1000;
        }
    }
}
