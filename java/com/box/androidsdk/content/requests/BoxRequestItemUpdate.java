package com.box.androidsdk.content.requests;

import com.box.androidsdk.content.models.BoxEntity;
import com.box.androidsdk.content.models.BoxFileVersion;
import com.box.androidsdk.content.models.BoxFolder;
import com.box.androidsdk.content.models.BoxItem;
import com.box.androidsdk.content.models.BoxMetadata;
import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.models.BoxSharedLink;
import com.box.androidsdk.content.requests.BoxRequest.Methods;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

public abstract class BoxRequestItemUpdate<E extends BoxItem, R extends BoxRequest<E, R>> extends BoxRequestItem<E, R> {
    public abstract BoxRequestUpdateSharedItem updateSharedLink();

    public BoxRequestItemUpdate(Class<E> clazz, String id, String requestUrl, BoxSession session) {
        super(clazz, id, requestUrl, session);
        this.mRequestMethod = Methods.PUT;
    }

    protected BoxRequestItemUpdate(BoxRequestItemUpdate r) {
        super(r);
    }

    public String getName() {
        return this.mBodyMap.containsKey(BoxFileVersion.FIELD_NAME) ? (String) this.mBodyMap.get(BoxFileVersion.FIELD_NAME) : null;
    }

    public R setName(String name) {
        this.mBodyMap.put(BoxFileVersion.FIELD_NAME, name);
        return this;
    }

    public String getDescription() {
        return this.mBodyMap.containsKey(BoxItem.FIELD_DESCRIPTION) ? (String) this.mBodyMap.get(BoxItem.FIELD_DESCRIPTION) : null;
    }

    public R setDescription(String description) {
        this.mBodyMap.put(BoxItem.FIELD_DESCRIPTION, description);
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

    public BoxSharedLink getSharedLink() {
        return this.mBodyMap.containsKey(BoxItem.FIELD_SHARED_LINK) ? (BoxSharedLink) this.mBodyMap.get(BoxItem.FIELD_SHARED_LINK) : null;
    }

    public R setSharedLink(BoxSharedLink sharedLink) {
        this.mBodyMap.put(BoxItem.FIELD_SHARED_LINK, sharedLink);
        return this;
    }

    public R setIfMatchEtag(String etag) {
        return super.setIfMatchEtag(etag);
    }

    public String getIfMatchEtag() {
        return super.getIfMatchEtag();
    }

    public List<String> getTags() {
        return this.mBodyMap.containsKey(BoxItem.FIELD_TAGS) ? (List) this.mBodyMap.get(BoxItem.FIELD_TAGS) : null;
    }

    public R setTags(List<String> tags) {
        JsonArray jsonArray = new JsonArray();
        for (String s : tags) {
            jsonArray.add(s);
        }
        this.mBodyMap.put(BoxItem.FIELD_TAGS, jsonArray);
        return this;
    }

    protected void parseHashMapEntry(JsonObject jsonBody, Entry<String, Object> entry) {
        if (((String) entry.getKey()).equals(BoxMetadata.FIELD_PARENT)) {
            jsonBody.add((String) entry.getKey(), parseJsonObject(entry.getValue()));
        } else if (!((String) entry.getKey()).equals(BoxItem.FIELD_SHARED_LINK)) {
            super.parseHashMapEntry(jsonBody, entry);
        } else if (entry.getValue() == null) {
            jsonBody.add((String) entry.getKey(), (String) null);
        } else {
            jsonBody.add((String) entry.getKey(), parseJsonObject(entry.getValue()));
        }
    }
}
