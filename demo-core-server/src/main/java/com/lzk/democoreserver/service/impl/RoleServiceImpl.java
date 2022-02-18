package com.lzk.democoreserver.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lzk.democommon.base.BaseParam;
import com.lzk.democommon.base.BaseResult;
import com.lzk.democommon.exception.ServiceException;
import com.lzk.democommon.status.Status;
import com.lzk.democoreserver.entity.Role;
import com.lzk.democoreserver.entity.SysRoleMenu;
import com.lzk.democoreserver.entity.UserRole;
import com.lzk.democoreserver.entity.Users;
import com.lzk.democoreserver.mapper.RoleMapper;
import com.lzk.democoreserver.mapper.SysRoleMenuMapper;
import com.lzk.democoreserver.mapper.UserRoleMapper;
import com.lzk.democoreserver.mapper.UsersMapper;
import com.lzk.democoreserver.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private SysRoleMenuMapper roleMenuMapper;

    @Resource
    private UsersMapper usersMapper;


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
        List<Long> menuIds = role.getMenuIds();
        if(menuIds!=null&&!menuIds.isEmpty()){
            menuIds.forEach(v->{
                SysRoleMenu roleMenu = new SysRoleMenu(role.getId(),v);
                roleMenuMapper.insert(roleMenu);
            });
        }
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
        if(haveUserByRole(param.getId())) return new BaseResult(Status.OPFAIL,null,"删除失败，原因存在绑定用户");
        roleMenuMapper.delete(Wrappers.lambdaQuery(SysRoleMenu.class).eq(SysRoleMenu::getRoleId,param.getId()));
        return BaseResult.success(roleMapper.deleteById(param.getId()));
    }

    //获取用户角色
    @Override
    public List<Role> getRoleUser(Long userId) {
        return roleMapper.getRoleUser(Wrappers.query()
                .eq("a.user_id", userId)
                .eq("b.status", 0));
    }

    @Override
    public BaseResult updateRolePrivilege(Role role) {
        if (role.getId() == null) return BaseResult.error(Status.PARAMEXCEPTION);
        List<Long> menuIds = role.getMenuIds();
        if(roleMenuMapper.delete(Wrappers.lambdaQuery(SysRoleMenu.class).eq(SysRoleMenu::getRoleId,role.getId()))!=1)
            throw new ServiceException(Status.OPFAIL);
        if(menuIds!=null&&!menuIds.isEmpty()){
            menuIds.forEach(v->{
                SysRoleMenu roleMenu = new SysRoleMenu(role.getId(),v);
                if(roleMenuMapper.insert(roleMenu)!=1) throw new ServiceException(Status.OPFAIL);
            });
        }
        return BaseResult.success();
    }

    //是否存在用户角色绑定
    private boolean haveUserByRole(Long roleId){
        List<UserRole> userRoles = userRoleMapper.selectList(Wrappers.lambdaQuery(UserRole.class).eq(UserRole::getRoleId, roleId));
        List<Long> userIds = userRoles.stream().map(UserRole::getUserId).collect(Collectors.toList());
        if(userIds.isEmpty()){
            return false;
        }
        return usersMapper.selectCount(Wrappers.lambdaQuery(Users.class).in(Users::getId,userIds).eq(Users::getIdDel,0))>0;
    }
}
