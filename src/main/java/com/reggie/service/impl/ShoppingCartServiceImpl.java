package com.reggie.service.impl;

import com.reggie.pojo.ShoppingCart;
import com.reggie.mapper.ShoppingCartMapper;
import com.reggie.service.IShoppingCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 购物车 服务实现类
 * </p>
 *
 * @author xingzhishan
 * @since 2022-04-29
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements IShoppingCartService {

}
