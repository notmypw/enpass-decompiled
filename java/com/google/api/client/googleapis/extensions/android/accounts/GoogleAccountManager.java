package com.google.api.client.googleapis.extensions.android.accounts;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Preconditions;

@Beta
public final class GoogleAccountManager {
    public static final String ACCOUNT_TYPE = "com.google";
    private final AccountManager manager;

    public GoogleAccountManager(AccountManager accountManager) {
        this.manager = (AccountManager) Preconditions.checkNotNull(accountManager);
    }

    public GoogleAccountManager(Context context) {
        this(AccountManager.get(context));
    }

    public AccountManager getAccountManager() {
        return this.manager;
    }

    public Account[] getAccounts() {
        return this.manager.getAccountsByType(ACCOUNT_TYPE);
    }

    public Account getAccountByName(String accountName) {
        if (accountName != null) {
            for (Account account : getAccounts()) {
                if (accountName.equals(account.name)) {
                    return account;
                }
            }
        }
        return null;
    }

    public void invalidateAuthToken(String authToken) {
        this.manager.invalidateAuthToken(ACCOUNT_TYPE, authToken);
    }
}
