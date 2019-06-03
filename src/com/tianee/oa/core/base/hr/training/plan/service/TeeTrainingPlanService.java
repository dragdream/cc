package com.tianee.oa.core.base.hr.training.plan.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.base.hr.TeeHrCodeManager;
import com.tianee.oa.core.base.hr.training.plan.bean.TeeTrainingPlan;
import com.tianee.oa.core.base.hr.training.plan.dao.TeeTrainingPlanDao;
import com.tianee.oa.core.base.hr.training.plan.model.TeeTrainingPlanModel;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeTrainingPlanService extends TeeBaseService {
	@Autowired
	private TeeTrainingPlanDao trainingPlanDao;
	@Autowired
	private TeeDeptDao deptDao;
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeAttachmentService attachmentService;
	
	@Autowired
	private TeeSmsManager smsManager;

	public TeeJson addOrUpdateService(HttpServletRequest request, TeeTrainingPlanModel model) {

		TeeJson json = new TeeJson();
		List<TeeAttachment> listAttachments  = new ArrayList<TeeAttachment>();
		if(!TeeUtility.isNullorEmpty(model.getAttacheIds())){
			listAttachments = attachmentService.getAttachmentsByIds(model.getAttacheIds());
		}
		try {
			TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			if (model.getSid() > 0) {
				TeeTrainingPlan obj = trainingPlanDao.get(model.getSid());
				if (obj != null) {
					int oldPersonId = obj.getApprovePerson().getUuid();
					if (TeeUtility.isDayTime(model.getCourseStartTimeStr())) {
						obj.setCourseStartTime(TeeUtility.parseDate(model.getCourseStartTimeStr()));
					}
					if (TeeUtility.isDayTime(model.getCourseEndTimeStr())) {
						obj.setCourseEndTime(TeeUtility.parseDate(model.getCourseEndTimeStr()));
					}
					TeePerson approvePerson = personDao.get(model.getApprovePersonId());
					if (approvePerson != null) {
						obj.setApprovePerson(approvePerson);
					}
					obj.setJoinDept(model.getJoinDeptIdStr());
					obj.setJoinPerson(model.getJoinPersonIdStr());
					TeeDepartment department = deptDao.get(model.getHostDepartmentsId());
					if (department != null) {
						obj.setHostDepartments(department);
					}
					TeePerson chargePerson = personDao.get(model.getChargePersonId());
					if (chargePerson != null) {
						obj.setChargePerson(chargePerson);
					}
					for (int i = 0; i < listAttachments.size(); i++) {
						TeeAttachment atta = listAttachments.get(i);
						atta.setModelId(obj.getSid()+"");
						simpleDaoSupport.update(atta);
					}
					
					
					obj.setPlanNo(model.getPlanNo());
					obj.setPlanName(model.getPlanName());
					obj.setPlanChannel(model.getPlanChannel());
					obj.setCourseTypes(model.getCourseTypes());
					obj.setJoinNum(model.getJoinNum());
					obj.setAddress(model.getAddress());
					obj.setInstitutionName(model.getInstitutionName());
					obj.setInstitutionContact(model.getInstitutionContact());
					obj.setCourseName(model.getCourseName());
					obj.setCourseHours(model.getCourseHours());
					if(TeeUtility.isDayTime(model.getCourseStartTimeStr())){
						obj.setCourseStartTime(TeeUtility.parseDate(model.getCourseStartTimeStr()));
					}
					if(TeeUtility.isDayTime(model.getCourseEndTimeStr())){
						obj.setCourseEndTime(TeeUtility.parseDate(model.getCourseEndTimeStr()));
					}
					obj.setPlanCost(model.getPlanCost());
					obj.setJoinDept(model.getJoinDeptIdStr());
					obj.setInstitutionInfo(model.getInstitutionInfo());
					obj.setInstituContactInfo(model.getInstituContactInfo());
					obj.setPlanRequires(model.getPlanRequires());
					obj.setDescription(model.getDescription());
					obj.setRemark(model.getRemark());
					obj.setContent(model.getContent());
					obj.setPlanStatus(0);
					
					trainingPlanDao.update(obj);
					if(model.getApprovePersonId() != oldPersonId){
						Map requestData = new HashMap();
						requestData.put("content", "[" + person.getUserName() + "]" + " 更新了培训计划：" + model.getPlanName() +"，请审批。");
						requestData.put("moduleNo","053");
						requestData.put("userListIds",model.getApprovePersonId());
						requestData.put("remindUrl","/system/core/base/hr/training/approval/index.jsp");
						smsManager.sendSms(requestData, person);
					}
				}
			} else {
				TeeTrainingPlan obj = new TeeTrainingPlan();
				BeanUtils.copyProperties(model, obj);
				if (TeeUtility.isDayTime(model.getCourseStartTimeStr())) {
					obj.setCourseStartTime(TeeUtility.parseDate(model.getCourseStartTimeStr()));
				}
				if (TeeUtility.isDayTime(model.getCourseEndTimeStr())) {
					obj.setCourseEndTime(TeeUtility.parseDate(model.getCourseEndTimeStr()));
				}
				if (model.getApprovePersonId() != 0) {
					TeePerson approvePerson = personDao.get(model.getApprovePersonId());
					obj.setApprovePerson(approvePerson);
				}
				obj.setJoinDept(model.getJoinDeptIdStr());
				obj.setJoinPerson(model.getJoinPersonIdStr());
				if (model.getHostDepartmentsId() != 0) {
					TeeDepartment department = deptDao.get(model.getHostDepartmentsId());
					obj.setHostDepartments(department);
				}
				if (model.getChargePersonId() != 0) {
					TeePerson chargePerson = personDao.get(model.getChargePersonId());
					obj.setChargePerson(chargePerson);
				}
				obj.setPlanStatus(0);
				obj.setCreateUser(person);
				obj.setCreateTime(new Date());
				obj.setContent(model.getContent());
				trainingPlanDao.save(obj);
				
				if(model.getApprovePersonId() !=0){
					Map requestData = new HashMap();
					requestData.put("content", "[" + person.getUserName() + "]" + " 新建了培训划：" + model.getPlanName() +"，请审批。");
					requestData.put("moduleNo","053");
					requestData.put("userListIds",model.getApprovePersonId());
					requestData.put("remindUrl","/system/core/base/hr/training/approval/index.jsp");
					smsManager.sendSms(requestData, person);
				}
				for (int i = 0; i < listAttachments.size(); i++) {
					TeeAttachment atta = listAttachments.get(i);
					atta.setModelId(obj.getSid()+"");
					simpleDaoSupport.update(atta);
				}
			}

			json.setRtState(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return json;
	}

	/**
	 * 通用列表
	 * 
	 * @param dm
	 * @return
	 * @throws ParseException
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getRecruitList(TeeDataGridModel dm, HttpServletRequest request, TeeTrainingPlanModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

		Map requestDatas = TeeServletUtility.getParamMap(request);
		String planNo = (String) requestDatas.get("planNo");
		String planName = (String) requestDatas.get("planName");
		String planStatus = (String) requestDatas.get("planStatus");
		String approvePersonId = (String) requestDatas.get("approvePersonId");
		String description = (String) requestDatas.get("description");
		String remark = (String) requestDatas.get("remark");

		String startDateStrMin = (String) requestDatas.get("startDateStrMin");
		String startDateStrMax = (String) requestDatas.get("startDateStrMax");
		String endDateStrMin = (String) requestDatas.get("endDateStrMin");
		String endDateStrMax = (String) requestDatas.get("endDateStrMax");

		String queryStr = " 1=1";
		if (!TeePersonService.checkIsSuperAdmin(loginPerson, "")) {
			queryStr = " require.createUser.uuid= " + loginPerson.getUuid();
		}
		String hql = "from TeeTrainingPlan require where " + queryStr;
		List param = new ArrayList();
		if (!TeeUtility.isNullorEmpty(planNo)) {
			hql += " and require.planNo like ?";
			param.add("%" + planNo + "%");
		}
		if (!TeeUtility.isNullorEmpty(planName)) {
			hql += " and require.planName like ?";
			param.add("%" + planName + "%");
		}
		if (TeeUtility.isInteger(planStatus)) {
			hql += " and require.planStatus=?";
			param.add(Integer.parseInt(planStatus));
		}

		if (TeeUtility.isInteger(approvePersonId)) {
			hql += " and require.approvePerson.uuid=?";
			param.add(Integer.parseInt(approvePersonId));
		}
		if (!TeeUtility.isNullorEmpty(description)) {
			hql += " and require.description like ?";
			param.add("%" + description + "%");
		}
		if (!TeeUtility.isNullorEmpty(remark)) {
			hql += " and require.remark like ?";
			param.add("%" + remark + "%");
		}

		if (!TeeUtility.isNullorEmpty(startDateStrMin)) {
			hql += " and require.courseStartTime >= ?";
			param.add(TeeDateUtil.parseDate("yyyy-MM-dd HH:mm:ss", startDateStrMin + " 00:00:00"));
		}
		if (!TeeUtility.isNullorEmpty(startDateStrMax)) {
			hql += " and require.courseStartTime <= ?";
			param.add(TeeDateUtil.parseDate("yyyy-MM-dd HH:mm:ss", startDateStrMax + " 23:59:59"));
		}
		if (!TeeUtility.isNullorEmpty(endDateStrMin)) {
			hql += " and require.courseEndTime >= ?";
			param.add(TeeDateUtil.parseDate("yyyy-MM-dd HH:mm:ss", endDateStrMin + " 00:00:00"));
		}
		if (!TeeUtility.isNullorEmpty(endDateStrMax)) {
			hql += " and require.courseEndTime <= ?";
			param.add(TeeDateUtil.parseDate("yyyy-MM-dd HH:mm:ss", endDateStrMax + " 23:59:59"));
		}

		// j.setTotal(trainingPlanDao.getQueryCount(loginPerson ,model));//
		// 设置总记录数
		j.setTotal(trainingPlanDao.countByList("select count(*) " + hql, param));// 设置总记录数

		hql += " order by require.createTime desc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		// List<TeeTrainingPlan> list =
		// trainingPlanDao.getMeetPageFind(firstIndex, dm.getRows(), dm,
		// model);// 查
		List<TeeTrainingPlan> list = trainingPlanDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeTrainingPlanModel> modelList = new ArrayList<TeeTrainingPlanModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeTrainingPlanModel modeltemp = parseModel(list.get(i) , true);
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	/**
	 * 封装对象
	 * 
	 * @date 2014-3-17
	 * @author
	 * @param obj
	 * @return
	 */
	public TeeTrainingPlanModel parseModel(TeeTrainingPlan obj , boolean isSimple) {
		TeeTrainingPlanModel model = new TeeTrainingPlanModel();
		if (obj == null) {
			return model;
		}
		BeanUtils.copyProperties(obj, model);
		if (!TeeUtility.isNullorEmpty(obj.getCreateTime())) {
			model.setCreateTimeStr(TeeUtility.getDateStrByFormat(obj.getCreateTime(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
		} else {
			model.setCreateTimeStr("");
		}
		if (!TeeUtility.isNullorEmpty(obj.getCourseStartTime())) {
			model.setCourseStartTimeStr(TeeUtility.getDateStrByFormat(obj.getCourseStartTime(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
		} else {
			model.setCourseStartTimeStr("");
		}
		if (!TeeUtility.isNullorEmpty(obj.getCourseEndTime())) {
			model.setCourseEndTimeStr(TeeUtility.getDateStrByFormat(obj.getCourseEndTime(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
		} else {
			model.setCourseEndTimeStr("");
		}
		if (!TeeUtility.isNullorEmpty(obj.getApproveTime())) {
			model.setApproveTimeStr(TeeUtility.getDateStrByFormat(obj.getApproveTime(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
		} else {
			model.setApproveTimeStr("");
		}
		if (obj.getApprovePerson() != null) {
			model.setApprovePersonId(obj.getApprovePerson().getUuid());
			model.setApprovePersonName(obj.getApprovePerson().getUserName());
		} else {
			model.setApprovePersonName("");
		}

	/*	model.setJoinDept(model.getJoinDeptIdStr());
		model.setJoinPerson(model.getJoinPersonIdStr());*/
		TeeDepartment department = obj.getHostDepartments();
		if (department != null) {
			model.setHostDepartmentsId(department.getUuid());
			model.setHostDepartmentsName(department.getDeptName());
		} else {
			model.setHostDepartmentsName("");
		}

		if (obj.getChargePerson() != null) {
			model.setChargePersonId(obj.getChargePerson().getUuid());
			model.setChargePersonName(obj.getChargePerson().getUserName());
		} else {
			model.setChargePersonName("");
		}

		if (TeeUtility.isNullorEmpty(obj.getApproveComment())) {
			model.setApproveComment("");
		}
		// 附件
        List<TeeAttachmentModel> attacheModels = new ArrayList<TeeAttachmentModel>();
        String attacheIds = "";
        String joinDeptIds = "";
        String joinDeptNames = "";
        String joinUserIds= "";
        String joinUserNames = "";
        if(!isSimple){
            List<TeeAttachment> attaches = attachmentService.getAttaches(TeeAttachmentModelKeys.TRAINING_PLAN, obj.getSid()+"");
        	for(TeeAttachment attach:attaches){
    			TeeAttachmentModel m = new TeeAttachmentModel();
    			BeanUtils.copyProperties(attach, m);
    			m.setUserId(attach.getUser().getUuid()+"");
    			m.setUserName(attach.getUser().getUserName());
    			m.setPriv(1+2+4);//一共五个权限好像     1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
    			attacheModels.add(m);
    			attacheIds = attacheIds + attach.getSid() + ",";
    		}
            if(attacheIds.endsWith(",")){
            	attacheIds = attacheIds.substring(0, attacheIds.length() - 1);
            }
            
            //参与部门
           if(!TeeUtility.isNullorEmpty(obj.getJoinDept())){
        	   List<TeeDepartment> deptList = deptDao.getDeptListByUuids(obj.getJoinDept());
               for (int i = 0; i < deptList.size(); i++) {
            	   joinDeptIds = joinDeptIds + deptList.get(i).getUuid() + ",";
            	   joinDeptNames = joinDeptNames + deptList.get(i).getDeptName() + ",";
               }
           }
          

           //参与人员
          if(!TeeUtility.isNullorEmpty(obj.getJoinPerson())){
        	  List<TeePerson> personList = personDao.getPersonByUuids(obj.getJoinPerson());
              for (int i = 0; i < personList.size(); i++) {
            	  joinUserIds = joinUserIds + personList.get(i).getUuid() + ",";
            	  joinUserNames = joinUserNames + personList.get(i).getUserName() + ",";
              } 
          }
          
        }
        
        //HR代码
		 if(!TeeUtility.isNullorEmpty(obj.getPlanChannel())){//培训渠道
			 model.setPlanChannel(obj.getPlanChannel());
			 model.setPlanChannelName(TeeHrCodeManager.getChildSysCodeNameCodeNo("TRAIN_PLAN_CHANNEL", obj.getPlanChannel()));
		 }
		 if(!TeeUtility.isNullorEmpty(obj.getCourseTypes())){//培训渠道
			 model.setCourseTypes(obj.getCourseTypes());
			 model.setCourseTypesName(TeeHrCodeManager.getChildSysCodeNameCodeNo("TRAIN_COURSE_TYPES", obj.getCourseTypes()));
		 }
        
        
        model.setAttacheModels(attacheModels);
        model.setAttacheIds("");
        model.setJoinDeptIdStr(joinDeptIds);
        model.setJoinDeptNameStr(joinDeptNames);
        model.setJoinPersonIdStr(joinUserIds);
        model.setJoinPersonNameStr(joinUserNames);
		return model;
	}

	/**
	 * 根据sid查看详情
	 * 
	 * @date 2014-3-8
	 * @author
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getInfoByIdService(HttpServletRequest request, TeeTrainingPlanModel model) {
		TeeJson json = new TeeJson();
		if (model.getSid() > 0) {
			TeeTrainingPlan out = trainingPlanDao.get(model.getSid());
			if (out != null) {
				model = parseModel(out , false);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		return json;
	}

	/**
	 * 删除信息
	 * 
	 * @date 2014年5月27日
	 * @author
	 * @param sids
	 * @return
	 */
	public TeeJson deleteObjByIdService(String sids) {
		TeeJson json = new TeeJson();

		trainingPlanDao.delByIds(sids);
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}

	/**
	 * 获取各种状态总数
	 * 
	 * @date 2014年6月7日
	 * @author
	 * @param person
	 * @param model
	 * @return
	 */
	public TeeJson getStatusCountService(TeePerson person, TeeTrainingPlanModel model) {
		TeeJson json = new TeeJson();
		// 待审批
		model.setPlanStatus(0);
		long applyCount = trainingPlanDao.getStatusCountDao(person, model);
		// 已批准
		model.setPlanStatus(1);
		long approveCount = trainingPlanDao.getStatusCountDao(person, model);

		// 未批准
		model.setPlanStatus(2);
		long noApproveCount = trainingPlanDao.getStatusCountDao(person, model);

		Map map = new HashMap();
		map.put("vehicleCount0", applyCount);
		map.put("vehicleCount1", approveCount);
		map.put("vehicleCount2", noApproveCount);
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}

	/**
	 * 通用列表
	 * 
	 * @param dm
	 * @return
	 * @throws ParseException
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getPlanApprovalListService(TeeDataGridModel dm, HttpServletRequest request, TeeTrainingPlanModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

		Map requestDatas = TeeServletUtility.getParamMap(request);
		int planStatus = TeeStringUtil.getInteger(requestDatas.get("planStatus") , 0);

		String hql = "from TeeTrainingPlan require where planStatus = ? ";
		List param = new ArrayList();
		param.add(planStatus);
		if(!TeePersonService.checkIsSuperAdmin(loginPerson, "")){
			hql = hql + " and approvePerson.uuid = ? ";
			param.add(loginPerson.getUuid());
		}
		
		
		// 设置总记录数
		j.setTotal(trainingPlanDao.countByList("select count(*) " + hql, param));// 设置总记录数

		hql += " order by require.createTime desc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeTrainingPlan> list = trainingPlanDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeTrainingPlanModel> modelList = new ArrayList<TeeTrainingPlanModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeTrainingPlanModel modeltemp = parseModel(list.get(i) , true);
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	/**
	 * 设置状态
	 * 
	 * @date 2014年6月8日
	 * @author
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	public TeeJson setPlanStatusService(HttpServletRequest request, TeeTrainingPlanModel model) throws ParseException {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		if (model.getSid() > 0) {
			TeeTrainingPlan obj = trainingPlanDao.get(model.getSid());
			if (obj != null) {
				obj.setPlanStatus(model.getPlanStatus());
				obj.setApproveTime(new Date());
				obj.setApprovePerson(person);
				obj.setApproveComment(model.getApproveComment());
				trainingPlanDao.update(obj);
				if(model.getPlanStatus() == 1){
					Map requestData = new HashMap();
					requestData.put("content", "[" + person.getUserName() + "]" + " 批准了你的培训计划：" + obj.getPlanName());
					requestData.put("moduleNo","053");
					requestData.put("userListIds",obj.getCreateUser().getUuid());
					requestData.put("remindUrl","/system/core/base/hr/training/approval/index.jsp");
					smsManager.sendSms(requestData, person);
				}else if (model.getPlanStatus() == 2) {
					Map requestData = new HashMap();
					requestData.put("content", "[" + person.getUserName() + "]" + " 拒绝了你的培训计划：" + obj.getPlanName());
					requestData.put("moduleNo","053");
					requestData.put("userListIds",obj.getCreateUser().getUuid());
					requestData.put("remindUrl","/system/core/base/hr/training/approval/index.jsp");
					smsManager.sendSms(requestData, person);
				}
			}
		}
		json.setRtState(true);
		return json;
	}

	
	/**
	 * 获取所有的记录
	 * @author syl
	 * @date 2014-6-21
	 * @return
	 */
	public TeeJson getAllPlan(){
		TeeJson json = new TeeJson();
		List<TeeTrainingPlan> list =  trainingPlanDao.getAllPlan();
		List<TeeTrainingPlanModel> modelList = new ArrayList<TeeTrainingPlanModel>(); 
		for (int i = 0; i < list.size(); i++) {
			TeeTrainingPlanModel model = new TeeTrainingPlanModel();
			BeanUtils.copyProperties(list.get(i), model);
			modelList.add(model);
		}
		json.setRtData(modelList);
		json.setRtState(true);
		return json;
	}
}