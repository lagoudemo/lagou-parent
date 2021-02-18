package com.lagou.edu.user.service.impl;

import com.lagou.edu.user.dao.UserDao;
import com.lagou.edu.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao userDao;

}
