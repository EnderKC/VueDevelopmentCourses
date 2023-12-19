package com.example.score.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.common.core.BaseController;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.example.common.core.BaseController;
import com.example.common.core.Constants;
import com.example.common.core.R;
import com.example.common.util.StringUtils;
import java.util.List;

import com.example.score.service.CourseService;
import com.example.score.entity.Course;
/**
 * <p>
 * 课程表 前端控制器
 * </p>
 *
 * @author lihuanzhe
 * @since 2023-12-05
 */

@Slf4j
@RestController
@RequestMapping("/course")
public class CourseController extends BaseController {

    @Autowired
    private CourseService courseService;

    /**
    * 根据id查询实体
    */
    @GetMapping("getById/{id}")
    public R getById(@PathVariable("id") String id)
    {
        if(id==null)
        {
            return R.error(Constants.PARAMETER_NULL_MSG);
        }
        return R.ok().put("data", courseService.getEntityById(id));
    }

    /**
    * 根据条件查询实体列表
    */
    @PostMapping("list")
    public R list(@RequestBody Course criteria,@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize") Integer pageSize)
    {
        if(criteria==null||pageNum==null||pageNum< 1||pageSize==null||pageSize< 1){
            return R.error(Constants.PARAMETER_NULL_MSG);
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Course> list= courseService.listByCriteria(criteria);

        PageInfo<Course> result=new PageInfo<Course>(list);
        return R.ok().put("data",result);
    }

    /**
    * 新增实体
    */
    @PostMapping("add")
    public R add(@RequestBody Course entity)
    {
        if(entity==null){
            return R.error(Constants.PARAMETER_NULL_MSG);
        }

        return toAjax(courseService.saveEntity(entity));
    }

    /**
    * 批量新增实体
    */
    @PostMapping("batchSave")
    public R batchSave(@RequestBody List<Course> list)
    {
        if(list==null){
            return R.error(Constants.PARAMETER_NULL_MSG);
        }

        return toAjax(courseService.batchSaveEntity(list));
    }

    /**
    * 更新实体
    */
    @PutMapping("update")
    public R update(@RequestBody Course entity)
    {
        if(entity==null){
            return R.error(Constants.PARAMETER_NULL_MSG);
        }

        return toAjax(courseService.updateEntity(entity));
    }

    /**
    * 删除实体
    */
    @DeleteMapping("/deleteById/{id}")
    public R delete(@PathVariable("id") String id)
    {
        if(id==null){
            return R.error(Constants.PARAMETER_NULL_MSG);
        }
        return R.ok().put("data",courseService.deleteById(id));
    }

    /**
    * 批量删除实体
    */
    @DeleteMapping("deleteByIds")
    public R batchDelete(@RequestParam String ids)
    {
        if(StringUtils.isBlank(ids))
        {
            return R.error(Constants.PARAMETER_NULL_MSG);
        }
        return R.ok().put("data",courseService.deleteByIds(ids));
    }

    /**
    * 批量删除实体
    */
    @DeleteMapping("deleteByCriteria")
    public R batchDeleteByCriteria(@RequestBody Course criteria)
    {
        if(criteria==null)
        {
            return R.error(Constants.PARAMETER_NULL_MSG);
        }

        return R.ok().put("data",courseService.deleteByCriteria(criteria));
    }
}

