package app.akeorcist.deviceinformation.utility;

import java.util.Locale;

/**
 * Created by Akexorcist on 2/26/15 AD.
 */
public class StringUtils {

    public static String wrapBlank(String str) {
        if(str == null || str.toLowerCase(Locale.getDefault()).equals("null"))
            str = "";
        return str;
    }

    public static String wrapUnknown(String str) {
        if(str == null || str.toLowerCase(Locale.getDefault()).equals("null") || str.equals(""))
            str = "Unknown";
        return str;
    }

    public static String wrapUnknownLower(String str) {
        if(str == null || str.toLowerCase(Locale.getDefault()).equals("null") || str.equals(""))
            str = "unknown";
        return str;
    }

    public static String wrapUrl(String str) {
        if(str != null)
            return str.replace("/", "_").replace(":", "_").replace(".", "_").replace(" ", "_");
        return str;
    }

    public static String removeUnknown(String str) {
        return str.replace("unknown", "").replace("Unknown", "");
    }

}
