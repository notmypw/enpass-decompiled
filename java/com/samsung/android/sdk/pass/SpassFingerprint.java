package com.samsung.android.sdk.pass;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.util.SparseArray;
import com.box.androidsdk.content.models.BoxSharedLink;
import com.samsung.android.fingerprint.IFingerprintClient;
import com.samsung.android.sdk.pass.support.IFingerprintManagerProxy;
import com.samsung.android.sdk.pass.support.SdkSupporter;
import com.samsung.android.sdk.pass.support.v1.FingerprintManagerProxyFactory;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;

public class SpassFingerprint {
    public static final int STATUS_AUTHENTIFICATION_FAILED = 16;
    public static final int STATUS_AUTHENTIFICATION_PASSWORD_SUCCESS = 100;
    public static final int STATUS_AUTHENTIFICATION_SUCCESS = 0;
    public static final int STATUS_QUALITY_FAILED = 12;
    public static final int STATUS_SENSOR_FAILED = 7;
    public static final int STATUS_TIMEOUT_FAILED = 4;
    public static final int STATUS_USER_CANCELLED = 8;
    public static final int STATUS_USER_CANCELLED_BY_TOUCH_OUTSIDE = 13;
    private static boolean l = false;
    private static String m = "sdk_version";
    private static boolean n = false;
    private static boolean o = false;
    private IFingerprintManagerProxy a;
    private Context b;
    private int c = -1;
    private ArrayList d = null;
    private String e = null;
    private int f = -1;
    private String g = null;
    private int h = -1;
    private int[] i = null;
    private boolean j = false;
    private int k = STATUS_AUTHENTIFICATION_SUCCESS;
    private IBinder p = null;
    private Dialog q = null;
    private IFingerprintClient r = null;
    private Bundle s = null;
    private IFingerprintClient t = null;
    private Handler u;

    public SpassFingerprint(Context context) {
        this.b = context;
        if (this.b == null) {
            throw new IllegalArgumentException("context is null.");
        }
        try {
            this.b.getPackageManager();
            if (!n) {
                o = this.b.getPackageManager().hasSystemFeature("com.sec.feature.fingerprint_manager_service");
                n = true;
            }
            if (o) {
                try {
                    Class cls = Class.forName("com.samsung.android.fingerprint.FingerprintManager");
                    Method method = cls.getMethod("getInstance", new Class[]{Context.class});
                    Method method2 = cls.getMethod("getVersion", new Class[STATUS_AUTHENTIFICATION_SUCCESS]);
                    Object invoke = method.invoke(null, new Object[]{this.b});
                    if (invoke != null) {
                        this.k = ((Integer) method2.invoke(invoke, new Object[STATUS_AUTHENTIFICATION_SUCCESS])).intValue();
                    }
                } catch (Exception e) {
                    Log.w("SpassFingerprintSDK", "getVersion failed : " + e);
                }
                int i = this.k >>> 24;
                if (i > 1) {
                    i = 1;
                }
                if (i > 0) {
                    this.a = FingerprintManagerProxyFactory.create(this.b);
                }
                this.u = new Handler(context.getMainLooper());
            }
            SdkSupporter.copyStaticFields(this, SpassFingerprint.class, "com.samsung.android.fingerprint.FingerprintManager", "EVENT_IDENTIFY_");
            if (this.a != null) {
                try {
                    if (this.a.getSensorType() == 2) {
                        l = true;
                    }
                } catch (Exception e2) {
                }
            }
        } catch (NullPointerException e3) {
            throw new IllegalArgumentException("context is not valid.");
        }
    }

    static /* synthetic */ int a(int i) {
        switch (i) {
            case STATUS_AUTHENTIFICATION_SUCCESS /*0*/:
                return STATUS_AUTHENTIFICATION_SUCCESS;
            case STATUS_TIMEOUT_FAILED /*4*/:
                return STATUS_TIMEOUT_FAILED;
            case STATUS_SENSOR_FAILED /*7*/:
                return STATUS_SENSOR_FAILED;
            case STATUS_USER_CANCELLED /*8*/:
                return STATUS_USER_CANCELLED;
            case STATUS_QUALITY_FAILED /*12*/:
                return STATUS_QUALITY_FAILED;
            case STATUS_USER_CANCELLED_BY_TOUCH_OUTSIDE /*13*/:
                return STATUS_USER_CANCELLED_BY_TOUCH_OUTSIDE;
            case STATUS_AUTHENTIFICATION_PASSWORD_SUCCESS /*100*/:
                return STATUS_AUTHENTIFICATION_PASSWORD_SUCCESS;
            default:
                return STATUS_AUTHENTIFICATION_FAILED;
        }
    }

    static boolean a() {
        return o;
    }

    private boolean a(String str) {
        String packageName = this.b.getPackageName();
        try {
            Resources resourcesForApplication = this.b.getPackageManager().getResourcesForApplication(packageName);
            if (resourcesForApplication == null) {
                return false;
            }
            try {
                int identifier = resourcesForApplication.getIdentifier(str, "drawable", packageName);
                return (identifier == 0 || identifier == -1 || BitmapFactory.decodeResource(resourcesForApplication, identifier) == null) ? false : true;
            } catch (NotFoundException e) {
                return false;
            }
        } catch (NameNotFoundException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    private synchronized void f() throws UnsupportedOperationException {
        if (!o) {
            throw new UnsupportedOperationException("Fingerprint Service is not supported in the platform.");
        } else if (this.a == null) {
            throw new UnsupportedOperationException("Fingerprint Service is not running on the device.");
        }
    }

    final boolean b() {
        f();
        return this.k >= 16843008;
    }

    final boolean c() {
        Object obj;
        f();
        try {
            Class.forName("com.samsung.android.fingerprint.FingerprintManager").getMethod("isSupportFingerprintIds", new Class[STATUS_AUTHENTIFICATION_SUCCESS]);
            obj = 1;
        } catch (Throwable e) {
            Log.w("SpassFingerprintSDK", e);
            obj = STATUS_AUTHENTIFICATION_SUCCESS;
        }
        return obj != null ? this.a.isSupportFingerprintIds() : b();
    }

    public void cancelIdentify() {
        f();
        if (this.p == null && this.r == null && this.q == null) {
            throw new IllegalStateException("No Identify request.");
        }
        if (this.p != null) {
            if (!this.a.cancel(this.p)) {
                throw new IllegalStateException("cancel() returned RESULT_FAILED due to FingerprintService Error.");
            }
        } else if (!(this.r == null && this.q == null)) {
            this.a.notifyAppActivityState(STATUS_TIMEOUT_FAILED, null);
        }
        this.p = null;
        this.r = null;
        this.q = null;
    }

    public int getIdentifiedFingerprintIndex() {
        f();
        if (this.c != -1) {
            return this.c;
        }
        throw new IllegalStateException("FingerprintIndex is Invalid. This API must be called inside IdentifyListener.onFinished() only.");
    }

    public SparseArray getRegisteredFingerprintName() {
        f();
        SparseArray sparseArray = new SparseArray();
        int enrolledFingers = this.a.getEnrolledFingers();
        if (enrolledFingers <= 0) {
            return null;
        }
        for (int i = 1; i <= 10; i++) {
            if (((1 << i) & enrolledFingers) != 0) {
                sparseArray.put(i, this.a.getIndexName(i));
            }
        }
        return sparseArray;
    }

    public SparseArray getRegisteredFingerprintUniqueID() {
        f();
        if (c()) {
            SparseArray sparseArray = new SparseArray();
            int enrolledFingers = this.a.getEnrolledFingers();
            if (enrolledFingers <= 0) {
                return null;
            }
            for (int i = 1; i <= 10; i++) {
                if (((1 << i) & enrolledFingers) != 0) {
                    sparseArray.put(i, this.a.getFingerprintId(i));
                }
            }
            return sparseArray;
        }
        throw new IllegalStateException("getRegisteredFingerprintUniqueID is not supported.");
    }

    public boolean hasRegisteredFinger() {
        f();
        return this.a.getEnrolledFingers() != 0;
    }

    public void registerFinger(Context context, RegisterListener registerListener) {
        f();
        if (context == null) {
            throw new IllegalArgumentException("activityContext passed is null.");
        } else if (registerListener == null) {
            throw new IllegalArgumentException("listener passed is null.");
        } else {
            if (this.a.isEnrolling()) {
                this.a.notifyEnrollEnd();
            }
            try {
                context.getPackageManager();
                try {
                    this.a.startEnrollActivity(context, new b(registerListener), toString());
                } catch (UndeclaredThrowableException e) {
                    throw new IllegalArgumentException("activityContext is invalid");
                }
            } catch (NullPointerException e2) {
                throw new IllegalArgumentException("activityContext is invalid");
            }
        }
    }

    public void setCanceledOnTouchOutside(boolean z) {
        f();
        if (b()) {
            this.j = z;
            return;
        }
        throw new IllegalStateException("setCanceledOnTouchOutside is not supported.");
    }

    public void setDialogBgTransparency(int i) {
        f();
        if (!b()) {
            throw new IllegalStateException("setDialogBGTransparency is not supported.");
        } else if (i < 0 || i > 255) {
            throw new IllegalArgumentException("the transparency passed is not valid.");
        } else {
            this.h = i;
        }
    }

    public void setDialogIcon(String str) {
        f();
        if (!b()) {
            throw new IllegalStateException("setDialogIcon is not supported.");
        } else if (str == null) {
            throw new IllegalArgumentException("the iconName passed is null.");
        } else if (a(str)) {
            this.g = str;
        } else {
            throw new IllegalArgumentException("the iconName passed is not valid.");
        }
    }

    public void setDialogTitle(String str, int i) {
        f();
        if (!b()) {
            throw new IllegalStateException("setDialogTitle is not supported.");
        } else if (str == null) {
            throw new IllegalArgumentException("the titletext passed is null.");
        } else if (str.length() > 256) {
            throw new IllegalArgumentException("the title text passed is longer than 256 characters.");
        } else if ((i >>> 24) != 0) {
            throw new IllegalArgumentException("alpha value is not supported in the titleColor.");
        } else {
            this.e = str;
            this.f = -16777216 + i;
        }
    }

    public void setIntendedFingerprintIndex(ArrayList arrayList) {
        f();
        if (arrayList == null) {
            Log.w("SpassFingerprintSDK", "requestedIndex is null. Identify is carried out for all indexes.");
        } else if (b()) {
            this.d = new ArrayList();
            for (int i = STATUS_AUTHENTIFICATION_SUCCESS; i < arrayList.size(); i++) {
                this.d.add((Integer) arrayList.get(i));
            }
        } else {
            throw new IllegalStateException("setIntendedFingerprintIndex is not supported.");
        }
    }

    public void startIdentify(IdentifyListener identifyListener) {
        f();
        if (identifyListener == null) {
            throw new IllegalArgumentException("listener passed is null.");
        }
        if (this.t == null) {
            this.t = new b(this, identifyListener, (byte) 0);
        }
        if (this.d != null) {
            this.i = new int[this.d.size()];
            for (int i = STATUS_AUTHENTIFICATION_SUCCESS; i < this.d.size(); i++) {
                this.i[i] = ((Integer) this.d.get(i)).intValue();
            }
        }
        this.s = new a(this.b.getPackageName()).a(this.i).a().b();
        this.p = this.a.registerClient(this.t, this.s);
        if (this.p == null) {
            throw new IllegalStateException("failed because registerClient returned null.");
        }
        int identify = this.a.identify(this.p, null);
        if (identify == -2) {
            throw new IllegalStateException("Identify request is denied because a previous request is still in progress.");
        } else if (identify == 51) {
            throw new SpassInvalidStateException("Identify request is denied because 5 identify attempts are failed.", 1);
        } else if (identify != 0) {
            if (this.a.hasPendingCommand()) {
                this.a.cancel(this.p);
            }
            this.a.unregisterClient(this.p);
            this.p = null;
            throw new IllegalStateException("Identify operation is failed.");
        } else {
            ((b) this.t).a(identifyListener);
            if (this.d != null) {
                this.d = null;
            }
            if (this.i != null) {
                this.i = null;
            }
        }
    }

    public void startIdentifyWithDialog(Context context, IdentifyListener identifyListener, boolean z) {
        f();
        if (context == null) {
            throw new IllegalArgumentException("activityContext passed is null.");
        } else if (identifyListener == null) {
            throw new IllegalArgumentException("listener passed is null.");
        } else {
            try {
                context.getPackageManager();
                if (b()) {
                    if (this.d != null && this.d.size() > 0) {
                        this.i = new int[this.d.size()];
                        for (int i = STATUS_AUTHENTIFICATION_SUCCESS; i < this.d.size(); i++) {
                            this.i[i] = ((Integer) this.d.get(i)).intValue();
                        }
                    }
                    this.r = new b(this, identifyListener, (byte) 0);
                    try {
                        Bundle bundle = new Bundle();
                        bundle.putBoolean(BoxSharedLink.FIELD_PASSWORD, z);
                        bundle.putString("packageName", context.getPackageName());
                        bundle.putString(m, "Pass-v" + String.format("%d.%d.%d", new Object[]{Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(3)}));
                        if (this.i != null) {
                            bundle.putIntArray("request_template_index_list", this.i);
                        }
                        if (this.e != null) {
                            bundle.putString("titletext", this.e);
                        }
                        if (this.f != -1) {
                            bundle.putInt("titlecolor", this.f);
                        }
                        if (this.g != null) {
                            bundle.putString("iconname", this.g);
                        }
                        if (this.h != -1) {
                            bundle.putInt("transparency", this.h);
                        }
                        if (this.j) {
                            bundle.putBoolean("touchoutside", this.j);
                        }
                        if (this.a.identifyWithDialog(context, this.r, bundle) != 0) {
                            throw new IllegalStateException("Identify operation is failed.");
                        }
                    } finally {
                        if (this.d != null) {
                            this.d = null;
                        }
                        if (this.i != null) {
                            this.i = null;
                        }
                        if (this.e != null) {
                            this.e = null;
                        }
                        if (this.f != -1) {
                            this.f = -1;
                        }
                        if (this.h != -1) {
                            this.h = -1;
                        }
                        if (this.g != null) {
                            this.g = null;
                        }
                        this.j = false;
                    }
                } else {
                    c cVar = new c(this, identifyListener, (byte) 0);
                    this.q = this.a.showIdentifyDialog(context, cVar, null, z);
                    if (this.q == null) {
                        throw new IllegalStateException("Identify operation is failed.");
                    }
                    this.q.setOnDismissListener(new a(cVar));
                    this.q.show();
                }
            } catch (NullPointerException e) {
                throw new IllegalArgumentException("activityContext is invalid");
            }
        }
    }
}
