package in.sinew.enpassengine;

import android.net.Uri;
import android.text.TextUtils;
import com.github.clans.fab.BuildConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetDomain {
    public static String GetDomainFromUrl(String Url) {
        return GetDomainFromUrl(Uri.parse(Url));
    }

    public static String GetDomainFromUrl(String Url, boolean Strict) {
        return GetDomainFromUrl(Uri.parse(Url), Strict);
    }

    public static String GetDomainFromUrl(Uri Url) {
        return GetDomainFromUrl(Url, false);
    }

    public static String GetDomainFromUrl(Uri Url1, boolean Strict1) {
        if (Url1 == null) {
            return null;
        }
        String host = Url1.getHost();
        if (TextUtils.isEmpty(host)) {
            return null;
        }
        String[] dotBits = host.split("\\.");
        if (dotBits.length == 1) {
            return Url1.getHost();
        }
        if (dotBits.length == 2) {
            return Url1.getHost();
        }
        List<String> tlds = new ArrayList();
        tlds.addAll(Arrays.asList(TldPatterns.EXACT));
        tlds.addAll(Arrays.asList(TldPatterns.UNDER));
        tlds.addAll(Arrays.asList(TldPatterns.EXCLUDED));
        String bestMatch = BuildConfig.FLAVOR;
        for (String tld : tlds) {
            if (Url1.getHost().endsWith(tld) && tld.length() > bestMatch.length()) {
                bestMatch = tld;
            }
        }
        if (TextUtils.isEmpty(bestMatch)) {
            return Url1.getHost();
        }
        String[] bestBits = bestMatch.split("\\.");
        String[] inputBits = Url1.getHost().split("\\.");
        int getLastBits = bestBits.length + 1;
        bestMatch = BuildConfig.FLAVOR;
        for (int c = inputBits.length - getLastBits; c < inputBits.length; c++) {
            if (bestMatch.length() > 0) {
                bestMatch = bestMatch + ".";
            }
            bestMatch = bestMatch + inputBits[c];
        }
        return bestMatch;
    }
}
