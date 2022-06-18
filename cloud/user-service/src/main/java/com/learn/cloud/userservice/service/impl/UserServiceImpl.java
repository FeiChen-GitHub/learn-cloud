package com.learn.cloud.userservice.service.impl;

import com.learn.cloud.userservice.entity.User;
import com.learn.cloud.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${server.port:7999}")
    private Integer port;

    @Override
    public void create(User user) {
        logger.info("......Method: {}, port: {}", "create", port);
    }

    @Override
    public User getUser(Long id) {
        logger.info("......Method: {}, port: {}", "getUser", port);
        User user = new User();
        user.setId(id);
        user.setUsername(port + "-" + System.currentTimeMillis());
        return user;
    }

    @Override
    public List<User> getUserByIds(List<Long> ids) {
        logger.info("......Method: {}, port: {}", "getUserByIds", port);
        return ids.stream().map(id -> {
            User user = new User();
            user.setId(id);
            user.setUsername("" + System.currentTimeMillis());
            return user;
        }).collect(Collectors.toList());
    }

    @Override
    public User getByUsername(String username) {
        logger.info("......Method: {}, port: {}", "getByUsername", port);
        return null;
    }

    @Override
    public void update(User user) {
        logger.info("......Method: {}, port: {}", "update", port);

    }

    @Override
    public void delete(Long id) {
        logger.info("......Method: {}, port: {}", "delete", port);

    }
}
