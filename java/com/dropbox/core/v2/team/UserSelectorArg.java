package com.dropbox.core.v2.team;

import com.box.androidsdk.content.models.BoxUploadEmail;
import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.UnionSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;
import net.sqlcipher.database.SQLiteDatabase;

public final class UserSelectorArg {
    private final Tag _tag;
    private final String emailValue;
    private final String externalIdValue;
    private final String teamMemberIdValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$UserSelectorArg$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$team$UserSelectorArg$Tag[Tag.TEAM_MEMBER_ID.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$UserSelectorArg$Tag[Tag.EXTERNAL_ID.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$UserSelectorArg$Tag[Tag.EMAIL.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static class Serializer extends UnionSerializer<UserSelectorArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(UserSelectorArg value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$UserSelectorArg$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("team_member_id", g);
                    g.writeFieldName("team_member_id");
                    StoneSerializers.string().serialize(value.teamMemberIdValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeStartObject();
                    writeTag("external_id", g);
                    g.writeFieldName("external_id");
                    StoneSerializers.string().serialize(value.externalIdValue, g);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeStartObject();
                    writeTag(BoxUploadEmail.FIELD_EMAIL, g);
                    g.writeFieldName(BoxUploadEmail.FIELD_EMAIL);
                    StoneSerializers.string().serialize(value.emailValue, g);
                    g.writeEndObject();
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value.tag());
            }
        }

        public UserSelectorArg deserialize(JsonParser p) throws IOException, JsonParseException {
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
            UserSelectorArg value;
            if ("team_member_id".equals(tag)) {
                StoneSerializer.expectField("team_member_id", p);
                value = UserSelectorArg.teamMemberId((String) StoneSerializers.string().deserialize(p));
            } else if ("external_id".equals(tag)) {
                StoneSerializer.expectField("external_id", p);
                value = UserSelectorArg.externalId((String) StoneSerializers.string().deserialize(p));
            } else if (BoxUploadEmail.FIELD_EMAIL.equals(tag)) {
                StoneSerializer.expectField(BoxUploadEmail.FIELD_EMAIL, p);
                value = UserSelectorArg.email((String) StoneSerializers.string().deserialize(p));
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
        TEAM_MEMBER_ID,
        EXTERNAL_ID,
        EMAIL
    }

    private UserSelectorArg(Tag _tag, String teamMemberIdValue, String externalIdValue, String emailValue) {
        this._tag = _tag;
        this.teamMemberIdValue = teamMemberIdValue;
        this.externalIdValue = externalIdValue;
        this.emailValue = emailValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isTeamMemberId() {
        return this._tag == Tag.TEAM_MEMBER_ID;
    }

    public static UserSelectorArg teamMemberId(String value) {
        if (value != null) {
            return new UserSelectorArg(Tag.TEAM_MEMBER_ID, value, null, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public String getTeamMemberIdValue() {
        if (this._tag == Tag.TEAM_MEMBER_ID) {
            return this.teamMemberIdValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.TEAM_MEMBER_ID, but was Tag." + this._tag.name());
    }

    public boolean isExternalId() {
        return this._tag == Tag.EXTERNAL_ID;
    }

    public static UserSelectorArg externalId(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        } else if (value.length() <= 64) {
            return new UserSelectorArg(Tag.EXTERNAL_ID, null, value, null);
        } else {
            throw new IllegalArgumentException("String is longer than 64");
        }
    }

    public String getExternalIdValue() {
        if (this._tag == Tag.EXTERNAL_ID) {
            return this.externalIdValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.EXTERNAL_ID, but was Tag." + this._tag.name());
    }

    public boolean isEmail() {
        return this._tag == Tag.EMAIL;
    }

    public static UserSelectorArg email(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        } else if (value.length() > 255) {
            throw new IllegalArgumentException("String is longer than 255");
        } else if (Pattern.matches("^['&A-Za-z0-9._%+-]+@[A-Za-z0-9-][A-Za-z0-9.-]*.[A-Za-z]{2,15}$", value)) {
            return new UserSelectorArg(Tag.EMAIL, null, null, value);
        } else {
            throw new IllegalArgumentException("String does not match pattern");
        }
    }

    public String getEmailValue() {
        if (this._tag == Tag.EMAIL) {
            return this.emailValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.EMAIL, but was Tag." + this._tag.name());
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.teamMemberIdValue, this.externalIdValue, this.emailValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UserSelectorArg)) {
            return false;
        }
        UserSelectorArg other = (UserSelectorArg) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$UserSelectorArg$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.teamMemberIdValue == other.teamMemberIdValue || this.teamMemberIdValue.equals(other.teamMemberIdValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.externalIdValue == other.externalIdValue || this.externalIdValue.equals(other.externalIdValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                if (this.emailValue == other.emailValue || this.emailValue.equals(other.emailValue)) {
                    return true;
                }
                return false;
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
