package in.sinew.enpass;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import io.enpass.app.R;

public class ProductTour extends AppCompatActivity {
    private static final int NUM_PAGES = 5;
    MyPageAdapter adapter;
    Integer[] colors;
    LinearLayout mDotsView;
    LinearLayout mDotsView2;
    TextView tv_done;
    TextView tv_next;
    TextView tv_skip;
    ViewPager vp;

    public ProductTour() {
        Integer[] numArr = new Integer[NUM_PAGES];
        numArr[0] = Integer.valueOf(Color.parseColor("#449BFB"));
        numArr[1] = Integer.valueOf(Color.parseColor("#FF9800"));
        numArr[2] = Integer.valueOf(Color.parseColor("#7C4DFF"));
        numArr[3] = Integer.valueOf(Color.parseColor("#DD1C5E"));
        numArr[4] = Integer.valueOf(Color.parseColor("#4CAF50"));
        this.colors = numArr;
    }

    protected void onCreate(Bundle savedInstanceState) {
        int i;
        if (EnpassApplication.getInstance().isRunningOnChromebook() || !getResources().getBoolean(2131034117)) {
            setRequestedOrientation(3);
        } else {
            setRequestedOrientation(1);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_tour);
        this.tv_skip = (TextView) findViewById(R.id.product_tour_tv_skip);
        this.tv_next = (TextView) findViewById(R.id.product_tour_tv_next);
        this.tv_done = (TextView) findViewById(R.id.product_tour_tv_done);
        this.mDotsView = (LinearLayout) findViewById(R.id.radio_group_view);
        this.mDotsView2 = (LinearLayout) findViewById(R.id.radio_group_view_bottom);
        this.vp = (ViewPager) findViewById(R.id.product_tour_vpager);
        this.adapter = new MyPageAdapter(this, getSupportFragmentManager());
        this.vp.setAdapter(this.adapter);
        this.vp.setBackgroundColor(Color.parseColor("#449BFB"));
        for (i = 0; i < NUM_PAGES; i++) {
            ImageView aSingleDot = new ImageView(this);
            LayoutParams prms = new LayoutParams(-2, -2);
            prms.setMargins(8, 0, 8, 0);
            aSingleDot.setLayoutParams(prms);
            aSingleDot.setBackgroundResource(R.drawable.dot);
            if (i == 0) {
                aSingleDot.setBackgroundResource(R.drawable.solid_dot);
            }
            this.mDotsView.addView(aSingleDot, i);
        }
        for (i = 0; i < NUM_PAGES; i++) {
            aSingleDot = new ImageView(this);
            prms = new LayoutParams(-2, -2);
            prms.setMargins(8, 0, 8, 0);
            aSingleDot.setLayoutParams(prms);
            aSingleDot.setBackgroundResource(R.drawable.dot);
            if (i == 0) {
                aSingleDot.setBackgroundResource(R.drawable.solid_dot);
            }
            this.mDotsView2.addView(aSingleDot, i);
        }
        this.tv_skip.setOnClickListener(new 1(this));
        this.tv_next.setOnClickListener(new 2(this));
        this.tv_done.setOnClickListener(new 3(this));
        this.vp.addOnPageChangeListener(new 4(this));
    }

    protected void onResume() {
        if (EnpassApplication.getInstance().isRunningOnChromebook() || !getResources().getBoolean(2131034117)) {
            setRequestedOrientation(3);
        }
        super.onResume();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        int aDeviceWidth = newConfig.smallestScreenWidthDp;
        if (newConfig.orientation == 2) {
            super.onConfigurationChanged(newConfig);
            EnpassApplication.getInstance().changeLocale(this);
        } else {
            super.onConfigurationChanged(newConfig);
            EnpassApplication.getInstance().changeLocale(this);
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.setFlags(67108864);
        startActivity(intent);
        finish();
        System.exit(0);
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(EnpassApplication.getInstance().changeLocale(base));
    }
}
