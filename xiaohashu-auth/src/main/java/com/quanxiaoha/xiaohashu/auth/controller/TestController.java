package com.quanxiaoha.xiaohashu.auth.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.quanxiaoha.framework.biz.operationlog.aspect.ApiOperationLog;
import com.quanxiaoha.framework.common.response.Response;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
public class TestController {

    @GetMapping("/test")
    @ApiOperationLog(description = "测试接口")
    public Response<String> test() {
        return Response.success("Hello, 犬小哈专栏");
    }

    @PostMapping("/test2")
    @ApiOperationLog(description = "测试接口2")
    public Response<User> test2(@RequestBody @Validated User user) {
        return Response.success(user);
    }

    //测试登录
    @RequestMapping("/user/doLogin")
    public String doLogin(String username, String password) {
        if ("zhan".equals(username)&&"123456".equals(password)){
            StpUtil.login(10001);
            return "登录成功";
        }
        return "登录失败";
    }

    @RequestMapping("/user/isLogin")
    public String isLogin(){
        return "当前登录状态："+StpUtil.isLogin();
    }

}
