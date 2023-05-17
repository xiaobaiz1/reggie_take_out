package com.dbb.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dbb.reggie.mapper.EmployeeMapper;
import com.dbb.reggie.mapper.OrderDetailMapper;
import com.dbb.reggie.pojo.Employee;
import com.dbb.reggie.pojo.OrderDetail;
import com.dbb.reggie.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
