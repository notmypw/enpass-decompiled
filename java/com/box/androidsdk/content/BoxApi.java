package com.box.androidsdk.content;

import com.box.androidsdk.content.models.BoxSession;

public class BoxApi {
    protected String mBaseUploadUri = BoxConstants.BASE_UPLOAD_URI;
    protected String mBaseUri = BoxConstants.BASE_URI;
    protected BoxSession mSession;

    public BoxApi(BoxSession session) {
        this.mSession = session;
    }

    protected String getBaseUri() {
        if (this.mSession == null || this.mSession.getAuthInfo() == null || this.mSession.getAuthInfo().getBaseDomain() == null) {
            return this.mBaseUri;
        }
        return String.format(BoxConstants.BASE_URI_TEMPLATE, new Object[]{this.mSession.getAuthInfo().getBaseDomain()});
    }

    protected String getBaseUploadUri() {
        if (this.mSession == null || this.mSession.getAuthInfo() == null || this.mSession.getAuthInfo().getBaseDomain() == null) {
            return this.mBaseUploadUri;
        }
        return String.format(BoxConstants.BASE_UPLOAD_URI_TEMPLATE, new Object[]{this.mSession.getAuthInfo().getBaseDomain()});
    }
}
