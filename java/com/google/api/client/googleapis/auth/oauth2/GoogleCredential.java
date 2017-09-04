package com.google.api.client.googleapis.auth.oauth2;

import com.box.androidsdk.content.BoxConstants;
import com.box.androidsdk.content.auth.BoxAuthentication.BoxAuthenticationInfo;
import com.box.androidsdk.content.models.BoxMetadata;
import com.box.androidsdk.content.models.BoxRealTimeServer;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.CredentialRefreshListener;
import com.google.api.client.auth.oauth2.TokenRequest;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets.Details;
import com.google.api.client.googleapis.util.Utils;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.webtoken.JsonWebSignature;
import com.google.api.client.json.webtoken.JsonWebSignature.Header;
import com.google.api.client.json.webtoken.JsonWebToken.Payload;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Clock;
import com.google.api.client.util.Joiner;
import com.google.api.client.util.PemReader;
import com.google.api.client.util.PemReader.Section;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.SecurityUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Collection;
import java.util.Collections;

public class GoogleCredential extends Credential {
    static final String SERVICE_ACCOUNT_FILE_TYPE = "service_account";
    static final String USER_FILE_TYPE = "authorized_user";
    @Beta
    private static DefaultCredentialProvider defaultCredentialProvider = new DefaultCredentialProvider();
    private String serviceAccountId;
    private PrivateKey serviceAccountPrivateKey;
    private String serviceAccountPrivateKeyId;
    private Collection<String> serviceAccountScopes;
    private String serviceAccountUser;

    public static class Builder extends com.google.api.client.auth.oauth2.Credential.Builder {
        String serviceAccountId;
        PrivateKey serviceAccountPrivateKey;
        String serviceAccountPrivateKeyId;
        Collection<String> serviceAccountScopes;
        String serviceAccountUser;

        public Builder() {
            super(BearerToken.authorizationHeaderAccessMethod());
            setTokenServerEncodedUrl(GoogleOAuthConstants.TOKEN_SERVER_URL);
        }

        public GoogleCredential build() {
            return new GoogleCredential(this);
        }

        public Builder setTransport(HttpTransport transport) {
            return (Builder) super.setTransport(transport);
        }

        public Builder setJsonFactory(JsonFactory jsonFactory) {
            return (Builder) super.setJsonFactory(jsonFactory);
        }

        public Builder setClock(Clock clock) {
            return (Builder) super.setClock(clock);
        }

        public Builder setClientSecrets(String clientId, String clientSecret) {
            setClientAuthentication(new ClientParametersAuthentication(clientId, clientSecret));
            return this;
        }

        public Builder setClientSecrets(GoogleClientSecrets clientSecrets) {
            Details details = clientSecrets.getDetails();
            setClientAuthentication(new ClientParametersAuthentication(details.getClientId(), details.getClientSecret()));
            return this;
        }

        public final String getServiceAccountId() {
            return this.serviceAccountId;
        }

        public Builder setServiceAccountId(String serviceAccountId) {
            this.serviceAccountId = serviceAccountId;
            return this;
        }

        public final Collection<String> getServiceAccountScopes() {
            return this.serviceAccountScopes;
        }

        public Builder setServiceAccountScopes(Collection<String> serviceAccountScopes) {
            this.serviceAccountScopes = serviceAccountScopes;
            return this;
        }

        public final PrivateKey getServiceAccountPrivateKey() {
            return this.serviceAccountPrivateKey;
        }

        public Builder setServiceAccountPrivateKey(PrivateKey serviceAccountPrivateKey) {
            this.serviceAccountPrivateKey = serviceAccountPrivateKey;
            return this;
        }

        @Beta
        public final String getServiceAccountPrivateKeyId() {
            return this.serviceAccountPrivateKeyId;
        }

        @Beta
        public Builder setServiceAccountPrivateKeyId(String serviceAccountPrivateKeyId) {
            this.serviceAccountPrivateKeyId = serviceAccountPrivateKeyId;
            return this;
        }

        public Builder setServiceAccountPrivateKeyFromP12File(File p12File) throws GeneralSecurityException, IOException {
            this.serviceAccountPrivateKey = SecurityUtils.loadPrivateKeyFromKeyStore(SecurityUtils.getPkcs12KeyStore(), new FileInputStream(p12File), "notasecret", "privatekey", "notasecret");
            return this;
        }

        @Beta
        public Builder setServiceAccountPrivateKeyFromPemFile(File pemFile) throws GeneralSecurityException, IOException {
            this.serviceAccountPrivateKey = SecurityUtils.getRsaKeyFactory().generatePrivate(new PKCS8EncodedKeySpec(PemReader.readFirstSectionAndClose(new FileReader(pemFile), "PRIVATE KEY").getBase64DecodedBytes()));
            return this;
        }

        public final String getServiceAccountUser() {
            return this.serviceAccountUser;
        }

        public Builder setServiceAccountUser(String serviceAccountUser) {
            this.serviceAccountUser = serviceAccountUser;
            return this;
        }

        public Builder setRequestInitializer(HttpRequestInitializer requestInitializer) {
            return (Builder) super.setRequestInitializer(requestInitializer);
        }

        public Builder addRefreshListener(CredentialRefreshListener refreshListener) {
            return (Builder) super.addRefreshListener(refreshListener);
        }

        public Builder setRefreshListeners(Collection<CredentialRefreshListener> refreshListeners) {
            return (Builder) super.setRefreshListeners(refreshListeners);
        }

        public Builder setTokenServerUrl(GenericUrl tokenServerUrl) {
            return (Builder) super.setTokenServerUrl(tokenServerUrl);
        }

        public Builder setTokenServerEncodedUrl(String tokenServerEncodedUrl) {
            return (Builder) super.setTokenServerEncodedUrl(tokenServerEncodedUrl);
        }

        public Builder setClientAuthentication(HttpExecuteInterceptor clientAuthentication) {
            return (Builder) super.setClientAuthentication(clientAuthentication);
        }
    }

    @Beta
    public static GoogleCredential getApplicationDefault() throws IOException {
        return getApplicationDefault(Utils.getDefaultTransport(), Utils.getDefaultJsonFactory());
    }

    @Beta
    public static GoogleCredential getApplicationDefault(HttpTransport transport, JsonFactory jsonFactory) throws IOException {
        Preconditions.checkNotNull(transport);
        Preconditions.checkNotNull(jsonFactory);
        return defaultCredentialProvider.getDefaultCredential(transport, jsonFactory);
    }

    @Beta
    public static GoogleCredential fromStream(InputStream credentialStream) throws IOException {
        return fromStream(credentialStream, Utils.getDefaultTransport(), Utils.getDefaultJsonFactory());
    }

    @Beta
    public static GoogleCredential fromStream(InputStream credentialStream, HttpTransport transport, JsonFactory jsonFactory) throws IOException {
        Preconditions.checkNotNull(credentialStream);
        Preconditions.checkNotNull(transport);
        Preconditions.checkNotNull(jsonFactory);
        GenericJson fileContents = (GenericJson) new JsonObjectParser(jsonFactory).parseAndClose(credentialStream, OAuth2Utils.UTF_8, GenericJson.class);
        String fileType = (String) fileContents.get(BoxRealTimeServer.FIELD_TYPE);
        if (fileType == null) {
            throw new IOException("Error reading credentials from stream, 'type' field not specified.");
        } else if (USER_FILE_TYPE.equals(fileType)) {
            return fromStreamUser(fileContents, transport, jsonFactory);
        } else {
            if (SERVICE_ACCOUNT_FILE_TYPE.equals(fileType)) {
                return fromStreamServiceAccount(fileContents, transport, jsonFactory);
            }
            throw new IOException(String.format("Error reading credentials from stream, 'type' value '%s' not recognized. Expecting '%s' or '%s'.", new Object[]{fileType, USER_FILE_TYPE, SERVICE_ACCOUNT_FILE_TYPE}));
        }
    }

    public GoogleCredential() {
        this(new Builder());
    }

    protected GoogleCredential(Builder builder) {
        super((com.google.api.client.auth.oauth2.Credential.Builder) builder);
        if (builder.serviceAccountPrivateKey == null) {
            boolean z = builder.serviceAccountId == null && builder.serviceAccountScopes == null && builder.serviceAccountUser == null;
            Preconditions.checkArgument(z);
            return;
        }
        this.serviceAccountId = (String) Preconditions.checkNotNull(builder.serviceAccountId);
        this.serviceAccountScopes = Collections.unmodifiableCollection(builder.serviceAccountScopes);
        this.serviceAccountPrivateKey = builder.serviceAccountPrivateKey;
        this.serviceAccountPrivateKeyId = builder.serviceAccountPrivateKeyId;
        this.serviceAccountUser = builder.serviceAccountUser;
    }

    public GoogleCredential setAccessToken(String accessToken) {
        return (GoogleCredential) super.setAccessToken(accessToken);
    }

    public GoogleCredential setRefreshToken(String refreshToken) {
        if (refreshToken != null) {
            boolean z = (getJsonFactory() == null || getTransport() == null || getClientAuthentication() == null) ? false : true;
            Preconditions.checkArgument(z, "Please use the Builder and call setJsonFactory, setTransport and setClientSecrets");
        }
        return (GoogleCredential) super.setRefreshToken(refreshToken);
    }

    public GoogleCredential setExpirationTimeMilliseconds(Long expirationTimeMilliseconds) {
        return (GoogleCredential) super.setExpirationTimeMilliseconds(expirationTimeMilliseconds);
    }

    public GoogleCredential setExpiresInSeconds(Long expiresIn) {
        return (GoogleCredential) super.setExpiresInSeconds(expiresIn);
    }

    public GoogleCredential setFromTokenResponse(TokenResponse tokenResponse) {
        return (GoogleCredential) super.setFromTokenResponse(tokenResponse);
    }

    @Beta
    protected TokenResponse executeRefreshToken() throws IOException {
        if (this.serviceAccountPrivateKey == null) {
            return super.executeRefreshToken();
        }
        Header header = new Header();
        header.setAlgorithm("RS256");
        header.setType("JWT");
        header.setKeyId(this.serviceAccountPrivateKeyId);
        Payload payload = new Payload();
        long currentTime = getClock().currentTimeMillis();
        payload.setIssuer(this.serviceAccountId);
        payload.setAudience(getTokenServerEncodedUrl());
        payload.setIssuedAtTimeSeconds(Long.valueOf(currentTime / 1000));
        payload.setExpirationTimeSeconds(Long.valueOf((currentTime / 1000) + 3600));
        payload.setSubject(this.serviceAccountUser);
        payload.put(BoxMetadata.FIELD_SCOPE, (Object) Joiner.on(' ').join(this.serviceAccountScopes));
        try {
            String assertion = JsonWebSignature.signUsingRsaSha256(this.serviceAccountPrivateKey, getJsonFactory(), header, payload);
            TokenRequest request = new TokenRequest(getTransport(), getJsonFactory(), new GenericUrl(getTokenServerEncodedUrl()), "urn:ietf:params:oauth:grant-type:jwt-bearer");
            request.put("assertion", (Object) assertion);
            return request.execute();
        } catch (GeneralSecurityException exception) {
            IOException e = new IOException();
            e.initCause(exception);
            throw e;
        }
    }

    public final String getServiceAccountId() {
        return this.serviceAccountId;
    }

    public final Collection<String> getServiceAccountScopes() {
        return this.serviceAccountScopes;
    }

    public final String getServiceAccountScopesAsString() {
        return this.serviceAccountScopes == null ? null : Joiner.on(' ').join(this.serviceAccountScopes);
    }

    public final PrivateKey getServiceAccountPrivateKey() {
        return this.serviceAccountPrivateKey;
    }

    @Beta
    public final String getServiceAccountPrivateKeyId() {
        return this.serviceAccountPrivateKeyId;
    }

    public final String getServiceAccountUser() {
        return this.serviceAccountUser;
    }

    @Beta
    public boolean createScopedRequired() {
        if (this.serviceAccountPrivateKey == null) {
            return false;
        }
        if (this.serviceAccountScopes == null || this.serviceAccountScopes.isEmpty()) {
            return true;
        }
        return false;
    }

    @Beta
    public GoogleCredential createScoped(Collection<String> scopes) {
        return this.serviceAccountPrivateKey == null ? this : new Builder().setServiceAccountPrivateKey(this.serviceAccountPrivateKey).setServiceAccountPrivateKeyId(this.serviceAccountPrivateKeyId).setServiceAccountId(this.serviceAccountId).setServiceAccountUser(this.serviceAccountUser).setServiceAccountScopes(scopes).setTokenServerEncodedUrl(getTokenServerEncodedUrl()).setTransport(getTransport()).setJsonFactory(getJsonFactory()).setClock(getClock()).build();
    }

    @Beta
    private static GoogleCredential fromStreamUser(GenericJson fileContents, HttpTransport transport, JsonFactory jsonFactory) throws IOException {
        String clientId = (String) fileContents.get(BoxAuthenticationInfo.FIELD_CLIENT_ID);
        String clientSecret = (String) fileContents.get(BoxConstants.KEY_CLIENT_SECRET);
        String refreshToken = (String) fileContents.get(BoxAuthenticationInfo.FIELD_REFRESH_TOKEN);
        if (clientId == null || clientSecret == null || refreshToken == null) {
            throw new IOException("Error reading user credential from stream,  expecting 'client_id', 'client_secret' and 'refresh_token'.");
        }
        GoogleCredential credential = new Builder().setClientSecrets(clientId, clientSecret).setTransport(transport).setJsonFactory(jsonFactory).build();
        credential.setRefreshToken(refreshToken);
        credential.refreshToken();
        return credential;
    }

    @Beta
    private static GoogleCredential fromStreamServiceAccount(GenericJson fileContents, HttpTransport transport, JsonFactory jsonFactory) throws IOException {
        String clientEmail = (String) fileContents.get("client_email");
        String privateKeyPem = (String) fileContents.get("private_key");
        String privateKeyId = (String) fileContents.get("private_key_id");
        if (((String) fileContents.get(BoxAuthenticationInfo.FIELD_CLIENT_ID)) == null || clientEmail == null || privateKeyPem == null || privateKeyId == null) {
            throw new IOException("Error reading service account credential from stream, expecting  'client_id', 'client_email', 'private_key' and 'private_key_id'.");
        }
        Builder credentialBuilder = new Builder().setTransport(transport).setJsonFactory(jsonFactory).setServiceAccountId(clientEmail).setServiceAccountScopes(Collections.emptyList()).setServiceAccountPrivateKey(privateKeyFromPkcs8(privateKeyPem)).setServiceAccountPrivateKeyId(privateKeyId);
        String tokenUri = (String) fileContents.get("token_uri");
        if (tokenUri != null) {
            credentialBuilder.setTokenServerEncodedUrl(tokenUri);
        }
        return credentialBuilder.build();
    }

    @Beta
    private static PrivateKey privateKeyFromPkcs8(String privateKeyPem) throws IOException {
        Exception unexpectedException;
        Section section = PemReader.readFirstSectionAndClose(new StringReader(privateKeyPem), "PRIVATE KEY");
        if (section == null) {
            throw new IOException("Invalid PKCS8 data.");
        }
        try {
            return SecurityUtils.getRsaKeyFactory().generatePrivate(new PKCS8EncodedKeySpec(section.getBase64DecodedBytes()));
        } catch (Exception exception) {
            unexpectedException = exception;
            throw ((IOException) OAuth2Utils.exceptionWithCause(new IOException("Unexpected exception reading PKCS data"), unexpectedException));
        } catch (Exception exception2) {
            unexpectedException = exception2;
            throw ((IOException) OAuth2Utils.exceptionWithCause(new IOException("Unexpected exception reading PKCS data"), unexpectedException));
        }
    }
}
