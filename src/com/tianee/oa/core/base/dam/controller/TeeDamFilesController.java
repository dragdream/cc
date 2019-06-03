package com.tianee.oa.core.base.dam.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.base.dam.model.TeeFilesModel;
import com.tianee.oa.core.base.dam.service.TeeDamFilesService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;


@Controller
@RequestMapping("/TeeDamFilesController")
public class TeeDamFilesController {
	@Autowired
	TeeDamFilesService fileService;
	@Autowired
	@Qualifier("teeBaseUpload")
	private TeeBaseUpload upload;
	@Autowired
	private TeeAttachmentService attachmentService;
	

	/**
	 * 流程归档
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/workFlowArchive")
	@ResponseBody
	public TeeJson workFlowArchive(HttpServletRequest request){
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestData=TeeServletUtility.getParamMap(request);
		requestData.put("loginUser",loginUser);
		return fileService.workFlowArchive(requestData);
	}
	
	
	
	
	
	
	/**
	 * 新建/编辑
	 * @param request
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request,TeeFilesModel model){
		return fileService.addOrUpdate(request,model);
	}
	
	
	
	/**
	 * 获取预归档文件列表
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getPreFileList")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {	
		return fileService.getPreFileList(dm, request);
	}
	
	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request){
		return fileService.getInfoBySid(request);
	}
	
	
	/**
	 * 删除预归档的文件
	 * @param request
	 * @return
	 */
	@RequestMapping("/delBySid")
	@ResponseBody
	public TeeJson delBySid(HttpServletRequest request){
		return fileService.delBySid(request);
	}
	
	
	
	/**
	 * 移交档案 
	 * @param request
	 * @return
	 */
	@RequestMapping("/turnOver")
	@ResponseBody
	public TeeJson turnOver(HttpServletRequest request){
		return fileService.turnOver(request);
	}
	
	
	/**
	 * 获取档案整理  文件管理的列表
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getManageFileList")
	@ResponseBody
	public TeeEasyuiDataGridJson getManageFileList(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {	
		return fileService.getManageFileList(dm, request);
	}
	
	
	/**
	 * 获取档案文件销毁列表
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getArchivedAndDeledFileList")
	@ResponseBody
	public TeeEasyuiDataGridJson getArchivedAndDeledFileList(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {	
		return fileService.getArchivedAndDeledFileList(dm, request);
	}
	
	
	
	/**
	 * 根据卷库主键  或者卷盒  主键  获取档案文件列表
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getFileListByBoxOrHouse")
	@ResponseBody
	public TeeEasyuiDataGridJson getFileListByBoxOrHouse(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {	
		return fileService.getFileListByBoxOrHouse(dm, request);
	}
	
	/**
	 * 查看还未归档的卷盒下的文件列表
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getFileListByBoxId")
	@ResponseBody
	public TeeEasyuiDataGridJson getFileListByBoxId(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {	
		return fileService.getFileListByBoxId(dm, request);
	}
	

	/**
	 * 退回档案 
	 * @param request
	 * @return
	 */
	@RequestMapping("/drawBack")
	@ResponseBody
	public TeeJson 	drawBack(HttpServletRequest request){
		return fileService.	drawBack(request);
	}
	
	
	
	/**
	 * 分配卷盒
	 * @param request
	 * @return
	 */
	@RequestMapping("/distributeBox")
	@ResponseBody
	public TeeJson 	distributeBox(HttpServletRequest request){
		return fileService.distributeBox(request);
	}
	
	
	/**
	 * 逻辑删除
	 * @param request
	 * @return
	 */
	@RequestMapping("/logicDel")
	@ResponseBody
	public TeeJson 	logicDel(HttpServletRequest request){
		return fileService.logicDel(request);
	}
	
	
	
	/**
	 * 销毁档案
	 * @param request
	 * @return
	 */
	@RequestMapping("/destory")
	@ResponseBody
	public TeeJson 	destory(HttpServletRequest request){
		return fileService.destory(request);
	}
	
	
	/**
	 * 档案还原
	 * @param request
	 * @return
	 */
	@RequestMapping("/restore")
	@ResponseBody
	public TeeJson 	restore(HttpServletRequest request){
		return fileService.restore(request);
	}
	
	
	/**
	 * 查询所有的已经归档的   未被删除的档案
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/queryAllArchivedFiles")
	@ResponseBody
	public TeeEasyuiDataGridJson queryAllArchivedFiles(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {	
		return fileService.queryAllArchivedFiles(dm, request);
	}
}
