package com.example.bank.Services;


import com.example.bank.Dto.Request.PermissionRequest;
import com.example.bank.Dto.Response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    PermissionResponse createPermission(PermissionRequest request);
    List<PermissionResponse> getAllPermissions();
    void deletePermission(String name);
}
