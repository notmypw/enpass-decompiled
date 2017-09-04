package com.google.api.services.drive;

import com.google.api.client.googleapis.GoogleUtils;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import com.google.api.services.drive.model.App;
import com.google.api.services.drive.model.AppList;
import com.google.api.services.drive.model.Change;
import com.google.api.services.drive.model.ChangeList;
import com.google.api.services.drive.model.Channel;
import com.google.api.services.drive.model.ChildList;
import com.google.api.services.drive.model.ChildReference;
import com.google.api.services.drive.model.Comment;
import com.google.api.services.drive.model.CommentList;
import com.google.api.services.drive.model.CommentReply;
import com.google.api.services.drive.model.CommentReplyList;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.ParentList;
import com.google.api.services.drive.model.ParentReference;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.drive.model.PermissionId;
import com.google.api.services.drive.model.PermissionList;
import com.google.api.services.drive.model.Property;
import com.google.api.services.drive.model.PropertyList;
import com.google.api.services.drive.model.Revision;
import com.google.api.services.drive.model.RevisionList;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Drive extends AbstractGoogleJsonClient {
    public static final String DEFAULT_BASE_URL = "https://www.googleapis.com/drive/v2/";
    public static final String DEFAULT_ROOT_URL = "https://www.googleapis.com/";
    public static final String DEFAULT_SERVICE_PATH = "drive/v2/";

    public class About {

        public class Get extends DriveRequest<com.google.api.services.drive.model.About> {
            private static final String REST_PATH = "about";
            @Key
            private Boolean includeSubscribed;
            @Key
            private Long maxChangeIdCount;
            @Key
            private Long startChangeId;

            protected Get() {
                super(Drive.this, HttpMethods.GET, REST_PATH, null, com.google.api.services.drive.model.About.class);
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public Get setAlt(String str) {
                return (Get) super.setAlt(str);
            }

            public Get setFields(String str) {
                return (Get) super.setFields(str);
            }

            public Get setKey(String str) {
                return (Get) super.setKey(str);
            }

            public Get setOauthToken(String str) {
                return (Get) super.setOauthToken(str);
            }

            public Get setPrettyPrint(Boolean bool) {
                return (Get) super.setPrettyPrint(bool);
            }

            public Get setQuotaUser(String str) {
                return (Get) super.setQuotaUser(str);
            }

            public Get setUserIp(String str) {
                return (Get) super.setUserIp(str);
            }

            public Boolean getIncludeSubscribed() {
                return this.includeSubscribed;
            }

            public Get setIncludeSubscribed(Boolean bool) {
                this.includeSubscribed = bool;
                return this;
            }

            public boolean isIncludeSubscribed() {
                if (this.includeSubscribed == null || this.includeSubscribed == Data.NULL_BOOLEAN) {
                    return true;
                }
                return this.includeSubscribed.booleanValue();
            }

            public Long getMaxChangeIdCount() {
                return this.maxChangeIdCount;
            }

            public Get setMaxChangeIdCount(Long l) {
                this.maxChangeIdCount = l;
                return this;
            }

            public Long getStartChangeId() {
                return this.startChangeId;
            }

            public Get setStartChangeId(Long l) {
                this.startChangeId = l;
                return this;
            }

            public Get set(String str, Object obj) {
                return (Get) super.set(str, obj);
            }
        }

        public Get get() throws IOException {
            AbstractGoogleClientRequest get = new Get();
            Drive.this.initialize(get);
            return get;
        }
    }

    public class Apps {

        public class Get extends DriveRequest<App> {
            private static final String REST_PATH = "apps/{appId}";
            @Key
            private String appId;

            protected Get(String str) {
                super(Drive.this, HttpMethods.GET, REST_PATH, null, App.class);
                this.appId = (String) Preconditions.checkNotNull(str, "Required parameter appId must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public Get setAlt(String str) {
                return (Get) super.setAlt(str);
            }

            public Get setFields(String str) {
                return (Get) super.setFields(str);
            }

            public Get setKey(String str) {
                return (Get) super.setKey(str);
            }

            public Get setOauthToken(String str) {
                return (Get) super.setOauthToken(str);
            }

            public Get setPrettyPrint(Boolean bool) {
                return (Get) super.setPrettyPrint(bool);
            }

            public Get setQuotaUser(String str) {
                return (Get) super.setQuotaUser(str);
            }

            public Get setUserIp(String str) {
                return (Get) super.setUserIp(str);
            }

            public String getAppId() {
                return this.appId;
            }

            public Get setAppId(String str) {
                this.appId = str;
                return this;
            }

            public Get set(String str, Object obj) {
                return (Get) super.set(str, obj);
            }
        }

        public class List extends DriveRequest<AppList> {
            private static final String REST_PATH = "apps";
            @Key
            private String appFilterExtensions;
            @Key
            private String appFilterMimeTypes;
            @Key
            private String languageCode;

            protected List() {
                super(Drive.this, HttpMethods.GET, REST_PATH, null, AppList.class);
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getLanguageCode() {
                return this.languageCode;
            }

            public List setLanguageCode(String str) {
                this.languageCode = str;
                return this;
            }

            public String getAppFilterExtensions() {
                return this.appFilterExtensions;
            }

            public List setAppFilterExtensions(String str) {
                this.appFilterExtensions = str;
                return this;
            }

            public String getAppFilterMimeTypes() {
                return this.appFilterMimeTypes;
            }

            public List setAppFilterMimeTypes(String str) {
                this.appFilterMimeTypes = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public Get get(String str) throws IOException {
            AbstractGoogleClientRequest get = new Get(str);
            Drive.this.initialize(get);
            return get;
        }

        public List list() throws IOException {
            AbstractGoogleClientRequest list = new List();
            Drive.this.initialize(list);
            return list;
        }
    }

    public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {
        public Builder(HttpTransport httpTransport, JsonFactory jsonFactory, HttpRequestInitializer httpRequestInitializer) {
            super(httpTransport, jsonFactory, Drive.DEFAULT_ROOT_URL, Drive.DEFAULT_SERVICE_PATH, httpRequestInitializer, false);
        }

        public Drive build() {
            return new Drive(this);
        }

        public Builder setRootUrl(String str) {
            return (Builder) super.setRootUrl(str);
        }

        public Builder setServicePath(String str) {
            return (Builder) super.setServicePath(str);
        }

        public Builder setHttpRequestInitializer(HttpRequestInitializer httpRequestInitializer) {
            return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
        }

        public Builder setApplicationName(String str) {
            return (Builder) super.setApplicationName(str);
        }

        public Builder setSuppressPatternChecks(boolean z) {
            return (Builder) super.setSuppressPatternChecks(z);
        }

        public Builder setSuppressRequiredParameterChecks(boolean z) {
            return (Builder) super.setSuppressRequiredParameterChecks(z);
        }

        public Builder setSuppressAllChecks(boolean z) {
            return (Builder) super.setSuppressAllChecks(z);
        }

        public Builder setDriveRequestInitializer(DriveRequestInitializer driveRequestInitializer) {
            return (Builder) super.setGoogleClientRequestInitializer((GoogleClientRequestInitializer) driveRequestInitializer);
        }

        public Builder setGoogleClientRequestInitializer(GoogleClientRequestInitializer googleClientRequestInitializer) {
            return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
        }
    }

    public class Changes {

        public class Get extends DriveRequest<Change> {
            private static final String REST_PATH = "changes/{changeId}";
            @Key
            private String changeId;

            protected Get(String str) {
                super(Drive.this, HttpMethods.GET, REST_PATH, null, Change.class);
                this.changeId = (String) Preconditions.checkNotNull(str, "Required parameter changeId must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public Get setAlt(String str) {
                return (Get) super.setAlt(str);
            }

            public Get setFields(String str) {
                return (Get) super.setFields(str);
            }

            public Get setKey(String str) {
                return (Get) super.setKey(str);
            }

            public Get setOauthToken(String str) {
                return (Get) super.setOauthToken(str);
            }

            public Get setPrettyPrint(Boolean bool) {
                return (Get) super.setPrettyPrint(bool);
            }

            public Get setQuotaUser(String str) {
                return (Get) super.setQuotaUser(str);
            }

            public Get setUserIp(String str) {
                return (Get) super.setUserIp(str);
            }

            public String getChangeId() {
                return this.changeId;
            }

            public Get setChangeId(String str) {
                this.changeId = str;
                return this;
            }

            public Get set(String str, Object obj) {
                return (Get) super.set(str, obj);
            }
        }

        public class List extends DriveRequest<ChangeList> {
            private static final String REST_PATH = "changes";
            @Key
            private Boolean includeDeleted;
            @Key
            private Boolean includeSubscribed;
            @Key
            private Integer maxResults;
            @Key
            private String pageToken;
            @Key
            private Long startChangeId;

            protected List() {
                super(Drive.this, HttpMethods.GET, REST_PATH, null, ChangeList.class);
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public Boolean getIncludeSubscribed() {
                return this.includeSubscribed;
            }

            public List setIncludeSubscribed(Boolean bool) {
                this.includeSubscribed = bool;
                return this;
            }

            public boolean isIncludeSubscribed() {
                if (this.includeSubscribed == null || this.includeSubscribed == Data.NULL_BOOLEAN) {
                    return true;
                }
                return this.includeSubscribed.booleanValue();
            }

            public Long getStartChangeId() {
                return this.startChangeId;
            }

            public List setStartChangeId(Long l) {
                this.startChangeId = l;
                return this;
            }

            public Boolean getIncludeDeleted() {
                return this.includeDeleted;
            }

            public List setIncludeDeleted(Boolean bool) {
                this.includeDeleted = bool;
                return this;
            }

            public boolean isIncludeDeleted() {
                if (this.includeDeleted == null || this.includeDeleted == Data.NULL_BOOLEAN) {
                    return true;
                }
                return this.includeDeleted.booleanValue();
            }

            public Integer getMaxResults() {
                return this.maxResults;
            }

            public List setMaxResults(Integer num) {
                this.maxResults = num;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public List setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public class Watch extends DriveRequest<Channel> {
            private static final String REST_PATH = "changes/watch";
            @Key
            private Boolean includeDeleted;
            @Key
            private Boolean includeSubscribed;
            @Key
            private Integer maxResults;
            @Key
            private String pageToken;
            @Key
            private Long startChangeId;

            protected Watch(Channel channel) {
                super(Drive.this, HttpMethods.POST, REST_PATH, channel, Channel.class);
            }

            public Watch setAlt(String str) {
                return (Watch) super.setAlt(str);
            }

            public Watch setFields(String str) {
                return (Watch) super.setFields(str);
            }

            public Watch setKey(String str) {
                return (Watch) super.setKey(str);
            }

            public Watch setOauthToken(String str) {
                return (Watch) super.setOauthToken(str);
            }

            public Watch setPrettyPrint(Boolean bool) {
                return (Watch) super.setPrettyPrint(bool);
            }

            public Watch setQuotaUser(String str) {
                return (Watch) super.setQuotaUser(str);
            }

            public Watch setUserIp(String str) {
                return (Watch) super.setUserIp(str);
            }

            public Boolean getIncludeSubscribed() {
                return this.includeSubscribed;
            }

            public Watch setIncludeSubscribed(Boolean bool) {
                this.includeSubscribed = bool;
                return this;
            }

            public boolean isIncludeSubscribed() {
                if (this.includeSubscribed == null || this.includeSubscribed == Data.NULL_BOOLEAN) {
                    return true;
                }
                return this.includeSubscribed.booleanValue();
            }

            public Long getStartChangeId() {
                return this.startChangeId;
            }

            public Watch setStartChangeId(Long l) {
                this.startChangeId = l;
                return this;
            }

            public Boolean getIncludeDeleted() {
                return this.includeDeleted;
            }

            public Watch setIncludeDeleted(Boolean bool) {
                this.includeDeleted = bool;
                return this;
            }

            public boolean isIncludeDeleted() {
                if (this.includeDeleted == null || this.includeDeleted == Data.NULL_BOOLEAN) {
                    return true;
                }
                return this.includeDeleted.booleanValue();
            }

            public Integer getMaxResults() {
                return this.maxResults;
            }

            public Watch setMaxResults(Integer num) {
                this.maxResults = num;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public Watch setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public Watch set(String str, Object obj) {
                return (Watch) super.set(str, obj);
            }
        }

        public Get get(String str) throws IOException {
            AbstractGoogleClientRequest get = new Get(str);
            Drive.this.initialize(get);
            return get;
        }

        public List list() throws IOException {
            AbstractGoogleClientRequest list = new List();
            Drive.this.initialize(list);
            return list;
        }

        public Watch watch(Channel channel) throws IOException {
            AbstractGoogleClientRequest watch = new Watch(channel);
            Drive.this.initialize(watch);
            return watch;
        }
    }

    public class Channels {

        public class Stop extends DriveRequest<Void> {
            private static final String REST_PATH = "channels/stop";

            protected Stop(Channel channel) {
                super(Drive.this, HttpMethods.POST, REST_PATH, channel, Void.class);
            }

            public Stop setAlt(String str) {
                return (Stop) super.setAlt(str);
            }

            public Stop setFields(String str) {
                return (Stop) super.setFields(str);
            }

            public Stop setKey(String str) {
                return (Stop) super.setKey(str);
            }

            public Stop setOauthToken(String str) {
                return (Stop) super.setOauthToken(str);
            }

            public Stop setPrettyPrint(Boolean bool) {
                return (Stop) super.setPrettyPrint(bool);
            }

            public Stop setQuotaUser(String str) {
                return (Stop) super.setQuotaUser(str);
            }

            public Stop setUserIp(String str) {
                return (Stop) super.setUserIp(str);
            }

            public Stop set(String str, Object obj) {
                return (Stop) super.set(str, obj);
            }
        }

        public Stop stop(Channel channel) throws IOException {
            AbstractGoogleClientRequest stop = new Stop(channel);
            Drive.this.initialize(stop);
            return stop;
        }
    }

    public class Children {

        public class Delete extends DriveRequest<Void> {
            private static final String REST_PATH = "files/{folderId}/children/{childId}";
            @Key
            private String childId;
            @Key
            private String folderId;

            protected Delete(String str, String str2) {
                super(Drive.this, HttpMethods.DELETE, REST_PATH, null, Void.class);
                this.folderId = (String) Preconditions.checkNotNull(str, "Required parameter folderId must be specified.");
                this.childId = (String) Preconditions.checkNotNull(str2, "Required parameter childId must be specified.");
            }

            public Delete setAlt(String str) {
                return (Delete) super.setAlt(str);
            }

            public Delete setFields(String str) {
                return (Delete) super.setFields(str);
            }

            public Delete setKey(String str) {
                return (Delete) super.setKey(str);
            }

            public Delete setOauthToken(String str) {
                return (Delete) super.setOauthToken(str);
            }

            public Delete setPrettyPrint(Boolean bool) {
                return (Delete) super.setPrettyPrint(bool);
            }

            public Delete setQuotaUser(String str) {
                return (Delete) super.setQuotaUser(str);
            }

            public Delete setUserIp(String str) {
                return (Delete) super.setUserIp(str);
            }

            public String getFolderId() {
                return this.folderId;
            }

            public Delete setFolderId(String str) {
                this.folderId = str;
                return this;
            }

            public String getChildId() {
                return this.childId;
            }

            public Delete setChildId(String str) {
                this.childId = str;
                return this;
            }

            public Delete set(String str, Object obj) {
                return (Delete) super.set(str, obj);
            }
        }

        public class Get extends DriveRequest<ChildReference> {
            private static final String REST_PATH = "files/{folderId}/children/{childId}";
            @Key
            private String childId;
            @Key
            private String folderId;

            protected Get(String str, String str2) {
                super(Drive.this, HttpMethods.GET, REST_PATH, null, ChildReference.class);
                this.folderId = (String) Preconditions.checkNotNull(str, "Required parameter folderId must be specified.");
                this.childId = (String) Preconditions.checkNotNull(str2, "Required parameter childId must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public Get setAlt(String str) {
                return (Get) super.setAlt(str);
            }

            public Get setFields(String str) {
                return (Get) super.setFields(str);
            }

            public Get setKey(String str) {
                return (Get) super.setKey(str);
            }

            public Get setOauthToken(String str) {
                return (Get) super.setOauthToken(str);
            }

            public Get setPrettyPrint(Boolean bool) {
                return (Get) super.setPrettyPrint(bool);
            }

            public Get setQuotaUser(String str) {
                return (Get) super.setQuotaUser(str);
            }

            public Get setUserIp(String str) {
                return (Get) super.setUserIp(str);
            }

            public String getFolderId() {
                return this.folderId;
            }

            public Get setFolderId(String str) {
                this.folderId = str;
                return this;
            }

            public String getChildId() {
                return this.childId;
            }

            public Get setChildId(String str) {
                this.childId = str;
                return this;
            }

            public Get set(String str, Object obj) {
                return (Get) super.set(str, obj);
            }
        }

        public class Insert extends DriveRequest<ChildReference> {
            private static final String REST_PATH = "files/{folderId}/children";
            @Key
            private String folderId;

            protected Insert(String str, ChildReference childReference) {
                super(Drive.this, HttpMethods.POST, REST_PATH, childReference, ChildReference.class);
                this.folderId = (String) Preconditions.checkNotNull(str, "Required parameter folderId must be specified.");
                checkRequiredParameter(childReference, "content");
                checkRequiredParameter(childReference.getId(), "ChildReference.getId()");
            }

            public Insert setAlt(String str) {
                return (Insert) super.setAlt(str);
            }

            public Insert setFields(String str) {
                return (Insert) super.setFields(str);
            }

            public Insert setKey(String str) {
                return (Insert) super.setKey(str);
            }

            public Insert setOauthToken(String str) {
                return (Insert) super.setOauthToken(str);
            }

            public Insert setPrettyPrint(Boolean bool) {
                return (Insert) super.setPrettyPrint(bool);
            }

            public Insert setQuotaUser(String str) {
                return (Insert) super.setQuotaUser(str);
            }

            public Insert setUserIp(String str) {
                return (Insert) super.setUserIp(str);
            }

            public String getFolderId() {
                return this.folderId;
            }

            public Insert setFolderId(String str) {
                this.folderId = str;
                return this;
            }

            public Insert set(String str, Object obj) {
                return (Insert) super.set(str, obj);
            }
        }

        public class List extends DriveRequest<ChildList> {
            private static final String REST_PATH = "files/{folderId}/children";
            @Key
            private String folderId;
            @Key
            private Integer maxResults;
            @Key
            private String pageToken;
            @Key
            private String q;

            protected List(String str) {
                super(Drive.this, HttpMethods.GET, REST_PATH, null, ChildList.class);
                this.folderId = (String) Preconditions.checkNotNull(str, "Required parameter folderId must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getFolderId() {
                return this.folderId;
            }

            public List setFolderId(String str) {
                this.folderId = str;
                return this;
            }

            public String getQ() {
                return this.q;
            }

            public List setQ(String str) {
                this.q = str;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public List setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public Integer getMaxResults() {
                return this.maxResults;
            }

            public List setMaxResults(Integer num) {
                this.maxResults = num;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public Delete delete(String str, String str2) throws IOException {
            AbstractGoogleClientRequest delete = new Delete(str, str2);
            Drive.this.initialize(delete);
            return delete;
        }

        public Get get(String str, String str2) throws IOException {
            AbstractGoogleClientRequest get = new Get(str, str2);
            Drive.this.initialize(get);
            return get;
        }

        public Insert insert(String str, ChildReference childReference) throws IOException {
            AbstractGoogleClientRequest insert = new Insert(str, childReference);
            Drive.this.initialize(insert);
            return insert;
        }

        public List list(String str) throws IOException {
            AbstractGoogleClientRequest list = new List(str);
            Drive.this.initialize(list);
            return list;
        }
    }

    public class Comments {

        public class Delete extends DriveRequest<Void> {
            private static final String REST_PATH = "files/{fileId}/comments/{commentId}";
            @Key
            private String commentId;
            @Key
            private String fileId;

            protected Delete(String str, String str2) {
                super(Drive.this, HttpMethods.DELETE, REST_PATH, null, Void.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                this.commentId = (String) Preconditions.checkNotNull(str2, "Required parameter commentId must be specified.");
            }

            public Delete setAlt(String str) {
                return (Delete) super.setAlt(str);
            }

            public Delete setFields(String str) {
                return (Delete) super.setFields(str);
            }

            public Delete setKey(String str) {
                return (Delete) super.setKey(str);
            }

            public Delete setOauthToken(String str) {
                return (Delete) super.setOauthToken(str);
            }

            public Delete setPrettyPrint(Boolean bool) {
                return (Delete) super.setPrettyPrint(bool);
            }

            public Delete setQuotaUser(String str) {
                return (Delete) super.setQuotaUser(str);
            }

            public Delete setUserIp(String str) {
                return (Delete) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Delete setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getCommentId() {
                return this.commentId;
            }

            public Delete setCommentId(String str) {
                this.commentId = str;
                return this;
            }

            public Delete set(String str, Object obj) {
                return (Delete) super.set(str, obj);
            }
        }

        public class Get extends DriveRequest<Comment> {
            private static final String REST_PATH = "files/{fileId}/comments/{commentId}";
            @Key
            private String commentId;
            @Key
            private String fileId;
            @Key
            private Boolean includeDeleted;

            protected Get(String str, String str2) {
                super(Drive.this, HttpMethods.GET, REST_PATH, null, Comment.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                this.commentId = (String) Preconditions.checkNotNull(str2, "Required parameter commentId must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public Get setAlt(String str) {
                return (Get) super.setAlt(str);
            }

            public Get setFields(String str) {
                return (Get) super.setFields(str);
            }

            public Get setKey(String str) {
                return (Get) super.setKey(str);
            }

            public Get setOauthToken(String str) {
                return (Get) super.setOauthToken(str);
            }

            public Get setPrettyPrint(Boolean bool) {
                return (Get) super.setPrettyPrint(bool);
            }

            public Get setQuotaUser(String str) {
                return (Get) super.setQuotaUser(str);
            }

            public Get setUserIp(String str) {
                return (Get) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Get setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getCommentId() {
                return this.commentId;
            }

            public Get setCommentId(String str) {
                this.commentId = str;
                return this;
            }

            public Boolean getIncludeDeleted() {
                return this.includeDeleted;
            }

            public Get setIncludeDeleted(Boolean bool) {
                this.includeDeleted = bool;
                return this;
            }

            public boolean isIncludeDeleted() {
                if (this.includeDeleted == null || this.includeDeleted == Data.NULL_BOOLEAN) {
                    return false;
                }
                return this.includeDeleted.booleanValue();
            }

            public Get set(String str, Object obj) {
                return (Get) super.set(str, obj);
            }
        }

        public class Insert extends DriveRequest<Comment> {
            private static final String REST_PATH = "files/{fileId}/comments";
            @Key
            private String fileId;

            protected Insert(String str, Comment comment) {
                super(Drive.this, HttpMethods.POST, REST_PATH, comment, Comment.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                checkRequiredParameter(comment, "content");
                checkRequiredParameter(comment.getContent(), "Comment.getContent()");
            }

            public Insert setAlt(String str) {
                return (Insert) super.setAlt(str);
            }

            public Insert setFields(String str) {
                return (Insert) super.setFields(str);
            }

            public Insert setKey(String str) {
                return (Insert) super.setKey(str);
            }

            public Insert setOauthToken(String str) {
                return (Insert) super.setOauthToken(str);
            }

            public Insert setPrettyPrint(Boolean bool) {
                return (Insert) super.setPrettyPrint(bool);
            }

            public Insert setQuotaUser(String str) {
                return (Insert) super.setQuotaUser(str);
            }

            public Insert setUserIp(String str) {
                return (Insert) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Insert setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public Insert set(String str, Object obj) {
                return (Insert) super.set(str, obj);
            }
        }

        public class List extends DriveRequest<CommentList> {
            private static final String REST_PATH = "files/{fileId}/comments";
            @Key
            private String fileId;
            @Key
            private Boolean includeDeleted;
            @Key
            private Integer maxResults;
            @Key
            private String pageToken;
            @Key
            private String updatedMin;

            protected List(String str) {
                super(Drive.this, HttpMethods.GET, REST_PATH, null, CommentList.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public List setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public List setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public String getUpdatedMin() {
                return this.updatedMin;
            }

            public List setUpdatedMin(String str) {
                this.updatedMin = str;
                return this;
            }

            public Boolean getIncludeDeleted() {
                return this.includeDeleted;
            }

            public List setIncludeDeleted(Boolean bool) {
                this.includeDeleted = bool;
                return this;
            }

            public boolean isIncludeDeleted() {
                if (this.includeDeleted == null || this.includeDeleted == Data.NULL_BOOLEAN) {
                    return false;
                }
                return this.includeDeleted.booleanValue();
            }

            public Integer getMaxResults() {
                return this.maxResults;
            }

            public List setMaxResults(Integer num) {
                this.maxResults = num;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public class Patch extends DriveRequest<Comment> {
            private static final String REST_PATH = "files/{fileId}/comments/{commentId}";
            @Key
            private String commentId;
            @Key
            private String fileId;

            protected Patch(String str, String str2, Comment comment) {
                super(Drive.this, HttpMethods.PATCH, REST_PATH, comment, Comment.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                this.commentId = (String) Preconditions.checkNotNull(str2, "Required parameter commentId must be specified.");
            }

            public Patch setAlt(String str) {
                return (Patch) super.setAlt(str);
            }

            public Patch setFields(String str) {
                return (Patch) super.setFields(str);
            }

            public Patch setKey(String str) {
                return (Patch) super.setKey(str);
            }

            public Patch setOauthToken(String str) {
                return (Patch) super.setOauthToken(str);
            }

            public Patch setPrettyPrint(Boolean bool) {
                return (Patch) super.setPrettyPrint(bool);
            }

            public Patch setQuotaUser(String str) {
                return (Patch) super.setQuotaUser(str);
            }

            public Patch setUserIp(String str) {
                return (Patch) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Patch setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getCommentId() {
                return this.commentId;
            }

            public Patch setCommentId(String str) {
                this.commentId = str;
                return this;
            }

            public Patch set(String str, Object obj) {
                return (Patch) super.set(str, obj);
            }
        }

        public class Update extends DriveRequest<Comment> {
            private static final String REST_PATH = "files/{fileId}/comments/{commentId}";
            @Key
            private String commentId;
            @Key
            private String fileId;

            protected Update(String str, String str2, Comment comment) {
                super(Drive.this, HttpMethods.PUT, REST_PATH, comment, Comment.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                this.commentId = (String) Preconditions.checkNotNull(str2, "Required parameter commentId must be specified.");
                checkRequiredParameter(comment, "content");
                checkRequiredParameter(comment.getContent(), "Comment.getContent()");
            }

            public Update setAlt(String str) {
                return (Update) super.setAlt(str);
            }

            public Update setFields(String str) {
                return (Update) super.setFields(str);
            }

            public Update setKey(String str) {
                return (Update) super.setKey(str);
            }

            public Update setOauthToken(String str) {
                return (Update) super.setOauthToken(str);
            }

            public Update setPrettyPrint(Boolean bool) {
                return (Update) super.setPrettyPrint(bool);
            }

            public Update setQuotaUser(String str) {
                return (Update) super.setQuotaUser(str);
            }

            public Update setUserIp(String str) {
                return (Update) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Update setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getCommentId() {
                return this.commentId;
            }

            public Update setCommentId(String str) {
                this.commentId = str;
                return this;
            }

            public Update set(String str, Object obj) {
                return (Update) super.set(str, obj);
            }
        }

        public Delete delete(String str, String str2) throws IOException {
            AbstractGoogleClientRequest delete = new Delete(str, str2);
            Drive.this.initialize(delete);
            return delete;
        }

        public Get get(String str, String str2) throws IOException {
            AbstractGoogleClientRequest get = new Get(str, str2);
            Drive.this.initialize(get);
            return get;
        }

        public Insert insert(String str, Comment comment) throws IOException {
            AbstractGoogleClientRequest insert = new Insert(str, comment);
            Drive.this.initialize(insert);
            return insert;
        }

        public List list(String str) throws IOException {
            AbstractGoogleClientRequest list = new List(str);
            Drive.this.initialize(list);
            return list;
        }

        public Patch patch(String str, String str2, Comment comment) throws IOException {
            AbstractGoogleClientRequest patch = new Patch(str, str2, comment);
            Drive.this.initialize(patch);
            return patch;
        }

        public Update update(String str, String str2, Comment comment) throws IOException {
            AbstractGoogleClientRequest update = new Update(str, str2, comment);
            Drive.this.initialize(update);
            return update;
        }
    }

    public class Files {

        public class Copy extends DriveRequest<File> {
            private static final String REST_PATH = "files/{fileId}/copy";
            @Key
            private Boolean convert;
            @Key
            private String fileId;
            @Key
            private Boolean ocr;
            @Key
            private String ocrLanguage;
            @Key
            private Boolean pinned;
            @Key
            private String timedTextLanguage;
            @Key
            private String timedTextTrackName;
            @Key
            private String visibility;

            protected Copy(String str, File file) {
                super(Drive.this, HttpMethods.POST, REST_PATH, file, File.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
            }

            public Copy setAlt(String str) {
                return (Copy) super.setAlt(str);
            }

            public Copy setFields(String str) {
                return (Copy) super.setFields(str);
            }

            public Copy setKey(String str) {
                return (Copy) super.setKey(str);
            }

            public Copy setOauthToken(String str) {
                return (Copy) super.setOauthToken(str);
            }

            public Copy setPrettyPrint(Boolean bool) {
                return (Copy) super.setPrettyPrint(bool);
            }

            public Copy setQuotaUser(String str) {
                return (Copy) super.setQuotaUser(str);
            }

            public Copy setUserIp(String str) {
                return (Copy) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Copy setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public Boolean getConvert() {
                return this.convert;
            }

            public Copy setConvert(Boolean bool) {
                this.convert = bool;
                return this;
            }

            public boolean isConvert() {
                if (this.convert == null || this.convert == Data.NULL_BOOLEAN) {
                    return false;
                }
                return this.convert.booleanValue();
            }

            public String getOcrLanguage() {
                return this.ocrLanguage;
            }

            public Copy setOcrLanguage(String str) {
                this.ocrLanguage = str;
                return this;
            }

            public String getVisibility() {
                return this.visibility;
            }

            public Copy setVisibility(String str) {
                this.visibility = str;
                return this;
            }

            public Boolean getPinned() {
                return this.pinned;
            }

            public Copy setPinned(Boolean bool) {
                this.pinned = bool;
                return this;
            }

            public boolean isPinned() {
                if (this.pinned == null || this.pinned == Data.NULL_BOOLEAN) {
                    return false;
                }
                return this.pinned.booleanValue();
            }

            public Boolean getOcr() {
                return this.ocr;
            }

            public Copy setOcr(Boolean bool) {
                this.ocr = bool;
                return this;
            }

            public boolean isOcr() {
                if (this.ocr == null || this.ocr == Data.NULL_BOOLEAN) {
                    return false;
                }
                return this.ocr.booleanValue();
            }

            public String getTimedTextTrackName() {
                return this.timedTextTrackName;
            }

            public Copy setTimedTextTrackName(String str) {
                this.timedTextTrackName = str;
                return this;
            }

            public String getTimedTextLanguage() {
                return this.timedTextLanguage;
            }

            public Copy setTimedTextLanguage(String str) {
                this.timedTextLanguage = str;
                return this;
            }

            public Copy set(String str, Object obj) {
                return (Copy) super.set(str, obj);
            }
        }

        public class Delete extends DriveRequest<Void> {
            private static final String REST_PATH = "files/{fileId}";
            @Key
            private String fileId;

            protected Delete(String str) {
                super(Drive.this, HttpMethods.DELETE, REST_PATH, null, Void.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
            }

            public Delete setAlt(String str) {
                return (Delete) super.setAlt(str);
            }

            public Delete setFields(String str) {
                return (Delete) super.setFields(str);
            }

            public Delete setKey(String str) {
                return (Delete) super.setKey(str);
            }

            public Delete setOauthToken(String str) {
                return (Delete) super.setOauthToken(str);
            }

            public Delete setPrettyPrint(Boolean bool) {
                return (Delete) super.setPrettyPrint(bool);
            }

            public Delete setQuotaUser(String str) {
                return (Delete) super.setQuotaUser(str);
            }

            public Delete setUserIp(String str) {
                return (Delete) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Delete setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public Delete set(String str, Object obj) {
                return (Delete) super.set(str, obj);
            }
        }

        public class EmptyTrash extends DriveRequest<Void> {
            private static final String REST_PATH = "files/trash";

            protected EmptyTrash() {
                super(Drive.this, HttpMethods.DELETE, REST_PATH, null, Void.class);
            }

            public EmptyTrash setAlt(String str) {
                return (EmptyTrash) super.setAlt(str);
            }

            public EmptyTrash setFields(String str) {
                return (EmptyTrash) super.setFields(str);
            }

            public EmptyTrash setKey(String str) {
                return (EmptyTrash) super.setKey(str);
            }

            public EmptyTrash setOauthToken(String str) {
                return (EmptyTrash) super.setOauthToken(str);
            }

            public EmptyTrash setPrettyPrint(Boolean bool) {
                return (EmptyTrash) super.setPrettyPrint(bool);
            }

            public EmptyTrash setQuotaUser(String str) {
                return (EmptyTrash) super.setQuotaUser(str);
            }

            public EmptyTrash setUserIp(String str) {
                return (EmptyTrash) super.setUserIp(str);
            }

            public EmptyTrash set(String str, Object obj) {
                return (EmptyTrash) super.set(str, obj);
            }
        }

        public class Get extends DriveRequest<File> {
            private static final String REST_PATH = "files/{fileId}";
            @Key
            private Boolean acknowledgeAbuse;
            @Key
            private String fileId;
            @Key
            private String projection;
            @Key
            private String revisionId;
            @Key
            private Boolean updateViewedDate;

            protected Get(String str) {
                super(Drive.this, HttpMethods.GET, REST_PATH, null, File.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                initializeMediaDownload();
            }

            public void executeMediaAndDownloadTo(OutputStream outputStream) throws IOException {
                super.executeMediaAndDownloadTo(outputStream);
            }

            public InputStream executeMediaAsInputStream() throws IOException {
                return super.executeMediaAsInputStream();
            }

            public HttpResponse executeMedia() throws IOException {
                return super.executeMedia();
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public Get setAlt(String str) {
                return (Get) super.setAlt(str);
            }

            public Get setFields(String str) {
                return (Get) super.setFields(str);
            }

            public Get setKey(String str) {
                return (Get) super.setKey(str);
            }

            public Get setOauthToken(String str) {
                return (Get) super.setOauthToken(str);
            }

            public Get setPrettyPrint(Boolean bool) {
                return (Get) super.setPrettyPrint(bool);
            }

            public Get setQuotaUser(String str) {
                return (Get) super.setQuotaUser(str);
            }

            public Get setUserIp(String str) {
                return (Get) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Get setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getProjection() {
                return this.projection;
            }

            public Get setProjection(String str) {
                this.projection = str;
                return this;
            }

            public String getRevisionId() {
                return this.revisionId;
            }

            public Get setRevisionId(String str) {
                this.revisionId = str;
                return this;
            }

            public Boolean getAcknowledgeAbuse() {
                return this.acknowledgeAbuse;
            }

            public Get setAcknowledgeAbuse(Boolean bool) {
                this.acknowledgeAbuse = bool;
                return this;
            }

            public boolean isAcknowledgeAbuse() {
                if (this.acknowledgeAbuse == null || this.acknowledgeAbuse == Data.NULL_BOOLEAN) {
                    return false;
                }
                return this.acknowledgeAbuse.booleanValue();
            }

            public Boolean getUpdateViewedDate() {
                return this.updateViewedDate;
            }

            public Get setUpdateViewedDate(Boolean bool) {
                this.updateViewedDate = bool;
                return this;
            }

            public boolean isUpdateViewedDate() {
                if (this.updateViewedDate == null || this.updateViewedDate == Data.NULL_BOOLEAN) {
                    return false;
                }
                return this.updateViewedDate.booleanValue();
            }

            public Get set(String str, Object obj) {
                return (Get) super.set(str, obj);
            }
        }

        public class Insert extends DriveRequest<File> {
            private static final String REST_PATH = "files";
            @Key
            private Boolean convert;
            @Key
            private Boolean ocr;
            @Key
            private String ocrLanguage;
            @Key
            private Boolean pinned;
            @Key
            private String timedTextLanguage;
            @Key
            private String timedTextTrackName;
            @Key
            private Boolean useContentAsIndexableText;
            @Key
            private String visibility;

            protected Insert(File file) {
                super(Drive.this, HttpMethods.POST, REST_PATH, file, File.class);
            }

            protected Insert(File file, AbstractInputStreamContent abstractInputStreamContent) {
                super(Drive.this, HttpMethods.POST, "/upload/" + Drive.this.getServicePath() + REST_PATH, file, File.class);
                initializeMediaUpload(abstractInputStreamContent);
            }

            public Insert setAlt(String str) {
                return (Insert) super.setAlt(str);
            }

            public Insert setFields(String str) {
                return (Insert) super.setFields(str);
            }

            public Insert setKey(String str) {
                return (Insert) super.setKey(str);
            }

            public Insert setOauthToken(String str) {
                return (Insert) super.setOauthToken(str);
            }

            public Insert setPrettyPrint(Boolean bool) {
                return (Insert) super.setPrettyPrint(bool);
            }

            public Insert setQuotaUser(String str) {
                return (Insert) super.setQuotaUser(str);
            }

            public Insert setUserIp(String str) {
                return (Insert) super.setUserIp(str);
            }

            public Boolean getConvert() {
                return this.convert;
            }

            public Insert setConvert(Boolean bool) {
                this.convert = bool;
                return this;
            }

            public boolean isConvert() {
                if (this.convert == null || this.convert == Data.NULL_BOOLEAN) {
                    return false;
                }
                return this.convert.booleanValue();
            }

            public Boolean getUseContentAsIndexableText() {
                return this.useContentAsIndexableText;
            }

            public Insert setUseContentAsIndexableText(Boolean bool) {
                this.useContentAsIndexableText = bool;
                return this;
            }

            public boolean isUseContentAsIndexableText() {
                if (this.useContentAsIndexableText == null || this.useContentAsIndexableText == Data.NULL_BOOLEAN) {
                    return false;
                }
                return this.useContentAsIndexableText.booleanValue();
            }

            public String getOcrLanguage() {
                return this.ocrLanguage;
            }

            public Insert setOcrLanguage(String str) {
                this.ocrLanguage = str;
                return this;
            }

            public String getVisibility() {
                return this.visibility;
            }

            public Insert setVisibility(String str) {
                this.visibility = str;
                return this;
            }

            public Boolean getPinned() {
                return this.pinned;
            }

            public Insert setPinned(Boolean bool) {
                this.pinned = bool;
                return this;
            }

            public boolean isPinned() {
                if (this.pinned == null || this.pinned == Data.NULL_BOOLEAN) {
                    return false;
                }
                return this.pinned.booleanValue();
            }

            public Boolean getOcr() {
                return this.ocr;
            }

            public Insert setOcr(Boolean bool) {
                this.ocr = bool;
                return this;
            }

            public boolean isOcr() {
                if (this.ocr == null || this.ocr == Data.NULL_BOOLEAN) {
                    return false;
                }
                return this.ocr.booleanValue();
            }

            public String getTimedTextTrackName() {
                return this.timedTextTrackName;
            }

            public Insert setTimedTextTrackName(String str) {
                this.timedTextTrackName = str;
                return this;
            }

            public String getTimedTextLanguage() {
                return this.timedTextLanguage;
            }

            public Insert setTimedTextLanguage(String str) {
                this.timedTextLanguage = str;
                return this;
            }

            public Insert set(String str, Object obj) {
                return (Insert) super.set(str, obj);
            }
        }

        public class List extends DriveRequest<FileList> {
            private static final String REST_PATH = "files";
            @Key
            private String corpus;
            @Key
            private Integer maxResults;
            @Key
            private String pageToken;
            @Key
            private String projection;
            @Key
            private String q;

            protected List() {
                super(Drive.this, HttpMethods.GET, REST_PATH, null, FileList.class);
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getQ() {
                return this.q;
            }

            public List setQ(String str) {
                this.q = str;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public List setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public String getCorpus() {
                return this.corpus;
            }

            public List setCorpus(String str) {
                this.corpus = str;
                return this;
            }

            public String getProjection() {
                return this.projection;
            }

            public List setProjection(String str) {
                this.projection = str;
                return this;
            }

            public Integer getMaxResults() {
                return this.maxResults;
            }

            public List setMaxResults(Integer num) {
                this.maxResults = num;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public class Patch extends DriveRequest<File> {
            private static final String REST_PATH = "files/{fileId}";
            @Key
            private String addParents;
            @Key
            private Boolean convert;
            @Key
            private String fileId;
            @Key
            private Boolean newRevision;
            @Key
            private Boolean ocr;
            @Key
            private String ocrLanguage;
            @Key
            private Boolean pinned;
            @Key
            private String removeParents;
            @Key
            private Boolean setModifiedDate;
            @Key
            private String timedTextLanguage;
            @Key
            private String timedTextTrackName;
            @Key
            private Boolean updateViewedDate;
            @Key
            private Boolean useContentAsIndexableText;

            protected Patch(String str, File file) {
                super(Drive.this, HttpMethods.PATCH, REST_PATH, file, File.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
            }

            public Patch setAlt(String str) {
                return (Patch) super.setAlt(str);
            }

            public Patch setFields(String str) {
                return (Patch) super.setFields(str);
            }

            public Patch setKey(String str) {
                return (Patch) super.setKey(str);
            }

            public Patch setOauthToken(String str) {
                return (Patch) super.setOauthToken(str);
            }

            public Patch setPrettyPrint(Boolean bool) {
                return (Patch) super.setPrettyPrint(bool);
            }

            public Patch setQuotaUser(String str) {
                return (Patch) super.setQuotaUser(str);
            }

            public Patch setUserIp(String str) {
                return (Patch) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Patch setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getAddParents() {
                return this.addParents;
            }

            public Patch setAddParents(String str) {
                this.addParents = str;
                return this;
            }

            public Boolean getUpdateViewedDate() {
                return this.updateViewedDate;
            }

            public Patch setUpdateViewedDate(Boolean bool) {
                this.updateViewedDate = bool;
                return this;
            }

            public boolean isUpdateViewedDate() {
                if (this.updateViewedDate == null || this.updateViewedDate == Data.NULL_BOOLEAN) {
                    return true;
                }
                return this.updateViewedDate.booleanValue();
            }

            public String getRemoveParents() {
                return this.removeParents;
            }

            public Patch setRemoveParents(String str) {
                this.removeParents = str;
                return this;
            }

            public Boolean getSetModifiedDate() {
                return this.setModifiedDate;
            }

            public Patch setSetModifiedDate(Boolean bool) {
                this.setModifiedDate = bool;
                return this;
            }

            public boolean isSetModifiedDate() {
                if (this.setModifiedDate == null || this.setModifiedDate == Data.NULL_BOOLEAN) {
                    return false;
                }
                return this.setModifiedDate.booleanValue();
            }

            public Boolean getConvert() {
                return this.convert;
            }

            public Patch setConvert(Boolean bool) {
                this.convert = bool;
                return this;
            }

            public boolean isConvert() {
                if (this.convert == null || this.convert == Data.NULL_BOOLEAN) {
                    return false;
                }
                return this.convert.booleanValue();
            }

            public Boolean getUseContentAsIndexableText() {
                return this.useContentAsIndexableText;
            }

            public Patch setUseContentAsIndexableText(Boolean bool) {
                this.useContentAsIndexableText = bool;
                return this;
            }

            public boolean isUseContentAsIndexableText() {
                if (this.useContentAsIndexableText == null || this.useContentAsIndexableText == Data.NULL_BOOLEAN) {
                    return false;
                }
                return this.useContentAsIndexableText.booleanValue();
            }

            public String getOcrLanguage() {
                return this.ocrLanguage;
            }

            public Patch setOcrLanguage(String str) {
                this.ocrLanguage = str;
                return this;
            }

            public Boolean getPinned() {
                return this.pinned;
            }

            public Patch setPinned(Boolean bool) {
                this.pinned = bool;
                return this;
            }

            public boolean isPinned() {
                if (this.pinned == null || this.pinned == Data.NULL_BOOLEAN) {
                    return false;
                }
                return this.pinned.booleanValue();
            }

            public Boolean getNewRevision() {
                return this.newRevision;
            }

            public Patch setNewRevision(Boolean bool) {
                this.newRevision = bool;
                return this;
            }

            public boolean isNewRevision() {
                if (this.newRevision == null || this.newRevision == Data.NULL_BOOLEAN) {
                    return true;
                }
                return this.newRevision.booleanValue();
            }

            public Boolean getOcr() {
                return this.ocr;
            }

            public Patch setOcr(Boolean bool) {
                this.ocr = bool;
                return this;
            }

            public boolean isOcr() {
                if (this.ocr == null || this.ocr == Data.NULL_BOOLEAN) {
                    return false;
                }
                return this.ocr.booleanValue();
            }

            public String getTimedTextLanguage() {
                return this.timedTextLanguage;
            }

            public Patch setTimedTextLanguage(String str) {
                this.timedTextLanguage = str;
                return this;
            }

            public String getTimedTextTrackName() {
                return this.timedTextTrackName;
            }

            public Patch setTimedTextTrackName(String str) {
                this.timedTextTrackName = str;
                return this;
            }

            public Patch set(String str, Object obj) {
                return (Patch) super.set(str, obj);
            }
        }

        public class Touch extends DriveRequest<File> {
            private static final String REST_PATH = "files/{fileId}/touch";
            @Key
            private String fileId;

            protected Touch(String str) {
                super(Drive.this, HttpMethods.POST, REST_PATH, null, File.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
            }

            public Touch setAlt(String str) {
                return (Touch) super.setAlt(str);
            }

            public Touch setFields(String str) {
                return (Touch) super.setFields(str);
            }

            public Touch setKey(String str) {
                return (Touch) super.setKey(str);
            }

            public Touch setOauthToken(String str) {
                return (Touch) super.setOauthToken(str);
            }

            public Touch setPrettyPrint(Boolean bool) {
                return (Touch) super.setPrettyPrint(bool);
            }

            public Touch setQuotaUser(String str) {
                return (Touch) super.setQuotaUser(str);
            }

            public Touch setUserIp(String str) {
                return (Touch) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Touch setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public Touch set(String str, Object obj) {
                return (Touch) super.set(str, obj);
            }
        }

        public class Trash extends DriveRequest<File> {
            private static final String REST_PATH = "files/{fileId}/trash";
            @Key
            private String fileId;

            protected Trash(String str) {
                super(Drive.this, HttpMethods.POST, REST_PATH, null, File.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
            }

            public Trash setAlt(String str) {
                return (Trash) super.setAlt(str);
            }

            public Trash setFields(String str) {
                return (Trash) super.setFields(str);
            }

            public Trash setKey(String str) {
                return (Trash) super.setKey(str);
            }

            public Trash setOauthToken(String str) {
                return (Trash) super.setOauthToken(str);
            }

            public Trash setPrettyPrint(Boolean bool) {
                return (Trash) super.setPrettyPrint(bool);
            }

            public Trash setQuotaUser(String str) {
                return (Trash) super.setQuotaUser(str);
            }

            public Trash setUserIp(String str) {
                return (Trash) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Trash setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public Trash set(String str, Object obj) {
                return (Trash) super.set(str, obj);
            }
        }

        public class Untrash extends DriveRequest<File> {
            private static final String REST_PATH = "files/{fileId}/untrash";
            @Key
            private String fileId;

            protected Untrash(String str) {
                super(Drive.this, HttpMethods.POST, REST_PATH, null, File.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
            }

            public Untrash setAlt(String str) {
                return (Untrash) super.setAlt(str);
            }

            public Untrash setFields(String str) {
                return (Untrash) super.setFields(str);
            }

            public Untrash setKey(String str) {
                return (Untrash) super.setKey(str);
            }

            public Untrash setOauthToken(String str) {
                return (Untrash) super.setOauthToken(str);
            }

            public Untrash setPrettyPrint(Boolean bool) {
                return (Untrash) super.setPrettyPrint(bool);
            }

            public Untrash setQuotaUser(String str) {
                return (Untrash) super.setQuotaUser(str);
            }

            public Untrash setUserIp(String str) {
                return (Untrash) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Untrash setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public Untrash set(String str, Object obj) {
                return (Untrash) super.set(str, obj);
            }
        }

        public class Update extends DriveRequest<File> {
            private static final String REST_PATH = "files/{fileId}";
            @Key
            private String addParents;
            @Key
            private Boolean convert;
            @Key
            private String fileId;
            @Key
            private Boolean newRevision;
            @Key
            private Boolean ocr;
            @Key
            private String ocrLanguage;
            @Key
            private Boolean pinned;
            @Key
            private String removeParents;
            @Key
            private Boolean setModifiedDate;
            @Key
            private String timedTextLanguage;
            @Key
            private String timedTextTrackName;
            @Key
            private Boolean updateViewedDate;
            @Key
            private Boolean useContentAsIndexableText;

            protected Update(String str, File file) {
                super(Drive.this, HttpMethods.PUT, REST_PATH, file, File.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
            }

            protected Update(String str, File file, AbstractInputStreamContent abstractInputStreamContent) {
                super(Drive.this, HttpMethods.PUT, "/upload/" + Drive.this.getServicePath() + REST_PATH, file, File.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                initializeMediaUpload(abstractInputStreamContent);
            }

            public Update setAlt(String str) {
                return (Update) super.setAlt(str);
            }

            public Update setFields(String str) {
                return (Update) super.setFields(str);
            }

            public Update setKey(String str) {
                return (Update) super.setKey(str);
            }

            public Update setOauthToken(String str) {
                return (Update) super.setOauthToken(str);
            }

            public Update setPrettyPrint(Boolean bool) {
                return (Update) super.setPrettyPrint(bool);
            }

            public Update setQuotaUser(String str) {
                return (Update) super.setQuotaUser(str);
            }

            public Update setUserIp(String str) {
                return (Update) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Update setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getAddParents() {
                return this.addParents;
            }

            public Update setAddParents(String str) {
                this.addParents = str;
                return this;
            }

            public Boolean getUpdateViewedDate() {
                return this.updateViewedDate;
            }

            public Update setUpdateViewedDate(Boolean bool) {
                this.updateViewedDate = bool;
                return this;
            }

            public boolean isUpdateViewedDate() {
                if (this.updateViewedDate == null || this.updateViewedDate == Data.NULL_BOOLEAN) {
                    return true;
                }
                return this.updateViewedDate.booleanValue();
            }

            public String getRemoveParents() {
                return this.removeParents;
            }

            public Update setRemoveParents(String str) {
                this.removeParents = str;
                return this;
            }

            public Boolean getSetModifiedDate() {
                return this.setModifiedDate;
            }

            public Update setSetModifiedDate(Boolean bool) {
                this.setModifiedDate = bool;
                return this;
            }

            public boolean isSetModifiedDate() {
                if (this.setModifiedDate == null || this.setModifiedDate == Data.NULL_BOOLEAN) {
                    return false;
                }
                return this.setModifiedDate.booleanValue();
            }

            public Boolean getConvert() {
                return this.convert;
            }

            public Update setConvert(Boolean bool) {
                this.convert = bool;
                return this;
            }

            public boolean isConvert() {
                if (this.convert == null || this.convert == Data.NULL_BOOLEAN) {
                    return false;
                }
                return this.convert.booleanValue();
            }

            public Boolean getUseContentAsIndexableText() {
                return this.useContentAsIndexableText;
            }

            public Update setUseContentAsIndexableText(Boolean bool) {
                this.useContentAsIndexableText = bool;
                return this;
            }

            public boolean isUseContentAsIndexableText() {
                if (this.useContentAsIndexableText == null || this.useContentAsIndexableText == Data.NULL_BOOLEAN) {
                    return false;
                }
                return this.useContentAsIndexableText.booleanValue();
            }

            public String getOcrLanguage() {
                return this.ocrLanguage;
            }

            public Update setOcrLanguage(String str) {
                this.ocrLanguage = str;
                return this;
            }

            public Boolean getPinned() {
                return this.pinned;
            }

            public Update setPinned(Boolean bool) {
                this.pinned = bool;
                return this;
            }

            public boolean isPinned() {
                if (this.pinned == null || this.pinned == Data.NULL_BOOLEAN) {
                    return false;
                }
                return this.pinned.booleanValue();
            }

            public Boolean getNewRevision() {
                return this.newRevision;
            }

            public Update setNewRevision(Boolean bool) {
                this.newRevision = bool;
                return this;
            }

            public boolean isNewRevision() {
                if (this.newRevision == null || this.newRevision == Data.NULL_BOOLEAN) {
                    return true;
                }
                return this.newRevision.booleanValue();
            }

            public Boolean getOcr() {
                return this.ocr;
            }

            public Update setOcr(Boolean bool) {
                this.ocr = bool;
                return this;
            }

            public boolean isOcr() {
                if (this.ocr == null || this.ocr == Data.NULL_BOOLEAN) {
                    return false;
                }
                return this.ocr.booleanValue();
            }

            public String getTimedTextLanguage() {
                return this.timedTextLanguage;
            }

            public Update setTimedTextLanguage(String str) {
                this.timedTextLanguage = str;
                return this;
            }

            public String getTimedTextTrackName() {
                return this.timedTextTrackName;
            }

            public Update setTimedTextTrackName(String str) {
                this.timedTextTrackName = str;
                return this;
            }

            public Update set(String str, Object obj) {
                return (Update) super.set(str, obj);
            }
        }

        public class Watch extends DriveRequest<Channel> {
            private static final String REST_PATH = "files/{fileId}/watch";
            @Key
            private Boolean acknowledgeAbuse;
            @Key
            private String fileId;
            @Key
            private String projection;
            @Key
            private String revisionId;
            @Key
            private Boolean updateViewedDate;

            protected Watch(String str, Channel channel) {
                super(Drive.this, HttpMethods.POST, REST_PATH, channel, Channel.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                initializeMediaDownload();
            }

            public void executeMediaAndDownloadTo(OutputStream outputStream) throws IOException {
                super.executeMediaAndDownloadTo(outputStream);
            }

            public InputStream executeMediaAsInputStream() throws IOException {
                return super.executeMediaAsInputStream();
            }

            public HttpResponse executeMedia() throws IOException {
                return super.executeMedia();
            }

            public Watch setAlt(String str) {
                return (Watch) super.setAlt(str);
            }

            public Watch setFields(String str) {
                return (Watch) super.setFields(str);
            }

            public Watch setKey(String str) {
                return (Watch) super.setKey(str);
            }

            public Watch setOauthToken(String str) {
                return (Watch) super.setOauthToken(str);
            }

            public Watch setPrettyPrint(Boolean bool) {
                return (Watch) super.setPrettyPrint(bool);
            }

            public Watch setQuotaUser(String str) {
                return (Watch) super.setQuotaUser(str);
            }

            public Watch setUserIp(String str) {
                return (Watch) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Watch setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getProjection() {
                return this.projection;
            }

            public Watch setProjection(String str) {
                this.projection = str;
                return this;
            }

            public String getRevisionId() {
                return this.revisionId;
            }

            public Watch setRevisionId(String str) {
                this.revisionId = str;
                return this;
            }

            public Boolean getAcknowledgeAbuse() {
                return this.acknowledgeAbuse;
            }

            public Watch setAcknowledgeAbuse(Boolean bool) {
                this.acknowledgeAbuse = bool;
                return this;
            }

            public boolean isAcknowledgeAbuse() {
                if (this.acknowledgeAbuse == null || this.acknowledgeAbuse == Data.NULL_BOOLEAN) {
                    return false;
                }
                return this.acknowledgeAbuse.booleanValue();
            }

            public Boolean getUpdateViewedDate() {
                return this.updateViewedDate;
            }

            public Watch setUpdateViewedDate(Boolean bool) {
                this.updateViewedDate = bool;
                return this;
            }

            public boolean isUpdateViewedDate() {
                if (this.updateViewedDate == null || this.updateViewedDate == Data.NULL_BOOLEAN) {
                    return false;
                }
                return this.updateViewedDate.booleanValue();
            }

            public Watch set(String str, Object obj) {
                return (Watch) super.set(str, obj);
            }
        }

        public Copy copy(String str, File file) throws IOException {
            AbstractGoogleClientRequest copy = new Copy(str, file);
            Drive.this.initialize(copy);
            return copy;
        }

        public Delete delete(String str) throws IOException {
            AbstractGoogleClientRequest delete = new Delete(str);
            Drive.this.initialize(delete);
            return delete;
        }

        public EmptyTrash emptyTrash() throws IOException {
            AbstractGoogleClientRequest emptyTrash = new EmptyTrash();
            Drive.this.initialize(emptyTrash);
            return emptyTrash;
        }

        public Get get(String str) throws IOException {
            AbstractGoogleClientRequest get = new Get(str);
            Drive.this.initialize(get);
            return get;
        }

        public Insert insert(File file) throws IOException {
            AbstractGoogleClientRequest insert = new Insert(file);
            Drive.this.initialize(insert);
            return insert;
        }

        public Insert insert(File file, AbstractInputStreamContent abstractInputStreamContent) throws IOException {
            AbstractGoogleClientRequest insert = new Insert(file, abstractInputStreamContent);
            Drive.this.initialize(insert);
            return insert;
        }

        public List list() throws IOException {
            AbstractGoogleClientRequest list = new List();
            Drive.this.initialize(list);
            return list;
        }

        public Patch patch(String str, File file) throws IOException {
            AbstractGoogleClientRequest patch = new Patch(str, file);
            Drive.this.initialize(patch);
            return patch;
        }

        public Touch touch(String str) throws IOException {
            AbstractGoogleClientRequest touch = new Touch(str);
            Drive.this.initialize(touch);
            return touch;
        }

        public Trash trash(String str) throws IOException {
            AbstractGoogleClientRequest trash = new Trash(str);
            Drive.this.initialize(trash);
            return trash;
        }

        public Untrash untrash(String str) throws IOException {
            AbstractGoogleClientRequest untrash = new Untrash(str);
            Drive.this.initialize(untrash);
            return untrash;
        }

        public Update update(String str, File file) throws IOException {
            AbstractGoogleClientRequest update = new Update(str, file);
            Drive.this.initialize(update);
            return update;
        }

        public Update update(String str, File file, AbstractInputStreamContent abstractInputStreamContent) throws IOException {
            AbstractGoogleClientRequest update = new Update(str, file, abstractInputStreamContent);
            Drive.this.initialize(update);
            return update;
        }

        public Watch watch(String str, Channel channel) throws IOException {
            AbstractGoogleClientRequest watch = new Watch(str, channel);
            Drive.this.initialize(watch);
            return watch;
        }
    }

    public class Parents {

        public class Delete extends DriveRequest<Void> {
            private static final String REST_PATH = "files/{fileId}/parents/{parentId}";
            @Key
            private String fileId;
            @Key
            private String parentId;

            protected Delete(String str, String str2) {
                super(Drive.this, HttpMethods.DELETE, REST_PATH, null, Void.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                this.parentId = (String) Preconditions.checkNotNull(str2, "Required parameter parentId must be specified.");
            }

            public Delete setAlt(String str) {
                return (Delete) super.setAlt(str);
            }

            public Delete setFields(String str) {
                return (Delete) super.setFields(str);
            }

            public Delete setKey(String str) {
                return (Delete) super.setKey(str);
            }

            public Delete setOauthToken(String str) {
                return (Delete) super.setOauthToken(str);
            }

            public Delete setPrettyPrint(Boolean bool) {
                return (Delete) super.setPrettyPrint(bool);
            }

            public Delete setQuotaUser(String str) {
                return (Delete) super.setQuotaUser(str);
            }

            public Delete setUserIp(String str) {
                return (Delete) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Delete setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getParentId() {
                return this.parentId;
            }

            public Delete setParentId(String str) {
                this.parentId = str;
                return this;
            }

            public Delete set(String str, Object obj) {
                return (Delete) super.set(str, obj);
            }
        }

        public class Get extends DriveRequest<ParentReference> {
            private static final String REST_PATH = "files/{fileId}/parents/{parentId}";
            @Key
            private String fileId;
            @Key
            private String parentId;

            protected Get(String str, String str2) {
                super(Drive.this, HttpMethods.GET, REST_PATH, null, ParentReference.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                this.parentId = (String) Preconditions.checkNotNull(str2, "Required parameter parentId must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public Get setAlt(String str) {
                return (Get) super.setAlt(str);
            }

            public Get setFields(String str) {
                return (Get) super.setFields(str);
            }

            public Get setKey(String str) {
                return (Get) super.setKey(str);
            }

            public Get setOauthToken(String str) {
                return (Get) super.setOauthToken(str);
            }

            public Get setPrettyPrint(Boolean bool) {
                return (Get) super.setPrettyPrint(bool);
            }

            public Get setQuotaUser(String str) {
                return (Get) super.setQuotaUser(str);
            }

            public Get setUserIp(String str) {
                return (Get) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Get setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getParentId() {
                return this.parentId;
            }

            public Get setParentId(String str) {
                this.parentId = str;
                return this;
            }

            public Get set(String str, Object obj) {
                return (Get) super.set(str, obj);
            }
        }

        public class Insert extends DriveRequest<ParentReference> {
            private static final String REST_PATH = "files/{fileId}/parents";
            @Key
            private String fileId;

            protected Insert(String str, ParentReference parentReference) {
                super(Drive.this, HttpMethods.POST, REST_PATH, parentReference, ParentReference.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                checkRequiredParameter(parentReference, "content");
                checkRequiredParameter(parentReference.getId(), "ParentReference.getId()");
            }

            public Insert setAlt(String str) {
                return (Insert) super.setAlt(str);
            }

            public Insert setFields(String str) {
                return (Insert) super.setFields(str);
            }

            public Insert setKey(String str) {
                return (Insert) super.setKey(str);
            }

            public Insert setOauthToken(String str) {
                return (Insert) super.setOauthToken(str);
            }

            public Insert setPrettyPrint(Boolean bool) {
                return (Insert) super.setPrettyPrint(bool);
            }

            public Insert setQuotaUser(String str) {
                return (Insert) super.setQuotaUser(str);
            }

            public Insert setUserIp(String str) {
                return (Insert) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Insert setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public Insert set(String str, Object obj) {
                return (Insert) super.set(str, obj);
            }
        }

        public class List extends DriveRequest<ParentList> {
            private static final String REST_PATH = "files/{fileId}/parents";
            @Key
            private String fileId;

            protected List(String str) {
                super(Drive.this, HttpMethods.GET, REST_PATH, null, ParentList.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public List setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public Delete delete(String str, String str2) throws IOException {
            AbstractGoogleClientRequest delete = new Delete(str, str2);
            Drive.this.initialize(delete);
            return delete;
        }

        public Get get(String str, String str2) throws IOException {
            AbstractGoogleClientRequest get = new Get(str, str2);
            Drive.this.initialize(get);
            return get;
        }

        public Insert insert(String str, ParentReference parentReference) throws IOException {
            AbstractGoogleClientRequest insert = new Insert(str, parentReference);
            Drive.this.initialize(insert);
            return insert;
        }

        public List list(String str) throws IOException {
            AbstractGoogleClientRequest list = new List(str);
            Drive.this.initialize(list);
            return list;
        }
    }

    public class Permissions {

        public class Delete extends DriveRequest<Void> {
            private static final String REST_PATH = "files/{fileId}/permissions/{permissionId}";
            @Key
            private String fileId;
            @Key
            private String permissionId;

            protected Delete(String str, String str2) {
                super(Drive.this, HttpMethods.DELETE, REST_PATH, null, Void.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                this.permissionId = (String) Preconditions.checkNotNull(str2, "Required parameter permissionId must be specified.");
            }

            public Delete setAlt(String str) {
                return (Delete) super.setAlt(str);
            }

            public Delete setFields(String str) {
                return (Delete) super.setFields(str);
            }

            public Delete setKey(String str) {
                return (Delete) super.setKey(str);
            }

            public Delete setOauthToken(String str) {
                return (Delete) super.setOauthToken(str);
            }

            public Delete setPrettyPrint(Boolean bool) {
                return (Delete) super.setPrettyPrint(bool);
            }

            public Delete setQuotaUser(String str) {
                return (Delete) super.setQuotaUser(str);
            }

            public Delete setUserIp(String str) {
                return (Delete) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Delete setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getPermissionId() {
                return this.permissionId;
            }

            public Delete setPermissionId(String str) {
                this.permissionId = str;
                return this;
            }

            public Delete set(String str, Object obj) {
                return (Delete) super.set(str, obj);
            }
        }

        public class Get extends DriveRequest<Permission> {
            private static final String REST_PATH = "files/{fileId}/permissions/{permissionId}";
            @Key
            private String fileId;
            @Key
            private String permissionId;

            protected Get(String str, String str2) {
                super(Drive.this, HttpMethods.GET, REST_PATH, null, Permission.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                this.permissionId = (String) Preconditions.checkNotNull(str2, "Required parameter permissionId must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public Get setAlt(String str) {
                return (Get) super.setAlt(str);
            }

            public Get setFields(String str) {
                return (Get) super.setFields(str);
            }

            public Get setKey(String str) {
                return (Get) super.setKey(str);
            }

            public Get setOauthToken(String str) {
                return (Get) super.setOauthToken(str);
            }

            public Get setPrettyPrint(Boolean bool) {
                return (Get) super.setPrettyPrint(bool);
            }

            public Get setQuotaUser(String str) {
                return (Get) super.setQuotaUser(str);
            }

            public Get setUserIp(String str) {
                return (Get) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Get setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getPermissionId() {
                return this.permissionId;
            }

            public Get setPermissionId(String str) {
                this.permissionId = str;
                return this;
            }

            public Get set(String str, Object obj) {
                return (Get) super.set(str, obj);
            }
        }

        public class GetIdForEmail extends DriveRequest<PermissionId> {
            private static final String REST_PATH = "permissionIds/{email}";
            @Key
            private String email;

            protected GetIdForEmail(String str) {
                super(Drive.this, HttpMethods.GET, REST_PATH, null, PermissionId.class);
                this.email = (String) Preconditions.checkNotNull(str, "Required parameter email must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public GetIdForEmail setAlt(String str) {
                return (GetIdForEmail) super.setAlt(str);
            }

            public GetIdForEmail setFields(String str) {
                return (GetIdForEmail) super.setFields(str);
            }

            public GetIdForEmail setKey(String str) {
                return (GetIdForEmail) super.setKey(str);
            }

            public GetIdForEmail setOauthToken(String str) {
                return (GetIdForEmail) super.setOauthToken(str);
            }

            public GetIdForEmail setPrettyPrint(Boolean bool) {
                return (GetIdForEmail) super.setPrettyPrint(bool);
            }

            public GetIdForEmail setQuotaUser(String str) {
                return (GetIdForEmail) super.setQuotaUser(str);
            }

            public GetIdForEmail setUserIp(String str) {
                return (GetIdForEmail) super.setUserIp(str);
            }

            public String getEmail() {
                return this.email;
            }

            public GetIdForEmail setEmail(String str) {
                this.email = str;
                return this;
            }

            public GetIdForEmail set(String str, Object obj) {
                return (GetIdForEmail) super.set(str, obj);
            }
        }

        public class Insert extends DriveRequest<Permission> {
            private static final String REST_PATH = "files/{fileId}/permissions";
            @Key
            private String emailMessage;
            @Key
            private String fileId;
            @Key
            private Boolean sendNotificationEmails;

            protected Insert(String str, Permission permission) {
                super(Drive.this, HttpMethods.POST, REST_PATH, permission, Permission.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                checkRequiredParameter(permission, "content");
                checkRequiredParameter(permission.getRole(), "Permission.getRole()");
                checkRequiredParameter(permission, "content");
                checkRequiredParameter(permission.getType(), "Permission.getType()");
            }

            public Insert setAlt(String str) {
                return (Insert) super.setAlt(str);
            }

            public Insert setFields(String str) {
                return (Insert) super.setFields(str);
            }

            public Insert setKey(String str) {
                return (Insert) super.setKey(str);
            }

            public Insert setOauthToken(String str) {
                return (Insert) super.setOauthToken(str);
            }

            public Insert setPrettyPrint(Boolean bool) {
                return (Insert) super.setPrettyPrint(bool);
            }

            public Insert setQuotaUser(String str) {
                return (Insert) super.setQuotaUser(str);
            }

            public Insert setUserIp(String str) {
                return (Insert) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Insert setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getEmailMessage() {
                return this.emailMessage;
            }

            public Insert setEmailMessage(String str) {
                this.emailMessage = str;
                return this;
            }

            public Boolean getSendNotificationEmails() {
                return this.sendNotificationEmails;
            }

            public Insert setSendNotificationEmails(Boolean bool) {
                this.sendNotificationEmails = bool;
                return this;
            }

            public boolean isSendNotificationEmails() {
                if (this.sendNotificationEmails == null || this.sendNotificationEmails == Data.NULL_BOOLEAN) {
                    return true;
                }
                return this.sendNotificationEmails.booleanValue();
            }

            public Insert set(String str, Object obj) {
                return (Insert) super.set(str, obj);
            }
        }

        public class List extends DriveRequest<PermissionList> {
            private static final String REST_PATH = "files/{fileId}/permissions";
            @Key
            private String fileId;

            protected List(String str) {
                super(Drive.this, HttpMethods.GET, REST_PATH, null, PermissionList.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public List setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public class Patch extends DriveRequest<Permission> {
            private static final String REST_PATH = "files/{fileId}/permissions/{permissionId}";
            @Key
            private String fileId;
            @Key
            private String permissionId;
            @Key
            private Boolean transferOwnership;

            protected Patch(String str, String str2, Permission permission) {
                super(Drive.this, HttpMethods.PATCH, REST_PATH, permission, Permission.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                this.permissionId = (String) Preconditions.checkNotNull(str2, "Required parameter permissionId must be specified.");
            }

            public Patch setAlt(String str) {
                return (Patch) super.setAlt(str);
            }

            public Patch setFields(String str) {
                return (Patch) super.setFields(str);
            }

            public Patch setKey(String str) {
                return (Patch) super.setKey(str);
            }

            public Patch setOauthToken(String str) {
                return (Patch) super.setOauthToken(str);
            }

            public Patch setPrettyPrint(Boolean bool) {
                return (Patch) super.setPrettyPrint(bool);
            }

            public Patch setQuotaUser(String str) {
                return (Patch) super.setQuotaUser(str);
            }

            public Patch setUserIp(String str) {
                return (Patch) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Patch setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getPermissionId() {
                return this.permissionId;
            }

            public Patch setPermissionId(String str) {
                this.permissionId = str;
                return this;
            }

            public Boolean getTransferOwnership() {
                return this.transferOwnership;
            }

            public Patch setTransferOwnership(Boolean bool) {
                this.transferOwnership = bool;
                return this;
            }

            public boolean isTransferOwnership() {
                if (this.transferOwnership == null || this.transferOwnership == Data.NULL_BOOLEAN) {
                    return false;
                }
                return this.transferOwnership.booleanValue();
            }

            public Patch set(String str, Object obj) {
                return (Patch) super.set(str, obj);
            }
        }

        public class Update extends DriveRequest<Permission> {
            private static final String REST_PATH = "files/{fileId}/permissions/{permissionId}";
            @Key
            private String fileId;
            @Key
            private String permissionId;
            @Key
            private Boolean transferOwnership;

            protected Update(String str, String str2, Permission permission) {
                super(Drive.this, HttpMethods.PUT, REST_PATH, permission, Permission.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                this.permissionId = (String) Preconditions.checkNotNull(str2, "Required parameter permissionId must be specified.");
            }

            public Update setAlt(String str) {
                return (Update) super.setAlt(str);
            }

            public Update setFields(String str) {
                return (Update) super.setFields(str);
            }

            public Update setKey(String str) {
                return (Update) super.setKey(str);
            }

            public Update setOauthToken(String str) {
                return (Update) super.setOauthToken(str);
            }

            public Update setPrettyPrint(Boolean bool) {
                return (Update) super.setPrettyPrint(bool);
            }

            public Update setQuotaUser(String str) {
                return (Update) super.setQuotaUser(str);
            }

            public Update setUserIp(String str) {
                return (Update) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Update setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getPermissionId() {
                return this.permissionId;
            }

            public Update setPermissionId(String str) {
                this.permissionId = str;
                return this;
            }

            public Boolean getTransferOwnership() {
                return this.transferOwnership;
            }

            public Update setTransferOwnership(Boolean bool) {
                this.transferOwnership = bool;
                return this;
            }

            public boolean isTransferOwnership() {
                if (this.transferOwnership == null || this.transferOwnership == Data.NULL_BOOLEAN) {
                    return false;
                }
                return this.transferOwnership.booleanValue();
            }

            public Update set(String str, Object obj) {
                return (Update) super.set(str, obj);
            }
        }

        public Delete delete(String str, String str2) throws IOException {
            AbstractGoogleClientRequest delete = new Delete(str, str2);
            Drive.this.initialize(delete);
            return delete;
        }

        public Get get(String str, String str2) throws IOException {
            AbstractGoogleClientRequest get = new Get(str, str2);
            Drive.this.initialize(get);
            return get;
        }

        public GetIdForEmail getIdForEmail(String str) throws IOException {
            AbstractGoogleClientRequest getIdForEmail = new GetIdForEmail(str);
            Drive.this.initialize(getIdForEmail);
            return getIdForEmail;
        }

        public Insert insert(String str, Permission permission) throws IOException {
            AbstractGoogleClientRequest insert = new Insert(str, permission);
            Drive.this.initialize(insert);
            return insert;
        }

        public List list(String str) throws IOException {
            AbstractGoogleClientRequest list = new List(str);
            Drive.this.initialize(list);
            return list;
        }

        public Patch patch(String str, String str2, Permission permission) throws IOException {
            AbstractGoogleClientRequest patch = new Patch(str, str2, permission);
            Drive.this.initialize(patch);
            return patch;
        }

        public Update update(String str, String str2, Permission permission) throws IOException {
            AbstractGoogleClientRequest update = new Update(str, str2, permission);
            Drive.this.initialize(update);
            return update;
        }
    }

    public class Properties {

        public class Delete extends DriveRequest<Void> {
            private static final String REST_PATH = "files/{fileId}/properties/{propertyKey}";
            @Key
            private String fileId;
            @Key
            private String propertyKey;
            @Key
            private String visibility;

            protected Delete(String str, String str2) {
                super(Drive.this, HttpMethods.DELETE, REST_PATH, null, Void.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                this.propertyKey = (String) Preconditions.checkNotNull(str2, "Required parameter propertyKey must be specified.");
            }

            public Delete setAlt(String str) {
                return (Delete) super.setAlt(str);
            }

            public Delete setFields(String str) {
                return (Delete) super.setFields(str);
            }

            public Delete setKey(String str) {
                return (Delete) super.setKey(str);
            }

            public Delete setOauthToken(String str) {
                return (Delete) super.setOauthToken(str);
            }

            public Delete setPrettyPrint(Boolean bool) {
                return (Delete) super.setPrettyPrint(bool);
            }

            public Delete setQuotaUser(String str) {
                return (Delete) super.setQuotaUser(str);
            }

            public Delete setUserIp(String str) {
                return (Delete) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Delete setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getPropertyKey() {
                return this.propertyKey;
            }

            public Delete setPropertyKey(String str) {
                this.propertyKey = str;
                return this;
            }

            public String getVisibility() {
                return this.visibility;
            }

            public Delete setVisibility(String str) {
                this.visibility = str;
                return this;
            }

            public Delete set(String str, Object obj) {
                return (Delete) super.set(str, obj);
            }
        }

        public class Get extends DriveRequest<Property> {
            private static final String REST_PATH = "files/{fileId}/properties/{propertyKey}";
            @Key
            private String fileId;
            @Key
            private String propertyKey;
            @Key
            private String visibility;

            protected Get(String str, String str2) {
                super(Drive.this, HttpMethods.GET, REST_PATH, null, Property.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                this.propertyKey = (String) Preconditions.checkNotNull(str2, "Required parameter propertyKey must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public Get setAlt(String str) {
                return (Get) super.setAlt(str);
            }

            public Get setFields(String str) {
                return (Get) super.setFields(str);
            }

            public Get setKey(String str) {
                return (Get) super.setKey(str);
            }

            public Get setOauthToken(String str) {
                return (Get) super.setOauthToken(str);
            }

            public Get setPrettyPrint(Boolean bool) {
                return (Get) super.setPrettyPrint(bool);
            }

            public Get setQuotaUser(String str) {
                return (Get) super.setQuotaUser(str);
            }

            public Get setUserIp(String str) {
                return (Get) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Get setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getPropertyKey() {
                return this.propertyKey;
            }

            public Get setPropertyKey(String str) {
                this.propertyKey = str;
                return this;
            }

            public String getVisibility() {
                return this.visibility;
            }

            public Get setVisibility(String str) {
                this.visibility = str;
                return this;
            }

            public Get set(String str, Object obj) {
                return (Get) super.set(str, obj);
            }
        }

        public class Insert extends DriveRequest<Property> {
            private static final String REST_PATH = "files/{fileId}/properties";
            @Key
            private String fileId;

            protected Insert(String str, Property property) {
                super(Drive.this, HttpMethods.POST, REST_PATH, property, Property.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
            }

            public Insert setAlt(String str) {
                return (Insert) super.setAlt(str);
            }

            public Insert setFields(String str) {
                return (Insert) super.setFields(str);
            }

            public Insert setKey(String str) {
                return (Insert) super.setKey(str);
            }

            public Insert setOauthToken(String str) {
                return (Insert) super.setOauthToken(str);
            }

            public Insert setPrettyPrint(Boolean bool) {
                return (Insert) super.setPrettyPrint(bool);
            }

            public Insert setQuotaUser(String str) {
                return (Insert) super.setQuotaUser(str);
            }

            public Insert setUserIp(String str) {
                return (Insert) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Insert setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public Insert set(String str, Object obj) {
                return (Insert) super.set(str, obj);
            }
        }

        public class List extends DriveRequest<PropertyList> {
            private static final String REST_PATH = "files/{fileId}/properties";
            @Key
            private String fileId;

            protected List(String str) {
                super(Drive.this, HttpMethods.GET, REST_PATH, null, PropertyList.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public List setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public class Patch extends DriveRequest<Property> {
            private static final String REST_PATH = "files/{fileId}/properties/{propertyKey}";
            @Key
            private String fileId;
            @Key
            private String propertyKey;
            @Key
            private String visibility;

            protected Patch(String str, String str2, Property property) {
                super(Drive.this, HttpMethods.PATCH, REST_PATH, property, Property.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                this.propertyKey = (String) Preconditions.checkNotNull(str2, "Required parameter propertyKey must be specified.");
            }

            public Patch setAlt(String str) {
                return (Patch) super.setAlt(str);
            }

            public Patch setFields(String str) {
                return (Patch) super.setFields(str);
            }

            public Patch setKey(String str) {
                return (Patch) super.setKey(str);
            }

            public Patch setOauthToken(String str) {
                return (Patch) super.setOauthToken(str);
            }

            public Patch setPrettyPrint(Boolean bool) {
                return (Patch) super.setPrettyPrint(bool);
            }

            public Patch setQuotaUser(String str) {
                return (Patch) super.setQuotaUser(str);
            }

            public Patch setUserIp(String str) {
                return (Patch) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Patch setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getPropertyKey() {
                return this.propertyKey;
            }

            public Patch setPropertyKey(String str) {
                this.propertyKey = str;
                return this;
            }

            public String getVisibility() {
                return this.visibility;
            }

            public Patch setVisibility(String str) {
                this.visibility = str;
                return this;
            }

            public Patch set(String str, Object obj) {
                return (Patch) super.set(str, obj);
            }
        }

        public class Update extends DriveRequest<Property> {
            private static final String REST_PATH = "files/{fileId}/properties/{propertyKey}";
            @Key
            private String fileId;
            @Key
            private String propertyKey;
            @Key
            private String visibility;

            protected Update(String str, String str2, Property property) {
                super(Drive.this, HttpMethods.PUT, REST_PATH, property, Property.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                this.propertyKey = (String) Preconditions.checkNotNull(str2, "Required parameter propertyKey must be specified.");
            }

            public Update setAlt(String str) {
                return (Update) super.setAlt(str);
            }

            public Update setFields(String str) {
                return (Update) super.setFields(str);
            }

            public Update setKey(String str) {
                return (Update) super.setKey(str);
            }

            public Update setOauthToken(String str) {
                return (Update) super.setOauthToken(str);
            }

            public Update setPrettyPrint(Boolean bool) {
                return (Update) super.setPrettyPrint(bool);
            }

            public Update setQuotaUser(String str) {
                return (Update) super.setQuotaUser(str);
            }

            public Update setUserIp(String str) {
                return (Update) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Update setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getPropertyKey() {
                return this.propertyKey;
            }

            public Update setPropertyKey(String str) {
                this.propertyKey = str;
                return this;
            }

            public String getVisibility() {
                return this.visibility;
            }

            public Update setVisibility(String str) {
                this.visibility = str;
                return this;
            }

            public Update set(String str, Object obj) {
                return (Update) super.set(str, obj);
            }
        }

        public Delete delete(String str, String str2) throws IOException {
            AbstractGoogleClientRequest delete = new Delete(str, str2);
            Drive.this.initialize(delete);
            return delete;
        }

        public Get get(String str, String str2) throws IOException {
            AbstractGoogleClientRequest get = new Get(str, str2);
            Drive.this.initialize(get);
            return get;
        }

        public Insert insert(String str, Property property) throws IOException {
            AbstractGoogleClientRequest insert = new Insert(str, property);
            Drive.this.initialize(insert);
            return insert;
        }

        public List list(String str) throws IOException {
            AbstractGoogleClientRequest list = new List(str);
            Drive.this.initialize(list);
            return list;
        }

        public Patch patch(String str, String str2, Property property) throws IOException {
            AbstractGoogleClientRequest patch = new Patch(str, str2, property);
            Drive.this.initialize(patch);
            return patch;
        }

        public Update update(String str, String str2, Property property) throws IOException {
            AbstractGoogleClientRequest update = new Update(str, str2, property);
            Drive.this.initialize(update);
            return update;
        }
    }

    public class Realtime {

        public class Get extends DriveRequest<Void> {
            private static final String REST_PATH = "files/{fileId}/realtime";
            @Key
            private String fileId;
            @Key
            private Integer revision;

            protected Get(String str) {
                super(Drive.this, HttpMethods.GET, REST_PATH, null, Void.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                initializeMediaDownload();
            }

            public void executeMediaAndDownloadTo(OutputStream outputStream) throws IOException {
                super.executeMediaAndDownloadTo(outputStream);
            }

            public InputStream executeMediaAsInputStream() throws IOException {
                return super.executeMediaAsInputStream();
            }

            public HttpResponse executeMedia() throws IOException {
                return super.executeMedia();
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public Get setAlt(String str) {
                return (Get) super.setAlt(str);
            }

            public Get setFields(String str) {
                return (Get) super.setFields(str);
            }

            public Get setKey(String str) {
                return (Get) super.setKey(str);
            }

            public Get setOauthToken(String str) {
                return (Get) super.setOauthToken(str);
            }

            public Get setPrettyPrint(Boolean bool) {
                return (Get) super.setPrettyPrint(bool);
            }

            public Get setQuotaUser(String str) {
                return (Get) super.setQuotaUser(str);
            }

            public Get setUserIp(String str) {
                return (Get) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Get setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public Integer getRevision() {
                return this.revision;
            }

            public Get setRevision(Integer num) {
                this.revision = num;
                return this;
            }

            public Get set(String str, Object obj) {
                return (Get) super.set(str, obj);
            }
        }

        public class Update extends DriveRequest<Void> {
            private static final String REST_PATH = "files/{fileId}/realtime";
            @Key
            private String baseRevision;
            @Key
            private String fileId;

            protected Update(String str) {
                super(Drive.this, HttpMethods.PUT, REST_PATH, null, Void.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
            }

            protected Update(String str, AbstractInputStreamContent abstractInputStreamContent) {
                super(Drive.this, HttpMethods.PUT, "/upload/" + Drive.this.getServicePath() + REST_PATH, null, Void.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                initializeMediaUpload(abstractInputStreamContent);
            }

            public Update setAlt(String str) {
                return (Update) super.setAlt(str);
            }

            public Update setFields(String str) {
                return (Update) super.setFields(str);
            }

            public Update setKey(String str) {
                return (Update) super.setKey(str);
            }

            public Update setOauthToken(String str) {
                return (Update) super.setOauthToken(str);
            }

            public Update setPrettyPrint(Boolean bool) {
                return (Update) super.setPrettyPrint(bool);
            }

            public Update setQuotaUser(String str) {
                return (Update) super.setQuotaUser(str);
            }

            public Update setUserIp(String str) {
                return (Update) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Update setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getBaseRevision() {
                return this.baseRevision;
            }

            public Update setBaseRevision(String str) {
                this.baseRevision = str;
                return this;
            }

            public Update set(String str, Object obj) {
                return (Update) super.set(str, obj);
            }
        }

        public Get get(String str) throws IOException {
            AbstractGoogleClientRequest get = new Get(str);
            Drive.this.initialize(get);
            return get;
        }

        public Update update(String str) throws IOException {
            AbstractGoogleClientRequest update = new Update(str);
            Drive.this.initialize(update);
            return update;
        }

        public Update update(String str, AbstractInputStreamContent abstractInputStreamContent) throws IOException {
            AbstractGoogleClientRequest update = new Update(str, abstractInputStreamContent);
            Drive.this.initialize(update);
            return update;
        }
    }

    public class Replies {

        public class Delete extends DriveRequest<Void> {
            private static final String REST_PATH = "files/{fileId}/comments/{commentId}/replies/{replyId}";
            @Key
            private String commentId;
            @Key
            private String fileId;
            @Key
            private String replyId;

            protected Delete(String str, String str2, String str3) {
                super(Drive.this, HttpMethods.DELETE, REST_PATH, null, Void.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                this.commentId = (String) Preconditions.checkNotNull(str2, "Required parameter commentId must be specified.");
                this.replyId = (String) Preconditions.checkNotNull(str3, "Required parameter replyId must be specified.");
            }

            public Delete setAlt(String str) {
                return (Delete) super.setAlt(str);
            }

            public Delete setFields(String str) {
                return (Delete) super.setFields(str);
            }

            public Delete setKey(String str) {
                return (Delete) super.setKey(str);
            }

            public Delete setOauthToken(String str) {
                return (Delete) super.setOauthToken(str);
            }

            public Delete setPrettyPrint(Boolean bool) {
                return (Delete) super.setPrettyPrint(bool);
            }

            public Delete setQuotaUser(String str) {
                return (Delete) super.setQuotaUser(str);
            }

            public Delete setUserIp(String str) {
                return (Delete) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Delete setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getCommentId() {
                return this.commentId;
            }

            public Delete setCommentId(String str) {
                this.commentId = str;
                return this;
            }

            public String getReplyId() {
                return this.replyId;
            }

            public Delete setReplyId(String str) {
                this.replyId = str;
                return this;
            }

            public Delete set(String str, Object obj) {
                return (Delete) super.set(str, obj);
            }
        }

        public class Get extends DriveRequest<CommentReply> {
            private static final String REST_PATH = "files/{fileId}/comments/{commentId}/replies/{replyId}";
            @Key
            private String commentId;
            @Key
            private String fileId;
            @Key
            private Boolean includeDeleted;
            @Key
            private String replyId;

            protected Get(String str, String str2, String str3) {
                super(Drive.this, HttpMethods.GET, REST_PATH, null, CommentReply.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                this.commentId = (String) Preconditions.checkNotNull(str2, "Required parameter commentId must be specified.");
                this.replyId = (String) Preconditions.checkNotNull(str3, "Required parameter replyId must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public Get setAlt(String str) {
                return (Get) super.setAlt(str);
            }

            public Get setFields(String str) {
                return (Get) super.setFields(str);
            }

            public Get setKey(String str) {
                return (Get) super.setKey(str);
            }

            public Get setOauthToken(String str) {
                return (Get) super.setOauthToken(str);
            }

            public Get setPrettyPrint(Boolean bool) {
                return (Get) super.setPrettyPrint(bool);
            }

            public Get setQuotaUser(String str) {
                return (Get) super.setQuotaUser(str);
            }

            public Get setUserIp(String str) {
                return (Get) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Get setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getCommentId() {
                return this.commentId;
            }

            public Get setCommentId(String str) {
                this.commentId = str;
                return this;
            }

            public String getReplyId() {
                return this.replyId;
            }

            public Get setReplyId(String str) {
                this.replyId = str;
                return this;
            }

            public Boolean getIncludeDeleted() {
                return this.includeDeleted;
            }

            public Get setIncludeDeleted(Boolean bool) {
                this.includeDeleted = bool;
                return this;
            }

            public boolean isIncludeDeleted() {
                if (this.includeDeleted == null || this.includeDeleted == Data.NULL_BOOLEAN) {
                    return false;
                }
                return this.includeDeleted.booleanValue();
            }

            public Get set(String str, Object obj) {
                return (Get) super.set(str, obj);
            }
        }

        public class Insert extends DriveRequest<CommentReply> {
            private static final String REST_PATH = "files/{fileId}/comments/{commentId}/replies";
            @Key
            private String commentId;
            @Key
            private String fileId;

            protected Insert(String str, String str2, CommentReply commentReply) {
                super(Drive.this, HttpMethods.POST, REST_PATH, commentReply, CommentReply.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                this.commentId = (String) Preconditions.checkNotNull(str2, "Required parameter commentId must be specified.");
            }

            public Insert setAlt(String str) {
                return (Insert) super.setAlt(str);
            }

            public Insert setFields(String str) {
                return (Insert) super.setFields(str);
            }

            public Insert setKey(String str) {
                return (Insert) super.setKey(str);
            }

            public Insert setOauthToken(String str) {
                return (Insert) super.setOauthToken(str);
            }

            public Insert setPrettyPrint(Boolean bool) {
                return (Insert) super.setPrettyPrint(bool);
            }

            public Insert setQuotaUser(String str) {
                return (Insert) super.setQuotaUser(str);
            }

            public Insert setUserIp(String str) {
                return (Insert) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Insert setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getCommentId() {
                return this.commentId;
            }

            public Insert setCommentId(String str) {
                this.commentId = str;
                return this;
            }

            public Insert set(String str, Object obj) {
                return (Insert) super.set(str, obj);
            }
        }

        public class List extends DriveRequest<CommentReplyList> {
            private static final String REST_PATH = "files/{fileId}/comments/{commentId}/replies";
            @Key
            private String commentId;
            @Key
            private String fileId;
            @Key
            private Boolean includeDeleted;
            @Key
            private Integer maxResults;
            @Key
            private String pageToken;

            protected List(String str, String str2) {
                super(Drive.this, HttpMethods.GET, REST_PATH, null, CommentReplyList.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                this.commentId = (String) Preconditions.checkNotNull(str2, "Required parameter commentId must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public List setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getCommentId() {
                return this.commentId;
            }

            public List setCommentId(String str) {
                this.commentId = str;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public List setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public Boolean getIncludeDeleted() {
                return this.includeDeleted;
            }

            public List setIncludeDeleted(Boolean bool) {
                this.includeDeleted = bool;
                return this;
            }

            public boolean isIncludeDeleted() {
                if (this.includeDeleted == null || this.includeDeleted == Data.NULL_BOOLEAN) {
                    return false;
                }
                return this.includeDeleted.booleanValue();
            }

            public Integer getMaxResults() {
                return this.maxResults;
            }

            public List setMaxResults(Integer num) {
                this.maxResults = num;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public class Patch extends DriveRequest<CommentReply> {
            private static final String REST_PATH = "files/{fileId}/comments/{commentId}/replies/{replyId}";
            @Key
            private String commentId;
            @Key
            private String fileId;
            @Key
            private String replyId;

            protected Patch(String str, String str2, String str3, CommentReply commentReply) {
                super(Drive.this, HttpMethods.PATCH, REST_PATH, commentReply, CommentReply.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                this.commentId = (String) Preconditions.checkNotNull(str2, "Required parameter commentId must be specified.");
                this.replyId = (String) Preconditions.checkNotNull(str3, "Required parameter replyId must be specified.");
            }

            public Patch setAlt(String str) {
                return (Patch) super.setAlt(str);
            }

            public Patch setFields(String str) {
                return (Patch) super.setFields(str);
            }

            public Patch setKey(String str) {
                return (Patch) super.setKey(str);
            }

            public Patch setOauthToken(String str) {
                return (Patch) super.setOauthToken(str);
            }

            public Patch setPrettyPrint(Boolean bool) {
                return (Patch) super.setPrettyPrint(bool);
            }

            public Patch setQuotaUser(String str) {
                return (Patch) super.setQuotaUser(str);
            }

            public Patch setUserIp(String str) {
                return (Patch) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Patch setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getCommentId() {
                return this.commentId;
            }

            public Patch setCommentId(String str) {
                this.commentId = str;
                return this;
            }

            public String getReplyId() {
                return this.replyId;
            }

            public Patch setReplyId(String str) {
                this.replyId = str;
                return this;
            }

            public Patch set(String str, Object obj) {
                return (Patch) super.set(str, obj);
            }
        }

        public class Update extends DriveRequest<CommentReply> {
            private static final String REST_PATH = "files/{fileId}/comments/{commentId}/replies/{replyId}";
            @Key
            private String commentId;
            @Key
            private String fileId;
            @Key
            private String replyId;

            protected Update(String str, String str2, String str3, CommentReply commentReply) {
                super(Drive.this, HttpMethods.PUT, REST_PATH, commentReply, CommentReply.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                this.commentId = (String) Preconditions.checkNotNull(str2, "Required parameter commentId must be specified.");
                this.replyId = (String) Preconditions.checkNotNull(str3, "Required parameter replyId must be specified.");
                checkRequiredParameter(commentReply, "content");
                checkRequiredParameter(commentReply.getContent(), "CommentReply.getContent()");
            }

            public Update setAlt(String str) {
                return (Update) super.setAlt(str);
            }

            public Update setFields(String str) {
                return (Update) super.setFields(str);
            }

            public Update setKey(String str) {
                return (Update) super.setKey(str);
            }

            public Update setOauthToken(String str) {
                return (Update) super.setOauthToken(str);
            }

            public Update setPrettyPrint(Boolean bool) {
                return (Update) super.setPrettyPrint(bool);
            }

            public Update setQuotaUser(String str) {
                return (Update) super.setQuotaUser(str);
            }

            public Update setUserIp(String str) {
                return (Update) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Update setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getCommentId() {
                return this.commentId;
            }

            public Update setCommentId(String str) {
                this.commentId = str;
                return this;
            }

            public String getReplyId() {
                return this.replyId;
            }

            public Update setReplyId(String str) {
                this.replyId = str;
                return this;
            }

            public Update set(String str, Object obj) {
                return (Update) super.set(str, obj);
            }
        }

        public Delete delete(String str, String str2, String str3) throws IOException {
            AbstractGoogleClientRequest delete = new Delete(str, str2, str3);
            Drive.this.initialize(delete);
            return delete;
        }

        public Get get(String str, String str2, String str3) throws IOException {
            AbstractGoogleClientRequest get = new Get(str, str2, str3);
            Drive.this.initialize(get);
            return get;
        }

        public Insert insert(String str, String str2, CommentReply commentReply) throws IOException {
            AbstractGoogleClientRequest insert = new Insert(str, str2, commentReply);
            Drive.this.initialize(insert);
            return insert;
        }

        public List list(String str, String str2) throws IOException {
            AbstractGoogleClientRequest list = new List(str, str2);
            Drive.this.initialize(list);
            return list;
        }

        public Patch patch(String str, String str2, String str3, CommentReply commentReply) throws IOException {
            AbstractGoogleClientRequest patch = new Patch(str, str2, str3, commentReply);
            Drive.this.initialize(patch);
            return patch;
        }

        public Update update(String str, String str2, String str3, CommentReply commentReply) throws IOException {
            AbstractGoogleClientRequest update = new Update(str, str2, str3, commentReply);
            Drive.this.initialize(update);
            return update;
        }
    }

    public class Revisions {

        public class Delete extends DriveRequest<Void> {
            private static final String REST_PATH = "files/{fileId}/revisions/{revisionId}";
            @Key
            private String fileId;
            @Key
            private String revisionId;

            protected Delete(String str, String str2) {
                super(Drive.this, HttpMethods.DELETE, REST_PATH, null, Void.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                this.revisionId = (String) Preconditions.checkNotNull(str2, "Required parameter revisionId must be specified.");
            }

            public Delete setAlt(String str) {
                return (Delete) super.setAlt(str);
            }

            public Delete setFields(String str) {
                return (Delete) super.setFields(str);
            }

            public Delete setKey(String str) {
                return (Delete) super.setKey(str);
            }

            public Delete setOauthToken(String str) {
                return (Delete) super.setOauthToken(str);
            }

            public Delete setPrettyPrint(Boolean bool) {
                return (Delete) super.setPrettyPrint(bool);
            }

            public Delete setQuotaUser(String str) {
                return (Delete) super.setQuotaUser(str);
            }

            public Delete setUserIp(String str) {
                return (Delete) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Delete setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getRevisionId() {
                return this.revisionId;
            }

            public Delete setRevisionId(String str) {
                this.revisionId = str;
                return this;
            }

            public Delete set(String str, Object obj) {
                return (Delete) super.set(str, obj);
            }
        }

        public class Get extends DriveRequest<Revision> {
            private static final String REST_PATH = "files/{fileId}/revisions/{revisionId}";
            @Key
            private String fileId;
            @Key
            private String revisionId;

            protected Get(String str, String str2) {
                super(Drive.this, HttpMethods.GET, REST_PATH, null, Revision.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                this.revisionId = (String) Preconditions.checkNotNull(str2, "Required parameter revisionId must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public Get setAlt(String str) {
                return (Get) super.setAlt(str);
            }

            public Get setFields(String str) {
                return (Get) super.setFields(str);
            }

            public Get setKey(String str) {
                return (Get) super.setKey(str);
            }

            public Get setOauthToken(String str) {
                return (Get) super.setOauthToken(str);
            }

            public Get setPrettyPrint(Boolean bool) {
                return (Get) super.setPrettyPrint(bool);
            }

            public Get setQuotaUser(String str) {
                return (Get) super.setQuotaUser(str);
            }

            public Get setUserIp(String str) {
                return (Get) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Get setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getRevisionId() {
                return this.revisionId;
            }

            public Get setRevisionId(String str) {
                this.revisionId = str;
                return this;
            }

            public Get set(String str, Object obj) {
                return (Get) super.set(str, obj);
            }
        }

        public class List extends DriveRequest<RevisionList> {
            private static final String REST_PATH = "files/{fileId}/revisions";
            @Key
            private String fileId;

            protected List(String str) {
                super(Drive.this, HttpMethods.GET, REST_PATH, null, RevisionList.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public List setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public class Patch extends DriveRequest<Revision> {
            private static final String REST_PATH = "files/{fileId}/revisions/{revisionId}";
            @Key
            private String fileId;
            @Key
            private String revisionId;

            protected Patch(String str, String str2, Revision revision) {
                super(Drive.this, HttpMethods.PATCH, REST_PATH, revision, Revision.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                this.revisionId = (String) Preconditions.checkNotNull(str2, "Required parameter revisionId must be specified.");
            }

            public Patch setAlt(String str) {
                return (Patch) super.setAlt(str);
            }

            public Patch setFields(String str) {
                return (Patch) super.setFields(str);
            }

            public Patch setKey(String str) {
                return (Patch) super.setKey(str);
            }

            public Patch setOauthToken(String str) {
                return (Patch) super.setOauthToken(str);
            }

            public Patch setPrettyPrint(Boolean bool) {
                return (Patch) super.setPrettyPrint(bool);
            }

            public Patch setQuotaUser(String str) {
                return (Patch) super.setQuotaUser(str);
            }

            public Patch setUserIp(String str) {
                return (Patch) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Patch setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getRevisionId() {
                return this.revisionId;
            }

            public Patch setRevisionId(String str) {
                this.revisionId = str;
                return this;
            }

            public Patch set(String str, Object obj) {
                return (Patch) super.set(str, obj);
            }
        }

        public class Update extends DriveRequest<Revision> {
            private static final String REST_PATH = "files/{fileId}/revisions/{revisionId}";
            @Key
            private String fileId;
            @Key
            private String revisionId;

            protected Update(String str, String str2, Revision revision) {
                super(Drive.this, HttpMethods.PUT, REST_PATH, revision, Revision.class);
                this.fileId = (String) Preconditions.checkNotNull(str, "Required parameter fileId must be specified.");
                this.revisionId = (String) Preconditions.checkNotNull(str2, "Required parameter revisionId must be specified.");
            }

            public Update setAlt(String str) {
                return (Update) super.setAlt(str);
            }

            public Update setFields(String str) {
                return (Update) super.setFields(str);
            }

            public Update setKey(String str) {
                return (Update) super.setKey(str);
            }

            public Update setOauthToken(String str) {
                return (Update) super.setOauthToken(str);
            }

            public Update setPrettyPrint(Boolean bool) {
                return (Update) super.setPrettyPrint(bool);
            }

            public Update setQuotaUser(String str) {
                return (Update) super.setQuotaUser(str);
            }

            public Update setUserIp(String str) {
                return (Update) super.setUserIp(str);
            }

            public String getFileId() {
                return this.fileId;
            }

            public Update setFileId(String str) {
                this.fileId = str;
                return this;
            }

            public String getRevisionId() {
                return this.revisionId;
            }

            public Update setRevisionId(String str) {
                this.revisionId = str;
                return this;
            }

            public Update set(String str, Object obj) {
                return (Update) super.set(str, obj);
            }
        }

        public Delete delete(String str, String str2) throws IOException {
            AbstractGoogleClientRequest delete = new Delete(str, str2);
            Drive.this.initialize(delete);
            return delete;
        }

        public Get get(String str, String str2) throws IOException {
            AbstractGoogleClientRequest get = new Get(str, str2);
            Drive.this.initialize(get);
            return get;
        }

        public List list(String str) throws IOException {
            AbstractGoogleClientRequest list = new List(str);
            Drive.this.initialize(list);
            return list;
        }

        public Patch patch(String str, String str2, Revision revision) throws IOException {
            AbstractGoogleClientRequest patch = new Patch(str, str2, revision);
            Drive.this.initialize(patch);
            return patch;
        }

        public Update update(String str, String str2, Revision revision) throws IOException {
            AbstractGoogleClientRequest update = new Update(str, str2, revision);
            Drive.this.initialize(update);
            return update;
        }
    }

    static {
        boolean z = GoogleUtils.MAJOR_VERSION.intValue() == 1 && GoogleUtils.MINOR_VERSION.intValue() >= 15;
        Preconditions.checkState(z, "You are currently running with version %s of google-api-client. You need at least version 1.15 of google-api-client to run version 1.20.0 of the Drive API library.", GoogleUtils.VERSION);
    }

    public Drive(HttpTransport httpTransport, JsonFactory jsonFactory, HttpRequestInitializer httpRequestInitializer) {
        this(new Builder(httpTransport, jsonFactory, httpRequestInitializer));
    }

    Drive(Builder builder) {
        super(builder);
    }

    protected void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
        super.initialize(abstractGoogleClientRequest);
    }

    public About about() {
        return new About();
    }

    public Apps apps() {
        return new Apps();
    }

    public Changes changes() {
        return new Changes();
    }

    public Channels channels() {
        return new Channels();
    }

    public Children children() {
        return new Children();
    }

    public Comments comments() {
        return new Comments();
    }

    public Files files() {
        return new Files();
    }

    public Parents parents() {
        return new Parents();
    }

    public Permissions permissions() {
        return new Permissions();
    }

    public Properties properties() {
        return new Properties();
    }

    public Realtime realtime() {
        return new Realtime();
    }

    public Replies replies() {
        return new Replies();
    }

    public Revisions revisions() {
        return new Revisions();
    }
}
