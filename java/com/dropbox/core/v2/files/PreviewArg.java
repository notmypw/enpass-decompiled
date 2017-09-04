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

class PreviewArg {
    protected final String path;
    protected final String rev;

    static class Serializer extends StructSerializer<PreviewArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(PreviewArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxMetadataUpdateTask.PATH);
            StoneSerializers.string().serialize(value.path, g);
            if (value.rev != null) {
                g.writeFieldName("rev");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.rev, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public PreviewArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_path = null;
                String f_rev = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxMetadataUpdateTask.PATH.equals(field)) {
                        f_path = (String) StoneSerializers.string().deserialize(p);
                    } else if ("rev".equals(field)) {
                        f_rev = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_path == null) {
                    throw new JsonParseException(p, "Required field \"path\" missing.");
                }
                PreviewArg value = new PreviewArg(f_path, f_rev);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public PreviewArg(String path, String rev) {
        if (path == null) {
            throw new IllegalArgumentException("Required value for 'path' is null");
        } else if (Pattern.matches("(/(.|[\\r\\n])*|id:.*)|(rev:[0-9a-f]{9,})|(ns:[0-9]+(/.*)?)", path)) {
            this.path = path;
            if (rev != null) {
                if (rev.length() < 9) {
                    throw new IllegalArgumentException("String 'rev' is shorter than 9");
                } else if (!Pattern.matches("[0-9a-f]+", rev)) {
                    throw new IllegalArgumentException("String 'rev' does not match pattern");
                }
            }
            this.rev = rev;
        } else {
            throw new IllegalArgumentException("String 'path' does not match pattern");
        }
    }

    public PreviewArg(String path) {
        this(path, null);
    }

    public String getPath() {
        return this.path;
    }

    public String getRev() {
        return this.rev;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.path, this.rev});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        PreviewArg other = (PreviewArg) obj;
        if (this.path == other.path || this.path.equals(other.path)) {
            if (this.rev == other.rev) {
                return true;
            }
            if (this.rev != null && this.rev.equals(other.rev)) {
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
