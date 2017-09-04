package com.dropbox.core.v2.users;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxWrappedException;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.v2.DbxRawClientV2;
import java.util.List;

public class DbxUserUsersRequests {
    private final DbxRawClientV2 client;

    public DbxUserUsersRequests(DbxRawClientV2 client) {
        this.client = client;
    }

    BasicAccount getAccount(GetAccountArg arg) throws GetAccountErrorException, DbxException {
        try {
            return (BasicAccount) this.client.rpcStyle(this.client.getHost().getApi(), "2/users/get_account", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new GetAccountErrorException("2/users/get_account", ex.getRequestId(), ex.getUserMessage(), (GetAccountError) ex.getErrorValue());
        }
    }

    public BasicAccount getAccount(String accountId) throws GetAccountErrorException, DbxException {
        return getAccount(new GetAccountArg(accountId));
    }

    List<BasicAccount> getAccountBatch(GetAccountBatchArg arg) throws GetAccountBatchErrorException, DbxException {
        try {
            return (List) this.client.rpcStyle(this.client.getHost().getApi(), "2/users/get_account_batch", arg, false, Serializer.INSTANCE, StoneSerializers.list(Serializer.INSTANCE), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new GetAccountBatchErrorException("2/users/get_account_batch", ex.getRequestId(), ex.getUserMessage(), (GetAccountBatchError) ex.getErrorValue());
        }
    }

    public List<BasicAccount> getAccountBatch(List<String> accountIds) throws GetAccountBatchErrorException, DbxException {
        return getAccountBatch(new GetAccountBatchArg(accountIds));
    }

    public FullAccount getCurrentAccount() throws DbxApiException, DbxException {
        try {
            return (FullAccount) this.client.rpcStyle(this.client.getHost().getApi(), "2/users/get_current_account", null, false, StoneSerializers.void_(), Serializer.INSTANCE, StoneSerializers.void_());
        } catch (DbxWrappedException ex) {
            throw new DbxApiException(ex.getRequestId(), ex.getUserMessage(), "Unexpected error response for \"get_current_account\":" + ex.getErrorValue());
        }
    }

    public SpaceUsage getSpaceUsage() throws DbxApiException, DbxException {
        try {
            return (SpaceUsage) this.client.rpcStyle(this.client.getHost().getApi(), "2/users/get_space_usage", null, false, StoneSerializers.void_(), Serializer.INSTANCE, StoneSerializers.void_());
        } catch (DbxWrappedException ex) {
            throw new DbxApiException(ex.getRequestId(), ex.getUserMessage(), "Unexpected error response for \"get_space_usage\":" + ex.getErrorValue());
        }
    }
}
