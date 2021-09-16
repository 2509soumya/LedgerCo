package com.ledgerco.services;

import com.ledgerco.command.Command;
import com.ledgerco.command.CommandExecutor;
import com.ledgerco.dao.IssuedLoanRepository;
import com.ledgerco.entities.IssuedLoans;
import com.ledgerco.entities.LumpSumPayments;
import com.ledgerco.model.payments.PaymentOrder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Log4j2
public class PaymentHandler implements CommandExecutor<PaymentOrder> {
    @Autowired
    IssuedLoanRepository issuedLoanRepository;


    static final String commandId="PAYMENT";

    @Override
    public String getCommandId() {
        return commandId;
    }

    @Override
    public String executeHelper(PaymentOrder input) {
        IssuedLoans loan=issuedLoanRepository.findByBorrowerNameAndBankName(input.getUserName(),input.getBankName());
        LumpSumPayments paymentrecord=LumpSumPayments.builder()
                                     .lumpSumAmount(input.getLumpsumAmount())
                                     .emiNum(input.getEmiNum()).build();
        if(loan.getLumpSumPayments()==null){
            loan.setLumpSumPayments(Arrays.asList(paymentrecord));
        }else{
            loan.getLumpSumPayments().add(paymentrecord);
        }
        issuedLoanRepository.save(loan);
        return "Payment issued";
    }

    @Override
    public void execute(Command command) {
        List<String> arguments=command.getArguments();
        PaymentOrder order=PaymentOrder.builder()
                .bankName(arguments.get(0))
                .userName(arguments.get(1))
                .lumpsumAmount(Double.valueOf(arguments.get(2)))
                .emiNum(Integer.parseInt(arguments.get(3))).build();
        executeHelper(order);
    }

    @Override
    public boolean isValid(Command command) {
        if(command.getName().equalsIgnoreCase(commandId) && command.getArguments()!=null && command.getArguments().size()==4){
            return true;
        }else{
            return false;
        }
    }
}
