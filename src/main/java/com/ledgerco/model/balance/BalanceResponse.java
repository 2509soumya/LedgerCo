package com.ledgerco.model.balance;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BalanceResponse {
    private String bankName;
    private String userName;
    private int amountpaid;
    private int emiRemaining;
    @Override
    public String toString(){
        return this.bankName+" "+this.userName+" "+this.amountpaid+" "+this.emiRemaining;
    }
}
