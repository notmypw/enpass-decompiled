package com.box.androidsdk.content.models;

import com.eclipsesource.json.JsonObject.Member;
import com.eclipsesource.json.JsonValue;
import java.util.Map;

public class BoxEnterpriseEvent extends BoxEvent {
    public static final String FIELD_ACCESSIBLE_BY = "accessible_by";
    public static final String FIELD_ADDITIONAL_DETAILS = "additional_details";
    public static final String FIELD_IP_ADDRESS = "ip_address";
    private static final long serialVersionUID = -1404872691081072451L;

    public enum Type {
        GROUP_ADD_USER,
        NEW_USER,
        GROUP_CREATION,
        GROUP_DELETION,
        DELETE_USER,
        GROUP_EDITED,
        EDIT_USER,
        GROUP_ADD_FOLDER,
        GROUP_REMOVE_USER,
        GROUP_REMOVE_FOLDER,
        ADMIN_LOGIN,
        ADD_DEVICE_ASSOCIATION,
        FAILED_LOGIN,
        LOGIN,
        USER_AUTHENTICATE_OAUTH2_TOKEN_REFRESH,
        REMOVE_DEVICE_ASSOCIATION,
        TERMS_OF_SERVICE_AGREE,
        TERMS_OF_SERVICE_REJECT,
        COPY,
        DELETE,
        DOWNLOAD,
        EDIT,
        LOCK,
        MOVE,
        PREVIEW,
        RENAME,
        STORAGE_EXPIRATION,
        UNDELETE,
        UNLOCK,
        UPLOAD,
        SHARE,
        ITEM_SHARED_UPDATE,
        UPDATE_SHARE_EXPIRATION,
        SHARE_EXPIRATION,
        UNSHARE,
        COLLABORATION_ACCEPT,
        COLLABORATION_ROLE_CHANGE,
        UPDATE_COLLABORATION_EXPIRATION,
        COLLABORATION_REMOVE,
        COLLABORATION_INVITE,
        COLLABORATION_EXPIRATION,
        ITEM_SYNC,
        ITEM_UNSYNC
    }

    protected void parseJSONMember(Member member) {
        String memberName = member.getName();
        JsonValue value = member.getValue();
        if (memberName.equals(FIELD_ACCESSIBLE_BY)) {
            this.mProperties.put(FIELD_ACCESSIBLE_BY, BoxEntity.createEntityFromJson(value.asObject()));
        } else if (memberName.equals(FIELD_ADDITIONAL_DETAILS)) {
            this.mProperties.put(FIELD_ADDITIONAL_DETAILS, value.toString());
        } else if (memberName.equals(FIELD_IP_ADDRESS)) {
            this.mProperties.put(FIELD_IP_ADDRESS, value.asString());
        } else {
            super.parseJSONMember(member);
        }
    }

    public BoxCollaborator getAccessibleBy() {
        return (BoxCollaborator) this.mProperties.get(FIELD_ACCESSIBLE_BY);
    }

    public String getAdditionalDetails() {
        return (String) this.mProperties.get(FIELD_ADDITIONAL_DETAILS);
    }

    public String getIpAddress() {
        return (String) this.mProperties.get(FIELD_IP_ADDRESS);
    }

    public BoxEnterpriseEvent(Map<String, Object> map) {
        super(map);
    }
}
