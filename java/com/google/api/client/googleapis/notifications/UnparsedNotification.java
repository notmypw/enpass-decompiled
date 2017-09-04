package com.google.api.client.googleapis.notifications;

import com.google.api.client.util.Beta;
import java.io.InputStream;

@Beta
public class UnparsedNotification extends AbstractNotification {
    private InputStream contentStream;
    private String contentType;

    public UnparsedNotification(long messageNumber, String resourceState, String resourceId, String resourceUri, String channelId) {
        super(messageNumber, resourceState, resourceId, resourceUri, channelId);
    }

    public final String getContentType() {
        return this.contentType;
    }

    public UnparsedNotification setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public final InputStream getContentStream() {
        return this.contentStream;
    }

    public UnparsedNotification setContentStream(InputStream contentStream) {
        this.contentStream = contentStream;
        return this;
    }

    public UnparsedNotification setMessageNumber(long messageNumber) {
        return (UnparsedNotification) super.setMessageNumber(messageNumber);
    }

    public UnparsedNotification setResourceState(String resourceState) {
        return (UnparsedNotification) super.setResourceState(resourceState);
    }

    public UnparsedNotification setResourceId(String resourceId) {
        return (UnparsedNotification) super.setResourceId(resourceId);
    }

    public UnparsedNotification setResourceUri(String resourceUri) {
        return (UnparsedNotification) super.setResourceUri(resourceUri);
    }

    public UnparsedNotification setChannelId(String channelId) {
        return (UnparsedNotification) super.setChannelId(channelId);
    }

    public UnparsedNotification setChannelExpiration(String channelExpiration) {
        return (UnparsedNotification) super.setChannelExpiration(channelExpiration);
    }

    public UnparsedNotification setChannelToken(String channelToken) {
        return (UnparsedNotification) super.setChannelToken(channelToken);
    }

    public UnparsedNotification setChanged(String changed) {
        return (UnparsedNotification) super.setChanged(changed);
    }

    public String toString() {
        return super.toStringHelper().add("contentType", this.contentType).toString();
    }
}
