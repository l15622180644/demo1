package com.lzk.democoreserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * 菜单权限表
 *
 * @author lzk
 * @since 2022-01-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 菜单ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 菜单名称 */
    private String name;

    /** 权限标识 */
    private String permission;

    /** 菜单类型 */
    private Integer menuType;

    /** 显示顺序 */
    private Integer sort;

    /** 父菜单ID */
    private Long parentId;

    /** 路由地址 */
    private String path;

    /** 菜单图标 */
    private String icon;

    /** 组件路径 */
    private String component;

    /** 菜单状态（0正常 1停用） */
    private Integer status;

    /** 创建时间 */
    private Long createTime;

    /** 更新时间 */
    private Long updateTime;

    /** 是否删除 */
    @TableLogic
    private Integer isDel;

    @TableField(exist = false)
    private List<SysMenu> children;


}
