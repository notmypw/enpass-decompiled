package com.dropbox.core.v2.files;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.dropbox.core.v2.properties.PropertyGroup;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

class CommitInfoWithProperties extends CommitInfo {
    protected final List<PropertyGroup> propertyGroups;

    public static class Builder extends com.dropbox.core.v2.files.CommitInfo.Builder {
        protected List<PropertyGroup> propertyGroups = null;

        protected Builder(String path) {
            super(path);
        }

        public Builder withPropertyGroups(List<PropertyGroup> propertyGroups) {
            if (propertyGroups != null) {
                for (PropertyGroup x : propertyGroups) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'propertyGroups' is null");
                    }
                }
            }
            this.propertyGroups = propertyGroups;
            return this;
        }

        public Builder withMode(WriteMode mode) {
            super.withMode(mode);
            return this;
        }

        public Builder withAutorename(Boolean autorename) {
            super.withAutorename(autorename);
            return this;
        }

        public Builder withClientModified(Date clientModified) {
            super.withClientModified(clientModified);
            return this;
        }

        public Builder withMute(Boolean mute) {
            super.withMute(mute);
            return this;
        }

        public CommitInfoWithProperties build() {
            return new CommitInfoWithProperties(this.path, this.mode, this.autorename, this.clientModified, this.mute, this.propertyGroups);
        }
    }

    static class Serializer extends StructSerializer<CommitInfoWithProperties> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(CommitInfoWithProperties value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxMetadataUpdateTask.PATH);
            StoneSerializers.string().serialize(value.path, g);
            g.writeFieldName("mode");
            Serializer.INSTANCE.serialize(value.mode, g);
            g.writeFieldName("autorename");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.autorename), g);
            if (value.clientModified != null) {
                g.writeFieldName("client_modified");
                StoneSerializers.nullable(StoneSerializers.timestamp()).serialize(value.clientModified, g);
            }
            g.writeFieldName("mute");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.mute), g);
            if (value.propertyGroups != null) {
                g.writeFieldName("property_groups");
                StoneSerializers.nullable(StoneSerializers.list(com.dropbox.core.v2.properties.PropertyGroup.Serializer.INSTANCE)).serialize(value.propertyGroups, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public CommitInfoWithProperties deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_path = null;
                WriteMode f_mode = WriteMode.ADD;
                Boolean f_autorename = Boolean.valueOf(false);
                Date f_clientModified = null;
                Boolean f_mute = Boolean.valueOf(false);
                List<PropertyGroup> f_propertyGroups = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxMetadataUpdateTask.PATH.equals(field)) {
                        f_path = (String) StoneSerializers.string().deserialize(p);
                    } else if ("mode".equals(field)) {
                        f_mode = Serializer.INSTANCE.deserialize(p);
                    } else if ("autorename".equals(field)) {
                        f_autorename = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("client_modified".equals(field)) {
                        f_clientModified = (Date) StoneSerializers.nullable(StoneSerializers.timestamp()).deserialize(p);
                    } else if ("mute".equals(field)) {
                        f_mute = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("property_groups".equals(field)) {
                        f_propertyGroups = (List) StoneSerializers.nullable(StoneSerializers.list(com.dropbox.core.v2.properties.PropertyGroup.Serializer.INSTANCE)).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_path == null) {
                    throw new JsonParseException(p, "Required field \"path\" missing.");
                }
                CommitInfoWithProperties value = new CommitInfoWithProperties(f_path, f_mode, f_autorename.booleanValue(), f_clientModified, f_mute.booleanValue(), f_propertyGroups);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public CommitInfoWithProperties(String path, WriteMode mode, boolean autorename, Date clientModified, boolean mute, List<PropertyGroup> propertyGroups) {
        super(path, mode, autorename, clientModified, mute);
        if (propertyGroups != null) {
            for (PropertyGroup x : propertyGroups) {
                if (x == null) {
                    throw new IllegalArgumentException("An item in list 'propertyGroups' is null");
                }
            }
        }
        this.propertyGroups = propertyGroups;
    }

    public CommitInfoWithProperties(String path) {
        this(path, WriteMode.ADD, false, null, false, null);
    }

    public String getPath() {
        return this.path;
    }

    public WriteMode getMode() {
        return this.mode;
    }

    public boolean getAutorename() {
        return this.autorename;
    }

    public Date getClientModified() {
        return this.clientModified;
    }

    public boolean getMute() {
        return this.mute;
    }

    public List<PropertyGroup> getPropertyGroups() {
        return this.propertyGroups;
    }

    public static Builder newBuilder(String path) {
        return new Builder(path);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.propertyGroups}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        CommitInfoWithProperties other = (CommitInfoWithProperties) obj;
        if ((this.path == other.path || this.path.equals(other.path)) && ((this.mode == other.mode || this.mode.equals(other.mode)) && this.autorename == other.autorename && ((this.clientModified == other.clientModified || (this.clientModified != null && this.clientModified.equals(other.clientModified))) && this.mute == other.mute))) {
            if (this.propertyGroups == other.propertyGroups) {
                return true;
            }
            if (this.propertyGroups != null && this.propertyGroups.equals(other.propertyGroups)) {
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
