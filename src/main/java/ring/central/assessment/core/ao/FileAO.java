package ring.central.assessment.core.ao;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ring.central.common.page.PageAO;

/**
 * @Description: 文件AO
 * @Author: zuo.li zuo.li@luckincoffee.com
 * @Date: 2020/5/22 15:50
 */
@Setter
@Getter
@ToString(callSuper = true)
public class FileAO extends PageAO {

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件内容
     */
    private String fileContent;
}
