package OKcoinHuobi;



import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpException;

import com.shanghai.stock.OKcoinHuobi.Huobi.Stock.GetRest;
import com.shanghai.stock.OKcoinHuobi.Huobi.Stock.HuobiService;
import com.shanghai.stock.OKcoinHuobi.Huobi.Util.HuobiRealTimeData;
import com.shanghai.stock.OKcoinHuobi.OKcoin.Stock.IStockApi;
import com.shanghai.stock.OKcoinHuobi.OKcoin.Stock.StockApi;
import com.shanghai.stock.OKcoinHuobi.OKcoin.Util.ChangeString;
import com.shanghai.stock.OKcoinHuobi.OKcoin.Util.OKCoinRealTimeData;

public class CatchInfo implements Runnable{
	//okcoin相关字符串
	private static String api_key = "bce9358d-54e9-4cfb-b22d-3546e10e7dc1";  //OKCoin申请的apiKey
	private static String secret_key = "6641776BE6A7E7EEAC1D710569F3B0D2";  //OKCoin 申请的secret_key
	private static String url_prex = "https://www.okcoin.cn";
	
	//
	
	//火币相关字符串
	/*private static String BUY = "buy";
    private static String BUY_MARKET = "buy_market";
    private static String CANCEL_ORDER = "cancel_order";
    */
    private static String ACCOUNT_INFO = "get_account_info";
    /*
    private static String NEW_DEAL_ORDERS = "get_new_deal_orders";
    private static String ORDER_ID_BY_TRADE_ID = "get_order_id_by_trade_id";
    private static String GET_ORDERS = "get_orders";
    private static String ORDER_INFO = "order_info";
    private static String SELL = "sell";
    private static String SELL_MARKET = "sell_market";
	*/
	//
    HuobiService huobiService = new HuobiService();
	StockApi okcoinGet = new StockApi(url_prex);
	StockApi okcoinPost = new StockApi(url_prex, api_key, secret_key);
	GetRest getRest = new GetRest();
	ChangeString changeString = new ChangeString();
     

	public static void main(String[] args) {
		
		CatchInfo catchInfo = new CatchInfo();
		
		catchInfo.run();
	

	}

	public void run() {
		int i = 0;
		IStockApi okcoinGet = new StockApi(url_prex);
		IStockApi okcoinPost = new StockApi(url_prex, api_key, secret_key);
		GetRest getRest = new GetRest();
		HuobiService service = new HuobiService();
		HuobiRealTimeData HuoData = new HuobiRealTimeData();
		OKCoinRealTimeData OkCoinData = new OKCoinRealTimeData();
		
		//for (int i = 0; i < 100; i++) {
		while(true){
				
				try {
					String HuoLastPrice = ChangeString.HuoJ2O(getRest.GetString());
					String OKCoinLastPrice = ChangeString.OKCJ2O(okcoinGet.ticker("btc_cny"));
					double diff = Double.valueOf(OKCoinLastPrice)-Double.valueOf(HuoLastPrice);
					double bizhi = Double.valueOf(OKCoinLastPrice)/Double.valueOf(HuoLastPrice);
					Date date = new Date();
					System.out.println(date.toString());
					System.out.println(i++);
					System.out.println("火币交易所比特币价格:" + HuoLastPrice);
					System.out.println("OKCoin交易所比特币价格:" + OKCoinLastPrice);
					System.out.println("OKCoin-火币价差:  " + diff);
					System.out.println("OK币/火币："+bizhi);
					System.out.println("**********************");
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (HttpException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
				try {
					Date date1 = new Date();
					System.out.println(date1.toString());
					String OkAccountInfo = okcoinPost.userinfo();
					String HuoAccountInfo = service.getAccountInfo(ACCOUNT_INFO);
					HuoData = ChangeString.getHuoAsset(HuoAccountInfo);
					OkCoinData = ChangeString.getOKAsset(OkAccountInfo);
					System.out.println("火币账户总资产： "+HuoData.total);
					System.out.println("火币账户净资产： "+HuoData.net);
					System.out.println("火币账户可用人民币： "+HuoData.free_cny);
					System.out.println("火币账户可用btc： "+HuoData.free_btc);
					System.out.println("火币账户可用ltc： "+HuoData.free_ltc);
					System.out.println("OK账户总资产： "+OkCoinData.total);
					System.out.println("OK账户净资产： "+OkCoinData.net);
					System.out.println("OK账户可用人民币： "+OkCoinData.free_cny);
					System.out.println("OK账户可用btc： "+OkCoinData.free_btc);
					System.out.println("OK账户可用ltc： "+OkCoinData.free_ltc);
					System.out.println("**********************");
				} catch (Exception e) {
					// TODO: handle exception
				}try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		//}
		}
		
	}

}
