package com.google.api.client.googleapis.media;

import com.google.api.client.http.HttpIOExceptionHandler;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Beta
class MediaUploadErrorHandler implements HttpIOExceptionHandler, HttpUnsuccessfulResponseHandler {
    static final Logger LOGGER = Logger.getLogger(MediaUploadErrorHandler.class.getName());
    private final HttpIOExceptionHandler originalIOExceptionHandler;
    private final HttpUnsuccessfulResponseHandler originalUnsuccessfulHandler;
    private final MediaHttpUploader uploader;

    public MediaUploadErrorHandler(MediaHttpUploader uploader, HttpRequest request) {
        this.uploader = (MediaHttpUploader) Preconditions.checkNotNull(uploader);
        this.originalIOExceptionHandler = request.getIOExceptionHandler();
        this.originalUnsuccessfulHandler = request.getUnsuccessfulResponseHandler();
        request.setIOExceptionHandler(this);
        request.setUnsuccessfulResponseHandler(this);
    }

    public boolean handleIOException(HttpRequest request, boolean supportsRetry) throws IOException {
        boolean handled = this.originalIOExceptionHandler != null && this.originalIOExceptionHandler.handleIOException(request, supportsRetry);
        if (handled) {
            try {
                this.uploader.serverErrorCallback();
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "exception thrown while calling server callback", e);
            }
        }
        return handled;
    }

    public boolean handleResponse(HttpRequest request, HttpResponse response, boolean supportsRetry) throws IOException {
        boolean handled = this.originalUnsuccessfulHandler != null && this.originalUnsuccessfulHandler.handleResponse(request, response, supportsRetry);
        if (handled && supportsRetry && response.getStatusCode() / 100 == 5) {
            try {
                this.uploader.serverErrorCallback();
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "exception thrown while calling server callback", e);
            }
        }
        return handled;
    }
}
