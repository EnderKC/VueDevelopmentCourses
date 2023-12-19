package com.example.score.mapper;

import com.example.score.entity.CourseChoosing;
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
 * 学生选课 Mapper 接口
 * </p>
 *
 * @author lihuanzhe
 * @since 2023-12-05
 */
public interface CourseChoosingMapper extends BaseMapper<CourseChoosing> {

    /**
     * 根据id删除实体
     * @param courseChoosing 要删除的实体
     * @return List 返回删除成功的记录数
     */
    int deleteById(CourseChoosing courseChoosing);

    /**
     * 保存实体
     * @param courseChoosing 要保存的实体
     * @return int 返回保存成功的记录灵敏
     */
    int save(CourseChoosing courseChoosing);

    /**
     * 根据查询条件查询实体列表
     * @param criteria 查询条件
     * @return List 返回的结果列表
     */
    List<CourseChoosing> listByCriteria(CourseChoosing criteria);

    /**
     *  根据条件删除
     * @param criteria 删除条件
     * @return int 删除的记录数
     */
    @ResultType(Integer.class)
    int deleteByCriteria(CourseChoosing criteria);

}
