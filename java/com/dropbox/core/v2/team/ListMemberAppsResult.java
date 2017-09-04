package com.dropbox.core.v2.team;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ListMemberAppsResult {
    protected final List<ApiApp> linkedApiApps;

    static class Serializer extends StructSerializer<ListMemberAppsResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListMemberAppsResult value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("linked_api_apps");
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.linkedApiApps, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ListMemberAppsResult deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<ApiApp> f_linkedApiApps = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("linked_api_apps".equals(field)) {
                        f_linkedApiApps = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_linkedApiApps == null) {
                    throw new JsonParseException(p, "Required field \"linked_api_apps\" missing.");
                }
                ListMemberAppsResult value = new ListMemberAppsResult(f_linkedApiApps);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ListMemberAppsResult(List<ApiApp> linkedApiApps) {
        if (linkedApiApps == null) {
            throw new IllegalArgumentException("Required value for 'linkedApiApps' is null");
        }
        for (ApiApp x : linkedApiApps) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'linkedApiApps' is null");
            }
        }
        this.linkedApiApps = linkedApiApps;
    }

    public List<ApiApp> getLinkedApiApps() {
        return this.linkedApiApps;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.linkedApiApps});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ListMemberAppsResult other = (ListMemberAppsResult) obj;
        if (this.linkedApiApps == other.linkedApiApps || this.linkedApiApps.equals(other.linkedApiApps)) {
            return true;
        }
        return false;
    }

    public String toString() {
        return Serializer.INSTANCE.serialize((Object) this, false);
    }

    public String toStringMultiline() {
        return Serializer.INSTANCE.serialize((Object) this, true);
    }
}
