package com.box.androidsdk.content.requests;

import android.text.TextUtils;
import com.box.androidsdk.content.models.BoxCollection;
import com.box.androidsdk.content.models.BoxEntity;
import com.box.androidsdk.content.models.BoxItem;
import com.box.androidsdk.content.models.BoxJsonObject;
import com.box.androidsdk.content.models.BoxListCollections;
import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.requests.BoxRequest.Methods;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

abstract class BoxRequestCollectionUpdate<E extends BoxItem, R extends BoxRequest<E, R>> extends BoxRequestItem<E, R> {
    protected static final String FIELD_COLLECTIONS = "collections";

    public BoxRequestCollectionUpdate(Class<E> clazz, String id, String requestUrl, BoxSession session) {
        super(clazz, id, requestUrl, session);
        this.mRequestMethod = Methods.PUT;
    }

    protected R setCollectionId(String id) {
        BoxListCollections collections = new BoxListCollections();
        if (!TextUtils.isEmpty(id)) {
            LinkedHashMap<String, Object> map = new LinkedHashMap();
            map.put(BoxEntity.FIELD_ID, id);
            collections.add((BoxJsonObject) new BoxCollection(map));
        }
        this.mBodyMap.put(FIELD_COLLECTIONS, collections);
        return this;
    }

    protected void parseHashMapEntry(JsonObject jsonBody, Entry<String, Object> entry) {
        if (!((String) entry.getKey()).equals(FIELD_COLLECTIONS)) {
            super.parseHashMapEntry(jsonBody, entry);
        } else if (entry.getValue() != null && (entry.getValue() instanceof BoxListCollections)) {
            BoxListCollections collections = (BoxListCollections) entry.getValue();
            JsonValue arr = new JsonArray();
            Iterator it = collections.iterator();
            while (it.hasNext()) {
                arr.add(JsonValue.readFrom(((BoxCollection) it.next()).toJson()));
            }
            jsonBody.add((String) entry.getKey(), arr);
        }
    }
}
