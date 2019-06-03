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

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.budget.bean.TeeUserBudget;
import com.tianee.oa.subsys.budget.dao.TeeUserBudgetDao;
import com.tianee.oa.subsys.budget.model.TeeUserBudgetModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeUserBudgetService extends TeeBaseService {

	@Autowired
	private TeeUserBudgetDao dao;

	@Autowired
	private TeePersonDao personDao;

	/**
	 * @function: 新建数据
	 * @author: wyw
	 * @data: 2014年9月5日
	 * @param requestMap
	 * @param loginPerson
	 * @return TeeJson
	 * @throws ParseException
	 */
	public TeeJson addObj(Map requestMap, TeePerson loginPerson, TeeUserBudgetModel model) throws ParseException {
		TeeJson json = new TeeJson();
		if (model.getUserId() > 0) {
			TeePerson user = personDao.get(model.getUserId());
			if (user != null) {
				for (int i = 1; i <= 12; i++) {
					String counterStr = (i < 10) ? "0" + i : String.valueOf(i);
					String amount = (String) requestMap.get("month_" + counterStr);
					TeeUserBudget obj = new TeeUserBudget();
					obj.setUser(user);
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
	public TeeJson updateObj(Map requestMap, TeePerson loginPerson, TeeUserBudgetModel model) throws ParseException {
		TeeJson json = new TeeJson();
		
		if (model.getUserId() > 0 ) {
			Map<String, String> map = new HashMap<String, String>();
			for (int i = 1; i <= 12; i++) {
				String counterStr = (i < 10) ? "0" + i : String.valueOf(i);
				String monthId = (String) requestMap.get("monthId_" + counterStr);
				String amount = (String) requestMap.get("month_" + counterStr);
				map.put(monthId, amount);
			}
			int fromUserId = TeeStringUtil.getInteger((String) requestMap.get("fromUserId"),0);
			String fromYearId = TeeStringUtil.getString((String) requestMap.get("fromYearId"), "0");
			
			TeeUserBudgetModel fromModel = new TeeUserBudgetModel();
			fromModel.setUserId(fromUserId);
			fromModel.setYear(fromYearId);
			
			
			TeePerson user = personDao.get(model.getUserId());
			if(user != null){
				List<TeeUserBudget> list = dao.getObjList(fromModel);
				if(list != null && list.size()>0){
					for(TeeUserBudget obj:list){
						obj.setUser(user);
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
	public TeeEasyuiDataGridJson getManageInfoList(TeeDataGridModel dm, Map requestDatas, TeeUserBudgetModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();

		String userId = (String) requestDatas.get("userId");
		String year = (String) requestDatas.get("year");
		String amountMax = (String) requestDatas.get("amountMax");
		String amountMin = (String) requestDatas.get("amountMin");
		
		TeePerson loginPerson = (TeePerson) requestDatas.get(TeeConst.LOGIN_USER);
		
		String queryStr = " 1=1";
		/*if(TeePersonService.checkIsAdminPriv(loginPerson)){*///如果是管理员，则可以看到所有人的预算
			if (!TeeUtility.isNullorEmpty(userId)) {
				queryStr += " and bu2.user_Id = " + userId;
			}
		/*}else{
			queryStr += " and bu2.user_Id = " + loginPerson.getUuid();
		}*/
		
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
				+ "bu2.user_id as USERID,"
				+ "bu2.cr_user_id as CR_USER_ID,"
				+ "(select p.user_name from person p where p.uuid=bu2.user_id) as USERNAME,"
				+ "bu2.year as YEAR "
				+ "from budget_user bu2 "
				+ " group by bu2.user_Id,bu2.year having " + queryStr ;//+ " order by bu2.year asc";
		
		if(TeeUtility.isNullorEmpty(dm.getSort())){
			dm.setSort("year");
			dm.setOrder("desc");
		}
		
		Map obj = simpleDaoSupport.executeNativeUnique("select count(1) as count from ("+sql+") tmp", null);
		sql += " order by " + dm.getSort() + " " + dm.getOrder();
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
				dataMap.put("USERNAME", map.get("USERNAME"));
				dataMap.put("AMOUNT", map.get("AMOUNT"));
				dataMap.put("USERID", map.get("USERID"));
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
	 * @return TeeUserBudgetModel
	 */
	public TeeUserBudgetModel parseReturnModel(TeeUserBudget obj) {
		TeeUserBudgetModel model = new TeeUserBudgetModel();
		if (obj == null) {
			return model;
		}
		BeanUtils.copyProperties(obj, model);

		model.setAmount(obj.getAmount());
		if (obj.getUser() != null) {
			model.setUserId(obj.getUser().getUuid());
			model.setUserName(obj.getUser().getUserName());
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
	public TeeJson getInfoById(Map requestMap, TeePerson loginPerson, TeeUserBudgetModel model) {
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
		List<TeeUserBudgetModel> monthList = new ArrayList<TeeUserBudgetModel>();
		
		
		if(model.getUserId()>0){
			List<TeeUserBudget> list = dao.getObjList(model);
			if(list != null && list.size()>0){
				for(TeeUserBudget userBudget:list){
					if(fistQuartersList.contains(userBudget.getMonth())){
						fistQuarter += userBudget.getAmount();
					}
					if(secondQuartersList.contains(userBudget.getMonth())){
						secondQuarter += userBudget.getAmount();
					}
					if(thirdQuartersList.contains(userBudget.getMonth())){
						thirdQuarter += userBudget.getAmount();
					}
					if(fourthQuartersList.contains(userBudget.getMonth())){
						fourthQuarter += userBudget.getAmount();
					}
					
					TeeUserBudgetModel budgetModel = parseReturnModel(userBudget);
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
	public TeeJson deleteObjById(Map requestMap, TeePerson loginPerson, TeeUserBudgetModel model) throws ParseException {
		TeeJson json = new TeeJson();
		String userId = (String) requestMap.get("userId");
		String year = (String) requestMap.get("year");
//		String sids = (String) requestMap.get("sids");
//		List<TeeUserBudget> userBudgetList = dao.getObjListByIds(sids);
//		if(userBudgetList != null && userBudgetList.size()>0){
//			for(TeeUserBudget userBudget:userBudgetList){
//				String year = userBudget.getYear();
//				int usertId = userBudget.getUser().getUuid();
//				TeeUserBudgetModel modelTemp = new TeeUserBudgetModel();
//				modelTemp.setUserId(usertId);
//				modelTemp.setYear(year);
//				dao.deleteObjByDeptIdAndYear(modelTemp);
//			}
//		}
		simpleDaoSupport.executeUpdate("delete from TeeUserBudget where user.uuid=? and year=?", new Object[]{Integer.parseInt(userId),year});
		json.setRtState(true);
		json.setRtMsg("操作 成功！");
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
	public TeeJson checkExist(Map requestMap, TeePerson loginPerson, TeeUserBudgetModel model) {
		TeeJson json = new TeeJson();
		String fromUserId = TeeStringUtil.getString((String) requestMap.get("userId"),"0");
		String fromYearId = TeeStringUtil.getString((String) requestMap.get("year"),"0");
		model.setUserId(Integer.parseInt(fromUserId));
		model.setYear(fromYearId);
		List<TeeUserBudget>  list = dao.checkExist(model);
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
	 * @function: 获取个人当月预算剩余金额
	 * @author: wyw
	 * @data: 2014年9月7日
	 * @param requestMap
	 * @param userId
	 * @return TeeJson
	 */
	public TeeJson getPersonBudgetCost(Map requestMap, int userId) {
		TeeJson json = new TeeJson();
		Calendar c = Calendar.getInstance();
		double personCost = dao.getUserBudgetCost(userId, c.get(Calendar.YEAR),c.get(Calendar.MONTH)+1);
		double regCost = dao.getRegBudgetCost(userId, c.get(Calendar.YEAR),c.get(Calendar.MONTH)+1);
		double returnAmount = personCost - regCost;
		Map map = new HashMap();
		map.put("returnAmount", returnAmount);
		
		json.setRtData(map);
		json.setRtMsg("操作 成功！");
		json.setRtState(true);
		return json;
	}
	

}
