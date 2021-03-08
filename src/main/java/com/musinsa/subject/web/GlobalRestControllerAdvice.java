package com.musinsa.subject.web;

import com.musinsa.subject.small.url.exception.SmallUrlNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalRestControllerAdvice {

    @ExceptionHandler(SmallUrlNotFoundException.class)
    public ResponseEntity<Void> smallUrlNotFoundException(SmallUrlNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

}
