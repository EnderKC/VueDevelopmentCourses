package com.example.score.service;

import com.example.score.entity.Score;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 成绩表 服务类
 * </p>
 *
 * @author lihuanzhe
 * @since 2023-12-05
 */
public interface ScoreService extends IService<Score> {

    /**
     * 根据id查询实体
     * @param id 实体编号
     * @return 返回查询到的实体，查询不到返回Null
     */
    Score getEntityById(String id);

    /**
     * 根据查询条件查询实体列表
     * @param criteria 查询条件
     * @return List 返回的结果列表
     */
    List<Score> listByCriteria(Score criteria);

    /**
     *  保存实体
     * @param entity 要保存的实体
     * @return 返回保存是否成功
     */
    boolean saveEntity(Score entity);

    /**
     * 批量保存
     * @param list 实体列表
     * @return 保存是否成功
     */
    boolean batchSaveEntity(List<Score> list);

    /**
     *  更新实体
     * @param entity 要更新的实体
     * @return 返回更新是否成功
     */
    boolean updateEntity(Score entity);

    /**
     *  根据id删除实体
     * @param id 要删除的实体编号
     * @return 返回已删除的记录数
     */
    int deleteById(String id);

    /**
     *  根据id列表删除
     * @param ids 要删除的编号列表，以逗号分隔
     * @return 返回已删除的记录数
     */
    int deleteByIds(String ids);

    /**
     *  根据条件删除
     * @param criteria 删除条件
     * @return 返回删除的记录数
     */
    int deleteByCriteria(Score criteria);

}
