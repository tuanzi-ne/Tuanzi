package com.spring.profile.controller;

import com.spring.common.mvc.BaseController;
import com.spring.common.utils.Constants;
import com.spring.common.utils.FileUtils;
import com.spring.common.utils.Result;
import com.spring.profile.pojo.SysHeadInfo;
import com.spring.profile.service.SysHeadService;
import com.spring.sys.service.SysParamService;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @Title: SysHeadController
 * @Description: 系统头像
 * @author  团子
 * @date 2018/9/2 9:19
 * @since v1.0
 */
@Controller
@RequestMapping("/sys/head")
public class SysHeadController extends BaseController {

    @Autowired
    SysHeadService sysHeadService;
    @Autowired
    SysParamService sysParamService;

    @Value("${upload.img.path}")
    private String uploadPath;

    @GetMapping
    String head() {
        return "/profile/head";
    }
    /**
     * 查询头像
     * @param response 响应
     */
    @GetMapping("/img")
    public void img(HttpServletResponse response) {
        SysHeadInfo  sysHeadInfo = sysHeadService.queryHeadInfoById(getUserId());
        String imgName = null;
        if (sysHeadInfo != null) {
            imgName = sysHeadInfo.getHeadImgName();
        }
        File file = new File(uploadPath + File.separator + imgName);
        try {
            if (!(file.exists() && file.canRead())) {
                log.info("head is not found, default head is used");
                file = new ClassPathResource(Constants.DEFAULT_HEAD_PATH).getFile();
            }
            FileInputStream inputStream = new FileInputStream(file);
            String imgType = StringUtils.substringAfterLast(file.getName(), ".");
            if ("jpg".equals(imgType)) {
                imgType = "jpeg";
            }
            response.setContentType("image/" + imgType);
            response.setCharacterEncoding("UTF-8");
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            Streams.copy(inputStream, outputStream, true);
        } catch (FileNotFoundException fnfe) {
            log.error("head file is not found, check filepath:{}, exception:{}",
                    file.getPath(), ExceptionUtils.getStackTrace(fnfe));
        } catch (IOException ioe) {
            log.error("get head I/O error occurred,exception:{}", ExceptionUtils.getFullStackTrace(ioe));
        }
    }

    /**
     * 前端上传头像保存到指定目录
     * @param head 生成的头像上传文件
     */
    @PostMapping("/upload")
    @ResponseBody
    public Result upload(@RequestParam("uploadFile") MultipartFile head) {
        String headFileName;
        String headPath;
        // 如果头像存在则先删除再上传
        SysHeadInfo hInfo = sysHeadService.queryHeadInfoById(getUserId());
        if (hInfo != null) {
            File headFile = new File(uploadPath + File.separator + hInfo.getHeadImgName());
            if (headFile.exists()) {
                headFile.delete();
            }
        }
        headFileName = getUserId() + "-face-" + FileUtils.genFileSuffix(8) + "." + StringUtils.substringAfterLast(head.getContentType(), "/");
        headPath = uploadPath + File.separator + headFileName;
        try {
            head.transferTo(new File(headPath));
            SysHeadInfo headInfo = new SysHeadInfo();
            headInfo.setUserId(getUserId());
            headInfo.setHeadImgName(headFileName);
            sysHeadService.save(headInfo);
            return Result.info("头像上传成功！");
        } catch (Exception e) {
            log.error("upload file failed,exception:{}", ExceptionUtils.getStackTrace(e));
            return Result.error("头像上传失败,请检查原因！");
        }
    }
}
