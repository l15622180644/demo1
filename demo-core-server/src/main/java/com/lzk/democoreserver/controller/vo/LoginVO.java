package com.lzk.democoreserver.controller.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginVO {

    @NotEmpty(message = "账号不能为空")
    private String loginName;

    @NotEmpty(message = "密码不能为空")
    private String password;

    @NotEmpty(message = "验证码不能为空")
    private String code;

    @NotEmpty(message = "验证码key不能为空")
    private String key;

}
