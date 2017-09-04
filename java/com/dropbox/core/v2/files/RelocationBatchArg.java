package com.dropbox.core.v2.files;

import com.box.androidsdk.content.models.BoxList;
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

class RelocationBatchArg {
    protected final boolean allowSharedFolder;
    protected final boolean autorename;
    protected final List<RelocationPath> entries;

    public static class Builder {
        protected boolean allowSharedFolder;
        protected boolean autorename;
        protected final List<RelocationPath> entries;

        protected Builder(List<RelocationPath> entries) {
            if (entries == null) {
                throw new IllegalArgumentException("Required value for 'entries' is null");
            }
            for (RelocationPath x : entries) {
                if (x == null) {
                    throw new IllegalArgumentException("An item in list 'entries' is null");
                }
            }
            this.entries = entries;
            this.allowSharedFolder = false;
            this.autorename = false;
        }

        public Builder withAllowSharedFolder(Boolean allowSharedFolder) {
            if (allowSharedFolder != null) {
                this.allowSharedFolder = allowSharedFolder.booleanValue();
            } else {
                this.allowSharedFolder = false;
            }
            return this;
        }

        public Builder withAutorename(Boolean autorename) {
            if (autorename != null) {
                this.autorename = autorename.booleanValue();
            } else {
                this.autorename = false;
            }
            return this;
        }

        public RelocationBatchArg build() {
            return new RelocationBatchArg(this.entries, this.allowSharedFolder, this.autorename);
        }
    }

    static class Serializer extends StructSerializer<RelocationBatchArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(RelocationBatchArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxList.FIELD_ENTRIES);
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.entries, g);
            g.writeFieldName("allow_shared_folder");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.allowSharedFolder), g);
            g.writeFieldName("autorename");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.autorename), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public RelocationBatchArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<RelocationPath> f_entries = null;
                Boolean f_allowSharedFolder = Boolean.valueOf(false);
                Boolean f_autorename = Boolean.valueOf(false);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxList.FIELD_ENTRIES.equals(field)) {
                        f_entries = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else if ("allow_shared_folder".equals(field)) {
                        f_allowSharedFolder = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("autorename".equals(field)) {
                        f_autorename = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_entries == null) {
                    throw new JsonParseException(p, "Required field \"entries\" missing.");
                }
                RelocationBatchArg value = new RelocationBatchArg(f_entries, f_allowSharedFolder.booleanValue(), f_autorename.booleanValue());
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public RelocationBatchArg(List<RelocationPath> entries, boolean allowSharedFolder, boolean autorename) {
        if (entries == null) {
            throw new IllegalArgumentException("Required value for 'entries' is null");
        }
        for (RelocationPath x : entries) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'entries' is null");
            }
        }
        this.entries = entries;
        this.allowSharedFolder = allowSharedFolder;
        this.autorename = autorename;
    }

    public RelocationBatchArg(List<RelocationPath> entries) {
        this(entries, false, false);
    }

    public List<RelocationPath> getEntries() {
        return this.entries;
    }

    public boolean getAllowSharedFolder() {
        return this.allowSharedFolder;
    }

    public boolean getAutorename() {
        return this.autorename;
    }

    public static Builder newBuilder(List<RelocationPath> entries) {
        return new Builder(entries);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.entries, Boolean.valueOf(this.allowSharedFolder), Boolean.valueOf(this.autorename)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        RelocationBatchArg other = (RelocationBatchArg) obj;
        if ((this.entries == other.entries || this.entries.equals(other.entries)) && this.allowSharedFolder == other.allowSharedFolder && this.autorename == other.autorename) {
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
