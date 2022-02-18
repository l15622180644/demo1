package com.lzk.democoreserver.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lzk.democommon.base.BaseParam;
import com.lzk.democommon.base.BaseResult;
import com.lzk.democommon.status.Status;
import com.lzk.democommon.utils.TreeUtils;
import com.lzk.democoreserver.entity.SysMenu;
import com.lzk.democoreserver.entity.SysRoleMenu;
import com.lzk.democoreserver.entity.Users;
import com.lzk.democoreserver.mapper.SysMenuMapper;
import com.lzk.democoreserver.mapper.SysRoleMenuMapper;
import com.lzk.democoreserver.service.SysMenuService;
import com.lzk.demosecurity.core.SecurityUserInfo;
import com.lzk.demosecurity.util.MySecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Resource
    private SysRoleMenuMapper roleMenuMapper;


    @Override
    public BaseResult getSysMenuPage(BaseParam param) {
        return BaseResult.success(page(param.getPAGE(entityClass)));
    }

    @Override
    public BaseResult getSysMenuOne(BaseParam param) {
        if (param.getId() == null) return BaseResult.error(Status.PARAMEXCEPTION);
        return BaseResult.success(getById(param.getId()));
    }

    @Override
    public BaseResult addSysMenu(SysMenu sysMenu) {
        return BaseResult.success(sysMenuMapper.insert(sysMenu));
    }

    @Override
    public BaseResult updateSysMenu(SysMenu sysMenu) {
        if (sysMenu.getId() == null) return BaseResult.error(Status.PARAMEXCEPTION);
        return BaseResult.success(sysMenuMapper.updateById(sysMenu));
    }

    @Override
    public BaseResult delSysMenu(BaseParam param) {
        if (param.getId() == null) return BaseResult.error(Status.PARAMEXCEPTION);
        if(checkMenuExistRole(param.getId())) return new BaseResult(Status.OPFAIL,null,"删除失败，原因存在角色绑定");
        if(haveChild(param.getId())) return new BaseResult(Status.OPFAIL,null,"删除失败，原因存在子节点");
//        roleMenuMapper.delete(Wrappers.lambdaQuery(SysRoleMenu.class).eq(SysRoleMenu::getMenuId,param.getId()));
        return BaseResult.success(sysMenuMapper.deleteById(param.getId()));
    }

    @Override
    public BaseResult getChildMenu(BaseParam param) {
        if (param.getId() == null) return BaseResult.error(Status.PARAMEXCEPTION);
        return BaseResult.success(getChildMenu(param.getId(),param.getStatus(),param.getType()));
    }

    @Override
    public BaseResult getSysMenuTree(BaseParam param) {
        List<SysMenu> list = list(Wrappers.lambdaQuery(entityClass)
                .eq(SysMenu::getStatus,0)
                .eq(param.getType()!=null,SysMenu::getMenuType,param.getType()));
        return BaseResult.success(TreeUtils.listToTree(JSONArray.toJSONString(list),"parentId","id","children","sort",0));
    }

    @Override
    public BaseResult getSysMenuTreeByUser(BaseParam param) {
        SecurityUserInfo user = MySecurityUtil.getUser();
        Users userInfo = (Users)user.getUserInfo();
        if(user.getRoleIds().contains(1L)) return getSysMenuTree(param);
        List<SysMenu> menu = sysMenuMapper.getSysMenu(Wrappers.query()
                .eq("a.user_id", userInfo.getId())
                .eq("b.status", 0)
                .eq("d.status", 0)
                .eq(StringUtils.isNotBlank(param.getStrType()),"d.menu_type",param.getStrType())
        );
        return BaseResult.success(TreeUtils.listToTree(JSONArray.toJSONString(menu),"parentId","id","children","sort",0));
    }

    @Override
    public List<String> getPerms(Long userId) {
        List<SysMenu> menu = sysMenuMapper.getSysMenu(Wrappers.query()
                .eq("a.user_id", userId)
                .eq("b.status", 0)
                .eq("d.status", 0));
        return menu.stream().map(SysMenu::getPermission).collect(Collectors.toList());
    }

    private List<SysMenu> getChildMenu(Long id,Integer status,Integer type) {
        List<SysMenu> list = list(Wrappers.lambdaQuery(SysMenu.class)
                .eq(SysMenu::getParentId, id)
                .eq(status!=null,SysMenu::getStatus, status)
                .eq(type!=null,SysMenu::getMenuType,type)
                .orderByAsc(SysMenu::getSort));
        for(SysMenu menu : list){
            List<SysMenu> childMenu = getChildMenu(menu.getId(),status,type);
            menu.setChildren(childMenu);
        }
        return list;
    }

    //是否存在子节点
    private boolean haveChild(Long id){
        return count(Wrappers.lambdaQuery(SysMenu.class).eq(SysMenu::getParentId,id)) > 0;
    }

    //是否存在角色绑定
    private boolean checkMenuExistRole(Long id){
        return roleMenuMapper.selectCount(Wrappers.lambdaQuery(SysRoleMenu.class).eq(SysRoleMenu::getMenuId,id)) > 0;
    }

}
