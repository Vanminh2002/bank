package com.example.bank.Services.Implement;

import com.example.bank.Dto.Request.BankAccountRequest;
import com.example.bank.Dto.Request.TransferRequest;
import com.example.bank.Dto.Response.BankAccountResponse;
import com.example.bank.Dto.Response.TransferResponse;
import com.example.bank.Entity.BankAccount;
import com.example.bank.Entity.TransferTransaction;
import com.example.bank.Entity.User;
import com.example.bank.Exception.AppException;
import com.example.bank.Exception.ErrorCode;
import com.example.bank.Mapper.BankAccountMapper;
import com.example.bank.Mapper.TransferMapper;
import com.example.bank.Repository.BankAccountRepository;
import com.example.bank.Repository.TransferRepository;
import com.example.bank.Repository.UserRepository;
import com.example.bank.Services.BankAccountService;
import com.example.bank.Services.TransferService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransferServiceImplement implements TransferService {
    UserRepository userRepository;
    BankAccountMapper bankAccountMapper;
    BankAccountRepository bankAccountRepository;
    private final TransferMapper transferMapper;
    private final TransferRepository transferRepository;


    @Transactional
    @Override
    public TransferResponse transferBankAccount(TransferRequest request) {
        String toAccountNumber = request.getToAccount();
        String fromAccountNumber = request.getFormAccount();
        BigDecimal amount = request.getAmount();
//        TransferTransaction save = null;
        TransferTransaction transaction = transferMapper.toRequest(request);
        try {
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Amount must be greater than zero");
            }
            if (fromAccountNumber.equals(toAccountNumber)) {
                throw new IllegalArgumentException("Bạn không thể chuyền tiền cho chính mình");
            }
            BankAccount fromAccount = bankAccountRepository.findByAccount_number(fromAccountNumber)
                    .orElseThrow(() -> new AppException(ErrorCode.BANK_NUMBER_NOT_FOUND));

            BankAccount toAccount = bankAccountRepository.findByAccount_number(toAccountNumber)
                    .orElseThrow(() -> new AppException(ErrorCode.BANK_NUMBER_NOT_FOUND));
            if (!"ACTIVE".equalsIgnoreCase(String.valueOf(fromAccount.getStatus()))) {
                throw new RuntimeException("Tài khoản của bạn đã bị khóa hoặc vô hiệu hóa");
            }
            if (!"ACTIVE".equalsIgnoreCase(String.valueOf(toAccount.getStatus()))) {
                throw new RuntimeException("Tài khoản bạn muốn chuyển tiền đến đã bị khóa hoặc vô hiệu hóa");
            }
            if (fromAccount.getBalance().compareTo(amount) < 0) {
                throw new RuntimeException("Số dư của bạn không đủ");
            }
            // Trừ tiền từ tài khoản nguồn
            fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
            bankAccountRepository.save(fromAccount);

            // Cộng tiền vào tài khoản đích
            toAccount.setBalance(toAccount.getBalance().add(amount));
            bankAccountRepository.save(toAccount);


//            transaction = transferMapper.toRequest(request);
            transaction.setFromAccountBalance(fromAccount.getBalance());
            transaction.setToAccountBalance(toAccount.getBalance());
            transaction.setFormAccount(fromAccount.getAccount_number());
            transaction.setStatus(TransferTransaction.Status.SUCCESS);
        } catch (Exception e) {
            transaction.setStatus(TransferTransaction.Status.FAILED);
        }
        System.out.println("From: " + fromAccountNumber);
        System.out.println("To: " + toAccountNumber);
        System.out.println("Amount: " + amount);
        TransferTransaction saveTransfer = transferRepository.save(transaction);
        return transferMapper.toResponse(saveTransfer);
    }
}
