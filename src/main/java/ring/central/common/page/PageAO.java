package ring.central.common.page;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description: 公共分页AO
 * @Author: zuo.li zuo.li@luckincoffee.com
 * @Date: 2020/5/23 15:27
 */
@Setter
@Getter
@ToString
public class PageAO {

    /**
     * 第几页（从1开始）
     */
    private int page;
    /**
     * 每页条数
     */
    private int rows;
}
