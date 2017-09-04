package com.dropbox.core;

import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;

public final class LocalizedText {
    static final StoneSerializer<LocalizedText> STONE_SERIALIZER = new StoneSerializer<LocalizedText>() {
        public void serialize(LocalizedText value, JsonGenerator g) throws IOException, JsonGenerationException {
            throw new UnsupportedOperationException("Error wrapper serialization not supported.");
        }

        public LocalizedText deserialize(JsonParser p) throws IOException, JsonParseException {
            String text = null;
            String locale = null;
            StoneSerializer.expectStartObject(p);
            while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                String field = p.getCurrentName();
                p.nextToken();
                if ("text".equals(field)) {
                    text = (String) StoneSerializers.string().deserialize(p);
                } else if ("locale".equals(field)) {
                    locale = (String) StoneSerializers.string().deserialize(p);
                } else {
                    StoneSerializer.skipValue(p);
                }
            }
            if (text == null) {
                throw new JsonParseException(p, "Required field \"text\" missing.");
            } else if (locale == null) {
                throw new JsonParseException(p, "Required field \"locale\" missing.");
            } else {
                LocalizedText value = new LocalizedText(text, locale);
                StoneSerializer.expectEndObject(p);
                return value;
            }
        }
    };
    private final String locale;
    private final String text;

    public LocalizedText(String text, String locale) {
        if (text == null) {
            throw new NullPointerException("text");
        } else if (locale == null) {
            throw new NullPointerException("locale");
        } else {
            this.text = text;
            this.locale = locale;
        }
    }

    public String getText() {
        return this.text;
    }

    public String getLocale() {
        return this.locale;
    }

    public String toString() {
        return this.text;
    }
}
