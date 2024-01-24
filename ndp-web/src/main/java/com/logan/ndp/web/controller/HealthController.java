package com.logan.ndp.web.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *健康检测，测试服务正常运行
 */
@RestController
@Tag(name = "health observation")
public class HealthController {

    @GetMapping("/")
    @Operation(method = "get", summary = "health observation")
    public String healthObserve() {return "success";}
}
