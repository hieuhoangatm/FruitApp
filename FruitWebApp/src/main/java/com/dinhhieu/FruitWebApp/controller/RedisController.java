package com.dinhhieu.FruitWebApp.controller;

import com.dinhhieu.FruitWebApp.service.BaseRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/redis")
public class RedisController {
    private final BaseRedisService baseRedisService;

    @PostMapping("/abc")
    public void set(){
        baseRedisService.set("hello", "redis");
        baseRedisService.set("hello2","redis2");
        System.out.println("qwerty");
    }
}
