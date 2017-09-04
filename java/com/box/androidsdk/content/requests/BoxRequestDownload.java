package com.box.androidsdk.content.requests;

import com.box.androidsdk.content.BoxConstants;
import com.box.androidsdk.content.BoxException;
import com.box.androidsdk.content.BoxException.MaxAttemptsExceeded;
import com.box.androidsdk.content.listeners.DownloadStartListener;
import com.box.androidsdk.content.listeners.ProgressListener;
import com.box.androidsdk.content.models.BoxDownload;
import com.box.androidsdk.content.models.BoxObject;
import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.requests.BoxRequest.BoxRequestHandler;
import com.box.androidsdk.content.requests.BoxRequest.Methods;
import com.box.androidsdk.content.utils.BoxLogUtils;
import com.box.androidsdk.content.utils.ProgressOutputStream;
import com.box.androidsdk.content.utils.SdkUtils;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.client.util.ExponentialBackOff;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

public abstract class BoxRequestDownload<E extends BoxObject, R extends BoxRequest<E, R>> extends BoxRequest<E, R> {
    private static final String QUERY_VERSION = "version";
    DownloadStartListener mDownloadStartListener;
    OutputStream mFileOutputStream;
    long mRangeEnd;
    long mRangeStart;
    File mTarget;

    public static class DownloadRequestHandler extends BoxRequestHandler<BoxRequestDownload> {
        protected static final int DEFAULT_MAX_WAIT_MILLIS = 90000;
        protected static final int DEFAULT_NUM_RETRIES = 2;
        protected int mNumAcceptedRetries = 0;
        protected int mRetryAfterMillis = 1000;

        public DownloadRequestHandler(BoxRequestDownload request) {
            super(request);
        }

        protected OutputStream getOutputStream(BoxDownload downloadInfo) throws FileNotFoundException, IOException {
            if (((BoxRequestDownload) this.mRequest).mFileOutputStream != null) {
                return ((BoxRequestDownload) this.mRequest).mFileOutputStream;
            }
            if (!downloadInfo.getOutputFile().exists()) {
                downloadInfo.getOutputFile().createNewFile();
            }
            return new FileOutputStream(downloadInfo.getOutputFile());
        }

        public BoxDownload onResponse(Class clazz, BoxHttpResponse response) throws IllegalAccessException, InstantiationException, BoxException {
            Throwable e;
            Throwable th;
            String contentType = response.getContentType();
            long contentLength = -1;
            if (response.getResponseCode() == BoxConstants.HTTP_STATUS_TOO_MANY_REQUESTS) {
                return (BoxDownload) retryRateLimited(response);
            }
            if (response.getResponseCode() == 202) {
                try {
                    if (this.mNumAcceptedRetries < DEFAULT_NUM_RETRIES) {
                        this.mNumAcceptedRetries++;
                        this.mRetryAfterMillis = BoxRequestHandler.getRetryAfterFromResponse(response, 1);
                    } else if (this.mRetryAfterMillis < DEFAULT_MAX_WAIT_MILLIS) {
                        this.mRetryAfterMillis = (int) (((double) this.mRetryAfterMillis) * (ExponentialBackOff.DEFAULT_MULTIPLIER + Math.random()));
                    } else {
                        throw new MaxAttemptsExceeded("Max wait time exceeded.", this.mNumAcceptedRetries);
                    }
                    Thread.sleep((long) this.mRetryAfterMillis);
                    return (BoxDownload) ((BoxRequestDownload) this.mRequest).send();
                } catch (InterruptedException e2) {
                    throw new BoxException(e2.getMessage(), response);
                }
            } else if (response.getResponseCode() != HttpStatusCodes.STATUS_CODE_OK && response.getResponseCode() != 206) {
                return new BoxDownload(null, 0, null, null, null, null);
            } else {
                String contentLengthString = response.getHttpURLConnection().getHeaderField("Content-Length");
                String contentDisposition = response.getHttpURLConnection().getHeaderField("Content-Disposition");
                try {
                    contentLength = Long.parseLong(contentLengthString);
                } catch (Exception e3) {
                }
                BoxDownload downloadInfo = new BoxDownload(contentDisposition, contentLength, contentType, response.getHttpURLConnection().getHeaderField("Content-Range"), response.getHttpURLConnection().getHeaderField("Date"), response.getHttpURLConnection().getHeaderField("Expiration")) {
                    public File getOutputFile() {
                        if (((BoxRequestDownload) DownloadRequestHandler.this.mRequest).getTarget() == null) {
                            return null;
                        }
                        if (((BoxRequestDownload) DownloadRequestHandler.this.mRequest).getTarget().isFile()) {
                            return ((BoxRequestDownload) DownloadRequestHandler.this.mRequest).getTarget();
                        }
                        if (SdkUtils.isEmptyString(getFileName())) {
                            return super.getOutputFile();
                        }
                        return new File(((BoxRequestDownload) DownloadRequestHandler.this.mRequest).getTarget(), getFileName());
                    }
                };
                if (((BoxRequestDownload) this.mRequest).mDownloadStartListener != null) {
                    ((BoxRequestDownload) this.mRequest).mDownloadStartListener.onStart(downloadInfo);
                }
                OutputStream output = null;
                try {
                    if (((BoxRequestDownload) this.mRequest).mListener != null) {
                        OutputStream progressOutputStream = new ProgressOutputStream(getOutputStream(downloadInfo), ((BoxRequestDownload) this.mRequest).mListener, contentLength);
                        try {
                            ((BoxRequestDownload) this.mRequest).mListener.onProgressChanged(0, contentLength);
                            output = progressOutputStream;
                        } catch (Exception e4) {
                            e = e4;
                            output = progressOutputStream;
                            try {
                                throw new BoxException(e.getMessage(), e);
                            } catch (Throwable th2) {
                                th = th2;
                                if (((BoxRequestDownload) this.mRequest).getTargetStream() == null) {
                                    try {
                                        output.close();
                                    } catch (IOException e5) {
                                    }
                                }
                                throw th;
                            }
                        } catch (Throwable th22) {
                            th = th22;
                            output = progressOutputStream;
                            if (((BoxRequestDownload) this.mRequest).getTargetStream() == null) {
                                output.close();
                            }
                            throw th;
                        }
                    }
                    output = getOutputStream(downloadInfo);
                    SdkUtils.copyStream(response.getHttpURLConnection().getInputStream(), output);
                    if (((BoxRequestDownload) this.mRequest).getTargetStream() == null) {
                        try {
                            output.close();
                        } catch (IOException e6) {
                        }
                    }
                    return downloadInfo;
                } catch (Exception e7) {
                    e = e7;
                    throw new BoxException(e.getMessage(), e);
                }
            }
        }
    }

    public BoxRequestDownload(Class<E> clazz, OutputStream outputStream, String requestUrl, BoxSession session) {
        super(clazz, requestUrl, session);
        this.mRangeStart = -1;
        this.mRangeEnd = -1;
        this.mRequestMethod = Methods.GET;
        this.mRequestUrlString = requestUrl;
        this.mFileOutputStream = outputStream;
        setRequestHandler(new DownloadRequestHandler(this));
    }

    public BoxRequestDownload(Class<E> clazz, File target, String requestUrl, BoxSession session) {
        super(clazz, requestUrl, session);
        this.mRangeStart = -1;
        this.mRangeEnd = -1;
        this.mRequestMethod = Methods.GET;
        this.mRequestUrlString = requestUrl;
        this.mTarget = target;
        setRequestHandler(new DownloadRequestHandler(this));
    }

    protected void setHeaders(BoxHttpRequest request) {
        super.setHeaders(request);
        if (this.mRangeStart != -1 && this.mRangeEnd != -1) {
            request.addHeader("Range", String.format("bytes=%s-%s", new Object[]{Long.toString(this.mRangeStart), Long.toString(this.mRangeEnd)}));
        }
    }

    protected void logDebug(BoxHttpResponse response) throws BoxException {
        logRequest();
        BoxLogUtils.i(BoxConstants.TAG, String.format(Locale.ENGLISH, "Response (%s)", new Object[]{Integer.valueOf(response.getResponseCode())}));
    }

    public File getTarget() {
        return this.mTarget;
    }

    public OutputStream getTargetStream() {
        return this.mFileOutputStream;
    }

    public long getRangeStart() {
        return this.mRangeStart;
    }

    public long getRangeEnd() {
        return this.mRangeEnd;
    }

    public R setRange(long rangeStart, long rangeEnd) {
        this.mRangeStart = rangeStart;
        this.mRangeEnd = rangeEnd;
        return this;
    }

    public R setVersion(String versionId) {
        this.mQueryMap.put(QUERY_VERSION, versionId);
        return this;
    }

    public String getVersion() {
        return (String) this.mQueryMap.get(QUERY_VERSION);
    }

    public R setProgressListener(ProgressListener listener) {
        this.mListener = listener;
        return this;
    }

    public R setDownloadStartListener(DownloadStartListener listener) {
        this.mDownloadStartListener = listener;
        return this;
    }
}
