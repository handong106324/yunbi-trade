package OKcoinHuobi.Huobi.Stock;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.shanghai.stock.OKcoinHuobi.Huobi.Util.HbLtcTopSellBuyData;

public class HbJason2String {
	
	public static HbLtcTopSellBuyData Jason2TopData(String jasonString){
		HbLtcTopSellBuyData data = new HbLtcTopSellBuyData();
		JSONObject jsonObject = new JSONObject(jasonString);
		JSONArray topBuyDataList = jsonObject.getJSONArray("top_buy");
		JSONArray topSellDataList = jsonObject.getJSONArray("top_sell");
		JSONObject buyElement = topBuyDataList.getJSONObject(0);
		JSONObject sellElement = topSellDataList.getJSONObject(0);
		data.setBuyAmount((float) buyElement.getDouble("amount"));
		data.setBuyPrice((float) buyElement.getDouble("price"));
		data.setSellAmount((float) sellElement.getDouble("amount"));
		data.setSellPrice((float)sellElement.getDouble("price"));
		return data;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HbLtcTopSellBuyData data = new HbLtcTopSellBuyData();
		GetHbJason jGetHbJason = new GetHbJason();
		try {
			data = Jason2TopData(jGetHbJason.GetLtcJason());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("top_sell_price:"+data.getSellPrice());
		System.out.println("top_sell_amount:"+data.getSellAmount());
		

	}

}
