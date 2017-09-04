package com.dropbox.core.v2.sharing;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.sharing.UpdateFolderPolicyArg.Builder;

public class UpdateFolderPolicyBuilder {
    private final Builder _builder;
    private final DbxUserSharingRequests _client;

    UpdateFolderPolicyBuilder(DbxUserSharingRequests _client, Builder _builder) {
        if (_client == null) {
            throw new NullPointerException("_client");
        }
        this._client = _client;
        if (_builder == null) {
            throw new NullPointerException("_builder");
        }
        this._builder = _builder;
    }

    public UpdateFolderPolicyBuilder withMemberPolicy(MemberPolicy memberPolicy) {
        this._builder.withMemberPolicy(memberPolicy);
        return this;
    }

    public UpdateFolderPolicyBuilder withAclUpdatePolicy(AclUpdatePolicy aclUpdatePolicy) {
        this._builder.withAclUpdatePolicy(aclUpdatePolicy);
        return this;
    }

    public UpdateFolderPolicyBuilder withViewerInfoPolicy(ViewerInfoPolicy viewerInfoPolicy) {
        this._builder.withViewerInfoPolicy(viewerInfoPolicy);
        return this;
    }

    public UpdateFolderPolicyBuilder withSharedLinkPolicy(SharedLinkPolicy sharedLinkPolicy) {
        this._builder.withSharedLinkPolicy(sharedLinkPolicy);
        return this;
    }

    public UpdateFolderPolicyBuilder withLinkSettings(LinkSettings linkSettings) {
        this._builder.withLinkSettings(linkSettings);
        return this;
    }

    public SharedFolderMetadata start() throws UpdateFolderPolicyErrorException, DbxException {
        return this._client.updateFolderPolicy(this._builder.build());
    }
}
