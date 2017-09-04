package com.google.api.client.googleapis.testing.services;

import com.google.api.client.googleapis.services.AbstractGoogleClient;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.util.Beta;

@Beta
public class MockGoogleClientRequest<T> extends AbstractGoogleClientRequest<T> {
    public MockGoogleClientRequest(AbstractGoogleClient client, String method, String uriTemplate, HttpContent content, Class<T> responseClass) {
        super(client, method, uriTemplate, content, responseClass);
    }

    public MockGoogleClientRequest<T> setDisableGZipContent(boolean disableGZipContent) {
        return (MockGoogleClientRequest) super.setDisableGZipContent(disableGZipContent);
    }

    public MockGoogleClientRequest<T> setRequestHeaders(HttpHeaders headers) {
        return (MockGoogleClientRequest) super.setRequestHeaders(headers);
    }

    public MockGoogleClientRequest<T> set(String fieldName, Object value) {
        return (MockGoogleClientRequest) super.set(fieldName, value);
    }
}
