package com.sangeng.service;

import com.sangeng.domain.entity.ResponseResult;
import com.sangeng.domain.entity.User;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();

}
