package com.musinsa.subject.small.url.interfaces;

import com.musinsa.subject.small.url.domain.SmallUrl;
import com.musinsa.subject.small.url.service.SmallUrlService;
import com.musinsa.subject.small.url.vo.SmallUrlCreateRequest;
import com.musinsa.subject.web.SuccessResponse;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

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

    @GetMapping("/{hash}")
    public void redirect(
            @PathVariable String hash,
            HttpServletResponse response
    ) throws IOException {
        var originalUrlByHash = smallUrlService.findOriginalUrlByHashForRedirect(hash);
        response.sendRedirect(originalUrlByHash);
    }

}
