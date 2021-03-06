package com.musinsa.subject.small.url.repository;


import com.musinsa.subject.small.url.domain.SmallUrl;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Repository
@Validated
public class SmallUrlRepository {

    private final Map<String, SmallUrl> datasource;

    public SmallUrlRepository() {
        this.datasource = new HashMap<>();
    }

    public SmallUrlRepository(Map<String, SmallUrl> datasource) {
        this.datasource = datasource;
    }

    public SmallUrl save(@Valid SmallUrl smallUrl) {
        this.datasource.put(smallUrl.getHash(), smallUrl);
        return smallUrl;
    }

}
