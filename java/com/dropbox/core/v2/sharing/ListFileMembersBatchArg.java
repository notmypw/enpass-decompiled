package com.dropbox.core.v2.sharing;

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
import java.util.regex.Pattern;

class ListFileMembersBatchArg {
    protected final List<String> files;
    protected final long limit;

    static class Serializer extends StructSerializer<ListFileMembersBatchArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListFileMembersBatchArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("files");
            StoneSerializers.list(StoneSerializers.string()).serialize(value.files, g);
            g.writeFieldName(BoxList.FIELD_LIMIT);
            StoneSerializers.uInt32().serialize(Long.valueOf(value.limit), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ListFileMembersBatchArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<String> f_files = null;
                Long f_limit = Long.valueOf(10);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("files".equals(field)) {
                        f_files = (List) StoneSerializers.list(StoneSerializers.string()).deserialize(p);
                    } else if (BoxList.FIELD_LIMIT.equals(field)) {
                        f_limit = (Long) StoneSerializers.uInt32().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_files == null) {
                    throw new JsonParseException(p, "Required field \"files\" missing.");
                }
                ListFileMembersBatchArg value = new ListFileMembersBatchArg(f_files, f_limit.longValue());
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ListFileMembersBatchArg(List<String> files, long limit) {
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
            if (limit > 20) {
                throw new IllegalArgumentException("Number 'limit' is larger than 20L");
            }
            this.limit = limit;
        }
    }

    public ListFileMembersBatchArg(List<String> files) {
        this(files, 10);
    }

    public List<String> getFiles() {
        return this.files;
    }

    public long getLimit() {
        return this.limit;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.files, Long.valueOf(this.limit)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ListFileMembersBatchArg other = (ListFileMembersBatchArg) obj;
        if ((this.files == other.files || this.files.equals(other.files)) && this.limit == other.limit) {
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
