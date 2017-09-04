package com.box.androidsdk.content.requests;

import com.box.androidsdk.content.models.BoxObject;

public class BoxResponse<E extends BoxObject> {
    protected final Exception mException;
    protected final BoxRequest mRequest;
    protected final E mResult;

    public BoxResponse(E result, Exception ex, BoxRequest request) {
        this.mResult = result;
        this.mException = ex;
        this.mRequest = request;
    }

    public E getResult() {
        return this.mResult;
    }

    public Exception getException() {
        return this.mException;
    }

    public BoxRequest getRequest() {
        return this.mRequest;
    }

    public boolean isSuccess() {
        return this.mException == null;
    }
}
