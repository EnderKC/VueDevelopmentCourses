package top.qwwq.pojo;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.io.Serializable;
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
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("user_id")
    private String userId;

    private String userName;

    private String userEmail;

    private String userPassword;

    private Integer userRole;
}
