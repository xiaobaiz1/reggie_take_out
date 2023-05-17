package com.dbb.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dbb.reggie.pojo.Orders;

public interface OrdersService extends IService<Orders> {
    void submit(Orders orders);
}
