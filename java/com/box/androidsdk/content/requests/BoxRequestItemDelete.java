package com.box.androidsdk.content.requests;

import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.models.BoxVoid;
import com.box.androidsdk.content.requests.BoxRequest.Methods;

abstract class BoxRequestItemDelete<R extends BoxRequest<BoxVoid, R>> extends BoxRequest<BoxVoid, R> {
    protected String mId;

    public BoxRequestItemDelete(String id, String requestUrl, BoxSession session) {
        super(BoxVoid.class, requestUrl, session);
        this.mId = id;
        this.mRequestMethod = Methods.DELETE;
    }

    public String getId() {
        return this.mId;
    }

    public R setIfMatchEtag(String etag) {
        return super.setIfMatchEtag(etag);
    }

    public String getIfMatchEtag() {
        return super.getIfMatchEtag();
    }
}
