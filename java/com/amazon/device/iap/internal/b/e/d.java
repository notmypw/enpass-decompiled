package com.amazon.device.iap.internal.b.e;

import android.os.RemoteException;
import com.amazon.android.framework.exception.KiwiException;
import com.amazon.device.iap.internal.b.e;
import com.amazon.device.iap.internal.model.UserDataBuilder;
import com.amazon.device.iap.internal.model.UserDataResponseBuilder;
import com.amazon.device.iap.model.UserData;
import com.amazon.device.iap.model.UserDataResponse.RequestStatus;
import com.amazon.venezia.command.SuccessResult;
import com.box.androidsdk.content.auth.OAuthActivity;
import com.example.zxcvbnjlib.BuildConfig;
import java.util.Map;

/* compiled from: GetUserIdCommandV1 */
public final class d extends b {
    private static final String b = d.class.getSimpleName();

    public d(e eVar) {
        super(eVar, BuildConfig.VERSION_NAME);
    }

    protected boolean a(SuccessResult successResult) throws RemoteException, KiwiException {
        com.amazon.device.iap.internal.util.e.a(b, "onSuccessInternal: result = " + successResult);
        Map data = successResult.getData();
        com.amazon.device.iap.internal.util.e.a(b, "data: " + data);
        String str = (String) data.get(OAuthActivity.USER_ID);
        e b = b();
        if (com.amazon.device.iap.internal.util.d.a(str)) {
            b.d().a(new UserDataResponseBuilder().setRequestId(b.c()).setRequestStatus(RequestStatus.FAILED).build());
            return false;
        }
        UserData build = new UserDataBuilder().setUserId(str).setMarketplace(a).build();
        Object build2 = new UserDataResponseBuilder().setRequestId(b.c()).setRequestStatus(RequestStatus.SUCCESSFUL).setUserData(build).build();
        b.d().a(OAuthActivity.USER_ID, build.getUserId());
        b.d().a(build2);
        return true;
    }
}
