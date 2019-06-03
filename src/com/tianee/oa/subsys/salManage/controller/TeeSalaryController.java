package com.tianee.oa.subsys.salManage.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.core.org.service.TeePersonManagerI;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.contract.bean.TeeCrmContractProductItem;
import com.tianee.oa.subsys.salManage.bean.TeeHrInsurancePara;
import com.tianee.oa.subsys.salManage.bean.TeeHrSalData;
import com.tianee.oa.subsys.salManage.bean.TeeSalItem;
import com.tianee.oa.subsys.salManage.model.TeeHrInsuranceParaModel;
import com.tianee.oa.subsys.salManage.model.TeeSalDataModel;
import com.tianee.oa.subsys.salManage.service.TeeSalaryService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

/**
 * 
 * @author CXT
 * 
 */
@Controller
@RequestMapping("/salaryManage")
public class TeeSalaryController extends BaseController {
	@Autowired
	private TeeSalaryService salaryService;

	@Autowired
	private TeeDeptService deptService;

	@Autowired
	private TeePersonService personService;

	@Autowired
	private TeePersonManagerI personService1;

	/**
	 * @author CXT 新增或者更新
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/addOrUpdateHrPara")
	@ResponseBody
	public TeeJson addOrUpdateHrPara(HttpServletRequest request)
			throws Exception {
		TeeJson json = new TeeJson();
		TeeHrInsuranceParaModel model = new TeeHrInsuranceParaModel();
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = salaryService.addOrUpdateHrPara(request, model);
		return json;
	}

	/**
	 * @author CXT 获取保险基数
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/getHrPara")
	@ResponseBody
	public TeeJson getHrPara(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		try {
			TeeHrInsurancePara hrPara = salaryService.getHrPara(sid);
			TeeHrInsuranceParaModel model = new TeeHrInsuranceParaModel();
			if (hrPara!=null) {
				BeanUtils.copyProperties(hrPara, model);
				json.setRtData(model);
			} else {
				json.setRtData(null);
			}
			json.setRtMsg("获取成功");
			json.setRtState(true);
		} catch (Exception ex) {
			json.setRtMsg("获取失败");
			json.setRtState(false);
		}
		return json;
	}
	
	@RequestMapping("/delHrPara")
	@ResponseBody
	public TeeJson delHrPara(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		salaryService.delHrPara(sid);
		return json;
	}
	
	/**
	 * 获取保险列表
	 * @param request
	 * @param dataGridModel
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/datagridInsurances")
	@ResponseBody
	public TeeEasyuiDataGridJson datagridInsurances(TeeDataGridModel dataGridModel) throws Exception {
		return salaryService.datagridInsurances(dataGridModel);
	}
	

	/**
	 * spring mvc 返回mav 用el表达式获取值
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/personList/{deptId}")
	public ModelAndView personList(HttpServletRequest request,
			@PathVariable("deptId") String deptId) {
		ModelAndView mav = new ModelAndView(
				"/system/subsys/salary/person/personList.jsp");
		TeeDepartment dept = deptService.selectDeptByUuid(deptId);
		String modelId = TeeStringUtil.getString(request
				.getParameter("modelId"));
		String userFilter = TeeStringUtil.getString(
				request.getParameter("userFilter"), "0");
		List<TeePerson> personList = personService1
				.getManagerPostPersonPrivByDept(request, modelId, deptId,
						userFilter);// personService.selectPersonByDeptId(deptId);
		List<TeeHrInsurancePara> list = salaryService.hrParaList();

		List<TeeSalItem> itemList = salaryService.salItemList(0);// 工资添加项；
		TeeHrInsurancePara para = new TeeHrInsurancePara();
		if (list.size() > 0) {
			para = list.get(0);

		}

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < personList.size(); i++) {
			sb = sb.append(personList.get(i).getUuid() + ",");
		}

		mav.addObject("deptName", dept.getDeptName());
		mav.addObject("deptId", dept.getUuid());
		mav.addObject("personCount", personList.size());
		mav.addObject("personList", personList);
		mav.addObject("hrPara", para);
		mav.addObject("personIds", sb.toString());
		mav.addObject("itemList", itemList);

		return mav;
	}

	/**
	 * @author CXT 获取部门人员uuid数组
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/getPersonList")
	@ResponseBody
	public TeeJson getPersonList(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		try {
			String deptId = request.getParameter("deptId");
			String modelId = TeeStringUtil.getString("modelId");
			String userFilter = TeeStringUtil.getString("userFilter", "0");
			List<TeePerson> personList = personService1
					.getManagerPostPersonPrivByDept(request, modelId, deptId,
							userFilter);// personService.selectPersonByDeptId(deptId);

			List<String> list = new ArrayList<String>();
			StringBuffer sb = new StringBuffer();
			for (TeePerson p : personList) {
				String uuid = p.getUuid() + "";
				sb.append(p.getUuid() + ",");
				// list.add(uuid);
			}
			json.setRtData(sb.toString());
			json.setRtMsg("获取成功");
			json.setRtState(true);
		} catch (Exception ex) {
			json.setRtMsg("获取失败");
			json.setRtState(false);
		}
		return json;
	}

	/**
	 * @author CXT 获取部门人员工资数值
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/getPersonSalData")
	@ResponseBody
	public TeeJson getPersonSalData(HttpServletRequest request)
			throws Exception {
		TeeJson json = new TeeJson();
		try {
			String personId = request.getParameter("personId");
			List<TeeHrSalData> dataList = salaryService
					.getSalDataList(personId);
			List<TeeSalDataModel> modelData = new ArrayList<TeeSalDataModel>();
			for (int i = 0; i < dataList.size(); i++) {
				TeeHrSalData data = dataList.get(i);
				TeeSalDataModel model = parseHrSalDataModel(data);
				modelData.add(model);
			}

			List<TeeSalItem> itemList = salaryService.salItemList(0);// 工资项
			Map map = new HashMap();
			map.put("modelData", modelData);
			map.put("itemList", itemList);
			json.setRtData(map);
			json.setRtMsg("获取成功");
			json.setRtState(true);
		} catch (Exception ex) {
			json.setRtMsg("获取失败");
			json.setRtState(false);
		}
		return json;
	}

	/**
	 * 转工资设置模型
	 * 
	 * @param data
	 * @return
	 */
	public TeeSalDataModel parseHrSalDataModel(TeeHrSalData data) {
		TeeSalDataModel model = new TeeSalDataModel();
		if (data == null) {
			return null;
		}

		BeanUtils.copyProperties(data, model);
		TeePerson user = data.getUser();
		model.setUserId(user.getUuid());
		model.setUserName(user.getUserName());
		return model;
	}

	/**
	 * @author CXT 保存部门人的薪酬数值
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/saveSalData")
	@ResponseBody
	public TeeJson saveSalData(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		try {
			String deptId = request.getParameter("deptId");
			// List<TeePerson> personList =
			// personService.selectPersonByDeptId(deptId);
			String itemListStr = TeeStringUtil.getString(request
					.getParameter("itemListStr"));
			String ids = TeeStringUtil.getString(request.getParameter("ids"));
			// 转对象
			List<TeeSalDataModel> itemList = (List<TeeSalDataModel>) TeeJsonUtil
					.JsonStr2ObjectList(itemListStr, TeeSalDataModel.class);
			for (int i = 0; i < itemList.size(); i++) {
				TeeHrSalData data = new TeeHrSalData();
				TeeSalDataModel model = itemList.get(i);
				data = salaryService.getSalData(model.getUserId() + "");
				if (data.getUser() == null) {// 新增
					TeeHrSalData d = new TeeHrSalData();
					BeanUtils.copyProperties(model, d, new String[] { "sid" });
					TeePerson user = personService.selectByUuid(model
							.getUserId());
					d.setUser(user);
					salaryService.saveSalData(d);
				} else {
					BeanUtils.copyProperties(model, data,
							new String[] { "sid" });
					salaryService.updateSalData(data);
				}
			}
			json.setRtMsg("保存成功");
			json.setRtState(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			json.setRtMsg("保存失败");
			json.setRtState(false);
		}
		return json;
	}

	/**
	 * spring mvc 返回mav 用el表达式获取值
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/personListMore.action")
	public ModelAndView personListMore(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(
				"/system/subsys/salary/person/personListMore.jsp");
		List<TeeSalItem> shuruList = salaryService.salItemList(0);
		List<TeeSalItem> jisuanList = salaryService.salItemList(1);
		// List<TeeSalItem> jisuanList = salaryService.salItemList(2);
		mav.addObject("shuruList", shuruList);
		mav.addObject("jisuanList", jisuanList);
		return mav;
	}

	/**
	 * @author CXT 批量设置薪酬数值
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/saveSalDataMore")
	@ResponseBody
	public TeeJson saveSalDataMore(HttpServletRequest request,
			TeeSalDataModel model) throws Exception {
		TeeJson json = new TeeJson();
		try {
			String postDeptIds = request.getParameter("postDeptIds");
			String postUserIds = request.getParameter("postUserIds");
			String postUserRoleIds = request.getParameter("postUserRoleIds");
			TeePerson person = (TeePerson) request.getSession().getAttribute(
					TeeConst.LOGIN_USER);
			// 将request中的对应字段映值射到目标对象的属性中

			List<TeePerson> personList = personService1
					.getPersonByUuidsOrDeptIdsOrRoleIds(person, postUserIds,
							postDeptIds, postUserRoleIds);
			for (TeePerson p : personList) {
				TeeHrSalData data = new TeeHrSalData();

				data = salaryService.getSalData(p.getUuid() + "");
				if (data.getUser() == null) {// 新增
					TeeHrSalData d = new TeeHrSalData();
					BeanUtils.copyProperties(model, data);
					// TeeServletUtility.requestParamsCopyToObject(request, d);
					d.setUser(p);
					d.setAllBase(TeeStringUtil.getDouble(
							request.getParameter("ALL_BASE"), 0));
					// System.out.println(request.getParameter("4_ALL_BASE")+","+request.getParameter(p.getUuid()+"_PENSION_BASE"));
					d.setPensionBase(TeeStringUtil.getDouble(
							request.getParameter("PENSION_BASE"), 0));
					d.setPensionU(TeeStringUtil.getDouble(
							request.getParameter("PENSION_U"), 0));
					d.setPensionP(TeeStringUtil.getDouble(
							request.getParameter("PENSION_P"), 0));
					d.setMedicalBase(TeeStringUtil.getDouble(
							request.getParameter("MEDICAL_BASE"), 0));
					d.setMedicalU(TeeStringUtil.getDouble(
							request.getParameter("MEDICAL_U"), 0));
					d.setMedicalP(TeeStringUtil.getDouble(
							request.getParameter("MEDICAL_P"), 0));
					d.setFertilityBase(TeeStringUtil.getDouble(
							request.getParameter("FERTILITY_BASE"), 0));
					d.setFertilityU(TeeStringUtil.getDouble(
							request.getParameter("FERTILITY_U"), 0));
					d.setFertilityP(TeeStringUtil.getDouble(
							request.getParameter("FERTILITY_P"), 0));
					d.setUnemploymentBase(TeeStringUtil.getDouble(
							request.getParameter("UNEMPLOYMENT_BASE"), 0));
					d.setUnemploymentU(TeeStringUtil.getDouble(
							request.getParameter("UNEMPLOYMENT_U"), 0));
					d.setUnemploymentP(TeeStringUtil.getDouble(
							request.getParameter("UNEMPLOYMENT_P"), 0));
					d.setInjuriesBase(TeeStringUtil.getDouble(
							request.getParameter("INJURIES_BASE"), 0));
					d.setInjuriesU(TeeStringUtil.getDouble(
							request.getParameter("INJURIES_U"), 0));
					d.setHousingBase(TeeStringUtil.getDouble(
							request.getParameter("HOUSING_BASE"), 0));
					d.setHousingU(TeeStringUtil.getDouble(
							request.getParameter("HOUSING_U"), 0));
					d.setHousingP(TeeStringUtil.getDouble(
							request.getParameter("HOUSING_P"), 0));
					salaryService.saveSalData(d);
				} else {// 更新
					BeanUtils.copyProperties(model, data,
							new String[] { "sid" });
					// TeeServletUtility.requestParamsCopyToObject(request,
					// data);
					data.setUser(p);
					data.setAllBase(TeeStringUtil.getDouble(
							request.getParameter("ALL_BASE"), 0));
					data.setPensionBase(TeeStringUtil.getDouble(
							request.getParameter("PENSION_BASE"), 0));
					data.setPensionU(TeeStringUtil.getDouble(
							request.getParameter("PENSION_U"), 0));
					data.setPensionP(TeeStringUtil.getDouble(
							request.getParameter("PENSION_P"), 0));
					data.setMedicalBase(TeeStringUtil.getDouble(
							request.getParameter("MEDICAL_BASE"), 0));
					data.setMedicalU(TeeStringUtil.getDouble(
							request.getParameter("MEDICAL_U"), 0));
					data.setMedicalP(TeeStringUtil.getDouble(
							request.getParameter("MEDICAL_P"), 0));
					data.setFertilityBase(TeeStringUtil.getDouble(
							request.getParameter("FERTILITY_BASE"), 0));
					data.setFertilityU(TeeStringUtil.getDouble(
							request.getParameter("FERTILITY_U"), 0));
					data.setFertilityP(TeeStringUtil.getDouble(
							request.getParameter("FERTILITY_P"), 0));
					data.setUnemploymentBase(TeeStringUtil.getDouble(
							request.getParameter("UNEMPLOYMENT_BASE"), 0));
					data.setUnemploymentU(TeeStringUtil.getDouble(
							request.getParameter("UNEMPLOYMENT_U"), 0));
					data.setUnemploymentP(TeeStringUtil.getDouble(
							request.getParameter("UNEMPLOYMENT_P"), 0));
					data.setInjuriesBase(TeeStringUtil.getDouble(
							request.getParameter("INJURIES_BASE"), 0));
					data.setInjuriesU(TeeStringUtil.getDouble(
							request.getParameter("INJURIES_U"), 0));
					data.setHousingBase(TeeStringUtil.getDouble(
							request.getParameter("HOUSING_BASE"), 0));
					data.setHousingU(TeeStringUtil.getDouble(
							request.getParameter("HOUSING_U"), 0));
					data.setHousingP(TeeStringUtil.getDouble(
							request.getParameter("HOUSING_P"), 0));
					salaryService.updateSalData(data);
				}
			}
			json.setRtMsg("保存成功");
			json.setRtState(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			json.setRtMsg("保存失败");
			json.setRtState(false);
		}
		return json;
	}

	
	
	
	//获取税率设置的值
	@RequestMapping("/getRate")
	@ResponseBody
	public TeeJson getRate(HttpServletRequest request) throws Exception {
		TeeJson json = salaryService.getRate();
		return json;
	}
	
	
	

	//修改
	@RequestMapping("/updateRate")
	@ResponseBody
	public TeeJson updateRate(HttpServletRequest request) throws Exception {
		TeeJson json = salaryService.updateRate(request);
		return json;
	}
}
