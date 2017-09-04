package com.box.androidsdk.content.requests;

import com.box.androidsdk.content.models.BoxFileVersion;
import com.box.androidsdk.content.models.BoxItem;
import com.box.androidsdk.content.models.BoxListItems;
import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.requests.BoxRequest.Methods;
import com.box.androidsdk.content.utils.BoxDateFormat;
import com.box.androidsdk.content.utils.SdkUtils;
import java.util.Date;
import java.util.Locale;

public class BoxRequestsSearch {

    public static class Search extends BoxRequest<BoxListItems, Search> {
        public static String CONTENT_TYPE_COMMENTS = "comments";
        public static String CONTENT_TYPE_DESCRIPTION = BoxItem.FIELD_DESCRIPTION;
        public static String CONTENT_TYPE_FILE_CONTENTS = "file_content";
        public static String CONTENT_TYPE_NAME = BoxFileVersion.FIELD_NAME;
        public static String CONTENT_TYPE_TAGS = BoxItem.FIELD_TAGS;
        protected static final String FIELD_ANCESTOR_FOLDER_IDS = "ancestor_folder_ids";
        protected static final String FIELD_CONTENT_TYPES = "content_types";
        protected static final String FIELD_CREATED_AT_RANGE = "created_at_range";
        protected static final String FIELD_FILE_EXTENSIONS = "file_extensions";
        protected static final String FIELD_LIMIT = "limit";
        protected static final String FIELD_OFFSET = "offset";
        protected static final String FIELD_OWNER_USER_IDS = "owner_user_ids";
        protected static final String FIELD_QUERY = "query";
        protected static final String FIELD_SCOPE = "scope";
        protected static final String FIELD_SIZE_RANGE = "size_range";
        protected static final String FIELD_TYPE = "type";
        protected static final String FIELD_UPDATED_AT_RANGE = "updated_at_range";

        public enum Scope {
            USER_CONTENT,
            ENTERPRISE_CONTENT
        }

        public Search(String query, String requestUrl, BoxSession session) {
            super(BoxListItems.class, requestUrl, session);
            limitValueForKey(FIELD_QUERY, query);
            this.mRequestMethod = Methods.GET;
        }

        public Search limitValueForKey(String key, String value) {
            this.mQueryMap.put(key, value);
            return this;
        }

        public Search limitSearchScope(Scope scope) {
            limitValueForKey(FIELD_SCOPE, scope.name().toLowerCase(Locale.US));
            return this;
        }

        public Search limitFileExtensions(String[] extensions) {
            limitValueForKey(FIELD_FILE_EXTENSIONS, SdkUtils.concatStringWithDelimiter(extensions, ","));
            return this;
        }

        public Search limitCreationTime(Date fromDate, Date toDate) {
            addTimeRange(FIELD_CREATED_AT_RANGE, fromDate, toDate);
            return this;
        }

        public Search limitLastUpdateTime(Date fromDate, Date toDate) {
            addTimeRange(FIELD_UPDATED_AT_RANGE, fromDate, toDate);
            return this;
        }

        public Search limitSizeRange(long minSize, long maxSize) {
            limitValueForKey(FIELD_SIZE_RANGE, String.format("%d,%d", new Object[]{Long.valueOf(minSize), Long.valueOf(maxSize)}));
            return this;
        }

        public Search limitOwnerUserIds(String[] userIds) {
            limitValueForKey(FIELD_OWNER_USER_IDS, SdkUtils.concatStringWithDelimiter(userIds, ","));
            return this;
        }

        public Search limitAncestorFolderIds(String[] folderIds) {
            limitValueForKey(FIELD_ANCESTOR_FOLDER_IDS, SdkUtils.concatStringWithDelimiter(folderIds, ","));
            return this;
        }

        public Search limitContentTypes(String[] contentTypes) {
            limitValueForKey(FIELD_CONTENT_TYPES, SdkUtils.concatStringWithDelimiter(contentTypes, ","));
            return this;
        }

        public Search limitType(String type) {
            limitValueForKey(FIELD_TYPE, type);
            return this;
        }

        public Search setLimit(int limit) {
            limitValueForKey(FIELD_LIMIT, String.valueOf(limit));
            return this;
        }

        public Search setOffset(int offset) {
            limitValueForKey(FIELD_OFFSET, String.valueOf(offset));
            return this;
        }

        public Date getLastUpdatedAtDateRangeFrom() {
            return returnFromDate(FIELD_UPDATED_AT_RANGE);
        }

        public Date getLastUpdatedAtDateRangeTo() {
            return returnToDate(FIELD_UPDATED_AT_RANGE);
        }

        public Date getCreatedAtDateRangeFrom() {
            return returnFromDate(FIELD_CREATED_AT_RANGE);
        }

        public Date getCreatedAtDateRangeTo() {
            return returnToDate(FIELD_CREATED_AT_RANGE);
        }

        public Long getSizeRangeFrom() {
            String range = (String) this.mQueryMap.get(FIELD_SIZE_RANGE);
            if (SdkUtils.isEmptyString(range)) {
                return null;
            }
            return Long.valueOf(Long.parseLong(range.split(",")[0]));
        }

        public Long getSizeRangeTo() {
            String range = (String) this.mQueryMap.get(FIELD_SIZE_RANGE);
            if (SdkUtils.isEmptyString(range)) {
                return null;
            }
            return Long.valueOf(Long.parseLong(range.split(",")[1]));
        }

        public String[] getOwnerUserIds() {
            return getStringArray(FIELD_OWNER_USER_IDS);
        }

        public String[] getAncestorFolderIds() {
            return getStringArray(FIELD_ANCESTOR_FOLDER_IDS);
        }

        public String[] getContentTypes() {
            return getStringArray(FIELD_CONTENT_TYPES);
        }

        public String getType() {
            return (String) this.mQueryMap.get(FIELD_TYPE);
        }

        public Integer getLimit() {
            Integer num = null;
            String limit = (String) this.mQueryMap.get(FIELD_LIMIT);
            if (limit != null) {
                try {
                    num = Integer.valueOf(Integer.parseInt(limit));
                } catch (NumberFormatException e) {
                }
            }
            return num;
        }

        public Integer getOffset() {
            Integer num = null;
            String offset = (String) this.mQueryMap.get(FIELD_OFFSET);
            if (offset != null) {
                try {
                    num = Integer.valueOf(Integer.parseInt(offset));
                } catch (NumberFormatException e) {
                }
            }
            return num;
        }

        public String getQuery() {
            return (String) this.mQueryMap.get(FIELD_QUERY);
        }

        public String getScope() {
            return (String) this.mQueryMap.get(FIELD_SCOPE);
        }

        public String[] getFileExtensions() {
            return getStringArray(FIELD_FILE_EXTENSIONS);
        }

        private String[] getStringArray(String key) {
            String types = (String) this.mQueryMap.get(key);
            if (SdkUtils.isEmptyString(types)) {
                return null;
            }
            return types.split(",");
        }

        private Date returnFromDate(String timeRangeKey) {
            String range = (String) this.mQueryMap.get(timeRangeKey);
            if (SdkUtils.isEmptyString(range)) {
                return null;
            }
            return BoxDateFormat.getTimeRangeDates(range)[0];
        }

        private Date returnToDate(String timeRangeKey) {
            String range = (String) this.mQueryMap.get(timeRangeKey);
            if (SdkUtils.isEmptyString(range)) {
                return null;
            }
            return BoxDateFormat.getTimeRangeDates(range)[1];
        }

        private void addTimeRange(String key, Date fromDate, Date toDate) {
            String range = BoxDateFormat.getTimeRangeString(fromDate, toDate);
            if (!SdkUtils.isEmptyString(range)) {
                limitValueForKey(key, range);
            }
        }
    }
}
