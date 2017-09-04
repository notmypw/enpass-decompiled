package com.google.api.client.json.webtoken;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.google.api.client.util.Objects;
import com.google.api.client.util.Preconditions;
import java.util.Collections;
import java.util.List;

public class JsonWebToken {
    private final Header header;
    private final Payload payload;

    public static class Payload extends GenericJson {
        @Key("aud")
        private Object audience;
        @Key("exp")
        private Long expirationTimeSeconds;
        @Key("iat")
        private Long issuedAtTimeSeconds;
        @Key("iss")
        private String issuer;
        @Key("jti")
        private String jwtId;
        @Key("nbf")
        private Long notBeforeTimeSeconds;
        @Key("sub")
        private String subject;
        @Key("typ")
        private String type;

        public final Long getExpirationTimeSeconds() {
            return this.expirationTimeSeconds;
        }

        public Payload setExpirationTimeSeconds(Long expirationTimeSeconds) {
            this.expirationTimeSeconds = expirationTimeSeconds;
            return this;
        }

        public final Long getNotBeforeTimeSeconds() {
            return this.notBeforeTimeSeconds;
        }

        public Payload setNotBeforeTimeSeconds(Long notBeforeTimeSeconds) {
            this.notBeforeTimeSeconds = notBeforeTimeSeconds;
            return this;
        }

        public final Long getIssuedAtTimeSeconds() {
            return this.issuedAtTimeSeconds;
        }

        public Payload setIssuedAtTimeSeconds(Long issuedAtTimeSeconds) {
            this.issuedAtTimeSeconds = issuedAtTimeSeconds;
            return this;
        }

        public final String getIssuer() {
            return this.issuer;
        }

        public Payload setIssuer(String issuer) {
            this.issuer = issuer;
            return this;
        }

        public final Object getAudience() {
            return this.audience;
        }

        public final List<String> getAudienceAsList() {
            if (this.audience == null) {
                return Collections.emptyList();
            }
            if (this.audience instanceof String) {
                return Collections.singletonList((String) this.audience);
            }
            return (List) this.audience;
        }

        public Payload setAudience(Object audience) {
            this.audience = audience;
            return this;
        }

        public final String getJwtId() {
            return this.jwtId;
        }

        public Payload setJwtId(String jwtId) {
            this.jwtId = jwtId;
            return this;
        }

        public final String getType() {
            return this.type;
        }

        public Payload setType(String type) {
            this.type = type;
            return this;
        }

        public final String getSubject() {
            return this.subject;
        }

        public Payload setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public Payload set(String fieldName, Object value) {
            return (Payload) super.set(fieldName, value);
        }

        public Payload clone() {
            return (Payload) super.clone();
        }
    }

    public static class Header extends GenericJson {
        @Key("cty")
        private String contentType;
        @Key("typ")
        private String type;

        public final String getType() {
            return this.type;
        }

        public Header setType(String type) {
            this.type = type;
            return this;
        }

        public final String getContentType() {
            return this.contentType;
        }

        public Header setContentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Header set(String fieldName, Object value) {
            return (Header) super.set(fieldName, value);
        }

        public Header clone() {
            return (Header) super.clone();
        }
    }

    public JsonWebToken(Header header, Payload payload) {
        this.header = (Header) Preconditions.checkNotNull(header);
        this.payload = (Payload) Preconditions.checkNotNull(payload);
    }

    public String toString() {
        return Objects.toStringHelper(this).add("header", this.header).add("payload", this.payload).toString();
    }

    public Header getHeader() {
        return this.header;
    }

    public Payload getPayload() {
        return this.payload;
    }
}
