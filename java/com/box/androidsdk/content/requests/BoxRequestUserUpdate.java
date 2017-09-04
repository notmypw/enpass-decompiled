package com.box.androidsdk.content.requests;

import com.box.androidsdk.content.models.BoxFileVersion;
import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.models.BoxUser;
import com.box.androidsdk.content.models.BoxUser.Role;
import com.box.androidsdk.content.models.BoxUser.Status;
import in.sinew.enpassengine.EnpassEngineConstants;

abstract class BoxRequestUserUpdate<E extends BoxUser, R extends BoxRequest<E, R>> extends BoxRequestItem<E, R> {
    public BoxRequestUserUpdate(Class<E> clazz, String id, String requestUrl, BoxSession session) {
        super(clazz, id, requestUrl, session);
    }

    public String getName() {
        return (String) this.mBodyMap.get(BoxFileVersion.FIELD_NAME);
    }

    public R setName(String name) {
        this.mBodyMap.put(BoxFileVersion.FIELD_NAME, name);
        return this;
    }

    public Role getRole() {
        return (Role) this.mBodyMap.get(BoxUser.FIELD_ROLE);
    }

    public R setRole(Role role) {
        this.mBodyMap.put(BoxUser.FIELD_ROLE, role);
        return this;
    }

    public boolean getIsSyncEnabled() {
        return ((Boolean) this.mBodyMap.get(BoxUser.FIELD_IS_SYNC_ENABLED)).booleanValue();
    }

    public R setIsSyncEnabled(boolean isSyncEnabled) {
        this.mBodyMap.put(BoxUser.FIELD_IS_SYNC_ENABLED, Boolean.valueOf(isSyncEnabled));
        return this;
    }

    public String getJobTitle() {
        return (String) this.mBodyMap.get(EnpassEngineConstants.CardFieldTypeJobTitle);
    }

    public R setJobTitle(String jobTitle) {
        this.mBodyMap.put(EnpassEngineConstants.CardFieldTypeJobTitle, jobTitle);
        return this;
    }

    public String getPhone() {
        return (String) this.mBodyMap.get(BoxUser.FIELD_PHONE);
    }

    public R setPhone(String phone) {
        this.mBodyMap.put(BoxUser.FIELD_PHONE, phone);
        return this;
    }

    public String getAddress() {
        return (String) this.mBodyMap.get(BoxUser.FIELD_ADDRESS);
    }

    public R setAddress(String address) {
        this.mBodyMap.put(BoxUser.FIELD_ADDRESS, address);
        return this;
    }

    public double getSpaceAmount() {
        return ((Double) this.mBodyMap.get(BoxUser.FIELD_SPACE_AMOUNT)).doubleValue();
    }

    public R setSpaceAmount(double spaceAmount) {
        this.mBodyMap.put(BoxUser.FIELD_SPACE_AMOUNT, Double.valueOf(spaceAmount));
        return this;
    }

    public boolean getCanSeeManagedUsers() {
        return ((Boolean) this.mBodyMap.get(BoxUser.FIELD_CAN_SEE_MANAGED_USERS)).booleanValue();
    }

    public R setCanSeeManagedUsers(boolean canSeeManagedUsers) {
        this.mBodyMap.put(BoxUser.FIELD_CAN_SEE_MANAGED_USERS, Boolean.valueOf(canSeeManagedUsers));
        return this;
    }

    public Status getStatus() {
        return (Status) this.mBodyMap.get(BoxUser.FIELD_STATUS);
    }

    public R setStatus(Status status) {
        this.mBodyMap.put(BoxUser.FIELD_STATUS, status);
        return this;
    }

    public String getTimezone() {
        return (String) this.mBodyMap.get(BoxUser.FIELD_TIMEZONE);
    }

    public R setTimezone(String timezone) {
        this.mBodyMap.put(BoxUser.FIELD_TIMEZONE, timezone);
        return this;
    }

    public boolean getIsExemptFromDeviceLimits() {
        return ((Boolean) this.mBodyMap.get(BoxUser.FIELD_IS_EXEMPT_FROM_DEVICE_LIMITS)).booleanValue();
    }

    public R setIsExemptFromDeviceLimits(boolean isExemptFromDeviceLimits) {
        this.mBodyMap.put(BoxUser.FIELD_IS_EXEMPT_FROM_DEVICE_LIMITS, Boolean.valueOf(isExemptFromDeviceLimits));
        return this;
    }

    public boolean getIsExemptFromLoginVerification() {
        return ((Boolean) this.mBodyMap.get(BoxUser.FIELD_IS_EXEMPT_FROM_LOGIN_VERIFICATION)).booleanValue();
    }

    public R setIsExemptFromLoginVerification(boolean isExemptFromLoginVerification) {
        this.mBodyMap.put(BoxUser.FIELD_IS_EXEMPT_FROM_LOGIN_VERIFICATION, Boolean.valueOf(isExemptFromLoginVerification));
        return this;
    }
}
