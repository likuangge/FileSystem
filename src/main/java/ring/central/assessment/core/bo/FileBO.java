package ring.central.assessment.core.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ring.central.common.page.PageBO;

/**
 * @Description: 文件BO
 * @Author: zuo.li zuo.li@luckincoffee.com
 * @Date: 2020/5/23 10:47
 */
@Setter
@Getter
@ToString(callSuper = true)
public class FileBO extends PageBO {

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件编号
     */
    private String fileNo;
}
