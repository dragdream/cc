package com.tianee.oa.core.base.hr.recruit.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.base.hr.TeeHrCodeManager;
import com.tianee.oa.core.base.hr.recruit.bean.TeeHrFilter;
import com.tianee.oa.core.base.hr.recruit.bean.TeeHrPool;
import com.tianee.oa.core.base.hr.recruit.dao.TeeHrPoolDao;
import com.tianee.oa.core.base.hr.recruit.model.TeeHrPoolModel;
import com.tianee.oa.core.base.hr.recruit.plan.bean.TeeRecruitPlan;
import com.tianee.oa.core.base.hr.recruit.plan.dao.TeeRecruitPlanDao;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.thread.TeeWebMailSendProcessor;
import com.tianee.webframe.util.thread.TeeWebMailThreadPool;

@Service
public class TeeHrPoolService extends TeeBaseService  {
	@Autowired
	private TeeHrPoolDao hrPoolDao;
	
	@Autowired
	private TeeRecruitPlanDao recruitPlanDao;
	@Autowired
	private TeeDeptDao deptDao;
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeBaseUpload upload;
	
	@Autowired
	private TeeAttachmentService attachmentService;
	
	@Autowired
	private TeeHrFilterService filterService;
	

	public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	public TeeJson addOrUpdate(HttpServletRequest request,
			TeeHrPoolModel model) throws IOException {

		TeeJson json = new TeeJson();
		
		//照片
		List<TeeAttachment> listAttachments  = new ArrayList<TeeAttachment>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<TeeAttachment> attachments = upload.manyAttachUpload(multipartRequest, TeeAttachmentModelKeys.hrPool);
		
		//简历
		List<TeeAttachment> attachIdsList  = new ArrayList<TeeAttachment>();
		if(!TeeUtility.isNullorEmpty(model.getAttacheIds())){
			attachIdsList = attachmentService.getAttachmentsByIds(model.getAttacheIds());
		}
		int planId = TeeStringUtil.getInteger(model.getPlanId(), 0);
		
		TeeRecruitPlan plan = null ;
		
		if(planId > 0){
			plan= recruitPlanDao.get(planId);
		}
		try {
			TeePerson person = (TeePerson) request.getSession().getAttribute(
					TeeConst.LOGIN_USER);
			if (model.getSid() > 0) {
				TeeHrPool obj = hrPoolDao.get(model.getSid());
				BeanUtils.copyProperties(model, obj);
				for (int i = 0; i < attachIdsList.size(); i++) {
					TeeAttachment attach = attachIdsList.get(i);
					attach.setModelId(String.valueOf(obj.getSid()));
					attachmentService.updateAttachment(attach);
				}
				if(attachments.size() > 0){
					obj.setAttachemnt(attachments.get(0));
				}
			   if(!TeeUtility.isNullorEmpty(model.getEmployeeBirthStr())){
		        	obj.setEmployeeBirth(TeeUtility.parseDate("yyyy-MM-dd",model.getEmployeeBirthStr()));
		        }
		        
		        if(!TeeUtility.isNullorEmpty(model.getGraduationDateStr())){
		        	obj.setGraduationDate(TeeUtility.parseDate("yyyy-MM-dd",model.getGraduationDateStr()));
		        }
		        if(!TeeUtility.isNullorEmpty(model.getJobBeginningStr())){
		        	obj.setJobBeginning(TeeUtility.parseDate("yyyy-MM-dd",model.getJobBeginningStr()));
		        }
				obj.setPlan(plan);
				hrPoolDao.update(obj);	
			} else {
				TeeHrPool obj = new TeeHrPool();
				BeanUtils.copyProperties(model, obj);
				obj.setCreateTime(new Date());
				
				if(attachments.size() > 0){
					obj.setAttachemnt(attachments.get(0));
				}
				

		        if(!TeeUtility.isNullorEmpty(model.getEmployeeBirthStr())){
		        	obj.setEmployeeBirth(TeeUtility.parseDate("yyyy-MM-dd",model.getEmployeeBirthStr()));
		        }
		        
		        if(!TeeUtility.isNullorEmpty(model.getGraduationDateStr())){
		        	obj.setGraduationDate(TeeUtility.parseDate("yyyy-MM-dd",model.getGraduationDateStr()));
		        }
		        if(!TeeUtility.isNullorEmpty(model.getJobBeginningStr())){
		        	obj.setJobBeginning(TeeUtility.parseDate("yyyy-MM-dd",model.getJobBeginningStr()));
		        }
		        obj.setPlan(plan);
				hrPoolDao.save(obj);
				
				for (int i = 0; i < listAttachments.size(); i++) {
					TeeAttachment attach = attachIdsList.get(i);
					attach.setModelId(String.valueOf(obj.getSid()));
					attachmentService.updateAttachment(attach);
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
	public TeeEasyuiDataGridJson getHrPoolList(TeeDataGridModel dm,
			HttpServletRequest request, TeeHrPoolModel model) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		String hql = "from TeeHrPool pool where 1 = 1";
		List param = new ArrayList();
		// 设置总记录数
		j.setTotal(hrPoolDao.countByList("select count(*) " + hql, param));// 设置总记录数

		hql += " order by pool.createTime desc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		// List<TeeHrPool> list =
		// hrPoolDao.getMeetPageFind(firstIndex, dm.getRows(), dm,
		// model);// 查
		List<TeeHrPool> list = hrPoolDao.pageFindByList(hql,
				(dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeHrPoolModel> modelList = new ArrayList<TeeHrPoolModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeHrPoolModel modeltemp = parseModel(list.get(i) , true);
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}
	
	/**
	 * 通用列表
	 * 
	 * @param dm
	 * @return
	 * @throws ParseException
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson queryHrPoolList(TeeDataGridModel dm,
			HttpServletRequest request, TeeHrPoolModel model) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		
	
		String hql = "from TeeHrPool pool where 1 = 1";
		List param = new ArrayList();
		if (!TeeUtility.isNullorEmpty(request.getParameter("planNo"))) {
			hql += " and pool.plan.planNo like ?";
			param.add("%" + request.getParameter("planNo") + "%");
		}
		
		if (!TeeUtility.isNullorEmpty(request.getParameter("employeeStatusStr"))) {
			hql += " and pool.employeeStatus in (" + request.getParameter("employeeStatusStr") + ")" ;
		}
		if (!TeeUtility.isNullorEmpty(model.getEmployeeName())) {
			hql += " and pool.employeeName like ?";
			param.add("%" + model.getEmployeeName() + "%");
		}
		if (!TeeUtility.isNullorEmpty(model.getEmployeeSex())) {
			hql += " and pool.employeeSex=?";
			param.add(model.getEmployeeSex());
		}
		if (!TeeUtility.isNullorEmpty(model.getEmployeeNativePlace())) {//籍贯
			hql += " and pool.employeeNativePlace=?";
			param.add(model.getEmployeeNativePlace());
		}
		
		if (!TeeUtility.isNullorEmpty(model.getEmployeePoliticalStatus())) {//治面貌
			hql += " and pool.employeePoliticalStatus=?";
			param.add(model.getEmployeePoliticalStatus());
		}
		
		if (!TeeUtility.isNullorEmpty(model.getEmployeeMaritalStatus())) {//婚姻状况
			hql += " and pool.employeeMaritalStatus=?";
			param.add(model.getEmployeeMaritalStatus());
		}
		
		if (!TeeUtility.isNullorEmpty(model.getJobCategory())) {//期望工作性质
			hql += " and pool.jobCategory=?";
			param.add(model.getJobCategory());
		}
		if (!TeeUtility.isNullorEmpty(model.getPosition())) {//岗位
			hql += " and pool.position=?";
			param.add(model.getPosition());
		}
		if (!TeeUtility.isNullorEmpty(model.getEmployeeMajor())) {//学位
			hql += " and pool.employeeMajor=?";
			param.add(model.getEmployeeMajor());
		}
		
		if (!TeeUtility.isNullorEmpty(model.getEmployeeHighestSchool())) {//学历
			hql += " and pool.employeeHighestSchool=?";
			param.add(model.getEmployeeHighestSchool());
		}
		
		if (!TeeUtility.isNullorEmpty(model.getWorkCity())) {
			hql += " and pool.workCity like ?";
			param.add("%" + model.getWorkCity() + "%");
		}
		/*if (model.getExpectedSalary() != 0) {
			hql += " and pool.expectedSalary = ? ";
			param.add(model.getExpectedSalary() );
		}*/
		
		if (TeeUtility.isNumber(request.getParameter("minExpectedSalary"))) {
			hql += " and pool.expectedSalary >= ? ";
			param.add(TeeStringUtil.getDouble(request.getParameter("minExpectedSalary"), 0));
		}
		if (TeeUtility.isNumber(request.getParameter("maxExpectedSalary"))) {
			hql += " and pool.expectedSalary <= ? ";
			param.add(TeeStringUtil.getDouble(request.getParameter("maxExpectedSalary"), 0));
		}
		
		// j.setTotal(hrPoolDao.getQueryCount(loginPerson ,model));//
		// 设置总记录数
		j.setTotal(hrPoolDao.countByList("select count(*) " + hql, param));// 设置总记录数

		hql += " order by pool.createTime desc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		// List<TeeHrPool> list =
		// hrPoolDao.getMeetPageFind(firstIndex, dm.getRows(), dm,
		// model);// 查
		List<TeeHrPool> list = hrPoolDao.pageFindByList(hql,
				(dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeHrPoolModel> modelList = new ArrayList<TeeHrPoolModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeHrPoolModel modeltemp = parseModel(list.get(i) , true);
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
	public TeeHrPoolModel parseModel(TeeHrPool obj ,boolean isSimple) {
		TeeHrPoolModel model = new TeeHrPoolModel();
		if (obj == null) {
			return model;
		}
		BeanUtils.copyProperties(obj, model);
		String sexDesc = "男";
		if(TeeStringUtil.getString(obj.getEmployeeSex()).equals("1")){
			sexDesc = "女";
		}
		model.setEmployeeSexDesc(sexDesc);
	    List<TeeAttachment> attaches = attachmentService.getAttaches(TeeAttachmentModelKeys.hrPool, String.valueOf(obj.getSid()));
	    List<TeeAttachmentModel> attacheModels = new ArrayList<TeeAttachmentModel>();
        String attacheIds = "";
        TeeAttachmentModel attModel = new TeeAttachmentModel();
        if(!isSimple){
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
             
             TeeAttachment att = obj.getAttachemnt();
             if(att != null){
            		
            		BeanUtils.copyProperties(att, attModel);
            		attModel.setUserId(att.getUser().getUuid()+"");
            		attModel.setUserName(att.getUser().getUserName());
            		attModel.setPriv(1+2+4);//
         			model.setAttachemntModel(attModel);
             }
        }
        if(obj.getEmployeeStatus() == 1 || obj.getEmployeeStatus() == 0){
        	model.setEmployeeStatusDesc("在库中");// 当前状态 1-未入职  -可以关联需求 2-已通过   //3-未通过  可以关联需  4-已发offer 5-已入职
        }else if(obj.getEmployeeStatus() == 2){
        	model.setEmployeeStatusDesc("已通过");// 
        }else if(obj.getEmployeeStatus() == 3){
        	model.setEmployeeStatusDesc("未通过 ");// 
        }else if(obj.getEmployeeStatus() == 4){
        	model.setEmployeeStatusDesc("已发Offer");// 
        }else if(obj.getEmployeeStatus() == 5){
        	model.setEmployeeStatusDesc("已入职");// 
        }
        TeeRecruitPlan plan =  obj.getPlan();
        if(plan != null){
        	model.setPlanId(plan.getSid() + "");
        	model.setPlanName(plan.getPlanName());
        }else{
        	model.setPlanName("");
        }
        
        if(obj.getEmployeeBirth() != null){
        	model.setEmployeeBirthStr(TeeUtility.getDateTimeStr(obj.getEmployeeBirth(), format));
        }
        if(obj.getCreateTime() != null){
        	model.setCreateTimeStr(TeeUtility.getDateTimeStr(obj.getCreateTime()));
        }
        if(obj.getGraduationDate()!= null){
        	model.setGraduationDateStr(TeeUtility.getDateTimeStr(obj.getGraduationDate(), format));
        }
        if(obj.getJobBeginning()!= null){
        	model.setJobBeginningStr(TeeUtility.getDateTimeStr(obj.getJobBeginning(), format));
        }
        model.setAttachesModels(attacheModels);
        
        //HR代码
        String employeeMaritalStatusDesc = "";//婚姻状况
        if(!TeeUtility.isNullorEmpty(obj.getEmployeeMaritalStatus())){
        	if(obj.getEmployeeMaritalStatus().equals("0")){
        		employeeMaritalStatusDesc = "未婚";
        	}else if(obj.getEmployeeMaritalStatus().equals("1")){
        		employeeMaritalStatusDesc = "已婚";
        	}else if(obj.getEmployeeMaritalStatus().equals("2")){
        		employeeMaritalStatusDesc = "离异";
        	}else if(obj.getEmployeeMaritalStatus().equals("3")){
        		employeeMaritalStatusDesc = "丧偶";
        	}
		}
        model.setEmployeeMaritalStatusDesc(employeeMaritalStatusDesc);
        String employeePoliticalStatusDesc = "";//政治面貌
		 if(!TeeUtility.isNullorEmpty(obj.getEmployeePoliticalStatus())){
			 employeePoliticalStatusDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("STAFF_POLITICAL_STATUS", obj.getEmployeePoliticalStatus());
		}
		model.setEmployeePoliticalStatusDesc(employeePoliticalStatusDesc);
        String jobCategoryDesc = "";//期望工作性质
        if(!TeeUtility.isNullorEmpty(obj.getJobCategory())){
        	jobCategoryDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("JOB_CATEGORY", obj.getJobCategory());
		}
		model.setJobCategoryDesc(jobCategoryDesc);
		
        String positionDesc = "";//岗位
        if(!TeeUtility.isNullorEmpty(obj.getPosition())){
        	positionDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("POOL_POSITION", obj.getPosition());
		}
		model.setPositionDesc(positionDesc);
		String startWorkingDesc = "";//期望工作性质 		：0-周以内 1-1个月内< 2-1~3个月 3-临时 4-随时到岗
        if(!TeeUtility.isNullorEmpty(obj.getStartWorking())){
        	if(obj.getStartWorking().equals("0")){
        		startWorkingDesc = "一周以内";
        	}else if(obj.getStartWorking().equals("1")){
        		startWorkingDesc = "1个月内";
        	}else if(obj.getStartWorking().equals("2")){
        		startWorkingDesc = "1~3个月";
        	}else if(obj.getStartWorking().equals("3")){
        		startWorkingDesc = "临时";
        	}else if(obj.getStartWorking().equals("4")){
        		startWorkingDesc = "随时到岗";
        	}
		}
        model.setStartWorkingDesc(startWorkingDesc);
        String employeeMajorDesc = "";//专业
        if(!TeeUtility.isNullorEmpty(obj.getEmployeeMajor())){
        	employeeMajorDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("POOL_EMPLOYEE_MAJOR", obj.getEmployeeMajor());
		}
		model.setEmployeeMajorDesc(employeeMajorDesc);
        String employeeHighestDegreeDesc = "";//学位
        if(!TeeUtility.isNullorEmpty(obj.getEmployeeHighestDegree())){
        	employeeHighestDegreeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("POOL_EMPLOYEE_MAJOR", obj.getEmployeeHighestDegree());
		}
		model.setEmployeeHighestDegreeDesc(employeeHighestDegreeDesc);
        String employeeHighestSchoolDesc = "";//学历
        if(!TeeUtility.isNullorEmpty(obj.getEmployeeHighestSchool())){
        	employeeHighestSchoolDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("STAFF_HIGHEST_SCHOOL", obj.getEmployeeHighestSchool());
		}
		model.setEmployeeHighestSchoolDesc(employeeHighestSchoolDesc);
        String employeeNativePlaceDesc = "";//籍贯
        if(!TeeUtility.isNullorEmpty(obj.getEmployeeNativePlace())){
        	employeeNativePlaceDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("AREA", obj.getEmployeeNativePlace());
		}
		model.setEmployeeNativePlaceDesc(employeeNativePlaceDesc);
        String recruChannelDesc = "";//招聘渠道
        if(!TeeUtility.isNullorEmpty(obj.getRecruChannel())){
        	recruChannelDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PLAN_DITCH", obj.getRecruChannel());
		}
		model.setRecruChannelDesc(recruChannelDesc);
		
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
	public TeeJson getInfoByIdService(HttpServletRequest request,
			TeeHrPoolModel model) {
		TeeJson json = new TeeJson();
		if (model.getSid() > 0) {
			TeeHrPool out = hrPoolDao.get(model.getSid());
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
		String ids [] = TeeStringUtil.parseStringArray(sids);
		TeeHrPool hrPool = null;
		for(String id:ids){
			if("".equals(id)){
				continue;
			}
			hrPool = (TeeHrPool) simpleDaoSupport.get(TeeHrPool.class, Integer.parseInt(id));//获取人才库信息
			List<TeeHrFilter> filters = simpleDaoSupport.find("from TeeHrFilter where hrPool.sid=?", new Object[]{hrPool.getSid()});
			//删除人才筛选信息
			for(TeeHrFilter filter:filters){
				filterService.deleteObjById(filter.getSid()+"");
			}
			//删除人才录用信息
			simpleDaoSupport.executeUpdate("delete from TeeHrRecruitment where poolObj.sid=?",new Object[]{hrPool.getSid()} );
			
			simpleDaoSupport.deleteByObj(hrPool);
		}
		
//		hrPoolDao.deleteByIds(sids);
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}

	/**
	 * 发送/取消 offer
	 * 
	 * @date 2014年5月27日
	 * @author
	 * @param sids
	 * @return
	 */
	public TeeJson sendoffer(String sids , String optType) {
		TeeJson json = new TeeJson();
		//发送/取消 offer
		simpleDaoSupport.executeUpdate("update  TeeHrPool set employeeStatus = ?   where sid in (" + sids + ")",new Object[]{TeeStringUtil.getInteger(optType,1)} );
		json.setRtState(true);
		json.setRtMsg("发送成功!");
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
	public TeeEasyuiDataGridJson getPlanApprovalListService(
			TeeDataGridModel dm, HttpServletRequest request,
			TeeHrPoolModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);

		Map requestDatas = TeeServletUtility.getParamMap(request);
		String planStatus = (String) requestDatas.get("planStatus");

		String hql = "from TeeHrPool require where approvePerson.uuid = ? and planStatus = ? ";
		List param = new ArrayList();
		param.add(loginPerson.getUuid());
		param.add(Integer.parseInt(planStatus));

		// 设置总记录数
		j.setTotal(hrPoolDao.countByList("select count(*) " + hql, param));// 设置总记录数

		hql += " order by require.createTime desc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeHrPool> list = hrPoolDao.pageFindByList(hql,
				(dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeHrPoolModel> modelList = new ArrayList<TeeHrPoolModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeHrPoolModel modeltemp = parseModel(list.get(i) , true);
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	
	/**
	 * 查询
	 * @author syl
	 * @date 2014-6-27
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson selectPool( HttpServletRequest request , TeeHrPoolModel model){
		TeeJson json = new TeeJson();
		List<TeeHrPool> list = hrPoolDao.selectPool(model);
		List<TeeHrPoolModel> modelList = new ArrayList<TeeHrPoolModel>();
		for (int i = 0; i < list.size(); i++) {
			TeeHrPoolModel modelObj = parseModel(list.get(i), true);
			modelList.add(modelObj);
		}
		json.setRtData(modelList);
		json.setRtState(true);
		return json;
	}

	
	/**
	 * 人才库批量导入
	 * @param request
	 * @return
	 */
	public TeeJson importHrPool(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer("[");
		String infoStr= "";
		
		int importCount=0;//成功导入的记录数
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		InputStream ins=null;
		TeeJson json = new TeeJson();
		Workbook wb = null;  
		try{
			MultipartFile  file = multipartRequest.getFile("excelFile");
			ins = file.getInputStream();
			wb = Workbook.getWorkbook(ins);
			if(wb==null){
				json.setRtState(false);
				json.setRtMsg("上传文件错误！");
			}
			Sheet[] sheet = wb.getSheets();  
	        if (sheet != null && sheet.length > 0) {  
	            // 对每个工作表进行循环  
	            for (int i = 0; i < sheet.length; i++) {  
	                // 得到当前工作表的行数  
	                int rowNum = sheet[i].getRows(); 
	            
                    if(sheet[i].getColumns()!=17){
                    	json.setRtState(false);
                    	json.setRtMsg("你导入的文件不正确，请下载模板，按模板填写内容");
                    	break;
                    }
	                for (int j = 1; j < rowNum; j++) {
	                	
	                	TeeHrPoolModel model = new TeeHrPoolModel();
	                    // 得到当前行的所有单元格  
	                    Cell[] cells = sheet[i].getRow(j); 
	                    if (cells != null && cells.length > 0) {  
	                        // 对每个单元格进行循环  
	                        for (int k = 0; k < cells.length; k++) {  
	                            String cellValue = cells[k].getContents();  
	                            switch(k){
	                            	case 0:
	                            	model.setPlanNo(cellValue);//计划编号
	                            	break;
	                            	case 1:
	                            	model.setEmployeeName(cellValue);//姓名
	                            	break;
	                            	case 2:
		                            model.setEmployeeSex(cellValue);//性别
		                            break;
	                            	case 3:
		                            model.setEmployeeAge(cellValue);//年龄
		                            break;
	                            	case 4:
		                            model.setEmployeeBirthStr(cellValue);//出生日期
		                            break;
	                            	case 5:
		                            model.setEmployeeNationality(cellValue);//民族
		                            break;
	                            	case 6:
		                            model.setResidencePlace(cellValue);//现居住城市
		                            break;
	                            	case 7:
		                            model.setEmployeePhone(cellValue);//联系电话
		                            break;
	                            	case 8:
		                            model.setEmployeeEmail(cellValue);//email
		                            break;
	                            	case 9:
		                            model.setEmployeeDomicilePlace(cellValue);//户口所在地
		                            break;
	                            	case 10:
		                            model.setEmployeeMaritalStatus(cellValue);//婚姻狀況
			                        break;
	                             	case 11:
		                            model.setEmployeePoliticalStatusDesc(cellValue);//政治面貌
	                            	break;
	                            	case 12:
		                            model.setExpectedSalary(TeeStringUtil.getDouble(cellValue, 0.00));//期望薪资
		                            break;
	                            	case 13:
		                            model.setEmployeeMajorDesc(cellValue);//所学专业
		                            break;
	                            	case 14:
			                        model.setEmployeeHighestSchoolDesc(cellValue);//学历
			                        break;
	                            	case 15:
		                            model.setEmployeeHighestDegreeDesc(cellValue);//学位
		                            break;
	                            	case 16:
		                            model.setResume(cellValue);//简历内容
		                            break;
	                            	
	                            }
	                        }  
	                    } 
	                    String color = "red";//显示导入信息的返回信息的 颜色
	                    
	                    //判断招聘计划编号
	                    if(TeeUtility.isNullorEmpty(model.getPlanNo())){
	                    	infoStr = "计划编号不能为空！";
	                    	getHrPoolInfoStr(sb, model, infoStr, color);
					        continue;
	                    }else{
	                    	//判断该招聘计划是否存在
	                    	List<TeeRecruitPlan> planList= simpleDaoSupport.executeQuery(" from TeeRecruitPlan where planNo=?  ", new Object[]{model.getPlanNo()});
	                        if(planList!=null&&planList.size()>0){//招聘计划存在
	                        	model.setPlanId(TeeStringUtil.getString(planList.get(0).getSid()));
	                        	model.setPlanName(TeeStringUtil.getString(planList.get(0).getPlanName()));
	                        }else{//招聘计划不存在
	                        	infoStr = "计划编号对应的招聘计划不存在！";
		                    	getHrPoolInfoStr(sb, model, infoStr, color);
						        continue;
	                        }
	                    }
	                    
	                    
	                    //判断姓名
	                    if(TeeUtility.isNullorEmpty(model.getEmployeeName())){
	                    	infoStr = "姓名不能为空！";
	                    	getHrPoolInfoStr(sb, model, infoStr, color);
					        continue;
	                    }
	                    
	                    //判断简历
	                    if(TeeUtility.isNullorEmpty(model.getResume())){
	                    	infoStr = "简历不能为空！";
	                    	getHrPoolInfoStr(sb, model, infoStr, color);
					        continue;
	                    }
	                    
	                    
	                    
	                    //处理性别
	                    if(!TeeUtility.isNullorEmpty(model.getEmployeeSex())){
	                    	if("男".equals(model.getEmployeeSex())){
	                    		model.setEmployeeSex("0");
	                    	}else{
	                    		model.setEmployeeSex("1");
	                    	}
	                    }
	                    
	                    //处理婚姻状态
	                    if(!TeeUtility.isNullorEmpty(model.getEmployeeMaritalStatus())){
	                    	if("未婚".equals(model.getEmployeeMaritalStatus())){
	                    		model.setEmployeeMaritalStatus("0");
	                    	}else if("已婚".equals(model.getEmployeeMaritalStatus())){
	                    		model.setEmployeeMaritalStatus("1");
	                    	}else if("离异".equals(model.getEmployeeMaritalStatus())){
	                    		model.setEmployeeMaritalStatus("2");
	                    	}else if("丧偶".equals(model.getEmployeeMaritalStatus())){
	                    		model.setEmployeeMaritalStatus("3");
	                    	}else{
	                    		model.setEmployeeMaritalStatus("");
	                    	}
	                    }
	                    //专业   学历    学位     政治面貌
	                    //专业
	                     if(TeeUtility.isNullorEmpty(model.getEmployeeMajorDesc())){
	                    	 model.setEmployeeMajor(""); 
	                     }else{
	                    	 List<Map> mapList1=TeeHrCodeManager.getChildSysCodeListByParentCodeNo("POOL_EMPLOYEE_MAJOR");
		                     for (Map map : mapList1) {
							    if(map.get("codeName").equals(model.getEmployeeMajorDesc())){
							    	model.setEmployeeMajor(TeeStringUtil.getString(map.get("codeNo")));
							    	break;
							    }else{
							    	model.setEmployeeMajor("");
							    }
							 }
	                     }
	                     
	                    //学历
	                     if(TeeUtility.isNullorEmpty(model.getEmployeeHighestSchoolDesc())){
	                    	 model.setEmployeeHighestSchool(""); 
	                     }else{
	                    	 List<Map> mapList2=TeeHrCodeManager.getChildSysCodeListByParentCodeNo("STAFF_HIGHEST_SCHOOL");
		                     for (Map map : mapList2) {
							    if(map.get("codeName").equals(model.getEmployeeHighestSchoolDesc())){
							    	model.setEmployeeHighestSchool(TeeStringUtil.getString(map.get("codeNo")));
							    	break;
							    }else{
							    	model.setEmployeeHighestSchool("");
							    }
							 }
	                     }
	                     
	                   //学位
	                     if(TeeUtility.isNullorEmpty(model.getEmployeeHighestDegreeDesc())){
	                    	 model.setEmployeeHighestDegree(""); 
	                     }else{
	                    	 List<Map> mapList3=TeeHrCodeManager.getChildSysCodeListByParentCodeNo("EMPLOYEE_HIGHEST_DEGREE");
		                     for (Map map : mapList3) {
							    if(map.get("codeName").equals(model.getEmployeeHighestDegreeDesc())){
							    	model.setEmployeeHighestDegree(TeeStringUtil.getString(map.get("codeNo")));
							    	break;
							    }else{
							    	model.setEmployeeHighestDegree("");
							    }
							 }
	                     }
	                   //政治面貌
	                     if(TeeUtility.isNullorEmpty(model.getEmployeePoliticalStatusDesc())){
	                    	 model.setEmployeePoliticalStatus(""); 
	                     }else{
	                    	 List<Map> mapList4=TeeHrCodeManager.getChildSysCodeListByParentCodeNo("STAFF_POLITICAL_STATUS");
		                     for (Map map : mapList4) {
							    if(map.get("codeName").equals(model.getEmployeePoliticalStatusDesc())){
							    	model.setEmployeePoliticalStatus(TeeStringUtil.getString(map.get("codeNo")));
							    	break;
							    }else{
							    	model.setEmployeePoliticalStatus("");
							    }
							 }
	                     }
	                     
	                 
	                     
	                    addOrUpdate(request,model);
	                    importCount++;
	                    infoStr="导入成功";
	                    color="black";
	                    getHrPoolInfoStr(sb, model, infoStr, color);
	                }
	              
	               
	                if(!(sb.toString()).equals("[")){
	        			sb.deleteCharAt(sb.length() -1);
	        		}
	                
	        		sb.append("]");
	        		String data = sb.toString();
	        		List<Map<String, String>> list =  TeeJsonUtil.JsonStr2MapList(data);
	        		json.setRtData(list);
	        		
	                json.setRtState(true);
	                json.setRtMsg(importCount+"");
	            }
	        }
	        wb.close();  
		}catch(Exception ex){
			ex.printStackTrace();
			json.setRtMsg("文件格式不对，请按模板进行内容填写");
			json.setRtState(false);
			
		}
		return json;
	}

	
	
	
	/**
	 * 导入人才库返回导入详细信息
	 * @author 
	 * @date 
	 * @param sb 返回所有字符串信息
	 * @param model
	 * @param infoStr  说明
	 * @param color 颜色
	 * @return
	 */
	public StringBuffer getHrPoolInfoStr(StringBuffer sb ,TeeHrPoolModel model ,String infoStr ,  String color){
		sb.append("{");
        sb.append("employeeName:\"" + model.getEmployeeName() + "\"");
        sb.append(",planNo:\"" + model.getPlanNo() + "\"");
        sb.append(",color:\"" + color + "\"");
        sb.append(",info:\"" + infoStr + "\"");
        sb.append("},");
        return sb;
	}

	
	
	
	/**
	 * 以Email的形式发送Offer
	 * @param request
	 * @return
	 */
	public TeeJson sendEmail(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上传来的sids
		String sids=TeeStringUtil.getString(request.getParameter("sids"));
		String title=TeeStringUtil.getString(request.getParameter("title"));
        String content=TeeStringUtil.getString(request.getParameter("content"));
        
        //获取选中的人才库
        List<TeeHrPool> poolList=simpleDaoSupport.executeQuery(" from TeeHrPool   where sid in (" + sids + ") ", null);
        //获取系统邮箱参数
		Map<String,String> sysWebMail = TeeJsonUtil.JsonStr2Map(TeeSysProps.getString("SYS_WEB_MAIL"));
		Map mailDatas = null;
		String realTitle="";
		String realContent="";
        if(poolList!=null&&poolList.size()>0){
        	for (TeeHrPool teeHrPool : poolList) {
        		realTitle=title.replace("{EmployeeName}", teeHrPool.getEmployeeName());
        		realContent=content.replace("{EmployeeName}", teeHrPool.getEmployeeName());
        		if(!TeeUtility.isNullorEmpty(teeHrPool.getEmployeeEmail())){
        			mailDatas = new HashMap();
            		mailDatas.put("to", teeHrPool.getEmployeeEmail());
            		mailDatas.put("title", realTitle);
            		mailDatas.put("content", realContent);
            		TeeWebMailSendProcessor webMailSendProcess=new TeeWebMailSendProcessor(mailDatas, sysWebMail);
            		TeeWebMailThreadPool.getInstance().execute(webMailSendProcess);
        			
        		}
			}
        }
        json.setRtState(true);
        
		return json;
	}

}
