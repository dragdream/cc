package com.beidasoft.xzzf.power.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.power.bean.BasePower;
import com.beidasoft.xzzf.power.model.PowerSelectModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class PowerSelectDao extends TeeBaseDao<BasePower> {

	/**
	 * 模糊查询分页查询总数
	 * 
	 * @param firstResult
	 * @param rows
	 * @param queryModel
	 * @return
	 */
	public List<BasePower> listByPage(int firstResult, int rows,
			PowerSelectModel queryModel) {

		String hql = "from BasePower  where isDelete = 0 and isStop = 0";

		if (!TeeUtility.isNullorEmpty(queryModel.getCode())) {
			hql += " and code like '%" + queryModel.getCode() + "%' ";
		}

		if (!TeeUtility.isNullorEmpty(queryModel.getName())) {
			hql += "and name like '%" + queryModel.getName() + "%'";
		}

		if (!TeeUtility.isNullorEmpty(queryModel.getPowerType())) {
			hql += " and powerType = '" + queryModel.getPowerType() + "' ";
		}

		if (!TeeUtility.isNullorEmpty(queryModel.getPowerLevel())) {
			hql += " and powerLevel like '%" + queryModel.getPowerLevel()+ "%' ";
		}

		if (!TeeUtility.isNullorEmpty(queryModel.getSubjectLaw())) {
			hql += " and subjectLaw = '" + queryModel.getSubjectLaw() + "' ";
		}
		if (!TeeUtility.isNullorEmpty(queryModel.getSubjectDesc())) {
			hql += " and subjectDesc = '" + queryModel.getSubjectDesc() + "' ";
		}
		if (queryModel.getDepartment() != null
				&& !"".equals(queryModel.getDepartment())) {
			hql += " and department = " + queryModel.getDepartment();
		}
		if (queryModel.getSubjectId() != null
				&& !"".equals(queryModel.getSubjectId())) {
			hql += " and subjectId = " + queryModel.getSubjectId();
		}
		if (!TeeUtility.isNullorEmpty(queryModel.getFlowPictureType())) {
			hql += " and flowPictureType = '" + queryModel.getFlowPictureType()+ "'";
		}
		if (queryModel.getPowerField() != 0) {
			hql += " and powerField = " + queryModel.getPowerField();
		}
		if (queryModel.getIsCriminal() != 0) {
			hql += " and isCriminal = " + queryModel.getIsCriminal();
		}
		if ((!TeeUtility.isNullorEmpty(queryModel.getCreateDateStr()))
				&& (TeeUtility.isNullorEmpty(queryModel.getCreateDateStr()))) {
			hql += " and createDate >= '" + queryModel.getCreateDateStr()
					+ "' ";
		} else if ((!TeeUtility.isNullorEmpty(queryModel.getCreateDateStr()))
				&& (!TeeUtility.isNullorEmpty(queryModel.getCreateDateStr()))) {
			hql += " and createDate between '" + queryModel.getCreateDateStr()
					+ "' and '" + queryModel.getCreateDateStr() + "' ";
		} else if ((TeeUtility.isNullorEmpty(queryModel.getCreateDateStr()))
				&& (!TeeUtility.isNullorEmpty(queryModel.getCreateDateStr()))) {
			hql += " and createDate <= '" + queryModel.getCreateDateStr()+ "' ";
		}

		return super.pageFind(hql + " order by commonlyUsed desc", firstResult, rows, null);
	}

	/**
	 * 返回总记录数
	 * 
	 * @return
	 */
	public long getTotal(String id) {
		String hql = "select count(id) from BasePowerGist  where powerId ='"
				+ id + "'";
		return super.count(hql, null);
	}

	public long getTotal(PowerSelectModel queryModel) {
		String hql = "select count(id) from BasePower  where 1=1 and isDelete !=1 ";

		if (!TeeUtility.isNullorEmpty(queryModel.getCode())) {
			hql += " and code like '%" + queryModel.getCode() + "%' ";
		}

		if (!TeeUtility.isNullorEmpty(queryModel.getName())) {
			hql += "and name like '%" + queryModel.getName() + "%'";
		}

		if (!TeeUtility.isNullorEmpty(queryModel.getPowerType())) {
			hql += " and powerType = '" + queryModel.getPowerType() + "' ";
		}

		if (!TeeUtility.isNullorEmpty(queryModel.getPowerLevel())) {
			hql += " and powerLevel = '" + queryModel.getPowerLevel() + "' ";
		}

		if (!TeeUtility.isNullorEmpty(queryModel.getSubjectLaw())) {
			hql += " and subjectLaw = '" + queryModel.getSubjectLaw() + "' ";
			;
		}
		if (!TeeUtility.isNullorEmpty(queryModel.getSubjectDesc())) {
			hql += " and subjectDesc = '" + queryModel.getSubjectDesc() + "' ";
		}
		if (!TeeUtility.isNullorEmpty(queryModel.getDepartment())) {
			hql += " and department = " + queryModel.getDepartment();
		}
		if (!TeeUtility.isNullorEmpty(queryModel.getSubjectId())) {
			hql += " and subjectId = " + queryModel.getSubjectId();
		}

		if (!TeeUtility.isNullorEmpty(queryModel.getFlowPictureType())) {
			hql += " and flowPictureType = '" + queryModel.getFlowPictureType()+ "'";
		}
		if (queryModel.getPowerField() != 0) {
			hql += " and PowerField = " + queryModel.getPowerField();
		}
		if (queryModel.getIsCriminal() != 0) {
			hql += " and IsCriminal = " + queryModel.getIsCriminal();
		}
		if (!TeeUtility.isNullorEmpty(queryModel.getCreateDateStr())) {
			hql += " and CreateDateStr like '%" + queryModel.getCreateDateStr()+ "%'";
		}
		if (queryModel.getIsDelete() != 0) {
			hql += " and IsDelete = " + queryModel.getIsDelete();
		}
		if (queryModel.getIsStop() != 0) {
			hql += " and IsStop = " + queryModel.getIsStop();
		}
		return super.count(hql, null);
	}

	/**
	 * 修改删除状态 并非真正删除
	 * 
	 * @param power_id
	 */
	public void updateByPowerId(String id) {
		String hql = "UPDATE BasePower set isDelete=1 Where id=?";
		Session session = this.getSession();
		Query q = session.createQuery(hql);
		q.setString(0, id);
		q.executeUpdate();
	}

	/**
	 * 查询所有职权信息
	 * 
	 * @return
	 */
	public List<BasePower> getPowerList() {
		String hql = "from BasePower where powerType='010000' and isDelete ='0' ";
		return super.find(hql, null);
	}
	
}
