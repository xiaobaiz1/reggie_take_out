package com.dbb.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dbb.reggie.dto.DishDto;
import com.dbb.reggie.dto.SetmealDto;
import com.dbb.reggie.mapper.SetmealMapper;
import com.dbb.reggie.pojo.*;
import com.dbb.reggie.service.CategoryService;
import com.dbb.reggie.service.SetmealDishService;
import com.dbb.reggie.service.SetmealService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SetmealDishService setmealDishService;
    @Override
    public Page<SetmealDto> mySetmealPage(int page, int pageSize, String name) {
        //构造分页器
        Page<Setmeal> pageInfo = new Page(page, pageSize);
        Page<SetmealDto> setmealDtoPage = new Page(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper();
        //判断name
        queryWrapper.like(StringUtils.isNotEmpty(name), Setmeal::getName, name);
        //添加排序条件(根据降序Desc)
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        //根据page和条件查询
        this.page(pageInfo, queryWrapper);

        List<Setmeal> records = pageInfo.getRecords();

        //stream循环
        List<SetmealDto> list = records.stream().map((item)->{
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            String categoryName = category.getName();
            setmealDto.setCategoryName(categoryName);
            return setmealDto;
        }).collect(Collectors.toList());
        setmealDtoPage.setRecords(list);
        return setmealDtoPage;
    }

    @Override
    public void mySave(SetmealDto setmealDto) {
        //先将套餐添加进去
        this.save(setmealDto);
        Long setmealId = setmealDto.getId();
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealId);
        }
        setmealDishService.saveBatch(setmealDishes);
    }

    /**
     * 删除套餐
     * @param id
     */
    @Override
    public void myRemove(Long id) {
        //1.删除套餐中的菜品
        LambdaUpdateWrapper<SetmealDish> setmealWrapper = new LambdaUpdateWrapper<SetmealDish>();
        setmealWrapper.eq(SetmealDish::getSetmealId,id);
        setmealDishService.remove(setmealWrapper);
        //2.删除套餐
        this.removeById(id);
    }

    @Override
    public SetmealDto getSetmealDto(Long id) {
        //查找套餐名称
        Setmeal setmeal = this.getById(id);

        SetmealDto setmealDto = new SetmealDto();

        BeanUtils.copyProperties(setmeal,setmealDto);
        //根据套餐查找菜名
        LambdaQueryWrapper<SetmealDish> dishDtoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishDtoLambdaQueryWrapper.eq(SetmealDish::getSetmealId,setmeal.getId());
        List<SetmealDish> list = setmealDishService.list(dishDtoLambdaQueryWrapper);
        setmealDto.setSetmealDishes(list);
        //查找套餐分类
        Category category = categoryService.getById(setmeal.getCategoryId());
        setmealDto.setCategoryName(category.getName());
        return setmealDto;
    }

    @Override
    public void updateSetmealDto(SetmealDto setmealDto) {
        //修改菜品表内容
        this.updateById(setmealDto);
        //删除该菜品
        LambdaUpdateWrapper<SetmealDish> setmealLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        setmealLambdaUpdateWrapper.eq(SetmealDish::getSetmealId,setmealDto.getId());
        setmealDishService.remove(setmealLambdaUpdateWrapper);

        //重写添加口味
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealDto.getId());
        }
        setmealDishService.saveBatch(setmealDishes);
    }


}
