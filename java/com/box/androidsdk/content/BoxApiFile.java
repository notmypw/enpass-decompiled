package com.box.androidsdk.content;

import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.requests.BoxRequestsFile.AddCommentToFile;
import com.box.androidsdk.content.requests.BoxRequestsFile.AddFileToCollection;
import com.box.androidsdk.content.requests.BoxRequestsFile.CopyFile;
import com.box.androidsdk.content.requests.BoxRequestsFile.DeleteFile;
import com.box.androidsdk.content.requests.BoxRequestsFile.DeleteFileFromCollection;
import com.box.androidsdk.content.requests.BoxRequestsFile.DeleteFileVersion;
import com.box.androidsdk.content.requests.BoxRequestsFile.DeleteTrashedFile;
import com.box.androidsdk.content.requests.BoxRequestsFile.DownloadFile;
import com.box.androidsdk.content.requests.BoxRequestsFile.DownloadThumbnail;
import com.box.androidsdk.content.requests.BoxRequestsFile.GetFileComments;
import com.box.androidsdk.content.requests.BoxRequestsFile.GetFileInfo;
import com.box.androidsdk.content.requests.BoxRequestsFile.GetFileVersions;
import com.box.androidsdk.content.requests.BoxRequestsFile.GetTrashedFile;
import com.box.androidsdk.content.requests.BoxRequestsFile.PromoteFileVersion;
import com.box.androidsdk.content.requests.BoxRequestsFile.RestoreTrashedFile;
import com.box.androidsdk.content.requests.BoxRequestsFile.UpdateFile;
import com.box.androidsdk.content.requests.BoxRequestsFile.UpdatedSharedFile;
import com.box.androidsdk.content.requests.BoxRequestsFile.UploadFile;
import com.box.androidsdk.content.requests.BoxRequestsFile.UploadNewVersion;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Locale;

public class BoxApiFile extends BoxApi {
    public BoxApiFile(BoxSession session) {
        super(session);
    }

    protected String getFilesUrl() {
        return String.format(Locale.ENGLISH, "%s/files", new Object[]{getBaseUri()});
    }

    protected String getFileInfoUrl(String id) {
        return String.format(Locale.ENGLISH, "%s/%s", new Object[]{getFilesUrl(), id});
    }

    protected String getFileCopyUrl(String id) {
        return String.format(Locale.ENGLISH, getFileInfoUrl(id) + "/copy", new Object[0]);
    }

    protected String getFileUploadUrl() {
        return String.format(Locale.ENGLISH, "%s/files/content", new Object[]{getBaseUploadUri()});
    }

    protected String getFileUploadNewVersionUrl(String id) {
        return String.format(Locale.ENGLISH, "%s/files/%s/content", new Object[]{getBaseUploadUri(), id});
    }

    protected String getTrashedFileUrl(String id) {
        return getFileInfoUrl(id) + "/trash";
    }

    protected String getFileCommentsUrl(String id) {
        return getFileInfoUrl(id) + BoxApiComment.COMMENTS_ENDPOINT;
    }

    protected String getFileVersionsUrl(String id) {
        return getFileInfoUrl(id) + "/versions";
    }

    protected String getPromoteFileVersionUrl(String id) {
        return getFileVersionsUrl(id) + "/current";
    }

    protected String getDeleteFileVersionUrl(String id, String versionId) {
        return String.format(Locale.ENGLISH, "%s/%s", new Object[]{getFileVersionsUrl(id), versionId});
    }

    protected String getFileDownloadUrl(String id) {
        return getFileInfoUrl(id) + "/content";
    }

    protected String getThumbnailFileDownloadUrl(String id) {
        return getFileInfoUrl(id) + "/thumbnail.png";
    }

    protected String getCommentUrl() {
        return getBaseUri() + BoxApiComment.COMMENTS_ENDPOINT;
    }

    public GetFileInfo getInfoRequest(String id) {
        return new GetFileInfo(id, getFileInfoUrl(id), this.mSession);
    }

    public UpdateFile getUpdateRequest(String id) {
        return new UpdateFile(id, getFileInfoUrl(id), this.mSession);
    }

    public CopyFile getCopyRequest(String id, String parentId) {
        return new CopyFile(id, parentId, getFileCopyUrl(id), this.mSession);
    }

    public UpdateFile getRenameRequest(String id, String newName) {
        UpdateFile request = new UpdateFile(id, getFileInfoUrl(id), this.mSession);
        request.setName(newName);
        return request;
    }

    public UpdateFile getMoveRequest(String id, String parentId) {
        UpdateFile request = new UpdateFile(id, getFileInfoUrl(id), this.mSession);
        request.setParentId(parentId);
        return request;
    }

    public DeleteFile getDeleteRequest(String id) {
        return new DeleteFile(id, getFileInfoUrl(id), this.mSession);
    }

    public UpdatedSharedFile getCreateSharedLinkRequest(String id) {
        return (UpdatedSharedFile) new UpdatedSharedFile(id, getFileInfoUrl(id), this.mSession).setAccess(null);
    }

    public UpdateFile getDisableSharedLinkRequest(String id) {
        return (UpdateFile) new UpdateFile(id, getFileInfoUrl(id), this.mSession).setSharedLink(null);
    }

    public AddCommentToFile getAddCommentRequest(String fileId, String message) {
        return new AddCommentToFile(fileId, message, getCommentUrl(), this.mSession);
    }

    public UploadFile getUploadRequest(InputStream fileInputStream, String fileName, String destinationFolderId) {
        return new UploadFile(fileInputStream, fileName, destinationFolderId, getFileUploadUrl(), this.mSession);
    }

    public UploadFile getUploadRequest(File file, String destinationFolderId) {
        return new UploadFile(file, destinationFolderId, getFileUploadUrl(), this.mSession);
    }

    public UploadNewVersion getUploadNewVersionRequest(InputStream fileInputStream, String destinationFileId) {
        return new UploadNewVersion(fileInputStream, getFileUploadNewVersionUrl(destinationFileId), this.mSession);
    }

    public UploadNewVersion getUploadNewVersionRequest(File file, String destinationFileId) {
        try {
            UploadNewVersion request = getUploadNewVersionRequest(new FileInputStream(file), destinationFileId);
            request.setUploadSize(file.length());
            request.setModifiedDate(new Date(file.lastModified()));
            return request;
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public DownloadFile getDownloadRequest(File target, String fileId) throws IOException {
        if (target.exists()) {
            return new DownloadFile(target, getFileDownloadUrl(fileId), this.mSession);
        }
        throw new FileNotFoundException();
    }

    public DownloadFile getDownloadRequest(OutputStream outputStream, String fileId) {
        return new DownloadFile(outputStream, getFileDownloadUrl(fileId), this.mSession);
    }

    public DownloadThumbnail getDownloadThumbnailRequest(File target, String fileId) throws IOException {
        if (target.exists()) {
            return new DownloadThumbnail(target, getThumbnailFileDownloadUrl(fileId), this.mSession);
        }
        throw new FileNotFoundException();
    }

    public DownloadThumbnail getDownloadThumbnailRequest(OutputStream outputStream, String fileId) {
        return new DownloadThumbnail(outputStream, getThumbnailFileDownloadUrl(fileId), this.mSession);
    }

    public GetTrashedFile getTrashedFileRequest(String id) {
        return new GetTrashedFile(id, getTrashedFileUrl(id), this.mSession);
    }

    public DeleteTrashedFile getDeleteTrashedFileRequest(String id) {
        return new DeleteTrashedFile(id, getTrashedFileUrl(id), this.mSession);
    }

    public RestoreTrashedFile getRestoreTrashedFileRequest(String id) {
        return new RestoreTrashedFile(id, getFileInfoUrl(id), this.mSession);
    }

    public GetFileComments getCommentsRequest(String id) {
        return new GetFileComments(id, getFileCommentsUrl(id), this.mSession);
    }

    public GetFileVersions getVersionsRequest(String id) {
        return new GetFileVersions(id, getFileVersionsUrl(id), this.mSession);
    }

    public PromoteFileVersion getPromoteVersionRequest(String id, String versionId) {
        return new PromoteFileVersion(id, versionId, getPromoteFileVersionUrl(id), this.mSession);
    }

    public DeleteFileVersion getDeleteVersionRequest(String id, String versionId) {
        return new DeleteFileVersion(versionId, getDeleteFileVersionUrl(id, versionId), this.mSession);
    }

    public AddFileToCollection getAddToCollectionRequest(String fileId, String collectionId) {
        return new AddFileToCollection(fileId, collectionId, getFileInfoUrl(fileId), this.mSession);
    }

    public DeleteFileFromCollection getDeleteFromCollectionRequest(String id) {
        return new DeleteFileFromCollection(id, getFileInfoUrl(id), this.mSession);
    }
}
