package com.box.androidsdk.content.models;

import com.eclipsesource.json.JsonObject.Member;
import com.eclipsesource.json.JsonValue;
import java.util.Iterator;

public class BoxListRealTimeServers extends BoxList<BoxRealTimeServer> {
    public static final String FIELD_CHUNK_SIZE = "chunk_size";
    private static final long serialVersionUID = -4986489348666966126L;

    protected void parseJSONMember(Member member) {
        String memberName = member.getName();
        JsonValue value = member.getValue();
        if (memberName.equals(FIELD_CHUNK_SIZE)) {
            this.mProperties.put(FIELD_CHUNK_SIZE, Long.valueOf(value.asLong()));
        } else if (memberName.equals(BoxList.FIELD_ENTRIES)) {
            Iterator it = value.asArray().iterator();
            while (it.hasNext()) {
                JsonValue entry = (JsonValue) it.next();
                BoxRealTimeServer server = new BoxRealTimeServer();
                server.createFromJson(entry.asObject());
                add((BoxJsonObject) server);
            }
            this.mProperties.put(BoxList.FIELD_ENTRIES, this.collection);
        } else {
            super.parseJSONMember(member);
        }
    }
}
