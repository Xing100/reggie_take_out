package com.reggie.service.impl;

import com.reggie.pojo.OrderDetail;
import com.reggie.mapper.OrderDetailMapper;
import com.reggie.service.IOrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单明细表 服务实现类
 * </p>
 *
 * @author xingzhishan
 * @since 2022-04-29
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {

}
