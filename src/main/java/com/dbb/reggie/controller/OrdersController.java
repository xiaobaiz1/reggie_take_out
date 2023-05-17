package com.dbb.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dbb.reggie.common.R;
import com.dbb.reggie.pojo.Orders;
import com.dbb.reggie.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/order")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;
    /**
     * 手机端实现结账功能
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody  Orders orders){
        ordersService.submit(orders);
        return R.success("成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) {
        //构造分页器
        Page pageInfo = new Page(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper();
        //添加排序条件(根据降序Asc)
        queryWrapper.orderByAsc(Orders::getOrderTime);
        ordersService.page(pageInfo, queryWrapper);
        //根据page和条件查询
        return R.success(pageInfo);
    }
}
