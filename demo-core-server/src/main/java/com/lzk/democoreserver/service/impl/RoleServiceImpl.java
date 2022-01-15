package com.lzk.democoreserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lzk.democommon.base.BaseParam;
import com.lzk.democommon.base.BaseResult;
import com.lzk.democommon.status.Status;
import com.lzk.democoreserver.entity.Role;
import com.lzk.democoreserver.mapper.RoleMapper;
import com.lzk.democoreserver.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public BaseResult getRolePage(BaseParam param) {
        return BaseResult.success(page(param.getPAGE(entityClass)));
    }

    @Override
    public BaseResult getRoleOne(BaseParam param) {
        if (param.getId() == null) return BaseResult.error(Status.PARAMEXCEPTION);
        return BaseResult.success(getById(param.getId()));
    }

    @Override
    public BaseResult addRole(Role role) {
        return BaseResult.success(roleMapper.insert(role));
    }

    @Override
    public BaseResult updateRole(Role role) {
        if (role.getId() == null) return BaseResult.error(Status.PARAMEXCEPTION);
        return BaseResult.success(roleMapper.updateById(role));
    }

    @Override
    public BaseResult delRole(BaseParam param) {
        if (param.getId() == null) return BaseResult.error(Status.PARAMEXCEPTION);
        return BaseResult.success(roleMapper.deleteById(param.getId()));
    }
}
