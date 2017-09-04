package com.dropbox.core.util;

import com.dropbox.core.util.DumpWriter.Multiline;
import com.dropbox.core.util.DumpWriter.Plain;

public abstract class Dumpable {
    protected abstract void dumpFields(DumpWriter dumpWriter);

    public final String toString() {
        StringBuilder buf = new StringBuilder();
        toString(buf);
        return buf.toString();
    }

    public final void toString(StringBuilder buf) {
        new Plain(buf).v(this);
    }

    public final String toStringMultiline() {
        StringBuilder buf = new StringBuilder();
        toStringMultiline(buf, 0, true);
        return buf.toString();
    }

    public final void toStringMultiline(StringBuilder buf, int currentIndent, boolean nl) {
        new Multiline(buf, 2, currentIndent, nl).v(this);
    }

    protected String getTypeName() {
        return null;
    }
}
