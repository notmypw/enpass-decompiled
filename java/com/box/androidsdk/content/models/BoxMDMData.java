package com.box.androidsdk.content.models;

import java.util.Map;

public class BoxMDMData extends BoxJsonObject {
    public static final String BILLING_ID = "billing_id";
    public static final String BOX_MDM_DATA = "box_mdm_data";
    public static final String BUNDLE_ID = "bundle_id";
    public static final String EMAIL_ID = "email_id";
    public static final String MANAGEMENT_ID = "management_id";
    public static final String PUBLIC_ID = "public_id";

    public BoxMDMData(Map<String, Object> map) {
        super(map);
    }

    public Object getValue(String key) {
        return this.mProperties.get(key);
    }

    public void setValue(String key, String value) {
        this.mProperties.put(key, value);
    }

    public void setBundleId(String bundleId) {
        setValue(BUNDLE_ID, bundleId);
    }

    public void setPublicId(String publicId) {
        setValue(PUBLIC_ID, publicId);
    }

    public void setManagementId(String managementId) {
        setValue(MANAGEMENT_ID, managementId);
    }

    public void setEmailId(String emailId) {
        setValue(EMAIL_ID, emailId);
    }

    public void setBillingId(String billingId) {
        setValue(BILLING_ID, billingId);
    }

    public String getBundleId() {
        return (String) getValue(PUBLIC_ID);
    }

    public String getPublicId() {
        return (String) getValue(PUBLIC_ID);
    }

    public String getManagementId() {
        return (String) getValue(MANAGEMENT_ID);
    }

    public String getEmailId() {
        return (String) getValue(EMAIL_ID);
    }

    public String getBillingIdId() {
        return (String) getValue(BILLING_ID);
    }
}
