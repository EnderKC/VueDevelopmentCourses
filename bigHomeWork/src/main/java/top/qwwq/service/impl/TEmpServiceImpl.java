package top.qwwq.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import top.qwwq.Utils.GetUUID;
import top.qwwq.Utils.ResponseVo;
import top.qwwq.pojo.TEmp;
import top.qwwq.mapper.TEmpMapper;
import top.qwwq.pojo.TUser;
import top.qwwq.service.ITEmpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-21
 */
@Slf4j
@Service
public class TEmpServiceImpl extends ServiceImpl<TEmpMapper, TEmp> implements ITEmpService {
    TEmpMapper tEmpMapper;
    public TEmpServiceImpl(TEmpMapper tEmpMapper) {
        this.tEmpMapper = tEmpMapper;
    }

    @Override
    public ResponseVo<JSONObject> addEmp(TEmp emp) {
        if (emp.getId() == null){
            emp.setId(GetUUID.getUUID());
        }
        try{
            tEmpMapper.insert(emp);
            return new ResponseVo<>(0,"添加成功");
        }catch (Exception e){
            return new ResponseVo<>(-1,"添加失败");
        }
    }

    @Override
    public ResponseVo<JSONObject> updateEmp(TEmp emp) {
        QueryWrapper<TEmp> wrapper = new QueryWrapper<>();
        wrapper.eq("id", emp.getId());
        if (tEmpMapper.selectOne(wrapper) != null){
            try{
                tEmpMapper.updateById(emp);
                return new ResponseVo<>(0,"更新成功");
            }catch (Exception e){
                return new ResponseVo<>(-1,"更新失败");
            }
        }
        return new ResponseVo<>(-1,"查无此人");
    }

    @Override
    public ResponseVo<JSONObject> deleteEmp(String id) {
        QueryWrapper<TEmp> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        if (tEmpMapper.selectOne(wrapper) != null){
            try{
                tEmpMapper.deleteById(id);
                return new ResponseVo<>(0,"删除成功");
            }catch (Exception e){
                return new ResponseVo<>(-1,"删除失败");
            }
        }
        return new ResponseVo<>(-1,"查无此人");
    }

    @Override
    public ResponseVo<JSONArray> findAllEmp() {
        List<TEmp> tEmps = tEmpMapper.selectList(null);
        JSONArray jsonArray = new JSONArray();
        for (TEmp tEmp : tEmps) {
            String jsonString = JSONObject.toJSONString(tEmp);
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            jsonArray.add(jsonObject);
        }
        return new ResponseVo(0,"success",jsonArray);
    }
}
