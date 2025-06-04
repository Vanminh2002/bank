package com.example.bank.Services;


import com.example.bank.Dto.Request.RoleRequest;
import com.example.bank.Dto.Response.RoleResponse;
import com.example.bank.Entity.Role;

import java.util.List;

public interface RoleService {

    RoleResponse createRole(RoleRequest roleRequest);
    List<RoleResponse> getAllRoles();
    RoleResponse updateRole(String name,RoleRequest roleRequest);
    RoleResponse getByName(String name);
}
