package com.lemain.service;

import com.lemain.domain.User;
import com.lemain.iservice.IUserService;
import com.lemain.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by txxs on 2017/2/1.
 */
@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserById(Integer id) {
        User user = userMapper.findUserById(id);
        return user;
    }
}
