package com.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.dto.SetmealDto;
import com.reggie.pojo.Category;
import com.reggie.pojo.R;
import com.reggie.pojo.Setmeal;
import com.reggie.service.ICategoryService;
import com.reggie.service.ISetmealDishService;
import com.reggie.service.ISetmealService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 套餐 前端控制器
 * </p>
 *
 * @author xingzhishan
 * @since 2022-04-29
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private ISetmealService iSetmealService;
    @Autowired
    private ISetmealDishService iSetmealDishService;
    @Autowired
    private ICategoryService iCategoryService;

    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        iSetmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }

    @GetMapping("page")
    public R<Page> page(int page,int pageSize,String name){
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> dtoPage = new Page<>();
        iSetmealService.page(pageInfo,new QueryWrapper<Setmeal>()
                .like(StringUtils.isNotEmpty(name),"name",name)
                .orderByDesc("update_time"));
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map((item)->{
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);
            Long categoryid = item.getCategoryId();
            Category category = iCategoryService.getById(categoryid);
            if(category!=null){
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect((Collectors.toList()));

        dtoPage.setRecords(list);

        return R.success(dtoPage);
    }

    /**
     * 删除套餐
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        iSetmealService.removeWithDish(ids);
        return R.success("套餐数据删除成功");
    }

}
