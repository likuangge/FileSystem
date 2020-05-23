package ring.central.assessment.core.vo;

import lombok.Data;
import ring.central.common.CommonConstants;
import ring.central.util.DateTimeUtil;

import java.util.Date;
import java.util.Objects;

/**
 * @Description: 文件VO
 * @Author: zuo.li zuo.li@luckincoffee.com
 * @Date: 2020/5/23 15:42
 */
@Data
public class FileVO {

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

    /**
     * 获取创建时间字符串
     *
     * @return 创建时间字符串
     */
    public String getCreateTimeDesc() {
        return Objects.isNull(getCreateTime()) ? CommonConstants.NULL_STR : DateTimeUtil.formatDateTime(getCreateTime());
    }
}
