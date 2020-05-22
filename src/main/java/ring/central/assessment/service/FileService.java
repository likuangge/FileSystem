package ring.central.assessment.service;

import ring.central.assessment.core.ao.FileAO;

import java.io.IOException;

/**
 * @Description: 文件service
 * @Author: zuo.li zuo.li@luckincoffee.com
 * @Date: 2020/5/22 16:28
 */
public interface FileService {

    /**
     * 创建文件
     *
     * @param fileAO 文件信息
     * @throws IOException io异常
     */
    void create(FileAO fileAO) throws IOException;
}
