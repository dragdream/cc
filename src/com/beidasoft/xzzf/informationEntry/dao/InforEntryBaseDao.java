package com.beidasoft.xzzf.informationEntry.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.informationEntry.bean.InforEntryBase;
import com.beidasoft.xzzf.informationEntry.model.InforEntryBaseModel;
import com.beidasoft.xzzf.inspection.inspectedCompany.bean.Company;
import com.beidasoft.xzzf.inspection.inspectedCompany.model.CompanyModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 历史信息录入主表DAO类
 */
@Repository
public class InforEntryBaseDao extends TeeBaseDao<InforEntryBase> {
	
	/**
	 * 分页条件查询检查对象
	 * @param InforEntryBaseModel
	 * @param dataGridModel
	 * @return
	 */	
	public List<InforEntryBase> listByPage(InforEntryBaseModel baseModel,TeeDataGridModel dataGridModel,HttpServletRequest request) {
		StringBuffer hql = new StringBuffer();
		
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		hql.append(" from InforEntryBase Where 1=1 and isDelete = 0 ");
		hql.append(" and createDept = '"+loginPerson.getDept().getUuid()+"' ");
		if (!TeeUtility.isNullorEmpty(baseModel.getBaseName())) {
			hql.append(" and baseName like '%" + baseModel.getBaseName() + "%'");
		}
		if (!TeeUtility.isNullorEmpty(baseModel.getBaseSymbol())) {
			hql.append(" and baseSymbol like '%" + baseModel.getBaseSymbol() + "%'");
		}
		if (!TeeUtility.isNullorEmpty(baseModel.getPartyName())) {
			hql.append(" and partyName like '%" + baseModel.getPartyName() + "%'");
		}
		if (!"-1".equals(baseModel.getCaseSource()) && !TeeUtility.isNullorEmpty(baseModel.getCaseSource())) {
			hql.append(" and caseSource = '" + baseModel.getCaseSource() + "'");
		}
		List<InforEntryBase> inforEntryBase = super.pageFind(hql.toString(), dataGridModel.getFirstResult(),dataGridModel.getRows(), null);
		return inforEntryBase;
	}
	
	/**
	 * 返回具备条件记录数
	 * @param InforEntryBaseModel
	 * @return
	 */
	public long getTotal(InforEntryBaseModel baseModel,HttpServletRequest request) {
		StringBuffer hql = new StringBuffer();
		
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		hql.append("select count(id) from InforEntryBase where 1=1 and isDelete = 0");
		hql.append(" and createDept = '"+loginPerson.getDept().getUuid()+"' ");
		if (!TeeUtility.isNullorEmpty(baseModel.getBaseName())) {
			hql.append(" and baseName like '%" + baseModel.getBaseName() + "%'");
		}
		if (!TeeUtility.isNullorEmpty(baseModel.getBaseSymbol())) {
			hql.append(" and baseSymbol like '%" + baseModel.getBaseSymbol() + "%'");
		}
		if (!TeeUtility.isNullorEmpty(baseModel.getPartyName())) {
			hql.append(" and partyName like '%" + baseModel.getPartyName() + "%'");
		}
		if (!"-1".equals(baseModel.getCaseSource()) && !TeeUtility.isNullorEmpty(baseModel.getCaseSource())) {
			hql.append(" and caseSource = '" + baseModel.getCaseSource() + "'");
		}
		return super.count(hql.toString(), null);
	}
	
}
