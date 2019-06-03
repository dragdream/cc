package com.tianee.oa.subsys.budget.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.subsys.budget.bean.TeeDeptBudget;
import com.tianee.oa.subsys.budget.dao.TeeDeptBudgetDao;
import com.tianee.oa.subsys.budget.model.TeeDeptBudgetModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeDeptBudgetService extends TeeBaseService {

	@Autowired
	private TeeDeptBudgetDao dao;

	@Autowired
	private TeeDeptDao deptDao;

	/**
	 * @function: 新建数据
	 * @author: wyw
	 * @data: 2014年9月5日
	 * @param requestMap
	 * @param loginPerson
	 * @return TeeJson
	 * @throws ParseException
	 */
	public TeeJson addObj(Map requestMap, TeePerson loginPerson, TeeDeptBudgetModel model) throws ParseException {
		TeeJson json = new TeeJson();
		if (model.getDeptId() > 0) {
			TeeDepartment dept = deptDao.get(model.getDeptId());
			if (dept != null) {
				for (int i = 1; i <= 12; i++) {
					String counterStr = (i < 10) ? "0" + i : String.valueOf(i);
					String amount = (String) requestMap.get("month_" + counterStr);
					TeeDeptBudget obj = new TeeDeptBudget();
					obj.setDept(dept);
					obj.setYear(model.getYear());
					obj.setMonth(counterStr);
					if (!TeeUtility.isNullorEmpty(amount)) {
						obj.setAmount(TeeUtility.parseDouble(amount));
					}
					obj.setCrUser(loginPerson);
					dao.save(obj);
				}
			}
		}
		json.setRtState(true);
		json.setRtMsg("保存成功！");
		return json;
	}

	/**
	 * @function: 更新数据
	 * @author: wyw
	 * @data: 2014年9月5日
	 * @param requestMap
	 * @param loginPerson
	 * @return TeeJson
	 * @throws ParseException 
	 */
	public TeeJson updateObj(Map requestMap, TeePerson loginPerson, TeeDeptBudgetModel model) throws ParseException {
		TeeJson json = new TeeJson();
		
		if (model.getDeptId() > 0 ) {
			Map<String, String> map = new HashMap<String, String>();
			for (int i = 1; i <= 12; i++) {
				String counterStr = (i < 10) ? "0" + i : String.valueOf(i);
				String monthId = (String) requestMap.get("monthId_" + counterStr);
				String amount = (String) requestMap.get("month_" + counterStr);
				map.put(monthId, amount);
			}
			int fromDeptId = TeeStringUtil.getInteger((String) requestMap.get("fromDeptId"),0);
			String fromYearId = TeeStringUtil.getString((String) requestMap.get("fromYearId"), "0");
			
			TeeDeptBudgetModel fromModel = new TeeDeptBudgetModel();
			fromModel.setDeptId(fromDeptId);
			fromModel.setYear(fromYearId);
			
			
			TeeDepartment dept = deptDao.get(model.getDeptId());
			if(dept != null){
				List<TeeDeptBudget> list = dao.getObjList(fromModel);
				if(list != null && list.size()>0){
					for(TeeDeptBudget obj:list){
						obj.setDept(dept);
						obj.setYear(model.getYear());
						String amount = map.get(obj.getUuid());
						if (!TeeUtility.isNullorEmpty(amount)) {
							obj.setAmount(TeeUtility.parseDouble(amount));
						}
					}
				}
			}
		}
		json.setRtState(true);
		json.setRtMsg("保存成功！");
		return json;
	}

	/**
	 * @function: 管理列表
	 * @author: wyw
	 * @data: 2014年9月5日
	 * @param dm
	 * @param requestDatas
	 * @param model
	 * @return
	 * @throws ParseException
	 *             TeeEasyuiDataGridJson
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getManageInfoList(TeeDataGridModel dm, Map requestDatas, TeeDeptBudgetModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();

		String deptId = (String) requestDatas.get("deptId");
		String year = (String) requestDatas.get("year");
		String amountMax = (String) requestDatas.get("amountMax");
		String amountMin = (String) requestDatas.get("amountMin");
		
		String queryStr = " 1=1";
		if (!TeeUtility.isNullorEmpty(deptId)) {
			queryStr += " and bu2.dept_id = " + deptId;
		}
		if (!TeeUtility.isNullorEmpty(year)) {
			queryStr += " and bu2.year = " + year;
		}
		if (!TeeUtility.isNullorEmpty(amountMax)) {
			queryStr += " and sum(bu2.amount) > " + amountMax;
		}
		if (!TeeUtility.isNullorEmpty(amountMin)) {
			queryStr += " and sum(bu2.amount) < " + amountMin;
		}
		String sql = "select sum(bu2.amount) as AMOUNT,"
				+ "bu2.dept_id as DEPTID,"
				+ "bu2.cr_user_id as CR_USER_ID,"
				+ "(select dept.dept_Name from department dept where dept.uuid=bu2.dept_id) as DEPTNAME,"
				+ "bu2.year as YEAR "
				+ "from budget_dept bu2 "
				+ " group by bu2.dept_id,bu2.year having " + queryStr ;//+ " order by bu2.year desc";
		
		//System.out.println(dm.getSort());
		Map obj = simpleDaoSupport.executeNativeUnique("select count(1) as count from ("+sql+") tmp", null);
		sql = sql + " order by " + dm.getSort() + " " + dm.getOrder();
		List<Map> dataList = simpleDaoSupport.executeNativeQuery(sql, null, dm.getFirstResult(), dm.getRows());
		List<Map> rows=new ArrayList<Map>();
		Map dataMap = null;
		if(!TeeUtility.isNullorEmpty(dataList)){
			TeePerson manager=null;
			for (Map map : dataList) {
				dataMap = new HashMap();
				//获取负责人
				int managePersonId = (Integer) map.get("CR_USER_ID");
				if(managePersonId!=0){
					manager=(TeePerson) simpleDaoSupport.get(TeePerson.class,managePersonId);
					dataMap.put("managePersonName", manager.getUserName());
				}else{
					dataMap.put("managePersonName", null);
				}
				//获取其他数据
				dataMap.put("YEAR", map.get("YEAR"));
				dataMap.put("DEPTNAME", map.get("DEPTNAME"));
				dataMap.put("AMOUNT", map.get("AMOUNT"));
				dataMap.put("DEPTID", map.get("DEPTID"));
				rows.add(dataMap);
			}
		}
		j.setTotal(TeeStringUtil.getLong(obj.get("count"), 0));
		j.setRows(rows);// 设置返回的行
		return j;
	}

	/**
	 * @function: 转换成返回对象
	 * @author: wyw
	 * @data: 2014年8月29日
	 * @param obj
	 * @return TeeDeptBudgetModel
	 */
	public TeeDeptBudgetModel parseReturnModel(TeeDeptBudget obj) {
		TeeDeptBudgetModel model = new TeeDeptBudgetModel();
		if (obj == null) {
			return model;
		}
		BeanUtils.copyProperties(obj, model);

		model.setAmount(obj.getAmount());
		if (obj.getDept() != null) {
			model.setDeptId(obj.getDept().getUuid());
			model.setDeptName(obj.getDept().getDeptName());
		}

		model.setMonth(obj.getMonth());
		model.setYear(obj.getYear());

		if (obj.getCrUser() != null) {
			model.setCrUserId(obj.getCrUser().getUuid());
			model.setCrUserName(obj.getCrUser().getUserName());
		}
		return model;
	}
	
	
	
	/**
	 * @function: 根据sid查看详情
	 * @author: wyw
	 * @data: 2014年9月5日
	 * @param requestMap
	 * @param loginPerson
	 * @param model
	 * @return TeeJson
	 */
	public TeeJson getInfoById(Map requestMap, TeePerson loginPerson, TeeDeptBudgetModel model) {
		String[] fistQuarters = {"01","02","03"};
		String[] secondQuarters = {"04","05","06"};
		String[] thirdQuarters = {"07","08","09"};
		String[] fourthQuarters = {"10","11","12"};
		
		List<String>fistQuartersList = Arrays.asList(fistQuarters);
		List<String>secondQuartersList = Arrays.asList(secondQuarters);
		List<String>thirdQuartersList = Arrays.asList(thirdQuarters);
		List<String>fourthQuartersList = Arrays.asList(fourthQuarters);
		
		
		
		TeeJson json = new TeeJson();
		double fistQuarter = 0 ;
		double secondQuarter = 0;
		double thirdQuarter = 0;
		double fourthQuarter = 0;
		List<TeeDeptBudgetModel> monthList = new ArrayList<TeeDeptBudgetModel>();
		
		
		if(model.getDeptId()>0){
			List<TeeDeptBudget> list = dao.getObjList(model);
			if(list != null && list.size()>0){
				for(TeeDeptBudget deptBudget:list){
					if(fistQuartersList.contains(deptBudget.getMonth())){
						fistQuarter += deptBudget.getAmount();
					}
					if(secondQuartersList.contains(deptBudget.getMonth())){
						secondQuarter += deptBudget.getAmount();
					}
					if(thirdQuartersList.contains(deptBudget.getMonth())){
						thirdQuarter += deptBudget.getAmount();
					}
					if(fourthQuartersList.contains(deptBudget.getMonth())){
						fourthQuarter += deptBudget.getAmount();
					}
					
					TeeDeptBudgetModel budgetModel = parseReturnModel(deptBudget);
					monthList.add(budgetModel);
				}
			}
		}
		
		Map map = new HashMap();
		map.put("fistQuarter", fistQuarter);
		map.put("secondQuarter", secondQuarter);
		map.put("thirdQuarter", thirdQuarter);
		map.put("fourthQuarter", fourthQuarter);
		map.put("monthList", monthList);
		
		json.setRtData(map);
		json.setRtMsg("查询成功！");
		json.setRtState(true);
		return json;
	}
	
	
	
	/**
	 * @function: 删除
	 * @author: wyw
	 * @data: 2014年9月6日
	 * @param requestMap
	 * @param loginPerson
	 * @param model
	 * @return
	 * @throws ParseException TeeJson
	 */
	public TeeJson deleteObjById(Map requestMap, TeePerson loginPerson, TeeDeptBudgetModel model) throws ParseException {
		TeeJson json = new TeeJson();
		String deptId = (String) requestMap.get("deptId");
		String year = (String) requestMap.get("year");
//		List<TeeDeptBudget> deptBudgetList = dao.getObjListByIds(sids);
//		if(deptBudgetList != null && deptBudgetList.size()>0){
//			for(TeeDeptBudget deptBudget:deptBudgetList){
//				String year = deptBudget.getYear();
//				int deptId = deptBudget.getDept().getUuid();
//				TeeDeptBudgetModel modelTemp = new TeeDeptBudgetModel();
//				modelTemp.setDeptId(deptId);
//				modelTemp.setYear(year);
//				dao.deleteObjByDeptIdAndYear(modelTemp);
//			}
//		}
		simpleDaoSupport.executeUpdate("delete from TeeDeptBudget where dept.uuid=? and year=?", new Object[]{Integer.parseInt(deptId),year});
		json.setRtState(true);
		json.setRtMsg("操作成功！");
		return json;
	}
	
	/**
	 * @function: 校验是否已存在
	 * @author: wyw
	 * @data: 2014年9月6日
	 * @param requestMap
	 * @param loginPerson
	 * @param model
	 * @return TeeJson
	 */
	public TeeJson checkExist(Map requestMap, TeePerson loginPerson, TeeDeptBudgetModel model) {
		TeeJson json = new TeeJson();
		List<TeeDeptBudget>  list = dao.checkExist(model);
		int existFlag = 0;
		if(list.size()>11){
			existFlag = 1; 
		}
		Map map = new HashMap();
		map.put("existFlag", existFlag);
		
		json.setRtData(map);
		json.setRtMsg("操作 成功！");
		json.setRtState(true);
		return json;
	}
	
	
	/**
	 * 获取部门当月预算剩余金额
	 * @function: 
	 * @author: wyw
	 * @data: 2014年9月14日
	 * @param requestMap
	 * @param deptId
	 * @return TeeJson
	 */
	public TeeJson getDeptBudgetCost(Map requestMap, int deptId) {
		TeeJson json = new TeeJson();
		Calendar c = Calendar.getInstance();
		double deptCost = dao.getDeptBudgetCost(deptId, c.get(Calendar.YEAR),c.get(Calendar.MONTH)+1);
		double regCost = dao.getRegBudgetCost(deptId, c.get(Calendar.YEAR),c.get(Calendar.MONTH)+1);
		
		double returnAmount = deptCost - regCost;
		Map map = new HashMap();
		map.put("returnAmount", returnAmount);
		
		json.setRtData(map);
		json.setRtMsg("操作 成功！");
		json.setRtState(true);
		return json;
	}
	
	
	
	
	
	
	

}
