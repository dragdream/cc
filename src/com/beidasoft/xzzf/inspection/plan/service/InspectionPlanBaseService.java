package com.beidasoft.xzzf.inspection.plan.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.inspection.plan.bean.InspectionPlanBase;
import com.beidasoft.xzzf.inspection.plan.dao.InspectionPlanBaseDao;
import com.beidasoft.xzzf.inspection.plan.model.InspectionPlanBaseModel;
import com.beidasoft.xzzf.punish.common.bean.PunishCalendar;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webservice.model.Person;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class InspectionPlanBaseService extends TeeBaseService {
	@Autowired
	private InspectionPlanBaseDao inspectionPlanBaseDao;
	@Autowired
	private TeePersonDao personDao;
	@Autowired
	private InspectionDeptService inspectionDeptService;
	@Autowired
	private TeeAttachmentService attachService;
	@Autowired
	private TeeDeptService deptService;
	@Autowired
	private InspectionTaskService inspectionTaskService;
	@Autowired
	private TeeSmsManager smsManager;
	/**
	 * 修改
	 * @param model
	 * @return
	 */
	public TeeJson updateInspectionPlanBase(InspectionPlanBaseModel model){
		TeeJson json = new TeeJson();
		InspectionPlanBase inspectionPlanBase = inspectionPlanBaseDao.loadById(model.getId());
		//计划开始日期
		if(!TeeUtility.isNullorEmpty(model.getPlanStartDateStr())){
			inspectionPlanBase.setPlanStartDate(TeeDateUtil.format(model.getPlanStartDateStr(), "yyyy-MM-dd"));
		}
		//计划结束日期
		if(!TeeUtility.isNullorEmpty(model.getPlanClosedDateStr())){
			inspectionPlanBase.setPlanClosedDate(TeeDateUtil.format(model.getPlanClosedDateStr(), "yyyy-MM-dd"));
		}
		//更新日期
		inspectionPlanBase.setUpdateTime(new Date());
		inspectionPlanBaseDao.update(inspectionPlanBase);
		json.setRtState(true);
		return json;
	}
	
	
	
	/**
	 * 分页查询
	 * @param dm
	 * @param request
	 * @param baseId
	 * @return
	 */
	public TeeEasyuiDataGridJson getInspectionPlanBaseOfPage(TeeDataGridModel dm, HttpServletRequest request) {
		TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
		//查询条件list
		List param = new ArrayList();
		//基础hql
		String hql = " from InspectionPlanBase  where 1=1 and isDeleted=0 ";
		//title查询条件
		String title = request.getParameter("title");
		if(!TeeUtility.isNullorEmpty(title)){
			String conditionTitle = TeeDbUtility.formatString(title);
			hql += " and title like '%"+conditionTitle+"%' ";
		}
		//status查询条件
		String status = request.getParameter("status");
		if(!TeeUtility.isNullorEmpty(status)){
			hql += " and status="+Integer.parseInt(status);
		}
		long total = simpleDaoSupport.count("select count(id) " + hql, param.toArray());
		datagrid.setTotal(total);
		List<InspectionPlanBase> list = simpleDaoSupport.pageFind(hql + " order by createTime DESC ", (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param.toArray());
		
		List<InspectionPlanBaseModel> models = new ArrayList<InspectionPlanBaseModel>();
		InspectionPlanBaseModel model = null;
		for(InspectionPlanBase row : list) {
			model = new InspectionPlanBaseModel();
			model = transferModel(row);
			models.add(model);
		}
		datagrid.setRows(models);
		
		return datagrid;
	}
	
	/**
	 * 将bean转换为model
	 * @param InspectionPlanBase
	 * @return
	 */
	private InspectionPlanBaseModel transferModel(InspectionPlanBase inspectionPlanBase) {
		InspectionPlanBaseModel inspectionPlanBaseModel = new InspectionPlanBaseModel();
		BeanUtils.copyProperties(inspectionPlanBase, inspectionPlanBaseModel);
		
		//转换计划开始时间
		if(inspectionPlanBase.getPlanStartDate() != null) {
			inspectionPlanBaseModel.setPlanStartDateStr(TeeUtility.getDateYMDStr(inspectionPlanBase.getPlanStartDate()));
		}

		//转换计划结束时间
		if(inspectionPlanBase.getPlanClosedDate() != null) {
			inspectionPlanBaseModel.setPlanClosedDateStr(TeeUtility.getDateYMDStr(inspectionPlanBase.getPlanClosedDate()));
		}

		//转换创建日期
		inspectionPlanBaseModel.setCreateTimeStr(TeeUtility.getDateTimeStr(inspectionPlanBase.getCreateTime()));
		
		//转换任务编码
		inspectionPlanBaseModel.setPlanNum(TeeDateUtil.format(inspectionPlanBase.getCreateTime(), "yyyyMMddHHmmss"));

		//转换修改日期
		if(inspectionPlanBase.getUpdateTime() != null) {
			inspectionPlanBaseModel.setUpdateTimeStr(TeeUtility.getDateTimeStr(inspectionPlanBase.getUpdateTime()));
		}
		
		return inspectionPlanBaseModel;
	}
	
	/**
	 * 保存
	 */
	public TeeJson save(InspectionPlanBaseModel inspectionPlanBaseModel, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		InspectionPlanBase inspectionPlanBase = new InspectionPlanBase();
		BeanUtils.copyProperties(inspectionPlanBaseModel, inspectionPlanBase);
		inspectionPlanBase.setId(UUID.randomUUID().toString());
		inspectionPlanBase.setCreateTime(new Date());
		
		//转换计划开始时间
		if(inspectionPlanBaseModel.getPlanStartDateStr() != null) {
			inspectionPlanBase.setPlanStartDate(TeeDateUtil.format(inspectionPlanBaseModel.getPlanStartDateStr(),"yyyy-MM-dd"));
		}

		//转换计划结束时间
		if(inspectionPlanBaseModel.getPlanClosedDateStr() != null) {
			inspectionPlanBase.setPlanClosedDate(TeeDateUtil.format(inspectionPlanBaseModel.getPlanClosedDateStr(),"yyyy-MM-dd"));
		}
		//创建人
		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		inspectionPlanBase.setCreatPerson(user.getUserName());
		//删除状态默认为0
		inspectionPlanBase.setIsDeleted(0);
		//保存执行计划主表
		inspectionPlanBaseDao.saveInspectionPlanBase(inspectionPlanBase);
		
		//保存执行计划部门表
		inspectionDeptService.saveDept(inspectionPlanBase.getExecuteDepartment(), inspectionPlanBase.getId());
		
		//附件处理
		List<TeeAttachment> attachList = attachService.getAttachmentsByIds(inspectionPlanBaseModel.getInspectionAttachment());
		for (TeeAttachment attach : attachList) {
			attach.setModelId(inspectionPlanBase.getId());
			attachService.updateAttachment(attach);
		}
		
		json.setRtData(inspectionPlanBase.getId());
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 根据id获取
	 */
	public TeeJson get(String id) {
		TeeJson json = new TeeJson();
		InspectionPlanBase inspectionPlanBase = inspectionPlanBaseDao.loadById(id);
		InspectionPlanBaseModel model = new InspectionPlanBaseModel();
		BeanUtils.copyProperties(inspectionPlanBase, model);
		model.setPlanStartDateStr(TeeDateUtil.format(inspectionPlanBase.getPlanStartDate(),"yyyy-MM-dd"));
		model.setPlanClosedDateStr(TeeDateUtil.format(inspectionPlanBase.getPlanClosedDate(),"yyyy-MM-dd"));
		String deptIds[] = inspectionPlanBase.getExecuteDepartment().split(",");
		String deptNames = "";
		for (int i = 0; i < deptIds.length; i++) {
			deptNames += deptService.get(Integer.parseInt(deptIds[i])).getDeptName();
			if(i < deptIds.length - 1){
				deptNames += ",";
			}
		}
		model.setExecuteDepartmentName(deptNames);
		//附件
		List<TeeAttachmentModel> attachModels = attachService.getAttacheModels("workFlow", inspectionPlanBase.getId());
		model.setAttachModels(attachModels);
		String attachNames = "";
		for (int i = 0; i < attachModels.size(); i++) {
			attachNames += attachModels.get(i).getSid();
			if(i < attachModels.size()-1){
				attachNames += ",";
			}
		}
		model.setInspectionAttachment(attachNames);
		json.setRtData(model);
		return json;
	}
	
	
	/**
	 * 修改状态
	 * @param ids
	 * @param num
	 * @param param
	 * @return TeeJson
	 */
	public TeeJson updateStatus(String ids, int num, String param){
		TeeJson json = new TeeJson();
		int count = inspectionPlanBaseDao.updateDelByUuids(ids, num, param);
		//如果启用则保存所有任务到案件主表并给队长发送站内信
		if (num==1&&"status".equals(param)) {
			inspectionTaskService.insertPunishBase(ids);
			sendSmsManager(ids);
		}
		json.setRtData(count);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 根据标题模糊查询
	 * @param title
	 * @return TeeJson
	 */
	public TeeJson listByTitle(String title){
		TeeJson json = new TeeJson();
		List<InspectionPlanBase> list = inspectionPlanBaseDao.listByTitle(title);
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("id", list.get(i).getId());
			jsonObj.put("text", list.get(i).getTitle());
			jsonArray.add(jsonObj);
		}
		
		json.setRtData(jsonArray);
		json.setRtState(true);
		return json;
	}
	
	//发送站内信
	private void sendSmsManager(String inspectionId){  
		String depts = inspectionPlanBaseDao.loadById(inspectionId).getExecuteDepartment();
		List<TeePerson> people = personDao.find("from TeePerson where dept.uuid in ("+depts+") and userRole.uuid=12", null);
		for (int i = 0; i < people.size(); i++) {
			Map requestData = new HashMap();
			requestData.put("content",people.get(i).getDept().getDeptName()+"，已分配双随机检查任务，请知晓。");
			requestData.put("userListIds",people.get(i).getUuid());
			//requestData.put("moduleNo","104");   //工作流
			requestData.put("remindUrl","/platform/inspection/plan/showDeptPlans.jsp?inspectionId="+inspectionId+"&deptUuid="+people.get(i).getDept().getUuid());
			smsManager.sendSms(requestData, null);
		}
		/*for (int i = 0; i < depts.length; i++) {
			Map requestData = new HashMap();
			requestData.put("content",deptService.get(Integer.parseInt(depts[i])).getDeptName()+"，已分配双随机检查任务，请知晓。");
			//requestData.put("userListIds",pCalendar.getUser().getUuid());
			requestData.put("moduleNo","104");
			//requestData.put("remindUrl",pCalendar.getRemindUrl());
			Calendar cl= Calendar.getInstance();
			smsManager.sendSms(requestData, null);
		}*/
		
	}
}
