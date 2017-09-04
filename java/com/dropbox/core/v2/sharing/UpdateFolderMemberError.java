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

public final class UpdateFolderMemberError {
    public static final UpdateFolderMemberError INSUFFICIENT_PLAN = new UpdateFolderMemberError(Tag.INSUFFICIENT_PLAN, null, null, null);
    public static final UpdateFolderMemberError NO_PERMISSION = new UpdateFolderMemberError(Tag.NO_PERMISSION, null, null, null);
    public static final UpdateFolderMemberError OTHER = new UpdateFolderMemberError(Tag.OTHER, null, null, null);
    private final Tag _tag;
    private final SharedFolderAccessError accessErrorValue;
    private final SharedFolderMemberError memberErrorValue;
    private final AddFolderMemberError noExplicitAccessValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$UpdateFolderMemberError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$UpdateFolderMemberError$Tag[Tag.ACCESS_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$UpdateFolderMemberError$Tag[Tag.MEMBER_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$UpdateFolderMemberError$Tag[Tag.NO_EXPLICIT_ACCESS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$UpdateFolderMemberError$Tag[Tag.INSUFFICIENT_PLAN.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$UpdateFolderMemberError$Tag[Tag.NO_PERMISSION.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$UpdateFolderMemberError$Tag[Tag.OTHER.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    static class Serializer extends UnionSerializer<UpdateFolderMemberError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(UpdateFolderMemberError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$UpdateFolderMemberError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("access_error", g);
                    g.writeFieldName("access_error");
                    Serializer.INSTANCE.serialize(value.accessErrorValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeStartObject();
                    writeTag("member_error", g);
                    g.writeFieldName("member_error");
                    Serializer.INSTANCE.serialize(value.memberErrorValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeStartObject();
                    writeTag("no_explicit_access", g);
                    g.writeFieldName("no_explicit_access");
                    Serializer.INSTANCE.serialize(value.noExplicitAccessValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("insufficient_plan");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("no_permission");
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public UpdateFolderMemberError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            UpdateFolderMemberError value;
            if ("access_error".equals(tag)) {
                StoneSerializer.expectField("access_error", p);
                value = UpdateFolderMemberError.accessError(Serializer.INSTANCE.deserialize(p));
            } else if ("member_error".equals(tag)) {
                StoneSerializer.expectField("member_error", p);
                value = UpdateFolderMemberError.memberError(Serializer.INSTANCE.deserialize(p));
            } else if ("no_explicit_access".equals(tag)) {
                StoneSerializer.expectField("no_explicit_access", p);
                value = UpdateFolderMemberError.noExplicitAccess(Serializer.INSTANCE.deserialize(p));
            } else if ("insufficient_plan".equals(tag)) {
                value = UpdateFolderMemberError.INSUFFICIENT_PLAN;
            } else if ("no_permission".equals(tag)) {
                value = UpdateFolderMemberError.NO_PERMISSION;
            } else {
                value = UpdateFolderMemberError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public enum Tag {
        ACCESS_ERROR,
        MEMBER_ERROR,
        NO_EXPLICIT_ACCESS,
        INSUFFICIENT_PLAN,
        NO_PERMISSION,
        OTHER
    }

    private UpdateFolderMemberError(Tag _tag, SharedFolderAccessError accessErrorValue, SharedFolderMemberError memberErrorValue, AddFolderMemberError noExplicitAccessValue) {
        this._tag = _tag;
        this.accessErrorValue = accessErrorValue;
        this.memberErrorValue = memberErrorValue;
        this.noExplicitAccessValue = noExplicitAccessValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isAccessError() {
        return this._tag == Tag.ACCESS_ERROR;
    }

    public static UpdateFolderMemberError accessError(SharedFolderAccessError value) {
        if (value != null) {
            return new UpdateFolderMemberError(Tag.ACCESS_ERROR, value, null, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public SharedFolderAccessError getAccessErrorValue() {
        if (this._tag == Tag.ACCESS_ERROR) {
            return this.accessErrorValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.ACCESS_ERROR, but was Tag." + this._tag.name());
    }

    public boolean isMemberError() {
        return this._tag == Tag.MEMBER_ERROR;
    }

    public static UpdateFolderMemberError memberError(SharedFolderMemberError value) {
        if (value != null) {
            return new UpdateFolderMemberError(Tag.MEMBER_ERROR, null, value, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public SharedFolderMemberError getMemberErrorValue() {
        if (this._tag == Tag.MEMBER_ERROR) {
            return this.memberErrorValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.MEMBER_ERROR, but was Tag." + this._tag.name());
    }

    public boolean isNoExplicitAccess() {
        return this._tag == Tag.NO_EXPLICIT_ACCESS;
    }

    public static UpdateFolderMemberError noExplicitAccess(AddFolderMemberError value) {
        if (value != null) {
            return new UpdateFolderMemberError(Tag.NO_EXPLICIT_ACCESS, null, null, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public AddFolderMemberError getNoExplicitAccessValue() {
        if (this._tag == Tag.NO_EXPLICIT_ACCESS) {
            return this.noExplicitAccessValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.NO_EXPLICIT_ACCESS, but was Tag." + this._tag.name());
    }

    public boolean isInsufficientPlan() {
        return this._tag == Tag.INSUFFICIENT_PLAN;
    }

    public boolean isNoPermission() {
        return this._tag == Tag.NO_PERMISSION;
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.accessErrorValue, this.memberErrorValue, this.noExplicitAccessValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UpdateFolderMemberError)) {
            return false;
        }
        UpdateFolderMemberError other = (UpdateFolderMemberError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$UpdateFolderMemberError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.accessErrorValue == other.accessErrorValue || this.accessErrorValue.equals(other.accessErrorValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.memberErrorValue == other.memberErrorValue || this.memberErrorValue.equals(other.memberErrorValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                if (this.noExplicitAccessValue == other.noExplicitAccessValue || this.noExplicitAccessValue.equals(other.noExplicitAccessValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                return true;
            case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                return true;
            case IRemoteStorage.BOX_REMOTE /*6*/:
                return true;
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
