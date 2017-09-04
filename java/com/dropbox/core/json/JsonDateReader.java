package com.dropbox.core.json;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class JsonDateReader {
    public static final JsonReader<Date> Dropbox = new JsonReader<Date>() {
        public Date read(JsonParser parser) throws IOException, JsonReadException {
            JsonLocation l = parser.getCurrentLocation();
            try {
                Date d = JsonDateReader.parseDropboxDate(parser.getTextCharacters(), parser.getTextOffset(), parser.getTextLength());
                parser.nextToken();
                return d;
            } catch (JsonParseException ex) {
                throw JsonReadException.fromJackson(ex);
            } catch (ParseException ex2) {
                throw new JsonReadException("bad date: \"" + ex2.getMessage() + " at offset " + ex2.getErrorOffset(), l);
            }
        }
    };
    public static final JsonReader<Date> DropboxV2 = new JsonReader<Date>() {
        public Date read(JsonParser parser) throws IOException, JsonReadException {
            JsonLocation l = parser.getCurrentLocation();
            try {
                Date d = JsonDateReader.parseDropbox8601Date(parser.getTextCharacters(), parser.getTextOffset(), parser.getTextLength());
                parser.nextToken();
                return d;
            } catch (JsonParseException ex) {
                throw JsonReadException.fromJackson(ex);
            } catch (ParseException ex2) {
                throw new JsonReadException("bad date: \"" + ex2.getMessage() + " at offset " + ex2.getErrorOffset(), l);
            }
        }
    };
    public static final TimeZone UTC = TimeZone.getTimeZone("UTC");

    public static Date parseDropboxDate(char[] buffer, int offset, int length) throws ParseException {
        int i = offset;
        char[] b = buffer;
        if (length != 31) {
            throw new ParseException("expecting date to be 31 characters, got " + length, 0);
        } else if (b.length < i + 31 || i < 0) {
            throw new IllegalArgumentException("range is not within 'b'");
        } else {
            if (((b[i + 30] != '0' ? 1 : 0) | ((((((((((((b[i + 4] != ' ' ? 1 : 0) | (b[i + 3] != ',' ? 1 : 0)) | (b[i + 7] != ' ' ? 1 : 0)) | (b[i + 11] != ' ' ? 1 : 0)) | (b[i + 16] != ' ' ? 1 : 0)) | (b[i + 19] != ':' ? 1 : 0)) | (b[i + 22] != ':' ? 1 : 0)) | (b[i + 25] != ' ' ? 1 : 0)) | (b[i + 26] != '+' ? 1 : 0)) | (b[i + 27] != '0' ? 1 : 0)) | (b[i + 28] != '0' ? 1 : 0)) | (b[i + 29] != '0' ? 1 : 0))) != 0) {
                if (b[i + 3] != ',') {
                    throw new ParseException("expecting ','", 3);
                } else if (b[i + 4] != ' ') {
                    throw new ParseException("expecting ' '", 4);
                } else if (b[i + 7] != ' ') {
                    throw new ParseException("expecting ' '", 7);
                } else if (b[i + 11] != ' ') {
                    throw new ParseException("expecting ' '", 11);
                } else if (b[i + 16] != ' ') {
                    throw new ParseException("expecting ' '", 16);
                } else if (b[i + 19] != ':') {
                    throw new ParseException("expecting ':'", 19);
                } else if (b[i + 22] != ':') {
                    throw new ParseException("expecting ':'", 22);
                } else if (b[i + 25] != ' ') {
                    throw new ParseException("expecting ' '", 25);
                } else if (b[i + 26] != '+') {
                    throw new ParseException("expecting '+'", 26);
                } else if (b[i + 27] != '0') {
                    throw new ParseException("expecting '0'", 27);
                } else if (b[i + 28] != '0') {
                    throw new ParseException("expecting '0'", 28);
                } else if (b[i + 29] != '0') {
                    throw new ParseException("expecting '0'", 29);
                } else if (b[i + 30] != '0') {
                    throw new ParseException("expecting '0'", 30);
                } else {
                    throw new AssertionError("unreachable");
                }
            } else if (isValidDayOfWeek(b[i], b[i + 1], b[i + 2])) {
                int month = getMonthIndex(b[i + 8], b[i + 9], b[i + 10]);
                if (month == -1) {
                    throw new ParseException("invalid month", 8);
                }
                char d1 = b[i + 5];
                char d2 = b[i + 6];
                if (isDigit(d1) && isDigit(d2)) {
                    int dayOfMonth = ((d1 * 10) + d2) - 528;
                    char y1 = b[i + 12];
                    char y2 = b[i + 13];
                    char y3 = b[i + 14];
                    char y4 = b[i + 15];
                    if (((!isDigit(y4) ? 1 : 0) | (((!isDigit(y2) ? 1 : 0) | (!isDigit(y1) ? 1 : 0)) | (!isDigit(y3) ? 1 : 0))) != 0) {
                        throw new ParseException("invalid year", 12);
                    }
                    int year = ((((y1 * 1000) + (y2 * 100)) + (y3 * 10)) + y4) - 53328;
                    char h1 = b[i + 17];
                    char h2 = b[i + 18];
                    if (((!isDigit(h1) ? 1 : 0) | (!isDigit(h2) ? 1 : 0)) != 0) {
                        throw new ParseException("invalid hour", 17);
                    }
                    int hour = ((h1 * 10) + h2) - 528;
                    char m1 = b[i + 20];
                    char m2 = b[i + 21];
                    if (((!isDigit(m1) ? 1 : 0) | (!isDigit(m2) ? 1 : 0)) != 0) {
                        throw new ParseException("invalid minute", 20);
                    }
                    int minute = ((m1 * 10) + m2) - 528;
                    char s1 = b[i + 23];
                    char s2 = b[i + 24];
                    if (((!isDigit(s1) ? 1 : 0) | (!isDigit(s2) ? 1 : 0)) != 0) {
                        throw new ParseException("invalid second", 23);
                    }
                    GregorianCalendar c = new GregorianCalendar(year, month, dayOfMonth, hour, minute, ((s1 * 10) + s2) - 528);
                    c.setTimeZone(UTC);
                    return c.getTime();
                }
                throw new ParseException("invalid day of month", 5);
            } else {
                throw new ParseException("invalid day of week", i);
            }
        }
    }

    private static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    public static boolean isValidDayOfWeek(char a, char b, char c) {
        int i;
        switch (a) {
            case 'F':
                int i2;
                if (b == 'r') {
                    i = 1;
                } else {
                    i = 0;
                }
                if (c == 'i') {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                if ((i2 & i) == 0) {
                    return false;
                }
                return true;
            case 'M':
                if (b == 'o') {
                    i = 1;
                } else {
                    i = 0;
                }
                if (((c == 'n' ? 1 : 0) & i) == 0) {
                    return false;
                }
                return true;
            case 'S':
                if (b == 'u') {
                    i = 1;
                } else {
                    i = 0;
                }
                if (((c == 'n' ? 1 : 0) & i) != 0) {
                    return true;
                }
                if (b == 'a') {
                    i = 1;
                } else {
                    i = 0;
                }
                if (((c == 't' ? 1 : 0) & i) == 0) {
                    return false;
                }
                return true;
            case 'T':
                if (b == 'u') {
                    i = 1;
                } else {
                    i = 0;
                }
                if (((c == 'e' ? 1 : 0) & i) != 0) {
                    return true;
                }
                if (b == 'h') {
                    i = 1;
                } else {
                    i = 0;
                }
                if (((c == 'u' ? 1 : 0) & i) == 0) {
                    return false;
                }
                return true;
            case 'W':
                if (b == 'e') {
                    i = 1;
                } else {
                    i = 0;
                }
                if (((c == 'd' ? 1 : 0) & i) == 0) {
                    return false;
                }
                return true;
            default:
                return false;
        }
    }

    public static int getMonthIndex(char a, char b, char c) {
        int i = 1;
        int i2;
        switch (a) {
            case 'A':
                if (((c == 'r' ? 1 : 0) & (b == 'p' ? 1 : 0)) != 0) {
                    return 3;
                }
                i2 = b == 'u' ? 1 : 0;
                if (c != 'g') {
                    i = 0;
                }
                return (i2 & i) != 0 ? 7 : -1;
            case 'D':
                i2 = b == 'e' ? 1 : 0;
                if (c != 'c') {
                    i = 0;
                }
                return (i2 & i) != 0 ? 11 : -1;
            case 'F':
                if (((c == 'b' ? 1 : 0) & (b == 'e' ? 1 : 0)) != 0) {
                    return 1;
                }
                return 0;
            case 'J':
                if (b == 'a') {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                if (c != 'n') {
                    i = 0;
                }
                if ((i & i2) != 0) {
                    return 0;
                }
                if (b != 'u') {
                    return -1;
                }
                if (c == 'n') {
                    return 5;
                }
                return c == 'l' ? 6 : -1;
            case 'M':
                if (b != 'a') {
                    return -1;
                }
                if (c == 'r') {
                    return 2;
                }
                return c == 'y' ? 4 : -1;
            case 'N':
                i2 = b == 'o' ? 1 : 0;
                if (c != 'v') {
                    i = 0;
                }
                return (i2 & i) != 0 ? 10 : -1;
            case 'O':
                i2 = b == 'c' ? 1 : 0;
                if (c != 't') {
                    i = 0;
                }
                return (i2 & i) != 0 ? 9 : -1;
            case 'S':
                i2 = b == 'e' ? 1 : 0;
                if (c != 'p') {
                    i = 0;
                }
                return (i2 & i) != 0 ? 8 : -1;
            default:
                return -1;
        }
    }

    public static Date parseDropbox8601Date(char[] buffer, int offset, int length) throws ParseException {
        int i = offset;
        char[] b = buffer;
        if (length == 20 || length == 24) {
            DateFormat format;
            String s = new String(b, i, length);
            if (length == 20) {
                format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            } else {
                format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            }
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                Date result = format.parse(s);
                if (result != null) {
                    return result;
                }
                throw new ParseException("invalid date" + s, 0);
            } catch (IllegalArgumentException e) {
                throw new ParseException("invalid characters in date" + s, 0);
            }
        }
        throw new ParseException("expecting date to be 20 or 24 characters, got " + length, 0);
    }
}
