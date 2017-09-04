package com.box.androidsdk.content;

import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.requests.BoxRequestsEvent.EventRealTimeServerRequest;
import com.box.androidsdk.content.requests.BoxRequestsEvent.GetEnterpriseEvents;
import com.box.androidsdk.content.requests.BoxRequestsEvent.GetUserEvents;
import com.box.androidsdk.content.utils.RealTimeServerConnection;
import com.box.androidsdk.content.utils.RealTimeServerConnection.OnChangeListener;

public class BoxApiEvent extends BoxApi {
    public BoxApiEvent(BoxSession session) {
        super(session);
    }

    protected String getEventsUrl() {
        return getBaseUri() + "/events";
    }

    public GetUserEvents getUserEventsRequest() {
        return new GetUserEvents(getEventsUrl(), this.mSession);
    }

    public GetEnterpriseEvents getEnterpriseEventsRequest() {
        return new GetEnterpriseEvents(getEventsUrl(), this.mSession);
    }

    public RealTimeServerConnection getLongPollServerConnection(OnChangeListener changeListener) {
        return new RealTimeServerConnection(new EventRealTimeServerRequest(getEventsUrl(), this.mSession), changeListener, this.mSession);
    }
}
