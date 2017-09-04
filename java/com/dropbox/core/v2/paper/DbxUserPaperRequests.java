package com.dropbox.core.v2.paper;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxWrappedException;
import com.dropbox.core.http.HttpRequestor.Header;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.v2.DbxRawClientV2;
import com.dropbox.core.v2.sharing.MemberSelector;
import java.util.Collections;
import java.util.List;

public class DbxUserPaperRequests {
    private final DbxRawClientV2 client;

    public DbxUserPaperRequests(DbxRawClientV2 client) {
        this.client = client;
    }

    void docsArchive(RefPaperDoc arg) throws DocLookupErrorException, DbxException {
        try {
            this.client.rpcStyle(this.client.getHost().getApi(), "2/paper/docs/archive", arg, false, Serializer.INSTANCE, StoneSerializers.void_(), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new DocLookupErrorException("2/paper/docs/archive", ex.getRequestId(), ex.getUserMessage(), (DocLookupError) ex.getErrorValue());
        }
    }

    public void docsArchive(String docId) throws DocLookupErrorException, DbxException {
        docsArchive(new RefPaperDoc(docId));
    }

    DbxDownloader<PaperDocExportResult> docsDownload(PaperDocExport arg, List<Header> _headers) throws DocLookupErrorException, DbxException {
        try {
            return this.client.downloadStyle(this.client.getHost().getApi(), "2/paper/docs/download", arg, false, _headers, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new DocLookupErrorException("2/paper/docs/download", ex.getRequestId(), ex.getUserMessage(), (DocLookupError) ex.getErrorValue());
        }
    }

    public DbxDownloader<PaperDocExportResult> docsDownload(String docId, ExportFormat exportFormat) throws DocLookupErrorException, DbxException {
        return docsDownload(new PaperDocExport(docId, exportFormat), Collections.emptyList());
    }

    public DocsDownloadBuilder docsDownloadBuilder(String docId, ExportFormat exportFormat) {
        return new DocsDownloadBuilder(this, docId, exportFormat);
    }

    ListUsersOnFolderResponse docsFolderUsersList(ListUsersOnFolderArgs arg) throws DocLookupErrorException, DbxException {
        try {
            return (ListUsersOnFolderResponse) this.client.rpcStyle(this.client.getHost().getApi(), "2/paper/docs/folder_users/list", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new DocLookupErrorException("2/paper/docs/folder_users/list", ex.getRequestId(), ex.getUserMessage(), (DocLookupError) ex.getErrorValue());
        }
    }

    public ListUsersOnFolderResponse docsFolderUsersList(String docId) throws DocLookupErrorException, DbxException {
        return docsFolderUsersList(new ListUsersOnFolderArgs(docId));
    }

    public ListUsersOnFolderResponse docsFolderUsersList(String docId, int limit) throws DocLookupErrorException, DbxException {
        if (limit < 1) {
            throw new IllegalArgumentException("Number 'limit' is smaller than 1");
        } else if (limit <= 1000) {
            return docsFolderUsersList(new ListUsersOnFolderArgs(docId, limit));
        } else {
            throw new IllegalArgumentException("Number 'limit' is larger than 1000");
        }
    }

    ListUsersOnFolderResponse docsFolderUsersListContinue(ListUsersOnFolderContinueArgs arg) throws ListUsersCursorErrorException, DbxException {
        try {
            return (ListUsersOnFolderResponse) this.client.rpcStyle(this.client.getHost().getApi(), "2/paper/docs/folder_users/list/continue", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new ListUsersCursorErrorException("2/paper/docs/folder_users/list/continue", ex.getRequestId(), ex.getUserMessage(), (ListUsersCursorError) ex.getErrorValue());
        }
    }

    public ListUsersOnFolderResponse docsFolderUsersListContinue(String docId, String cursor) throws ListUsersCursorErrorException, DbxException {
        return docsFolderUsersListContinue(new ListUsersOnFolderContinueArgs(docId, cursor));
    }

    FoldersContainingPaperDoc docsGetFolderInfo(RefPaperDoc arg) throws DocLookupErrorException, DbxException {
        try {
            return (FoldersContainingPaperDoc) this.client.rpcStyle(this.client.getHost().getApi(), "2/paper/docs/get_folder_info", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new DocLookupErrorException("2/paper/docs/get_folder_info", ex.getRequestId(), ex.getUserMessage(), (DocLookupError) ex.getErrorValue());
        }
    }

    public FoldersContainingPaperDoc docsGetFolderInfo(String docId) throws DocLookupErrorException, DbxException {
        return docsGetFolderInfo(new RefPaperDoc(docId));
    }

    ListPaperDocsResponse docsList(ListPaperDocsArgs arg) throws DbxApiException, DbxException {
        try {
            return (ListPaperDocsResponse) this.client.rpcStyle(this.client.getHost().getApi(), "2/paper/docs/list", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, StoneSerializers.void_());
        } catch (DbxWrappedException ex) {
            throw new DbxApiException(ex.getRequestId(), ex.getUserMessage(), "Unexpected error response for \"docs/list\":" + ex.getErrorValue());
        }
    }

    public ListPaperDocsResponse docsList() throws DbxApiException, DbxException {
        return docsList(new ListPaperDocsArgs());
    }

    public DocsListBuilder docsListBuilder() {
        return new DocsListBuilder(this, ListPaperDocsArgs.newBuilder());
    }

    ListPaperDocsResponse docsListContinue(ListPaperDocsContinueArgs arg) throws ListDocsCursorErrorException, DbxException {
        try {
            return (ListPaperDocsResponse) this.client.rpcStyle(this.client.getHost().getApi(), "2/paper/docs/list/continue", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new ListDocsCursorErrorException("2/paper/docs/list/continue", ex.getRequestId(), ex.getUserMessage(), (ListDocsCursorError) ex.getErrorValue());
        }
    }

    public ListPaperDocsResponse docsListContinue(String cursor) throws ListDocsCursorErrorException, DbxException {
        return docsListContinue(new ListPaperDocsContinueArgs(cursor));
    }

    void docsPermanentlyDelete(RefPaperDoc arg) throws DocLookupErrorException, DbxException {
        try {
            this.client.rpcStyle(this.client.getHost().getApi(), "2/paper/docs/permanently_delete", arg, false, Serializer.INSTANCE, StoneSerializers.void_(), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new DocLookupErrorException("2/paper/docs/permanently_delete", ex.getRequestId(), ex.getUserMessage(), (DocLookupError) ex.getErrorValue());
        }
    }

    public void docsPermanentlyDelete(String docId) throws DocLookupErrorException, DbxException {
        docsPermanentlyDelete(new RefPaperDoc(docId));
    }

    SharingPolicy docsSharingPolicyGet(RefPaperDoc arg) throws DocLookupErrorException, DbxException {
        try {
            return (SharingPolicy) this.client.rpcStyle(this.client.getHost().getApi(), "2/paper/docs/sharing_policy/get", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new DocLookupErrorException("2/paper/docs/sharing_policy/get", ex.getRequestId(), ex.getUserMessage(), (DocLookupError) ex.getErrorValue());
        }
    }

    public SharingPolicy docsSharingPolicyGet(String docId) throws DocLookupErrorException, DbxException {
        return docsSharingPolicyGet(new RefPaperDoc(docId));
    }

    void docsSharingPolicySet(PaperDocSharingPolicy arg) throws DocLookupErrorException, DbxException {
        try {
            this.client.rpcStyle(this.client.getHost().getApi(), "2/paper/docs/sharing_policy/set", arg, false, Serializer.INSTANCE, StoneSerializers.void_(), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new DocLookupErrorException("2/paper/docs/sharing_policy/set", ex.getRequestId(), ex.getUserMessage(), (DocLookupError) ex.getErrorValue());
        }
    }

    public void docsSharingPolicySet(String docId, SharingPolicy sharingPolicy) throws DocLookupErrorException, DbxException {
        docsSharingPolicySet(new PaperDocSharingPolicy(docId, sharingPolicy));
    }

    List<AddPaperDocUserMemberResult> docsUsersAdd(AddPaperDocUser arg) throws DocLookupErrorException, DbxException {
        try {
            return (List) this.client.rpcStyle(this.client.getHost().getApi(), "2/paper/docs/users/add", arg, false, Serializer.INSTANCE, StoneSerializers.list(Serializer.INSTANCE), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new DocLookupErrorException("2/paper/docs/users/add", ex.getRequestId(), ex.getUserMessage(), (DocLookupError) ex.getErrorValue());
        }
    }

    public List<AddPaperDocUserMemberResult> docsUsersAdd(String docId, List<AddMember> members) throws DocLookupErrorException, DbxException {
        return docsUsersAdd(new AddPaperDocUser(docId, members));
    }

    public DocsUsersAddBuilder docsUsersAddBuilder(String docId, List<AddMember> members) {
        return new DocsUsersAddBuilder(this, AddPaperDocUser.newBuilder(docId, members));
    }

    ListUsersOnPaperDocResponse docsUsersList(ListUsersOnPaperDocArgs arg) throws DocLookupErrorException, DbxException {
        try {
            return (ListUsersOnPaperDocResponse) this.client.rpcStyle(this.client.getHost().getApi(), "2/paper/docs/users/list", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new DocLookupErrorException("2/paper/docs/users/list", ex.getRequestId(), ex.getUserMessage(), (DocLookupError) ex.getErrorValue());
        }
    }

    public ListUsersOnPaperDocResponse docsUsersList(String docId) throws DocLookupErrorException, DbxException {
        return docsUsersList(new ListUsersOnPaperDocArgs(docId));
    }

    public DocsUsersListBuilder docsUsersListBuilder(String docId) {
        return new DocsUsersListBuilder(this, ListUsersOnPaperDocArgs.newBuilder(docId));
    }

    ListUsersOnPaperDocResponse docsUsersListContinue(ListUsersOnPaperDocContinueArgs arg) throws ListUsersCursorErrorException, DbxException {
        try {
            return (ListUsersOnPaperDocResponse) this.client.rpcStyle(this.client.getHost().getApi(), "2/paper/docs/users/list/continue", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new ListUsersCursorErrorException("2/paper/docs/users/list/continue", ex.getRequestId(), ex.getUserMessage(), (ListUsersCursorError) ex.getErrorValue());
        }
    }

    public ListUsersOnPaperDocResponse docsUsersListContinue(String docId, String cursor) throws ListUsersCursorErrorException, DbxException {
        return docsUsersListContinue(new ListUsersOnPaperDocContinueArgs(docId, cursor));
    }

    void docsUsersRemove(RemovePaperDocUser arg) throws DocLookupErrorException, DbxException {
        try {
            this.client.rpcStyle(this.client.getHost().getApi(), "2/paper/docs/users/remove", arg, false, Serializer.INSTANCE, StoneSerializers.void_(), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new DocLookupErrorException("2/paper/docs/users/remove", ex.getRequestId(), ex.getUserMessage(), (DocLookupError) ex.getErrorValue());
        }
    }

    public void docsUsersRemove(String docId, MemberSelector member) throws DocLookupErrorException, DbxException {
        docsUsersRemove(new RemovePaperDocUser(docId, member));
    }
}
