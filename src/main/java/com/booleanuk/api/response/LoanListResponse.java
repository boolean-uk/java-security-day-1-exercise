package com.booleanuk.api.response;

import com.booleanuk.api.loan.Loan;
import com.booleanuk.api.user.User;

import java.util.List;

public class LoanListResponse extends Response<List<Loan>> {

    public LoanListResponse(List<Loan> data) {
        super("success", data);
    }
}
