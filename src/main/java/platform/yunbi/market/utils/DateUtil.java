package platform.yunbi.market.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by handong on 17/1/22.
 */
public class DateUtil {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String format(Date date) {
        return format.format(date);
    }
}
