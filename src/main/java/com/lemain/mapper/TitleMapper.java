package com.lemain.mapper;

import com.lemain.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by txxs on 2017/2/1.
 */
@Mapper
public interface UserMapper {
    User findUserById(Integer id);
}
