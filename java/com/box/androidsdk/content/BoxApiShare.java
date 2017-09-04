package com.box.androidsdk.content;

import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.models.BoxSharedLinkSession;
import com.box.androidsdk.content.requests.BoxRequestsShare.GetSharedLink;

public class BoxApiShare extends BoxApi {
    protected String getSharedItemsUrl() {
        return String.format("%s/shared_items", new Object[]{getBaseUri()});
    }

    public BoxApiShare(BoxSession session) {
        super(session);
    }

    public GetSharedLink getSharedLinkRequest(String sharedLink) {
        BoxSharedLinkSession session = new BoxSharedLinkSession(sharedLink, this.mSession);
        session.setSharedLink(sharedLink);
        return new GetSharedLink(getSharedItemsUrl(), session);
    }
}
