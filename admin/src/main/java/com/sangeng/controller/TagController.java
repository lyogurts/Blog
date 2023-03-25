package com.sangeng.controller;

import com.sangeng.domain.entity.ResponseResult;
import com.sangeng.domain.vo.TagDto;
import com.sangeng.domain.vo.TagListDto;
import com.sangeng.domain.vo.TagVo;
import com.sangeng.service.TagService;
import io.swagger.annotations.ApiOperation;
import kotlin.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @DeleteMapping("/content/tag/{id}")
    public ResponseResult deleteTag( @PathVariable ("id") Integer  id){
    tagService.deleteTag(id);
    return ResponseResult.okResult();
    }
    @GetMapping("/content/tag/{id}")
    public ResponseResult selectByIdTag(@PathVariable("id") Integer id){
      return   tagService.getTagId(id);
    }
    @PutMapping("/content/tag")
    public ResponseResult updateByIdTag(@RequestBody TagDto tagDto){
        return tagService.updateByIdTag(tagDto);
    }

    @GetMapping("/content/tag/listAllTag")
    public ResponseResult listAllTag(){
        List<TagVo> list = tagService.listAllTag();
        return ResponseResult.okResult(list);
    }
}
