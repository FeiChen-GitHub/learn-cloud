package com.learn.cloud.hystrixserver.service;

import com.learn.cloud.userservice.entity.User;
import com.learn.cloud.userservice.vo.CommonResult;

import java.util.concurrent.Future;

public interface UserService {
    CommonResult getUser(Long id);

    CommonResult getUserCommand(Long id);

    CommonResult getUserCache(Long id);

    void deleteCache(Long id);

    Future<User> getUserFuture(Long id);

    User getUserF(Long id);
}
