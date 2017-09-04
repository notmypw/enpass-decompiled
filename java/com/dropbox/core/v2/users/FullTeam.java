package com.dropbox.core.v2.users;

import com.box.androidsdk.content.models.BoxEntity;
import com.box.androidsdk.content.models.BoxFileVersion;
import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.dropbox.core.v2.teampolicies.TeamSharingPolicies;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;

public class FullTeam extends Team {
    protected final TeamSharingPolicies sharingPolicies;

    static class Serializer extends StructSerializer<FullTeam> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(FullTeam value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxEntity.FIELD_ID);
            StoneSerializers.string().serialize(value.id, g);
            g.writeFieldName(BoxFileVersion.FIELD_NAME);
            StoneSerializers.string().serialize(value.name, g);
            g.writeFieldName("sharing_policies");
            com.dropbox.core.v2.teampolicies.TeamSharingPolicies.Serializer.INSTANCE.serialize(value.sharingPolicies, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public FullTeam deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_id = null;
                String f_name = null;
                TeamSharingPolicies f_sharingPolicies = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxEntity.FIELD_ID.equals(field)) {
                        f_id = (String) StoneSerializers.string().deserialize(p);
                    } else if (BoxFileVersion.FIELD_NAME.equals(field)) {
                        f_name = (String) StoneSerializers.string().deserialize(p);
                    } else if ("sharing_policies".equals(field)) {
                        f_sharingPolicies = (TeamSharingPolicies) com.dropbox.core.v2.teampolicies.TeamSharingPolicies.Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_id == null) {
                    throw new JsonParseException(p, "Required field \"id\" missing.");
                } else if (f_name == null) {
                    throw new JsonParseException(p, "Required field \"name\" missing.");
                } else if (f_sharingPolicies == null) {
                    throw new JsonParseException(p, "Required field \"sharing_policies\" missing.");
                } else {
                    FullTeam value = new FullTeam(f_id, f_name, f_sharingPolicies);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public FullTeam(String id, String name, TeamSharingPolicies sharingPolicies) {
        super(id, name);
        if (sharingPolicies == null) {
            throw new IllegalArgumentException("Required value for 'sharingPolicies' is null");
        }
        this.sharingPolicies = sharingPolicies;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public TeamSharingPolicies getSharingPolicies() {
        return this.sharingPolicies;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.sharingPolicies}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        FullTeam other = (FullTeam) obj;
        if ((this.id == other.id || this.id.equals(other.id)) && ((this.name == other.name || this.name.equals(other.name)) && (this.sharingPolicies == other.sharingPolicies || this.sharingPolicies.equals(other.sharingPolicies)))) {
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
