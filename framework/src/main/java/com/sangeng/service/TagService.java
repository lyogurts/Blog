package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.entity.ResponseResult;
import com.sangeng.domain.entity.Tag;
import com.sangeng.domain.vo.TagListDto;


/**
 * 标签(SgTag)表服务接口
 *
 * @author makejava
 * @since 2023-03-20 15:15:44
 */
public interface TagService extends IService<Tag> {

    ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult saveTag(TagListDto tagListDto);
}

