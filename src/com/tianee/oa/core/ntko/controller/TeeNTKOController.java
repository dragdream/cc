package com.tianee.oa.core.ntko.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashMap;
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

import com.tianee.oa.core.attachment.bean.TeeBaseStream;
import com.tianee.oa.core.attachment.service.TeeBaseDownloadService;
import com.tianee.oa.core.ntko.service.TeeNTKOService;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("/ntko")
public class TeeNTKOController {

	@Autowired
	private TeeBaseDownloadService baseDownloadService;

	@Autowired
	private TeeNTKOService teeNTKOServices;
	
	@RequestMapping("/newWord")
	public String upload(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		    OutputStream ops = null;
		    InputStream is = null;
		    try {
		      
		     is = new FileInputStream(new File("d:/1231.doc"));
		      response.setHeader("Cache-control","private");
		      response.setContentType("application/octet-stream");
		      response.setHeader("Cache-Control","maxage=3600");
		      response.setHeader("Pragma","public");
		      response.setHeader("Accept-Ranges","bytes");
		      response.setHeader("Content-disposition","filename=" + "神化测试");
		      //response.setHeader("Accept-Length",String.valueOf(ns.getFileSize()));
		      //response.setHeader("Content-Length",String.valueOf(ns.getFileSize()));
		      ops = response.getOutputStream();
		      if(is != null){
		        byte[] buff = new byte[8192];
		        int byteread = 0;
		        while( (byteread = is.read(buff)) != -1){
		          ops.write(buff,0,byteread);
		          ops.flush();
		        }
		      }
		      //System.out.println(ns.toString());
		      //System.out.println(response.getContentType());
		    } catch (Exception ex) {
		      ex.printStackTrace();
		    } finally {
		      if (is != null) {
		        is.close();
		      }
		    }
		    return null;
	}
	/**
	 * 阅读 附件 主要是权限 
	 * 现在还没有在这里处理权限 
	 * 不知道该不该加入一个接口 在这里处理一下 
	 * 或者是在之前就先判断一下 这样使得阅读等方法透明 
	 * 不用处理权限等其他事宜
	 * @author zhp
	 * @createTime 2013-10-17
	 * @editTime 上午11:19:09
	 * @desc
	 */
	@RequestMapping("/readFile")
	public String downFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sAttachId = request.getParameter("id");
		String model = request.getParameter("model");
		model = TeeStringUtil.getString(model, "");
		int attachId = TeeStringUtil.getInteger(sAttachId, 0);
		OutputStream ops = null;
		InputStream is = null;
		Connection conn = null;
		TeeBaseStream baseStream = null;
		try {
			String fileType = request.getParameter("fileType");
			baseStream = baseDownloadService.getTeeBaseStream(attachId);
			
			is = baseStream.getFileInputStream();
//			HashMap<String, String> contentTypeMap = (HashMap<String, String>) baseDownloadService
//					.getAttachHeard(baseStream.getFileName());
//			String contentType = contentTypeMap.get("contentType");
//			String contentTypeDesc = contentTypeMap.get("contentTypeDesc");
//			String fileName = baseStream.getFileName();
//			fileName = fileName.replaceAll("\\+", "%20");
//			response.setHeader("Cache-control", "private");
//			if (contentTypeDesc != null) {
//				response.setContentType(contentTypeDesc);
//			} else {
//				response.setContentType("application/octet-stream");
//			}
//			response.setHeader("Accept-Ranges", "bytes");
//			response.setHeader("Cache-Control", "maxage=3600");
//			response.setHeader("Pragma", "public");
//			response.setHeader("Accept-Length", String.valueOf(baseStream
//					.getFileSize()));
//			response.setHeader("Content-Length", String.valueOf(baseStream
//					.getFileSize()));
//			response.setHeader("Content-disposition", "attachment; filename=\""
//					+ fileName + "\"");
			ops = response.getOutputStream();
			if (is != null) {
				byte[] buff = new byte[8192];
				int byteread = 0;
				while ((byteread = is.read(buff)) != -1) {
					ops.write(buff, 0, byteread);
//					ops.flush();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (is != null) {
				is.close();
			}
		}
//		ops.flush();
		return null;
	}
	
	@RequestMapping("/updateFile")
	@ResponseBody
	public TeeJson updateFile(HttpServletRequest request,HttpServletResponse response) throws IOException{
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 	TeeJson json = new TeeJson();
//			MultipartFile file = multipartRequest.getFile("file");
			String model = multipartRequest.getParameter("model");
			int id = TeeStringUtil.getInteger(multipartRequest.getParameter("attachmentId"), 0);
			
			boolean resule = false;
			Map<String,MultipartFile> files = multipartRequest.getFileMap();
			Set<String> keys = files.keySet();
			for(String key:keys){
				resule = teeNTKOServices.updateFile(files.get(key), model, id);
				break;
			}
			if(resule){
				json.setRtMsg("更新文件成功!");
			 	json.setRtState(true);
			}else{
				json.setRtMsg("更新文件失败!");
			 	json.setRtState(false);
			}
			
		return json;
	}
	
	public TeeNTKOService getTeeNTKOServices() {
		return teeNTKOServices;
	}
	
	public void setTeeNTKOServices(TeeNTKOService teeNTKOServices) {
		this.teeNTKOServices = teeNTKOServices;
	}
	
	public TeeBaseDownloadService getBaseDownloadService() {
		return baseDownloadService;
	}

	public void setBaseDownloadService(
			TeeBaseDownloadService baseDownloadService) {
		this.baseDownloadService = baseDownloadService;
	}
}
