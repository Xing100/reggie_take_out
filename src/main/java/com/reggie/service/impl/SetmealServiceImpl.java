package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.reggie.common.CustomException;
import com.reggie.dto.SetmealDto;
import com.reggie.pojo.Setmeal;
import com.reggie.mapper.SetmealMapper;
import com.reggie.pojo.SetmealDish;
import com.reggie.service.ISetmealDishService;
import com.reggie.service.ISetmealService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 套餐 服务实现类
 * </p>
 *
 * @author xingzhishan
 * @since 2022-04-29
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements ISetmealService {

    @Autowired
    private ISetmealDishService iSetmealDishService;
    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐的基本信息，操作setmeal，执行insert操作
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        //保存套餐和菜品的关联信息，操作setmeal_dish执行insert操作
        iSetmealDishService.saveBatch(setmealDishes);
    }

    /**
     * 删除套餐，同时需要删除套餐和菜品的关联数据
     * @param ids
     */
    @Override
    public void removeWithDish(List<Long> ids) {

        int count = this.count(new QueryWrapper<Setmeal>()
                .in("id",ids)
                .eq("status",1));
        if (count>0){
            throw new CustomException("套餐正在售卖中，不能删除");
        }
        this.removeByIds(ids);
        iSetmealDishService.remove(new QueryWrapper<SetmealDish>()
                .in("setmeal_id",ids));

    }
}
