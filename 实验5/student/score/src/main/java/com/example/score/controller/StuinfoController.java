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

import com.example.score.service.StuinfoService;
import com.example.score.entity.Stuinfo;
/**
 * <p>
 * 学生信息表 前端控制器
 * </p>
 *
 * @author lihuanzhe
 * @since 2023-12-05
 */

@Slf4j
@RestController
@RequestMapping("/stuinfo")
public class StuinfoController extends BaseController {

    @Autowired
    private StuinfoService stuinfoService;

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
        return R.ok().put("data", stuinfoService.getEntityById(id));
    }

    /**
     * 检测学号是否存在
     * @param stuid
     * @return 返回true表示不存在，学号可用，返回false表示存在，学号不可用
     */
    @GetMapping("checkStuId/{stuid}")
    public R checkStuId(@PathVariable String stuid)
    {
        if(StringUtils.isBlank(stuid)){
            return R.error(Constants.PARAMETER_NULL_MSG);
        }
        return R.ok().put("data",stuinfoService.checkStuId(stuid));
    }

    /**
    * 根据条件查询实体列表
    */
    @PostMapping("list")
    public R list(@RequestBody Stuinfo criteria,@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize") Integer pageSize)
    {
        if(criteria==null||pageNum==null||pageNum< 1||pageSize==null||pageSize< 1){
            return R.error(Constants.PARAMETER_NULL_MSG);
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Stuinfo> list= stuinfoService.listByCriteria(criteria);

        PageInfo<Stuinfo> result=new PageInfo<Stuinfo>(list);
        return R.ok().put("data",result);
    }

    /**
    * 新增实体
    */
    @PostMapping("add")
    public R add(@RequestBody Stuinfo entity)
    {
        if(entity==null){
            return R.error(Constants.PARAMETER_NULL_MSG);
        }

        return toAjax(stuinfoService.saveEntity(entity));
    }

    /**
    * 批量新增实体
    */
    @PostMapping("batchSave")
    public R batchSave(@RequestBody List<Stuinfo> list)
    {
        if(list==null){
            return R.error(Constants.PARAMETER_NULL_MSG);
        }

        return toAjax(stuinfoService.batchSaveEntity(list));
    }

    /**
    * 更新实体
    */
    @PutMapping("update")
    public R update(@RequestBody Stuinfo entity)
    {
        if(entity==null){
            return R.error(Constants.PARAMETER_NULL_MSG);
        }

        return toAjax(stuinfoService.updateEntity(entity));
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
        return R.ok().put("data",stuinfoService.deleteById(id));
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
        return R.ok().put("data",stuinfoService.deleteByIds(ids));
    }

    /**
    * 批量删除实体
    */
    @DeleteMapping("deleteByCriteria")
    public R batchDeleteByCriteria(@RequestBody Stuinfo criteria)
    {
        if(criteria==null)
        {
            return R.error(Constants.PARAMETER_NULL_MSG);
        }

        return R.ok().put("data",stuinfoService.deleteByCriteria(criteria));
    }
}

