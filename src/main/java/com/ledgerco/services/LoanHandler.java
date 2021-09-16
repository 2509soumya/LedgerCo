package com.ledgerco.services;

import com.ledgerco.command.Command;
import com.ledgerco.command.CommandExecutor;
import com.ledgerco.dao.IssuedLoanRepository;
import com.ledgerco.entities.IssuedLoans;
import com.ledgerco.model.loan.LoanOrder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class LoanHandler implements CommandExecutor<LoanOrder> {

    @Autowired
    IssuedLoanRepository issuedLoanRepository;

    static final String commandId="LOAN";

    @Override
    public String getCommandId() {
        return commandId;
    }

    @Override
    public String executeHelper(LoanOrder input) {
        IssuedLoans issueLoan=IssuedLoans.builder()
                .bankName(input.getBankName())
                .borrowerName(input.getUserName())
                .principal(input.getPrincipal())
                .interestRate(input.getRateOfInterest())
                .years(input.getYears()).build();
        issuedLoanRepository.save(issueLoan);
        return "Loan Issued";
    }

    @Override
    public void execute(Command command) {
        List<String> arguments=command.getArguments();
        LoanOrder order=LoanOrder.builder()
                  .bankName(arguments.get(0))
                  .userName(arguments.get(1))
                  .principal(Double.valueOf(arguments.get(2)))
                  .years(Integer.valueOf(arguments.get(3)))
                  .rateOfInterest(Double.valueOf(arguments.get(4))).build();
        executeHelper(order);
    }

    @Override
    public boolean isValid(Command command) {
        if(command.getName().equalsIgnoreCase(commandId) && command.getArguments()!=null && command.getArguments().size()==5){
         return true;
        }else{
            return false;
        }
    }
}
