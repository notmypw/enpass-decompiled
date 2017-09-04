package com.dropbox.core.v2.sharing;

import com.box.androidsdk.content.models.BoxCollection;
import com.box.androidsdk.content.models.BoxSharedLink;
import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.dropbox.core.util.LangUtil;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.github.clans.fab.BuildConfig;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class LinkMetadata {
    protected final Date expires;
    protected final String url;
    protected final Visibility visibility;

    static class Serializer extends StructSerializer<LinkMetadata> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(LinkMetadata value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (value instanceof PathLinkMetadata) {
                Serializer.INSTANCE.serialize((PathLinkMetadata) value, g, collapse);
            } else if (value instanceof CollectionLinkMetadata) {
                Serializer.INSTANCE.serialize((CollectionLinkMetadata) value, g, collapse);
            } else {
                if (!collapse) {
                    g.writeStartObject();
                }
                g.writeFieldName(BoxSharedLink.FIELD_URL);
                StoneSerializers.string().serialize(value.url, g);
                g.writeFieldName("visibility");
                Serializer.INSTANCE.serialize(value.visibility, g);
                if (value.expires != null) {
                    g.writeFieldName("expires");
                    StoneSerializers.nullable(StoneSerializers.timestamp()).serialize(value.expires, g);
                }
                if (!collapse) {
                    g.writeEndObject();
                }
            }
        }

        public LinkMetadata deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            LinkMetadata value;
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
                if (BuildConfig.FLAVOR.equals(tag)) {
                    tag = null;
                }
            }
            if (tag == null) {
                String f_url = null;
                Visibility f_visibility = null;
                Date f_expires = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxSharedLink.FIELD_URL.equals(field)) {
                        f_url = (String) StoneSerializers.string().deserialize(p);
                    } else if ("visibility".equals(field)) {
                        f_visibility = Serializer.INSTANCE.deserialize(p);
                    } else if ("expires".equals(field)) {
                        f_expires = (Date) StoneSerializers.nullable(StoneSerializers.timestamp()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_url == null) {
                    throw new JsonParseException(p, "Required field \"url\" missing.");
                } else if (f_visibility == null) {
                    throw new JsonParseException(p, "Required field \"visibility\" missing.");
                } else {
                    value = new LinkMetadata(f_url, f_visibility, f_expires);
                }
            } else if (BuildConfig.FLAVOR.equals(tag)) {
                value = INSTANCE.deserialize(p, true);
            } else if (BoxMetadataUpdateTask.PATH.equals(tag)) {
                value = Serializer.INSTANCE.deserialize(p, true);
            } else if (BoxCollection.TYPE.equals(tag)) {
                value = Serializer.INSTANCE.deserialize(p, true);
            } else {
                throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
            }
            if (!collapsed) {
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public LinkMetadata(String url, Visibility visibility, Date expires) {
        if (url == null) {
            throw new IllegalArgumentException("Required value for 'url' is null");
        }
        this.url = url;
        if (visibility == null) {
            throw new IllegalArgumentException("Required value for 'visibility' is null");
        }
        this.visibility = visibility;
        this.expires = LangUtil.truncateMillis(expires);
    }

    public LinkMetadata(String url, Visibility visibility) {
        this(url, visibility, null);
    }

    public String getUrl() {
        return this.url;
    }

    public Visibility getVisibility() {
        return this.visibility;
    }

    public Date getExpires() {
        return this.expires;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.url, this.visibility, this.expires});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        LinkMetadata other = (LinkMetadata) obj;
        if ((this.url == other.url || this.url.equals(other.url)) && (this.visibility == other.visibility || this.visibility.equals(other.visibility))) {
            if (this.expires == other.expires) {
                return true;
            }
            if (this.expires != null && this.expires.equals(other.expires)) {
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
