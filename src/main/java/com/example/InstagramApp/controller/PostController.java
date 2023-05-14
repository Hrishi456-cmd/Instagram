package com.example.InstagramApp.controller;

import com.example.InstagramApp.dto.SignInInput;
import com.example.InstagramApp.dto.SignInOutput;
import com.example.InstagramApp.dto.SignUpInput;
import com.example.InstagramApp.dto.SignUpOutput;
import com.example.InstagramApp.service.postService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    postService postService;

    //sign up
    @PostMapping("/signup")
    public SignUpOutput signup(@RequestBody SignUpInput signUpDto)
    {
        return postService.signUp(signUpDto);
    }

    //sign in
    @PostMapping("/signin")
    public SignInOutput signup(@RequestBody SignInInput signInDto)
    {
        return postService.signIn(signInDto);
    }

}
