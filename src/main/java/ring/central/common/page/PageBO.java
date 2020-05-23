package ring.central.common.page;

import lombok.ToString;

/**
 * @Description: 分页公用BO
 * @Author: zuo.li zuo.li@luckincoffee.com
 * @Date: 2020/5/23 15:30
 */
@ToString
public class PageBO {

    /**
     * 页码（从1开始）
     */
    private Integer pageNo;

    /**
     * 每页多少条
     */
    private Integer pageSize;

    /**
     * 偏移量
     */
    private Integer offset;

    /**
     * 获取 页码（从1开始）
     *
     * @return pageNo 页码（从1开始）
     */
    public Integer getPageNo() {
        return this.pageNo;
    }

    /**
     * 设置 页码（从1开始）
     *
     * @param pageNo 页码（从1开始）
     */
    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * 获取 每页多少条
     *
     * @return pageSize 每页多少条
     */
    public Integer getPageSize() {
        return this.pageSize;
    }

    /**
     * 设置 每页多少条
     *
     * @param pageSize 每页多少条
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取 偏移量
     *
     * @return offset 偏移量
     */
    public Integer getOffset() {
        if(pageNo != null && pageSize != null){
            this.offset = (this.pageNo > 0 ? this.pageNo - 1 : 0) * this.pageSize;
            return offset;
        }else{
            return 0;
        }
    }

    /**
     * 设置 偏移量
     *
     * @param offset 偏移量
     */
    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
