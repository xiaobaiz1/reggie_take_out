package com.dbb.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dbb.reggie.dto.DishDto;
import com.dbb.reggie.dto.SetmealDto;
import com.dbb.reggie.pojo.Setmeal;

public interface SetmealService extends IService<Setmeal> {
   Page<SetmealDto> mySetmealPage(int page, int pageSize, String name);

   void mySave(SetmealDto setmealDto);

   void myRemove(Long id);

   SetmealDto getSetmealDto(Long id);

   void updateSetmealDto(SetmealDto setmealDto);
}
