package platform.yunbi.client.strategy.impl;

import platform.yunbi.client.strategy.AbsStrategy;
import platform.yunbi.client.strategy.StrategyParam;

import java.io.IOException;

/**
 * Created by handong on 17/1/27.
 */
public class EasyStrategy extends AbsStrategy{
    public EasyStrategy(StrategyParam param) throws IOException {
        super(param);
    }

    private double buyPrice;

}
