package com.dropbox.core.v2.sharing;

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
import net.sqlcipher.database.SQLiteDatabase;

public final class LinkPassword {
    public static final LinkPassword OTHER = new LinkPassword(Tag.OTHER, null);
    public static final LinkPassword REMOVE_PASSWORD = new LinkPassword(Tag.REMOVE_PASSWORD, null);
    private final Tag _tag;
    private final String setPasswordValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$LinkPassword$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$LinkPassword$Tag[Tag.REMOVE_PASSWORD.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$LinkPassword$Tag[Tag.SET_PASSWORD.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$LinkPassword$Tag[Tag.OTHER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static class Serializer extends UnionSerializer<LinkPassword> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(LinkPassword value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$LinkPassword$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("remove_password");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeStartObject();
                    writeTag("set_password", g);
                    g.writeFieldName("set_password");
                    StoneSerializers.string().serialize(value.setPasswordValue, g);
                    g.writeEndObject();
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public LinkPassword deserialize(JsonParser p) throws IOException, JsonParseException {
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
            LinkPassword value;
            if ("remove_password".equals(tag)) {
                value = LinkPassword.REMOVE_PASSWORD;
            } else if ("set_password".equals(tag)) {
                StoneSerializer.expectField("set_password", p);
                value = LinkPassword.setPassword((String) StoneSerializers.string().deserialize(p));
            } else {
                value = LinkPassword.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public enum Tag {
        REMOVE_PASSWORD,
        SET_PASSWORD,
        OTHER
    }

    private LinkPassword(Tag _tag, String setPasswordValue) {
        this._tag = _tag;
        this.setPasswordValue = setPasswordValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isRemovePassword() {
        return this._tag == Tag.REMOVE_PASSWORD;
    }

    public boolean isSetPassword() {
        return this._tag == Tag.SET_PASSWORD;
    }

    public static LinkPassword setPassword(String value) {
        if (value != null) {
            return new LinkPassword(Tag.SET_PASSWORD, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public String getSetPasswordValue() {
        if (this._tag == Tag.SET_PASSWORD) {
            return this.setPasswordValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.SET_PASSWORD, but was Tag." + this._tag.name());
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.setPasswordValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LinkPassword)) {
            return false;
        }
        LinkPassword other = (LinkPassword) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$LinkPassword$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                return true;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.setPasswordValue == other.setPasswordValue || this.setPasswordValue.equals(other.setPasswordValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
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
