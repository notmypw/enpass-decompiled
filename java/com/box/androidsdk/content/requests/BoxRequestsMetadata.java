package com.box.androidsdk.content.requests;

import com.box.androidsdk.content.models.BoxArray;
import com.box.androidsdk.content.models.BoxJsonObject;
import com.box.androidsdk.content.models.BoxMetadata;
import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.models.BoxVoid;
import com.box.androidsdk.content.requests.BoxRequest.ContentTypes;
import com.box.androidsdk.content.requests.BoxRequest.Methods;
import com.github.clans.fab.BuildConfig;
import java.util.Map;

public class BoxRequestsMetadata {

    public static class AddFileMetadata extends BoxRequest<BoxMetadata, AddFileMetadata> {
        public AddFileMetadata(Map<String, Object> values, String requestUrl, BoxSession session) {
            super(BoxMetadata.class, requestUrl, session);
            this.mRequestMethod = Methods.POST;
            setValues(values);
        }

        protected AddFileMetadata setValues(Map<String, Object> map) {
            this.mBodyMap.putAll(map);
            return this;
        }
    }

    public static class DeleteFileMetadata extends BoxRequest<BoxVoid, DeleteFileMetadata> {
        public DeleteFileMetadata(String requestUrl, BoxSession session) {
            super(BoxVoid.class, requestUrl, session);
            this.mRequestMethod = Methods.DELETE;
        }
    }

    public static class GetFileMetadata extends BoxRequest<BoxMetadata, GetFileMetadata> {
        public GetFileMetadata(String requestUrl, BoxSession session) {
            super(BoxMetadata.class, requestUrl, session);
            this.mRequestMethod = Methods.GET;
        }
    }

    public static class GetMetadataTemplateSchema extends BoxRequest<BoxMetadata, GetMetadataTemplateSchema> {
        public GetMetadataTemplateSchema(String requestUrl, BoxSession session) {
            super(BoxMetadata.class, requestUrl, session);
            this.mRequestMethod = Methods.GET;
        }
    }

    public static class GetMetadataTemplates extends BoxRequest<BoxMetadata, GetMetadataTemplates> {
        public GetMetadataTemplates(String requestUrl, BoxSession session) {
            super(BoxMetadata.class, requestUrl, session);
            this.mRequestMethod = Methods.GET;
        }
    }

    public static class UpdateFileMetadata extends BoxRequest<BoxMetadata, UpdateFileMetadata> {
        private BoxArray<BoxMetadataUpdateTask> mUpdateTasks;

        private class BoxMetadataUpdateTask extends BoxJsonObject {
            public static final String OPERATION = "op";
            public static final String PATH = "path";
            public static final String VALUE = "value";

            public BoxMetadataUpdateTask(Operations operation, String key, String value) {
                this.mProperties.put(OPERATION, operation.toString());
                this.mProperties.put(PATH, "/" + key);
                if (operation != Operations.REMOVE) {
                    this.mProperties.put(VALUE, value);
                }
            }
        }

        public enum Operations {
            ADD("add"),
            REPLACE("replace"),
            REMOVE("remove"),
            TEST("test");
            
            private String mName;

            private Operations(String name) {
                this.mName = name;
            }

            public String toString() {
                return this.mName;
            }
        }

        public UpdateFileMetadata(String requestUrl, BoxSession session) {
            super(BoxMetadata.class, requestUrl, session);
            this.mRequestMethod = Methods.PUT;
            this.mContentType = ContentTypes.JSON_PATCH;
            this.mUpdateTasks = new BoxArray();
        }

        protected UpdateFileMetadata setUpdateTasks(BoxArray<BoxMetadataUpdateTask> updateTasks) {
            this.mBodyMap.put(BoxRequest.JSON_OBJECT, updateTasks);
            return this;
        }

        public UpdateFileMetadata addUpdateTask(Operations operation, String key, String value) {
            this.mUpdateTasks.add(new BoxMetadataUpdateTask(operation, key, value));
            return setUpdateTasks(this.mUpdateTasks);
        }

        public UpdateFileMetadata addUpdateTask(Operations operation, String key) {
            return addUpdateTask(operation, key, BuildConfig.FLAVOR);
        }
    }
}
