package com.beidasoft.xzzf.inspection.plan.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.inspection.code.service.BaseCodeDetailService;
import com.beidasoft.xzzf.inspection.inspectedCompany.bean.Company;
import com.beidasoft.xzzf.inspection.inspectedCompany.service.CompanyService;
import com.beidasoft.xzzf.inspection.inspectors.bean.Inspectors;
import com.beidasoft.xzzf.inspection.inspectors.service.InspectorsService;
import com.beidasoft.xzzf.inspection.plan.bean.InspectionDept;
import com.beidasoft.xzzf.inspection.plan.bean.InspectionOrg;
import com.beidasoft.xzzf.inspection.plan.bean.InspectionStaff;
import com.beidasoft.xzzf.inspection.plan.bean.InspectionTask;
import com.beidasoft.xzzf.inspection.plan.dao.InspectionDeptDao;
import com.beidasoft.xzzf.inspection.plan.dao.InspectionOrgDao;
import com.beidasoft.xzzf.inspection.plan.dao.InspectionTaskDao;
import com.beidasoft.xzzf.inspection.plan.model.InspectionTaskModel;
import com.beidasoft.xzzf.punish.common.bean.PunishBase;
import com.beidasoft.xzzf.punish.common.dao.PunishBaseDao;
import com.beidasoft.xzzf.punish.common.service.PunishBaseService;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class InspectionTaskService extends TeeBaseService{
	@Autowired
	private InspectionDeptDao inspectionDeptDao;
	@Autowired
	private InspectionTaskDao inspectionTaskDao;
	@Autowired
	private InspectionOrgDao inspectionOrgDao;
	@Autowired
	private InspectionStaffService inspectionStaffService;
	@Autowired
	private InspectorsService inspectorsService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private BaseCodeDetailService baseCodeDetailService;
	@Autowired
	private PunishBaseDao baseDao;
	@Autowired
	private TeePersonService personService;
	/**
	 * 保存到案件主表
	 * @param inspectionId
	 * @return
	 */
	public TeeJson insertPunishBase(String inspectionId){
		TeeJson json = new TeeJson();
		List<InspectionTask> taskList = inspectionTaskDao.getListByInsId(inspectionId);
		if (!taskList.isEmpty()) {
			for (int i = 0;i < taskList.size();i++) {
				PunishBase punishBase = new PunishBase();
				punishBase.setBaseId(TeeStringUtil.getString(UUID.randomUUID()));
				Date now = new Date();
				punishBase.setBaseCode(TeeDateUtil.format(now,"yyyyMMddHHmm000ss"));
				//案件来源
				punishBase.setSourceId(taskList.get(i).getId());
				punishBase.setSourceType("20");
				//当事人地址和类型
				punishBase.setOrganName(taskList.get(i).getOrgName());
				punishBase.setOrganAddress(taskList.get(i).getOrgAddress());
				punishBase.setOrganType("1");
				//当事人类型
				punishBase.setLitigantType("2");
				
				//主办人
				punishBase.setMajorPersonId(taskList.get(i).getMajorPerson());
				Inspectors MajorPerson = inspectorsService.getById(taskList.get(i).getMajorPerson());
				punishBase.setMajorPersonName(MajorPerson.getStaffName());
				//协办人
				punishBase.setMinorPersonId(taskList.get(i).getMinorPerson());
				Inspectors MinorPerson = inspectorsService.getById(taskList.get(i).getMinorPerson());
				punishBase.setMinorPersonName(MinorPerson.getStaffName());
				punishBase.setDepartmentId(MajorPerson.getDepartmentId());
				punishBase.setDepartmentName(MajorPerson.getDepartmentName());
				// 负责人
				punishBase.setChargePsnId(punishBase.getMajorPersonId());
				punishBase.setChargePsnName(punishBase.getMajorPersonName());
				punishBase.setChargeDeptId(punishBase.getDepartmentId());
				punishBase.setChargeDeptName(punishBase.getDepartmentName());
				
				// 企业信息
				Company company = companyService.getById(taskList.get(i).getOrgId());
				// 电话
				punishBase.setOrganLeadingTel(company.getLegalRepreTel());
				// 法人
				punishBase.setOrganLeadingName(company.getLegalRepre());
				
				// 案件环节状态
				punishBase.setStatus("00");
				// 未立案
				punishBase.setIsApply(1);
				// 未申请立案
				punishBase.setIsRegister(1);
				// 是否结案
				punishBase.setIsClosed(0);
				// 是否听证
				punishBase.setIsHearing(0);
				// 归档标识
				punishBase.setPunishFlg("0");
				// 是否借阅
				punishBase.setBorrowingFlg("0");
				
				baseDao.save(punishBase);//保存到案件主表
				taskList.get(i).setBaseId(punishBase.getBaseId());
				inspectionTaskDao.update(taskList.get(i));//回填案件id
				json.setRtState(true);
			}
		}
		return json;
	}
	/**
	 * 修改计划
	 * @param inspectionTaskModel
	 * @return
	 */
	public TeeJson update(InspectionTaskModel inspectionTaskModel){
		TeeJson json = new TeeJson();
		InspectionTask task = inspectionTaskDao.get(inspectionTaskModel.getId());
		if (!TeeUtility.isNullorEmpty(task)) {
			//主办
			task.setMajorPerson(inspectionTaskModel.getMajorPerson());
			//协办
			task.setMinorPerson(inspectionTaskModel.getMinorPerson());
			Company company = companyService.getById(inspectionTaskModel.getOrgId());
			if (!TeeUtility.isNullorEmpty(company)) {
				task.setOrgId(company.getId());
				task.setOrgName(company.getOrgName());
				task.setOrgAddress(company.getRegAddr());
				inspectionTaskDao.update(task);
				json.setRtState(true);
			}
		}
		return json;
	}
	/**
	 * 根据计划id查询
	 * @param id
	 * @return
	 */
	public InspectionTaskModel getObjById(String id){
		InspectionTask task = inspectionTaskDao.getObjById(id);
		InspectionTaskModel taskModel = new InspectionTaskModel();
		Map<String, String> codeMap = baseCodeDetailService.getCodeMap("ZF_INSPECTION_ORG_TYPE");
		BeanUtils.copyProperties(task, taskModel);
		//主办人
		Inspectors majorPerson = inspectorsService.getById(task.getMajorPerson());
		taskModel.setMajorPersonName(majorPerson.getStaffName());
		taskModel.setMajorPersonCode(personService.get(task.getMajorPerson()).getPerformCode());
		//协办人
		Inspectors minorPerson = inspectorsService.getById(task.getMinorPerson());
		taskModel.setMinorPersonName(minorPerson.getStaffName());
		taskModel.setMinorPersonCode(personService.get(task.getMinorPerson()).getPerformCode());
		//所属部门名称
		taskModel.setPersonDept(majorPerson.getDepartmentName());
		//被检查企业
		Company company = companyService.getById(task.getOrgId());
		taskModel.setOrgCode(company.getOrgCode());
		taskModel.setOrgName(company.getOrgName());
		taskModel.setOrgType(codeMap.get(company.getOrgTypeDic()));
		return taskModel;
	}
	/**
	 * 分配任务
	 * @param inspectionId
	 * @return
	 */
	public TeeJson assignmentTask(String inspectionId){
		TeeJson json = new TeeJson();
		//再次分配时删除之前所有记录
		delListByInsId(inspectionId);
		Map<String, String> orgType = baseCodeDetailService.getCodeMap("ZF_INSPECTION_ORG_TYPE");
		//已经被分配的企业
		List<InspectionOrg> allOrg = new ArrayList<InspectionOrg>();
		for (String orgTypeKey : orgType.keySet()) {
			//拿到一种类型的企业
			List<InspectionOrg> oneTypeList = inspectionOrgDao.getListByOrgType(inspectionId, orgTypeKey);
			List<InspectionOrg> jiaoJi = new ArrayList<InspectionOrg>(oneTypeList);
			jiaoJi.retainAll(allOrg);
			//如果第二次查到了同一家企业
			if (jiaoJi.size()>0) {
				//去重--------开始---------
				Iterator<InspectionOrg> oneTypeiter = oneTypeList.iterator();
				while (oneTypeiter.hasNext()) {
					for (int i = 0; i < jiaoJi.size(); i++) {
						if (jiaoJi.get(i).getId().equals(oneTypeiter.next().getId())) {
							oneTypeiter.remove();
						}
					}
				}
				//去重--------结束---------
			}
			//查询检查该类型企业的部门
			List<InspectionDept> deptList = inspectionDeptDao.getListByDepartmentInspectType(inspectionId, orgTypeKey);
			//非空验证
			if (!deptList.isEmpty()) {
				if (deptList.size()>1) {
					//如果存在两个部门以上的平均分配
					int orgSize = oneTypeList.size()/deptList.size();
					//根据部门数量循环
					for (int i = 0; i < deptList.size(); i++) {
						List<InspectionOrg> beiChaOrg = new ArrayList<InspectionOrg>();
						int deptId = deptList.get(i).getExecuteDepartment();
						List<InspectionStaff> staffList = inspectionStaffService.getListByDeptId(inspectionId, deptId);
						if (i==deptList.size()-1) {
							//最后一次循环的部门检查剩下的企业
							for (int j = orgSize*i; j < oneTypeList.size(); j++) {
								beiChaOrg.add(oneTypeList.get(j));
							}
							randomAllocation(beiChaOrg, staffList, inspectionId);
						} else {
							//其余平均分
							for (int j = orgSize*i; j < orgSize*(i+1); j++) {
								beiChaOrg.add(oneTypeList.get(j));
							}
							randomAllocation(beiChaOrg, staffList, inspectionId);
							allOrg.addAll(oneTypeList);
						}
					}
				} else {
					List<InspectionStaff> staffList = inspectionStaffService.getListByDeptId(inspectionId, deptList.get(0).getExecuteDepartment());
					randomAllocation(oneTypeList, staffList, inspectionId);
					allOrg.addAll(oneTypeList);
				}
			}
		}
		json.setRtState(true);
		return json;
	}
	/**
	 * 随机将人员分配给企业
	 * @param companys
	 * @param inspectors
	 * @param inspectionId
	 */
	private void randomAllocation(List<InspectionOrg> orgs,List<InspectionStaff> staffs,String inspectionId){
		//循环匹配 主办人、协办人和企业
		for (int i = 0; i < orgs.size(); i++) {
			if (staffs.size()<=0) {
				break;
			}
			for (int j = 1; j < staffs.size(); j+=2) {
				InspectionTask task = new InspectionTask();
				Company company = companyService.getById(orgs.get(i).getOrgId());
				task.setId(TeeStringUtil.getString(UUID.randomUUID()));
				task.setInspectionId(inspectionId);
				task.setMajorPerson(staffs.get(j-1).getStaffId());
				task.setMinorPerson(staffs.get(j).getStaffId());
				task.setDeptId(personService.get(staffs.get(j-1).getStaffId()).getDept().getUuid());
				task.setOrgId(company.getId());
				task.setOrgName(company.getOrgName());
				task.setOrgAddress(company.getTaxAddr());
				inspectionTaskDao.save(task);
				i++;
				if (i>=orgs.size()) {
					break;
				}
			}
			//打乱人员集合 再次循环匹配
			Collections.shuffle(staffs);
			i--;
		}
	}
	/**
	 * 分页查询
	 * @param inspectionId
	 * @param firstResult
	 * @param rows
	 * @return
	 */
	public TeeEasyuiDataGridJson taskListByPage(InspectionTaskModel taskModel,int firstResult,int rows){
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		List<InspectionTask> list = inspectionTaskDao.getListByPage(firstResult, rows, taskModel);
		List<InspectionTaskModel> modelList = new ArrayList<InspectionTaskModel>();
		Map<String, String> codeMap = baseCodeDetailService.getCodeMap("ZF_INSPECTION_ORG_TYPE");
		for (InspectionTask inspectionTask : list) {
			InspectionTaskModel inspectionTaskModel = new InspectionTaskModel();
			BeanUtils.copyProperties(inspectionTask, inspectionTaskModel);
			//主办人
			Inspectors majorPerson = inspectorsService.getById(inspectionTask.getMajorPerson());
			inspectionTaskModel.setMajorPersonName(majorPerson.getStaffName());
			inspectionTaskModel.setMajorPersonCode(personService.get(inspectionTask.getMajorPerson()).getPerformCode());
			//协办人
			Inspectors minorPerson = inspectorsService.getById(inspectionTask.getMinorPerson());
			inspectionTaskModel.setMinorPersonName(minorPerson.getStaffName());
			inspectionTaskModel.setMinorPersonCode(personService.get(inspectionTask.getMinorPerson()).getPerformCode());
			//所属部门名称
			inspectionTaskModel.setPersonDept(majorPerson.getDepartmentName());
			//被检查企业
			Company company = companyService.getById(inspectionTask.getOrgId());
			inspectionTaskModel.setOrgCode(company.getOrgCode());
			inspectionTaskModel.setOrgName(company.getOrgName());
			inspectionTaskModel.setOrgType(codeMap.get(company.getOrgTypeDic()));
			modelList.add(inspectionTaskModel);
		}
		long total = inspectionTaskDao.getTotal(taskModel);
		json.setRows(modelList);
		json.setTotal(total);
		return json;
	}
	/**
	 * 根据主表id删除相关所有任务
	 * @param inspectionId
	 * @return
	 */
	public TeeJson delListByInsId(String inspectionId){
		TeeJson json = new TeeJson();
		List<InspectionTask> taskList = inspectionTaskDao.getListByInsId(inspectionId);
		if (!taskList.isEmpty()) {
			for (InspectionTask inspectionTask : taskList) {
				inspectionTaskDao.deleteByObj(inspectionTask);
				json.setRtState(true);
			}
		}
		return json;
	}
}
