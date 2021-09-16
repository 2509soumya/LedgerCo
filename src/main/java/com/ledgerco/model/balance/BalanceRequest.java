package com.ledgerco.model.balance;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BalanceRequest {
    private String bankName;
    private String userName;
    private int emiNum;
}
