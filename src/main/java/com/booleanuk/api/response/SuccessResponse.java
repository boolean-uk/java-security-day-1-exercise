package com.booleanuk.api.response;

import java.util.Map;

public class SuccessResponse extends Response<Map<String, String>>{

    public SuccessResponse(String status, Map<String, String> data) {
        super(status, data);
    }
}
