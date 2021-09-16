package com.ledgerco.services;

import com.ledgerco.command.Command;
import com.ledgerco.command.CommandExecutor;
import com.ledgerco.dao.IssuedLoanRepository;
import com.ledgerco.entities.IssuedLoans;
import com.ledgerco.model.balance.BalanceRequest;
import com.ledgerco.model.balance.BalanceResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class BalanceHandler implements CommandExecutor<BalanceRequest> {

    @Autowired
    IssuedLoanRepository issuedLoanRepository;


    static final String commandId="BALANCE";

    @Override
    public String getCommandId() {
        return commandId;
    }

    @Override
    public String executeHelper(BalanceRequest input) {
       IssuedLoans issuedLoan= issuedLoanRepository.findByBorrowerNameAndBankName(input.getUserName(),input.getBankName());

       double totamounttopay=issuedLoan.getPrincipal()+(issuedLoan.getPrincipal()*issuedLoan.getYears()*issuedLoan.getInterestRate())/100;
       int monthlyemi=(int)Math.ceil(totamounttopay/(issuedLoan.getYears()*12));

       int totalemipaid=monthlyemi*input.getEmiNum();
       if(issuedLoan.getLumpSumPayments()!=null){
           double totallumpsum=issuedLoan.getLumpSumPayments().stream().filter(lumpSumPayments -> lumpSumPayments.getEmiNum()<=input.getEmiNum()).map(lumpSumPayments -> lumpSumPayments.getLumpSumAmount()).collect(Collectors.summingDouble(Double::doubleValue));
           totalemipaid+=(int)totallumpsum;
       }
        double amountremaining=totamounttopay-totalemipaid;
        int remainingemi=(int)Math.ceil(amountremaining/monthlyemi);
        BalanceResponse resp=BalanceResponse.builder()
                .bankName(input.getBankName())
                .userName(input.getUserName())
                .amountpaid(totalemipaid)
                .emiRemaining(remainingemi).build();
       return resp.toString();
    }

    @Override
    public void execute(Command command) {
        List<String> arguments=command.getArguments();
        BalanceRequest request=BalanceRequest.builder()
                .bankName(arguments.get(0))
                .userName(arguments.get(1))
                .emiNum(Integer.parseInt(arguments.get(2))).build();
        String resp=executeHelper(request);
        System.out.println(resp);
    }

    @Override
    public boolean isValid(Command command) {
        if(command.getName().equalsIgnoreCase(commandId) && command.getArguments()!=null && command.getArguments().size()==3){
            return true;
        }else{
            return false;
        }
    }
}
