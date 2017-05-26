package platform.OKcoinHuobi.Huobi.Stock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetHbJason {
	
	public String GetBtcJason() throws IOException{
		URL url = null;
		url = new URL("http://api.huobi.com/staticmarket/detail_btc_json.js");
		HttpURLConnection connet;
		connet = (HttpURLConnection) url.openConnection();
		if(connet.getResponseCode() != 200){
			throw new IOException(connet.getResponseMessage());
		}
		BufferedReader brd = new BufferedReader(new InputStreamReader(connet.getInputStream()));
		String restString = brd.readLine();
		connet.disconnect();
		return restString;
	}
	
	public String GetLtcJason() throws IOException{
		URL url = null;
		url = new URL("http://api.huobi.com/staticmarket/detail_ltc_json.js");
		HttpURLConnection connet;
		connet = (HttpURLConnection) url.openConnection();
		if(connet.getResponseCode() != 200){
			throw new IOException(connet.getResponseMessage());
		}
		BufferedReader brd = new BufferedReader(new InputStreamReader(connet.getInputStream()));
		String restString = brd.readLine();
		connet.disconnect();
		return restString;
	}
	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		URL url = null;
		url = new URL("http://api.huobi.com/staticmarket/detail_ltc_json.js");
		HttpURLConnection connet;
		connet = (HttpURLConnection) url.openConnection();
		if(connet.getResponseCode() != 200){
			throw new IOException(connet.getResponseMessage());
		}
		BufferedReader brd = new BufferedReader(new InputStreamReader(connet.getInputStream()));
		System.out.println(brd.readLine());
		connet.disconnect();
	}

}
