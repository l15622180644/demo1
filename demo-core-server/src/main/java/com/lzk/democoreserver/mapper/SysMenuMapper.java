package com.lzk.democoreserver.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.lzk.democoreserver.entity.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenu> {

    @Select("select DISTINCT d.id,d.`name`,d.permission,d.menu_type,d.sort,d.parent_id,d.path,d.icon,d.component,d.`status`" +
            " FROM user_role a LEFT JOIN role b on a.role_id = b.id LEFT JOIN sys_role_menu c ON b.id = c.role_id LEFT JOIN sys_menu d ON c.menu_id = d.id ${ew.CustomSqlSegment}")
    public List<SysMenu> getSysMenu(@Param(Constants.WRAPPER) Wrapper ew);
}
