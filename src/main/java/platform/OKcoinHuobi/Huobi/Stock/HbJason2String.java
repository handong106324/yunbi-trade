package platform.OKcoinHuobi.Huobi.Stock;

import platform.OKcoinHuobi.Huobi.Util.HbLtcTopSellBuyData;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;


public class HbJason2String {
	
	public static HbLtcTopSellBuyData Jason2TopData(String jasonString){
		HbLtcTopSellBuyData data = new HbLtcTopSellBuyData();
		JSONObject jsonObject = JSONObject.parseObject(jasonString);
		JSONArray topBuyDataList = jsonObject.getJSONArray("top_buy");
		JSONArray topSellDataList = jsonObject.getJSONArray("top_sell");
		JSONObject buyElement = topBuyDataList.getJSONObject(0);
		JSONObject sellElement = topSellDataList.getJSONObject(0);
		data.setBuyAmount( buyElement.getFloat("amount"));
		data.setBuyPrice(buyElement.getFloat("price"));
		data.setSellAmount(sellElement.getFloat("amount"));
		data.setSellPrice(sellElement.getFloat("price"));
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
