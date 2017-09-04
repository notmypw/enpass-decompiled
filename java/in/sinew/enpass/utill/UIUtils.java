package in.sinew.enpass.utill;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import com.box.androidsdk.content.models.BoxError;
import com.box.androidsdk.content.models.BoxFileVersion;
import com.github.clans.fab.BuildConfig;
import io.enpass.app.R;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UIUtils {
    public static Map<String, String> getCreditCardType(Context context) {
        Map<String, String> creditCardType = new LinkedHashMap();
        Resources res = context.getResources();
        creditCardType.put("master", res.getString(R.string.creditcard_type_master));
        creditCardType.put("visa", res.getString(R.string.creditcard_type_visa));
        creditCardType.put("maestro", res.getString(R.string.creditcard_type_maestro));
        creditCardType.put("americanexpress", res.getString(R.string.creditcard_type_american_express));
        creditCardType.put("discover", res.getString(R.string.creditcard_type_discover));
        creditCardType.put("diners", res.getString(R.string.creditcard_type_dinersClub));
        creditCardType.put("cirrus", res.getString(R.string.creditcard_type_cirrus));
        creditCardType.put("default", res.getString(R.string.creditcard_type_other));
        return creditCardType;
    }

    public static String getUrlTitle(Uri uri) {
        List<String> parts = new LinkedList(Arrays.asList(getHost(uri.toString()).split("\\.")));
        if (parts.size() < 2) {
            return null;
        }
        parts.remove(parts.size() - 1);
        String lastString = (String) parts.get(parts.size() - 1);
        List<String> secondLevelDomainList = Arrays.asList(new String[]{"com", "edu", "gov", "net", "org", "mil", "nom", "pro", "sch", BoxFileVersion.FIELD_NAME, "edu", "off", "gob", "int", "pro", "tur", "nic", "info", "uri", "urn", "asn", "csiro", "conf", "act", "nsw", "tas", "qld", "vic", "pro", "biz", "untz", "unmo", "unze", "unbi", "web", "store", "asso", "barreau", "gouv", "adm", "adv", "agr", "arq", "art", "ato", "bio", "blog", "bmd", "cim", "cng", "cnt", "coop", "ecn", "eco", "emp", "eng", "esp", "etc", "eti", "far", "flog", "fnd", "fot", "fst"});
        if (lastString.length() == 2) {
            parts.remove(parts.size() - 1);
        } else {
            for (String str : secondLevelDomainList) {
                if (parts.size() > 0) {
                    String lastStr = (String) parts.get(parts.size() - 1);
                    if (str.equals(lastStr)) {
                        parts.remove(lastStr);
                    }
                }
            }
        }
        if (parts.size() > 1) {
            return (String) parts.get(parts.size() - 1);
        }
        return null;
    }

    public static String getHost(String url) {
        if (url == null || url.length() == 0) {
            return BuildConfig.FLAVOR;
        }
        int doubleslash = url.indexOf("//");
        if (doubleslash == -1) {
            doubleslash = 0;
        } else {
            doubleslash += 2;
        }
        int end = url.indexOf(47, doubleslash);
        if (end < 0) {
            end = url.length();
        }
        return url.substring(doubleslash, end);
    }

    public static Map<String, String> getCountryList(String json) {
        Map<String, String> countryMap = new HashMap();
        try {
            JSONArray jarray = new JSONArray(json);
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jobj = jarray.getJSONObject(i);
                countryMap.put(jobj.getString(BoxError.FIELD_CODE), jobj.getString(BoxFileVersion.FIELD_NAME));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return countryMap;
    }

    public static String getOptionRealPart(String keyValuePair) {
        try {
            return keyValuePair.split("-")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getOptionKeyPart(String keyValuePair) {
        String[] parts = keyValuePair.split("-");
        if (parts.length > 0) {
            return parts[0];
        }
        return keyValuePair;
    }

    public static int getHashCode(String copyString) {
        BigInteger hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(copyString.getBytes());
            hash = new BigInteger(1, md.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash.intValue();
    }

    public static boolean isValidEmail(String email) {
        return email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    }

    public static int nthOccurrence(String str, char c, int n) {
        int pos = str.indexOf(c, 0);
        while (true) {
            n--;
            if (n <= 0 || pos == -1) {
                return pos;
            }
            pos = str.indexOf(c, pos + 1);
        }
        return pos;
    }
}
