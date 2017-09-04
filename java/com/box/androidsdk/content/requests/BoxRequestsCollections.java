package com.box.androidsdk.content.requests;

import com.box.androidsdk.content.models.BoxListCollections;
import com.box.androidsdk.content.models.BoxListItems;
import com.box.androidsdk.content.models.BoxSession;

public class BoxRequestsCollections {

    public static class GetCollectionItems extends BoxRequestList<BoxListItems, GetCollectionItems> {
        public GetCollectionItems(String collectionItemsUrl, BoxSession session) {
            super(BoxListItems.class, null, collectionItemsUrl, session);
        }
    }

    public static class GetCollections extends BoxRequestList<BoxListCollections, GetCollections> {
        public GetCollections(String collectionsUrl, BoxSession session) {
            super(BoxListCollections.class, null, collectionsUrl, session);
        }
    }
}
