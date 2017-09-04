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
import java.util.List;

public class SharedContentLinkMetadata extends SharedContentLinkMetadataBase {
    protected final String url;

    static class Serializer extends StructSerializer<SharedContentLinkMetadata> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(SharedContentLinkMetadata value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("audience_options");
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.audienceOptions, g);
            g.writeFieldName("current_audience");
            Serializer.INSTANCE.serialize(value.currentAudience, g);
            g.writeFieldName("link_permissions");
            StoneSerializers.list(Serializer.INSTANCE).serialize(value.linkPermissions, g);
            g.writeFieldName("password_protected");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.passwordProtected), g);
            g.writeFieldName(BoxSharedLink.FIELD_URL);
            StoneSerializers.string().serialize(value.url, g);
            if (value.expiry != null) {
                g.writeFieldName("expiry");
                StoneSerializers.nullable(StoneSerializers.timestamp()).serialize(value.expiry, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public SharedContentLinkMetadata deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<LinkAudience> f_audienceOptions = null;
                LinkAudience f_currentAudience = null;
                List<LinkPermission> f_linkPermissions = null;
                Boolean f_passwordProtected = null;
                String f_url = null;
                Date f_expiry = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("audience_options".equals(field)) {
                        f_audienceOptions = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else if ("current_audience".equals(field)) {
                        f_currentAudience = Serializer.INSTANCE.deserialize(p);
                    } else if ("link_permissions".equals(field)) {
                        f_linkPermissions = (List) StoneSerializers.list(Serializer.INSTANCE).deserialize(p);
                    } else if ("password_protected".equals(field)) {
                        f_passwordProtected = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if (BoxSharedLink.FIELD_URL.equals(field)) {
                        f_url = (String) StoneSerializers.string().deserialize(p);
                    } else if ("expiry".equals(field)) {
                        f_expiry = (Date) StoneSerializers.nullable(StoneSerializers.timestamp()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_audienceOptions == null) {
                    throw new JsonParseException(p, "Required field \"audience_options\" missing.");
                } else if (f_currentAudience == null) {
                    throw new JsonParseException(p, "Required field \"current_audience\" missing.");
                } else if (f_linkPermissions == null) {
                    throw new JsonParseException(p, "Required field \"link_permissions\" missing.");
                } else if (f_passwordProtected == null) {
                    throw new JsonParseException(p, "Required field \"password_protected\" missing.");
                } else if (f_url == null) {
                    throw new JsonParseException(p, "Required field \"url\" missing.");
                } else {
                    SharedContentLinkMetadata value = new SharedContentLinkMetadata(f_audienceOptions, f_currentAudience, f_linkPermissions, f_passwordProtected.booleanValue(), f_url, f_expiry);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public SharedContentLinkMetadata(List<LinkAudience> audienceOptions, LinkAudience currentAudience, List<LinkPermission> linkPermissions, boolean passwordProtected, String url, Date expiry) {
        super(audienceOptions, currentAudience, linkPermissions, passwordProtected, expiry);
        if (url == null) {
            throw new IllegalArgumentException("Required value for 'url' is null");
        }
        this.url = url;
    }

    public SharedContentLinkMetadata(List<LinkAudience> audienceOptions, LinkAudience currentAudience, List<LinkPermission> linkPermissions, boolean passwordProtected, String url) {
        this(audienceOptions, currentAudience, linkPermissions, passwordProtected, url, null);
    }

    public List<LinkAudience> getAudienceOptions() {
        return this.audienceOptions;
    }

    public LinkAudience getCurrentAudience() {
        return this.currentAudience;
    }

    public List<LinkPermission> getLinkPermissions() {
        return this.linkPermissions;
    }

    public boolean getPasswordProtected() {
        return this.passwordProtected;
    }

    public String getUrl() {
        return this.url;
    }

    public Date getExpiry() {
        return this.expiry;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.url}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        SharedContentLinkMetadata other = (SharedContentLinkMetadata) obj;
        if ((this.audienceOptions == other.audienceOptions || this.audienceOptions.equals(other.audienceOptions)) && ((this.currentAudience == other.currentAudience || this.currentAudience.equals(other.currentAudience)) && ((this.linkPermissions == other.linkPermissions || this.linkPermissions.equals(other.linkPermissions)) && this.passwordProtected == other.passwordProtected && (this.url == other.url || this.url.equals(other.url))))) {
            if (this.expiry == other.expiry) {
                return true;
            }
            if (this.expiry != null && this.expiry.equals(other.expiry)) {
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
