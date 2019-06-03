package com.beidasoft.xzfy.caseFile.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.beidasoft.xzfy.caseFile.bean.CaseFile;
import com.beidasoft.xzfy.caseFile.service.CaseFileService;
import com.tianee.webframe.httpmodel.TeeJson;

/**   
 * Description:案件材料controller
 * @title CaseFileController.java
 * @package com.beidasoft.xzfy.caseFile.controller 
 * @author zhangchengkun
 * @version 0.1 2019年5月7日
 */
@Controller
@RequestMapping("caseFileController")
public class CaseFileController {
	
	@Autowired
	CaseFileService caseFileService;
	
	/**
	 * Description:文件新增
	 * @author zhangchengkun
	 * @version 0.1 2019年5月7日
	 * @param request
	 * @param wj
	 * @return
	 * @throws Exception  TeeJson
	 */
	@ResponseBody
	@RequestMapping("/add")
	public TeeJson add(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
		// 获得前台上传的 全部文件
		//MultipartFile file = multipart.getFile("file");
		List<MultipartFile> listFile = fyAttachUpload(multipart);
		List<CaseFile> list = null;
		try {
			list = caseFileService.wjsc(request, listFile);
			json.setRtMsg("文件上传成功!");
			json.setRtState(true);
			json.setRtData(list);
		} catch (Exception e) {
			json.setRtMsg("文件上传失败,请联系管理员!");
			json.setRtState(false);
		}
		return json;
	}
	
	/**
	 * Description:根据文件id删除文件
	 * @author zhangchengkun
	 * @version 0.1 2019年5月7日
	 * @param request  void
	 */
	@SuppressWarnings("null")
	@ResponseBody
	@RequestMapping("/deleteById")
	public TeeJson deleteById(HttpServletRequest request){
		TeeJson json = null;
		String fileId = request.getParameter("fileId");
		// 查询符合条件的所有信息
		try {
			caseFileService.deleteById(fileId);
			json.setRtMsg("删除成功!");
			json.setRtState(true);
		} catch (Exception e) {
			json.setRtMsg("删除失败,请联系管理员!");
			json.setRtState(false);
		}
		return json;
	}
	
	/**
	 * Description:下载错误案件(备用方法)
	 * @author zhangchengkun
	 * @version 0.1 2019年5月7日
	 * @param request
	 * @param response
	 * @throws Exception  void
	 */
	@RequestMapping("downErrFile")
	@ResponseBody
	public void downErrFile(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String filePath = request.getParameter("filePath");
    	String fileName = request.getParameter("fileName");
    	File file = null;
    	try {
	    	file = new File(filePath);
	    	InputStream in;
	    	in = new FileInputStream(file);
	        OutputStream out;
	    	response.setHeader("Content-Disposition", "attachment;filename="+ java.net.URLEncoder.encode(fileName, "UTF-8"));
	    	out = response.getOutputStream(); 
	        //写文件  
	    	byte[] b = new byte[1024];
	        int len = 0;
	        while ((len = in.read(b)) != -1) {
	            out.write(b, 0, len);
	            out.flush();
	        }
	        in.close();  
	        in = null;
	        out.close(); 
	        out = null;
    	} catch (Exception e) {
			e.printStackTrace();
		} 
    }

	
	/**
	 * Description:文件上传获取文件集合
	 * @author zhangchengkun
	 * @version 0.1 2019年5月7日
	 * @param multipartRequest
	 * @return
	 * @throws IOException  List<MultipartFile>
	 */
	public static List<MultipartFile> fyAttachUpload(MultipartHttpServletRequest multipartRequest) throws IOException {
		List<MultipartFile> list = new ArrayList<MultipartFile>();
		Map<String, MultipartFile> files = multipartRequest.getFileMap();
		Set<String> keys = files.keySet();
		MultipartFile file = null;
		for (String key : keys) {
			file = files.get(key);
			if (file.isEmpty())
				continue;
			list.add(file);
		}
		return list;
	}
}
