package com.dbb.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dbb.reggie.common.R;
import com.dbb.reggie.dto.DishDto;
import com.dbb.reggie.pojo.Category;
import com.dbb.reggie.pojo.Dish;
import com.dbb.reggie.pojo.DishFlavor;
import com.dbb.reggie.service.CategoryService;
import com.dbb.reggie.service.DishFlavorService;
import com.dbb.reggie.service.DishService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜品管理
 */
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;


    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        Page<DishDto> dishDtoPage = dishService.myPage(page, pageSize, name);
        return R.success(dishDtoPage);
    }

    /**
     * 菜品添加
     *
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        dishService.saveWithDishFlavor(dishDto);
        return R.success("添加成功");
    }

    /**
     * 修改页面的回显
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id) {
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    /**
     * 修改的实现
     *
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> put(@RequestBody DishDto dishDto) {
        dishService.updateWithDishFlavor(dishDto);
        return R.success("修改成功");
    }

    /*@GetMapping("/list")
    public R<List<Dish>> list(Dish dish) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        dishLambdaQueryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        dishLambdaQueryWrapper.eq(Dish::getStatus, 1);
        if (StringUtils.isNotBlank(dish.getName())) {
            dishLambdaQueryWrapper.like(Dish::getName, dish.getName());
        }
        List<Dish> list = dishService.list(dishLambdaQueryWrapper);
        return R.success(list);
    }*/
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        dishLambdaQueryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        dishLambdaQueryWrapper.eq(Dish::getStatus, 1);
        if (StringUtils.isNotBlank(dish.getName())) {
            dishLambdaQueryWrapper.like(Dish::getName, dish.getName());
        }
        List<Dish> dishList = dishService.list(dishLambdaQueryWrapper);
        //stream循环
        List<DishDto> list = dishList.stream().map((item)->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            String categoryName = category.getName();
            dishDto.setCategoryName(categoryName);

            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> dishFlavorWrapper = new LambdaQueryWrapper<>();
            dishFlavorWrapper.eq(DishFlavor::getDishId,dishId);

            List<DishFlavor> dishFlavorList = dishFlavorService.list(dishFlavorWrapper);
            dishDto.setFlavors(dishFlavorList);
            return dishDto;
        }).collect(Collectors.toList());

        //List<Dish> list = dishService.list(dishLambdaQueryWrapper);
        return R.success(list);
    }



    /**
     * 设置售卖状态
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> state(@PathVariable Integer status, Long[] ids) {
        for (Long id : ids) {
            LambdaUpdateWrapper<Dish> dishLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            dishLambdaUpdateWrapper.eq(Dish::getId, id).set(Dish::getStatus, status);
            dishService.update(dishLambdaUpdateWrapper);
        }

        return R.success("修改成功");
    }


}
