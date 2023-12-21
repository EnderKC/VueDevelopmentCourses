package top.qwwq.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "TUser对象", description = "")
public class TUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String username;

    private String realname;

    private String password;

    private String sex;
}
