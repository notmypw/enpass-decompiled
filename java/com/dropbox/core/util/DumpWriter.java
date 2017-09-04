package com.dropbox.core.util;

import com.box.androidsdk.content.BoxConstants;
import com.dropbox.core.json.JsonDateReader;
import java.util.Date;
import java.util.GregorianCalendar;

public abstract class DumpWriter {

    public static final class Multiline extends DumpWriter {
        private final StringBuilder buf;
        private int currentIndent;
        private final int indentAmount;
        boolean nl;

        public Multiline(StringBuilder buf, int indentAmount, int currentIndent, boolean nl) {
            this.nl = true;
            if (buf == null) {
                throw new IllegalArgumentException("'buf' must not be null");
            } else if (indentAmount < 0) {
                throw new IllegalArgumentException("'indentAmount' must be non-negative");
            } else if (currentIndent < 0) {
                throw new IllegalArgumentException("'currentIndent' must be non-negative");
            } else {
                this.buf = buf;
                this.indentAmount = indentAmount;
                this.currentIndent = currentIndent;
                this.nl = nl;
            }
        }

        public Multiline(StringBuilder buf, int indentAmount, boolean nl) {
            this(buf, indentAmount, 0, nl);
        }

        private void prefix() {
            if (this.nl) {
                int l = this.currentIndent;
                for (int i = 0; i < l; i++) {
                    this.buf.append(' ');
                }
            }
        }

        private void indentMore() {
            this.currentIndent += this.indentAmount;
        }

        private void indentLess() {
            if (this.indentAmount > this.currentIndent) {
                throw new IllegalStateException("indent went negative");
            }
            this.currentIndent -= this.indentAmount;
        }

        public DumpWriter recordStart(String name) {
            prefix();
            if (name != null) {
                this.buf.append(name).append(" ");
            }
            this.buf.append("{\n");
            this.nl = true;
            indentMore();
            return this;
        }

        public DumpWriter recordEnd() {
            if (this.nl) {
                indentLess();
                prefix();
                this.buf.append("}\n");
                this.nl = true;
                return this;
            }
            throw new AssertionError("called recordEnd() in a bad state");
        }

        public DumpWriter f(String name) {
            if (this.nl) {
                prefix();
                this.buf.append(name).append(" = ");
                this.nl = false;
                return this;
            }
            throw new AssertionError("called fieldStart() in a bad state");
        }

        public DumpWriter listStart() {
            prefix();
            this.buf.append("[\n");
            this.nl = true;
            indentMore();
            return this;
        }

        public DumpWriter listEnd() {
            if (this.nl) {
                indentLess();
                prefix();
                this.buf.append("]\n");
                this.nl = true;
                return this;
            }
            throw new AssertionError("called listEnd() in a bad state");
        }

        public DumpWriter verbatim(String s) {
            prefix();
            this.buf.append(s);
            this.buf.append('\n');
            this.nl = true;
            return this;
        }
    }

    public static final class Plain extends DumpWriter {
        private StringBuilder buf;
        private boolean needSep = false;

        public Plain(StringBuilder buf) {
            this.buf = buf;
        }

        private void sep() {
            if (this.needSep) {
                this.buf.append(", ");
            } else {
                this.needSep = true;
            }
        }

        public DumpWriter recordStart(String name) {
            if (name != null) {
                this.buf.append(name);
            }
            this.buf.append("(");
            this.needSep = false;
            return this;
        }

        public DumpWriter recordEnd() {
            this.buf.append(")");
            this.needSep = true;
            return this;
        }

        public DumpWriter f(String name) {
            sep();
            this.buf.append(name).append('=');
            this.needSep = false;
            return this;
        }

        public DumpWriter listStart() {
            sep();
            this.buf.append("[");
            this.needSep = false;
            return this;
        }

        public DumpWriter listEnd() {
            this.buf.append("]");
            this.needSep = true;
            return this;
        }

        public DumpWriter verbatim(String s) {
            sep();
            this.buf.append(s);
            return this;
        }
    }

    public abstract DumpWriter f(String str);

    public abstract DumpWriter listEnd();

    public abstract DumpWriter listStart();

    public abstract DumpWriter recordEnd();

    public abstract DumpWriter recordStart(String str);

    public abstract DumpWriter verbatim(String str);

    public DumpWriter fieldVerbatim(String name, String s) {
        return f(name).verbatim(s);
    }

    public DumpWriter v(Iterable<? extends Dumpable> list) {
        if (list == null) {
            verbatim("null");
        } else {
            listStart();
            for (Dumpable d : list) {
                v(d);
            }
            listEnd();
        }
        return this;
    }

    public DumpWriter v(String v) {
        if (v == null) {
            verbatim("null");
        } else {
            verbatim(StringUtil.jq(v));
        }
        return this;
    }

    public DumpWriter v(int v) {
        return verbatim(Integer.toString(v));
    }

    public DumpWriter v(long v) {
        return verbatim(Long.toString(v));
    }

    public DumpWriter v(boolean v) {
        return verbatim(Boolean.toString(v));
    }

    public DumpWriter v(float v) {
        return verbatim(Float.toString(v));
    }

    public DumpWriter v(double v) {
        return verbatim(Double.toString(v));
    }

    public DumpWriter v(Date v) {
        return verbatim(toStringDate(v));
    }

    public DumpWriter v(Long v) {
        return verbatim(v == null ? "null" : Long.toString(v.longValue()));
    }

    public DumpWriter v(Dumpable v) {
        if (v == null) {
            verbatim("null");
        } else {
            recordStart(v.getTypeName());
            v.dumpFields(this);
            recordEnd();
        }
        return this;
    }

    public static String toStringDate(Date date) {
        if (date == null) {
            return "null";
        }
        StringBuilder buf = new StringBuilder();
        GregorianCalendar c = new GregorianCalendar(JsonDateReader.UTC);
        c.setTime(date);
        String year = Integer.toString(c.get(1));
        String month = zeroPad(Integer.toString(c.get(2) + 1), 2);
        String day = zeroPad(Integer.toString(c.get(5)), 2);
        String hour = zeroPad(Integer.toString(c.get(11)), 2);
        String minute = zeroPad(Integer.toString(c.get(12)), 2);
        String second = zeroPad(Integer.toString(c.get(13)), 2);
        buf.append('\"');
        buf.append(year).append("/").append(month).append("/").append(day).append(" ");
        buf.append(hour).append(":").append(minute).append(":").append(second).append(" UTC");
        buf.append('\"');
        return buf.toString();
    }

    private static String zeroPad(String v, int desiredLength) {
        while (v.length() < desiredLength) {
            v = BoxConstants.ROOT_FOLDER_ID + v;
        }
        return v;
    }
}
