package com.dropbox.core.v2;

import com.dropbox.core.DbxHost;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxRequestUtil;
import com.dropbox.core.http.HttpRequestor.Header;
import java.util.List;

public class DbxTeamClientV2 extends DbxTeamClientV2Base {
    private final String accessToken;

    private static final class DbxTeamRawClientV2 extends DbxRawClientV2 {
        private final String accessToken;
        private final String memberId;

        private DbxTeamRawClientV2(DbxRequestConfig requestConfig, DbxHost host, String accessToken) {
            this(requestConfig, host, accessToken, null);
        }

        private DbxTeamRawClientV2(DbxRequestConfig requestConfig, DbxHost host, String accessToken, String memberId) {
            super(requestConfig, host);
            if (accessToken == null) {
                throw new NullPointerException("accessToken");
            }
            this.accessToken = accessToken;
            this.memberId = memberId;
        }

        protected void addAuthHeaders(List<Header> headers) {
            DbxRequestUtil.addAuthHeader(headers, this.accessToken);
            if (this.memberId != null) {
                DbxRequestUtil.addSelectUserHeader(headers, this.memberId);
            }
        }
    }

    public DbxTeamClientV2(DbxRequestConfig requestConfig, String accessToken) {
        this(requestConfig, accessToken, DbxHost.DEFAULT);
    }

    public DbxTeamClientV2(DbxRequestConfig requestConfig, String accessToken, DbxHost host) {
        super(new DbxTeamRawClientV2(requestConfig, host, accessToken));
        this.accessToken = accessToken;
    }

    public DbxClientV2 asMember(String memberId) {
        if (memberId != null) {
            return new DbxClientV2(new DbxTeamRawClientV2(this._client.getRequestConfig(), this._client.getHost(), this.accessToken, memberId));
        }
        throw new IllegalArgumentException("'memberId' should not be null");
    }
}
