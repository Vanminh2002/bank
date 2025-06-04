package com.example.bank.Services.Implement;

import com.example.bank.Dto.Request.UserRequest;
import com.example.bank.Dto.Request.UserUpdateRequest;
import com.example.bank.Dto.Response.UserResponse;
import com.example.bank.Entity.User;
import com.example.bank.Enums.Role;
import com.example.bank.Exception.AppException;
import com.example.bank.Exception.ErrorCode;
import com.example.bank.Mapper.UserMapper;
import com.example.bank.Repository.RoleRepository;
import com.example.bank.Repository.UserRepository;
import com.example.bank.Services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//@EnableWebSecurity
public class UserServiceImplement implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toRequest(userRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // dùng HashSet để tránh trùng lặp, khả năng tìm kiếm nhanh hơn
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
//        user.setRoles(roles);
        User save = userRepository.save(user);
        return userMapper.toResponse(save);
    }

    // kiểm tra trước khi vào hàm
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Override
    public UserResponse findUserById(Long id) {


        // đoạn này để log thông tin user đang đăng nhập
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority ->
                log.info(grantedAuthority.getAuthority()));


        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toResponse(user);
    }

    // kiểm tra trước khi vào hàm
    @PreAuthorize("hasRole('ADMIN')")
    // kiểm tra sau khi hàm được thực thi
//    @PostAuthorize()
    @Override
    public List<UserResponse> findAllUsers() {

        // đoạn này để log thông tin user đang đăng nhập
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority ->
                log.info(grantedAuthority.getAuthority()));

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

    }

    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest userRequest) {

        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        userMapper.updateUser(user, userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        User save = userRepository.save(user);
        return userMapper.toResponse(save);
    }

    // sử dụng permission thay vì dùng role
    @PreAuthorize("hasRole('Update')")
    // hasAnyAuthority sẽ chuẩn hơn và cần ghi đủ tên của role hay permission
//    @PreAuthorize("hasAnyAuthority('Update')")
    @Override
    public UserResponse updateUserByAdmin(Long id, UserUpdateRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateUser(user, userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        var roles = roleRepository.findAllById(userRequest.getRoles());
        user.setRoles(new HashSet<>(roles));
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

    @Override
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toResponse(user);
    }
}
