package com.dropbox.core.v1;

import com.box.androidsdk.content.models.BoxList;
import com.dropbox.core.BadRequestException;
import com.dropbox.core.BadResponseException;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxHost;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxRequestUtil;
import com.dropbox.core.DbxRequestUtil.RequestMaker;
import com.dropbox.core.DbxRequestUtil.ResponseHandler;
import com.dropbox.core.DbxStreamWriter;
import com.dropbox.core.DbxStreamWriter.ByteArrayCopier;
import com.dropbox.core.DbxStreamWriter.InputStreamCopier;
import com.dropbox.core.NetworkIOException;
import com.dropbox.core.NoThrowOutputStream;
import com.dropbox.core.NoThrowOutputStream.HiddenException;
import com.dropbox.core.http.HttpRequestor.Header;
import com.dropbox.core.http.HttpRequestor.Response;
import com.dropbox.core.json.JsonArrayReader;
import com.dropbox.core.json.JsonDateReader;
import com.dropbox.core.json.JsonReadException;
import com.dropbox.core.json.JsonReader;
import com.dropbox.core.util.Collector;
import com.dropbox.core.util.Collector.ArrayListCollector;
import com.dropbox.core.util.Collector.NullSkipper;
import com.dropbox.core.util.CountingOutputStream;
import com.dropbox.core.util.DumpWriter;
import com.dropbox.core.util.Dumpable;
import com.dropbox.core.util.IOUtil;
import com.dropbox.core.util.IOUtil.ReadException;
import com.dropbox.core.util.IOUtil.WriteException;
import com.dropbox.core.util.LangUtil;
import com.dropbox.core.util.Maybe;
import com.dropbox.core.util.StringUtil;
import com.dropbox.core.v1.DbxDelta.Reader;
import com.dropbox.core.v1.DbxDeltaC.Entry;
import com.dropbox.core.v1.DbxEntry.File;
import com.dropbox.core.v1.DbxEntry.Folder;
import com.dropbox.core.v1.DbxEntry.WithChildren;
import com.dropbox.core.v1.DbxEntry.WithChildrenC;
import com.dropbox.core.v1.DbxEntry.WithChildrenC.ReaderMaybeDeleted;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.google.api.client.http.HttpStatusCodes;
import in.sinew.enpassengine.Attachment;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class DbxClientV1 {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final int ChunkedUploadChunkSize = 4194304;
    private static final long ChunkedUploadThreshold = 8388608;
    private static JsonReader<String> LatestCursorReader = new JsonReader<String>() {
        public String read(JsonParser parser) throws IOException, JsonReadException {
            JsonLocation top = JsonReader.expectObjectStart(parser);
            String cursorId = null;
            while (parser.getCurrentToken() == JsonToken.FIELD_NAME) {
                String fieldName = parser.getCurrentName();
                parser.nextToken();
                try {
                    if (fieldName.equals("cursor")) {
                        cursorId = (String) JsonReader.StringReader.readField(parser, fieldName, cursorId);
                    } else {
                        JsonReader.skipValue(parser);
                    }
                } catch (JsonReadException ex) {
                    throw ex.addFieldContext(fieldName);
                }
            }
            JsonReader.expectObjectEnd(parser);
            if (cursorId != null) {
                return cursorId;
            }
            throw new JsonReadException("missing field \"cursor\"", top);
        }
    };
    public static final String USER_AGENT_ID = "Dropbox-Java-SDK";
    private final String accessToken;
    private final DbxHost host;
    private final DbxRequestConfig requestConfig;

    private final class ChunkedUploadOutputStream extends OutputStream {
        static final /* synthetic */ boolean $assertionsDisabled = (!DbxClientV1.class.desiredAssertionStatus() ? true : DbxClientV1.$assertionsDisabled);
        private final byte[] chunk;
        private int chunkPos;
        private String uploadId;
        private long uploadOffset;

        private ChunkedUploadOutputStream(int chunkSize) {
            this.chunkPos = 0;
            this.chunk = new byte[chunkSize];
            this.chunkPos = 0;
        }

        public void write(int i) throws IOException {
            byte[] bArr = this.chunk;
            int i2 = this.chunkPos;
            this.chunkPos = i2 + 1;
            bArr[i2] = (byte) i;
            try {
                finishChunkIfNecessary();
            } catch (DbxException ex) {
                throw new IODbxException(ex);
            }
        }

        private void finishChunkIfNecessary() throws DbxException {
            if (!$assertionsDisabled && this.chunkPos > this.chunk.length) {
                throw new AssertionError();
            } else if (this.chunkPos == this.chunk.length) {
                finishChunk();
            }
        }

        private void finishChunk() throws DbxException {
            if (this.chunkPos != 0) {
                if (this.uploadId == null) {
                    this.uploadId = (String) DbxRequestUtil.runAndRetry(3, new RequestMaker<String, RuntimeException>() {
                        public String run() throws DbxException {
                            return DbxClientV1.this.chunkedUploadFirst(ChunkedUploadOutputStream.this.chunk, 0, ChunkedUploadOutputStream.this.chunkPos);
                        }
                    });
                    this.uploadOffset = (long) this.chunkPos;
                } else {
                    long expectedOffset;
                    final String uploadId = this.uploadId;
                    int arrayOffset = 0;
                    while (true) {
                        final int arrayOffsetFinal = arrayOffset;
                        long correctedOffset = ((Long) DbxRequestUtil.runAndRetry(3, new RequestMaker<Long, RuntimeException>() {
                            public Long run() throws DbxException {
                                return Long.valueOf(DbxClientV1.this.chunkedUploadAppend(uploadId, ChunkedUploadOutputStream.this.uploadOffset, ChunkedUploadOutputStream.this.chunk, arrayOffsetFinal, ChunkedUploadOutputStream.this.chunkPos - arrayOffsetFinal));
                            }
                        })).longValue();
                        expectedOffset = this.uploadOffset + ((long) this.chunkPos);
                        if (correctedOffset == -1) {
                            break;
                        }
                        arrayOffset += (int) (correctedOffset - this.uploadOffset);
                    }
                    this.uploadOffset = expectedOffset;
                }
                this.chunkPos = 0;
            }
        }

        public void write(byte[] bytes, int offset, int length) throws IOException {
            int inputEnd = offset + length;
            int inputPos = offset;
            while (inputPos < inputEnd) {
                int bytesToCopy = Math.min(inputEnd - inputPos, this.chunk.length - this.chunkPos);
                System.arraycopy(bytes, inputPos, this.chunk, this.chunkPos, bytesToCopy);
                this.chunkPos += bytesToCopy;
                inputPos += bytesToCopy;
                try {
                    finishChunkIfNecessary();
                } catch (DbxException ex) {
                    throw new IODbxException(ex);
                }
            }
        }

        public void close() throws IOException {
        }
    }

    private static final class ChunkedUploadState extends Dumpable {
        public static final JsonReader<ChunkedUploadState> Reader = new JsonReader<ChunkedUploadState>() {
            public ChunkedUploadState read(JsonParser parser) throws IOException, JsonReadException {
                JsonLocation top = JsonReader.expectObjectStart(parser);
                String uploadId = null;
                long bytesComplete = -1;
                while (parser.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String fieldName = parser.getCurrentName();
                    parser.nextToken();
                    try {
                        if (fieldName.equals("upload_id")) {
                            uploadId = (String) JsonReader.StringReader.readField(parser, fieldName, uploadId);
                        } else if (fieldName.equals(BoxList.FIELD_OFFSET)) {
                            bytesComplete = JsonReader.readUnsignedLongField(parser, fieldName, bytesComplete);
                        } else {
                            JsonReader.skipValue(parser);
                        }
                    } catch (JsonReadException ex) {
                        throw ex.addFieldContext(fieldName);
                    }
                }
                JsonReader.expectObjectEnd(parser);
                if (uploadId == null) {
                    throw new JsonReadException("missing field \"upload_id\"", top);
                } else if (bytesComplete != -1) {
                    return new ChunkedUploadState(uploadId, bytesComplete);
                } else {
                    throw new JsonReadException("missing field \"offset\"", top);
                }
            }
        };
        public final long offset;
        public final String uploadId;

        public ChunkedUploadState(String uploadId, long offset) {
            if (uploadId == null) {
                throw new IllegalArgumentException("'uploadId' can't be null");
            } else if (uploadId.length() == 0) {
                throw new IllegalArgumentException("'uploadId' can't be empty");
            } else if (offset < 0) {
                throw new IllegalArgumentException("'offset' can't be negative");
            } else {
                this.uploadId = uploadId;
                this.offset = offset;
            }
        }

        protected void dumpFields(DumpWriter w) {
            w.f("uploadId").v(this.uploadId);
            w.f(BoxList.FIELD_OFFSET).v(this.offset);
        }
    }

    public static abstract class Uploader {
        public abstract void abort();

        public abstract void close();

        public abstract File finish() throws DbxException;

        public abstract OutputStream getBody();
    }

    private final class ChunkedUploader extends Uploader {
        private final ChunkedUploadOutputStream body;
        private final long numBytes;
        private final String targetPath;
        private final DbxWriteMode writeMode;

        private ChunkedUploader(String targetPath, DbxWriteMode writeMode, long numBytes, ChunkedUploadOutputStream body) {
            this.targetPath = targetPath;
            this.writeMode = writeMode;
            this.numBytes = numBytes;
            this.body = body;
        }

        public OutputStream getBody() {
            return this.body;
        }

        public void abort() {
        }

        public File finish() throws DbxException {
            if (this.body.uploadId == null) {
                return DbxClientV1.this.uploadFileSingle(this.targetPath, this.writeMode, (long) this.body.chunkPos, new ByteArrayCopier(this.body.chunk, 0, this.body.chunkPos));
            }
            final String uploadId = this.body.uploadId;
            this.body.finishChunk();
            if (this.numBytes == -1 || this.numBytes == this.body.uploadOffset) {
                return (File) DbxRequestUtil.runAndRetry(3, new RequestMaker<File, RuntimeException>() {
                    public File run() throws DbxException {
                        return DbxClientV1.this.chunkedUploadFinish(ChunkedUploader.this.targetPath, ChunkedUploader.this.writeMode, uploadId);
                    }
                });
            }
            throw new IllegalStateException("'numBytes' is " + this.numBytes + " but you wrote " + this.body.uploadOffset + " bytes");
        }

        public void close() {
        }
    }

    private static final class CopyRef {
        public static final JsonReader<CopyRef> Reader = new JsonReader<CopyRef>() {
            public CopyRef read(JsonParser parser) throws IOException, JsonReadException {
                JsonLocation top = JsonReader.expectObjectStart(parser);
                String id = null;
                Date expires = null;
                while (parser.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String fieldName = parser.getCurrentName();
                    parser.nextToken();
                    try {
                        if (fieldName.equals("copy_ref")) {
                            id = (String) JsonReader.StringReader.readField(parser, fieldName, id);
                        } else if (fieldName.equals("expires")) {
                            expires = (Date) JsonDateReader.Dropbox.readField(parser, fieldName, expires);
                        } else {
                            JsonReader.skipValue(parser);
                        }
                    } catch (JsonReadException ex) {
                        throw ex.addFieldContext(fieldName);
                    }
                }
                JsonReader.expectObjectEnd(parser);
                if (id == null) {
                    throw new JsonReadException("missing field \"copy_ref\"", top);
                } else if (expires != null) {
                    return new CopyRef(id, expires);
                } else {
                    throw new JsonReadException("missing field \"expires\"", top);
                }
            }
        };
        public final Date expires;
        public final String id;

        private CopyRef(String id, Date expires) {
            this.id = id;
            this.expires = expires;
        }
    }

    public static final class Downloader {
        public final InputStream body;
        public final File metadata;

        public Downloader(File metadata, InputStream body) {
            this.metadata = metadata;
            this.body = body;
        }

        File copyBodyAndClose(OutputStream target) throws DbxException, IOException {
            try {
                IOUtil.copyStreamToStream(this.body, target);
                close();
                return this.metadata;
            } catch (ReadException ex) {
                throw new NetworkIOException(ex.getCause());
            } catch (WriteException ex2) {
                throw ex2.getCause();
            } catch (Throwable th) {
                close();
            }
        }

        public void close() {
            IOUtil.closeInput(this.body);
        }
    }

    public static final class IODbxException extends IOException {
        private static final long serialVersionUID = 0;
        public final DbxException underlying;

        public IODbxException(DbxException underlying) {
            super(underlying);
            this.underlying = underlying;
        }
    }

    private static final class SingleUploader extends Uploader {
        private final CountingOutputStream body;
        private final long claimedBytes;
        private com.dropbox.core.http.HttpRequestor.Uploader httpUploader;

        public SingleUploader(com.dropbox.core.http.HttpRequestor.Uploader httpUploader, long claimedBytes) {
            if (claimedBytes < 0) {
                throw new IllegalArgumentException("'numBytes' must be greater than or equal to 0");
            }
            this.httpUploader = httpUploader;
            this.claimedBytes = claimedBytes;
            this.body = new CountingOutputStream(httpUploader.getBody());
        }

        public OutputStream getBody() {
            return this.body;
        }

        public void abort() {
            if (this.httpUploader == null) {
                throw new IllegalStateException("already called 'finish', 'abort', or 'close'");
            }
            com.dropbox.core.http.HttpRequestor.Uploader p = this.httpUploader;
            this.httpUploader = null;
            p.abort();
        }

        public void close() {
            if (this.httpUploader != null) {
                abort();
            }
        }

        public File finish() throws DbxException {
            if (this.httpUploader == null) {
                throw new IllegalStateException("already called 'finish', 'abort', or 'close'");
            }
            com.dropbox.core.http.HttpRequestor.Uploader u = this.httpUploader;
            this.httpUploader = null;
            try {
                final long bytesWritten = this.body.getBytesWritten();
                if (this.claimedBytes != bytesWritten) {
                    u.abort();
                    throw new IllegalStateException("You said you were going to upload " + this.claimedBytes + " bytes, but you wrote " + bytesWritten + " bytes to the Uploader's 'body' stream.");
                }
                Response response = u.finish();
                u.close();
                return (File) DbxRequestUtil.finishResponse(response, new ResponseHandler<File>() {
                    public File handle(Response response) throws DbxException {
                        if (response.getStatusCode() != HttpStatusCodes.STATUS_CODE_OK) {
                            throw DbxRequestUtil.unexpectedStatus(response);
                        }
                        File f = (File) DbxRequestUtil.readJsonFromResponse(File.Reader, response);
                        if (f.numBytes == bytesWritten) {
                            return f;
                        }
                        throw new BadResponseException(DbxRequestUtil.getRequestId(response), "we uploaded " + bytesWritten + ", but server returned metadata entry with file size " + f.numBytes);
                    }
                });
            } catch (IOException ex) {
                throw new NetworkIOException(ex);
            } catch (Throwable th) {
                u.close();
            }
        }
    }

    static {
        boolean z;
        if (DbxClientV1.class.desiredAssertionStatus()) {
            z = $assertionsDisabled;
        } else {
            z = true;
        }
        $assertionsDisabled = z;
    }

    public DbxClientV1(DbxRequestConfig requestConfig, String accessToken) {
        this(requestConfig, accessToken, DbxHost.DEFAULT);
    }

    public DbxClientV1(DbxRequestConfig requestConfig, String accessToken, DbxHost host) {
        if (requestConfig == null) {
            throw new IllegalArgumentException("'requestConfig' is null");
        } else if (accessToken == null) {
            throw new IllegalArgumentException("'accessToken' is null");
        } else if (host == null) {
            throw new IllegalArgumentException("'host' is null");
        } else {
            this.requestConfig = requestConfig;
            this.accessToken = accessToken;
            this.host = host;
        }
    }

    public DbxRequestConfig getRequestConfig() {
        return this.requestConfig;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public DbxHost getHost() {
        return this.host;
    }

    public DbxEntry getMetadata(String path, boolean includeMediaInfo) throws DbxException {
        String str;
        DbxPathV1.checkArg(BoxMetadataUpdateTask.PATH, path);
        String host = this.host.getApi();
        String apiPath = "1/metadata/auto" + path;
        String[] params = new String[4];
        params[0] = "list";
        params[1] = "false";
        params[2] = "include_media_info";
        if (includeMediaInfo) {
            str = "true";
        } else {
            str = null;
        }
        params[3] = str;
        return (DbxEntry) doGet(host, apiPath, params, null, new ResponseHandler<DbxEntry>() {
            public DbxEntry handle(Response response) throws DbxException {
                if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_NOT_FOUND) {
                    return null;
                }
                if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_OK) {
                    return (DbxEntry) DbxRequestUtil.readJsonFromResponse(DbxEntry.ReaderMaybeDeleted, response);
                }
                throw DbxRequestUtil.unexpectedStatus(response);
            }
        });
    }

    public DbxEntry getMetadata(String path) throws DbxException {
        return getMetadata(path, $assertionsDisabled);
    }

    public WithChildren getMetadataWithChildren(String path, boolean includeMediaInfo) throws DbxException {
        return (WithChildren) getMetadataWithChildrenBase(path, includeMediaInfo, WithChildren.ReaderMaybeDeleted);
    }

    public WithChildren getMetadataWithChildren(String path) throws DbxException {
        return getMetadataWithChildren(path, $assertionsDisabled);
    }

    public <C> WithChildrenC<C> getMetadataWithChildrenC(String path, boolean includeMediaInfo, Collector<DbxEntry, ? extends C> collector) throws DbxException {
        return (WithChildrenC) getMetadataWithChildrenBase(path, includeMediaInfo, new ReaderMaybeDeleted(collector));
    }

    public <C> WithChildrenC<C> getMetadataWithChildrenC(String path, Collector<DbxEntry, ? extends C> collector) throws DbxException {
        return getMetadataWithChildrenC(path, $assertionsDisabled, collector);
    }

    private <T> T getMetadataWithChildrenBase(String path, boolean includeMediaInfo, final JsonReader<? extends T> reader) throws DbxException {
        String str;
        DbxPathV1.checkArg(BoxMetadataUpdateTask.PATH, path);
        String host = this.host.getApi();
        String apiPath = "1/metadata/auto" + path;
        String[] params = new String[6];
        params[0] = "list";
        params[1] = "true";
        params[2] = "file_limit";
        params[3] = "25000";
        params[4] = "include_media_info";
        if (includeMediaInfo) {
            str = "true";
        } else {
            str = null;
        }
        params[5] = str;
        return doGet(host, apiPath, params, null, new ResponseHandler<T>() {
            public T handle(Response response) throws DbxException {
                if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_NOT_FOUND) {
                    return null;
                }
                if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_OK) {
                    return DbxRequestUtil.readJsonFromResponse(reader, response);
                }
                throw DbxRequestUtil.unexpectedStatus(response);
            }
        });
    }

    public Maybe<WithChildren> getMetadataWithChildrenIfChanged(String path, boolean includeMediaInfo, String previousFolderHash) throws DbxException {
        return getMetadataWithChildrenIfChangedBase(path, includeMediaInfo, previousFolderHash, WithChildren.ReaderMaybeDeleted);
    }

    public Maybe<WithChildren> getMetadataWithChildrenIfChanged(String path, String previousFolderHash) throws DbxException {
        return getMetadataWithChildrenIfChanged(path, $assertionsDisabled, previousFolderHash);
    }

    public <C> Maybe<WithChildrenC<C>> getMetadataWithChildrenIfChangedC(String path, boolean includeMediaInfo, String previousFolderHash, Collector<DbxEntry, ? extends C> collector) throws DbxException {
        return getMetadataWithChildrenIfChangedBase(path, includeMediaInfo, previousFolderHash, new ReaderMaybeDeleted(collector));
    }

    public <C> Maybe<WithChildrenC<C>> getMetadataWithChildrenIfChangedC(String path, String previousFolderHash, Collector<DbxEntry, ? extends C> collector) throws DbxException {
        return getMetadataWithChildrenIfChangedC(path, $assertionsDisabled, previousFolderHash, collector);
    }

    private <T> Maybe<T> getMetadataWithChildrenIfChangedBase(String path, boolean includeMediaInfo, String previousFolderHash, final JsonReader<T> reader) throws DbxException {
        if (previousFolderHash == null) {
            throw new IllegalArgumentException("'previousFolderHash' must not be null");
        } else if (previousFolderHash.length() == 0) {
            throw new IllegalArgumentException("'previousFolderHash' must not be empty");
        } else {
            String str;
            DbxPathV1.checkArg(BoxMetadataUpdateTask.PATH, path);
            String host = this.host.getApi();
            String apiPath = "1/metadata/auto" + path;
            String[] params = new String[8];
            params[0] = "list";
            params[1] = "true";
            params[2] = "file_limit";
            params[3] = "25000";
            params[4] = "hash";
            params[5] = previousFolderHash;
            params[6] = "include_media_info";
            if (includeMediaInfo) {
                str = "true";
            } else {
                str = null;
            }
            params[7] = str;
            return (Maybe) doGet(host, apiPath, params, null, new ResponseHandler<Maybe<T>>() {
                public Maybe<T> handle(Response response) throws DbxException {
                    if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_NOT_FOUND) {
                        return Maybe.Just(null);
                    }
                    if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_NOT_MODIFIED) {
                        return Maybe.Nothing();
                    }
                    if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_OK) {
                        return Maybe.Just(DbxRequestUtil.readJsonFromResponse(reader, response));
                    }
                    throw DbxRequestUtil.unexpectedStatus(response);
                }
            });
        }
    }

    public DbxAccountInfo getAccountInfo() throws DbxException {
        return (DbxAccountInfo) doGet(this.host.getApi(), "1/account/info", null, null, new ResponseHandler<DbxAccountInfo>() {
            public DbxAccountInfo handle(Response response) throws DbxException {
                if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_OK) {
                    return (DbxAccountInfo) DbxRequestUtil.readJsonFromResponse(DbxAccountInfo.Reader, response);
                }
                throw DbxRequestUtil.unexpectedStatus(response);
            }
        });
    }

    public void disableAccessToken() throws DbxException {
        doPost(this.host.getApi(), "1/disable_access_token", null, null, new ResponseHandler<Void>() {
            public Void handle(Response response) throws DbxException {
                if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_OK) {
                    return null;
                }
                throw new BadResponseException(DbxRequestUtil.getRequestId(response), "unexpected response code: " + response.getStatusCode());
            }
        });
    }

    public File getFile(String path, String rev, OutputStream target) throws DbxException, IOException {
        Downloader downloader = startGetFile(path, rev);
        if (downloader == null) {
            return null;
        }
        return downloader.copyBodyAndClose(target);
    }

    public Downloader startGetFile(String path, String rev) throws DbxException {
        DbxPathV1.checkArgNonRoot(BoxMetadataUpdateTask.PATH, path);
        return startGetSomething("1/files/auto" + path, new String[]{"rev", rev});
    }

    private Downloader startGetSomething(final String apiPath, final String[] params) throws DbxException {
        final String host = this.host.getContent();
        return (Downloader) DbxRequestUtil.runAndRetry(this.requestConfig.getMaxRetries(), new RequestMaker<Downloader, DbxException>() {
            public Downloader run() throws DbxException {
                Response response = DbxRequestUtil.startGet(DbxClientV1.this.requestConfig, DbxClientV1.this.accessToken, DbxClientV1.USER_AGENT_ID, host, apiPath, params, null);
                try {
                    if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_NOT_FOUND) {
                        if (null != null) {
                            return null;
                        }
                        try {
                            response.getBody().close();
                            return null;
                        } catch (IOException e) {
                            return null;
                        }
                    } else if (response.getStatusCode() != HttpStatusCodes.STATUS_CODE_OK) {
                        throw DbxRequestUtil.unexpectedStatus(response);
                    } else {
                        Downloader result = new Downloader((File) File.Reader.readFully(DbxRequestUtil.getFirstHeader(response, "x-dropbox-metadata")), response.getBody());
                        if (!true) {
                            try {
                                response.getBody().close();
                            } catch (IOException e2) {
                            }
                        }
                        return result;
                    }
                } catch (JsonReadException ex) {
                    throw new BadResponseException(DbxRequestUtil.getRequestId(response), "Bad JSON in X-Dropbox-Metadata header: " + ex.getMessage(), ex);
                } catch (Throwable th) {
                    if (null == null) {
                        try {
                            response.getBody().close();
                        } catch (IOException e3) {
                        }
                    }
                }
            }
        });
    }

    public File uploadFile(String targetPath, DbxWriteMode writeMode, long numBytes, InputStream contents) throws DbxException, IOException {
        return uploadFile(targetPath, writeMode, numBytes, new InputStreamCopier(contents));
    }

    public <E extends Throwable> File uploadFile(String targetPath, DbxWriteMode writeMode, long numBytes, DbxStreamWriter<E> writer) throws DbxException, Throwable {
        return finishUploadFile(startUploadFile(targetPath, writeMode, numBytes), writer);
    }

    public Uploader startUploadFile(String targetPath, DbxWriteMode writeMode, long numBytes) throws DbxException {
        if (numBytes < 0) {
            if (numBytes == -1) {
                return startUploadFileChunked(targetPath, writeMode, numBytes);
            }
            throw new IllegalArgumentException("numBytes must be -1 or greater; given " + numBytes);
        } else if (numBytes > ChunkedUploadThreshold) {
            return startUploadFileChunked(targetPath, writeMode, numBytes);
        } else {
            return startUploadFileSingle(targetPath, writeMode, numBytes);
        }
    }

    public <E extends Throwable> File finishUploadFile(Uploader uploader, DbxStreamWriter<E> writer) throws DbxException, Throwable {
        NoThrowOutputStream streamWrapper = new NoThrowOutputStream(uploader.getBody());
        try {
            writer.write(streamWrapper);
            File finish = uploader.finish();
            uploader.close();
            return finish;
        } catch (HiddenException ex) {
            if (ex.owner == streamWrapper) {
                throw new NetworkIOException(ex.getCause());
            }
            throw ex;
        } catch (Throwable th) {
            uploader.close();
        }
    }

    public Uploader startUploadFileSingle(String targetPath, DbxWriteMode writeMode, long numBytes) throws DbxException {
        DbxPathV1.checkArg("targetPath", targetPath);
        if (numBytes < 0) {
            throw new IllegalArgumentException("numBytes must be zero or greater");
        }
        String host = this.host.getContent();
        String apiPath = "1/files_put/auto" + targetPath;
        ArrayList<Header> headers = new ArrayList();
        headers.add(new Header("Content-Type", "application/octet-stream"));
        headers.add(new Header("Content-Length", Long.toString(numBytes)));
        return new SingleUploader(DbxRequestUtil.startPut(this.requestConfig, this.accessToken, USER_AGENT_ID, host, apiPath, writeMode.params, headers), numBytes);
    }

    public <E extends Throwable> File uploadFileSingle(String targetPath, DbxWriteMode writeMode, long numBytes, DbxStreamWriter<E> writer) throws DbxException, Throwable {
        return finishUploadFile(startUploadFileSingle(targetPath, writeMode, numBytes), writer);
    }

    private <E extends Throwable> Response chunkedUploadCommon(String[] params, long chunkSize, DbxStreamWriter<E> writer) throws DbxException, Throwable {
        ArrayList<Header> headers = new ArrayList();
        headers.add(new Header("Content-Type", "application/octet-stream"));
        headers.add(new Header("Content-Length", Long.toString(chunkSize)));
        com.dropbox.core.http.HttpRequestor.Uploader uploader = DbxRequestUtil.startPut(this.requestConfig, this.accessToken, USER_AGENT_ID, this.host.getContent(), "1/chunked_upload", params, headers);
        NoThrowOutputStream nt;
        try {
            nt = new NoThrowOutputStream(uploader.getBody());
            writer.write(nt);
            long bytesWritten = nt.getBytesWritten();
            if (bytesWritten != chunkSize) {
                throw new IllegalStateException("'chunkSize' is " + chunkSize + ", but 'writer' only wrote " + bytesWritten + " bytes");
            }
            Response finish = uploader.finish();
            uploader.close();
            return finish;
        } catch (IOException ex) {
            throw new NetworkIOException(ex);
        } catch (HiddenException ex2) {
            if (ex2.owner == nt) {
                throw new NetworkIOException(ex2.getCause());
            }
            throw ex2;
        } catch (Throwable th) {
            uploader.close();
        }
    }

    private ChunkedUploadState chunkedUploadCheckForOffsetCorrection(Response response) throws DbxException {
        if (response.getStatusCode() != 400) {
            return null;
        }
        byte[] data = DbxRequestUtil.loadErrorBody(response);
        try {
            return (ChunkedUploadState) ChunkedUploadState.Reader.readFully(data);
        } catch (JsonReadException e) {
            String requestId = DbxRequestUtil.getRequestId(response);
            throw new BadRequestException(requestId, DbxRequestUtil.parseErrorBody(requestId, 400, data));
        }
    }

    private ChunkedUploadState chunkedUploadParse200(Response response) throws BadResponseException, NetworkIOException {
        if ($assertionsDisabled || response.getStatusCode() == HttpStatusCodes.STATUS_CODE_OK) {
            return (ChunkedUploadState) DbxRequestUtil.readJsonFromResponse(ChunkedUploadState.Reader, response);
        }
        throw new AssertionError(response.getStatusCode());
    }

    public String chunkedUploadFirst(byte[] data) throws DbxException {
        return chunkedUploadFirst(data, 0, data.length);
    }

    public String chunkedUploadFirst(byte[] data, int dataOffset, int dataLength) throws DbxException {
        return chunkedUploadFirst(dataLength, new ByteArrayCopier(data, dataOffset, dataLength));
    }

    public <E extends Throwable> String chunkedUploadFirst(int chunkSize, DbxStreamWriter<E> writer) throws DbxException, Throwable {
        Response response = chunkedUploadCommon(new String[0], (long) chunkSize, writer);
        try {
            if (chunkedUploadCheckForOffsetCorrection(response) != null) {
                throw new BadResponseException(DbxRequestUtil.getRequestId(response), "Got offset correction response on first chunk.");
            } else if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_NOT_FOUND) {
                throw new BadResponseException(DbxRequestUtil.getRequestId(response), "Got a 404, but we didn't send an upload_id");
            } else if (response.getStatusCode() != HttpStatusCodes.STATUS_CODE_OK) {
                throw DbxRequestUtil.unexpectedStatus(response);
            } else {
                ChunkedUploadState returnedState = chunkedUploadParse200(response);
                if (returnedState.offset != ((long) chunkSize)) {
                    throw new BadResponseException(DbxRequestUtil.getRequestId(response), "Sent " + chunkSize + " bytes, but returned offset is " + returnedState.offset);
                }
                String str = returnedState.uploadId;
                return str;
            }
        } finally {
            IOUtil.closeInput(response.getBody());
        }
    }

    public long chunkedUploadAppend(String uploadId, long uploadOffset, byte[] data) throws DbxException {
        return chunkedUploadAppend(uploadId, uploadOffset, data, 0, data.length);
    }

    public long chunkedUploadAppend(String uploadId, long uploadOffset, byte[] data, int dataOffset, int dataLength) throws DbxException {
        return chunkedUploadAppend(uploadId, uploadOffset, (long) dataLength, new ByteArrayCopier(data, dataOffset, dataLength));
    }

    public <E extends Throwable> long chunkedUploadAppend(String uploadId, long uploadOffset, long chunkSize, DbxStreamWriter<E> writer) throws DbxException, Throwable {
        if (uploadId == null) {
            throw new IllegalArgumentException("'uploadId' can't be null");
        } else if (uploadId.length() == 0) {
            throw new IllegalArgumentException("'uploadId' can't be empty");
        } else if (uploadOffset < 0) {
            throw new IllegalArgumentException("'offset' can't be negative");
        } else {
            Response response = chunkedUploadCommon(new String[]{"upload_id", uploadId, BoxList.FIELD_OFFSET, Long.toString(uploadOffset)}, chunkSize, writer);
            String requestId = DbxRequestUtil.getRequestId(response);
            long j;
            try {
                ChunkedUploadState correctedState = chunkedUploadCheckForOffsetCorrection(response);
                long expectedOffset = uploadOffset + chunkSize;
                if (correctedState != null) {
                    if (!correctedState.uploadId.equals(uploadId)) {
                        throw new BadResponseException(requestId, "uploadId mismatch: us=" + StringUtil.jq(uploadId) + ", server=" + StringUtil.jq(correctedState.uploadId));
                    } else if (correctedState.offset == uploadOffset) {
                        throw new BadResponseException(requestId, "Corrected offset is same as given: " + uploadOffset);
                    } else if (correctedState.offset < uploadOffset) {
                        throw new BadResponseException(requestId, "we were at offset " + uploadOffset + ", server said " + correctedState.offset);
                    } else if (correctedState.offset > expectedOffset) {
                        throw new BadResponseException(requestId, "we were at offset " + uploadOffset + ", server said " + correctedState.offset);
                    } else if ($assertionsDisabled || correctedState.offset != expectedOffset) {
                        j = correctedState.offset;
                        return j;
                    } else {
                        throw new AssertionError();
                    }
                } else if (response.getStatusCode() != HttpStatusCodes.STATUS_CODE_OK) {
                    throw DbxRequestUtil.unexpectedStatus(response);
                } else {
                    ChunkedUploadState returnedState = chunkedUploadParse200(response);
                    if (returnedState.offset != expectedOffset) {
                        throw new BadResponseException(requestId, "Expected offset " + expectedOffset + " bytes, but returned offset is " + returnedState.offset);
                    }
                    IOUtil.closeInput(response.getBody());
                    return -1;
                }
            } finally {
                j = response.getBody();
                IOUtil.closeInput((InputStream) j);
            }
        }
    }

    public File chunkedUploadFinish(String targetPath, DbxWriteMode writeMode, String uploadId) throws DbxException {
        DbxPathV1.checkArgNonRoot("targetPath", targetPath);
        return (File) doPost(this.host.getContent(), "1/commit_chunked_upload/auto" + targetPath, (String[]) LangUtil.arrayConcat(new String[]{"upload_id", uploadId}, writeMode.params), null, new ResponseHandler<File>() {
            public File handle(Response response) throws DbxException {
                if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_OK) {
                    return (File) DbxRequestUtil.readJsonFromResponse(File.Reader, response);
                }
                throw DbxRequestUtil.unexpectedStatus(response);
            }
        });
    }

    public Uploader startUploadFileChunked(String targetPath, DbxWriteMode writeMode, long numBytes) {
        return startUploadFileChunked(ChunkedUploadChunkSize, targetPath, writeMode, numBytes);
    }

    public Uploader startUploadFileChunked(int chunkSize, String targetPath, DbxWriteMode writeMode, long numBytes) {
        DbxPathV1.checkArg("targetPath", targetPath);
        if (writeMode == null) {
            throw new IllegalArgumentException("'writeMode' can't be null");
        }
        return new ChunkedUploader(targetPath, writeMode, numBytes, new ChunkedUploadOutputStream(chunkSize));
    }

    public <E extends Throwable> File uploadFileChunked(String targetPath, DbxWriteMode writeMode, long numBytes, DbxStreamWriter<E> writer) throws DbxException, Throwable {
        return finishUploadFile(startUploadFileChunked(targetPath, writeMode, numBytes), writer);
    }

    public <E extends Throwable> File uploadFileChunked(int chunkSize, String targetPath, DbxWriteMode writeMode, long numBytes, DbxStreamWriter<E> writer) throws DbxException, Throwable {
        return finishUploadFile(startUploadFileChunked(chunkSize, targetPath, writeMode, numBytes), writer);
    }

    public DbxDelta<DbxEntry> getDelta(String cursor, boolean includeMediaInfo) throws DbxException {
        return _getDelta(cursor, null, includeMediaInfo);
    }

    public DbxDelta<DbxEntry> getDelta(String cursor) throws DbxException {
        return getDelta(cursor, $assertionsDisabled);
    }

    public <C> DbxDeltaC<C> getDeltaC(Collector<Entry<DbxEntry>, C> collector, String cursor, boolean includeMediaInfo) throws DbxException {
        return _getDeltaC(collector, cursor, null, includeMediaInfo);
    }

    public <C> DbxDeltaC<C> getDeltaC(Collector<Entry<DbxEntry>, C> collector, String cursor) throws DbxException {
        return getDeltaC(collector, cursor, $assertionsDisabled);
    }

    public DbxDelta<DbxEntry> getDeltaWithPathPrefix(String cursor, String pathPrefix, boolean includeMediaInfo) throws DbxException {
        DbxPathV1.checkArg(BoxMetadataUpdateTask.PATH, pathPrefix);
        return _getDelta(cursor, pathPrefix, includeMediaInfo);
    }

    public DbxDelta<DbxEntry> getDeltaWithPathPrefix(String cursor, String pathPrefix) throws DbxException {
        DbxPathV1.checkArg(BoxMetadataUpdateTask.PATH, pathPrefix);
        return _getDelta(cursor, pathPrefix, $assertionsDisabled);
    }

    public <C> DbxDeltaC<C> getDeltaCWithPathPrefix(Collector<Entry<DbxEntry>, C> collector, String cursor, String pathPrefix, boolean includeMediaInfo) throws DbxException {
        DbxPathV1.checkArg(BoxMetadataUpdateTask.PATH, pathPrefix);
        return _getDeltaC(collector, cursor, pathPrefix, includeMediaInfo);
    }

    public <C> DbxDeltaC<C> getDeltaCWithPathPrefix(Collector<Entry<DbxEntry>, C> collector, String cursor, String pathPrefix) throws DbxException {
        return getDeltaCWithPathPrefix(collector, cursor, pathPrefix, $assertionsDisabled);
    }

    private DbxDelta<DbxEntry> _getDelta(String cursor, String pathPrefix, boolean includeMediaInfo) throws DbxException {
        String str;
        String host = this.host.getApi();
        String apiPath = "1/delta";
        String[] params = new String[6];
        params[0] = "cursor";
        params[1] = cursor;
        params[2] = "path_prefix";
        params[3] = pathPrefix;
        params[4] = "include_media_info";
        if (includeMediaInfo) {
            str = "true";
        } else {
            str = null;
        }
        params[5] = str;
        return (DbxDelta) doPost(host, apiPath, params, null, new ResponseHandler<DbxDelta<DbxEntry>>() {
            public DbxDelta<DbxEntry> handle(Response response) throws DbxException {
                if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_OK) {
                    return (DbxDelta) DbxRequestUtil.readJsonFromResponse(new Reader(DbxEntry.Reader), response);
                }
                throw DbxRequestUtil.unexpectedStatus(response);
            }
        });
    }

    private <C> DbxDeltaC<C> _getDeltaC(final Collector<Entry<DbxEntry>, C> collector, String cursor, String pathPrefix, boolean includeMediaInfo) throws DbxException {
        String str;
        String host = this.host.getApi();
        String apiPath = "1/delta";
        String[] params = new String[6];
        params[0] = "cursor";
        params[1] = cursor;
        params[2] = "path_prefix";
        params[3] = pathPrefix;
        params[4] = "include_media_info";
        if (includeMediaInfo) {
            str = "true";
        } else {
            str = null;
        }
        params[5] = str;
        return (DbxDeltaC) doPost(host, apiPath, params, null, new ResponseHandler<DbxDeltaC<C>>() {
            public DbxDeltaC<C> handle(Response response) throws DbxException {
                if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_OK) {
                    return (DbxDeltaC) DbxRequestUtil.readJsonFromResponse(new DbxDeltaC.Reader(DbxEntry.Reader, collector), response);
                }
                throw DbxRequestUtil.unexpectedStatus(response);
            }
        });
    }

    public String getDeltaLatestCursor(boolean includeMediaInfo) throws DbxException {
        return _getDeltaLatestCursor(null, includeMediaInfo);
    }

    public String getDeltaLatestCursor() throws DbxException {
        return _getDeltaLatestCursor(null, $assertionsDisabled);
    }

    public String getDeltaLatestCursorWithPathPrefix(String pathPrefix, boolean includeMediaInfo) throws DbxException {
        DbxPathV1.checkArg(BoxMetadataUpdateTask.PATH, pathPrefix);
        return _getDeltaLatestCursor(pathPrefix, includeMediaInfo);
    }

    public String getDeltaLatestCursorWithPathPrefix(String pathPrefix) throws DbxException {
        return getDeltaLatestCursorWithPathPrefix(pathPrefix, $assertionsDisabled);
    }

    private String _getDeltaLatestCursor(String pathPrefix, boolean includeMediaInfo) throws DbxException {
        String str;
        String host = this.host.getApi();
        String apiPath = "1/delta/latest_cursor";
        String[] params = new String[4];
        params[0] = "path_prefix";
        params[1] = pathPrefix;
        params[2] = "include_media_info";
        if (includeMediaInfo) {
            str = "true";
        } else {
            str = null;
        }
        params[3] = str;
        return (String) doPost(host, apiPath, params, null, new ResponseHandler<String>() {
            public String handle(Response response) throws DbxException {
                if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_OK) {
                    return (String) DbxRequestUtil.readJsonFromResponse(DbxClientV1.LatestCursorReader, response);
                }
                throw DbxRequestUtil.unexpectedStatus(response);
            }
        });
    }

    public DbxLongpollDeltaResult getLongpollDelta(String cursor, int timeout) throws DbxException {
        if (cursor == null) {
            throw new IllegalArgumentException("'cursor' can't be null");
        } else if (timeout < 30 || timeout > 480) {
            throw new IllegalArgumentException("'timeout' must be >=30 and <= 480");
        } else {
            return (DbxLongpollDeltaResult) DbxRequestUtil.doGet(getRequestConfig(), getAccessToken(), USER_AGENT_ID, this.host.getNotify(), "1/longpoll_delta", new String[]{"cursor", cursor, "timeout", Integer.toString(timeout)}, null, new ResponseHandler<DbxLongpollDeltaResult>() {
                public DbxLongpollDeltaResult handle(Response response) throws DbxException {
                    if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_OK) {
                        return (DbxLongpollDeltaResult) DbxRequestUtil.readJsonFromResponse(DbxLongpollDeltaResult.Reader, response);
                    }
                    throw DbxRequestUtil.unexpectedStatus(response);
                }
            });
        }
    }

    public File getThumbnail(DbxThumbnailSize sizeBound, DbxThumbnailFormat format, String path, String rev, OutputStream target) throws DbxException, IOException {
        if (target == null) {
            throw new IllegalArgumentException("'target' can't be null");
        }
        Downloader downloader = startGetThumbnail(sizeBound, format, path, rev);
        if (downloader == null) {
            return null;
        }
        return downloader.copyBodyAndClose(target);
    }

    public Downloader startGetThumbnail(DbxThumbnailSize sizeBound, DbxThumbnailFormat format, String path, String rev) throws DbxException {
        DbxPathV1.checkArgNonRoot(BoxMetadataUpdateTask.PATH, path);
        if (sizeBound == null) {
            throw new IllegalArgumentException("'size' can't be null");
        } else if (format == null) {
            throw new IllegalArgumentException("'format' can't be null");
        } else {
            return startGetSomething("1/thumbnails/auto" + path, new String[]{Attachment.ATTACHMENT_SIZE, sizeBound.ident, "format", format.ident, "rev", rev});
        }
    }

    public List<File> getRevisions(String path) throws DbxException {
        DbxPathV1.checkArgNonRoot(BoxMetadataUpdateTask.PATH, path);
        return (List) doGet(this.host.getApi(), "1/revisions/auto" + path, null, null, new ResponseHandler<List<File>>() {
            public List<File> handle(Response response) throws DbxException {
                if (response.getStatusCode() != HttpStatusCodes.STATUS_CODE_OK) {
                    throw DbxRequestUtil.unexpectedStatus(response);
                }
                return (List) DbxRequestUtil.readJsonFromResponse(JsonArrayReader.mk(File.ReaderMaybeDeleted, NullSkipper.mk(new ArrayListCollector())), response);
            }
        });
    }

    public File restoreFile(String path, String rev) throws DbxException {
        DbxPathV1.checkArgNonRoot(BoxMetadataUpdateTask.PATH, path);
        if (rev == null) {
            throw new IllegalArgumentException("'rev' can't be null");
        } else if (rev.length() == 0) {
            throw new IllegalArgumentException("'rev' can't be empty");
        } else {
            return (File) doGet(this.host.getApi(), "1/restore/auto" + path, new String[]{"rev", rev}, null, new ResponseHandler<File>() {
                public File handle(Response response) throws DbxException {
                    if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_NOT_FOUND) {
                        return null;
                    }
                    if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_OK) {
                        return (File) DbxRequestUtil.readJsonFromResponse(File.Reader, response);
                    }
                    throw DbxRequestUtil.unexpectedStatus(response);
                }
            });
        }
    }

    public List<DbxEntry> searchFileAndFolderNames(String basePath, String query) throws DbxException {
        DbxPathV1.checkArg("basePath", basePath);
        if (query == null) {
            throw new IllegalArgumentException("'query' can't be null");
        } else if (query.length() == 0) {
            throw new IllegalArgumentException("'query' can't be empty");
        } else {
            return (List) doPost(this.host.getApi(), "1/search/auto" + basePath, new String[]{"query", query}, null, new ResponseHandler<List<DbxEntry>>() {
                public List<DbxEntry> handle(Response response) throws DbxException {
                    if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_OK) {
                        return (List) DbxRequestUtil.readJsonFromResponse(JsonArrayReader.mk(DbxEntry.Reader), response);
                    }
                    throw DbxRequestUtil.unexpectedStatus(response);
                }
            });
        }
    }

    public String createShareableUrl(String path) throws DbxException {
        DbxPathV1.checkArg(BoxMetadataUpdateTask.PATH, path);
        return (String) doPost(this.host.getApi(), "1/shares/auto" + path, new String[]{"short_url", "false"}, null, new ResponseHandler<String>() {
            public String handle(Response response) throws DbxException {
                if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_NOT_FOUND) {
                    return null;
                }
                if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_OK) {
                    return ((DbxUrlWithExpiration) DbxRequestUtil.readJsonFromResponse(DbxUrlWithExpiration.Reader, response)).url;
                }
                throw DbxRequestUtil.unexpectedStatus(response);
            }
        });
    }

    public DbxUrlWithExpiration createTemporaryDirectUrl(String path) throws DbxException {
        DbxPathV1.checkArgNonRoot(BoxMetadataUpdateTask.PATH, path);
        return (DbxUrlWithExpiration) doPost(this.host.getApi(), "1/media/auto" + path, null, null, new ResponseHandler<DbxUrlWithExpiration>() {
            public DbxUrlWithExpiration handle(Response response) throws DbxException {
                if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_NOT_FOUND) {
                    return null;
                }
                if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_OK) {
                    return (DbxUrlWithExpiration) DbxRequestUtil.readJsonFromResponse(DbxUrlWithExpiration.Reader, response);
                }
                throw DbxRequestUtil.unexpectedStatus(response);
            }
        });
    }

    public String createCopyRef(String path) throws DbxException {
        DbxPathV1.checkArgNonRoot(BoxMetadataUpdateTask.PATH, path);
        return (String) doPost(this.host.getApi(), "1/copy_ref/auto" + path, null, null, new ResponseHandler<String>() {
            public String handle(Response response) throws DbxException {
                if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_NOT_FOUND) {
                    return null;
                }
                if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_OK) {
                    return ((CopyRef) DbxRequestUtil.readJsonFromResponse(CopyRef.Reader, response)).id;
                }
                throw DbxRequestUtil.unexpectedStatus(response);
            }
        });
    }

    public DbxEntry copy(String fromPath, String toPath) throws DbxException {
        DbxPathV1.checkArg("fromPath", fromPath);
        DbxPathV1.checkArgNonRoot("toPath", toPath);
        return (DbxEntry) doPost(this.host.getApi(), "1/fileops/copy", new String[]{"root", "auto", "from_path", fromPath, "to_path", toPath}, null, new ResponseHandler<DbxEntry>() {
            public DbxEntry handle(Response response) throws DbxException {
                if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_FORBIDDEN) {
                    return null;
                }
                if (response.getStatusCode() != HttpStatusCodes.STATUS_CODE_OK) {
                    throw DbxRequestUtil.unexpectedStatus(response);
                }
                WithChildren dwc = (WithChildren) DbxRequestUtil.readJsonFromResponse(WithChildren.Reader, response);
                if (dwc != null) {
                    return dwc.entry;
                }
                return null;
            }
        });
    }

    public DbxEntry copyFromCopyRef(String copyRef, String toPath) throws DbxException {
        if (copyRef == null) {
            throw new IllegalArgumentException("'copyRef' can't be null");
        } else if (copyRef.length() == 0) {
            throw new IllegalArgumentException("'copyRef' can't be empty");
        } else {
            DbxPathV1.checkArgNonRoot("toPath", toPath);
            return (DbxEntry) doPost(this.host.getApi(), "1/fileops/copy", new String[]{"root", "auto", "from_copy_ref", copyRef, "to_path", toPath}, null, new ResponseHandler<DbxEntry>() {
                public DbxEntry handle(Response response) throws DbxException {
                    if (response.getStatusCode() != HttpStatusCodes.STATUS_CODE_OK) {
                        throw DbxRequestUtil.unexpectedStatus(response);
                    }
                    WithChildren dwc = (WithChildren) DbxRequestUtil.readJsonFromResponse(WithChildren.Reader, response);
                    if (dwc == null) {
                        return null;
                    }
                    return dwc.entry;
                }
            });
        }
    }

    public Folder createFolder(String path) throws DbxException {
        DbxPathV1.checkArgNonRoot(BoxMetadataUpdateTask.PATH, path);
        return (Folder) doPost(this.host.getApi(), "1/fileops/create_folder", new String[]{"root", "auto", BoxMetadataUpdateTask.PATH, path}, null, new ResponseHandler<Folder>() {
            public Folder handle(Response response) throws DbxException {
                if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_FORBIDDEN) {
                    return null;
                }
                if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_OK) {
                    return (Folder) DbxRequestUtil.readJsonFromResponse(Folder.Reader, response);
                }
                throw DbxRequestUtil.unexpectedStatus(response);
            }
        });
    }

    public void delete(String path) throws DbxException {
        DbxPathV1.checkArgNonRoot(BoxMetadataUpdateTask.PATH, path);
        doPost(this.host.getApi(), "1/fileops/delete", new String[]{"root", "auto", BoxMetadataUpdateTask.PATH, path}, null, new ResponseHandler<Void>() {
            public Void handle(Response response) throws DbxException {
                if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_OK) {
                    return null;
                }
                throw DbxRequestUtil.unexpectedStatus(response);
            }
        });
    }

    public DbxEntry move(String fromPath, String toPath) throws DbxException {
        DbxPathV1.checkArgNonRoot("fromPath", fromPath);
        DbxPathV1.checkArgNonRoot("toPath", toPath);
        return (DbxEntry) doPost(this.host.getApi(), "1/fileops/move", new String[]{"root", "auto", "from_path", fromPath, "to_path", toPath}, null, new ResponseHandler<DbxEntry>() {
            public DbxEntry handle(Response response) throws DbxException {
                if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_FORBIDDEN) {
                    return null;
                }
                if (response.getStatusCode() != HttpStatusCodes.STATUS_CODE_OK) {
                    throw DbxRequestUtil.unexpectedStatus(response);
                }
                WithChildren dwc = (WithChildren) DbxRequestUtil.readJsonFromResponse(WithChildren.Reader, response);
                if (dwc != null) {
                    return dwc.entry;
                }
                return null;
            }
        });
    }

    private <T> T doGet(String host, String path, String[] params, ArrayList<Header> headers, ResponseHandler<T> handler) throws DbxException {
        return DbxRequestUtil.doGet(this.requestConfig, this.accessToken, USER_AGENT_ID, host, path, params, headers, handler);
    }

    public <T> T doPost(String host, String path, String[] params, ArrayList<Header> headers, ResponseHandler<T> handler) throws DbxException {
        return DbxRequestUtil.doPost(this.requestConfig, this.accessToken, USER_AGENT_ID, host, path, params, headers, handler);
    }
}
