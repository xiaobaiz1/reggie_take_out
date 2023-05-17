package com.dbb.reggie.dto;

import com.dbb.reggie.pojo.Setmeal;
import com.dbb.reggie.pojo.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
