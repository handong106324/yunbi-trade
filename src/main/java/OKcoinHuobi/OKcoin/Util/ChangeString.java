package OKcoinHuobi.OKcoin.Util;


import OKcoinHuobi.Huobi.Stock.GetRest;
import OKcoinHuobi.Huobi.Stock.HuobiService;
import OKcoinHuobi.Huobi.Util.HuobiRealTimeData;
import OKcoinHuobi.OKcoin.Stock.IStockApi;
import OKcoinHuobi.OKcoin.Stock.StockApi;
import com.alibaba.fastjson.JSONObject;

public class ChangeString {

   	public static String HuoJ2O(String jsonString){
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
		String p_new = String.valueOf(jsonObject.get("p_new"));
		return p_new;    	
	} 
	
	public static String HuoJ2Ltc(String jsonString){
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
		String p_new = String.valueOf(jsonObject.get("p_new"));
		return p_new;
	}
	public static String OKCJ2O(String jsonString){
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
		JSONObject ticker = jsonObject.getJSONObject("ticker");
		Object last = ticker.get("last");
		String last_price = last.toString();
		return last_price;
	}
	public static String OKCLTCJ2O(String jsonString){
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
		JSONObject ticker = jsonObject.getJSONObject("ticker");
		Object last = ticker.get("last");
		String last_price = last.toString();
		return last_price;
	}
	public static OKCoinRealTimeData getOKAsset(String jsonString)
	{
		OKCoinRealTimeData realTimeData = new OKCoinRealTimeData();
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
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
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
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
		
		IStockApi okcoinGet = new StockApi();
//		String OkAccountInfo = AccountInfoUtil.get();
		GetRest getRest = new GetRest();
		HuobiService service = new HuobiService();
//		String HuoAccountInfo = service.getAccountInfo(ACCOUNT_INFO);
		System.out.println(HuoJ2Ltc(getRest.GetLtcString()));
		System.out.println(OKCLTCJ2O(okcoinGet.ticker("ltc_cny")));
		System.out.println(HuoJ2O(getRest.GetString()));
		System.out.println(OKCJ2O(okcoinGet.ticker("btc_cny")));
//		System.out.println("火币账户总资产： "+getHuoAsset(HuoAccountInfo).total);
//		System.out.println("火币账户净资产： "+getHuoAsset(HuoAccountInfo).net);
//		System.out.println("火币账户可用人民币： "+getHuoAsset(HuoAccountInfo).free_cny);
//		System.out.println("火币账户可用btc： "+getHuoAsset(HuoAccountInfo).free_btc);
//		System.out.println("OK账户总资产： "+getOKAsset(OkAccountInfo).total);
//		System.out.println("OK账户净资产： "+getOKAsset(OkAccountInfo).net);
//		System.out.println("OK账户可用人民币： "+getOKAsset(OkAccountInfo).free_cny);
//		System.out.println("OK账户可用btc： "+getOKAsset(OkAccountInfo).free_btc);

	}

}
