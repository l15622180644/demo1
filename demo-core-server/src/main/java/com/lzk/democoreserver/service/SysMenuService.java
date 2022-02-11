package com.lzk.democoreserver.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lzk.democommon.base.BaseParam;
import com.lzk.democommon.base.BaseResult;
import com.lzk.democoreserver.entity.SysMenu;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {

    BaseResult getSysMenuPage(BaseParam param);

    BaseResult getSysMenuOne(BaseParam param);

    BaseResult addSysMenu(SysMenu sysMenu);

    BaseResult updateSysMenu(SysMenu sysMenu);

    BaseResult delSysMenu(BaseParam param);

    BaseResult getChildMenu(BaseParam param);

    BaseResult getSysMenuTree(BaseParam param);

    BaseResult getSysMenuTreeByUser(BaseParam param);

    List<String> getPerms(Long userId);

}
