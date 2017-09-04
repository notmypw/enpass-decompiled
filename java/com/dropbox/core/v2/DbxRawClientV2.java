package com.dropbox.core.v2;

import com.dropbox.core.BadResponseException;
import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxHost;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxRequestUtil;
import com.dropbox.core.DbxWrappedException;
import com.dropbox.core.NetworkIOException;
import com.dropbox.core.RetryException;
import com.dropbox.core.http.HttpRequestor.Header;
import com.dropbox.core.http.HttpRequestor.Response;
import com.dropbox.core.http.HttpRequestor.Uploader;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.util.LangUtil;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.clans.fab.BuildConfig;
import com.google.api.client.http.HttpStatusCodes;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class DbxRawClientV2 {
    private static final JsonFactory JSON = new JsonFactory();
    private static final Random RAND = new Random();
    public static final String USER_AGENT_ID = "OfficialDropboxJavaSDKv2";
    private final DbxHost host;
    private final DbxRequestConfig requestConfig;

    private interface RetriableExecution<T> {
        T execute() throws DbxWrappedException, DbxException;
    }

    protected abstract void addAuthHeaders(List<Header> list);

    protected DbxRawClientV2(DbxRequestConfig requestConfig, DbxHost host) {
        if (requestConfig == null) {
            throw new NullPointerException("requestConfig");
        } else if (host == null) {
            throw new NullPointerException("host");
        } else {
            this.requestConfig = requestConfig;
            this.host = host;
        }
    }

    public <ArgT, ResT, ErrT> ResT rpcStyle(String host, String path, ArgT arg, boolean noAuth, StoneSerializer<ArgT> argSerializer, StoneSerializer<ResT> responseSerializer, StoneSerializer<ErrT> errorSerializer) throws DbxWrappedException, DbxException {
        final byte[] body = writeAsBytes(argSerializer, arg);
        final List<Header> headers = new ArrayList();
        if (!noAuth) {
            addAuthHeaders(headers);
        }
        if (!this.host.getNotify().equals(host)) {
            DbxRequestUtil.addUserLocaleHeader(headers, this.requestConfig);
        }
        headers.add(new Header("Content-Type", "application/json; charset=utf-8"));
        final String str = host;
        final String str2 = path;
        final StoneSerializer<ResT> stoneSerializer = responseSerializer;
        final StoneSerializer<ErrT> stoneSerializer2 = errorSerializer;
        return executeRetriable(this.requestConfig.getMaxRetries(), new RetriableExecution<ResT>() {
            public ResT execute() throws DbxWrappedException, DbxException {
                Response response = DbxRequestUtil.startPostRaw(DbxRawClientV2.this.requestConfig, DbxRawClientV2.USER_AGENT_ID, str, str2, body, headers);
                try {
                    switch (response.getStatusCode()) {
                        case HttpStatusCodes.STATUS_CODE_OK /*200*/:
                            return stoneSerializer.deserialize(response.getBody());
                        case HttpStatusCodes.STATUS_CODE_CONFLICT /*409*/:
                            throw DbxWrappedException.fromResponse(stoneSerializer2, response);
                        default:
                            throw DbxRequestUtil.unexpectedStatus(response);
                    }
                } catch (JsonProcessingException ex) {
                    throw new BadResponseException(DbxRequestUtil.getRequestId(response), "Bad JSON: " + ex.getMessage(), ex);
                } catch (IOException ex2) {
                    throw new NetworkIOException(ex2);
                }
            }
        });
    }

    public <ArgT, ResT, ErrT> DbxDownloader<ResT> downloadStyle(String host, String path, ArgT arg, boolean noAuth, List<Header> extraHeaders, StoneSerializer<ArgT> argSerializer, StoneSerializer<ResT> responseSerializer, StoneSerializer<ErrT> errorSerializer) throws DbxWrappedException, DbxException {
        final List<Header> headers = new ArrayList(extraHeaders);
        if (!noAuth) {
            addAuthHeaders(headers);
        }
        DbxRequestUtil.addUserLocaleHeader(headers, this.requestConfig);
        headers.add(new Header("Dropbox-API-Arg", headerSafeJson(argSerializer, arg)));
        headers.add(new Header("Content-Type", BuildConfig.FLAVOR));
        final byte[] body = new byte[0];
        final String str = host;
        final String str2 = path;
        final StoneSerializer<ResT> stoneSerializer = responseSerializer;
        final StoneSerializer<ErrT> stoneSerializer2 = errorSerializer;
        return (DbxDownloader) executeRetriable(this.requestConfig.getMaxRetries(), new RetriableExecution<DbxDownloader<ResT>>() {
            public DbxDownloader<ResT> execute() throws DbxWrappedException, DbxException {
                Response response = DbxRequestUtil.startPostRaw(DbxRawClientV2.this.requestConfig, DbxRawClientV2.USER_AGENT_ID, str, str2, body, headers);
                String requestId = DbxRequestUtil.getRequestId(response);
                try {
                    switch (response.getStatusCode()) {
                        case HttpStatusCodes.STATUS_CODE_OK /*200*/:
                        case 206:
                            List<String> resultHeaders = (List) response.getHeaders().get("dropbox-api-result");
                            if (resultHeaders == null) {
                                throw new BadResponseException(requestId, "Missing Dropbox-API-Result header; " + response.getHeaders());
                            } else if (resultHeaders.size() == 0) {
                                throw new BadResponseException(requestId, "No Dropbox-API-Result header; " + response.getHeaders());
                            } else {
                                String resultHeader = (String) resultHeaders.get(0);
                                if (resultHeader != null) {
                                    return new DbxDownloader(stoneSerializer.deserialize(resultHeader), response.getBody());
                                }
                                throw new BadResponseException(requestId, "Null Dropbox-API-Result header; " + response.getHeaders());
                            }
                        case HttpStatusCodes.STATUS_CODE_CONFLICT /*409*/:
                            throw DbxWrappedException.fromResponse(stoneSerializer2, response);
                        default:
                            throw DbxRequestUtil.unexpectedStatus(response);
                    }
                } catch (JsonProcessingException ex) {
                    throw new BadResponseException(requestId, "Bad JSON: " + ex.getMessage(), ex);
                } catch (IOException ex2) {
                    throw new NetworkIOException(ex2);
                }
            }
        });
    }

    private static <T> byte[] writeAsBytes(StoneSerializer<T> serializer, T arg) throws DbxException {
        OutputStream out = new ByteArrayOutputStream();
        try {
            serializer.serialize((Object) arg, out);
            return out.toByteArray();
        } catch (IOException ex) {
            throw LangUtil.mkAssert("Impossible", ex);
        }
    }

    private static <T> String headerSafeJson(StoneSerializer<T> serializer, T value) {
        StringWriter out = new StringWriter();
        try {
            JsonGenerator g = JSON.createGenerator(out);
            g.setHighestNonEscapedChar(126);
            serializer.serialize((Object) value, g);
            g.flush();
            return out.toString();
        } catch (IOException ex) {
            throw LangUtil.mkAssert("Impossible", ex);
        }
    }

    public <ArgT> Uploader uploadStyle(String host, String path, ArgT arg, boolean noAuth, StoneSerializer<ArgT> argSerializer) throws DbxException {
        String uri = DbxRequestUtil.buildUri(host, path);
        List<Header> headers = new ArrayList();
        if (!noAuth) {
            addAuthHeaders(headers);
        }
        DbxRequestUtil.addUserLocaleHeader(headers, this.requestConfig);
        headers.add(new Header("Content-Type", "application/octet-stream"));
        headers = DbxRequestUtil.addUserAgentHeader(headers, this.requestConfig, USER_AGENT_ID);
        headers.add(new Header("Dropbox-API-Arg", headerSafeJson(argSerializer, arg)));
        try {
            return this.requestConfig.getHttpRequestor().startPost(uri, headers);
        } catch (IOException ex) {
            throw new NetworkIOException(ex);
        }
    }

    public DbxRequestConfig getRequestConfig() {
        return this.requestConfig;
    }

    public DbxHost getHost() {
        return this.host;
    }

    private static <T> T executeRetriable(int maxRetries, RetriableExecution<T> execution) throws DbxWrappedException, DbxException {
        if (maxRetries == 0) {
            return execution.execute();
        }
        int retries = 0;
        while (true) {
            try {
                break;
            } catch (RetryException ex) {
                if (retries < maxRetries) {
                    retries++;
                    sleepQuietlyWithJitter(ex.getBackoffMillis());
                } else {
                    throw ex;
                }
            }
        }
        return execution.execute();
    }

    private static void sleepQuietlyWithJitter(long millis) {
        long sleepMillis = millis + ((long) RAND.nextInt(1000));
        if (sleepMillis > 0) {
            try {
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
