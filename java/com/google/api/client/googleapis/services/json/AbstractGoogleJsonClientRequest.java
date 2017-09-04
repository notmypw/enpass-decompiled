package com.google.api.client.googleapis.services.json;

import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonErrorContainer;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.json.JsonHttpContent;
import java.io.IOException;

public abstract class AbstractGoogleJsonClientRequest<T> extends AbstractGoogleClientRequest<T> {
    private final Object jsonContent;

    protected AbstractGoogleJsonClientRequest(AbstractGoogleJsonClient abstractGoogleJsonClient, String requestMethod, String uriTemplate, Object jsonContent, Class<T> responseClass) {
        HttpContent httpContent;
        String str = null;
        if (jsonContent == null) {
            httpContent = null;
        } else {
            JsonHttpContent jsonHttpContent = new JsonHttpContent(abstractGoogleJsonClient.getJsonFactory(), jsonContent);
            if (!abstractGoogleJsonClient.getObjectParser().getWrapperKeys().isEmpty()) {
                str = "data";
            }
            httpContent = jsonHttpContent.setWrapperKey(str);
        }
        super(abstractGoogleJsonClient, requestMethod, uriTemplate, httpContent, responseClass);
        this.jsonContent = jsonContent;
    }

    public AbstractGoogleJsonClient getAbstractGoogleClient() {
        return (AbstractGoogleJsonClient) super.getAbstractGoogleClient();
    }

    public AbstractGoogleJsonClientRequest<T> setDisableGZipContent(boolean disableGZipContent) {
        return (AbstractGoogleJsonClientRequest) super.setDisableGZipContent(disableGZipContent);
    }

    public AbstractGoogleJsonClientRequest<T> setRequestHeaders(HttpHeaders headers) {
        return (AbstractGoogleJsonClientRequest) super.setRequestHeaders(headers);
    }

    public final void queue(BatchRequest batchRequest, JsonBatchCallback<T> callback) throws IOException {
        super.queue(batchRequest, GoogleJsonErrorContainer.class, callback);
    }

    protected GoogleJsonResponseException newExceptionOnError(HttpResponse response) {
        return GoogleJsonResponseException.from(getAbstractGoogleClient().getJsonFactory(), response);
    }

    public Object getJsonContent() {
        return this.jsonContent;
    }

    public AbstractGoogleJsonClientRequest<T> set(String fieldName, Object value) {
        return (AbstractGoogleJsonClientRequest) super.set(fieldName, value);
    }
}
