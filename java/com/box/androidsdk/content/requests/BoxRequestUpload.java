package com.box.androidsdk.content.requests;

import com.box.androidsdk.content.BoxException;
import com.box.androidsdk.content.listeners.ProgressListener;
import com.box.androidsdk.content.models.BoxFolder;
import com.box.androidsdk.content.models.BoxJsonObject;
import com.box.androidsdk.content.models.BoxList;
import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.requests.BoxRequest.BoxRequestHandler;
import com.box.androidsdk.content.requests.BoxRequest.Methods;
import com.github.clans.fab.BuildConfig;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Locale;

public abstract class BoxRequestUpload<E extends BoxJsonObject, R extends BoxRequest<E, R>> extends BoxRequest<E, R> {
    Date mCreatedDate;
    File mFile;
    String mFileName;
    Date mModifiedDate;
    String mSha1;
    InputStream mStream;
    long mUploadSize;

    public BoxRequestUpload(Class<E> clazz, InputStream fileInputStream, String requestUrl, BoxSession session) {
        super(clazz, requestUrl, session);
        this.mRequestMethod = Methods.POST;
        this.mStream = fileInputStream;
        this.mFileName = BuildConfig.FLAVOR;
    }

    protected void setHeaders(BoxHttpRequest request) {
        super.setHeaders(request);
        if (this.mSha1 != null) {
            request.addHeader("Content-MD5", this.mSha1);
        }
    }

    protected InputStream getInputStream() throws FileNotFoundException {
        if (this.mStream != null) {
            return this.mStream;
        }
        return new FileInputStream(this.mFile);
    }

    protected BoxRequestMultipart createMultipartRequest() throws IOException, BoxException {
        BoxRequestMultipart httpRequest = new BoxRequestMultipart(buildUrl(), this.mRequestMethod, this.mListener);
        setHeaders(httpRequest);
        httpRequest.setFile(getInputStream(), this.mFileName, this.mUploadSize);
        if (this.mCreatedDate != null) {
            httpRequest.putField(BoxFolder.FIELD_CONTENT_CREATED_AT, this.mCreatedDate);
        }
        if (this.mModifiedDate != null) {
            httpRequest.putField(BoxFolder.FIELD_CONTENT_MODIFIED_AT, this.mModifiedDate);
        }
        return httpRequest;
    }

    protected BoxHttpRequest createHttpRequest() throws IOException, BoxException {
        BoxRequestMultipart httpRequest = createMultipartRequest();
        httpRequest.writeBody(httpRequest.mUrlConnection, this.mListener);
        return httpRequest;
    }

    public E send() throws BoxException {
        IOException e;
        InstantiationException e2;
        IllegalAccessException e3;
        BoxException e4;
        BoxRequestHandler requestHandler = getRequestHandler();
        BoxHttpResponse response = null;
        try {
            BoxHttpResponse response2 = new BoxHttpResponse(createHttpRequest().getUrlConnection());
            try {
                response2.open();
                logDebug(response2);
                if (requestHandler.isResponseSuccess(response2)) {
                    return (BoxJsonObject) ((BoxList) requestHandler.onResponse(BoxList.class, response2)).toArray()[0];
                }
                int code = response2.getResponseCode();
                throw new BoxException(String.format(Locale.ENGLISH, "An error occurred while sending the request (%s)", new Object[]{Integer.valueOf(code)}), response2);
            } catch (IOException e5) {
                e = e5;
                response = response2;
                throw handleSendException(requestHandler, response, e);
            } catch (InstantiationException e6) {
                e2 = e6;
                response = response2;
                throw handleSendException(requestHandler, response, e2);
            } catch (IllegalAccessException e7) {
                e3 = e7;
                response = response2;
                throw handleSendException(requestHandler, response, e3);
            } catch (BoxException e8) {
                e4 = e8;
                response = response2;
                throw handleSendException(requestHandler, response, e4);
            }
        } catch (IOException e9) {
            e = e9;
            throw handleSendException(requestHandler, response, e);
        } catch (InstantiationException e10) {
            e2 = e10;
            throw handleSendException(requestHandler, response, e2);
        } catch (IllegalAccessException e11) {
            e3 = e11;
            throw handleSendException(requestHandler, response, e3);
        } catch (BoxException e12) {
            e4 = e12;
            throw handleSendException(requestHandler, response, e4);
        }
    }

    private BoxException handleSendException(BoxRequestHandler requestHandler, BoxHttpResponse response, Exception ex) throws BoxException {
        BoxException e = ex instanceof BoxException ? (BoxException) ex : new BoxException("Couldn't connect to the Box API due to a network error.", (Throwable) ex);
        requestHandler.onException(this, response, e);
        return e;
    }

    public R setProgressListener(ProgressListener listener) {
        this.mListener = listener;
        return this;
    }

    public long getUploadSize() {
        return this.mUploadSize;
    }

    public R setUploadSize(long mUploadSize) {
        this.mUploadSize = mUploadSize;
        return this;
    }

    public Date getModifiedDate() {
        return this.mModifiedDate;
    }

    public R setModifiedDate(Date mModifiedDate) {
        this.mModifiedDate = mModifiedDate;
        return this;
    }

    public Date getCreatedDate() {
        return this.mCreatedDate;
    }

    public R setCreatedDate(Date mCreatedDate) {
        this.mCreatedDate = mCreatedDate;
        return this;
    }

    public String getSha1() {
        return this.mSha1;
    }

    public void setSha1(String sha1) {
        this.mSha1 = sha1;
    }

    public File getFile() {
        return this.mFile;
    }
}
