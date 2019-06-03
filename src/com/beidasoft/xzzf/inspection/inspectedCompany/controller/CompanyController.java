package com.beidasoft.xzzf.inspection.inspectedCompany.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.inspection.code.service.BaseCodeDetailService;
import com.beidasoft.xzzf.inspection.inspectedCompany.bean.Company;
import com.beidasoft.xzzf.inspection.inspectedCompany.model.CompanyModel;
import com.beidasoft.xzzf.inspection.inspectedCompany.service.CompanyService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/companyCtrl")
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private BaseCodeDetailService codeDetailService;
	
	@ResponseBody
	@RequestMapping("/getCompanies")
	public TeeEasyuiDataGridJson getCompanies(CompanyModel companyModel,TeeDataGridModel dataGridModel) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		
		//通过分页获取检查对象信息数据的List集合
		long total = companyService.getTotal(companyModel);
		
		List<CompanyModel> modelList = new ArrayList<>();
		List<Company> companyList = companyService.getCompanies(companyModel, dataGridModel);
		//获取代码表map
		Map<String, String> mapOrgType = codeDetailService.getCodeMap("ZF_INSPECTION_ORG_TYPE");
		Map<String, String> mapAreaNumber = codeDetailService.getCodeMap("ZF_AREA_NUMBER");
		Map<String, String> mapCreditRating = codeDetailService.getCodeMap("ZF_CREDIT_RATING");
		for (Company company : companyList) {
			CompanyModel companyModelRow = new CompanyModel();
			
			BeanUtils.copyProperties(company, companyModelRow);
			//根据map中key取value
			companyModelRow.setOrgTypeDic(mapOrgType.get(companyModelRow.getOrgTypeDic()));
			companyModelRow.setRegDistrictDic(mapAreaNumber.get(companyModelRow.getRegDistrictDic()));
			companyModelRow.setCreditLevel(mapCreditRating.get(companyModelRow.getCreditLevel()));
			
			modelList.add(companyModelRow);
		}
		dataGridJson.setTotal(total);
		dataGridJson.setRows(modelList);
		
		return dataGridJson;
	}
	
	/**
	 * 保存/更新
	 * 
	 * @param companyModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveCompany")
	public TeeJson saveCompany(CompanyModel companyModel, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		
		Company company = new Company();
		if (StringUtils.isBlank(companyModel.getId())) {
			BeanUtils.copyProperties(companyModel, company);
			company.setId(UUID.randomUUID().toString());
			company.setDelFlg("0");
			company.setCreateDate(Calendar.getInstance().getTime());
		} else {
			company = companyService.getCompany(companyModel.getId());
			BeanUtils.copyProperties(companyModel, company);
		}
		companyService.saveCompany(company);
		json.setRtData(company);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getCompany")
	public TeeJson getCompany(String id) {
		TeeJson json = new TeeJson();
		
		CompanyModel companyModel = new CompanyModel();
		Company company = companyService.getCompany(id);
		
		BeanUtils.copyProperties(company, companyModel);
		
		json.setRtData(companyModel);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delCompany")
	public TeeJson delCompany(String id) {
		TeeJson json = new TeeJson();

		Company company = companyService.getCompany(id);
		if (company != null) {
			company.setDelFlg("1");
			companyService.saveCompany(company);
			json.setRtState(true);
		} else {
			json.setRtState(false);
		}
		
		return json;
	}
	
}
