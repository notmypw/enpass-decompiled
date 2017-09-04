package com.google.api.client.googleapis.media;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.IOUtils;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.io.OutputStream;

public final class MediaHttpDownloader {
    public static final int MAXIMUM_CHUNK_SIZE = 33554432;
    private long bytesDownloaded;
    private int chunkSize = MAXIMUM_CHUNK_SIZE;
    private boolean directDownloadEnabled = false;
    private DownloadState downloadState = DownloadState.NOT_STARTED;
    private long lastBytePos = -1;
    private long mediaContentLength;
    private MediaHttpDownloaderProgressListener progressListener;
    private final HttpRequestFactory requestFactory;
    private final HttpTransport transport;

    public enum DownloadState {
        NOT_STARTED,
        MEDIA_IN_PROGRESS,
        MEDIA_COMPLETE
    }

    public MediaHttpDownloader(HttpTransport transport, HttpRequestInitializer httpRequestInitializer) {
        this.transport = (HttpTransport) Preconditions.checkNotNull(transport);
        this.requestFactory = httpRequestInitializer == null ? transport.createRequestFactory() : transport.createRequestFactory(httpRequestInitializer);
    }

    public void download(GenericUrl requestUrl, OutputStream outputStream) throws IOException {
        download(requestUrl, null, outputStream);
    }

    public void download(GenericUrl requestUrl, HttpHeaders requestHeaders, OutputStream outputStream) throws IOException {
        Preconditions.checkArgument(this.downloadState == DownloadState.NOT_STARTED);
        requestUrl.put("alt", (Object) "media");
        if (this.directDownloadEnabled) {
            updateStateAndNotifyListener(DownloadState.MEDIA_IN_PROGRESS);
            this.mediaContentLength = executeCurrentRequest(this.lastBytePos, requestUrl, requestHeaders, outputStream).getHeaders().getContentLength().longValue();
            this.bytesDownloaded = this.mediaContentLength;
            updateStateAndNotifyListener(DownloadState.MEDIA_COMPLETE);
            return;
        }
        while (true) {
            long currentRequestLastBytePos = (this.bytesDownloaded + ((long) this.chunkSize)) - 1;
            if (this.lastBytePos != -1) {
                currentRequestLastBytePos = Math.min(this.lastBytePos, currentRequestLastBytePos);
            }
            String contentRange = executeCurrentRequest(currentRequestLastBytePos, requestUrl, requestHeaders, outputStream).getHeaders().getContentRange();
            long nextByteIndex = getNextByteIndex(contentRange);
            setMediaContentLength(contentRange);
            if (this.mediaContentLength <= nextByteIndex) {
                this.bytesDownloaded = this.mediaContentLength;
                updateStateAndNotifyListener(DownloadState.MEDIA_COMPLETE);
                return;
            }
            this.bytesDownloaded = nextByteIndex;
            updateStateAndNotifyListener(DownloadState.MEDIA_IN_PROGRESS);
        }
    }

    private HttpResponse executeCurrentRequest(long currentRequestLastBytePos, GenericUrl requestUrl, HttpHeaders requestHeaders, OutputStream outputStream) throws IOException {
        HttpRequest request = this.requestFactory.buildGetRequest(requestUrl);
        if (requestHeaders != null) {
            request.getHeaders().putAll(requestHeaders);
        }
        if (!(this.bytesDownloaded == 0 && currentRequestLastBytePos == -1)) {
            StringBuilder rangeHeader = new StringBuilder();
            rangeHeader.append("bytes=").append(this.bytesDownloaded).append("-");
            if (currentRequestLastBytePos != -1) {
                rangeHeader.append(currentRequestLastBytePos);
            }
            request.getHeaders().setRange(rangeHeader.toString());
        }
        HttpResponse response = request.execute();
        try {
            IOUtils.copy(response.getContent(), outputStream);
            return response;
        } finally {
            response.disconnect();
        }
    }

    private long getNextByteIndex(String rangeHeader) {
        if (rangeHeader == null) {
            return 0;
        }
        return Long.parseLong(rangeHeader.substring(rangeHeader.indexOf(45) + 1, rangeHeader.indexOf(47))) + 1;
    }

    public MediaHttpDownloader setBytesDownloaded(long bytesDownloaded) {
        Preconditions.checkArgument(bytesDownloaded >= 0);
        this.bytesDownloaded = bytesDownloaded;
        return this;
    }

    public MediaHttpDownloader setContentRange(long firstBytePos, int lastBytePos) {
        Preconditions.checkArgument(((long) lastBytePos) >= firstBytePos);
        setBytesDownloaded(firstBytePos);
        this.lastBytePos = (long) lastBytePos;
        return this;
    }

    private void setMediaContentLength(String rangeHeader) {
        if (rangeHeader != null && this.mediaContentLength == 0) {
            this.mediaContentLength = Long.parseLong(rangeHeader.substring(rangeHeader.indexOf(47) + 1));
        }
    }

    public boolean isDirectDownloadEnabled() {
        return this.directDownloadEnabled;
    }

    public MediaHttpDownloader setDirectDownloadEnabled(boolean directDownloadEnabled) {
        this.directDownloadEnabled = directDownloadEnabled;
        return this;
    }

    public MediaHttpDownloader setProgressListener(MediaHttpDownloaderProgressListener progressListener) {
        this.progressListener = progressListener;
        return this;
    }

    public MediaHttpDownloaderProgressListener getProgressListener() {
        return this.progressListener;
    }

    public HttpTransport getTransport() {
        return this.transport;
    }

    public MediaHttpDownloader setChunkSize(int chunkSize) {
        boolean z = chunkSize > 0 && chunkSize <= MAXIMUM_CHUNK_SIZE;
        Preconditions.checkArgument(z);
        this.chunkSize = chunkSize;
        return this;
    }

    public int getChunkSize() {
        return this.chunkSize;
    }

    public long getNumBytesDownloaded() {
        return this.bytesDownloaded;
    }

    public long getLastBytePosition() {
        return this.lastBytePos;
    }

    private void updateStateAndNotifyListener(DownloadState downloadState) throws IOException {
        this.downloadState = downloadState;
        if (this.progressListener != null) {
            this.progressListener.progressChanged(this);
        }
    }

    public DownloadState getDownloadState() {
        return this.downloadState;
    }

    public double getProgress() {
        return this.mediaContentLength == 0 ? 0.0d : ((double) this.bytesDownloaded) / ((double) this.mediaContentLength);
    }
}
