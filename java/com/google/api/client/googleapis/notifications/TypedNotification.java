package com.google.api.client.googleapis.notifications;

import com.google.api.client.util.Beta;

@Beta
public class TypedNotification<T> extends AbstractNotification {
    private T content;

    public TypedNotification(long messageNumber, String resourceState, String resourceId, String resourceUri, String channelId) {
        super(messageNumber, resourceState, resourceId, resourceUri, channelId);
    }

    public TypedNotification(UnparsedNotification sourceNotification) {
        super(sourceNotification);
    }

    public final T getContent() {
        return this.content;
    }

    public TypedNotification<T> setContent(T content) {
        this.content = content;
        return this;
    }

    public TypedNotification<T> setMessageNumber(long messageNumber) {
        return (TypedNotification) super.setMessageNumber(messageNumber);
    }

    public TypedNotification<T> setResourceState(String resourceState) {
        return (TypedNotification) super.setResourceState(resourceState);
    }

    public TypedNotification<T> setResourceId(String resourceId) {
        return (TypedNotification) super.setResourceId(resourceId);
    }

    public TypedNotification<T> setResourceUri(String resourceUri) {
        return (TypedNotification) super.setResourceUri(resourceUri);
    }

    public TypedNotification<T> setChannelId(String channelId) {
        return (TypedNotification) super.setChannelId(channelId);
    }

    public TypedNotification<T> setChannelExpiration(String channelExpiration) {
        return (TypedNotification) super.setChannelExpiration(channelExpiration);
    }

    public TypedNotification<T> setChannelToken(String channelToken) {
        return (TypedNotification) super.setChannelToken(channelToken);
    }

    public TypedNotification<T> setChanged(String changed) {
        return (TypedNotification) super.setChanged(changed);
    }

    public String toString() {
        return super.toStringHelper().add("content", this.content).toString();
    }
}
