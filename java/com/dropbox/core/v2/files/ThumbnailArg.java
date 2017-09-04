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
import in.sinew.enpassengine.Attachment;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

class ThumbnailArg {
    protected final ThumbnailFormat format;
    protected final String path;
    protected final ThumbnailSize size;

    public static class Builder {
        protected ThumbnailFormat format;
        protected final String path;
        protected ThumbnailSize size;

        protected Builder(String path) {
            if (path == null) {
                throw new IllegalArgumentException("Required value for 'path' is null");
            } else if (Pattern.matches("(/(.|[\\r\\n])*|id:.*)|(rev:[0-9a-f]{9,})|(ns:[0-9]+(/.*)?)", path)) {
                this.path = path;
                this.format = ThumbnailFormat.JPEG;
                this.size = ThumbnailSize.W64H64;
            } else {
                throw new IllegalArgumentException("String 'path' does not match pattern");
            }
        }

        public Builder withFormat(ThumbnailFormat format) {
            if (format != null) {
                this.format = format;
            } else {
                this.format = ThumbnailFormat.JPEG;
            }
            return this;
        }

        public Builder withSize(ThumbnailSize size) {
            if (size != null) {
                this.size = size;
            } else {
                this.size = ThumbnailSize.W64H64;
            }
            return this;
        }

        public ThumbnailArg build() {
            return new ThumbnailArg(this.path, this.format, this.size);
        }
    }

    static class Serializer extends StructSerializer<ThumbnailArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ThumbnailArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxMetadataUpdateTask.PATH);
            StoneSerializers.string().serialize(value.path, g);
            g.writeFieldName("format");
            Serializer.INSTANCE.serialize(value.format, g);
            g.writeFieldName(Attachment.ATTACHMENT_SIZE);
            Serializer.INSTANCE.serialize(value.size, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ThumbnailArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_path = null;
                ThumbnailFormat f_format = ThumbnailFormat.JPEG;
                ThumbnailSize f_size = ThumbnailSize.W64H64;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxMetadataUpdateTask.PATH.equals(field)) {
                        f_path = (String) StoneSerializers.string().deserialize(p);
                    } else if ("format".equals(field)) {
                        f_format = Serializer.INSTANCE.deserialize(p);
                    } else if (Attachment.ATTACHMENT_SIZE.equals(field)) {
                        f_size = Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_path == null) {
                    throw new JsonParseException(p, "Required field \"path\" missing.");
                }
                ThumbnailArg value = new ThumbnailArg(f_path, f_format, f_size);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ThumbnailArg(String path, ThumbnailFormat format, ThumbnailSize size) {
        if (path == null) {
            throw new IllegalArgumentException("Required value for 'path' is null");
        } else if (Pattern.matches("(/(.|[\\r\\n])*|id:.*)|(rev:[0-9a-f]{9,})|(ns:[0-9]+(/.*)?)", path)) {
            this.path = path;
            if (format == null) {
                throw new IllegalArgumentException("Required value for 'format' is null");
            }
            this.format = format;
            if (size == null) {
                throw new IllegalArgumentException("Required value for 'size' is null");
            }
            this.size = size;
        } else {
            throw new IllegalArgumentException("String 'path' does not match pattern");
        }
    }

    public ThumbnailArg(String path) {
        this(path, ThumbnailFormat.JPEG, ThumbnailSize.W64H64);
    }

    public String getPath() {
        return this.path;
    }

    public ThumbnailFormat getFormat() {
        return this.format;
    }

    public ThumbnailSize getSize() {
        return this.size;
    }

    public static Builder newBuilder(String path) {
        return new Builder(path);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.path, this.format, this.size});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ThumbnailArg other = (ThumbnailArg) obj;
        if ((this.path == other.path || this.path.equals(other.path)) && ((this.format == other.format || this.format.equals(other.format)) && (this.size == other.size || this.size.equals(other.size)))) {
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
