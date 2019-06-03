package com.beidasoft.xzzf.task.caseAssigned.dao;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.beidasoft.xzzf.punish.common.bean.PunishBase;
import com.beidasoft.xzzf.punish.common.model.PunishBaseModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class CaseTransactDao extends TeeBaseDao<PunishBase> {
	
	   
	/**
	 * 获取总数(案件办理)
	 * @param queryModel
	 * @param userId
	 * @return
	 */   
	public long getTotal(PunishBaseModel queryModel, TeePerson loginUser){
		//获取登录人员的权限
		String roleName = loginUser.getUserRole().getRoleName();
		int departmentId = loginUser.getDept().getUuid();
		int userId = loginUser.getUuid();
		String hql = "select count(id) from PunishBase where 1 = 1 ";

		// 区分现场检查和案件办理的条件 （isApply为0 是 案件办理）
		hql += " and isApply = 0 " ;
		
		if("系统管理员".equals(roleName)) {
			if (!TeeUtility.isNullorEmpty(queryModel.getInspectionDateStartStr())) {
				hql += " and inspectionDate >= '"
						+ queryModel.getInspectionDateStartStr() + " 00:00'";
			}
			if (!TeeUtility.isNullorEmpty(queryModel.getInspectionDateEndStr())) {
				hql += " and inspectionDate <= '" + queryModel.getInspectionDateEndStr() + " 23:59'";
			}
			if (!TeeUtility.isNullorEmpty(queryModel.getFilingDateStartStr())) {
				hql += " and filingDate >= '"+ queryModel.getFilingDateStartStr() + " 00:00'";
			}
			if (!TeeUtility.isNullorEmpty(queryModel.getFilingDateEndStr())) {
				hql += " and filingDate <= '" + queryModel.getFilingDateEndStr() + " 23:59'";
			}
			if (!(StringUtils.isBlank(queryModel.getStatus()) || "-1".equals(queryModel.getStatus()))) {
				hql += " and status = " + queryModel.getStatus();
			}
			if (!(StringUtils.isBlank(queryModel.getLitigantType()) || "-1".equals(queryModel.getLitigantType()))) {
				hql += " and litigantType = " + queryModel.getLitigantType();
			}
		}
		
		// 二.部门领导（部门负责人、部门副职）
		else if(("部门负责人".equals(roleName) || "部门副职".equals(roleName))) {
			if (!TeeUtility.isNullorEmpty(queryModel.getInspectionDateStartStr())) {
				hql += " and inspectionDate >= '"
						+ queryModel.getInspectionDateStartStr() + " 00:00'";
			}
			if (!TeeUtility.isNullorEmpty(queryModel.getInspectionDateEndStr())) {
				hql += " and inspectionDate <= '" + queryModel.getInspectionDateEndStr() + " 23:59'";
			}
			if (!TeeUtility.isNullorEmpty(queryModel.getFilingDateStartStr())) {
				hql += " and filingDate >= '"+ queryModel.getFilingDateStartStr() + " 00:00'";
			}
			if (!TeeUtility.isNullorEmpty(queryModel.getFilingDateEndStr())) {
				hql += " and filingDate <= '" + queryModel.getFilingDateEndStr() + " 23:59'";
			}
			if (!(StringUtils.isBlank(queryModel.getStatus()) || "-1".equals(queryModel.getStatus()))) {
				hql += " and status = " + queryModel.getStatus();
			}
			if (!(StringUtils.isBlank(queryModel.getLitigantType()) || "-1".equals(queryModel.getLitigantType()))) {
				hql += " and litigantType = " + queryModel.getLitigantType();
			}
			hql += " and departmentId = " + departmentId;
		}else {
			//执法人员
			// 区分现场检查用条件
			hql += " and isRegister = " + queryModel.getIsRegister();

			if (!TeeUtility.isNullorEmpty(queryModel.getInspectionDateStartStr())) {
				hql += " and inspectionDate >= '"
						+ queryModel.getInspectionDateStartStr() + " 00:00'";
			}
			if (!TeeUtility.isNullorEmpty(queryModel.getInspectionDateEndStr())) {
				hql += " and inspectionDate <= '" + queryModel.getInspectionDateEndStr() + " 23:59'";
			}
			if (!TeeUtility.isNullorEmpty(queryModel.getFilingDateStartStr())) {
				hql += " and filingDate >= '"+ queryModel.getFilingDateStartStr() + " 00:00'";
			}
			if (!TeeUtility.isNullorEmpty(queryModel.getFilingDateEndStr())) {
				hql += " and filingDate <= '" + queryModel.getFilingDateEndStr() + " 23:59'";
			}
			if (!(StringUtils.isBlank(queryModel.getStatus()) || "-1"
					.equals(queryModel.getStatus()))) {
				hql += " and status = " + queryModel.getStatus();
			}
			if (!(StringUtils.isBlank(queryModel.getLitigantType()) || "-1"
					.equals(queryModel.getLitigantType()))) {
				hql += " and litigantType = " + queryModel.getLitigantType();
			}	
			hql += " and departmentId = " + departmentId + " and ( majorPersonId = " + userId +" or minorPersonId = " + userId + " )";
		}
		return super.count(hql, null);// 最后一个参数是条件查询的条件
	}
	
	/**
	 * 案件办理获取全部案件（list）
	 * @param firstResult
	 * @param rows
	 * @param queryModel
	 * @param userId
	 * @return
	 */
	public List<PunishBase> listByPageuserId(int firstResult, int rows, PunishBaseModel queryModel, TeePerson loginUser) {
		//获取登录人员的权限
		String roleName = loginUser.getUserRole().getRoleName();
		int departmentId = loginUser.getDept().getUuid();
		int userId = loginUser.getUuid();
		String hql = "from PunishBase where 1 = 1 ";

		// 区分现场检查和案件办理的条件 （isApply为0 是 案件办理）
		hql += " and isApply = 0 " ;
		
		if("系统管理员".equals(roleName)) {
			if (!TeeUtility.isNullorEmpty(queryModel.getInspectionDateStartStr())) {
				hql += " and inspectionDate >= '"
						+ queryModel.getInspectionDateStartStr() + " 00:00'";
			}
			if (!TeeUtility.isNullorEmpty(queryModel.getInspectionDateEndStr())) {
				hql += " and inspectionDate <= '" + queryModel.getInspectionDateEndStr() + " 23:59'";
			}
			if (!TeeUtility.isNullorEmpty(queryModel.getFilingDateStartStr())) {
				hql += " and filingDate >= '"+ queryModel.getFilingDateStartStr() + " 00:00'";
			}
			if (!TeeUtility.isNullorEmpty(queryModel.getFilingDateEndStr())) {
				hql += " and filingDate <= '" + queryModel.getFilingDateEndStr() + " 23:59'";
			}
			if (!(StringUtils.isBlank(queryModel.getStatus()) || "-1".equals(queryModel.getStatus()))) {
				hql += " and status = " + queryModel.getStatus();
			}
			if (!(StringUtils.isBlank(queryModel.getLitigantType()) || "-1".equals(queryModel.getLitigantType()))) {
				hql += " and litigantType = " + queryModel.getLitigantType();
			}
		}
		
		// 二.部门领导（部门负责人、部门副职）
		else if(("部门负责人".equals(roleName) || "部门副职".equals(roleName))) {
			// 区分现场检查用条件
			hql += " and isRegister = " + queryModel.getIsRegister(); 
			
			if (!TeeUtility.isNullorEmpty(queryModel.getInspectionDateStartStr())) {
				hql += " and inspectionDate >= '"
						+ queryModel.getInspectionDateStartStr() + " 00:00'";
			}
			if (!TeeUtility.isNullorEmpty(queryModel.getInspectionDateEndStr())) {
				hql += " and inspectionDate <= '" + queryModel.getInspectionDateEndStr() + " 23:59'";
			}
			if (!TeeUtility.isNullorEmpty(queryModel.getFilingDateStartStr())) {
				hql += " and filingDate >= '"+ queryModel.getFilingDateStartStr() + " 00:00'";
			}
			if (!TeeUtility.isNullorEmpty(queryModel.getFilingDateEndStr())) {
				hql += " and filingDate <= '" + queryModel.getFilingDateEndStr() + " 23:59'";
			}
			if (!(StringUtils.isBlank(queryModel.getStatus()) || "-1".equals(queryModel.getStatus()))) {
				hql += " and status = " + queryModel.getStatus();
			}
			if (!(StringUtils.isBlank(queryModel.getLitigantType()) || "-1".equals(queryModel.getLitigantType()))) {
				hql += " and litigantType = " + queryModel.getLitigantType();
			}
			hql += " and departmentId = " + departmentId;
		}else {
			//执法人员
			// 区分现场检查用条件
			hql += " and isRegister = " + queryModel.getIsRegister();

			if (!TeeUtility.isNullorEmpty(queryModel.getInspectionDateStartStr())) {
				hql += " and inspectionDate >= '"
						+ queryModel.getInspectionDateStartStr() + " 00:00'";
			}
			if (!TeeUtility.isNullorEmpty(queryModel.getInspectionDateEndStr())) {
				hql += " and inspectionDate <= '" + queryModel.getInspectionDateEndStr() + " 23:59'";
			}
			if (!TeeUtility.isNullorEmpty(queryModel.getFilingDateStartStr())) {
				hql += " and filingDate >= '"+ queryModel.getFilingDateStartStr() + " 00:00'";
			}
			if (!TeeUtility.isNullorEmpty(queryModel.getFilingDateEndStr())) {
				hql += " and filingDate <= '" + queryModel.getFilingDateEndStr() + " 23:59'";
			}
			if (!(StringUtils.isBlank(queryModel.getStatus()) || "-1"
					.equals(queryModel.getStatus()))) {
				hql += " and status = " + queryModel.getStatus();
			}
			if (!(StringUtils.isBlank(queryModel.getLitigantType()) || "-1"
					.equals(queryModel.getLitigantType()))) {
				hql += " and litigantType = " + queryModel.getLitigantType();
			}	
			hql += " and departmentId = " + departmentId + " and ( majorPersonId = " + userId +" or minorPersonId = " + userId + " )";
		}
		hql += "order by(baseCode) desc";
		return super.pageFind(hql, firstResult, rows, null);// 最后一个参数是条件查询的条件
	}
	
	/**
	 * 获取总数(现场检查)
	 * @param queryModel
	 * @param userId
	 * @return
	 */   
	public long getCheckTotal(PunishBaseModel queryModel, TeePerson loginUser){
		//获取登录人员的权限
		String roleName = loginUser.getUserRole().getRoleName();
		int departmentId = loginUser.getDept().getUuid();
		int userId = loginUser.getUuid();
		String hql = "select count(id) from PunishBase where 1 = 1 ";
		
		// 区分申请立案和未申请立案的条件 （isRegister 为1 是 现场检查未申请立案   为0是 现场检查 已申请立案）
		hql += " and isRegister = "+ queryModel.getIsRegister();
		
		// 区分现场检查和案件办理条件     sourceId 为空 或者  sourceId == baseId  时候是现场检查
		//hql += " and (sourceId is null or baseId = sourceId )";
		hql += " and sourceType in ('20', '30') ";
		
		if("系统管理员".equals(roleName)) {
			if (!TeeUtility.isNullorEmpty(queryModel.getInspectionDateStartStr())) {
				hql += " and inspectionDate >= '"
						+ queryModel.getInspectionDateStartStr() + " 00:00'";
			}
			if (!TeeUtility.isNullorEmpty(queryModel.getInspectionDateEndStr())) {
				hql += " and inspectionDate <= '" + queryModel.getInspectionDateEndStr() + " 23:59'";
			}
			if (!(StringUtils.isBlank(queryModel.getStatus()) || "-1".equals(queryModel.getStatus()))) {
				hql += " and status = " + queryModel.getStatus();
			}
			if (!(StringUtils.isBlank(queryModel.getLitigantType()) || "-1".equals(queryModel.getLitigantType()))) {
				hql += " and litigantType = " + queryModel.getLitigantType();
			}
		}
		
		// 二.部门领导（部门负责人、部门副职）
		else if(("部门负责人".equals(roleName) || "部门副职".equals(roleName))) {
			if (!TeeUtility.isNullorEmpty(queryModel.getInspectionDateStartStr())) {
				hql += " and inspectionDate >= '"
						+ queryModel.getInspectionDateStartStr() + " 00:00'";
			}
			if (!TeeUtility.isNullorEmpty(queryModel.getInspectionDateEndStr())) {
				hql += " and inspectionDate <= '" + queryModel.getInspectionDateEndStr() + " 23:59'";
			}
			if (!(StringUtils.isBlank(queryModel.getStatus()) || "-1".equals(queryModel.getStatus()))) {
				hql += " and status = " + queryModel.getStatus();
			}
			if (!(StringUtils.isBlank(queryModel.getLitigantType()) || "-1".equals(queryModel.getLitigantType()))) {
				hql += " and litigantType = " + queryModel.getLitigantType();
			}
			hql += " and chargeDeptId = " + departmentId;
		}else {
			//执法人员
			if (!TeeUtility.isNullorEmpty(queryModel.getInspectionDateStartStr())) {
				hql += " and inspectionDate >= '"
						+ queryModel.getInspectionDateStartStr() + " 00:00'";
			}
			if (!TeeUtility.isNullorEmpty(queryModel.getInspectionDateEndStr())) {
				hql += " and inspectionDate <= '" + queryModel.getInspectionDateEndStr() + " 23:59'";
			}
			if (!(StringUtils.isBlank(queryModel.getStatus()) || "-1".equals(queryModel.getStatus()))) {
				hql += " and status = " + queryModel.getStatus();
			}
			if (!(StringUtils.isBlank(queryModel.getLitigantType()) || "-1".equals(queryModel.getLitigantType()))) {
				hql += " and litigantType = " + queryModel.getLitigantType();
			}
			hql += " and chargePsnId = " + userId + " ";
		}
		hql += " and isUnerror = 0 ";
		return super.count(hql, null);// 最后一个参数是条件查询的条件
	}
	
	/**
	 * 现场检查获取全部案件（list）
	 * @param firstResult
	 * @param rows
	 * @param queryModel
	 * @param userId
	 * @return
	 */
	public List<PunishBase> listByCheckPage(int firstResult, int rows, PunishBaseModel queryModel, TeePerson loginUser) {
		//获取登录人员的权限
		String roleName = loginUser.getUserRole().getRoleName();
		int departmentId = loginUser.getDept().getUuid();
		int userId = loginUser.getUuid();
		String hql = "from PunishBase where 1 = 1 ";
		
		// 区分申请立案和未申请立案的条件 （isRegister 为1 是 现场检查未申请立案   为0是 现场检查 已申请立案）
		hql += " and isRegister = "+ queryModel.getIsRegister();
		
		// 区分现场检查和案件办理条件     sourceId 为空 或者  sourceId == baseId  时候是现场检查
		// hql += " and (sourceId is null or baseId = sourceId )";
		hql += " and sourceType in ('20', '30') ";
		
		if("系统管理员".equals(roleName)) {
			
			if (!TeeUtility.isNullorEmpty(queryModel.getInspectionDateStartStr())) {
				hql += " and inspectionDate >= '"
						+ queryModel.getInspectionDateStartStr() + " 00:00'";
			}
			if (!TeeUtility.isNullorEmpty(queryModel.getInspectionDateEndStr())) {
				hql += " and inspectionDate <= '" + queryModel.getInspectionDateEndStr() + " 23:59'";
			}
			if (!(StringUtils.isBlank(queryModel.getStatus()) || "-1".equals(queryModel.getStatus()))) {
				hql += " and status = " + queryModel.getStatus();
			}
			if (!(StringUtils.isBlank(queryModel.getLitigantType()) || "-1".equals(queryModel.getLitigantType()))) {
				hql += " and litigantType = " + queryModel.getLitigantType();
			}
		}
		
		// 二.部门领导（部门负责人、部门副职）
		else if(("部门负责人".equals(roleName) || "部门副职".equals(roleName))) {
			
			if (!TeeUtility.isNullorEmpty(queryModel.getInspectionDateStartStr())) {
				hql += " and inspectionDate >= '"
						+ queryModel.getInspectionDateStartStr() + " 00:00'";
			}
			if (!TeeUtility.isNullorEmpty(queryModel.getInspectionDateEndStr())) {
				hql += " and inspectionDate <= '" + queryModel.getInspectionDateEndStr() + " 23:59'";
			}
			if (!(StringUtils.isBlank(queryModel.getStatus()) || "-1".equals(queryModel.getStatus()))) {
				hql += " and status = " + queryModel.getStatus();
			}
			if (!(StringUtils.isBlank(queryModel.getLitigantType()) || "-1".equals(queryModel.getLitigantType()))) {
				hql += " and litigantType = " + queryModel.getLitigantType();
			}
			hql += " and chargeDeptId = " + departmentId;
		}else {
			//执法人员
			if (!TeeUtility.isNullorEmpty(queryModel.getInspectionDateStartStr())) {
				hql += " and inspectionDate >= '"
						+ queryModel.getInspectionDateStartStr() + " 00:00'";
			}
			if (!TeeUtility.isNullorEmpty(queryModel.getInspectionDateEndStr())) {
				hql += " and inspectionDate <= '" + queryModel.getInspectionDateEndStr() + " 23:59'";
			}
			if (!(StringUtils.isBlank(queryModel.getStatus()) || "-1".equals(queryModel.getStatus()))) {
				hql += " and status = " + queryModel.getStatus();
			}
			if (!(StringUtils.isBlank(queryModel.getLitigantType()) || "-1".equals(queryModel.getLitigantType()))) {
				hql += " and litigantType = " + queryModel.getLitigantType();
			}
			
			hql += " and chargePsnId = " + userId + " ";
		}
		hql += " and isUnerror = 0 ";
		hql += " order by inspectionDate desc ";
		
		return super.pageFind(hql, firstResult, rows, null);// 最后一个参数是条件查询的条件
	}
	
	/**
	 * 获取总数(听证)
	 * @param queryModel
	 * @param userId
	 * @return
	 */   
	public long getHearingInfoTotal(PunishBaseModel queryModel, TeePerson loginUser) {
		
		String hql = "select count(id) from PunishBase where 1 = 1 ";

		// 是否听证：0.不听证、1.听证
		hql += " and isHearing = " + queryModel.getIsHearing();

		if (!TeeUtility.isNullorEmpty(queryModel.getHearingDateStartStr())) {
			hql += " and hearingDate >= '" + queryModel.getHearingDateStartStr() + "'";
		}
		if (!TeeUtility.isNullorEmpty(queryModel.getHearingDateEndStr())) {
			hql += " and hearingDate <= '" + queryModel.getHearingDateEndStr() + "'";
		}
		if (!(StringUtils.isBlank(queryModel.getLitigantType()) || "-1".equals(queryModel.getLitigantType()))) {
			hql += " and litigantType = " + queryModel.getLitigantType();
		}
		if (!TeeUtility.isNullorEmpty(queryModel.getOrganName()) && !TeeUtility.isNullorEmpty(queryModel.getPsnName())) {
			hql += " and ( OrganName like '%" + queryModel.getOrganName() + "%'";
			hql += " or psnName like '%" + queryModel.getPsnName() + "%' )";
		} else if (!TeeUtility.isNullorEmpty(queryModel.getOrganName())) {
			hql += " and OrganName like '%" + queryModel.getOrganName() + "%'";
		} else if (!TeeUtility.isNullorEmpty(queryModel.getPsnName())) {
			hql += " and psnName like '%" + queryModel.getPsnName() + "%'";
		}
		
		return super.count(hql, null);// 最后一个参数是条件查询的条件
	}
	
	/**
	 * 现场检查获取全部案件（听证）
	 * @param firstResult
	 * @param rows
	 * @param queryModel
	 * @param userId
	 * @return
	 */
	public List<PunishBase> getHearingInfo(int firstResult, int rows, PunishBaseModel queryModel, TeePerson loginUser) {
		String hql = " from PunishBase where 1 = 1 ";

		// 是否听证：0.不听证、1.听证
		hql += " and isHearing = " + queryModel.getIsHearing();

		if (!TeeUtility.isNullorEmpty(queryModel.getHearingDateStartStr())) {
			hql += " and hearingDate >= '" + queryModel.getHearingDateStartStr() + "'";
		}
		if (!TeeUtility.isNullorEmpty(queryModel.getHearingDateEndStr())) {
			hql += " and hearingDate <= '" + queryModel.getHearingDateEndStr() + "'";
		}
		if (!(StringUtils.isBlank(queryModel.getLitigantType()) || "-1".equals(queryModel.getLitigantType()))) {
			hql += " and litigantType = " + queryModel.getLitigantType();
		}
		if (!TeeUtility.isNullorEmpty(queryModel.getOrganName()) && !TeeUtility.isNullorEmpty(queryModel.getPsnName())) {
			hql += " and ( OrganName like '%" + queryModel.getOrganName() + "%'";
			hql += " or psnName like '%" + queryModel.getPsnName() + "%' )";
		} else if (!TeeUtility.isNullorEmpty(queryModel.getOrganName())) {
			hql += " and OrganName like '%" + queryModel.getOrganName() + "%'";
		} else if (!TeeUtility.isNullorEmpty(queryModel.getPsnName())) {
			hql += " and psnName like '%" + queryModel.getPsnName() + "%'";
		}
		return super.pageFind(hql, firstResult, rows, null);// 最后一个参数是条件查询的条件
	}
}
