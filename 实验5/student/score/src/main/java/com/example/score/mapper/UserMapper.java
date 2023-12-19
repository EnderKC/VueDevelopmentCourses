package com.example.score.mapper;

import com.example.score.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;
/**
 * <p>
 * 用户类型字段分为教师和学生两种角色 Mapper 接口
 * </p>
 *
 * @author lihuanzhe
 * @since 2023-12-05
 */
public interface UserMapper extends BaseMapper<User> {

    User getByEntityId(String username);

    /**
     * 根据查询条件查询实体列表
     * @param criteria 查询条件
     * @return List 返回的结果列表
     */
    List<User> listByCriteria(User criteria);

    /**
     *  根据条件删除
     * @param criteria 删除条件
     * @return int 删除的记录数
     */
    @ResultType(Integer.class)
    int deleteByCriteria(User criteria);

}
