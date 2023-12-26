package top.qwwq.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 身份表
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-26
 */
@Getter
@Setter
@ApiModel(value = "Role对象", description = "身份表")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("身份id")
    @TableId("role_id")
    private Integer roleId;

    @ApiModelProperty("身份名称")
    private Integer roleName;
}
