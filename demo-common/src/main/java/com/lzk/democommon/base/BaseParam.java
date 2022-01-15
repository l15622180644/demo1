package com.lzk.democommon.base;


import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


import java.io.Serializable;
import java.util.List;

/**
 * 描述：接口参数获取的基类
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseParam implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 111111L;
    // /=================select===================
    private Long id;
    private String name;
    private String phone;
    private String department;
    private String title;
    private Integer page;
    private Integer limit;
    private Long startTime;
    private Long endTime;
    private Long userId;
    private Long orgId;
    private Integer orgType;
    private String openId;
    private Integer status;
    private Integer status2;
    private List<String> ids;
    private Integer type;
    private String strType;
    private Integer platform;
    private String number;
    private Boolean isExistUsedCars;//查询用户是否存在用车
    private Boolean isETC;
    private Boolean isASC;
    public <T> Page<T> getPAGE(Class<T> t) {
        return new Page<T>(page == null ? 1 : page, limit == null ? 10 : limit).addOrder(OrderItem.desc("create_time"));
    }
    public <T> Page<T> getPAGEMR(Class<T> t) {
        return new Page<T>(page == null ? 1 : page, limit == null ? 10 : limit);
    }
}
