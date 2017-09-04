package com.google.api.client.googleapis.media;

import com.google.api.client.googleapis.MethodOverride;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.EmptyContent;
import com.google.api.client.http.GZipEncoding;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.MultipartContent;
import com.google.api.client.util.Beta;
import com.google.api.client.util.ByteStreams;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sleeper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public final class MediaHttpUploader {
    public static final String CONTENT_LENGTH_HEADER = "X-Upload-Content-Length";
    public static final String CONTENT_TYPE_HEADER = "X-Upload-Content-Type";
    public static final int DEFAULT_CHUNK_SIZE = 10485760;
    private static final int KB = 1024;
    static final int MB = 1048576;
    public static final int MINIMUM_CHUNK_SIZE = 262144;
    private Byte cachedByte;
    private int chunkSize = DEFAULT_CHUNK_SIZE;
    private InputStream contentInputStream;
    private int currentChunkLength;
    private HttpRequest currentRequest;
    private byte[] currentRequestContentBuffer;
    private boolean directUploadEnabled;
    private boolean disableGZipContent;
    private HttpHeaders initiationHeaders = new HttpHeaders();
    private String initiationRequestMethod = HttpMethods.POST;
    private boolean isMediaContentLengthCalculated;
    private final AbstractInputStreamContent mediaContent;
    private long mediaContentLength;
    String mediaContentLengthStr = "*";
    private HttpContent metadata;
    private MediaHttpUploaderProgressListener progressListener;
    private final HttpRequestFactory requestFactory;
    Sleeper sleeper = Sleeper.DEFAULT;
    private long totalBytesClientSent;
    private long totalBytesServerReceived;
    private final HttpTransport transport;
    private UploadState uploadState = UploadState.NOT_STARTED;

    public enum UploadState {
        NOT_STARTED,
        INITIATION_STARTED,
        INITIATION_COMPLETE,
        MEDIA_IN_PROGRESS,
        MEDIA_COMPLETE
    }

    private com.google.api.client.http.HttpResponse resumableUpload(com.google.api.client.http.GenericUrl r21) throws java.io.IOException {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:854)
	at java.util.HashMap$KeyIterator.next(HashMap.java:885)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:286)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:173)
*/
        /*
        r20 = this;
        r10 = r20.executeUploadInitiation(r21);
        r18 = r10.isSuccessStatusCode();
        if (r18 != 0) goto L_0x000c;
    L_0x000a:
        r11 = r10;
    L_0x000b:
        return r11;
    L_0x000c:
        r16 = new com.google.api.client.http.GenericUrl;	 Catch:{ all -> 0x00ca }
        r18 = r10.getHeaders();	 Catch:{ all -> 0x00ca }
        r18 = r18.getLocation();	 Catch:{ all -> 0x00ca }
        r0 = r16;	 Catch:{ all -> 0x00ca }
        r1 = r18;	 Catch:{ all -> 0x00ca }
        r0.<init>(r1);	 Catch:{ all -> 0x00ca }
        r10.disconnect();
        r0 = r20;
        r0 = r0.mediaContent;
        r18 = r0;
        r18 = r18.getInputStream();
        r0 = r18;
        r1 = r20;
        r1.contentInputStream = r0;
        r0 = r20;
        r0 = r0.contentInputStream;
        r18 = r0;
        r18 = r18.markSupported();
        if (r18 != 0) goto L_0x0053;
    L_0x003c:
        r18 = r20.isMediaLengthKnown();
        if (r18 == 0) goto L_0x0053;
    L_0x0042:
        r18 = new java.io.BufferedInputStream;
        r0 = r20;
        r0 = r0.contentInputStream;
        r19 = r0;
        r18.<init>(r19);
        r0 = r18;
        r1 = r20;
        r1.contentInputStream = r0;
    L_0x0053:
        r0 = r20;
        r0 = r0.requestFactory;
        r18 = r0;
        r19 = 0;
        r0 = r18;
        r1 = r16;
        r2 = r19;
        r18 = r0.buildPutRequest(r1, r2);
        r0 = r18;
        r1 = r20;
        r1.currentRequest = r0;
        r20.setContentAndHeadersOnCurrentRequest();
        r18 = new com.google.api.client.googleapis.media.MediaUploadErrorHandler;
        r0 = r20;
        r0 = r0.currentRequest;
        r19 = r0;
        r0 = r18;
        r1 = r20;
        r2 = r19;
        r0.<init>(r1, r2);
        r18 = r20.isMediaLengthKnown();
        if (r18 == 0) goto L_0x00cf;
    L_0x0085:
        r0 = r20;
        r0 = r0.currentRequest;
        r18 = r0;
        r0 = r20;
        r1 = r18;
        r11 = r0.executeCurrentRequestWithoutGZip(r1);
    L_0x0093:
        r14 = 0;
        r18 = r11.isSuccessStatusCode();	 Catch:{ all -> 0x0197 }
        if (r18 == 0) goto L_0x00de;	 Catch:{ all -> 0x0197 }
    L_0x009a:
        r18 = r20.getMediaContentLength();	 Catch:{ all -> 0x0197 }
        r0 = r18;	 Catch:{ all -> 0x0197 }
        r2 = r20;	 Catch:{ all -> 0x0197 }
        r2.totalBytesServerReceived = r0;	 Catch:{ all -> 0x0197 }
        r0 = r20;	 Catch:{ all -> 0x0197 }
        r0 = r0.mediaContent;	 Catch:{ all -> 0x0197 }
        r18 = r0;	 Catch:{ all -> 0x0197 }
        r18 = r18.getCloseInputStream();	 Catch:{ all -> 0x0197 }
        if (r18 == 0) goto L_0x00b9;	 Catch:{ all -> 0x0197 }
    L_0x00b0:
        r0 = r20;	 Catch:{ all -> 0x0197 }
        r0 = r0.contentInputStream;	 Catch:{ all -> 0x0197 }
        r18 = r0;	 Catch:{ all -> 0x0197 }
        r18.close();	 Catch:{ all -> 0x0197 }
    L_0x00b9:
        r18 = com.google.api.client.googleapis.media.MediaHttpUploader.UploadState.MEDIA_COMPLETE;	 Catch:{ all -> 0x0197 }
        r0 = r20;	 Catch:{ all -> 0x0197 }
        r1 = r18;	 Catch:{ all -> 0x0197 }
        r0.updateStateAndNotifyListener(r1);	 Catch:{ all -> 0x0197 }
        r14 = 1;
        if (r14 != 0) goto L_0x000b;
    L_0x00c5:
        r11.disconnect();
        goto L_0x000b;
    L_0x00ca:
        r18 = move-exception;
        r10.disconnect();
        throw r18;
    L_0x00cf:
        r0 = r20;
        r0 = r0.currentRequest;
        r18 = r0;
        r0 = r20;
        r1 = r18;
        r11 = r0.executeCurrentRequest(r1);
        goto L_0x0093;
    L_0x00de:
        r18 = r11.getStatusCode();	 Catch:{ all -> 0x0197 }
        r19 = 308; // 0x134 float:4.32E-43 double:1.52E-321;
        r0 = r18;
        r1 = r19;
        if (r0 == r1) goto L_0x00f2;
    L_0x00ea:
        r14 = 1;
        if (r14 != 0) goto L_0x000b;
    L_0x00ed:
        r11.disconnect();
        goto L_0x000b;
    L_0x00f2:
        r18 = r11.getHeaders();	 Catch:{ all -> 0x0197 }
        r15 = r18.getLocation();	 Catch:{ all -> 0x0197 }
        if (r15 == 0) goto L_0x0105;	 Catch:{ all -> 0x0197 }
    L_0x00fc:
        r17 = new com.google.api.client.http.GenericUrl;	 Catch:{ all -> 0x0197 }
        r0 = r17;	 Catch:{ all -> 0x0197 }
        r0.<init>(r15);	 Catch:{ all -> 0x0197 }
        r16 = r17;	 Catch:{ all -> 0x0197 }
    L_0x0105:
        r18 = r11.getHeaders();	 Catch:{ all -> 0x0197 }
        r18 = r18.getRange();	 Catch:{ all -> 0x0197 }
        r0 = r20;	 Catch:{ all -> 0x0197 }
        r1 = r18;	 Catch:{ all -> 0x0197 }
        r12 = r0.getNextByteIndex(r1);	 Catch:{ all -> 0x0197 }
        r0 = r20;	 Catch:{ all -> 0x0197 }
        r0 = r0.totalBytesServerReceived;	 Catch:{ all -> 0x0197 }
        r18 = r0;	 Catch:{ all -> 0x0197 }
        r8 = r12 - r18;	 Catch:{ all -> 0x0197 }
        r18 = 0;	 Catch:{ all -> 0x0197 }
        r18 = (r8 > r18 ? 1 : (r8 == r18 ? 0 : -1));	 Catch:{ all -> 0x0197 }
        if (r18 < 0) goto L_0x0182;	 Catch:{ all -> 0x0197 }
    L_0x0123:
        r0 = r20;	 Catch:{ all -> 0x0197 }
        r0 = r0.currentChunkLength;	 Catch:{ all -> 0x0197 }
        r18 = r0;	 Catch:{ all -> 0x0197 }
        r0 = r18;	 Catch:{ all -> 0x0197 }
        r0 = (long) r0;	 Catch:{ all -> 0x0197 }
        r18 = r0;	 Catch:{ all -> 0x0197 }
        r18 = (r8 > r18 ? 1 : (r8 == r18 ? 0 : -1));	 Catch:{ all -> 0x0197 }
        if (r18 > 0) goto L_0x0182;	 Catch:{ all -> 0x0197 }
    L_0x0132:
        r18 = 1;	 Catch:{ all -> 0x0197 }
    L_0x0134:
        com.google.api.client.util.Preconditions.checkState(r18);	 Catch:{ all -> 0x0197 }
        r0 = r20;	 Catch:{ all -> 0x0197 }
        r0 = r0.currentChunkLength;	 Catch:{ all -> 0x0197 }
        r18 = r0;	 Catch:{ all -> 0x0197 }
        r0 = r18;	 Catch:{ all -> 0x0197 }
        r0 = (long) r0;	 Catch:{ all -> 0x0197 }
        r18 = r0;	 Catch:{ all -> 0x0197 }
        r6 = r18 - r8;	 Catch:{ all -> 0x0197 }
        r18 = r20.isMediaLengthKnown();	 Catch:{ all -> 0x0197 }
        if (r18 == 0) goto L_0x0188;	 Catch:{ all -> 0x0197 }
    L_0x014a:
        r18 = 0;	 Catch:{ all -> 0x0197 }
        r18 = (r6 > r18 ? 1 : (r6 == r18 ? 0 : -1));	 Catch:{ all -> 0x0197 }
        if (r18 <= 0) goto L_0x016e;	 Catch:{ all -> 0x0197 }
    L_0x0150:
        r0 = r20;	 Catch:{ all -> 0x0197 }
        r0 = r0.contentInputStream;	 Catch:{ all -> 0x0197 }
        r18 = r0;	 Catch:{ all -> 0x0197 }
        r18.reset();	 Catch:{ all -> 0x0197 }
        r0 = r20;	 Catch:{ all -> 0x0197 }
        r0 = r0.contentInputStream;	 Catch:{ all -> 0x0197 }
        r18 = r0;	 Catch:{ all -> 0x0197 }
        r0 = r18;	 Catch:{ all -> 0x0197 }
        r4 = r0.skip(r8);	 Catch:{ all -> 0x0197 }
        r18 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1));	 Catch:{ all -> 0x0197 }
        if (r18 != 0) goto L_0x0185;	 Catch:{ all -> 0x0197 }
    L_0x0169:
        r18 = 1;	 Catch:{ all -> 0x0197 }
    L_0x016b:
        com.google.api.client.util.Preconditions.checkState(r18);	 Catch:{ all -> 0x0197 }
    L_0x016e:
        r0 = r20;	 Catch:{ all -> 0x0197 }
        r0.totalBytesServerReceived = r12;	 Catch:{ all -> 0x0197 }
        r18 = com.google.api.client.googleapis.media.MediaHttpUploader.UploadState.MEDIA_IN_PROGRESS;	 Catch:{ all -> 0x0197 }
        r0 = r20;	 Catch:{ all -> 0x0197 }
        r1 = r18;	 Catch:{ all -> 0x0197 }
        r0.updateStateAndNotifyListener(r1);	 Catch:{ all -> 0x0197 }
        if (r14 != 0) goto L_0x0053;
    L_0x017d:
        r11.disconnect();
        goto L_0x0053;
    L_0x0182:
        r18 = 0;
        goto L_0x0134;
    L_0x0185:
        r18 = 0;
        goto L_0x016b;
    L_0x0188:
        r18 = 0;
        r18 = (r6 > r18 ? 1 : (r6 == r18 ? 0 : -1));
        if (r18 != 0) goto L_0x016e;
    L_0x018e:
        r18 = 0;
        r0 = r18;	 Catch:{ all -> 0x0197 }
        r1 = r20;	 Catch:{ all -> 0x0197 }
        r1.currentRequestContentBuffer = r0;	 Catch:{ all -> 0x0197 }
        goto L_0x016e;
    L_0x0197:
        r18 = move-exception;
        if (r14 != 0) goto L_0x019d;
    L_0x019a:
        r11.disconnect();
    L_0x019d:
        throw r18;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.api.client.googleapis.media.MediaHttpUploader.resumableUpload(com.google.api.client.http.GenericUrl):com.google.api.client.http.HttpResponse");
    }

    public MediaHttpUploader(AbstractInputStreamContent mediaContent, HttpTransport transport, HttpRequestInitializer httpRequestInitializer) {
        this.mediaContent = (AbstractInputStreamContent) Preconditions.checkNotNull(mediaContent);
        this.transport = (HttpTransport) Preconditions.checkNotNull(transport);
        this.requestFactory = httpRequestInitializer == null ? transport.createRequestFactory() : transport.createRequestFactory(httpRequestInitializer);
    }

    public HttpResponse upload(GenericUrl initiationRequestUrl) throws IOException {
        Preconditions.checkArgument(this.uploadState == UploadState.NOT_STARTED);
        if (this.directUploadEnabled) {
            return directUpload(initiationRequestUrl);
        }
        return resumableUpload(initiationRequestUrl);
    }

    private HttpResponse directUpload(GenericUrl initiationRequestUrl) throws IOException {
        updateStateAndNotifyListener(UploadState.MEDIA_IN_PROGRESS);
        HttpContent content = this.mediaContent;
        if (this.metadata != null) {
            content = new MultipartContent().setContentParts(Arrays.asList(new HttpContent[]{this.metadata, this.mediaContent}));
            initiationRequestUrl.put("uploadType", (Object) "multipart");
        } else {
            initiationRequestUrl.put("uploadType", (Object) "media");
        }
        HttpRequest request = this.requestFactory.buildRequest(this.initiationRequestMethod, initiationRequestUrl, content);
        request.getHeaders().putAll(this.initiationHeaders);
        HttpResponse response = executeCurrentRequest(request);
        boolean responseProcessed = false;
        try {
            if (isMediaLengthKnown()) {
                this.totalBytesServerReceived = getMediaContentLength();
            }
            updateStateAndNotifyListener(UploadState.MEDIA_COMPLETE);
            responseProcessed = true;
            return response;
        } finally {
            if (!responseProcessed) {
                response.disconnect();
            }
        }
    }

    private boolean isMediaLengthKnown() throws IOException {
        return getMediaContentLength() >= 0;
    }

    private long getMediaContentLength() throws IOException {
        if (!this.isMediaContentLengthCalculated) {
            this.mediaContentLength = this.mediaContent.getLength();
            this.isMediaContentLengthCalculated = true;
        }
        return this.mediaContentLength;
    }

    private HttpResponse executeUploadInitiation(GenericUrl initiationRequestUrl) throws IOException {
        updateStateAndNotifyListener(UploadState.INITIATION_STARTED);
        initiationRequestUrl.put("uploadType", (Object) "resumable");
        HttpRequest request = this.requestFactory.buildRequest(this.initiationRequestMethod, initiationRequestUrl, this.metadata == null ? new EmptyContent() : this.metadata);
        this.initiationHeaders.set(CONTENT_TYPE_HEADER, this.mediaContent.getType());
        if (isMediaLengthKnown()) {
            this.initiationHeaders.set(CONTENT_LENGTH_HEADER, Long.valueOf(getMediaContentLength()));
        }
        request.getHeaders().putAll(this.initiationHeaders);
        HttpResponse response = executeCurrentRequest(request);
        boolean notificationCompleted = false;
        try {
            updateStateAndNotifyListener(UploadState.INITIATION_COMPLETE);
            notificationCompleted = true;
            return response;
        } finally {
            if (!notificationCompleted) {
                response.disconnect();
            }
        }
    }

    private HttpResponse executeCurrentRequestWithoutGZip(HttpRequest request) throws IOException {
        new MethodOverride().intercept(request);
        request.setThrowExceptionOnExecuteError(false);
        return request.execute();
    }

    private HttpResponse executeCurrentRequest(HttpRequest request) throws IOException {
        if (!(this.disableGZipContent || (request.getContent() instanceof EmptyContent))) {
            request.setEncoding(new GZipEncoding());
        }
        return executeCurrentRequestWithoutGZip(request);
    }

    private void setContentAndHeadersOnCurrentRequest() throws IOException {
        int blockSize;
        AbstractInputStreamContent contentChunk;
        if (isMediaLengthKnown()) {
            blockSize = (int) Math.min((long) this.chunkSize, getMediaContentLength() - this.totalBytesServerReceived);
        } else {
            blockSize = this.chunkSize;
        }
        int actualBlockSize = blockSize;
        if (isMediaLengthKnown()) {
            this.contentInputStream.mark(blockSize);
            contentChunk = new InputStreamContent(this.mediaContent.getType(), ByteStreams.limit(this.contentInputStream, (long) blockSize)).setRetrySupported(true).setLength((long) blockSize).setCloseInputStream(false);
            this.mediaContentLengthStr = String.valueOf(getMediaContentLength());
        } else {
            int bytesAllowedToRead;
            int copyBytes = 0;
            if (this.currentRequestContentBuffer == null) {
                if (this.cachedByte == null) {
                    bytesAllowedToRead = blockSize + 1;
                } else {
                    bytesAllowedToRead = blockSize;
                }
                this.currentRequestContentBuffer = new byte[(blockSize + 1)];
                if (this.cachedByte != null) {
                    this.currentRequestContentBuffer[0] = this.cachedByte.byteValue();
                }
            } else {
                copyBytes = (int) (this.totalBytesClientSent - this.totalBytesServerReceived);
                System.arraycopy(this.currentRequestContentBuffer, this.currentChunkLength - copyBytes, this.currentRequestContentBuffer, 0, copyBytes);
                if (this.cachedByte != null) {
                    this.currentRequestContentBuffer[copyBytes] = this.cachedByte.byteValue();
                }
                bytesAllowedToRead = blockSize - copyBytes;
            }
            int actualBytesRead = ByteStreams.read(this.contentInputStream, this.currentRequestContentBuffer, (blockSize + 1) - bytesAllowedToRead, bytesAllowedToRead);
            if (actualBytesRead < bytesAllowedToRead) {
                actualBlockSize = copyBytes + Math.max(0, actualBytesRead);
                if (this.cachedByte != null) {
                    actualBlockSize++;
                    this.cachedByte = null;
                }
                if (this.mediaContentLengthStr.equals("*")) {
                    this.mediaContentLengthStr = String.valueOf(this.totalBytesServerReceived + ((long) actualBlockSize));
                }
            } else {
                this.cachedByte = Byte.valueOf(this.currentRequestContentBuffer[blockSize]);
            }
            contentChunk = new ByteArrayContent(this.mediaContent.getType(), this.currentRequestContentBuffer, 0, actualBlockSize);
            this.totalBytesClientSent = this.totalBytesServerReceived + ((long) actualBlockSize);
        }
        this.currentChunkLength = actualBlockSize;
        this.currentRequest.setContent(contentChunk);
        if (actualBlockSize == 0) {
            this.currentRequest.getHeaders().setContentRange("bytes */" + this.mediaContentLengthStr);
        } else {
            this.currentRequest.getHeaders().setContentRange("bytes " + this.totalBytesServerReceived + "-" + ((this.totalBytesServerReceived + ((long) actualBlockSize)) - 1) + "/" + this.mediaContentLengthStr);
        }
    }

    @Beta
    void serverErrorCallback() throws IOException {
        Preconditions.checkNotNull(this.currentRequest, "The current request should not be null");
        this.currentRequest.setContent(new EmptyContent());
        this.currentRequest.getHeaders().setContentRange("bytes */" + this.mediaContentLengthStr);
    }

    private long getNextByteIndex(String rangeHeader) {
        if (rangeHeader == null) {
            return 0;
        }
        return Long.parseLong(rangeHeader.substring(rangeHeader.indexOf(45) + 1)) + 1;
    }

    public HttpContent getMetadata() {
        return this.metadata;
    }

    public MediaHttpUploader setMetadata(HttpContent metadata) {
        this.metadata = metadata;
        return this;
    }

    public HttpContent getMediaContent() {
        return this.mediaContent;
    }

    public HttpTransport getTransport() {
        return this.transport;
    }

    public MediaHttpUploader setDirectUploadEnabled(boolean directUploadEnabled) {
        this.directUploadEnabled = directUploadEnabled;
        return this;
    }

    public boolean isDirectUploadEnabled() {
        return this.directUploadEnabled;
    }

    public MediaHttpUploader setProgressListener(MediaHttpUploaderProgressListener progressListener) {
        this.progressListener = progressListener;
        return this;
    }

    public MediaHttpUploaderProgressListener getProgressListener() {
        return this.progressListener;
    }

    public MediaHttpUploader setChunkSize(int chunkSize) {
        boolean z = chunkSize > 0 && chunkSize % MINIMUM_CHUNK_SIZE == 0;
        Preconditions.checkArgument(z, "chunkSize must be a positive multiple of 262144.");
        this.chunkSize = chunkSize;
        return this;
    }

    public int getChunkSize() {
        return this.chunkSize;
    }

    public boolean getDisableGZipContent() {
        return this.disableGZipContent;
    }

    public MediaHttpUploader setDisableGZipContent(boolean disableGZipContent) {
        this.disableGZipContent = disableGZipContent;
        return this;
    }

    public Sleeper getSleeper() {
        return this.sleeper;
    }

    public MediaHttpUploader setSleeper(Sleeper sleeper) {
        this.sleeper = sleeper;
        return this;
    }

    public String getInitiationRequestMethod() {
        return this.initiationRequestMethod;
    }

    public MediaHttpUploader setInitiationRequestMethod(String initiationRequestMethod) {
        boolean z = initiationRequestMethod.equals(HttpMethods.POST) || initiationRequestMethod.equals(HttpMethods.PUT) || initiationRequestMethod.equals(HttpMethods.PATCH);
        Preconditions.checkArgument(z);
        this.initiationRequestMethod = initiationRequestMethod;
        return this;
    }

    public MediaHttpUploader setInitiationHeaders(HttpHeaders initiationHeaders) {
        this.initiationHeaders = initiationHeaders;
        return this;
    }

    public HttpHeaders getInitiationHeaders() {
        return this.initiationHeaders;
    }

    public long getNumBytesUploaded() {
        return this.totalBytesServerReceived;
    }

    private void updateStateAndNotifyListener(UploadState uploadState) throws IOException {
        this.uploadState = uploadState;
        if (this.progressListener != null) {
            this.progressListener.progressChanged(this);
        }
    }

    public UploadState getUploadState() {
        return this.uploadState;
    }

    public double getProgress() throws IOException {
        Preconditions.checkArgument(isMediaLengthKnown(), "Cannot call getProgress() if the specified AbstractInputStreamContent has no content length. Use  getNumBytesUploaded() to denote progress instead.");
        return getMediaContentLength() == 0 ? 0.0d : ((double) this.totalBytesServerReceived) / ((double) getMediaContentLength());
    }
}
