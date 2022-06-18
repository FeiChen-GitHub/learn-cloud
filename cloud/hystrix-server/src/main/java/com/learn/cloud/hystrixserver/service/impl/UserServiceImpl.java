package com.learn.cloud.hystrixserver.service.impl;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.learn.cloud.hystrixserver.service.UserService;
import com.learn.cloud.userservice.entity.User;
import com.learn.cloud.userservice.vo.CommonResult;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import netscape.javascript.JSObject;
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    private RestTemplate restTemplate;
    @Value("${service-url.user-service}")
    private String userServiceUrl;

    //    @HystrixCommand(fallbackMethod = "getDefaultUser", ignoreExceptions = IllegalStateException.class)
    @HystrixCommand(fallbackMethod = "getDefaultUser")
    @Override
    public CommonResult getUser(Long id) {
        try {
            return restTemplate.getForObject(userServiceUrl + "/user/{1}", CommonResult.class, id);
        } catch (Exception e) {
//            e.printStackTrace();
            throw e;
        }
    }

    @HystrixCommand(fallbackMethod = "getDefaultUser",
            commandKey = "getUserCommand",
            groupKey = "getUserGroup",
            threadPoolKey = "getUserThreadPool")
    @Override
    public CommonResult getUserCommand(Long id) {
        return restTemplate.getForObject(userServiceUrl + "/user/{1}", CommonResult.class, id);
    }

    @CacheResult(cacheKeyMethod = "genKey")
    @HystrixCommand(fallbackMethod = "getDefaultUser", commandKey = "idCache")
    @Override
    public CommonResult getUserCache(Long id) {
        logger.info("now: {}, id:{}", System.currentTimeMillis(), id);
        return restTemplate.getForObject(userServiceUrl + "/user/{1}", CommonResult.class, id);
    }

    @CacheRemove(cacheKeyMethod = "genKey", commandKey = "idCache")
    @HystrixCommand
    @Override
    public void deleteCache(Long id) {
        logger.info("REMOVE, id:{}", id);
    }

    @HystrixCollapser(batchMethod = "getUserByIds", collapserProperties = {
            @HystrixProperty(name = "timerDelayInMilliseconds", value = "700")
    })
    @Override
    public Future<User> getUserFuture(Long id) {
        return new AsyncResult<User>() {
            @Override
            public User invoke() {
                logger.info("getUserFuture. id:{}", id);
                CommonResult commonResult = restTemplate.getForObject(userServiceUrl + "/user/{1}", CommonResult.class, id);
                Object data = commonResult.getData();
                logger.info("Class: {}", data.getClass().getName());
                return null;
            }
        };
    }

    @HystrixCollapser(batchMethod = "getUserByIds", collapserProperties = {
            @HystrixProperty(name = "timerDelayInMilliseconds", value = "1000")
    })
    @Override
    public User getUserF(Long id) {
        logger.info("getUserF, id:{}", id);
        CommonResult commonResult = restTemplate.getForObject(userServiceUrl + "/user/{1}", CommonResult.class, id);
        Object data = commonResult.getData();
        logger.info("Class: {}", data.getClass().getName());
        return null;
    }

    @HystrixCommand
    public List<User> getUserByIds(List<Long> ids) {
        logger.info("getUserByIds, Ids: {}", ids);
        CommonResult commonResult = restTemplate.getForObject(userServiceUrl + "/user/getUserByIds?ids={1}", CommonResult.class, ids.stream().map(String::valueOf).collect(Collectors.joining(",")));
        logger.info("Result: {}", commonResult);
        List<Map> list = (List<Map>) commonResult.getData();
        logger.info("current time: {}", System.currentTimeMillis());
        return list.stream().map(m -> {
            User user = new User();
            user.setId(Long.valueOf("" + m.get("id")));
            user.setUsername((String) m.get("username"));
            return user;
        }).collect(Collectors.toList());
    }

    public String genKey(Long id) {
        logger.info("genKey, id:{}", id);
        return "" + id;
    }

    private CommonResult getDefaultUser(@PathVariable Long id) {
        User user = new User();
        user.setId(id * -1);
        user.setUsername("DEFAULT");
        return new CommonResult<>(user);
    }
}
