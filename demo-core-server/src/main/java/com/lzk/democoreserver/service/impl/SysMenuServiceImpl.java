package com.lzk.democoreserver.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lzk.democommon.base.BaseParam;
import com.lzk.democommon.base.BaseResult;
import com.lzk.democommon.status.Status;
import com.lzk.democoreserver.entity.SysMenu;
import com.lzk.democoreserver.mapper.SysMenuMapper;
import com.lzk.democoreserver.service.SysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;


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
        return BaseResult.success(sysMenuMapper.deleteById(param.getId()));
    }

    @Override
    public BaseResult getChildMenu(BaseParam param) {
        if (param.getId() == null) return BaseResult.error(Status.PARAMEXCEPTION);
        return BaseResult.success(getChildMenu(param.getId(),param.getStatus(),param.getStrType()));
    }

    @Override
    public BaseResult getSysMenuTree(BaseParam param) {

    }

    private List<SysMenu> getChildMenu(Long id,Integer status,String strType) {
        List<SysMenu> list = list(Wrappers.lambdaQuery(SysMenu.class)
                .eq(SysMenu::getParentId, id)
                .eq(status!=null,SysMenu::getStatus, status)
                .eq(StringUtils.isNotBlank(strType),SysMenu::getMenuType,strType)
                .orderByAsc(SysMenu::getSort));
        for(SysMenu menu : list){
            List<SysMenu> childMenu = getChildMenu(menu.getId(),status,strType);
            menu.setChildren(childMenu);
        }
        return list;
    }

}
