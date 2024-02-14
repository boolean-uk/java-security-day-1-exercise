package com.booleanuk.api.Responses;

public class Response<T>{
    protected String status;
    protected T data;

    public void set(T data){
        this.data = data;
        this.status = "success";
    }
}
