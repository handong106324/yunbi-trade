package OKcoinHuobi.OKcoin.Util;

import org.json.JSONObject;

import com.shanghai.stock.OKcoinHuobi.Huobi.Stock.GetRest;
import com.shanghai.stock.OKcoinHuobi.Huobi.Stock.HuobiService;
import com.shanghai.stock.OKcoinHuobi.Huobi.Util.HuobiRealTimeData;
import com.shanghai.stock.OKcoinHuobi.OKcoin.Stock.IStockApi;
import com.shanghai.stock.OKcoinHuobi.OKcoin.Stock.StockApi;




public class ChangeString {
	private static String url_prex = "https://www.okcoin.cn";
	private static String api_key = "52612f43-9b250482-71090eca-af5e9";  //OKCoin申请的apiKey
   	private static String secret_key = "b5c50526-dcd49dd6-5b3f5a46-1832b";  //OKCoin 申请的secret_key
	//private String url_prex = "https://www.okcoin.cn";  //注意：请求URL 国际站https://www.okcoin.com ; 国内站https://www.okcoin.cn
   	private static String ACCOUNT_INFO = "get_account_info";
   	public static String HuoJ2O(String jsonString){
		JSONObject jsonObject = new JSONObject(jsonString);
		String p_new = String.valueOf(jsonObject.get("p_new"));
		return p_new;    	
	} 
	
	public static String HuoJ2Ltc(String jsonString){
		JSONObject jsonObject = new JSONObject(jsonString);
		String p_new = String.valueOf(jsonObject.get("p_new"));
		return p_new;
	}
	public static String OKCJ2O(String jsonString){
		JSONObject jsonObject = new JSONObject(jsonString);
		JSONObject ticker = jsonObject.getJSONObject("ticker");
		Object last = ticker.get("last");
		String last_price = last.toString();
		return last_price;
	}
	public static String OKCLTCJ2O(String jsonString){
		JSONObject jsonObject = new JSONObject(jsonString);
		JSONObject ticker = jsonObject.getJSONObject("ticker");
		Object last = ticker.get("last");
		String last_price = last.toString();
		return last_price;
	}
	public static OKCoinRealTimeData getOKAsset(String jsonString)
	{
		OKCoinRealTimeData realTimeData = new OKCoinRealTimeData();
		JSONObject jsonObject = new JSONObject(jsonString);
		JSONObject info = jsonObject.getJSONObject("info");
		JSONObject funds = info.getJSONObject("funds");
		JSONObject asset = funds.getJSONObject("asset");
		JSONObject free = funds.getJSONObject("free");
		JSONObject freezed = funds.getJSONObject("freezed");
		realTimeData.setFree_btc(Float.valueOf(free.get("btc").toString()));
		realTimeData.setFree_cny(Float.valueOf(free.get("cny").toString()));
		realTimeData.setFree_ltc(Float.valueOf(free.get("ltc").toString()));
		realTimeData.setFreezed_btc(Float.valueOf(freezed.get("btc").toString()));
		realTimeData.setFreezed_cny(Float.valueOf(freezed.get("ltc").toString()));
		realTimeData.setFreezed_ltc(Float.valueOf(freezed.get("ltc").toString()));
		realTimeData.setNet(Float.valueOf(asset.get("net").toString()));
		realTimeData.setTotal(Float.valueOf(asset.get("total").toString()));
		return realTimeData;
	}
	public static HuobiRealTimeData getHuoAsset(String jsonString){
		HuobiRealTimeData realTimeData = new HuobiRealTimeData();
		JSONObject jsonObject = new JSONObject(jsonString);
		realTimeData.setFree_btc(Float.valueOf(jsonObject.get("available_btc_display").toString()));
		realTimeData.setFree_cny(Float.valueOf(jsonObject.get("available_cny_display").toString()));
		realTimeData.setFree_ltc(Float.valueOf(jsonObject.get("available_ltc_display").toString()));
		realTimeData.setFrozen_btc(Float.valueOf(jsonObject.get("frozen_btc_display").toString()));
		realTimeData.setFrozen_cny(Float.valueOf(jsonObject.get("frozen_cny_display").toString()));
		realTimeData.setFrozen_ltc(Float.valueOf(jsonObject.get("frozen_ltc_display").toString()));
		realTimeData.setNet(Float.valueOf(jsonObject.get("net_asset").toString()));
		realTimeData.setTotal(Float.valueOf(jsonObject.get("total").toString()));
		return realTimeData;
	}
	
	public static void main(String[] args) throws Exception {
		
		IStockApi okcoinGet = new StockApi(url_prex);
		IStockApi okcoinPost = new StockApi(url_prex, api_key, secret_key);
		String OkAccountInfo = okcoinPost.userinfo();
		GetRest getRest = new GetRest();
		HuobiService service = new HuobiService();
		String HuoAccountInfo = service.getAccountInfo(ACCOUNT_INFO);
		System.out.println(HuoJ2Ltc(getRest.GetLtcString()));
		System.out.println(OKCLTCJ2O(okcoinGet.ticker("ltc_cny")));
		System.out.println(HuoJ2O(getRest.GetString()));
		System.out.println(OKCJ2O(okcoinGet.ticker("btc_cny")));
		System.out.println("火币账户总资产： "+getHuoAsset(HuoAccountInfo).total);
		System.out.println("火币账户净资产： "+getHuoAsset(HuoAccountInfo).net);
		System.out.println("火币账户可用人民币： "+getHuoAsset(HuoAccountInfo).free_cny);
		System.out.println("火币账户可用btc： "+getHuoAsset(HuoAccountInfo).free_btc);
		System.out.println("OK账户总资产： "+getOKAsset(OkAccountInfo).total);
		System.out.println("OK账户净资产： "+getOKAsset(OkAccountInfo).net);
		System.out.println("OK账户可用人民币： "+getOKAsset(OkAccountInfo).free_cny);
		System.out.println("OK账户可用btc： "+getOKAsset(OkAccountInfo).free_btc);

	}

}
