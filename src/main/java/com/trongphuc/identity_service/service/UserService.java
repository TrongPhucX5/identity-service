package com.trongphuc.identity_service.service;

import com.trongphuc.identity_service.dto.request.UserCreationRequest;
import com.trongphuc.identity_service.dto.request.UserUpdateRequest;
import com.trongphuc.identity_service.dto.response.UserResponse;
import com.trongphuc.identity_service.entity.User;
import com.trongphuc.identity_service.exception.AppException;
import com.trongphuc.identity_service.exception.ErrorCode;
import com.trongphuc.identity_service.mapper.UserMapper;
import com.trongphuc.identity_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Service

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    public UserResponse createUser(UserCreationRequest request){
        if (userRepository.existsByUsername(request.getUsername()))
            throw new RuntimeException("ErrorCode.USER_EXISTED");

        User user = userMapper.toUser(request);

        return userMapper.toUserResponse(userRepository.save(user));

    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateUser(user, request);

        return userMapper.toUserResponse(userRepository.save(user));

    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
     }

    public List<UserResponse> getUsers(){
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse).toList();
    }

    public UserResponse getUser(String id){
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }
}
