package ring.central.assessment.core.ao;

import lombok.Data;

/**
 * @Description: 文件AO
 * @Author: zuo.li zuo.li@luckincoffee.com
 * @Date: 2020/5/22 15:50
 */
@Data
public class FileAO {

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件内容
     */
    private String fileContent;
}
