package com.google.api.client.googleapis.extensions.android.gms.auth;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.google.api.client.googleapis.extensions.android.accounts.GoogleAccountManager;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import com.google.api.client.util.BackOff;
import com.google.api.client.util.BackOffUtils;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Joiner;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sleeper;
import java.io.IOException;
import java.util.Collection;

@Beta
public class GoogleAccountCredential implements HttpRequestInitializer {
    private final GoogleAccountManager accountManager;
    private String accountName;
    private BackOff backOff;
    final Context context;
    final String scope;
    private Account selectedAccount;
    private Sleeper sleeper = Sleeper.DEFAULT;

    @Beta
    class RequestHandler implements HttpExecuteInterceptor, HttpUnsuccessfulResponseHandler {
        boolean received401;
        String token;

        RequestHandler() {
        }

        public void intercept(HttpRequest request) throws IOException {
            try {
                this.token = GoogleAccountCredential.this.getToken();
                request.getHeaders().setAuthorization("Bearer " + this.token);
            } catch (GooglePlayServicesAvailabilityException e) {
                throw new GooglePlayServicesAvailabilityIOException(e);
            } catch (UserRecoverableAuthException e2) {
                throw new UserRecoverableAuthIOException(e2);
            } catch (GoogleAuthException e3) {
                throw new GoogleAuthIOException(e3);
            }
        }

        public boolean handleResponse(HttpRequest request, HttpResponse response, boolean supportsRetry) {
            if (response.getStatusCode() != HttpStatusCodes.STATUS_CODE_UNAUTHORIZED || this.received401) {
                return false;
            }
            this.received401 = true;
            GoogleAuthUtil.invalidateToken(GoogleAccountCredential.this.context, this.token);
            return true;
        }
    }

    public GoogleAccountCredential(Context context, String scope) {
        this.accountManager = new GoogleAccountManager(context);
        this.context = context;
        this.scope = scope;
    }

    public static GoogleAccountCredential usingOAuth2(Context context, Collection<String> scopes) {
        boolean z = scopes != null && scopes.iterator().hasNext();
        Preconditions.checkArgument(z);
        return new GoogleAccountCredential(context, "oauth2: " + Joiner.on(' ').join(scopes));
    }

    public static GoogleAccountCredential usingAudience(Context context, String audience) {
        Preconditions.checkArgument(audience.length() != 0);
        return new GoogleAccountCredential(context, "audience:" + audience);
    }

    public final GoogleAccountCredential setSelectedAccountName(String accountName) {
        this.selectedAccount = this.accountManager.getAccountByName(accountName);
        if (this.selectedAccount == null) {
            accountName = null;
        }
        this.accountName = accountName;
        return this;
    }

    public final GoogleAccountCredential setSelectedAccount(Account selectedAccount) {
        this.selectedAccount = selectedAccount;
        this.accountName = selectedAccount == null ? null : selectedAccount.name;
        return this;
    }

    public void initialize(HttpRequest request) {
        RequestHandler handler = new RequestHandler();
        request.setInterceptor(handler);
        request.setUnsuccessfulResponseHandler(handler);
    }

    public final Context getContext() {
        return this.context;
    }

    public final String getScope() {
        return this.scope;
    }

    public final GoogleAccountManager getGoogleAccountManager() {
        return this.accountManager;
    }

    public final Account[] getAllAccounts() {
        return this.accountManager.getAccounts();
    }

    public final Account getSelectedAccount() {
        return this.selectedAccount;
    }

    public BackOff getBackOff() {
        return this.backOff;
    }

    public GoogleAccountCredential setBackOff(BackOff backOff) {
        this.backOff = backOff;
        return this;
    }

    public final Sleeper getSleeper() {
        return this.sleeper;
    }

    public final GoogleAccountCredential setSleeper(Sleeper sleeper) {
        this.sleeper = (Sleeper) Preconditions.checkNotNull(sleeper);
        return this;
    }

    public final String getSelectedAccountName() {
        return this.accountName;
    }

    public final Intent newChooseAccountIntent() {
        return AccountPicker.newChooseAccountIntent(this.selectedAccount, null, new String[]{GoogleAccountManager.ACCOUNT_TYPE}, true, null, null, null, null);
    }

    public String getToken() throws IOException, GoogleAuthException {
        if (this.backOff != null) {
            this.backOff.reset();
        }
        while (true) {
            try {
                break;
            } catch (IOException e) {
                if (this.backOff == null || !BackOffUtils.next(this.sleeper, this.backOff)) {
                    throw e;
                }
            } catch (InterruptedException e2) {
            }
        }
        return GoogleAuthUtil.getToken(this.context, this.accountName, this.scope);
    }
}
