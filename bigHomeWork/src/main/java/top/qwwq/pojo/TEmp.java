package top.qwwq.pojo;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;
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
@Data
@TableName("t_emp")
public class TEmp implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private Double salary;

    private Integer age;

    private LocalDate bir;
}
