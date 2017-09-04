package com.google.api.client.http;

import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public final class ByteArrayContent extends AbstractInputStreamContent {
    private final byte[] byteArray;
    private final int length;
    private final int offset;

    public ByteArrayContent(String type, byte[] array) {
        this(type, array, 0, array.length);
    }

    public ByteArrayContent(String type, byte[] array, int offset, int length) {
        boolean z;
        super(type);
        this.byteArray = (byte[]) Preconditions.checkNotNull(array);
        if (offset < 0 || length < 0 || offset + length > array.length) {
            z = false;
        } else {
            z = true;
        }
        Preconditions.checkArgument(z, "offset %s, length %s, array length %s", Integer.valueOf(offset), Integer.valueOf(length), Integer.valueOf(array.length));
        this.offset = offset;
        this.length = length;
    }

    public static ByteArrayContent fromString(String type, String contentString) {
        return new ByteArrayContent(type, StringUtils.getBytesUtf8(contentString));
    }

    public long getLength() {
        return (long) this.length;
    }

    public boolean retrySupported() {
        return true;
    }

    public InputStream getInputStream() {
        return new ByteArrayInputStream(this.byteArray, this.offset, this.length);
    }

    public ByteArrayContent setType(String type) {
        return (ByteArrayContent) super.setType(type);
    }

    public ByteArrayContent setCloseInputStream(boolean closeInputStream) {
        return (ByteArrayContent) super.setCloseInputStream(closeInputStream);
    }
}
