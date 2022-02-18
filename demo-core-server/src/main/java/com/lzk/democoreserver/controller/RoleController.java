package com.lzk.democoreserver.controller;


import com.lzk.democommon.base.BaseParam;
import com.lzk.democommon.base.BaseResult;
import com.lzk.democoreserver.entity.Role;
import com.lzk.democoreserver.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * 【角色】Controller
 */
@RestController
@RequestMapping("/roleService")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 查询【】列表
     */
    @PostMapping("/getRolePage")
    public BaseResult getRolePage(@RequestBody BaseParam param) {
        return roleService.getRolePage(param);
    }


    /**
     * 获取【】详细信息
     */
    @PostMapping("/getRoleOne")
    public BaseResult getRoleOne(@RequestBody BaseParam param) {
        return roleService.getRoleOne(param);
    }

    /**
     * 新增【】
     */
    @Transactional
//    @Log(content = "【请填写功能名称】", type = Log.INSERT)
    @PostMapping("/addRole")
    public BaseResult addRole(@RequestBody Role role) {
        return roleService.addRole(role);
    }

    /**
     * 更新【】
     */
    @Transactional
//    @Log(content = "【请填写功能名称】", type = Log.UPDATE)
    @PostMapping("/updateRole")
    public BaseResult updateRole(@RequestBody Role role) {
        return roleService.updateRole(role);
    }

    /**
     * 删除【】
     */
    @Transactional
//    @Log(content = "【请填写功能名称】", type = Log.DELETE)
    @PostMapping("/delRole")
    public BaseResult delRole(@RequestBody BaseParam param) {
        return roleService.delRole(param);
    }

    /**
     * 修改角色权限
     * @param role
     * @return
     */
    @Transactional
    @PostMapping("/updateRolePrivilege")
    public BaseResult updateRolePrivilege(@RequestBody Role role){
        return roleService.updateRolePrivilege(role);
    }
}
