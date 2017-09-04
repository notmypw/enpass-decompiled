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

class SaveCopyReferenceArg {
    protected final String copyReference;
    protected final String path;

    static class Serializer extends StructSerializer<SaveCopyReferenceArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(SaveCopyReferenceArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("copy_reference");
            StoneSerializers.string().serialize(value.copyReference, g);
            g.writeFieldName(BoxMetadataUpdateTask.PATH);
            StoneSerializers.string().serialize(value.path, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public SaveCopyReferenceArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_copyReference = null;
                String f_path = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("copy_reference".equals(field)) {
                        f_copyReference = (String) StoneSerializers.string().deserialize(p);
                    } else if (BoxMetadataUpdateTask.PATH.equals(field)) {
                        f_path = (String) StoneSerializers.string().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_copyReference == null) {
                    throw new JsonParseException(p, "Required field \"copy_reference\" missing.");
                } else if (f_path == null) {
                    throw new JsonParseException(p, "Required field \"path\" missing.");
                } else {
                    SaveCopyReferenceArg value = new SaveCopyReferenceArg(f_copyReference, f_path);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public SaveCopyReferenceArg(String copyReference, String path) {
        if (copyReference == null) {
            throw new IllegalArgumentException("Required value for 'copyReference' is null");
        }
        this.copyReference = copyReference;
        if (path == null) {
            throw new IllegalArgumentException("Required value for 'path' is null");
        } else if (Pattern.matches("/(.|[\\r\\n])*", path)) {
            this.path = path;
        } else {
            throw new IllegalArgumentException("String 'path' does not match pattern");
        }
    }

    public String getCopyReference() {
        return this.copyReference;
    }

    public String getPath() {
        return this.path;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.copyReference, this.path});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        SaveCopyReferenceArg other = (SaveCopyReferenceArg) obj;
        if ((this.copyReference == other.copyReference || this.copyReference.equals(other.copyReference)) && (this.path == other.path || this.path.equals(other.path))) {
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
