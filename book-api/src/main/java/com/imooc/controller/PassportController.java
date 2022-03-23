package com.imooc.controller;

import com.imooc.bo.RegistLoginBo;
import com.imooc.grace.result.GraceJSONResult;
import com.imooc.utils.IPUtil;
import com.imooc.utils.SMSUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
@Api(tags = "PassportController 通行证接口模块")
@RequestMapping("passport")
public class PassportController extends BaseInfoProperties {

    private SMSUtils smsUtils;

    @Autowired
    public void setSMSUtils(SMSUtils smsUtils){
        this.smsUtils = smsUtils;
    }

    @PostMapping("getSMSCode")
    @ApiOperation(value = "sms 短信发送")
    public Object sms(@RequestParam String mobile, HttpServletRequest request) throws Exception {

        if (StringUtils.isBlank(mobile)){
            return GraceJSONResult.ok();
        }

        // 获取客户IP，
        String userIp = IPUtil.getRequestIp(request);
        // 限制同一IP一分钟内只能请求一次短信
        redis.setnx60s(MOBILE_SMSCODE + ":" + userIp, userIp);

        String code = (int)((Math.random() * 9 + 1) * 100000) + "";
        log.info(code);

        smsUtils.sendSMS(mobile, code);

        // 验证码放入缓存，30分钟失效
        redis.set(MOBILE_SMSCODE + ":" + mobile, code, 30 * 60);

        return GraceJSONResult.ok("发送短信成功");
    }

    @PostMapping("login")
    @ApiOperation(value = "login 登陆注册")
    public Object login(@Valid @RequestBody RegistLoginBo registLoginBo, BindingResult result, HttpServletRequest request){
        if (result.hasErrors()){
            Map<String,String> error = this.getErrors(result);
            return GraceJSONResult.errorMap(error);
        }
        return GraceJSONResult.ok();
    }

}
