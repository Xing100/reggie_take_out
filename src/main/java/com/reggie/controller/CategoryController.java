package com.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.pojo.Category;
import com.reggie.pojo.R;
import com.reggie.service.ICategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜品及套餐分类 前端控制器
 * </p>
 *
 * @author xingzhishan
 * @since 2022-04-29
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategoryService iCategoryService;

    /**
     * 新增分类
     */
    @PostMapping
    public R<String> save(@RequestBody Category category){
        iCategoryService.save(category);
        return R.success("新增分类成功");
    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
        Page<Category> pageInfo = new Page(page,pageSize);
        iCategoryService.page(pageInfo,new QueryWrapper<Category>().orderByAsc("sort"));
        return R.success(pageInfo);
    }
    /**
     * 根据id删除分类
     */
    @DeleteMapping
    public R<String> deleteById(@RequestParam("ids") Long id){
        System.out.println(id);
        iCategoryService.remove(id);
//        iCategoryService.removeById(ids);
        return R.success("分类信息删除成功");
    }

    /**
     * 根据id修改分类信息
     */
    @PutMapping
    public R<String> update(@RequestBody Category category){
        iCategoryService.updateById(category);
        return R.success("修改分类信息成功");

    }

    /**
     * 根据条件查询分类数据
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        List<Category> list = iCategoryService.list(new QueryWrapper<Category>()
                .eq("type",category.getType())
                .orderByAsc("sort")
                .orderByDesc("update_time"));

        return R.success(list);
    }

}
