package com.lzk.demosecurity.core.handle;

import com.lzk.democommon.status.Status;
import com.lzk.democommon.utils.ServletUtil;
import com.lzk.demosecurity.config.SecurityProperties;
import com.lzk.demosecurity.core.service.SecurityService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@AllArgsConstructor
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    private final SecurityProperties properties;

    private final SecurityService securityService;




    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String token = request.getHeader(properties.getHead());
        if(StringUtils.isNotBlank(token)){
            securityService.logout(token);
        }
        ServletUtil.returnJSON(response,Status.OPFAIL,null);
    }
}
