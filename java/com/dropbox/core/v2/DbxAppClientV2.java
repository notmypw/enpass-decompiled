package com.dropbox.core.v2;

import com.dropbox.core.DbxHost;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxRequestUtil;
import com.dropbox.core.http.HttpRequestor.Header;
import java.util.List;

class DbxAppClientV2 extends DbxAppClientV2Base {

    private static final class DbxAppRawClientV2 extends DbxRawClientV2 {
        private final String key;
        private final String secret;

        private DbxAppRawClientV2(DbxRequestConfig requestConfig, String key, String secret, DbxHost host) {
            super(requestConfig, host);
            this.key = key;
            this.secret = secret;
        }

        protected void addAuthHeaders(List<Header> headers) {
            DbxRequestUtil.addBasicAuthHeader(headers, this.key, this.secret);
        }
    }

    public DbxAppClientV2(DbxRequestConfig requestConfig, String key, String secret) {
        this(requestConfig, key, secret, DbxHost.DEFAULT);
    }

    public DbxAppClientV2(DbxRequestConfig requestConfig, String key, String secret, DbxHost host) {
        super(new DbxAppRawClientV2(requestConfig, key, secret, host));
    }
}
