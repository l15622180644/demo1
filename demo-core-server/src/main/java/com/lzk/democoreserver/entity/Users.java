package com.lzk.democoreserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Users implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String loginName;

    private String password;

    @TableField(exist = false)
    private List<Long> roleIds;
}
