package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.Beta;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

@Beta
public class OAuth2Utils {
    private static final int COMPUTE_PING_CONNECTION_TIMEOUT_MS = 500;
    private static final String DEFAULT_METADATA_SERVER_URL = "http://169.254.169.254";
    private static final Logger LOGGER = Logger.getLogger(OAuth2Utils.class.getName());
    private static final int MAX_COMPUTE_PING_TRIES = 3;
    static final Charset UTF_8 = Charset.forName("UTF-8");

    static <T extends Throwable> T exceptionWithCause(T exception, Throwable cause) {
        exception.initCause(cause);
        return exception;
    }

    static boolean headersContainValue(HttpHeaders headers, String headerName, String value) {
        Collection<Object> values = headers.get(headerName);
        if (values instanceof Collection) {
            for (Object headerValue : values) {
                if ((headerValue instanceof String) && ((String) headerValue).equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean runningOnComputeEngine(HttpTransport transport, SystemEnvironmentProvider environment) {
        if (Boolean.parseBoolean(environment.getEnv("NO_GCE_CHECK"))) {
            return false;
        }
        GenericUrl tokenUrl = new GenericUrl(getMetadataServerUrl(environment));
        int i = 1;
        while (i <= MAX_COMPUTE_PING_TRIES) {
            HttpResponse response;
            try {
                HttpRequest request = transport.createRequestFactory().buildGetRequest(tokenUrl);
                request.setConnectTimeout(COMPUTE_PING_CONNECTION_TIMEOUT_MS);
                response = request.execute();
                boolean headersContainValue = headersContainValue(response.getHeaders(), "Metadata-Flavor", "Google");
                response.disconnect();
                return headersContainValue;
            } catch (SocketTimeoutException e) {
            } catch (IOException e2) {
                LOGGER.log(Level.WARNING, "Failed to detect whether we are running on Google Compute Engine.", e2);
            } catch (Throwable th) {
                response.disconnect();
            }
        }
        return false;
        i++;
    }

    public static String getMetadataServerUrl() {
        return getMetadataServerUrl(SystemEnvironmentProvider.INSTANCE);
    }

    static String getMetadataServerUrl(SystemEnvironmentProvider environment) {
        String metadataServerAddress = environment.getEnv("GCE_METADATA_HOST");
        if (metadataServerAddress != null) {
            return "http://" + metadataServerAddress;
        }
        return DEFAULT_METADATA_SERVER_URL;
    }
}
