package ring.central.common.result;

import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 分页查询结果
 * @Author: zuo.li zuo.li@luckincoffee.com
 * @Date: 2020/5/23 15:40
 */
@ToString
public class PaginationResult<T> implements Serializable {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 5663257580384725616L;

    /**
     * 返回数据总数
     */
    private Long total;

    /**
     * 返回数据
     */
    private List<T> rows;

    /**
     * 创建分页返回数据
     *
     * @param total 总数
     * @param rows 数据
     */
    public PaginationResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    /**
     * 获取 返回数据总数
     *
     * @return total 返回数据总数
     */
    public Long getTotal() {
        return this.total;
    }

    /**
     * 设置 返回数据总数
     *
     * @param total 返回数据总数
     */
    public void setTotal(Long total) {
        this.total = total;
    }

    /**
     * 获取 返回数据
     *
     * @return rows 返回数据
     */
    public List<T> getRows() {
        return this.rows;
    }

    /**
     * 设置 返回数据
     *
     * @param rows 返回数据
     */
    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
