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

public class ListMembersAppsResult {
    protected final List<MemberLinkedApps> apps;
    protected final String cursor;
    protected final boolean hasMore;

    static class Serializer extends StructSerializer<ListMembersAppsResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListMembersAppsResult value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("apps");
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.apps, g);
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

        public ListMembersAppsResult deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<MemberLinkedApps> f_apps = null;
                Boolean f_hasMore = null;
                String f_cursor = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("apps".equals(field)) {
                        f_apps = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else if ("has_more".equals(field)) {
                        f_hasMore = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("cursor".equals(field)) {
                        f_cursor = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_apps == null) {
                    throw new JsonParseException(p, "Required field \"apps\" missing.");
                } else if (f_hasMore == null) {
                    throw new JsonParseException(p, "Required field \"has_more\" missing.");
                } else {
                    ListMembersAppsResult value = new ListMembersAppsResult(f_apps, f_hasMore.booleanValue(), f_cursor);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ListMembersAppsResult(List<MemberLinkedApps> apps, boolean hasMore, String cursor) {
        if (apps == null) {
            throw new IllegalArgumentException("Required value for 'apps' is null");
        }
        for (MemberLinkedApps x : apps) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'apps' is null");
            }
        }
        this.apps = apps;
        this.hasMore = hasMore;
        this.cursor = cursor;
    }

    public ListMembersAppsResult(List<MemberLinkedApps> apps, boolean hasMore) {
        this(apps, hasMore, null);
    }

    public List<MemberLinkedApps> getApps() {
        return this.apps;
    }

    public boolean getHasMore() {
        return this.hasMore;
    }

    public String getCursor() {
        return this.cursor;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.apps, Boolean.valueOf(this.hasMore), this.cursor});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ListMembersAppsResult other = (ListMembersAppsResult) obj;
        if ((this.apps == other.apps || this.apps.equals(other.apps)) && this.hasMore == other.hasMore) {
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
