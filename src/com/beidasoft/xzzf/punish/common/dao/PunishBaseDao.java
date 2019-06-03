package com.beidasoft.xzzf.punish.common.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.punish.common.bean.PunishBase;
import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.model.PunishBaseModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class PunishBaseDao extends TeeBaseDao<PunishBase> {

	public List<PunishBase> getPunishBaseInfo(int firstResult, int rows, int sid) {
		String hql = "from PunishBase";
		return super.pageFind(hql, firstResult, rows, null);
	}

	/**
	 * 根据taskRecId查询案件基础表信息
	 * 
	 * @param taskRecId
	 * @return
	 */
	public PunishBase getBytaskRecId(String taskRecId) {
		PunishBase punishBase = new PunishBase();
		Session session = this.getSession();
		String sql = "from PunishBase where sourceId=:sourceId";
		punishBase = (PunishBase) session.createQuery(sql)
				.setString("sourceId", taskRecId).uniqueResult();
		return punishBase;
	}

	/**
	 * 执法文书案件目录序号排序及baseid
	 */
	public List<PunishBase> getManageResp(String baseId) {
		String hql = " from PunishBase where 1=1 ";
		hql += " and baseId= '" + baseId + "' ";
		return super.find(hql, null);
	}

	/**
	 * 返回符合条件记录
	 * 
	 * @param punishBaseModel
	 * @param dataGridModel
	 * @return
	 */
	public List<PunishBase> getPunishBases(PunishBaseModel punishBaseModel,
			TeeDataGridModel dataGridModel) {
		String hql = " from PunishBase Where punishFlg='1' ";
		if (!TeeUtility.isNullorEmpty(punishBaseModel.getBaseCode())) {
			hql += " and baseCode like '%" + punishBaseModel.getBaseCode()
					+ "%'";
		}
		if (!TeeUtility.isNullorEmpty(punishBaseModel.getLitigantType())) {
			hql += " and litigantType='" + punishBaseModel.getLitigantType()
					+ "'";
		}
		if (!TeeUtility.isNullorEmpty(punishBaseModel.getSourceType())) {
			hql += " and sourceType='" + punishBaseModel.getSourceType() + "'";
		}
		if (!TeeUtility.isNullorEmpty(punishBaseModel.getBorrowingFlg())) {
			hql += " and borrowingFlg='" + punishBaseModel.getBorrowingFlg()
					+ "'";
		}
		if (!TeeUtility.isNullorEmpty(punishBaseModel.getDepartmentName())) {
			hql += " and departmentName='"
					+ punishBaseModel.getDepartmentName() + "'";
		}
		if (!TeeUtility.isNullorEmpty(punishBaseModel.getPunishType())) {
			hql += " and punishType='"
					+ punishBaseModel.getPunishType() + "'";
		}
		List<PunishBase> punishBases = super.pageFind(hql,
				dataGridModel.getFirstResult(), dataGridModel.getRows(), null);
		return punishBases;
	}

	public List<PunishBase> getTransPunish(PunishBaseModel punishBaseModel,
			TeeDataGridModel dataGridModel) {
		String hql = " from PunishBase Where isClosed=1 ";

		if (!TeeUtility.isNullorEmpty(punishBaseModel.getLitigantType())) {
			hql += " and litigantType='" + punishBaseModel.getLitigantType()
					+ "'";
		}
		if (!TeeUtility.isNullorEmpty(punishBaseModel.getSourceType())) {
			hql += " and sourceType='" + punishBaseModel.getSourceType() + "'";
		}
		List<PunishBase> punishBases = super.pageFind(hql,
				dataGridModel.getFirstResult(), dataGridModel.getRows(), null);
		return punishBases;
	}

	/**
	 * 返回总记录数
	 * 
	 * @return
	 */
	public long getTotal() {
		return super.count("select count(baseId) from PunishBase", null);
	}

	/**
	 * 返回具备条件记录数
	 * 
	 * @param companyModel
	 * @return
	 */
	public long getTotal(PunishBaseModel punishBaseModel) {
		String hql = "select count(baseId) from PunishBase where punishFlg='1' ";

		if (!TeeUtility.isNullorEmpty(punishBaseModel.getBaseCode())) {
			hql += " and baseCode like '%" + punishBaseModel.getBaseCode()
					+ "%'";
		}
		if (!TeeUtility.isNullorEmpty(punishBaseModel.getLitigantType())) {
			hql += " and litigantType='" + punishBaseModel.getLitigantType()
					+ "'";
		}
		if (!TeeUtility.isNullorEmpty(punishBaseModel.getSourceType())) {
			hql += " and sourceType='" + punishBaseModel.getSourceType() + "'";
		}
		if (!TeeUtility.isNullorEmpty(punishBaseModel.getBorrowingFlg())) {
			hql += " and borrowingFlg='" + punishBaseModel.getBorrowingFlg()
					+ "'";
		}
		if (!TeeUtility.isNullorEmpty(punishBaseModel.getDepartmentName())) {
			hql += " and departmentName='"
					+ punishBaseModel.getDepartmentName() + "'";
		}

		return super.count(hql, null);
	}

	public PunishBase getPunishBaseById(String baseId) {
		return super.get(baseId);
	}

	/**
	 * 返回具备条件记录数
	 * 
	 * @param companyModel
	 * @return
	 */
	public long getTransTotal(PunishBaseModel punishBaseModel) {
		String hql = "select count(baseId) from PunishBase where isClosed=1 ";
		if (!TeeUtility.isNullorEmpty(punishBaseModel.getLitigantType())) {
			hql += " and litigantType='" + punishBaseModel.getLitigantType()
					+ "'";
		}
		if (!TeeUtility.isNullorEmpty(punishBaseModel.getSourceType())) {
			hql += " and sourceType='" + punishBaseModel.getSourceType() + "'";
		}
		return super.count(hql, null);
	}

	/**
	 * 执法文书案件目录序号aseid
	 */
	public List<PunishBase> getManageNum(String baseId) {
		String hql = " from PunishBase where 1=1 ";
		hql += " and baseId= '" + baseId + "' ";
		return super.find(hql, null);
	}

	/**
	 * 通过sourceId 获取案件list
	 * 
	 * @param sourceId
	 * @return
	 */
	public List<PunishBase> getBaseBySourceId(String sourceId) {
		String hql = " from PunishBase where 1=1 and sourceId='" + sourceId
				+ "' ";
		return super.find(hql, null);
	}

	/**
	 * 首页获取任务接收列表
	 */
	public List<PunishBase> listByPage(int firstResult, int rows,PunishBaseModel queryModel, HttpServletRequest request) {
		// 拿到登陆人信息
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		String hql = " from PunishBase where isClosed = 0 and isApply = 0 and isHearing = 0 and isUnerror = 0 ";
		hql += " and (majorPersonId ="+loginPerson.getUuid()+" or minorPersonId ="+loginPerson.getUuid()+") and receiveDate is null ";
		hql += "order by filingDate desc";
		return super.pageFind(hql, firstResult, rows, null);
	}
	
	/**
	 * 首页获取任务接收列表总数
	 */
	public long count(PunishBaseModel queryModel, HttpServletRequest request) {
		// 拿到登陆人信息
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String hql = "select count(baseId) from PunishBase where isClosed = 0 and isApply = 0 and isHearing = 0 and isUnerror = 0 ";
		hql += " and (majorPersonId ="+loginPerson.getUuid()+" or minorPersonId ="+loginPerson.getUuid()+") and receiveDate is null ";
		return super.count(hql, null);
	}
	
	
	/**
	 * 首页获取案件办理列表
	 */
	public List<PunishBase> listByHandling(int firstResult, int rows,PunishBaseModel queryModel, HttpServletRequest request) {
		// 拿到登陆人信息
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String hql = " from PunishBase where isClosed = 0 and isApply = 0 and isHearing = 0 and isUnerror = 0 ";
		hql += " and majorPersonId ="+loginPerson.getUuid()+" and receiveDate is not null ";
		hql += "order by receiveDate desc";
		return super.pageFind(hql, firstResult, rows, null);
	}
	
	
	/**
	 * 首页获取任务接收列表总数
	 */
	public long countByHandling(PunishBaseModel queryModel, HttpServletRequest request) {
		// 拿到登陆人信息
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String hql = "select count(baseId) from PunishBase where isClosed = 0 and isApply = 0 and isHearing = 0 and isUnerror = 0 ";
		hql += " and majorPersonId ="+loginPerson.getUuid()+" and receiveDate is not null ";
		return super.count(hql, null);
	}
}