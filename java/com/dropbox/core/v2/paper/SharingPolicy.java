package com.dropbox.core.v2.paper;

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

public class SharingPolicy {
    protected final SharingPublicPolicyType publicSharingPolicy;
    protected final SharingTeamPolicyType teamSharingPolicy;

    public static class Builder {
        protected SharingPublicPolicyType publicSharingPolicy = null;
        protected SharingTeamPolicyType teamSharingPolicy = null;

        protected Builder() {
        }

        public Builder withPublicSharingPolicy(SharingPublicPolicyType publicSharingPolicy) {
            this.publicSharingPolicy = publicSharingPolicy;
            return this;
        }

        public Builder withTeamSharingPolicy(SharingTeamPolicyType teamSharingPolicy) {
            this.teamSharingPolicy = teamSharingPolicy;
            return this;
        }

        public SharingPolicy build() {
            return new SharingPolicy(this.publicSharingPolicy, this.teamSharingPolicy);
        }
    }

    static class Serializer extends StructSerializer<SharingPolicy> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(SharingPolicy value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            if (value.publicSharingPolicy != null) {
                g.writeFieldName("public_sharing_policy");
                StoneSerializers.nullable(Serializer.INSTANCE).serialize(value.publicSharingPolicy, g);
            }
            if (value.teamSharingPolicy != null) {
                g.writeFieldName("team_sharing_policy");
                StoneSerializers.nullable(Serializer.INSTANCE).serialize(value.teamSharingPolicy, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public SharingPolicy deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                SharingPublicPolicyType f_publicSharingPolicy = null;
                SharingTeamPolicyType f_teamSharingPolicy = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("public_sharing_policy".equals(field)) {
                        f_publicSharingPolicy = (SharingPublicPolicyType) StoneSerializers.nullable(Serializer.INSTANCE).deserialize(p);
                    } else if ("team_sharing_policy".equals(field)) {
                        f_teamSharingPolicy = (SharingTeamPolicyType) StoneSerializers.nullable(Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                SharingPolicy value = new SharingPolicy(f_publicSharingPolicy, f_teamSharingPolicy);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public SharingPolicy(SharingPublicPolicyType publicSharingPolicy, SharingTeamPolicyType teamSharingPolicy) {
        this.publicSharingPolicy = publicSharingPolicy;
        this.teamSharingPolicy = teamSharingPolicy;
    }

    public SharingPolicy() {
        this(null, null);
    }

    public SharingPublicPolicyType getPublicSharingPolicy() {
        return this.publicSharingPolicy;
    }

    public SharingTeamPolicyType getTeamSharingPolicy() {
        return this.teamSharingPolicy;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.publicSharingPolicy, this.teamSharingPolicy});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        SharingPolicy other = (SharingPolicy) obj;
        if (this.publicSharingPolicy == other.publicSharingPolicy || (this.publicSharingPolicy != null && this.publicSharingPolicy.equals(other.publicSharingPolicy))) {
            if (this.teamSharingPolicy == other.teamSharingPolicy) {
                return true;
            }
            if (this.teamSharingPolicy != null && this.teamSharingPolicy.equals(other.teamSharingPolicy)) {
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
