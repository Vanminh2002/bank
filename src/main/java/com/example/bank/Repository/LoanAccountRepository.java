package com.example.bank.Repository;

import com.example.bank.Entity.LoanAccount;
import com.example.bank.Entity.TransferTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanAccountRepository extends JpaRepository<LoanAccount, Long> {



}