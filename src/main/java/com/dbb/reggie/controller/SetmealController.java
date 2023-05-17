package com.dbb.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dbb.reggie.common.R;
import com.dbb.reggie.dto.SetmealDto;
import com.dbb.reggie.pojo.Setmeal;
import com.dbb.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 套餐管理
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<SetmealDto>> page(int page, int pageSize, String name){
        Page<SetmealDto> setmealPage = setmealService.mySetmealPage(page, pageSize, name);
        return R.success(setmealPage);
    }

    /**
     * 添加套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        System.out.println(setmealDto.toString());
        setmealService.mySave(setmealDto);
        return R.success("添加成功  ");
    }

    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long[] ids){
        for (Long id : ids) {
            setmealService.myRemove(id);
        }
        return R.success("删除成功");
    }

    /**
     * 修改回显
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDto> get(@PathVariable Long id){
        SetmealDto setmealDto = setmealService.getSetmealDto(id);
        return R.success(setmealDto);
    }

    /**
     * 修改
     * @param setmealDto
     * @return
     */
    @PutMapping
    public R<String> put(@RequestBody SetmealDto setmealDto){
        setmealService.updateSetmealDto(setmealDto);
        return R.success("成功");
    }

    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,setmeal.getCategoryId());
        setmealLambdaQueryWrapper.eq(Setmeal::getStatus,setmeal.getStatus());
        List<Setmeal> list1 = setmealService.list(setmealLambdaQueryWrapper);
        return R.success(list1);

    }


}
