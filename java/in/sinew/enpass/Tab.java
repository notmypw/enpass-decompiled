package in.sinew.enpass;

import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.WebView;
import com.github.clans.fab.BuildConfig;
import io.enpass.app.R;

public class Tab {
    WebView mBrowser;
    Bitmap mScreenImg;
    String mTitle = BuildConfig.FLAVOR;
    String mUrl = BuildConfig.FLAVOR;

    public Tab(Context aContext, WebView browser, Bitmap img) {
        this.mTitle = browser.getTitle();
        this.mUrl = browser.getUrl();
        if (this.mTitle == null || this.mTitle.isEmpty()) {
            this.mTitle = aContext.getString(R.string.tab_blank_page);
        }
        this.mBrowser = browser;
        this.mScreenImg = img;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public WebView getWebView() {
        return this.mBrowser;
    }

    public Bitmap getScrImg() {
        return this.mScreenImg;
    }
}
