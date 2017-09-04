package com.box.androidsdk.content.requests;

import com.box.androidsdk.content.models.BoxEntity;
import com.box.androidsdk.content.models.BoxFileVersion;
import com.box.androidsdk.content.models.BoxFolder;
import com.box.androidsdk.content.models.BoxItem;
import com.box.androidsdk.content.models.BoxMetadata;
import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.requests.BoxRequest.Methods;
import com.eclipsesource.json.JsonObject;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

abstract class BoxRequestItemRestoreTrashed<E extends BoxItem, R extends BoxRequest<E, R>> extends BoxRequestItem<E, R> {
    public BoxRequestItemRestoreTrashed(Class<E> clazz, String id, String requestUrl, BoxSession session) {
        super(clazz, id, requestUrl, session);
        this.mRequestMethod = Methods.POST;
    }

    public String getName() {
        return this.mBodyMap.containsKey(BoxFileVersion.FIELD_NAME) ? (String) this.mBodyMap.get(BoxFileVersion.FIELD_NAME) : null;
    }

    public R setName(String name) {
        this.mBodyMap.put(BoxFileVersion.FIELD_NAME, name);
        return this;
    }

    public String getParentId() {
        return this.mBodyMap.containsKey(BoxMetadata.FIELD_PARENT) ? ((BoxFolder) this.mBodyMap.get(BoxMetadata.FIELD_PARENT)).getId() : null;
    }

    public R setParentId(String parentId) {
        LinkedHashMap<String, Object> map = new LinkedHashMap();
        map.put(BoxEntity.FIELD_ID, parentId);
        this.mBodyMap.put(BoxMetadata.FIELD_PARENT, new BoxFolder(map));
        return this;
    }

    protected void parseHashMapEntry(JsonObject jsonBody, Entry<String, Object> entry) {
        if (((String) entry.getKey()).equals(BoxMetadata.FIELD_PARENT)) {
            jsonBody.add((String) entry.getKey(), parseJsonObject(entry.getValue()));
        } else {
            super.parseHashMapEntry(jsonBody, entry);
        }
    }
}
