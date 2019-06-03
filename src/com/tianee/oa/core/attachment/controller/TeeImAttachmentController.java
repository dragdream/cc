package com.tianee.oa.core.attachment.controller;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.attachment.bean.TeeBaseStream;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseDownloadService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.workFlowFrame.dataloader.TeeSimpleDataLoaderInterface;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("/imAttachment")
public class TeeImAttachmentController {
	
	@Autowired
	private TeeBaseDownloadService baseDownloadService;
	
	@Autowired
	private TeeAttachmentService attachmentService;
	
	@Autowired
	private TeeSimpleDataLoaderInterface dataLoaderInterface;
	
	@Autowired
	private TeePersonService personService;
	
	
	/**
	 * 精灵下载文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/downFile")
	@ResponseBody
	public String downFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sAttachId = request.getParameter("id");
		long vt = TeeStringUtil.getLong(request.getParameter("vt"), 0);
		if(new Date().getTime()-vt>1000*60*10){//60秒下载有效期限，如果超过，则不允许下载
			return "文件无效或已过期";
		}
		
		int attachId = TeeStringUtil.getInteger(sAttachId, 0);
		OutputStream ops = null;
		InputStream is = null;
		TeeBaseStream baseStream = null;
		String fileName = null;
		try {
			
			fileName = attachmentService.getFileNameById(attachId);
			String contentTypeDesc = baseDownloadService.getContentType(fileName);
			if (contentTypeDesc != null) {
				response.setContentType(contentTypeDesc);
			} else {
				response.setContentType("application/octet-stream");
			}
			
			//是否为不可在线编辑的缓存文件，如果不是的话，则走客户端缓存
			boolean isEditable = baseDownloadService.isEditableFile(fileName);
			if(!isEditable){
				String IfNoneMatch = request.getHeader("If-None-Match");
				if(TeeUtility.isNullorEmpty(IfNoneMatch)){//如果请求中没有超时时限，则写入一个超时的实现
					response.setHeader("ETag", Long.toString(new Date().getTime()));
					response.setStatus(HttpServletResponse.SC_OK);
				}else{
					response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
					return null;
				}
			}
			
			
			baseStream = baseDownloadService.getTeeBaseStream(attachId);
			is = baseStream.getFileInputStream();
			fileName = baseStream.getFileName();
			fileName = fileName.replaceAll("\\+", "%20");
			response.setHeader("Cache-control", "private");
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Cache-Control", "maxage=3600");
			response.setHeader("Pragma", "public");
			response.setHeader("Accept-Length", String.valueOf(baseStream
					.getFileSize()));
			response.setHeader("Content-Length", String.valueOf(baseStream
					.getFileSize()));
			response.setHeader("Content-disposition", "attachment; filename=\""
					+ URLEncoder.encode(fileName, "UTF-8") + "\"");
			ops = response.getOutputStream();
			ops.flush();
			if (is != null) {
				byte[] buff = new byte[1024*50];
				int byteread = 0;
				while ((byteread = is.read(buff)) != -1) {
					ops.write(buff, 0, byteread);
					ops.flush();
				}
			}
		} catch (Exception ex) {
//			ex.printStackTrace();
//			throw ex;
		} finally {
			if(baseStream!=null){
				baseStream.close();
			}
		}
		return null;
	}
	
	/**
	 * 检查文件状态
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkFileComplele")
	@ResponseBody
	public TeeJson checkFileComplete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TeeJson json = new TeeJson();
		String id = request.getParameter("id");
		String ext = request.getParameter("ext");
		File file = new File(TeeSysProps.getString("IM_FOLDER")+File.separator+id+"."+ext);
		if(!file.exists()){
			json.setRtData(-1);
			json.setRtMsg("文件不存在");
		}else{
			if(file.canWrite()){
				json.setRtData(0);
				json.setRtMsg("文件传输中");
			}else{
				json.setRtData(1);
				json.setRtMsg("传输完毕");
			}
		}
		return json;
	}
	
	
	
	/**
	 * 表单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/printRunHtml")
	public void printRunHtml(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
//		long vt = TeeStringUtil.getLong(request.getParameter("vt"), 0);
//		if(new Date().getTime()-vt>1000*60*10){//60秒下载有效期限，如果超过，则不允许下载
//			return;
//		}
		
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		
		TeePerson loginPerson = personService.getPersonByUserId("admin");
		
		Map requestData = new HashMap();
		requestData.put("runId", runId);
		requestData.put("total", 1);
		requestData.put("view", 1);
		
		String html = dataLoaderInterface.getFormPrintDataStream(requestData, loginPerson);
		response.getWriter().write(html);
	}
}
