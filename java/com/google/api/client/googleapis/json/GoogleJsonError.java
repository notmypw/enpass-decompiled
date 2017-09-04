package com.google.api.client.googleapis.json;

import com.box.androidsdk.content.requests.BoxRequest.BoxRequestHandler;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser.Builder;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class GoogleJsonError extends GenericJson {
    @Key
    private int code;
    @Key
    private List<ErrorInfo> errors;
    @Key
    private String message;

    public static class ErrorInfo extends GenericJson {
        @Key
        private String domain;
        @Key
        private String location;
        @Key
        private String locationType;
        @Key
        private String message;
        @Key
        private String reason;

        public final String getDomain() {
            return this.domain;
        }

        public final void setDomain(String domain) {
            this.domain = domain;
        }

        public final String getReason() {
            return this.reason;
        }

        public final void setReason(String reason) {
            this.reason = reason;
        }

        public final String getMessage() {
            return this.message;
        }

        public final void setMessage(String message) {
            this.message = message;
        }

        public final String getLocation() {
            return this.location;
        }

        public final void setLocation(String location) {
            this.location = location;
        }

        public final String getLocationType() {
            return this.locationType;
        }

        public final void setLocationType(String locationType) {
            this.locationType = locationType;
        }

        public ErrorInfo set(String fieldName, Object value) {
            return (ErrorInfo) super.set(fieldName, value);
        }

        public ErrorInfo clone() {
            return (ErrorInfo) super.clone();
        }
    }

    public static GoogleJsonError parse(JsonFactory jsonFactory, HttpResponse response) throws IOException {
        return (GoogleJsonError) new Builder(jsonFactory).setWrapperKeys(Collections.singleton(BoxRequestHandler.OAUTH_ERROR_HEADER)).build().parseAndClose(response.getContent(), response.getContentCharset(), GoogleJsonError.class);
    }

    static {
        Data.nullOf(ErrorInfo.class);
    }

    public final List<ErrorInfo> getErrors() {
        return this.errors;
    }

    public final void setErrors(List<ErrorInfo> errors) {
        this.errors = errors;
    }

    public final int getCode() {
        return this.code;
    }

    public final void setCode(int code) {
        this.code = code;
    }

    public final String getMessage() {
        return this.message;
    }

    public final void setMessage(String message) {
        this.message = message;
    }

    public GoogleJsonError set(String fieldName, Object value) {
        return (GoogleJsonError) super.set(fieldName, value);
    }

    public GoogleJsonError clone() {
        return (GoogleJsonError) super.clone();
    }
}
