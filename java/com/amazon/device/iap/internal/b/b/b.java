package com.amazon.device.iap.internal.b.b;

import com.amazon.android.framework.exception.KiwiException;
import com.amazon.device.iap.internal.b.e;
import com.example.zxcvbnjlib.BuildConfig;

/* compiled from: PurchaseItemCommandV1 */
public final class b extends a {
    public b(e eVar, String str) {
        super(eVar, BuildConfig.VERSION_NAME, str);
    }

    protected void preExecution() throws KiwiException {
        super.preExecution();
        com.amazon.device.iap.internal.c.b.a().b(c());
    }
}
