package com.dropbox.core.v2.auth;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxWrappedException;
import com.dropbox.core.v2.DbxRawClientV2;

public class DbxAppAuthRequests {
    private final DbxRawClientV2 client;

    public DbxAppAuthRequests(DbxRawClientV2 client) {
        this.client = client;
    }

    TokenFromOAuth1Result tokenFromOauth1(TokenFromOAuth1Arg arg) throws TokenFromOAuth1ErrorException, DbxException {
        try {
            return (TokenFromOAuth1Result) this.client.rpcStyle(this.client.getHost().getApi(), "2/auth/token/from_oauth1", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new TokenFromOAuth1ErrorException("2/auth/token/from_oauth1", ex.getRequestId(), ex.getUserMessage(), (TokenFromOAuth1Error) ex.getErrorValue());
        }
    }

    public TokenFromOAuth1Result tokenFromOauth1(String oauth1Token, String oauth1TokenSecret) throws TokenFromOAuth1ErrorException, DbxException {
        return tokenFromOauth1(new TokenFromOAuth1Arg(oauth1Token, oauth1TokenSecret));
    }
}
