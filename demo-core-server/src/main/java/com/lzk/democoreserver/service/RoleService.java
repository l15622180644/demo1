package com.lzk.democoreserver.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lzk.democommon.base.BaseParam;
import com.lzk.democommon.base.BaseResult;
import com.lzk.democoreserver.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {

    BaseResult getRolePage(BaseParam param);

    BaseResult getRoleOne(BaseParam param);

    BaseResult addRole(Role role);

    BaseResult updateRole(Role role);

    BaseResult delRole(BaseParam param);

    List<Role> getRoleUser(Long userId);

}
