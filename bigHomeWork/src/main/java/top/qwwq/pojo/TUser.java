package top.qwwq.pojo;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-21
 */
@Getter
@Setter
@TableName("t_user")
public class TUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private String username;

    private String realname;

    private String password;

    private String sex;
}
