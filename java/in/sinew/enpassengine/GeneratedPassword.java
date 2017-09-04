package in.sinew.enpassengine;

public class GeneratedPassword {
    private String mDomain;
    private String mPassword;
    private long mTimestamp;

    public GeneratedPassword(String password, long time, String domain) {
        this.mPassword = password;
        this.mTimestamp = time;
        this.mDomain = domain;
    }

    public String getPassword() {
        return this.mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public long getTimestamp() {
        return this.mTimestamp;
    }

    public void setTimestamp(long mTimestamp) {
        this.mTimestamp = mTimestamp;
    }

    public String getDomain() {
        return this.mDomain;
    }

    public void setDomain(String mDomain) {
        this.mDomain = mDomain;
    }
}
