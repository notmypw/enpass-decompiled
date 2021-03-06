package com.box.androidsdk.content.utils;

import android.util.Log;
import com.box.androidsdk.content.BoxConfig;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

public class BoxLogUtils {
    public static boolean getIsLoggingEnabled() {
        return BoxConfig.IS_LOG_ENABLED && BoxConfig.IS_DEBUG;
    }

    public static void i(String tag, String msg) {
        if (getIsLoggingEnabled()) {
            Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Map<String, String> map) {
        if (getIsLoggingEnabled() && map != null) {
            for (Entry<String, String> e : map.entrySet()) {
                Log.i(tag, String.format(Locale.ENGLISH, "%s:  %s:%s", new Object[]{msg, e.getKey(), e.getValue()}));
            }
        }
    }

    public static void d(String tag, String msg) {
        if (getIsLoggingEnabled()) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (getIsLoggingEnabled()) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable t) {
        if (getIsLoggingEnabled()) {
            Log.e(tag, msg, t);
        }
    }
}
