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

public class ListTeamDevicesResult {
    protected final String cursor;
    protected final List<MemberDevices> devices;
    protected final boolean hasMore;

    static class Serializer extends StructSerializer<ListTeamDevicesResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListTeamDevicesResult value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("devices");
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.devices, g);
            g.writeFieldName("has_more");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.hasMore), g);
            if (value.cursor != null) {
                g.writeFieldName("cursor");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.cursor, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ListTeamDevicesResult deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<MemberDevices> f_devices = null;
                Boolean f_hasMore = null;
                String f_cursor = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("devices".equals(field)) {
                        f_devices = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else if ("has_more".equals(field)) {
                        f_hasMore = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("cursor".equals(field)) {
                        f_cursor = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_devices == null) {
                    throw new JsonParseException(p, "Required field \"devices\" missing.");
                } else if (f_hasMore == null) {
                    throw new JsonParseException(p, "Required field \"has_more\" missing.");
                } else {
                    ListTeamDevicesResult value = new ListTeamDevicesResult(f_devices, f_hasMore.booleanValue(), f_cursor);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ListTeamDevicesResult(List<MemberDevices> devices, boolean hasMore, String cursor) {
        if (devices == null) {
            throw new IllegalArgumentException("Required value for 'devices' is null");
        }
        for (MemberDevices x : devices) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'devices' is null");
            }
        }
        this.devices = devices;
        this.hasMore = hasMore;
        this.cursor = cursor;
    }

    public ListTeamDevicesResult(List<MemberDevices> devices, boolean hasMore) {
        this(devices, hasMore, null);
    }

    public List<MemberDevices> getDevices() {
        return this.devices;
    }

    public boolean getHasMore() {
        return this.hasMore;
    }

    public String getCursor() {
        return this.cursor;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.devices, Boolean.valueOf(this.hasMore), this.cursor});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ListTeamDevicesResult other = (ListTeamDevicesResult) obj;
        if ((this.devices == other.devices || this.devices.equals(other.devices)) && this.hasMore == other.hasMore) {
            if (this.cursor == other.cursor) {
                return true;
            }
            if (this.cursor != null && this.cursor.equals(other.cursor)) {
                return true;
            }
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
