package com.learn.cloud.sentinelservice.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.learn.cloud.nacosuserservice.entity.User;
import com.learn.cloud.nacosuserservice.vo.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/breaker")
public class CircleBreakerController {
    private Logger logger = LoggerFactory.getLogger(CircleBreakerController.class);

    @Autowired
    private RestTemplate restTemplate;
    @Value("${service-url.user-service}")
    private String userServiceUrl;

    @RequestMapping("/fallback/{id}")
    @SentinelResource(value = "fallback", fallback = "handleFallback")
    public CommonResult fallback(@PathVariable Long id) {
        return restTemplate.getForObject(userServiceUrl + "/user/{1}", CommonResult.class, id);
    }

    @RequestMapping("/fallbackException/{id}")
    @SentinelResource(value = "fallbackException", fallback = "handleFallback2", exceptionsToIgnore = {NullPointerException.class})
    public CommonResult fallbackException(@PathVariable Long id) {
        if (id == 1) {
            throw new IndexOutOfBoundsException();
        } else if (id == 2) {
            throw new NullPointerException();
        }
        return restTemplate.getForObject(userServiceUrl + "/user/{1}", CommonResult.class, id);
    }

    public CommonResult handleFallback(Long id) {
        User defaultUser = new User();
        defaultUser.setId(-1L);
        defaultUser.setUsername("defaultUser");
        CommonResult commonResult = new CommonResult(defaultUser);
        return commonResult;
    }

    public CommonResult handleFallback2(@PathVariable Long id, Throwable e) {
        logger.error("handleFallback2 id:{},throwable class:{}", id, e.getClass());
        User defaultUser = new User();
        defaultUser.setId(-2L);
        defaultUser.setUsername("defaultUser-2");
        CommonResult commonResult = new CommonResult(defaultUser);
        return commonResult;
    }


}
