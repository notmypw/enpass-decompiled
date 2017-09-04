package com.dropbox.core.v2.files;

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

public final class MediaInfo {
    public static final MediaInfo PENDING = new MediaInfo(Tag.PENDING, null);
    private final Tag _tag;
    private final MediaMetadata metadataValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$files$MediaInfo$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$files$MediaInfo$Tag[Tag.PENDING.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$files$MediaInfo$Tag[Tag.METADATA.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static class Serializer extends UnionSerializer<MediaInfo> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(MediaInfo value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$MediaInfo$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("pending");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeStartObject();
                    writeTag(BoxApiMetadata.BOX_API_METADATA, g);
                    g.writeFieldName(BoxApiMetadata.BOX_API_METADATA);
                    Serializer.INSTANCE.serialize(value.metadataValue, g);
                    g.writeEndObject();
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value.tag());
            }
        }

        public MediaInfo deserialize(JsonParser p) throws IOException, JsonParseException {
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
            MediaInfo value;
            if ("pending".equals(tag)) {
                value = MediaInfo.PENDING;
            } else if (BoxApiMetadata.BOX_API_METADATA.equals(tag)) {
                StoneSerializer.expectField(BoxApiMetadata.BOX_API_METADATA, p);
                value = MediaInfo.metadata((MediaMetadata) Serializer.INSTANCE.deserialize(p));
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
        PENDING,
        METADATA
    }

    private MediaInfo(Tag _tag, MediaMetadata metadataValue) {
        this._tag = _tag;
        this.metadataValue = metadataValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isPending() {
        return this._tag == Tag.PENDING;
    }

    public boolean isMetadata() {
        return this._tag == Tag.METADATA;
    }

    public static MediaInfo metadata(MediaMetadata value) {
        if (value != null) {
            return new MediaInfo(Tag.METADATA, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public MediaMetadata getMetadataValue() {
        if (this._tag == Tag.METADATA) {
            return this.metadataValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.METADATA, but was Tag." + this._tag.name());
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.metadataValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MediaInfo)) {
            return false;
        }
        MediaInfo other = (MediaInfo) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$files$MediaInfo$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                return true;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.metadataValue == other.metadataValue || this.metadataValue.equals(other.metadataValue)) {
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
