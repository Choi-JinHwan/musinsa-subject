package com.musinsa.subject.small.url.interfaces;

import com.musinsa.subject.SuccessResponse;
import com.musinsa.subject.small.url.domain.SmallUrl;
import com.musinsa.subject.small.url.service.SmallUrlService;
import com.musinsa.subject.small.url.vo.SmallUrlCreateRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class SmallUrlController {

    private final SmallUrlService smallUrlService;

    public SmallUrlController(SmallUrlService smallUrlService) {
        this.smallUrlService = smallUrlService;
    }

    @PostMapping("/small-url")
    public SuccessResponse<SmallUrl> createSmallUrl(
            @Valid @RequestBody SmallUrlCreateRequest request
    ) {
        return SuccessResponse.of(smallUrlService.createSmallUrl(request.getOriginalUrl()));
    }

}
