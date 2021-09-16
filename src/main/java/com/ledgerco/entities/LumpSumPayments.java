package com.ledgerco.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="lumpsum_payments")
public class LumpSumPayments {
   @Id
   @GeneratedValue(strategy= GenerationType.AUTO)
   private Long paymentId;
   private Double lumpSumAmount;
   int emiNum;
   @ManyToOne
   @JoinColumn(name="loanId")
   private IssuedLoans issuedLoans;
}
