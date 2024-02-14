package com.booleanuk.api.response;

import lombok.Setter;

@Setter
public class Response<T> {
    private String status = "Success";
    private T data;
}
