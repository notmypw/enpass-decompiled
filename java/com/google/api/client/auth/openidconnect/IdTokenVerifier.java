package com.google.api.client.auth.openidconnect;

import com.google.api.client.util.Beta;
import com.google.api.client.util.Clock;
import com.google.api.client.util.Preconditions;
import java.util.Collection;
import java.util.Collections;

@Beta
public class IdTokenVerifier {
    public static final long DEFAULT_TIME_SKEW_SECONDS = 300;
    private final long acceptableTimeSkewSeconds;
    private final Collection<String> audience;
    private final Clock clock;
    private final Collection<String> issuers;

    @Beta
    public static class Builder {
        long acceptableTimeSkewSeconds = IdTokenVerifier.DEFAULT_TIME_SKEW_SECONDS;
        Collection<String> audience;
        Clock clock = Clock.SYSTEM;
        Collection<String> issuers;

        public IdTokenVerifier build() {
            return new IdTokenVerifier(this);
        }

        public final Clock getClock() {
            return this.clock;
        }

        public Builder setClock(Clock clock) {
            this.clock = (Clock) Preconditions.checkNotNull(clock);
            return this;
        }

        public final String getIssuer() {
            if (this.issuers == null) {
                return null;
            }
            return (String) this.issuers.iterator().next();
        }

        public Builder setIssuer(String issuer) {
            if (issuer == null) {
                return setIssuers(null);
            }
            return setIssuers(Collections.singleton(issuer));
        }

        public final Collection<String> getIssuers() {
            return this.issuers;
        }

        public Builder setIssuers(Collection<String> issuers) {
            boolean z = issuers == null || !issuers.isEmpty();
            Preconditions.checkArgument(z, "Issuers must not be empty");
            this.issuers = issuers;
            return this;
        }

        public final Collection<String> getAudience() {
            return this.audience;
        }

        public Builder setAudience(Collection<String> audience) {
            this.audience = audience;
            return this;
        }

        public final long getAcceptableTimeSkewSeconds() {
            return this.acceptableTimeSkewSeconds;
        }

        public Builder setAcceptableTimeSkewSeconds(long acceptableTimeSkewSeconds) {
            Preconditions.checkArgument(acceptableTimeSkewSeconds >= 0);
            this.acceptableTimeSkewSeconds = acceptableTimeSkewSeconds;
            return this;
        }
    }

    public IdTokenVerifier() {
        this(new Builder());
    }

    protected IdTokenVerifier(Builder builder) {
        Collection collection = null;
        this.clock = builder.clock;
        this.acceptableTimeSkewSeconds = builder.acceptableTimeSkewSeconds;
        this.issuers = builder.issuers == null ? null : Collections.unmodifiableCollection(builder.issuers);
        if (builder.audience != null) {
            collection = Collections.unmodifiableCollection(builder.audience);
        }
        this.audience = collection;
    }

    public final Clock getClock() {
        return this.clock;
    }

    public final long getAcceptableTimeSkewSeconds() {
        return this.acceptableTimeSkewSeconds;
    }

    public final String getIssuer() {
        if (this.issuers == null) {
            return null;
        }
        return (String) this.issuers.iterator().next();
    }

    public final Collection<String> getIssuers() {
        return this.issuers;
    }

    public final Collection<String> getAudience() {
        return this.audience;
    }

    public boolean verify(IdToken idToken) {
        return (this.issuers == null || idToken.verifyIssuer(this.issuers)) && ((this.audience == null || idToken.verifyAudience(this.audience)) && idToken.verifyTime(this.clock.currentTimeMillis(), this.acceptableTimeSkewSeconds));
    }
}
