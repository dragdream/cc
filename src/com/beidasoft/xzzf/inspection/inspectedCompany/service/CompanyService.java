package com.beidasoft.xzzf.inspection.inspectedCompany.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.inspection.inspectedCompany.bean.Company;
import com.beidasoft.xzzf.inspection.inspectedCompany.dao.CompanyDao;
import com.beidasoft.xzzf.inspection.inspectedCompany.model.CompanyModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class CompanyService extends TeeBaseService {
	
	@Autowired
	private CompanyDao companyDao;
	
	/**
	 * 条件分页查询检查对象
	 * @param companyModel
	 * @param dataGridModel
	 * @return
	 */
	public List<Company> getCompanies (CompanyModel companyModel,TeeDataGridModel dataGridModel) {
		return companyDao.getCompany(companyModel, dataGridModel);
	}
	
	/**
	 * 条件查询检查对象
	 * @param companyModel
	 * @return
	 */
	public List<Company> getCompanies (CompanyModel companyModel) {
		return companyDao.getCompany(companyModel);
	}
	
	/**
	 * 条件查询检查对象（根据多个类型）
	 * @param companyModel
	 * @return
	 */
	public List<Company> getCompaniesByMultiType (CompanyModel companyModel) {
		return companyDao.getCompaniesByMultiType(companyModel);
	}
	
	/**
	 * 根据检查对象id查询
	 * @param id
	 * @return
	 */
	public Company getById(String id) {
		return companyDao.get(id);
	}
	
	/**
	 * 根据检查对象code查询
	 * @param orgCode
	 * @return
	 */
	public Company getByCode(String orgCode) {
		return companyDao.getByCode(orgCode);
	}
	
	/**
	 * 返回总记录数
	 * 
	 * @return
	 */
	public long getTotal() {
		return companyDao.getTotal();
	}

	/**
	 * 返回符合条件记录数
	 * 
	 * @return
	 */
	public long getTotal(CompanyModel companyModel) {
		return companyDao.getTotal(companyModel);
	}

	/**
	 * 保存
	 * 
	 * @param company
	 */
	public void saveCompany(Company company) {
		companyDao.saveOrUpdate(company);
	}

	/**
	 * 获取
	 * 
	 * @param id
	 * @return
	 */
	public Company getCompany(String id) {
		return companyDao.get(id);
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void delCompany(String id) {
		companyDao.delete(id);
	}

}
