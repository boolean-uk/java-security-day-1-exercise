package com.booleanuk.api.response;

import com.booleanuk.api.model.User;
import lombok.Getter;

import java.util.List;

@Getter
public class Response<T> {
    protected String status;
    protected T data;

    public void set(T data) {
        this.status = "success";
        this.data = data;
    }
}
