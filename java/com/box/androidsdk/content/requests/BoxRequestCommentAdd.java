package com.box.androidsdk.content.requests;

import com.box.androidsdk.content.models.BoxComment;
import com.box.androidsdk.content.models.BoxEntity;
import com.box.androidsdk.content.models.BoxRealTimeServer;
import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.models.BoxSimpleMessage;
import com.box.androidsdk.content.requests.BoxRequest.Methods;
import java.util.LinkedHashMap;

abstract class BoxRequestCommentAdd<E extends BoxComment, R extends BoxRequest<E, R>> extends BoxRequestItem<E, R> {
    public BoxRequestCommentAdd(Class<E> clazz, String requestUrl, BoxSession session) {
        super(clazz, null, requestUrl, session);
        this.mRequestMethod = Methods.POST;
    }

    public String getMessage() {
        return (String) this.mBodyMap.get(BoxSimpleMessage.FIELD_MESSAGE);
    }

    public R setMessage(String message) {
        this.mBodyMap.put(BoxSimpleMessage.FIELD_MESSAGE, message);
        return this;
    }

    public String getItemId() {
        return this.mBodyMap.containsKey(BoxComment.FIELD_ITEM) ? (String) this.mBodyMap.get(BoxEntity.FIELD_ID) : null;
    }

    protected R setItemId(String id) {
        LinkedHashMap<String, Object> itemMap = new LinkedHashMap();
        if (this.mBodyMap.containsKey(BoxComment.FIELD_ITEM)) {
            itemMap = new LinkedHashMap(((BoxEntity) this.mBodyMap.get(BoxComment.FIELD_ITEM)).getPropertiesAsHashMap());
        }
        itemMap.put(BoxEntity.FIELD_ID, id);
        this.mBodyMap.put(BoxComment.FIELD_ITEM, new BoxEntity(itemMap));
        return this;
    }

    public String getItemType() {
        return this.mBodyMap.containsKey(BoxComment.FIELD_ITEM) ? (String) this.mBodyMap.get(BoxRealTimeServer.FIELD_TYPE) : null;
    }

    protected R setItemType(String type) {
        LinkedHashMap<String, Object> itemMap = new LinkedHashMap();
        if (this.mBodyMap.containsKey(BoxComment.FIELD_ITEM)) {
            itemMap = new LinkedHashMap(((BoxEntity) this.mBodyMap.get(BoxComment.FIELD_ITEM)).getPropertiesAsHashMap());
        }
        itemMap.put(BoxRealTimeServer.FIELD_TYPE, type);
        this.mBodyMap.put(BoxComment.FIELD_ITEM, new BoxEntity(itemMap));
        return this;
    }
}
