package com.lzk.democoreserver.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.lzk.democoreserver.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {

    @Select("SELECT b.* FROM user_role a LEFT JOIN role b ON a.role_id = b.id ${ew.CustomSqlSegment}")
    public List<Role> getRoleUser(@Param(Constants.WRAPPER) Wrapper ew);
}
