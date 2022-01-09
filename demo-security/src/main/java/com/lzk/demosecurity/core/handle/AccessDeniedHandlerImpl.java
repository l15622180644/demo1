package com.lzk.demosecurity.core.handle;

import com.lzk.democommon.status.Status;
import com.lzk.democommon.utils.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限不足时的处理类
 * Spring Security 通过 ExceptionTranslationFilter 方法，调用当前类
 */


@Slf4j
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {


    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ServletUtil.returnJSON(response,Status.FORBIDDEN,null);
    }
}
