package com.dropbox.core.v2.files;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.dropbox.core.util.LangUtil;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Pattern;

public class CommitInfo {
    protected final boolean autorename;
    protected final Date clientModified;
    protected final WriteMode mode;
    protected final boolean mute;
    protected final String path;

    public static class Builder {
        protected boolean autorename;
        protected Date clientModified;
        protected WriteMode mode;
        protected boolean mute;
        protected final String path;

        protected Builder(String path) {
            if (path == null) {
                throw new IllegalArgumentException("Required value for 'path' is null");
            } else if (Pattern.matches("(/(.|[\\r\\n])*)|(ns:[0-9]+(/.*)?)", path)) {
                this.path = path;
                this.mode = WriteMode.ADD;
                this.autorename = false;
                this.clientModified = null;
                this.mute = false;
            } else {
                throw new IllegalArgumentException("String 'path' does not match pattern");
            }
        }

        public Builder withMode(WriteMode mode) {
            if (mode != null) {
                this.mode = mode;
            } else {
                this.mode = WriteMode.ADD;
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

        public Builder withClientModified(Date clientModified) {
            this.clientModified = LangUtil.truncateMillis(clientModified);
            return this;
        }

        public Builder withMute(Boolean mute) {
            if (mute != null) {
                this.mute = mute.booleanValue();
            } else {
                this.mute = false;
            }
            return this;
        }

        public CommitInfo build() {
            return new CommitInfo(this.path, this.mode, this.autorename, this.clientModified, this.mute);
        }
    }

    static class Serializer extends StructSerializer<CommitInfo> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(CommitInfo value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
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
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public CommitInfo deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
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
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_path == null) {
                    throw new JsonParseException(p, "Required field \"path\" missing.");
                }
                CommitInfo value = new CommitInfo(f_path, f_mode, f_autorename.booleanValue(), f_clientModified, f_mute.booleanValue());
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public CommitInfo(String path, WriteMode mode, boolean autorename, Date clientModified, boolean mute) {
        if (path == null) {
            throw new IllegalArgumentException("Required value for 'path' is null");
        } else if (Pattern.matches("(/(.|[\\r\\n])*)|(ns:[0-9]+(/.*)?)", path)) {
            this.path = path;
            if (mode == null) {
                throw new IllegalArgumentException("Required value for 'mode' is null");
            }
            this.mode = mode;
            this.autorename = autorename;
            this.clientModified = LangUtil.truncateMillis(clientModified);
            this.mute = mute;
        } else {
            throw new IllegalArgumentException("String 'path' does not match pattern");
        }
    }

    public CommitInfo(String path) {
        this(path, WriteMode.ADD, false, null, false);
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

    public static Builder newBuilder(String path) {
        return new Builder(path);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.path, this.mode, Boolean.valueOf(this.autorename), this.clientModified, Boolean.valueOf(this.mute)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        CommitInfo other = (CommitInfo) obj;
        if ((this.path == other.path || this.path.equals(other.path)) && ((this.mode == other.mode || this.mode.equals(other.mode)) && this.autorename == other.autorename && ((this.clientModified == other.clientModified || (this.clientModified != null && this.clientModified.equals(other.clientModified))) && this.mute == other.mute))) {
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
