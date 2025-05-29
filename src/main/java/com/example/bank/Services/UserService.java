package com.example.bank.Services;

import com.example.bank.Dto.Request.UserRequest;
import com.example.bank.Dto.Request.UserUpdateRequest;
import com.example.bank.Dto.Response.UserResponse;

import java.util.List;

public interface UserService {
    public UserResponse createUser(UserRequest userRequest);

    UserResponse findUserById(Long id);

    List<UserResponse> findAllUsers();

    void deleteUserById(Long id);

    UserResponse updateUser(Long id, UserUpdateRequest userRequest);


    void softDeleteUser(Long id);

    void restoreUser(Long id);

    List<UserResponse> getAllDeletedUsers();
}
