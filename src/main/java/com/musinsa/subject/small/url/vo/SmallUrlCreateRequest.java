package com.musinsa.subject.small.url.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

@Getter
@RequiredArgsConstructor
public class SmallUrlCreateRequest {

    @URL
    @NotBlank
    private final String originalUrl;

}
