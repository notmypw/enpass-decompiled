package com.dropbox.core.android;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Base64;
import com.box.androidsdk.content.BoxConstants;
import java.util.List;

public class DbxOfficialAppConnector {
    public static final String ACTION_DBXC_EDIT = "com.dropbox.android.intent.action.DBXC_EDIT";
    public static final String ACTION_DBXC_VIEW = "com.dropbox.android.intent.action.DBXC_VIEW";
    public static final String ACTION_SHOW_DROPBOX_PREVIEW = "com.dropbox.android.intent.action.SHOW_PREVIEW";
    public static final String ACTION_SHOW_UPGRADE = "com.dropbox.android.intent.action.SHOW_UPGRADE";
    private static final int CORRECT_USER = 1;
    private static final String[] DROPBOX_APP_SIGNATURES = new String[]{"308202223082018b02044bd207bd300d06092a864886f70d01010405003058310b3009060355040613025553310b3009060355040813024341311630140603550407130d53616e204672616e636973636f3110300e060355040a130744726f70626f783112301006035504031309546f6d204d65796572301e170d3130303432333230343930315a170d3430303431353230343930315a3058310b3009060355040613025553310b3009060355040813024341311630140603550407130d53616e204672616e636973636f3110300e060355040a130744726f70626f783112301006035504031309546f6d204d6579657230819f300d06092a864886f70d010101050003818d0030818902818100ac1595d0ab278a9577f0ca5a14144f96eccde75f5616f36172c562fab0e98c48ad7d64f1091c6cc11ce084a4313d522f899378d312e112a748827545146a779defa7c31d8c00c2ed73135802f6952f59798579859e0214d4e9c0554b53b26032a4d2dfc2f62540d776df2ea70e2a6152945fb53fef5bac5344251595b729d4810203010001300d06092a864886f70d01010405000381810055c425d94d036153203dc0bbeb3516f94563b102fff39c3d4ed91278db24fc4424a244c2e59f03bbfea59404512b8bf74662f2a32e37eafa2ac904c31f99cfc21c9ff375c977c432d3b6ec22776f28767d0f292144884538c3d5669b568e4254e4ed75d9054f75229ac9d4ccd0b7c3c74a34f07b7657083b2aa76225c0c56ffc", "308201e53082014ea00302010202044e17e115300d06092a864886f70d01010505003037310b30090603550406130255533110300e060355040a1307416e64726f6964311630140603550403130d416e64726f6964204465627567301e170d3131303730393035303331375a170d3431303730313035303331375a3037310b30090603550406130255533110300e060355040a1307416e64726f6964311630140603550403130d416e64726f696420446562756730819f300d06092a864886f70d010101050003818d003081890281810096759fe5abea6a0757039b92adc68d672efa84732c3f959408e12efa264545c61f23141026a6d01eceeeaa13ec7087087e5894a3363da8bf5c69ed93657a6890738a80998e4ca22dc94848f30e2d0e1890000ae2cddf543b20c0c3828deca6c7944b5ecd21a9d18c988b2b3e54517dafbc34b48e801bb1321e0fa49e4d575d7f0203010001300d06092a864886f70d0101050500038181002b6d4b65bcfa6ec7bac97ae6d878064d47b3f9f8da654995b8ef4c385bc4fbfbb7a987f60783ef0348760c0708acd4b7e63f0235c35a4fbcd5ec41b3b4cb295feaa7d5c27fa562a02562b7e1f4776b85147be3e295714986c4a9a07183f48ea09ae4d3ea31b88d0016c65b93526b9c45f2967c3d28dee1aff5a5b29b9c2c8639"};
    public static final String EXTRA_CALLING_PACKAGE = "com.dropbox.android.intent.extra.CALLING_PACKAGE";
    public static final String EXTRA_DROPBOX_PATH = "com.dropbox.android.intent.extra.DROPBOX_PATH";
    public static final String EXTRA_DROPBOX_READ_ONLY = "com.dropbox.android.intent.extra.READ_ONLY";
    public static final String EXTRA_DROPBOX_REV = "com.dropbox.android.intent.extra.DROPBOX_REV";
    public static final String EXTRA_DROPBOX_SESSION_ID = "com.dropbox.android.intent.extra.SESSION_ID";
    public static final String EXTRA_DROPBOX_UID = "com.dropbox.android.intent.extra.DROPBOX_UID";
    private static final Uri LOGGED_IN_URI = Uri.parse("content://com.dropbox.android.provider.SDK/is_user_logged_in/");
    private static final int MIN_OPENWITH_VERSION = 240607;
    private static final int NO_USER = 0;
    private static final int WRONG_USER = -1;
    protected String uid = null;

    public static class DbxOfficialAppInstallInfo {
        public final boolean supportsOpenWith;
        public final int versionCode;

        public DbxOfficialAppInstallInfo(boolean supportsOpenWith, int versionCode) {
            this.supportsOpenWith = supportsOpenWith;
            this.versionCode = versionCode;
        }

        public String toString() {
            return String.format("DbxOfficialAppInstallInfo(versionCode=%s, supportsOpenWith=%s)", new Object[]{Integer.valueOf(this.versionCode), Boolean.valueOf(this.supportsOpenWith)});
        }
    }

    public DbxOfficialAppConnector(String uid) throws DropboxUidNotInitializedException {
        if (uid == null || uid.length() == 0) {
            throw new DropboxUidNotInitializedException("Must initialize session's uid before constructing DbxOfficialAppConnector");
        }
        this.uid = uid;
    }

    protected void addExtrasToIntent(Context context, Intent intent) {
        intent.putExtra(EXTRA_DROPBOX_UID, this.uid);
        intent.putExtra(EXTRA_CALLING_PACKAGE, context.getPackageName());
    }

    public static DbxOfficialAppInstallInfo isInstalled(Context context) {
        PackageInfo dropboxPackage = getDropboxAppPackage(context, AuthActivity.getOfficialAuthIntent());
        if (dropboxPackage == null) {
            return null;
        }
        int versionCode = dropboxPackage.versionCode;
        return new DbxOfficialAppInstallInfo(versionCode >= MIN_OPENWITH_VERSION, versionCode);
    }

    private static int getLoggedinState(Context context, String uid) {
        Cursor cursor = context.getContentResolver().query(LOGGED_IN_URI.buildUpon().appendPath(uid).build(), null, null, null, null);
        if (cursor == null) {
            return NO_USER;
        }
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex("logged_in"));
    }

    public static boolean isAnySignedIn(Context context) {
        return getLoggedinState(context, BoxConstants.ROOT_FOLDER_ID) != 0;
    }

    public static Intent getDropboxPlayStoreIntent() {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("market://details?id=com.dropbox.android"));
        return intent;
    }

    public boolean isSignedIn(Context context) {
        if (getLoggedinState(context, this.uid) == CORRECT_USER) {
            return true;
        }
        return false;
    }

    protected Intent launchDropbox(Context context) {
        Intent i = context.getPackageManager().getLaunchIntentForPackage("com.dropbox.android");
        if (getDropboxAppPackage(context, i) == null) {
            return null;
        }
        return i;
    }

    public Intent getUpgradeAccountIntent(Context context) {
        Intent upgradeIntent = new Intent(ACTION_SHOW_UPGRADE);
        addExtrasToIntent(context, upgradeIntent);
        if (getDropboxAppPackage(context, upgradeIntent) != null) {
            return upgradeIntent;
        }
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("https://www.dropbox.com/upgrade?oqa=upeaoq"));
        return intent;
    }

    static PackageInfo getDropboxAppPackage(Context context, Intent intent) {
        PackageManager manager = context.getPackageManager();
        List<ResolveInfo> infos = manager.queryIntentActivities(intent, NO_USER);
        if (infos == null || CORRECT_USER != infos.size()) {
            return null;
        }
        ResolveInfo resolveInfo = manager.resolveActivity(intent, NO_USER);
        if (resolveInfo == null) {
            return null;
        }
        try {
            PackageInfo packageInfo = manager.getPackageInfo(resolveInfo.activityInfo.packageName, 64);
            Signature[] signatureArr = packageInfo.signatures;
            int length = signatureArr.length;
            for (int i = NO_USER; i < length; i += CORRECT_USER) {
                Signature signature = signatureArr[i];
                String[] strArr = DROPBOX_APP_SIGNATURES;
                int length2 = strArr.length;
                for (int i2 = NO_USER; i2 < length2; i2 += CORRECT_USER) {
                    if (strArr[i2].equals(signature.toCharsString())) {
                        return packageInfo;
                    }
                }
            }
            return null;
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    public Intent getPreviewFileIntent(Context context, String path, String lastRev) {
        Intent previewIntent = new Intent(ACTION_SHOW_DROPBOX_PREVIEW);
        addExtrasToIntent(context, previewIntent);
        previewIntent.putExtra(EXTRA_DROPBOX_PATH, path);
        previewIntent.putExtra(EXTRA_DROPBOX_REV, lastRev);
        if (getDropboxAppPackage(context, previewIntent) == null) {
            return null;
        }
        return previewIntent;
    }

    public static Intent generateOpenWithIntentFromUtmContent(String UtmContent) throws DropboxParseException {
        try {
            byte[] b = Base64.decode(UtmContent, NO_USER);
            Parcel parcel = Parcel.obtain();
            parcel.unmarshall(b, NO_USER, b.length);
            parcel.setDataPosition(NO_USER);
            Bundle bundle = parcel.readBundle();
            parcel.recycle();
            if (bundle == null) {
                throw new DropboxParseException("Could not extract bundle from UtmContent");
            }
            String action = bundle.getString("_action");
            if (action == null) {
                throw new DropboxParseException("_action was not present in bundle");
            }
            bundle.remove("_action");
            Uri uri = (Uri) bundle.getParcelable("_uri");
            if (uri == null) {
                throw new DropboxParseException("_uri was not present in bundle");
            }
            bundle.remove("_uri");
            String type = bundle.getString("_type");
            if (type == null) {
                throw new DropboxParseException("_type was not present in bundle");
            }
            bundle.remove("_type");
            Intent openWithIntent = new Intent(action);
            openWithIntent.setDataAndType(uri, type);
            openWithIntent.putExtras(bundle);
            return openWithIntent;
        } catch (IllegalArgumentException ex) {
            throw new DropboxParseException("UtmContent was not base64 encoded: " + ex.getMessage());
        }
    }
}
