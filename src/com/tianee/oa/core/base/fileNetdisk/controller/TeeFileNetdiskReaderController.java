package com.tianee.oa.core.base.fileNetdisk.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.core.base.fileNetdisk.model.TeeFileNetdiskReaderModel;
import com.tianee.oa.core.base.fileNetdisk.service.TeeFileNetdiskReaderService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

/**
 * 文件签阅表
 * 
 * @author wyw
 * 
 */
@Controller
@RequestMapping("/TeeFileNetdiskReaderController")
public class TeeFileNetdiskReaderController {

	@Autowired
	private TeeFileNetdiskReaderService service;

	/**
	 * @function: 新建或编辑
	 * @author: wyw
	 * @data: 2014年9月6日
	 * @param request
	 * @return
	 * @throws java.text.ParseException 
	 * @throws ParseException
	 * @throws java.text.ParseException
	 *             TeeJson
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request) throws java.text.ParseException  {
		TeeJson json = new TeeJson();
		TeeFileNetdiskReaderModel model = new TeeFileNetdiskReaderModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		Map requestMap = TeeServletUtility.getParamMap(request);
		if (model.getFileNetdiskId() != 0) {
			json = service.addObj(requestMap, loginPerson, model);
		}
		return json;
	}
	
	
	
	

	
	

	/**
	 * @function: 详情
	 * @author: wyw
	 * @data: 2014年9月6日
	 * @param request
	 * @return
	 * @throws java.text.ParseException 
	 * @throws ParseException
	 * @throws java.text.ParseException
	 *             TeeJson
	 */
	@RequestMapping("/getInfoById")
	@ResponseBody
	public TeeJson getInfoById(HttpServletRequest request) throws java.text.ParseException {
		TeeJson json = new TeeJson();
		TeeFileNetdiskReaderModel model = new TeeFileNetdiskReaderModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);

		Map requestMap = TeeServletUtility.getParamMap(request);
		json = service.getInfoById(requestMap, loginPerson, model);
		return json;
	}

	/**
	 * @function: 删除
	 * @author: wyw
	 * @data: 2014年9月6日
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 *             TeeJson
	 */
	@RequestMapping("/deleteObjById")
	@ResponseBody
	public TeeJson deleteObjById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeeFileNetdiskReaderModel model = new TeeFileNetdiskReaderModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);

		Map requestMap = TeeServletUtility.getParamMap(request);
		String sids = (String) requestMap.get("sids");
		json = service.deleteObjById(sids);
		return json;
	}
	
	
	/**
	 * 是否签阅
	 * @function: 
	 * @author: wyw
	 * @data: 2015年7月23日
	 * @param request
	 * @return
	 * @throws java.text.ParseException TeeJson
	 */
	@RequestMapping("/isSignRead")
	@ResponseBody
	public TeeJson isSignRead(HttpServletRequest request) throws java.text.ParseException {
		TeeJson json = new TeeJson();
		TeeFileNetdiskReaderModel model = new TeeFileNetdiskReaderModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);

		Map requestMap = TeeServletUtility.getParamMap(request);
		json = service.isSignRead(requestMap, loginPerson, model);
		return json;
	}
	
	
	
	/**
	 * 查看签阅情况
	 * @function: 
	 * @author: wyw
	 * @data: 2015年7月23日
	 * @param request
	 * @return
	 * @throws java.text.ParseException TeeJson
	 */
	@RequestMapping("/showSignReadDetail")
	@ResponseBody
	public TeeEasyuiDataGridJson showSignReadDetail(HttpServletRequest request) throws java.text.ParseException {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		TeeFileNetdiskReaderModel model = new TeeFileNetdiskReaderModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		Map requestMap = TeeServletUtility.getParamMap(request);
		json = service.showSignReadDetail(requestMap, loginPerson);
		return json;
	}
	
	
	/**
	 * 根据文件Id删除签阅情况
	 * @function: 
	 * @data: 2015年7月23日
	 * @param request
	 * @return
	 * @throws java.text.ParseException TeeJson
	 */
	@RequestMapping("/delInfoByFileId")
	@ResponseBody
	public TeeJson delInfoByFileId(HttpServletRequest request) throws java.text.ParseException {
		TeeJson json = new TeeJson();
		TeeFileNetdiskReaderModel model = new TeeFileNetdiskReaderModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		Map requestMap = TeeServletUtility.getParamMap(request);
		json = service.delInfoByFileId(requestMap, loginPerson);
		return json;
	}
	
	
	
	

}
