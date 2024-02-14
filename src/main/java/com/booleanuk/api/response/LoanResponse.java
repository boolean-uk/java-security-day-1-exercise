package com.booleanuk.api.response;

import com.booleanuk.api.loan.Loan;
import com.booleanuk.api.videogame.Game;

public class LoanResponse extends Response<Loan>{
    public LoanResponse(Loan loan) {
        super("success", loan);
    }

}
