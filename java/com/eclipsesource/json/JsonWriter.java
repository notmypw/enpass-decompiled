package com.eclipsesource.json;

import com.eclipsesource.json.JsonObject.Member;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

class JsonWriter {
    private static final char[] BS_CHARS = new char[]{'\\', '\\'};
    private static final int CONTROL_CHARACTERS_END = 31;
    private static final int CONTROL_CHARACTERS_START = 0;
    private static final char[] CR_CHARS = new char[]{'\\', 'r'};
    private static final char[] HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] LF_CHARS = new char[]{'\\', 'n'};
    private static final char[] QUOT_CHARS = new char[]{'\\', '\"'};
    private static final char[] TAB_CHARS = new char[]{'\\', 't'};
    private static final char[] UNICODE_2028_CHARS = new char[]{'\\', 'u', '2', '0', '2', '8'};
    private static final char[] UNICODE_2029_CHARS = new char[]{'\\', 'u', '2', '0', '2', '9'};
    protected final Writer writer;

    JsonWriter(Writer writer) {
        this.writer = writer;
    }

    void write(String string) throws IOException {
        this.writer.write(string);
    }

    void writeString(String string) throws IOException {
        this.writer.write(34);
        int length = string.length();
        int start = 0;
        char[] chars = new char[length];
        string.getChars(0, length, chars, 0);
        for (int index = 0; index < length; index++) {
            char[] replacement = getReplacementChars(chars[index]);
            if (replacement != null) {
                this.writer.write(chars, start, index - start);
                this.writer.write(replacement);
                start = index + 1;
            }
        }
        this.writer.write(chars, start, length - start);
        this.writer.write(34);
    }

    private static char[] getReplacementChars(char ch) {
        if (ch == '\"') {
            return QUOT_CHARS;
        }
        if (ch == '\\') {
            return BS_CHARS;
        }
        if (ch == '\n') {
            return LF_CHARS;
        }
        if (ch == '\r') {
            return CR_CHARS;
        }
        if (ch == '\t') {
            return TAB_CHARS;
        }
        if (ch == '\u2028') {
            return UNICODE_2028_CHARS;
        }
        if (ch == '\u2029') {
            return UNICODE_2029_CHARS;
        }
        if (ch < '\u0000' || ch > '\u001f') {
            return null;
        }
        char[] replacement = new char[]{'\\', 'u', '0', '0', '0', '0'};
        replacement[4] = HEX_DIGITS[(ch >> 4) & 15];
        replacement[5] = HEX_DIGITS[ch & 15];
        return replacement;
    }

    protected void writeObject(JsonObject object) throws IOException {
        writeBeginObject();
        boolean first = true;
        Iterator i$ = object.iterator();
        while (i$.hasNext()) {
            Member member = (Member) i$.next();
            if (!first) {
                writeObjectValueSeparator();
            }
            writeString(member.getName());
            writeNameValueSeparator();
            member.getValue().write(this);
            first = false;
        }
        writeEndObject();
    }

    protected void writeBeginObject() throws IOException {
        this.writer.write(123);
    }

    protected void writeEndObject() throws IOException {
        this.writer.write(125);
    }

    protected void writeNameValueSeparator() throws IOException {
        this.writer.write(58);
    }

    protected void writeObjectValueSeparator() throws IOException {
        this.writer.write(44);
    }

    protected void writeArray(JsonArray array) throws IOException {
        writeBeginArray();
        boolean first = true;
        Iterator i$ = array.iterator();
        while (i$.hasNext()) {
            JsonValue value = (JsonValue) i$.next();
            if (!first) {
                writeArrayValueSeparator();
            }
            value.write(this);
            first = false;
        }
        writeEndArray();
    }

    protected void writeBeginArray() throws IOException {
        this.writer.write(91);
    }

    protected void writeEndArray() throws IOException {
        this.writer.write(93);
    }

    protected void writeArrayValueSeparator() throws IOException {
        this.writer.write(44);
    }
}
