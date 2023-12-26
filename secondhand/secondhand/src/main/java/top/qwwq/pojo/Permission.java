package top.qwwq.pojo;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.io.Serializable;
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
public class Permission implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("permission_id")
    private Integer permissionId;

    private String name;

    private String perms;

    private Integer menuType;
}
