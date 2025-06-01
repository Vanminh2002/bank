package com.example.bank.Services.Implement;

import com.example.bank.Dto.Request.BankAccountRequest;
import com.example.bank.Dto.Request.TransferRequest;
import com.example.bank.Dto.Response.BankAccountResponse;
import com.example.bank.Dto.Response.TransferResponse;
import com.example.bank.Entity.BankAccount;
import com.example.bank.Entity.User;
import com.example.bank.Exception.AppException;
import com.example.bank.Exception.ErrorCode;
import com.example.bank.Mapper.BankAccountMapper;

import com.example.bank.Repository.BankAccountRepository;
import com.example.bank.Repository.UserRepository;
import com.example.bank.Services.BankAccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BankAccountServiceImplement implements BankAccountService {
    UserRepository userRepository;
    BankAccountMapper bankAccountMapper;
    BankAccountRepository bankAccountRepository;

    @Override
    public BankAccountResponse createBankAccount(BankAccountRequest bankAccountRequest) {
        User user = userRepository.findById(bankAccountRequest.getUserid())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        String accountNumber = generateAccountNumber();
        BankAccount bankAccount = new BankAccount();
        bankAccount.setUser(user);
        bankAccount.setType(BankAccount.type.CHECKING);
        bankAccount.setAccount_number(accountNumber);
        bankAccount.setBalance(bankAccountRequest.getBalance());
        bankAccount.setStatus(BankAccount.status.ACTIVE);
        bankAccountMapper.toRequest(bankAccountRequest);
        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);
        return bankAccountMapper.toResponse(savedBankAccount);
    }

    @Override
    public List<BankAccountResponse> findAllBankAccounts() {
        List<BankAccount> account = (List<BankAccount>) bankAccountRepository.findAll();
        return account.stream().map(bankAccountMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public BankAccountResponse findBankAccountById(Long id) {
        BankAccount account = bankAccountRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return bankAccountMapper.toResponse(account);
    }

    @Override
    public BankAccountResponse findBankAccountByBankAccountNumber(String bankAccountNumber) {
        BankAccount account = bankAccountRepository.findByAccount_number(bankAccountNumber)
                .orElseThrow(() -> new AppException(ErrorCode.BANK_NUMBER_NOT_FOUND));
        return bankAccountMapper.toResponse(account);
    }

    @Override
    @Transactional
    public BankAccountResponse depositBankAccount(String accountNumber, BigDecimal amount) {
        System.out.println("Deposit requested: accountNumber=" + accountNumber + ", amount=" + amount);
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        BankAccount account = bankAccountRepository.findByAccount_number(accountNumber)
                .orElseThrow(() -> new AppException(ErrorCode.BANK_NUMBER_NOT_FOUND));


        if (!"ACTIVE".equalsIgnoreCase(String.valueOf(account.getStatus()))) {
            throw new RuntimeException("Account status is not ACTIVE");
        }
        account.setBalance(account.getBalance().add(amount));
        BankAccount savedAccount = bankAccountRepository.save(account);
        System.out.println("Deposit successful: new balance=" + savedAccount.getBalance());
        return bankAccountMapper.toResponse(savedAccount);
    }

    @Transactional
    @Override
    public TransferResponse transferBankAccount(TransferRequest request) {
        String toAccountNumber = request.getToAccount();
        String fromAccountNumber = request.getToAccount();
        BigDecimal amount = request.getAmount();
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
//        if (formBankAccount.equals(toBankAccount)) {
//            throw new IllegalArgumentException("Bạn không thể chuyền tiền cho chính mình");
//        }
        BankAccount fromAccount = bankAccountRepository.findByAccount_number(fromAccountNumber)
                .orElseThrow(() -> new AppException(ErrorCode.BANK_NUMBER_NOT_FOUND));

        BankAccount toAccount = bankAccountRepository.findByAccount_number(toAccountNumber)
                .orElseThrow(() -> new AppException(ErrorCode.BANK_NUMBER_NOT_FOUND));
        if (!"ACTIVE".equalsIgnoreCase(String.valueOf(fromAccount.getStatus()))) {
            throw new RuntimeException("Tài khoản bạn muốn chuyển tiền đến đã vị khóa hoặc vô hiệu hóa");
        }
        if (!"ACTIVE".equalsIgnoreCase(String.valueOf(toAccount.getStatus()))) {
            throw new RuntimeException("Tài khoản bạn muốn chuyển tiền đến đã vị khóa hoặc vô hiệu hóa");
        }
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Số dư của bạn không đủ");
        }




        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        bankAccountRepository.save(fromAccount);
        toAccount.setBalance(toAccount.getBalance().add(amount));
        bankAccountRepository.save(toAccount);

        return null;
    }

    @Override
    public List<BankAccountResponse> findAccountUser(Long id) {
        List<BankAccount> bankAccounts = bankAccountRepository.findByUser_Id(id);
        return bankAccounts.stream().map(bankAccountMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public void softDeleteBankAccount(Long id) {
        BankAccount account = bankAccountRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BANK_ACCOUNT_NOT_FOUND));

    }

    @Override
    public void restoreBankAccount(Long id) {

    }

    @Override
    public BankAccountResponse getAllBankAccountsSoftDeleted() {
        return null;
    }


    private String generateAccountNumber() {
        // Ví dụ: 12 chữ số bắt đầu bằng "1000"
        String prefix = "1000";
        String randomDigits = String.valueOf((long) (Math.random() * 1_0000_0000L));
        return prefix + String.format("%08d", Long.parseLong(randomDigits));
    }
}
