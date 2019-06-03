package com.beidasoft.xzzf.punish.mobile.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.clue.bean.Clue;
import com.beidasoft.xzzf.clue.bean.ClueReply;
import com.beidasoft.xzzf.clue.model.ClueModel;
import com.beidasoft.xzzf.clue.service.ClueReplyService;
import com.beidasoft.xzzf.clue.service.ClueService;
import com.beidasoft.xzzf.punish.common.bean.AffiliatedPerson;
import com.beidasoft.xzzf.punish.common.bean.ConfTache;
import com.beidasoft.xzzf.punish.common.bean.PunishBase;
import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.model.ConfFlowModel;
import com.beidasoft.xzzf.punish.common.model.ConfTacheModel;
import com.beidasoft.xzzf.punish.common.model.PunishBaseModel;
import com.beidasoft.xzzf.punish.common.model.PunishFLowModel;
import com.beidasoft.xzzf.punish.common.service.AffiliatedService;
import com.beidasoft.xzzf.punish.common.service.CommonService;
import com.beidasoft.xzzf.punish.common.service.ConfFlowService;
import com.beidasoft.xzzf.punish.common.service.ConfTacheService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseService;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.punish.mobile.service.MobileService;
import com.beidasoft.xzzf.task.taskAppointed.bean.CaseAppointedInfo;
import com.beidasoft.xzzf.task.taskAppointed.model.CaseAppointedInfoModel;
import com.beidasoft.xzzf.task.taskAppointed.service.CaseAppointedInfoService;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("mobileController")
public class MobileController {
	
	@Autowired
	MobileService mobileService;
	
	@Autowired
	TeePersonService personService;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	private TeeWorkflowServiceInterface workflowService;
	
	@Autowired
	private ConfFlowService confFlowService;
	
	@Autowired
	private ConfTacheService confTacheService;
	
	@Autowired
	private PunishBaseService punishBaseService;
	
	@Autowired
	private PunishFlowService punishFlowService;
	
	@Autowired
	private CaseAppointedInfoService appointedInfoService;
	
	@Autowired
	private ClueService clueservice;
	
	@Autowired
	private PunishBaseService baseService;
	
	@Autowired
	private AffiliatedService affiliService;
	
	@Autowired
	private TeeAttachmentService attachmentService;
	
	@Autowired
	private ClueReplyService replyService;
	
	/**
	 * 获取气泡接口（暂时弃用） 2019-01-02 更新  （指派气泡）
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/bubble")
	public TeeJson bubble(HttpServletRequest request) {
		TeeJson result = new TeeJson();
		//获取当前登录用户
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
//		//获取token
//		String token = request.getParameter("token");
//		//移动端当前登陆用户ID
//		String userId = request.getParameter("userId");
		//气泡类型（10：移动检查，20：移动办案）
		int type = 0;
		int dealType = 0;
		if (StringUtils.isNotBlank(request.getParameter("type"))) {
			if (StringUtils.isNotBlank(request.getParameter("dealType"))) {
				type = Integer.parseInt(request.getParameter("type"));
				dealType = Integer.parseInt(request.getParameter("dealType"));
			} else{
				result.setRtState(false);
				result.setRtData(null);
				result.setRtMsg("未传值");
			}
		} else {
			result.setRtState(false);
			result.setRtData(null);
			result.setRtMsg("未传值");
		}

		Map<String, String> requestData = TeeServletUtility.getParamMap(request); 
		//分页信息
		TeeDataGridModel gridModel = new TeeDataGridModel();
		gridModel.setPage(TeeStringUtil.getInteger(requestData.get("start"), 1));
		gridModel.setRows(TeeStringUtil.getInteger(requestData.get("length"), 10));
		
		TeeEasyuiDataGridJson gridJson = mobileService.getCaseAppointList(person, type, gridModel, dealType ,request);
		
		result.setRtState(true);
		result.setRtData(gridJson);	

		return result;
	}
	
	
	/**
	 * 待办（全部）
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/punishFlowDeal")
	public TeeJson punishFlowDeal(HttpServletRequest request) {
		TeeJson result = new TeeJson();
		
		Map<String, String> requestData = TeeServletUtility.getParamMap(request); 
		//获取token
//		String token = TeeStringUtil.getString(requestData.get("token"), "");
		
		//获取当前登录用户
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取执法代办列表
		TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
		TeeDataGridModel gridModel = new TeeDataGridModel();
		gridModel.setPage(TeeStringUtil.getInteger(requestData.get("start"), 1));
		gridModel.setRows(TeeStringUtil.getInteger(requestData.get("length"), 10));
		try{
			gridJson = workflowService.getReceivedWorks(requestData,person, gridModel);
			result.setRtData(gridJson);
			result.setRtState(true);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/punishWaitDeal2")
	public TeeJson punishWaitDeal2(HttpServletRequest request) {
		TeeJson json = new TeeJson();

		//获取当前登录用户
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		Map<String, String> requestData = TeeServletUtility.getParamMap(request);
		
		//获取执法代办列表
		TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
		TeeEasyuiDataGridJson newGridJson = new TeeEasyuiDataGridJson();
		
		TeeDataGridModel gridModel = new TeeDataGridModel();
		gridModel.setPage(TeeStringUtil.getInteger(requestData.get("start"), 1));
		gridModel.setRows(TeeStringUtil.getInteger(requestData.get("length"), 10));
		
		gridJson = workflowService.getReceivedWorks(requestData,person, gridModel);
		List<Map> mapList = gridJson.getRows();
		List<Map> newList = new ArrayList<Map>();
		int index = 0;
		for (Map map : mapList) {
			String runId = (String) map.get("runId");
			String flowName = (String) map.get("flowName");
			PunishFLow flow = punishFlowService.getFlowByRunIdOrFlowName(runId, flowName);
			if (flow != null) {
				String tachId = flow.getTacheId();
				ConfTache tache = confTacheService.getById(tachId);
				if (tache != null && !tache.getConfTacheName().equals("调查取证")) { //如果不是调查取证
					newList.add(map);
					index += 1;
				}
			}
		}
		newGridJson.setRows(newList);
		newGridJson.setTotal((long) index);

		json.setRtData(newGridJson);
		json.setRtState(true);
		return json;
	}
	
	
	/**
	 * 提交    2019-01-02新增
	 * 
	 * @param caseAppointedInfoModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/submitCaseAppoint")
	public TeeJson submitCaseAppoint(CaseAppointedInfoModel caseAppointedInfoModel, HttpServletRequest request) {
		
		if (StringUtils.isNotBlank(request.getParameter("taskRecId"))) {
			caseAppointedInfoModel.setTaskRecId(request.getParameter("taskRecId"));
		}
		
		if (StringUtils.isNotBlank(request.getParameter("majorPersonId"))) {
			caseAppointedInfoModel.setMajorPersonId(Integer.parseInt(request.getParameter("majorPersonId")));
		}
		if (StringUtils.isNotBlank(request.getParameter("majorPersonName"))) {
			caseAppointedInfoModel.setMajorPersonName(request.getParameter("majorPersonName"));
		}
		if (StringUtils.isNotBlank(request.getParameter("minorPersonId"))) {
			caseAppointedInfoModel.setMinorPersonId(Integer.parseInt(request.getParameter("minorPersonId")));
		}
		if (StringUtils.isNotBlank(request.getParameter("minorPersonName"))) {
			caseAppointedInfoModel.setMinorPersonName(request.getParameter("minorPersonName"));
		}
		
		return appointedInfoService.saveAppointed(caseAppointedInfoModel, request);
	}
	
	/**
	 * 办结    2019-01-02新增
	 * 
	 * @param caseAppointedInfoModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appointBreak")
	public TeeJson appointBreak(CaseAppointedInfoModel caseAppointedInfoModel, HttpServletRequest request) {
		return appointedInfoService.appointBreak(request.getParameter("taskRecId"));
	}
	
	/**
	 * 案件不予立案
	 * @param CaseAppointedInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/unApply")
	public TeeJson update(CaseAppointedInfoModel caseAppointedInfoModel, HttpServletRequest request){
		CaseAppointedInfo userInfo = new CaseAppointedInfo();
		
		if (StringUtils.isNotBlank(request.getParameter("taskRecId"))) {
			userInfo = appointedInfoService.getByTaskRecId(request.getParameter("taskRecId"));
			userInfo.setExtraComments(request.getParameter("extraComment"));
			userInfo.setDealType(11);
			userInfo.setDisposeTime(Calendar.getInstance());
		}
		
		return appointedInfoService.update(userInfo);
	}
	
	/**
	 * 获取线索相关信息用于生成受理单    2019-01-02新增
	 * 
	 * @param sourceId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getClueByTaskRecId")
	public TeeJson getClueByTaskRecId(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String sourceId = request.getParameter("sourceId");
		Clue clue = clueservice.get(sourceId);
		ClueModel model = new ClueModel();
		if (clue != null) {
			model = clueservice.beanToModel(clue);					
			json.setRtData(model);
			json.setRtState(true);
		} else {
			json.setRtData(null);
			json.setRtState(false);
			json.setRtMsg("未查到相关线索信息");
		}
		
		return json;
	}
	
	/**
	 * 获取指派表详情    2019-01-02新增
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getByTaskRecId")
	public TeeJson getByTaskRecId(HttpServletRequest request) {
		TeeJson json = new TeeJson();

		String taskRecId = request.getParameter("taskRecId");
		//获取案件信息
		CaseAppointedInfo caseAppointedInfo = appointedInfoService.getByTaskRecId(taskRecId);
		CaseAppointedInfoModel caseAppoiontedInfoModel = new CaseAppointedInfoModel();
		
		BeanUtils.copyProperties(caseAppointedInfo, caseAppoiontedInfoModel);
		if(caseAppointedInfo.getTaskSendTime() != null) {
			caseAppoiontedInfoModel.setTaskSendTimeStr(TeeDateUtil.format(caseAppointedInfo.getTaskSendTime(), "yyyy-MM-dd"));
		}
		if(caseAppointedInfo.getCreateTime() != null) {
			caseAppoiontedInfoModel.setCreateTimeStr(TeeDateUtil.format(caseAppointedInfo.getCreateTime().getTime(),"yyyy-MM-dd HH:mm"));
		}
		if(caseAppointedInfo.getDisposeTime() != null) {
			caseAppoiontedInfoModel.setDisposeTimeStr(TeeDateUtil.format(caseAppointedInfo.getDisposeTime().getTime(),"yyyy-MM-dd HH:mm"));
		}
		json.setRtData(caseAppoiontedInfoModel);
		json.setRtState(true);
		return json;
	}
	

	/**
	 * 主办（办案）
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/punishDirect")
	public TeeJson punishDirect(HttpServletRequest request) {
		TeeJson result = new TeeJson();
		
		Map<String, String> requestData = TeeServletUtility.getParamMap(request);
		//获取当前登录用户
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//分页信息
		TeeDataGridModel gridModel = new TeeDataGridModel();
		gridModel.setPage(TeeStringUtil.getInteger(requestData.get("start"), 1));
		gridModel.setRows(TeeStringUtil.getInteger(requestData.get("length"), 10));
		
		TeeEasyuiDataGridJson gridJson = mobileService.punishDirect(requestData, person, gridModel);
		
		result.setRtState(true);
		result.setRtData(gridJson);
		
		return result;
	}
	
	/**
	 * 参与（办案）
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/punishWaitDeal")
	public TeeJson punishWaitDeal(HttpServletRequest request) {
		TeeJson result = new TeeJson();
		
		Map<String, String> requestData = TeeServletUtility.getParamMap(request); 
		//获取token
//		String token = TeeStringUtil.getString(requestData.get("token"), "");
		
		//获取当前登录用户
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取执法代办列表
		TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
		TeeDataGridModel gridModel = new TeeDataGridModel();
		gridModel.setPage(TeeStringUtil.getInteger(requestData.get("start"), 1));
		gridModel.setRows(TeeStringUtil.getInteger(requestData.get("length"), 10));
		
		gridJson = mobileService.punishWaitDeal(request, person, gridModel);
		result.setRtData(gridJson);
		result.setRtState(true);
		return result;
	}
	
	
	/**
	 * 获取环节、案件文书流程（办案、检查）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("punishDetails")
	public TeeJson punishDetails(HttpServletRequest request) throws Exception {
		TeeJson result = new TeeJson();
		JSONObject jsonObject = new JSONObject();
		//获取请求参数
		String baseId = TeeStringUtil.getString(request.getParameter("baseId"), "");
//		String tacheId = TeeStringUtil.getString(request.getParameter("tacheId"), "");
		
		if("".equals(baseId)) {
			result.setRtState(false);
			result.setRtMsg("未查询到案件主键");
			return result;
		}
		//当前用户
//		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		List<Object> rtnInfo = new ArrayList<Object>();		
		Map<String, Object> confInfo = new HashMap<String, Object>();
		List<ConfFlowModel> flowList = null;
		ConfFlowModel tmpMdl = null;
		List<ConfTacheModel> tacheList = confTacheService.getConfTacheInfo(new ConfTache());
		// 根据环节信息获取对应流程信息
		for (ConfTacheModel mdl : tacheList) {
			confInfo = new HashMap<String, Object>();
			tmpMdl = new ConfFlowModel();
			tmpMdl.setConfTacheCode(mdl.getConfTacheCode());
			flowList = confFlowService.getConfFlowInfo(tmpMdl);
			
			//环节ID
			confInfo.put("tacheId", mdl.getConfTacheCode());
			//环节名称
			confInfo.put("tacheName", mdl.getConfTacheName());
			//文书流程信息
			confInfo.put("flowList", JSONArray.fromObject(flowList));
			//设定返回信息
			rtnInfo.add(confInfo);
		}
		jsonObject.put("confFlow", rtnInfo);
		
		//案件基础信息
		PunishBase punishBase = punishBaseService.getbyid(baseId);
		PunishBaseModel punishBaseModel = new PunishBaseModel();
		BeanUtils.copyProperties(punishBase, punishBaseModel);
		
		//获取参与人list
		List<AffiliatedPerson> personList = affiliService.getListByBaseId(baseId);
		punishBaseModel.setPsnArrList(personList);
		
		jsonObject.put("punishBase", punishBaseModel);
		
		//已办文书
		List<PunishFLow> punishFLows = punishFlowService.getFlowcase(baseId, "", "");
		List<PunishFLowModel> punishFLowModels = new ArrayList<PunishFLowModel>();

		PunishFLowModel punishFLowModel = null;
		for(PunishFLow punishFLow : punishFLows) {
			punishFLowModel = new PunishFLowModel();
			BeanUtils.copyProperties(punishFLow, punishFLowModel);
			
			punishFLowModel.setCreatetimeStr(TeeDateUtil.format(punishFLow.getTime(), "yyyy-MM-dd"));
			
			punishFLowModels.add(punishFLowModel);
		}
		jsonObject.put("punishFLows", JSONArray.fromObject(punishFLowModels));
		
		//获取附件信息
		List<TeeAttachmentModel> attachList= attachmentService.getAttacheModels("CaseAttachment", baseId);
		jsonObject.put("attaches", JSONArray.fromObject(attachList));
		
		result.setRtData(jsonObject.toString());
		result.setRtState(true);
		
		return result;
	}
	
	/**
	 * 保存参与人
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/savePersonList")
	public TeeJson savePersonList(HttpServletRequest request) {
		TeeJson result  = new TeeJson();
		
		result  = mobileService.savePersonList(request);
		
		return result;
	}
	
	/**
	 * 办结（办案）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/punishClosed")
	public TeeJson punishClosed(HttpServletRequest request) throws Exception {
		TeeJson result = new TeeJson();
		Map<String, String> requestData = TeeServletUtility.getParamMap(request);
		
		int type = TeeStringUtil.getInteger(requestData.get("type"), 0);
		
		//初始化列表分页
		TeeDataGridModel gridModel = new TeeDataGridModel();
		gridModel.setPage(TeeStringUtil.getInteger(requestData.get("start"), 1));
		gridModel.setRows(TeeStringUtil.getInteger(requestData.get("length"), 10));
		
		//获取移动端登陆用户
		TeePerson person = personService.getPersonByUserId(TeeStringUtil.getString(requestData.get("userId"), ""));
		
		if(type == 1) {			//主办已完成
			requestData.put("isClosed", "1");
			TeeEasyuiDataGridJson gridJson = mobileService.punishDirect(requestData, person, gridModel);
			
			result.setRtData(gridJson);
			result.setRtState(true);			
			return result;
		} else if(type == 2) { //待办已完成
			TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
			
			try{
				gridJson = workflowService.getHandledWorks(requestData, person, gridModel);
				result.setRtData(gridJson);
				result.setRtState(true);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return result;
		}
		result.setRtState(false);
		result.setRtMsg("类型传输异常");
		return result;
	}
	
	/**
	 * 获取检查环节文书列表 
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDocFlowList")
	public TeeJson getDocFlowList() {
		TeeJson json = new TeeJson();
		
		List<Object> rtnInfo = new ArrayList<Object>();		
		Map<String, Object> confInfo = new HashMap<String, Object>();
		List<ConfFlowModel> flowList = null;
		ConfFlowModel tmpMdl = null;
		
		//初始化环节配置  环节code 为00 则为现场检查环节
		ConfTache conf = new ConfTache();
		conf.setConfTacheCode("00");
		List<ConfTacheModel> tacheList = confTacheService.getConfTacheInfo(new ConfTache());
		// 根据环节信息获取对应流程信息
		for (ConfTacheModel mdl : tacheList) {
			confInfo = new HashMap<String, Object>();
			tmpMdl = new ConfFlowModel();
			tmpMdl.setConfTacheCode(mdl.getConfTacheCode());
			flowList = confFlowService.getConfFlowInfo(tmpMdl);
			
			//环节ID
			confInfo.put("tacheId", mdl.getConfTacheCode());
			//环节名称
			confInfo.put("tacheName", mdl.getConfTacheName());
			//文书流程信息
			confInfo.put("flowList", JSONArray.fromObject(flowList));
			//设定返回信息
			rtnInfo.add(confInfo);
		}
		json.setRtData(rtnInfo);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 主办（检查）
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkDirect")
	public TeeJson checkDirect(HttpServletRequest request) {
		TeeJson result = new TeeJson();
		
		String sourceType = request.getParameter("sourceType");
		if (StringUtils.isBlank(sourceType)) {
			sourceType = "";
		}
		Map<String, String> requestData = TeeServletUtility.getParamMap(request); 
		//获取当前登录用户
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//分页信息
		TeeDataGridModel gridModel = new TeeDataGridModel();
		gridModel.setPage(TeeStringUtil.getInteger(requestData.get("start"), 1));
		gridModel.setRows(TeeStringUtil.getInteger(requestData.get("length"), 10));
		
		TeeEasyuiDataGridJson gridJson = mobileService.checkDirect(person, sourceType, gridModel);
		
		result.setRtState(true);
		result.setRtData(gridJson);
		
		return result;
	}
	
	/**
	 * 参与（检查）
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkFlowDeal")
	public TeeJson checkFlowDeal(HttpServletRequest request) {
		TeeJson result = new TeeJson();
		
		Map<String, String> requestData = TeeServletUtility.getParamMap(request); 
		//获取token
//		String token = TeeStringUtil.getString(requestData.get("token"), "");
		
		//获取当前登录用户
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取执法代办列表
		TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
		TeeDataGridModel gridModel = new TeeDataGridModel();
		gridModel.setPage(TeeStringUtil.getInteger(requestData.get("start"), 1));
		gridModel.setRows(TeeStringUtil.getInteger(requestData.get("length"), 10));
		
		gridJson = mobileService.getReceivedCheckWorks(request, person, gridModel);
		result.setRtData(gridJson);
		result.setRtState(true);
		return result;
	}
	
	/**
	 * 获取已检查案件
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/finishCheckList")
	public TeeEasyuiDataGridJson finishCheckList(HttpServletRequest request) {
		Map<String, String> requestData = TeeServletUtility.getParamMap(request);
		// 获取当前登录用户
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		// 获取执法代办列表
		TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
		TeeDataGridModel gridModel = new TeeDataGridModel();
		gridModel.setPage(TeeStringUtil.getInteger(requestData.get("start"), 1));
		gridModel.setRows(TeeStringUtil.getInteger(requestData.get("length"), 10));
		gridJson = mobileService.finishCheckList(person, gridModel);
		return gridJson;
	}
	
	/**
	 * 移动端保存案件当事人信息
	 * 
	 * @param baseModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveCheckPartyInfo")
	public TeeJson saveCheckPartyInfo(PunishBaseModel baseModel, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		
		json = baseService.save(baseModel, request);
		
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取指派给别人的文书
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAppointPunish")
	public TeeJson getAppointPunish(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		
		json = mobileService.getAppointPunish(request);
		
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 申请立案
	 * 
	 * @param baseId punishBase 表主键UUID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/applyRegister")
	public TeeJson applyRegister(String baseId) {
		TeeJson json = new TeeJson();
		
		json = appointedInfoService.applyRegister(baseId);
		
		return json;
	}
	
	/**
	 * 检查合格
	 * 
	 * @param baseId punishBase 表主键UUID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkUnError")
	public TeeJson checkUnError(String baseId) {
		TeeJson json = new TeeJson();
		
		json = appointedInfoService.checkUnError(baseId, 1);
		
		return json;
	}
	
	/**
	 * 企业不存在或其他原因
	 * 
	 * @param baseId punishBase 表主键UUID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkLostParty")
	public TeeJson checkLostParty(String baseId) {
		TeeJson json = new TeeJson();
		
		json = appointedInfoService.checkUnError(baseId, 2);
		
		return json;
	}
	
	/**
	 * 更新系统流程的流程变量
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/setVarValue")
	public TeeJson setVarValue(HttpServletRequest request) throws Exception {
		TeeJson result = new TeeJson();
		int runId = TeeStringUtil.getInteger(request.getParameter("RUN_ID"), 0);
		String primaryId = TeeStringUtil.getString(request.getParameter("VALUES"));
		String key = TeeStringUtil.getString(request.getParameter("KEY"));
		//根据key和runId更新流程变量
		commonService.updateFlowRunVars(primaryId, runId, key);
		result.setRtState(true);
		return result;
	}
	

	/**
	 * 通过runId 获取文书流程
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getFlowByRunId")
	public TeeJson getFlowByRunId(HttpServletRequest request) throws Exception {
		TeeJson teeJson = new TeeJson();
		//获取token
//		String token = TeeStringUtil.getString(request.getParameter("token"), "");
		
		//获取当前登录用户
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		JSONObject jsonObject = mobileService.getFlowByRunId(request, person);
		
		teeJson.setRtData(jsonObject.toString());
		teeJson.setRtState(true);
		return teeJson;
	}
	
	/**
	 * 执法队员任务接收（主办人、协办人仅查看）
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/receivePunish")
	public TeeJson receivePunish(HttpServletRequest request) {
		TeeJson teeJson = new TeeJson();
		
		//获取当前登录用户
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson datagrid = mobileService.getByPerson(person);
		
		teeJson.setRtData(datagrid);
		teeJson.setRtState(true);
		return teeJson;
	}
	
	/**
	 * 执法人员确认接收任务
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/isReceive")
	public TeeJson isReceive(HttpServletRequest request) {
		TeeJson teeJson = new TeeJson();
		String baseId = TeeStringUtil.getString(request.getParameter("baseId"),"");
		teeJson = mobileService.isReceive(baseId);
		
		return teeJson;
	}
	
	/**
	 * 获取待听证列表
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/isHearingList")
	public TeeEasyuiDataGridJson isHearingList(HttpServletRequest request) {
		TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
		
		Map<String, String> requestData = TeeServletUtility.getParamMap(request);

		// 初始化列表分页
		TeeDataGridModel gridModel = new TeeDataGridModel();
		gridModel.setPage(TeeStringUtil.getInteger(requestData.get("start"), 1));
		gridModel.setRows(TeeStringUtil.getInteger(requestData.get("length"),10));
		datagrid = mobileService.isHearingList(gridModel);
		
		return datagrid;
	}
	
	/**
	 * 申请听证
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/ApplyHearing")
	public TeeJson ApplyHearing(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String baseId = TeeStringUtil.getString(request.getParameter("baseId"), "");
		
		if (StringUtils.isNotBlank(baseId)) {
			PunishBase base = baseService.getbyid(baseId);
			base.setIsHearing(1);
			base.setStatus("31");
			baseService.update(base);
			json.setRtData(base);
			json.setRtState(true);
		} else {
			json.setRtData(null);
			json.setRtState(false);
			json.setRtMsg("参数为空");
		}
		
		return json;
	}
	
	/**
	 * 听证办结
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/closeHearing")
	public TeeJson closeHearing(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String baseId = TeeStringUtil.getString(request.getParameter("baseId"), "");
		
		if (StringUtils.isNotBlank(baseId)) {
			PunishBase base = baseService.getbyid(baseId);
			base.setIsHearing(0);
			base.setStatus("30");
			baseService.update(base);
			json.setRtData(base);
			json.setRtState(true);
		} else {
			json.setRtData(null);
			json.setRtState(false);
			json.setRtMsg("参数为空");
		}
		
		return json;
	}
	
	
	/**
	 * 查询所有法律信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAllLawsOrSearch")
	public TeeEasyuiDataGridJson getAllLawsOrSearch(HttpServletRequest request) {
		String nameSearch = TeeStringUtil.getString(request.getParameter("nameSearch"), "");
		String submitlawLevel = TeeStringUtil.getString(request.getParameter("submitlawLevel"), "");
		
		Map<String, String> requestData = TeeServletUtility.getParamMap(request); 
		
		//初始化列表分页
		TeeDataGridModel gridModel = new TeeDataGridModel();
		gridModel.setPage(TeeStringUtil.getInteger(requestData.get("start"), 1));
		gridModel.setRows(TeeStringUtil.getInteger(requestData.get("length"), 10));
		
		TeeEasyuiDataGridJson datagrid = mobileService.getAllLawsOrSearch(nameSearch, submitlawLevel, gridModel);
		
		return datagrid;
	}
	
	/**
	 * 查询所有法律子表信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showLawDetailById")
	public TeeEasyuiDataGridJson showLawById(HttpServletRequest request) {
		
		String lawId = TeeStringUtil.getString(request.getParameter("lawId"), "");
		Map<String, String> requestData = TeeServletUtility.getParamMap(request); 
		
		//初始化列表分页
		TeeDataGridModel gridModel = new TeeDataGridModel();
		gridModel.setPage(TeeStringUtil.getInteger(requestData.get("start"), 1));
		gridModel.setRows(TeeStringUtil.getInteger(requestData.get("length"), 10));
		TeeEasyuiDataGridJson datagrid = mobileService.showLawById(lawId, gridModel);
		
		return datagrid;
	}
	
	/**
	 * 查询所有职权信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAllPowersOrSearch")
	public TeeEasyuiDataGridJson getAllPowersOrSearch(HttpServletRequest request) {
		TeeEasyuiDataGridJson datagrid = mobileService.getAllPowersOrSearch(request);
		return datagrid;
	}
	
	/**
	 * 查询所有职权子表信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showPowerDetailById")
	public TeeEasyuiDataGridJson showPowerDetailById(HttpServletRequest request) {
		
		String powerId = TeeStringUtil.getString(request.getParameter("powerId"), "");
		Map<String, String> requestData = TeeServletUtility.getParamMap(request); 
		
		//初始化列表分页
		TeeDataGridModel gridModel = new TeeDataGridModel();
		gridModel.setPage(TeeStringUtil.getInteger(requestData.get("start"), 1));
		gridModel.setRows(TeeStringUtil.getInteger(requestData.get("length"), 10));
		TeeEasyuiDataGridJson datagrid = mobileService.showPowerDetailById(powerId, gridModel);
		
		return datagrid;
	}
	
	/**
	 * 获取自由裁量权列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/dataGridDiscretion")
	public TeeEasyuiDataGridJson dataGridDiscretion(HttpServletRequest request) {
		
		String powerId = TeeStringUtil.getString(request.getParameter("powerId"), "");
		Map<String, String> requestData = TeeServletUtility.getParamMap(request); 
		
		//初始化列表分页
		TeeDataGridModel gridModel = new TeeDataGridModel();
		gridModel.setPage(TeeStringUtil.getInteger(requestData.get("start"), 1));
		gridModel.setRows(TeeStringUtil.getInteger(requestData.get("length"), 10));
		TeeEasyuiDataGridJson datagrid = mobileService.dataGridDiscretion(powerId, gridModel);
		return datagrid;
	}
	
	/**
	 * 自由裁量权数据操作（flag=add新建，flag=updata修改，flag=del删除）
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/operatBaseDiscretion")
	public TeeJson operatBaseDiscretion(HttpServletRequest request){
		TeeJson waitAppointedInfo = mobileService.operatBaseDiscretion(request);
		return waitAppointedInfo;
	}
	
	/**
	 * 获取未最终回复列表
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getUnReplyPunish")
	public TeeEasyuiDataGridJson getUnReplyPunish(HttpServletRequest request){
		Map<String, String> requestData = TeeServletUtility.getParamMap(request); 
		//获取当前登录用户
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		//初始化列表分页
		TeeDataGridModel gridModel = new TeeDataGridModel();
		gridModel.setPage(TeeStringUtil.getInteger(requestData.get("start"), 1));
		gridModel.setRows(TeeStringUtil.getInteger(requestData.get("length"), 10));
		TeeEasyuiDataGridJson datagrid = mobileService.getUnReplyPunish(person, gridModel);
		
		return datagrid;
	}
	
	/**
	 * 获取已最终回复列表
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getReplyPunish")
	public TeeEasyuiDataGridJson getReplyPunish(HttpServletRequest request){
		Map<String, String> requestData = TeeServletUtility.getParamMap(request); 
		//获取当前登录用户
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		//初始化列表分页
		TeeDataGridModel gridModel = new TeeDataGridModel();
		gridModel.setPage(TeeStringUtil.getInteger(requestData.get("start"), 1));
		gridModel.setRows(TeeStringUtil.getInteger(requestData.get("length"), 10));
		TeeEasyuiDataGridJson datagrid = mobileService.getReplyPunish(person, gridModel);
		
		return datagrid;
	}
	
	/**
	 * 获取回复案件的详情
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/punishReplyDetails")
	public TeeJson punishReplyDetails(HttpServletRequest request) throws Exception {
		TeeJson result = new TeeJson();
		JSONObject jsonObject = new JSONObject();
		//获取请求参数
		String baseId = TeeStringUtil.getString(request.getParameter("baseId"), "");
		
		if("".equals(baseId)) {
			result.setRtState(false);
			result.setRtMsg("未查询到案件主键");
			return result;
		}
		
		//案件基础信息
		PunishBase punishBase = punishBaseService.getbyid(baseId);
		PunishBaseModel punishBaseModel = new PunishBaseModel();
		BeanUtils.copyProperties(punishBase, punishBaseModel);
		//获取参与人list
		List<AffiliatedPerson> personList = affiliService.getListByBaseId(baseId);
		punishBaseModel.setPsnArrList(personList);
		
		jsonObject.put("punishBase", punishBaseModel);
		
		
		//回复list
		List<ClueReply> clueReplies = replyService.findClueReply(punishBaseModel.getSourceId());
		jsonObject.put("clueReplies", clueReplies);
		
		result.setRtData(jsonObject.toString());
		result.setRtState(true);
		
		return result;
	}
	
	/**
	 * 通过runId 获取
	 * 
	 * @param runId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getFlowByRunIdOrFlowName")
	public TeeJson getFlowByRunIdOrFlowName (String runId, String flowName) {
		TeeJson json = new TeeJson();
		
		PunishFLow flow = punishFlowService.getFlowByRunIdOrFlowName(runId, flowName);
		
		if (flow != null) {
			json.setRtState(true);
		} else {
			json.setRtState(false);
		}

		json.setRtData(flow);
		return json;
	}
}
