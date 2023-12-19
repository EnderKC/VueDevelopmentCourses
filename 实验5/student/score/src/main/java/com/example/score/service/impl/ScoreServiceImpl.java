package com.example.score.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.Convert;
import com.example.score.entity.Score;
import com.example.score.mapper.ScoreMapper;
import com.example.score.service.ScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
/**
 * <p>
 * 成绩表 服务实现类
 * </p>
 *
 * @author lihuanzhe
 * @since 2023-12-05
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score>implements ScoreService {

    /**
     * 根据id查询实体
     * @param id 实体编号
     * @return 返回查询到的实体，查询不到返回Null
     */
    @Override
    public Score getEntityById(String id){
        return this.getById(id);
    }

    /**
     * 根据查询条件查询实体列表
     * @param criteria 查询条件
     * @return List 返回的结果列表
     */
    @Override
    public List<Score> listByCriteria(Score criteria){
        return baseMapper.listByCriteria(criteria);
    }

    /**
     *  保存实体
     * @param entity 要保存的实体
     * @return 返回保存是否成功
     */
    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean saveEntity(Score entity){
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
    public boolean batchSaveEntity(List<Score> list) {
        return this.saveBatch(list,100);
    }

    /**
     *  更新实体
     * @param entity 要更新的实体
     * @return 返回更新是否成功
     */
    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean updateEntity(Score entity){
        //entity中的主键是否为空，为空返false
        if(entity.getId()==null){
            return false;
        }
        //判断要更新对象在数据库是否存在，不存在返回false
        if(this.getEntityById(entity.getId())==null){
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
    public int deleteById(String id){
        return baseMapper.deleteById(id);
    }

    /**
     *  根据id列表删除
     * @param ids 要删除的编号列表，以逗号分隔
     * @return 返回已删除的记录数
     */
    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public int deleteByIds(String ids){
        Integer[]Ids= Convert.toIntArray(ids);
        List<Integer>idList=Arrays.asList(Ids);
        return baseMapper.deleteBatchIds(idList);
    }

    /**
     *  根据条件删除
     * @param criteria 删除条件
     * @return 返回删除的记录数
     */
    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public int deleteByCriteria(Score criteria){
        return baseMapper.deleteByCriteria(criteria);
    }
}
