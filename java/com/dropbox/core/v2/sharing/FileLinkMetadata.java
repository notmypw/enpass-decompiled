package com.dropbox.core.v2.sharing;

import com.box.androidsdk.content.models.BoxEntity;
import com.box.androidsdk.content.models.BoxFile;
import com.box.androidsdk.content.models.BoxFileVersion;
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
import in.sinew.enpassengine.Attachment;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Pattern;

public class FileLinkMetadata extends SharedLinkMetadata {
    protected final Date clientModified;
    protected final String rev;
    protected final Date serverModified;
    protected final long size;

    public static class Builder extends com.dropbox.core.v2.sharing.SharedLinkMetadata.Builder {
        protected final Date clientModified;
        protected final String rev;
        protected final Date serverModified;
        protected final long size;

        protected Builder(String url, String name, LinkPermissions linkPermissions, Date clientModified, Date serverModified, String rev, long size) {
            super(url, name, linkPermissions);
            if (clientModified == null) {
                throw new IllegalArgumentException("Required value for 'clientModified' is null");
            }
            this.clientModified = LangUtil.truncateMillis(clientModified);
            if (serverModified == null) {
                throw new IllegalArgumentException("Required value for 'serverModified' is null");
            }
            this.serverModified = LangUtil.truncateMillis(serverModified);
            if (rev == null) {
                throw new IllegalArgumentException("Required value for 'rev' is null");
            } else if (rev.length() < 9) {
                throw new IllegalArgumentException("String 'rev' is shorter than 9");
            } else if (Pattern.matches("[0-9a-f]+", rev)) {
                this.rev = rev;
                this.size = size;
            } else {
                throw new IllegalArgumentException("String 'rev' does not match pattern");
            }
        }

        public Builder withId(String id) {
            super.withId(id);
            return this;
        }

        public Builder withExpires(Date expires) {
            super.withExpires(expires);
            return this;
        }

        public Builder withPathLower(String pathLower) {
            super.withPathLower(pathLower);
            return this;
        }

        public Builder withTeamMemberInfo(TeamMemberInfo teamMemberInfo) {
            super.withTeamMemberInfo(teamMemberInfo);
            return this;
        }

        public Builder withContentOwnerTeamInfo(Team contentOwnerTeamInfo) {
            super.withContentOwnerTeamInfo(contentOwnerTeamInfo);
            return this;
        }

        public FileLinkMetadata build() {
            return new FileLinkMetadata(this.url, this.name, this.linkPermissions, this.clientModified, this.serverModified, this.rev, this.size, this.id, this.expires, this.pathLower, this.teamMemberInfo, this.contentOwnerTeamInfo);
        }
    }

    static class Serializer extends StructSerializer<FileLinkMetadata> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(FileLinkMetadata value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            writeTag(BoxFile.TYPE, g);
            g.writeFieldName(BoxSharedLink.FIELD_URL);
            StoneSerializers.string().serialize(value.url, g);
            g.writeFieldName(BoxFileVersion.FIELD_NAME);
            StoneSerializers.string().serialize(value.name, g);
            g.writeFieldName("link_permissions");
            Serializer.INSTANCE.serialize(value.linkPermissions, g);
            g.writeFieldName("client_modified");
            StoneSerializers.timestamp().serialize(value.clientModified, g);
            g.writeFieldName("server_modified");
            StoneSerializers.timestamp().serialize(value.serverModified, g);
            g.writeFieldName("rev");
            StoneSerializers.string().serialize(value.rev, g);
            g.writeFieldName(Attachment.ATTACHMENT_SIZE);
            StoneSerializers.uInt64().serialize(Long.valueOf(value.size), g);
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

        public FileLinkMetadata deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
                if (BoxFile.TYPE.equals(tag)) {
                    tag = null;
                }
            }
            if (tag == null) {
                String f_url = null;
                String f_name = null;
                LinkPermissions f_linkPermissions = null;
                Date f_clientModified = null;
                Date f_serverModified = null;
                String f_rev = null;
                Long f_size = null;
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
                    } else if ("client_modified".equals(field)) {
                        f_clientModified = (Date) StoneSerializers.timestamp().deserialize(p);
                    } else if ("server_modified".equals(field)) {
                        f_serverModified = (Date) StoneSerializers.timestamp().deserialize(p);
                    } else if ("rev".equals(field)) {
                        f_rev = (String) StoneSerializers.string().deserialize(p);
                    } else if (Attachment.ATTACHMENT_SIZE.equals(field)) {
                        f_size = (Long) StoneSerializers.uInt64().deserialize(p);
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
                } else if (f_clientModified == null) {
                    throw new JsonParseException(p, "Required field \"client_modified\" missing.");
                } else if (f_serverModified == null) {
                    throw new JsonParseException(p, "Required field \"server_modified\" missing.");
                } else if (f_rev == null) {
                    throw new JsonParseException(p, "Required field \"rev\" missing.");
                } else if (f_size == null) {
                    throw new JsonParseException(p, "Required field \"size\" missing.");
                } else {
                    FileLinkMetadata value = new FileLinkMetadata(f_url, f_name, f_linkPermissions, f_clientModified, f_serverModified, f_rev, f_size.longValue(), f_id, f_expires, f_pathLower, f_teamMemberInfo, f_contentOwnerTeamInfo);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public FileLinkMetadata(String url, String name, LinkPermissions linkPermissions, Date clientModified, Date serverModified, String rev, long size, String id, Date expires, String pathLower, TeamMemberInfo teamMemberInfo, Team contentOwnerTeamInfo) {
        super(url, name, linkPermissions, id, expires, pathLower, teamMemberInfo, contentOwnerTeamInfo);
        if (clientModified == null) {
            throw new IllegalArgumentException("Required value for 'clientModified' is null");
        }
        this.clientModified = LangUtil.truncateMillis(clientModified);
        if (serverModified == null) {
            throw new IllegalArgumentException("Required value for 'serverModified' is null");
        }
        this.serverModified = LangUtil.truncateMillis(serverModified);
        if (rev == null) {
            throw new IllegalArgumentException("Required value for 'rev' is null");
        } else if (rev.length() < 9) {
            throw new IllegalArgumentException("String 'rev' is shorter than 9");
        } else if (Pattern.matches("[0-9a-f]+", rev)) {
            this.rev = rev;
            this.size = size;
        } else {
            throw new IllegalArgumentException("String 'rev' does not match pattern");
        }
    }

    public FileLinkMetadata(String url, String name, LinkPermissions linkPermissions, Date clientModified, Date serverModified, String rev, long size) {
        this(url, name, linkPermissions, clientModified, serverModified, rev, size, null, null, null, null, null);
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

    public Date getClientModified() {
        return this.clientModified;
    }

    public Date getServerModified() {
        return this.serverModified;
    }

    public String getRev() {
        return this.rev;
    }

    public long getSize() {
        return this.size;
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

    public static Builder newBuilder(String url, String name, LinkPermissions linkPermissions, Date clientModified, Date serverModified, String rev, long size) {
        return new Builder(url, name, linkPermissions, clientModified, serverModified, rev, size);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.clientModified, this.serverModified, this.rev, Long.valueOf(this.size)}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        FileLinkMetadata other = (FileLinkMetadata) obj;
        if ((this.url == other.url || this.url.equals(other.url)) && ((this.name == other.name || this.name.equals(other.name)) && ((this.linkPermissions == other.linkPermissions || this.linkPermissions.equals(other.linkPermissions)) && ((this.clientModified == other.clientModified || this.clientModified.equals(other.clientModified)) && ((this.serverModified == other.serverModified || this.serverModified.equals(other.serverModified)) && ((this.rev == other.rev || this.rev.equals(other.rev)) && this.size == other.size && ((this.id == other.id || (this.id != null && this.id.equals(other.id))) && ((this.expires == other.expires || (this.expires != null && this.expires.equals(other.expires))) && ((this.pathLower == other.pathLower || (this.pathLower != null && this.pathLower.equals(other.pathLower))) && (this.teamMemberInfo == other.teamMemberInfo || (this.teamMemberInfo != null && this.teamMemberInfo.equals(other.teamMemberInfo)))))))))))) {
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
