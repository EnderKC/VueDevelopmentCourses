package top.qwwq.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
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
public class UserInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("user_id")
    private String userId;

    private String address;

    private String phoneNumber;

    private String qqNumber;

    private String wechatNumber;
}
