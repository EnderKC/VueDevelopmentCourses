package top.qwwq.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 分页请求
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PageRequest {
    /**
     * 当前页码
     */
    private int pageNum;
    /**
     * 每页数量
     */
    private int pageSize;

}