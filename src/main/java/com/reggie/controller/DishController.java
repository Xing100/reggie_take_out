package com.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.dto.DishDto;
import com.reggie.pojo.Category;
import com.reggie.pojo.Dish;
import com.reggie.pojo.R;
import com.reggie.service.ICategoryService;
import com.reggie.service.IDishFlavorService;
import com.reggie.service.IDishService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜品管理 前端控制器
 * </p>
 *
 * @author xingzhishan
 * @since 2022-04-29
 */
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private IDishService iDishService;

    @Autowired
    private IDishFlavorService iDishFlavorService;

    @Autowired
    private ICategoryService iCategoryService;

    /**
     * 菜品分页查询
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize,String name){
        Page<Dish> pageInfo = new Page(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        iDishService.page(pageInfo,new QueryWrapper<Dish>()
                .like(StringUtils.isNotEmpty(name),"name",name)
                .orderByDesc("update_time"));

        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");

        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item)->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item.getCategoryId();
            Category category = iCategoryService.getById(categoryId);
            if (category!=null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }

    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        System.out.println(dishDto);
        iDishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }


    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        DishDto dishDto = iDishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        iDishService.updateWithFlavor(dishDto);
        return R.success("修改成功");
    }

    /**
     * 根据
     * @param id
     * @return
     */
    @GetMapping("/list")
    public R<List<Dish>> getDishByid(@RequestParam("categoryId") Long id){

        List<Dish> dish = iDishService.list(new QueryWrapper<Dish>()
                .eq("category_id", id)
                .eq("status",1));
        return R.success(dish);
    }

    @DeleteMapping
    public R deleteByid(Long ids){
        System.out.println(ids);
        return null;
    }

}
