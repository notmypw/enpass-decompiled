package com.box.androidsdk.content;

import com.box.androidsdk.content.models.BoxCollaboration.Role;
import com.box.androidsdk.content.models.BoxCollaborator;
import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.requests.BoxRequestsShare.AddCollaboration;
import com.box.androidsdk.content.requests.BoxRequestsShare.DeleteCollaboration;
import com.box.androidsdk.content.requests.BoxRequestsShare.GetCollaborationInfo;
import com.box.androidsdk.content.requests.BoxRequestsShare.GetPendingCollaborations;
import com.box.androidsdk.content.requests.BoxRequestsShare.UpdateCollaboration;

public class BoxApiCollaboration extends BoxApi {
    protected String getCollaborationsUrl() {
        return String.format("%s/collaborations", new Object[]{getBaseUri()});
    }

    protected String getCollaborationInfoUrl(String id) {
        return String.format("%s/%s", new Object[]{getCollaborationsUrl(), id});
    }

    public BoxApiCollaboration(BoxSession session) {
        super(session);
    }

    public GetCollaborationInfo getInfoRequest(String collaborationId) {
        return new GetCollaborationInfo(collaborationId, getCollaborationInfoUrl(collaborationId), this.mSession);
    }

    public AddCollaboration getAddRequest(String folderId, Role role, BoxCollaborator collaborator) {
        return new AddCollaboration(getCollaborationsUrl(), folderId, role, collaborator, this.mSession);
    }

    public AddCollaboration getAddRequest(String folderId, Role role, String login) {
        return new AddCollaboration(getCollaborationsUrl(), folderId, role, login, this.mSession);
    }

    public GetPendingCollaborations getPendingCollaborationsRequest() {
        return new GetPendingCollaborations(getCollaborationsUrl(), this.mSession);
    }

    public DeleteCollaboration getDeleteRequest(String collaborationId) {
        return new DeleteCollaboration(collaborationId, getCollaborationInfoUrl(collaborationId), this.mSession);
    }

    public UpdateCollaboration getUpdateRequest(String collaborationId) {
        return new UpdateCollaboration(collaborationId, getCollaborationInfoUrl(collaborationId), this.mSession);
    }
}
