package com.google.api.client.testing.http.apache;

import com.google.api.client.util.Beta;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.AuthenticationHandler;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.RequestDirector;
import org.apache.http.client.UserTokenHandler;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;

@Beta
public class MockHttpClient extends DefaultHttpClient {
    int responseCode;

    protected RequestDirector createClientRequestDirector(HttpRequestExecutor requestExec, ClientConnectionManager conman, ConnectionReuseStrategy reustrat, ConnectionKeepAliveStrategy kastrat, HttpRoutePlanner rouplan, HttpProcessor httpProcessor, HttpRequestRetryHandler retryHandler, RedirectHandler redirectHandler, AuthenticationHandler targetAuthHandler, AuthenticationHandler proxyAuthHandler, UserTokenHandler stateHandler, HttpParams params) {
        return new RequestDirector() {
            @Beta
            public HttpResponse execute(HttpHost target, HttpRequest request, HttpContext context) throws HttpException, IOException {
                return new BasicHttpResponse(HttpVersion.HTTP_1_1, MockHttpClient.this.responseCode, null);
            }
        };
    }

    public final int getResponseCode() {
        return this.responseCode;
    }

    public MockHttpClient setResponseCode(int responseCode) {
        Preconditions.checkArgument(responseCode >= 0);
        this.responseCode = responseCode;
        return this;
    }
}
