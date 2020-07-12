package com.spring.gen.controller;

import com.github.pagehelper.PageInfo;
import com.spring.common.utils.Constants;
import com.spring.common.utils.PageUtils;
import com.spring.common.utils.Result;
import com.spring.common.xss.XssHttpServletRequestWrapper;
import com.spring.gen.pojo.TableInfo;
import com.spring.gen.service.SysGenService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器Controller类
 *
 * @author  团子
 * @date 2018/4/12 16:52
 * @since V1.0
 */
@Controller
@RequestMapping("/sys/gen")
public class SysGenController {
    @Autowired
    private SysGenService sysGenService;

    @GetMapping
    String gen() {
        return "/gen/gen";
    }
    /**
     * 列表
     */
    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("sys:gen:list")
    public Result list(@RequestParam Map<String, Object> params) {
        // 分页
        PageUtils.initPaging(params);
        // 查询数据
        List<TableInfo> list = sysGenService.queryList(params);
        PageInfo page = new PageInfo(list);
        HashMap dataMap = new HashMap(50);
        dataMap.put("rows", page.getList());
        dataMap.put("total", page.getTotal());
        return Result.datas(dataMap);
    }

    /**
     * 生成代码
     */
    @GetMapping("/code")
    @RequiresPermissions("sys:gen:code")
    public void code(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //不做xss过滤处理
        HttpServletRequest orgRequest = XssHttpServletRequestWrapper.getOrgRequest(request);
        String tables = orgRequest.getParameter("tables");
        String[] tableNames = tables.split(",");
        String fileName = StringUtils.join(tableNames, "&");
        if (fileName != null && fileName.length() >= Constants.FILENAME_MAX_LEN) {
            fileName = fileName.substring(Constants.FILENAME_MAX_LEN) + "..." + fileName.substring(fileName.length() - 1);
        }
        byte[] data = sysGenService.genCode(tableNames);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".zip");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}
