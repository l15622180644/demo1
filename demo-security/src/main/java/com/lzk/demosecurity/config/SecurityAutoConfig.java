package com.lzk.demosecurity.config;

import com.lzk.demosecurity.core.filter.JWTAuthenticationTokenFilter;
import com.lzk.demosecurity.core.handle.AccessDeniedHandlerImpl;
import com.lzk.demosecurity.core.handle.AuthenticationEntryPointImpl;
import com.lzk.demosecurity.core.handle.LogoutSuccessHandlerImpl;
import com.lzk.demosecurity.core.provider.UsernamePasswordAuthenticationProvider;
import com.lzk.demosecurity.core.service.SecurityService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;

/**
 * Spring Security 自动配置类，主要用于相关组件的配置
 */

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityAutoConfig {

    @Resource
    private SecurityProperties securityProperties;

    @Bean
    public JWTAuthenticationTokenFilter  jwtAuthenticationTokenFilter(SecurityService securityService){
        return new JWTAuthenticationTokenFilter(securityProperties,securityService);
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new AccessDeniedHandlerImpl();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new AuthenticationEntryPointImpl();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(SecurityService securityService){
        return new LogoutSuccessHandlerImpl(securityProperties,securityService);
    }

    @Bean
    public UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider(SecurityService securityService){
        return new UsernamePasswordAuthenticationProvider(securityService);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
