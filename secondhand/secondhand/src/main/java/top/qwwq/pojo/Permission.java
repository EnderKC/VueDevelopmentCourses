package top.qwwq.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 权限
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-26
 */
@Getter
@Setter
@ApiModel(value = "Permission对象", description = "权限")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("权限id")
    @TableId("permission_id")
    private Integer permissionId;

    @ApiModelProperty("权限名称")
    private Integer permissionName;

    @ApiModelProperty("具体权限")
    private String perms;

    @ApiModelProperty("所在触发的位置（0一级菜单，1二菜单，2 按钮）")
    private Integer menuType;
}
