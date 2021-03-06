package com.musinsa.subject.small.url.domain;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.concurrent.atomic.AtomicLong;

public class SmallUrl {

    @NotBlank
    private final String hash;

    @NotBlank
    @URL
    private final String originalUrl;

    @NotNull
    @PositiveOrZero
    private final AtomicLong redirectCount;

    public SmallUrl(String hash, String originalUrl) {
        this.hash = hash;
        this.originalUrl = originalUrl;
        this.redirectCount = new AtomicLong(0L);
    }

    public String getHash() {
        return hash;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public Long getRedirectCount() {
        return redirectCount.get();
    }

    public Long incrementRedirectCount() {
        return redirectCount.incrementAndGet();
    }

}
