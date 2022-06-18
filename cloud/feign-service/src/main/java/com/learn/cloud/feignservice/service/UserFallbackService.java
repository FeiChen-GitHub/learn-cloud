package com.learn.cloud.feignservice.service;

import com.learn.cloud.userservice.entity.User;
import com.learn.cloud.userservice.vo.CommonResult;
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
        return new CommonResult(defaultUser);
    }

    @Override
    public CommonResult<User> getUser(Long id) {
        logger.info("******Method: getUser");
        User defaultUser = new User();
        defaultUser.setId(-1L);
        defaultUser.setUsername("DEFAULT-getUser");
        return new CommonResult(defaultUser);
    }

    @Override
    public CommonResult<List<User>> getUserByIds(List<Long> ids) {
        logger.info("******Method: getUserByIds");
        return new CommonResult<>(new ArrayList<>());
    }

    @Override
    public CommonResult<User> getByUsername(String username) {
        logger.info("******Method: getByUsername");
        User defaultUser = new User();
        defaultUser.setId(-1L);
        defaultUser.setUsername("DEFAULT-getByUsername");
        return new CommonResult<>();
    }

    @Override
    public CommonResult update(User user) {
        return new CommonResult("update success", 100);
    }

    @Override
    public CommonResult delete(Long id) {
        return new CommonResult("delete success", 100);
    }
}
