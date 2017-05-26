package platform.bittrex;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.text.DecimalFormat;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class BittrexCommunicator
{
    private String market;
    private final String API_KEY;
    private final String SECRET;
    private final String apiURL = "https://bittrex.com/api/v1.1/";
    private DecimalFormat d = new DecimalFormat("#");

    public BittrexCommunicator(String market, String API_KEY, String SECRET)
    {
        this.market = market;
        this.API_KEY = API_KEY;
        this.SECRET = SECRET;
        d.setMaximumFractionDigits(8);
        d.setMinimumFractionDigits(8);
    }

    public void setMarket(String market)
    {
        this.market = market;
    }

    public JSONArray getMarkets()
    {
        return getArrayResponse(apiURL + "public/getmarkets", false);
    }

    public JSONArray getCurrencies()
    {
        return getArrayResponse(apiURL + "public/getcurrencies", false);
    }

    public JSONObject getTicker()
    {
        return getObjectResponse(apiURL + "public/getticker?market=" + market, false);
    }

    public JSONArray getMarketSummaries()
    {
        return getArrayResponse(apiURL + "public/getmarketsummaries", false);
    }

    public JSONObject getMarketSummary()
    {
        JSONArray response = (JSONArray)getArrayResponse(apiURL + "public/getmarketsummary?market=" + market, false);

        return (response == null || response.size() < 1) ? null : (JSONObject)response.get(0);
    }

    public JSONObject getOrderBook(int depth)
    {
        return getObjectResponse(apiURL + "public/getorderbook?market=" + market + "&type=both&depth=" + depth, false);
    }

    public JSONArray getMarketHistory(int count)
    {
        return getArrayResponse(apiURL + "public/getmarkethistory?market=" + market + "&count=" + count, false);
    }

    public String placeBuyOrder(double quantity, double rate)
    {
        JSONObject result = getObjectResponse(apiURL + "market/buylimit?" +
                "apikey=" + API_KEY +
                "&market=" + market +
                "&quantity=" + d.format(quantity) +
                "&rate=" + d.format(rate), true);
        return (result == null || result.get("uuid") == null) ? null : result.get("uuid").toString();
    }

    public String placeSellOrder(double quantity, double rate)
    {
        JSONObject result = getObjectResponse(apiURL + "market/selllimit?" +
                "apikey=" + API_KEY +
                "&market=" + market +
                "&quantity=" + d.format(quantity) +
                "&rate=" + d.format(rate), true);
        return (result == null || result.get("uuid") == null) ? null : result.get("uuid").toString();
    }

//    public boolean cancelOrder(String uuid)
//    {
//        try {
//            JSONParser parser = new JSONParser();
//            JSONObject result = (JSONObject)parser.parse(getResponse(apiURL + "market/cancel?" +
//                    "apikey=" + API_KEY +
//                    "&uuid=" + uuid, true));
//            return (boolean)result.get("success");
//        } catch(Exception e) {
//            return false;
//        }
//    }

    public JSONArray getOpenOrders()
    {
        return getArrayResponse(apiURL + "market/getopenorders?apikey=" + API_KEY + "&market=" + market, true);
    }

    public JSONArray getAllOpenOrders()
    {
        return getArrayResponse(apiURL + "market/getopenorders?apikey=" + API_KEY, true);
    }

    public JSONArray getBalances()
    {
        return getArrayResponse(apiURL + "account/getbalances?apikey=" + API_KEY, true);
    }

    public JSONObject getBalance(String currency)
    {
        return getObjectResponse(apiURL + "account/getbalance?apikey=" + API_KEY + "&currency=" + currency, true);
    }

    public String getDepositAddress(String currency)
    {
        JSONObject response = getObjectResponse(apiURL + "account/getdepositaddress?apikey=" + API_KEY + "&currency=" + currency, true);

        return (response == null || response.get("Address") == null) ? null : response.get("Address").toString();
    }

    public String withdraw(String currency, double quantity, String address)
    {
        JSONObject result = getObjectResponse(apiURL + "account/withdraw?" +
                "apikey=" + API_KEY +
                "&currency=" + currency +
                "&quantity=" + d.format(quantity) +
                "&address=" + address, true);
        return (result == null || result.get("uuid") == null) ? null : result.get("uuid").toString();
    }

    public JSONObject getOrder(String uuid)
    {
        return getObjectResponse(apiURL + "account/getorder?apikey=" + API_KEY + "&uuid=" + uuid, true);
    }

    public JSONArray getOrderHistory()
    {
        return getArrayResponse(apiURL + "account/getorderhistory?apikey=" + API_KEY, true);
    }

    public JSONArray getOrderHistory(String market)
    {
        return getArrayResponse(apiURL + "account/getorderhistory?apikey=" + API_KEY + "&market=" + market, true);
    }

    public JSONArray getOrderHistory(int count)
    {
        return getArrayResponse(apiURL + "account/getorderhistory?apikey=" + API_KEY + "&count=" + count, true);
    }

    public JSONArray getOrderHistory(String market, int count)
    {
        return getArrayResponse(apiURL + "account/getorderhistory?apikey=" + API_KEY + "&market=" + market + "&count=" + count, true);
    }

    public JSONArray getWithdrawalHistory()
    {
        return getArrayResponse(apiURL + "account/getwithdrawalhistory?apikey=" + API_KEY, true);
    }

    public JSONArray getWithdrawalHistory(String currency)
    {
        return getArrayResponse(apiURL + "account/getwithdrawalhistory?apikey=" + API_KEY + "&currency=" + currency, true);
    }

    public JSONArray getWithdrawalHistory(int count)
    {
        return getArrayResponse(apiURL + "account/getwithdrawalhistory?apikey=" + API_KEY + "&count=" + count, true);
    }

    public JSONArray getWithdrawalHistory(String currency, int count)
    {
        return getArrayResponse(apiURL + "account/getwithdrawalhistory?apikey=" + API_KEY + "&currency=" + currency + "&count=" + count, true);
    }

    public JSONArray getDepositHistory()
    {
        return getArrayResponse(apiURL + "account/getdeposithistory?apikey=" + API_KEY, true);
    }

    public JSONArray getDepositHistory(String currency)
    {
        return getArrayResponse(apiURL + "account/getdeposithistory?apikey=" + API_KEY + "&currency=" + currency, true);
    }

    public JSONArray getDepositHistory(int count)
    {
        return getArrayResponse(apiURL + "account/getdeposithistory?apikey=" + API_KEY + "&count=" + count, true);
    }

    public JSONArray getDepositHistory(String currency, int count)
    {
        return getArrayResponse(apiURL + "account/getdeposithistory?apikey=" + API_KEY + "&currency=" + currency + "&count=" + count, true);
    }

    private String getResponse(String url, boolean authenticated)
    {
        try {
            URL urlObj = new URL(url + (authenticated ? (url.contains("?") ? "&nonce=" : "?nonce=") + System.currentTimeMillis()/1000 : ""));
            HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
            con.setRequestMethod("GET");
            if(authenticated)
                con.addRequestProperty("apisign", hmacDigest(urlObj.toString(), SECRET, "HmacSHA512"));
            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } catch(Exception e) {
            return null;
        }
    }

    private JSONObject getObjectResponse(String url, boolean authenticated)
    {
        try {
            JSONObject response = (JSONObject) JSON.parse(getResponse(url, authenticated));
            if(!(boolean)response.get("success"))
                return null;

            return (JSONObject)response.get("result");
        } catch(Exception e) {
            return null;
        }
    }

    private JSONArray getArrayResponse(String url, boolean authenticated)
    {
        try {
            JSONObject response = (JSONObject)JSON.parse(getResponse(url, authenticated));
            if(!(boolean)response.get("success"))
                return null;

            return (JSONArray)response.get("result");
        } catch(Exception e) {
            return null;
        }
    }

    private static String hmacDigest(String msg, String keyString, String algo)
    {
        String digest = null;
        try {
            SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), algo);
            Mac mac = Mac.getInstance(algo);
            mac.init(key);

            byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));

            StringBuffer hash = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            digest = hash.toString();
        } catch (UnsupportedEncodingException e) {
        } catch (InvalidKeyException e) {
        } catch (NoSuchAlgorithmException e) {
        }
        return digest;
    }
}
