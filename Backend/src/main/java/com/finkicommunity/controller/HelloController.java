package com.finkicommunity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloController {

    @GetMapping
    public String hello(){
        return "<h1>Welcome to Finki Community Forum</h1>";
    }

//    @PostMapping
//    public ResponseEntity<String> login(@RequestBody LoginViewModel loginViewModel){
//
//    }
}
