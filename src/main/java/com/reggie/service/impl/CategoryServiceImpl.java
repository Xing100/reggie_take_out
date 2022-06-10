package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.reggie.common.CustomException;
import com.reggie.pojo.Category;
import com.reggie.mapper.CategoryMapper;
import com.reggie.pojo.Dish;
import com.reggie.pojo.Setmeal;
import com.reggie.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.service.IDishService;
import com.reggie.service.ISetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜品及套餐分类 服务实现类
 * </p>
 *
 * @author xingzhishan
 * @since 2022-04-29
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Autowired
    private IDishService iDishService;

    @Autowired
    private ISetmealService iSetmealService;

    /**
     * 根据id删除分类，删除之前需要进行判断
     * @param id
     */
    @Override
    public void remove(Long id) {
        //查询当前分类是否关联了菜品，如果已经关联，抛出一个也无异常
        int count1 = iDishService.count(new QueryWrapper<Dish>().eq("category_id", id));
        if (count1>0){
            //已经关联菜品，抛出一个业务异常
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }
        //查询当前分类是否关联了套餐，如果已经关联，抛出一个业务异常
        int count2 = iSetmealService.count(new QueryWrapper<Setmeal>().eq("category_id",id));
        if (count2>0){
            //已经关联了套餐，抛出一个业务异常
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }
        //正常删除分类
        super.removeById(id);
    }
}
