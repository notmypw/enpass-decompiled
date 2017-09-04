package com.box.androidsdk.content.requests;

import com.box.androidsdk.content.models.BoxJsonObject;
import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.requests.BoxRequest.ContentTypes;
import java.util.Locale;

public abstract class BoxRequestItem<E extends BoxJsonObject, R extends BoxRequest<E, R>> extends BoxRequest<E, R> {
    private static String QUERY_FIELDS = "fields";
    protected String mId;

    public BoxRequestItem(Class<E> clazz, String id, String requestUrl, BoxSession session) {
        super(clazz, requestUrl, session);
        this.mId = null;
        this.mContentType = ContentTypes.JSON;
        this.mId = id;
    }

    protected BoxRequestItem(BoxRequestItem r) {
        super(r);
        this.mId = null;
    }

    public R setFields(String... fields) {
        if (fields.length > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(fields[0]);
            for (int i = 1; i < fields.length; i++) {
                sb.append(String.format(Locale.ENGLISH, ",%s", new Object[]{fields[i]}));
            }
            this.mQueryMap.put(QUERY_FIELDS, sb.toString());
        }
        return this;
    }

    public String getId() {
        return this.mId;
    }
}
