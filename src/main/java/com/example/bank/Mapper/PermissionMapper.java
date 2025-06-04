package com.example.bank.Mapper;

//import com.example.bank.Dto.Request.RegisterRequest;

import com.example.bank.Dto.Request.PermissionRequest;
import com.example.bank.Dto.Request.UserRequest;
import com.example.bank.Dto.Request.UserUpdateRequest;
import com.example.bank.Dto.Response.PermissionResponse;
import com.example.bank.Dto.Response.UserResponse;
import com.example.bank.Entity.Permission;
import com.example.bank.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toRequest(PermissionRequest request);


    PermissionResponse toResponse(Permission permission);
}
