package com.musinsa.subject;

import com.musinsa.subject.small.url.domain.SmallUrl;
import com.musinsa.subject.small.url.repository.SmallUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataInitRunner implements ApplicationRunner {

    public static SmallUrl testSmallUrl;

    private final SmallUrlRepository smallUrlRepository;

    @Override
    public void run(ApplicationArguments args) {
        initSmallUrlData();
    }

    private void initSmallUrlData() {
        var originalUrl = "https://www.wanted.co.kr/newintro";
        var hash = String.format("%08x", originalUrl.hashCode());
        var smallUrl = new SmallUrl(hash, originalUrl);
        this.testSmallUrl = smallUrlRepository.save(smallUrl);
    }

}
