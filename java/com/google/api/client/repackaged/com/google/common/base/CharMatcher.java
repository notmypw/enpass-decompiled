package com.google.api.client.repackaged.com.google.common.base;

import com.github.clans.fab.BuildConfig;
import com.github.clans.fab.R;
import com.google.api.client.repackaged.com.google.common.annotations.Beta;
import com.google.api.client.repackaged.com.google.common.annotations.GwtCompatible;
import com.google.api.client.repackaged.com.google.common.annotations.GwtIncompatible;
import com.samsung.android.sdk.pass.SpassFingerprint;
import in.sinew.enpass.IRemoteStorage;
import java.util.Arrays;
import java.util.BitSet;
import javax.annotation.CheckReturnValue;
import net.sqlcipher.database.SQLiteDatabase;

@GwtCompatible(emulated = true)
@Beta
public abstract class CharMatcher implements Predicate<Character> {
    public static final CharMatcher ANY = new FastMatcher("CharMatcher.ANY") {
        public boolean matches(char c) {
            return true;
        }

        public int indexIn(CharSequence sequence) {
            return sequence.length() == 0 ? -1 : CharMatcher.WHITESPACE_SHIFT;
        }

        public int indexIn(CharSequence sequence, int start) {
            int length = sequence.length();
            Preconditions.checkPositionIndex(start, length);
            return start == length ? -1 : start;
        }

        public int lastIndexIn(CharSequence sequence) {
            return sequence.length() - 1;
        }

        public boolean matchesAllOf(CharSequence sequence) {
            Preconditions.checkNotNull(sequence);
            return true;
        }

        public boolean matchesNoneOf(CharSequence sequence) {
            return sequence.length() == 0;
        }

        public String removeFrom(CharSequence sequence) {
            Preconditions.checkNotNull(sequence);
            return BuildConfig.FLAVOR;
        }

        public String replaceFrom(CharSequence sequence, char replacement) {
            char[] array = new char[sequence.length()];
            Arrays.fill(array, replacement);
            return new String(array);
        }

        public String replaceFrom(CharSequence sequence, CharSequence replacement) {
            StringBuilder retval = new StringBuilder(sequence.length() * replacement.length());
            for (int i = CharMatcher.WHITESPACE_SHIFT; i < sequence.length(); i++) {
                retval.append(replacement);
            }
            return retval.toString();
        }

        public String collapseFrom(CharSequence sequence, char replacement) {
            return sequence.length() == 0 ? BuildConfig.FLAVOR : String.valueOf(replacement);
        }

        public String trimFrom(CharSequence sequence) {
            Preconditions.checkNotNull(sequence);
            return BuildConfig.FLAVOR;
        }

        public int countIn(CharSequence sequence) {
            return sequence.length();
        }

        public CharMatcher and(CharMatcher other) {
            return (CharMatcher) Preconditions.checkNotNull(other);
        }

        public CharMatcher or(CharMatcher other) {
            Preconditions.checkNotNull(other);
            return this;
        }

        public CharMatcher negate() {
            return NONE;
        }
    };
    public static final CharMatcher ASCII = inRange('\u0000', '\u007f', "CharMatcher.ASCII");
    public static final CharMatcher BREAKING_WHITESPACE = new CharMatcher() {
        public /* bridge */ /* synthetic */ boolean apply(Object x0) {
            return super.apply((Character) x0);
        }

        public boolean matches(char c) {
            switch (c) {
                case IRemoteStorage.WEBDAV_REMOTE /*9*/:
                case IRemoteStorage.WEBDAV_REMOTE_USERNAME /*10*/:
                case IRemoteStorage.WEBDAV_REMOTE_PASSWORD /*11*/:
                case SpassFingerprint.STATUS_QUALITY_FAILED /*12*/:
                case SpassFingerprint.STATUS_USER_CANCELLED_BY_TOUCH_OUTSIDE /*13*/:
                case R.styleable.FloatingActionMenu_menu_openDirection /*32*/:
                case '\u0085':
                case '\u1680':
                case '\u2028':
                case '\u2029':
                case '\u205f':
                case '\u3000':
                    return true;
                case '\u2007':
                    return false;
                default:
                    if (c < '\u2000' || c > '\u200a') {
                        return false;
                    }
                    return true;
            }
        }

        public String toString() {
            return "CharMatcher.BREAKING_WHITESPACE";
        }
    };
    public static final CharMatcher DIGIT = new RangesMatcher("CharMatcher.DIGIT", ZEROES.toCharArray(), NINES.toCharArray());
    private static final int DISTINCT_CHARS = 65536;
    public static final CharMatcher INVISIBLE = new RangesMatcher("CharMatcher.INVISIBLE", "\u0000\u007f\u00ad\u0600\u061c\u06dd\u070f\u1680\u180e\u2000\u2028\u205f\u2066\u2067\u2068\u2069\u206a\u3000\ud800\ufeff\ufff9\ufffa".toCharArray(), " \u00a0\u00ad\u0604\u061c\u06dd\u070f\u1680\u180e\u200f\u202f\u2064\u2066\u2067\u2068\u2069\u206f\u3000\uf8ff\ufeff\ufff9\ufffb".toCharArray());
    public static final CharMatcher JAVA_DIGIT = new CharMatcher("CharMatcher.JAVA_DIGIT") {
        public /* bridge */ /* synthetic */ boolean apply(Object x0) {
            return super.apply((Character) x0);
        }

        public boolean matches(char c) {
            return Character.isDigit(c);
        }
    };
    public static final CharMatcher JAVA_ISO_CONTROL = inRange('\u0000', '\u001f').or(inRange('\u007f', '\u009f')).withToString("CharMatcher.JAVA_ISO_CONTROL");
    public static final CharMatcher JAVA_LETTER = new CharMatcher("CharMatcher.JAVA_LETTER") {
        public /* bridge */ /* synthetic */ boolean apply(Object x0) {
            return super.apply((Character) x0);
        }

        public boolean matches(char c) {
            return Character.isLetter(c);
        }
    };
    public static final CharMatcher JAVA_LETTER_OR_DIGIT = new CharMatcher("CharMatcher.JAVA_LETTER_OR_DIGIT") {
        public /* bridge */ /* synthetic */ boolean apply(Object x0) {
            return super.apply((Character) x0);
        }

        public boolean matches(char c) {
            return Character.isLetterOrDigit(c);
        }
    };
    public static final CharMatcher JAVA_LOWER_CASE = new CharMatcher("CharMatcher.JAVA_LOWER_CASE") {
        public /* bridge */ /* synthetic */ boolean apply(Object x0) {
            return super.apply((Character) x0);
        }

        public boolean matches(char c) {
            return Character.isLowerCase(c);
        }
    };
    public static final CharMatcher JAVA_UPPER_CASE = new CharMatcher("CharMatcher.JAVA_UPPER_CASE") {
        public /* bridge */ /* synthetic */ boolean apply(Object x0) {
            return super.apply((Character) x0);
        }

        public boolean matches(char c) {
            return Character.isUpperCase(c);
        }
    };
    private static final String NINES;
    public static final CharMatcher NONE = new FastMatcher("CharMatcher.NONE") {
        public boolean matches(char c) {
            return false;
        }

        public int indexIn(CharSequence sequence) {
            Preconditions.checkNotNull(sequence);
            return -1;
        }

        public int indexIn(CharSequence sequence, int start) {
            Preconditions.checkPositionIndex(start, sequence.length());
            return -1;
        }

        public int lastIndexIn(CharSequence sequence) {
            Preconditions.checkNotNull(sequence);
            return -1;
        }

        public boolean matchesAllOf(CharSequence sequence) {
            return sequence.length() == 0;
        }

        public boolean matchesNoneOf(CharSequence sequence) {
            Preconditions.checkNotNull(sequence);
            return true;
        }

        public String removeFrom(CharSequence sequence) {
            return sequence.toString();
        }

        public String replaceFrom(CharSequence sequence, char replacement) {
            return sequence.toString();
        }

        public String replaceFrom(CharSequence sequence, CharSequence replacement) {
            Preconditions.checkNotNull(replacement);
            return sequence.toString();
        }

        public String collapseFrom(CharSequence sequence, char replacement) {
            return sequence.toString();
        }

        public String trimFrom(CharSequence sequence) {
            return sequence.toString();
        }

        public String trimLeadingFrom(CharSequence sequence) {
            return sequence.toString();
        }

        public String trimTrailingFrom(CharSequence sequence) {
            return sequence.toString();
        }

        public int countIn(CharSequence sequence) {
            Preconditions.checkNotNull(sequence);
            return CharMatcher.WHITESPACE_SHIFT;
        }

        public CharMatcher and(CharMatcher other) {
            Preconditions.checkNotNull(other);
            return this;
        }

        public CharMatcher or(CharMatcher other) {
            return (CharMatcher) Preconditions.checkNotNull(other);
        }

        public CharMatcher negate() {
            return ANY;
        }
    };
    public static final CharMatcher SINGLE_WIDTH = new RangesMatcher("CharMatcher.SINGLE_WIDTH", "\u0000\u05be\u05d0\u05f3\u0600\u0750\u0e00\u1e00\u2100\ufb50\ufe70\uff61".toCharArray(), "\u04f9\u05be\u05ea\u05f4\u06ff\u077f\u0e7f\u20af\u213a\ufdff\ufeff\uffdc".toCharArray());
    public static final CharMatcher WHITESPACE = new FastMatcher("WHITESPACE") {
        public boolean matches(char c) {
            return CharMatcher.WHITESPACE_TABLE.charAt((CharMatcher.WHITESPACE_MULTIPLIER * c) >>> WHITESPACE_SHIFT) == c;
        }

        @GwtIncompatible("java.util.BitSet")
        void setBits(BitSet table) {
            for (int i = CharMatcher.WHITESPACE_SHIFT; i < CharMatcher.WHITESPACE_TABLE.length(); i++) {
                table.set(CharMatcher.WHITESPACE_TABLE.charAt(i));
            }
        }
    };
    static final int WHITESPACE_MULTIPLIER = 1682554634;
    static final int WHITESPACE_SHIFT = Integer.numberOfLeadingZeros(WHITESPACE_TABLE.length() - 1);
    static final String WHITESPACE_TABLE = "\u2002\u3000\r\u0085\u200a\u2005\u2000\u3000\u2029\u000b\u3000\u2008\u2003\u205f\u3000\u1680\t \u2006\u2001\u202f\u00a0\f\u2009\u3000\u2004\u3000\u3000\u2028\n\u2007\u3000";
    private static final String ZEROES = "0\u0660\u06f0\u07c0\u0966\u09e6\u0a66\u0ae6\u0b66\u0be6\u0c66\u0ce6\u0d66\u0e50\u0ed0\u0f20\u1040\u1090\u17e0\u1810\u1946\u19d0\u1b50\u1bb0\u1c40\u1c50\ua620\ua8d0\ua900\uaa50\uff10";
    final String description;

    static abstract class FastMatcher extends CharMatcher {
        public /* bridge */ /* synthetic */ boolean apply(Object x0) {
            return super.apply((Character) x0);
        }

        FastMatcher() {
        }

        FastMatcher(String description) {
            super(description);
        }

        public final CharMatcher precomputed() {
            return this;
        }

        public CharMatcher negate() {
            return new NegatedFastMatcher(this);
        }
    }

    private static class And extends CharMatcher {
        final CharMatcher first;
        final CharMatcher second;

        public /* bridge */ /* synthetic */ boolean apply(Object x0) {
            return super.apply((Character) x0);
        }

        And(CharMatcher a, CharMatcher b) {
            this(a, b, "CharMatcher.and(" + a + ", " + b + ")");
        }

        And(CharMatcher a, CharMatcher b, String description) {
            super(description);
            this.first = (CharMatcher) Preconditions.checkNotNull(a);
            this.second = (CharMatcher) Preconditions.checkNotNull(b);
        }

        public boolean matches(char c) {
            return this.first.matches(c) && this.second.matches(c);
        }

        @GwtIncompatible("java.util.BitSet")
        void setBits(BitSet table) {
            BitSet tmp1 = new BitSet();
            this.first.setBits(tmp1);
            BitSet tmp2 = new BitSet();
            this.second.setBits(tmp2);
            tmp1.and(tmp2);
            table.or(tmp1);
        }

        CharMatcher withToString(String description) {
            return new And(this.first, this.second, description);
        }
    }

    @GwtIncompatible("java.util.BitSet")
    private static class BitSetMatcher extends FastMatcher {
        private final BitSet table;

        private BitSetMatcher(BitSet table, String description) {
            super(description);
            if (table.length() + 64 < table.size()) {
                table = (BitSet) table.clone();
            }
            this.table = table;
        }

        public boolean matches(char c) {
            return this.table.get(c);
        }

        void setBits(BitSet bitSet) {
            bitSet.or(this.table);
        }
    }

    private static class NegatedMatcher extends CharMatcher {
        final CharMatcher original;

        public /* bridge */ /* synthetic */ boolean apply(Object x0) {
            return super.apply((Character) x0);
        }

        NegatedMatcher(String toString, CharMatcher original) {
            super(toString);
            this.original = original;
        }

        NegatedMatcher(CharMatcher original) {
            this(original + ".negate()", original);
        }

        public boolean matches(char c) {
            return !this.original.matches(c);
        }

        public boolean matchesAllOf(CharSequence sequence) {
            return this.original.matchesNoneOf(sequence);
        }

        public boolean matchesNoneOf(CharSequence sequence) {
            return this.original.matchesAllOf(sequence);
        }

        public int countIn(CharSequence sequence) {
            return sequence.length() - this.original.countIn(sequence);
        }

        @GwtIncompatible("java.util.BitSet")
        void setBits(BitSet table) {
            BitSet tmp = new BitSet();
            this.original.setBits(tmp);
            tmp.flip(CharMatcher.WHITESPACE_SHIFT, CharMatcher.DISTINCT_CHARS);
            table.or(tmp);
        }

        public CharMatcher negate() {
            return this.original;
        }

        CharMatcher withToString(String description) {
            return new NegatedMatcher(description, this.original);
        }
    }

    static final class NegatedFastMatcher extends NegatedMatcher {
        NegatedFastMatcher(CharMatcher original) {
            super(original);
        }

        NegatedFastMatcher(String toString, CharMatcher original) {
            super(toString, original);
        }

        public final CharMatcher precomputed() {
            return this;
        }

        CharMatcher withToString(String description) {
            return new NegatedFastMatcher(description, this.original);
        }
    }

    private static class Or extends CharMatcher {
        final CharMatcher first;
        final CharMatcher second;

        public /* bridge */ /* synthetic */ boolean apply(Object x0) {
            return super.apply((Character) x0);
        }

        Or(CharMatcher a, CharMatcher b, String description) {
            super(description);
            this.first = (CharMatcher) Preconditions.checkNotNull(a);
            this.second = (CharMatcher) Preconditions.checkNotNull(b);
        }

        Or(CharMatcher a, CharMatcher b) {
            this(a, b, "CharMatcher.or(" + a + ", " + b + ")");
        }

        @GwtIncompatible("java.util.BitSet")
        void setBits(BitSet table) {
            this.first.setBits(table);
            this.second.setBits(table);
        }

        public boolean matches(char c) {
            return this.first.matches(c) || this.second.matches(c);
        }

        CharMatcher withToString(String description) {
            return new Or(this.first, this.second, description);
        }
    }

    private static class RangesMatcher extends CharMatcher {
        private final char[] rangeEnds;
        private final char[] rangeStarts;

        public /* bridge */ /* synthetic */ boolean apply(Object x0) {
            return super.apply((Character) x0);
        }

        RangesMatcher(String description, char[] rangeStarts, char[] rangeEnds) {
            boolean z;
            super(description);
            this.rangeStarts = rangeStarts;
            this.rangeEnds = rangeEnds;
            if (rangeStarts.length == rangeEnds.length) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkArgument(z);
            for (int i = CharMatcher.WHITESPACE_SHIFT; i < rangeStarts.length; i++) {
                if (rangeStarts[i] <= rangeEnds[i]) {
                    z = true;
                } else {
                    z = false;
                }
                Preconditions.checkArgument(z);
                if (i + 1 < rangeStarts.length) {
                    if (rangeEnds[i] < rangeStarts[i + 1]) {
                        z = true;
                    } else {
                        z = false;
                    }
                    Preconditions.checkArgument(z);
                }
            }
        }

        public boolean matches(char c) {
            int index = Arrays.binarySearch(this.rangeStarts, c);
            if (index >= 0) {
                return true;
            }
            index = (index ^ -1) - 1;
            if (index < 0 || c > this.rangeEnds[index]) {
                return false;
            }
            return true;
        }
    }

    public abstract boolean matches(char c);

    static {
        StringBuilder builder = new StringBuilder(ZEROES.length());
        for (int i = WHITESPACE_SHIFT; i < ZEROES.length(); i++) {
            builder.append((char) (ZEROES.charAt(i) + 9));
        }
        NINES = builder.toString();
    }

    private static String showCharacter(char c) {
        String hex = "0123456789ABCDEF";
        char[] tmp = new char[]{'\\', 'u', '\u0000', '\u0000', '\u0000', '\u0000'};
        for (int i = WHITESPACE_SHIFT; i < 4; i++) {
            tmp[5 - i] = hex.charAt(c & 15);
            c = (char) (c >> 4);
        }
        return String.copyValueOf(tmp);
    }

    public static CharMatcher is(final char match) {
        return new FastMatcher("CharMatcher.is('" + showCharacter(match) + "')") {
            public boolean matches(char c) {
                return c == match;
            }

            public String replaceFrom(CharSequence sequence, char replacement) {
                return sequence.toString().replace(match, replacement);
            }

            public CharMatcher and(CharMatcher other) {
                return other.matches(match) ? this : NONE;
            }

            public CharMatcher or(CharMatcher other) {
                return other.matches(match) ? other : super.or(other);
            }

            public CharMatcher negate() {
                return CharMatcher.isNot(match);
            }

            @GwtIncompatible("java.util.BitSet")
            void setBits(BitSet table) {
                table.set(match);
            }
        };
    }

    public static CharMatcher isNot(final char match) {
        return new FastMatcher("CharMatcher.isNot('" + showCharacter(match) + "')") {
            public boolean matches(char c) {
                return c != match;
            }

            public CharMatcher and(CharMatcher other) {
                return other.matches(match) ? super.and(other) : other;
            }

            public CharMatcher or(CharMatcher other) {
                return other.matches(match) ? ANY : this;
            }

            @GwtIncompatible("java.util.BitSet")
            void setBits(BitSet table) {
                table.set(CharMatcher.WHITESPACE_SHIFT, match);
                table.set(match + 1, CharMatcher.DISTINCT_CHARS);
            }

            public CharMatcher negate() {
                return CharMatcher.is(match);
            }
        };
    }

    public static CharMatcher anyOf(CharSequence sequence) {
        switch (sequence.length()) {
            case WHITESPACE_SHIFT /*?: ONE_ARG  (wrap: int
  0x00d9: INVOKE  (r2_29 int) = (wrap: int
  ?: ARITH  (r2_28 int) = (wrap: int
  0x00d3: INVOKE  (r2_27 int) = (wrap: java.lang.String
  ?: SGET  (r2_26 java.lang.String) =  com.google.api.client.repackaged.com.google.common.base.CharMatcher.WHITESPACE_TABLE java.lang.String) java.lang.String.length():int type: VIRTUAL) - (1 int)) java.lang.Integer.numberOfLeadingZeros(int):int type: STATIC)*/:
                return NONE;
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                return is(sequence.charAt(WHITESPACE_SHIFT));
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                return isEither(sequence.charAt(WHITESPACE_SHIFT), sequence.charAt(1));
            default:
                final char[] chars = sequence.toString().toCharArray();
                Arrays.sort(chars);
                StringBuilder description = new StringBuilder("CharMatcher.anyOf(\"");
                char[] arr$ = chars;
                int len$ = arr$.length;
                for (int i$ = WHITESPACE_SHIFT; i$ < len$; i$++) {
                    description.append(showCharacter(arr$[i$]));
                }
                description.append("\")");
                return new CharMatcher(description.toString()) {
                    public /* bridge */ /* synthetic */ boolean apply(Object x0) {
                        return super.apply((Character) x0);
                    }

                    public boolean matches(char c) {
                        return Arrays.binarySearch(chars, c) >= 0;
                    }

                    @GwtIncompatible("java.util.BitSet")
                    void setBits(BitSet table) {
                        char[] arr$ = chars;
                        int len$ = arr$.length;
                        for (int i$ = CharMatcher.WHITESPACE_SHIFT; i$ < len$; i$++) {
                            table.set(arr$[i$]);
                        }
                    }
                };
        }
    }

    private static CharMatcher isEither(final char match1, final char match2) {
        return new FastMatcher("CharMatcher.anyOf(\"" + showCharacter(match1) + showCharacter(match2) + "\")") {
            public boolean matches(char c) {
                return c == match1 || c == match2;
            }

            @GwtIncompatible("java.util.BitSet")
            void setBits(BitSet table) {
                table.set(match1);
                table.set(match2);
            }
        };
    }

    public static CharMatcher noneOf(CharSequence sequence) {
        return anyOf(sequence).negate();
    }

    public static CharMatcher inRange(char startInclusive, char endInclusive) {
        Preconditions.checkArgument(endInclusive >= startInclusive);
        return inRange(startInclusive, endInclusive, "CharMatcher.inRange('" + showCharacter(startInclusive) + "', '" + showCharacter(endInclusive) + "')");
    }

    static CharMatcher inRange(final char startInclusive, final char endInclusive, String description) {
        return new FastMatcher(description) {
            public boolean matches(char c) {
                return startInclusive <= c && c <= endInclusive;
            }

            @GwtIncompatible("java.util.BitSet")
            void setBits(BitSet table) {
                table.set(startInclusive, endInclusive + 1);
            }
        };
    }

    public static CharMatcher forPredicate(final Predicate<? super Character> predicate) {
        Preconditions.checkNotNull(predicate);
        if (predicate instanceof CharMatcher) {
            return (CharMatcher) predicate;
        }
        return new CharMatcher("CharMatcher.forPredicate(" + predicate + ")") {
            public boolean matches(char c) {
                return predicate.apply(Character.valueOf(c));
            }

            public boolean apply(Character character) {
                return predicate.apply(Preconditions.checkNotNull(character));
            }
        };
    }

    CharMatcher(String description) {
        this.description = description;
    }

    protected CharMatcher() {
        this.description = super.toString();
    }

    public CharMatcher negate() {
        return new NegatedMatcher(this);
    }

    public CharMatcher and(CharMatcher other) {
        return new And(this, (CharMatcher) Preconditions.checkNotNull(other));
    }

    public CharMatcher or(CharMatcher other) {
        return new Or(this, (CharMatcher) Preconditions.checkNotNull(other));
    }

    public CharMatcher precomputed() {
        return Platform.precomputeCharMatcher(this);
    }

    CharMatcher withToString(String description) {
        throw new UnsupportedOperationException();
    }

    @GwtIncompatible("java.util.BitSet")
    CharMatcher precomputedInternal() {
        BitSet table = new BitSet();
        setBits(table);
        int totalCharacters = table.cardinality();
        if (totalCharacters * 2 <= DISTINCT_CHARS) {
            return precomputedPositive(totalCharacters, table, this.description);
        }
        table.flip(WHITESPACE_SHIFT, DISTINCT_CHARS);
        String suffix = ".negate()";
        return new NegatedFastMatcher(toString(), precomputedPositive(DISTINCT_CHARS - totalCharacters, table, this.description.endsWith(suffix) ? this.description.substring(WHITESPACE_SHIFT, this.description.length() - suffix.length()) : this.description + suffix));
    }

    @GwtIncompatible("java.util.BitSet")
    private static CharMatcher precomputedPositive(int totalCharacters, BitSet table, String description) {
        switch (totalCharacters) {
            case WHITESPACE_SHIFT /*?: ONE_ARG  (wrap: int
  0x00d9: INVOKE  (r2_29 int) = (wrap: int
  ?: ARITH  (r2_28 int) = (wrap: int
  0x00d3: INVOKE  (r2_27 int) = (wrap: java.lang.String
  ?: SGET  (r2_26 java.lang.String) =  com.google.api.client.repackaged.com.google.common.base.CharMatcher.WHITESPACE_TABLE java.lang.String) java.lang.String.length():int type: VIRTUAL) - (1 int)) java.lang.Integer.numberOfLeadingZeros(int):int type: STATIC)*/:
                return NONE;
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                return is((char) table.nextSetBit(WHITESPACE_SHIFT));
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                char c1 = (char) table.nextSetBit(WHITESPACE_SHIFT);
                return isEither(c1, (char) table.nextSetBit(c1 + 1));
            default:
                if (isSmall(totalCharacters, table.length())) {
                    return SmallCharMatcher.from(table, description);
                }
                return new BitSetMatcher(table, description);
        }
    }

    @GwtIncompatible("SmallCharMatcher")
    private static boolean isSmall(int totalCharacters, int tableLength) {
        return totalCharacters <= 1023 && tableLength > (totalCharacters * 4) * 16;
    }

    @GwtIncompatible("java.util.BitSet")
    void setBits(BitSet table) {
        for (int c = 65535; c >= 0; c--) {
            if (matches((char) c)) {
                table.set(c);
            }
        }
    }

    public boolean matchesAnyOf(CharSequence sequence) {
        return !matchesNoneOf(sequence);
    }

    public boolean matchesAllOf(CharSequence sequence) {
        for (int i = sequence.length() - 1; i >= 0; i--) {
            if (!matches(sequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean matchesNoneOf(CharSequence sequence) {
        return indexIn(sequence) == -1;
    }

    public int indexIn(CharSequence sequence) {
        int length = sequence.length();
        for (int i = WHITESPACE_SHIFT; i < length; i++) {
            if (matches(sequence.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    public int indexIn(CharSequence sequence, int start) {
        int length = sequence.length();
        Preconditions.checkPositionIndex(start, length);
        for (int i = start; i < length; i++) {
            if (matches(sequence.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    public int lastIndexIn(CharSequence sequence) {
        for (int i = sequence.length() - 1; i >= 0; i--) {
            if (matches(sequence.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    public int countIn(CharSequence sequence) {
        int count = WHITESPACE_SHIFT;
        for (int i = WHITESPACE_SHIFT; i < sequence.length(); i++) {
            if (matches(sequence.charAt(i))) {
                count++;
            }
        }
        return count;
    }

    @CheckReturnValue
    public String removeFrom(CharSequence sequence) {
        String string = sequence.toString();
        int pos = indexIn(string);
        if (pos == -1) {
            return string;
        }
        char[] chars = string.toCharArray();
        int spread = 1;
        while (true) {
            pos++;
            while (pos != chars.length) {
                if (matches(chars[pos])) {
                    spread++;
                } else {
                    chars[pos - spread] = chars[pos];
                    pos++;
                }
            }
            return new String(chars, WHITESPACE_SHIFT, pos - spread);
        }
    }

    @CheckReturnValue
    public String retainFrom(CharSequence sequence) {
        return negate().removeFrom(sequence);
    }

    @CheckReturnValue
    public String replaceFrom(CharSequence sequence, char replacement) {
        String string = sequence.toString();
        int pos = indexIn(string);
        if (pos == -1) {
            return string;
        }
        char[] chars = string.toCharArray();
        chars[pos] = replacement;
        for (int i = pos + 1; i < chars.length; i++) {
            if (matches(chars[i])) {
                chars[i] = replacement;
            }
        }
        return new String(chars);
    }

    @CheckReturnValue
    public String replaceFrom(CharSequence sequence, CharSequence replacement) {
        int replacementLen = replacement.length();
        if (replacementLen == 0) {
            return removeFrom(sequence);
        }
        if (replacementLen == 1) {
            return replaceFrom(sequence, replacement.charAt(WHITESPACE_SHIFT));
        }
        String string = sequence.toString();
        int pos = indexIn(string);
        if (pos == -1) {
            return string;
        }
        int len = string.length();
        StringBuilder buf = new StringBuilder(((len * 3) / 2) + 16);
        int oldpos = WHITESPACE_SHIFT;
        do {
            buf.append(string, oldpos, pos);
            buf.append(replacement);
            oldpos = pos + 1;
            pos = indexIn(string, oldpos);
        } while (pos != -1);
        buf.append(string, oldpos, len);
        return buf.toString();
    }

    @CheckReturnValue
    public String trimFrom(CharSequence sequence) {
        int len = sequence.length();
        int first = WHITESPACE_SHIFT;
        while (first < len && matches(sequence.charAt(first))) {
            first++;
        }
        int last = len - 1;
        while (last > first && matches(sequence.charAt(last))) {
            last--;
        }
        return sequence.subSequence(first, last + 1).toString();
    }

    @CheckReturnValue
    public String trimLeadingFrom(CharSequence sequence) {
        int len = sequence.length();
        for (int first = WHITESPACE_SHIFT; first < len; first++) {
            if (!matches(sequence.charAt(first))) {
                return sequence.subSequence(first, len).toString();
            }
        }
        return BuildConfig.FLAVOR;
    }

    @CheckReturnValue
    public String trimTrailingFrom(CharSequence sequence) {
        for (int last = sequence.length() - 1; last >= 0; last--) {
            if (!matches(sequence.charAt(last))) {
                return sequence.subSequence(WHITESPACE_SHIFT, last + 1).toString();
            }
        }
        return BuildConfig.FLAVOR;
    }

    @CheckReturnValue
    public String collapseFrom(CharSequence sequence, char replacement) {
        int len = sequence.length();
        int i = WHITESPACE_SHIFT;
        while (i < len) {
            char c = sequence.charAt(i);
            if (matches(c)) {
                if (c != replacement || (i != len - 1 && matches(sequence.charAt(i + 1)))) {
                    return finishCollapseFrom(sequence, i + 1, len, replacement, new StringBuilder(len).append(sequence.subSequence(WHITESPACE_SHIFT, i)).append(replacement), true);
                }
                i++;
            }
            i++;
        }
        return sequence.toString();
    }

    @CheckReturnValue
    public String trimAndCollapseFrom(CharSequence sequence, char replacement) {
        int len = sequence.length();
        int first = WHITESPACE_SHIFT;
        while (first < len && matches(sequence.charAt(first))) {
            first++;
        }
        int last = len - 1;
        while (last > first && matches(sequence.charAt(last))) {
            last--;
        }
        if (first == 0 && last == len - 1) {
            return collapseFrom(sequence, replacement);
        }
        return finishCollapseFrom(sequence, first, last + 1, replacement, new StringBuilder((last + 1) - first), false);
    }

    private String finishCollapseFrom(CharSequence sequence, int start, int end, char replacement, StringBuilder builder, boolean inMatchingGroup) {
        for (int i = start; i < end; i++) {
            char c = sequence.charAt(i);
            if (!matches(c)) {
                builder.append(c);
                inMatchingGroup = false;
            } else if (!inMatchingGroup) {
                builder.append(replacement);
                inMatchingGroup = true;
            }
        }
        return builder.toString();
    }

    @Deprecated
    public boolean apply(Character character) {
        return matches(character.charValue());
    }

    public String toString() {
        return this.description;
    }
}
