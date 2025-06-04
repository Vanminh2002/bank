package com.example.bank.Mapper;

//import com.example.bank.Dto.Request.RegisterRequest;

import com.example.bank.Dto.Request.RoleRequest;
import com.example.bank.Dto.Response.RoleResponse;
import com.example.bank.Entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    // bỏ qua field permission
    @Mapping(target = "permissions", ignore = true)
    Role toRequest(RoleRequest request);

    RoleResponse toResponse(Role role);

}
