package com.lemain.iservice;

import com.lemain.domain.User;

/**
 * Created by txxs on 2017/2/1.
 */
public interface IUserService {

    public User findUserById(Integer id);
}
