package com.dropbox.core.v2.auth;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxWrappedException;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.v2.DbxRawClientV2;

public class DbxUserAuthRequests {
    private final DbxRawClientV2 client;

    public DbxUserAuthRequests(DbxRawClientV2 client) {
        this.client = client;
    }

    public void tokenRevoke() throws DbxApiException, DbxException {
        try {
            this.client.rpcStyle(this.client.getHost().getApi(), "2/auth/token/revoke", null, false, StoneSerializers.void_(), StoneSerializers.void_(), StoneSerializers.void_());
        } catch (DbxWrappedException ex) {
            throw new DbxApiException(ex.getRequestId(), ex.getUserMessage(), "Unexpected error response for \"token/revoke\":" + ex.getErrorValue());
        }
    }
}
