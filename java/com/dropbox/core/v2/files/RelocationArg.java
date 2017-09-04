package com.dropbox.core.v2.files;

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
import java.util.regex.Pattern;

class RelocationArg extends RelocationPath {
    protected final boolean allowSharedFolder;
    protected final boolean autorename;

    public static class Builder {
        protected boolean allowSharedFolder;
        protected boolean autorename;
        protected final String fromPath;
        protected final String toPath;

        protected Builder(String fromPath, String toPath) {
            if (fromPath == null) {
                throw new IllegalArgumentException("Required value for 'fromPath' is null");
            } else if (Pattern.matches("(/(.|[\\r\\n])*)|(ns:[0-9]+(/.*)?)", fromPath)) {
                this.fromPath = fromPath;
                if (toPath == null) {
                    throw new IllegalArgumentException("Required value for 'toPath' is null");
                } else if (Pattern.matches("(/(.|[\\r\\n])*)|(ns:[0-9]+(/.*)?)", toPath)) {
                    this.toPath = toPath;
                    this.allowSharedFolder = false;
                    this.autorename = false;
                } else {
                    throw new IllegalArgumentException("String 'toPath' does not match pattern");
                }
            } else {
                throw new IllegalArgumentException("String 'fromPath' does not match pattern");
            }
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

        public RelocationArg build() {
            return new RelocationArg(this.fromPath, this.toPath, this.allowSharedFolder, this.autorename);
        }
    }

    static class Serializer extends StructSerializer<RelocationArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(RelocationArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("from_path");
            StoneSerializers.string().serialize(value.fromPath, g);
            g.writeFieldName("to_path");
            StoneSerializers.string().serialize(value.toPath, g);
            g.writeFieldName("allow_shared_folder");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.allowSharedFolder), g);
            g.writeFieldName("autorename");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.autorename), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public RelocationArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_fromPath = null;
                String f_toPath = null;
                Boolean f_allowSharedFolder = Boolean.valueOf(false);
                Boolean f_autorename = Boolean.valueOf(false);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("from_path".equals(field)) {
                        f_fromPath = (String) StoneSerializers.string().deserialize(p);
                    } else if ("to_path".equals(field)) {
                        f_toPath = (String) StoneSerializers.string().deserialize(p);
                    } else if ("allow_shared_folder".equals(field)) {
                        f_allowSharedFolder = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("autorename".equals(field)) {
                        f_autorename = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_fromPath == null) {
                    throw new JsonParseException(p, "Required field \"from_path\" missing.");
                } else if (f_toPath == null) {
                    throw new JsonParseException(p, "Required field \"to_path\" missing.");
                } else {
                    RelocationArg value = new RelocationArg(f_fromPath, f_toPath, f_allowSharedFolder.booleanValue(), f_autorename.booleanValue());
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public RelocationArg(String fromPath, String toPath, boolean allowSharedFolder, boolean autorename) {
        super(fromPath, toPath);
        this.allowSharedFolder = allowSharedFolder;
        this.autorename = autorename;
    }

    public RelocationArg(String fromPath, String toPath) {
        this(fromPath, toPath, false, false);
    }

    public String getFromPath() {
        return this.fromPath;
    }

    public String getToPath() {
        return this.toPath;
    }

    public boolean getAllowSharedFolder() {
        return this.allowSharedFolder;
    }

    public boolean getAutorename() {
        return this.autorename;
    }

    public static Builder newBuilder(String fromPath, String toPath) {
        return new Builder(fromPath, toPath);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Boolean.valueOf(this.allowSharedFolder), Boolean.valueOf(this.autorename)}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        RelocationArg other = (RelocationArg) obj;
        if ((this.fromPath == other.fromPath || this.fromPath.equals(other.fromPath)) && ((this.toPath == other.toPath || this.toPath.equals(other.toPath)) && this.allowSharedFolder == other.allowSharedFolder && this.autorename == other.autorename)) {
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
