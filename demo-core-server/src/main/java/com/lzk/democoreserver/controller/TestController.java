package com.lzk.democoreserver.controller;


import com.lzk.demosecurity.core.SecurityUserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testService")
public class TestController {

    @GetMapping("/getMyTest/{content}")
    public String getMyTest(@PathVariable("content") String content){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUserInfo userInfo = (SecurityUserInfo)authentication.getPrincipal();
        return userInfo.toString();
    }
}
