package com.lzk.democoreserver.config;


import com.lzk.democommon.base.BaseResult;
import com.lzk.democommon.exception.ServiceException;
import com.lzk.democommon.status.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ValidationException;
import java.util.Arrays;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = ServiceException.class)
    public BaseResult serviceException(ServiceException e){
        return new BaseResult(e.getStatus());
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public BaseResult exceptionHandler(HttpMessageNotReadableException e) {
        LOGGER.error(HttpMessageNotReadableException.class.getName(), e);
        return new BaseResult(Status.PARAMEXCEPTION);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public BaseResult exceptionHandler(HttpRequestMethodNotSupportedException e) {
        LOGGER.error(HttpRequestMethodNotSupportedException.class.getName(), e);
        return new BaseResult(Status.REQUEST_METHOD_ERROE,null,"该请求仅支持"+ Arrays.toString(e.getSupportedMethods()));
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public BaseResult exceptionHandler(MissingServletRequestParameterException e) {
        LOGGER.error(MissingServletRequestParameterException.class.getName(), e);
        return new BaseResult(Status.PARAMEXCEPTION,null,"缺少类型为 "+e.getParameterType()+" 的 "+e.getParameterName()+" 参数");
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public BaseResult exceptionHandler(MethodArgumentNotValidException e){
        LOGGER.error(MissingServletRequestParameterException.class.getName(), e);
        return new BaseResult(Status.PARAMEXCEPTION,null,e.getBindingResult().getFieldError().getDefaultMessage());
    }
}
