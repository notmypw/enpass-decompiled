package com.box.androidsdk.content.models;

import java.util.ArrayList;

public class BoxSharedLinkSession extends BoxSession {
    protected ArrayList<OnSharedLinkResponseListener> mListeners = new ArrayList();
    String mPassword;
    String mSharedLink;

    public interface OnSharedLinkResponseListener {
        void onResponse(String str, String str2, Exception exception);
    }

    public BoxSharedLinkSession(String sharedLink, BoxSession session) {
        super(session);
        this.mSharedLink = sharedLink;
    }

    public String getSharedLink() {
        return this.mSharedLink;
    }

    public BoxSharedLinkSession setSharedLink(String sharedLink) {
        this.mSharedLink = sharedLink;
        return this;
    }

    public String getPassword() {
        return this.mPassword;
    }

    public BoxSharedLinkSession setPassword(String password) {
        this.mPassword = password;
        return this;
    }

    public synchronized BoxSharedLinkSession addOnSharedLinkResponseListener(OnSharedLinkResponseListener listener) {
        this.mListeners.add(listener);
        return this;
    }
}
