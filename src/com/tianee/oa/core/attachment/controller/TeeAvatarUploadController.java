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
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("/avatarUploadController")
public class TeeAvatarUploadController {
	@Autowired
	private TeeBaseUpload baseUpload;
	
	@Autowired
	private TeeBaseDownloadService baseDownloadService;
	
	@Autowired
	private TeeAttachmentService attachmentService;
	
	@Autowired
	private TeePersonService personService;
	
	@RequestMapping("/upload")
	@ResponseBody
	public Map upload(HttpServletRequest request) throws IOException{
		Map json = new HashMap();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String model = TeeStringUtil.getString(multipartRequest.getParameter("model"));
		String userId = TeeStringUtil.getString(multipartRequest.getParameter("userId"));
		
		
		List<TeeAttachmentModel> list = new ArrayList<TeeAttachmentModel>();
		List<TeeAttachment> attachs = baseUpload.manyAttachUpload(multipartRequest, model);
		
		for(TeeAttachment attach:attachs){
			TeeAttachmentModel am = new TeeAttachmentModel();
			BeanUtils.copyProperties(attach, am);
			am.setCreateTimeDesc(TeeDateUtil.format(attach.getCreateTime()));
			am.setSizeDesc(TeeFileUtility.getFileSizeDesc(attach.getSize()));
			
			//新上传的附件设置为所有权限
			am.setPriv(1|2|4|8|16|32|64|128);
			list.add(am);
			
			if(!TeeUtility.isNullorEmpty(userId)){
				TeePerson person = personService.getPersonByUserId(userId);
				person.setAvatar(String.valueOf(am.getSid()));
				personService.updatePerson(person);
			}
		}
		
		
//		json.setRtMsg("上传成功");
//		json.setRtState(true);
//		json.setRtData(list);
		json.put("success", true);
		json.put("sid", list.get(0).getSid()+"");
		json.put("code", 5);
		return json;
	}
}
