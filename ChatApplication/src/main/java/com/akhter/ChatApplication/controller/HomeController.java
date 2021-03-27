package com.akhter.ChatApplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }


    @GetMapping("/profile")
    public String chatroom() {
        return "profile";
    }
}
