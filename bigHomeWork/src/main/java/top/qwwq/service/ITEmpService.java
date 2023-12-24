package top.qwwq.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import top.qwwq.Utils.ResponseVo;
import top.qwwq.pojo.TEmp;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-21
 */
public interface ITEmpService extends IService<TEmp> {
    //查找所有员工
    ResponseVo<JSONArray> findAllEmp();
    // 添加员工
    ResponseVo<JSONObject> addEmp(TEmp emp);
    // 更新员工信息
    ResponseVo<JSONObject> updateEmp(TEmp emp);
    // 删除员工
    ResponseVo<JSONObject> deleteEmp(String id);

}
