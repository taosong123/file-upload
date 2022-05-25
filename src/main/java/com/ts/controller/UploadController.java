package com.ts.controller;

import com.ts.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.Map;

@Controller
public class UploadController {

    @Autowired
    UploadService uploadService ;

    @PostMapping("/upload/file")
    @ResponseBody
    public Map<String ,Object>  uploadFile(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) throws IOException {

        if (multipartFile.isEmpty()) {
            return null;
        }
        String dir = request.getParameter("dir");
        Map<String, Object> map = uploadService.uploadFile(multipartFile, dir);

        return map;

    }
}
