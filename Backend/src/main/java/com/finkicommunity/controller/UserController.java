package com.finkicommunity.controller;

import com.finkicommunity.domain.request.user.FollowRequest;
import com.finkicommunity.domain.request.user.RegisterUserRequest;
import com.finkicommunity.domain.response.user.UserResponse;
import com.finkicommunity.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/exists")
    public ResponseEntity<Boolean> doesUsernameExists(@RequestBody Map<String, String> json){
        String username = json.get("username");
        System.out.println(username);
        return ResponseEntity.ok(userService.doesUsernameExists(username));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterUserRequest registerUserRequest){
        System.out.println(registerUserRequest);
        if(userService.saveNewUser(registerUserRequest) != null){
            System.out.println("SUCCESS!");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        System.out.println("BAD_REQUEST");
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/follow")
    public ResponseEntity<FollowRequest> follow(@RequestBody @Valid FollowRequest followRequest){
        return ResponseEntity.ok(userService.follow(followRequest));
    }
}
