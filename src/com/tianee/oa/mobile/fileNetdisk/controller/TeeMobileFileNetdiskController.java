package com.tianee.oa.mobile.fileNetdisk.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.base.fileNetdisk.service.TeeFileNetdiskPersonService;
import com.tianee.oa.core.base.fileNetdisk.service.TeeFileNetdiskService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.mobile.fileNetdisk.service.TeeMobileFileNetdiskService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/mobileFileNetdiskController")
public class TeeMobileFileNetdiskController extends BaseController {
	@Autowired
    private TeeMobileFileNetdiskService mobileFileNetdiskService ;
	
	@Autowired
	private TeeFileNetdiskService fileNetdiskService;
	
	@Autowired
	private TeeFileNetdiskPersonService fileNetdiskPersonService;
	
	@Autowired
	private TeeBaseUpload baseUpload;
	
	@Autowired
	private TeeAttachmentService attachmentService;
	
	/**
	 * 获取个人文件/文件夹通用个列表
	 * @param dm
	 * @param response
	 * @return
	 */
    @RequestMapping("/getPersonFilePage")
    @ResponseBody
    public TeeEasyuiDataGridJson getPersonFilePage(TeeDataGridModel dm, HttpServletRequest response) {
        return mobileFileNetdiskService.getPersonFilePage(dm, response);
    }
    
    /**
	 * 获取个人文件/文件夹通用个列表
	 * @param dm
	 * @param response
	 * @return
	 */
    @RequestMapping("/searchPersonFiles")
    @ResponseBody
    public TeeEasyuiDataGridJson searchPersonFiles(TeeDataGridModel dm, HttpServletRequest request) {
        return mobileFileNetdiskService.searchPersonFiles(dm, request);
    }
    
    
    /**
	 * 获取公共网盘 通用个列表
	 * @param dm
	 * @param response
	 * @return
	 */
    @RequestMapping("/getPublicFilePage")
    @ResponseBody
    public TeeEasyuiDataGridJson getPublicFilePage(TeeDataGridModel dm, HttpServletRequest response) {
        return mobileFileNetdiskService.getPublicFilePage(dm, response);
    }
    
    
    /**
   	 * 获取个人文件/文件夹通用个列表
   	 * @param dm
   	 * @param response
   	 * @return
   	 */
       @RequestMapping("/searchPublicFiles")
       @ResponseBody
       public TeeEasyuiDataGridJson searchPublicFiles(TeeDataGridModel dm, HttpServletRequest request) {
           return mobileFileNetdiskService.searchPublicFiles(dm, request);
       }
       
       
     /**
   	 * 上传个人文件
   	 * 
   	 * @date 2014-2-13
   	 * @author
   	 * @param request
   	 * @return
   	 * @throws IOException
   	 */
   	@RequestMapping("/uploadPersonalFile")
   	@ResponseBody
   	public TeeJson uploadPersonalFile(HttpServletRequest request) throws IOException {
   		TeeJson json = new TeeJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String model = "fileNetdiskPerson";
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String folderId = multipartRequest.getParameter("folderId");
		
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
			
	        fileNetdiskPersonService.uploadNetdiskFileServbice(folderId, attach.getSid()+"", person);
		}
		
		json.setRtMsg("上传成功");
		json.setRtState(true);
		json.setRtData(list);
		
		return json;
   	}
   	
   	
   	/**
   	 * 上传公共文件
   	 * 
   	 * @date 2014-2-13
   	 * @author
   	 * @param request
   	 * @return
   	 * @throws IOException
   	 */
   	@RequestMapping("/uploadPublicFile")
   	@ResponseBody
   	public TeeJson uploadPublicFile(HttpServletRequest request) throws IOException {
   		TeeJson json = new TeeJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String model = "fileNetdisk";
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String folderId = multipartRequest.getParameter("folderId");
		
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
			
			fileNetdiskService.uploadNetdiskFileServbice(folderId, attach.getSid()+"", "0","0",person,0);
		}
		
		json.setRtMsg("上传成功");
		json.setRtState(true);
		json.setRtData(list);
		
		return json;
   	}
}
