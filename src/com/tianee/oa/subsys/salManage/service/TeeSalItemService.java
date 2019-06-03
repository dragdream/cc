package com.tianee.oa.subsys.salManage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.salManage.bean.TeeHrInsurancePara;
import com.tianee.oa.subsys.salManage.bean.TeeHrSalData;
import com.tianee.oa.subsys.salManage.bean.TeeSalAccount;
import com.tianee.oa.subsys.salManage.bean.TeeSalDataPerson;
import com.tianee.oa.subsys.salManage.bean.TeeSalItem;
import com.tianee.oa.subsys.salManage.dao.TeeHrInsuranceParaDao;
import com.tianee.oa.subsys.salManage.dao.TeeHrSalDao;
import com.tianee.oa.subsys.salManage.dao.TeeSalAccountDao;
import com.tianee.oa.subsys.salManage.dao.TeeSalDataPersonDao;
import com.tianee.oa.subsys.salManage.dao.TeeSalItemDao;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
@Service
public class TeeSalItemService extends TeeBaseService {
	@Autowired
	private TeeSalItemDao salItemDao;
	
	
	
	@Autowired
	private TeeSalDataPersonDao salDataDao;
	
	@Autowired
	private TeeHrInsuranceParaDao paraDao;
	
	@Autowired
	private TeeDeptDao deptDao;
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeSalAccountDao accountDao;
	
	/**
	 * 新建或者更新
	 * @param item
	 * @return
	 */
	public TeeJson addOrUpdate(TeeSalItem salItem){
		TeeJson json = new TeeJson();
		if(salItem.getSid() > 0){
			TeeSalItem oldItem = salItemDao.get(salItem.getSid());
			BeanUtils.copyProperties(salItem, oldItem , new String[]{"sid" , "accountId" ,"itemColumn"});
			long count = salItemDao.checkExtisItemName(salItem.getAccountId(), salItem);
			if(count > 0){
				json.setRtMsg("工资项目名称已存在，请重新输入！");
				json.setRtState(false);
			}else{
				if(oldItem.getpIncome()==1){
					simpleDaoSupport.executeUpdate("update TeeSalItem set pIncome=0 where accountId="+oldItem.getAccountId()+" and sid!="+oldItem.getSid(), null);
				}
				salItemDao.update(oldItem);
				json.setRtMsg("保存成功！");
				json.setRtState(true);
			}
		
		}else{
			json = saveSalItem( salItem);
		}
		return json;
	}
	/**
	 * 获取有效字段，未使用
	 * @param map
	 * @param item
	 * @return
	 */
	public String getUseColumn(Map map  , TeeSalItem item){
		List<TeeSalItem> list = salItemDao.salAllItemListByAccountId(item.getAccountId());
		String columnName = "";
//		List set = new ArrayList();
//		for (int i = 1; i <= 100; i++) {
//			set.add("s" + i);
//		}
//		for (int i = 0; i < list.size() ; i++) {
//			TeeSalItem itemObj = list.get(i);
//			String itemColumn = itemObj.getItemColumn();
//			set.remove(itemColumn);
//		}
//		if(set.size() > 0){
//			columnName = (String)set.get(0);
//		}
		if(list.size()>0){
			int max = 0;
			for(TeeSalItem item0:list){
				int num = TeeStringUtil.getInteger(item0.getItemColumn().replace("s", ""), 0);
				if(num>max){
					max=num;
				}
			}
//			TeeSalItem it = list.get(list.size()-1);
			max++;
			item.setSortNo(max);
			columnName = "s"+max;
		}else{
			columnName = "s1";
		}
		return columnName;
	}
	
	/**
	 * 获取工资项
	 * @author 
	 */
	public TeeSalItem getSalItem(String sid){
		
		TeeSalItem item = salItemDao.get(Integer.parseInt(sid));
		return item;
	}
	
	/**
	 * 添加工资项
	 * @author 兴涛
	 */
	public TeeJson saveSalItem(TeeSalItem salItem){
		TeeJson json = new TeeJson();
		long count = salItemDao.getCount(salItem.getAccountId());
		if(count >= 100){
			json.setRtMsg("不能超过100个自定义项！");
			json.setRtState(false);
		}else{
			count = salItemDao.checkExtisItemName(salItem.getAccountId(), salItem);
			if(count > 0){
				json.setRtMsg("工资项目名称已存在，请重新输入！");
				json.setRtState(false);
			}else{
				String itemColumn = getUseColumn(null, salItem);//获取有效的字段
				salItem.setItemColumn(itemColumn);
				if(salItem.getpIncome()==1){
					simpleDaoSupport.executeUpdate("update TeeSalItem set pIncome=0 where accountId="+salItem.getAccountId()+"", null);
				}
				salItemDao.save(salItem);
				json.setRtMsg("保存成功！");
				json.setRtState(true);
			}
			
		}
		return json;
	}
	
	/**
	 * 更新工资项
	 * @author 兴涛
	 */
	public void updateSalItem(TeeSalItem salItem){
		salItemDao.update(salItem);
	}

	/**
	 * 已定义工资项目
	 * @author 兴涛
	 * @return
	 */
	public List<TeeSalItem> salItemList(int accountId){
		List<TeeSalItem> list = new ArrayList<TeeSalItem>();
		list = salItemDao.salAllItemListByAccountId(accountId);
		return list;
	}
	
	/**
	 * 获取所有的，包括不显示的
	 * @return
	 */
	public List<TeeSalItem> salAllItemList(int accountId){
		List<TeeSalItem> list = new ArrayList<TeeSalItem>();
		list = salItemDao.salAllItemListByAccountId(accountId);
		return list;
	}
	
	
	
	/**
	 * 删除工资项
	 * @author 兴涛
	 */
	public void deleteSalItem(String sid){
		salItemDao.delete(Integer.parseInt(sid));
	}

	/**
	 * 
	 * @param deptId
	 * @param salYear
	 * @param salMonth
	 * @return
	 */
	public List<Map> querySalary(Map requestMap) {
		List<Map> listMap = new ArrayList<Map>();
		int deptId = TeeStringUtil.getInteger(requestMap.get("deptId"), 0);
		int salYear = TeeStringUtil.getInteger(requestMap.get("salYear"), 0);
		int salMonth = TeeStringUtil.getInteger(requestMap.get("salMonth"), 0);
		String userName = TeeStringUtil.getString(requestMap.get("userName"), "");
		int accountId = TeeStringUtil.getInteger(requestMap.get("accountId"), 0);///账套Id

		List<TeeHrInsurancePara> list = paraDao.hrParaList();
		List<TeePerson> personList = new ArrayList<TeePerson>();
		if(deptId>0){
			TeeDepartment dept = (TeeDepartment)deptDao.get(deptId);
			String  deptLevel = dept.getDeptParentLevel();
			if(TeeUtility.isNullorEmpty(deptLevel)){//如果是第一级部门
				deptLevel = dept.getGuid();
			}
		    personList = personDao.selectDeptAndChildDeptPerson(deptId,deptLevel);
		}else{
			personList = personDao.getAllUserNoDelete();
		}
		StringBuffer userIds = new StringBuffer();
		if(!TeeUtility.isNullorEmpty(personList)){
			for(TeePerson person :personList){
				userIds.append(person.getUuid()+",");
			}
		}
		if(userIds.toString().endsWith(",")){
			userIds.deleteCharAt(userIds.length()-1);
		}
		Map headerMap =this.getTableHeader(requestMap);
		listMap.add(headerMap);
		List<TeeSalDataPerson> salList = salDataDao.getAllSalaryData(userIds.toString(),salYear,salMonth,userName, accountId);
		if(!TeeUtility.isNullorEmpty(salList)){
			for(TeeSalDataPerson salData:salList){
				Map salMap = new HashMap();
				StringBuffer sb = new StringBuffer();
				sb.append(salData.getSid() +"*");
				sb.append(salData.getSalYear()+"年"+salData.getSalMonth()+"月"+"*");
				sb.append(salData.getUser().getUserName()+"*");
				List<TeeSalItem> itemList = salItemDao.salItemListByAccountId(accountId);
				if(!TeeUtility.isNullorEmpty(itemList)){
					for(TeeSalItem item:itemList){
						sb.append(this.getItemValue(item.getItemColumn(),salData)+"*");
					}
				}
				if(sb.toString().endsWith("*")){
					sb.deleteCharAt(sb.length()-1);
				}
				salMap.put("valueList", sb.toString());
				listMap.add(salMap);
			}
		}
		return listMap;
	}
	
	/**
	 * 查询薪资详情
	 * @param requestMap
	 * @return
	 */
	public Map querySalaryDetail(Map requestMap) {
		int sid = TeeStringUtil.getInteger(requestMap.get("sid"), 0);

		TeeSalDataPerson salData = salDataDao.getById(sid);
		requestMap.put("accountId", salData.getAccountId());
		
		Map headerMap =this.getTableHeader(requestMap);
		
		if(!TeeUtility.isNullorEmpty(salData)){
			
			StringBuffer sb = new StringBuffer();
			sb.append(salData.getSid() +"*");
			sb.append(salData.getSalYear()+"年"+salData.getSalMonth()+"月"+"*");
			sb.append(salData.getUser().getUserName()+"*");
			List<TeeSalItem> itemList = salItemDao.salItemListByAccountId(salData.getAccountId());
			if(!TeeUtility.isNullorEmpty(itemList)){
				for(TeeSalItem item:itemList){
					sb.append(this.getItemValue(item.getItemColumn(),salData)+"*");
				}
			}
			if(sb.toString().endsWith("*")){
				sb.deleteCharAt(sb.length()-1);
			}
			headerMap.put("valueList", sb.toString());
		}
		return headerMap;
	}
	
	
	public Map querySalaryBase(Map requestMap) {
		int deptId = TeeStringUtil.getInteger(requestMap.get("deptId"), 0);
		String userName = TeeStringUtil.getString(requestMap.get("userName"), "");
		int accountId = TeeStringUtil.getInteger(requestMap.get("accountId"), 0);///账套Id

		List<TeeHrInsurancePara> list = paraDao.hrParaList();
		List<TeePerson> personList = new ArrayList<TeePerson>();
		
		Map map =this.getTableHeaderBaseSetting(requestMap);
		String hql = "select sid as sid,accountId as accountId,user.userName as userName,allBase as allBase,sbBase as sbBase,gjjBase as gjjBase";
		
		List<TeeSalItem> itemList = salItemDao.salItemListByAccountId(accountId);
		for(TeeSalItem salItem:itemList){
			if(salItem.getItemColumn().equals("finalPayAmount")){
				continue;
			}
			hql+=","+salItem.getItemColumn()+" as "+salItem.getItemColumn();
		}
		
		hql+=" from TeeHrSalData where ";
		
		if(deptId>0){
			hql+=" user.dept.uuid="+deptId;
		}else{
			hql+=" user.deleteStatus='0' ";
		}
		hql+=" and accountId="+accountId+" order by sid asc";
		
		List<Map> salList = simpleDaoSupport.getMaps(hql, null);
		List valueList = new ArrayList();
		if(!TeeUtility.isNullorEmpty(salList)){
			StringBuffer sb = new StringBuffer();
			for(Map sal:salList){
				sb.append(sal.get("sid"));
				sb.append("*"+sal.get("userName"));
				sb.append("*"+sal.get("allBase"));
				sb.append("*"+sal.get("sbBase"));
				sb.append("*"+sal.get("gjjBase"));
				for(TeeSalItem salItem:itemList){
					sb.append("*"+sal.get(salItem.getItemColumn()));
				}
				valueList.add(sb.toString());
				sb.delete(0, sb.length());
			}
		}
		map.put("valueList", valueList);
		return map;
	}
	
	
	
	public void updateSalaryBase(Map requestMap) {
		String model = TeeStringUtil.getString(requestMap.get("model"));
		List<Map<String,String>> list = TeeJsonUtil.JsonStr2MapList(model);
		Set<String> keySet = null;
		String hql = "";
		for(Map<String,String> data:list){
			keySet = data.keySet();
			hql = "update TeeHrSalData set ";
			for(String key:keySet){
				if(!key.equals("sid")){
					hql+=key+"="+TeeStringUtil.getDouble(data.get(key), 0.00)+",";
				}
			}
			hql = hql.substring(0, hql.length()-1);
			hql+=" where sid="+data.get("sid");
			simpleDaoSupport.executeUpdate(hql, null);
		}
	}
	
	/**
	 * @author nieyi
	 * 获取数据列表表头
	 * @return
	 */
	public Map getTableHeader(Map requestDatas){
		Map headerMap = new HashMap();
		StringBuffer sb = new StringBuffer();
		sb.append("sid"+",");
		sb.append("月份"+",");
		sb.append("姓名"+",");
		int accountId = TeeStringUtil.getInteger(requestDatas.get("accountId"), 0);
		List<TeeSalItem> itemList = new ArrayList<TeeSalItem>();
		if(accountId > 0){
			itemList = salItemDao.salItemListByAccountId(accountId);
		}
		if(!TeeUtility.isNullorEmpty(itemList)){
			for(TeeSalItem item:itemList){
				sb.append(item.getItemName()+",");
			}
		}
		if(sb.toString().endsWith(",")){
			sb.deleteCharAt(sb.length()-1);
		}
		headerMap.put("tableHeaderName", sb.toString());
		return headerMap;
	}
	
	/**
	 * @author nieyi
	 * 获取数据列表表头
	 * @return
	 */
	public Map getTableHeaderBaseSetting(Map requestDatas){
		Map headerMap = new HashMap();
		StringBuffer sb = new StringBuffer();
		sb.append("sid"+",");
		sb.append("姓名"+",");
		sb.append("SYS_基本工资|allBase"+",");
		sb.append("社保基数|sbBase"+",");
		sb.append("公积金基数|gjjBase"+",");
		int accountId = TeeStringUtil.getInteger(requestDatas.get("accountId"), 0);
		List<TeeSalItem> itemList = new ArrayList<TeeSalItem>();
		if(accountId > 0){
			itemList = salItemDao.salItemListByAccountId(accountId);
		}
		//List<TeeSalItem> itemList =  salItemDao.salItemListByAccountId(0);
		if(!TeeUtility.isNullorEmpty(itemList)){
			for(TeeSalItem item:itemList){
				sb.append(item.getItemName()+"|"+item.getItemColumn()+"|"+item.getItemType()+",");
			}
		}
		if(sb.toString().endsWith(",")){
			sb.deleteCharAt(sb.length()-1);
		}
		headerMap.put("tableHeaderName", sb.toString());
		return headerMap;
	}
	
	/**
	 * @author nieyi
	 * 根据itemColumn和salData获取相应的value
	 * @param itemColumn
	 * @param salData
	 * @return
	 */
	public String getItemValue(String itemColumn,TeeSalDataPerson salData){
		String itemValue=salItemDao.getItemValue(itemColumn,salData);
		return itemValue;
	}

	/**
	 * @author nieyi
	 * 删除人员工资
	 * @param sids
	 */
	public void delSalaryInfo(String sids) {
		salItemDao.delSalaryInfo(sids);
	}

	/**
	 * 根据条件查询个人工资情况
	 * @param requestDatas
	 * @return
	 */
	public List<Map> querySalaryByCondition(Map requestDatas) {
		List<Map> result = new ArrayList();
		//获取人员所属账套
		TeePerson loginPerson = (TeePerson)requestDatas.get(TeeConst.LOGIN_USER);
		List<TeeSalAccount> listSalAccounts = simpleDaoSupport.find("select account from TeeSalAccount account where exists (select 1 from TeeSalDataPerson persond where persond.accountId=account.sid and persond.user.uuid ="+loginPerson.getUuid()+")", null);
		
		for(TeeSalAccount account:listSalAccounts){
			requestDatas.put("accountId", account.getSid());
			Map dataMap = new HashMap();
			Map headerMap =this.getTableHeader(requestDatas);
			List valueList = new ArrayList();
			dataMap.put("accountName", account.getAccountName());
			dataMap.put("tableHeaderName", headerMap);
			List<TeeSalDataPerson> salList = salDataDao.querySalaryByCondition(requestDatas);
			if(!TeeUtility.isNullorEmpty(salList)){
				for(TeeSalDataPerson salData:salList){
					StringBuffer sb = new StringBuffer();
					sb.append(salData.getSid() +"*");
					sb.append(salData.getSalYear()+"年"+salData.getSalMonth()+"月"+"*");
					sb.append(salData.getUser().getUserName()+"*");
//					sb.append(salData.getAllBase()+"*");
//					sb.append(salData.getPensionBase() +"*");
//					sb.append(salData.getPensionU() +"*");
//					sb.append(salData.getPensionP() +"*");
//					sb.append(salData.getMedicalBase()+"*");
//					sb.append(salData.getMedicalU() +"*");
//					sb.append(salData.getMedicalP() +"*");
//					sb.append(salData.getFertilityBase() +"*");
//					sb.append(salData.getFertilityU() +"*");
//					sb.append(salData.getUnemploymentBase() +"*");
//					sb.append(salData.getUnemploymentU() +"*");
//					sb.append(salData.getUnemploymentP() +"*");
//					sb.append(salData.getInjuriesBase() +"*");
//					sb.append(salData.getInjuriesU() +"*");
//					sb.append(salData.getHousingBase() +"*");
//					sb.append(salData.getHousingU() +"*");
//					sb.append(salData.getHousingP() +"*");
					
		
					List<TeeSalItem> itemList = new ArrayList<TeeSalItem>();
					if(account != null){
						itemList = salItemDao.salItemListByAccountId(account.getSid());
					}
					if(!TeeUtility.isNullorEmpty(itemList)){
						for(TeeSalItem item:itemList){
							sb.append(this.getItemValue(item.getItemColumn(),salData)+"*");
						}
					}
					if(sb.toString().endsWith("*")){
						sb.deleteCharAt(sb.length()-1);
					}
					valueList.add(sb.toString());
				}
			}
			dataMap.put("valueList", valueList);
			result.add(dataMap);
		}
		
		return result;
	}

	public List<Map> getSalaryTitle() {
		List<Map> mapList = new ArrayList<Map>();
		List<TeeHrInsurancePara> list = paraDao.hrParaList();
		Map headerMap = new HashMap();
		Map titleMap = new HashMap();
		StringBuffer sb = new StringBuffer();
		StringBuffer titles = new StringBuffer();
		sb.append("sid"+",");
		titles.append("sid"+",");
		sb.append("年份"+",");
		titles.append("salYear"+",");
		sb.append("月份"+",");
		titles.append("salMonth"+",");
		sb.append("用户名"+",");
		titles.append("userName"+",");
		if(!TeeUtility.isNullorEmpty(list)){
			TeeHrInsurancePara para = list.get(0);
			if(para.getYesOther()==1){
				sb.append("保险基数"+",");
				titles.append("allBase"+",");
				sb.append("养老保险"+",");
				titles.append("pensionBase"+",");
				sb.append("单位养老"+",");
				titles.append("pensionU"+",");
				sb.append("个人养老"+",");
				titles.append("pensionP"+",");
				sb.append("医疗保险"+",");
				titles.append("medicalBase"+",");
				sb.append("单位医疗"+",");
				titles.append("medicalU"+",");
				sb.append("个人医疗"+",");
				titles.append("medicalP"+",");
				sb.append("生育保险"+",");
				titles.append("fertilityBase"+",");
				sb.append("单位生育"+",");
				titles.append("fertilityU"+",");
				sb.append("失业保险"+",");
				titles.append("unemploymentBase"+",");
				sb.append("单位失业"+",");
				titles.append("unemploymentU"+",");
				sb.append("个人失业"+",");
				titles.append("unemploymentP"+",");
				sb.append("工伤保险"+",");
				titles.append("injuriesBase"+",");
				sb.append("单位工伤"+",");
				titles.append("injuriesU"+",");
				sb.append("住房公积金"+",");
				titles.append("housingBase"+",");
				sb.append("单位住房"+",");
				titles.append("housingU"+",");
				sb.append("个人住房"+",");
				titles.append("housingP"+",");
			}
		}
		List<TeeSalItem> itemList = salItemDao.salItemListByAccountId(0);
		if(!TeeUtility.isNullorEmpty(itemList)){
			for(TeeSalItem item:itemList){
				sb.append(item.getItemName()+",");
				titles.append(item.getItemColumn()+",");
			}
		}
		sb.append("实发金额"+",");
		titles.append("finalPayAmount"+",");
		if(sb.toString().endsWith(",")){
			sb.deleteCharAt(sb.length()-1);
		}
		if(titles.toString().endsWith(",")){
			titles.deleteCharAt(titles.length()-1);
		}
		headerMap.put("tableHeaderName", sb.toString());
		titleMap.put("titleFieldName", titles.toString());
		mapList.add(headerMap);
		mapList.add(titleMap);
		return mapList;
	}
	
	
	/**
	 * 获取当前登录人的工资账套
	 * @param person
	 * @return
	 */
	public TeeSalAccount getSalAccountByPerson(TeePerson person){
		List<TeeSalAccount> list = accountDao.getSalAccountByPersonId(person.getUuid());
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
}
