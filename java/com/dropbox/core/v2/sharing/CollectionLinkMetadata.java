package com.dropbox.core.v2.sharing;

import com.box.androidsdk.content.models.BoxCollection;
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
import java.util.Date;

public class CollectionLinkMetadata extends LinkMetadata {

    static class Serializer extends StructSerializer<CollectionLinkMetadata> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(CollectionLinkMetadata value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            writeTag(BoxCollection.TYPE, g);
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

        public CollectionLinkMetadata deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
                if (BoxCollection.TYPE.equals(tag)) {
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
                    CollectionLinkMetadata value = new CollectionLinkMetadata(f_url, f_visibility, f_expires);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public CollectionLinkMetadata(String url, Visibility visibility, Date expires) {
        super(url, visibility, expires);
    }

    public CollectionLinkMetadata(String url, Visibility visibility) {
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
        return getClass().toString().hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        CollectionLinkMetadata other = (CollectionLinkMetadata) obj;
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
