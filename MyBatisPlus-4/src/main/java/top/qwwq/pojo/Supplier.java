package top.qwwq.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/*
 * 供应商实体类
 */
public class Supplier {
    private Long supplierID;
    private String supplierName;
    private String mobile;
    private String cateID;
}
