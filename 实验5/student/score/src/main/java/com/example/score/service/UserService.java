package com.example.score.service;

import com.example.common.core.R;
import com.example.score.dto.UserInfoDto;
import com.example.score.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * <p>
 * 用户类型字段分为教师和学生两种角色 服务类
 * </p>
 *
 * @author lihuanzhe
 * @since 2023-12-05
 */
public interface UserService extends IService<User> {

    /**
     * 根据id查询实体
     * @param id 实体编号
     * @return 返回查询到的实体，查询不到返回Null
     */
    User getEntityById(String id);

    /**
     * 根据查询条件查询实体列表
     * @param criteria 查询条件
     * @return List 返回的结果列表
     */
    List<User> listByCriteria(User criteria);

    /**
     *  保存实体
     * @param entity 要保存的实体
     * @return 返回保存是否成功
     */
    boolean saveEntity(User entity);

    /**
     * 注册
     * @param userInfo 要注册的用户信息
     * @return 返回注册是否成功
     */
    boolean register(UserInfoDto userInfo);

    /**
     * 检查用户名是否存在
     * @param username 用户名
     * @return
     */
    boolean checkUsername(String username);
    /**
     * 批量保存
     * @param list 实体列表
     * @return 保存是否成功
     */
    boolean batchSaveEntity(List<User> list);

    /**
     *  更新实体
     * @param entity 要更新的实体
     * @return 返回更新是否成功
     */
    boolean updateEntity(User entity);

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
    int deleteByCriteria(User criteria);

    R uploadFile(MultipartFile file);


}
