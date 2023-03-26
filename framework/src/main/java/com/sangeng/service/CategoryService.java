package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.entity.Category;
import com.sangeng.domain.entity.ResponseResult;
import com.sangeng.domain.vo.CategoryVo;

import java.util.List;


/**
 * 分类表(SgCategory)表服务接口
 *
 * @author makejava
 * @since 2023-03-18 12:01:17
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    List<CategoryVo> listAllCategory();

    ResponseResult pageCategoryList(Integer pageNum, Integer pageSize, CategoryVo categoryVo);
}

