package platform.OKcoinHuobi.OKcoin.Stock;

import java.io.IOException;

import org.apache.http.HttpException;

public class StockClient {
	
	public static void main(String[] args) throws HttpException, IOException{

	    /**
	     * get请求无需发送身份认证,通常用于获取行情，市场深度等公共信息
	     * 
	    */
	    IStockApi stockGet = new StockApi();
		
	    /**
	     * post请求需发送身份认证，获取用户个人相关信息时，需要指定api_key,与secret_key并与参数进行签名，
	     * 此处对构造方法传入api_key与secret_key,在请求用户相关方法时则无需再传入，
	     * 发送post请求之前，程序会做自动加密，生成签名。
	     * 
	    */

	    //现货行情
	    stockGet.ticker("btc_cny");
	    System.out.println(stockGet.ticker("btc_cny"));
	    System.out.println(stockGet.ticker("ltc_cny"));

        //现货市场深度
        String depth = stockGet.depth("ltc_cny");
        System.out.println(depth);
		
        //现货OKCoin历史交易信息
        //stockGet.trades("btc_cny", "20");
		
	    //现货用户信息
	    //System.out.println(stockPost.userinfo());
		
	    //现货下单交易
	    //String tradeResult = stockPost.trade("btc_cny", "buy", "50", "0.01");
	    //System.out.println(tradeResult);
	    //JSONObject tradeJSV1 = JSONObject.parseObject(tradeResult);
	    //String tradeOrderV1 = tradeJSV1.getString("order_id");

	    //现货获取用户订单信息
        //stockPost.order_info("btc_cny", tradeOrderV1);
		
	    //现货撤销订单
	    //stockPost.cancel_order("btc_cny", tradeOrderV1);
		
	    //现货批量下单
	    //stockPost.batch_trade("btc_cny", "buy", "[{price:50, amount:0.02},{price:50, amount:0.03}]");

	    //批量获取用户订单
	    //stockPost.orders_info("0", "btc_cny", "125420341, 125420342");
		
	    //获取用户历史订单信息，只返回最近七天的信息
	    //stockPost.order_history("btc_cny", "0", "1", "20");

	}
}
