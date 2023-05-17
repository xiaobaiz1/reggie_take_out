package com.dbb.reggie.controller;

import com.dbb.reggie.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
public class CommonController {
    //获取yml中的文件路径
    @Value("${reggie.path}")
    private String basePath;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        /*
            1.获取原文件名
            2.截取原文件后缀名
            3.使用uuid创建文件名并与后缀连接
            4.判断文件路径是否存在
            5.将临时文件存放到指定位置
         */
        //file是一个临时文件，需要转存到指定位置，否则本次请求结束后便会删除
        //原始的文件名
        String fileName = file.getOriginalFilename();
        //获取原文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //使用uuid生成文件名
        fileName = UUID.randomUUID().toString() + suffix;
        try {
            //创建一个文件basePath
            File dir = new File(basePath);
            //判断该文件路径是否存在
            if (!dir.exists()) {
                dir.mkdir();
            }
            //将临时文件上传到指定位置
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.success(fileName);
    }

    /**
     * 文件下载
     *
     * @param response
     * @param name
     */
    @GetMapping("/download")
    public void download(HttpServletResponse response, String name) {
        FileInputStream fileInputStream = null;
        ServletOutputStream outputStream = null;
        try {
            //通过输入流读取文件内容
            fileInputStream = new FileInputStream(new File(basePath + name));
            //通过输出流响应到前端页面
            outputStream = response.getOutputStream();
            //设置图片格式
            response.setContentType("image/jpeg");
            int len = 0;
            byte[] bytes = new byte[1024];
            //循环读取
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            fileInputStream.close();
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}




