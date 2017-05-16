package OKcoinHuobi;

import java.io.IOException;

import OKcoinHuobi.Huobi.Stock.GetRest;
import OKcoinHuobi.Huobi.Stock.HuobiService;
import OKcoinHuobi.OKcoin.Stock.IStockApi;
import OKcoinHuobi.OKcoin.Stock.StockApi;
import OKcoinHuobi.OKcoin.Util.ChangeString;
import org.apache.http.HttpException;

public class CalAverage implements Runnable {

	public void run() {
		IStockApi okcoinGet = new StockApi();
//		IStockApi okcoinPost = new StockApi(URL_PREX, API_KEY, SECRET_KEY);
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
