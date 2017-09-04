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

public class PathRootError {
    protected final String pathRoot;

    public static class Serializer extends StructSerializer<PathRootError> {
        public static final Serializer INSTANCE = new Serializer();

        public void serialize(PathRootError value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            if (value.pathRoot != null) {
                g.writeFieldName("path_root");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.pathRoot, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public PathRootError deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_pathRoot = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("path_root".equals(field)) {
                        f_pathRoot = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                PathRootError value = new PathRootError(f_pathRoot);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public PathRootError(String pathRoot) {
        this.pathRoot = pathRoot;
    }

    public PathRootError() {
        this(null);
    }

    public String getPathRoot() {
        return this.pathRoot;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.pathRoot});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        PathRootError other = (PathRootError) obj;
        if (this.pathRoot == other.pathRoot || (this.pathRoot != null && this.pathRoot.equals(other.pathRoot))) {
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
