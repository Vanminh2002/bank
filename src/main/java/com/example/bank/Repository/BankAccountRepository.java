package com.example.bank.Repository;

import com.example.bank.Entity.BankAccount;
import com.example.bank.Entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {
    @Query("SELECT b from BankAccount b where b.account_number = :accountNumber  ")
    Optional<BankAccount> findByAccount_number(@Param("accountNumber") String accountNumber);
    @Query("SELECT b from BankAccount b where b.user.id = :user_id")
    List<BankAccount> findByUser_Id(@Param("user_id") Long user_id);

    @Query("SELECT b from BankAccount b where b.user.id = :user_id")
    List<BankAccount> findByUser_Id(@Param("user_id") Long user_id);


    @Query("select u.username , b.balance from BankAccount b inner join  User u on  b.user.id   =u.id")
    List<Object[]> getUserBalance();
}