package com.example.bank.Services.Implement;

import com.example.bank.Dto.Request.UserRequest;
import com.example.bank.Dto.Request.UserUpdateRequest;
import com.example.bank.Dto.Response.UserResponse;
import com.example.bank.Entity.User;
import com.example.bank.Exception.AppException;
import com.example.bank.Exception.ErrorCode;
import com.example.bank.Mapper.UserMapper;
import com.example.bank.Repository.UserRepository;
import com.example.bank.Services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.aspectj.weaver.ast.Not;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImplement implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toRequest(userRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User save = userRepository.save(user);
        return userMapper.toResponse(save);
    }

    @Override
    public UserResponse findUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> findAllUsers() {


        List<User> user = userRepository.findAll();
        if (user.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        return user.stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userRepository.delete(user);
//        userRepository.deleteById(id);
    }

    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest userRequest) {

        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
//        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
//            throw new RuntimeException("Username already exists");
//        }

        userMapper.updateUser(user, userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User save = userRepository.save(user);
        return userMapper.toResponse(save);
    }

    @Override
    public void softDeleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setDeleted(true);
        userRepository.save(user);
    }

    @Override
    public void restoreUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setDeleted(false);
        userRepository.save(user);
    }

    @Override
    public List<UserResponse> getAllDeletedUsers() {

        List<User> deletedUsers = userRepository.findDeletedUsers();
        if (deletedUsers.isEmpty()) {
            throw new NoSuchElementException("Không có user nào đã bị xóa.");
        }

        return deletedUsers.stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }
}
