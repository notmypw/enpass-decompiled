package com.box.androidsdk.content.auth;

import com.box.androidsdk.content.BoxApi;
import com.box.androidsdk.content.BoxConstants;
import com.box.androidsdk.content.BoxException;
import com.box.androidsdk.content.auth.BoxAuthentication.BoxAuthenticationInfo;
import com.box.androidsdk.content.models.BoxMDMData;
import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.requests.BoxRequest;
import com.box.androidsdk.content.requests.BoxRequest.ContentTypes;
import com.box.androidsdk.content.requests.BoxRequest.Methods;
import com.box.androidsdk.content.utils.SdkUtils;
import java.util.Locale;

class BoxApiAuthentication extends BoxApi {
    static final String GRANT_TYPE = "grant_type";
    static final String GRANT_TYPE_AUTH_CODE = "authorization_code";
    static final String GRANT_TYPE_REFRESH = "refresh_token";
    static final String OAUTH_TOKEN_REQUEST_URL = "%s/oauth2/token";
    static final String OAUTH_TOKEN_REVOKE_URL = "%s/oauth2/revoke";
    static final String REFRESH_TOKEN = "refresh_token";
    static final String RESPONSE_TYPE_BASE_DOMAIN = "base_domain";
    static final String RESPONSE_TYPE_CODE = "code";
    static final String RESPONSE_TYPE_ERROR = "error";

    static class BoxCreateAuthRequest extends BoxRequest<BoxAuthenticationInfo, BoxCreateAuthRequest> {
        public BoxCreateAuthRequest(BoxSession session, String requestUrl, String code, String clientId, String clientSecret) {
            super(BoxAuthenticationInfo.class, requestUrl, session);
            this.mRequestMethod = Methods.POST;
            setContentType(ContentTypes.URL_ENCODED);
            this.mBodyMap.put(BoxApiAuthentication.GRANT_TYPE, BoxApiAuthentication.GRANT_TYPE_AUTH_CODE);
            this.mBodyMap.put(BoxApiAuthentication.RESPONSE_TYPE_CODE, code);
            this.mBodyMap.put(BoxAuthenticationInfo.FIELD_CLIENT_ID, clientId);
            this.mBodyMap.put(BoxConstants.KEY_CLIENT_SECRET, clientSecret);
            if (session.getDeviceId() != null) {
                setDevice(session.getDeviceId(), session.getDeviceName());
            }
            if (session.getManagementData() != null) {
                setMdmData(session.getManagementData());
            }
            if (session.getRefreshTokenExpiresAt() != null) {
                setRefreshExpiresAt(session.getRefreshTokenExpiresAt().longValue());
            }
        }

        public BoxCreateAuthRequest setMdmData(BoxMDMData mdmData) {
            if (mdmData != null) {
                this.mBodyMap.put(BoxMDMData.BOX_MDM_DATA, mdmData.toJson());
            }
            return this;
        }

        public BoxCreateAuthRequest setDevice(String deviceId, String deviceName) {
            if (!SdkUtils.isEmptyString(deviceId)) {
                this.mBodyMap.put(BoxConstants.KEY_BOX_DEVICE_ID, deviceId);
            }
            if (!SdkUtils.isEmptyString(deviceName)) {
                this.mBodyMap.put(BoxConstants.KEY_BOX_DEVICE_NAME, deviceName);
            }
            return this;
        }

        public BoxCreateAuthRequest setRefreshExpiresAt(long expiresAt) {
            this.mBodyMap.put(BoxConstants.KEY_BOX_REFRESH_TOKEN_EXPIRES_AT, Long.toString(expiresAt));
            return this;
        }
    }

    static class BoxRefreshAuthRequest extends BoxRequest<BoxAuthenticationInfo, BoxRefreshAuthRequest> {
        public BoxRefreshAuthRequest(BoxSession session, String requestUrl, String refreshToken, String clientId, String clientSecret) {
            super(BoxAuthenticationInfo.class, requestUrl, session);
            this.mContentType = ContentTypes.URL_ENCODED;
            this.mRequestMethod = Methods.POST;
            this.mBodyMap.put(BoxApiAuthentication.GRANT_TYPE, BoxApiAuthentication.REFRESH_TOKEN);
            this.mBodyMap.put(BoxApiAuthentication.REFRESH_TOKEN, refreshToken);
            this.mBodyMap.put(BoxAuthenticationInfo.FIELD_CLIENT_ID, clientId);
            this.mBodyMap.put(BoxConstants.KEY_CLIENT_SECRET, clientSecret);
            if (session.getDeviceId() != null) {
                setDevice(session.getDeviceId(), session.getDeviceName());
            }
            if (session.getRefreshTokenExpiresAt() != null) {
                setRefreshExpiresAt(session.getRefreshTokenExpiresAt().longValue());
            }
        }

        public BoxRefreshAuthRequest setDevice(String deviceId, String deviceName) {
            if (!SdkUtils.isEmptyString(deviceId)) {
                this.mBodyMap.put(BoxConstants.KEY_BOX_DEVICE_ID, deviceId);
            }
            if (!SdkUtils.isEmptyString(deviceName)) {
                this.mBodyMap.put(BoxConstants.KEY_BOX_DEVICE_NAME, deviceName);
            }
            return this;
        }

        public BoxAuthenticationInfo send() throws BoxException {
            BoxAuthenticationInfo info = (BoxAuthenticationInfo) super.send();
            info.setUser(this.mSession.getUser());
            return info;
        }

        public BoxRefreshAuthRequest setRefreshExpiresAt(long expiresAt) {
            this.mBodyMap.put(BoxConstants.KEY_BOX_REFRESH_TOKEN_EXPIRES_AT, Long.toString(expiresAt));
            return this;
        }
    }

    static class BoxRevokeAuthRequest extends BoxRequest<BoxAuthenticationInfo, BoxRevokeAuthRequest> {
        public BoxRevokeAuthRequest(BoxSession session, String requestUrl, String token, String clientId, String clientSecret) {
            super(BoxAuthenticationInfo.class, requestUrl, session);
            this.mRequestMethod = Methods.POST;
            setContentType(ContentTypes.URL_ENCODED);
            this.mBodyMap.put(BoxAuthenticationInfo.FIELD_CLIENT_ID, clientId);
            this.mBodyMap.put(BoxConstants.KEY_CLIENT_SECRET, clientSecret);
            this.mBodyMap.put(BoxConstants.KEY_TOKEN, token);
        }
    }

    BoxApiAuthentication(BoxSession account) {
        super(account);
        this.mBaseUri = BoxConstants.OAUTH_BASE_URI;
    }

    protected String getBaseUri() {
        if (this.mSession == null || this.mSession.getAuthInfo() == null || this.mSession.getAuthInfo().getBaseDomain() == null) {
            return super.getBaseUri();
        }
        return String.format(BoxConstants.OAUTH_BASE_URI_TEMPLATE, new Object[]{this.mSession.getAuthInfo().getBaseDomain()});
    }

    BoxRefreshAuthRequest refreshOAuth(String refreshToken, String clientId, String clientSecret) {
        return new BoxRefreshAuthRequest(this.mSession, getTokenUrl(), refreshToken, clientId, clientSecret);
    }

    BoxCreateAuthRequest createOAuth(String code, String clientId, String clientSecret) {
        return new BoxCreateAuthRequest(this.mSession, getTokenUrl(), code, clientId, clientSecret);
    }

    BoxRevokeAuthRequest revokeOAuth(String token, String clientId, String clientSecret) {
        return new BoxRevokeAuthRequest(this.mSession, getTokenRevokeUrl(), token, clientId, clientSecret);
    }

    protected String getTokenUrl() {
        return String.format(Locale.ENGLISH, OAUTH_TOKEN_REQUEST_URL, new Object[]{getBaseUri()});
    }

    protected String getTokenRevokeUrl() {
        return String.format(Locale.ENGLISH, OAUTH_TOKEN_REVOKE_URL, new Object[]{getBaseUri()});
    }
}
