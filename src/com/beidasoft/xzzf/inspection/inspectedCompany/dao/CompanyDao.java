package com.beidasoft.xzzf.inspection.inspectedCompany.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.inspection.inspectedCompany.bean.Company;
import com.beidasoft.xzzf.inspection.inspectedCompany.model.CompanyModel;
import com.beidasoft.xzzf.inspection.inspectors.bean.Inspectors;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class CompanyDao extends TeeBaseDao<Company> {
	
	/**
	 * 分页条件查询检查对象
	 * @param companyModel
	 * @param dataGridModel
	 * @return
	 */
	public List<Company> getCompany(CompanyModel companyModel,TeeDataGridModel dataGridModel) {
		String hql = " from Company Where 1=1 and delFlg = '0'";
		if (!TeeUtility.isNullorEmpty(companyModel.getOrgTypeDic())) {
			hql += " and orgTypeDic='" + companyModel.getOrgTypeDic() + "'";
		}
		if (!TeeUtility.isNullorEmpty(companyModel.getFieldType())) {
			hql += " and fieldType='" + companyModel.getFieldType() + "'";
		}
		if (!TeeUtility.isNullorEmpty(companyModel.getCreditLevel())) {
			hql += " and creditLevel='" + companyModel.getCreditLevel() + "'";
		}
		if (!TeeUtility.isNullorEmpty(companyModel.getRegDistrictDic())) {
			hql += " and regDistrictDic='" + companyModel.getRegDistrictDic() + "'";
		}
		if (!TeeUtility.isNullorEmpty(companyModel.getOrgName())) {
			hql += " and orgName like '%" + companyModel.getOrgName() + "%'";
		}
		
		List<Company> companies = super.pageFind(hql, dataGridModel.getFirstResult(),dataGridModel.getRows(), null);
		return companies;
	}
	
	/**
	 * 条件查询检查对象
	 * @param companyModel
	 * @return
	 */
	public List<Company> getCompany(CompanyModel companyModel) {
		String hql = " from Company Where 1=1 ";
		if (!TeeUtility.isNullorEmpty(companyModel.getOrgTypeDic())) {
			hql += " and orgTypeDic='" + companyModel.getOrgTypeDic() + "'";
		}
		if (!TeeUtility.isNullorEmpty(companyModel.getFieldType())) {
			hql += " and fieldType='" + companyModel.getFieldType() + "'";
		}
		if (!TeeUtility.isNullorEmpty(companyModel.getCreditLevel())) {
			hql += " and creditLevel='" + companyModel.getCreditLevel() + "'";
		}
		if (!TeeUtility.isNullorEmpty(companyModel.getRegDistrictDic())) {
			hql += " and regDistrictDic='" + companyModel.getRegDistrictDic() + "'";
		}
		List<Company> companies = super.find(hql, null);
		return companies;
	}
	
	/**
	 * 条件查询检查对象（多个企业类型）
	 * @param companyModel
	 * @return
	 */
	public List<Company> getCompaniesByMultiType(CompanyModel companyModel) {
		String hql = " from Company Where 1=1 ";
		if ((!TeeUtility.isNullorEmpty(companyModel.getOrgTypeDic()))&&!("0".equals(companyModel.getOrgTypeDic()))) {
			hql += " and orgTypeDic in(" + companyModel.getOrgTypeDic() + ")";
		}
		if (!TeeUtility.isNullorEmpty(companyModel.getCreditLevel())) {
			hql += " and creditLevel='" + companyModel.getCreditLevel() + "'";
		}
		if ((!TeeUtility.isNullorEmpty(companyModel.getRegDistrictDic()))&&companyModel.getRegDistrictDic()!="110100") {
			hql += " and regDistrictDic='" + companyModel.getRegDistrictDic() + "'";
		}
		List<Company> companies = super.find(hql, null);
		return companies;
	}
	
	/**
	 * 根据检查对象code查询
	 * @param orgCode
	 * @return
	 */
	public Company getByCode(String orgCode) {
		String hql = "from Company Where orgCode='" + orgCode+ "' ";
		List<Company> list= super.find(hql, null);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 返回总记录数
	 * 
	 * @return
	 */
	public long getTotal() {
		return super.count("select count(id) from Company", null);
	}

	/**
	 * 返回具备条件记录数
	 * @param companyModel
	 * @return
	 */
	public long getTotal(CompanyModel companyModel) {
		String hql = "select count(id) from Company where 1=1 and delFlg = '0'";

		if (!TeeUtility.isNullorEmpty(companyModel.getOrgTypeDic())) {
			hql += " and orgTypeDic = '" + companyModel.getOrgTypeDic() + "'";
		}
		if (!TeeUtility.isNullorEmpty(companyModel.getFieldType())) {
			hql += " and fieldType='" + companyModel.getFieldType() + "'";
		}
		if (!TeeUtility.isNullorEmpty(companyModel.getCreditLevel())) {
			hql += " and creditLevel = '" + companyModel.getCreditLevel() + "'";
		}
		if (!TeeUtility.isNullorEmpty(companyModel.getRegDistrictDic())) {
			hql += " and regDistrictDic = '" + companyModel.getRegDistrictDic() + "'";
		}
		if (!TeeUtility.isNullorEmpty(companyModel.getOrgName())) {
			hql += " and orgName like '%" + companyModel.getOrgName() + "%'";
		}

		return super.count(hql, null);
	}
}
