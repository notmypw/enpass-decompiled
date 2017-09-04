package com.box.androidsdk.content.requests;

import com.box.androidsdk.content.models.BoxListUsers;
import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.models.BoxUser;
import com.box.androidsdk.content.models.BoxUser.Role;
import com.box.androidsdk.content.models.BoxUser.Status;
import com.box.androidsdk.content.models.BoxVoid;
import com.box.androidsdk.content.requests.BoxRequest.Methods;
import com.eclipsesource.json.JsonObject;
import java.util.Map.Entry;

public class BoxRequestsUser {

    public static class CreateEnterpriseUser extends BoxRequestUserUpdate<BoxUser, CreateEnterpriseUser> {
        public /* bridge */ /* synthetic */ String getAddress() {
            return super.getAddress();
        }

        public /* bridge */ /* synthetic */ boolean getCanSeeManagedUsers() {
            return super.getCanSeeManagedUsers();
        }

        public /* bridge */ /* synthetic */ boolean getIsExemptFromDeviceLimits() {
            return super.getIsExemptFromDeviceLimits();
        }

        public /* bridge */ /* synthetic */ boolean getIsExemptFromLoginVerification() {
            return super.getIsExemptFromLoginVerification();
        }

        public /* bridge */ /* synthetic */ boolean getIsSyncEnabled() {
            return super.getIsSyncEnabled();
        }

        public /* bridge */ /* synthetic */ String getJobTitle() {
            return super.getJobTitle();
        }

        public /* bridge */ /* synthetic */ String getName() {
            return super.getName();
        }

        public /* bridge */ /* synthetic */ String getPhone() {
            return super.getPhone();
        }

        public /* bridge */ /* synthetic */ Role getRole() {
            return super.getRole();
        }

        public /* bridge */ /* synthetic */ double getSpaceAmount() {
            return super.getSpaceAmount();
        }

        public /* bridge */ /* synthetic */ Status getStatus() {
            return super.getStatus();
        }

        public /* bridge */ /* synthetic */ String getTimezone() {
            return super.getTimezone();
        }

        public CreateEnterpriseUser(String requestUrl, BoxSession session, String login, String name) {
            super(BoxUser.class, null, requestUrl, session);
            this.mRequestMethod = Methods.POST;
            setLogin(login);
            setName(name);
        }

        public String getLogin() {
            return (String) this.mBodyMap.get(BoxUser.FIELD_LOGIN);
        }

        public CreateEnterpriseUser setLogin(String login) {
            this.mBodyMap.put(BoxUser.FIELD_LOGIN, login);
            return this;
        }
    }

    public static class DeleteEnterpriseUser extends BoxRequest<BoxVoid, DeleteEnterpriseUser> {
        protected static final String QUERY_FORCE = "force";
        protected static final String QUERY_NOTIFY = "notify";
        protected String mId;

        public DeleteEnterpriseUser(String requestUrl, BoxSession session, String userId) {
            super(BoxVoid.class, requestUrl, session);
            this.mRequestMethod = Methods.DELETE;
            this.mId = userId;
        }

        public String getId() {
            return this.mId;
        }

        public Boolean getShouldNotify() {
            return Boolean.valueOf((String) this.mQueryMap.get(QUERY_NOTIFY));
        }

        public DeleteEnterpriseUser setShouldNotify(Boolean shouldNotify) {
            this.mQueryMap.put(QUERY_NOTIFY, Boolean.toString(shouldNotify.booleanValue()));
            return this;
        }

        public Boolean getShouldForce() {
            return Boolean.valueOf((String) this.mQueryMap.get(QUERY_FORCE));
        }

        public DeleteEnterpriseUser setShouldForce(Boolean shouldForce) {
            this.mQueryMap.put(QUERY_FORCE, Boolean.toString(shouldForce.booleanValue()));
            return this;
        }
    }

    public static class GetEnterpriseUsers extends BoxRequestItem<BoxListUsers, GetEnterpriseUsers> {
        protected static final String QUERY_FILTER_TERM = "filter_term";
        protected static final String QUERY_LIMIT = "limit";
        protected static final String QUERY_OFFSET = "offset";

        public GetEnterpriseUsers(String requestUrl, BoxSession session) {
            super(BoxListUsers.class, null, requestUrl, session);
            this.mRequestMethod = Methods.GET;
        }

        public String getFilterTerm() {
            return (String) this.mQueryMap.get(QUERY_FILTER_TERM);
        }

        public GetEnterpriseUsers setFilterTerm(String filterTerm) {
            this.mQueryMap.put(QUERY_FILTER_TERM, filterTerm);
            return this;
        }

        public long getLimit() {
            return Long.valueOf((String) this.mQueryMap.get(QUERY_LIMIT)).longValue();
        }

        public GetEnterpriseUsers setLimit(long limit) {
            this.mQueryMap.put(QUERY_LIMIT, Long.toString(limit));
            return this;
        }

        public long getOffset() {
            return Long.valueOf((String) this.mQueryMap.get(QUERY_OFFSET)).longValue();
        }

        public GetEnterpriseUsers setOffset(long offset) {
            this.mQueryMap.put(QUERY_OFFSET, Long.toString(offset));
            return this;
        }
    }

    public static class GetUserInfo extends BoxRequestItem<BoxUser, GetUserInfo> {
        public GetUserInfo(String requestUrl, BoxSession session) {
            super(BoxUser.class, null, requestUrl, session);
            this.mRequestMethod = Methods.GET;
        }
    }

    public static class UpdateUserInformation extends BoxRequestUserUpdate<BoxUser, UpdateUserInformation> {
        protected static final String FIELD_IS_PASSWORD_RESET_REQUIRED = "is_password_reset_required";

        public /* bridge */ /* synthetic */ String getAddress() {
            return super.getAddress();
        }

        public /* bridge */ /* synthetic */ boolean getCanSeeManagedUsers() {
            return super.getCanSeeManagedUsers();
        }

        public /* bridge */ /* synthetic */ boolean getIsExemptFromDeviceLimits() {
            return super.getIsExemptFromDeviceLimits();
        }

        public /* bridge */ /* synthetic */ boolean getIsExemptFromLoginVerification() {
            return super.getIsExemptFromLoginVerification();
        }

        public /* bridge */ /* synthetic */ boolean getIsSyncEnabled() {
            return super.getIsSyncEnabled();
        }

        public /* bridge */ /* synthetic */ String getJobTitle() {
            return super.getJobTitle();
        }

        public /* bridge */ /* synthetic */ String getName() {
            return super.getName();
        }

        public /* bridge */ /* synthetic */ String getPhone() {
            return super.getPhone();
        }

        public /* bridge */ /* synthetic */ Role getRole() {
            return super.getRole();
        }

        public /* bridge */ /* synthetic */ double getSpaceAmount() {
            return super.getSpaceAmount();
        }

        public /* bridge */ /* synthetic */ Status getStatus() {
            return super.getStatus();
        }

        public /* bridge */ /* synthetic */ String getTimezone() {
            return super.getTimezone();
        }

        public UpdateUserInformation(String requestUrl, BoxSession session, String login, String name) {
            super(BoxUser.class, null, requestUrl, session);
            this.mRequestMethod = Methods.PUT;
        }

        public String getEnterprise() {
            return (String) this.mBodyMap.get(BoxUser.FIELD_ENTERPRISE);
        }

        public UpdateUserInformation setEnterprise(String enterprise) {
            this.mBodyMap.put(BoxUser.FIELD_ENTERPRISE, enterprise);
            return this;
        }

        public String getIsPasswordResetRequired() {
            return (String) this.mBodyMap.get(FIELD_IS_PASSWORD_RESET_REQUIRED);
        }

        public UpdateUserInformation setIsPasswordResetRequired(boolean isPasswordResetRequired) {
            this.mBodyMap.put(FIELD_IS_PASSWORD_RESET_REQUIRED, Boolean.valueOf(isPasswordResetRequired));
            return this;
        }

        protected void parseHashMapEntry(JsonObject jsonBody, Entry<String, Object> entry) {
            if (((String) entry.getKey()).equals(BoxUser.FIELD_ENTERPRISE)) {
                String str;
                String str2 = (String) entry.getKey();
                if (entry.getValue() == null) {
                    str = null;
                } else {
                    str = (String) entry.getValue();
                }
                jsonBody.add(str2, str);
                return;
            }
            super.parseHashMapEntry(jsonBody, entry);
        }
    }
}
