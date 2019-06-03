package com.tianee.oa.core.attachment.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseDownloadService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/voiceUploadController")
public class TeeVoiceUploadController {
	@Autowired
	private TeeBaseUpload baseUpload;
	
	@Autowired
	private TeeBaseDownloadService baseDownloadService;
	
	@Autowired
	private TeeAttachmentService attachmentService;
	
	/**
	 * 移动端上传录制音频
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/mobileUpload")
	@ResponseBody
	public Map mobileUpload(HttpServletRequest request) throws IOException{
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String model = TeeStringUtil.getString(multipartRequest.getParameter("model"));
		
		List<TeeAttachment> attachs = baseUpload.manyAttachUpload(multipartRequest, model);
		
		Map param = new HashMap();
		param.put("id", attachs.get(0).getSid());
		
		return param;
	}
}
