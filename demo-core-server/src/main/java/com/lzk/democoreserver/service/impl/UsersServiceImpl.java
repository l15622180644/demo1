package com.lzk.democoreserver.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzk.democommon.base.BaseResult;
import com.lzk.democommon.exception.ServiceException;
import com.lzk.democommon.status.Status;
import com.lzk.democommon.utils.AesUtil;
import com.lzk.democommon.utils.JwtUtil;
import com.lzk.democommon.utils.TimeHelper;
import com.lzk.democoreserver.controller.vo.LoginVO;
import com.lzk.democoreserver.entity.Users;
import com.lzk.democoreserver.mapper.UsersMapper;
import com.lzk.democoreserver.service.UsersService;
import com.lzk.demosecurity.core.SecurityUserInfo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper,Users> implements UsersService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public SecurityUserInfo verifyTokenAndRefresh(String token) {
        String s = (String)redisTemplate.opsForValue().get(JwtUtil.verifyToken(token));
        SecurityUserInfo securityUserInfo = null;
        if(s!=null) {
            securityUserInfo = JSONObject.parseObject(s,SecurityUserInfo.class);
            if((TimeHelper.getCurrentTime10() - securityUserInfo.getLoginTime()) > 5400) redisTemplate.expire(token,120L, TimeUnit.MINUTES);
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
        return new SecurityUserInfo(users,null);
    }

    @Override
    public BaseResult loginByPassword(LoginVO loginVO) {
        Object realCode = redisTemplate.opsForValue().get(loginVO.getUuid());
        if(realCode==null) return new BaseResult(Status.LOGIN_FAIL_CAUSE_CODE_INVALID);
        if(!realCode.toString().equalsIgnoreCase(loginVO.getCode())) return new BaseResult(Status.LOGINFAILCAUSECODE);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginVO.getUserName(), loginVO.getPassword()));
        SecurityUserInfo securityUserInfo = (SecurityUserInfo)authentication.getPrincipal();
        Users users = (Users) securityUserInfo.getUserInfo();
        if(!users.getPassword().equals(AesUtil.enCode(loginVO.getPassword()))) return new BaseResult(Status.LOGIN_FAIL_CAUSE_PWD);
        String token = AesUtil.enCode(users.getId() + TimeHelper.getCurrentTime10Str());
        String jwtToken = JwtUtil.createToken(token);
        securityUserInfo.setToken(jwtToken);
        securityUserInfo.setLoginTime(TimeHelper.getCurrentTime10());
        redisTemplate.opsForValue().set(token, JSON.toJSONString(securityUserInfo), 120 ,TimeUnit.MINUTES);
        return BaseResult.success(jwtToken);
    }
}
