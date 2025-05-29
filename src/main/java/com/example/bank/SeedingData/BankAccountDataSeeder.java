//package com.example.bank.SeedingData;
//
//import com.example.bank.Entity.BankAccount;
//import com.example.bank.Entity.User;
//import com.example.bank.Repository.BankAccountRepository;
//import com.example.bank.Repository.UserRepository;
//import com.github.javafaker.Faker;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//import java.util.Random;
//
//@Component
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//
//
//public class BankAccountDataSeeder implements CommandLineRunner {
//    BankAccountRepository bankAccountRepository;
//    UserRepository userRepository;
//    PasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) throws Exception {
//        Faker faker = new Faker(new Locale("en"));
//        Random random = new Random();
//
//
//        List<User> users = userRepository.findAll();
//        if (users.isEmpty()) {
//            System.out.println("No users found");
//            return;
//        }
//
////        User user = userRepository.findById(1L).orElseGet(()->{
////            User user1 = new User();
////            user1.setUsername("Test1");
////            user1.setPassword(passwordEncoder.encode("123456"));
////            user1.setFullName("Test1");
////            return userRepository.save(user1);
////        });
//    List<BankAccount> bankAccounts = new ArrayList<>();
//    for (int i = 0; i < 1000; i++) {
//        BankAccount bankAccount = new BankAccount();
//
//        User randomUser = users.get(random.nextInt(users.size()));
//        bankAccount.setUser(randomUser);
//        bankAccount.setAccount_number("100"+String.format("%08d", i));
//        bankAccount.setBalance(BigDecimal.valueOf(faker.number().randomDouble(2,0,100000)));
//        bankAccount.setStatus(BankAccount.status.ACTIVE);
//        bankAccount.setType(BankAccount.type.CHECKING);
//        bankAccounts.add(bankAccount);
//    }
//    bankAccountRepository.saveAll(bankAccounts);
//        System.out.println("Bank account created 1000 accounts");
//
//    }
//}
