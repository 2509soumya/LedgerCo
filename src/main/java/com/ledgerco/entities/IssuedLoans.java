package com.ledgerco.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="issued_loans")
public class IssuedLoans {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long loanId;
    private String borrowerName;
    private String bankName;
    private Double principal;
    private Double interestRate;
    private int years;

    @OneToMany(targetEntity = LumpSumPayments.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="loanId")
    List<LumpSumPayments> lumpSumPayments=new ArrayList<>();
}
