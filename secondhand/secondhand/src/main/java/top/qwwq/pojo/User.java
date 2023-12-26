package top.qwwq.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-26
 */
@Getter
@Setter
@ApiModel(value = "User对象", description = "用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id 主键")
    @TableId("user_id")
    private String userId;

    @ApiModelProperty("用户名 唯一")
    private String userName;

    @ApiModelProperty("用户邮箱")
    private String userEmail;

    @ApiModelProperty("用户密码")
    private String userPassword;

    @ApiModelProperty("用户角色")
    private Integer userRole;
}
