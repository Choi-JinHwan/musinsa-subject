package com.musinsa.subject.small.url.repository;


import com.musinsa.subject.small.url.domain.SmallUrl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SmallUrlRepository {

    private final Map<String, SmallUrl> datasource;

    public SmallUrlRepository() {
        this.datasource = new HashMap<>();
    }

    public SmallUrlRepository(Map<String, SmallUrl> datasource) {
        this.datasource = datasource;
    }

    public SmallUrl save(SmallUrl smallUrl) {
        this.datasource.put(smallUrl.getHash(), smallUrl);
        return smallUrl;
    }

}
