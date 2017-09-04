package com.google.api.client.util;

import java.io.IOException;
import java.io.OutputStream;

public class ByteArrayStreamingContent implements StreamingContent {
    private final byte[] byteArray;
    private final int length;
    private final int offset;

    public ByteArrayStreamingContent(byte[] byteArray) {
        this(byteArray, 0, byteArray.length);
    }

    public ByteArrayStreamingContent(byte[] byteArray, int offset, int length) {
        this.byteArray = (byte[]) Preconditions.checkNotNull(byteArray);
        boolean z = offset >= 0 && length >= 0 && offset + length <= byteArray.length;
        Preconditions.checkArgument(z);
        this.offset = offset;
        this.length = length;
    }

    public void writeTo(OutputStream out) throws IOException {
        out.write(this.byteArray, this.offset, this.length);
        out.flush();
    }
}
