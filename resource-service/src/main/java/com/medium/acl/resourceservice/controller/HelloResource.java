package com.medium.acl.resourceservice.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v0.1.1")
public class HelloResource {

    @GetMapping("/")
    public String greeting() {
        return "Hello from the otherside!!!!";
    }

    @GetMapping("/user")
    public String protectedSource() {
        return "Hello from protected-source";
    }

}
