package com.google.api.client.googleapis.auth.oauth2;

import com.github.clans.fab.BuildConfig;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential.Builder;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.util.Beta;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.AccessControlException;
import java.util.Locale;
import net.sqlcipher.database.SQLiteDatabase;

@Beta
class DefaultCredentialProvider extends SystemEnvironmentProvider {
    static final String APP_ENGINE_CREDENTIAL_CLASS = "com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential$AppEngineCredentialWrapper";
    static final String CLOUDSDK_CONFIG_DIRECTORY = "gcloud";
    static final String CLOUD_SHELL_ENV_VAR = "DEVSHELL_CLIENT_PORT";
    static final String CREDENTIAL_ENV_VAR = "GOOGLE_APPLICATION_CREDENTIALS";
    static final String HELP_PERMALINK = "https://developers.google.com/accounts/docs/application-default-credentials";
    static final String WELL_KNOWN_CREDENTIALS_FILE = "application_default_credentials.json";
    private GoogleCredential cachedCredential = null;
    private Environment detectedEnvironment = null;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$api$client$googleapis$auth$oauth2$DefaultCredentialProvider$Environment = new int[Environment.values().length];

        static {
            try {
                $SwitchMap$com$google$api$client$googleapis$auth$oauth2$DefaultCredentialProvider$Environment[Environment.ENVIRONMENT_VARIABLE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$api$client$googleapis$auth$oauth2$DefaultCredentialProvider$Environment[Environment.WELL_KNOWN_FILE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$api$client$googleapis$auth$oauth2$DefaultCredentialProvider$Environment[Environment.APP_ENGINE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$google$api$client$googleapis$auth$oauth2$DefaultCredentialProvider$Environment[Environment.CLOUD_SHELL.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$google$api$client$googleapis$auth$oauth2$DefaultCredentialProvider$Environment[Environment.COMPUTE_ENGINE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    private static class ComputeGoogleCredential extends GoogleCredential {
        private static final String TOKEN_SERVER_ENCODED_URL = (OAuth2Utils.getMetadataServerUrl() + "/computeMetadata/v1/instance/service-accounts/default/token");

        ComputeGoogleCredential(HttpTransport transport, JsonFactory jsonFactory) {
            super(new Builder().setTransport(transport).setJsonFactory(jsonFactory).setTokenServerEncodedUrl(TOKEN_SERVER_ENCODED_URL));
        }

        protected TokenResponse executeRefreshToken() throws IOException {
            HttpRequest request = getTransport().createRequestFactory().buildGetRequest(new GenericUrl(getTokenServerEncodedUrl()));
            JsonObjectParser parser = new JsonObjectParser(getJsonFactory());
            request.setParser(parser);
            request.getHeaders().set("Metadata-Flavor", (Object) "Google");
            request.setThrowExceptionOnExecuteError(false);
            HttpResponse response = request.execute();
            int statusCode = response.getStatusCode();
            if (statusCode == HttpStatusCodes.STATUS_CODE_OK) {
                InputStream content = response.getContent();
                if (content != null) {
                    return (TokenResponse) parser.parseAndClose(content, response.getContentCharset(), TokenResponse.class);
                }
                throw new IOException("Empty content from metadata token server request.");
            } else if (statusCode == HttpStatusCodes.STATUS_CODE_NOT_FOUND) {
                throw new IOException(String.format("Error code %s trying to get security access token from Compute Engine metadata for the default service account. This may be because the virtual machine instance does not have permission scopes specified.", new Object[]{Integer.valueOf(statusCode)}));
            } else {
                throw new IOException(String.format("Unexpected Error code %s trying to get security access token from Compute Engine metadata for the default service account: %s", new Object[]{Integer.valueOf(statusCode), response.parseAsString()}));
            }
        }
    }

    private enum Environment {
        UNKNOWN,
        ENVIRONMENT_VARIABLE,
        WELL_KNOWN_FILE,
        CLOUD_SHELL,
        APP_ENGINE,
        COMPUTE_ENGINE
    }

    DefaultCredentialProvider() {
    }

    final GoogleCredential getDefaultCredential(HttpTransport transport, JsonFactory jsonFactory) throws IOException {
        synchronized (this) {
            if (this.cachedCredential == null) {
                this.cachedCredential = getDefaultCredentialUnsynchronized(transport, jsonFactory);
            }
            if (this.cachedCredential != null) {
                GoogleCredential googleCredential = this.cachedCredential;
                return googleCredential;
            }
            throw new IOException(String.format("The Application Default Credentials are not available. They are available if running on Google App Engine, Google Compute Engine, or Google Cloud Shell. Otherwise, the environment variable %s must be defined pointing to a file defining the credentials. See %s for more information.", new Object[]{CREDENTIAL_ENV_VAR, HELP_PERMALINK}));
        }
    }

    private final GoogleCredential getDefaultCredentialUnsynchronized(HttpTransport transport, JsonFactory jsonFactory) throws IOException {
        if (this.detectedEnvironment == null) {
            this.detectedEnvironment = detectEnvironment(transport);
        }
        switch (AnonymousClass1.$SwitchMap$com$google$api$client$googleapis$auth$oauth2$DefaultCredentialProvider$Environment[this.detectedEnvironment.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                return getCredentialUsingEnvironmentVariable(transport, jsonFactory);
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                return getCredentialUsingWellKnownFile(transport, jsonFactory);
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                return getAppEngineCredential(transport, jsonFactory);
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                return getCloudShellCredential(jsonFactory);
            case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                return getComputeCredential(transport, jsonFactory);
            default:
                return null;
        }
    }

    private final File getWellKnownCredentialsFile() {
        File cloudConfigPath;
        if (getProperty("os.name", BuildConfig.FLAVOR).toLowerCase(Locale.US).indexOf("windows") >= 0) {
            cloudConfigPath = new File(new File(getEnv("APPDATA")), CLOUDSDK_CONFIG_DIRECTORY);
        } else {
            cloudConfigPath = new File(new File(getProperty("user.home", BuildConfig.FLAVOR), ".config"), CLOUDSDK_CONFIG_DIRECTORY);
        }
        return new File(cloudConfigPath, WELL_KNOWN_CREDENTIALS_FILE);
    }

    boolean fileExists(File file) {
        return file.exists() && !file.isDirectory();
    }

    String getProperty(String property, String def) {
        return System.getProperty(property, def);
    }

    Class<?> forName(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }

    private final Environment detectEnvironment(HttpTransport transport) throws IOException {
        if (runningUsingEnvironmentVariable()) {
            return Environment.ENVIRONMENT_VARIABLE;
        }
        if (runningUsingWellKnownFile()) {
            return Environment.WELL_KNOWN_FILE;
        }
        if (runningOnAppEngine()) {
            return Environment.APP_ENGINE;
        }
        if (runningOnCloudShell()) {
            return Environment.CLOUD_SHELL;
        }
        if (OAuth2Utils.runningOnComputeEngine(transport, this)) {
            return Environment.COMPUTE_ENGINE;
        }
        return Environment.UNKNOWN;
    }

    private boolean runningUsingEnvironmentVariable() throws IOException {
        String credentialsPath = getEnv(CREDENTIAL_ENV_VAR);
        if (credentialsPath == null || credentialsPath.length() == 0) {
            return false;
        }
        try {
            File credentialsFile = new File(credentialsPath);
            if (credentialsFile.exists() && !credentialsFile.isDirectory()) {
                return true;
            }
            throw new IOException(String.format("Error reading credential file from environment variable %s, value '%s': File does not exist.", new Object[]{CREDENTIAL_ENV_VAR, credentialsPath}));
        } catch (AccessControlException e) {
            return false;
        }
    }

    private GoogleCredential getCredentialUsingEnvironmentVariable(HttpTransport transport, JsonFactory jsonFactory) throws IOException {
        IOException e;
        Throwable th;
        InputStream credentialsStream = null;
        try {
            InputStream credentialsStream2 = new FileInputStream(getEnv(CREDENTIAL_ENV_VAR));
            try {
                GoogleCredential fromStream = GoogleCredential.fromStream(credentialsStream2, transport, jsonFactory);
                if (credentialsStream2 != null) {
                    credentialsStream2.close();
                }
                return fromStream;
            } catch (IOException e2) {
                e = e2;
                credentialsStream = credentialsStream2;
                try {
                    throw ((IOException) OAuth2Utils.exceptionWithCause(new IOException(String.format("Error reading credential file from environment variable %s, value '%s': %s", new Object[]{CREDENTIAL_ENV_VAR, credentialsPath, e.getMessage()})), e));
                } catch (Throwable th2) {
                    th = th2;
                    if (credentialsStream != null) {
                        credentialsStream.close();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                credentialsStream = credentialsStream2;
                if (credentialsStream != null) {
                    credentialsStream.close();
                }
                throw th;
            }
        } catch (IOException e3) {
            e = e3;
            throw ((IOException) OAuth2Utils.exceptionWithCause(new IOException(String.format("Error reading credential file from environment variable %s, value '%s': %s", new Object[]{CREDENTIAL_ENV_VAR, credentialsPath, e.getMessage()})), e));
        }
    }

    private boolean runningUsingWellKnownFile() {
        try {
            return fileExists(getWellKnownCredentialsFile());
        } catch (AccessControlException e) {
            return false;
        }
    }

    private GoogleCredential getCredentialUsingWellKnownFile(HttpTransport transport, JsonFactory jsonFactory) throws IOException {
        IOException e;
        Throwable th;
        InputStream credentialsStream = null;
        try {
            InputStream credentialsStream2 = new FileInputStream(getWellKnownCredentialsFile());
            try {
                GoogleCredential fromStream = GoogleCredential.fromStream(credentialsStream2, transport, jsonFactory);
                if (credentialsStream2 != null) {
                    credentialsStream2.close();
                }
                return fromStream;
            } catch (IOException e2) {
                e = e2;
                credentialsStream = credentialsStream2;
                try {
                    throw new IOException(String.format("Error reading credential file from location %s: %s", new Object[]{wellKnownFileLocation, e.getMessage()}));
                } catch (Throwable th2) {
                    th = th2;
                    if (credentialsStream != null) {
                        credentialsStream.close();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                credentialsStream = credentialsStream2;
                if (credentialsStream != null) {
                    credentialsStream.close();
                }
                throw th;
            }
        } catch (IOException e3) {
            e = e3;
            throw new IOException(String.format("Error reading credential file from location %s: %s", new Object[]{wellKnownFileLocation, e.getMessage()}));
        }
    }

    private boolean runningOnAppEngine() {
        Exception cause;
        try {
            try {
                Field environmentField = forName("com.google.appengine.api.utils.SystemProperty").getField("environment");
                if (environmentField.getType().getMethod(BoxMetadataUpdateTask.VALUE, new Class[0]).invoke(environmentField.get(null), new Object[0]) != null) {
                    return true;
                }
                return false;
            } catch (Exception exception) {
                cause = exception;
                throw ((RuntimeException) OAuth2Utils.exceptionWithCause(new RuntimeException(String.format("Unexpcted error trying to determine if runnning on Google App Engine: %s", new Object[]{cause.getMessage()})), cause));
            } catch (Exception exception2) {
                cause = exception2;
                throw ((RuntimeException) OAuth2Utils.exceptionWithCause(new RuntimeException(String.format("Unexpcted error trying to determine if runnning on Google App Engine: %s", new Object[]{cause.getMessage()})), cause));
            } catch (Exception exception22) {
                cause = exception22;
                throw ((RuntimeException) OAuth2Utils.exceptionWithCause(new RuntimeException(String.format("Unexpcted error trying to determine if runnning on Google App Engine: %s", new Object[]{cause.getMessage()})), cause));
            } catch (Exception exception222) {
                cause = exception222;
                throw ((RuntimeException) OAuth2Utils.exceptionWithCause(new RuntimeException(String.format("Unexpcted error trying to determine if runnning on Google App Engine: %s", new Object[]{cause.getMessage()})), cause));
            } catch (Exception exception2222) {
                cause = exception2222;
                throw ((RuntimeException) OAuth2Utils.exceptionWithCause(new RuntimeException(String.format("Unexpcted error trying to determine if runnning on Google App Engine: %s", new Object[]{cause.getMessage()})), cause));
            } catch (Exception exception22222) {
                cause = exception22222;
                throw ((RuntimeException) OAuth2Utils.exceptionWithCause(new RuntimeException(String.format("Unexpcted error trying to determine if runnning on Google App Engine: %s", new Object[]{cause.getMessage()})), cause));
            }
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private final GoogleCredential getAppEngineCredential(HttpTransport transport, JsonFactory jsonFactory) throws IOException {
        Exception innerException;
        try {
            return (GoogleCredential) forName(APP_ENGINE_CREDENTIAL_CLASS).getConstructor(new Class[]{HttpTransport.class, JsonFactory.class}).newInstance(new Object[]{transport, jsonFactory});
        } catch (Exception e) {
            innerException = e;
            throw ((IOException) OAuth2Utils.exceptionWithCause(new IOException(String.format("Application Default Credentials failed to create the Google App Engine service account credentials class %s. Check that the component 'google-api-client-appengine' is deployed.", new Object[]{APP_ENGINE_CREDENTIAL_CLASS})), innerException));
        } catch (Exception e2) {
            innerException = e2;
            throw ((IOException) OAuth2Utils.exceptionWithCause(new IOException(String.format("Application Default Credentials failed to create the Google App Engine service account credentials class %s. Check that the component 'google-api-client-appengine' is deployed.", new Object[]{APP_ENGINE_CREDENTIAL_CLASS})), innerException));
        } catch (Exception e22) {
            innerException = e22;
            throw ((IOException) OAuth2Utils.exceptionWithCause(new IOException(String.format("Application Default Credentials failed to create the Google App Engine service account credentials class %s. Check that the component 'google-api-client-appengine' is deployed.", new Object[]{APP_ENGINE_CREDENTIAL_CLASS})), innerException));
        } catch (Exception e222) {
            innerException = e222;
            throw ((IOException) OAuth2Utils.exceptionWithCause(new IOException(String.format("Application Default Credentials failed to create the Google App Engine service account credentials class %s. Check that the component 'google-api-client-appengine' is deployed.", new Object[]{APP_ENGINE_CREDENTIAL_CLASS})), innerException));
        } catch (Exception e2222) {
            innerException = e2222;
            throw ((IOException) OAuth2Utils.exceptionWithCause(new IOException(String.format("Application Default Credentials failed to create the Google App Engine service account credentials class %s. Check that the component 'google-api-client-appengine' is deployed.", new Object[]{APP_ENGINE_CREDENTIAL_CLASS})), innerException));
        }
    }

    private boolean runningOnCloudShell() {
        return getEnv(CLOUD_SHELL_ENV_VAR) != null;
    }

    private GoogleCredential getCloudShellCredential(JsonFactory jsonFactory) {
        return new CloudShellCredential(Integer.parseInt(getEnv(CLOUD_SHELL_ENV_VAR)), jsonFactory);
    }

    private final GoogleCredential getComputeCredential(HttpTransport transport, JsonFactory jsonFactory) {
        return new ComputeGoogleCredential(transport, jsonFactory);
    }
}
