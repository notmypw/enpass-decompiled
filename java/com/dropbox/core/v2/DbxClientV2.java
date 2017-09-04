package com.dropbox.core.v2;

import com.dropbox.core.DbxHost;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxRequestUtil;
import com.dropbox.core.http.HttpRequestor.Header;
import java.util.List;

public class DbxClientV2 extends DbxClientV2Base {

    private static final class DbxUserRawClientV2 extends DbxRawClientV2 {
        private final String accessToken;

        public DbxUserRawClientV2(DbxRequestConfig requestConfig, String accessToken, DbxHost host) {
            super(requestConfig, host);
            if (accessToken == null) {
                throw new NullPointerException("accessToken");
            }
            this.accessToken = accessToken;
        }

        protected void addAuthHeaders(List<Header> headers) {
            DbxRequestUtil.addAuthHeader(headers, this.accessToken);
        }
    }

    public DbxClientV2(DbxRequestConfig requestConfig, String accessToken) {
        this(requestConfig, accessToken, DbxHost.DEFAULT);
    }

    public DbxClientV2(DbxRequestConfig requestConfig, String accessToken, DbxHost host) {
        super(new DbxUserRawClientV2(requestConfig, accessToken, host));
    }

    DbxClientV2(DbxRawClientV2 client) {
        super(client);
    }
}
