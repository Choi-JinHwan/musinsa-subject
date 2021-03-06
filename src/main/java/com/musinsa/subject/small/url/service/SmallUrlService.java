package com.musinsa.subject.small.url.service;

import com.musinsa.subject.small.url.domain.SmallUrl;
import com.musinsa.subject.small.url.repository.SmallUrlRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Service
@Validated
public class SmallUrlService {

    private final SmallUrlRepository smallUrlRepository;

    public SmallUrlService(SmallUrlRepository smallUrlRepository) {
        this.smallUrlRepository = smallUrlRepository;
    }

    public SmallUrl createSmallUrl(@Valid @NotBlank String originalUrl) {
        var hash = String.format("%08x", originalUrl.hashCode());
        return smallUrlRepository.save(new SmallUrl(hash, originalUrl));
    }

}
