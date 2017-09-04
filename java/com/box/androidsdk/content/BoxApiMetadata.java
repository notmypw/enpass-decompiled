package com.box.androidsdk.content;

import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.requests.BoxRequestsMetadata.AddFileMetadata;
import com.box.androidsdk.content.requests.BoxRequestsMetadata.DeleteFileMetadata;
import com.box.androidsdk.content.requests.BoxRequestsMetadata.GetFileMetadata;
import com.box.androidsdk.content.requests.BoxRequestsMetadata.GetMetadataTemplateSchema;
import com.box.androidsdk.content.requests.BoxRequestsMetadata.GetMetadataTemplates;
import com.box.androidsdk.content.requests.BoxRequestsMetadata.UpdateFileMetadata;
import java.util.LinkedHashMap;
import java.util.Locale;

public class BoxApiMetadata extends BoxApi {
    public static final String BOX_API_METADATA = "metadata";
    public static final String BOX_API_METADATA_SCHEMA = "schema";
    public static final String BOX_API_METADATA_TEMPLATES = "metadata_templates";
    public static final String BOX_API_SCOPE_ENTERPRISE = "enterprise";
    public static final String BOX_API_SCOPE_GLOBAL = "global";

    public BoxApiMetadata(BoxSession session) {
        super(session);
    }

    protected String getFilesUrl() {
        return String.format(Locale.ENGLISH, "%s/files", new Object[]{getBaseUri()});
    }

    protected String getFileInfoUrl(String id) {
        return String.format(Locale.ENGLISH, "%s/%s", new Object[]{getFilesUrl(), id});
    }

    protected String getFileMetadataUrl(String id) {
        return String.format(Locale.ENGLISH, "%s/%s", new Object[]{getFileInfoUrl(id), BOX_API_METADATA});
    }

    protected String getFileMetadataUrl(String id, String scope, String template) {
        return String.format(Locale.ENGLISH, "%s/%s/%s", new Object[]{getFileMetadataUrl(id), scope, template});
    }

    protected String getFileMetadataUrl(String id, String template) {
        return getFileMetadataUrl(id, BOX_API_SCOPE_ENTERPRISE, template);
    }

    protected String getMetadataTemplatesUrl(String scope) {
        return String.format(Locale.ENGLISH, "%s/%s/%s", new Object[]{getBaseUri(), BOX_API_METADATA_TEMPLATES, scope});
    }

    protected String getMetadataTemplatesUrl() {
        return getMetadataTemplatesUrl(BOX_API_SCOPE_ENTERPRISE);
    }

    protected String getMetadataTemplatesUrl(String scope, String template) {
        return String.format(Locale.ENGLISH, "%s/%s/%s", new Object[]{getMetadataTemplatesUrl(scope), template, BOX_API_METADATA_SCHEMA});
    }

    public AddFileMetadata getAddMetadataRequest(String id, LinkedHashMap<String, Object> values, String scope, String template) {
        return new AddFileMetadata(values, getFileMetadataUrl(id, scope, template), this.mSession);
    }

    public GetFileMetadata getMetadataRequest(String id) {
        return new GetFileMetadata(getFileMetadataUrl(id), this.mSession);
    }

    public GetFileMetadata getMetadataRequest(String id, String template) {
        return new GetFileMetadata(getFileMetadataUrl(id, template), this.mSession);
    }

    public UpdateFileMetadata getUpdateMetadataRequest(String id, String scope, String template) {
        return new UpdateFileMetadata(getFileMetadataUrl(id, scope, template), this.mSession);
    }

    public DeleteFileMetadata getDeleteMetadataTemplateRequest(String id, String template) {
        return new DeleteFileMetadata(getFileMetadataUrl(id, template), this.mSession);
    }

    public GetMetadataTemplates getMetadataTemplatesRequest() {
        return new GetMetadataTemplates(getMetadataTemplatesUrl(), this.mSession);
    }

    public GetMetadataTemplateSchema getMetadataTemplateSchemaRequest(String scope, String template) {
        return new GetMetadataTemplateSchema(getMetadataTemplatesUrl(scope, template), this.mSession);
    }

    public GetMetadataTemplateSchema getMetadataTemplateSchemaRequest(String template) {
        return getMetadataTemplateSchemaRequest(BOX_API_SCOPE_ENTERPRISE, template);
    }
}
