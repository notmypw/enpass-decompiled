package com.dropbox.core;

import com.dropbox.core.http.HttpRequestor.Response;
import com.dropbox.core.stone.StoneSerializer;
import com.fasterxml.jackson.core.JsonParseException;
import java.io.IOException;

public final class DbxWrappedException extends Exception {
    private static final long serialVersionUID = 0;
    private final Object errValue;
    private final String requestId;
    private final LocalizedText userMessage;

    public DbxWrappedException(Object errValue, String requestId, LocalizedText userMessage) {
        this.errValue = errValue;
        this.requestId = requestId;
        this.userMessage = userMessage;
    }

    public Object getErrorValue() {
        return this.errValue;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public LocalizedText getUserMessage() {
        return this.userMessage;
    }

    public static <T> DbxWrappedException fromResponse(StoneSerializer<T> errSerializer, Response response) throws IOException, JsonParseException {
        ApiErrorResponse<T> apiResponse = (ApiErrorResponse) new Serializer(errSerializer).deserialize(response.getBody());
        return new DbxWrappedException(apiResponse.getError(), DbxRequestUtil.getRequestId(response), apiResponse.getUserMessage());
    }
}
