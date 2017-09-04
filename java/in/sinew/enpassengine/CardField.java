package in.sinew.enpassengine;

import com.github.clans.fab.BuildConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CardField {
    private boolean isDeleted;
    private boolean isMetaModified;
    private String mLabel;
    List<CardFieldHistory> mPassowrdHistoryList;
    private Boolean mSensitive;
    private Date mTimestamp;
    private String mType;
    private int mUid;
    private StringBuilder mValue;
    private String mtemplabel;

    public enum CardFieldType {
        CardFieldTypeText,
        CardFieldTypePassword,
        CardFieldTypePin,
        CardFieldTypeNumeric,
        CardFieldTypeDate,
        CardFieldTypeEmail,
        CardFieldTypeUrl,
        CardFieldTypePhone,
        CardFieldTypeSeperator,
        CardFieldTypeNote,
        CardFieldTypeNone,
        CardFieldTypeUsername,
        CardFieldTypeCardPin,
        CardFieldTypeTxnPassword,
        CardFieldTypeTOTP
    }

    public CardField(int aUid, Date aTimestamp, String aLabel, StringBuilder aValue, boolean aSensitive, String aType, boolean deleted) {
        this.mUid = aUid;
        this.mTimestamp = aTimestamp;
        this.mLabel = aLabel;
        setValue(aValue);
        this.mSensitive = Boolean.valueOf(aSensitive);
        this.mType = aType;
        this.isDeleted = deleted;
    }

    public int getUid() {
        return this.mUid;
    }

    public void setUid(int mUid) {
        this.mUid = mUid;
    }

    public Date getTimestamp() {
        return this.mTimestamp;
    }

    public void setTimestamp(Date mTimestamp) {
        this.mTimestamp = mTimestamp;
    }

    public String getLabel() {
        return this.mLabel;
    }

    public void setLabel(String mLabel) {
        this.mLabel = mLabel;
    }

    public StringBuilder getValue() {
        return this.mValue;
    }

    public void setValue(StringBuilder mValue) {
        Utils.wipeString(this.mValue);
        this.mValue = mValue;
    }

    public String getType() {
        return this.mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public Boolean isSensitive() {
        return this.mSensitive;
    }

    public void setSensitive(boolean aSensitive) {
        this.mSensitive = Boolean.valueOf(aSensitive);
    }

    public String getTempLabel() {
        return this.mtemplabel;
    }

    public void setTempLabel(String mtemplabel) {
        this.mtemplabel = mtemplabel;
    }

    public boolean getMetaModified() {
        return this.isMetaModified;
    }

    public void setMetaModified(boolean isMetaModified) {
        this.isMetaModified = isMetaModified;
    }

    public boolean isDeleted() {
        return this.isDeleted;
    }

    public void setDeleted(boolean deleted) {
        this.isDeleted = deleted;
    }

    public void setPasswordHistoryList(List<CardFieldHistory> historyList) {
        this.mPassowrdHistoryList = historyList;
    }

    public List<CardFieldHistory> getPasswordHistoryList() {
        return this.mPassowrdHistoryList;
    }

    public void updateWithUid(int aUid, Date aTimestamp, String aLabel, StringBuilder aValue, boolean aSensitive, String aType) {
        this.mUid = aUid;
        this.mTimestamp = aTimestamp;
        this.mLabel = aLabel;
        setValue(aValue);
        this.mSensitive = Boolean.valueOf(aSensitive);
        this.mType = aType;
    }

    public void wipe() {
        Utils.wipeString(this.mValue);
    }

    public CardField copyAsTemplateField() {
        return new CardField(this.mUid, new Date(), this.mLabel, new StringBuilder(this.mValue), this.mSensitive.booleanValue(), this.mType, this.isDeleted);
    }

    public CardField copy() {
        return new CardField(this.mUid, this.mTimestamp, this.mLabel, this.mValue, this.mSensitive.booleanValue(), this.mType, this.isDeleted);
    }

    public String toString() {
        return "\nField :: " + this.mUid + "  " + this.mTimestamp + "  " + this.mLabel + "  " + this.mValue + "  " + this.mSensitive + "  " + this.mType + "\n";
    }

    public boolean foundText(String searchingText, Locale currentLocale) {
        Locale current = currentLocale;
        String text = searchingText.toLowerCase(current);
        if (!this.isDeleted && isOptionalField()) {
            try {
                if (this.mValue.toString().split("-")[1].toLowerCase(current).contains(text) || this.mLabel.toLowerCase(current).contains(text)) {
                    return true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } else if (!this.isDeleted && this.mValue.toString().toLowerCase(current).contains(text)) {
            return true;
        } else {
            if (!(this.isDeleted || this.mLabel.equals(BuildConfig.FLAVOR) || !this.mLabel.toLowerCase(current).contains(text) || this.mValue.toString().equals(BuildConfig.FLAVOR))) {
                return true;
            }
        }
        return false;
    }

    public boolean isOptionalField() {
        if (this.mType.contains("_options")) {
            return true;
        }
        return false;
    }

    public boolean addHistoryToList(CardFieldHistory newPasswordMeta) {
        String newValue = newPasswordMeta.getValue();
        Date newTimeStamp = newPasswordMeta.getTimeStamp();
        if (this.mPassowrdHistoryList == null) {
            this.mPassowrdHistoryList = new ArrayList();
            this.mPassowrdHistoryList.add(0, newPasswordMeta);
            return false;
        }
        boolean alreadyExist = false;
        for (CardFieldHistory passwordmeta : this.mPassowrdHistoryList) {
            if (passwordmeta.getValue().equals(newValue) && passwordmeta.getTimeStamp().equals(newTimeStamp)) {
                alreadyExist = true;
                break;
            }
        }
        if (alreadyExist) {
            return true;
        }
        this.mPassowrdHistoryList.add(0, newPasswordMeta);
        return false;
    }

    void syncHistoryWithField(CardField aOldField) {
        int count = 0;
        if (this.mPassowrdHistoryList != null) {
            count = this.mPassowrdHistoryList.size();
        }
        int otherCount = 0;
        if (aOldField.getPasswordHistoryList() != null) {
            otherCount = aOldField.getPasswordHistoryList().size();
        }
        if (count != 0 || otherCount != 0) {
            if (count == 0 && otherCount != 0) {
                this.mPassowrdHistoryList = new ArrayList();
                this.mPassowrdHistoryList.addAll(aOldField.getPasswordHistoryList());
            } else if (!(count == 0 || otherCount == 0)) {
                for (int i = 0; i < count; i++) {
                    CardFieldHistory fieldhistory = (CardFieldHistory) this.mPassowrdHistoryList.get(i);
                    if (aOldField.getPasswordHistoryList().indexOf(fieldhistory) != -1) {
                        aOldField.getPasswordHistoryList().remove(fieldhistory);
                    }
                }
                this.mPassowrdHistoryList.addAll(aOldField.getPasswordHistoryList());
            }
            Collections.sort(this.mPassowrdHistoryList);
        }
    }
}
