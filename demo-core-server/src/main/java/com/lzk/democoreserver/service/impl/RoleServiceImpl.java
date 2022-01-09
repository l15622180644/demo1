package com.lzk.democoreserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzk.democoreserver.entity.Role;
import com.lzk.democoreserver.mapper.RoleMapper;
import com.lzk.democoreserver.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
}
