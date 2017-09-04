package com.box.androidsdk.content.requests;

import com.box.androidsdk.content.models.BoxListEnterpriseEvents;
import com.box.androidsdk.content.models.BoxListEvents;
import com.box.androidsdk.content.models.BoxListRealTimeServers;
import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.models.BoxSimpleMessage;
import com.box.androidsdk.content.requests.BoxRequest.Methods;
import com.box.androidsdk.content.utils.BoxDateFormat;
import java.util.Date;

public class BoxRequestsEvent {

    public static class EventRealTimeServerRequest extends BoxRequest<BoxListRealTimeServers, EventRealTimeServerRequest> {
        public EventRealTimeServerRequest(String requestUrl, BoxSession session) {
            super(BoxListRealTimeServers.class, requestUrl, session);
            this.mRequestUrlString = requestUrl;
            this.mRequestMethod = Methods.OPTIONS;
        }
    }

    public static class GetEnterpriseEvents extends BoxRequestEvent<BoxListEnterpriseEvents, GetEnterpriseEvents> {
        public static final String FIELD_CREATED_AFTER = "created_after";
        public static final String FIELD_CREATED_BEFORE = "created_before";
        protected static final String STREAM_TYPE = "admin_logs";

        public GetEnterpriseEvents(String requestUrl, BoxSession session) {
            super(BoxListEnterpriseEvents.class, requestUrl, session);
            setStreamType(STREAM_TYPE);
        }

        public GetEnterpriseEvents setCreatedAfter(Date date) {
            this.mQueryMap.put(FIELD_CREATED_AFTER, BoxDateFormat.format(date));
            return this;
        }

        public GetEnterpriseEvents setCreatedBefore(Date date) {
            this.mQueryMap.put(FIELD_CREATED_BEFORE, BoxDateFormat.format(date));
            return this;
        }
    }

    public static class GetUserEvents extends BoxRequestEvent<BoxListEvents, GetUserEvents> {
        public GetUserEvents(String requestUrl, BoxSession session) {
            super(BoxListEvents.class, requestUrl, session);
        }

        public GetUserEvents setStreamType(String streamType) {
            return (GetUserEvents) super.setStreamType(streamType);
        }
    }

    public static class LongPollMessageRequest extends BoxRequest<BoxSimpleMessage, LongPollMessageRequest> {
        public LongPollMessageRequest(String requestUrl, BoxSession session) {
            super(BoxSimpleMessage.class, requestUrl, session);
            this.mRequestUrlString = requestUrl;
            this.mRequestMethod = Methods.GET;
        }
    }
}
