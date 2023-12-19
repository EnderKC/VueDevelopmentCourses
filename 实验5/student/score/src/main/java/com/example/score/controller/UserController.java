package com.example.score.controller;


import com.alibaba.fastjson2.JSONObject;
import com.example.score.dto.UserInfoDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.example.score.service.UserService;
import com.example.score.entity.User;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 用户类型字段分为教师和学生两种角色 前端控制器
 * </p>
 *
 * @author lihuanzhe
 * @since 2023-12-05
 */

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
    * 根据id查询实体
    */
    @PreAuthorize("hasRole('admin') or hasRole('stu')")
    @GetMapping("getById/{id}")
    public R getById(@PathVariable("id") String id)
    {
        if(id==null)
        {
            return R.error(Constants.PARAMETER_NULL_MSG);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        return R.ok().put("data", userService.getEntityById(id));
    }

    @GetMapping("getUserInfo")
    public R getUserInfo()
    {
        //获取当前登录用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)principal;
        //如果user为null，表示未登录
        if(user==null){
            return R.error("未登录，不能获取用户信息");
        }
        //去数据库查询当有登录用户是否存在
        User entity=userService.getEntityById(user.getUsername());
        if(entity==null){
            return R.error("未能获取用户信息");
        }
        //构造返回结果
        JSONObject obj=new JSONObject();
        obj.put("stuid",entity.getStuid());
        obj.put("role",entity.getRole());
        if(entity.getStuInfo()!=null){
            obj.put("stuname",entity.getStuInfo().getStuname());
            obj.put("gender",entity.getStuInfo().getGender());
            obj.put("classname",entity.getStuInfo().getClassname());
        }
        return R.ok().put("data", obj);
    }

    /**
     * 检查用户名是否可用
     * @param username 用户名
     * @return 返回true表示可用，false表示不可用
     */
    @GetMapping("checkUserName/{username}")
    public R checkUserName(@PathVariable String username)
    {
        if(StringUtils.isBlank(username)){
            return R.error(Constants.PARAMETER_NULL_MSG);
        }
        return R.ok().put("data",userService.checkUsername(username));
    }

    /**
    * 根据条件查询实体列表
    */
    @PostMapping("list")
    public R list(@RequestBody User criteria,@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize") Integer pageSize)
    {
        if(criteria==null||pageNum==null||pageNum< 1||pageSize==null||pageSize< 1){
            return R.error(Constants.PARAMETER_NULL_MSG);
        }
        PageHelper.startPage(pageNum,pageSize);
        List<User> list= userService.listByCriteria(criteria);

        PageInfo<User> result=new PageInfo<User>(list);
        return R.ok().put("data",result);
    }

    /**
    * 新增实体
    */
    @PostMapping("add")
    public R add(@RequestBody User entity)
    {
        if(entity==null){
            return R.error(Constants.PARAMETER_NULL_MSG);
        }
        if(entity.getRole().equalsIgnoreCase("admin")){
            entity.setStuid(null);
        }
        return toAjax(userService.saveEntity(entity));
    }

    /**
     * 注册学生用户，不能用于注册教师用户
     */
    @PostMapping("register")
    public R register(@RequestBody UserInfoDto userInfo)
    {
        if(userInfo==null){
            return R.error(Constants.PARAMETER_NULL_MSG);
        }
        userInfo.setRole("student");
        return toAjax(userService.register(userInfo));
    }

    /**
    * 批量新增实体
    */
    @PostMapping("batchSave")
    public R batchSave(@RequestBody List<User> list)
    {
        if(list==null){
            return R.error(Constants.PARAMETER_NULL_MSG);
        }

        return toAjax(userService.batchSaveEntity(list));
    }

    /**
    * 更新实体
    */
    @PutMapping("update")
    public R update(@RequestBody User entity)
    {
        if(entity==null){
            return R.error(Constants.PARAMETER_NULL_MSG);
        }

        return toAjax(userService.updateEntity(entity));
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
        return R.ok().put("data",userService.deleteById(id));
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
        return R.ok().put("data",userService.deleteByIds(ids));
    }

    /**
    * 批量删除实体
    */
    @DeleteMapping("deleteByCriteria")
    public R batchDeleteByCriteria(@RequestBody User criteria)
    {
        if(criteria==null)
        {
            return R.error(Constants.PARAMETER_NULL_MSG);
        }

        return R.ok().put("data",userService.deleteByCriteria(criteria));
    }

    @PostMapping("/uploadFile")
    public R uploadFile(@RequestBody MultipartFile file) {
        if (file == null) {
            return R.error(Constants.PARAMETER_NULL_MSG);
        }

        R ret = userService.uploadFile(file);
        return ret;
    }
}

