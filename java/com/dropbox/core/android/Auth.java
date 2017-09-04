package com.dropbox.core.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.github.clans.fab.BuildConfig;
import java.util.Arrays;
import net.sqlcipher.database.SQLiteDatabase;

public class Auth {
    public static void startOAuth2Authentication(Context context, String appKey) {
        startOAuth2Authentication(context, appKey, null, null, null);
    }

    public static void startOAuth2Authentication(Context context, String appKey, String desiredUid, String[] alreadyAuthedUids, String sessionId) {
        if (!AuthActivity.checkAppBeforeAuth(context, appKey, true)) {
            return;
        }
        if (alreadyAuthedUids == null || !Arrays.asList(alreadyAuthedUids).contains(desiredUid)) {
            Context context2 = context;
            String str = appKey;
            String str2 = desiredUid;
            String[] strArr = alreadyAuthedUids;
            String str3 = sessionId;
            Intent intent = AuthActivity.makeIntent(context2, str, str2, strArr, str3, "www.dropbox.com", "1");
            if (!(context instanceof Activity)) {
                intent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
            }
            context.startActivity(intent);
            return;
        }
        throw new IllegalArgumentException("desiredUid cannot be present in alreadyAuthedUids");
    }

    public static String getOAuth2Token() {
        Intent data = AuthActivity.result;
        if (data == null) {
            return null;
        }
        String token = data.getStringExtra(AuthActivity.EXTRA_ACCESS_TOKEN);
        String secret = data.getStringExtra(AuthActivity.EXTRA_ACCESS_SECRET);
        String uid = data.getStringExtra(AuthActivity.EXTRA_UID);
        if (token == null || token.equals(BuildConfig.FLAVOR) || secret == null || secret.equals(BuildConfig.FLAVOR) || uid == null || uid.equals(BuildConfig.FLAVOR)) {
            return null;
        }
        return secret;
    }

    public static String getUid() {
        Intent data = AuthActivity.result;
        if (data == null) {
            return null;
        }
        String token = data.getStringExtra(AuthActivity.EXTRA_ACCESS_TOKEN);
        String secret = data.getStringExtra(AuthActivity.EXTRA_ACCESS_SECRET);
        String uid = data.getStringExtra(AuthActivity.EXTRA_UID);
        if (token == null || token.equals(BuildConfig.FLAVOR) || secret == null || secret.equals(BuildConfig.FLAVOR) || uid == null || uid.equals(BuildConfig.FLAVOR)) {
            return null;
        }
        return uid;
    }
}
