package com.ledgerco.model.payments;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentOrder {
    private String bankName;
    private String userName;
    private double lumpsumAmount;
    private int emiNum;
}
