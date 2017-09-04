package com.google.api.client.googleapis.testing.compute;

import com.box.androidsdk.content.auth.BoxAuthentication.BoxAuthenticationInfo;
import com.google.api.client.googleapis.auth.oauth2.OAuth2Utils;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.Json;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.google.api.client.util.Beta;
import java.io.IOException;

@Beta
public class MockMetadataServerTransport extends MockHttpTransport {
    static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final String METADATA_SERVER_URL = OAuth2Utils.getMetadataServerUrl();
    private static final String METADATA_TOKEN_SERVER_URL = (METADATA_SERVER_URL + "/computeMetadata/v1/instance/service-accounts/default/token");
    String accessToken;
    Integer tokenRequestStatusCode;

    public MockMetadataServerTransport(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setTokenRequestStatusCode(Integer tokenRequestStatusCode) {
        this.tokenRequestStatusCode = tokenRequestStatusCode;
    }

    public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        if (url.equals(METADATA_TOKEN_SERVER_URL)) {
            return new MockLowLevelHttpRequest(url) {
                public LowLevelHttpResponse execute() throws IOException {
                    if (MockMetadataServerTransport.this.tokenRequestStatusCode != null) {
                        return new MockLowLevelHttpResponse().setStatusCode(MockMetadataServerTransport.this.tokenRequestStatusCode.intValue()).setContent("Token Fetch Error");
                    }
                    if ("Google".equals(getFirstHeaderValue("Metadata-Flavor"))) {
                        GenericJson refreshContents = new GenericJson();
                        refreshContents.setFactory(MockMetadataServerTransport.JSON_FACTORY);
                        refreshContents.put(BoxAuthenticationInfo.FIELD_ACCESS_TOKEN, (Object) MockMetadataServerTransport.this.accessToken);
                        refreshContents.put(BoxAuthenticationInfo.FIELD_EXPIRES_IN, (Object) Integer.valueOf(3600000));
                        refreshContents.put("token_type", (Object) "Bearer");
                        return new MockLowLevelHttpResponse().setContentType(Json.MEDIA_TYPE).setContent(refreshContents.toPrettyString());
                    }
                    throw new IOException("Metadata request header not found.");
                }
            };
        }
        if (url.equals(METADATA_SERVER_URL)) {
            return new MockLowLevelHttpRequest(url) {
                public LowLevelHttpResponse execute() {
                    MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
                    response.addHeader("Metadata-Flavor", "Google");
                    return response;
                }
            };
        }
        return super.buildRequest(method, url);
    }
}
