package ring.central.assessment.dao;

import org.apache.ibatis.annotations.Mapper;
import ring.central.assessment.core.bo.FileBO;
import ring.central.assessment.core.entity.FileDO;

import java.util.List;

/**
 * @Description: 文件dao
 * @Author: zuo.li zuo.li@luckincoffee.com
 * @Date: 2020/5/23 10:36
 */
@Mapper
public interface FileDao {

    /**
     * 创建文件
     *
     * @param fileDO 文件信息
     */
    void create(FileDO fileDO);

    /**
     * 分页获取文件列表
     *
     * @param fileBO 文件信息
     * @return 文件列表
     */
    List<FileDO> getListByPage(FileBO fileBO);

    /**
     * 获取文件数量
     *
     * @param fileBO 文件信息
     * @return 文件数量
     */
    Long getFileCount(FileBO fileBO);

    /**
     * 通过文件编号获取文件信息
     *
     * @param fileNo 文件编号
     * @return 文件信息
     */
    FileBO getFileByNo(String fileNo);
}
