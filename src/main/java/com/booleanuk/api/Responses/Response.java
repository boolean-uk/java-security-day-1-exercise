package com.booleanuk.api.Responses;

import lombok.Getter;

@Getter
public class Response<T>{
    protected String status;
    protected T data;

    public void set(T data){
        this.data = data;
        this.status = "success";
    }
}
