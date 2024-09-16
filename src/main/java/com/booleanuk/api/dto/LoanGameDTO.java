package com.booleanuk.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanGameDTO {
    private int loanId;
    private boolean returned;
    private String gameTitle;

    public LoanGameDTO(int loanId, boolean returned, String gameTitle) {
        this.loanId = loanId;
        this.returned = returned;
        this.gameTitle = gameTitle;
    }

}