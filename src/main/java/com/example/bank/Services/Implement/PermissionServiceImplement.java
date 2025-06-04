package com.example.bank.Services.Implement;

import com.example.bank.Dto.Request.PermissionRequest;
import com.example.bank.Dto.Response.PermissionResponse;
import com.example.bank.Entity.Permission;
import com.example.bank.Exception.AppException;
import com.example.bank.Exception.ErrorCode;
import com.example.bank.Mapper.PermissionMapper;
import com.example.bank.Repository.PermissionRepository;
import com.example.bank.Services.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionServiceImplement implements PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @Override
    public PermissionResponse createPermission(PermissionRequest request) {
        if (permissionRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        }
        Permission permission = permissionMapper.toRequest(request);

        permissionRepository.save(permission);
        return permissionMapper.toResponse(permission);
    }

    @Override
    public List<PermissionResponse> getAllPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        if (permissions.isEmpty()) {
            throw new AppException(ErrorCode.PERMISSION_NOT_FOUND);
        }
        return permissions.stream().map(permissionMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public void deletePermission(String name) {
        permissionRepository.deleteById(name);

    }

}
