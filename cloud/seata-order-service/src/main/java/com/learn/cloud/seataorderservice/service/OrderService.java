package com.learn.cloud.seataorderservice.service;


import com.learn.cloud.seataorderservice.domain.Order;

public interface OrderService {

    /**
     * 创建订单
     */
    void create(Order order);
}
