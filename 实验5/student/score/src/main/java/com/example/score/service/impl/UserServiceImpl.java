package com.example.score.service.impl;

import com.example.common.core.R;
import com.example.common.util.Convert;
import com.example.common.util.file.FileUploadUtils;
import com.example.score.dto.UserInfoDto;
import com.example.score.entity.Stuinfo;
import com.example.score.entity.User;
import com.example.score.mapper.StuinfoMapper;
import com.example.score.mapper.UserMapper;
import com.example.score.service.StuinfoService;
import com.example.score.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
/**
 * <p>
 * 用户类型字段分为教师和学生两种角色 服务实现类
 * </p>
 *
 * @author lihuanzhe
 * @since 2023-12-05
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>implements UserService {

    private final StuinfoService stuinfoService;

    public UserServiceImpl(StuinfoService stuinfoService) {
        this.stuinfoService = stuinfoService;
    }

    /**
     * 根据id查询实体
     * @param id 实体编号
     * @return 返回查询到的实体，查询不到返回Null
     */
    @Override
    public User getEntityById(String id){
        return baseMapper.getByEntityId(id);
    }

    /**
     * 根据查询条件查询实体列表
     * @param criteria 查询条件
     * @return List 返回的结果列表
     */
    @Override
    public List<User> listByCriteria(User criteria){
        return baseMapper.listByCriteria(criteria);
    }

    /**
     *  保存实体
     * @param entity 要保存的实体
     * @return 返回保存是否成功
     */
    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean saveEntity(User entity){
        //在不允许重复的字段上一定要创建唯一索引，防止出现重复值
        return this.save(entity);
    }

    /**
     * 注册
     * @param userInfo 要保存的实体
     * @return 返回保存是否成功
     */
    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean register(UserInfoDto userInfo) {
        User user = new User();
        user.setUsername(userInfo.getUsername());
        user.setPassword(userInfo.getPassword());
        user.setRole(userInfo.getRole());
        user.setStuid(userInfo.getStuid());
        Stuinfo stuinfo = new Stuinfo();
        stuinfo.setStuid(userInfo.getStuid());
        stuinfo.setStuname(userInfo.getStuname());
        stuinfo.setGender(userInfo.getGender());
        stuinfo.setBirthday(userInfo.getBirthday());
        stuinfo.setClassname(userInfo.getClassname());
        stuinfo.setAddress(userInfo.getAddress());
        boolean ret=stuinfoService.saveEntity(stuinfo);
        if(ret){
            return this.save(user);
        }
        return false;
    }

    /**
     * 检查用户名是否可用
     * @param username 用户名
     * @return 返回true表示可用，false表示不可用
     */
    @Override
    public boolean checkUsername(String username) {
        User user = this.getEntityById(username);
        return user==null;
    }

    /**
     * 批量保存
     * @param list 实体列表
     * @return 保存是否成功
    */
    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean batchSaveEntity(List<User> list) {
        return this.saveBatch(list,100);
    }

    /**
     *  更新实体
     * @param entity 要更新的实体
     * @return 返回更新是否成功
     */
    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean updateEntity(User entity){
        //entity中的主键是否为空，为空返false
        if(entity.getUsername()==null){
            return false;
        }
        //判断要更新对象在数据库是否存在，不存在返回false
        if(this.getEntityById(entity.getUsername())==null){
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
    public int deleteByCriteria(User criteria){
        return baseMapper.deleteByCriteria(criteria);
    }

    @Override
    public R uploadFile(MultipartFile file) {
        String fileName ="";
        try {
            //保存上传的文件到指定位置并反回一个新的文件名
            fileName = FileUploadUtils.upload(file);
            return R.ok("上传文件成功").put("data",fileName);
        }catch (Exception e){
            log.error("上传文件失败{}",e.getMessage());
            return R.error(e.getMessage());
        }
    }
}
