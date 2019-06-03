package com.beidasoft.xzzf.inspection.plan.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.beidasoft.xzzf.inspection.code.service.BaseCodeDetailService;
import com.beidasoft.xzzf.inspection.inspectedCompany.bean.Company;
import com.beidasoft.xzzf.inspection.inspectedCompany.model.CompanyModel;
import com.beidasoft.xzzf.inspection.inspectedCompany.service.CompanyService;
import com.beidasoft.xzzf.inspection.plan.bean.InspectionCondition;
import com.beidasoft.xzzf.inspection.plan.bean.InspectionOrg;
import com.beidasoft.xzzf.inspection.plan.bean.InspectionStaff;
import com.beidasoft.xzzf.inspection.plan.bean.InspectionWeight;
import com.beidasoft.xzzf.inspection.plan.dao.InspectionOrgDao;
import com.beidasoft.xzzf.inspection.plan.dao.InspectionTaskDao;
import com.beidasoft.xzzf.inspection.plan.model.InspectionConditionModel;
import com.beidasoft.xzzf.inspection.plan.model.InspectionOrgModel;
import com.beidasoft.xzzf.inspection.weightRandom.WeightCategory;
import com.beidasoft.xzzf.inspection.weightRandom.WeightRandom;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.data.TeeDataRecord;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.file.TeeCSVUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class InspectionOrgService extends TeeBaseService{
	@Autowired
	private InspectionOrgDao inspectionOrgDao;
	@Autowired
	private InspectionWeightService inspectionWeightService;
	@Autowired
	private InspectionConditionService inspectionConditionService;
	@Autowired
	private BaseCodeDetailService baseCodeDetailService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private InspectionTaskDao inspectionTaskDao;
	/**
	 * 根据检查计划主表id 查询所有筛选条件
	 * @param inspectionId
	 * @return
	 */
	public TeeJson findAllCondition(String inspectionId){
		TeeJson json = new TeeJson();
		//获取该计划信用等级权重
		InspectionWeight weightObj = inspectionWeightService.getObjByInsId(inspectionId);
		//获取该计划附加条件
		List<InspectionCondition> conditionList = inspectionConditionService.getListByInsId(inspectionId);
		//附加条件Model赋值
		List<InspectionConditionModel> conditionModelList = new ArrayList<InspectionConditionModel>();
		//企业类型
		Map<String, String> conditionCode = baseCodeDetailService.getCodeMap("ZF_INSPECTION_ORG_TYPE");
		//所在城区
		Map<String, String> RegionCode = baseCodeDetailService.getCodeMap("ZF_AREA_NUMBER");
		for (InspectionCondition inspectionCondition : conditionList) {
			InspectionConditionModel conditionModel = new InspectionConditionModel();
			BeanUtils.copyProperties(inspectionCondition, conditionModel);
			String typeCodeStr [] = inspectionCondition.getConditionType().split(",");
			String typeCode = "";
			for (int i = 0; i < typeCodeStr.length; i++) {
				typeCode += conditionCode.get(TeeStringUtil.getString(typeCodeStr[i]));
				if(i < typeCodeStr.length-1){
					typeCode += ",";
				}
			}
			conditionModel.setConditionTypeName(typeCode);
			String typeNameStr [] = inspectionCondition.getConditionType().split(",");
			String typeName = "";
			for (int i = 0; i < typeNameStr.length; i++) {
				typeName += conditionCode.get(TeeStringUtil.getString(typeNameStr[i]));
				if(i < typeNameStr.length-1){
					typeName += ",";
				}
			}
			conditionModel.setConditionTypeName(typeName);
			conditionModel.setConditionRegionName(RegionCode.get(TeeStringUtil.getString(inspectionCondition.getConditionRegion())));
			conditionModelList.add(conditionModel);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("weightObj", weightObj);
		map.put("conditionList", conditionModelList);
		if(TeeUtility.isNullorEmpty(weightObj)&&conditionModelList.isEmpty()){
			json.setRtState(false);
		}else{
			json.setRtData(map);
			json.setRtState(true);
		}
		return json;
	}

	/**
	 * 分页查询被检企业列表
	 * @param inspectionWeight
	 * @param conditionJson
	 * @param areaJson
	 * @return
	 */
	public TeeEasyuiDataGridJson orgListByPage(String inspectionId,int firstResult,int rows){
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		long total = inspectionOrgDao.getTotal(inspectionId);
		//企业类型代码表
		Map<String, String> conditionCode = baseCodeDetailService.getCodeMap("ZF_INSPECTION_ORG_TYPE");
		//所在城区代码表
		Map<String, String> RegionCode = baseCodeDetailService.getCodeMap("ZF_AREA_NUMBER");
		//信用等级代码表
		Map<String, String> creditCode = baseCodeDetailService.getCodeMap("ZF_CREDIT_RATING");
		List<InspectionOrgModel> modelList = new ArrayList<InspectionOrgModel>();
		List<InspectionOrg> list = inspectionOrgDao.getListByInspectionId(firstResult, rows, inspectionId);
		for (InspectionOrg inspectionOrg : list) {
			InspectionOrgModel model = new InspectionOrgModel();
			Company company = companyService.getById(inspectionOrg.getOrgId());
			BeanUtils.copyProperties(inspectionOrg, model);
			String[] types = inspectionOrg.getOrgType().split(",");
			StringBuffer typeName = new StringBuffer();
			for (String string : types) {
				typeName.append(conditionCode.get(string));
			}
			model.setOrgTypeName(typeName.toString());
			model.setCreditLevel(creditCode.get(company.getCreditLevel()));
			model.setRegDistrict(RegionCode.get(company.getRegDistrictDic()));
			modelList.add(model);
		}
		json.setTotal(total);
		json.setRows(modelList);
		return json;
	}
	
	/**
	 * 随机生成被检企业列表
	 * @param inspectionId
	 * @return
	 */
	private TeeJson screeningOrg(String inspectionId){
		inspectionTaskDao.delListByInsId(inspectionId);//别检查企业变动时删除后续计划
		TeeJson json = new TeeJson();
		//根据各色权重计算出各色企业总数集合
		Map<String, Integer> weightTotalMap = getWeightTotalList(inspectionId);
		List<InspectionCondition> conditionList = inspectionConditionService.getListByInsId(inspectionId);
		for (InspectionCondition inspectionCondition : conditionList) {
			if (0 == inspectionCondition.getConditionCredit()) {
				//如果信用等级为0，则三种信用等级同时随机
				Set<InspectionOrg> blueSet = getOrgListByCondition(inspectionCondition,"10",weightTotalMap.get("blueTotal"));
				weightTotalMap.put("blueTotal",weightTotalMap.get("blueTotal")-blueSet.size());
				Set<InspectionOrg> yellowSet = getOrgListByCondition(inspectionCondition,"20",weightTotalMap.get("yellowTotal"));
				weightTotalMap.put("yellowTotal",weightTotalMap.get("yellowTotal")-yellowSet.size());
				Set<InspectionOrg> redSet = getOrgListByCondition(inspectionCondition,"30",weightTotalMap.get("redTotal"));
				weightTotalMap.put("redTotal",weightTotalMap.get("redTotal")-redSet.size());
			} else if (10 == inspectionCondition.getConditionCredit()) {
				//如果信用等级为10，则在蓝色信用中随机企业
				Set<InspectionOrg> blueSet = getOrgListByCondition(inspectionCondition,"10",weightTotalMap.get("blueTotal"));
				weightTotalMap.put("blueTotal",weightTotalMap.get("blueTotal")-blueSet.size());
			} else if (20 == inspectionCondition.getConditionCredit()) {
				//如果信用等级为20，则在蓝色信用中随机企业
				Set<InspectionOrg> yellowSet = getOrgListByCondition(inspectionCondition,"20",weightTotalMap.get("yellowTotal"));
				weightTotalMap.put("yellowTotal",weightTotalMap.get("yellowTotal")-yellowSet.size());
			} else if (30 == inspectionCondition.getConditionCredit()) {
				//如果信用等级为30，则在蓝色信用中随机企业
				Set<InspectionOrg> redSet = getOrgListByCondition(inspectionCondition,"30",weightTotalMap.get("redTotal"));
				weightTotalMap.put("redTotal",weightTotalMap.get("redTotal")-redSet.size());
			}
		}
		//三种信用剩余部分 
		if(weightTotalMap.get("blueTotal") > 0){
			getOrgListByCondition(inspectionId,"10",weightTotalMap.get("blueTotal"));
		};
		if(weightTotalMap.get("yellowTotal") > 0){
			getOrgListByCondition(inspectionId,"20",weightTotalMap.get("yellowTotal"));
		};
		if(weightTotalMap.get("redTotal") > 0){
			getOrgListByCondition(inspectionId,"30",weightTotalMap.get("redTotal"));
		};
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 随机获取单色企业
	 * @param inspectionId
	 * @param CreditLevel
	 * @param CreditTotal
	 * @return
	 */
	private Set<InspectionOrg> getOrgListByCondition(String inspectionId,String CreditLevel,int CreditTotal){
		Set<InspectionOrg> orgSet = new HashSet<InspectionOrg>();
		//获取对应信用等级的企业列表
		CompanyModel companyModel = new CompanyModel();
		companyModel.setCreditLevel(CreditLevel);
		List<Company> companies = companyService.getCompanies(companyModel);
		//创建随机需要的list集合
		List<WeightCategory> weightCategories = new ArrayList<WeightCategory>();
		for (Company company : companies) {
			WeightCategory weightCategory = new WeightCategory();
			weightCategory.setCategory(company);
			weightCategory.setWeight(TeeStringUtil.getInteger(company.getWeight(), 0));
			weightCategories.add(weightCategory);
		}
		//随机获取对应数量的企业
		for (int i = 0; i < CreditTotal; i++) {
			TeeJson randomObject = WeightRandom.randomObject(weightCategories);
			Company company = (Company) randomObject.getRtData();
			InspectionOrg inspectionOrg = new InspectionOrg();
			inspectionOrg.setId(TeeStringUtil.getString(UUID.randomUUID()));
			inspectionOrg.setInspectionId(inspectionId);
			inspectionOrg.setOrgId(company.getId());
			inspectionOrg.setOrgName(company.getOrgName());
			inspectionOrg.setOrgCode(company.getOrgCode());
			inspectionOrg.setOrgType(company.getOrgTypeDic());
			boolean result = orgSet.add(inspectionOrg);
			//查询统一计划下是否存在该企业
			InspectionOrg org = inspectionOrgDao.getObjByOrgId(company.getId(), inspectionId);
			if (result&&TeeUtility.isNullorEmpty(org)) {
				//set中添加成功，数据库中未找到则不重复
				inspectionOrgDao.save(inspectionOrg);
			} else {
				orgSet.remove(inspectionOrg);
				i--;
			}
		}
		return orgSet;
	}
	
	/**
	 * 根据条件随机企业列表
	 * @param inspectionCondition
	 * @param CreditLevel
	 * @param CreditTotal
	 * @return
	 */
	private Set<InspectionOrg> getOrgListByCondition(InspectionCondition inspectionCondition,String CreditLevel,int CreditTotal){
		Set<InspectionOrg> orgSet = new HashSet<InspectionOrg>();
		//根据筛选条件查出企业列表
		CompanyModel companyModel = new CompanyModel();
		companyModel.setOrgTypeDic(TeeStringUtil.getString(inspectionCondition.getConditionType()));
		companyModel.setRegDistrictDic(TeeStringUtil.getString(inspectionCondition.getConditionRegion()));
		companyModel.setCreditLevel(CreditLevel);
		List<Company> companies = companyService.getCompaniesByMultiType(companyModel);
		//创建随机需要的list集合
		List<WeightCategory> weightCategories = new ArrayList<WeightCategory>();
		for (Company company : companies) {
			WeightCategory weightCategory = new WeightCategory();
			weightCategory.setCategory(company);
			weightCategory.setWeight(TeeStringUtil.getInteger(company.getWeight(), 0));
			weightCategories.add(weightCategory);
		}
		//计算需要选出的数量
		double total = CreditTotal*(inspectionCondition.getConditionProportion()/100.0);
		int randomTotal = (int)total;
		if (randomTotal > weightCategories.size()) {
			//如果需要的数量>备选的数量
			for (int i = 0; i < companies.size(); i++) {
				InspectionOrg inspectionOrg = new InspectionOrg();
				inspectionOrg.setId(TeeStringUtil.getString(UUID.randomUUID()));
				inspectionOrg.setInspectionId(inspectionCondition.getInspectionId());
				inspectionOrg.setOrgId(companies.get(i).getId());
				inspectionOrg.setOrgName(companies.get(i).getOrgName());
				inspectionOrg.setOrgCode(companies.get(i).getOrgCode());
				inspectionOrg.setOrgType(companies.get(i).getOrgTypeDic());
				orgSet.add(inspectionOrg);
				inspectionOrgDao.save(inspectionOrg);
			}
		} else {
			//如果需要的数量<=备选的数量
			for (int i = 0; i < randomTotal; i++) {
				TeeJson randomObject = WeightRandom.randomObject(weightCategories);
				Company company = (Company) randomObject.getRtData();
				InspectionOrg inspectionOrg = new InspectionOrg();
				inspectionOrg.setId(TeeStringUtil.getString(UUID.randomUUID()));
				inspectionOrg.setInspectionId(inspectionCondition.getInspectionId());
				inspectionOrg.setOrgId(company.getId());
				inspectionOrg.setOrgName(company.getOrgName());
				inspectionOrg.setOrgCode(company.getOrgCode());
				inspectionOrg.setOrgType(company.getOrgTypeDic());
				boolean result = orgSet.add(inspectionOrg);
				if (result) {
					inspectionOrgDao.save(inspectionOrg);
				} else {
					i--;
				}
			}
		}
		return orgSet;
	}
	
	/**
	 * 根据各色权重计算出各色企业总数集合
	 * @param weightObj
	 * @return
	 */
	private Map<String, Integer> getWeightTotalList(String inspectionId){
		//获取对应 检查任务颜色权重对象
		InspectionWeight weightObj = inspectionWeightService.getObjByInsId(inspectionId);
		int total = weightObj.getInspectionTotal();//检查总企业数量
		//分别计算出各个信用等级所占数量
		double redTotal = total * (weightObj.getWeightRed()/100.0);
		double yellowTotal = total * (weightObj.getWeightYellow()/100.0);
		double blueTotal = total * (weightObj.getWeightBlue()/100.0);
		List<String> weightNames = new ArrayList<String>();
		Double [] weightTotals = new Double[]{redTotal,yellowTotal,blueTotal};
		//各色权重具体数值（整数）
		Map<String, Integer> weightTotalMap = new HashMap<>();
		Arrays.sort(weightTotals);//排序
		//将排好序的各色总数数组和各色名字对应上
		for (int i = 0; i < weightTotals.length; i++) {
			if (weightTotals[i]==redTotal&&!weightNames.contains("redTotal")){
				weightNames.add("redTotal");//30
				continue;
			}
			if (weightTotals[i]==yellowTotal&&!weightNames.contains("yellowTotal")){
				weightNames.add("yellowTotal");//20
				continue;
			}
			if (weightTotals[i]==blueTotal&&!weightNames.contains("blueTotal")){
				weightNames.add("blueTotal");//10
				continue;
			}
		}
		int disparity = 0;
		//循环判断，如果计算结果不为整数则少者+1，最多者-其他加的部分
		for (int i = 0; i < weightTotals.length; i++) {
			if ((weightTotals[i].intValue()-weightTotals[i]!=0) && i != weightTotals.length-1) {
				weightTotalMap.put(weightNames.get(i), TeeStringUtil.getInteger(weightTotals[i], 0) + 1);
			}else{
				weightTotalMap.put(weightNames.get(i), TeeStringUtil.getInteger(weightTotals[i], 0));
			}
			if (i == weightTotals.length-1) {
				weightTotalMap.put(weightNames.get(i),total-disparity);
				break;
			}
			disparity += weightTotalMap.get(weightNames.get(i));
		}
		return weightTotalMap;
	}
	
	/**
	 * 保存所有筛选条件并筛选出企业列表
	 * @param inspectionRegion
	 * @param inspectionWeight
	 * @param conditionJson
	 * @return TeeJson
	 */
	public TeeJson saveWeightAndCondition(InspectionWeight inspectionWeight,String conditionJson){
		TeeJson json = new TeeJson();
		//将各类型权重和企业总数存入表中
		TeeJson weightJson = inspectionWeightService.addOrUpdate(inspectionWeight);
		if (weightJson.isRtState()) {
			//权重保存成功，继续保存筛选条件
			inspectionConditionService.saveConditionList(inspectionWeight.getInspectionId(), conditionJson);
			
			List<InspectionOrg> orgList = inspectionOrgDao.getListByInsId(inspectionWeight.getInspectionId());
			//如果已存在数据 则删除重新随机
			if (!orgList.isEmpty()) {
				for (InspectionOrg inspectionOrg : orgList) {
					inspectionOrgDao.deleteByObj(inspectionOrg);
				}
			}
			TeeJson screeningOrgJson = screeningOrg(inspectionWeight.getInspectionId());
			if (screeningOrgJson.isRtState()) {
				json.setRtState(true);
			}
		}
		return json;
	}
	/**
	 * 根据ID删除一条数据
	 * @param id
	 * @return
	 */
	public TeeJson delById(String id){
		TeeJson json = new TeeJson();
		InspectionOrg org = inspectionOrgDao.get(id);
		inspectionOrgDao.delete(id);
		json.setRtState(true);
		inspectionTaskDao.delListByInsId(org.getInspectionId());//别检查企业变动时删除后续计划
		return json;
	}
	
	/**
	 * 批量新增
	 * @param orgIds
	 * @param inspectionId
	 * @return
	 */
	public TeeJson batchSave(String orgIds,String inspectionId){
		TeeJson json = new TeeJson();
		//判断参数是否为空
		if (!TeeUtility.isNullorEmpty(orgIds)&&!"".equals(orgIds)) {
			String[] orgIdArr = orgIds.split(",");
			for (String orgId : orgIdArr) {
				Company company = companyService.getById(orgId);
				InspectionOrg insOrg = inspectionOrgDao.getObjByOrgId(orgId, inspectionId);
				//判断该id的企业是否存在,并去重
				if (!TeeUtility.isNullorEmpty(company)&&TeeUtility.isNullorEmpty(insOrg)) {
					InspectionOrg org = new InspectionOrg();
					org.setId(TeeStringUtil.getString(UUID.randomUUID()));
					org.setInspectionId(inspectionId);
					org.setOrgId(company.getId());
					org.setOrgName(company.getOrgName());
					org.setOrgCode(company.getOrgCode());
					org.setOrgType(company.getOrgTypeDic());
					inspectionOrgDao.save(org);
					inspectionTaskDao.delListByInsId(inspectionId);//别检查企业变动时删除后续计划
					json.setRtState(true);
				}
			}
		}
		return json;
	}
	
	/**
	 * 导出被检查企业
	 * @return
	 */
	public ArrayList<TeeDataRecord> exportOrgInfo(String inspectionId){
		ArrayList<TeeDataRecord> list = new ArrayList<TeeDataRecord>();
		List<InspectionOrg> orgList = inspectionOrgDao.getListByInsId(inspectionId);
	    for (int i = 0; i < orgList.size(); i++) {
	    	InspectionOrg inspectionOrg = orgList.get(i);
	        TeeDataRecord dbrec = new TeeDataRecord();
	        dbrec.addField("机构代码", TeeStringUtil.getString(inspectionOrg.getOrgCode()));
	        dbrec.addField("企业名称", TeeStringUtil.getString(inspectionOrg.getOrgName()));
	        list.add(dbrec);
	      }
		return list;
	}
    
	
	/**
	 * 导入被检查企业
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public TeeJson importOrg(HttpServletRequest request,String inspectionId) throws Exception{
		TeeJson json = new TeeJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		StringBuffer sb = new StringBuffer("[");
		String infoStr= "";
		int importSuccessCount = 0;
		try {
			MultipartFile  file = multipartRequest.getFile("importOrgFile");
			if(!file.isEmpty() ){
				//获取真实文件名称
				String realName = file.getOriginalFilename();
				ArrayList<TeeDataRecord> dbL = TeeCSVUtil.CVSReader(file.getInputStream(), "GBK");
				for (int i = 0; i < dbL.size(); i++) {
					TeeDataRecord dr = dbL.get(i);
					String orgCode = TeeStringUtil.getString(dr.getValueByName("机构代码"));
					String orgName = TeeStringUtil.getString(dr.getValueByName("企业名称"));
					Company company = companyService.getByCode(orgCode);
					if (TeeUtility.isNullorEmpty(company)) {
						infoStr = "导入失败,该用户 " + orgName + " 不存在！";
						sb.append("{");
						sb.append("orgCode:\"" + orgCode + "\"");
				        sb.append("orgName:\"" + orgName + "\"");
				        sb.append("},");
				        continue;
					}
					InspectionOrg org = inspectionOrgDao.getObjByOrgId(company.getId(), inspectionId);
					if (!TeeUtility.isNullorEmpty(org)) {
						infoStr = "导入失败,执法人员 " + orgName + " 已经存在！";
						sb.append("{");
						sb.append("orgCode:\"" + orgCode + "\"");
				        sb.append("orgName:\"" + orgName + "\"");
				        sb.append("},");
				        continue;
					}
					//personService.getPersonByUserId(userId)
//					inspectorsService
					//isCount++;
			        infoStr = "导入成功";
			        InspectionOrg newOrg = new InspectionOrg();
			        newOrg.setId(TeeStringUtil.getString(UUID.randomUUID()));
			        newOrg.setInspectionId(inspectionId);
			        newOrg.setOrgCode(company.getOrgCode());
			        newOrg.setOrgId(company.getId());
			        newOrg.setOrgName(company.getOrgName());
			        inspectionOrgDao.save(newOrg);
			        inspectionTaskDao.delListByInsId(inspectionId);//别检查企业变动时删除后续计划
			        sb.append("{");
			        sb.append("orgCode:\"" + orgCode + "\"");
			        sb.append("orgName:\"" + orgName + "\"");
			        sb.append("},");
			        importSuccessCount++;
				}
			}else{
				json.setRtState(false);
				json.setRtMsg("文件为空！");
				return json;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		if(!sb.equals("[")){
			sb.deleteCharAt(sb.length() -1);
		}
		sb.append("]");
		json.setRtData(sb);
		json.setRtState(true);
		json.setRtMsg(importSuccessCount + "");
		return json;
	}
}