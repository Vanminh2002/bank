package com.example.bank.Controller;

import com.example.bank.Dto.Request.UserRequest;
import com.example.bank.Dto.Request.UserUpdateRequest;
import com.example.bank.Dto.Response.ApiResponse;
import com.example.bank.Dto.Response.UserResponse;
import com.example.bank.Services.Implement.UserServiceImplement;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserServiceImplement userService;

    @PostMapping
    ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.createUser(userRequest));
    }

    @GetMapping("/by/{id}")
    ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping
    ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{id}")
    ResponseEntity<UserResponse> updateUser(@Valid @PathVariable Long id, @RequestBody UserUpdateRequest userRequest) {
        return ResponseEntity.ok(userService.updateUser(id, userRequest));
    }
    @PutMapping("/update-by-admin/{id}")
    ResponseEntity<UserResponse> updateUserByAdmin(@Valid @PathVariable Long id, @RequestBody UserUpdateRequest userRequest) {
        return ResponseEntity.ok(userService.updateUserByAdmin(id, userRequest));
    }


    @PutMapping("/soft-delete/{id}")
    String softDelete(@PathVariable Long id) {
        userService.softDeleteUser(id);
        return "User has been transferred to the bin ";
    }


    @PutMapping("/recover/{id}")
    String restore(@PathVariable Long id) {
        userService.restoreUser(id);
        return "User has been recover";
    }


    @GetMapping("/all-user-soft-delete")
    ApiResponse<List<UserResponse>> getAllUserSoftDelete() {
        return ApiResponse.<List<UserResponse>>builder()
                .code(200)
                .message("you have been all User soft delete")
                .result(userService.getAllDeletedUsers())
                .build();
    }

    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo() {
        UserResponse response = userService.getMyInfo();
        return ApiResponse.<UserResponse>builder()
                .result(response)
                .build();
    }
}
