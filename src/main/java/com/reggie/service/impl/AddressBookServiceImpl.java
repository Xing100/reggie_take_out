package com.reggie.service.impl;

import com.reggie.pojo.AddressBook;
import com.reggie.mapper.AddressBookMapper;
import com.reggie.service.IAddressBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 地址管理 服务实现类
 * </p>
 *
 * @author xingzhishan
 * @since 2022-04-29
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements IAddressBookService {

}
