package com.lzk.democoreserver.config;


import com.lzk.democommon.base.BaseResult;
import com.lzk.democommon.exception.ServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ServiceException.class)
    @ResponseBody
    public BaseResult serviceException(ServiceException e){
        return new BaseResult(e.getStatus());
    }
}
