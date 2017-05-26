package platform.com.xinran.util;
import java.io.FileReader;

import platform.OKcoinHuobi.OKcoin.Stock.StockApi;
import platform.yunbi.market.bean.AppAccount;
import platform.com.okcoin.rest.HttpUtilManager;
import platform.com.okcoin.rest.MD5Util;
import org.apache.http.HttpException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 44283 on 2017/2/19.
 */
public class AccountInfoUtil {
    private static String YUNBI_AK, YUNBI_SK;
    private static String HUOBI_AK, HUOBI_SK;
    private static String OKBI_AK, OKBI_SK;
    public final static String OK_ACCOUNT_INFO = "get_account_info";
    static {
        FileReader fileReader = null;
        BufferedReader reader = null;
        try {
            fileReader = new FileReader("/Users/syd/key.txt");
            reader = new BufferedReader(fileReader);
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                String[] vals = temp.split(":");
                if ("yunbi-ak".equals(vals[0])) {
                    YUNBI_AK = vals[1];
                } else if ("yunbi-sk".equals(vals[1])) {
                    YUNBI_SK = vals[1];
                } else if ("huobi-ak".equals(vals[1])) {
                    HUOBI_AK = vals[1];
                } else if ("huobi-sk".equals(vals[1])) {
                    HUOBI_SK = vals[1];
                } else if ("okbi-ak".equals(vals[1])) {
                    OKBI_AK = vals[1];
                } else if ("okbi-sk".equals(vals[1])) {
                    OKBI_SK = vals[1];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static AppAccount getYunBiAccount() {
        AppAccount appAccount = new AppAccount();
        appAccount.setId(1L);
        appAccount.setAccessKey(YUNBI_AK); // todo 替换为access_key
        appAccount.setSecretKey(YUNBI_SK); // todo 替换为secret_key
        return appAccount;
    }

    public static String getHuobiSk() {
        return HUOBI_SK;
    }

    public static String getOkbiSk() {
        return OKBI_SK;
    }

    public static String getOkbiAk() {
        return OKBI_AK;
    }

    public static String getHuobiAk() {
        return HUOBI_AK;
    }

    public static String getOkCoinUserInfo() throws HttpException, IOException {
        // 构造参数签名
        Map<String, String> params = new HashMap<String, String>();
        params.put("api_key", OKBI_AK);
        String sign = MD5Util.buildMysignV1(params, OKBI_SK);
        params.put("sign", sign);

        // 发送post请求
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String result = httpUtil.requestHttpPost("https://www.okcoin.cn", StockApi.USERINFO_URL,
                params);

        return result;
    }
}
