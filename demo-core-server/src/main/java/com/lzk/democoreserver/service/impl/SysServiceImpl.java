package com.lzk.democoreserver.service.impl;

import com.lzk.democommon.base.BaseResult;
import com.lzk.democommon.constant.Constants;
import com.lzk.democommon.status.Status;
import com.lzk.democommon.utils.Base64Utils;
import com.lzk.democommon.utils.CaptchaUtils;
import com.lzk.democommon.utils.RandomCodeUtils;
import com.lzk.democommon.utils.UuidUtil;
import com.lzk.democoreserver.service.SysService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author
 * @module
 * @date 2022/1/14 17:27
 */

@Service
public class SysServiceImpl implements SysService {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public BaseResult captcha() {
        String code = RandomCodeUtils.getRandomCode(4);
        String uuid = UuidUtil.get32UUID();
        redisTemplate.opsForValue().set(Constants.CAPTCHA_CODE_KEY.getName() + uuid,code,3L, TimeUnit.MINUTES);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()){
            BufferedImage image = CaptchaUtils.create(200, 100, code);
            ImageIO.write(image,"jpg",out);
            Map<String,Object> map = new HashMap<>();
            map.put("key",uuid);
            map.put("img", Base64Utils.encode(out.toByteArray()));
            return BaseResult.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            redisTemplate.delete(Constants.CAPTCHA_CODE_KEY.getName() + uuid);
        }
        return BaseResult.error(Status.EXCEPTION);
    }
}
