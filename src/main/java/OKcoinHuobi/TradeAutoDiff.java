
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

public class TradeAutoDiff implements Runnable{
 
	private static float COINPERTIME = (float) 0.2;
	private static float LTCPERTIME = 20;
	private static float MINCASH = 3000;
	private static float MINBTCLEFT = 1;
	private static float BALANCETRIGGER = (float) 0.1;
	private static float LTCBALANCETRIGGER = 5;
	//private static float MINLTCLEFT = 0;
	//private static float CONSTBTC = (float) 0.2;
	//private static float CONSTLTC = 30;
	//private static float MINDIFF = (float) 0.5;//
	private static float INITLTC = 3000;
	private static float INITBTC = (float) 132.64;
	private static float INITCNY = (float) 1941841.59;
 
	//private float okCoinBtc;
	//private float okCoinLtc;
	//private float okCoinCash;
	//private float huoBiBtc;
	//private float huoBiLtc;
	//private float huoBiCash;
 
	//private float[] coinBtcDiff; //OkCoin-HuoBi
	//private float[] coinLtcDiff; //OkCoin-HuoBi
 
	private static String API_KEY = "bce9358d-54e9-4cfb-b22d-3546e10e7dc1";  //OKCoin申请的apiKey
	private static String SECRET_KEY = "6641776BE6A7E7EEAC1D710569F3B0D2";  //OKCoin 申请的secret_key
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
    //private static String SELL_MARKET = "sell_market";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TradeAutoDiff tAutoDiff = new TradeAutoDiff();
		tAutoDiff.run();
	
	}

	public void run() {
		 // TODO Auto-generated method stub
		 IStockApi okcoinGet = new StockApi(URL_PREX);
		 IStockApi okcoinPost = new StockApi(URL_PREX, API_KEY, SECRET_KEY);
		 GetRest getRest = new GetRest();
		 HuobiService service = new HuobiService();
		 HuobiRealTimeData HuoData = new HuobiRealTimeData();
		 OKCoinRealTimeData OkCoinData = new OKCoinRealTimeData();
		 float freeBTC = 0;
		 float freeLTC = 0;
		 int errors = 0;
		 int bigDiffTimes=0;
		 int normalDiffTimes=0;
		 int smallDiffTimes=0;
		 int tradesLTC = 0;
	  
	 while(true){
		 Date date = new Date();
		 float diff = 0;
		 float diffLTC = 0;
		 String HuoBTCLastPrice = null;
		 String OKCoinBTCLastPrice = null;
		 String HuoLTCLastPrice = null;
		 String OKCoinLTCLastPrice = null;
		 String OkAccountInfo = null;
		 String HuoAccountInfo = null;
		 float ratioBtc = 0;
		 float ratioLtc = 0;
   
	  try {
		  OkAccountInfo = okcoinPost.userinfo();
		  HuoAccountInfo = service.getAccountInfo(ACCOUNT_INFO);
		  HuoData = ChangeString.getHuoAsset(HuoAccountInfo);//获取huobi的账户信息类对象
		  OkCoinData = ChangeString.getOKAsset(OkAccountInfo);//获取OK的账户信息类对象
		  Thread.sleep(100);
		  } catch (Exception e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
	   }
   
	  try {
		  HuoBTCLastPrice = ChangeString.HuoJ2O(getRest.GetString());
		  HuoLTCLastPrice = ChangeString.HuoJ2Ltc(getRest.GetLtcString());
		  OKCoinBTCLastPrice = ChangeString.OKCJ2O(okcoinGet.ticker("btc_cny"));
		  OKCoinLTCLastPrice = ChangeString.OKCLTCJ2O(okcoinGet.ticker("ltc_cny"));
		  System.out.println(date);
		  ratioBtc = Float.valueOf(OKCoinBTCLastPrice)/Float.valueOf(HuoBTCLastPrice);
		  ratioLtc = Float.valueOf(OKCoinLTCLastPrice)/Float.valueOf(HuoLTCLastPrice);
		  diff = Float.valueOf(OKCoinBTCLastPrice)-Float.valueOf(HuoBTCLastPrice);
		  diffLTC = Float.valueOf(OKCoinLTCLastPrice)-Float.valueOf(HuoLTCLastPrice);
		  System.out.println("OKCoin-HB差价： "+ diff);
		  System.out.println("OKCoin-HB的LTC差价： "+ diffLTC);
		  float increCNY = OkCoinData.free_cny+HuoData.free_cny-INITCNY;
		  float increBTC = OkCoinData.free_btc+HuoData.free_btc-INITBTC;
		  float increLTC = OkCoinData.free_ltc+HuoData.free_ltc-INITLTC;
		  float increTotal = increCNY + increBTC*(Float.valueOf(OKCoinBTCLastPrice)+Float.valueOf(HuoBTCLastPrice))/2
				  +increLTC*(Float.valueOf(OKCoinLTCLastPrice)+Float.valueOf(HuoLTCLastPrice))/2;
		  System.out.println("人民币增量："+ increCNY);
		  System.out.println("BTC增量： "+ increBTC);
		  System.out.println("LTC增量： "+ increLTC);
 
		  //支持多种转币额度
		  if (OkCoinData.free_btc+HuoData.free_btc>130) {
			  freeBTC = INITBTC;
		  }else if (OkCoinData.free_btc+HuoData.free_btc>110 && 
		      OkCoinData.free_btc+HuoData.free_btc<=130) {
			  freeBTC = INITBTC-20;
		  }else if (OkCoinData.free_btc+HuoData.free_btc>90 && 
		      OkCoinData.free_btc+HuoData.free_btc<=110) {
			  freeBTC = INITBTC-40;
		  }else if (OkCoinData.free_btc+HuoData.free_btc>70 && 
		      OkCoinData.free_btc+HuoData.free_btc<=90) {
			  freeBTC = INITBTC-60;
		  }else if (OkCoinData.free_btc+HuoData.free_btc>50 && 
		      OkCoinData.free_btc+HuoData.free_btc<=70) {
			  freeBTC = INITBTC-80;
		  }else if (OkCoinData.free_btc+HuoData.free_btc>30 && 
		      OkCoinData.free_btc+HuoData.free_btc<=50) {
			  freeBTC = INITBTC-100;
		  }else if (OkCoinData.free_btc+HuoData.free_btc>30 && 
		      OkCoinData.free_btc+HuoData.free_btc<=50) {
			  freeBTC = INITBTC-120;
		  }
		  //多种额度转移LTC  略
	
    
  
		    //配平，使得两个账户的BTC总数不变
		    if (freeBTC-OkCoinData.free_btc-HuoData.free_btc>BALANCETRIGGER) {
		    	//System.out.println((OkCoinData.free_btc+HuoData.free_btc));
		    	//int temp =Math.round((freeBTC-OkCoinData.free_btc-HuoData.free_btc)/BALANCETRIGGER);
		    	//System.out.println(temp);
		    	errors++;
			     if (diff>=0) {
			    	 System.out.println(buyBtcAtHuoBi(service, twopoint(Float.valueOf(HuoBTCLastPrice)+30),twopoint(BALANCETRIGGER))); 
			     }
			     if (diff<0) {
			    	 System.out.println(buyBtcAtOkCoin(okcoinPost, twopoint(Float.valueOf(OKCoinBTCLastPrice)+30), twopoint(BALANCETRIGGER)));
			     }
		    }else if (freeBTC-OkCoinData.free_btc-HuoData.free_btc< -BALANCETRIGGER) {
		     //int temp = -Math.round((freeBTC-OkCoinData.free_btc-HuoData.free_btc)/BALANCETRIGGER);
			     errors++;
			     if (diff>=0) {
			    	 System.out.println(sellBtcAtOkCoin(okcoinPost, twopoint(Float.valueOf(OKCoinBTCLastPrice)-30), twopoint(BALANCETRIGGER)));
			     }
			     if (diff<0) {
			    	 System.out.println(sellBtcAtHuoBi(service, twopoint(Float.valueOf(HuoBTCLastPrice)-30),twopoint(BALANCETRIGGER)));
			     }
		    }
		    
		    if(OkCoinData.free_ltc+HuoData.free_ltc>=2900){
		    	freeLTC = INITLTC;
		    }else if (OkCoinData.free_ltc+HuoData.free_ltc>0 && OkCoinData.free_ltc+HuoData.free_ltc<300) {
				freeLTC = INITLTC-2800;
			}
		    if (freeLTC-OkCoinData.free_ltc-HuoData.free_ltc>LTCBALANCETRIGGER) {
				if (diffLTC>=0) {
					System.out.println(buyLtcAtHuoBi(service, twopoint(Float.valueOf(HuoLTCLastPrice)+1), twopoint(LTCBALANCETRIGGER)));
				}
				if (diffLTC<0) {
					System.out.println(buyLtcAtOkCoin(okcoinPost, twopoint(Float.valueOf(OKCoinLTCLastPrice)+1), twopoint(LTCBALANCETRIGGER)));
				}
			}else if (freeLTC-OkCoinData.free_ltc-HuoData.free_ltc<-LTCBALANCETRIGGER) {
				if (diffLTC>=0) {
					System.out.println(sellLtcAtOkCoin(okcoinPost, twopoint(Float.valueOf(OKCoinLTCLastPrice)+1), twopoint(LTCBALANCETRIGGER)));
				}
				if (diffLTC<0) {
					System.out.println(sellLtcAtHuoBi(service, twopoint(Float.valueOf(HuoLTCLastPrice)+1), twopoint(LTCBALANCETRIGGER)));
				}
			}
		    
		    float ratioOKCash =  OkCoinData.free_cny/(OkCoinData.free_cny+HuoData.free_cny);
		    //realtime diff
		    //Shou 调整了参数，更符合实际16.7.8
		    float realTimeDiff = 0;
		    if (ratioOKCash>0.875) {
		    	realTimeDiff = (float) 2.8;
		    }else if (ratioOKCash>0.75) {
		    	realTimeDiff = (float) 2.2;
		    }else if (ratioOKCash>0.625) {
		    	realTimeDiff = (float) 1.2;
		    }else if (ratioOKCash<0.125) {
		    	realTimeDiff = (float) -2.8;
		    }else if (ratioOKCash<0.25) {
		    	realTimeDiff = (float) -2.3;
		    }else if (ratioOKCash<0.4) {
		    	realTimeDiff = (float) -1.3;
		    }else {
		    	realTimeDiff = 0;
		    }
	    //(6.2,0.2)170(5.2,-0.8)145(4.2,-1.8)121(3,-3)72(1.8,-4.2)50(0.8,-5.2)24(-0.2,-6.2)
	    //HY 2016/7/9  dan ci jiao yi e ti sheng
		//Shou.16.7.8 start 0.7bei jia cha jiao yi 0.04 btc    
		    if((diff>2*(3+realTimeDiff)) && OkCoinData.free_btc>MINBTCLEFT && HuoData.free_cny>MINCASH) {
		    	System.out.println(sellBtcAtOkCoin(okcoinPost, twopoint(Float.valueOf(OKCoinBTCLastPrice)-30), COINPERTIME*2));
		    	System.out.println(buyBtcAtHuoBi(service, twopoint(Float.valueOf(HuoBTCLastPrice)+30), COINPERTIME*2));
		    	bigDiffTimes++;
		    }else if((diff>3+realTimeDiff) && OkCoinData.free_btc>MINBTCLEFT && HuoData.free_cny>MINCASH) {
		    	System.out.println(sellBtcAtOkCoin(okcoinPost, twopoint(Float.valueOf(OKCoinBTCLastPrice)-30), COINPERTIME));
		    	System.out.println(buyBtcAtHuoBi(service, twopoint(Float.valueOf(HuoBTCLastPrice)+30), COINPERTIME));
		    	normalDiffTimes++;
		    }else if((diff>0.7*(3+realTimeDiff)) && OkCoinData.free_btc>MINBTCLEFT && HuoData.free_cny>MINCASH) {
		    	System.out.println(sellBtcAtOkCoin(okcoinPost, twopoint(Float.valueOf(OKCoinBTCLastPrice)-30), COINPERTIME/5));
		    	System.out.println(buyBtcAtHuoBi(service, twopoint(Float.valueOf(HuoBTCLastPrice)+30), COINPERTIME/5));
		    	smallDiffTimes++;
		    }

		    if((diff<2*(-3+realTimeDiff))&& HuoData.free_btc>MINBTCLEFT && OkCoinData.free_cny>MINCASH) {
		    	System.out.println(buyBtcAtOkCoin(okcoinPost, twopoint(Float.valueOf(OKCoinBTCLastPrice)+30), COINPERTIME*2));
		    	System.out.println(sellBtcAtHuoBi(service, twopoint(Float.valueOf(HuoBTCLastPrice)-30), COINPERTIME*2));
		    	bigDiffTimes++;
		    }else if((diff<-3+realTimeDiff)&& HuoData.free_btc>MINBTCLEFT && OkCoinData.free_cny>MINCASH) {
		    	System.out.println(buyBtcAtOkCoin(okcoinPost, twopoint(Float.valueOf(OKCoinBTCLastPrice)+30), COINPERTIME));
		    	System.out.println(sellBtcAtHuoBi(service, twopoint(Float.valueOf(HuoBTCLastPrice)-30), COINPERTIME));
		    	normalDiffTimes++;
		    }else if ((diff<0.7*(-3+realTimeDiff))&& HuoData.free_btc>MINBTCLEFT && OkCoinData.free_cny>MINCASH) {
		    	System.out.println(buyBtcAtOkCoin(okcoinPost, twopoint(Float.valueOf(OKCoinBTCLastPrice)+30), COINPERTIME/5));
		    	System.out.println(sellBtcAtHuoBi(service, twopoint(Float.valueOf(HuoBTCLastPrice)-30), COINPERTIME/5));
		    	smallDiffTimes++;
		    }
		if (diffLTC>=0.03 && OkCoinData.free_ltc>0 && HuoData.free_cny>0) {
			sellLtcAtOkCoin(okcoinPost, twopoint(Float.valueOf(OKCoinLTCLastPrice)), LTCPERTIME);
			buyLtcAtHuoBi(service, twopoint(Float.valueOf(HuoLTCLastPrice)), LTCPERTIME);
			tradesLTC++;
		}else if (diffLTC<=-0.03 && HuoData.free_ltc>0 && OkCoinData.free_cny>0) {
			buyLtcAtOkCoin(okcoinPost, twopoint(Float.valueOf(OKCoinLTCLastPrice)), LTCPERTIME);
			sellLtcAtHuoBi(service, twopoint(Float.valueOf(HuoLTCLastPrice)), LTCPERTIME);
			tradesLTC++;
			
		}
	    float bigDiffa = (float)2*(-3+realTimeDiff);
	    float bigDiffb = (float)2*(3+realTimeDiff);
	    float smallDiffa = (float)0.7 * (-3+realTimeDiff);
	    float smallDiffb = (float)0.7 * (3+realTimeDiff);
	    System.out.println("LTC交易次数: "+tradesLTC);
	    System.out.println("diff:"+ normalDiffTimes+ "  diff*0.7:"+smallDiffTimes+ "  diff*2:"+bigDiffTimes+ "  0.7diff:("+smallDiffa+","+smallDiffb+ ")  2diff:("+bigDiffa+","+bigDiffb+")");
	    //Shou 16.7.8   end
		System.out.println("总增量： "+ increTotal);
		System.out.println("================");
		Thread.sleep(200);
	   } catch (Exception e) {
	    // TODO: handle exception
	   }
	   
	  }
	 }
	 
	 public float twopoint(float f){
		 return (float)(Math.round(f*100))/100;
	 }
	 public String buyBtcAtHuoBi(HuobiService service,float price,float amount) throws Exception{
		 return service.buy(1, String.valueOf(price), String.valueOf(amount), null, null, BUY); 
	 }
	 public String buyLtcAtHuoBi(HuobiService service,float price,float amount) throws Exception {
		 return service.buy(2, String.valueOf(price), String.valueOf(amount), null, null, BUY);
	 }
	 
	 public String buyBtcAtOkCoin(IStockApi stockPost,float price,float amount) throws HttpException, IOException{
		 return stockPost.trade("btc_cny", BUY, String.valueOf(price), String.valueOf(amount));
	 }
	 
	 public String buyLtcAtOkCoin(IStockApi stockPost,float price,float amount) throws HttpException, IOException{
		 return stockPost.trade("ltc_cny", BUY, String.valueOf(price), String.valueOf(amount));
	 }
	 
	 public String sellBtcAtHuoBi(HuobiService service,float price,float amount) throws Exception{
		 return service.sell(1, String.valueOf(price), String.valueOf(amount), null, null, SELL); 
	 }
	 
	 public String sellLtcAtHuoBi(HuobiService service,float price,float amount) throws Exception{
		 return service.sell(2, String.valueOf(price), String.valueOf(amount), null, null, SELL); 
	 }
	 
	 public String sellBtcAtOkCoin(IStockApi stockPost,float price,float amount) throws HttpException, IOException{
		 return stockPost.trade("btc_cny", SELL, String.valueOf(price), String.valueOf(amount));
	 }
	 
	 public String sellLtcAtOkCoin(IStockApi stockPost,float price,float amount) throws HttpException, IOException{
		 return stockPost.trade("ltc_cny", SELL, String.valueOf(price), String.valueOf(amount));
	 }

}

 