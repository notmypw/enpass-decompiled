package com.google.api.client.googleapis.testing.auth.oauth2;

import com.box.androidsdk.content.BoxConstants;
import com.box.androidsdk.content.auth.BoxAuthentication.BoxAuthenticationInfo;
import com.box.androidsdk.content.models.BoxMetadata;
import com.google.api.client.googleapis.auth.oauth2.GoogleOAuthConstants;
import com.google.api.client.googleapis.testing.TestUtils;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.Json;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.webtoken.JsonWebSignature;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.google.api.client.util.Beta;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Beta
public class MockTokenServerTransport extends MockHttpTransport {
    static final String EXPECTED_GRANT_TYPE = "urn:ietf:params:oauth:grant-type:jwt-bearer";
    static final JsonFactory JSON_FACTORY = new JacksonFactory();
    Map<String, String> clients;
    Map<String, String> refreshTokens;
    Map<String, String> serviceAccounts;
    final String tokenServerUrl;

    public MockTokenServerTransport() {
        this(GoogleOAuthConstants.TOKEN_SERVER_URL);
    }

    public MockTokenServerTransport(String tokenServerUrl) {
        this.serviceAccounts = new HashMap();
        this.clients = new HashMap();
        this.refreshTokens = new HashMap();
        this.tokenServerUrl = tokenServerUrl;
    }

    public void addServiceAccount(String email, String accessToken) {
        this.serviceAccounts.put(email, accessToken);
    }

    public void addClient(String clientId, String clientSecret) {
        this.clients.put(clientId, clientSecret);
    }

    public void addRefreshToken(String refreshToken, String accessTokenToReturn) {
        this.refreshTokens.put(refreshToken, accessTokenToReturn);
    }

    public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        return url.equals(this.tokenServerUrl) ? new MockLowLevelHttpRequest(url) {
            public LowLevelHttpResponse execute() throws IOException {
                String accessToken;
                Map<String, String> query = TestUtils.parseQuery(getContentAsString());
                String foundId = (String) query.get(BoxAuthenticationInfo.FIELD_CLIENT_ID);
                if (foundId != null) {
                    if (MockTokenServerTransport.this.clients.containsKey(foundId)) {
                        String foundSecret = (String) query.get(BoxConstants.KEY_CLIENT_SECRET);
                        String expectedSecret = (String) MockTokenServerTransport.this.clients.get(foundId);
                        if (foundSecret == null || !foundSecret.equals(expectedSecret)) {
                            throw new IOException("Client secret not found.");
                        }
                        String foundRefresh = (String) query.get(BoxAuthenticationInfo.FIELD_REFRESH_TOKEN);
                        if (MockTokenServerTransport.this.refreshTokens.containsKey(foundRefresh)) {
                            accessToken = (String) MockTokenServerTransport.this.refreshTokens.get(foundRefresh);
                        } else {
                            throw new IOException("Refresh Token not found.");
                        }
                    }
                    throw new IOException("Client ID not found.");
                } else if (query.containsKey("grant_type")) {
                    if (MockTokenServerTransport.EXPECTED_GRANT_TYPE.equals((String) query.get("grant_type"))) {
                        JsonWebSignature signature = JsonWebSignature.parse(MockTokenServerTransport.JSON_FACTORY, (String) query.get("assertion"));
                        String foundEmail = signature.getPayload().getIssuer();
                        if (MockTokenServerTransport.this.serviceAccounts.containsKey(foundEmail)) {
                            accessToken = (String) MockTokenServerTransport.this.serviceAccounts.get(foundEmail);
                            String foundScopes = (String) signature.getPayload().get(BoxMetadata.FIELD_SCOPE);
                            if (foundScopes == null || foundScopes.length() == 0) {
                                throw new IOException("Scopes not found.");
                            }
                        }
                        throw new IOException("Service Account Email not found as issuer.");
                    }
                    throw new IOException("Unexpected Grant Type.");
                } else {
                    throw new IOException("Unknown token type.");
                }
                GenericJson refreshContents = new GenericJson();
                refreshContents.setFactory(MockTokenServerTransport.JSON_FACTORY);
                refreshContents.put(BoxAuthenticationInfo.FIELD_ACCESS_TOKEN, (Object) accessToken);
                refreshContents.put(BoxAuthenticationInfo.FIELD_EXPIRES_IN, (Object) Integer.valueOf(3600000));
                refreshContents.put("token_type", (Object) "Bearer");
                return new MockLowLevelHttpResponse().setContentType(Json.MEDIA_TYPE).setContent(refreshContents.toPrettyString());
            }
        } : super.buildRequest(method, url);
    }
}
