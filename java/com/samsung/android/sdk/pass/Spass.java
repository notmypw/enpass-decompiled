package com.samsung.android.sdk.pass;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Parcelable;
import android.util.Log;
import com.samsung.android.sdk.SsdkInterface;
import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.SsdkVendorCheck;

public class Spass implements SsdkInterface {
    public static final int DEVICE_FINGERPRINT = 0;
    public static final int DEVICE_FINGERPRINT_CUSTOMIZED_DIALOG = 2;
    public static final int DEVICE_FINGERPRINT_FINGER_INDEX = 1;
    public static final int DEVICE_FINGERPRINT_UNIQUE_ID = 3;
    private SpassFingerprint a;

    public int getVersionCode() {
        return 5;
    }

    public String getVersionName() {
        Object[] objArr = new Object[DEVICE_FINGERPRINT_UNIQUE_ID];
        objArr[DEVICE_FINGERPRINT] = Integer.valueOf(DEVICE_FINGERPRINT_FINGER_INDEX);
        objArr[DEVICE_FINGERPRINT_FINGER_INDEX] = Integer.valueOf(DEVICE_FINGERPRINT_FINGER_INDEX);
        objArr[DEVICE_FINGERPRINT_CUSTOMIZED_DIALOG] = Integer.valueOf(DEVICE_FINGERPRINT_UNIQUE_ID);
        return String.format("%d.%d.%d", objArr);
    }

    public void initialize(Context context) throws SsdkUnsupportedException {
        if (this.a == null) {
            if (context == null) {
                throw new IllegalArgumentException("context passed is null.");
            }
            int i = -1;
            try {
                i = context.getPackageManager().getPackageInfo("com.samsung.android.providers.context", 128).versionCode;
                try {
                    Log.d("SM_SDK", "versionCode: " + i);
                    if (i <= DEVICE_FINGERPRINT_FINGER_INDEX) {
                        Log.d("SM_SDK", "Add com.samsung.android.providers.context.permission.WRITE_USE_APP_FEATURE_SURVEY permission");
                    } else if (context.checkCallingOrSelfPermission("com.samsung.android.providers.context.permission.WRITE_USE_APP_FEATURE_SURVEY") != 0) {
                        throw new SecurityException();
                    } else {
                        Parcelable contentValues = new ContentValues();
                        String name = getClass().getPackage().getName();
                        String str = context.getPackageName() + "#" + getVersionCode();
                        contentValues.put("app_id", name);
                        contentValues.put("feature", str);
                        Intent intent = new Intent();
                        intent.setAction("com.samsung.android.providers.context.log.action.USE_APP_FEATURE_SURVEY");
                        intent.putExtra("data", contentValues);
                        intent.setPackage("com.samsung.android.providers.context");
                        context.sendBroadcast(intent);
                    }
                    if (SsdkVendorCheck.isSamsungDevice()) {
                        this.a = new SpassFingerprint(context);
                        SpassFingerprint spassFingerprint = this.a;
                        if (!SpassFingerprint.a()) {
                            throw new SsdkUnsupportedException("This device does not provide FingerprintService.", DEVICE_FINGERPRINT_FINGER_INDEX);
                        }
                        return;
                    }
                    throw new SsdkUnsupportedException("This is not Samsung device.", DEVICE_FINGERPRINT);
                } catch (SecurityException e) {
                    throw new SecurityException("com.samsung.android.providers.context.permission.WRITE_USE_APP_FEATURE_SURVEY permission is required.");
                } catch (NullPointerException e2) {
                    throw new IllegalArgumentException("context is not valid.");
                }
            } catch (NameNotFoundException e3) {
                Log.d("SM_SDK", "Could not find ContextProvider");
            }
        }
    }

    public boolean isFeatureEnabled(int i) {
        if (this.a == null) {
            throw new IllegalStateException("initialize() is not Called first.");
        }
        switch (i) {
            case DEVICE_FINGERPRINT /*0*/:
                SpassFingerprint spassFingerprint = this.a;
                return SpassFingerprint.a();
            case DEVICE_FINGERPRINT_FINGER_INDEX /*1*/:
            case DEVICE_FINGERPRINT_CUSTOMIZED_DIALOG /*2*/:
                return this.a.b();
            case DEVICE_FINGERPRINT_UNIQUE_ID /*3*/:
                return this.a.c();
            default:
                throw new IllegalArgumentException("type passed is not valid");
        }
    }
}
