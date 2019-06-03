package com.beidasoft.xzzf.punish.common.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.clue.bean.Clue;
import com.beidasoft.xzzf.clue.service.ClueService;
import com.beidasoft.xzzf.inspection.code.service.BaseCodeDetailService;
import com.beidasoft.xzzf.punish.common.bean.AffiliatedPerson;
import com.beidasoft.xzzf.punish.common.bean.ConfTache;
import com.beidasoft.xzzf.punish.common.bean.PunishBase;
import com.beidasoft.xzzf.punish.common.bean.PunishCalendar;
import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.model.PunishBaseModel;
import com.beidasoft.xzzf.punish.common.model.PunishCalendarModel;
import com.beidasoft.xzzf.punish.common.model.PunishRecordModel;
import com.beidasoft.xzzf.punish.common.service.AffiliatedService;
import com.beidasoft.xzzf.punish.common.service.ConfTacheService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseService;
import com.beidasoft.xzzf.punish.common.service.PunishCalendarService;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.punish.common.service.PunishRecordService;
import com.beidasoft.xzzf.punish.common.service.PunishTacheService;
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
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("/punishBaseController")
public class PunishBaseController {
	
	@Autowired
	PunishBaseService baseService;
	
	@Autowired
	AffiliatedService affiliService;
	
	@Autowired
	TeePersonService personService;
	
	@Autowired
	private PunishTacheService tacheService;
	
	@Autowired
	private PunishBaseService punishBaseService;
	
	@Autowired
	private PunishCalendarService punishCalendarService;
	
	@Autowired
	private BaseCodeDetailService codeDetailService;
	
	@Autowired
	private PunishRecordService punishRecordService ;
	
	@Autowired
	private ConfTacheService confTacheService;
	
	@Autowired
	@Qualifier("teeWorkflowService")
	private TeeWorkflowServiceInterface workflowService;
	
	@Autowired
	private PunishFlowService flowService;
	
	@Autowired
	private ClueService clueService;
	
	@RequestMapping("/table")
	@ResponseBody
	public TeeEasyuiDataGridJson table(HttpServletRequest request, TeeDataGridModel dm) {
		return baseService.getPunishBaseOfPage(dm, request);
	}
	
	/**
	 * 获取参与人信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getPersonInfo")
	@ResponseBody
	public TeeJson getPersonDeptInfo(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		Map<String, Object> mapInfo = null;
		List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();
		// 用户UUID群
		String userIds = request.getParameter("userIds");
		// 获取用户相关信息
		List<TeePerson> psnList = personService.getPersonByUuids(userIds);
		for (TeePerson psnInfo : psnList) {
			mapInfo = new HashMap<String, Object>();
			// 用户UUID
			mapInfo.put("userUuid", psnInfo.getUuid());
			// 用户名
			mapInfo.put("userName", psnInfo.getUserName());
			// 执法证号
			mapInfo.put("userCode", StringUtils.defaultIfBlank(psnInfo.getOicqNo(), ""));
			// 部门UUID
			mapInfo.put("deptUuid", psnInfo.getDept().getUuid());
			// 部门名称
			mapInfo.put("deptName", psnInfo.getDept().getDeptName());
			rtnList.add(mapInfo);
		}
		json.setRtState(true);
		json.setRtData(rtnList);
		return json;
	}
	
	/**
	 * 保存案件信息 （现场检查用）
	 * @param baseModel
	 * @return
	 */
	@RequestMapping("/savePartyInfo")
	@ResponseBody
	public TeeJson savePartyInfo(PunishBaseModel baseModel, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		
		json = baseService.save(baseModel, request);
		
		json.setRtState(true);
		return json;
	}
	
	
	
	/**
	 * 获取所有的案件信息  punishBase
	 * @param request
	 * @param dm
	 * @return
	 */
	@RequestMapping("/baseList")
	@ResponseBody
	public TeeEasyuiDataGridJson getALL(HttpServletRequest request, TeeDataGridModel dm) {
		return baseService.getPunishBaseOfPage(dm, request);
	}
	/**
	 * 通过baseId 查询单个 punishBase  案件信息
	 * @param baseid
	 * @return
	 */
	@RequestMapping("/getByBaseId")
	@ResponseBody
	public TeeJson getByBaseId(HttpServletRequest request) {
		String baseId = request.getParameter("baseId");
		
		TeeJson json = new TeeJson();
		
		PunishBase base = baseService.getbyid(baseId);
		PunishBaseModel baseModel = new PunishBaseModel();
		
		BeanUtils.copyProperties(base, baseModel);
		
		//单独处理时间类型转换
		baseModel.setFilingDateStr(TeeDateUtil.format(base.getFilingDate()));
		baseModel.setInspectionDateStr(TeeDateUtil.format(base.getInspectionDate()));
		baseModel.setPunishmentDateStr(TeeDateUtil.format(base.getPunishmentDate()));
		baseModel.setPunishmentExeDateStr(TeeDateUtil.format(base.getPunishmentExeDate()));
		baseModel.setClosedDateStr(TeeDateUtil.format(base.getClosedDate()));
		
		//获取参与人列表
		List<AffiliatedPerson> psnArr = affiliService.getListByBaseId(baseId);
		if(psnArr.size() > 0){
			baseModel.setPsnArrList(psnArr);
		} else {
			baseModel.setPsnArrList(new ArrayList<AffiliatedPerson>()); //返回一个空对象数组
		}
		
		json.setRtData(baseModel);
		
		return json;
	}
	/**
	 * 根据环节执行步骤修改base表环节状态码（10.立案 20.调查取证30.责改告知 40.案件呈批 50.执行结案）
	 */
	@RequestMapping("/updatebyId")
	@ResponseBody
	@Transactional
	public TeeJson updatebyId(HttpServletRequest request) {
		String baseId = request.getParameter("baseId");
		String status = request.getParameter("status");
		int isHearing = TeeStringUtil.getInteger(request.getParameter("isHearing"), 0);
		TeeJson json = new TeeJson();
		PunishBase base = baseService.getbyid(baseId);
		base.setStatus(status);
		base.setIsHearing(isHearing);
		baseService.update(base);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 办理结案更新案件状态
	 * @param punishBase
	 * @param request
	 * @return
	 */
	@RequestMapping("/closedCase")
	@ResponseBody
	public TeeJson closedCase(PunishBase punishBase, HttpServletRequest request) {
		TeeJson result = new TeeJson();
		String baseId = request.getParameter("baseId");
		//办结人员
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		punishBaseService.caseClosed(baseId, person);
		result.setRtState(true);
		return result;
	}
	
	@RequestMapping("/transManage")
	@ResponseBody
	public TeeEasyuiDataGridJson transManage(PunishBaseModel punishBaseModel, TeeDataGridModel dm) {
		TeeEasyuiDataGridJson dataJson = new TeeEasyuiDataGridJson();
		long total = punishBaseService.getTransTotal(punishBaseModel);
		List<PunishBaseModel> modelList = new ArrayList<>();
		List<PunishBase> punishBasesList = punishBaseService.getTransPunish(punishBaseModel, dm);
		
		for (PunishBase punishBase : punishBasesList) {
			PunishBaseModel punishBaseModelRow = new PunishBaseModel();
			
			BeanUtils.copyProperties(punishBase, punishBaseModelRow);
			
			// 单独处理时间类型转换
			if (punishBase.getPunishmentDate() != null) {
				punishBaseModelRow.setPunishmentDateStr(TeeDateUtil.format(punishBase.getPunishmentDate(),
						"yyyy年MM月dd日"));
			}
			
			modelList.add(punishBaseModelRow);
		}
		dataJson.setTotal(total);
		dataJson.setRows(modelList);
		
		return dataJson;
	}
	
	/**
	 * 案件归档
	 * @param caseBaseModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updatePunishType")
	public TeeJson updatePunishType(PunishBaseModel punishBaseModel) {
		TeeJson json = new TeeJson();
		String msg = "";
		
		PunishBase punishBase = baseService.getbyid(punishBaseModel.getBaseId());
		punishBase.setPunishType(punishBaseModel.getPunishType());
		
		if("0".equals(punishBase.getPunishFlg())) {
			punishBase.setPunishFlg(punishBaseModel.getPunishFlg());
			baseService.updateFile(punishBase);
			msg += "存档成功！";
		}else {
			msg += "该案件已存档，请去文书存档中查看";
		}
		json.setRtMsg(msg);
		json.setRtState(true);
		return json;
	}
	
	
	/**
	 * 案件借阅
	 * @param punishBaseModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/borrowPunishType")
	public TeeJson borrowPunishType(PunishBaseModel punishBaseModel, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		PunishRecordModel punishRecordModel = new PunishRecordModel();
		String msg = "";
		
		PunishBase punishBase = baseService.getbyid(punishBaseModel.getBaseId());
		punishBase.setBorrowingFlg(punishBaseModel.getBorrowingFlg());
		
		punishRecordModel.setBaseId(TeeStringUtil.getString(request.getParameter("baseId"), ""));
		
		if ("0".equals(punishBase.getBorrowingFlg())) {
			punishRecordModel.setOperationContent("归还案件（"+ punishBase.getBaseCode() +"）");
			msg += "归还成功！";
		} else if("1".equals(punishBase.getBorrowingFlg())) {
			punishRecordModel.setOperationContent("借阅案件（"+ punishBase.getBaseCode() +"）");
			msg += "借阅成功！";
		}
		punishRecordModel.setConfTacheName("案件借阅");
		punishRecordService.save(punishRecordModel, request);
		baseService.updateFile(punishBase);
		
		json.setRtMsg(msg);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 查询归档案件
	 * @param punishBaseModel
	 * @param dataGridModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getPunishBases")
	public TeeEasyuiDataGridJson getPunishBases(PunishBaseModel punishBaseModel,TeeDataGridModel dataGridModel) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		long total = punishBaseService.getTotal(punishBaseModel);
		List<PunishBaseModel> modelList = new ArrayList<>();
		List<PunishBase> punishBasesList = punishBaseService.getPunishBases(punishBaseModel, dataGridModel);
		
		for (PunishBase punishBase : punishBasesList) {
			PunishBaseModel punishBaseModelRow = new PunishBaseModel();
			
			BeanUtils.copyProperties(punishBase, punishBaseModelRow);
			
			// 单独处理时间类型转换
			if (punishBase.getPunishmentDate() != null) {
				punishBaseModelRow.setPunishmentDateStr(TeeDateUtil.format(punishBase.getPunishmentDate(),
						"yyyy年MM月dd日"));
			}
			
			modelList.add(punishBaseModelRow);
		}
		dataGridJson.setTotal(total);
		dataGridJson.setRows(modelList);
		
		return dataGridJson;
	}
	
	@ResponseBody
	@RequestMapping("/monthList")
	public TeeJson monthList(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);	
		
		int year = TeeStringUtil.getInteger(request.getParameter("year"),0);
		int month = TeeStringUtil.getInteger(request.getParameter("month"),0);
		String baseId = TeeStringUtil.getString(request.getParameter("baseId"), "");
		
		json.setRtData(punishCalendarService.monthList(loginPerson, year, month, baseId));
		return json;
		
	}
	
	@RequestMapping("/getPunishCalendar")
	@ResponseBody
	public TeeJson getPunishCalendar(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);	
		String date = TeeStringUtil.getString(request.getParameter("date"));
		String baseId = TeeStringUtil.getString(request.getParameter("baseId"), "");
		List<PunishCalendar> calendars = punishCalendarService.getPunishCalendar(loginPerson, date, baseId);
		List<PunishCalendarModel> calendarModels = new ArrayList<>();
		for (PunishCalendar punishCalendar : calendars) {
			PunishCalendarModel calendarModel = new PunishCalendarModel();
			BeanUtils.copyProperties(punishCalendar, calendarModel);
			calendarModels.add(calendarModel);
		}
		json.setRtData(calendarModels);
		return json;
	}
	
	/**
	 * 案卷查询（按当前登录人）
	 * @param dm
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/getRecordsList")
	@ResponseBody
	public TeeEasyuiDataGridJson getRecordsList(TeeDataGridModel dm,PunishBaseModel model, HttpServletRequest request){
		return baseService.getRecordsList(dm, model, request);
	}
	/**
	 * 查询所有已归档案件
	 * @param dm
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/ListPunishBase")
	@ResponseBody
	public TeeEasyuiDataGridJson ListPunishBase(TeeDataGridModel dm,PunishBaseModel model, HttpServletRequest request){
		return baseService.ListPunishBase(dm, model, request);
	}
	
	/**
	 * 案卷日常统计周统计
	 * @param request
	 * @return
	 */
	@RequestMapping("/getListDaily")
	@ResponseBody
	public TeeJson getListDaily(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		
		json.setRtData(baseService.getListDaily(request));
		
		return json;
	}
	
	/**
	 * 案卷日常统计领域类型统计
	 * @param request
	 * @return
	 */
	@RequestMapping("/getListDoMainType")
	@ResponseBody
	public TeeJson getListDoMainType(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		
		json.setRtData(baseService.getListDoMainType(request));
		
		return json;
	}
	
	/**
	 * 案卷日常统计月统计
	 * @param request
	 * @return
	 */
	@RequestMapping("/getListMonth")
	@ResponseBody
	public TeeJson getListMonth(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		
		json.setRtData(baseService.getListMonth(request));
		
		return json;
	}
	
	/**
	 * 如果是登陆人为执法队长返回true
	 * @param request
	 * @return
	 */
	@RequestMapping("/doUndertaker")
	@ResponseBody
	public TeeJson doUndertaker(HttpServletRequest request){
		TeeJson json =  new TeeJson();
		
		// 拿到登陆人信息
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		if (loginPerson.getUserRole().getUuid() == 12) {
			json.setRtState(true);
		}else{
			json.setRtState(false);
		}
		
		return json;
	}

	/**
	 * 工作台页面获取执法队案件办理量（柱形图）
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCaseHandle")
	@ResponseBody
	public TeeJson getCaseHandle(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		List result = null;
		
		result = baseService.getCaseHandle(request);
		
		Map<String, Object> map = new HashMap<String, Object>();
		List datas = new ArrayList();
		List<String> name = new ArrayList<String>();
		List<Integer> num = new ArrayList<Integer>();
		for (int i = 0; i < result.size(); i++) {
			name.add(((Map)result.get(i)).get("namea").toString());
			num.add(Integer.parseInt(((Map)result.get(i)).get("num").toString()));
		}
		map.put("name", name);
		map.put("num", num);
		json.setRtData(map);
		if(name.isEmpty()){
			json.setRtState(false);
		}else{
			json.setRtState(true);
		}
		return json;
	}
	
	/**
	 * 工作台页面获取执法队案件办理量（饼状图）
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCasePie")
	@ResponseBody
	public TeeJson getCasePie(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		List result = null;
		
		result = baseService.getCasePie(request);
		
		Map<String, Object> map = new HashMap<String, Object>();
		List datas = new ArrayList();
		for (int i = 0; i < result.size(); i++) {
			map = new HashMap<String, Object>();
			map.put("name",((Map)result.get(i)).get("namea").toString());
			map.put("value",Integer.parseInt(((Map)result.get(i)).get("num").toString()));
			datas.add(map);
		}
		json.setRtData(datas);
		if(datas.isEmpty()){
			json.setRtState(false);
		}else{
			json.setRtState(true);
		}
		return json;
	}	
	
	/**
 	 * 分页显示任务接受信息
 	 * 
 	 * @param dataGridModel
 	 * @param queryModel
 	 * @return
 	 */
 	@ResponseBody
 	@RequestMapping("/listByPage")
 	public TeeEasyuiDataGridJson listBypage(TeeDataGridModel dataGridModel,PunishBaseModel queryModel, HttpServletRequest request) {
 		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();

 		// 通过分页获取用户信息数据的list集合
 		long total = baseService.count(queryModel, request);
 		List<PunishBaseModel> modelList = new ArrayList<PunishBaseModel>();
 		List<PunishBase> rpsInfos = baseService.listByPage(dataGridModel.getFirstResult(), dataGridModel.getRows(),queryModel, request);
 		for (PunishBase rpsInfo : rpsInfos) {
 			PunishBaseModel infoModel = new PunishBaseModel();
 			BeanUtils.copyProperties(rpsInfo, infoModel);
 			 //单独处理
 			
 			modelList.add(infoModel);
 		}
 		
 		dataGridJson.setTotal(total);
 		dataGridJson.setRows(modelList);

 		return dataGridJson;
 	}
 	
 	/**
 	 * 待办任务
 	 * 
 	 * @param dm
 	 * @param queryModel
 	 * @param request
 	 * @return
 	 */
 	@ResponseBody
 	@RequestMapping("/getTable")
 	public Map<String, Object> getTable(PunishBaseModel queryModel, HttpServletRequest request) {
 		
		Map<String, Object> map = new HashMap<String, Object>();
		List<PunishBaseModel> modelList = new ArrayList<PunishBaseModel>();
		
		List<PunishBase> rpsInfos = baseService.listByPage(queryModel.getStart(), queryModel.getLength(), queryModel, request);
		for (int i = 0; i < rpsInfos.size(); i++) {
			PunishBase rpsInfo = rpsInfos.get(i);
			PunishBaseModel infoModel = baseService.baseferModel(rpsInfo);
			if (rpsInfo.getSourceType().equals("10")) { //线索
				Clue clue =clueService.get(rpsInfo.getSourceId());
				int dealTime = 0;
				if (clue != null) {
					int time = clue.getDealTime();
					if (time == 10) {
						dealTime = 10;
					} else if (time == 20) {
						dealTime = 15;
					} else if (time == 30) {
						dealTime = 30;
					} else {
						dealTime = clue.getDealTimeRemark();
					}
				}
				infoModel.setDealTime(dealTime+"");
			} else if (rpsInfo.getSourceType().equals("20")) { //双随机
				
			} else if (rpsInfo.getSourceType().equals("30")) { //自发检查
				
			}
			infoModel.setCode((i + 1) + "");
			modelList.add(infoModel);
		}
		JSONArray jsonArray = JSONArray.fromObject(modelList);
		long total = baseService.count(queryModel, request);

		// 返回值
		map.put("recordsTotal", total);
		map.put("recordsFiltered", total);
		map.put("data", jsonArray);

		return map;
 	}
 	
 	@ResponseBody
 	@RequestMapping("/getTableHandling")
 	public Map<String, Object> getTableHandling(PunishBaseModel queryModel, HttpServletRequest request) {
 		Map<String, Object> map = new HashMap<String, Object>();
		List<PunishBaseModel> modelList = new ArrayList<PunishBaseModel>();
		List<PunishBase> rpsInfos = baseService.listByHandling(queryModel.getStart() , queryModel.getLength() , queryModel , request);
	 	for (int i = 0 ; i < rpsInfos.size(); i++) {
	 		PunishBaseModel infoModel = new PunishBaseModel();
		 	BeanUtils.copyProperties(rpsInfos.get(i), infoModel);
		 	infoModel.setCode((i+1)+"");
		 	//单独处理
		 	modelList.add(infoModel);
	 	}
	 	long total = baseService.countByHandling(queryModel, request);
	 	//将数据转换成json格式
	 	JSONArray jsonArray = JSONArray.fromObject(modelList);
	 	//返回参数也是固定的 
	 	map.put("recordsTotal", total);//数据总条数 
	 	map.put("recordsFiltered", total);//显示的条数 
	 	map.put("data", jsonArray);//数据集合 
	 	return map;
 	}
 	
 	@ResponseBody
 	@RequestMapping("/getTableApproval")
 	public Map<String, Object> getTableApproval(PunishBaseModel queryModel, HttpServletRequest request) {
	 	Map<String, Object> map = new HashMap<String, Object>(); 
	 	//当前登录人
	 	TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
	 	//request
		Map requestData = TeeServletUtility.getParamMap(request);
		TeeDataGridModel m = new TeeDataGridModel();
		m.setPage(queryModel.getStart()/queryModel.getLength()+1);
		m.setRows(queryModel.getLength());
		//查询待办任务
	 	TeeEasyuiDataGridJson json = workflowService.getReceivedWorks(requestData,person,m);
	 	List<Map<String, Object>> rows = json.getRows();//数据
	 	//查询文书文号
	 	for (Map daiBan : rows) {
	 		if (daiBan.containsKey("runId")) {
	 			PunishFLow flow = flowService.getFlowByRunIdOrFlowName(daiBan.get("runId").toString().toString(), daiBan.get("flowName").toString());
	 			if (!TeeUtility.isNullorEmpty(flow)) {
	 				ConfTache tache = confTacheService.getById(flow.getTacheId());
	 				daiBan.put("tacheName",tache.getConfTacheName());
	 				daiBan.put("wenHao",flow.getContentsNumber());
				} else {
					daiBan.put("tacheName","");
					daiBan.put("wenHao","");
				}
			}
		}
	 	
	 	
	 	long total = json.getTotal();//总记录数
	 	//返回参数也是固定的 
	 	map.put("recordsTotal", total);//数据总条数 
	 	map.put("recordsFiltered", total);//显示的条数 
	 	map.put("data", rows);//数据集合 
	 	return map;
 	}
 	
 	
 	/**
	 * 查询案件办结的总数根据部门分组
	 * @return
	 */
 	@ResponseBody
 	@RequestMapping("/getClosedCountByDept")
	public TeeJson getClosedCountByDept(HttpServletRequest request){
 		TeeJson json = new TeeJson();
		// 返回的map
		Map<String, Integer> mapList = new HashMap<String, Integer>();

		List<Map<String, Object>> mapC = baseService.getClosedCountByDept(request);;


		for (int i = 0; i < mapC.size(); i++) {
			// 领域类型为key，值为value
			mapList.put("C"+ mapC.get(i).get("namea").toString(),Integer.parseInt((mapC.get(i)).get("num").toString()));
		}
		
		List<Map<String, Object>> mapN = baseService.getNotClosedCountByDept(request);


		for (int i = 0; i < mapN.size(); i++) {
			// 领域类型为key，值为value
			mapList.put("N"+ mapN.get(i).get("namea").toString(),Integer.parseInt((mapN.get(i)).get("num").toString()));
		}
		
		// 拿到结案的五个执法队，如果执法队没有办理结案的案件赋值未0
		if (mapList.get("C10") == null) {
			mapList.put("C10", 0);
		}
		if (mapList.get("C11") == null) {
			mapList.put("C11", 0);
		}
		if (mapList.get("C12") == null) {
			mapList.put("C12", 0);
		}
		if (mapList.get("C13") == null) {
			mapList.put("C13", 0);
		}
		if (mapList.get("C14") == null) {
			mapList.put("C14", 0);
		}
		//拿到未结案的五个执法队，如果执法队没有未办理结案的案件赋值未0
		if (mapList.get("N10") == null) {
			mapList.put("N10", 0);
		}
		if (mapList.get("N11") == null) {
			mapList.put("N11", 0);
		}
		if (mapList.get("N12") == null) {
			mapList.put("N12", 0);
		}
		if (mapList.get("N13") == null) {
			mapList.put("N13", 0);
		}
		if (mapList.get("N14") == null) {
			mapList.put("N14", 0);
		}
		
		json.setRtData(mapList);
		if (mapList.isEmpty()) {
			json.setRtState(false);
		} else {
			json.setRtState(true);
		}
		return json;
	}
 	
 	/**
	 * 查询案件办结的总数根据领域分组
	 * @return
	 */
 	@ResponseBody
 	@RequestMapping("/getClosedCountByDomain")
	public TeeJson getClosedCountByDomain(HttpServletRequest request){
 		TeeJson json = new TeeJson();

		List<Map<String, Object>> map = baseService.getClosedCountByDomain(request);

		// 返回的map
		Map<String, Integer> mapList = new HashMap<String, Integer>();

		for (int i = 0; i < map.size(); i++) {
			if((map.get(i)).get("namea").toString() != null){
				// 领域类型为key，值为value
				mapList.put("D" + (map.get(i)).get("namea").toString(),
						Integer.parseInt((map.get(i)).get("num").toString()));
			}
		}

		// 如果为空，赋值为o
		if (mapList.get("D10") == null) {
			mapList.put("D10", 0);
		}
		if (mapList.get("D20") == null) {
			mapList.put("D20", 0);
		}
		if (mapList.get("D30") == null) {
			mapList.put("D30", 0);
		}
		if (mapList.get("D40") == null) {
			mapList.put("D40", 0);
		}
		if (mapList.get("D50") == null) {
			mapList.put("D50", 0);
		}
		if (mapList.get("D60") == null) {
			mapList.put("D60", 0);
		}
		if (mapList.get("D70") == null) {
			mapList.put("D70", 0);
		}

		json.setRtData(mapList);
		if (mapList.isEmpty()) {
			json.setRtState(false);
		} else {
			json.setRtState(true);
		}
		return json;
	}
 	
 	/**
 	 * 查询liutiejing桌面统计数据
 	 * @return
 	 */
 	@ResponseBody
 	@RequestMapping("/getLiutiejingData")
 	public TeeJson getLiutiejingData(HttpServletRequest request){
 		TeeJson json = new TeeJson();
 		List dealList = new ArrayList();
 		List inspectionList = new ArrayList();
 		dealList.add(baseService.getLeaderCountDeal("day", 11, 1));
 		dealList.add(baseService.getLeaderCountDeal("day", 11, 2));
 		dealList.add(baseService.getLeaderCountDeal("day", 11, 3));
 		dealList.add(baseService.getLeaderCountDeal("day", 11, 4));
 		dealList.add(baseService.getLeaderCountDeal("day", 14, 1));
 		dealList.add(baseService.getLeaderCountDeal("day", 14, 2));
 		dealList.add(baseService.getLeaderCountDeal("day", 14, 3));
 		dealList.add(baseService.getLeaderCountDeal("day", 14, 4));
 		dealList.add(baseService.getLeaderCountDeal("day", 13, 1));
 		dealList.add(baseService.getLeaderCountDeal("day", 13, 2));
 		dealList.add(baseService.getLeaderCountDeal("day", 13, 3));
 		dealList.add(baseService.getLeaderCountDeal("day", 13, 4));
 		inspectionList.add(baseService.getLeaderCountInspection("day", 11));
 		inspectionList.add(baseService.getLeaderCountInspection("day", 14));
 		inspectionList.add(baseService.getLeaderCountInspection("day", 15));
 		inspectionList.add(baseService.getLeaderCountInspection("year", 11));
 		inspectionList.add(baseService.getLeaderCountInspection("year", 14));
 		inspectionList.add(baseService.getLeaderCountInspection("year", 15));
 		Map map = new HashMap();
 		map.put("deal", dealList);
 		map.put("inspection", inspectionList);
 		json.setRtState(true);
 		json.setRtData(map);
 		return json;
 	}
 	/**
 	 * 查询wangningzhi桌面统计数据
 	 * @return
 	 */
 	@ResponseBody
 	@RequestMapping("/getWangningzhiData")
 	public TeeJson getWangningzhiData(HttpServletRequest request){
 		TeeJson json = new TeeJson();
 		List dealList = new ArrayList();
 		List inspectionList = new ArrayList();
 		List reportList = new ArrayList();
 		dealList.add(baseService.getLeaderCountDeal("day", 0, 1));
 		dealList.add(baseService.getLeaderCountDeal("day", 0, 2));
 		dealList.add(baseService.getLeaderCountDeal("day", 0, 3));
 		dealList.add(baseService.getLeaderCountDeal("day", 12, 1));
 		dealList.add(baseService.getLeaderCountDeal("day", 12, 2));
 		dealList.add(baseService.getLeaderCountDeal("day", 12, 3));
 		dealList.add(baseService.getLeaderCountDeal("year", 0, 0));
 		dealList.add(baseService.getLeaderCountDeal("year", 0, 3));
 		dealList.add(baseService.getLeaderCountDeal("year", 0, 4));
 		dealList.add(baseService.getLeaderCountDeal("year", 12, 0));
 		dealList.add(baseService.getLeaderCountDeal("year", 12, 3));
 		dealList.add(baseService.getLeaderCountDeal("year", 12, 4));
 		inspectionList.add(baseService.getLeaderCountInspection("day", 0));
 		inspectionList.add(baseService.getLeaderCountInspection("day", 12));
 		inspectionList.add(baseService.getLeaderCountInspection("year", 0));
 		inspectionList.add(baseService.getLeaderCountInspection("year", 12));
 		reportList.add(baseService.getLeaderCountReport(1));
 		reportList.add(baseService.getLeaderCountInspection("all",0));
 		reportList.add(baseService.getLeaderCountReport(2));
 		reportList.add(baseService.getLeaderCountInspection("all",0));
 		Map map = new HashMap();
 		map.put("deal", dealList);
 		map.put("inspection", inspectionList);
 		map.put("report", reportList);
 		json.setRtState(true);
 		json.setRtData(map);
 		return json;
 	}
 	/**
 	 * 查询zhoudaqing桌面统计数据
 	 * @return
 	 */
 	@ResponseBody
 	@RequestMapping("/getZhoudaqingData")
 	public TeeJson getZhoudaqingData(HttpServletRequest request){
 		TeeJson json = new TeeJson();
 		List dealList = new ArrayList();
 		List inspectionList = new ArrayList();
 		dealList.add(baseService.getLeaderCountDeal("day", 10, 1));
 		dealList.add(baseService.getLeaderCountDeal("day", 10, 2));
 		dealList.add(baseService.getLeaderCountDeal("day", 10, 3));
 		dealList.add(baseService.getLeaderCountDeal("year", 10, 0));
 		dealList.add(baseService.getLeaderCountDeal("year", 10, 3));
 		dealList.add(baseService.getLeaderCountDeal("year", 10, 4));
 		inspectionList.add(baseService.getLeaderCountInspection("day", 10));
 		inspectionList.add(baseService.getLeaderCountInspection("year", 10));
 		Map map = new HashMap();
 		map.put("deal", dealList);
 		map.put("inspection", inspectionList);
 		json.setRtState(true);
 		json.setRtData(map);
 		return json;
 	}
 	/**
 	 * 查询zhangwei桌面统计数据
 	 * @return
 	 */
 	@ResponseBody
 	@RequestMapping("/getZhangweiData")
 	public TeeJson getZhangweiData(HttpServletRequest request){
 		TeeJson json = new TeeJson();
 		List reportList = new ArrayList();
 		reportList.add(baseService.getLeaderCountReport(1));
 		reportList.add(baseService.getLeaderCountInspection("all",0));
 		reportList.add(baseService.getLeaderCountReport(2));
 		reportList.add(baseService.getLeaderCountInspection("all",0));
 		Map map = new HashMap();
 		map.put("report", reportList);
 		json.setRtState(true);
 		json.setRtData(map);
 		return json;
 	}
 	/**
 	 * 查询wangningzhi桌面统计数据
 	 * @return
 	 */
 	@ResponseBody
 	@RequestMapping("/getLishujiangData")
 	public TeeJson getLishujiangData(HttpServletRequest request){
 		TeeJson json = new TeeJson();
 		List dealList = new ArrayList();
 		List inspectionList = new ArrayList();
 		List reportList = new ArrayList();
 		dealList.add(baseService.getLeaderCountDeal("day", 0, 1));
 		dealList.add(baseService.getLeaderCountDeal("day", 0, 2));
 		dealList.add(baseService.getLeaderCountDeal("day", 0, 3));
 		dealList.add(baseService.getLeaderCountDeal("year", 0, 0));
 		dealList.add(baseService.getLeaderCountDeal("year", 0, 3));
 		dealList.add(baseService.getLeaderCountDeal("year", 0, 4));
 		inspectionList.add(baseService.getLeaderCountInspection("day", 0));
 		inspectionList.add(baseService.getLeaderCountInspection("year", 0));
 		reportList.add(baseService.getLeaderCountReport(1));
 		reportList.add(baseService.getLeaderCountInspection("all",0));
 		reportList.add(baseService.getLeaderCountReport(2));
 		reportList.add(baseService.getLeaderCountInspection("all",0));
 		Map map = new HashMap();
 		map.put("deal", dealList);
 		map.put("inspection", inspectionList);
 		map.put("report", reportList);
 		json.setRtState(true);
 		json.setRtData(map);
 		return json;
 	}
 	
 	
 	/**
	 * 根据领域类型查询执法检查量
	 * SOURCE_TYPE 案件来源 20 双随机检查  30 现场检查
	 * @return
	 */
 	@ResponseBody
 	@RequestMapping("/getSourceByDomain")
	public TeeJson getSourceByDomain(HttpServletRequest request){
		TeeJson json = new TeeJson();

		List<Map<String, Object>> map = baseService.getSourceByDomain(request);

		// 返回的map
		Map<String, Integer> mapList = new HashMap<String, Integer>();

		for (int i = 0; i < map.size(); i++) {
			if((map.get(i)).get("namea").toString() != null){
				// 领域类型为key，值为value
				mapList.put("D" + (map.get(i)).get("namea").toString(),
						Integer.parseInt((map.get(i)).get("num").toString()));
			}
		}
		// 如果为空，赋值为o
		if (mapList.get("D10") == null) {
			mapList.put("D10", 0);
		}
		if (mapList.get("D20") == null) {
			mapList.put("D20", 0);
		}
		if (mapList.get("D30") == null) {
			mapList.put("D30", 0);
		}
		if (mapList.get("D40") == null) {
			mapList.put("D40", 0);
		}
		if (mapList.get("D50") == null) {
			mapList.put("D50", 0);
		}
		if (mapList.get("D60") == null) {
			mapList.put("D60", 0);
		}
		if (mapList.get("D70") == null) {
			mapList.put("D70", 0);
		}

		json.setRtData(mapList);
		if (mapList.isEmpty()) {
			json.setRtState(false);
		} else {
			json.setRtState(true);
		}
		return json;
	}
 	
 	/**
	 * 根据部门查询执法检查量
	 * SOURCE_TYPE 案件来源 20 双随机检查  30 现场检查
	 * @return
	 */
 	@ResponseBody
 	@RequestMapping("/getSourceByDept")
	public TeeJson getSourceByDept(HttpServletRequest request){
		TeeJson json = new TeeJson();
		// 返回的map
		Map<String, Integer> mapList = new HashMap<String, Integer>();

		List<Map<String, Object>> map = baseService.getSourceByDept(request);;


		for (int i = 0; i < map.size(); i++) {
			// 领域类型为key，值为value
			mapList.put("Dept"+ map.get(i).get("namea").toString(),Integer.parseInt((map.get(i)).get("num").toString()));
		}
		
		// 拿到结案的五个执法队，如果执法队没有办理结案的案件赋值未0
		if (mapList.get("Dept10") == null) {
			mapList.put("Dept10", 0);
		}
		if (mapList.get("Dept11") == null) {
			mapList.put("Dept11", 0);
		}
		if (mapList.get("Dept12") == null) {
			mapList.put("Dept12", 0);
		}
		if (mapList.get("Dept13") == null) {
			mapList.put("Dept13", 0);
		}
		if (mapList.get("Dept14") == null) {
			mapList.put("Dept14", 0);
		}

		json.setRtData(mapList);
		if (mapList.isEmpty()) {
			json.setRtState(false);
		} else {
			json.setRtState(true);
		}
		return json;
	}

	/**
	 * 执法人员确认接收任务
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/isReceive")
	public TeeJson isReceive(String baseId) {
		TeeJson teeJson = new TeeJson();
		
		PunishBase base = baseService.getbyid(baseId);
		base.setReceiveDate(Calendar.getInstance());
		baseService.update(base);
		
		teeJson.setRtData(base);
		teeJson.setRtState(true);
		return teeJson;
	}
	
	/**
	 * 获取案件列表（执法视频管理用）
	 * @param dm
	 * @param punishBaseModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getListOfVideo")
	public TeeEasyuiDataGridJson getListOfVideo(TeeDataGridModel dm, PunishBaseModel punishBaseModel, HttpServletRequest request) {
		return baseService.getListOfVideo(dm, punishBaseModel);
	}
}


