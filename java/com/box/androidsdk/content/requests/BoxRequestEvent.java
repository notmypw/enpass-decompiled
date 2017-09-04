package com.box.androidsdk.content.requests;

import com.box.androidsdk.content.BoxConstants;
import com.box.androidsdk.content.BoxException;
import com.box.androidsdk.content.models.BoxEvent;
import com.box.androidsdk.content.models.BoxJsonObject;
import com.box.androidsdk.content.models.BoxListEvents;
import com.box.androidsdk.content.models.BoxObject;
import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.models.BoxUser;
import com.box.androidsdk.content.requests.BoxRequest.BoxRequestHandler;
import com.box.androidsdk.content.requests.BoxRequest.ContentTypes;
import com.box.androidsdk.content.requests.BoxRequest.Methods;
import com.box.androidsdk.content.utils.IStreamPosition;
import java.util.Collection;

abstract class BoxRequestEvent<E extends BoxJsonObject, R extends BoxRequest<E, R>> extends BoxRequest<E, R> {
    public static final String FIELD_LIMIT = "stream_limit";
    public static final String FIELD_STREAM_POSITION = "stream_position";
    public static final String FIELD_STREAM_TYPE = "stream_type";
    public static final String STREAM_TYPE_ALL = "all";
    public static final String STREAM_TYPE_CHANGES = "changes";
    public static final String STREAM_TYPE_SYNC = "sync";
    private boolean mFilterDuplicates = true;
    private E mListEvents;

    public BoxRequestEvent(Class<E> clazz, String requestUrl, BoxSession session) {
        super(clazz, requestUrl, session);
        this.mRequestUrlString = requestUrl;
        this.mRequestMethod = Methods.GET;
        setRequestHandler(new BoxRequestHandler<BoxRequestEvent>(this) {
            public <T extends BoxObject> T onResponse(Class<T> clazz, BoxHttpResponse response) throws IllegalAccessException, InstantiationException, BoxException {
                if (response.getResponseCode() == BoxConstants.HTTP_STATUS_TOO_MANY_REQUESTS) {
                    return retryRateLimited(response);
                }
                String contentType = response.getContentType();
                BoxObject entity = (BoxObject) clazz.newInstance();
                if (entity instanceof BoxListEvents) {
                    ((BoxListEvents) entity).setFilterDuplicates(BoxRequestEvent.this.mFilterDuplicates);
                }
                if (!(entity instanceof BoxJsonObject) || !contentType.contains(ContentTypes.JSON.toString())) {
                    return entity;
                }
                String json = response.getStringBody();
                char charA = json.charAt(json.indexOf(BoxEvent.TYPE) - 1);
                char charB = json.charAt(json.indexOf(BoxUser.TYPE) - 1);
                ((BoxJsonObject) entity).createFromJson(json);
                return entity;
            }
        });
    }

    public R setStreamPosition(String streamPosition) {
        this.mQueryMap.put(FIELD_STREAM_POSITION, streamPosition);
        return this;
    }

    protected R setStreamType(String streamType) {
        this.mQueryMap.put(FIELD_STREAM_TYPE, streamType);
        return this;
    }

    public R setLimit(int limit) {
        this.mQueryMap.put(FIELD_LIMIT, Integer.toString(limit));
        return this;
    }

    public R setFilterDuplicates(boolean filterDuplicates) {
        this.mFilterDuplicates = filterDuplicates;
        return this;
    }

    public R setPreviousListEvents(E listEvents) {
        this.mListEvents = listEvents;
        setStreamPosition(((IStreamPosition) this.mListEvents).getNextStreamPosition().toString());
        return this;
    }

    public E send() throws BoxException {
        if (this.mListEvents == null) {
            return (BoxJsonObject) super.send();
        }
        BoxJsonObject nextEvents = (BoxJsonObject) super.send();
        ((Collection) this.mListEvents).addAll((Collection) super.send());
        ((Collection) nextEvents).clear();
        ((Collection) nextEvents).addAll((Collection) this.mListEvents);
        return nextEvents;
    }
}
