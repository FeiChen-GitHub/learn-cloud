package com.learn.cloud.userservice.service;

import com.learn.cloud.userservice.entity.User;

import java.util.List;

public interface UserService {
    void create(User user);

    User getUser(Long id);

    List<User> getUserByIds(List<Long> ids);

    User getByUsername(String username);

    void update(User user);

    void delete(Long id);
}
