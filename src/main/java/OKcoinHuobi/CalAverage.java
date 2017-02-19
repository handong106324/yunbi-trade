package OKcoinHuobi;

import java.io.IOException;

import org.apache.http.HttpException;

import com.shanghai.stock.OKcoinHuobi.Huobi.Stock.GetRest;
import com.shanghai.stock.OKcoinHuobi.Huobi.Stock.HuobiService;
import com.shanghai.stock.OKcoinHuobi.OKcoin.Stock.IStockApi;
import com.shanghai.stock.OKcoinHuobi.OKcoin.Stock.StockApi;
import com.shanghai.stock.OKcoinHuobi.OKcoin.Util.ChangeString;

public class CalAverage implements Runnable {
	
	private static String API_KEY = "7b4de9db-0927-4fd7-806b-a1c71c480218";  //OKCoin申请的apiKey
   	private static String SECRET_KEY = "9D1EFE43F30E83CDFA79670F79120DD2";  //OKCoin 申请的secret_key
	private static String URL_PREX = "https://www.okcoin.cn";
	
	

	public void run() {
		IStockApi okcoinGet = new StockApi(URL_PREX);
		IStockApi okcoinPost = new StockApi(URL_PREX, API_KEY, SECRET_KEY);
		GetRest getRest = new GetRest();
		HuobiService service = new HuobiService();
		for (int i = 0; i < 200; i++) {
			String HuoLastPrice = null;
			try {
				HuoLastPrice = ChangeString.HuoJ2O(getRest.GetString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String OKCoinLastPrice = null;
			try {
				OKCoinLastPrice = ChangeString.OKCJ2O(okcoinGet.ticker("btc_cny"));
			} catch (HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			double diff = Double.valueOf(OKCoinLastPrice)-Double.valueOf(HuoLastPrice);
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
