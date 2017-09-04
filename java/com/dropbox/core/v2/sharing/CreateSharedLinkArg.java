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

class CreateSharedLinkArg {
    protected final String path;
    protected final PendingUploadMode pendingUpload;
    protected final boolean shortUrl;

    public static class Builder {
        protected final String path;
        protected PendingUploadMode pendingUpload;
        protected boolean shortUrl;

        protected Builder(String path) {
            if (path == null) {
                throw new IllegalArgumentException("Required value for 'path' is null");
            }
            this.path = path;
            this.shortUrl = false;
            this.pendingUpload = null;
        }

        public Builder withShortUrl(Boolean shortUrl) {
            if (shortUrl != null) {
                this.shortUrl = shortUrl.booleanValue();
            } else {
                this.shortUrl = false;
            }
            return this;
        }

        public Builder withPendingUpload(PendingUploadMode pendingUpload) {
            this.pendingUpload = pendingUpload;
            return this;
        }

        public CreateSharedLinkArg build() {
            return new CreateSharedLinkArg(this.path, this.shortUrl, this.pendingUpload);
        }
    }

    static class Serializer extends StructSerializer<CreateSharedLinkArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(CreateSharedLinkArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxMetadataUpdateTask.PATH);
            StoneSerializers.string().serialize(value.path, g);
            g.writeFieldName("short_url");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.shortUrl), g);
            if (value.pendingUpload != null) {
                g.writeFieldName("pending_upload");
                StoneSerializers.nullable(Serializer.INSTANCE).serialize(value.pendingUpload, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public CreateSharedLinkArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_path = null;
                Boolean f_shortUrl = Boolean.valueOf(false);
                PendingUploadMode f_pendingUpload = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxMetadataUpdateTask.PATH.equals(field)) {
                        f_path = (String) StoneSerializers.string().deserialize(p);
                    } else if ("short_url".equals(field)) {
                        f_shortUrl = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("pending_upload".equals(field)) {
                        f_pendingUpload = (PendingUploadMode) StoneSerializers.nullable(Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_path == null) {
                    throw new JsonParseException(p, "Required field \"path\" missing.");
                }
                CreateSharedLinkArg value = new CreateSharedLinkArg(f_path, f_shortUrl.booleanValue(), f_pendingUpload);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public CreateSharedLinkArg(String path, boolean shortUrl, PendingUploadMode pendingUpload) {
        if (path == null) {
            throw new IllegalArgumentException("Required value for 'path' is null");
        }
        this.path = path;
        this.shortUrl = shortUrl;
        this.pendingUpload = pendingUpload;
    }

    public CreateSharedLinkArg(String path) {
        this(path, false, null);
    }

    public String getPath() {
        return this.path;
    }

    public boolean getShortUrl() {
        return this.shortUrl;
    }

    public PendingUploadMode getPendingUpload() {
        return this.pendingUpload;
    }

    public static Builder newBuilder(String path) {
        return new Builder(path);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.path, Boolean.valueOf(this.shortUrl), this.pendingUpload});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        CreateSharedLinkArg other = (CreateSharedLinkArg) obj;
        if ((this.path == other.path || this.path.equals(other.path)) && this.shortUrl == other.shortUrl) {
            if (this.pendingUpload == other.pendingUpload) {
                return true;
            }
            if (this.pendingUpload != null && this.pendingUpload.equals(other.pendingUpload)) {
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
