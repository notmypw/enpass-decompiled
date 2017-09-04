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
import java.util.regex.Pattern;

class GetFileMetadataBatchArg {
    protected final List<FileAction> actions;
    protected final List<String> files;

    static class Serializer extends StructSerializer<GetFileMetadataBatchArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GetFileMetadataBatchArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("files");
            StoneSerializers.list(StoneSerializers.string()).serialize(value.files, g);
            if (value.actions != null) {
                g.writeFieldName("actions");
                StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).serialize(value.actions, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public GetFileMetadataBatchArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<String> f_files = null;
                List<FileAction> f_actions = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("files".equals(field)) {
                        f_files = (List) StoneSerializers.list(StoneSerializers.string()).deserialize(p);
                    } else if ("actions".equals(field)) {
                        f_actions = (List) StoneSerializers.nullable(StoneSerializers.list(Serializer.INSTANCE)).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_files == null) {
                    throw new JsonParseException(p, "Required field \"files\" missing.");
                }
                GetFileMetadataBatchArg value = new GetFileMetadataBatchArg(f_files, f_actions);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public GetFileMetadataBatchArg(List<String> files, List<FileAction> actions) {
        if (files == null) {
            throw new IllegalArgumentException("Required value for 'files' is null");
        } else if (files.size() > 100) {
            throw new IllegalArgumentException("List 'files' has more than 100 items");
        } else {
            for (String x : files) {
                if (x == null) {
                    throw new IllegalArgumentException("An item in list 'files' is null");
                } else if (x.length() < 1) {
                    throw new IllegalArgumentException("Stringan item in list 'files' is shorter than 1");
                } else if (!Pattern.matches("((/|id:).*|nspath:[0-9]+:.*)|ns:[0-9]+(/.*)?", x)) {
                    throw new IllegalArgumentException("Stringan item in list 'files' does not match pattern");
                }
            }
            this.files = files;
            if (actions != null) {
                for (FileAction x2 : actions) {
                    if (x2 == null) {
                        throw new IllegalArgumentException("An item in list 'actions' is null");
                    }
                }
            }
            this.actions = actions;
        }
    }

    public GetFileMetadataBatchArg(List<String> files) {
        this(files, null);
    }

    public List<String> getFiles() {
        return this.files;
    }

    public List<FileAction> getActions() {
        return this.actions;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.files, this.actions});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        GetFileMetadataBatchArg other = (GetFileMetadataBatchArg) obj;
        if (this.files == other.files || this.files.equals(other.files)) {
            if (this.actions == other.actions) {
                return true;
            }
            if (this.actions != null && this.actions.equals(other.actions)) {
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
