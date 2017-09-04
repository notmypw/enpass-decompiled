package com.google.api.client.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DateTime implements Serializable {
    private static final TimeZone GMT = TimeZone.getTimeZone("GMT");
    private static final Pattern RFC3339_PATTERN = Pattern.compile("^(\\d{4})-(\\d{2})-(\\d{2})([Tt](\\d{2}):(\\d{2}):(\\d{2})(\\.\\d+)?)?([Zz]|([+-])(\\d{2}):(\\d{2}))?");
    private static final long serialVersionUID = 1;
    private final boolean dateOnly;
    private final int tzShift;
    private final long value;

    public DateTime(Date date, TimeZone zone) {
        this(false, date.getTime(), zone == null ? null : Integer.valueOf(zone.getOffset(date.getTime()) / ExponentialBackOff.DEFAULT_MAX_INTERVAL_MILLIS));
    }

    public DateTime(long value) {
        this(false, value, null);
    }

    public DateTime(Date value) {
        this(value.getTime());
    }

    public DateTime(long value, int tzShift) {
        this(false, value, Integer.valueOf(tzShift));
    }

    public DateTime(boolean dateOnly, long value, Integer tzShift) {
        this.dateOnly = dateOnly;
        this.value = value;
        int offset = dateOnly ? 0 : tzShift == null ? TimeZone.getDefault().getOffset(value) / ExponentialBackOff.DEFAULT_MAX_INTERVAL_MILLIS : tzShift.intValue();
        this.tzShift = offset;
    }

    public DateTime(String value) {
        DateTime dateTime = parseRfc3339(value);
        this.dateOnly = dateTime.dateOnly;
        this.value = dateTime.value;
        this.tzShift = dateTime.tzShift;
    }

    public long getValue() {
        return this.value;
    }

    public boolean isDateOnly() {
        return this.dateOnly;
    }

    public int getTimeZoneShift() {
        return this.tzShift;
    }

    public String toStringRfc3339() {
        StringBuilder sb = new StringBuilder();
        Calendar dateTime = new GregorianCalendar(GMT);
        dateTime.setTimeInMillis(this.value + (((long) this.tzShift) * 60000));
        appendInt(sb, dateTime.get(1), 4);
        sb.append('-');
        appendInt(sb, dateTime.get(2) + 1, 2);
        sb.append('-');
        appendInt(sb, dateTime.get(5), 2);
        if (!this.dateOnly) {
            sb.append('T');
            appendInt(sb, dateTime.get(11), 2);
            sb.append(':');
            appendInt(sb, dateTime.get(12), 2);
            sb.append(':');
            appendInt(sb, dateTime.get(13), 2);
            if (dateTime.isSet(14)) {
                sb.append('.');
                appendInt(sb, dateTime.get(14), 3);
            }
            if (this.tzShift == 0) {
                sb.append('Z');
            } else {
                int absTzShift = this.tzShift;
                if (this.tzShift > 0) {
                    sb.append('+');
                } else {
                    sb.append('-');
                    absTzShift = -absTzShift;
                }
                int tzMinutes = absTzShift % 60;
                appendInt(sb, absTzShift / 60, 2);
                sb.append(':');
                appendInt(sb, tzMinutes, 2);
            }
        }
        return sb.toString();
    }

    public String toString() {
        return toStringRfc3339();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DateTime)) {
            return false;
        }
        DateTime other = (DateTime) o;
        if (this.dateOnly == other.dateOnly && this.value == other.value && this.tzShift == other.tzShift) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        long[] jArr = new long[3];
        jArr[0] = this.value;
        jArr[1] = this.dateOnly ? serialVersionUID : 0;
        jArr[2] = (long) this.tzShift;
        return Arrays.hashCode(jArr);
    }

    public static DateTime parseRfc3339(String str) throws NumberFormatException {
        Matcher matcher = RFC3339_PATTERN.matcher(str);
        if (matcher.matches()) {
            int year = Integer.parseInt(matcher.group(1));
            int month = Integer.parseInt(matcher.group(2)) - 1;
            int day = Integer.parseInt(matcher.group(3));
            boolean isTimeGiven = matcher.group(4) != null;
            String tzShiftRegexGroup = matcher.group(9);
            boolean isTzShiftGiven = tzShiftRegexGroup != null;
            int hourOfDay = 0;
            int minute = 0;
            int second = 0;
            int milliseconds = 0;
            Integer tzShiftInteger = null;
            if (!isTzShiftGiven || isTimeGiven) {
                boolean z;
                if (isTimeGiven) {
                    hourOfDay = Integer.parseInt(matcher.group(5));
                    minute = Integer.parseInt(matcher.group(6));
                    second = Integer.parseInt(matcher.group(7));
                    if (matcher.group(8) != null) {
                        milliseconds = (int) (((double) ((float) Integer.parseInt(matcher.group(8).substring(1)))) / Math.pow(10.0d, (double) (matcher.group(8).substring(1).length() - 3)));
                    }
                }
                Calendar dateTime = new GregorianCalendar(GMT);
                dateTime.set(year, month, day, hourOfDay, minute, second);
                dateTime.set(14, milliseconds);
                long value = dateTime.getTimeInMillis();
                if (isTimeGiven && isTzShiftGiven) {
                    int tzShift;
                    if (Character.toUpperCase(tzShiftRegexGroup.charAt(0)) == 'Z') {
                        tzShift = 0;
                    } else {
                        tzShift = (Integer.parseInt(matcher.group(11)) * 60) + Integer.parseInt(matcher.group(12));
                        if (matcher.group(10).charAt(0) == '-') {
                            tzShift = -tzShift;
                        }
                        value -= ((long) tzShift) * 60000;
                    }
                    tzShiftInteger = Integer.valueOf(tzShift);
                }
                if (isTimeGiven) {
                    z = false;
                } else {
                    z = true;
                }
                return new DateTime(z, value, tzShiftInteger);
            }
            throw new NumberFormatException("Invalid date/time format, cannot specify time zone shift without specifying time: " + str);
        }
        throw new NumberFormatException("Invalid date/time format: " + str);
    }

    private static void appendInt(StringBuilder sb, int num, int numDigits) {
        if (num < 0) {
            sb.append('-');
            num = -num;
        }
        int x = num;
        while (x > 0) {
            x /= 10;
            numDigits--;
        }
        for (int i = 0; i < numDigits; i++) {
            sb.append('0');
        }
        if (num != 0) {
            sb.append(num);
        }
    }
}
