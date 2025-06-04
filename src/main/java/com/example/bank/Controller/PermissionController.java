package com.example.bank.Controller;

import com.example.bank.Dto.Request.PermissionRequest;

import com.example.bank.Dto.Response.ApiResponse;
import com.example.bank.Dto.Response.PermissionResponse;
import com.example.bank.Services.Implement.PermissionServiceImplement;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionServiceImplement permissionService;


    @PostMapping
    ApiResponse<PermissionResponse> createPermission(@RequestBody @Valid PermissionRequest permissionRequest) {
        PermissionResponse response = permissionService.createPermission(permissionRequest);
        return ApiResponse.<PermissionResponse>builder()
                .result(response)
                .message("Permission created")
                .build();
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> getAllPermissions() {
        List<PermissionResponse> permissions = permissionService.getAllPermissions();
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissions)
                .message("Permissions found")
                .build();
    }

    @DeleteMapping("/delete-by/{name}")
    ApiResponse<Void> deletePermission(@PathVariable String name) {
        permissionService.deletePermission(name);
        return ApiResponse.<Void>builder()
                .message("Permission deleted")
                .build();
    }
}
