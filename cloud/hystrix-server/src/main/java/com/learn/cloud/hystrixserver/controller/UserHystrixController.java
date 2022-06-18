package com.learn.cloud.hystrixserver.controller;

import com.learn.cloud.hystrixserver.service.UserService;
import com.learn.cloud.userservice.entity.User;
import com.learn.cloud.userservice.vo.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserHystrixController {

    private final Logger logger = LoggerFactory.getLogger(UserHystrixController.class);
    @Resource
    private UserService userService;

    @GetMapping("/testFallback/{id}")
    public CommonResult testFallback(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/testCommand/{id}")
    public CommonResult testCommand(@PathVariable Long id) {
        return userService.getUserCommand(id);
    }

    @GetMapping("/testCache/{id}")
    public CommonResult testCache(@PathVariable Long id) {
        userService.getUserCache(id);
        userService.getUserCache(id);
        userService.getUserCache(id);
        return new CommonResult("操作成功", 200);
    }

    @GetMapping("/deleteCache/{id}")
    public CommonResult deleteCache(@PathVariable Long id) {
        userService.getUserCache(id);
        userService.getUserCache(id);
        userService.deleteCache(id);
        userService.getUserCache(id);
        return new CommonResult("操作成功", 200);
    }

    @GetMapping("/testCollapser")
    public CommonResult testCollapser() throws ExecutionException, InterruptedException {
        long a = System.currentTimeMillis();
        Future<User> future1 = userService.getUserFuture(1L);
        long b = System.currentTimeMillis();
        Future<User> future2 = userService.getUserFuture(2L);
        User u1 = future1.get();
        long f = System.currentTimeMillis();
        User u2 = future2.get();
        long c = System.currentTimeMillis();
        TimeUnit.MILLISECONDS.sleep(200);
        long d = System.currentTimeMillis();
        Future<User> future3 = userService.getUserFuture(3L);
        User u3 = future3.get();
        long e = System.currentTimeMillis();
        System.out.println("testCollapser====" + u1 + "\t" + u2 + "\t" + u3);
        logger.info("b-a={}, f-b={}, c-f={}, e-d={}, ", b - a, f - b, c - f, e - d);
        return new CommonResult("操作成功", 200);
    }

    @GetMapping("/testCollapser2")
    public CommonResult testCollapser2() throws InterruptedException {
        long a = System.currentTimeMillis();
        User u1 = userService.getUserF(1L);
        long b = System.currentTimeMillis();
        User u2 = userService.getUserF(2L);
        long c = System.currentTimeMillis();
        TimeUnit.MILLISECONDS.sleep(200);
        long d = System.currentTimeMillis();
        User u3 = userService.getUserF(3L);
        long e = System.currentTimeMillis();
        logger.info("b-a={}, c-b={}, e-d={}", b - a, c - b, e - d);
        System.out.println("testCollapser2====" + u1 + "\t" + u2 + "\t" + u3);
        return new CommonResult("操作成功", 200);
    }

    @GetMapping("/testCollapser3")
    public CommonResult testCollapser3() throws InterruptedException {
        long a = System.currentTimeMillis();
        userService.getUserF(1L);
        userService.getUserF(2L);
        TimeUnit.MILLISECONDS.sleep(200);
        userService.getUserF(3L);
        long b = System.currentTimeMillis();
        logger.info("Times: {}", b - a);
        return new CommonResult("操作成功", 200);
    }

    @GetMapping("/testCollapser4")
    public CommonResult testCollapser4() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            userService.getUserFuture(i + 0L);
            TimeUnit.MILLISECONDS.sleep(500);
        }
        return new CommonResult("操作成功", 200);
    }
}
