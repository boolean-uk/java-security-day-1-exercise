package com.booleanuk.api.response;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponse extends Response<Map<String, String>> {

    public ErrorResponse(String message) {
        super(new HashMap<>() {{put("message", message);}});
        this.setStatus("error");
    }
}
