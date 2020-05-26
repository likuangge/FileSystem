package ring.central.assessment.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ring.central.assessment.core.ao.FileAO;
import ring.central.assessment.core.bo.FileBO;
import ring.central.assessment.core.entity.FileDO;
import ring.central.assessment.core.vo.FileVO;
import ring.central.assessment.core.vo.ReadLockVO;
import ring.central.assessment.dao.FileDao;
import ring.central.assessment.service.FileService;
import ring.central.common.CommonConstants;
import ring.central.common.result.PaginationResult;
import ring.central.util.FileUtil;
import ring.central.util.RedisUtil;
import ring.central.util.SnowFlakeUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 文件service接口实现
 * @Author: zuo.li zuo.li@luckincoffee.com
 * @Date: 2020/5/22 16:29
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    /**
     * 雪花编号生成工具（唯一）
     */
    private static final SnowFlakeUtil SNOW_FLAKE_UTIL = new SnowFlakeUtil(0, 0);

    /**
     * 线程池
     */
    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(3, 6, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    /**
     * 文件dao
     */
    @Autowired
    private FileDao fileDao;

    /**
     * redis工具类
     */
    @Autowired
    private RedisUtil redisUtil;

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
        FileUtil.writeFile(fileAO.getFileName(), fileAO.getFileContent());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PaginationResult<FileVO> getList(FileBO fileBO) {
        //分页获取文件列表
        List<FileDO> fileDOS = fileDao.getListByPage(fileBO);
        List<FileVO> fileList = new ArrayList<>();
        for (FileDO fileDO : fileDOS) {
            FileVO fileVO = new FileVO();
            fileVO.setFileNo(fileDO.getFileNo());
            fileVO.setFileName(fileDO.getFileName());
            fileVO.setCreateTime(fileDO.getCreateTime());
            fileList.add(fileVO);
        }
        //获取文件总数
        Long count = fileDao.getFileCount(fileBO);
        return new PaginationResult<>(count, fileList);
    }

    @Override
    public File getDownloadFile(String fileNo) {
        FileBO fileBO = fileDao.getFileByNo(fileNo);
        return FileUtil.getFile(fileBO.getFileName());
    }

    @Override
    public ReadLockVO getReadLock(String fileNo) {
        //获取线程id
        Long threadId = Thread.currentThread().getId();
        ReadLockVO readLockVO = new ReadLockVO();
        if (redisUtil.set(fileNo, threadId, CommonConstants.EXPIRE_TIME)) {
            readLockVO.setReadLock(CommonConstants.INT_ONE);
            readLockVO.setThreadId(threadId);
        } else {
            readLockVO.setReadLock(CommonConstants.INT_ZERO);
        }
        return readLockVO;
    }

    @Override
    public FileVO getFile(String fileNo) throws IOException {
        FileVO fileVO = new FileVO();
        //获取文件信息
        FileBO fileBO = fileDao.getFileByNo(fileNo);
        fileVO.setFileNo(fileBO.getFileNo());
        fileVO.setFileName(fileBO.getFileName());
        //读取文件内容
        fileVO.setFileContent(FileUtil.readFile(fileBO.getFileName()));
        return fileVO;
    }

    @Override
    public void saveFile(FileAO fileAO) throws IOException {
        if (Objects.isNull(redisUtil.get(fileAO.getFileNo())) || !Objects.equals(redisUtil.get(fileAO.getFileNo()), fileAO.getThreadId())) {
            throw new RuntimeException("编辑已过期， 请重新进入编辑页面进行编辑");
        } else {
            protectThread(fileAO);
            FileUtil.writeFile(fileAO.getFileName(), fileAO.getFileContent());
            redisUtil.del(fileAO.getFileNo());
        }
    }

    @Override
    public void delLock(FileAO fileAO) {
        protectThread(fileAO);
        if (Objects.nonNull(redisUtil.get(fileAO.getFileNo())) && Objects.equals(redisUtil.get(fileAO.getFileNo()), fileAO.getThreadId())) {
            redisUtil.del(fileAO.getFileNo());
        }
    }

    /**
     * redis get与del操作的守护线程
     *
     * @param fileAO 文件信息
     */
    private void protectThread(FileAO fileAO) {
        THREAD_POOL_EXECUTOR.execute(() -> {
            while (true) {
                try {
                    if (Objects.isNull(redisUtil.get(fileAO.getFileNo())) || !Objects.equals(redisUtil.get(fileAO.getFileNo()), fileAO.getThreadId())) {
                        break;
                    }
                    redisUtil.expire(fileAO.getFileNo(), CommonConstants.EXPIRE_TIME);
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    log.error("线程执行出错:", e);
                    throw new RuntimeException("守护线程执行出错");
                }
            }
        });
    }
}
