package com.auth_service.service;

import com.auth_service.constant.PredefinedRole;
import com.auth_service.dto.request.UserRequest;
import com.auth_service.dto.request.UserUpdateRequest;
import com.auth_service.dto.response.UserResponse;
import com.auth_service.entity.Role;
import com.auth_service.entity.User;
import com.auth_service.exception.AppException;
import com.auth_service.exception.ErrorCode;
import com.auth_service.mapper.UserMapper;
import com.auth_service.repository.RoleRepository;
import com.auth_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserRequest request){
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.TALENT_ROLE).ifPresent(roles::add);

        user.setRoles(roles);

        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        return userMapper.toUserResponse(user);
    }
    public UserResponse getUser(String id){
        Optional<User> byId = userRepository.findById(id);
        User user = byId.get();
        return userMapper.toUserResponse(user);
    }
    public UserResponse updateUser(String id, UserUpdateRequest request){
        User user = userRepository.findById(id).get();
        user.setPassword(request.getPassword());
        return userMapper.toUserResponse(userRepository.save(user));
    }
    public Void deleteUser(String id){
        userRepository.deleteById(id);
        return null;
    }

    
}
