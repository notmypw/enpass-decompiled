package com.dropbox.core;

import com.box.androidsdk.content.requests.BoxRequest.BoxRequestHandler;
import com.dropbox.core.stone.StoneSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;

final class ApiErrorResponse<T> {
    private final T error;
    private LocalizedText userMessage;

    static final class Serializer<T> extends StoneSerializer<ApiErrorResponse<T>> {
        private StoneSerializer<T> errSerializer;

        public Serializer(StoneSerializer<T> errSerializer) {
            this.errSerializer = errSerializer;
        }

        public void serialize(ApiErrorResponse<T> apiErrorResponse, JsonGenerator g) throws IOException, JsonGenerationException {
            throw new UnsupportedOperationException("Error wrapper serialization not supported.");
        }

        public ApiErrorResponse<T> deserialize(JsonParser p) throws IOException, JsonParseException {
            Object error = null;
            LocalizedText userMessage = null;
            StoneSerializer.expectStartObject(p);
            while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                String field = p.getCurrentName();
                p.nextToken();
                if (BoxRequestHandler.OAUTH_ERROR_HEADER.equals(field)) {
                    error = this.errSerializer.deserialize(p);
                } else if ("user_message".equals(field)) {
                    userMessage = (LocalizedText) LocalizedText.STONE_SERIALIZER.deserialize(p);
                } else {
                    StoneSerializer.skipValue(p);
                }
            }
            if (error == null) {
                throw new JsonParseException(p, "Required field \"error\" missing.");
            }
            ApiErrorResponse<T> value = new ApiErrorResponse(error, userMessage);
            StoneSerializer.expectEndObject(p);
            return value;
        }
    }

    public ApiErrorResponse(T error, LocalizedText userMessage) {
        if (error == null) {
            throw new NullPointerException(BoxRequestHandler.OAUTH_ERROR_HEADER);
        }
        this.error = error;
        this.userMessage = userMessage;
    }

    public T getError() {
        return this.error;
    }

    public LocalizedText getUserMessage() {
        return this.userMessage;
    }
}
