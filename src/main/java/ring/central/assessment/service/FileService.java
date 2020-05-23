package ring.central.assessment.service;

import ring.central.assessment.core.ao.FileAO;
import ring.central.assessment.core.bo.FileBO;
import ring.central.assessment.core.vo.FileVO;
import ring.central.common.result.PaginationResult;

import java.io.File;
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

    /**
     * 获取文件列表
     *
     * @param fileBO 文件信息
     * @return 文件列表
     */
    PaginationResult<FileVO> getList(FileBO fileBO);

    /**
     * 获取下载文件内容
     *
     * @param fileNo 文件编号
     * @return 下载文件内容
     */
    File getDownloadFile(String fileNo);
}
