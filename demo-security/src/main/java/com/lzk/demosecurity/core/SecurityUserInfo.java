package com.lzk.demosecurity.core;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * 登录用户信息
 *
 * @author 芋道源码
 */
@Data
public class SecurityUserInfo implements UserDetails {

    private Object userInfo;

    private List<String> permissions;

    private String token;

    private Long loginTime;

    public SecurityUserInfo() {
    }

    public SecurityUserInfo(Object userInfo, List<String> permissions) {
        this.userInfo = userInfo;
        this.permissions = permissions;
    }

    public SecurityUserInfo(Object userInfo, List<String> permissions, String token, Long loginTime) {
        this.userInfo = userInfo;
        this.permissions = permissions;
        this.token = token;
        this.loginTime = loginTime;
    }

    @Override
    @JsonIgnore// 避免序列化
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    @JsonIgnore// 避免序列化
    public boolean isAccountNonExpired() {
        return true; // 返回 true，不依赖 Spring Security 判断
    }

    @Override
    @JsonIgnore// 避免序列化
    public boolean isAccountNonLocked() {
        return true; // 返回 true，不依赖 Spring Security 判断
    }

    @Override
    @JsonIgnore// 避免序列化
    public boolean isCredentialsNonExpired() {
        return true;  // 返回 true，不依赖 Spring Security 判断
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
