package com.lzk.demosecurity.core.filter;


import com.lzk.democommon.status.Status;
import com.lzk.democommon.utils.ServletUtil;
import com.lzk.demosecurity.config.SecurityProperties;
import com.lzk.demosecurity.core.SecurityUserInfo;
import com.lzk.demosecurity.core.service.SecurityService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT 过滤器，验证 token 的有效性
 *
 */

@AllArgsConstructor
public class JWTAuthenticationTokenFilter extends OncePerRequestFilter {

    private final SecurityProperties properties;

    private final SecurityService securityService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(properties.getHead());
        if(StringUtils.isNotBlank(token)){
            SecurityUserInfo securityUserInfo = securityService.verifyTokenAndRefresh(token);
            if(securityUserInfo!=null){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(securityUserInfo, null, securityUserInfo.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //设置到security上下文
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }else {
                ServletUtil.returnJSON(response, Status.TOKENTIMEOUT,"无效token");
            }
        }
        filterChain.doFilter(request,response);
    }
}
