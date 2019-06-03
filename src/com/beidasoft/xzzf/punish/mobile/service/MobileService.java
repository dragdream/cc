package com.beidasoft.xzzf.punish.mobile.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;












































import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.clue.bean.ClueReply;
import com.beidasoft.xzzf.clue.service.ClueReplyService;
import com.beidasoft.xzzf.law.bean.BaseLaw;
import com.beidasoft.xzzf.law.bean.BaseLawDetail;
import com.beidasoft.xzzf.law.model.BaseLawModel;
import com.beidasoft.xzzf.power.bean.BaseDiscretion;
import com.beidasoft.xzzf.power.bean.BasePower;
import com.beidasoft.xzzf.power.bean.BasePowerDetail;
import com.beidasoft.xzzf.power.bean.BasePowerGist;
import com.beidasoft.xzzf.power.model.DiscretionModel;
import com.beidasoft.xzzf.power.model.PowerSelectModel;
import com.beidasoft.xzzf.power.service.DiscretionService;
import com.beidasoft.xzzf.power.service.PowerSelectService;
import com.beidasoft.xzzf.punish.common.bean.AffiliatedPerson;
import com.beidasoft.xzzf.punish.common.bean.ConfFlow;
import com.beidasoft.xzzf.punish.common.bean.ConfTache;
import com.beidasoft.xzzf.punish.common.bean.PunishBase;
import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.dao.AffiliatedDao;
import com.beidasoft.xzzf.punish.common.dao.ConfFlowDao;
import com.beidasoft.xzzf.punish.common.dao.ConfTacheDao;
import com.beidasoft.xzzf.punish.common.dao.PunishBaseDao;
import com.beidasoft.xzzf.punish.common.model.ConfFlowModel;
import com.beidasoft.xzzf.punish.common.model.PunishBaseModel;
import com.beidasoft.xzzf.punish.common.model.PunishFLowModel;
import com.beidasoft.xzzf.punish.common.service.PunishBaseService;
import com.beidasoft.xzzf.task.taskAppointed.bean.CaseAppointedInfo;
import com.beidasoft.xzzf.task.taskAppointed.dao.CaseAppointedInfoDao;
import com.beidasoft.xzzf.task.taskAppointed.model.CaseAppointedInfoModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.dao.TeeFlowProcessDao;
import com.tianee.oa.core.workflow.flowrun.bean.FlowRunToken;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunVars;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class MobileService extends TeeBaseService{
	@Autowired
	private TeeFlowRunDao flowRunDao;

	@Autowired
	private TeeFlowProcessDao flowProcessDao;
	
	@Autowired
	private PunishBaseDao punishBaseDao;
	
	@Autowired
	private ConfFlowDao confFlowDao;
	
	@Autowired
	private ConfTacheDao confTacheDao;
	
	@Autowired
	private CaseAppointedInfoDao caseAppiontedInfoDao;
	
	@Autowired
	private PunishBaseService baseService;
	
	@Autowired
	private DiscretionService discretionService;
	
	@Autowired
	private PowerSelectService powerSelectService;
	
	@Autowired
	private ClueReplyService replyService;
	
	@Autowired
	private AffiliatedDao affiliatedDao;
	
	public TeeEasyuiDataGridJson punishDirect(Map queryParams, TeePerson person, TeeDataGridModel dm) {
		TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
		
		//案件号、场所名称、办理环节
		String query = TeeStringUtil.getString(queryParams.get("query"), "");
		//立案开始日期
		String startTime = TeeStringUtil.getString(queryParams.get("startTime"), "");
		//立案结束日期
		String endTime = TeeStringUtil.getString(queryParams.get("endTime"), "");
		//是否结案
		int isClosed = TeeStringUtil.getInteger(queryParams.get("isClosed"), 0);
		
		//查询条件集合
		List param = new ArrayList();
		
		//默认添加是否结案条件
		StringBuffer hql = new StringBuffer(" from PunishBase where isClosed = " + isClosed + " AND status <> '00' ");
		
		hql.append(" AND (majorPersonId = " + person.getUuid() + " or minorPersonId = " + person.getUuid() + ")");
		
		//案件号、对象名称、地址
		if(query != null && !"".equals(query)) {
			hql.append(" AND ( baseCode like '%" + query + "%' ");			
			hql.append(" OR OrganName like '%" + query + "%' ");
			hql.append(" OR psnName like '%" + query + "%' ");
			hql.append(" OR organAddress like '%" + query + "%' )");
		}
		
		//立案开始日期
		if(startTime != null && !"".equals(startTime)) {
			hql.append(" AND filingDate >= to_date('" + startTime + "', 'yyyy-MM-dd') ");
			
		}
		
		//立案结束日期
		if(endTime != null && !"".equals(endTime)) {
			hql.append(" AND filingDate <= to_date('" + endTime + "', 'yyyy-MM-dd') ");
			param.add(endTime);
		}
		
		long total = simpleDaoSupport.count("SELECT COUNT(baseId) " + hql, null) ;
		List<PunishBase> list = simpleDaoSupport.pageFind(hql + " order by baseCode desc ", 
				(dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);
		
		gridJson.setTotal(total);
		gridJson.setRows(list);
		
		return gridJson;
	}
	
	public JSONObject getFlowByRunId(HttpServletRequest request, TeePerson loginPerson)  throws Exception{
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
		TeeFlowRunPrcs flowRunPrcs = null;
		
		if(frpSid!=0){
			flowRunPrcs = (TeeFlowRunPrcs) simpleDaoSupport.get(TeeFlowRunPrcs.class, frpSid);
		}else{
			//获取当前登陆在指定流程上的最后一个办理步骤
			List<TeeFlowRunPrcs> frpList = simpleDaoSupport.find("from TeeFlowRunPrcs where prcsUser.uuid="+loginPerson.getUuid()+" and flowRun.runId="+runId+" order by sid desc", null);
			if(frpList.size()!=0){
				flowRunPrcs = frpList.get(0);
			}else{
				TeeFlowRun flowRun = flowRunDao.get(runId);
				
				//获取流程变量
				List<TeeFlowRunVars> vars = getFlowRunVars(runId);
				
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("runId", flowRun.getRunId());
				if(flowRunPrcs!=null){
					jsonObject.put("frpSid", flowRunPrcs.getSid());
					jsonObject.put("prcsId", flowRunPrcs.getFlowPrcs().getSortNo());
					TeeFlowProcess fp = flowRunPrcs.getFlowPrcs();
					if(fp!=null){
						jsonObject.put("prcsName", fp.getPrcsName());
						jsonObject.put("flowPrcs", fp.getSid());
					}else{
						jsonObject.put("prcsName", "");
						jsonObject.put("flowPrcs", "");
					}
					jsonObject.put("prcsUser", flowRunPrcs.getPrcsUser().getUuid());
					jsonObject.put("flag", flowRunPrcs.getFlag());
				}else{
					jsonObject.put("prcsName", "");
					jsonObject.put("flowPrcs", "");
					jsonObject.put("frpSid", 0);
					jsonObject.put("prcsId", 0);
					jsonObject.put("prcsUser", 0);
					jsonObject.put("flag", 4);
				}
				
				jsonObject.put("flowId", flowRun.getFlowType().getSid());
				Map<String,String> varsMap = new HashMap();
				for(TeeFlowRunVars var:vars){
					varsMap.put(var.getVarKey(), var.getVarValue());
				}
				jsonObject.put("vars", varsMap);
				jsonObject.put("runName", flowRun.getRunName());
				
				return jsonObject;
			}
		}
		if(flowRunPrcs==null){
			throw new TeeOperationException("该工作已删除");
		} else {
			TeeFlowRun flowRun = flowRunPrcs.getFlowRun();
			TeeFlowType ft = flowRun.getFlowType();

			//获取流程变量
			List<TeeFlowRunVars> vars = getFlowRunVars(runId);
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("runId", flowRunPrcs.getFlowRun().getRunId());
			if(flowRunPrcs!=null){
				jsonObject.put("frpSid", flowRunPrcs.getSid());
				jsonObject.put("prcsId", flowRunPrcs.getFlowPrcs().getSortNo());
				TeeFlowProcess fp = flowRunPrcs.getFlowPrcs();
				if(fp!=null){
					jsonObject.put("prcsName", fp.getPrcsName());
					jsonObject.put("flowPrcs", fp.getSid());
				}else{
					jsonObject.put("prcsName", "");
					jsonObject.put("flowPrcs", "");
				}
				jsonObject.put("prcsUser", flowRunPrcs.getPrcsUser().getUuid());
				jsonObject.put("flag", flowRunPrcs.getFlag());
			}else{
				jsonObject.put("prcsName", "");
				jsonObject.put("flowPrcs", "");
				jsonObject.put("frpSid", 0);
				jsonObject.put("prcsId", 0);
				jsonObject.put("prcsUser", 0);
				jsonObject.put("flag", 4);
			}
			
			jsonObject.put("flowId", ft.getSid());
			Map<String,String> varsMap = new HashMap();
			for(TeeFlowRunVars var:vars){
				varsMap.put(var.getVarKey(), var.getVarValue());
			}
			jsonObject.put("vars", varsMap);
			jsonObject.put("runName", flowRunPrcs.getFlowRun().getRunName());
			
			return jsonObject;
		}
	}

	public List<TeeFlowRunVars> getFlowRunVars(int runId) {
		return simpleDaoSupport.find("from TeeFlowRunVars where flowRun.runId="+runId, null);
	}
	
	/**
	 * 案件详情查询
	 * @param person
	 * @param baseId
	 * @param tacheId
	 * @return
	 */
	public TeeJson punishDetails(TeePerson person, String baseId, ConfTache confTache) {
		TeeJson teeJson = new TeeJson();
		
		//案件基础信息
		PunishBase punishBase = punishBaseDao.getPunishBaseById(baseId);
				
		//案件案件环节文书
		List<ConfTache> confTaches = confTacheDao.getConfTacheInfo(confTache);
		
		return teeJson;
	}
	/**
	 * 获取案件环节文书（已办、可办）
	 * @param person
	 * @param baseId
	 * @param tacheId
	 * @return
	 */
	public JSONObject punishTache(TeePerson person, PunishFLowModel punishflowModel, ConfFlowModel confFlowModel) {
		JSONObject tache = new JSONObject();
		
		//可办文书列表
		
		//系统定义环节文书
		List<ConfFlow> confFlows = confFlowDao.getConfFlowInfo(confFlowModel);
		
		return tache;
	}

	
	/**
	 * 获取现场检查案件列表
	 * 
	 * @param person
	 * @param sourceType
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson checkDirect(TeePerson person, String sourceType, TeeDataGridModel dm) {
		TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
		
		int departmentId = person.getDept().getUuid();
		int userId = person.getUuid();
		//默认添加是否结案条件
		StringBuffer hql = new StringBuffer(" from PunishBase where isClosed = 0 AND status = '00' ");
		
		if (sourceType != "") {
			hql.append(" AND sourceType = "+sourceType);
		} else {
			hql.append(" AND sourceType in (20, 30) ");
		}
		hql.append(" AND isUnerror = 0 ");
		
		hql.append(" AND chargeDeptId = " + departmentId + " and chargePsnId = " + userId + "  ");
		hql.append(" AND isApply = 1 AND isRegister = 1");
		
		long total = simpleDaoSupport.count("SELECT COUNT(baseId) " + hql, null) ;
		
		@SuppressWarnings("unchecked")
		List<PunishBase> list = simpleDaoSupport.pageFind(hql + " order by baseCode desc ", 
				(dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);
		
		gridJson.setTotal(total);
		gridJson.setRows(list);
		
		return gridJson;
	}

	/**
	 * 获取指派表列表
	 * 
	 * @param person
	 * @param taskRec
	 * @param dm
	 * @param dealType
	 * @return
	 */
	public TeeEasyuiDataGridJson getCaseAppointList(TeePerson person, int taskRec, TeeDataGridModel dm, int dealType,HttpServletRequest request) {
		TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
		//获取登录人员的权限
		String roleName = person.getUserRole().getRoleName();
		
		String searchVals = TeeStringUtil.getString(request.getParameter("searchVals"), "");
		String searchTimeStart = TeeStringUtil.getString(request.getParameter("searchTimeStart"), "");
		String searchTimeEnd = TeeStringUtil.getString(request.getParameter("searchTimeEnd"), "");
		
		StringBuffer hql = new StringBuffer();
		
		if (taskRec == 20) { //如果是检查 （包括现场检查和双随机）
			hql.append("from CaseAppointedInfo where taskRec in (20, 30) ");
		} else if (taskRec == 10){
			hql.append("from CaseAppointedInfo where taskRec =" + taskRec);
		} else if (taskRec == -1) {
			hql.append("from CaseAppointedInfo where 1 = 1");
		} 
		if (searchVals != "") {
			hql.append(" and taskName like '%"+searchVals+"%' ");
		}
		if (searchTimeStart != "") {
			hql.append(" and disposeTime > '"+searchTimeStart +" 00:00'");
		}
		if (searchTimeEnd != "") {
			hql.append(" and disposeTime < '"+searchTimeEnd+" 23:59'");
		}
		
		if (dealType == -1) { //查询全部
			hql.append("");
		} else if (dealType == 0) { //查询未办理
			hql.append(" and dealType in (0, 9) ");
		} else { //查询已立案 和  不予立案 （10 ， 11）
			hql.append(" and dealType ="+ dealType);
		}
		
		int deptId = person.getDept().getUuid();
		if("系统管理员".equals(roleName)) {  
			
		}
		// 二.部门领导（部门负责人、部门副职）
		if(("部门负责人".equals(roleName) || "部门副职".equals(roleName))) {
			hql.append(" and organizationId = " + deptId);
		} 
		if ("工作人员".equals(roleName)) {
			hql.append(" and organizationId = " + deptId);
		}
		//如果detalType等于-1、0按照送审时间排序。如果为9、 10、 11按照处理时间排序
		if (dealType == -1 || dealType == 0) {
			hql.append(" order by(taskSendTime) desc");
		}
		if (dealType == 9 || dealType == 10 || dealType == 11) {
			hql.append(" order by(disposeTime) desc");
		}		
		
		long total = simpleDaoSupport.count("SELECT COUNT(id) " + hql, null) ;
		
		@SuppressWarnings("unchecked")
		List<CaseAppointedInfo> list = simpleDaoSupport.pageFind(hql + " order by baseCode desc ", 
				(dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);
		
		gridJson.setTotal(total);
		List<CaseAppointedInfoModel> modelList = new ArrayList<CaseAppointedInfoModel>();
		for (CaseAppointedInfo cases : list) {
			CaseAppointedInfoModel model = new CaseAppointedInfoModel();
			BeanUtils.copyProperties(cases, model);
			if (cases.getCreateTime() != null) {
				model.setCreateTimeStr(TeeDateUtil.format(cases.getCreateTime().getTime(), "yyyy年MM月dd日"));
			}
			if (cases.getTaskSendTime() != null) {
				model.setTaskSendTimeStr(TeeDateUtil.format(cases.getTaskSendTime(), "yyyy年MM月dd日"));
			}
			if (cases.getDisposeTime() != null) {
				model.setDisposeTimeStr(TeeDateUtil.format(cases.getDisposeTime().getTime(), "yyyy年MM月dd日"));
			}
			
			modelList.add(model);
		}
		gridJson.setRows(modelList);
		
		return gridJson;
	}
	
	/**
	 * 移动端获取执法人员接受的待处理案件信息
	 * 
	 * @param person
	 * @return
	 */
	public TeeEasyuiDataGridJson getByPerson(TeePerson person) {
		TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
		
		int personId = person.getUuid();
		String hql = " from PunishBase where isClosed = 0 and isApply = 0 and isHearing = 0 and isUnerror = 0 ";
		hql += " and (majorPersonId ="+personId+" or minorPersonId ="+personId+") and receiveDate = null ";
		hql += "order by filingDate desc";

		long total = simpleDaoSupport.count("select count(baseId) " + hql, null);
		datagrid.setTotal(total);
		
		@SuppressWarnings("unchecked")
		List<PunishBase> baselist = simpleDaoSupport.find(hql, null);
		List<PunishBaseModel> baseModelList = new ArrayList<PunishBaseModel>();
		if (baselist.size() > 0) {
			for (PunishBase punishBase : baselist) {
				PunishBaseModel baseModel = new PunishBaseModel();
				BeanUtils.copyProperties(punishBase, baseModel);
				if (punishBase.getFilingDate() != null) {
					baseModel.setFilingDateEndStr(TeeDateUtil.format(punishBase.getFilingDate(), "yyyy年MM月dd日"));
				}
				baseModelList.add(baseModel);
			}
		}
		datagrid.setRows(baseModelList);

		return datagrid;
	}
	
	/**
	 * 获取指派过的文书（可能需要加其他判断，关于是否为案件负责人，因为只有案件负责人才可以指派文书给别人）
	 * 
	 * @param request
	 * @return
	 */
	public TeeJson getAppointPunish(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
		
		//获取当前登录用户
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String baseId = request.getParameter("baseId");
		String tacheId = request.getParameter("tacheId");
		int personId = person.getUuid();
		
		String hql = " from PunishFLow where baseId = '"+baseId+"' and tacheId ='"+tacheId+"' ";
		hql += " and majorId <>"+personId+" and minorId <>"+personId+") ";

		long total = simpleDaoSupport.count("select count(id) " + hql, null);
		datagrid.setTotal(total);

		hql += "order by time desc";
		@SuppressWarnings("unchecked")
		List<PunishFLow> flowlist = simpleDaoSupport.find(hql, null);
		List<PunishFLowModel> flowModelList = new ArrayList<PunishFLowModel>();
		if (flowlist.size() > 0) {
			for (PunishFLow punishFLow : flowlist) {
				PunishFLowModel flowModel = new PunishFLowModel();
				BeanUtils.copyProperties(punishFLow, flowModel);
				
				flowModel.setCreatetimeStr(TeeDateUtil.format(punishFLow.getTime(), "yyyy年MM月dd日"));
				flowModelList.add(flowModel);
			}
		}
		datagrid.setRows(flowModelList);
		json.setRtData(datagrid);
		json.setRtState(true);
		return json;
	}

	/**
	 * 查询或检索法律信息
	 * 
	 * @param nameSerch
	 * @param submitlawLevel
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson getAllLawsOrSearch(String nameSearch, String submitlawLevel, TeeDataGridModel dm) {
		TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
		
		String hql = "from BaseLaw where 1=1 ";
		if (StringUtils.isNotBlank(nameSearch)) {
			hql += " and (name like '%" + nameSearch 
					+ "%' or word like '%" 
					+ nameSearch + "%'  or lawNum like '%" 
					+ nameSearch + "%' or submitlawLevel like '%" 
					+ nameSearch + "%') "; //模糊查询检索名称和发文号字
		}
		if (StringUtils.isNotBlank(submitlawLevel)) {
			hql += "and submitlawLevel = '" + submitlawLevel +"' ";
		}
		
		@SuppressWarnings("unchecked")
		List<BaseLaw> lawList = simpleDaoSupport.pageFind(hql, 
				(dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);
		List<BaseLawModel> lawModelList = new ArrayList<BaseLawModel>();
		if (lawList.size() != 0) {
			for (BaseLaw baseLaw : lawList) {
				BaseLawModel lawmodel = new BaseLawModel();
				BeanUtils.copyProperties(baseLaw, lawmodel);
				if (baseLaw.getAnnulTime() != null) {
					lawmodel.setAnnulTimeStr(TeeDateUtil.format(baseLaw.getAnnulTime(), "yyyy年MM月dd日"));
				}
				if (baseLaw.getImplementation() != null) {
					lawmodel.setImplementationStr(TeeDateUtil.format(baseLaw.getImplementation(), "yyyy年MM月dd日"));
				}
				if (baseLaw.getPromulgation() != null) {
					lawmodel.setPromulgationStr(TeeDateUtil.format(baseLaw.getPromulgation(), "yyyy年MM月dd日"));
				}
				lawModelList.add(lawmodel);
			}
		}
		
		long total = simpleDaoSupport.count("select count(id) " + hql, null);
		datagrid.setTotal(total);
		datagrid.setRows(lawModelList);
		
		return datagrid;
	}
	

	/**
	 * 显示法律信息子表list
	 * 
	 * @param lawId
	 * @param gridModel
	 * @return
	 */
	public TeeEasyuiDataGridJson showLawById(String lawId, TeeDataGridModel dm) {
		TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
		
		String hql = "from BaseLawDetail where 1=1 ";
		
		if (lawId != "") {
			hql += " and lawId = '" + lawId +"'";
		} else {
			return null;
		}
		
		@SuppressWarnings("unchecked")
		List<BaseLawDetail> lawDetailList = simpleDaoSupport.pageFind(hql, 
				(dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);
		
		long total = simpleDaoSupport.count("select count(id) " + hql, null);
		datagrid.setTotal(total);
		datagrid.setRows(lawDetailList);
		
		return datagrid;
	}

	/**
	 * 查询和检索职权信息
	 * 
	 * @param nameSerch
	 * @param poweType
	 * @param gridModel
	 * @return
	 */
	public TeeEasyuiDataGridJson getAllPowersOrSearch(HttpServletRequest request) {
		TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
		PowerSelectModel queryModel = new PowerSelectModel();
		int page = TeeStringUtil.getInteger(request.getParameter("start"), 1);
		int rows = TeeStringUtil.getInteger(request.getParameter("length"), 10);
		queryModel.setName(TeeStringUtil.getString(request.getParameter("nameSearch"), ""));
		
		List<BasePower> listByPage = powerSelectService.listByPage((page - 1) * rows, rows, queryModel);
		long total = powerSelectService.getTotal(queryModel);
		List<PowerSelectModel> modelList = new ArrayList<PowerSelectModel>();
		for (BasePower basePower : listByPage) {
			String str = "";
			PowerSelectModel powermodel = new PowerSelectModel();
			List<BasePowerDetail> details = powerSelectService.getByPowerId(basePower.getId());
			if (details.size() > 0) {
				for (BasePowerDetail basePowerDetail : details) {
					str += basePowerDetail.getName() + ",";
				}
				str = str.substring(0, str.length() - 1);
				BeanUtils.copyProperties(basePower, powermodel);
				powermodel.setDetailName(str);
				modelList.add(powermodel);
			}
		}
		datagrid.setTotal(total);// 赋值
		datagrid.setRows(modelList);
		return datagrid;
	}

	/**
	 * 显示职权信息子表list
	 * 
	 * @param powerId
	 * @param gridModel
	 * @return
	 */
	public TeeEasyuiDataGridJson showPowerDetailById(String powerId, TeeDataGridModel dm) {
		TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
		
		String hql = "from BasePowerGist where 1=1 ";
		
		if (powerId != "") {
			hql += " and powerId = '" + powerId +"'";
		} else {
			return null;
		}
		
		@SuppressWarnings("unchecked")
		List<BasePowerGist> powerGistList = simpleDaoSupport.pageFind(hql, 
				(dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);
		
		long total = simpleDaoSupport.count("select count(id) " + hql, null);
		datagrid.setTotal(total);
		datagrid.setRows(powerGistList);
		
		return datagrid;
	}
	
	/**
	 * 获取自由裁量权列表
	 * 
	 * @param powerId
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson dataGridDiscretion(String powerId, TeeDataGridModel dm) {
		TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
		DiscretionModel queryModel = new DiscretionModel();
		queryModel.setPowerId(TeeStringUtil.getString(powerId, ""));
		List<BaseDiscretion> discretionList = discretionService.listByPage((dm.getPage() - 1) * dm.getRows(), dm.getRows(), queryModel);
		long total = discretionService.getTotal(queryModel);
		datagrid.setTotal(total);
		datagrid.setRows(discretionList);
		return datagrid;
	}
	
	/**
	 * 自由裁量权数据操作（flag=add新建，flag=updata修改，flag=del删除）
	 * 
	 * @param request
	 * @return
	 */
	public TeeJson operatBaseDiscretion(HttpServletRequest request){
		TeeJson json = new TeeJson();
		BaseDiscretion discretion = new BaseDiscretion();
		String flag = TeeStringUtil.getString(request.getParameter("flag"), "");
		String id = TeeStringUtil.getString(request.getParameter("id"), "");
		discretion.setId(TeeStringUtil.getString(request.getParameter("id"), ""));
		discretion.setBreaklow(TeeStringUtil.getString(request.getParameter("breaklow"), ""));
		discretion.setLegalbasis(TeeStringUtil.getString(request.getParameter("legalbasis"), ""));
		discretion.setPowerCode(TeeStringUtil.getString(request.getParameter("powerCode"), ""));
		discretion.setPowerId(TeeStringUtil.getString(request.getParameter("powerId"), ""));
		if ("add".equals(flag)) {
			discretion.setId(UUID.randomUUID().toString());
			discretionService.save(discretion);
			json.setRtData(discretion);
			json.setRtState(true);
		}
		if ("updata".equals(flag)) {
			discretionService.update(discretion);
			json.setRtData(discretion);
			json.setRtState(true);
		}
		if ("del".equals(flag)) {
			discretionService.deleteById(id);
			json.setRtState(true);
		}
		return json;
	}

	/**
	 * 多表查询参与办案
	 * 
	 * @param request
	 * @param person
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson punishWaitDeal(HttpServletRequest request, TeePerson person, TeeDataGridModel dm) {
		TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
		int majorId = person.getUuid();
		
		String hql = " select r.*,f.* from ZF_CONF_TACHE t, ZF_PUNISH_FLOW f, FLOW_RUN r"
				+ " where f.PUNISH_RUN_ID = r.RUN_ID "
				+ " and t.CONF_TACHE_CODE = f.PUNISH_TACHE_ID "
				+ " and t.CONF_TACHE_NAME <> '现场检查' "
				+ " and r.END_TIME is null "
				+ " and f.MAJOR_ID ="+majorId 
				+ " order by f.CREATE_TIME desc";
		
		@SuppressWarnings("unchecked")
		List flowRunList = simpleDaoSupport.executeNativeQuery(hql, null,
				(dm.getPage() - 1) * dm.getRows(), dm.getRows());
		String countHql = " select count(r.RUN_ID) from ZF_CONF_TACHE t, ZF_PUNISH_FLOW f, FLOW_RUN r "
				+ " where f.PUNISH_RUN_ID = r.RUN_ID "
				+ " and t.CONF_TACHE_CODE = f.PUNISH_TACHE_ID "
				+ " and t.CONF_TACHE_NAME <> '现场检查' "
				+ " and r.END_TIME is null "
				+ " and f.MAJOR_ID ="+majorId;
		
		long total = simpleDaoSupport.countSQLByList(countHql, null);
		datagrid.setRows(flowRunList);
		datagrid.setTotal(total);
		return datagrid;
	}
	/**
	 * 参与检查
	 * @param request 
	 * 
	 * @param person
	 * @param gridModel
	 * @return
	 */
	public TeeEasyuiDataGridJson getReceivedCheckWorks(HttpServletRequest request, TeePerson person, TeeDataGridModel dm) {
		TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
		int majorId = person.getUuid();
		
		String hql = " select r.*,f.* from ZF_CONF_TACHE t, ZF_PUNISH_FLOW f, FLOW_RUN r "
				+ " where f.PUNISH_RUN_ID = r.RUN_ID "
				+ " and t.CONF_TACHE_CODE = f.PUNISH_TACHE_ID "
				+ " and t.CONF_TACHE_NAME = '现场检查' "
				+ " and r.END_TIME is null "
				+ " and f.MAJOR_ID ="+majorId 
				+ " order by f.CREATE_TIME desc";
		
		@SuppressWarnings("unchecked")
		List flowRunList = simpleDaoSupport.executeNativeQuery(hql, null,
				(dm.getPage() - 1) * dm.getRows(), dm.getRows());
		String countHql = " select count(r.RUN_ID) from ZF_CONF_TACHE t, ZF_PUNISH_FLOW f, FLOW_RUN r "
				+ " where f.PUNISH_RUN_ID = r.RUN_ID "
				+ " and t.CONF_TACHE_CODE = f.PUNISH_TACHE_ID "
				+ " and t.CONF_TACHE_NAME = '现场检查' "
				+ " and r.END_TIME is null "
				+ " and f.MAJOR_ID ="+majorId;
		
		long total = simpleDaoSupport.countSQLByList(countHql, null);
		datagrid.setRows(flowRunList);
		datagrid.setTotal(total);
		return datagrid;
	}

	/**
	 * 获取已检查案件
	 * 
	 * @param person
	 * @param gridModel
	 * @return
	 */
	public TeeEasyuiDataGridJson finishCheckList(TeePerson person, TeeDataGridModel dm) {
		TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
		int majorId = person.getUuid();
		
		String hql = " from PunishBase where 1 = 1 and (isUnerror <> 0 or isRegister = 0)";
		hql += " and chargePsnId="+majorId + "order by inspectionDate desc";
				
		
		@SuppressWarnings("unchecked")
		List<PunishBase> baseList = simpleDaoSupport.pageFind(hql, 
				(dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);
		
		List<PunishBaseModel> baseModelList = new ArrayList<PunishBaseModel>();
		if (baseList.size() > 0) {
			for (PunishBase punishBase : baseList) {
				PunishBaseModel baseModel = baseService.baseferModel(punishBase);
				
				baseModelList.add(baseModel);
			}
		}
		datagrid.setRows(baseModelList);
		long total = simpleDaoSupport.count("select count(baseId) " + hql, null);
		datagrid.setTotal(total);
		return datagrid;
	}

	/**
	 * 获取听证列表
	 * 
	 * @param baseId
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson isHearingList(TeeDataGridModel dm) {
		TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
		
		String hql = " from PunishBase where 1 = 1 and isClosed = 0 and isHearing = 1 ";
		
		@SuppressWarnings("unchecked")
		List<PunishBase> baseList = simpleDaoSupport.pageFind(hql, 
				(dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);
		
		List<PunishBaseModel> baseModelList = new ArrayList<PunishBaseModel>();
		if (baseList.size() > 0) {
			for (PunishBase punishBase : baseList) {
				PunishBaseModel baseModel = baseService.baseferModel(punishBase);
				
				baseModelList.add(baseModel);
			}
		}
		datagrid.setRows(baseModelList);
		long total = simpleDaoSupport.count("select count(baseId) " + hql, null);
		datagrid.setTotal(total);
		return datagrid;
	}

	/**
	 * 执法人确认接收任务
	 * 
	 * @param baseId
	 * @return 
	 */
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
	 * 获取待最终回复案件列表
	 * 
	 * @param person
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson getUnReplyPunish(TeePerson person, TeeDataGridModel dm) {
		TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
		
		String hql = " from PunishBase where 1 = 1 and isApply = 0 and isRegister = 0 and sourceType = '10' "
				+ " and isFinalReply = 0 and (majorPersonId ="+person.getUuid()+" or minorPersonId ="+person.getUuid()+" )";
		
		@SuppressWarnings("unchecked")
		List<PunishBase> baseList = simpleDaoSupport.pageFind(hql, 
				(dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);
		
		List<PunishBaseModel> baseModelList = new ArrayList<PunishBaseModel>();
		if (baseList.size() > 0) {
			for (PunishBase punishBase : baseList) {
				PunishBaseModel baseModel = baseService.baseferModel(punishBase);
				List<ClueReply> clueReplies = replyService.findClueReply(baseModel.getSourceId());
				if (clueReplies.size() != 0) {
					baseModel.setFirstReply(clueReplies.get(0).getReplyContent());
					baseModel.setReplyCount(clueReplies.size()+"");
				} else {
					baseModel.setFirstReply("");
					baseModel.setReplyCount("0");
				}
				baseModelList.add(baseModel);
			}
		}
		
		datagrid.setRows(baseModelList);
		long total = baseModelList.size();
		datagrid.setTotal(total);
		return datagrid;
	}
	
	/**
	 * 获取已最终回复案件列表
	 * 
	 * @param person
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson getReplyPunish(TeePerson person, TeeDataGridModel dm) {
		TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
		
		String hql = " from PunishBase where 1 = 1 and isApply = 0 and isRegister = 0 and sourceType = '10' "
				+ " and isFinalReply = 1";
		
		@SuppressWarnings("unchecked")
		List<PunishBase> baseList = simpleDaoSupport.pageFind(hql, 
				(dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);
		
		List<PunishBaseModel> baseModelList = new ArrayList<PunishBaseModel>();
		if (baseList.size() > 0) {
			for (PunishBase punishBase : baseList) {
				PunishBaseModel baseModel = baseService.baseferModel(punishBase);
				List<ClueReply> clueReplies = replyService.findClueReply(baseModel.getSourceId());
				if (clueReplies.size() != 0) {
					baseModel.setFirstReply(clueReplies.get(0).getReplyContent());
					baseModel.setReplyCount(clueReplies.size()+"");
				}
				baseModelList.add(baseModel);
			}
		}
		datagrid.setRows(baseModelList);
		long total = baseModelList.size();
		datagrid.setTotal(total);
		return datagrid;
	}

	/**
	 * 保存参与人
	 * 
	 * @param request
	 * @return
	 */
	public TeeJson savePersonList(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		
		String psnArrStr = TeeStringUtil.getString(request.getParameter("psnArrStr"), "");
		String baseId = TeeStringUtil.getString(request.getParameter("baseId"), "");
		String lawLinkId = TeeStringUtil.getString(request.getParameter("lawLinkId"), "");
		
		if (!"".equals(psnArrStr) && !"".equals(baseId) && !"".equals(lawLinkId)) {
			JSONArray psnArr = JSONArray.fromObject(psnArrStr);
			if (psnArr.size() > 1) {
				// 保存前删除所有与案件有关的参与人信息
				affiliatedDao.deletePersonByBaseId(baseId);
				for (int i = 0; i < psnArr.size(); i++) {
					JSONObject object = psnArr.getJSONObject(i);

					AffiliatedPerson person = new AffiliatedPerson();

					person.setId(UUID.randomUUID().toString());
					person.setBaseId(baseId);
					person.setDepartmentId(object.getInt("deptId"));
					person.setDepartmentName(object.getString("deptName"));
					person.setPersonId(object.getInt("personId"));
					person.setPersonName(object.getString("personName"));
					person.setPersonNo(object.getString("personNo"));
					person.setDelFlg("0");

					// 重新保存参与人信息
					affiliatedDao.save(person);
				}
			}
			json.setRtState(true);
		} else {
			json.setRtState(false);
		}
		
		return json;
	}

}
