package com.learn.cloud.consuluserservice.service;

import com.learn.cloud.consuluserservice.entity.User;

import java.util.List;

public interface UserService {
    void create(User user);

    User getUser(Long id);

    List<User> getUserByIds(List<Long> ids);

    User getByUsername(String username);

    void update(User user);

    void delete(Long id);
}
