package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.domain.entity.ResponseResult;
import com.sangeng.domain.entity.Tag;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.domain.vo.TagListDto;
import com.sangeng.mapper.TagMapper;
import com.sangeng.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * 标签(SgTag)表服务实现类
 *
 * @author makejava
 * @since 2023-03-20 15:15:45
 */
@Service("sgTagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(tagListDto.getName()), Tag::getName, tagListDto.getName());
        queryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()), Tag::getRemark, tagListDto.getRemark());
        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        //分页查询
        page(page, queryWrapper);
        //封装数据返回
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult saveTag(TagListDto tagListDto) {
        // 标签是否存在
        Tag existTag = tagMapper.selectOne(new LambdaQueryWrapper<Tag>()
                //利用条件构造器查询某个字段。这里是id
                .select(Tag::getId)
                //判断name相同
                .eq(Tag::getName, tagListDto.getName()));
        //断言
        Assert.isNull(existTag, tagListDto.getName() + "标签已存在");
        // 添加新标签
        Tag tag = new Tag();
        tag.setName(tagListDto.getName());
        tag.setRemark(tagListDto.getRemark());
        baseMapper.insert(tag);
        return ResponseResult.okResult();
    }
}

