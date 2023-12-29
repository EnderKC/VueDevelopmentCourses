package top.qwwq.pojo;

import java.io.Serializable;

import lombok.*;

/**
 * <p>
 * 商品
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String goodsName;

    private String goodsDescription;

    private Integer goodsStock;

    private String userId;
}
