package in.sinew.enpass;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import com.github.clans.fab.BuildConfig;
import io.enpass.app.R;

public class PasswordGeneratorActivity extends EnpassActivity {
    static int mDigitLength = 0;
    static int mSymbolLength = 0;
    static int mUpperCaseLength = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_activity);
        invalidateOptionsMenu();
        boolean aIsFromEditPage = getIntent().getBooleanExtra("from_edit", false);
        String aUrl = getIntent().getStringExtra("url_domain");
        boolean aIsFromTab = getIntent().getBooleanExtra("IsTab", false);
        Bundle bundle = new Bundle();
        bundle.putString("url_domain", aUrl);
        bundle.putBoolean("from_edit", aIsFromEditPage);
        bundle.putBoolean("IsTab", aIsFromTab);
        PasswordGeneratorDialogFragment dialog = new PasswordGeneratorDialogFragment();
        dialog.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.password_frame_layout, dialog).commit();
    }

    public static String generatePassword() {
        AppSettings appSettings = EnpassApplication.getInstance().getAppSettings();
        String mShuffleString = BuildConfig.FLAVOR;
        if (appSettings.isPronounceable()) {
            mShuffleString = PronounceablePasswordGenerator.generateNewWordsPassword(PronounceablePasswordGenerator.getPasswordWordsLengthPref(), PronounceablePasswordGenerator.getIncludeDigits(), PronounceablePasswordGenerator.getUpperCase());
            calculateDigitSymbolUppercase(mShuffleString);
            return mShuffleString;
        }
        mDigitLength = RandomPasswordGenerator.getPasswordDigitLengthPref();
        mSymbolLength = RandomPasswordGenerator.getPasswordSymbolLengthPref();
        mUpperCaseLength = RandomPasswordGenerator.getPasswordUppercaseLengthPref();
        StringBuilder mGeneratedPassword = new StringBuilder(BuildConfig.FLAVOR);
        int mPasswordLength = RandomPasswordGenerator.getPasswordLengthPref();
        mGeneratedPassword.setLength(0);
        RandomPasswordGenerator.generateRandomPassword(mPasswordLength, mGeneratedPassword);
        return shuffleString(mGeneratedPassword.substring(0, mPasswordLength));
    }

    public static String shuffleString(String aString) {
        String shuffledString = BuildConfig.FLAVOR;
        while (aString.length() != 0) {
            int index = (int) Math.floor(Math.random() * ((double) aString.length()));
            char c = aString.charAt(index);
            aString = aString.substring(0, index) + aString.substring(index + 1);
            shuffledString = shuffledString + c;
        }
        return shuffledString;
    }

    public static SpannableString getColoredString(String password) {
        SpannableString spnStr = new SpannableString(password);
        int strLen = spnStr.length();
        for (int i = 0; i < strLen; i++) {
            int aChar = spnStr.charAt(i);
            if (Character.isUpperCase(aChar)) {
                spnStr.setSpan(new ForegroundColorSpan(-16777216), i, i + 1, 0);
            } else if (Character.isDigit(aChar)) {
                spnStr.setSpan(new ForegroundColorSpan(Color.parseColor("#1867C4")), i, i + 1, 0);
            } else if (Character.isLowerCase(aChar)) {
                spnStr.setSpan(new ForegroundColorSpan(-16777216), i, i + 1, 0);
            } else {
                spnStr.setSpan(new ForegroundColorSpan(Color.parseColor("#CD0A0A")), i, i + 1, 0);
            }
        }
        return spnStr;
    }

    public static int calculateEntropy(String mPassword) {
        int d;
        int nSymbols = 28;
        int nUpperCases = 24;
        int nLowerCases = 24;
        int nDigits = 9;
        int mPasswordLength = mPassword.length();
        if (mPasswordLength > mDigitLength) {
            d = mDigitLength;
        } else {
            d = mPasswordLength;
        }
        int s = mPasswordLength > mDigitLength + mSymbolLength ? mSymbolLength : mPasswordLength - mDigitLength;
        int u = mPasswordLength > (mDigitLength + mSymbolLength) + mUpperCaseLength ? mUpperCaseLength : (mPasswordLength - mDigitLength) - mSymbolLength;
        int l = ((mPasswordLength - mDigitLength) - mSymbolLength) - mUpperCaseLength > 0 ? ((mPasswordLength - mDigitLength) - mSymbolLength) - mUpperCaseLength : 0;
        if (s <= 0) {
            nSymbols = 0;
        }
        int num = 0 + nSymbols;
        if (u <= 0) {
            nUpperCases = 0;
        }
        num += nUpperCases;
        if (l <= 0) {
            nLowerCases = 0;
        }
        num += nLowerCases;
        if (d <= 0) {
            nDigits = 0;
        }
        return (int) (((((double) mPasswordLength) * log2((double) (num + nDigits))) / 128.0d) * 100.0d);
    }

    public static void calculateDigitSymbolUppercase(String aPassword) {
        mUpperCaseLength = 0;
        mDigitLength = 0;
        mSymbolLength = 0;
        int mPasswordLength = aPassword.length();
        for (int i = 0; i < mPasswordLength; i++) {
            char ch = aPassword.charAt(i);
            if (Character.isUpperCase(ch)) {
                mUpperCaseLength++;
            } else if (Character.isDigit(ch)) {
                mDigitLength++;
            } else if (!Character.isLowerCase(ch)) {
                mSymbolLength++;
            }
        }
    }

    public static double log2(double number) {
        return Math.log(number) / Math.log(2.0d);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        EnpassApplication.getInstance().changeLocale(this);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 82) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(EnpassApplication.getInstance().changeLocale(base));
    }
}
