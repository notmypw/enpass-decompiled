package com.dropbox.core.v2.sharing;

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

public final class InviteeInfo {
    public static final InviteeInfo OTHER = new InviteeInfo(Tag.OTHER, null);
    private final Tag _tag;
    private final String emailValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$InviteeInfo$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$InviteeInfo$Tag[Tag.EMAIL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$InviteeInfo$Tag[Tag.OTHER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public static class Serializer extends UnionSerializer<InviteeInfo> {
        public static final Serializer INSTANCE = new Serializer();

        public void serialize(InviteeInfo value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$InviteeInfo$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag(BoxUploadEmail.FIELD_EMAIL, g);
                    g.writeFieldName(BoxUploadEmail.FIELD_EMAIL);
                    StoneSerializers.string().serialize(value.emailValue, g);
                    g.writeEndObject();
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public InviteeInfo deserialize(JsonParser p) throws IOException, JsonParseException {
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
            InviteeInfo value;
            if (BoxUploadEmail.FIELD_EMAIL.equals(tag)) {
                StoneSerializer.expectField(BoxUploadEmail.FIELD_EMAIL, p);
                value = InviteeInfo.email((String) StoneSerializers.string().deserialize(p));
            } else {
                value = InviteeInfo.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public enum Tag {
        EMAIL,
        OTHER
    }

    private InviteeInfo(Tag _tag, String emailValue) {
        this._tag = _tag;
        this.emailValue = emailValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isEmail() {
        return this._tag == Tag.EMAIL;
    }

    public static InviteeInfo email(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        } else if (value.length() > 255) {
            throw new IllegalArgumentException("String is longer than 255");
        } else if (Pattern.matches("^['&A-Za-z0-9._%+-]+@[A-Za-z0-9-][A-Za-z0-9.-]*.[A-Za-z]{2,15}$", value)) {
            return new InviteeInfo(Tag.EMAIL, value);
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

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.emailValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof InviteeInfo)) {
            return false;
        }
        InviteeInfo other = (InviteeInfo) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$InviteeInfo$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.emailValue == other.emailValue || this.emailValue.equals(other.emailValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
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
