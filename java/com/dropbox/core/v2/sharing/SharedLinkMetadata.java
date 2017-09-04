package com.dropbox.core.v2.sharing;

import com.box.androidsdk.content.models.BoxEntity;
import com.box.androidsdk.content.models.BoxFile;
import com.box.androidsdk.content.models.BoxFileVersion;
import com.box.androidsdk.content.models.BoxFolder;
import com.box.androidsdk.content.models.BoxSharedLink;
import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.dropbox.core.util.LangUtil;
import com.dropbox.core.v2.users.Team;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.github.clans.fab.BuildConfig;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class SharedLinkMetadata {
    protected final Team contentOwnerTeamInfo;
    protected final Date expires;
    protected final String id;
    protected final LinkPermissions linkPermissions;
    protected final String name;
    protected final String pathLower;
    protected final TeamMemberInfo teamMemberInfo;
    protected final String url;

    public static class Builder {
        protected Team contentOwnerTeamInfo;
        protected Date expires;
        protected String id;
        protected final LinkPermissions linkPermissions;
        protected final String name;
        protected String pathLower;
        protected TeamMemberInfo teamMemberInfo;
        protected final String url;

        protected Builder(String url, String name, LinkPermissions linkPermissions) {
            if (url == null) {
                throw new IllegalArgumentException("Required value for 'url' is null");
            }
            this.url = url;
            if (name == null) {
                throw new IllegalArgumentException("Required value for 'name' is null");
            }
            this.name = name;
            if (linkPermissions == null) {
                throw new IllegalArgumentException("Required value for 'linkPermissions' is null");
            }
            this.linkPermissions = linkPermissions;
            this.id = null;
            this.expires = null;
            this.pathLower = null;
            this.teamMemberInfo = null;
            this.contentOwnerTeamInfo = null;
        }

        public Builder withId(String id) {
            if (id == null || id.length() >= 1) {
                this.id = id;
                return this;
            }
            throw new IllegalArgumentException("String 'id' is shorter than 1");
        }

        public Builder withExpires(Date expires) {
            this.expires = LangUtil.truncateMillis(expires);
            return this;
        }

        public Builder withPathLower(String pathLower) {
            this.pathLower = pathLower;
            return this;
        }

        public Builder withTeamMemberInfo(TeamMemberInfo teamMemberInfo) {
            this.teamMemberInfo = teamMemberInfo;
            return this;
        }

        public Builder withContentOwnerTeamInfo(Team contentOwnerTeamInfo) {
            this.contentOwnerTeamInfo = contentOwnerTeamInfo;
            return this;
        }

        public SharedLinkMetadata build() {
            return new SharedLinkMetadata(this.url, this.name, this.linkPermissions, this.id, this.expires, this.pathLower, this.teamMemberInfo, this.contentOwnerTeamInfo);
        }
    }

    static class Serializer extends StructSerializer<SharedLinkMetadata> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(SharedLinkMetadata value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (value instanceof FileLinkMetadata) {
                Serializer.INSTANCE.serialize((FileLinkMetadata) value, g, collapse);
            } else if (value instanceof FolderLinkMetadata) {
                Serializer.INSTANCE.serialize((FolderLinkMetadata) value, g, collapse);
            } else {
                if (!collapse) {
                    g.writeStartObject();
                }
                g.writeFieldName(BoxSharedLink.FIELD_URL);
                StoneSerializers.string().serialize(value.url, g);
                g.writeFieldName(BoxFileVersion.FIELD_NAME);
                StoneSerializers.string().serialize(value.name, g);
                g.writeFieldName("link_permissions");
                Serializer.INSTANCE.serialize(value.linkPermissions, g);
                if (value.id != null) {
                    g.writeFieldName(BoxEntity.FIELD_ID);
                    StoneSerializers.nullable(StoneSerializers.string()).serialize(value.id, g);
                }
                if (value.expires != null) {
                    g.writeFieldName("expires");
                    StoneSerializers.nullable(StoneSerializers.timestamp()).serialize(value.expires, g);
                }
                if (value.pathLower != null) {
                    g.writeFieldName("path_lower");
                    StoneSerializers.nullable(StoneSerializers.string()).serialize(value.pathLower, g);
                }
                if (value.teamMemberInfo != null) {
                    g.writeFieldName("team_member_info");
                    StoneSerializers.nullableStruct(Serializer.INSTANCE).serialize(value.teamMemberInfo, g);
                }
                if (value.contentOwnerTeamInfo != null) {
                    g.writeFieldName("content_owner_team_info");
                    StoneSerializers.nullableStruct(com.dropbox.core.v2.users.Team.Serializer.INSTANCE).serialize(value.contentOwnerTeamInfo, g);
                }
                if (!collapse) {
                    g.writeEndObject();
                }
            }
        }

        public SharedLinkMetadata deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            SharedLinkMetadata value;
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
                String f_name = null;
                LinkPermissions f_linkPermissions = null;
                String f_id = null;
                Date f_expires = null;
                String f_pathLower = null;
                TeamMemberInfo f_teamMemberInfo = null;
                Team f_contentOwnerTeamInfo = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxSharedLink.FIELD_URL.equals(field)) {
                        f_url = (String) StoneSerializers.string().deserialize(p);
                    } else if (BoxFileVersion.FIELD_NAME.equals(field)) {
                        f_name = (String) StoneSerializers.string().deserialize(p);
                    } else if ("link_permissions".equals(field)) {
                        f_linkPermissions = (LinkPermissions) Serializer.INSTANCE.deserialize(p);
                    } else if (BoxEntity.FIELD_ID.equals(field)) {
                        f_id = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("expires".equals(field)) {
                        f_expires = (Date) StoneSerializers.nullable(StoneSerializers.timestamp()).deserialize(p);
                    } else if ("path_lower".equals(field)) {
                        f_pathLower = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("team_member_info".equals(field)) {
                        f_teamMemberInfo = (TeamMemberInfo) StoneSerializers.nullableStruct(Serializer.INSTANCE).deserialize(p);
                    } else if ("content_owner_team_info".equals(field)) {
                        f_contentOwnerTeamInfo = (Team) StoneSerializers.nullableStruct(com.dropbox.core.v2.users.Team.Serializer.INSTANCE).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_url == null) {
                    throw new JsonParseException(p, "Required field \"url\" missing.");
                } else if (f_name == null) {
                    throw new JsonParseException(p, "Required field \"name\" missing.");
                } else if (f_linkPermissions == null) {
                    throw new JsonParseException(p, "Required field \"link_permissions\" missing.");
                } else {
                    value = new SharedLinkMetadata(f_url, f_name, f_linkPermissions, f_id, f_expires, f_pathLower, f_teamMemberInfo, f_contentOwnerTeamInfo);
                }
            } else if (BuildConfig.FLAVOR.equals(tag)) {
                value = INSTANCE.deserialize(p, true);
            } else if (BoxFile.TYPE.equals(tag)) {
                value = Serializer.INSTANCE.deserialize(p, true);
            } else if (BoxFolder.TYPE.equals(tag)) {
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

    public SharedLinkMetadata(String url, String name, LinkPermissions linkPermissions, String id, Date expires, String pathLower, TeamMemberInfo teamMemberInfo, Team contentOwnerTeamInfo) {
        if (url == null) {
            throw new IllegalArgumentException("Required value for 'url' is null");
        }
        this.url = url;
        if (id == null || id.length() >= 1) {
            this.id = id;
            if (name == null) {
                throw new IllegalArgumentException("Required value for 'name' is null");
            }
            this.name = name;
            this.expires = LangUtil.truncateMillis(expires);
            this.pathLower = pathLower;
            if (linkPermissions == null) {
                throw new IllegalArgumentException("Required value for 'linkPermissions' is null");
            }
            this.linkPermissions = linkPermissions;
            this.teamMemberInfo = teamMemberInfo;
            this.contentOwnerTeamInfo = contentOwnerTeamInfo;
            return;
        }
        throw new IllegalArgumentException("String 'id' is shorter than 1");
    }

    public SharedLinkMetadata(String url, String name, LinkPermissions linkPermissions) {
        this(url, name, linkPermissions, null, null, null, null, null);
    }

    public String getUrl() {
        return this.url;
    }

    public String getName() {
        return this.name;
    }

    public LinkPermissions getLinkPermissions() {
        return this.linkPermissions;
    }

    public String getId() {
        return this.id;
    }

    public Date getExpires() {
        return this.expires;
    }

    public String getPathLower() {
        return this.pathLower;
    }

    public TeamMemberInfo getTeamMemberInfo() {
        return this.teamMemberInfo;
    }

    public Team getContentOwnerTeamInfo() {
        return this.contentOwnerTeamInfo;
    }

    public static Builder newBuilder(String url, String name, LinkPermissions linkPermissions) {
        return new Builder(url, name, linkPermissions);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.url, this.id, this.name, this.expires, this.pathLower, this.linkPermissions, this.teamMemberInfo, this.contentOwnerTeamInfo});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        SharedLinkMetadata other = (SharedLinkMetadata) obj;
        if ((this.url == other.url || this.url.equals(other.url)) && ((this.name == other.name || this.name.equals(other.name)) && ((this.linkPermissions == other.linkPermissions || this.linkPermissions.equals(other.linkPermissions)) && ((this.id == other.id || (this.id != null && this.id.equals(other.id))) && ((this.expires == other.expires || (this.expires != null && this.expires.equals(other.expires))) && ((this.pathLower == other.pathLower || (this.pathLower != null && this.pathLower.equals(other.pathLower))) && (this.teamMemberInfo == other.teamMemberInfo || (this.teamMemberInfo != null && this.teamMemberInfo.equals(other.teamMemberInfo))))))))) {
            if (this.contentOwnerTeamInfo == other.contentOwnerTeamInfo) {
                return true;
            }
            if (this.contentOwnerTeamInfo != null && this.contentOwnerTeamInfo.equals(other.contentOwnerTeamInfo)) {
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
