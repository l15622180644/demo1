package com.lzk.demosecurity.core.service;

import com.lzk.demosecurity.core.SecurityUserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SecurityService extends UserDetailsService {

    /**
     * 校验 token 的有效性，并获取用户信息
     * 通过后，刷新 token 的过期时间
     * @param token
     * @return 用户信息
     */
    SecurityUserInfo verifyTokenAndRefresh(String token);

    /**
     * 基于 token 退出登录
     * @param token
     */
    void logout(String token);

}
