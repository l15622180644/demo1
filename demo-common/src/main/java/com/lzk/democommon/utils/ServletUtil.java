package com.lzk.democommon.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzk.democommon.status.Status;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author
 * @module
 * @date 2021/5/25 17:17
 */
public class ServletUtil {

    public static ServletRequestAttributes getRequestAttributes(){
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    public static HttpServletRequest getRequest(){
        return getRequestAttributes().getRequest();
    }

    public static HttpServletResponse getResponse(){
        return getRequestAttributes().getResponse();
    }

    public static String getUserAgent(HttpServletRequest request) {
        String ua = request.getHeader("User-Agent");
        return ua != null ? ua : "";
    }

    // 必须使用 APPLICATION_JSON_UTF8_VALUE，否则会乱码
    public static void returnJSON(HttpServletResponse response, Object object) {
        String content = JSON.toJSONString(object);
        cn.hutool.extra.servlet.ServletUtil.write(response, content, MediaType.APPLICATION_JSON_UTF8_VALUE);
    }

    public static void returnJSON(HttpServletResponse response, Status status, String msg) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", status.code());
        jsonObject.put("msg", StringUtils.isNotBlank(msg)?msg:status.msg());
        String jsonString = jsonObject.toJSONString();
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(jsonString);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }

}
