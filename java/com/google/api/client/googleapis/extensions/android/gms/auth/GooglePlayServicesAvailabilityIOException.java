package com.google.api.client.googleapis.extensions.android.gms.auth;

import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.api.client.util.Beta;

@Beta
public class GooglePlayServicesAvailabilityIOException extends UserRecoverableAuthIOException {
    private static final long serialVersionUID = 1;

    public GooglePlayServicesAvailabilityIOException(GooglePlayServicesAvailabilityException wrapped) {
        super(wrapped);
    }

    public GooglePlayServicesAvailabilityException getCause() {
        return (GooglePlayServicesAvailabilityException) super.getCause();
    }

    public final int getConnectionStatusCode() {
        return getCause().getConnectionStatusCode();
    }
}
