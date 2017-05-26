package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by handong on 17/5/17.
 */
public class BanZhuanInfo {
    private String unionKey;
    private ChainCoin bittrexCoin;
    private List<ChainCoin> chinaCoinList = new ArrayList<>();

    public String getUnionKey() {
        return unionKey;
    }

    public void setUnionKey(String unionKey) {
        this.unionKey = unionKey;
    }

    public ChainCoin getBittrexCoin() {
        return bittrexCoin;
    }

    public void setBittrexCoin(ChainCoin bittrexCoin) {
        this.bittrexCoin = bittrexCoin;
    }

    public List<ChainCoin> getChinaCoinList() {
        return chinaCoinList;
    }

    public void setChinaCoinList(List<ChainCoin> chinaCoinList) {
        this.chinaCoinList = chinaCoinList;
    }
}
