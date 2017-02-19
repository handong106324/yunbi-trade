import OKcoinHuobi.App;
import bt.yunbi.market.bean.AppAccount;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by 44283 on 2017/2/19.
 */
public class AccountInfoUtil {
    private static String YUNBI_AK, YUNBI_SK;
    private static String HUOBI_AK, HUOBI_SK;
    private static String OKBI_AK, OKBI_SK;
    static {
        FileReader fileReader = null;
        BufferedReader reader = null;
        try {
            fileReader = new FileReader("I:\\/key.txt");
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
}
