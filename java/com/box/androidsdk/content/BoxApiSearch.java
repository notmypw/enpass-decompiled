package com.box.androidsdk.content;

import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.requests.BoxRequestsSearch.Search;

public class BoxApiSearch extends BoxApi {
    public BoxApiSearch(BoxSession session) {
        super(session);
    }

    public Search getSearchRequest(String query) {
        return new Search(query, getSearchUrl(), this.mSession);
    }

    protected String getSearchUrl() {
        return String.format("%s/search", new Object[]{getBaseUri()});
    }
}
