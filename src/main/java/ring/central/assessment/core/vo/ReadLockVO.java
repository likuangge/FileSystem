package ring.central.assessment.core.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description: 文件读锁VO
 * @Author: zuo.li zuo.li@luckincoffee.com
 * @Date: 2020/5/26 10:25
 */
@Getter
@Setter
@ToString
public class ReadLockVO {

    /**
     * 是否可读：0，不可读；1，可读
     */
    private Integer readLock;

    /**
     * 读取文件线程id
     */
    private Long threadId;
}
