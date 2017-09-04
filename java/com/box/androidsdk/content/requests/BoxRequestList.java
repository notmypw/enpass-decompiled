package com.box.androidsdk.content.requests;

import com.box.androidsdk.content.models.BoxList;
import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.requests.BoxRequest.Methods;

abstract class BoxRequestList<E extends BoxList, R extends BoxRequest<E, R>> extends BoxRequestItem<E, R> {
    private static final String DEFAULT_LIMIT = "1000";
    private static final String DEFAULT_OFFSET = "0";
    private static final String LIMIT = "limit";
    private static final String OFFSET = "offset";

    public BoxRequestList(Class<E> clazz, String id, String requestUrl, BoxSession session) {
        super(clazz, id, requestUrl, session);
        this.mRequestMethod = Methods.GET;
        this.mQueryMap.put(LIMIT, DEFAULT_LIMIT);
        this.mQueryMap.put(OFFSET, DEFAULT_OFFSET);
    }

    public R setLimit(int limit) {
        this.mQueryMap.put(LIMIT, String.valueOf(limit));
        return this;
    }

    public R setOffset(int offset) {
        this.mQueryMap.put(OFFSET, String.valueOf(offset));
        return this;
    }
}
