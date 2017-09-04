package com.box.androidsdk.content;

import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.requests.BoxRequestsUser.CreateEnterpriseUser;
import com.box.androidsdk.content.requests.BoxRequestsUser.DeleteEnterpriseUser;
import com.box.androidsdk.content.requests.BoxRequestsUser.GetEnterpriseUsers;
import com.box.androidsdk.content.requests.BoxRequestsUser.GetUserInfo;

public class BoxApiUser extends BoxApi {
    public BoxApiUser(BoxSession session) {
        super(session);
    }

    protected String getUsersUrl() {
        return String.format("%s/users", new Object[]{getBaseUri()});
    }

    protected String getUserInformationUrl(String id) {
        return String.format("%s/%s", new Object[]{getUsersUrl(), id});
    }

    public GetUserInfo getCurrentUserInfoRequest() {
        return new GetUserInfo(getUserInformationUrl("me"), this.mSession);
    }

    public GetUserInfo getUserInfoRequest(String id) {
        return new GetUserInfo(getUserInformationUrl(id), this.mSession);
    }

    public GetEnterpriseUsers getEnterpriseUsersRequest() {
        return new GetEnterpriseUsers(getUsersUrl(), this.mSession);
    }

    public CreateEnterpriseUser getCreateEnterpriseUserRequest(String login, String name) {
        return new CreateEnterpriseUser(getUsersUrl(), this.mSession, login, name);
    }

    public DeleteEnterpriseUser getDeleteEnterpriseUserRequest(String userId) {
        return new DeleteEnterpriseUser(getUserInformationUrl(userId), this.mSession, userId);
    }
}
