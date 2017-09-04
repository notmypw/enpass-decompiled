package com.dropbox.core.v1;

import com.box.androidsdk.content.models.BoxSharedLink;
import com.dropbox.core.json.JsonDateReader;
import com.dropbox.core.json.JsonReadException;
import com.dropbox.core.json.JsonReader;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Date;

public final class DbxUrlWithExpiration {
    public static final JsonReader<DbxUrlWithExpiration> Reader = new JsonReader<DbxUrlWithExpiration>() {
        public DbxUrlWithExpiration read(JsonParser parser) throws IOException, JsonReadException {
            JsonLocation top = JsonReader.expectObjectStart(parser);
            String url = null;
            Date expires = null;
            while (parser.getCurrentToken() == JsonToken.FIELD_NAME) {
                String fieldName = parser.getCurrentName();
                parser.nextToken();
                try {
                    if (fieldName.equals(BoxSharedLink.FIELD_URL)) {
                        url = (String) JsonReader.StringReader.readField(parser, fieldName, url);
                    } else if (fieldName.equals("expires")) {
                        expires = (Date) JsonDateReader.Dropbox.readField(parser, fieldName, expires);
                    } else {
                        JsonReader.skipValue(parser);
                    }
                } catch (JsonReadException ex) {
                    throw ex.addFieldContext(fieldName);
                }
            }
            JsonReader.expectObjectEnd(parser);
            if (url == null) {
                throw new JsonReadException("missing field \"url\"", top);
            } else if (expires != null) {
                return new DbxUrlWithExpiration(url, expires);
            } else {
                throw new JsonReadException("missing field \"expires\"", top);
            }
        }
    };
    public final Date expires;
    public final String url;

    public DbxUrlWithExpiration(String url, Date expires) {
        this.url = url;
        this.expires = expires;
    }
}
