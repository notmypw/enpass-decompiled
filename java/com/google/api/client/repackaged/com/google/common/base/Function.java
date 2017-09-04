package com.google.api.client.repackaged.com.google.common.base;

import com.google.api.client.repackaged.com.google.common.annotations.GwtCompatible;
import javax.annotation.Nullable;

@GwtCompatible
public interface Function<F, T> {
    @Nullable
    T apply(@Nullable F f);

    boolean equals(@Nullable Object obj);
}
