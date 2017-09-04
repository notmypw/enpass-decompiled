package in.sinew.enpass.chromeconnector;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat.Builder;
import com.box.androidsdk.content.models.BoxUser;
import in.sinew.enpass.MainActivity;
import io.enpass.app.R;
import java.io.IOException;
import java.net.InetSocketAddress;

public class ChromeConnectorForegroundService extends Service {
    private static final String LOG_TAG = "ForegroundService";
    private static Context ctx;
    public static ChromeDatabaseHelper mChromeDbHelper;
    static EnpassWebSocketServer mSocketServer;
    String TAG = "ChromeConnectorService";

    public static ChromeDatabaseHelper getChromeDatabaseHelper() {
        if (mChromeDbHelper == null && ctx != null) {
            mChromeDbHelper = new ChromeDatabaseHelper(ctx);
        }
        return mChromeDbHelper;
    }

    public void onCreate() {
        super.onCreate();
        ctx = this;
        mChromeDbHelper = getChromeDatabaseHelper();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        ctx = this;
        if (intent != null) {
            String address = intent.getStringExtra(BoxUser.FIELD_ADDRESS);
            int port = intent.getIntExtra("port", -1);
            String cryptoReply = intent.getStringExtra("cryptoReply");
            String messageReply = intent.getStringExtra("messageReply");
            if (address != null && port != -1) {
                Builder mBuilder = new Builder(this).setSmallIcon(R.drawable.notification).setColor(getResources().getColor(2131099740)).setAutoCancel(false).setContentTitle(getString(R.string.accessibility_service_label)).setContentText(getString(R.string.autofill_service_chromebook));
                Intent notificationIntent = new Intent(this, MainActivity.class);
                notificationIntent.setFlags(268468224);
                mBuilder.setContentIntent(PendingIntent.getActivity(this, 0, notificationIntent, 0));
                NotificationManager mNotificationManager = (NotificationManager) getSystemService("notification");
                Notification notification = mBuilder.build();
                notification.flags = 32;
                mNotificationManager.notify(350, notification);
                startForeground(350, notification);
                mSocketServer = startServer(address, port, this);
            } else if (cryptoReply != null && mSocketServer != null) {
                try {
                    mSocketServer.sendToAll(cryptoReply);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (!(messageReply == null || mSocketServer == null)) {
                try {
                    mSocketServer.sendToAll(messageReply);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        return 1;
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    public void onDestroy() {
        if (mSocketServer != null) {
            try {
                mSocketServer.stop();
                mSocketServer.onDisconnect();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
        super.onDestroy();
    }

    public static EnpassWebSocketServer getSocketServer() {
        return mSocketServer;
    }

    public EnpassWebSocketServer startServer(String address, int port, Context context) {
        EnpassWebSocketServer server = new EnpassWebSocketServer(new InetSocketAddress(address, port), context);
        server.start();
        return server;
    }
}
