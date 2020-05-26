package ring.central.assessment.service;

import ring.central.assessment.core.ao.FileAO;
import ring.central.assessment.core.bo.FileBO;
import ring.central.assessment.core.vo.FileVO;
import ring.central.assessment.core.vo.ReadLockVO;
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
     * @throws IOException IO异常
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

    /**
     * 获取文件读锁
     *
     * @param fileNo 文件编号
     * @return 文件读锁
     */
    ReadLockVO getReadLock(String fileNo);

    /**
     * 通过文件编号获取文件详情
     *
     * @param fileNo 文件编号
     * @throws IOException IO异常
     * @return 文件详情
     */
    FileVO getFile(String fileNo) throws IOException;

    /**
     * 保存文件
     *
     * @param fileAO 文件信息
     * @throws IOException IO异常
     */
    void saveFile(FileAO fileAO) throws IOException;

    /**
     * 删除文件读锁
     *
     * @param fileAO 文件信息
     */
    void delLock(FileAO fileAO);
}
