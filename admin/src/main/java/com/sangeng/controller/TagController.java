package com.sangeng.controller;

import com.sangeng.domain.entity.ResponseResult;
import com.sangeng.domain.vo.TagListDto;
import com.sangeng.service.TagService;
import io.swagger.annotations.ApiOperation;
import kotlin.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class TagController {
    @Autowired
    private TagService tagService;
    @GetMapping("/content/tag/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }
    /**
     * 添加标签
     *
     * @param tag 标签信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "添加标签")
    @PostMapping("/content/tag")
    public ResponseResult addTag( @RequestBody TagListDto tag) {
        tagService.saveTag(tag);
        return ResponseResult.okResult();
    }
}
