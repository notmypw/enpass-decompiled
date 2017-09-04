package com.box.androidsdk.content.requests;

import com.box.androidsdk.content.models.BoxItem;
import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.models.BoxSharedLink;
import com.box.androidsdk.content.models.BoxSharedLink.Access;
import com.box.androidsdk.content.models.BoxSharedLink.Permissions;
import com.box.androidsdk.content.models.BoxUploadEmail;
import com.box.androidsdk.content.requests.BoxRequest.Methods;
import com.box.androidsdk.content.utils.BoxDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;

public abstract class BoxRequestUpdateSharedItem<E extends BoxItem, R extends BoxRequest<E, R>> extends BoxRequestItemUpdate<E, R> {
    public BoxRequestUpdateSharedItem(Class<E> clazz, String id, String requestUrl, BoxSession session) {
        super(clazz, id, requestUrl, session);
        this.mRequestMethod = Methods.PUT;
    }

    protected BoxRequestUpdateSharedItem(BoxRequestItemUpdate r) {
        super(r);
    }

    public Access getAccess() {
        return this.mBodyMap.containsKey(BoxItem.FIELD_SHARED_LINK) ? ((BoxSharedLink) this.mBodyMap.get(BoxItem.FIELD_SHARED_LINK)).getAccess() : null;
    }

    public R setAccess(Access access) {
        LinkedHashMap<String, Object> map = new LinkedHashMap();
        if (this.mBodyMap.containsKey(BoxItem.FIELD_SHARED_LINK)) {
            map = new LinkedHashMap(((BoxSharedLink) this.mBodyMap.get(BoxItem.FIELD_SHARED_LINK)).getPropertiesAsHashMap());
        }
        map.put(BoxUploadEmail.FIELD_ACCESS, access);
        this.mBodyMap.put(BoxItem.FIELD_SHARED_LINK, new BoxSharedLink(map));
        return this;
    }

    public Date getUnsharedAt() {
        return this.mBodyMap.containsKey(BoxItem.FIELD_SHARED_LINK) ? ((BoxSharedLink) this.mBodyMap.get(BoxItem.FIELD_SHARED_LINK)).getUnsharedDate() : null;
    }

    public R setUnsharedAt(Date unsharedAt) throws ParseException {
        LinkedHashMap<String, Object> map = new LinkedHashMap();
        if (this.mBodyMap.containsKey(BoxItem.FIELD_SHARED_LINK)) {
            map = new LinkedHashMap(((BoxSharedLink) this.mBodyMap.get(BoxItem.FIELD_SHARED_LINK)).getPropertiesAsHashMap());
        }
        if (unsharedAt == null) {
            map.put(BoxSharedLink.FIELD_UNSHARED_AT, null);
        } else {
            map.put(BoxSharedLink.FIELD_UNSHARED_AT, BoxDateFormat.convertToDay(unsharedAt));
        }
        this.mBodyMap.put(BoxItem.FIELD_SHARED_LINK, new BoxSharedLink(map));
        return this;
    }

    public R setRemoveUnsharedAtDate() throws ParseException {
        return setUnsharedAt(null);
    }

    public String getPassword() {
        return this.mBodyMap.containsKey(BoxItem.FIELD_SHARED_LINK) ? ((BoxSharedLink) this.mBodyMap.get(BoxItem.FIELD_SHARED_LINK)).getPassword() : null;
    }

    public R setPassword(String password) {
        LinkedHashMap<String, Object> map = new LinkedHashMap();
        if (this.mBodyMap.containsKey(BoxItem.FIELD_SHARED_LINK)) {
            map = new LinkedHashMap(((BoxSharedLink) this.mBodyMap.get(BoxItem.FIELD_SHARED_LINK)).getPropertiesAsHashMap());
        }
        map.put(BoxSharedLink.FIELD_PASSWORD, password);
        this.mBodyMap.put(BoxItem.FIELD_SHARED_LINK, new BoxSharedLink(map));
        return this;
    }

    protected Boolean getCanDownload() {
        return this.mBodyMap.containsKey(BoxItem.FIELD_SHARED_LINK) ? ((BoxSharedLink) this.mBodyMap.get(BoxItem.FIELD_SHARED_LINK)).getPermissions().getCanDownload() : null;
    }

    protected R setCanDownload(boolean canDownload) {
        LinkedHashMap<String, Object> sharedLinkMap = new LinkedHashMap();
        if (this.mBodyMap.containsKey(BoxItem.FIELD_SHARED_LINK)) {
            sharedLinkMap = new LinkedHashMap(((BoxSharedLink) this.mBodyMap.get(BoxItem.FIELD_SHARED_LINK)).getPropertiesAsHashMap());
        }
        LinkedHashMap<String, Object> permissionsMap = new LinkedHashMap();
        if (sharedLinkMap.containsKey(BoxSharedLink.FIELD_PERMISSIONS)) {
            permissionsMap = new LinkedHashMap(((Permissions) sharedLinkMap.get(BoxSharedLink.FIELD_PERMISSIONS)).getPropertiesAsHashMap());
        }
        permissionsMap.put(Permissions.FIELD_CAN_DOWNLOAD, Boolean.valueOf(canDownload));
        sharedLinkMap.put(BoxSharedLink.FIELD_PERMISSIONS, new Permissions(permissionsMap));
        this.mBodyMap.put(BoxItem.FIELD_SHARED_LINK, new BoxSharedLink(sharedLinkMap));
        return this;
    }

    public BoxRequestUpdateSharedItem updateSharedLink() {
        return this;
    }
}
