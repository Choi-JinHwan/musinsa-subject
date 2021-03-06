package com.musinsa.subject.small.url.vo;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

public class SmallUrlCreateRequest {

    @URL
    @NotBlank
    private final String originalUrl;

    public SmallUrlCreateRequest(@URL @NotBlank String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

}
