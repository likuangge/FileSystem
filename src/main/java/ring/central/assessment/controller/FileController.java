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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

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
            if (file.exists()) {
                // 配置文件下载
                httpServletResponse.setHeader("content-type", "application/octet-stream");
                httpServletResponse.setContentType("application/octet-stream");
                // 下载文件能正常显示中文
                httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
                // 实现文件下载
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = httpServletResponse.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    log.info("文件下载成功");
                } catch (Exception e) {
                    log.error("文件下载失败");
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            log.error("BufferedInputStream关闭失败:", e);
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            log.error("FileInputStream关闭失败:", e);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("错误信息:", e);
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
        log.error("获取文件详情，入参:{}", fileNo);
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
