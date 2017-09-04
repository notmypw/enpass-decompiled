package com.dropbox.core.v2.sharing;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.sharing.ShareFolderArg.Builder;
import java.util.List;

public class ShareFolderBuilder {
    private final Builder _builder;
    private final DbxUserSharingRequests _client;

    ShareFolderBuilder(DbxUserSharingRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public ShareFolderBuilder withMemberPolicy(MemberPolicy memberPolicy) {
        this._builder.withMemberPolicy(memberPolicy);
        return this;
    }

    public ShareFolderBuilder withAclUpdatePolicy(AclUpdatePolicy aclUpdatePolicy) {
        this._builder.withAclUpdatePolicy(aclUpdatePolicy);
        return this;
    }

    public ShareFolderBuilder withSharedLinkPolicy(SharedLinkPolicy sharedLinkPolicy) {
        this._builder.withSharedLinkPolicy(sharedLinkPolicy);
        return this;
    }

    public ShareFolderBuilder withForceAsync(Boolean forceAsync) {
        this._builder.withForceAsync(forceAsync);
        return this;
    }

    public ShareFolderBuilder withActions(List<FolderAction> actions) {
        this._builder.withActions(actions);
        return this;
    }

    public ShareFolderBuilder withLinkSettings(LinkSettings linkSettings) {
        this._builder.withLinkSettings(linkSettings);
        return this;
    }

    public ShareFolderBuilder withViewerInfoPolicy(ViewerInfoPolicy viewerInfoPolicy) {
        this._builder.withViewerInfoPolicy(viewerInfoPolicy);
        return this;
    }

    public ShareFolderLaunch start() throws ShareFolderErrorException, DbxException {
        return this._client.shareFolder(this._builder.build());
    }
}
