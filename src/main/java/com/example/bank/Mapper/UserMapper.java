package com.example.bank.Mapper;

//import com.example.bank.Dto.Request.RegisterRequest;
import com.example.bank.Dto.Request.UserRequest;
import com.example.bank.Dto.Request.UserUpdateRequest;
//import com.example.bank.Dto.Response.RegisterResponse;
import com.example.bank.Dto.Response.UserResponse;
import com.example.bank.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toRequest(UserRequest userRequest);

    @Mapping(target = "roles" , source = "roles")
    UserResponse toResponse(User user);

    //MappingTarget là 1 anotation của mapstruct dùng trong hàm update để cập nhật đối tượng có sẵn thay vì update
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

//    User registerUser(RegisterRequest request);
//    RegisterResponse toResponseRegis(User user);
}
