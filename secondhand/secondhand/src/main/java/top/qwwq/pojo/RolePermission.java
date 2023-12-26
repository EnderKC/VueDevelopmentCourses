package top.qwwq.pojo;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
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
public class RolePermission implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer roleId;

    private Integer permissionId;
}
