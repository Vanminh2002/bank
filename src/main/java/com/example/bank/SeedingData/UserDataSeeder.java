//package com.example.bank.SeedingData;
//
//import com.example.bank.Entity.User;
//import com.example.bank.Repository.UserRepository;
//import com.github.javafaker.Faker;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.Locale;
//
//@Component
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//public class UserDataSeeder implements CommandLineRunner {
//    UserRepository userRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        Faker faker = new Faker(new Locale("en"));
//        for (int i = 0; i < 10; i++) {
//            User user = new User();
//            user.setFullName(faker.name().fullName());
//            user.setUsername(faker.name().username());
//            user.setPassword(faker.internet().password());
//            user.setDeleted(false);
//            userRepository.save(user);
//        }
//        System.out.println("Users have been added 10 times");
//    }
//}
