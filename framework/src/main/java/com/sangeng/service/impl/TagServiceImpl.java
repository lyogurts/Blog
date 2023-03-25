package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.domain.entity.ResponseResult;
import com.sangeng.domain.entity.Tag;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.domain.vo.TagDto;
import com.sangeng.domain.vo.TagListDto;
import com.sangeng.domain.vo.TagVo;
import com.sangeng.mapper.TagMapper;
import com.sangeng.service.TagService;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

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

    @Override
    public void deleteTag(Integer  id) {
        tagMapper.deleteById(id);
    }

    @Override
    public ResponseResult getTagId(Integer id) {
        Tag tag = tagMapper.selectById(id);
        TagDto tagDto = BeanCopyUtils.copyBean(tag, TagDto.class);
        tagDto.setId(id);
        return ResponseResult.okResult(tagDto);
    }

    @Override
    public ResponseResult updateByIdTag(TagDto tagDto) {
        //判断该标签名字是否占用
        Tag selectOne = tagMapper.selectOne(
                new LambdaQueryWrapper<Tag>()
                        .select(Tag::getId)
                        .eq(Tag::getName, tagDto.getName())
        );
        Assert.isNull(selectOne,tagDto.getName()+"名字已存在");
        Tag tag = new Tag();
        tag.setRemark(tagDto.getRemark());
        tag.setName(tagDto.getName());
        Integer id = tagDto.getId();
        tag.setId(id);
        tagMapper.updateById(tag);

        //修改
        //返回值
        return ResponseResult.okResult(tag);
    }

    @Override
    public List<TagVo> listAllTag() {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Tag::getId,Tag::getName);
        List<Tag> list = list(wrapper);
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(list, TagVo.class);
        return tagVos;
    }

}

