package com.dropbox.core.v1;

import com.dropbox.core.json.JsonReadException;
import com.dropbox.core.json.JsonReader;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;

public class DbxLongpollDeltaResult {
    public static final JsonReader<DbxLongpollDeltaResult> Reader = new JsonReader<DbxLongpollDeltaResult>() {
        public DbxLongpollDeltaResult read(JsonParser parser) throws IOException, JsonReadException {
            JsonLocation top = JsonReader.expectObjectStart(parser);
            Boolean changes = null;
            long backoff = -1;
            while (parser.getCurrentToken() == JsonToken.FIELD_NAME) {
                String fieldName = parser.getCurrentName();
                parser.nextToken();
                try {
                    if (fieldName.equals(BoxRequestEvent.STREAM_TYPE_CHANGES)) {
                        changes = (Boolean) JsonReader.BooleanReader.readField(parser, fieldName, changes);
                    } else if (fieldName.equals("backoff")) {
                        backoff = JsonReader.readUnsignedLongField(parser, fieldName, backoff);
                    } else {
                        JsonReader.skipValue(parser);
                    }
                } catch (JsonReadException ex) {
                    throw ex.addFieldContext(fieldName);
                }
            }
            JsonReader.expectObjectEnd(parser);
            if (changes != null) {
                return new DbxLongpollDeltaResult(changes.booleanValue(), backoff);
            }
            throw new JsonReadException("missing field \"changes\"", top);
        }
    };
    public long backoff;
    public boolean mightHaveChanges;

    public DbxLongpollDeltaResult(boolean mightHaveChanges, long backoff) {
        this.mightHaveChanges = mightHaveChanges;
        this.backoff = backoff;
    }
}
