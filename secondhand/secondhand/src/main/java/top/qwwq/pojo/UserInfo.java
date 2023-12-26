package top.qwwq.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-26
 */
@Getter
@Setter
@TableName("user_info")
@ApiModel(value = "UserInfo对象", description = "用户信息表")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id，与user表主键相同")
    @TableId("user_id")
    private String userId;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("手机号")
    private String phoneNumber;

    @ApiModelProperty("qq号")
    private String qqNumber;

    @ApiModelProperty("微信号")
    private String wechatNumber;
}
