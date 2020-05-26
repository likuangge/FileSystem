package ring.central.assessment.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ring.central.assessment.core.bo.FileBO;
import ring.central.common.CommonConstants;
import ring.central.common.page.PageAO;
import ring.central.common.page.PageBO;
import ring.central.common.result.ResultInfo;
import ring.central.assessment.core.ao.FileAO;
import ring.central.assessment.service.FileService;
import ring.central.util.HttpUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

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
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo create(@RequestBody FileAO fileAO) {
        if(StringUtils.isEmpty(fileAO.getFileName())) {
            return ResultInfo.errorMessage("请输入文件名称");
        }
        log.info("创建文件，入参:{}", fileAO.toString());
        try {
            fileService.create(fileAO);
            return ResultInfo.success("新建文件成功");
        } catch (IOException e) {
            log.error("错误信息:", e);
            return ResultInfo.errorMessage("写入文件失败");
        } catch (Exception e) {
            log.error("错误信息:", e);
            return ResultInfo.errorMessage("系统内部错误");
        }
    }

    /**
     * 分页获取文件列表
     *
     * @param fileAO 文件信息
     * @return 操作结果
     */
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getList(@RequestBody FileAO fileAO) {
        log.info("分页获取文件列表，入参:{}", fileAO.toString());
        try {
            FileBO fileBO = new FileBO();
            convertToPageBO(fileAO, fileBO);
            return ResultInfo.success(fileService.getList(fileBO));
        } catch (Exception e) {
            log.error("错误信息:", e);
            return ResultInfo.errorMessage("系统内部错误");
        }
    }

    @RequestMapping(value = "/download")
    public void download(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String fileNo = httpServletRequest.getParameter("fileNo");
        log.info("下载文件，文件编号:{}", fileNo);
        try {
            File file = fileService.getDownloadFile(fileNo);
            if (file.exists() && file.isFile()) {
                HttpUtil.download(file, httpServletResponse);
            }
        } catch (Exception e) {
            log.error("错误信息:", e);
        }
    }

    /**
     * 获取文件读锁
     *
     * @param fileNo 文件编号
     * @return 文件读锁
     */
    @RequestMapping(value = "/getReadLock", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getReadLock(@RequestParam String fileNo) {
        if(StringUtils.isEmpty(fileNo)) {
            return ResultInfo.errorMessage("请选择一个文件");
        }
        log.info("获取文件读锁，入参:{}", fileNo);
        try {
            return ResultInfo.success(fileService.getReadLock(fileNo));
        } catch (Exception e) {
            log.error("错误信息:", e);
            return ResultInfo.errorMessage("系统内部错误");
        }
    }

    /**
     * 获取文件详情
     *
     * @param fileNo 文件编号
     * @return 文件详情
     */
    @RequestMapping(value = "getFile", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getFile(@RequestParam String fileNo) {
        if(StringUtils.isEmpty(fileNo)) {
            return ResultInfo.errorMessage("无文件编号，无法获取文件");
        }
        log.info("获取文件详情，入参:{}", fileNo);
        try {
            return ResultInfo.success(fileService.getFile(fileNo));
        } catch (IOException e) {
            log.error("错误信息:", e);
            return ResultInfo.errorMessage("读取文件失败");
        } catch (Exception e) {
            log.error("错误信息:", e);
            return ResultInfo.errorMessage("系统内部错误");
        }
    }

    /**
     * 保存文件
     *
     * @param fileAO 文件信息
     * @return 操作结果
     */
    @RequestMapping(value = "/saveFile", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo saveFile(@RequestBody FileAO fileAO) {
        log.info("保存文件，入参:{}", fileAO.toString());
        try {
            fileService.saveFile(fileAO);
            return ResultInfo.success();
        } catch (IOException e) {
            log.error("错误信息:", e);
            return ResultInfo.errorMessage("写文件失败");
        } catch (RuntimeException e) {
            return ResultInfo.errorMessage(e.getMessage());
        } catch (Exception e) {
            log.error("错误信息:", e);
            return ResultInfo.errorMessage("系统内部错误");
        }
    }

    /**
     * 删除文件读锁
     *
     * @param fileAO 文件信息
     * @return 操作结果
     */
    @RequestMapping(value = "/delLock", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo delLock(@RequestBody FileAO fileAO) {
        log.info("删除文件读锁， 入参:{}", fileAO.toString());
        try {
            fileService.delLock(fileAO);
            return ResultInfo.success();
        } catch (Exception e) {
            log.error("错误信息:", e);
            return ResultInfo.errorMessage("系统内部错误");
        }
    }

    /**
     * 分页对象转换
     *
     * @param pageAO 公共分页AO
     * @param pageBO 公共分页BO
     */
    private void convertToPageBO(PageAO pageAO, PageBO pageBO) {
        pageBO.setPageNo(pageAO.getPage());
        // 每页条数为0，则设置默认
        if (pageAO.getRows() == CommonConstants.INT_ZERO) {
            pageBO.setPageSize(CommonConstants.SIZE);
        } else {
            pageBO.setPageSize(pageAO.getRows());
        }
    }
}
