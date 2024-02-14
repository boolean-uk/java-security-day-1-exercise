package com.booleanuk.api.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Response<T> {
    protected String status;
    protected T data;

    public Response(T data) {
        this.status = "success";
        this.data = data;
    }
}
