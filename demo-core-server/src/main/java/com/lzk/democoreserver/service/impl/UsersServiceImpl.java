package com.lzk.democoreserver.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzk.democommon.base.BaseResult;
import com.lzk.democommon.constant.Constants;
import com.lzk.democommon.exception.ServiceException;
import com.lzk.democommon.status.Status;
import com.lzk.democommon.utils.AesUtil;
import com.lzk.democommon.utils.JwtUtil;
import com.lzk.democommon.utils.TimeHelper;
import com.lzk.democoreserver.controller.vo.LoginVO;
import com.lzk.democoreserver.entity.Role;
import com.lzk.democoreserver.entity.Users;
import com.lzk.democoreserver.mapper.UsersMapper;
import com.lzk.democoreserver.service.RoleService;
import com.lzk.democoreserver.service.SysMenuService;
import com.lzk.democoreserver.service.UsersService;
import com.lzk.demosecurity.config.SecurityProperties;
import com.lzk.demosecurity.core.SecurityUserInfo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper,Users> implements UsersService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource
    private RoleService roleService;

    @Resource
    private SysMenuService sysMenuService;

    @Resource
    private SecurityProperties securityProperties;

    @Override
    public SecurityUserInfo verifyTokenAndRefresh(String token) {
        String verifyToken = JwtUtil.verifyToken(token);
        SecurityUserInfo securityUserInfo = null;
        if (verifyToken!=null) {
            String s = (String)redisTemplate.opsForValue().get(verifyToken);
            if(s!=null) {
                securityUserInfo = JSONObject.parseObject(s,SecurityUserInfo.class);
                securityUserInfo.setUserInfo(((JSONObject)securityUserInfo.getUserInfo()).toJavaObject(Users.class));
                if((TimeHelper.getCurrentTime10() - securityUserInfo.getLoginTime()) > 5400) redisTemplate.expire(token,120L, TimeUnit.MINUTES);
            }
        }
        return securityUserInfo;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(token);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = getOne(Wrappers.lambdaQuery(Users.class).eq(Users::getLoginName,username));
        if(users==null) throw new ServiceException(Status.LOGIN_FAIL_CAUSE_PWD);
        List<Role> roles = roleService.getRoleUser(users.getId());
        List<Long> roleIds = new ArrayList<>();
        List<String> roleName = new ArrayList<>();
        roles.forEach(v->{
            roleIds.add(v.getId());
            roleName.add(v.getName());
        });
        return new SecurityUserInfo(users,sysMenuService.getPerms(users.getId()),roleName,roleIds);
    }

    @Override
    public BaseResult loginByPassword(LoginVO loginVO) {
        Object realCode = redisTemplate.opsForValue().get(Constants.CAPTCHA_CODE_KEY.getName() + loginVO.getKey());
        if(realCode==null) return new BaseResult(Status.LOGIN_FAIL_CAUSE_CODE_INVALID);
        if(!realCode.toString().equalsIgnoreCase(loginVO.getCode())) return new BaseResult(Status.LOGINFAILCAUSECODE);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginVO.getLoginName(), loginVO.getPassword()));
        SecurityUserInfo securityUserInfo = (SecurityUserInfo)authentication.getPrincipal();
        Users users = (Users) securityUserInfo.getUserInfo();
        if(!users.getPassword().equals(AesUtil.enCode(loginVO.getPassword()))) return new BaseResult(Status.LOGIN_FAIL_CAUSE_PWD);
        users.setPassword(null);
        String token = AesUtil.enCode(users.getId() + TimeHelper.getCurrentTime10Str());
        String jwtToken = JwtUtil.createToken(token);
        securityUserInfo.setToken(jwtToken);
        securityUserInfo.setLoginTime(TimeHelper.getCurrentTime10());
        redisTemplate.opsForValue().set(token, JSON.toJSONString(securityUserInfo), securityProperties.getTimeOut() ,TimeUnit.MINUTES);
        return BaseResult.success(jwtToken);
    }
}
