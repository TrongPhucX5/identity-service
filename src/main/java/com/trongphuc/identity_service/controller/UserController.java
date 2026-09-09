package com.trongphuc.identity_service.controller;

import com.trongphuc.identity_service.dto.request.ApiRespond;
import com.trongphuc.identity_service.dto.request.UserCreationRequest;
import com.trongphuc.identity_service.dto.request.UserUpdateRequest;
import com.trongphuc.identity_service.entity.User;
import com.trongphuc.identity_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    ApiRespond<User> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiRespond<User> apiRespond = new ApiRespond<>();

        apiRespond.setResult(userService.createUser(request));
        return apiRespond;
    }

    @GetMapping
    List<User> getUsers() {
        return userService.getUsers();
    }


    @GetMapping("/{userId}")
    User getUser(@PathVariable("userId") String userId){
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    User updateUser(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
        return "User has been deleted!";
    }

}
