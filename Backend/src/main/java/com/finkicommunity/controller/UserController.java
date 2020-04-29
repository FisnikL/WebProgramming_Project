package com.finkicommunity.controller;

import com.finkicommunity.domain.User;
import com.finkicommunity.domain.request.user.FollowRequest;
import com.finkicommunity.domain.response.UserResponse;
import com.finkicommunity.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @PostMapping("/follow")
    public ResponseEntity<FollowRequest> follow(@RequestBody @Valid FollowRequest followRequest){
        return ResponseEntity.ok(userService.follow(followRequest));
    }
}
