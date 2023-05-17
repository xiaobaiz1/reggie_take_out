package com.dbb.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dbb.reggie.dto.DishDto;
import com.dbb.reggie.pojo.Dish;

public interface DishService extends IService<Dish> {
    boolean saveWithDishFlavor(DishDto dishDto);

    DishDto getByIdWithFlavor(Long id);

    void updateWithDishFlavor(DishDto dishDto);

    Page<DishDto> myPage(int page, int pageSize, String name);
}
