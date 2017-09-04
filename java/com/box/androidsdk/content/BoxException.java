package com.box.androidsdk.content;

import com.box.androidsdk.content.models.BoxError;
import com.box.androidsdk.content.requests.BoxHttpResponse;
import com.github.clans.fab.BuildConfig;
import com.google.api.client.http.HttpStatusCodes;
import java.net.UnknownHostException;

public class BoxException extends Exception {
    private static final long serialVersionUID = 1;
    private BoxHttpResponse boxHttpResponse;
    private String response;
    private final int responseCode;

    public enum ErrorType {
        INVALID_GRANT_TOKEN_EXPIRED("invalid_grant", 400),
        INVALID_GRANT_INVALID_TOKEN("invalid_grant", 400),
        ACCESS_DENIED("access_denied", HttpStatusCodes.STATUS_CODE_FORBIDDEN),
        INVALID_REQUEST("invalid_request", 400),
        INVALID_CLIENT("invalid_client", 400),
        PASSWORD_RESET_REQUIRED("password_reset_required", 400),
        TERMS_OF_SERVICE_REQUIRED("terms_of_service_required", 400),
        NO_CREDIT_CARD_TRIAL_ENDED("no_credit_card_trial_ended", 400),
        TEMPORARILY_UNAVAILABLE("temporarily_unavailable", BoxConstants.HTTP_STATUS_TOO_MANY_REQUESTS),
        SERVICE_BLOCKED("service_blocked", 400),
        UNAUTHORIZED_DEVICE("unauthorized_device", 400),
        GRACE_PERIOD_EXPIRED("grace_period_expired", HttpStatusCodes.STATUS_CODE_FORBIDDEN),
        NETWORK_ERROR("bad_connection_network_error", 0),
        LOCATION_BLOCKED("access_from_location_blocked", HttpStatusCodes.STATUS_CODE_FORBIDDEN),
        OTHER(BuildConfig.FLAVOR, 0);
        
        private final int mStatusCode;
        private final String mValue;

        private ErrorType(String value, int statusCode) {
            this.mValue = value;
            this.mStatusCode = statusCode;
        }

        public static ErrorType fromErrorInfo(String errorCode, int statusCode) {
            for (ErrorType type : values()) {
                if (type.mStatusCode == statusCode && type.mValue.equals(errorCode)) {
                    return type;
                }
            }
            return OTHER;
        }
    }

    public static class MaxAttemptsExceeded extends BoxException {
        private final int mTimesTried;

        public MaxAttemptsExceeded(String message, int timesTried) {
            this(message, timesTried, null);
        }

        public MaxAttemptsExceeded(String message, int timesTried, BoxHttpResponse response) {
            super(message + timesTried, response);
            this.mTimesTried = timesTried;
        }

        public int getTimesTried() {
            return this.mTimesTried;
        }
    }

    public static class RateLimitAttemptsExceeded extends MaxAttemptsExceeded {
        public RateLimitAttemptsExceeded(String message, int timesTried, BoxHttpResponse response) {
            super(message, timesTried, response);
        }
    }

    public static class RefreshFailure extends BoxException {
        public RefreshFailure(BoxException exception) {
            super(exception.getMessage(), exception.responseCode, exception.getResponse(), exception);
        }

        public boolean isErrorFatal() {
            ErrorType type = getErrorType();
            for (ErrorType fatalType : new ErrorType[]{ErrorType.INVALID_GRANT_INVALID_TOKEN, ErrorType.INVALID_GRANT_TOKEN_EXPIRED, ErrorType.ACCESS_DENIED, ErrorType.NO_CREDIT_CARD_TRIAL_ENDED, ErrorType.SERVICE_BLOCKED, ErrorType.INVALID_CLIENT, ErrorType.UNAUTHORIZED_DEVICE, ErrorType.GRACE_PERIOD_EXPIRED, ErrorType.OTHER}) {
                if (type == fatalType) {
                    return true;
                }
            }
            return false;
        }
    }

    public BoxException(String message) {
        super(message);
        this.responseCode = 0;
        this.boxHttpResponse = null;
        this.response = null;
    }

    public BoxException(String message, BoxHttpResponse response) {
        super(message, (Throwable) null);
        this.boxHttpResponse = response;
        if (response != null) {
            this.responseCode = response.getResponseCode();
        } else {
            this.responseCode = 0;
        }
        try {
            this.response = response.getStringBody();
        } catch (Exception e) {
            this.response = null;
        }
    }

    public BoxException(String message, Throwable cause) {
        super(message, cause);
        this.responseCode = 0;
        this.response = null;
    }

    public BoxException(String message, int responseCode, String response, Throwable cause) {
        super(message, cause);
        this.responseCode = responseCode;
        this.response = response;
    }

    public int getResponseCode() {
        return this.responseCode;
    }

    public String getResponse() {
        return this.response;
    }

    public BoxError getAsBoxError() {
        try {
            BoxError error = new BoxError();
            error.createFromJson(getResponse());
            return error;
        } catch (Exception e) {
            return null;
        }
    }

    public ErrorType getErrorType() {
        if (getCause() instanceof UnknownHostException) {
            return ErrorType.NETWORK_ERROR;
        }
        BoxError error = getAsBoxError();
        return ErrorType.fromErrorInfo(error != null ? error.getError() : null, getResponseCode());
    }
}
