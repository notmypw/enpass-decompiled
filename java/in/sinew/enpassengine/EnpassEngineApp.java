package in.sinew.enpassengine;

import android.app.Application;
import android.content.Context;

public class EnpassEngineApp extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        context.getApplicationContext();
    }

    public static Context getEngineAppContext() {
        return context;
    }
}
