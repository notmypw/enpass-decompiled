package in.sinew.enpass.chromeconnector.srp6;

import java.io.Serializable;
import java.math.BigInteger;

public abstract class SRP6JavascriptServerSession implements Serializable {
    public static int HASH_HEX_LENGTH = 0;
    private static final long serialVersionUID = -5998252135527603869L;
    protected final SRP6CryptoParams config;
    protected final SRP6ServerSession session = new SRP6ServerSession(this.config);

    public String step1(String username, String salt, String v) {
        return BigIntegerUtils.toHex(this.session.step1(username, BigIntegerUtils.fromHex(salt), BigIntegerUtils.fromHex(v)));
    }

    public String step2(String A, String M1) throws Exception {
        return HexHashedRoutines.leadingZerosPad(BigIntegerUtils.toHex(this.session.step2(BigIntegerUtils.fromHex(A), BigIntegerUtils.fromHex(M1))), HASH_HEX_LENGTH);
    }

    public String getState() {
        return this.session.getState().name();
    }

    public String getUserID() {
        return this.session.getUserID();
    }

    public SRP6JavascriptServerSession(SRP6CryptoParams srp6CryptoParams) {
        this.config = srp6CryptoParams;
        this.session.setHashedKeysRoutine(new HexHashedURoutine());
        this.session.setClientEvidenceRoutine(new HexHashedClientEvidenceRoutine());
        this.session.setServerEvidenceRoutine(new HexHashedServerEvidenceRoutine());
    }

    public String k() {
        return BigIntegerUtils.toHex(SRP6Routines.computeK(this.config.getMessageDigestInstance(), this.config.N, this.config.g));
    }

    public static BigInteger fromDecimal(String base10) {
        return new BigInteger(base10, 10);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("g: %s\n", new Object[]{this.config.g.toString(10)}));
        builder.append(String.format("N: %s\n", new Object[]{this.config.N.toString(10)}));
        builder.append(String.format("k: %s\n", new Object[]{k()}));
        return builder.toString();
    }

    @Deprecated
    public String getSalt() {
        return BigIntegerUtils.toHex(this.session.getSalt());
    }

    @Deprecated
    public String getPublicServerValue() {
        return BigIntegerUtils.toHex(this.session.getPublicServerValue());
    }

    @Deprecated
    public String getServerEvidenceMessage() {
        return BigIntegerUtils.toHex(this.session.getServerEvidenceMessage());
    }

    public String getSessionKey(boolean doHash) {
        String S = BigIntegerUtils.toHex(this.session.getSessionKey(false));
        if (doHash) {
            return HexHashedRoutines.toHexString(this.config.getMessageDigestInstance().digest(S.getBytes(HexHashedRoutines.utf8)));
        }
        return S;
    }
}
