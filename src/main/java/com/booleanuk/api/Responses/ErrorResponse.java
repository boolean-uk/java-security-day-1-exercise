package com.booleanuk.api.Responses;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponse extends Response<Map<String, String>> {
    public void set(String message){
        Map<String, String> reply = new HashMap<>();
        reply.put("message", message);

        this.data = reply;
        this.status = "error";
    }
}
