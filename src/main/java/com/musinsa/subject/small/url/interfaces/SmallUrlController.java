package com.musinsa.subject.small.url.interfaces;

import com.musinsa.subject.small.url.domain.SmallUrl;
import com.musinsa.subject.small.url.service.SmallUrlService;
import com.musinsa.subject.small.url.vo.SmallUrlCreateRequest;
import com.musinsa.subject.web.SuccessResponse;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "SmallUrl을 저장한다")
    public SuccessResponse<SmallUrl> createSmallUrl(
            @Valid @RequestBody SmallUrlCreateRequest request
    ) {
        return SuccessResponse.of(smallUrlService.createSmallUrl(request.getOriginalUrl()));
    }

    @GetMapping("/{hash:\\w{8}}")
    @ApiOperation(value = "hash가 같은 SmallUrl의 originalUrl로 리다이렉션한다")
    public void redirect(
            @PathVariable String hash,
            HttpServletResponse response
    ) throws IOException {
        var originalUrlByHash = smallUrlService.findOriginalUrlByHashForRedirect(hash);
        response.sendRedirect(originalUrlByHash);
    }

}
