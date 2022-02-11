package com.lzk.demosecurity.util;

import com.lzk.demosecurity.core.SecurityUserInfo;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author
 * @module
 * @date 2022/1/18 15:59
 */
public class MySecurityUtil {

    public static SecurityUserInfo getUser(){
        return (SecurityUserInfo)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
