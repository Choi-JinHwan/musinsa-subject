package com.musinsa.subject.small.url.domain;

import java.util.concurrent.atomic.AtomicLong;

public class SmallUrl {

    private final String hash;

    private final String originalUrl;

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
