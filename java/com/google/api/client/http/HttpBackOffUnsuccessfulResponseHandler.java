package com.google.api.client.http;

import com.google.api.client.util.BackOff;
import com.google.api.client.util.BackOffUtils;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sleeper;
import java.io.IOException;

@Beta
public class HttpBackOffUnsuccessfulResponseHandler implements HttpUnsuccessfulResponseHandler {
    private final BackOff backOff;
    private BackOffRequired backOffRequired = BackOffRequired.ON_SERVER_ERROR;
    private Sleeper sleeper = Sleeper.DEFAULT;

    @Beta
    public interface BackOffRequired {
        public static final BackOffRequired ALWAYS = new BackOffRequired() {
            public boolean isRequired(HttpResponse response) {
                return true;
            }
        };
        public static final BackOffRequired ON_SERVER_ERROR = new BackOffRequired() {
            public boolean isRequired(HttpResponse response) {
                return response.getStatusCode() / 100 == 5;
            }
        };

        boolean isRequired(HttpResponse httpResponse);
    }

    public HttpBackOffUnsuccessfulResponseHandler(BackOff backOff) {
        this.backOff = (BackOff) Preconditions.checkNotNull(backOff);
    }

    public final BackOff getBackOff() {
        return this.backOff;
    }

    public final BackOffRequired getBackOffRequired() {
        return this.backOffRequired;
    }

    public HttpBackOffUnsuccessfulResponseHandler setBackOffRequired(BackOffRequired backOffRequired) {
        this.backOffRequired = (BackOffRequired) Preconditions.checkNotNull(backOffRequired);
        return this;
    }

    public final Sleeper getSleeper() {
        return this.sleeper;
    }

    public HttpBackOffUnsuccessfulResponseHandler setSleeper(Sleeper sleeper) {
        this.sleeper = (Sleeper) Preconditions.checkNotNull(sleeper);
        return this;
    }

    public final boolean handleResponse(HttpRequest request, HttpResponse response, boolean supportsRetry) throws IOException {
        boolean z = false;
        if (supportsRetry && this.backOffRequired.isRequired(response)) {
            try {
                z = BackOffUtils.next(this.sleeper, this.backOff);
            } catch (InterruptedException e) {
            }
        }
        return z;
    }
}
