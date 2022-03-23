package com.imooc.controller;

import com.imooc.grace.result.GraceJSONResult;
import com.imooc.utils.SMSUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "HelloController 测试的接口")
@RestController
public class HelloController {
    @GetMapping("hello")
    @ApiOperation(value = "hello方法")
    public Object hello() throws Exception {
        return GraceJSONResult.ok();
    }

    @GetMapping("user")
    public Object getUser(){
        return "name: tyson, age: 25";
    }

    @GetMapping("user/name")
    public Object getUserName(){
        return "name: tyson";
    }

    @Autowired
    private SMSUtils smsUtils;

//    @GetMapping("sms")
//    @ApiOperation(value = "短信发送接口")
//    public Object sms() throws Exception {
//        smsUtils.sendSMS("13537175943","961217");
//        return GraceJSONResult.ok();
//    }
}
