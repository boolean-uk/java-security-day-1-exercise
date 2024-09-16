package com.booleanuk.api.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoanUserDTO {
    private int loanId;
    private boolean returned;
    private String userName;

    public LoanUserDTO(int loanId, boolean returned, String userName) {
        this.loanId = loanId;
        this.returned = returned;
        this.userName = userName;
    }

}