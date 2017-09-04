package com.dropbox.core.v2.sharing;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.UnionSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;
import net.sqlcipher.database.SQLiteDatabase;

public final class JobError {
    public static final JobError OTHER = new JobError(Tag.OTHER, null, null, null);
    private final Tag _tag;
    private final RelinquishFolderMembershipError relinquishFolderMembershipErrorValue;
    private final RemoveFolderMemberError removeFolderMemberErrorValue;
    private final UnshareFolderError unshareFolderErrorValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$JobError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$JobError$Tag[Tag.UNSHARE_FOLDER_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$JobError$Tag[Tag.REMOVE_FOLDER_MEMBER_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$JobError$Tag[Tag.RELINQUISH_FOLDER_MEMBERSHIP_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$JobError$Tag[Tag.OTHER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    static class Serializer extends UnionSerializer<JobError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(JobError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$JobError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("unshare_folder_error", g);
                    g.writeFieldName("unshare_folder_error");
                    Serializer.INSTANCE.serialize(value.unshareFolderErrorValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeStartObject();
                    writeTag("remove_folder_member_error", g);
                    g.writeFieldName("remove_folder_member_error");
                    Serializer.INSTANCE.serialize(value.removeFolderMemberErrorValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeStartObject();
                    writeTag("relinquish_folder_membership_error", g);
                    g.writeFieldName("relinquish_folder_membership_error");
                    Serializer.INSTANCE.serialize(value.relinquishFolderMembershipErrorValue, g);
                    g.writeEndObject();
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public JobError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            JobError value;
            if ("unshare_folder_error".equals(tag)) {
                StoneSerializer.expectField("unshare_folder_error", p);
                value = JobError.unshareFolderError(Serializer.INSTANCE.deserialize(p));
            } else if ("remove_folder_member_error".equals(tag)) {
                StoneSerializer.expectField("remove_folder_member_error", p);
                value = JobError.removeFolderMemberError(Serializer.INSTANCE.deserialize(p));
            } else if ("relinquish_folder_membership_error".equals(tag)) {
                StoneSerializer.expectField("relinquish_folder_membership_error", p);
                value = JobError.relinquishFolderMembershipError(Serializer.INSTANCE.deserialize(p));
            } else {
                value = JobError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public enum Tag {
        UNSHARE_FOLDER_ERROR,
        REMOVE_FOLDER_MEMBER_ERROR,
        RELINQUISH_FOLDER_MEMBERSHIP_ERROR,
        OTHER
    }

    private JobError(Tag _tag, UnshareFolderError unshareFolderErrorValue, RemoveFolderMemberError removeFolderMemberErrorValue, RelinquishFolderMembershipError relinquishFolderMembershipErrorValue) {
        this._tag = _tag;
        this.unshareFolderErrorValue = unshareFolderErrorValue;
        this.removeFolderMemberErrorValue = removeFolderMemberErrorValue;
        this.relinquishFolderMembershipErrorValue = relinquishFolderMembershipErrorValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isUnshareFolderError() {
        return this._tag == Tag.UNSHARE_FOLDER_ERROR;
    }

    public static JobError unshareFolderError(UnshareFolderError value) {
        if (value != null) {
            return new JobError(Tag.UNSHARE_FOLDER_ERROR, value, null, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public UnshareFolderError getUnshareFolderErrorValue() {
        if (this._tag == Tag.UNSHARE_FOLDER_ERROR) {
            return this.unshareFolderErrorValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.UNSHARE_FOLDER_ERROR, but was Tag." + this._tag.name());
    }

    public boolean isRemoveFolderMemberError() {
        return this._tag == Tag.REMOVE_FOLDER_MEMBER_ERROR;
    }

    public static JobError removeFolderMemberError(RemoveFolderMemberError value) {
        if (value != null) {
            return new JobError(Tag.REMOVE_FOLDER_MEMBER_ERROR, null, value, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public RemoveFolderMemberError getRemoveFolderMemberErrorValue() {
        if (this._tag == Tag.REMOVE_FOLDER_MEMBER_ERROR) {
            return this.removeFolderMemberErrorValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.REMOVE_FOLDER_MEMBER_ERROR, but was Tag." + this._tag.name());
    }

    public boolean isRelinquishFolderMembershipError() {
        return this._tag == Tag.RELINQUISH_FOLDER_MEMBERSHIP_ERROR;
    }

    public static JobError relinquishFolderMembershipError(RelinquishFolderMembershipError value) {
        if (value != null) {
            return new JobError(Tag.RELINQUISH_FOLDER_MEMBERSHIP_ERROR, null, null, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public RelinquishFolderMembershipError getRelinquishFolderMembershipErrorValue() {
        if (this._tag == Tag.RELINQUISH_FOLDER_MEMBERSHIP_ERROR) {
            return this.relinquishFolderMembershipErrorValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.RELINQUISH_FOLDER_MEMBERSHIP_ERROR, but was Tag." + this._tag.name());
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.unshareFolderErrorValue, this.removeFolderMemberErrorValue, this.relinquishFolderMembershipErrorValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof JobError)) {
            return false;
        }
        JobError other = (JobError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$JobError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.unshareFolderErrorValue == other.unshareFolderErrorValue || this.unshareFolderErrorValue.equals(other.unshareFolderErrorValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.removeFolderMemberErrorValue == other.removeFolderMemberErrorValue || this.removeFolderMemberErrorValue.equals(other.removeFolderMemberErrorValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                if (this.relinquishFolderMembershipErrorValue == other.relinquishFolderMembershipErrorValue || this.relinquishFolderMembershipErrorValue.equals(other.relinquishFolderMembershipErrorValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
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
