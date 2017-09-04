package com.dropbox.core.v2.sharing;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.UnionSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import in.sinew.enpass.IRemoteStorage;
import java.io.IOException;
import java.util.Arrays;
import net.sqlcipher.database.SQLiteDatabase;

public final class ShareFolderError {
    public static final ShareFolderError DISALLOWED_SHARED_LINK_POLICY = new ShareFolderError(Tag.DISALLOWED_SHARED_LINK_POLICY, null);
    public static final ShareFolderError EMAIL_UNVERIFIED = new ShareFolderError(Tag.EMAIL_UNVERIFIED, null);
    public static final ShareFolderError NO_PERMISSION = new ShareFolderError(Tag.NO_PERMISSION, null);
    public static final ShareFolderError OTHER = new ShareFolderError(Tag.OTHER, null);
    public static final ShareFolderError TEAM_POLICY_DISALLOWS_MEMBER_POLICY = new ShareFolderError(Tag.TEAM_POLICY_DISALLOWS_MEMBER_POLICY, null);
    private final Tag _tag;
    private final SharePathError badPathValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$ShareFolderError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$ShareFolderError$Tag[Tag.EMAIL_UNVERIFIED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$ShareFolderError$Tag[Tag.BAD_PATH.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$ShareFolderError$Tag[Tag.TEAM_POLICY_DISALLOWS_MEMBER_POLICY.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$ShareFolderError$Tag[Tag.DISALLOWED_SHARED_LINK_POLICY.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$ShareFolderError$Tag[Tag.OTHER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$ShareFolderError$Tag[Tag.NO_PERMISSION.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    static class Serializer extends UnionSerializer<ShareFolderError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ShareFolderError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$ShareFolderError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("email_unverified");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeStartObject();
                    writeTag("bad_path", g);
                    g.writeFieldName("bad_path");
                    Serializer.INSTANCE.serialize(value.badPathValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("team_policy_disallows_member_policy");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("disallowed_shared_link_policy");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("other");
                    return;
                case IRemoteStorage.BOX_REMOTE /*6*/:
                    g.writeString("no_permission");
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value.tag());
            }
        }

        public ShareFolderError deserialize(JsonParser p) throws IOException, JsonParseException {
            boolean collapsed;
            String tag;
            if (p.getCurrentToken() == JsonToken.VALUE_STRING) {
                collapsed = true;
                tag = StoneSerializer.getStringValue(p);
                p.nextToken();
            } else {
                collapsed = false;
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                throw new JsonParseException(p, "Required field missing: .tag");
            }
            ShareFolderError value;
            if ("email_unverified".equals(tag)) {
                value = ShareFolderError.EMAIL_UNVERIFIED;
            } else if ("bad_path".equals(tag)) {
                StoneSerializer.expectField("bad_path", p);
                value = ShareFolderError.badPath(Serializer.INSTANCE.deserialize(p));
            } else if ("team_policy_disallows_member_policy".equals(tag)) {
                value = ShareFolderError.TEAM_POLICY_DISALLOWS_MEMBER_POLICY;
            } else if ("disallowed_shared_link_policy".equals(tag)) {
                value = ShareFolderError.DISALLOWED_SHARED_LINK_POLICY;
            } else if ("other".equals(tag)) {
                value = ShareFolderError.OTHER;
            } else if ("no_permission".equals(tag)) {
                value = ShareFolderError.NO_PERMISSION;
            } else {
                throw new JsonParseException(p, "Unknown tag: " + tag);
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public enum Tag {
        EMAIL_UNVERIFIED,
        BAD_PATH,
        TEAM_POLICY_DISALLOWS_MEMBER_POLICY,
        DISALLOWED_SHARED_LINK_POLICY,
        OTHER,
        NO_PERMISSION
    }

    private ShareFolderError(Tag _tag, SharePathError badPathValue) {
        this._tag = _tag;
        this.badPathValue = badPathValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isEmailUnverified() {
        return this._tag == Tag.EMAIL_UNVERIFIED;
    }

    public boolean isBadPath() {
        return this._tag == Tag.BAD_PATH;
    }

    public static ShareFolderError badPath(SharePathError value) {
        if (value != null) {
            return new ShareFolderError(Tag.BAD_PATH, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public SharePathError getBadPathValue() {
        if (this._tag == Tag.BAD_PATH) {
            return this.badPathValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.BAD_PATH, but was Tag." + this._tag.name());
    }

    public boolean isTeamPolicyDisallowsMemberPolicy() {
        return this._tag == Tag.TEAM_POLICY_DISALLOWS_MEMBER_POLICY;
    }

    public boolean isDisallowedSharedLinkPolicy() {
        return this._tag == Tag.DISALLOWED_SHARED_LINK_POLICY;
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public boolean isNoPermission() {
        return this._tag == Tag.NO_PERMISSION;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.badPathValue}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ShareFolderError)) {
            return false;
        }
        ShareFolderError other = (ShareFolderError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$ShareFolderError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
            case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
            case IRemoteStorage.BOX_REMOTE /*6*/:
                return true;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.badPathValue == other.badPathValue || this.badPathValue.equals(other.badPathValue)) {
                    z = true;
                }
                return z;
            default:
                return false;
        }
    }

    public String toString() {
        return Serializer.INSTANCE.serialize((Object) this, false);
    }

    public String toStringMultiline() {
        return Serializer.INSTANCE.serialize((Object) this, true);
    }
}
