package com.box.androidsdk.content.models;

import com.box.androidsdk.content.utils.BoxDateFormat;
import com.box.androidsdk.content.utils.SdkUtils;
import java.io.File;
import java.util.Date;

public class BoxDownload extends BoxJsonObject {
    private static final String FIELD_CONTENT_LENGTH = "content_length";
    private static final String FIELD_CONTENT_TYPE = "content_type";
    private static final String FIELD_DATE = "date";
    private static final String FIELD_END_RANGE = "end_range";
    private static final String FIELD_EXPIRATION = "expiration";
    private static final String FIELD_FILE_NAME = "file_name";
    private static final String FIELD_START_RANGE = "start_range";
    private static final String FIELD_TOTAL_RANGE = "total_range";

    public BoxDownload(String contentDisposition, long contentLength, String contentType, String contentRange, String date, String expirationDate) {
        if (!SdkUtils.isEmptyString(contentDisposition)) {
            setFileName(contentDisposition);
        }
        this.mProperties.put(FIELD_CONTENT_LENGTH, Long.valueOf(contentLength));
        if (!SdkUtils.isEmptyString(contentType)) {
            this.mProperties.put(FIELD_CONTENT_TYPE, contentType);
        }
        if (!SdkUtils.isEmptyString(contentRange)) {
            setContentRange(contentRange);
        }
        if (!SdkUtils.isEmptyString(date)) {
            this.mProperties.put(FIELD_DATE, parseDate(date));
        }
        if (!SdkUtils.isEmptyString(expirationDate)) {
            this.mProperties.put(FIELD_EXPIRATION, parseDate(expirationDate));
        }
    }

    protected void setFileName(String contentDisposition) {
        for (String disposition : contentDisposition.split(";")) {
            if (disposition.startsWith("filename=")) {
                String fileName;
                if (disposition.endsWith("\"")) {
                    fileName = disposition.substring(disposition.indexOf("\"") + 1, disposition.length() - 1);
                } else {
                    fileName = disposition.substring(9);
                }
                this.mProperties.put(FIELD_FILE_NAME, fileName);
            }
        }
    }

    protected void setContentRange(String contentRange) {
        int slashPos = contentRange.lastIndexOf("/");
        int dashPos = contentRange.indexOf("-");
        this.mProperties.put(FIELD_START_RANGE, Long.valueOf(Long.parseLong(contentRange.substring(contentRange.indexOf("bytes") + 6, dashPos))));
        this.mProperties.put(FIELD_END_RANGE, Long.valueOf(Long.parseLong(contentRange.substring(dashPos + 1, slashPos))));
        this.mProperties.put(FIELD_TOTAL_RANGE, Long.valueOf(Long.parseLong(contentRange.substring(slashPos + 1))));
    }

    public String getFileName() {
        return (String) this.mProperties.get(FIELD_FILE_NAME);
    }

    public File getOutputFile() {
        return null;
    }

    public Long getContentLength() {
        return (Long) this.mProperties.get(FIELD_CONTENT_LENGTH);
    }

    public String getContentType() {
        return (String) this.mProperties.get(FIELD_CONTENT_TYPE);
    }

    public Long getStartRange() {
        return (Long) this.mProperties.get(FIELD_START_RANGE);
    }

    public Long getEndRange() {
        return (Long) this.mProperties.get(FIELD_END_RANGE);
    }

    public Long getTotalRange() {
        return (Long) this.mProperties.get(FIELD_TOTAL_RANGE);
    }

    public Date getDate() {
        return (Date) this.mProperties.get(FIELD_DATE);
    }

    public Date getExpiration() {
        return (Date) this.mProperties.get(FIELD_EXPIRATION);
    }

    private static final Date parseDate(String dateString) {
        try {
            return BoxDateFormat.parseHeaderDate(dateString);
        } catch (Exception e) {
            return null;
        }
    }
}
