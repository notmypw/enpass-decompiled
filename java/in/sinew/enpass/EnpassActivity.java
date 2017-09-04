package in.sinew.enpass;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class EnpassActivity extends AppCompatActivity {
    private String TAG = "EnpassActivity";
    boolean inMultiWindowMode = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EnpassApplication.getInstance().getAppSettings().isScreenshotEnabledPref()) {
            getWindow().setFlags(8192, 8192);
        }
    }

    @SuppressLint({"NewApi"})
    protected void onResume() {
        super.onResume();
        EnpassApplication.getInstance().onActivityResume(this);
    }

    @SuppressLint({"NewApi"})
    protected void onPause() {
        super.onPause();
        EnpassApplication.getInstance().onActivityPause(this);
    }

    public void onUserInteraction() {
        super.onUserInteraction();
        EnpassApplication.getInstance().touch();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        this.inMultiWindowMode = isInMultiWindowMode;
        EnpassApplication.getInstance().goingTomultiWindowMode(isInMultiWindowMode);
    }
}
