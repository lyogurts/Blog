package com.sangeng.service;

import com.sangeng.domain.entity.ResponseResult;
import com.sangeng.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);
}
