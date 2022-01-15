package com.lzk.democoreserver.controller;


import com.lzk.democommon.base.BaseResult;
import com.lzk.democoreserver.controller.vo.LoginVO;
import com.lzk.democoreserver.service.UsersService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/usersService")
public class UsersController {

    @Resource
    private UsersService usersService;

    @PostMapping("/login")
    public BaseResult loginByPW(@RequestBody @Validated LoginVO loginVO){
        return usersService.loginByPassword(loginVO);
    }
}
