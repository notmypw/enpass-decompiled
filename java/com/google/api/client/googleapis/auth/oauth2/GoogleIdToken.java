package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.openidconnect.IdToken;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.webtoken.JsonWebSignature;
import com.google.api.client.json.webtoken.JsonWebSignature.Header;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Key;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Beta
public class GoogleIdToken extends IdToken {

    @Beta
    public static class Payload extends com.google.api.client.auth.openidconnect.IdToken.Payload {
        @Key("email")
        private String email;
        @Key("email_verified")
        private Object emailVerified;
        @Key("hd")
        private String hostedDomain;

        @Deprecated
        public String getUserId() {
            return getSubject();
        }

        @Deprecated
        public Payload setUserId(String userId) {
            return setSubject(userId);
        }

        @Deprecated
        public String getIssuee() {
            return getAuthorizedParty();
        }

        @Deprecated
        public Payload setIssuee(String issuee) {
            return setAuthorizedParty(issuee);
        }

        public String getHostedDomain() {
            return this.hostedDomain;
        }

        public Payload setHostedDomain(String hostedDomain) {
            this.hostedDomain = hostedDomain;
            return this;
        }

        public String getEmail() {
            return this.email;
        }

        public Payload setEmail(String email) {
            this.email = email;
            return this;
        }

        public Boolean getEmailVerified() {
            if (this.emailVerified == null) {
                return null;
            }
            if (this.emailVerified instanceof Boolean) {
                return (Boolean) this.emailVerified;
            }
            return Boolean.valueOf((String) this.emailVerified);
        }

        public Payload setEmailVerified(Boolean emailVerified) {
            this.emailVerified = emailVerified;
            return this;
        }

        public Payload setAuthorizationTimeSeconds(Long authorizationTimeSeconds) {
            return (Payload) super.setAuthorizationTimeSeconds(authorizationTimeSeconds);
        }

        public Payload setAuthorizedParty(String authorizedParty) {
            return (Payload) super.setAuthorizedParty(authorizedParty);
        }

        public Payload setNonce(String nonce) {
            return (Payload) super.setNonce(nonce);
        }

        public Payload setAccessTokenHash(String accessTokenHash) {
            return (Payload) super.setAccessTokenHash(accessTokenHash);
        }

        public Payload setClassReference(String classReference) {
            return (Payload) super.setClassReference(classReference);
        }

        public Payload setMethodsReferences(List<String> methodsReferences) {
            return (Payload) super.setMethodsReferences(methodsReferences);
        }

        public Payload setExpirationTimeSeconds(Long expirationTimeSeconds) {
            return (Payload) super.setExpirationTimeSeconds(expirationTimeSeconds);
        }

        public Payload setNotBeforeTimeSeconds(Long notBeforeTimeSeconds) {
            return (Payload) super.setNotBeforeTimeSeconds(notBeforeTimeSeconds);
        }

        public Payload setIssuedAtTimeSeconds(Long issuedAtTimeSeconds) {
            return (Payload) super.setIssuedAtTimeSeconds(issuedAtTimeSeconds);
        }

        public Payload setIssuer(String issuer) {
            return (Payload) super.setIssuer(issuer);
        }

        public Payload setAudience(Object audience) {
            return (Payload) super.setAudience(audience);
        }

        public Payload setJwtId(String jwtId) {
            return (Payload) super.setJwtId(jwtId);
        }

        public Payload setType(String type) {
            return (Payload) super.setType(type);
        }

        public Payload setSubject(String subject) {
            return (Payload) super.setSubject(subject);
        }

        public Payload set(String fieldName, Object value) {
            return (Payload) super.set(fieldName, value);
        }

        public Payload clone() {
            return (Payload) super.clone();
        }
    }

    public static GoogleIdToken parse(JsonFactory jsonFactory, String idTokenString) throws IOException {
        JsonWebSignature jws = JsonWebSignature.parser(jsonFactory).setPayloadClass(Payload.class).parse(idTokenString);
        return new GoogleIdToken(jws.getHeader(), (Payload) jws.getPayload(), jws.getSignatureBytes(), jws.getSignedContentBytes());
    }

    public GoogleIdToken(Header header, Payload payload, byte[] signatureBytes, byte[] signedContentBytes) {
        super(header, payload, signatureBytes, signedContentBytes);
    }

    public boolean verify(GoogleIdTokenVerifier verifier) throws GeneralSecurityException, IOException {
        return verifier.verify(this);
    }

    public Payload getPayload() {
        return (Payload) super.getPayload();
    }
}
