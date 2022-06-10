package com.reggie.service;

import com.reggie.dto.DishDto;
import com.reggie.pojo.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 菜品管理 服务类
 * </p>
 *
 * @author xingzhishan
 * @since 2022-04-29
 */
public interface IDishService extends IService<Dish> {
    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish dish_flavor
    public void saveWithFlavor(DishDto dishDto);

    //根据id查询菜品信息和对应的口味信息
    public DishDto getByIdWithFlavor(Long id);

    //更新菜品信息和口味信息
    public void updateWithFlavor(DishDto dishDto);
}
