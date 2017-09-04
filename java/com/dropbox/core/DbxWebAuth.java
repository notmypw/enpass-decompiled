package com.dropbox.core;

import com.box.androidsdk.content.BoxConstants;
import com.box.androidsdk.content.auth.BoxAuthentication.BoxAuthenticationInfo;
import com.box.androidsdk.content.models.BoxError;
import com.box.androidsdk.content.requests.BoxRequest.BoxRequestHandler;
import com.dropbox.core.DbxRequestUtil.ResponseHandler;
import com.dropbox.core.http.HttpRequestor.Header;
import com.dropbox.core.http.HttpRequestor.Response;
import com.dropbox.core.util.StringUtil;
import com.dropbox.core.v2.DbxRawClientV2;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.client.util.ExponentialBackOff;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbxWebAuth {
    private static final int CSRF_BYTES_SIZE = 16;
    private static final int CSRF_STRING_SIZE = StringUtil.urlSafeBase64Encode(new byte[CSRF_BYTES_SIZE]).length();
    private static final SecureRandom RAND = new SecureRandom();
    public static final String ROLE_PERSONAL = "personal";
    public static final String ROLE_WORK = "work";
    private final DbxAppInfo appInfo;
    private final Request deprecatedRequest;
    private final DbxRequestConfig requestConfig;

    public static abstract class Exception extends Exception {
        private static final long serialVersionUID = 0;

        public Exception(String message) {
            super(message);
        }
    }

    public static final class BadRequestException extends Exception {
        private static final long serialVersionUID = 0;

        public BadRequestException(String message) {
            super(message);
        }
    }

    public static final class BadStateException extends Exception {
        private static final long serialVersionUID = 0;

        public BadStateException(String message) {
            super(message);
        }
    }

    public static final class CsrfException extends Exception {
        private static final long serialVersionUID = 0;

        public CsrfException(String message) {
            super(message);
        }
    }

    public static final class NotApprovedException extends Exception {
        private static final long serialVersionUID = 0;

        public NotApprovedException(String message) {
            super(message);
        }
    }

    public static final class ProviderException extends Exception {
        private static final long serialVersionUID = 0;

        public ProviderException(String message) {
            super(message);
        }
    }

    public static final class Request {
        private static final int MAX_STATE_SIZE = 500;
        private static final Charset UTF8 = Charset.forName("UTF-8");
        private final Boolean disableSignup;
        private final Boolean forceReapprove;
        private final String redirectUri;
        private final String requireRole;
        private final DbxSessionStore sessionStore;
        private final String state;

        public static final class Builder {
            private Boolean disableSignup;
            private Boolean forceReapprove;
            private String redirectUri;
            private String requireRole;
            private DbxSessionStore sessionStore;
            private String state;

            private Builder() {
                this(null, null, null, null, null, null);
            }

            private Builder(String redirectUri, String state, String requireRole, Boolean forceReapprove, Boolean disableSignup, DbxSessionStore sessionStore) {
                this.redirectUri = redirectUri;
                this.state = state;
                this.requireRole = requireRole;
                this.forceReapprove = forceReapprove;
                this.disableSignup = disableSignup;
                this.sessionStore = sessionStore;
            }

            public Builder withNoRedirect() {
                this.redirectUri = null;
                this.sessionStore = null;
                return this;
            }

            public Builder withRedirectUri(String redirectUri, DbxSessionStore sessionStore) {
                if (redirectUri == null) {
                    throw new NullPointerException("redirectUri");
                } else if (sessionStore == null) {
                    throw new NullPointerException("sessionStore");
                } else {
                    this.redirectUri = redirectUri;
                    this.sessionStore = sessionStore;
                    return this;
                }
            }

            public Builder withState(String state) {
                if (state == null || state.getBytes(Request.UTF8).length + DbxWebAuth.CSRF_STRING_SIZE <= Request.MAX_STATE_SIZE) {
                    this.state = state;
                    return this;
                }
                throw new IllegalArgumentException("UTF-8 encoded state cannot be greater than " + (500 - DbxWebAuth.CSRF_STRING_SIZE) + " bytes.");
            }

            public Builder withRequireRole(String requireRole) {
                this.requireRole = requireRole;
                return this;
            }

            public Builder withForceReapprove(Boolean forceReapprove) {
                this.forceReapprove = forceReapprove;
                return this;
            }

            public Builder withDisableSignup(Boolean disableSignup) {
                this.disableSignup = disableSignup;
                return this;
            }

            public Request build() {
                if (this.redirectUri != null || this.state == null) {
                    return new Request(this.redirectUri, this.state, this.requireRole, this.forceReapprove, this.disableSignup, this.sessionStore);
                }
                throw new IllegalStateException("Cannot specify a state without a redirect URI.");
            }
        }

        private Request(String redirectUri, String state, String requireRole, Boolean forceReapprove, Boolean disableSignup, DbxSessionStore sessionStore) {
            this.redirectUri = redirectUri;
            this.state = state;
            this.requireRole = requireRole;
            this.forceReapprove = forceReapprove;
            this.disableSignup = disableSignup;
            this.sessionStore = sessionStore;
        }

        public Builder copy() {
            return new Builder(this.redirectUri, this.state, this.requireRole, this.forceReapprove, this.disableSignup, this.sessionStore);
        }

        public static Builder newBuilder() {
            return new Builder();
        }
    }

    @Deprecated
    public DbxWebAuth(DbxRequestConfig requestConfig, DbxAppInfo appInfo, String redirectUri, DbxSessionStore sessionStore) {
        if (requestConfig == null) {
            throw new NullPointerException("requestConfig");
        } else if (appInfo == null) {
            throw new NullPointerException("appInfo");
        } else {
            this.requestConfig = requestConfig;
            this.appInfo = appInfo;
            this.deprecatedRequest = newRequestBuilder().withRedirectUri(redirectUri, sessionStore).build();
        }
    }

    public DbxWebAuth(DbxRequestConfig requestConfig, DbxAppInfo appInfo) {
        if (requestConfig == null) {
            throw new NullPointerException("requestConfig");
        } else if (appInfo == null) {
            throw new NullPointerException("appInfo");
        } else {
            this.requestConfig = requestConfig;
            this.appInfo = appInfo;
            this.deprecatedRequest = null;
        }
    }

    @Deprecated
    public String start(String urlState) {
        if (this.deprecatedRequest != null) {
            return authorizeImpl(this.deprecatedRequest.copy().withState(urlState).build());
        }
        throw new IllegalStateException("Must use DbxWebAuth.authorize instead.");
    }

    public String authorize(Request request) {
        if (this.deprecatedRequest == null) {
            return authorizeImpl(request);
        }
        throw new IllegalStateException("Must create this instance using DbxWebAuth(DbxRequestConfig,DbxAppInfo) to call this method.");
    }

    private String authorizeImpl(Request request) {
        Map<String, String> params = new HashMap();
        params.put(BoxAuthenticationInfo.FIELD_CLIENT_ID, this.appInfo.getKey());
        params.put("response_type", BoxError.FIELD_CODE);
        if (request.redirectUri != null) {
            params.put(BoxConstants.KEY_REDIRECT_URL, request.redirectUri);
            params.put("state", appendCsrfToken(request));
        }
        if (request.requireRole != null) {
            params.put("require_role", request.requireRole);
        }
        if (request.forceReapprove != null) {
            params.put("force_reapprove", Boolean.toString(request.forceReapprove.booleanValue()).toLowerCase());
        }
        if (request.disableSignup != null) {
            params.put("disable_signup", Boolean.toString(request.disableSignup.booleanValue()).toLowerCase());
        }
        return DbxRequestUtil.buildUrlWithParams(this.requestConfig.getUserLocale(), this.appInfo.getHost().getWeb(), "oauth2/authorize", DbxRequestUtil.toParamsArray(params));
    }

    public DbxAuthFinish finishFromCode(String code) throws DbxException {
        return finish(code);
    }

    public DbxAuthFinish finishFromCode(String code, String redirectUri) throws DbxException {
        return finish(code, redirectUri, null);
    }

    public DbxAuthFinish finishFromRedirect(String redirectUri, DbxSessionStore sessionStore, Map<String, String[]> params) throws DbxException, BadRequestException, BadStateException, CsrfException, NotApprovedException, ProviderException {
        if (redirectUri == null) {
            throw new NullPointerException("redirectUri");
        } else if (sessionStore == null) {
            throw new NullPointerException("sessionStore");
        } else if (params == null) {
            throw new NullPointerException("params");
        } else {
            String state = getParam(params, "state");
            if (state == null) {
                throw new BadRequestException("Missing required parameter: \"state\".");
            }
            String error = getParam(params, BoxRequestHandler.OAUTH_ERROR_HEADER);
            String code = getParam(params, BoxError.FIELD_CODE);
            String errorDescription = getParam(params, BoxError.FIELD_ERROR_DESCRIPTION);
            if (code == null && error == null) {
                throw new BadRequestException("Missing both \"code\" and \"error\".");
            } else if (code != null && error != null) {
                throw new BadRequestException("Both \"code\" and \"error\" are set.");
            } else if (code == null || errorDescription == null) {
                state = verifyAndStripCsrfToken(state, sessionStore);
                if (error == null) {
                    return finish(code, redirectUri, state);
                }
                if (error.equals("access_denied")) {
                    throw new NotApprovedException(errorDescription == null ? "No additional description from Dropbox" : "Additional description from Dropbox: " + errorDescription);
                }
                String exceptionMessage;
                if (errorDescription == null) {
                    exceptionMessage = error;
                } else {
                    exceptionMessage = String.format("%s: %s", new Object[]{error, errorDescription});
                }
                throw new ProviderException(exceptionMessage);
            } else {
                throw new BadRequestException("Both \"code\" and \"error_description\" are set.");
            }
        }
    }

    private DbxAuthFinish finish(String code) throws DbxException {
        return finish(code, null, null);
    }

    private DbxAuthFinish finish(String code, String redirectUri, final String state) throws DbxException {
        if (code == null) {
            throw new NullPointerException(BoxError.FIELD_CODE);
        }
        Map<String, String> params = new HashMap();
        params.put("grant_type", "authorization_code");
        params.put(BoxError.FIELD_CODE, code);
        params.put("locale", this.requestConfig.getUserLocale());
        if (redirectUri != null) {
            params.put(BoxConstants.KEY_REDIRECT_URL, redirectUri);
        }
        List<Header> headers = new ArrayList();
        DbxRequestUtil.addBasicAuthHeader(headers, this.appInfo.getKey(), this.appInfo.getSecret());
        return (DbxAuthFinish) DbxRequestUtil.doPostNoAuth(this.requestConfig, DbxRawClientV2.USER_AGENT_ID, this.appInfo.getHost().getApi(), "oauth2/token", DbxRequestUtil.toParamsArray(params), headers, new ResponseHandler<DbxAuthFinish>() {
            public DbxAuthFinish handle(Response response) throws DbxException {
                if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_OK) {
                    return ((DbxAuthFinish) DbxRequestUtil.readJsonFromResponse(DbxAuthFinish.Reader, response)).withUrlState(state);
                }
                throw DbxRequestUtil.unexpectedStatus(response);
            }
        });
    }

    @Deprecated
    public DbxAuthFinish finish(Map<String, String[]> queryParams) throws DbxException, BadRequestException, BadStateException, CsrfException, NotApprovedException, ProviderException {
        if (this.deprecatedRequest != null) {
            return finishFromRedirect(this.deprecatedRequest.redirectUri, this.deprecatedRequest.sessionStore, queryParams);
        }
        throw new IllegalStateException("Must use DbxWebAuth.finishFromRedirect(..) instead.");
    }

    private static String appendCsrfToken(Request request) {
        byte[] csrf = new byte[CSRF_BYTES_SIZE];
        RAND.nextBytes(csrf);
        String prefix = StringUtil.urlSafeBase64Encode(csrf);
        if (prefix.length() != CSRF_STRING_SIZE) {
            throw new AssertionError("unexpected CSRF token length: " + prefix.length());
        }
        if (request.sessionStore != null) {
            request.sessionStore.set(prefix);
        }
        if (request.state == null) {
            return prefix;
        }
        String combined = prefix + request.state;
        if (combined.length() <= ExponentialBackOff.DEFAULT_INITIAL_INTERVAL_MILLIS) {
            return combined;
        }
        throw new AssertionError("unexpected combined state length: " + combined.length());
    }

    private static String verifyAndStripCsrfToken(String state, DbxSessionStore sessionStore) throws CsrfException, BadStateException {
        String expected = sessionStore.get();
        if (expected == null) {
            throw new BadStateException("No CSRF Token loaded from session store.");
        } else if (expected.length() < CSRF_STRING_SIZE) {
            throw new BadStateException("Token retrieved from session store is too small: " + expected);
        } else if (state.length() < CSRF_STRING_SIZE) {
            throw new CsrfException("Token too small: " + state);
        } else {
            String actual = state.substring(CSRF_STRING_SIZE, CSRF_STRING_SIZE);
            if (StringUtil.secureStringEquals(expected, actual)) {
                String stripped = state.substring(CSRF_STRING_SIZE, state.length());
                sessionStore.clear();
                return stripped.isEmpty() ? null : stripped;
            } else {
                throw new CsrfException("expecting " + StringUtil.jq(expected) + ", got " + StringUtil.jq(actual));
            }
        }
    }

    private static String getParam(Map<String, String[]> params, String name) throws BadRequestException {
        String[] v = (String[]) params.get(name);
        if (v == null) {
            return null;
        }
        if (v.length == 0) {
            throw new IllegalArgumentException("Parameter \"" + name + "\" missing value.");
        } else if (v.length == 1) {
            return v[CSRF_STRING_SIZE];
        } else {
            throw new BadRequestException("multiple occurrences of \"" + name + "\" parameter");
        }
    }

    public static Builder newRequestBuilder() {
        return Request.newBuilder();
    }
}
