package com.lzk.democoreserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzk.democommon.base.BaseResult;
import com.lzk.democoreserver.controller.vo.LoginVO;
import com.lzk.democoreserver.entity.Users;
import com.lzk.demosecurity.core.service.SecurityService;

public interface UsersService extends SecurityService, IService<Users> {

    BaseResult loginByPassword(LoginVO loginVO);
}
