package com.box.androidsdk.content.requests;

import com.box.androidsdk.content.BoxException;
import com.box.androidsdk.content.models.BoxComment;
import com.box.androidsdk.content.models.BoxDownload;
import com.box.androidsdk.content.models.BoxEntity;
import com.box.androidsdk.content.models.BoxFile;
import com.box.androidsdk.content.models.BoxFileVersion;
import com.box.androidsdk.content.models.BoxListComments;
import com.box.androidsdk.content.models.BoxListFileVersions;
import com.box.androidsdk.content.models.BoxRealTimeServer;
import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.models.BoxVoid;
import com.box.androidsdk.content.requests.BoxRequest.Methods;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

public class BoxRequestsFile {

    public static class AddCommentToFile extends BoxRequestCommentAdd<BoxComment, AddCommentToFile> {
        public /* bridge */ /* synthetic */ String getItemId() {
            return super.getItemId();
        }

        public /* bridge */ /* synthetic */ String getItemType() {
            return super.getItemType();
        }

        public /* bridge */ /* synthetic */ String getMessage() {
            return super.getMessage();
        }

        public AddCommentToFile(String fileId, String message, String requestUrl, BoxSession session) {
            super(BoxComment.class, requestUrl, session);
            setItemId(fileId);
            setItemType(BoxFile.TYPE);
            setMessage(message);
        }
    }

    public static class AddFileToCollection extends BoxRequestCollectionUpdate<BoxFile, AddFileToCollection> {
        public AddFileToCollection(String id, String collectionId, String requestUrl, BoxSession session) {
            super(BoxFile.class, id, requestUrl, session);
            setCollectionId(collectionId);
        }

        public AddFileToCollection setCollectionId(String id) {
            return (AddFileToCollection) super.setCollectionId(id);
        }
    }

    public static class CopyFile extends BoxRequestItemCopy<BoxFile, CopyFile> {
        public /* bridge */ /* synthetic */ String getName() {
            return super.getName();
        }

        public /* bridge */ /* synthetic */ String getParentId() {
            return super.getParentId();
        }

        public CopyFile(String id, String parentId, String requestUrl, BoxSession session) {
            super(BoxFile.class, id, parentId, requestUrl, session);
        }
    }

    public static class DeleteFile extends BoxRequestItemDelete<DeleteFile> {
        public /* bridge */ /* synthetic */ String getId() {
            return super.getId();
        }

        public /* bridge */ /* synthetic */ String getIfMatchEtag() {
            return super.getIfMatchEtag();
        }

        public DeleteFile(String id, String requestUrl, BoxSession session) {
            super(id, requestUrl, session);
        }
    }

    public static class DeleteFileFromCollection extends BoxRequestCollectionUpdate<BoxFile, DeleteFileFromCollection> {
        public DeleteFileFromCollection(String id, String requestUrl, BoxSession session) {
            super(BoxFile.class, id, requestUrl, session);
            setCollectionId(null);
        }
    }

    public static class DeleteFileVersion extends BoxRequest<BoxVoid, DeleteFileVersion> {
        private final String mVersionId;

        public DeleteFileVersion(String versionId, String requestUrl, BoxSession session) {
            super(BoxVoid.class, requestUrl, session);
            this.mRequestMethod = Methods.DELETE;
            this.mVersionId = versionId;
        }

        public String getVersionId() {
            return this.mVersionId;
        }
    }

    public static class DeleteTrashedFile extends BoxRequestItemDelete<DeleteTrashedFile> {
        public /* bridge */ /* synthetic */ String getId() {
            return super.getId();
        }

        public /* bridge */ /* synthetic */ String getIfMatchEtag() {
            return super.getIfMatchEtag();
        }

        public DeleteTrashedFile(String id, String requestUrl, BoxSession session) {
            super(id, requestUrl, session);
        }
    }

    public static class DownloadFile extends BoxRequestDownload<BoxDownload, DownloadFile> {
        public DownloadFile(OutputStream outputStream, String requestUrl, BoxSession session) {
            super(BoxDownload.class, outputStream, requestUrl, session);
        }

        public DownloadFile(File target, String requestUrl, BoxSession session) {
            super(BoxDownload.class, target, requestUrl, session);
        }
    }

    public static class DownloadThumbnail extends BoxRequestDownload<BoxDownload, DownloadThumbnail> {
        private static final String FIELD_MAX_HEIGHT = "max_height";
        private static final String FIELD_MAX_WIDTH = "max_width";
        private static final String FIELD_MIN_HEIGHT = "min_height";
        private static final String FIELD_MIN_WIDTH = "min_width";
        public static int SIZE_128 = 128;
        public static int SIZE_256 = 256;
        public static int SIZE_32 = 32;
        public static int SIZE_64 = 64;

        public DownloadThumbnail(OutputStream outputStream, String requestUrl, BoxSession session) {
            super(BoxDownload.class, outputStream, requestUrl, session);
        }

        public DownloadThumbnail(File target, String requestUrl, BoxSession session) {
            super(BoxDownload.class, target, requestUrl, session);
        }

        public DownloadThumbnail setMinWidth(int width) {
            this.mQueryMap.put(FIELD_MIN_WIDTH, Integer.toString(width));
            return this;
        }

        public DownloadThumbnail setMaxWidth(int width) {
            this.mQueryMap.put(FIELD_MAX_WIDTH, Integer.toString(width));
            return this;
        }

        public DownloadThumbnail setMinHeight(int height) {
            this.mQueryMap.put(FIELD_MIN_HEIGHT, Integer.toString(height));
            return this;
        }

        public DownloadThumbnail setMaxHeight(int height) {
            this.mQueryMap.put(FIELD_MAX_HEIGHT, Integer.toString(height));
            return this;
        }

        public DownloadThumbnail setMinSize(int size) {
            setMinWidth(size);
            setMinHeight(size);
            return this;
        }
    }

    public static class GetFileComments extends BoxRequestItem<BoxListComments, GetFileComments> {
        public GetFileComments(String id, String requestUrl, BoxSession session) {
            super(BoxListComments.class, id, requestUrl, session);
            this.mRequestMethod = Methods.GET;
        }
    }

    public static class GetFileInfo extends BoxRequestItem<BoxFile, GetFileInfo> {
        public GetFileInfo(String id, String requestUrl, BoxSession session) {
            super(BoxFile.class, id, requestUrl, session);
            this.mRequestMethod = Methods.GET;
        }

        public GetFileInfo setIfNoneMatchEtag(String etag) {
            return (GetFileInfo) super.setIfNoneMatchEtag(etag);
        }

        public String getIfNoneMatchEtag() {
            return super.getIfNoneMatchEtag();
        }
    }

    public static class GetFileVersions extends BoxRequestItem<BoxListFileVersions, GetFileVersions> {
        public GetFileVersions(String id, String requestUrl, BoxSession session) {
            super(BoxListFileVersions.class, id, requestUrl, session);
            this.mRequestMethod = Methods.GET;
            setFields(BoxFileVersion.ALL_FIELDS);
        }
    }

    public static class GetTrashedFile extends BoxRequestItem<BoxFile, GetTrashedFile> {
        public GetTrashedFile(String id, String requestUrl, BoxSession session) {
            super(BoxFile.class, id, requestUrl, session);
            this.mRequestMethod = Methods.GET;
        }

        public GetTrashedFile setIfNoneMatchEtag(String etag) {
            return (GetTrashedFile) super.setIfNoneMatchEtag(etag);
        }

        public String getIfNoneMatchEtag() {
            return super.getIfNoneMatchEtag();
        }
    }

    public static class PromoteFileVersion extends BoxRequestItem<BoxFileVersion, PromoteFileVersion> {
        public PromoteFileVersion(String id, String versionId, String requestUrl, BoxSession session) {
            super(BoxFileVersion.class, id, requestUrl, session);
            this.mRequestMethod = Methods.POST;
            setVersionId(versionId);
        }

        public PromoteFileVersion setVersionId(String versionId) {
            this.mBodyMap.put(BoxRealTimeServer.FIELD_TYPE, BoxFileVersion.TYPE);
            this.mBodyMap.put(BoxEntity.FIELD_ID, versionId);
            return this;
        }
    }

    public static class RestoreTrashedFile extends BoxRequestItemRestoreTrashed<BoxFile, RestoreTrashedFile> {
        public /* bridge */ /* synthetic */ String getName() {
            return super.getName();
        }

        public /* bridge */ /* synthetic */ String getParentId() {
            return super.getParentId();
        }

        public RestoreTrashedFile(String id, String requestUrl, BoxSession session) {
            super(BoxFile.class, id, requestUrl, session);
        }
    }

    public static class UpdateFile extends BoxRequestItemUpdate<BoxFile, UpdateFile> {
        public UpdateFile(String id, String requestUrl, BoxSession session) {
            super(BoxFile.class, id, requestUrl, session);
        }

        public UpdatedSharedFile updateSharedLink() {
            return new UpdatedSharedFile(this);
        }
    }

    public static class UpdatedSharedFile extends BoxRequestUpdateSharedItem<BoxFile, UpdatedSharedFile> {
        public UpdatedSharedFile(String id, String requestUrl, BoxSession session) {
            super(BoxFile.class, id, requestUrl, session);
        }

        protected UpdatedSharedFile(UpdateFile r) {
            super(r);
        }

        public UpdatedSharedFile setCanDownload(boolean canDownload) {
            return (UpdatedSharedFile) super.setCanDownload(canDownload);
        }

        public Boolean getCanDownload() {
            return super.getCanDownload();
        }
    }

    public static class UploadFile extends BoxRequestUpload<BoxFile, UploadFile> {
        String mDestinationFolderId;

        public UploadFile(InputStream inputStream, String fileName, String destinationFolderId, String requestUrl, BoxSession session) {
            super(BoxFile.class, inputStream, requestUrl, session);
            this.mRequestUrlString = requestUrl;
            this.mRequestMethod = Methods.POST;
            this.mFileName = fileName;
            this.mStream = inputStream;
            this.mDestinationFolderId = destinationFolderId;
        }

        public UploadFile(File file, String destinationFolderId, String requestUrl, BoxSession session) {
            super(BoxFile.class, null, requestUrl, session);
            this.mRequestUrlString = requestUrl;
            this.mRequestMethod = Methods.POST;
            this.mDestinationFolderId = destinationFolderId;
            this.mFileName = file.getName();
            this.mFile = file;
            this.mUploadSize = file.length();
            this.mModifiedDate = new Date(file.lastModified());
        }

        protected BoxRequestMultipart createMultipartRequest() throws IOException, BoxException {
            BoxRequestMultipart request = super.createMultipartRequest();
            request.putField("parent_id", this.mDestinationFolderId);
            return request;
        }

        public String getFileName() {
            return this.mFileName;
        }

        public UploadFile setFileName(String mFileName) {
            this.mFileName = mFileName;
            return this;
        }

        public String getDestinationFolderId() {
            return this.mDestinationFolderId;
        }
    }

    public static class UploadNewVersion extends BoxRequestUpload<BoxFile, UploadNewVersion> {
        public UploadNewVersion(InputStream fileInputStream, String requestUrl, BoxSession session) {
            super(BoxFile.class, fileInputStream, requestUrl, session);
        }

        public UploadNewVersion setIfMatchEtag(String etag) {
            return (UploadNewVersion) super.setIfMatchEtag(etag);
        }

        public String getIfMatchEtag() {
            return super.getIfMatchEtag();
        }
    }
}
