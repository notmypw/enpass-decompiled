package com.box.androidsdk.content.requests;

import com.box.androidsdk.content.BoxException;
import com.box.androidsdk.content.models.BoxObject;
import java.util.ArrayList;
import java.util.Iterator;

public class BoxRequestBatch extends BoxRequest<BoxResponseBatch, BoxRequestBatch> {
    protected ArrayList<BoxRequest> mRequests = new ArrayList();

    public BoxRequestBatch() {
        super(BoxResponseBatch.class, null, null);
    }

    public BoxRequestBatch addRequest(BoxRequest request) {
        this.mRequests.add(request);
        return this;
    }

    public BoxResponseBatch send() throws BoxException {
        BoxResponseBatch responses = new BoxResponseBatch();
        Iterator it = this.mRequests.iterator();
        while (it.hasNext()) {
            BoxRequest req = (BoxRequest) it.next();
            BoxObject value = null;
            Exception ex = null;
            try {
                value = req.send();
            } catch (Exception e) {
                ex = e;
            }
            responses.addResponse(new BoxResponse(value, ex, req));
        }
        return responses;
    }
}
