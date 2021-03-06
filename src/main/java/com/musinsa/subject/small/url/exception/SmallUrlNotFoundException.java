package com.musinsa.subject.small.url.exception;

public class SmallUrlNotFoundException extends RuntimeException {
    public SmallUrlNotFoundException(String message) {
        super(message);
    }

    public static SmallUrlNotFoundException byHash(String hash) {
        return new SmallUrlNotFoundException("SmnallUrl[hash=" + hash + "] not found");
    }

}
