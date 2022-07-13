package com.learn.cloud.sentinelservice.service;

import com.learn.cloud.nacosuserservice.entity.User;
import com.learn.cloud.nacosuserservice.vo.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserFallbackService implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserFallbackService.class);

    @Override
    public CommonResult create(User user) {
        logger.info("******Method: create");
        User defaultUser = new User();
        defaultUser.setId(-1L);
        defaultUser.setUsername("DEFAULT-create");
        CommonResult commonResult = new CommonResult(defaultUser);
        commonResult.setMsg("服务降级返回");
        return commonResult;
    }

    @Override
    public CommonResult<User> getUser(Long id) {
        logger.info("******Method: getUser");
        User defaultUser = new User();
        defaultUser.setId(-1L);
        defaultUser.setUsername("DEFAULT-getUser");
        CommonResult commonResult = new CommonResult(defaultUser);
        commonResult.setMsg("服务降级返回");
        return commonResult;
    }

    @Override
    public CommonResult<List<User>> getUserByIds(List<Long> ids) {
        logger.info("******Method: getUserByIds");
        CommonResult commonResult = new CommonResult(new ArrayList<>());
        commonResult.setMsg("服务降级返回");
        return commonResult;
    }

    @Override
    public CommonResult<User> getByUsername(String username) {
        logger.info("******Method: getByUsername");
        User defaultUser = new User();
        defaultUser.setId(-1L);
        defaultUser.setUsername("DEFAULT-getByUsername");
        CommonResult commonResult = new CommonResult(defaultUser);
        commonResult.setMsg("服务降级返回");
        return commonResult;
    }

    @Override
    public CommonResult update(User user) {
        return new CommonResult("update success. 服务降级返回", 100);
    }

    @Override
    public CommonResult delete(Long id) {
        return new CommonResult("delete success. 服务降级返回", 100);
    }
}
