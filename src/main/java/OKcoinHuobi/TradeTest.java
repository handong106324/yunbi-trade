package OKcoinHuobi;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.log4j.PropertyConfigurator;

import com.shanghai.stock.OKcoinHuobi.Huobi.Stock.GetRest;
import com.shanghai.stock.OKcoinHuobi.Huobi.Stock.HuobiService;
import com.shanghai.stock.OKcoinHuobi.Huobi.Util.HuobiRealTimeData;
import com.shanghai.stock.OKcoinHuobi.OKcoin.Stock.IStockApi;
import com.shanghai.stock.OKcoinHuobi.OKcoin.Stock.StockApi;
import com.shanghai.stock.OKcoinHuobi.OKcoin.Util.ChangeString;
import com.shanghai.stock.OKcoinHuobi.OKcoin.Util.OKCoinRealTimeData;

public class TradeTest implements Runnable {
	
	private static float COINPERTIME = (float) 0.1;
	private static float MINCASH = 3000;
	private static float MINBTCLEFT = 1;
	private static float BALANCETRIGGER = (float) 0.2;
	//private static float MINLTCLEFT = 0;
	//private static float CONSTBTC = (float) 0.2;
	//private static float CONSTLTC = 30;
	private static float MINDIFF = (float) 0.5;//
	private static float INITBTC = (float) 132.64;
	
	//private float okCoinBtc;
	//private float okCoinLtc;
	//private float okCoinCash;
	//private float huoBiBtc;
	//private float huoBiLtc;
	//private float huoBiCash;
	
	//private float[] coinBtcDiff; //OkCoin-HuoBi
	//private float[] coinLtcDiff; //OkCoin-HuoBi
	
	private static String API_KEY = "7b4de9db-0927-4fd7-806b-a1c71c480218";  //OKCoin申请的apiKey
   	private static String SECRET_KEY = "9D1EFE43F30E83CDFA79670F79120DD2";  //OKCoin 申请的secret_key
	private static String URL_PREX = "https://www.okcoin.cn";
	
	private static String BUY = "buy";
    //private static String BUY_MARKET = "buy_market";
    //private static String CANCEL_ORDER = "cancel_order";
    private static String ACCOUNT_INFO = "get_account_info";
    //private static String NEW_DEAL_ORDERS = "get_new_deal_orders";
    //private static String ORDER_ID_BY_TRADE_ID = "get_order_id_by_trade_id";
    //private static String GET_ORDERS = "get_orders";
    //private static String ORDER_INFO = "order_info";
    private static String SELL = "sell";
    private static String WITHDRAW = "withdraw_coin";
    //private static String SELL_MARKET = "sell_market";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PropertyConfigurator.configure("log4j.properties");
		TradeTest tradeTest = new TradeTest();
		tradeTest.run();
	}


	public void run() {
		PropertyConfigurator.configure("log4j.properties");
		int countTrade = 0;
		int errors = 0;
		// TODO Auto-generated method stub
		IStockApi okcoinGet = new StockApi(URL_PREX);
		IStockApi okcoinPost = new StockApi(URL_PREX, API_KEY, SECRET_KEY);
		GetRest getRest = new GetRest();
		HuobiService service = new HuobiService();
		HuobiRealTimeData HuoData = new HuobiRealTimeData();
		OKCoinRealTimeData OkCoinData = new OKCoinRealTimeData();
		/*float diffs[] = null;
		for (int i = 0; i < 100; i++) {
			try {
				System.out.println(i);
				String HuoLastPrice = ChangeString.HuoJ2O(getRest.GetString());
				String OKCoinLastPrice = ChangeString.OKCJ2O(okcoinGet.ticker("btc_cny"));
				float diff = Float.valueOf(OKCoinLastPrice)-Float.valueOf(HuoLastPrice);
				diffs[i] = diff;
			} catch (Exception e) {
				// TODO: handle exception
			}
		    try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		//float average = allDiffAverage(diffs);
		float average = 2;///test
		while(true){
			float diff = 0;
			String HuoLastPrice = null;
			String OKCoinLastPrice = null;
			String OkAccountInfo = null;
			String HuoAccountInfo = null;
			
			try {
				OkAccountInfo = okcoinPost.userinfo();
				//System.out.println(OkAccountInfo);
				System.out.println("countTrade: "+countTrade);
				HuoAccountInfo = service.getAccountInfo(ACCOUNT_INFO);
				HuoData = ChangeString.getHuoAsset(HuoAccountInfo);
				//System.out.println(-0.15);
				OkCoinData = ChangeString.getOKAsset(OkAccountInfo);
				Thread.sleep(200);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				HuoLastPrice = ChangeString.HuoJ2O(getRest.GetString());
				OKCoinLastPrice = ChangeString.OKCJ2O(okcoinGet.ticker("btc_cny"));
				diff = Float.valueOf(OKCoinLastPrice)-Float.valueOf(HuoLastPrice);
				//System.out.println("HB最新比特币价格："+HuoLastPrice);
				//System.out.println("OKCoin最新比特币价格"+OKCoinLastPrice);
				System.out.println("OKCoin-HB差价:"+diff);
				//System.out.println(-0.45);
			    //System.out.println("diff"+diff);
				//System.out.println("average"+average);
			    //System.out.println("MINDIFF"+MINDIFF);
				//if (Math.abs(diff)>=average && Math.abs(diff)>=MINDIFF) {
				//System.out.println(-0.1);
				//Thread.sleep(1000);
				if (errors>=20) {
					System.out.println("System error!");
					Thread.interrupted();
				}
				if (INITBTC - OkCoinData.free_btc-HuoData.free_btc>BALANCETRIGGER*2) {
					if (diff>0) {
						errors++;
						System.out.println(buyBtcAtHuoBi(service, twopoint(Float.valueOf(HuoLastPrice)+30), BALANCETRIGGER));
						countTrade++;
					}
					if (diff<0) {
						errors++;
						System.out.println(buyBtcAtOkCoin(okcoinPost, twopoint(Float.valueOf(OKCoinLastPrice)+30), BALANCETRIGGER));
						countTrade++;
					}
				}else if (INITBTC - OkCoinData.free_btc-HuoData.free_btc< -BALANCETRIGGER*2) {
					if (diff>0) {
						errors++;
						System.out.println(sellBtcAtOkCoin(okcoinPost, twopoint(Float.valueOf(OKCoinLastPrice)-30), BALANCETRIGGER));
						countTrade++;
					}
					if (diff<0) {
						errors++;
						System.out.println(sellBtcAtHuoBi(service, twopoint(Float.valueOf(HuoLastPrice)-30), BALANCETRIGGER));
						countTrade++;
					}
				}
				
			    //Thread.sleep(100);
				
				if ((diff >= average || diff<= -average) && (diff >= MINDIFF ||diff <= MINDIFF)){
					System.out.println(0);
					if (diff>0 && OkCoinData.free_btc>MINBTCLEFT && HuoData.free_cny>MINCASH) {
						System.out.println(0.1);
						//System.out.println("HB买入："+twopoint(Float.valueOf(HuoLastPrice)+diff/2));
						//System.out.println("OK卖出："+twopoint(Float.valueOf(OKCoinLastPrice)-diff/2));
						System.out.println(buyBtcAtHuoBi(service, twopoint(Float.valueOf(HuoLastPrice)+30), COINPERTIME));
						System.out.println(sellBtcAtOkCoin(okcoinPost, twopoint(Float.valueOf(OKCoinLastPrice)-30), COINPERTIME));
						countTrade++;
					}
					if (diff<0 && HuoData.free_btc>MINBTCLEFT && OkCoinData.free_cny>MINCASH) {
						System.out.println(0.2);
						//System.out.println("HB卖出："+twopoint(Float.valueOf(HuoLastPrice)+diff/2));
						//System.out.println("OK买入："+twopoint(Float.valueOf(OKCoinLastPrice)-diff/2));
						System.out.println(buyBtcAtOkCoin(okcoinPost, twopoint(Float.valueOf(OKCoinLastPrice)+30), COINPERTIME));
						System.out.println(sellBtcAtHuoBi(service, twopoint(Float.valueOf(HuoLastPrice)-30), COINPERTIME));
						countTrade++;
					}
				}
				/*else {
					//System.out.println(1);
				}*/
			
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				Thread.sleep(200);
			} catch (Exception e) {
				// TODO: handle exception
			}	
		}	
	}
	
	public float allDiffAverage(float[] coinDiff){
		float average = 0;
		float sum = 0;
		for (int i = 0; i < coinDiff.length; i++) {
			sum = sum + Math.abs(coinDiff[i]);
		}
		average = sum/coinDiff.length;
		return (float) (average*1.2);
	}
	public float twopoint(float f){
		return (float)(Math.round(f*100))/100;
	}
	public String buyBtcAtHuoBi(HuobiService service,float price,float amount) throws Exception{
		return service.buy(1, String.valueOf(price), String.valueOf(amount), null, null, BUY);	
	}
	
	public String buyBtcAtOkCoin(IStockApi stockPost,float price,float amount) throws HttpException, IOException{
		return stockPost.trade("btc_cny", BUY, String.valueOf(price), String.valueOf(amount));
	}
	
	public String sellBtcAtHuoBi(HuobiService service,float price,float amount) throws Exception{
		return service.sell(1, String.valueOf(price), String.valueOf(amount), null, null, SELL);	
	}
	
	public String sellBtcAtOkCoin(IStockApi stockPost,float price,float amount) throws HttpException, IOException{
		return stockPost.trade("btc_cny", SELL, String.valueOf(price), String.valueOf(amount));
	}
	

}
