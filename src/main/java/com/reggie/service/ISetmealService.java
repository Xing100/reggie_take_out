package com.reggie.service;

import com.reggie.dto.SetmealDto;
import com.reggie.pojo.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 套餐 服务类
 * </p>
 *
 * @author xingzhishan
 * @since 2022-04-29
 */
public interface ISetmealService extends IService<Setmeal> {

    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    void saveWithDish(SetmealDto setmealDto);
    void removeWithDish(List<Long> ids);
}
