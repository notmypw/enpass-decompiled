package in.sinew.enpass;

import android.content.SharedPreferences.Editor;
import com.github.clans.fab.BuildConfig;
import com.google.api.client.http.HttpMethods;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SignInAsTeamTokenVerifier {
    public static final String PREMIUM_VERSION_PREFERENCE = "premiumVersion";
    public final String PARTNER_SERVER = "https://partner.enpass.io/";

    public SignInAsTeamTokenVerifier() {
        EnpassApplication.getInstance().getClass();
    }

    public void verifyToken() {
        Object[] objects = new Object[3];
        objects[0] = getRequestForApi("api/validate/", HttpMethods.POST);
        new VerifyTokenTask(this).execute(objects);
    }

    public HttpURLConnection getRequestForApi(String api, String httpMethod) {
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(this.PARTNER_SERVER + api).openConnection();
            urlConnection.setRequestMethod(httpMethod);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            return urlConnection;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return urlConnection;
        } catch (IOException e2) {
            e2.printStackTrace();
            return urlConnection;
        }
    }

    private String getResponse(InputStream in) {
        IOException e;
        Throwable th;
        Exception e2;
        String json = BuildConfig.FLAVOR;
        BufferedReader reader = null;
        try {
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(in));
            try {
                String str = BuildConfig.FLAVOR;
                while (true) {
                    str = reader2.readLine();
                    if (str == null) {
                        break;
                    }
                    System.out.println(str);
                    json = json + str;
                }
                if (reader2 != null) {
                    try {
                        reader2.close();
                        reader = reader2;
                    } catch (IOException e3) {
                        e3.printStackTrace();
                        reader = reader2;
                    }
                }
            } catch (IOException e4) {
                e3 = e4;
                reader = reader2;
                try {
                    e3.printStackTrace();
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e32) {
                            e32.printStackTrace();
                        }
                    }
                    return json;
                } catch (Throwable th2) {
                    th = th2;
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e322) {
                            e322.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Exception e5) {
                e2 = e5;
                reader = reader2;
                e2.printStackTrace();
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e3222) {
                        e3222.printStackTrace();
                    }
                }
                return json;
            } catch (Throwable th3) {
                th = th3;
                reader = reader2;
                if (reader != null) {
                    reader.close();
                }
                throw th;
            }
        } catch (IOException e6) {
            e3222 = e6;
            e3222.printStackTrace();
            if (reader != null) {
                reader.close();
            }
            return json;
        } catch (Exception e7) {
            e2 = e7;
            e2.printStackTrace();
            if (reader != null) {
                reader.close();
            }
            return json;
        }
        return json;
    }

    public void savePurchase(boolean hasPremiumVersion) {
        Editor edit = EnpassApplication.getInstance().getSharedPreferences(PREMIUM_VERSION_PREFERENCE, 0).edit();
        edit.putBoolean(PREMIUM_VERSION_PREFERENCE, hasPremiumVersion);
        edit.commit();
    }
}
