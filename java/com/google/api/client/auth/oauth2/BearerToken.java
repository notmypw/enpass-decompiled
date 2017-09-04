package com.google.api.client.auth.oauth2;

import com.google.api.client.auth.oauth2.Credential.AccessMethod;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.util.Data;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class BearerToken {
    static final Pattern INVALID_TOKEN_ERROR = Pattern.compile("\\s*error\\s*=\\s*\"?invalid_token\"?");
    static final String PARAM_NAME = "access_token";

    static final class AuthorizationHeaderAccessMethod implements AccessMethod {
        static final String HEADER_PREFIX = "Bearer ";

        AuthorizationHeaderAccessMethod() {
        }

        public void intercept(HttpRequest request, String accessToken) throws IOException {
            request.getHeaders().setAuthorization(HEADER_PREFIX + accessToken);
        }

        public String getAccessTokenFromRequest(HttpRequest request) {
            List<String> authorizationAsList = request.getHeaders().getAuthorizationAsList();
            if (authorizationAsList != null) {
                for (String header : authorizationAsList) {
                    if (header.startsWith(HEADER_PREFIX)) {
                        return header.substring(HEADER_PREFIX.length());
                    }
                }
            }
            return null;
        }
    }

    static final class FormEncodedBodyAccessMethod implements AccessMethod {
        FormEncodedBodyAccessMethod() {
        }

        public void intercept(HttpRequest request, String accessToken) throws IOException {
            Preconditions.checkArgument(!HttpMethods.GET.equals(request.getRequestMethod()), "HTTP GET method is not supported");
            getData(request).put(BearerToken.PARAM_NAME, accessToken);
        }

        public String getAccessTokenFromRequest(HttpRequest request) {
            Object bodyParam = getData(request).get(BearerToken.PARAM_NAME);
            return bodyParam == null ? null : bodyParam.toString();
        }

        private static Map<String, Object> getData(HttpRequest request) {
            return Data.mapOf(UrlEncodedContent.getContent(request).getData());
        }
    }

    static final class QueryParameterAccessMethod implements AccessMethod {
        QueryParameterAccessMethod() {
        }

        public void intercept(HttpRequest request, String accessToken) throws IOException {
            request.getUrl().set(BearerToken.PARAM_NAME, (Object) accessToken);
        }

        public String getAccessTokenFromRequest(HttpRequest request) {
            Object param = request.getUrl().get(BearerToken.PARAM_NAME);
            return param == null ? null : param.toString();
        }
    }

    public static AccessMethod authorizationHeaderAccessMethod() {
        return new AuthorizationHeaderAccessMethod();
    }

    public static AccessMethod formEncodedBodyAccessMethod() {
        return new FormEncodedBodyAccessMethod();
    }

    public static AccessMethod queryParameterAccessMethod() {
        return new QueryParameterAccessMethod();
    }
}
