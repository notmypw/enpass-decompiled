package com.box.androidsdk.content.models;

import java.util.LinkedHashMap;
import java.util.Map;

public class BoxGroup extends BoxCollaborator {
    public static final String TYPE = "group";
    private static final long serialVersionUID = 5872741782856508553L;

    public BoxGroup(Map<String, Object> map) {
        super(map);
    }

    public static BoxGroup createFromId(String groupId) {
        LinkedHashMap<String, Object> groupMap = new LinkedHashMap();
        groupMap.put(BoxEntity.FIELD_ID, groupId);
        groupMap.put(BoxRealTimeServer.FIELD_TYPE, BoxUser.TYPE);
        return new BoxGroup(groupMap);
    }
}
