package com.ledgerco.model.loan;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoanOrder {
    private String bankName;
    private String userName;
    private double principal;
    private int years;
    private double rateOfInterest;
}
