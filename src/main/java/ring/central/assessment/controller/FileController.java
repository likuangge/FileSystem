package ring.central.assessment.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ring.central.assessment.common.result.ResultInfo;
import ring.central.assessment.core.ao.FileAO;
import ring.central.assessment.service.FileService;

/**
 * @Description: 文件系统controller
 * @Author: zuo.li zuo.li@luckincoffee.com
 * @Date: 2020/5/22 15:24
 */
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RequestMapping("/file")
@RestController
@Slf4j
public class FileController {

    /**
     * 文件service
     */
    @Autowired
    private FileService fileService;

    /**
     * 创建文件
     *
     * @param fileAO 文件信息
     * @return 操作结果
     */
    @RequestMapping("/create")
    @ResponseBody
    public ResultInfo create(@RequestBody FileAO fileAO) {
        log.info("创建文件，入参:{}", fileAO.toString());
        try {
            fileService.create(fileAO);
            return ResultInfo.success();
        } catch (Exception e) {
            log.error("错误信息:", e);
            return ResultInfo.errorMessage("系统内部错误");
        }
    }
}
