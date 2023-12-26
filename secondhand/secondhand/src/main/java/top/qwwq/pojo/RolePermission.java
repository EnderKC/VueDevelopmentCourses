package top.qwwq.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 角色和权限的对应关系
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-26
 */
@Getter
@Setter
@TableName("role_permission")
@ApiModel(value = "RolePermission对象", description = "角色和权限的对应关系")
public class RolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("对应关系主键")
    private Integer id;

    @ApiModelProperty("角色id")
    private Integer roleId;

    @ApiModelProperty("权限id")
    private Integer permissionId;
}
