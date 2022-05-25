package com.ts.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UploadService {

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Value("${file.staticPath}")
    private String staticPath;


    public Map<String ,Object> uploadFile(MultipartFile multipartFile, String dir) throws IOException {
        //指定文件上传的目录，dir用于新建一个文件夹，前端可以指定dir的名字，方便文件的管理
        //不同文件放入不同文件夹中
        //如果目录不存在则递归创建目录，注意区分mkdirs和mkdir

        //获取原始文件名
        String originalFilename = multipartFile.getOriginalFilename();
        //截取文件后缀名,包括.也截取了
        String imgSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //生成新文件名
        String newFileName = UUID.randomUUID().toString() + imgSuffix;
        //生成日期格式路径
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String datePath = simpleDateFormat.format(new Date());
        //生成存储文件目标路径   D:\IDEA_project\tempFile\bbs\2022\05\25
        //指定文件上传目录
        String serverPath = uploadFolder;
        File targetFilePath = new File(serverPath + dir, datePath);
        //如果目录不存在则递归创建目录，注意区分mkdirs和mkdir
        if (!targetFilePath.exists()) {
            targetFilePath.mkdirs();
        }
        //指定上传的文件，参数为 文件目录 和 文件名
        File targetFileName = new File(targetFilePath, newFileName);
        //上传文件
        multipartFile.transferTo(targetFileName);

        //保存的文件路径
        String fileName = dir + "/" + datePath + "/" + newFileName;
        //return staticPath+"/upimages/"+fileName;

        HashMap<String, Object> map = new HashMap<>();
        map.put("url",staticPath+"/upimages/"+fileName);
        map.put("size",multipartFile.getSize());
        map.put("imgSuffix",imgSuffix);
        return map;
    }
}
