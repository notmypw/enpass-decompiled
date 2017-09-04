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
import java.util.Date;

public class PathLinkMetadata extends LinkMetadata {
    protected final String path;

    static class Serializer extends StructSerializer<PathLinkMetadata> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(PathLinkMetadata value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            writeTag(BoxMetadataUpdateTask.PATH, g);
            g.writeFieldName(BoxSharedLink.FIELD_URL);
            StoneSerializers.string().serialize(value.url, g);
            g.writeFieldName("visibility");
            Serializer.INSTANCE.serialize(value.visibility, g);
            g.writeFieldName(BoxMetadataUpdateTask.PATH);
            StoneSerializers.string().serialize(value.path, g);
            if (value.expires != null) {
                g.writeFieldName("expires");
                StoneSerializers.nullable(StoneSerializers.timestamp()).serialize(value.expires, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public PathLinkMetadata deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
                if (BoxMetadataUpdateTask.PATH.equals(tag)) {
                    tag = null;
                }
            }
            if (tag == null) {
                String f_url = null;
                Visibility f_visibility = null;
                String f_path = null;
                Date f_expires = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxSharedLink.FIELD_URL.equals(field)) {
                        f_url = (String) StoneSerializers.string().deserialize(p);
                    } else if ("visibility".equals(field)) {
                        f_visibility = Serializer.INSTANCE.deserialize(p);
                    } else if (BoxMetadataUpdateTask.PATH.equals(field)) {
                        f_path = (String) StoneSerializers.string().deserialize(p);
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
                } else if (f_path == null) {
                    throw new JsonParseException(p, "Required field \"path\" missing.");
                } else {
                    PathLinkMetadata value = new PathLinkMetadata(f_url, f_visibility, f_path, f_expires);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public PathLinkMetadata(String url, Visibility visibility, String path, Date expires) {
        super(url, visibility, expires);
        if (path == null) {
            throw new IllegalArgumentException("Required value for 'path' is null");
        }
        this.path = path;
    }

    public PathLinkMetadata(String url, Visibility visibility, String path) {
        this(url, visibility, path, null);
    }

    public String getUrl() {
        return this.url;
    }

    public Visibility getVisibility() {
        return this.visibility;
    }

    public String getPath() {
        return this.path;
    }

    public Date getExpires() {
        return this.expires;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.path}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        PathLinkMetadata other = (PathLinkMetadata) obj;
        if ((this.url == other.url || this.url.equals(other.url)) && ((this.visibility == other.visibility || this.visibility.equals(other.visibility)) && (this.path == other.path || this.path.equals(other.path)))) {
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
