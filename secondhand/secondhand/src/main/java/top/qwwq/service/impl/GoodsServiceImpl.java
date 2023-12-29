package top.qwwq.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import top.qwwq.mapper.UserMapper;
import top.qwwq.pojo.Goods;
import top.qwwq.mapper.GoodsMapper;
import top.qwwq.pojo.PageRequest;
import top.qwwq.pojo.PageResult;
import top.qwwq.pojo.User;
import top.qwwq.service.IGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.qwwq.utils.PageUtils;
import top.qwwq.utils.ResponseVo;
import top.qwwq.utils.GetUUID;

import java.util.List;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-26
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {
    UserMapper userMapper;
    GoodsMapper goodsMapper;
    public GoodsServiceImpl(UserMapper userMapper,GoodsMapper goodsMapper) {
        this.userMapper = userMapper;
        this.goodsMapper = goodsMapper;
    }

    @Override
    public ResponseVo<JSONObject> publishGoods(User user, Goods goods) {
        try{
            goods.setId(GetUUID.getUUID());
            goods.setUserId(user.getUserId());
            goodsMapper.insert(goods);
        }catch (Exception e){
            return new ResponseVo<>(-1,"发布失败");
        }
        String jsonString = JSONObject.toJSONString(goods);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        return new ResponseVo<>(0,"发布成功",jsonObject);
    }

    @Override
    public ResponseVo<JSONObject> updateGoods(User user, Goods goods) {
        if(!goods.getUserId().equals(user.getUserId())){
            return new ResponseVo<>(-1,"非法操作");
        }
        try{
            goodsMapper.updateById(goods);
        }catch (Exception e){
            return new ResponseVo<>(-1,"更新失败");
        }
        return new ResponseVo<>(0,"更新成功");
    }

    @Override
    public ResponseVo<JSONObject> deleteGoods(User user, Goods goods) {
        if(!goods.getId().equals(user.getUserId())){
            return new ResponseVo<>(-1,"非法操作");
        }
        try{
            goodsMapper.deleteById(goods.getId());
        }catch (Exception e){
            return new ResponseVo<>(-1,"删除失败");
        }
        return new ResponseVo<>(0,"删除成功");
    }

    @Override
    public ResponseVo<JSONArray> searchGoods(User user) {
        QueryWrapper<Goods> goodsQueryWrapper = new QueryWrapper<Goods>();
        goodsQueryWrapper.eq("user_id",user.getUserId());
        try {
            JSONArray jsonArray = new JSONArray();
            for (Goods goods : goodsMapper.selectList(goodsQueryWrapper)) {
                String jsonString = JSONObject.toJSONString(goods);
                JSONObject jsonObject = JSONObject.parseObject(jsonString);
                jsonArray.add(jsonObject);
            }
            return new ResponseVo<>(0, "查询成功", jsonArray);
        }catch (Exception e){
            return new ResponseVo<>(-1,"查询失败");
        }
    }

    @Override
    public ResponseVo<PageResult> listByCriteria(Goods criteria, PageRequest pageRequest) {
        PageResult pageResult = PageUtils.getPageResult(pageRequest, getPageInfo(criteria, pageRequest));
        return new ResponseVo<>(0,"查询成功",pageResult);
    }
    /**
     * 调用分页插件完成分页
     * @return
     */
    private PageInfo<Goods> getPageInfo(Goods criteria, PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Goods> goodsList = goodsMapper.listByCriteria(criteria);
        return new PageInfo<Goods>(goodsList);
    }
}
