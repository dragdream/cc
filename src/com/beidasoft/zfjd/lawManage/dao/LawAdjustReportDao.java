package com.beidasoft.zfjd.lawManage.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.lawManage.bean.LawAdjustReport;
import com.beidasoft.zfjd.lawManage.model.LawAdjustReportModel;
import com.tianee.webframe.dao.TeeBaseDao;

/**
 * 法律法规调整DAO类
 */
@Repository
public class LawAdjustReportDao extends TeeBaseDao<LawAdjustReport> {

	/**
	 * 分页获取符合条件的数据(对填报申请)
	 *
	 * @param lawAdjustReportModel
	 * @return List<LawAdjustReport> 数据list集合
	 */
	public List<LawAdjustReport> listByPage(int firstResult, int rows, LawAdjustReportModel lawAdjustReportModel,
			OrgCtrlInfoModel orgCtrlInfoModel) {
		String hql = "from LawAdjustReport where isDelete = 0 ";
		//其他数据条件
		if(lawAdjustReportModel.getName() != null && !"".equals(lawAdjustReportModel.getName())){
			hql = hql + "and name like '%" + lawAdjustReportModel.getName() + "%'";
		}
		if(lawAdjustReportModel.getSubmitlawLevel() != null && !"".equals(lawAdjustReportModel.getSubmitlawLevel())){
			hql = hql + "and submitlawLevel = '" + lawAdjustReportModel.getSubmitlawLevel() + "'";
		}
		
		//是否具有数据权限
		if (orgCtrlInfoModel != null) {
			Integer orgType = orgCtrlInfoModel.getOrgType();
			if (orgType != null) {
				if(orgType == 1){
					//监督部门
					hql = hql + "and createSupDeptId ='" + orgCtrlInfoModel.getSupDeptId() + "'";
				}else if(orgType == 2){
					//执法部门
					hql = hql + "and createDeptId ='" + orgCtrlInfoModel.getDepartId() + "'";
				}else if(orgType == 3){
					//执法主体
					hql = hql + "and createSubjectId ='" + orgCtrlInfoModel.getSubjectId() + "'";
				}else{
					return null;
				}
			}else{
				return null;
			}
		}else{
			return null;
		}
		hql = hql + " order by createDate";
		return pageFind(hql, firstResult, rows, null);
	}

	/**
	 * 保存法律法规调整数据(对填报申请)
	 *
	 * @param lawAdjustReportModel
	 * @return long 数量
	 */
	public long getTotal(LawAdjustReportModel lawAdjustReportModel, OrgCtrlInfoModel orgCtrlInfoModel) {
		String hql = "select count(id) from LawAdjustReport where isDelete = 0";
		//其他数据条件
		if(lawAdjustReportModel.getName() != null && !"".equals(lawAdjustReportModel.getName())){
			hql = hql + "and name like '%" + lawAdjustReportModel.getName() + "%'";
		}
		if(lawAdjustReportModel.getSubmitlawLevel() != null && !"".equals(lawAdjustReportModel.getSubmitlawLevel())){
			hql = hql + "and submitlawLevel = '" + lawAdjustReportModel.getSubmitlawLevel() + "'";
		}
		//是否具有数据权限
		if (orgCtrlInfoModel != null) {
			Integer orgType = orgCtrlInfoModel.getOrgType();
			if (orgType != null) {
				if(orgType == 1){
					//监督部门
					hql = hql + "and createSupDeptId ='" + orgCtrlInfoModel.getSupDeptId() + "'";
				}else if(orgType == 2){
					//执法部门
					hql = hql + "and createDeptId ='" + orgCtrlInfoModel.getDepartId() + "'";
				}else if(orgType == 3){
					//执法主体
					hql = hql + "and createSubjectId ='" + orgCtrlInfoModel.getSubjectId() + "'";
				}else{
					return 0L;
				}
			}else{
				return 0L;
			}
		}else{
			return 0L;
		}
		return super.count(hql, null);
	}
	
	/**
	 * 保存法律法规调整数据
	 *
	 * @param LawAdjustReport
	 * @return void
	 */
	public void updateLawReportToSubmit(LawAdjustReport lawAdjustReport){
		String hql = "update LawAdjustReport set submitDate =? ";
        hql = hql + ", examine = '" + lawAdjustReport.getExamine() + "' ";
        hql = hql + "where id = '" + lawAdjustReport.getId() + "' ";
        System.out.println(hql);
        Object[] fObject = new Object[1];
        fObject[0] = lawAdjustReport.getSubmitDate();
        deleteOrUpdateByQuery(hql, fObject);
	}
	
	/**
	 * 更新法律法规调整数据
	 *
	 * @param LawAdjustReport
	 * @return void
	 */
	public void updateReportInfo(LawAdjustReport lawAdjustReport){
        Map<String, Object> params = new HashMap<>();
        // *申请调整类型
        params.put("controlType", lawAdjustReport.getControlType());
        // *法律名称
        params.put("name", lawAdjustReport.getName());
        // 发文字号
        params.put("word", lawAdjustReport.getWord());
        // 发布机关
        params.put("organ", lawAdjustReport.getOrgan());
        // 法律类别
        params.put("submitLawLevel", lawAdjustReport.getSubmitlawLevel());
        // 时效性
        params.put("timeliness", lawAdjustReport.getTimeliness());
        // 颁布日期
        params.put("promulgation", lawAdjustReport.getPromulgation());
        // 实施日期
        params.put("implementation", lawAdjustReport.getImplementation());
        // 内容
        params.put("remark", lawAdjustReport.getRemark());
        // 调整法律id
        params.put("updateLawId", lawAdjustReport.getUpdateLawId());
        // 重置是否退回标识
        params.put("isBack", 0);
        // 重置是否退回原因
        params.put("backReason", "");
        // 重置退回日期
        params.put("backDate", "");
        update(params, lawAdjustReport.getId());
	}
	
	/**
	 * 分页获取符合条件的数据(对审核)
	 *
	 * @param lawAdjustReportModel
	 * @return List<LawAdjustReport> 数据list集合
	 */
	public List<LawAdjustReport> examineListByPage(int firstResult, int rows, LawAdjustReportModel lawAdjustReportModel,
			OrgCtrlInfoModel orgCtrlInfoModel) {
		String hql = "from LawAdjustReport where isDelete = 0 and examine > 1 ";
		//其他数据条件
		if(lawAdjustReportModel.getName() != null && !"".equals(lawAdjustReportModel.getName())){
			hql = hql + "and name like '%" + lawAdjustReportModel.getName() + "%'";
		}
		if(lawAdjustReportModel.getSubmitlawLevel() != null && !"".equals(lawAdjustReportModel.getSubmitlawLevel())){
			hql = hql + "and submitlawLevel = '" + lawAdjustReportModel.getSubmitlawLevel() + "'";
		}
		//是否具有数据权限

		hql = hql + " order by examine asc,submitDate desc";
		return pageFind(hql, firstResult, rows, null);
	}

	/**
	 * 保存法律法规调整数据(对审核)
	 *
	 * @param lawAdjustReportModel
	 * @return long 数量
	 */
	public long getExamineTotal(LawAdjustReportModel lawAdjustReportModel, OrgCtrlInfoModel orgCtrlInfoModel) {
		String hql = "select count(id) from LawAdjustReport where isDelete = 0 and examine > 1 ";
		//其他数据条件
		if(lawAdjustReportModel.getName() != null && !"".equals(lawAdjustReportModel.getName())){
			hql = hql + "and name like '%" + lawAdjustReportModel.getName() + "%'";
		}
		if(lawAdjustReportModel.getSubmitlawLevel() != null && !"".equals(lawAdjustReportModel.getSubmitlawLevel())){
			hql = hql + "and submitlawLevel = '" + lawAdjustReportModel.getSubmitlawLevel() + "'";
		}
		//是否具有数据权限
//		if (orgCtrlInfoModel != null) {
//			Integer orgType = orgCtrlInfoModel.getOrgType();
//			if (orgType != null) {
//				if(orgType == 1){
//					//监督部门
//					hql = hql + "and createSupDeptId ='" + orgCtrlInfoModel.getSupDeptId() + "'";
//				}else if(orgType == 2){
//					//执法部门
//					hql = hql + "and createDeptId ='" + orgCtrlInfoModel.getDepartId() + "'";
//				}else if(orgType == 3){
//					//执法主体
//					hql = hql + "and createSubjectId ='" + orgCtrlInfoModel.getSubjectId() + "'";
//				}else{
//					return 0L;
//				}
//			}else{
//				return 0L;
//			}
//		}else{
//			return 0L;
//		}
		return super.count(hql, null);
	}
	
	/**
	 * 更新法律法规调整数据
	 *
	 * @param LawAdjustReport
	 * @return void
	 */
	public void updateExamineById(LawAdjustReportModel lawAdjustReportModel){
        Map<String, Object> params = new HashMap<>();
        // *审核标记
        params.put("examine", lawAdjustReportModel.getExamine());
        //是否退回
        if(lawAdjustReportModel.getExamine().equals(1)){
        	params.put("isBack", 1);
        }
        update(params, lawAdjustReportModel.getId());
	}
}
