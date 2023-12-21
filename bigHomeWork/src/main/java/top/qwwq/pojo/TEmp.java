package top.qwwq.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
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
@TableName("t_emp")
@ApiModel(value = "TEmp对象", description = "")
public class TEmp implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private Double salary;

    private Integer age;

    private LocalDate bir;
}
