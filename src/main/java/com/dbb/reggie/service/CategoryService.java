package com.dbb.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dbb.reggie.pojo.Category;

public interface CategoryService extends IService<Category> {

   void remove(Long id);
}

