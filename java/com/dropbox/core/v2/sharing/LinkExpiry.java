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
import java.util.Date;
import net.sqlcipher.database.SQLiteDatabase;

public final class LinkExpiry {
    public static final LinkExpiry OTHER = new LinkExpiry(Tag.OTHER, null);
    public static final LinkExpiry REMOVE_EXPIRY = new LinkExpiry(Tag.REMOVE_EXPIRY, null);
    private final Tag _tag;
    private final Date setExpiryValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$LinkExpiry$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$LinkExpiry$Tag[Tag.REMOVE_EXPIRY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$LinkExpiry$Tag[Tag.SET_EXPIRY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$LinkExpiry$Tag[Tag.OTHER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static class Serializer extends UnionSerializer<LinkExpiry> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(LinkExpiry value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$LinkExpiry$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("remove_expiry");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeStartObject();
                    writeTag("set_expiry", g);
                    g.writeFieldName("set_expiry");
                    StoneSerializers.timestamp().serialize(value.setExpiryValue, g);
                    g.writeEndObject();
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public LinkExpiry deserialize(JsonParser p) throws IOException, JsonParseException {
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
            LinkExpiry value;
            if ("remove_expiry".equals(tag)) {
                value = LinkExpiry.REMOVE_EXPIRY;
            } else if ("set_expiry".equals(tag)) {
                StoneSerializer.expectField("set_expiry", p);
                value = LinkExpiry.setExpiry((Date) StoneSerializers.timestamp().deserialize(p));
            } else {
                value = LinkExpiry.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public enum Tag {
        REMOVE_EXPIRY,
        SET_EXPIRY,
        OTHER
    }

    private LinkExpiry(Tag _tag, Date setExpiryValue) {
        this._tag = _tag;
        this.setExpiryValue = setExpiryValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isRemoveExpiry() {
        return this._tag == Tag.REMOVE_EXPIRY;
    }

    public boolean isSetExpiry() {
        return this._tag == Tag.SET_EXPIRY;
    }

    public static LinkExpiry setExpiry(Date value) {
        if (value != null) {
            return new LinkExpiry(Tag.SET_EXPIRY, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public Date getSetExpiryValue() {
        if (this._tag == Tag.SET_EXPIRY) {
            return this.setExpiryValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.SET_EXPIRY, but was Tag." + this._tag.name());
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.setExpiryValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LinkExpiry)) {
            return false;
        }
        LinkExpiry other = (LinkExpiry) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$LinkExpiry$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                return true;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.setExpiryValue == other.setExpiryValue || this.setExpiryValue.equals(other.setExpiryValue)) {
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
