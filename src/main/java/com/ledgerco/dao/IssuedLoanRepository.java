package com.ledgerco.dao;

import com.ledgerco.entities.IssuedLoans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssuedLoanRepository extends JpaRepository<IssuedLoans,Long> {
      IssuedLoans findByBorrowerNameAndBankName(String borrowerName,String bankName);
}
