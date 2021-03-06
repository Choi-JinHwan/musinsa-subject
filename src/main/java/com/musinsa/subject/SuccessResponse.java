package com.musinsa.subject;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SuccessResponse<T> {

    private final T data;

    public static <A> SuccessResponse<A> of(A data) {
        return new SuccessResponse<A>(data);
    }

}
