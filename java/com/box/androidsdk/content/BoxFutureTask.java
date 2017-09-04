package com.box.androidsdk.content;

import com.box.androidsdk.content.models.BoxObject;
import com.box.androidsdk.content.requests.BoxRequest;
import com.box.androidsdk.content.requests.BoxResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class BoxFutureTask<E extends BoxObject> extends FutureTask<BoxResponse<E>> {
    protected ArrayList<OnCompletedListener<E>> mCompletedListeners = new ArrayList();
    protected final BoxRequest mRequest;

    class AnonymousClass1 implements Callable<BoxResponse<E>> {
        final /* synthetic */ BoxRequest val$request;

        AnonymousClass1(BoxRequest boxRequest) {
            this.val$request = boxRequest;
        }

        public BoxResponse<E> call() throws Exception {
            E ret = null;
            Exception ex = null;
            try {
                ret = this.val$request.send();
            } catch (Exception e) {
                ex = e;
            }
            return new BoxResponse(ret, ex, this.val$request);
        }
    }

    public interface OnCompletedListener<E extends BoxObject> {
        void onCompleted(BoxResponse<E> boxResponse);
    }

    public BoxFutureTask(Class<E> cls, BoxRequest request) {
        super(new AnonymousClass1(request));
        this.mRequest = request;
    }

    protected void done() {
        BoxResponse<E> response = null;
        Throwable ex = null;
        try {
            response = (BoxResponse) get();
        } catch (Throwable e) {
            ex = e;
        } catch (Throwable e2) {
            ex = e2;
        }
        if (ex != null) {
            response = new BoxResponse(null, new BoxException("Unable to retrieve response from FutureTask.", ex), this.mRequest);
        }
        Iterator it = getCompletionListeners().iterator();
        while (it.hasNext()) {
            ((OnCompletedListener) it.next()).onCompleted(response);
        }
    }

    public ArrayList<OnCompletedListener<E>> getCompletionListeners() {
        return this.mCompletedListeners;
    }

    public BoxFutureTask<E> addOnCompletedListener(OnCompletedListener<E> listener) {
        this.mCompletedListeners.add(listener);
        return this;
    }
}
