package com.dropbox.core.v2.files;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxWrappedException;
import com.dropbox.core.http.HttpRequestor.Header;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.v2.DbxRawClientV2;
import com.dropbox.core.v2.async.PollArg;
import com.dropbox.core.v2.async.PollArg.Serializer;
import com.dropbox.core.v2.async.PollError;
import com.dropbox.core.v2.async.PollErrorException;
import com.dropbox.core.v2.properties.GetPropertyTemplateArg;
import com.dropbox.core.v2.properties.GetPropertyTemplateResult;
import com.dropbox.core.v2.properties.ListPropertyTemplateIds;
import com.dropbox.core.v2.properties.PropertyGroup;
import com.dropbox.core.v2.properties.PropertyTemplateError;
import com.dropbox.core.v2.properties.PropertyTemplateErrorException;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class DbxUserFilesRequests {
    private final DbxRawClientV2 client;

    public DbxUserFilesRequests(DbxRawClientV2 client) {
        this.client = client;
    }

    Metadata alphaGetMetadata(AlphaGetMetadataArg arg) throws AlphaGetMetadataErrorException, DbxException {
        try {
            return (Metadata) this.client.rpcStyle(this.client.getHost().getApi(), "2/files/alpha/get_metadata", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new AlphaGetMetadataErrorException("2/files/alpha/get_metadata", ex.getRequestId(), ex.getUserMessage(), (AlphaGetMetadataError) ex.getErrorValue());
        }
    }

    public Metadata alphaGetMetadata(String path) throws AlphaGetMetadataErrorException, DbxException {
        return alphaGetMetadata(new AlphaGetMetadataArg(path));
    }

    public AlphaGetMetadataBuilder alphaGetMetadataBuilder(String path) {
        return new AlphaGetMetadataBuilder(this, AlphaGetMetadataArg.newBuilder(path));
    }

    AlphaUploadUploader alphaUpload(CommitInfoWithProperties arg) throws DbxException {
        return new AlphaUploadUploader(this.client.uploadStyle(this.client.getHost().getContent(), "2/files/alpha/upload", arg, false, Serializer.INSTANCE));
    }

    public AlphaUploadUploader alphaUpload(String path) throws DbxException {
        return alphaUpload(new CommitInfoWithProperties(path));
    }

    public AlphaUploadBuilder alphaUploadBuilder(String path) {
        return new AlphaUploadBuilder(this, CommitInfoWithProperties.newBuilder(path));
    }

    Metadata copy(RelocationArg arg) throws RelocationErrorException, DbxException {
        try {
            return (Metadata) this.client.rpcStyle(this.client.getHost().getApi(), "2/files/copy", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new RelocationErrorException("2/files/copy", ex.getRequestId(), ex.getUserMessage(), (RelocationError) ex.getErrorValue());
        }
    }

    public Metadata copy(String fromPath, String toPath) throws RelocationErrorException, DbxException {
        return copy(new RelocationArg(fromPath, toPath));
    }

    public CopyBuilder copyBuilder(String fromPath, String toPath) {
        return new CopyBuilder(this, RelocationArg.newBuilder(fromPath, toPath));
    }

    RelocationBatchLaunch copyBatch(RelocationBatchArg arg) throws DbxApiException, DbxException {
        try {
            return (RelocationBatchLaunch) this.client.rpcStyle(this.client.getHost().getApi(), "2/files/copy_batch", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, StoneSerializers.void_());
        } catch (DbxWrappedException ex) {
            throw new DbxApiException(ex.getRequestId(), ex.getUserMessage(), "Unexpected error response for \"copy_batch\":" + ex.getErrorValue());
        }
    }

    public RelocationBatchLaunch copyBatch(List<RelocationPath> entries) throws DbxApiException, DbxException {
        return copyBatch(new RelocationBatchArg(entries));
    }

    public CopyBatchBuilder copyBatchBuilder(List<RelocationPath> entries) {
        return new CopyBatchBuilder(this, RelocationBatchArg.newBuilder(entries));
    }

    RelocationBatchJobStatus copyBatchCheck(PollArg arg) throws PollErrorException, DbxException {
        try {
            return (RelocationBatchJobStatus) this.client.rpcStyle(this.client.getHost().getApi(), "2/files/copy_batch/check", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, PollError.Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new PollErrorException("2/files/copy_batch/check", ex.getRequestId(), ex.getUserMessage(), (PollError) ex.getErrorValue());
        }
    }

    public RelocationBatchJobStatus copyBatchCheck(String asyncJobId) throws PollErrorException, DbxException {
        return copyBatchCheck(new PollArg(asyncJobId));
    }

    GetCopyReferenceResult copyReferenceGet(GetCopyReferenceArg arg) throws GetCopyReferenceErrorException, DbxException {
        try {
            return (GetCopyReferenceResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/files/copy_reference/get", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new GetCopyReferenceErrorException("2/files/copy_reference/get", ex.getRequestId(), ex.getUserMessage(), (GetCopyReferenceError) ex.getErrorValue());
        }
    }

    public GetCopyReferenceResult copyReferenceGet(String path) throws GetCopyReferenceErrorException, DbxException {
        return copyReferenceGet(new GetCopyReferenceArg(path));
    }

    SaveCopyReferenceResult copyReferenceSave(SaveCopyReferenceArg arg) throws SaveCopyReferenceErrorException, DbxException {
        try {
            return (SaveCopyReferenceResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/files/copy_reference/save", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new SaveCopyReferenceErrorException("2/files/copy_reference/save", ex.getRequestId(), ex.getUserMessage(), (SaveCopyReferenceError) ex.getErrorValue());
        }
    }

    public SaveCopyReferenceResult copyReferenceSave(String copyReference, String path) throws SaveCopyReferenceErrorException, DbxException {
        return copyReferenceSave(new SaveCopyReferenceArg(copyReference, path));
    }

    FolderMetadata createFolder(CreateFolderArg arg) throws CreateFolderErrorException, DbxException {
        try {
            return (FolderMetadata) this.client.rpcStyle(this.client.getHost().getApi(), "2/files/create_folder", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new CreateFolderErrorException("2/files/create_folder", ex.getRequestId(), ex.getUserMessage(), (CreateFolderError) ex.getErrorValue());
        }
    }

    public FolderMetadata createFolder(String path) throws CreateFolderErrorException, DbxException {
        return createFolder(new CreateFolderArg(path));
    }

    public FolderMetadata createFolder(String path, boolean autorename) throws CreateFolderErrorException, DbxException {
        return createFolder(new CreateFolderArg(path, autorename));
    }

    Metadata delete(DeleteArg arg) throws DeleteErrorException, DbxException {
        try {
            return (Metadata) this.client.rpcStyle(this.client.getHost().getApi(), "2/files/delete", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new DeleteErrorException("2/files/delete", ex.getRequestId(), ex.getUserMessage(), (DeleteError) ex.getErrorValue());
        }
    }

    public Metadata delete(String path) throws DeleteErrorException, DbxException {
        return delete(new DeleteArg(path));
    }

    DeleteBatchLaunch deleteBatch(DeleteBatchArg arg) throws DbxApiException, DbxException {
        try {
            return (DeleteBatchLaunch) this.client.rpcStyle(this.client.getHost().getApi(), "2/files/delete_batch", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, StoneSerializers.void_());
        } catch (DbxWrappedException ex) {
            throw new DbxApiException(ex.getRequestId(), ex.getUserMessage(), "Unexpected error response for \"delete_batch\":" + ex.getErrorValue());
        }
    }

    public DeleteBatchLaunch deleteBatch(List<DeleteArg> entries) throws DbxApiException, DbxException {
        return deleteBatch(new DeleteBatchArg(entries));
    }

    DeleteBatchJobStatus deleteBatchCheck(PollArg arg) throws PollErrorException, DbxException {
        try {
            return (DeleteBatchJobStatus) this.client.rpcStyle(this.client.getHost().getApi(), "2/files/delete_batch/check", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, PollError.Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new PollErrorException("2/files/delete_batch/check", ex.getRequestId(), ex.getUserMessage(), (PollError) ex.getErrorValue());
        }
    }

    public DeleteBatchJobStatus deleteBatchCheck(String asyncJobId) throws PollErrorException, DbxException {
        return deleteBatchCheck(new PollArg(asyncJobId));
    }

    DbxDownloader<FileMetadata> download(DownloadArg arg, List<Header> _headers) throws DownloadErrorException, DbxException {
        try {
            return this.client.downloadStyle(this.client.getHost().getContent(), "2/files/download", arg, false, _headers, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new DownloadErrorException("2/files/download", ex.getRequestId(), ex.getUserMessage(), (DownloadError) ex.getErrorValue());
        }
    }

    public DbxDownloader<FileMetadata> download(String path) throws DownloadErrorException, DbxException {
        return download(new DownloadArg(path), Collections.emptyList());
    }

    public DbxDownloader<FileMetadata> download(String path, String rev) throws DownloadErrorException, DbxException {
        if (rev != null) {
            if (rev.length() < 9) {
                throw new IllegalArgumentException("String 'rev' is shorter than 9");
            } else if (!Pattern.matches("[0-9a-f]+", rev)) {
                throw new IllegalArgumentException("String 'rev' does not match pattern");
            }
        }
        return download(new DownloadArg(path, rev), Collections.emptyList());
    }

    public DownloadBuilder downloadBuilder(String path) {
        return new DownloadBuilder(this, path);
    }

    Metadata getMetadata(GetMetadataArg arg) throws GetMetadataErrorException, DbxException {
        try {
            return (Metadata) this.client.rpcStyle(this.client.getHost().getApi(), "2/files/get_metadata", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new GetMetadataErrorException("2/files/get_metadata", ex.getRequestId(), ex.getUserMessage(), (GetMetadataError) ex.getErrorValue());
        }
    }

    public Metadata getMetadata(String path) throws GetMetadataErrorException, DbxException {
        return getMetadata(new GetMetadataArg(path));
    }

    public GetMetadataBuilder getMetadataBuilder(String path) {
        return new GetMetadataBuilder(this, GetMetadataArg.newBuilder(path));
    }

    DbxDownloader<FileMetadata> getPreview(PreviewArg arg, List<Header> _headers) throws PreviewErrorException, DbxException {
        try {
            return this.client.downloadStyle(this.client.getHost().getContent(), "2/files/get_preview", arg, false, _headers, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new PreviewErrorException("2/files/get_preview", ex.getRequestId(), ex.getUserMessage(), (PreviewError) ex.getErrorValue());
        }
    }

    public DbxDownloader<FileMetadata> getPreview(String path) throws PreviewErrorException, DbxException {
        return getPreview(new PreviewArg(path), Collections.emptyList());
    }

    public DbxDownloader<FileMetadata> getPreview(String path, String rev) throws PreviewErrorException, DbxException {
        if (rev != null) {
            if (rev.length() < 9) {
                throw new IllegalArgumentException("String 'rev' is shorter than 9");
            } else if (!Pattern.matches("[0-9a-f]+", rev)) {
                throw new IllegalArgumentException("String 'rev' does not match pattern");
            }
        }
        return getPreview(new PreviewArg(path, rev), Collections.emptyList());
    }

    public GetPreviewBuilder getPreviewBuilder(String path) {
        return new GetPreviewBuilder(this, path);
    }

    GetTemporaryLinkResult getTemporaryLink(GetTemporaryLinkArg arg) throws GetTemporaryLinkErrorException, DbxException {
        try {
            return (GetTemporaryLinkResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/files/get_temporary_link", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new GetTemporaryLinkErrorException("2/files/get_temporary_link", ex.getRequestId(), ex.getUserMessage(), (GetTemporaryLinkError) ex.getErrorValue());
        }
    }

    public GetTemporaryLinkResult getTemporaryLink(String path) throws GetTemporaryLinkErrorException, DbxException {
        return getTemporaryLink(new GetTemporaryLinkArg(path));
    }

    DbxDownloader<FileMetadata> getThumbnail(ThumbnailArg arg, List<Header> _headers) throws ThumbnailErrorException, DbxException {
        try {
            return this.client.downloadStyle(this.client.getHost().getContent(), "2/files/get_thumbnail", arg, false, _headers, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new ThumbnailErrorException("2/files/get_thumbnail", ex.getRequestId(), ex.getUserMessage(), (ThumbnailError) ex.getErrorValue());
        }
    }

    public DbxDownloader<FileMetadata> getThumbnail(String path) throws ThumbnailErrorException, DbxException {
        return getThumbnail(new ThumbnailArg(path), Collections.emptyList());
    }

    public GetThumbnailBuilder getThumbnailBuilder(String path) {
        return new GetThumbnailBuilder(this, ThumbnailArg.newBuilder(path));
    }

    ListFolderResult listFolder(ListFolderArg arg) throws ListFolderErrorException, DbxException {
        try {
            return (ListFolderResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/files/list_folder", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new ListFolderErrorException("2/files/list_folder", ex.getRequestId(), ex.getUserMessage(), (ListFolderError) ex.getErrorValue());
        }
    }

    public ListFolderResult listFolder(String path) throws ListFolderErrorException, DbxException {
        return listFolder(new ListFolderArg(path));
    }

    public ListFolderBuilder listFolderBuilder(String path) {
        return new ListFolderBuilder(this, ListFolderArg.newBuilder(path));
    }

    ListFolderResult listFolderContinue(ListFolderContinueArg arg) throws ListFolderContinueErrorException, DbxException {
        try {
            return (ListFolderResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/files/list_folder/continue", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new ListFolderContinueErrorException("2/files/list_folder/continue", ex.getRequestId(), ex.getUserMessage(), (ListFolderContinueError) ex.getErrorValue());
        }
    }

    public ListFolderResult listFolderContinue(String cursor) throws ListFolderContinueErrorException, DbxException {
        return listFolderContinue(new ListFolderContinueArg(cursor));
    }

    ListFolderGetLatestCursorResult listFolderGetLatestCursor(ListFolderArg arg) throws ListFolderErrorException, DbxException {
        try {
            return (ListFolderGetLatestCursorResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/files/list_folder/get_latest_cursor", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new ListFolderErrorException("2/files/list_folder/get_latest_cursor", ex.getRequestId(), ex.getUserMessage(), (ListFolderError) ex.getErrorValue());
        }
    }

    public ListFolderGetLatestCursorResult listFolderGetLatestCursor(String path) throws ListFolderErrorException, DbxException {
        return listFolderGetLatestCursor(new ListFolderArg(path));
    }

    public ListFolderGetLatestCursorBuilder listFolderGetLatestCursorBuilder(String path) {
        return new ListFolderGetLatestCursorBuilder(this, ListFolderArg.newBuilder(path));
    }

    ListFolderLongpollResult listFolderLongpoll(ListFolderLongpollArg arg) throws ListFolderLongpollErrorException, DbxException {
        try {
            return (ListFolderLongpollResult) this.client.rpcStyle(this.client.getHost().getNotify(), "2/files/list_folder/longpoll", arg, true, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new ListFolderLongpollErrorException("2/files/list_folder/longpoll", ex.getRequestId(), ex.getUserMessage(), (ListFolderLongpollError) ex.getErrorValue());
        }
    }

    public ListFolderLongpollResult listFolderLongpoll(String cursor) throws ListFolderLongpollErrorException, DbxException {
        return listFolderLongpoll(new ListFolderLongpollArg(cursor));
    }

    public ListFolderLongpollResult listFolderLongpoll(String cursor, long timeout) throws ListFolderLongpollErrorException, DbxException {
        if (timeout < 30) {
            throw new IllegalArgumentException("Number 'timeout' is smaller than 30L");
        } else if (timeout <= 480) {
            return listFolderLongpoll(new ListFolderLongpollArg(cursor, timeout));
        } else {
            throw new IllegalArgumentException("Number 'timeout' is larger than 480L");
        }
    }

    ListRevisionsResult listRevisions(ListRevisionsArg arg) throws ListRevisionsErrorException, DbxException {
        try {
            return (ListRevisionsResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/files/list_revisions", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new ListRevisionsErrorException("2/files/list_revisions", ex.getRequestId(), ex.getUserMessage(), (ListRevisionsError) ex.getErrorValue());
        }
    }

    public ListRevisionsResult listRevisions(String path) throws ListRevisionsErrorException, DbxException {
        return listRevisions(new ListRevisionsArg(path));
    }

    public ListRevisionsResult listRevisions(String path, long limit) throws ListRevisionsErrorException, DbxException {
        if (limit < 1) {
            throw new IllegalArgumentException("Number 'limit' is smaller than 1L");
        } else if (limit <= 100) {
            return listRevisions(new ListRevisionsArg(path, limit));
        } else {
            throw new IllegalArgumentException("Number 'limit' is larger than 100L");
        }
    }

    Metadata move(RelocationArg arg) throws RelocationErrorException, DbxException {
        try {
            return (Metadata) this.client.rpcStyle(this.client.getHost().getApi(), "2/files/move", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new RelocationErrorException("2/files/move", ex.getRequestId(), ex.getUserMessage(), (RelocationError) ex.getErrorValue());
        }
    }

    public Metadata move(String fromPath, String toPath) throws RelocationErrorException, DbxException {
        return move(new RelocationArg(fromPath, toPath));
    }

    public MoveBuilder moveBuilder(String fromPath, String toPath) {
        return new MoveBuilder(this, RelocationArg.newBuilder(fromPath, toPath));
    }

    RelocationBatchLaunch moveBatch(RelocationBatchArg arg) throws DbxApiException, DbxException {
        try {
            return (RelocationBatchLaunch) this.client.rpcStyle(this.client.getHost().getApi(), "2/files/move_batch", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, StoneSerializers.void_());
        } catch (DbxWrappedException ex) {
            throw new DbxApiException(ex.getRequestId(), ex.getUserMessage(), "Unexpected error response for \"move_batch\":" + ex.getErrorValue());
        }
    }

    public RelocationBatchLaunch moveBatch(List<RelocationPath> entries) throws DbxApiException, DbxException {
        return moveBatch(new RelocationBatchArg(entries));
    }

    public MoveBatchBuilder moveBatchBuilder(List<RelocationPath> entries) {
        return new MoveBatchBuilder(this, RelocationBatchArg.newBuilder(entries));
    }

    RelocationBatchJobStatus moveBatchCheck(PollArg arg) throws PollErrorException, DbxException {
        try {
            return (RelocationBatchJobStatus) this.client.rpcStyle(this.client.getHost().getApi(), "2/files/move_batch/check", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, PollError.Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new PollErrorException("2/files/move_batch/check", ex.getRequestId(), ex.getUserMessage(), (PollError) ex.getErrorValue());
        }
    }

    public RelocationBatchJobStatus moveBatchCheck(String asyncJobId) throws PollErrorException, DbxException {
        return moveBatchCheck(new PollArg(asyncJobId));
    }

    void permanentlyDelete(DeleteArg arg) throws DeleteErrorException, DbxException {
        try {
            this.client.rpcStyle(this.client.getHost().getApi(), "2/files/permanently_delete", arg, false, Serializer.INSTANCE, StoneSerializers.void_(), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new DeleteErrorException("2/files/permanently_delete", ex.getRequestId(), ex.getUserMessage(), (DeleteError) ex.getErrorValue());
        }
    }

    public void permanentlyDelete(String path) throws DeleteErrorException, DbxException {
        permanentlyDelete(new DeleteArg(path));
    }

    void propertiesAdd(PropertyGroupWithPath arg) throws AddPropertiesErrorException, DbxException {
        try {
            this.client.rpcStyle(this.client.getHost().getApi(), "2/files/properties/add", arg, false, Serializer.INSTANCE, StoneSerializers.void_(), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new AddPropertiesErrorException("2/files/properties/add", ex.getRequestId(), ex.getUserMessage(), (AddPropertiesError) ex.getErrorValue());
        }
    }

    public void propertiesAdd(String path, List<PropertyGroup> propertyGroups) throws AddPropertiesErrorException, DbxException {
        propertiesAdd(new PropertyGroupWithPath(path, propertyGroups));
    }

    void propertiesOverwrite(PropertyGroupWithPath arg) throws InvalidPropertyGroupErrorException, DbxException {
        try {
            this.client.rpcStyle(this.client.getHost().getApi(), "2/files/properties/overwrite", arg, false, Serializer.INSTANCE, StoneSerializers.void_(), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new InvalidPropertyGroupErrorException("2/files/properties/overwrite", ex.getRequestId(), ex.getUserMessage(), (InvalidPropertyGroupError) ex.getErrorValue());
        }
    }

    public void propertiesOverwrite(String path, List<PropertyGroup> propertyGroups) throws InvalidPropertyGroupErrorException, DbxException {
        propertiesOverwrite(new PropertyGroupWithPath(path, propertyGroups));
    }

    void propertiesRemove(RemovePropertiesArg arg) throws RemovePropertiesErrorException, DbxException {
        try {
            this.client.rpcStyle(this.client.getHost().getApi(), "2/files/properties/remove", arg, false, Serializer.INSTANCE, StoneSerializers.void_(), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new RemovePropertiesErrorException("2/files/properties/remove", ex.getRequestId(), ex.getUserMessage(), (RemovePropertiesError) ex.getErrorValue());
        }
    }

    public void propertiesRemove(String path, List<String> propertyTemplateIds) throws RemovePropertiesErrorException, DbxException {
        propertiesRemove(new RemovePropertiesArg(path, propertyTemplateIds));
    }

    GetPropertyTemplateResult propertiesTemplateGet(GetPropertyTemplateArg arg) throws PropertyTemplateErrorException, DbxException {
        try {
            return (GetPropertyTemplateResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/files/properties/template/get", arg, false, GetPropertyTemplateArg.Serializer.INSTANCE, GetPropertyTemplateResult.Serializer.INSTANCE, PropertyTemplateError.Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new PropertyTemplateErrorException("2/files/properties/template/get", ex.getRequestId(), ex.getUserMessage(), (PropertyTemplateError) ex.getErrorValue());
        }
    }

    public GetPropertyTemplateResult propertiesTemplateGet(String templateId) throws PropertyTemplateErrorException, DbxException {
        return propertiesTemplateGet(new GetPropertyTemplateArg(templateId));
    }

    public ListPropertyTemplateIds propertiesTemplateList() throws PropertyTemplateErrorException, DbxException {
        try {
            return (ListPropertyTemplateIds) this.client.rpcStyle(this.client.getHost().getApi(), "2/files/properties/template/list", null, false, StoneSerializers.void_(), ListPropertyTemplateIds.Serializer.INSTANCE, PropertyTemplateError.Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new PropertyTemplateErrorException("2/files/properties/template/list", ex.getRequestId(), ex.getUserMessage(), (PropertyTemplateError) ex.getErrorValue());
        }
    }

    void propertiesUpdate(UpdatePropertyGroupArg arg) throws UpdatePropertiesErrorException, DbxException {
        try {
            this.client.rpcStyle(this.client.getHost().getApi(), "2/files/properties/update", arg, false, Serializer.INSTANCE, StoneSerializers.void_(), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new UpdatePropertiesErrorException("2/files/properties/update", ex.getRequestId(), ex.getUserMessage(), (UpdatePropertiesError) ex.getErrorValue());
        }
    }

    public void propertiesUpdate(String path, List<PropertyGroupUpdate> updatePropertyGroups) throws UpdatePropertiesErrorException, DbxException {
        propertiesUpdate(new UpdatePropertyGroupArg(path, updatePropertyGroups));
    }

    FileMetadata restore(RestoreArg arg) throws RestoreErrorException, DbxException {
        try {
            return (FileMetadata) this.client.rpcStyle(this.client.getHost().getApi(), "2/files/restore", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new RestoreErrorException("2/files/restore", ex.getRequestId(), ex.getUserMessage(), (RestoreError) ex.getErrorValue());
        }
    }

    public FileMetadata restore(String path, String rev) throws RestoreErrorException, DbxException {
        return restore(new RestoreArg(path, rev));
    }

    SaveUrlResult saveUrl(SaveUrlArg arg) throws SaveUrlErrorException, DbxException {
        try {
            return (SaveUrlResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/files/save_url", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new SaveUrlErrorException("2/files/save_url", ex.getRequestId(), ex.getUserMessage(), (SaveUrlError) ex.getErrorValue());
        }
    }

    public SaveUrlResult saveUrl(String path, String url) throws SaveUrlErrorException, DbxException {
        return saveUrl(new SaveUrlArg(path, url));
    }

    SaveUrlJobStatus saveUrlCheckJobStatus(PollArg arg) throws PollErrorException, DbxException {
        try {
            return (SaveUrlJobStatus) this.client.rpcStyle(this.client.getHost().getApi(), "2/files/save_url/check_job_status", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, PollError.Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new PollErrorException("2/files/save_url/check_job_status", ex.getRequestId(), ex.getUserMessage(), (PollError) ex.getErrorValue());
        }
    }

    public SaveUrlJobStatus saveUrlCheckJobStatus(String asyncJobId) throws PollErrorException, DbxException {
        return saveUrlCheckJobStatus(new PollArg(asyncJobId));
    }

    SearchResult search(SearchArg arg) throws SearchErrorException, DbxException {
        try {
            return (SearchResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/files/search", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new SearchErrorException("2/files/search", ex.getRequestId(), ex.getUserMessage(), (SearchError) ex.getErrorValue());
        }
    }

    public SearchResult search(String path, String query) throws SearchErrorException, DbxException {
        return search(new SearchArg(path, query));
    }

    public SearchBuilder searchBuilder(String path, String query) {
        return new SearchBuilder(this, SearchArg.newBuilder(path, query));
    }

    UploadUploader upload(CommitInfo arg) throws DbxException {
        return new UploadUploader(this.client.uploadStyle(this.client.getHost().getContent(), "2/files/upload", arg, false, Serializer.INSTANCE));
    }

    public UploadUploader upload(String path) throws DbxException {
        return upload(new CommitInfo(path));
    }

    public UploadBuilder uploadBuilder(String path) {
        return new UploadBuilder(this, CommitInfo.newBuilder(path));
    }

    UploadSessionAppendUploader uploadSessionAppend(UploadSessionCursor arg) throws DbxException {
        return new UploadSessionAppendUploader(this.client.uploadStyle(this.client.getHost().getContent(), "2/files/upload_session/append", arg, false, Serializer.INSTANCE));
    }

    @Deprecated
    public UploadSessionAppendUploader uploadSessionAppend(String sessionId, long offset) throws DbxException {
        return uploadSessionAppend(new UploadSessionCursor(sessionId, offset));
    }

    UploadSessionAppendV2Uploader uploadSessionAppendV2(UploadSessionAppendArg arg) throws DbxException {
        return new UploadSessionAppendV2Uploader(this.client.uploadStyle(this.client.getHost().getContent(), "2/files/upload_session/append_v2", arg, false, Serializer.INSTANCE));
    }

    public UploadSessionAppendV2Uploader uploadSessionAppendV2(UploadSessionCursor cursor) throws DbxException {
        return uploadSessionAppendV2(new UploadSessionAppendArg(cursor));
    }

    public UploadSessionAppendV2Uploader uploadSessionAppendV2(UploadSessionCursor cursor, boolean close) throws DbxException {
        return uploadSessionAppendV2(new UploadSessionAppendArg(cursor, close));
    }

    UploadSessionFinishUploader uploadSessionFinish(UploadSessionFinishArg arg) throws DbxException {
        return new UploadSessionFinishUploader(this.client.uploadStyle(this.client.getHost().getContent(), "2/files/upload_session/finish", arg, false, Serializer.INSTANCE));
    }

    public UploadSessionFinishUploader uploadSessionFinish(UploadSessionCursor cursor, CommitInfo commit) throws DbxException {
        return uploadSessionFinish(new UploadSessionFinishArg(cursor, commit));
    }

    UploadSessionFinishBatchLaunch uploadSessionFinishBatch(UploadSessionFinishBatchArg arg) throws DbxApiException, DbxException {
        try {
            return (UploadSessionFinishBatchLaunch) this.client.rpcStyle(this.client.getHost().getApi(), "2/files/upload_session/finish_batch", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, StoneSerializers.void_());
        } catch (DbxWrappedException ex) {
            throw new DbxApiException(ex.getRequestId(), ex.getUserMessage(), "Unexpected error response for \"upload_session/finish_batch\":" + ex.getErrorValue());
        }
    }

    public UploadSessionFinishBatchLaunch uploadSessionFinishBatch(List<UploadSessionFinishArg> entries) throws DbxApiException, DbxException {
        return uploadSessionFinishBatch(new UploadSessionFinishBatchArg(entries));
    }

    UploadSessionFinishBatchJobStatus uploadSessionFinishBatchCheck(PollArg arg) throws PollErrorException, DbxException {
        try {
            return (UploadSessionFinishBatchJobStatus) this.client.rpcStyle(this.client.getHost().getApi(), "2/files/upload_session/finish_batch/check", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, PollError.Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new PollErrorException("2/files/upload_session/finish_batch/check", ex.getRequestId(), ex.getUserMessage(), (PollError) ex.getErrorValue());
        }
    }

    public UploadSessionFinishBatchJobStatus uploadSessionFinishBatchCheck(String asyncJobId) throws PollErrorException, DbxException {
        return uploadSessionFinishBatchCheck(new PollArg(asyncJobId));
    }

    UploadSessionStartUploader uploadSessionStart(UploadSessionStartArg arg) throws DbxException {
        return new UploadSessionStartUploader(this.client.uploadStyle(this.client.getHost().getContent(), "2/files/upload_session/start", arg, false, Serializer.INSTANCE));
    }

    public UploadSessionStartUploader uploadSessionStart() throws DbxException {
        return uploadSessionStart(new UploadSessionStartArg());
    }

    public UploadSessionStartUploader uploadSessionStart(boolean close) throws DbxException {
        return uploadSessionStart(new UploadSessionStartArg(close));
    }
}
