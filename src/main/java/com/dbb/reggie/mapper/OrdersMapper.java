package com.dbb.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dbb.reggie.pojo.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

}