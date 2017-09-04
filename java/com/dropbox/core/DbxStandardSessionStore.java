package com.dropbox.core;

import javax.servlet.http.HttpSession;

public final class DbxStandardSessionStore implements DbxSessionStore {
    private final String key;
    private final HttpSession session;

    public DbxStandardSessionStore(HttpSession session, String key) {
        this.session = session;
        this.key = key;
    }

    public HttpSession getSession() {
        return this.session;
    }

    public String getKey() {
        return this.key;
    }

    public String get() {
        Object v = this.session.getAttribute(this.key);
        if (v instanceof String) {
            return (String) v;
        }
        return null;
    }

    public void set(String value) {
        this.session.setAttribute(this.key, value);
    }

    public void clear() {
        this.session.removeAttribute(this.key);
    }
}
