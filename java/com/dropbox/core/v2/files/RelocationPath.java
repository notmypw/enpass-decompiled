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

public class RelocationPath {
    protected final String fromPath;
    protected final String toPath;

    static class Serializer extends StructSerializer<RelocationPath> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(RelocationPath value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("from_path");
            StoneSerializers.string().serialize(value.fromPath, g);
            g.writeFieldName("to_path");
            StoneSerializers.string().serialize(value.toPath, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public RelocationPath deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_fromPath = null;
                String f_toPath = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("from_path".equals(field)) {
                        f_fromPath = (String) StoneSerializers.string().deserialize(p);
                    } else if ("to_path".equals(field)) {
                        f_toPath = (String) StoneSerializers.string().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_fromPath == null) {
                    throw new JsonParseException(p, "Required field \"from_path\" missing.");
                } else if (f_toPath == null) {
                    throw new JsonParseException(p, "Required field \"to_path\" missing.");
                } else {
                    RelocationPath value = new RelocationPath(f_fromPath, f_toPath);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public RelocationPath(String fromPath, String toPath) {
        if (fromPath == null) {
            throw new IllegalArgumentException("Required value for 'fromPath' is null");
        } else if (Pattern.matches("(/(.|[\\r\\n])*)|(ns:[0-9]+(/.*)?)", fromPath)) {
            this.fromPath = fromPath;
            if (toPath == null) {
                throw new IllegalArgumentException("Required value for 'toPath' is null");
            } else if (Pattern.matches("(/(.|[\\r\\n])*)|(ns:[0-9]+(/.*)?)", toPath)) {
                this.toPath = toPath;
            } else {
                throw new IllegalArgumentException("String 'toPath' does not match pattern");
            }
        } else {
            throw new IllegalArgumentException("String 'fromPath' does not match pattern");
        }
    }

    public String getFromPath() {
        return this.fromPath;
    }

    public String getToPath() {
        return this.toPath;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.fromPath, this.toPath});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        RelocationPath other = (RelocationPath) obj;
        if ((this.fromPath == other.fromPath || this.fromPath.equals(other.fromPath)) && (this.toPath == other.toPath || this.toPath.equals(other.toPath))) {
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
