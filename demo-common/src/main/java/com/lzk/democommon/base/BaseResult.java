package com.lzk.democommon.base;


import com.alibaba.fastjson.JSON;
import com.lzk.democommon.status.Status;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class BaseResult implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    private int count = 0;

    private String msg;

    private int code;

    private Object data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setResult(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public BaseResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public BaseResult(Status resultStatus) {
        this.code = resultStatus.code();
        this.msg = resultStatus.msg();
    }

    public BaseResult(Status resultStatus, Object data) {
        this.code = resultStatus.code();
        this.msg = resultStatus.msg();
        this.data = data;
    }

    public BaseResult(Status resultStatus, Object data, String msg) {
        this.code = resultStatus.code();
        this.msg = StringUtils.isNotBlank(msg)?msg:resultStatus.msg();
        this.data = data;
    }

    public static BaseResult success(int flag) {
        return flag == 1 ? new BaseResult(Status.OPSUCCESS, flag) : new BaseResult(Status.OPFAIL, flag);
    }

    public static BaseResult success(boolean flag){
        return flag == true ? new BaseResult(Status.OPSUCCESS,flag) : new BaseResult(Status.OPFAIL,flag);
    }

    public static BaseResult success(Object data){
        return new BaseResult(Status.OPSUCCESS,data);
    }

    public static BaseResult success(){
        return new BaseResult(Status.SUCCESS);
    }


    public static BaseResult error(Status status) {
        return new BaseResult(status);
    }

    private int longToInt(long size) {
        return Integer.valueOf(String.valueOf(size));
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }


    @Override
    public String toString() {
        return "BaseResult{" +
                "id=" + id +
                ", count=" + count +
                ", msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}
