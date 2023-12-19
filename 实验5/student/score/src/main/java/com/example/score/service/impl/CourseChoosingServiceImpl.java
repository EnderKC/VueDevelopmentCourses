package com.example.score.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.score.entity.CompKey;
import com.example.score.entity.CourseChoosing;
import com.example.score.mapper.CourseChoosingMapper;
import com.example.score.service.CourseChoosingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * 学生选课 服务实现类
 * </p>
 *
 * @author lihuanzhe
 * @since 2023-12-05
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class CourseChoosingServiceImpl extends ServiceImpl<CourseChoosingMapper, CourseChoosing>implements CourseChoosingService {

    /**
     * 根据id查询实体
     * @param id 实体编号
     * @return 返回查询到的实体，查询不到返回Null
     */
    @Override
    public CourseChoosing getEntityById(String stuid,String cid){
        QueryWrapper<CourseChoosing> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("stuid",stuid);
        queryWrapper.eq("cid",cid);
        return this.getOne(queryWrapper);
    }

    /**
     * 根据查询条件查询实体列表
     * @param criteria 查询条件
     * @return List 返回的结果列表
     */
    @Override
    public List<CourseChoosing> listByCriteria(CourseChoosing criteria){
        return baseMapper.listByCriteria(criteria);
    }

    /**
     *  保存实体
     * @param entity 要保存的实体
     * @return 返回保存是否成功
     */
    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean saveEntity(CourseChoosing entity){
        //在不允许重复的字段上一定要创建唯一索引，防止出现重复值
        return this.save(entity);
    }

    /**
     * 批量保存
     * @param list 实体列表
     * @return 保存是否成功
    */
    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean batchSaveEntity(List<CourseChoosing> list) {
        return this.saveBatch(list,100);
    }

    /**
     *  更新实体
     * @param entity 要更新的实体
     * @return 返回更新是否成功
     */
    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean updateEntity(CourseChoosing entity){
        //entity中的主键是否为空，为空返false
        if(entity.getStuid()==null || entity.getCid()==null){
            return false;
        }
        //判断要更新对象在数据库是否存在，不存在返回false
        if(this.getEntityById(entity.getStuid(),entity.getCid())==null){
            return false;
        }
        return this.updateById(entity);
    }
    /**
     *  根据id删除实体
     * @param id 要删除的实体编号
     * @return 返回已删除的记录数
     */
    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public int deleteById(String stuid,String cid){
        QueryWrapper<CourseChoosing> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("stuid",stuid);
        queryWrapper.eq("cid",cid);
        return this.remove(queryWrapper)?1:0;
    }

    /**
     *  根据id列表删除
     * @param ids 要删除的编号列表，以逗号分隔
     * @return 返回已删除的记录数
     */
    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public int deleteByIds(List<CompKey> idList){
        AtomicInteger n= new AtomicInteger();
        idList.forEach(item->{
            n.addAndGet(this.deleteById(item.getStuid(), item.getCid()));
        });
        return n.get();
    }

    /**
     *  根据条件删除
     * @param criteria 删除条件
     * @return 返回删除的记录数
     */
    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public int deleteByCriteria(CourseChoosing criteria){
        return baseMapper.deleteByCriteria(criteria);
    }
}
