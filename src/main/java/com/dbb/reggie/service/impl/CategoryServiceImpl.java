package com.dbb.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dbb.reggie.common.CustomException;
import com.dbb.reggie.mapper.CategoryMapper;
import com.dbb.reggie.pojo.Category;
import com.dbb.reggie.pojo.Dish;
import com.dbb.reggie.pojo.Setmeal;
import com.dbb.reggie.service.CategoryService;
import com.dbb.reggie.service.DishService;
import com.dbb.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id) {
        //判断category中有没有菜单
        LambdaQueryWrapper<Dish> dishQueryWrapper = new LambdaQueryWrapper<>();
        dishQueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(dishQueryWrapper);
        if(count1>0){
            throw new CustomException("当前分类项关联了菜品，不能删除");
        }

        //判断category中有没有套餐
        LambdaQueryWrapper<Setmeal> setmealQueryWrapper = new LambdaQueryWrapper<>();
        setmealQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(setmealQueryWrapper);
        if(count2>0){
            throw new CustomException("当前分类项关联了套餐，不能删除");
        }
        super.removeById(id);
    }
}
