package ring.central.assessment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ring.central.assessment.core.ao.FileAO;
import ring.central.assessment.core.bo.FileBO;
import ring.central.assessment.core.entity.FileDO;
import ring.central.assessment.core.vo.FileVO;
import ring.central.assessment.dao.FileDao;
import ring.central.assessment.service.FileService;
import ring.central.common.result.PaginationResult;
import ring.central.util.SnowFlakeUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 文件service接口实现
 * @Author: zuo.li zuo.li@luckincoffee.com
 * @Date: 2020/5/22 16:29
 */
@Service
public class FileServiceImpl implements FileService {

    /**
     * 文件存放目录
     */
    private static final String FILE_DIR = "C:\\Project\\FileSystem\\file";

    /**
     * 文件分隔符
     */
    private static final String FILE_SEPARATOR = "\\";

    /**
     * 雪花编号生成工具（唯一）
     */
    private static final SnowFlakeUtil SNOW_FLAKE_UTIL = new SnowFlakeUtil(0, 0);

    /**
     * 文件dao
     */
    @Autowired
    private FileDao fileDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void create(FileAO fileAO) throws IOException {
        //文件信息落库
        long fileNo = SNOW_FLAKE_UTIL.nextId();
        FileDO fileDO = new FileDO();
        fileDO.setFileNo(Long.toString(fileNo));
        fileDO.setFileName(fileAO.getFileName());
        fileDO.setCreateTime(new Date());
        fileDao.create(fileDO);
        //本地写入文件
        String filePath = FILE_DIR + FILE_SEPARATOR + fileAO.getFileName();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
        bufferedWriter.write(fileAO.getFileContent());
        bufferedWriter.close();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PaginationResult<FileVO> getList(FileBO fileBO) {
        //分页获取文件列表
        List<FileDO> fileDOS = fileDao.getListByPage(fileBO);
        List<FileVO> fileList = new ArrayList<>();
        for(FileDO fileDO : fileDOS) {
            FileVO fileVO = convertFileDO2VO(fileDO);
            fileList.add(fileVO);
        }
        //获取文件总数
        Long count = fileDao.getFileCount(fileBO);
        return new PaginationResult<>(count, fileList);
    }

    @Override
    public File getDownloadFile(String fileNo) {
        FileBO fileBO = fileDao.getFileByNo(fileNo);
        return new File(FILE_DIR + FILE_SEPARATOR + fileBO.getFileName());
    }

    /**
     * 文件DO转VO
     *
     * @param fileDO 文件DO
     * @return 文件VO
     */
    private FileVO convertFileDO2VO(FileDO fileDO) {
        FileVO fileVO = new FileVO();
        fileVO.setFileNo(fileDO.getFileNo());
        fileVO.setFileName(fileDO.getFileName());
        fileVO.setCreateTime(fileDO.getCreateTime());
        return fileVO;
    }
}
