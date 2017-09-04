package com.dropbox.core.v2.sharing;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

class TransferFolderArg {
    protected final String sharedFolderId;
    protected final String toDropboxId;

    static class Serializer extends StructSerializer<TransferFolderArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(TransferFolderArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("shared_folder_id");
            StoneSerializers.string().serialize(value.sharedFolderId, g);
            g.writeFieldName("to_dropbox_id");
            StoneSerializers.string().serialize(value.toDropboxId, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public TransferFolderArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_sharedFolderId = null;
                String f_toDropboxId = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("shared_folder_id".equals(field)) {
                        f_sharedFolderId = (String) StoneSerializers.string().deserialize(p);
                    } else if ("to_dropbox_id".equals(field)) {
                        f_toDropboxId = (String) StoneSerializers.string().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_sharedFolderId == null) {
                    throw new JsonParseException(p, "Required field \"shared_folder_id\" missing.");
                } else if (f_toDropboxId == null) {
                    throw new JsonParseException(p, "Required field \"to_dropbox_id\" missing.");
                } else {
                    TransferFolderArg value = new TransferFolderArg(f_sharedFolderId, f_toDropboxId);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public TransferFolderArg(String sharedFolderId, String toDropboxId) {
        if (sharedFolderId == null) {
            throw new IllegalArgumentException("Required value for 'sharedFolderId' is null");
        } else if (Pattern.matches("[-_0-9a-zA-Z:]+", sharedFolderId)) {
            this.sharedFolderId = sharedFolderId;
            if (toDropboxId == null) {
                throw new IllegalArgumentException("Required value for 'toDropboxId' is null");
            } else if (toDropboxId.length() < 1) {
                throw new IllegalArgumentException("String 'toDropboxId' is shorter than 1");
            } else {
                this.toDropboxId = toDropboxId;
            }
        } else {
            throw new IllegalArgumentException("String 'sharedFolderId' does not match pattern");
        }
    }

    public String getSharedFolderId() {
        return this.sharedFolderId;
    }

    public String getToDropboxId() {
        return this.toDropboxId;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.sharedFolderId, this.toDropboxId});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        TransferFolderArg other = (TransferFolderArg) obj;
        if ((this.sharedFolderId == other.sharedFolderId || this.sharedFolderId.equals(other.sharedFolderId)) && (this.toDropboxId == other.toDropboxId || this.toDropboxId.equals(other.toDropboxId))) {
            return true;
        }
        return false;
    }

    public String toString() {
        return Serializer.INSTANCE.serialize((Object) this, false);
    }

    public String toStringMultiline() {
        return Serializer.INSTANCE.serialize((Object) this, true);
    }
}
