package com.eclipsesource.json;

import com.box.androidsdk.content.models.BoxFileVersion;
import com.github.clans.fab.R;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

class JsonParser {
    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private static final int MIN_BUFFER_SIZE = 10;
    private final char[] buffer;
    private int bufferOffset;
    private StringBuilder captureBuffer;
    private int captureStart;
    private int current;
    private int fill;
    private int index;
    private int line;
    private int lineOffset;
    private final Reader reader;

    JsonParser(String string) {
        this(new StringReader(string), Math.max(MIN_BUFFER_SIZE, Math.min(DEFAULT_BUFFER_SIZE, string.length())));
    }

    JsonParser(Reader reader) {
        this(reader, DEFAULT_BUFFER_SIZE);
    }

    JsonParser(Reader reader, int buffersize) {
        this.reader = reader;
        this.buffer = new char[buffersize];
        this.line = 1;
        this.captureStart = -1;
    }

    JsonValue parse() throws IOException {
        read();
        skipWhiteSpace();
        JsonValue result = readValue();
        skipWhiteSpace();
        if (isEndOfText()) {
            return result;
        }
        throw error("Unexpected character");
    }

    private JsonValue readValue() throws IOException {
        switch (this.current) {
            case R.styleable.FloatingActionMenu_menu_shadowRadius /*34*/:
                return readString();
            case 45:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
                return readNumber();
            case 91:
                return readArray();
            case 102:
                return readFalse();
            case 110:
                return readNull();
            case 116:
                return readTrue();
            case 123:
                return readObject();
            default:
                throw expected(BoxMetadataUpdateTask.VALUE);
        }
    }

    private JsonArray readArray() throws IOException {
        read();
        JsonArray array = new JsonArray();
        skipWhiteSpace();
        if (!readChar(']')) {
            do {
                skipWhiteSpace();
                array.add(readValue());
                skipWhiteSpace();
            } while (readChar(','));
            if (!readChar(']')) {
                throw expected("',' or ']'");
            }
        }
        return array;
    }

    private JsonObject readObject() throws IOException {
        read();
        JsonObject object = new JsonObject();
        skipWhiteSpace();
        if (!readChar('}')) {
            do {
                skipWhiteSpace();
                String name = readName();
                skipWhiteSpace();
                if (readChar(':')) {
                    skipWhiteSpace();
                    object.add(name, readValue());
                    skipWhiteSpace();
                } else {
                    throw expected("':'");
                }
            } while (readChar(','));
            if (!readChar('}')) {
                throw expected("',' or '}'");
            }
        }
        return object;
    }

    private String readName() throws IOException {
        if (this.current == 34) {
            return readStringInternal();
        }
        throw expected(BoxFileVersion.FIELD_NAME);
    }

    private JsonValue readNull() throws IOException {
        read();
        readRequiredChar('u');
        readRequiredChar('l');
        readRequiredChar('l');
        return JsonValue.NULL;
    }

    private JsonValue readTrue() throws IOException {
        read();
        readRequiredChar('r');
        readRequiredChar('u');
        readRequiredChar('e');
        return JsonValue.TRUE;
    }

    private JsonValue readFalse() throws IOException {
        read();
        readRequiredChar('a');
        readRequiredChar('l');
        readRequiredChar('s');
        readRequiredChar('e');
        return JsonValue.FALSE;
    }

    private void readRequiredChar(char ch) throws IOException {
        if (!readChar(ch)) {
            throw expected("'" + ch + "'");
        }
    }

    private JsonValue readString() throws IOException {
        return new JsonString(readStringInternal());
    }

    private String readStringInternal() throws IOException {
        read();
        startCapture();
        while (this.current != 34) {
            if (this.current == 92) {
                pauseCapture();
                readEscape();
                startCapture();
            } else if (this.current < 32) {
                throw expected("valid string character");
            } else {
                read();
            }
        }
        String string = endCapture();
        read();
        return string;
    }

    private void readEscape() throws IOException {
        read();
        switch (this.current) {
            case R.styleable.FloatingActionMenu_menu_shadowRadius /*34*/:
            case 47:
            case 92:
                this.captureBuffer.append((char) this.current);
                break;
            case 98:
                this.captureBuffer.append('\b');
                break;
            case 102:
                this.captureBuffer.append('\f');
                break;
            case 110:
                this.captureBuffer.append('\n');
                break;
            case 114:
                this.captureBuffer.append('\r');
                break;
            case 116:
                this.captureBuffer.append('\t');
                break;
            case 117:
                char[] hexChars = new char[4];
                int i = 0;
                while (i < 4) {
                    read();
                    if (isHexDigit()) {
                        hexChars[i] = (char) this.current;
                        i++;
                    } else {
                        throw expected("hexadecimal digit");
                    }
                }
                this.captureBuffer.append((char) Integer.parseInt(String.valueOf(hexChars), 16));
                break;
            default:
                throw expected("valid escape sequence");
        }
        read();
    }

    private JsonValue readNumber() throws IOException {
        startCapture();
        readChar('-');
        int firstDigit = this.current;
        if (readDigit()) {
            if (firstDigit != 48) {
                do {
                } while (readDigit());
            }
            readFraction();
            readExponent();
            return new JsonNumber(endCapture());
        }
        throw expected("digit");
    }

    private boolean readFraction() throws IOException {
        if (!readChar('.')) {
            return false;
        }
        if (readDigit()) {
            do {
            } while (readDigit());
            return true;
        }
        throw expected("digit");
    }

    private boolean readExponent() throws IOException {
        if (!readChar('e') && !readChar('E')) {
            return false;
        }
        if (!readChar('+')) {
            readChar('-');
        }
        if (readDigit()) {
            do {
            } while (readDigit());
            return true;
        }
        throw expected("digit");
    }

    private boolean readChar(char ch) throws IOException {
        if (this.current != ch) {
            return false;
        }
        read();
        return true;
    }

    private boolean readDigit() throws IOException {
        if (!isDigit()) {
            return false;
        }
        read();
        return true;
    }

    private void skipWhiteSpace() throws IOException {
        while (isWhiteSpace()) {
            read();
        }
    }

    private void read() throws IOException {
        if (isEndOfText()) {
            throw error("Unexpected end of input");
        }
        if (this.index == this.fill) {
            if (this.captureStart != -1) {
                this.captureBuffer.append(this.buffer, this.captureStart, this.fill - this.captureStart);
                this.captureStart = 0;
            }
            this.bufferOffset += this.fill;
            this.fill = this.reader.read(this.buffer, 0, this.buffer.length);
            this.index = 0;
            if (this.fill == -1) {
                this.current = -1;
                return;
            }
        }
        if (this.current == MIN_BUFFER_SIZE) {
            this.line++;
            this.lineOffset = this.bufferOffset + this.index;
        }
        char[] cArr = this.buffer;
        int i = this.index;
        this.index = i + 1;
        this.current = cArr[i];
    }

    private void startCapture() {
        if (this.captureBuffer == null) {
            this.captureBuffer = new StringBuilder();
        }
        this.captureStart = this.index - 1;
    }

    private void pauseCapture() {
        this.captureBuffer.append(this.buffer, this.captureStart, (this.current == -1 ? this.index : this.index - 1) - this.captureStart);
        this.captureStart = -1;
    }

    private String endCapture() {
        String captured;
        int end = this.current == -1 ? this.index : this.index - 1;
        if (this.captureBuffer.length() > 0) {
            this.captureBuffer.append(this.buffer, this.captureStart, end - this.captureStart);
            captured = this.captureBuffer.toString();
            this.captureBuffer.setLength(0);
        } else {
            captured = new String(this.buffer, this.captureStart, end - this.captureStart);
        }
        this.captureStart = -1;
        return captured;
    }

    private ParseException expected(String expected) {
        if (isEndOfText()) {
            return error("Unexpected end of input");
        }
        return error("Expected " + expected);
    }

    private ParseException error(String message) {
        int absIndex = this.bufferOffset + this.index;
        return new ParseException(message, isEndOfText() ? absIndex : absIndex - 1, this.line, (absIndex - this.lineOffset) - 1);
    }

    private boolean isWhiteSpace() {
        return this.current == 32 || this.current == 9 || this.current == MIN_BUFFER_SIZE || this.current == 13;
    }

    private boolean isDigit() {
        return this.current >= 48 && this.current <= 57;
    }

    private boolean isHexDigit() {
        return (this.current >= 48 && this.current <= 57) || ((this.current >= 97 && this.current <= 102) || (this.current >= 65 && this.current <= 70));
    }

    private boolean isEndOfText() {
        return this.current == -1;
    }
}
