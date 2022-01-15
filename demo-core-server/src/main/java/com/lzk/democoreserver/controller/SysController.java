package com.lzk.democoreserver.controller;

import com.lzk.democommon.base.BaseResult;
import com.lzk.democoreserver.service.SysService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author
 * @module
 * @date 2022/1/14 17:22
 */

@RestController
@RequestMapping("/sysService")
public class SysController {


    @Resource
    private SysService sysService;

    @GetMapping("/captcha")
    public BaseResult captcha(){
        return sysService.captcha();
    }
}
