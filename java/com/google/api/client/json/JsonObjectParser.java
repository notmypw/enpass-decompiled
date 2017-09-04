package com.google.api.client.json;

import com.google.api.client.util.ObjectParser;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sets;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class JsonObjectParser implements ObjectParser {
    private final JsonFactory jsonFactory;
    private final Set<String> wrapperKeys;

    public static class Builder {
        final JsonFactory jsonFactory;
        Collection<String> wrapperKeys = Sets.newHashSet();

        public Builder(JsonFactory jsonFactory) {
            this.jsonFactory = (JsonFactory) Preconditions.checkNotNull(jsonFactory);
        }

        public JsonObjectParser build() {
            return new JsonObjectParser(this);
        }

        public final JsonFactory getJsonFactory() {
            return this.jsonFactory;
        }

        public final Collection<String> getWrapperKeys() {
            return this.wrapperKeys;
        }

        public Builder setWrapperKeys(Collection<String> wrapperKeys) {
            this.wrapperKeys = wrapperKeys;
            return this;
        }
    }

    private void initializeParser(com.google.api.client.json.JsonParser r8) throws java.io.IOException {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:854)
	at java.util.HashMap$KeyIterator.next(HashMap.java:885)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:286)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:173)
*/
        /*
        r7 = this;
        r2 = 1;
        r3 = 0;
        r4 = r7.wrapperKeys;
        r4 = r4.isEmpty();
        if (r4 == 0) goto L_0x000b;
    L_0x000a:
        return;
    L_0x000b:
        r0 = 1;
        r4 = r7.wrapperKeys;	 Catch:{ all -> 0x0032 }
        r1 = r8.skipToKey(r4);	 Catch:{ all -> 0x0032 }
        if (r1 == 0) goto L_0x0030;	 Catch:{ all -> 0x0032 }
    L_0x0014:
        r4 = r8.getCurrentToken();	 Catch:{ all -> 0x0032 }
        r5 = com.google.api.client.json.JsonToken.END_OBJECT;	 Catch:{ all -> 0x0032 }
        if (r4 == r5) goto L_0x0030;	 Catch:{ all -> 0x0032 }
    L_0x001c:
        r3 = "wrapper key(s) not found: %s";	 Catch:{ all -> 0x0032 }
        r4 = 1;	 Catch:{ all -> 0x0032 }
        r4 = new java.lang.Object[r4];	 Catch:{ all -> 0x0032 }
        r5 = 0;	 Catch:{ all -> 0x0032 }
        r6 = r7.wrapperKeys;	 Catch:{ all -> 0x0032 }
        r4[r5] = r6;	 Catch:{ all -> 0x0032 }
        com.google.api.client.util.Preconditions.checkArgument(r2, r3, r4);	 Catch:{ all -> 0x0032 }
        r0 = 0;
        if (r0 == 0) goto L_0x000a;
    L_0x002c:
        r8.close();
        goto L_0x000a;
    L_0x0030:
        r2 = r3;
        goto L_0x001c;
    L_0x0032:
        r2 = move-exception;
        if (r0 == 0) goto L_0x0038;
    L_0x0035:
        r8.close();
    L_0x0038:
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.api.client.json.JsonObjectParser.initializeParser(com.google.api.client.json.JsonParser):void");
    }

    public JsonObjectParser(JsonFactory jsonFactory) {
        this(new Builder(jsonFactory));
    }

    protected JsonObjectParser(Builder builder) {
        this.jsonFactory = builder.jsonFactory;
        this.wrapperKeys = new HashSet(builder.wrapperKeys);
    }

    public <T> T parseAndClose(InputStream in, Charset charset, Class<T> dataClass) throws IOException {
        return parseAndClose(in, charset, (Type) dataClass);
    }

    public Object parseAndClose(InputStream in, Charset charset, Type dataType) throws IOException {
        JsonParser parser = this.jsonFactory.createJsonParser(in, charset);
        initializeParser(parser);
        return parser.parse(dataType, true);
    }

    public <T> T parseAndClose(Reader reader, Class<T> dataClass) throws IOException {
        return parseAndClose(reader, (Type) dataClass);
    }

    public Object parseAndClose(Reader reader, Type dataType) throws IOException {
        JsonParser parser = this.jsonFactory.createJsonParser(reader);
        initializeParser(parser);
        return parser.parse(dataType, true);
    }

    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }

    public Set<String> getWrapperKeys() {
        return Collections.unmodifiableSet(this.wrapperKeys);
    }
}
