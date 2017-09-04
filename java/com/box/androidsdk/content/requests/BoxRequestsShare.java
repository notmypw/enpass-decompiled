package com.box.androidsdk.content.requests;

import com.box.androidsdk.content.BoxConstants;
import com.box.androidsdk.content.BoxException;
import com.box.androidsdk.content.models.BoxBookmark;
import com.box.androidsdk.content.models.BoxCollaboration;
import com.box.androidsdk.content.models.BoxCollaboration.Role;
import com.box.androidsdk.content.models.BoxCollaboration.Status;
import com.box.androidsdk.content.models.BoxCollaborator;
import com.box.androidsdk.content.models.BoxComment;
import com.box.androidsdk.content.models.BoxEnterpriseEvent;
import com.box.androidsdk.content.models.BoxEntity;
import com.box.androidsdk.content.models.BoxFile;
import com.box.androidsdk.content.models.BoxFolder;
import com.box.androidsdk.content.models.BoxGroup;
import com.box.androidsdk.content.models.BoxItem;
import com.box.androidsdk.content.models.BoxListCollaborations;
import com.box.androidsdk.content.models.BoxMapJsonObject;
import com.box.androidsdk.content.models.BoxObject;
import com.box.androidsdk.content.models.BoxRealTimeServer;
import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.models.BoxSharedLinkSession;
import com.box.androidsdk.content.models.BoxUser;
import com.box.androidsdk.content.models.BoxVoid;
import com.box.androidsdk.content.requests.BoxRequest.BoxRequestHandler;
import com.box.androidsdk.content.requests.BoxRequest.ContentTypes;
import com.box.androidsdk.content.requests.BoxRequest.Methods;
import com.box.androidsdk.content.utils.SdkUtils;
import java.util.HashMap;

public class BoxRequestsShare {

    public static class AddCollaboration extends BoxRequest<BoxCollaboration, AddCollaboration> {
        public static final String ERROR_CODE_USER_ALREADY_COLLABORATOR = "user_already_collaborator";
        private final String mFolderId;

        public AddCollaboration(String url, String folderId, Role role, String userEmail, BoxSession session) {
            super(BoxCollaboration.class, url, session);
            this.mRequestMethod = Methods.POST;
            this.mFolderId = folderId;
            setFolder(folderId);
            setAccessibleBy(null, userEmail, BoxUser.TYPE);
            this.mBodyMap.put(BoxUser.FIELD_ROLE, role.toString());
        }

        public AddCollaboration(String url, String folderId, Role role, BoxCollaborator collaborator, BoxSession session) {
            super(BoxCollaboration.class, url, session);
            this.mRequestMethod = Methods.POST;
            this.mFolderId = folderId;
            setFolder(folderId);
            setAccessibleBy(collaborator.getId(), null, collaborator.getType());
            this.mBodyMap.put(BoxUser.FIELD_ROLE, role.toString());
        }

        public AddCollaboration notifyCollaborators(boolean notify) {
            this.mQueryMap.put("notify", Boolean.toString(notify));
            return this;
        }

        public String getFolderId() {
            return this.mFolderId;
        }

        private void setFolder(String id) {
            HashMap<String, Object> map = new HashMap();
            map.put(BoxEntity.FIELD_ID, id);
            map.put(BoxRealTimeServer.FIELD_TYPE, BoxFolder.TYPE);
            this.mBodyMap.put(BoxComment.FIELD_ITEM, new BoxMapJsonObject(map));
        }

        private void setAccessibleBy(String accessibleById, String accessibleByEmail, String accessibleByType) {
            BoxCollaborator collaborator;
            HashMap<String, Object> map = new HashMap();
            if (!SdkUtils.isEmptyString(accessibleById)) {
                map.put(BoxEntity.FIELD_ID, accessibleById);
            }
            if (!SdkUtils.isEmptyString(accessibleByEmail)) {
                map.put(BoxUser.FIELD_LOGIN, accessibleByEmail);
            }
            map.put(BoxRealTimeServer.FIELD_TYPE, accessibleByType);
            if (accessibleByType.equals(BoxUser.TYPE)) {
                collaborator = new BoxUser(map);
            } else if (accessibleByType.equals(BoxGroup.TYPE)) {
                collaborator = new BoxGroup(map);
            } else {
                throw new IllegalArgumentException("AccessibleBy property can only be set with type BoxUser.TYPE or BoxGroup.TYPE");
            }
            this.mBodyMap.put(BoxEnterpriseEvent.FIELD_ACCESSIBLE_BY, collaborator);
        }

        public BoxCollaborator getAccessibleBy() {
            return this.mBodyMap.containsKey(BoxEnterpriseEvent.FIELD_ACCESSIBLE_BY) ? (BoxCollaborator) this.mBodyMap.get(BoxEnterpriseEvent.FIELD_ACCESSIBLE_BY) : null;
        }
    }

    public static class DeleteCollaboration extends BoxRequest<BoxVoid, DeleteCollaboration> {
        private String mId;

        public DeleteCollaboration(String collaborationId, String requestUrl, BoxSession session) {
            super(BoxVoid.class, requestUrl, session);
            this.mId = collaborationId;
            this.mRequestMethod = Methods.DELETE;
        }

        public String getId() {
            return this.mId;
        }
    }

    public static class GetCollaborationInfo extends BoxRequest<BoxCollaboration, GetCollaborationInfo> {
        private final String mId;

        public GetCollaborationInfo(String collaborationId, String requestUrl, BoxSession session) {
            super(BoxCollaboration.class, requestUrl, session);
            this.mRequestMethod = Methods.GET;
            this.mId = collaborationId;
        }

        public String getId() {
            return this.mId;
        }
    }

    public static class GetPendingCollaborations extends BoxRequest<BoxListCollaborations, GetPendingCollaborations> {
        public GetPendingCollaborations(String requestUrl, BoxSession session) {
            super(BoxListCollaborations.class, requestUrl, session);
            this.mRequestMethod = Methods.GET;
            this.mQueryMap.put(BoxUser.FIELD_STATUS, Status.PENDING.toString());
        }
    }

    public static class GetSharedLink extends BoxRequest<BoxItem, GetSharedLink> {
        public GetSharedLink(String requestUrl, BoxSharedLinkSession session) {
            super(BoxItem.class, requestUrl, session);
            this.mRequestMethod = Methods.GET;
            setRequestHandler(new BoxRequestHandler<GetSharedLink>(this) {
                public <T extends BoxObject> T onResponse(Class<T> cls, BoxHttpResponse response) throws BoxException {
                    if (response.getResponseCode() == BoxConstants.HTTP_STATUS_TOO_MANY_REQUESTS) {
                        return retryRateLimited(response);
                    }
                    String contentType = response.getContentType();
                    T entity = new BoxEntity();
                    if (!contentType.contains(ContentTypes.JSON.toString())) {
                        return entity;
                    }
                    String json = response.getStringBody();
                    entity.createFromJson(json);
                    if (entity.getType().equals(BoxFolder.TYPE)) {
                        entity = new BoxFolder();
                        entity.createFromJson(json);
                        return entity;
                    } else if (entity.getType().equals(BoxFile.TYPE)) {
                        entity = new BoxFile();
                        entity.createFromJson(json);
                        return entity;
                    } else if (!entity.getType().equals(BoxBookmark.TYPE)) {
                        return entity;
                    } else {
                        entity = new BoxBookmark();
                        entity.createFromJson(json);
                        return entity;
                    }
                }
            });
        }

        public GetSharedLink setIfNoneMatchEtag(String etag) {
            return (GetSharedLink) super.setIfNoneMatchEtag(etag);
        }

        public String getIfNoneMatchEtag() {
            return super.getIfNoneMatchEtag();
        }
    }

    public static class UpdateCollaboration extends BoxRequest<BoxCollaboration, UpdateCollaboration> {
        private String mId;

        public UpdateCollaboration(String collaborationId, String requestUrl, BoxSession session) {
            super(BoxCollaboration.class, requestUrl, session);
            this.mId = collaborationId;
            this.mRequestMethod = Methods.PUT;
        }

        public String getId() {
            return this.mId;
        }

        public UpdateCollaboration setNewRole(Role newRole) {
            this.mBodyMap.put(BoxUser.FIELD_ROLE, newRole.toString());
            return this;
        }

        public UpdateCollaboration setNewStatus(String status) {
            this.mBodyMap.put(BoxUser.FIELD_STATUS, status);
            return this;
        }
    }
}
