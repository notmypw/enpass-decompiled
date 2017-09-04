package com.google.api.client.googleapis.json;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public class GoogleJsonErrorContainer extends GenericJson {
    @Key
    private GoogleJsonError error;

    public final GoogleJsonError getError() {
        return this.error;
    }

    public final void setError(GoogleJsonError error) {
        this.error = error;
    }

    public GoogleJsonErrorContainer set(String fieldName, Object value) {
        return (GoogleJsonErrorContainer) super.set(fieldName, value);
    }

    public GoogleJsonErrorContainer clone() {
        return (GoogleJsonErrorContainer) super.clone();
    }
}
