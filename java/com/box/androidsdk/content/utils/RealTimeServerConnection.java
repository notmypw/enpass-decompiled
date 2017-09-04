package com.box.androidsdk.content.utils;

import com.box.androidsdk.content.BoxException;
import com.box.androidsdk.content.BoxException.MaxAttemptsExceeded;
import com.box.androidsdk.content.BoxFutureTask;
import com.box.androidsdk.content.BoxFutureTask.OnCompletedListener;
import com.box.androidsdk.content.models.BoxListRealTimeServers;
import com.box.androidsdk.content.models.BoxRealTimeServer;
import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.models.BoxSimpleMessage;
import com.box.androidsdk.content.requests.BoxRequest;
import com.box.androidsdk.content.requests.BoxRequestsEvent.LongPollMessageRequest;
import com.box.androidsdk.content.requests.BoxResponse;
import java.net.SocketTimeoutException;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RealTimeServerConnection implements OnCompletedListener<BoxSimpleMessage> {
    private BoxRealTimeServer mBoxRealTimeServer;
    private final OnChangeListener mChangeListener;
    private final ThreadPoolExecutor mExecutor = SdkUtils.createDefaultThreadPoolExecutor(1, 1, 3600, TimeUnit.SECONDS);
    private BoxRequest mRequest;
    private int mRetries = 0;
    private BoxSession mSession;

    public interface OnChangeListener {
        void onChange(BoxSimpleMessage boxSimpleMessage, RealTimeServerConnection realTimeServerConnection);

        void onException(Exception exception, RealTimeServerConnection realTimeServerConnection);
    }

    public RealTimeServerConnection(BoxRequest getRealTimeServerRequest, OnChangeListener changeListener, BoxSession session) {
        this.mRequest = getRealTimeServerRequest;
        this.mSession = session;
        this.mChangeListener = changeListener;
    }

    public BoxRequest getRequest() {
        return this.mRequest;
    }

    public int getTimesRetried() {
        return this.mRetries;
    }

    public BoxRealTimeServer getRealTimeServer() {
        return this.mBoxRealTimeServer;
    }

    public FutureTask<BoxSimpleMessage> toTask() {
        return new FutureTask(new Callable<BoxSimpleMessage>() {
            public BoxSimpleMessage call() throws Exception {
                return RealTimeServerConnection.this.connect();
            }
        });
    }

    public BoxSimpleMessage connect() {
        this.mRetries = 0;
        try {
            this.mBoxRealTimeServer = (BoxRealTimeServer) ((BoxListRealTimeServers) this.mRequest.send()).get(0);
            LongPollMessageRequest messageRequest = new LongPollMessageRequest(this.mBoxRealTimeServer.getUrl(), this.mSession);
            messageRequest.setTimeOut(this.mBoxRealTimeServer.getFieldRetryTimeout().intValue() * 1000);
            boolean shouldRetry = true;
            do {
                BoxFutureTask<BoxSimpleMessage> task = null;
                try {
                    task = messageRequest.toTask().addOnCompletedListener(this);
                    this.mExecutor.submit(task);
                    BoxResponse<BoxSimpleMessage> response = (BoxResponse) task.get((long) this.mBoxRealTimeServer.getFieldRetryTimeout().intValue(), TimeUnit.SECONDS);
                    if (response.isSuccess() && !((BoxSimpleMessage) response.getResult()).getMessage().equals(BoxSimpleMessage.MESSAGE_RECONNECT)) {
                        return (BoxSimpleMessage) response.getResult();
                    }
                } catch (TimeoutException e) {
                    if (task != null) {
                        try {
                            task.cancel(true);
                        } catch (CancellationException e2) {
                        }
                    }
                } catch (InterruptedException e3) {
                    this.mChangeListener.onException(e3, this);
                } catch (TimeoutException e4) {
                    this.mChangeListener.onException(e4, this);
                }
                this.mRetries++;
                if (this.mBoxRealTimeServer.getMaxRetries().longValue() < ((long) this.mRetries)) {
                    shouldRetry = false;
                    continue;
                }
            } while (shouldRetry);
            this.mChangeListener.onException(new MaxAttemptsExceeded("Max retries exceeded, ", this.mRetries), this);
            return null;
        } catch (BoxException e5) {
            this.mChangeListener.onException(e5, this);
            return null;
        }
    }

    protected void handleResponse(BoxResponse<BoxSimpleMessage> response) {
        if (response.isSuccess()) {
            if (!((BoxSimpleMessage) response.getResult()).getMessage().equals(BoxSimpleMessage.MESSAGE_RECONNECT)) {
                this.mChangeListener.onChange((BoxSimpleMessage) response.getResult(), this);
            }
        } else if (!(response.getException() instanceof BoxException) || !(response.getException().getCause() instanceof SocketTimeoutException)) {
            this.mChangeListener.onException(response.getException(), this);
        }
    }

    public void onCompleted(BoxResponse<BoxSimpleMessage> response) {
        handleResponse(response);
    }
}
