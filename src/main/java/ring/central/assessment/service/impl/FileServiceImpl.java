package ring.central.assessment.service.impl;

import org.springframework.stereotype.Service;
import ring.central.assessment.core.ao.FileAO;
import ring.central.assessment.service.FileService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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

    @Override
    public void create(FileAO fileAO) throws IOException {
        String filePath = FILE_DIR + "\\" + fileAO.getFileName();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
        bufferedWriter.write(fileAO.getFileContent());
        bufferedWriter.close();
    }
}
