package com.example.score.controller;


import com.example.score.entity.CompKey;
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

import com.example.score.service.CourseChoosingService;
import com.example.score.entity.CourseChoosing;
/**
 * <p>
 * 学生选课 前端控制器
 * </p>
 *
 * @author lihuanzhe
 * @since 2023-12-05
 */

@Slf4j
@RestController
@RequestMapping("/courseChoosing")
public class CourseChoosingController extends BaseController {

    @Autowired
    private CourseChoosingService courseChoosingService;

    /**
    * 根据id查询实体
    */
    @GetMapping("getById")
    public R getById(@RequestParam("stuid") String stuid,@RequestParam("cid") String cid)
    {
        if(StringUtils.isBlank(stuid) || StringUtils.isBlank(cid))
        {
            return R.error(Constants.PARAMETER_NULL_MSG);
        }
        return R.ok().put("data", courseChoosingService.getEntityById(stuid,cid));
    }

    /**
    * 根据条件查询实体列表
    */
    @PostMapping("list")
    public R list(@RequestBody CourseChoosing criteria,@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize") Integer pageSize)
    {
        if(criteria==null||pageNum==null||pageNum< 1||pageSize==null||pageSize< 1){
            return R.error(Constants.PARAMETER_NULL_MSG);
        }
        PageHelper.startPage(pageNum,pageSize);
        List<CourseChoosing> list= courseChoosingService.listByCriteria(criteria);

        PageInfo<CourseChoosing> result=new PageInfo<CourseChoosing>(list);
        return R.ok().put("data",result);
    }

    /**
    * 新增实体
    */
    @PostMapping("add")
    public R add(@RequestBody CourseChoosing entity)
    {
        if(entity==null){
            return R.error(Constants.PARAMETER_NULL_MSG);
        }

        return toAjax(courseChoosingService.saveEntity(entity));
    }

    /**
    * 批量新增实体
    */
    @PostMapping("batchSave")
    public R batchSave(@RequestBody List<CourseChoosing> list)
    {
        if(list==null){
            return R.error(Constants.PARAMETER_NULL_MSG);
        }

        return toAjax(courseChoosingService.batchSaveEntity(list));
    }

    /**
    * 更新实体
    */
    @PutMapping("update")
    public R update(@RequestBody CourseChoosing entity)
    {
        if(entity==null){
            return R.error(Constants.PARAMETER_NULL_MSG);
        }
        log.info("courseChoosing/update 请求更新数据，请求参数为：{}", entity);
        return toAjax(courseChoosingService.updateEntity(entity));
    }

    /**
    * 删除实体
    */
    @DeleteMapping("/deleteById")
    public R delete(@RequestParam("stuid") String stuid,@RequestParam("cid") String cid)
    {
        if(StringUtils.isBlank(stuid) || StringUtils.isBlank(cid))
        {
            return R.error(Constants.PARAMETER_NULL_MSG);
        }
        return R.ok().put("data",courseChoosingService.deleteById(stuid,cid));
    }

    /**
    * 批量删除实体
    */
    @DeleteMapping("deleteByIds")
    public R batchDelete(@RequestBody List<CompKey> idList)
    {
        if(idList==null || idList.size()==0)
        {
            return R.error(Constants.PARAMETER_NULL_MSG);
        }
        return R.ok().put("data",courseChoosingService.deleteByIds(idList));
    }

    /**
    * 批量删除实体
    */
    @DeleteMapping("deleteByCriteria")
    public R batchDeleteByCriteria(@RequestBody CourseChoosing criteria)
    {
        if(criteria==null)
        {
            return R.error(Constants.PARAMETER_NULL_MSG);
        }

        return R.ok().put("data",courseChoosingService.deleteByCriteria(criteria));
    }
}

