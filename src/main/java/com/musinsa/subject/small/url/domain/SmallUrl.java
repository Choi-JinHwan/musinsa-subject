package com.musinsa.subject.small.url.domain;

import lombok.Getter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.concurrent.atomic.AtomicLong;

@Getter
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

    public Long incrementRedirectCount() {
        return redirectCount.incrementAndGet();
    }

    public Long getRedirectCount() {
        return redirectCount.get();
    }
}
