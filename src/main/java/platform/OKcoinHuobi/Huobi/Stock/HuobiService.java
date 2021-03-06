package platform.OKcoinHuobi.Huobi.Stock;

import java.util.TreeMap;
import org.apache.commons.lang.StringUtils;

public class HuobiService {
	/**
     * 下单接口
     * 
     * @param coinType
     * @param price
     * @param amount
     * @param tradePassword
     * @param tradeid
     * @param method
     * @return
     * @throws Exception
     */
    public String buy(int coinType, String price, String amount, String tradePassword, Integer tradeid, String method)
            throws Exception {
    	Base base = new Base();
        TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
        paraMap.put("method", method);
        paraMap.put("created", base.getTimestamp());
        paraMap.put("access_key", Base.HUOBI_ACCESS_KEY);
        paraMap.put("secret_key", Base.HUOBI_SECRET_KEY);
        paraMap.put("coin_type", coinType);
        paraMap.put("price", price);
        paraMap.put("amount", amount);
        String md5 = base.sign(paraMap);
        paraMap.remove("secret_key");
        paraMap.put("sign", md5);
        if (StringUtils.isNotEmpty(tradePassword)) {
            paraMap.put("trade_password", tradePassword);
        }
        if (null != tradeid) {
            paraMap.put("trade_id", tradeid);
        }
        return base.post(paraMap, Base.HUOBI_API_URL);
    }

    /**
     * 提交市价单接口
     * 
     * @param coinType
     * @param amount
     * @param tradePassword
     * @param tradeid
     * @param method
     * @return
     * @throws Exception
     */
    public String buyMarket(int coinType, String amount, String tradePassword, Integer tradeid, String method)
            throws Exception {
    	Base base = new Base();
        TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
        paraMap.put("method", method);
        paraMap.put("created", base.getTimestamp());
        paraMap.put("access_key", Base.HUOBI_ACCESS_KEY);
        paraMap.put("secret_key", Base.HUOBI_SECRET_KEY);
        paraMap.put("coin_type", coinType);
        paraMap.put("amount", amount);
        String md5 = base.sign(paraMap);
        paraMap.remove("secret_key");
        paraMap.put("sign", md5);
        if (StringUtils.isNotEmpty(tradePassword)) {
            paraMap.put("trade_password", tradePassword);
        }
        if (null != tradeid) {
            paraMap.put("trade_id", tradeid);
        }
        return base.post(paraMap, Base.HUOBI_API_URL);
    }

    /**
     * 撤销订单
     * 
     * @param coinType
     * @param id
     * @param method
     * @return
     * @throws Exception
     */
    public String cancelOrder(int coinType, long id, String method) throws Exception {
        TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
        Base base = new Base();
        paraMap.put("method", method);
        paraMap.put("created", base.getTimestamp());
        paraMap.put("access_key", Base.HUOBI_ACCESS_KEY);
        paraMap.put("secret_key", Base.HUOBI_SECRET_KEY);
        paraMap.put("coin_type", coinType);
        paraMap.put("id", id);
        String md5 = base.sign(paraMap);
        paraMap.remove("secret_key");
        paraMap.put("sign", md5);
        return base.post(paraMap, Base.HUOBI_API_URL);
    }

    /**
     * 获取账号详情
     * 
     * @param method
     * @return
     * @throws Exception
     */
    public String getAccountInfo(String method) throws Exception {
    	Base base = new Base();
        TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
        paraMap.put("method", method);
        paraMap.put("created", base.getTimestamp());
        paraMap.put("access_key", Base.HUOBI_ACCESS_KEY);
        paraMap.put("secret_key", Base.HUOBI_SECRET_KEY);
        String md5 = base.sign(paraMap);
        paraMap.remove("secret_key");
        paraMap.put("sign", md5);
        return base.post(paraMap, Base.HUOBI_API_URL);
    }

    /**
     * 查询个人最新10条成交订单
     * 
     * @param coinType
     * @param method
     * @return
     * @throws Exception
     */
    public String getNewDealOrders(int coinType, String method) throws Exception {
        TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
        Base base = new Base();
        paraMap.put("method", method);
        paraMap.put("created", base.getTimestamp());
        paraMap.put("access_key", Base.HUOBI_ACCESS_KEY);
        paraMap.put("secret_key", Base.HUOBI_SECRET_KEY);
        paraMap.put("coin_type", coinType);
        String md5 = base.sign(paraMap);
        paraMap.remove("secret_key");
        paraMap.put("sign", md5);
        return base.post(paraMap, Base.HUOBI_API_URL);
    }

    /**
     * 根据trade_id查询oder_id
     * 
     * @param coinType
     * @param tradeid
     * @param method
     * @return
     * @throws Exception
     */
    public String getOrderIdByTradeId(int coinType, long tradeid, String method) throws Exception {
    	Base base = new Base();
    	TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
        paraMap.put("method", method);
        paraMap.put("created", base.getTimestamp());
        paraMap.put("access_key", Base.HUOBI_ACCESS_KEY);
        paraMap.put("secret_key", Base.HUOBI_SECRET_KEY);
        paraMap.put("coin_type", coinType);
        paraMap.put("trade_id", tradeid);
        String md5 = base.sign(paraMap);
        paraMap.remove("secret_key");
        paraMap.put("sign", md5);
        return base.post(paraMap, Base.HUOBI_API_URL);
    }

    /**
     * 获取所有正在进行的委托
     * 
     * @param coinType
     * @param method
     * @return
     * @throws Exception
     */
    public String getOrders(int coinType, String method) throws Exception {
    	Base base = new Base();
    	TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
        paraMap.put("method", method);
        paraMap.put("created", base.getTimestamp());
        paraMap.put("access_key", Base.HUOBI_ACCESS_KEY);
        paraMap.put("secret_key", Base.HUOBI_SECRET_KEY);
        paraMap.put("coin_type", coinType);
        String md5 = base.sign(paraMap);
        paraMap.remove("secret_key");
        paraMap.put("sign", md5);
        return base.post(paraMap, Base.HUOBI_API_URL);
    }

    /**
     * 获取订单详情
     * 
     * @param coinType
     * @param id
     * @param method
     * @return
     * @throws Exception
     */
    public String getOrderInfo(int coinType, long id, String method) throws Exception {
    	Base base = new Base();
    	TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
        paraMap.put("method", method);
        paraMap.put("created", base.getTimestamp());
        paraMap.put("access_key", Base.HUOBI_ACCESS_KEY);
        paraMap.put("secret_key", Base.HUOBI_SECRET_KEY);
        paraMap.put("coin_type", coinType);
        paraMap.put("id", id);
        String md5 = base.sign(paraMap);
        paraMap.remove("secret_key");
        paraMap.put("sign", md5);
        return base.post(paraMap, Base.HUOBI_API_URL);
    }

    /**
     * 限价卖出
     * 
     * @param coinType
     * @param price
     * @param amount
     * @param tradePassword
     * @param tradeid
     * @param method
     * @return
     * @throws Exception
     */
    public String sell(int coinType, String price, String amount, String tradePassword, Integer tradeid, String method)
            throws Exception {
    	Base base = new Base();
        TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
        paraMap.put("method", method);
        paraMap.put("created", base.getTimestamp());
        paraMap.put("access_key", Base.HUOBI_ACCESS_KEY);
        paraMap.put("secret_key", Base.HUOBI_SECRET_KEY);
        paraMap.put("coin_type", coinType);
        paraMap.put("price", price);
        paraMap.put("amount", amount);
        String md5 = base.sign(paraMap);
        paraMap.remove("secret_key");
        paraMap.put("sign", md5);
        if (StringUtils.isNotEmpty(tradePassword)) {
            paraMap.put("trade_password", tradePassword);
        }
        if (null != tradeid) {
            paraMap.put("trade_id", tradeid);
        }
        return base.post(paraMap, Base.HUOBI_API_URL);
    }

    /**
     * 市价卖出
     * 
     * @param coinType
     * @param amount
     * @param tradePassword
     * @param tradeid
     * @param method
     * @return
     * @throws Exception
     */
    public String sellMarket(int coinType, String amount, String tradePassword, Integer tradeid, String method)
            throws Exception {
    	Base base = new Base();
        TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
        paraMap.put("method", method);
        paraMap.put("created", base.getTimestamp());
        paraMap.put("access_key", Base.HUOBI_ACCESS_KEY);
        paraMap.put("secret_key", Base.HUOBI_SECRET_KEY);
        paraMap.put("coin_type", coinType);
        paraMap.put("amount", amount);
        String md5 = base.sign(paraMap);
        paraMap.remove("secret_key");
        paraMap.put("sign", md5);
        if (StringUtils.isNotEmpty(tradePassword)) {
            paraMap.put("trade_password", tradePassword);
        }
        if (null != tradeid) {
            paraMap.put("trade_id", tradeid);
        }
        return base.post(paraMap, Base.HUOBI_API_URL);
    }
    
    /**
     * 提币
     * 
     * @param coinType
     * @param amount
     * @param address
     * @param coinType
     * @param method
     * 
     * @return
     */
    
    public String withDraw_coin(int coinType, String address, String amount, String tradePassword,String method)
			throws Exception{
		TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
		Base base = new Base();
		paraMap.put("method", method);
		paraMap.put("created", base.getTimestamp());
		paraMap.put("access_key", Base.HUOBI_ACCESS_KEY);
        paraMap.put("secret_key", Base.HUOBI_SECRET_KEY);
		paraMap.put("coin_type", coinType);
		paraMap.put("withdraw_address", address);
		paraMap.put("withdraw_amount", amount);
		String md5 = base.sign(paraMap);
		paraMap.remove("secret_key");
		paraMap.put("sign",md5);
		if(StringUtils.isNotEmpty(tradePassword)){
			paraMap.put("trade_password",tradePassword);
		}
		return base.post(paraMap,Base.HUOBI_API_URL);
    }
}
