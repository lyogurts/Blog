package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.entity.Comment;
import com.sangeng.domain.entity.ResponseResult;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-03-18 21:24:07
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize);

}

