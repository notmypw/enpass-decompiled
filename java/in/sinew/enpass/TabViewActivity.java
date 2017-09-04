package in.sinew.enpass;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.GridView;
import com.box.androidsdk.content.models.BoxRealTimeServer;
import in.sinew.enpassui.adapter.TabAdapter;
import io.enpass.app.R;

public class TabViewActivity extends EnpassActivity {
    TabAdapter adapter;
    WebView[] deletedView;
    private final String mNewTab = "new tab";
    private final String mOldTab = "old tab";
    private final String mType = BoxRealTimeServer.FIELD_TYPE;
    int tabCount = 1;
    GridView tabs;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_list);
        setTitle(getString(R.string.tabs));
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        this.tabs = (GridView) findViewById(R.id.tab_view_list);
        this.adapter = new TabAdapter(this, EnpassApplication.getInstance().mTabs);
        this.tabs.setAdapter(this.adapter);
        this.tabs.setOnItemClickListener(new 1(this));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.browser_menus, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_tab /*2131296783*/:
                Intent returnIntent = new Intent();
                returnIntent.putExtra(BoxRealTimeServer.FIELD_TYPE, "new tab");
                setResult(-1, returnIntent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void removeTab(int pos) {
        EnpassApplication.getInstance().removeTab(pos);
        this.adapter.notifyDataSetChanged();
        if (EnpassApplication.getInstance().mTabs.size() < 1) {
            finish();
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        EnpassApplication.getInstance().changeLocale(this);
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(EnpassApplication.getInstance().changeLocale(base));
    }
}
