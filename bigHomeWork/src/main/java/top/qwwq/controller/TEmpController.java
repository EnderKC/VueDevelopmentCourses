package top.qwwq.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import top.qwwq.Utils.ResponseVo;
import top.qwwq.pojo.TEmp;
import top.qwwq.service.ITEmpService;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-21
 */
@Slf4j
@RestController
@RequestMapping("/tEmp")
public class TEmpController {
    ITEmpService tEmpService;
    public TEmpController(ITEmpService tEmpService) {
        this.tEmpService = tEmpService;
    }

    // 添加员工
    @RequestMapping("/addEmp")
    public ResponseVo<JSONObject> addEmp(@RequestBody TEmp emp){
        return tEmpService.addEmp(emp);
    }

    // 删除员工
    @RequestMapping("/deleteEmp")
    public ResponseVo<JSONObject> deleteEmp(@RequestBody TEmp emp){
        log.info(emp.toString());
        return tEmpService.deleteEmp(emp.getId());
    }

    // 更新员工信息
    @RequestMapping("/updateEmp")
    public ResponseVo<JSONObject> updateEmp(@RequestBody TEmp emp){
        log.info(emp.toString());
        return tEmpService.updateEmp(emp);
    }

    // 查找所有员工
    @RequestMapping("/findAllEmp")
    public ResponseVo<JSONArray> findAllEmp(){
        return tEmpService.findAllEmp();
    }

}
