package com.example.bank.Controller;

import com.example.bank.Dto.Request.PermissionRequest;
import com.example.bank.Dto.Request.RoleRequest;
import com.example.bank.Dto.Response.ApiResponse;
import com.example.bank.Dto.Response.PermissionResponse;
import com.example.bank.Dto.Response.RoleResponse;
import com.example.bank.Services.Implement.PermissionServiceImplement;
import com.example.bank.Services.Implement.RoleServiceImplement;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleServiceImplement roleServiceImplement;


    @PostMapping
    ApiResponse<RoleResponse> createRole(@RequestBody @Valid RoleRequest request) {
        RoleResponse response = roleServiceImplement.createRole(request);
        return ApiResponse.<RoleResponse>builder()
                .result(response)
                .message("Role created")
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAllPermissions() {
        List<RoleResponse> responses = roleServiceImplement.getAllRoles();
        return ApiResponse.<List<RoleResponse>>builder()
                .result(responses)
                .message("Permissions found")
                .build();
    }
    @PutMapping("update-by/{name}")
    ApiResponse<RoleResponse> updateRole(@PathVariable String name, @RequestBody @Valid RoleRequest request) {
        RoleResponse response = roleServiceImplement.updateRole(name, request);
        return ApiResponse.<RoleResponse>builder()
                .result(response)
                .message("Role updated")
                .build();
    }
//    @DeleteMapping("/delete-by/{name}")
//    ApiResponse<Void> deletePermission(@PathVariable String name) {
//        roleServiceImplement.deletePermission(name);
//        return ApiResponse.<Void>builder()
//                .message("Permission deleted")
//                .build();
//    }
}
