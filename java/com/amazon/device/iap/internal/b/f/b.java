package com.amazon.device.iap.internal.b.f;

import com.amazon.device.iap.internal.b.e;
import com.example.zxcvbnjlib.BuildConfig;

/* compiled from: ResponseReceivedCommandV1 */
public final class b extends a {
    public b(e eVar, String str) {
        super(eVar, BuildConfig.VERSION_NAME);
        getCommandData().put("requestId", str);
    }
}
