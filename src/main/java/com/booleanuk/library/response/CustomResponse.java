package com.booleanuk.library.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class CustomResponse {
    private String status;
    private Object data;
}