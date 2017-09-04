package com.dropbox.core.v2.sharing;

import com.box.androidsdk.content.models.BoxSharedLink;
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

class GetSharedLinkMetadataArg {
    protected final String linkPassword;
    protected final String path;
    protected final String url;

    public static class Builder {
        protected String linkPassword;
        protected String path;
        protected final String url;

        protected Builder(String url) {
            if (url == null) {
                throw new IllegalArgumentException("Required value for 'url' is null");
            }
            this.url = url;
            this.path = null;
            this.linkPassword = null;
        }

        public Builder withPath(String path) {
            if (path == null || Pattern.matches("/(.|[\\r\\n])*", path)) {
                this.path = path;
                return this;
            }
            throw new IllegalArgumentException("String 'path' does not match pattern");
        }

        public Builder withLinkPassword(String linkPassword) {
            this.linkPassword = linkPassword;
            return this;
        }

        public GetSharedLinkMetadataArg build() {
            return new GetSharedLinkMetadataArg(this.url, this.path, this.linkPassword);
        }
    }

    static class Serializer extends StructSerializer<GetSharedLinkMetadataArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GetSharedLinkMetadataArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxSharedLink.FIELD_URL);
            StoneSerializers.string().serialize(value.url, g);
            if (value.path != null) {
                g.writeFieldName(BoxMetadataUpdateTask.PATH);
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.path, g);
            }
            if (value.linkPassword != null) {
                g.writeFieldName("link_password");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.linkPassword, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public GetSharedLinkMetadataArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_url = null;
                String f_path = null;
                String f_linkPassword = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxSharedLink.FIELD_URL.equals(field)) {
                        f_url = (String) StoneSerializers.string().deserialize(p);
                    } else if (BoxMetadataUpdateTask.PATH.equals(field)) {
                        f_path = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("link_password".equals(field)) {
                        f_linkPassword = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_url == null) {
                    throw new JsonParseException(p, "Required field \"url\" missing.");
                }
                GetSharedLinkMetadataArg value = new GetSharedLinkMetadataArg(f_url, f_path, f_linkPassword);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public GetSharedLinkMetadataArg(String url, String path, String linkPassword) {
        if (url == null) {
            throw new IllegalArgumentException("Required value for 'url' is null");
        }
        this.url = url;
        if (path == null || Pattern.matches("/(.|[\\r\\n])*", path)) {
            this.path = path;
            this.linkPassword = linkPassword;
            return;
        }
        throw new IllegalArgumentException("String 'path' does not match pattern");
    }

    public GetSharedLinkMetadataArg(String url) {
        this(url, null, null);
    }

    public String getUrl() {
        return this.url;
    }

    public String getPath() {
        return this.path;
    }

    public String getLinkPassword() {
        return this.linkPassword;
    }

    public static Builder newBuilder(String url) {
        return new Builder(url);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.url, this.path, this.linkPassword});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        GetSharedLinkMetadataArg other = (GetSharedLinkMetadataArg) obj;
        if ((this.url == other.url || this.url.equals(other.url)) && (this.path == other.path || (this.path != null && this.path.equals(other.path)))) {
            if (this.linkPassword == other.linkPassword) {
                return true;
            }
            if (this.linkPassword != null && this.linkPassword.equals(other.linkPassword)) {
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
