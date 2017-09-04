package com.dropbox.core;

import com.box.androidsdk.content.auth.BoxAuthentication.BoxAuthenticationInfo;
import com.dropbox.core.DbxRequestUtil.ResponseHandler;
import com.dropbox.core.http.HttpRequestor.Header;
import com.dropbox.core.http.HttpRequestor.Response;
import com.dropbox.core.json.JsonReadException;
import com.dropbox.core.json.JsonReader;
import com.dropbox.core.util.LangUtil;
import com.dropbox.core.v1.DbxClientV1;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.google.api.client.http.HttpStatusCodes;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public final class DbxOAuth1Upgrader {
    public static final JsonReader<String> ResponseReader = new JsonReader<String>() {
        public String read(JsonParser parser) throws IOException, JsonReadException {
            JsonLocation top = JsonReader.expectObjectStart(parser);
            String accessToken = null;
            String tokenType = null;
            while (parser.getCurrentToken() == JsonToken.FIELD_NAME) {
                String fieldName = parser.getCurrentName();
                JsonReader.nextToken(parser);
                try {
                    if (fieldName.equals("token_type")) {
                        tokenType = (String) DbxAuthFinish.BearerTokenTypeReader.readField(parser, fieldName, tokenType);
                    } else if (fieldName.equals(BoxAuthenticationInfo.FIELD_ACCESS_TOKEN)) {
                        accessToken = (String) DbxAuthFinish.AccessTokenReader.readField(parser, fieldName, accessToken);
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
            } else if (accessToken != null) {
                return accessToken;
            } else {
                throw new JsonReadException("missing field \"access_token\"", top);
            }
        }
    };
    private final DbxAppInfo appInfo;
    private final DbxRequestConfig requestConfig;

    public DbxOAuth1Upgrader(DbxRequestConfig requestConfig, DbxAppInfo appInfo) {
        if (requestConfig == null) {
            throw new IllegalArgumentException("'requestConfig' is null");
        } else if (appInfo == null) {
            throw new IllegalArgumentException("'appInfo' is null");
        } else {
            this.requestConfig = requestConfig;
            this.appInfo = appInfo;
        }
    }

    public String createOAuth2AccessToken(DbxOAuth1AccessToken token) throws DbxException {
        if (token != null) {
            return (String) DbxRequestUtil.doPostNoAuth(this.requestConfig, DbxClientV1.USER_AGENT_ID, this.appInfo.getHost().getApi(), "1/oauth2/token_from_oauth1", null, getHeaders(token), new ResponseHandler<String>() {
                public String handle(Response response) throws DbxException {
                    if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_OK) {
                        return (String) DbxRequestUtil.readJsonFromResponse(DbxOAuth1Upgrader.ResponseReader, response);
                    }
                    throw DbxRequestUtil.unexpectedStatus(response);
                }
            });
        }
        throw new IllegalArgumentException("'token' can't be null");
    }

    public void disableOAuth1AccessToken(DbxOAuth1AccessToken token) throws DbxException {
        if (token == null) {
            throw new IllegalArgumentException("'token' can't be null");
        }
        DbxRequestUtil.doPostNoAuth(this.requestConfig, DbxClientV1.USER_AGENT_ID, this.appInfo.getHost().getApi(), "1/disable_access_token", null, getHeaders(token), new ResponseHandler<Void>() {
            public Void handle(Response response) throws DbxException {
                if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_OK) {
                    return null;
                }
                throw DbxRequestUtil.unexpectedStatus(response);
            }
        });
    }

    private ArrayList<Header> getHeaders(DbxOAuth1AccessToken token) {
        ArrayList<Header> headers = new ArrayList(1);
        headers.add(new Header("Authorization", buildOAuth1Header(token)));
        return headers;
    }

    private String buildOAuth1Header(DbxOAuth1AccessToken token) {
        StringBuilder buf = new StringBuilder();
        buf.append("OAuth oauth_version=\"1.0\", oauth_signature_method=\"PLAINTEXT\"");
        buf.append(", oauth_consumer_key=\"").append(encode(this.appInfo.getKey())).append("\"");
        buf.append(", oauth_token=\"").append(encode(token.getKey())).append("\"");
        buf.append(", oauth_signature=\"").append(encode(this.appInfo.getSecret())).append("&").append(encode(token.getSecret())).append("\"");
        return buf.toString();
    }

    private static String encode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw LangUtil.mkAssert("UTF-8 should always be supported", ex);
        }
    }
}
