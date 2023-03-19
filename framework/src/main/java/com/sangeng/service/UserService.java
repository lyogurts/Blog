package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.entity.ResponseResult;
import com.sangeng.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-03-18 22:06:55
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

}

