package ring.central.assessment.core.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Description: 文件DO
 * @Author: zuo.li zuo.li@luckincoffee.com
 * @Date: 2020/5/23 15:55
 */
@Data
public class FileDO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 文件编号
     */
    private String fileNo;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 创建时间
     */
    private Date createTime;
}
