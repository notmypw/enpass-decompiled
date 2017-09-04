package com.google.api.client.googleapis.auth.clientlogin;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpResponseException.Builder;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Key;
import com.google.api.client.util.StringUtils;
import com.google.api.client.util.Strings;
import java.io.IOException;

@Beta
public final class ClientLogin {
    @Key
    public String accountType;
    @Key("source")
    public String applicationName;
    @Key("service")
    public String authTokenType;
    @Key("logincaptcha")
    public String captchaAnswer;
    @Key("logintoken")
    public String captchaToken;
    @Key("Passwd")
    public String password;
    public GenericUrl serverUrl = new GenericUrl("https://www.google.com");
    public HttpTransport transport;
    @Key("Email")
    public String username;

    public static final class ErrorInfo {
        @Key("CaptchaToken")
        public String captchaToken;
        @Key("CaptchaUrl")
        public String captchaUrl;
        @Key("Error")
        public String error;
        @Key("Url")
        public String url;
    }

    public static final class Response implements HttpExecuteInterceptor, HttpRequestInitializer {
        @Key("Auth")
        public String auth;

        public String getAuthorizationHeaderValue() {
            return ClientLogin.getAuthorizationHeaderValue(this.auth);
        }

        public void initialize(HttpRequest request) {
            request.setInterceptor(this);
        }

        public void intercept(HttpRequest request) {
            request.getHeaders().setAuthorization(getAuthorizationHeaderValue());
        }
    }

    public Response authenticate() throws IOException {
        GenericUrl url = this.serverUrl.clone();
        url.appendRawPath("/accounts/ClientLogin");
        HttpRequest request = this.transport.createRequestFactory().buildPostRequest(url, new UrlEncodedContent(this));
        request.setParser(AuthKeyValueParser.INSTANCE);
        request.setContentLoggingLimit(0);
        request.setThrowExceptionOnExecuteError(false);
        HttpResponse response = request.execute();
        if (response.isSuccessStatusCode()) {
            return (Response) response.parseAs(Response.class);
        }
        Builder builder = new Builder(response.getStatusCode(), response.getStatusMessage(), response.getHeaders());
        ErrorInfo details = (ErrorInfo) response.parseAs(ErrorInfo.class);
        String detailString = details.toString();
        StringBuilder message = HttpResponseException.computeMessageBuffer(response);
        if (!Strings.isNullOrEmpty(detailString)) {
            message.append(StringUtils.LINE_SEPARATOR).append(detailString);
            builder.setContent(detailString);
        }
        builder.setMessage(message.toString());
        throw new ClientLoginResponseException(builder, details);
    }

    public static String getAuthorizationHeaderValue(String authToken) {
        return "GoogleLogin auth=" + authToken;
    }
}
