package com.google.api.client.googleapis.testing.services.json;

import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.util.Beta;

@Beta
public class MockGoogleJsonClientRequest<T> extends AbstractGoogleJsonClientRequest<T> {
    public MockGoogleJsonClientRequest(AbstractGoogleJsonClient client, String method, String uriTemplate, Object content, Class<T> responseClass) {
        super(client, method, uriTemplate, content, responseClass);
    }

    public MockGoogleJsonClient getAbstractGoogleClient() {
        return (MockGoogleJsonClient) super.getAbstractGoogleClient();
    }

    public MockGoogleJsonClientRequest<T> setDisableGZipContent(boolean disableGZipContent) {
        return (MockGoogleJsonClientRequest) super.setDisableGZipContent(disableGZipContent);
    }

    public MockGoogleJsonClientRequest<T> setRequestHeaders(HttpHeaders headers) {
        return (MockGoogleJsonClientRequest) super.setRequestHeaders(headers);
    }
}
