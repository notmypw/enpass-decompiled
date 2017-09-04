package com.dropbox.core.v2.sharing;

import com.box.androidsdk.content.BoxApiMetadata;
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

public final class GetFileMetadataIndividualResult {
    public static final GetFileMetadataIndividualResult OTHER = new GetFileMetadataIndividualResult(Tag.OTHER, null, null);
    private final Tag _tag;
    private final SharingFileAccessError accessErrorValue;
    private final SharedFileMetadata metadataValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$GetFileMetadataIndividualResult$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$GetFileMetadataIndividualResult$Tag[Tag.METADATA.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$GetFileMetadataIndividualResult$Tag[Tag.ACCESS_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$GetFileMetadataIndividualResult$Tag[Tag.OTHER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static class Serializer extends UnionSerializer<GetFileMetadataIndividualResult> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GetFileMetadataIndividualResult value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$GetFileMetadataIndividualResult$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag(BoxApiMetadata.BOX_API_METADATA, g);
                    Serializer.INSTANCE.serialize(value.metadataValue, g, true);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeStartObject();
                    writeTag("access_error", g);
                    g.writeFieldName("access_error");
                    Serializer.INSTANCE.serialize(value.accessErrorValue, g);
                    g.writeEndObject();
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public GetFileMetadataIndividualResult deserialize(JsonParser p) throws IOException, JsonParseException {
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
            GetFileMetadataIndividualResult value;
            if (BoxApiMetadata.BOX_API_METADATA.equals(tag)) {
                value = GetFileMetadataIndividualResult.metadata(Serializer.INSTANCE.deserialize(p, true));
            } else if ("access_error".equals(tag)) {
                StoneSerializer.expectField("access_error", p);
                value = GetFileMetadataIndividualResult.accessError(Serializer.INSTANCE.deserialize(p));
            } else {
                value = GetFileMetadataIndividualResult.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public enum Tag {
        METADATA,
        ACCESS_ERROR,
        OTHER
    }

    private GetFileMetadataIndividualResult(Tag _tag, SharedFileMetadata metadataValue, SharingFileAccessError accessErrorValue) {
        this._tag = _tag;
        this.metadataValue = metadataValue;
        this.accessErrorValue = accessErrorValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isMetadata() {
        return this._tag == Tag.METADATA;
    }

    public static GetFileMetadataIndividualResult metadata(SharedFileMetadata value) {
        if (value != null) {
            return new GetFileMetadataIndividualResult(Tag.METADATA, value, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public SharedFileMetadata getMetadataValue() {
        if (this._tag == Tag.METADATA) {
            return this.metadataValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.METADATA, but was Tag." + this._tag.name());
    }

    public boolean isAccessError() {
        return this._tag == Tag.ACCESS_ERROR;
    }

    public static GetFileMetadataIndividualResult accessError(SharingFileAccessError value) {
        if (value != null) {
            return new GetFileMetadataIndividualResult(Tag.ACCESS_ERROR, null, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public SharingFileAccessError getAccessErrorValue() {
        if (this._tag == Tag.ACCESS_ERROR) {
            return this.accessErrorValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.ACCESS_ERROR, but was Tag." + this._tag.name());
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.metadataValue, this.accessErrorValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof GetFileMetadataIndividualResult)) {
            return false;
        }
        GetFileMetadataIndividualResult other = (GetFileMetadataIndividualResult) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$GetFileMetadataIndividualResult$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.metadataValue == other.metadataValue || this.metadataValue.equals(other.metadataValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.accessErrorValue == other.accessErrorValue || this.accessErrorValue.equals(other.accessErrorValue)) {
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
