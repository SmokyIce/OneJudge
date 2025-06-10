package com.smokingice.sandbox.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/h")
public class MainController {
    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
