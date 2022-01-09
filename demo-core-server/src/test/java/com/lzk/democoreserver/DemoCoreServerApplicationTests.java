package com.lzk.democoreserver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzk.democoreserver.entity.Users;
import com.lzk.demosecurity.core.SecurityUserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//@SpringBootTest
class DemoCoreServerApplicationTests {

    @Test
    void test1() {
        Users users = new Users();
        users.setId(1L);
        users.setLoginName("lzk");
        users.setPassword("6868686");
        SecurityUserInfo info = new SecurityUserInfo(JSONObject.parseObject(JSON.toJSONString(users)),null);
        info.setToken("uuuuuuu");
        info.setPermissions(new ArrayList<>());
        SecurityUserInfo parseObject = JSONObject.parseObject(JSON.toJSONString(info),SecurityUserInfo.class);
        int a = 1;
    }


}
