package com.tianee.oa.core.base.diary.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.base.diary.bean.TeeDiaryReply;
import com.tianee.oa.core.base.diary.model.TeeDiaryModel;
import com.tianee.oa.core.base.diary.model.TeeDiaryReplyModel;
import com.tianee.oa.core.base.diary.service.TeeDiaryService;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("/diaryController")
public class TeeDiaryController {
	
	@Autowired
	private TeeDiaryService diaryService;
	@Autowired
	@Qualifier("teeBaseUpload")
	private TeeBaseUpload upload;
	
	@Autowired
	private TeeSmsManager smsManager;
	
	public TeeDiaryService getDiaryService() {
		return diaryService;
	}

	public void setDiaryService(TeeDiaryService diaryService) {
		this.diaryService = diaryService;
	}
	
	public TeeBaseUpload getUpload() {
		return upload;
	}

	public void setUpload(TeeBaseUpload upload) {
		this.upload = upload;
	}
	
	
	@RequestMapping("/returnHtml")
	@ResponseBody
	public TeeJson returnHtml() throws Exception{
		TeeJson json = new TeeJson();
		String html = diaryService.diaryHtml();
		json.setRtMsg("成功");
		json.setRtState(true);
		json.setRtData(html);
		return json;
	}
	
	@RequestMapping("/returnStartAndEndTime")
	@ResponseBody
	public TeeJson returnStartAndEndTime() throws Exception{
		TeeJson json = new TeeJson();
		Map returnStr = diaryService.returnStartAndEndTime();
		json.setRtMsg("成功");
		json.setRtState(true);
		json.setRtData(returnStr);
		return json;
	}
	
	@RequestMapping("/addOrUpdateDiary")
	@ResponseBody
	public TeeJson addOrUpdateDiary(HttpServletRequest request) throws  Exception {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		TeeDiaryModel model = new TeeDiaryModel();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List attachments = upload.manyAttachUpload(multipartRequest, TeeAttachmentModelKeys.diary);
		
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		
		model.setAttacheModels(attachments);
		diaryService.addOrUpdateDiary(model , person);
		
		//内部短信提醒共享人员
		String remaind =TeeStringUtil.getString(multipartRequest.getParameter("remaind"), "") ;
		if(remaind.equals("on") && !TeeUtility.isNullorEmpty(model.getShareRangesIds())){
			Map requestData = new HashMap();
			requestData.put("content", person.getUserName()+"分享了一遍日志：" + model.getTitle());
			requestData.put("userListIds",model.getShareRangesIds() );
			requestData.put("moduleNo", "018");
			requestData.put("remindUrl", "/system/core/base/diary/manage/detail.jsp?sid="+model.getSid());
			requestData.put("remindUrl1", "/system/mobile/phone/diary/diaryInfo.jsp?sid="+model.getSid());
			smsManager.sendSms(requestData, person);
		}
		
		json.setRtData(model.getSid());
		json.setRtState(true);
		return json;
	}
	
	
	@RequestMapping("/getDiaryById")
	@ResponseBody
	public TeeJson getDiaryById(HttpServletRequest request) throws Exception{
		String sid = request.getParameter("sid");
		TeeJson json = new TeeJson();
		TeeDiaryModel model = new TeeDiaryModel();
		model = diaryService.getDiaryById(sid);
		json.setRtMsg("成功");
		json.setRtState(true);
		json.setRtData(model);
		return json;
	}
	
	
	@RequestMapping("/delDiary")
	@ResponseBody
	public TeeJson delDiary(HttpServletRequest request) throws Exception{
		String sid = request.getParameter("sid");
		TeeJson json = new TeeJson();
	    diaryService.delDiaryService(sid);
		json.setRtMsg("成功");
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 我的日志
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doSearch")
	@ResponseBody
	public TeeJson doSearch(HttpServletRequest request) throws Exception{
		Map requestDatas = TeeServletUtility.getParamMap(request);
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		requestDatas.put(TeeConst.LOGIN_USER, person);
		TeeJson json = new TeeJson();
	    Object returned = diaryService.doSearch(requestDatas);
		json.setRtData(returned);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 共享日志
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doSearchShare")
	@ResponseBody
	public TeeJson doSearchShare(HttpServletRequest request) throws Exception{
		Map requestDatas = TeeServletUtility.getParamMap(request);
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		requestDatas.put(TeeConst.LOGIN_USER, person);
		TeeJson json = new TeeJson();
	    Object returned = diaryService.doSearchShare(requestDatas);
		json.setRtData(returned);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 下属日志
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doSearchBranch")
	@ResponseBody
	public TeeJson doSearchBranch(HttpServletRequest request) throws Exception{
		Map requestDatas = TeeServletUtility.getParamMap(request);
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		requestDatas.put(TeeConst.LOGIN_USER, person);
		TeeJson json = new TeeJson();
	    Object returned = diaryService.doSearchBranch(requestDatas);
		json.setRtData(returned);
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/saveDiarySetting")
	@ResponseBody
	public TeeJson saveDiarySetting(HttpServletRequest request) throws Exception{
		Map requestDatas = TeeServletUtility.getParamMap(request);
		TeeJson json = new TeeJson();
	    diaryService.saveDiarySetting(requestDatas);
	    json.setRtMsg("参数设置成功！");
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/getDiarySetting")
	@ResponseBody
	public TeeJson getDiarySetting(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
	    Map returned = diaryService.getDiarySetting();
		json.setRtData(returned);
		json.setRtMsg("参数获取成功");
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/isLocked")
	@ResponseBody
	public TeeJson isLocked(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		Map returnedData = new HashMap();
		String addTime = TeeStringUtil.getString(request.getParameter("addTime"),"");
	    boolean returned = diaryService.isLocked(addTime);
	    returnedData.put("flag", returned);
		json.setRtData(returnedData);
		json.setRtMsg("判断成功！");
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/getReplies")
	@ResponseBody
	public TeeJson getReplies(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		int diaryId = TeeStringUtil.getInteger(request.getParameter("diaryId"), 0);
		json.setRtData(diaryService.getDiaryReplies(diaryId));
		json.setRtMsg("判断成功！");
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/addReply")
	@ResponseBody
	public TeeJson addReply(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeDiaryReplyModel diaryReplyModel = new TeeDiaryReplyModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeServletUtility.requestParamsCopyToObject(request, diaryReplyModel);
		diaryReplyModel.setReplyUserId(loginPerson.getUuid());
		diaryService.addReply(diaryReplyModel);
		json.setRtMsg("回复成功");
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/deleteReply")
	@ResponseBody
	public TeeJson deleteReply(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		int replyId = TeeStringUtil.getInteger(request.getParameter("replyId"), 0);
		diaryService.deleteReply(replyId);
		json.setRtMsg("删除成功");
		json.setRtState(true);
		return json;
	}
	
	
	
	/**
	 * 获取下属tree
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getXsTree")
	@ResponseBody
	public TeeJson getXsTree(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return diaryService.getXsTree(request);
	}
	
	/**
	 * 获取某天的所有日志
	 * */
	@ResponseBody
	@RequestMapping("/findWorkLogAll")
	public TeeJson findWorkLogAll(HttpServletRequest request){
		return diaryService.findWorkLogAll(request);
	}
	
	/**
	 * 获取某个日志
	 * */
	@ResponseBody
	@RequestMapping("/findWorkLogById")
	public TeeJson findWorkLogById(HttpServletRequest request){
		return diaryService.findWorkLogById(request);
	}
	
	/**
	 * 添加
	 * */
	@ResponseBody
	@RequestMapping("/workLogSave")
	public TeeJson workLogSave(HttpServletRequest request){
		return diaryService.workLogSave(request);
	}
}
