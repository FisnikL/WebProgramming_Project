package com.finkicommunity.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class HelloController {

    @GetMapping
    public String hello(){
        return "<h1>Welcome to Finki Community Forum</h1>";
    }
}
