package com.medium.acl.authservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0.1/")
public class HelloResource {

    @GetMapping
    public String hello() {
        return "Hello From the Otherside !!!";
    }


    @GetMapping("/user")
    public String userAccess() {
        return "Hello USER from some-where!!!";
    }

    @GetMapping("/admin")
    public String adminAccess() {
        return "Hello ADMIN from every-where !!!";
    }
}
