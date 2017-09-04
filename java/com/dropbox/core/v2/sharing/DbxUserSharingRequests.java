package com.dropbox.core.v2.sharing;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxWrappedException;
import com.dropbox.core.http.HttpRequestor.Header;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.v2.DbxRawClientV2;
import com.dropbox.core.v2.async.LaunchEmptyResult;
import com.dropbox.core.v2.async.LaunchResultBase;
import com.dropbox.core.v2.async.PollArg;
import com.dropbox.core.v2.async.PollArg.Serializer;
import com.dropbox.core.v2.async.PollError;
import com.dropbox.core.v2.async.PollErrorException;
import java.util.Collections;
import java.util.List;

public class DbxUserSharingRequests {
    private final DbxRawClientV2 client;

    public DbxUserSharingRequests(DbxRawClientV2 client) {
        this.client = client;
    }

    List<FileMemberActionResult> addFileMember(AddFileMemberArgs arg) throws AddFileMemberErrorException, DbxException {
        try {
            return (List) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/add_file_member", arg, false, Serializer.INSTANCE, StoneSerializers.list(Serializer.INSTANCE), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new AddFileMemberErrorException("2/sharing/add_file_member", ex.getRequestId(), ex.getUserMessage(), (AddFileMemberError) ex.getErrorValue());
        }
    }

    public List<FileMemberActionResult> addFileMember(String file, List<MemberSelector> members) throws AddFileMemberErrorException, DbxException {
        return addFileMember(new AddFileMemberArgs(file, members));
    }

    public AddFileMemberBuilder addFileMemberBuilder(String file, List<MemberSelector> members) {
        return new AddFileMemberBuilder(this, AddFileMemberArgs.newBuilder(file, members));
    }

    void addFolderMember(AddFolderMemberArg arg) throws AddFolderMemberErrorException, DbxException {
        try {
            this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/add_folder_member", arg, false, Serializer.INSTANCE, StoneSerializers.void_(), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new AddFolderMemberErrorException("2/sharing/add_folder_member", ex.getRequestId(), ex.getUserMessage(), (AddFolderMemberError) ex.getErrorValue());
        }
    }

    public void addFolderMember(String sharedFolderId, List<AddMember> members) throws AddFolderMemberErrorException, DbxException {
        addFolderMember(new AddFolderMemberArg(sharedFolderId, members));
    }

    public AddFolderMemberBuilder addFolderMemberBuilder(String sharedFolderId, List<AddMember> members) {
        return new AddFolderMemberBuilder(this, AddFolderMemberArg.newBuilder(sharedFolderId, members));
    }

    FileMemberActionResult changeFileMemberAccess(ChangeFileMemberAccessArgs arg) throws FileMemberActionErrorException, DbxException {
        try {
            return (FileMemberActionResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/change_file_member_access", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new FileMemberActionErrorException("2/sharing/change_file_member_access", ex.getRequestId(), ex.getUserMessage(), (FileMemberActionError) ex.getErrorValue());
        }
    }

    @Deprecated
    public FileMemberActionResult changeFileMemberAccess(String file, MemberSelector member, AccessLevel accessLevel) throws FileMemberActionErrorException, DbxException {
        return changeFileMemberAccess(new ChangeFileMemberAccessArgs(file, member, accessLevel));
    }

    JobStatus checkJobStatus(PollArg arg) throws PollErrorException, DbxException {
        try {
            return (JobStatus) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/check_job_status", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, PollError.Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new PollErrorException("2/sharing/check_job_status", ex.getRequestId(), ex.getUserMessage(), (PollError) ex.getErrorValue());
        }
    }

    public JobStatus checkJobStatus(String asyncJobId) throws PollErrorException, DbxException {
        return checkJobStatus(new PollArg(asyncJobId));
    }

    RemoveMemberJobStatus checkRemoveMemberJobStatus(PollArg arg) throws PollErrorException, DbxException {
        try {
            return (RemoveMemberJobStatus) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/check_remove_member_job_status", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, PollError.Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new PollErrorException("2/sharing/check_remove_member_job_status", ex.getRequestId(), ex.getUserMessage(), (PollError) ex.getErrorValue());
        }
    }

    public RemoveMemberJobStatus checkRemoveMemberJobStatus(String asyncJobId) throws PollErrorException, DbxException {
        return checkRemoveMemberJobStatus(new PollArg(asyncJobId));
    }

    ShareFolderJobStatus checkShareJobStatus(PollArg arg) throws PollErrorException, DbxException {
        try {
            return (ShareFolderJobStatus) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/check_share_job_status", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, PollError.Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new PollErrorException("2/sharing/check_share_job_status", ex.getRequestId(), ex.getUserMessage(), (PollError) ex.getErrorValue());
        }
    }

    public ShareFolderJobStatus checkShareJobStatus(String asyncJobId) throws PollErrorException, DbxException {
        return checkShareJobStatus(new PollArg(asyncJobId));
    }

    PathLinkMetadata createSharedLink(CreateSharedLinkArg arg) throws CreateSharedLinkErrorException, DbxException {
        try {
            return (PathLinkMetadata) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/create_shared_link", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new CreateSharedLinkErrorException("2/sharing/create_shared_link", ex.getRequestId(), ex.getUserMessage(), (CreateSharedLinkError) ex.getErrorValue());
        }
    }

    @Deprecated
    public PathLinkMetadata createSharedLink(String path) throws CreateSharedLinkErrorException, DbxException {
        return createSharedLink(new CreateSharedLinkArg(path));
    }

    @Deprecated
    public CreateSharedLinkBuilder createSharedLinkBuilder(String path) {
        return new CreateSharedLinkBuilder(this, CreateSharedLinkArg.newBuilder(path));
    }

    SharedLinkMetadata createSharedLinkWithSettings(CreateSharedLinkWithSettingsArg arg) throws CreateSharedLinkWithSettingsErrorException, DbxException {
        try {
            return (SharedLinkMetadata) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/create_shared_link_with_settings", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new CreateSharedLinkWithSettingsErrorException("2/sharing/create_shared_link_with_settings", ex.getRequestId(), ex.getUserMessage(), (CreateSharedLinkWithSettingsError) ex.getErrorValue());
        }
    }

    public SharedLinkMetadata createSharedLinkWithSettings(String path) throws CreateSharedLinkWithSettingsErrorException, DbxException {
        return createSharedLinkWithSettings(new CreateSharedLinkWithSettingsArg(path));
    }

    public SharedLinkMetadata createSharedLinkWithSettings(String path, SharedLinkSettings settings) throws CreateSharedLinkWithSettingsErrorException, DbxException {
        return createSharedLinkWithSettings(new CreateSharedLinkWithSettingsArg(path, settings));
    }

    SharedFileMetadata getFileMetadata(GetFileMetadataArg arg) throws GetFileMetadataErrorException, DbxException {
        try {
            return (SharedFileMetadata) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/get_file_metadata", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new GetFileMetadataErrorException("2/sharing/get_file_metadata", ex.getRequestId(), ex.getUserMessage(), (GetFileMetadataError) ex.getErrorValue());
        }
    }

    public SharedFileMetadata getFileMetadata(String file) throws GetFileMetadataErrorException, DbxException {
        return getFileMetadata(new GetFileMetadataArg(file));
    }

    public SharedFileMetadata getFileMetadata(String file, List<FileAction> actions) throws GetFileMetadataErrorException, DbxException {
        if (actions != null) {
            for (FileAction x : actions) {
                if (x == null) {
                    throw new IllegalArgumentException("An item in list 'actions' is null");
                }
            }
        }
        return getFileMetadata(new GetFileMetadataArg(file, actions));
    }

    List<GetFileMetadataBatchResult> getFileMetadataBatch(GetFileMetadataBatchArg arg) throws SharingUserErrorException, DbxException {
        try {
            return (List) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/get_file_metadata/batch", arg, false, Serializer.INSTANCE, StoneSerializers.list(Serializer.INSTANCE), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new SharingUserErrorException("2/sharing/get_file_metadata/batch", ex.getRequestId(), ex.getUserMessage(), (SharingUserError) ex.getErrorValue());
        }
    }

    public List<GetFileMetadataBatchResult> getFileMetadataBatch(List<String> files) throws SharingUserErrorException, DbxException {
        return getFileMetadataBatch(new GetFileMetadataBatchArg(files));
    }

    public List<GetFileMetadataBatchResult> getFileMetadataBatch(List<String> files, List<FileAction> actions) throws SharingUserErrorException, DbxException {
        if (actions != null) {
            for (FileAction x : actions) {
                if (x == null) {
                    throw new IllegalArgumentException("An item in list 'actions' is null");
                }
            }
        }
        return getFileMetadataBatch(new GetFileMetadataBatchArg(files, actions));
    }

    SharedFolderMetadata getFolderMetadata(GetMetadataArgs arg) throws SharedFolderAccessErrorException, DbxException {
        try {
            return (SharedFolderMetadata) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/get_folder_metadata", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new SharedFolderAccessErrorException("2/sharing/get_folder_metadata", ex.getRequestId(), ex.getUserMessage(), (SharedFolderAccessError) ex.getErrorValue());
        }
    }

    public SharedFolderMetadata getFolderMetadata(String sharedFolderId) throws SharedFolderAccessErrorException, DbxException {
        return getFolderMetadata(new GetMetadataArgs(sharedFolderId));
    }

    public SharedFolderMetadata getFolderMetadata(String sharedFolderId, List<FolderAction> actions) throws SharedFolderAccessErrorException, DbxException {
        if (actions != null) {
            for (FolderAction x : actions) {
                if (x == null) {
                    throw new IllegalArgumentException("An item in list 'actions' is null");
                }
            }
        }
        return getFolderMetadata(new GetMetadataArgs(sharedFolderId, actions));
    }

    DbxDownloader<SharedLinkMetadata> getSharedLinkFile(GetSharedLinkMetadataArg arg, List<Header> _headers) throws GetSharedLinkFileErrorException, DbxException {
        try {
            return this.client.downloadStyle(this.client.getHost().getContent(), "2/sharing/get_shared_link_file", arg, false, _headers, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new GetSharedLinkFileErrorException("2/sharing/get_shared_link_file", ex.getRequestId(), ex.getUserMessage(), (GetSharedLinkFileError) ex.getErrorValue());
        }
    }

    public DbxDownloader<SharedLinkMetadata> getSharedLinkFile(String url) throws GetSharedLinkFileErrorException, DbxException {
        return getSharedLinkFile(new GetSharedLinkMetadataArg(url), Collections.emptyList());
    }

    public GetSharedLinkFileBuilder getSharedLinkFileBuilder(String url) {
        return new GetSharedLinkFileBuilder(this, GetSharedLinkMetadataArg.newBuilder(url));
    }

    SharedLinkMetadata getSharedLinkMetadata(GetSharedLinkMetadataArg arg) throws SharedLinkErrorException, DbxException {
        try {
            return (SharedLinkMetadata) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/get_shared_link_metadata", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new SharedLinkErrorException("2/sharing/get_shared_link_metadata", ex.getRequestId(), ex.getUserMessage(), (SharedLinkError) ex.getErrorValue());
        }
    }

    public SharedLinkMetadata getSharedLinkMetadata(String url) throws SharedLinkErrorException, DbxException {
        return getSharedLinkMetadata(new GetSharedLinkMetadataArg(url));
    }

    public GetSharedLinkMetadataBuilder getSharedLinkMetadataBuilder(String url) {
        return new GetSharedLinkMetadataBuilder(this, GetSharedLinkMetadataArg.newBuilder(url));
    }

    GetSharedLinksResult getSharedLinks(GetSharedLinksArg arg) throws GetSharedLinksErrorException, DbxException {
        try {
            return (GetSharedLinksResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/get_shared_links", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new GetSharedLinksErrorException("2/sharing/get_shared_links", ex.getRequestId(), ex.getUserMessage(), (GetSharedLinksError) ex.getErrorValue());
        }
    }

    @Deprecated
    public GetSharedLinksResult getSharedLinks() throws GetSharedLinksErrorException, DbxException {
        return getSharedLinks(new GetSharedLinksArg());
    }

    @Deprecated
    public GetSharedLinksResult getSharedLinks(String path) throws GetSharedLinksErrorException, DbxException {
        return getSharedLinks(new GetSharedLinksArg(path));
    }

    SharedFileMembers listFileMembers(ListFileMembersArg arg) throws ListFileMembersErrorException, DbxException {
        try {
            return (SharedFileMembers) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/list_file_members", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new ListFileMembersErrorException("2/sharing/list_file_members", ex.getRequestId(), ex.getUserMessage(), (ListFileMembersError) ex.getErrorValue());
        }
    }

    public SharedFileMembers listFileMembers(String file) throws ListFileMembersErrorException, DbxException {
        return listFileMembers(new ListFileMembersArg(file));
    }

    public ListFileMembersBuilder listFileMembersBuilder(String file) {
        return new ListFileMembersBuilder(this, ListFileMembersArg.newBuilder(file));
    }

    List<ListFileMembersBatchResult> listFileMembersBatch(ListFileMembersBatchArg arg) throws SharingUserErrorException, DbxException {
        try {
            return (List) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/list_file_members/batch", arg, false, Serializer.INSTANCE, StoneSerializers.list(Serializer.INSTANCE), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new SharingUserErrorException("2/sharing/list_file_members/batch", ex.getRequestId(), ex.getUserMessage(), (SharingUserError) ex.getErrorValue());
        }
    }

    public List<ListFileMembersBatchResult> listFileMembersBatch(List<String> files) throws SharingUserErrorException, DbxException {
        return listFileMembersBatch(new ListFileMembersBatchArg(files));
    }

    public List<ListFileMembersBatchResult> listFileMembersBatch(List<String> files, long limit) throws SharingUserErrorException, DbxException {
        if (limit <= 20) {
            return listFileMembersBatch(new ListFileMembersBatchArg(files, limit));
        }
        throw new IllegalArgumentException("Number 'limit' is larger than 20L");
    }

    SharedFileMembers listFileMembersContinue(ListFileMembersContinueArg arg) throws ListFileMembersContinueErrorException, DbxException {
        try {
            return (SharedFileMembers) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/list_file_members/continue", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new ListFileMembersContinueErrorException("2/sharing/list_file_members/continue", ex.getRequestId(), ex.getUserMessage(), (ListFileMembersContinueError) ex.getErrorValue());
        }
    }

    public SharedFileMembers listFileMembersContinue(String cursor) throws ListFileMembersContinueErrorException, DbxException {
        return listFileMembersContinue(new ListFileMembersContinueArg(cursor));
    }

    SharedFolderMembers listFolderMembers(ListFolderMembersArgs arg) throws SharedFolderAccessErrorException, DbxException {
        try {
            return (SharedFolderMembers) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/list_folder_members", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new SharedFolderAccessErrorException("2/sharing/list_folder_members", ex.getRequestId(), ex.getUserMessage(), (SharedFolderAccessError) ex.getErrorValue());
        }
    }

    public SharedFolderMembers listFolderMembers(String sharedFolderId) throws SharedFolderAccessErrorException, DbxException {
        return listFolderMembers(new ListFolderMembersArgs(sharedFolderId));
    }

    public ListFolderMembersBuilder listFolderMembersBuilder(String sharedFolderId) {
        return new ListFolderMembersBuilder(this, ListFolderMembersArgs.newBuilder(sharedFolderId));
    }

    SharedFolderMembers listFolderMembersContinue(ListFolderMembersContinueArg arg) throws ListFolderMembersContinueErrorException, DbxException {
        try {
            return (SharedFolderMembers) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/list_folder_members/continue", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new ListFolderMembersContinueErrorException("2/sharing/list_folder_members/continue", ex.getRequestId(), ex.getUserMessage(), (ListFolderMembersContinueError) ex.getErrorValue());
        }
    }

    public SharedFolderMembers listFolderMembersContinue(String cursor) throws ListFolderMembersContinueErrorException, DbxException {
        return listFolderMembersContinue(new ListFolderMembersContinueArg(cursor));
    }

    ListFoldersResult listFolders(ListFoldersArgs arg) throws DbxApiException, DbxException {
        try {
            return (ListFoldersResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/list_folders", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, StoneSerializers.void_());
        } catch (DbxWrappedException ex) {
            throw new DbxApiException(ex.getRequestId(), ex.getUserMessage(), "Unexpected error response for \"list_folders\":" + ex.getErrorValue());
        }
    }

    public ListFoldersResult listFolders() throws DbxApiException, DbxException {
        return listFolders(new ListFoldersArgs());
    }

    public ListFoldersBuilder listFoldersBuilder() {
        return new ListFoldersBuilder(this, ListFoldersArgs.newBuilder());
    }

    ListFoldersResult listFoldersContinue(ListFoldersContinueArg arg) throws ListFoldersContinueErrorException, DbxException {
        try {
            return (ListFoldersResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/list_folders/continue", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new ListFoldersContinueErrorException("2/sharing/list_folders/continue", ex.getRequestId(), ex.getUserMessage(), (ListFoldersContinueError) ex.getErrorValue());
        }
    }

    public ListFoldersResult listFoldersContinue(String cursor) throws ListFoldersContinueErrorException, DbxException {
        return listFoldersContinue(new ListFoldersContinueArg(cursor));
    }

    ListFoldersResult listMountableFolders(ListFoldersArgs arg) throws DbxApiException, DbxException {
        try {
            return (ListFoldersResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/list_mountable_folders", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, StoneSerializers.void_());
        } catch (DbxWrappedException ex) {
            throw new DbxApiException(ex.getRequestId(), ex.getUserMessage(), "Unexpected error response for \"list_mountable_folders\":" + ex.getErrorValue());
        }
    }

    public ListFoldersResult listMountableFolders() throws DbxApiException, DbxException {
        return listMountableFolders(new ListFoldersArgs());
    }

    public ListMountableFoldersBuilder listMountableFoldersBuilder() {
        return new ListMountableFoldersBuilder(this, ListFoldersArgs.newBuilder());
    }

    ListFoldersResult listMountableFoldersContinue(ListFoldersContinueArg arg) throws ListFoldersContinueErrorException, DbxException {
        try {
            return (ListFoldersResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/list_mountable_folders/continue", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new ListFoldersContinueErrorException("2/sharing/list_mountable_folders/continue", ex.getRequestId(), ex.getUserMessage(), (ListFoldersContinueError) ex.getErrorValue());
        }
    }

    public ListFoldersResult listMountableFoldersContinue(String cursor) throws ListFoldersContinueErrorException, DbxException {
        return listMountableFoldersContinue(new ListFoldersContinueArg(cursor));
    }

    ListFilesResult listReceivedFiles(ListFilesArg arg) throws SharingUserErrorException, DbxException {
        try {
            return (ListFilesResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/list_received_files", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new SharingUserErrorException("2/sharing/list_received_files", ex.getRequestId(), ex.getUserMessage(), (SharingUserError) ex.getErrorValue());
        }
    }

    public ListFilesResult listReceivedFiles() throws SharingUserErrorException, DbxException {
        return listReceivedFiles(new ListFilesArg());
    }

    public ListReceivedFilesBuilder listReceivedFilesBuilder() {
        return new ListReceivedFilesBuilder(this, ListFilesArg.newBuilder());
    }

    ListFilesResult listReceivedFilesContinue(ListFilesContinueArg arg) throws ListFilesContinueErrorException, DbxException {
        try {
            return (ListFilesResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/list_received_files/continue", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new ListFilesContinueErrorException("2/sharing/list_received_files/continue", ex.getRequestId(), ex.getUserMessage(), (ListFilesContinueError) ex.getErrorValue());
        }
    }

    public ListFilesResult listReceivedFilesContinue(String cursor) throws ListFilesContinueErrorException, DbxException {
        return listReceivedFilesContinue(new ListFilesContinueArg(cursor));
    }

    ListSharedLinksResult listSharedLinks(ListSharedLinksArg arg) throws ListSharedLinksErrorException, DbxException {
        try {
            return (ListSharedLinksResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/list_shared_links", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new ListSharedLinksErrorException("2/sharing/list_shared_links", ex.getRequestId(), ex.getUserMessage(), (ListSharedLinksError) ex.getErrorValue());
        }
    }

    public ListSharedLinksResult listSharedLinks() throws ListSharedLinksErrorException, DbxException {
        return listSharedLinks(new ListSharedLinksArg());
    }

    public ListSharedLinksBuilder listSharedLinksBuilder() {
        return new ListSharedLinksBuilder(this, ListSharedLinksArg.newBuilder());
    }

    SharedLinkMetadata modifySharedLinkSettings(ModifySharedLinkSettingsArgs arg) throws ModifySharedLinkSettingsErrorException, DbxException {
        try {
            return (SharedLinkMetadata) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/modify_shared_link_settings", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new ModifySharedLinkSettingsErrorException("2/sharing/modify_shared_link_settings", ex.getRequestId(), ex.getUserMessage(), (ModifySharedLinkSettingsError) ex.getErrorValue());
        }
    }

    public SharedLinkMetadata modifySharedLinkSettings(String url, SharedLinkSettings settings) throws ModifySharedLinkSettingsErrorException, DbxException {
        return modifySharedLinkSettings(new ModifySharedLinkSettingsArgs(url, settings));
    }

    public SharedLinkMetadata modifySharedLinkSettings(String url, SharedLinkSettings settings, boolean removeExpiration) throws ModifySharedLinkSettingsErrorException, DbxException {
        return modifySharedLinkSettings(new ModifySharedLinkSettingsArgs(url, settings, removeExpiration));
    }

    SharedFolderMetadata mountFolder(MountFolderArg arg) throws MountFolderErrorException, DbxException {
        try {
            return (SharedFolderMetadata) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/mount_folder", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new MountFolderErrorException("2/sharing/mount_folder", ex.getRequestId(), ex.getUserMessage(), (MountFolderError) ex.getErrorValue());
        }
    }

    public SharedFolderMetadata mountFolder(String sharedFolderId) throws MountFolderErrorException, DbxException {
        return mountFolder(new MountFolderArg(sharedFolderId));
    }

    void relinquishFileMembership(RelinquishFileMembershipArg arg) throws RelinquishFileMembershipErrorException, DbxException {
        try {
            this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/relinquish_file_membership", arg, false, Serializer.INSTANCE, StoneSerializers.void_(), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new RelinquishFileMembershipErrorException("2/sharing/relinquish_file_membership", ex.getRequestId(), ex.getUserMessage(), (RelinquishFileMembershipError) ex.getErrorValue());
        }
    }

    public void relinquishFileMembership(String file) throws RelinquishFileMembershipErrorException, DbxException {
        relinquishFileMembership(new RelinquishFileMembershipArg(file));
    }

    LaunchEmptyResult relinquishFolderMembership(RelinquishFolderMembershipArg arg) throws RelinquishFolderMembershipErrorException, DbxException {
        try {
            return (LaunchEmptyResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/relinquish_folder_membership", arg, false, Serializer.INSTANCE, LaunchEmptyResult.Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new RelinquishFolderMembershipErrorException("2/sharing/relinquish_folder_membership", ex.getRequestId(), ex.getUserMessage(), (RelinquishFolderMembershipError) ex.getErrorValue());
        }
    }

    public LaunchEmptyResult relinquishFolderMembership(String sharedFolderId) throws RelinquishFolderMembershipErrorException, DbxException {
        return relinquishFolderMembership(new RelinquishFolderMembershipArg(sharedFolderId));
    }

    public LaunchEmptyResult relinquishFolderMembership(String sharedFolderId, boolean leaveACopy) throws RelinquishFolderMembershipErrorException, DbxException {
        return relinquishFolderMembership(new RelinquishFolderMembershipArg(sharedFolderId, leaveACopy));
    }

    FileMemberActionIndividualResult removeFileMember(RemoveFileMemberArg arg) throws RemoveFileMemberErrorException, DbxException {
        try {
            return (FileMemberActionIndividualResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/remove_file_member", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new RemoveFileMemberErrorException("2/sharing/remove_file_member", ex.getRequestId(), ex.getUserMessage(), (RemoveFileMemberError) ex.getErrorValue());
        }
    }

    @Deprecated
    public FileMemberActionIndividualResult removeFileMember(String file, MemberSelector member) throws RemoveFileMemberErrorException, DbxException {
        return removeFileMember(new RemoveFileMemberArg(file, member));
    }

    FileMemberRemoveActionResult removeFileMember2(RemoveFileMemberArg arg) throws RemoveFileMemberErrorException, DbxException {
        try {
            return (FileMemberRemoveActionResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/remove_file_member_2", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new RemoveFileMemberErrorException("2/sharing/remove_file_member_2", ex.getRequestId(), ex.getUserMessage(), (RemoveFileMemberError) ex.getErrorValue());
        }
    }

    public FileMemberRemoveActionResult removeFileMember2(String file, MemberSelector member) throws RemoveFileMemberErrorException, DbxException {
        return removeFileMember2(new RemoveFileMemberArg(file, member));
    }

    LaunchResultBase removeFolderMember(RemoveFolderMemberArg arg) throws RemoveFolderMemberErrorException, DbxException {
        try {
            return (LaunchResultBase) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/remove_folder_member", arg, false, Serializer.INSTANCE, LaunchResultBase.Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new RemoveFolderMemberErrorException("2/sharing/remove_folder_member", ex.getRequestId(), ex.getUserMessage(), (RemoveFolderMemberError) ex.getErrorValue());
        }
    }

    public LaunchResultBase removeFolderMember(String sharedFolderId, MemberSelector member, boolean leaveACopy) throws RemoveFolderMemberErrorException, DbxException {
        return removeFolderMember(new RemoveFolderMemberArg(sharedFolderId, member, leaveACopy));
    }

    void revokeSharedLink(RevokeSharedLinkArg arg) throws RevokeSharedLinkErrorException, DbxException {
        try {
            this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/revoke_shared_link", arg, false, Serializer.INSTANCE, StoneSerializers.void_(), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new RevokeSharedLinkErrorException("2/sharing/revoke_shared_link", ex.getRequestId(), ex.getUserMessage(), (RevokeSharedLinkError) ex.getErrorValue());
        }
    }

    public void revokeSharedLink(String url) throws RevokeSharedLinkErrorException, DbxException {
        revokeSharedLink(new RevokeSharedLinkArg(url));
    }

    ShareFolderLaunch shareFolder(ShareFolderArg arg) throws ShareFolderErrorException, DbxException {
        try {
            return (ShareFolderLaunch) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/share_folder", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new ShareFolderErrorException("2/sharing/share_folder", ex.getRequestId(), ex.getUserMessage(), (ShareFolderError) ex.getErrorValue());
        }
    }

    public ShareFolderLaunch shareFolder(String path) throws ShareFolderErrorException, DbxException {
        return shareFolder(new ShareFolderArg(path));
    }

    public ShareFolderBuilder shareFolderBuilder(String path) {
        return new ShareFolderBuilder(this, ShareFolderArg.newBuilder(path));
    }

    void transferFolder(TransferFolderArg arg) throws TransferFolderErrorException, DbxException {
        try {
            this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/transfer_folder", arg, false, Serializer.INSTANCE, StoneSerializers.void_(), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new TransferFolderErrorException("2/sharing/transfer_folder", ex.getRequestId(), ex.getUserMessage(), (TransferFolderError) ex.getErrorValue());
        }
    }

    public void transferFolder(String sharedFolderId, String toDropboxId) throws TransferFolderErrorException, DbxException {
        transferFolder(new TransferFolderArg(sharedFolderId, toDropboxId));
    }

    void unmountFolder(UnmountFolderArg arg) throws UnmountFolderErrorException, DbxException {
        try {
            this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/unmount_folder", arg, false, Serializer.INSTANCE, StoneSerializers.void_(), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new UnmountFolderErrorException("2/sharing/unmount_folder", ex.getRequestId(), ex.getUserMessage(), (UnmountFolderError) ex.getErrorValue());
        }
    }

    public void unmountFolder(String sharedFolderId) throws UnmountFolderErrorException, DbxException {
        unmountFolder(new UnmountFolderArg(sharedFolderId));
    }

    void unshareFile(UnshareFileArg arg) throws UnshareFileErrorException, DbxException {
        try {
            this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/unshare_file", arg, false, Serializer.INSTANCE, StoneSerializers.void_(), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new UnshareFileErrorException("2/sharing/unshare_file", ex.getRequestId(), ex.getUserMessage(), (UnshareFileError) ex.getErrorValue());
        }
    }

    public void unshareFile(String file) throws UnshareFileErrorException, DbxException {
        unshareFile(new UnshareFileArg(file));
    }

    LaunchEmptyResult unshareFolder(UnshareFolderArg arg) throws UnshareFolderErrorException, DbxException {
        try {
            return (LaunchEmptyResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/unshare_folder", arg, false, Serializer.INSTANCE, LaunchEmptyResult.Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new UnshareFolderErrorException("2/sharing/unshare_folder", ex.getRequestId(), ex.getUserMessage(), (UnshareFolderError) ex.getErrorValue());
        }
    }

    public LaunchEmptyResult unshareFolder(String sharedFolderId) throws UnshareFolderErrorException, DbxException {
        return unshareFolder(new UnshareFolderArg(sharedFolderId));
    }

    public LaunchEmptyResult unshareFolder(String sharedFolderId, boolean leaveACopy) throws UnshareFolderErrorException, DbxException {
        return unshareFolder(new UnshareFolderArg(sharedFolderId, leaveACopy));
    }

    MemberAccessLevelResult updateFileMember(UpdateFileMemberArgs arg) throws FileMemberActionErrorException, DbxException {
        try {
            return (MemberAccessLevelResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/update_file_member", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new FileMemberActionErrorException("2/sharing/update_file_member", ex.getRequestId(), ex.getUserMessage(), (FileMemberActionError) ex.getErrorValue());
        }
    }

    public MemberAccessLevelResult updateFileMember(String file, MemberSelector member, AccessLevel accessLevel) throws FileMemberActionErrorException, DbxException {
        return updateFileMember(new UpdateFileMemberArgs(file, member, accessLevel));
    }

    MemberAccessLevelResult updateFolderMember(UpdateFolderMemberArg arg) throws UpdateFolderMemberErrorException, DbxException {
        try {
            return (MemberAccessLevelResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/update_folder_member", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new UpdateFolderMemberErrorException("2/sharing/update_folder_member", ex.getRequestId(), ex.getUserMessage(), (UpdateFolderMemberError) ex.getErrorValue());
        }
    }

    public MemberAccessLevelResult updateFolderMember(String sharedFolderId, MemberSelector member, AccessLevel accessLevel) throws UpdateFolderMemberErrorException, DbxException {
        return updateFolderMember(new UpdateFolderMemberArg(sharedFolderId, member, accessLevel));
    }

    SharedFolderMetadata updateFolderPolicy(UpdateFolderPolicyArg arg) throws UpdateFolderPolicyErrorException, DbxException {
        try {
            return (SharedFolderMetadata) this.client.rpcStyle(this.client.getHost().getApi(), "2/sharing/update_folder_policy", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new UpdateFolderPolicyErrorException("2/sharing/update_folder_policy", ex.getRequestId(), ex.getUserMessage(), (UpdateFolderPolicyError) ex.getErrorValue());
        }
    }

    public SharedFolderMetadata updateFolderPolicy(String sharedFolderId) throws UpdateFolderPolicyErrorException, DbxException {
        return updateFolderPolicy(new UpdateFolderPolicyArg(sharedFolderId));
    }

    public UpdateFolderPolicyBuilder updateFolderPolicyBuilder(String sharedFolderId) {
        return new UpdateFolderPolicyBuilder(this, UpdateFolderPolicyArg.newBuilder(sharedFolderId));
    }
}
