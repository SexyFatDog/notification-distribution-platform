package com.logan.ndp.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *健康检测，测试服务正常运行
 */
@RestController
@Api("健康检测")
public class HealthController {

    @GetMapping("/")
    @ApiOperation("/健康检测")
    public String healthObserve() {return "success";}
}
