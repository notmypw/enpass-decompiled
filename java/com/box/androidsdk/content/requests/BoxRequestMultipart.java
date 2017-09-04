package com.box.androidsdk.content.requests;

import com.box.androidsdk.content.BoxException;
import com.box.androidsdk.content.listeners.ProgressListener;
import com.box.androidsdk.content.models.BoxFileVersion;
import com.box.androidsdk.content.requests.BoxRequest.Methods;
import com.box.androidsdk.content.utils.BoxDateFormat;
import com.box.androidsdk.content.utils.ProgressOutputStream;
import in.sinew.enpassengine.Attachment;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

class BoxRequestMultipart extends BoxHttpRequest {
    private static final String BOUNDARY = "da39a3ee5e6b4b0d3255bfef95601890afd80709";
    private static final int BUFFER_SIZE = 8192;
    private static final Logger LOGGER = Logger.getLogger(BoxRequestMultipart.class.getName());
    private Map<String, String> fields = new HashMap();
    private long fileSize;
    private String filename;
    private boolean firstBoundary = true;
    private InputStream inputStream;
    private final StringBuilder loggedRequest = new StringBuilder();
    private OutputStream outputStream;

    public BoxRequestMultipart(URL url, Methods method, ProgressListener listener) throws IOException {
        super(url, method, listener);
        addHeader("Content-Type", "multipart/form-data; boundary=da39a3ee5e6b4b0d3255bfef95601890afd80709");
    }

    public void putField(String key, String value) {
        this.fields.put(key, value);
    }

    public void putField(String key, Date value) {
        this.fields.put(key, BoxDateFormat.format(value));
    }

    public void setFile(InputStream inputStream, String filename) {
        this.inputStream = inputStream;
        this.filename = filename;
    }

    public void setFile(InputStream inputStream, String filename, long fileSize) {
        setFile(inputStream, filename);
        this.fileSize = fileSize;
    }

    public BoxHttpRequest setBody(InputStream body) throws IOException {
        throw new UnsupportedOperationException();
    }

    public void setBody(String body) {
        throw new UnsupportedOperationException();
    }

    protected void writeBody(HttpURLConnection connection, ProgressListener listener) throws BoxException {
        try {
            connection.setChunkedStreamingMode(0);
            connection.setDoOutput(true);
            this.outputStream = connection.getOutputStream();
            r5 = new String[2][];
            r5[0] = new String[]{BoxFileVersion.FIELD_NAME, Attachment.ATTACHMENT_FILENAME};
            r5[1] = new String[]{Attachment.ATTACHMENT_FILENAME, this.filename};
            writePartHeader(r5, "application/octet-stream");
            OutputStream fileContentsOutputStream = this.outputStream;
            if (listener != null) {
                fileContentsOutputStream = new ProgressOutputStream(this.outputStream, listener, this.fileSize);
            }
            byte[] buffer = new byte[BUFFER_SIZE];
            int n = this.inputStream.read(buffer);
            while (n != -1) {
                fileContentsOutputStream.write(buffer, 0, n);
                n = this.inputStream.read(buffer);
            }
            if (LOGGER.isLoggable(Level.FINE)) {
                this.loggedRequest.append("<File Contents Omitted>");
            }
            for (Entry<String, String> entry : this.fields.entrySet()) {
                String[][] strArr = new String[1][];
                strArr[0] = new String[]{BoxFileVersion.FIELD_NAME, (String) entry.getKey()};
                writePartHeader(strArr);
                writeOutput((String) entry.getValue());
            }
            writeBoundary();
        } catch (Throwable e) {
            throw new BoxException("Couldn't connect to the Box API due to a network error.", e);
        }
    }

    protected void resetBody() throws IOException {
        this.firstBoundary = true;
        this.inputStream.reset();
        this.loggedRequest.setLength(0);
    }

    protected String bodyToString() {
        return this.loggedRequest.toString();
    }

    private void writeBoundary() throws IOException {
        if (!this.firstBoundary) {
            writeOutput("\r\n");
        }
        this.firstBoundary = false;
        writeOutput("--");
        writeOutput(BOUNDARY);
    }

    private void writePartHeader(String[][] formData) throws IOException {
        writePartHeader(formData, null);
    }

    private void writePartHeader(String[][] formData, String contentType) throws IOException {
        writeBoundary();
        writeOutput("\r\n");
        writeOutput("Content-Disposition: form-data");
        for (int i = 0; i < formData.length; i++) {
            writeOutput("; ");
            writeOutput(formData[i][0]);
            writeOutput("=\"");
            writeOutput(formData[i][1]);
            writeOutput("\"");
        }
        if (contentType != null) {
            writeOutput("\r\nContent-Type: ");
            writeOutput(contentType);
        }
        writeOutput("\r\n\r\n");
    }

    private void writeOutput(String s) throws IOException {
        this.outputStream.write(s.getBytes(Charset.forName("UTF-8")));
        if (LOGGER.isLoggable(Level.FINE)) {
            this.loggedRequest.append(s);
        }
    }

    private void writeOutput(int b) throws IOException {
        this.outputStream.write(b);
    }
}
