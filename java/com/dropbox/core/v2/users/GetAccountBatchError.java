package com.dropbox.core.v2.users;

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

public final class GetAccountBatchError {
    public static final GetAccountBatchError OTHER = new GetAccountBatchError(Tag.OTHER, null);
    private final Tag _tag;
    private final String noAccountValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$users$GetAccountBatchError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$users$GetAccountBatchError$Tag[Tag.NO_ACCOUNT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$users$GetAccountBatchError$Tag[Tag.OTHER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static class Serializer extends UnionSerializer<GetAccountBatchError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GetAccountBatchError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$users$GetAccountBatchError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("no_account", g);
                    g.writeFieldName("no_account");
                    StoneSerializers.string().serialize(value.noAccountValue, g);
                    g.writeEndObject();
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public GetAccountBatchError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            GetAccountBatchError value;
            if ("no_account".equals(tag)) {
                StoneSerializer.expectField("no_account", p);
                value = GetAccountBatchError.noAccount((String) StoneSerializers.string().deserialize(p));
            } else {
                value = GetAccountBatchError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public enum Tag {
        NO_ACCOUNT,
        OTHER
    }

    private GetAccountBatchError(Tag _tag, String noAccountValue) {
        this._tag = _tag;
        this.noAccountValue = noAccountValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isNoAccount() {
        return this._tag == Tag.NO_ACCOUNT;
    }

    public static GetAccountBatchError noAccount(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        } else if (value.length() < 40) {
            throw new IllegalArgumentException("String is shorter than 40");
        } else if (value.length() <= 40) {
            return new GetAccountBatchError(Tag.NO_ACCOUNT, value);
        } else {
            throw new IllegalArgumentException("String is longer than 40");
        }
    }

    public String getNoAccountValue() {
        if (this._tag == Tag.NO_ACCOUNT) {
            return this.noAccountValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.NO_ACCOUNT, but was Tag." + this._tag.name());
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.noAccountValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof GetAccountBatchError)) {
            return false;
        }
        GetAccountBatchError other = (GetAccountBatchError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$users$GetAccountBatchError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.noAccountValue == other.noAccountValue || this.noAccountValue.equals(other.noAccountValue)) {
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
