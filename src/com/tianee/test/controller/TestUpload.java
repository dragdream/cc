package com.tianee.test.controller;
import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/uploadfile")
public class TestUpload {

	@RequestMapping(params = "method=upload")
	@ResponseBody
    protected TeeJson uploadFile(HttpServletRequest request)
            throws Exception {
	 TeeJson j = new TeeJson();
	 j.setRtMsg("上传成功!");
        try{
            //cast to multipart file so we can get additional information
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
            String name = multipartRequest.getParameter("shenhua");
            System.out.println(name+"namenamenamenamenamenamenamename");
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            byte[] bytes = file.getBytes();
            String uploadDir = "d://uploadFile";
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + uploadDir);
            File dirPath = new File(uploadDir);
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }
            String sep = System.getProperty("file.separator");
//            if (log.isDebugEnabled()) {
//                log.debug("uploading to: " + uploadDir + sep +
//                file.getOriginalFilename());
//                }
            File uploadedFile = new File(uploadDir + sep
                    + file.getOriginalFilename());
            FileCopyUtils.copy(bytes, uploadedFile);
            System.out.println("********************************");
            System.out.println(uploadedFile.getAbsolutePath());
            System.out.println(bytes.length);
            System.out.println("********************************");
        }catch(Exception e){
            e.printStackTrace();
        }
        return  j;
    }
	@RequestMapping("test1")
	public String test1(){
		System.out.println("1111111");
		return "/oaop";
	}
}
