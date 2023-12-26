package top.qwwq.pojo;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.io.Serializable;
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
public class Role implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("role_id")
    private Integer roleId;

    private String roleName;
}
