package com.dbb.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dbb.reggie.mapper.AddressBookMapper;
import com.dbb.reggie.pojo.AddressBook;
import com.dbb.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper,AddressBook> implements AddressBookService {
}
