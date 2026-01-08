package com.auth_service.controller;

import com.auth_service.dto.ApiResponse;
import com.auth_service.dto.request.UserRequest;
import com.auth_service.dto.request.UserUpdateRequest;
import com.auth_service.dto.response.UserResponse;
import com.auth_service.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping("/user")
    public ApiResponse<UserResponse> createUser(@RequestBody UserRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/{id}")
    public ApiResponse<UserResponse> getUser(@PathVariable String id){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(id))
                .build();
    }

    @PutMapping("/user/{id}")
    public ApiResponse<UserResponse> updateUser(
            @PathVariable String id,
            @RequestBody UserUpdateRequest request
    ){
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(id, request))
                .build();
    }

    // thieu api update role

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/user/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable String id){
        return ApiResponse.<Void>builder()
                .result(userService.deleteUser(id))
                .build();
    }
}
