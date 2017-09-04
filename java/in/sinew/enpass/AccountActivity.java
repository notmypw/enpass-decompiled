package in.sinew.enpass;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.style.UnderlineSpan;
import android.widget.Button;
import android.widget.TextView;
import io.enpass.app.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountActivity extends AppCompatActivity {
    TextView mContactSupport;
    TextView mPurchaseBefore;
    Button mPurchaseNow;
    TextView mPurchaseViaFreeOffer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);
        TextView accountText = (TextView) findViewById(R.id.account_text);
        if (EnpassApplication.MARKET_ID == EnpassApplication.INSTALL_FROM_GOOGLE_PLAY) {
            accountText.setText(R.string.account_text_playStore);
        } else {
            if (EnpassApplication.MARKET_ID == EnpassApplication.INSTALL_FROM_AMAZON_STORE) {
                accountText.setText(R.string.account_text_amazonStore);
            } else {
                if (EnpassApplication.MARKET_ID == EnpassApplication.INSTALL_FROM_NOKIA_STORE) {
                    accountText.setText(R.string.account_text_playStore);
                }
            }
        }
        this.mPurchaseNow = (Button) findViewById(R.id.purchased_now);
        this.mPurchaseBefore = (TextView) findViewById(R.id.text_purchase_before_jan);
        this.mPurchaseViaFreeOffer = (TextView) findViewById(R.id.text_purchase_via_freeoffer);
        this.mContactSupport = (TextView) findViewById(R.id.text_contact_support);
        String supportText = getString(R.string.contact_support);
        SpannableString contactSupport = new SpannableString(supportText);
        contactSupport.setSpan(new UnderlineSpan(), 0, supportText.length(), 0);
        this.mContactSupport.setText(contactSupport);
        this.mContactSupport.setTextColor(getResources().getColor(2131099754));
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse("31/01/2015");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String s = DateFormat.getDateFormat(this).format(date);
        String text = String.format(getString(R.string.purchase_before_jan), new Object[]{s});
        SpannableString content = new SpannableString(text);
        content.setSpan(new UnderlineSpan(), 0, text.length(), 0);
        this.mPurchaseBefore.setText(content);
        this.mPurchaseBefore.setTextColor(getResources().getColor(2131099754));
        String purchaseViaText = getString(R.string.purchase_via_freeoffer);
        SpannableString purchaseViaStr = new SpannableString(purchaseViaText);
        purchaseViaStr.setSpan(new UnderlineSpan(), 0, purchaseViaText.length(), 0);
        this.mPurchaseViaFreeOffer.setText(purchaseViaStr);
        this.mPurchaseViaFreeOffer.setTextColor(getResources().getColor(2131099754));
        if (EnpassApplication.MARKET_ID != EnpassApplication.INSTALL_FROM_GOOGLE_PLAY) {
            this.mPurchaseBefore.setVisibility(8);
            this.mContactSupport.setVisibility(8);
        }
        EnpassApplication.getInstance().setAccountActivity(this);
        setTitle(String.format(getResources().getString(R.string.account_title), new Object[0]));
        this.mPurchaseNow.setOnClickListener(new 1(this));
        this.mPurchaseBefore.setOnClickListener(new 2(this));
        this.mPurchaseViaFreeOffer.setOnClickListener(new 3(this));
        this.mContactSupport.setOnClickListener(new 4(this));
    }

    public void sendEmail(String[] recipientList, String title, String subject, String body) {
        try {
            Intent emailIntent = new Intent("android.intent.action.SEND");
            emailIntent.setType("plain/text");
            emailIntent.putExtra("android.intent.extra.EMAIL", recipientList);
            emailIntent.putExtra("android.intent.extra.SUBJECT", subject);
            emailIntent.putExtra("android.intent.extra.TEXT", body);
            startActivity(emailIntent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void finishActivityAfterPurchase() {
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Activity activity = EnpassApplication.getInstance().getMainActivity();
        if (activity != null) {
            ((MainActivity) activity).handleOnActivityResult(requestCode, resultCode, data);
        }
    }

    public void onUserInteraction() {
        super.onUserInteraction();
        if (EnpassApplication.getInstance().getKeychain() != null) {
            EnpassApplication.getInstance().touch();
        }
    }

    protected void onResume() {
        super.onResume();
        if (EnpassApplication.getInstance().getKeychain() != null) {
            EnpassApplication.getInstance().onActivityResume(this);
        }
    }

    protected void onPause() {
        super.onPause();
        if (EnpassApplication.getInstance().getKeychain() != null) {
            EnpassApplication.getInstance().onActivityPause(this);
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        EnpassApplication.getInstance().changeLocale(this);
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(EnpassApplication.getInstance().changeLocale(base));
    }

    protected void onDestroy() {
        super.onDestroy();
        if (EnpassApplication.MARKET_ID == EnpassApplication.INSTALL_FROM_AMAZON_STORE && EnpassApplication.getInstance().getMainActivity() != null) {
            ((MainActivity) EnpassApplication.getInstance().getMainActivity()).mAmazonIapManager.deactivate();
        }
        if (EnpassApplication.MARKET_ID == EnpassApplication.INSTALL_FROM_GOOGLE_PLAY && EnpassApplication.getInstance().getMainActivity() != null) {
            ((MainActivity) EnpassApplication.getInstance().getMainActivity()).disposeIabHelper();
        }
    }
}
