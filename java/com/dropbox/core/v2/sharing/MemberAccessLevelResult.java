package com.dropbox.core.v2.sharing;

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

public class MemberAccessLevelResult {
    protected final List<ParentFolderAccessInfo> accessDetails;
    protected final AccessLevel accessLevel;
    protected final String warning;

    public static class Builder {
        protected List<ParentFolderAccessInfo> accessDetails = null;
        protected AccessLevel accessLevel = null;
        protected String warning = null;

        protected Builder() {
        }

        public Builder withAccessLevel(AccessLevel accessLevel) {
            this.accessLevel = accessLevel;
            return this;
        }

        public Builder withWarning(String warning) {
            this.warning = warning;
            return this;
        }

        public Builder withAccessDetails(List<ParentFolderAccessInfo> accessDetails) {
            if (accessDetails != null) {
                for (ParentFolderAccessInfo x : accessDetails) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'accessDetails' is null");
                    }
                }
            }
            this.accessDetails = accessDetails;
            return this;
        }

        public MemberAccessLevelResult build() {
            return new MemberAccessLevelResult(this.accessLevel, this.warning, this.accessDetails);
        }
    }

    static class Serializer extends StructSerializer<MemberAccessLevelResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(MemberAccessLevelResult value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            if (value.accessLevel != null) {
                g.writeFieldName("access_level");
                StoneSerializers.nullable(Serializer.INSTANCE).serialize(value.accessLevel, g);
            }
            if (value.warning != null) {
                g.writeFieldName("warning");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.warning, g);
            }
            if (value.accessDetails != null) {
                g.writeFieldName("access_details");
                StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).serialize(value.accessDetails, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public MemberAccessLevelResult deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                AccessLevel f_accessLevel = null;
                String f_warning = null;
                List<ParentFolderAccessInfo> f_accessDetails = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("access_level".equals(field)) {
                        f_accessLevel = (AccessLevel) StoneSerializers.nullable(Serializer.INSTANCE).deserialize(p);
                    } else if ("warning".equals(field)) {
                        f_warning = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("access_details".equals(field)) {
                        f_accessDetails = (List) StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                MemberAccessLevelResult value = new MemberAccessLevelResult(f_accessLevel, f_warning, f_accessDetails);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public MemberAccessLevelResult(AccessLevel accessLevel, String warning, List<ParentFolderAccessInfo> accessDetails) {
        this.accessLevel = accessLevel;
        this.warning = warning;
        if (accessDetails != null) {
            for (ParentFolderAccessInfo x : accessDetails) {
                if (x == null) {
                    throw new IllegalArgumentException("An item in list 'accessDetails' is null");
                }
            }
        }
        this.accessDetails = accessDetails;
    }

    public MemberAccessLevelResult() {
        this(null, null, null);
    }

    public AccessLevel getAccessLevel() {
        return this.accessLevel;
    }

    public String getWarning() {
        return this.warning;
    }

    public List<ParentFolderAccessInfo> getAccessDetails() {
        return this.accessDetails;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.accessLevel, this.warning, this.accessDetails});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        MemberAccessLevelResult other = (MemberAccessLevelResult) obj;
        if ((this.accessLevel == other.accessLevel || (this.accessLevel != null && this.accessLevel.equals(other.accessLevel))) && (this.warning == other.warning || (this.warning != null && this.warning.equals(other.warning)))) {
            if (this.accessDetails == other.accessDetails) {
                return true;
            }
            if (this.accessDetails != null && this.accessDetails.equals(other.accessDetails)) {
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
