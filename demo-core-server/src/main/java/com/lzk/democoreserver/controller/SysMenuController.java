package com.lzk.democoreserver.controller;


import com.lzk.democommon.base.BaseParam;
import com.lzk.democommon.base.BaseResult;
import com.lzk.democommon.status.Status;
import com.lzk.democoreserver.entity.SysMenu;
import com.lzk.democoreserver.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/sysMenuService")
public class SysMenuController {
    @Resource
    private SysMenuService sysMenuService;

    /**
     * 查询列表
     */
    @PostMapping("/getMenuPage")
    public BaseResult getSysMenuPage(@RequestBody BaseParam param) {
        return sysMenuService.getSysMenuPage(param);
    }


    /**
     * 获取详细信息
     */
    @PostMapping("/getMenuOne")
    public BaseResult getSysMenuOne(@RequestBody BaseParam param) {
        return sysMenuService.getSysMenuOne(param);
    }

    /**
     * 新增
     */
    @Transactional
//    @Log(content = "【请填写功能名称】", type = Log.INSERT)
    @PostMapping("/addMenu")
    public BaseResult addSysMenu(@RequestBody SysMenu sysMenu) {
        return sysMenuService.addSysMenu(sysMenu);
    }

    /**
     * 更新
     */
    @Transactional
//    @Log(content = "【请填写功能名称】", type = Log.UPDATE)
    @PostMapping("/updateMenu")
    public BaseResult updateSysMenu(@RequestBody SysMenu sysMenu) {
        return sysMenuService.updateSysMenu(sysMenu);
    }

    /**
     * 删除
     */
    @Transactional
//    @Log(content = "【请填写功能名称】", type = Log.DELETE)
    @PostMapping("/delMenu")
    public BaseResult delSysMenu(@RequestBody BaseParam param) {
        return sysMenuService.delSysMenu(param);
    }

    /**
     * 根据父id获取子节点
     * @param param
     * @return
     */
    @PostMapping("/getChildMenu")
    public BaseResult getChildMenu(@RequestBody BaseParam param){
        return sysMenuService.getChildMenu(param);
    }

    /**
     * 获取全部菜单树
     * @param param
     * @return
     */
    @PostMapping("/getMenuTree")
    public BaseResult getSysMenuTree(@RequestBody BaseParam param){
        return sysMenuService.getSysMenuTree(param);
    }

    /**
     * 获取用户的菜单权限
     * @return
     */
    @PostMapping("/getMenuTreeByUser")
    public BaseResult getSysMenuTreeByUser(@RequestBody BaseParam param){
        return sysMenuService.getSysMenuTreeByUser(param);
    }
}
