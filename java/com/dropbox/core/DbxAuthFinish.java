package com.dropbox.core;

import com.box.androidsdk.content.auth.BoxAuthentication.BoxAuthenticationInfo;
import com.dropbox.core.json.JsonReadException;
import com.dropbox.core.json.JsonReader;
import com.dropbox.core.util.StringUtil;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;

public final class DbxAuthFinish {
    public static final JsonReader<String> AccessTokenReader = new JsonReader<String>() {
        public String read(JsonParser parser) throws IOException, JsonReadException {
            try {
                String v = parser.getText();
                String error = DbxAppInfo.getTokenPartError(v);
                if (error != null) {
                    throw new JsonReadException(error, parser.getTokenLocation());
                }
                parser.nextToken();
                return v;
            } catch (JsonParseException ex) {
                throw JsonReadException.fromJackson(ex);
            }
        }
    };
    public static final JsonReader<String> BearerTokenTypeReader = new JsonReader<String>() {
        public String read(JsonParser parser) throws IOException, JsonReadException {
            try {
                String v = parser.getText();
                if (v.equals("Bearer") || v.equals("bearer")) {
                    parser.nextToken();
                    return v;
                }
                throw new JsonReadException("expecting \"Bearer\": got " + StringUtil.jq(v), parser.getTokenLocation());
            } catch (JsonParseException ex) {
                throw JsonReadException.fromJackson(ex);
            }
        }
    };
    public static final JsonReader<DbxAuthFinish> Reader = new JsonReader<DbxAuthFinish>() {
        public DbxAuthFinish read(JsonParser parser) throws IOException, JsonReadException {
            JsonLocation top = JsonReader.expectObjectStart(parser);
            String accessToken = null;
            String tokenType = null;
            String userId = null;
            String state = null;
            while (parser.getCurrentToken() == JsonToken.FIELD_NAME) {
                String fieldName = parser.getCurrentName();
                JsonReader.nextToken(parser);
                try {
                    if (fieldName.equals("token_type")) {
                        tokenType = (String) DbxAuthFinish.BearerTokenTypeReader.readField(parser, fieldName, tokenType);
                    } else if (fieldName.equals(BoxAuthenticationInfo.FIELD_ACCESS_TOKEN)) {
                        accessToken = (String) DbxAuthFinish.AccessTokenReader.readField(parser, fieldName, accessToken);
                    } else if (fieldName.equals("uid")) {
                        userId = (String) JsonReader.StringReader.readField(parser, fieldName, userId);
                    } else if (fieldName.equals("state")) {
                        state = (String) JsonReader.StringReader.readField(parser, fieldName, state);
                    } else {
                        JsonReader.skipValue(parser);
                    }
                } catch (JsonReadException ex) {
                    throw ex.addFieldContext(fieldName);
                }
            }
            JsonReader.expectObjectEnd(parser);
            if (tokenType == null) {
                throw new JsonReadException("missing field \"token_type\"", top);
            } else if (accessToken == null) {
                throw new JsonReadException("missing field \"access_token\"", top);
            } else if (userId != null) {
                return new DbxAuthFinish(accessToken, userId, state);
            } else {
                throw new JsonReadException("missing field \"uid\"", top);
            }
        }
    };
    private final String accessToken;
    private final String urlState;
    private final String userId;

    public DbxAuthFinish(String accessToken, String userId, String urlState) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.urlState = urlState;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getUrlState() {
        return this.urlState;
    }

    DbxAuthFinish withUrlState(String urlState) {
        if (this.urlState == null) {
            return new DbxAuthFinish(this.accessToken, this.userId, urlState);
        }
        throw new IllegalStateException("Already have URL state.");
    }
}
