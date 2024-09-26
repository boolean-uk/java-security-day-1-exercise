package com.booleanuk.api.response;

import java.util.Map;

public class SuccessResponse extends Response<Object>{

    public SuccessResponse(Object data) {
        super("success", data);
    }
}
