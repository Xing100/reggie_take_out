package com.reggie.service;

import com.reggie.pojo.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 菜品及套餐分类 服务类
 * </p>
 *
 * @author xingzhishan
 * @since 2022-04-29
 */
public interface ICategoryService extends IService<Category> {
    public void remove(Long id);

}
