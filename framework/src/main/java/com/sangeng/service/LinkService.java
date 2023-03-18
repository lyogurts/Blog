package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.entity.Link;
import com.sangeng.domain.entity.ResponseResult;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-03-18 15:35:38
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

}

