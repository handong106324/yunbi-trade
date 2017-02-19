package bt.yunbi.common;

import java.text.DecimalFormat;

/**
 * Created by classic1999 on 14-6-9.
 */
public class DoubleUtils {

    public static Double toFourDecimal(double a) {
        DecimalFormat dcmFmt = new DecimalFormat("#.#####");
        return Double.parseDouble(dcmFmt.format(a));
    }
    public static Double toThreeDecimal(double a) {
        DecimalFormat dcmFmt = new DecimalFormat("#.###");
        return Double.parseDouble(dcmFmt.format(a));
    }
}
