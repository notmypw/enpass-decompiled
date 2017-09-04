package com.google.api.client.http;

public class HttpStatusCodes {
    public static final int STATUS_CODE_BAD_GATEWAY = 502;
    public static final int STATUS_CODE_CONFLICT = 409;
    public static final int STATUS_CODE_FORBIDDEN = 403;
    public static final int STATUS_CODE_FOUND = 302;
    public static final int STATUS_CODE_MOVED_PERMANENTLY = 301;
    public static final int STATUS_CODE_MULTIPLE_CHOICES = 300;
    public static final int STATUS_CODE_NOT_FOUND = 404;
    public static final int STATUS_CODE_NOT_MODIFIED = 304;
    public static final int STATUS_CODE_NO_CONTENT = 204;
    public static final int STATUS_CODE_OK = 200;
    public static final int STATUS_CODE_SEE_OTHER = 303;
    public static final int STATUS_CODE_SERVER_ERROR = 500;
    public static final int STATUS_CODE_SERVICE_UNAVAILABLE = 503;
    public static final int STATUS_CODE_TEMPORARY_REDIRECT = 307;
    public static final int STATUS_CODE_UNAUTHORIZED = 401;

    public static boolean isSuccess(int statusCode) {
        return statusCode >= STATUS_CODE_OK && statusCode < STATUS_CODE_MULTIPLE_CHOICES;
    }

    public static boolean isRedirect(int statusCode) {
        switch (statusCode) {
            case STATUS_CODE_MOVED_PERMANENTLY /*301*/:
            case STATUS_CODE_FOUND /*302*/:
            case STATUS_CODE_SEE_OTHER /*303*/:
            case STATUS_CODE_TEMPORARY_REDIRECT /*307*/:
                return true;
            default:
                return false;
        }
    }
}
