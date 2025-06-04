package com.example.bank.Services.Implement;

import com.example.bank.Dto.Request.RoleRequest;
import com.example.bank.Dto.Response.RoleResponse;
import com.example.bank.Entity.Role;
import com.example.bank.Exception.AppException;
import com.example.bank.Exception.ErrorCode;
import com.example.bank.Mapper.RoleMapper;
import com.example.bank.Repository.PermissionRepository;
import com.example.bank.Repository.RoleRepository;
import com.example.bank.Services.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImplement implements RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    @Override
    public RoleResponse createRole(RoleRequest request) {
        if (roleRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }
        Role role = roleMapper.toRequest(request);
        var permission = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permission));
        var save = roleRepository.save(role);
        return roleMapper.toResponse(save);
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        List<Role> role = roleRepository.findAll();
        if (role == null) {
            throw new AppException(ErrorCode.ROLE_NOT_FOUND);
        }
        return role.stream().map(roleMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public RoleResponse updateRole(String name,RoleRequest request) {
        if(roleRepository.findByName(name).isEmpty()) {
            throw new AppException(ErrorCode.ROLE_NOT_FOUND);
        }
        Role role = roleMapper.toRequest(request);

        var permission = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permission));
        var save = roleRepository.save(role);
        return roleMapper.toResponse(save);

    }

    @Override
    public RoleResponse getByName(String name) {
//        var role = roleRepository.fibd(name);
        return null;
    }
}
