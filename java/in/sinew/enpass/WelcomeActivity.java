package in.sinew.enpass;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.github.clans.fab.BuildConfig;
import in.sinew.enpass.flipperwidgetmodel.ButtonItems;
import in.sinew.enpass.utill.RootChecker;
import io.enpass.app.R;
import java.util.ArrayList;
import java.util.List;
import net.sqlcipher.database.SQLiteDatabase;

public class WelcomeActivity extends AppCompatActivity {
    final String EXISTING_USER_ACTION = "2";
    final String MASTER_PASSWORD_ACTION = "3";
    final String NEW_USER_ACTION = "1";
    final int NEXT_BUTTON_ICON = 12345;
    final String READ_MORE_ACTION = "4";
    final int TAB_WIDTH = LoginActivity.TAB_WIDTH;
    RelativeLayout childLayout;
    int end;
    private float lastX;
    List<View> layouts;
    int mCurrentColor;
    Button mExistingKeychain;
    List<ButtonItems> mFirstPageButtons;
    Button mNewKeychain;
    List<ButtonItems> mSecondPageButtons;
    List<ButtonItems> mThirdPageButtons;
    RelativeLayout mWelcomeLayout;
    ValueAnimator valueAnimator;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.welcome);
        boolean rootedDeviceWarningAlreadyShown = EnpassApplication.getInstance().getAppSettings().isrootedDeviceWarningAlreadyShown();
        if (RootChecker.isDeviceRooted() && !rootedDeviceWarningAlreadyShown) {
            showRootedDeviceWarning();
        }
        this.childLayout = (RelativeLayout) findViewById(R.id.child_view);
        this.mWelcomeLayout = (RelativeLayout) findViewById(R.id.welcome_main_layout);
        Button aSetpasswordBtn = (Button) findViewById(R.id.welcome_first_time_btn);
        TextView tv_subtitle = (TextView) findViewById(R.id.welcome_subtitle);
        ((ImageView) findViewById(R.id.welcome_image)).setImageDrawable(ContextCompat.getDrawable(this, R.drawable.welcome_new1));
        tv_subtitle.setText(getString(R.string.welcome_page_text));
        Button aRestoreBtn = (Button) findViewById(R.id.welcome_restore_btn);
        RelativeLayout aBottomLayout = (RelativeLayout) findViewById(R.id.welcome_bottom);
        if (getResources().getConfiguration().smallestScreenWidthDp < 600) {
            ((LayoutParams) aBottomLayout.getLayoutParams()).width = -1;
        }
        this.end = Color.parseColor("#2196F3");
        this.mCurrentColor = Color.parseColor("#2196F3");
        this.layouts = new ArrayList();
        this.mFirstPageButtons = new ArrayList();
        if (EnpassApplication.getInstance().getAppSettings().getSharedData() != null) {
            Builder builder = new Builder(this);
            builder.setMessage(R.string.create_db_for_import_shareCard);
            builder.setTitle(R.string.app_name);
            builder.setNeutralButton(R.string.ok, new 1(this));
            builder.create().show();
        }
        this.mFirstPageButtons.add(new ButtonItems(12345, getResources().getString(R.string.first_user), "1"));
        this.mFirstPageButtons.add(new ButtonItems(12345, getResources().getString(R.string.existing_user), "2"));
        this.mSecondPageButtons = new ArrayList();
        this.mSecondPageButtons.add(new ButtonItems(12345, BuildConfig.FLAVOR, "1"));
        this.mThirdPageButtons = new ArrayList();
        this.mThirdPageButtons.add(new ButtonItems(12345, BuildConfig.FLAVOR, "3"));
        this.mThirdPageButtons.add(new ButtonItems(12345, BuildConfig.FLAVOR, "4"));
        aSetpasswordBtn.setOnClickListener(new 2(this));
        aRestoreBtn.setOnClickListener(new 3(this));
    }

    public void setViewBackground(int index) {
        switch (index) {
            case SQLiteDatabase.OPEN_READWRITE /*0*/:
                this.end = Color.parseColor("#2196F3");
                break;
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                this.end = Color.parseColor("#2196F3");
                break;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                this.end = Color.parseColor("#2196F3");
                break;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                this.end = Color.parseColor("#2196F3");
                break;
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                this.end = Color.parseColor("#2196F3");
                break;
            case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                this.end = Color.parseColor("#2196F3");
                break;
        }
        this.valueAnimator = ObjectAnimator.ofInt(this.mWelcomeLayout, "backgroundColor", new int[]{this.mCurrentColor, this.end});
        this.valueAnimator.setDuration(1000);
        this.valueAnimator.setEvaluator(new ArgbEvaluator());
        this.valueAnimator.addListener(new 4(this));
        this.valueAnimator.start();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        int aDeviceWidth = newConfig.smallestScreenWidthDp;
        if (newConfig.orientation != 2 || aDeviceWidth >= LoginActivity.TAB_WIDTH) {
            ((ImageView) findViewById(R.id.welcome_image)).setVisibility(0);
        } else {
            ImageView imageView = (ImageView) findViewById(R.id.welcome_image);
        }
        super.onConfigurationChanged(newConfig);
        EnpassApplication.getInstance().changeLocale(this);
    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }

    void showRootedDeviceWarning() {
        Builder alert = new Builder(this);
        alert.setCancelable(true);
        alert.setTitle(getString(R.string.warning));
        alert.setMessage(getString(R.string.device_root_msg));
        alert.setNeutralButton(getString(R.string.ok), new 5(this)).show();
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(EnpassApplication.getInstance().changeLocale(base));
    }
}
