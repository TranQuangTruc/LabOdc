package com.auth_service.mapper;

import com.auth_service.dto.request.UserRequest;
import com.auth_service.dto.response.UserResponse;
import com.auth_service.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserRequest userRequest);
    UserResponse toUserResponse(User user);
}
