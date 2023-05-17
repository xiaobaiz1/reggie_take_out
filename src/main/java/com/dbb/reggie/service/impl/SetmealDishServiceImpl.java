package com.dbb.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dbb.reggie.dto.DishDto;
import com.dbb.reggie.mapper.SetmealDishMapper;
import com.dbb.reggie.pojo.Category;
import com.dbb.reggie.pojo.Dish;
import com.dbb.reggie.pojo.SetmealDish;
import com.dbb.reggie.service.SetmealDishService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper,SetmealDish> implements SetmealDishService {


}
