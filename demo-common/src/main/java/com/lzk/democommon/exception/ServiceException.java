package com.lzk.democommon.exception;


import com.lzk.democommon.status.Status;

/**
 * 业务逻辑异常 Exception
 */
public final class ServiceException extends RuntimeException {

    /**
     * 业务错误码
     */
    private Integer code;
    /**
     * 错误提示
     */
    private String message;

    private Status status;

    /**
     * 空构造方法，避免反序列化问题
     */
    public ServiceException() {
    }

    public ServiceException(Status status){
        this.code = status.code();
        this.message = status.msg();
        this.status = status;
    }

    public ServiceException(int code , String msg){
        this.code = code;
        this.message = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ServiceException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
