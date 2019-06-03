package com.tianee.oa.subsys.crm.core.customer.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.bean.TeeUserRoleType;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.model.TeeUserRoleModel;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.chances.bean.TeeCrmChances;
import com.tianee.oa.subsys.crm.core.chances.service.TeeCrmChancesService;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomer;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeExtendFiled;
import com.tianee.oa.subsys.crm.core.customer.dao.TeeCrmCustomerDao;
import com.tianee.oa.subsys.crm.core.customer.model.TeeCrmCustomerModel;
import com.tianee.oa.subsys.crm.core.drawback.bean.TeeCrmDrawback;
import com.tianee.oa.subsys.crm.core.order.bean.TeeCrmOrder;
import com.tianee.oa.subsys.crm.core.order.bean.TeeCrmReturnOrder;
import com.tianee.oa.subsys.crm.core.order.bean.TeeOrderProducts;
import com.tianee.oa.subsys.crm.core.order.bean.TeeReturnOrderProducts;
import com.tianee.oa.subsys.crm.core.order.service.TeeCrmOrderService;
import com.tianee.oa.subsys.crm.core.payback.bean.TeeCrmPayback;
import com.tianee.oa.subsys.crm.core.target.bean.TeeCrmDeptTarget;
import com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;


@Service
public class TeeCrmCustomerService extends TeeBaseService{
	@Autowired
	private TeeCrmCustomerDao customerInfoDao;
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeePersonService personService;
	
	@Autowired
	private TeeCrmChancesService chancesService;
	
	@Autowired
	private TeeCrmOrderService orderService;

	
	/**
	 * 判断客户名称是否已存在
	 * @param request
	 * @return
	 */
	public TeeJson isExistCustomNum(HttpServletRequest request){
		TeeJson json = new TeeJson();
		//客户id
		String sid=TeeStringUtil.getString(request.getParameter("sid"),"0");
		//客户编号
		String customerName = TeeStringUtil.getString(request.getParameter("customerName"), null);
		long count= 0;
		if(sid!=null&&!("").equals(sid)&&!("0").equals(sid)){//编辑
		    count = simpleDaoSupport.count("select count(sid) from TeeCrmCustomer where customerName = ? and sid!= ?", new Object[]{customerName,Integer.parseInt(sid)});
		}else{//新增
			count = simpleDaoSupport.count("select count(sid) from TeeCrmCustomer where customerName = ?", new Object[]{customerName});
		}
		if(count>0){
	    	json.setRtData(1);
		}else{
			json.setRtData(0);
	    }
		
		json.setRtState(true);
    	return json;
	}

	/**
	 * 保存或更新
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeCrmCustomerModel model) {
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		TeePerson person = (TeePerson)personDao.get(model.getManagePersonId());
		TeeCrmCustomer customerInfo = new TeeCrmCustomer();
		//int[] sharePersonIds = TeeStringUtil.parseIntegerArray(model.getSharePersonIds());
		List<TeePerson> personList = personDao.getPersonByUuids(TeeStringUtil.getString(model.getSharePersonIds(), "0"));
		if(model.getSid() > 0){
			
			//删除中间表中之前的数据
    		//simpleDaoSupport.executeNativeUpdate(" delete from customer_persons where customer_id= ? ", new Object[]{model.getSid()});
    		
    		Calendar cl = Calendar.getInstance();
			customerInfo= customerInfoDao.get(model.getSid());
			
			if(customerInfo != null){
				BeanUtils.copyProperties(model, customerInfo);
				customerInfo.setManagePerson(person);
				customerInfo.setLocateInformation(model.getAddressDesc());
				customerInfo.setAddTime(cl);
				customerInfo.setManagePerson(person);
				customerInfo.setCustomerStatus(1);
				customerInfo.setDealStatus(0);
				customerInfo.setAddPerson(loginPerson);
				if(personList != null && personList.size()>0){
					customerInfo.getSharePerson().clear();
					//System.out.println(customerInfo.getSharePerson()+"[[[[[[[");
					customerInfo.getSharePerson().addAll(personList);
				}else{
					customerInfo.setSharePerson(new HashSet<TeePerson>());
				}
				
				customerInfoDao.updateCustomerInfo(customerInfo);
				int sid = model.getSid();
				//处理自定义字段
				Map map = TeeServletUtility.getParamMap(request);
				Map<String,String> mapExtra = new HashMap<String,String>();
				Set<String> set = map.keySet();
				for (String key : set) {
					if(key.startsWith("EXTRA_")){
						mapExtra.put(key,(String) map.get(key));
					}
				}
				List  list=new ArrayList();
				if(mapExtra.keySet().size()>0){
					String sql= "update CRM_CUSTOMER set";
					int count = 0;
					for (String key2 : mapExtra.keySet()) {
						count++;
						if(count==mapExtra.keySet().size()){
							sql += " "+ key2+" = ?";
						}else{
							sql +=" "+ key2+" = ? ,";
						}
						list.add(mapExtra.get(key2));
					}
					sql += " "+"where sid = "+sid;
					
					simpleDaoSupport.executeNativeUpdate(sql,list.toArray());
				}
				json.setRtMsg("编辑成功！");
	
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到相关客户信息！");
				return json;
			}
			
		}else{
			Calendar cl = Calendar.getInstance();
			BeanUtils.copyProperties(model, customerInfo);
			customerInfo.setLocateInformation(model.getAddressDesc());
			customerInfo.setAddTime(cl);
			customerInfo.setManagePerson(person);
			customerInfo.setCustomerStatus(1);
			customerInfo.setDealStatus(0);
			customerInfo.setAddPerson(loginPerson);
			if(personList != null && personList.size()>0){
				customerInfo.getSharePerson().clear();
				customerInfo.getSharePerson().addAll(personList);
			}else{
				customerInfo.setSharePerson(new HashSet<TeePerson>());
			}
			simpleDaoSupport.save(customerInfo);
			
			int sid = customerInfo.getSid();
			//处理自定义字段
			Map map = TeeServletUtility.getParamMap(request);
			Map<String,String> mapExtra = new HashMap<String,String>();
			Set<String> set = map.keySet();
			for (String key : set) {
				if(key.startsWith("EXTRA_")){
					mapExtra.put(key,(String) map.get(key));
				}
			}
			List  list=new ArrayList();
			if(mapExtra.keySet().size()>0){
				String sql= "update CRM_CUSTOMER set";
				int count = 0;
				for (String key2 : mapExtra.keySet()) {
					count++;
					if(count==mapExtra.keySet().size()){
						sql += " "+ key2+" = ?";
					}else{
						sql +=" "+ key2+" = ? ,";
					}
					list.add(mapExtra.get(key2));
				}
				sql += " "+"where sid = "+sid;
				simpleDaoSupport.executeNativeUpdate(sql,list.toArray());
			}
			json.setRtMsg("添加成功！");
		}
		
		json.setRtState(true);
		return json;
	}

	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm, Map requestDatas) {
		TeePerson person = (TeePerson)requestDatas.get("LOGIN_USER");
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String customerName = (String) requestDatas.get("customerName");//客户名称
		String managePersonName = (String) requestDatas.get("managePersonName");//负责人
		String manageUserId = (String) requestDatas.get("managePersonId");
		String customerStatusDesc = (String) requestDatas.get("customerStatus"); //客户状态
		String customerTypeDesc = (String) requestDatas.get("customerType");//客户级别
		String customerSourceDesc = (String) requestDatas.get("customerSource");//客户来源
		String industryDesc = (String) requestDatas.get("industry");//所属行业
		String customerNum = (String) requestDatas.get("customerNum");//客户编号
		String companyAddress = (String) requestDatas.get("companyAddress");//公司地址
		String types=(String)requestDatas.get("type");//type=2 代表所有的客户，type =1 共享客户  没有type代表我的客户
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List param = new ArrayList();
		String sql = "";
		String sql2 ="select * from CRM_CUSTOMER C where 1=1";
		if(!TeeUtility.isNullorEmpty(customerName)){//客户名称
				sql += " and C.CUSTOMER_NAME like ?";
				param.add("%"+customerName+"%");
		}
		if(!TeeUtility.isNullorEmpty(manageUserId)&& !"0".equals(manageUserId)){//负责人
			TeePerson managePerson = (TeePerson) simpleDaoSupport.get(TeePerson.class, Integer.parseInt(manageUserId));
			if(!TeeUtility.isNullorEmpty(managePerson)){
				sql+=" and C.MANAGER_USER_ID= ?";
				param.add(manageUserId);
			}
		}
		if(!TeeUtility.isNullorEmpty(customerStatusDesc)){//状态
			if(!"0".equals(customerStatusDesc)){
				sql+=" and C.CUSTOMER_STATUS = ?";
				param.add(Integer.parseInt(customerStatusDesc));
			}else{
				sql +=" ";
			}
		}
		if(!TeeUtility.isNullorEmpty(industryDesc) && !"0".equals(industryDesc)){//所属行业
			sql+=" and C.INDUSTRY_TYPE=?";
			param.add(industryDesc);
		}
		if(!TeeUtility.isNullorEmpty(customerTypeDesc) && !"0".equals(customerTypeDesc)){//客户级别
			sql+=" and C.CUSTOMER_TYPE = ?";
			param.add(customerTypeDesc);
		}
		if(!TeeUtility.isNullorEmpty(customerSourceDesc) && !"0".equals(customerSourceDesc)){//客户来源
			sql+=" and C.CUSTOMER_SOURCE = ?";
			param.add(customerSourceDesc);
		}
		if(!TeeUtility.isNullorEmpty(customerNum)){//客户编号
			sql += " and C.CUSTOMER_NUM like ?";
			param.add("%"+customerNum+"%");
	    }
		if(!TeeUtility.isNullorEmpty(companyAddress)){//公司地址
			sql += " and C.COMPANY_ADDRESS like ?";
			param.add("%"+companyAddress+"%");
	    }
		//获取自定义 字段查询
		Map customMap=requestDatas;
		for (Object obj : customMap.keySet()) {
			String str=TeeStringUtil.getString(obj);
			if(str.startsWith("EXTRA_")){
				int filedId=TeeStringUtil.getInteger(str.substring(6,str.length()), 0);
				if(filedId>0){
					TeeExtendFiled field=(TeeExtendFiled) simpleDaoSupport.get(TeeExtendFiled.class,filedId);
					
					if(("单行输入框").equals(field.getFiledType())||("多行输入框").equals(field.getFiledType())){
						String value=(String) customMap.get(str);
						if(!("").equals(value)){
							sql+=" and C."+str+" like ? ";
							param.add("%"+value+"%");
						}
					}else if(("下拉列表").equals(field.getFiledType())){
						String value=(String) customMap.get(str);
						if(!("").equals(value)){
							sql+=" and C."+str+" = ? ";
							param.add(value);
						}
					}	
				}	
			}
		}
		if(!TeeUtility.isNullorEmpty(types)){
			if("2".equals(types)){//type =2我负责的
				sql +=" and C.MANAGER_USER_ID = "+person.getUuid()+"";
			}else if("1".equals(types)){//type=1 代表全部
				sql +="";
			}else if("3".equals(types)){//type=3代表我下属负责的
			    List<TeePerson> underPersonList = personService.getUnderlines(person.getUuid());
			    String underPersonIds = "";
			    if(!TeeUtility.isNullorEmpty(underPersonList)){
			    	for (TeePerson teePerson : underPersonList) {
			    		underPersonIds+=teePerson.getUuid()+",";
					}
			    	if(!TeeUtility.isNullorEmpty(underPersonIds)){
			    		if(underPersonIds.endsWith(",")){
			    			underPersonIds=underPersonIds.substring(0, underPersonIds.length()-1);
			    		}
			    		sql+=" and C.MANAGER_USER_ID in ("+ underPersonIds+")";
			    		
			    	}else{
			    		sql+=" and C.MANAGER_USER_ID is null";
				    }
			    }
			}
		}/*else{//  没有type代表我的客户
			sql +=" and (customer.managePerson.uuid="+person.getUuid()+" or exists (select 1 from customer.sharePerson sharePerson where sharePerson.uuid = "+person.getUuid()+"))";
		}*/
		long total = simpleDaoSupport.countSQLByList("select count(*) from CRM_CUSTOMER C where 1=1"+ sql +" order by C.ADD_TIME desc", param);
		dataGridJson.setTotal(total);
		List<Map> resultList = simpleDaoSupport.executeNativeQuery(sql2+sql +" order by C.ADD_TIME desc", param.toArray(), (dm.getPage() - 1) * dm.getRows(),  dm.getRows());
		List<Map> rows=new ArrayList<Map>();
		Map map=null;
		if(resultList!=null){
			TeePerson manager=null;
			List<TeeExtendFiled> list1=null;
			for (Map m: resultList) {
				map=new HashMap();
		    	list1=simpleDaoSupport.executeQuery(" from TeeExtendFiled where 1=1 ", null);
		    	if(list1!=null){
		    		for (TeeExtendFiled field : list1) {
						if(("单行输入框").equals(field.getFiledType())||("多行输入框").equals(field.getFiledType())){
							map.put("EXTRA_"+field.getSid(),m.get("EXTRA_"+field.getSid()));
						}else if(("下拉列表").equals(field.getFiledType())){
							if(("CRM系统编码").equals(field.getCodeType())){
								 String value =field.getSysCode();
								 String name=TeeCrmCodeManager.getChildSysCodeNameCodeNo(value,(String)(m.get("EXTRA_"+field.getSid())));
								 map.put("EXTRA_"+field.getSid(),name);
							}else if(("自定义选项").equals(field.getCodeType())){
								 String[] optionNames = field.getOptionName().split(",");
								 String[] optionValues = field.getOptionValue().split(",");
								 for (int i=0;i<optionValues.length;i++) {
									 if(optionValues[i].equals(m.get("EXTRA_"+field.getSid()))){
										 map.put("EXTRA_"+field.getSid(),optionNames[i]);
										 break;
									 }
								 }
							}
						}
					}
		    	}
		    	
		    	
				String sid=TeeStringUtil.getString(m.get("SID"));
				map.put("sid", sid);
				map.put("customerName", m.get("CUSTOMER_NAME"));
				map.put("customerNum", m.get("CUSTOMER_NUM"));
				map.put("companyAddress", m.get("COMPANY_ADDRESS"));
				map.put("companyPhone", m.get("COMPANY_PHONE"));
				map.put("companyZipCode", m.get("COMPANY_ZIPCODE"));
				map.put("companyUrl", m.get("COMPANY_URL"));
				map.put("addressDesc", m.get("LOCATE_INFORMATION"));
				//获取负责人
				int managePersonId=(Integer) m.get("MANAGER_USER_ID");
				if(managePersonId!=0){
					manager=(TeePerson) simpleDaoSupport.get(TeePerson.class,managePersonId);
					map.put("managePersonName", manager.getUserName());
				}else{
					map.put("managePersonName", null);
				}
				//获取创建人
				int addPersonId = (Integer) m.get("ADD_PERSON_ID");
				TeePerson addPerson = (TeePerson) simpleDaoSupport.get(TeePerson.class, addPersonId);
				String addPersonName = addPerson.getUserName();
				map.put("addPersonId", addPersonId);
				map.put("addPersonName", addPersonName);
				map.put("addTime", sdf.format((Date)m.get("ADD_TIME")));
				if(!TeeUtility.isNullorEmpty(m.get("PROVINCE"))){
					 map.put("address", TeeStringUtil.getString(m.get("PROVINCE"))+"/"+TeeStringUtil.getString(m.get("CITY"))+"/"+TeeStringUtil.getString(m.get("DISTRICT")));
				 }else{
					 map.put("address",null);
				 }
				//获取客户状态
				String customerStatus = "";
				int status = (Integer) m.get("CUSTOMER_STATUS") ;
					if(status==1){
						customerStatus="已分配";
					}else if(status== -1){
						customerStatus="未分配";
					}else if(status==2){
						customerStatus="已作废";
					}
				map.put("customerStatus", customerStatus);
				//获取交易状态
				String dealStatus = "";
				int status2 =(Integer) m.get("DEAL_STATUS") ;
					if(status2==1){
						dealStatus="已成交";
					}else{
						dealStatus="未成交";
					}
				map.put("dealStatus", dealStatus);
				//获取CRM编码值
				String customerType=TeeCrmCodeManager.getChildSysCodeNameCodeNo("CUSTOMER_TYPE", (String) m.get("CUSTOMER_TYPE"));
				map.put("customerType", customerType);
				String customerSource = TeeCrmCodeManager.getChildSysCodeNameCodeNo("CUSTOMER_SOURCE", (String) m.get("CUSTOMER_SOURCE"));
				map.put("customerSource", customerSource);
				String industryType = TeeCrmCodeManager.getChildSysCodeNameCodeNo("INDUSTRY_TYPE", (String) m.get("INDUSTRY_TYPE"));
				map.put("industryType", industryType);
				String companyScale= TeeCrmCodeManager.getChildSysCodeNameCodeNo("COMPANY_SCALE", (String) m.get("COMPANY_SCALE"));
				map.put("companyScale", companyScale);
				//获取客户性质
				String type = "";
				int cusType =(Integer) m.get("TYPE") ;
					if(cusType==1){
						type="客户";
					}else if(cusType==2){
						type="供应商";
					}
				map.put("type", type);
				String unitType= TeeCrmCodeManager.getChildSysCodeNameCodeNo("UNIT_TYPE", (String) m.get("UNIT_TYPE"));
				map.put("unitType", unitType);

				rows.add(map);
			}
		
		}
		dataGridJson.setRows(rows);
		return dataGridJson;
	
	}

	/**
	 * 获取客户详情
	 * @param request
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TeeJson json = new TeeJson();
		List<Map> list = new ArrayList();
		String sid=TeeStringUtil.getString(request.getParameter("sid"));
		TeePerson addPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		if(!("").equals(sid)){
			TeeCrmCustomer customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, Integer.parseInt(sid));
			if(!TeeUtility.isNullorEmpty(customer)){
					 
					 Map map = new HashMap();
					 String sql = "select * from CRM_CUSTOMER C where C.SID = ? ";
					 map =simpleDaoSupport.executeNativeUnique(sql, new Object[]{sid});
					 
					 String sql2="select P.UUID, P.USER_NAME from PERSON P where P.UUID in  (select CP.PERSON_ID from CUSTOMER_PERSONS CP where CP.CUSTOMER_ID = ?)";
					 list = simpleDaoSupport.executeNativeQuery(sql2, new Object[]{sid}, 0, Integer.MAX_VALUE);
					 //获取共享人
					 String sharePersonIds="";
					 String sharePersonNames="";
					 if(list!=null){
						 for (int i = 0; i < list.size(); i++) {
							 sharePersonIds += list.get(i).get("UUID")+",";
							 sharePersonNames += list.get(i).get("USER_NAME")+",";
						 }
						 
						 if(sharePersonIds.endsWith(",")){
							 sharePersonIds=sharePersonIds.substring(0, sharePersonIds.length()-1);
						 }
						 if(sharePersonNames.endsWith(",")){
							 sharePersonNames=sharePersonNames.substring(0, sharePersonNames.length()-1);
						 }
						 map.put("sharePersonIds", sharePersonIds);
						 map.put("sharePersonNames", sharePersonNames);
					 }
					 
					 //获取负责人
					 int managePersonId =(Integer) map.get("MANAGER_USER_ID");
					 if(0!=managePersonId){
						 TeePerson managerPerson = (TeePerson) simpleDaoSupport.get(TeePerson.class, managePersonId);
						 String managePersonName = managerPerson.getUserName();
						 map.put("managePersonName", managePersonName);
						 map.put("managePersonId", managePersonId);
					 }else{
						 map.put("managePersonName", null);
						 map.put("managePersonId", 0);
					 }
					 //获取创建人
					 map.put("addPersonId", addPerson.getUuid());
					 map.put("addPersonName", addPerson.getUserName());
					 map.put("addTime", sdf.format((Date)map.get("ADD_TIME")));
					 if(!TeeUtility.isNullorEmpty(map.get("PROVINCE"))){
						 map.put("address", TeeStringUtil.getString(map.get("PROVINCE"))+"/"+TeeStringUtil.getString(map.get("CITY"))+"/"+TeeStringUtil.getString(map.get("DISTRICT")));
					 }else{
						 map.put("address",null);
					 }
					 //获取客户状态
					 String customerStatus = "";
					 int status = (Integer) map.get("CUSTOMER_STATUS") ;
					 if(status==1){
						 customerStatus="已分配";
					 }else if(status== -1){
						 customerStatus="未分配";
					 }else if(status==2){
						 customerStatus="已作废";
					 }
					 map.put("customerStatus", customerStatus);
					 //获取交易状态
					 String dealStatus = "";
					 int status2 =(Integer) map.get("DEAL_STATUS") ;
					 if(status2==1){
						 dealStatus="已成交";
					 }else{
						 dealStatus="未成交";
					 }
					 map.put("dealStatus", dealStatus);
					 //获取CRM编码值
					 String customerType=TeeCrmCodeManager.getChildSysCodeNameCodeNo("CUSTOMER_TYPE", (String) map.get("CUSTOMER_TYPE"));
					 map.put("customerType", customerType);
					 String customerSource = TeeCrmCodeManager.getChildSysCodeNameCodeNo("CUSTOMER_SOURCE", (String) map.get("CUSTOMER_SOURCE"));
					 map.put("customerSource", customerSource);
					 String industryType = TeeCrmCodeManager.getChildSysCodeNameCodeNo("INDUSTRY_TYPE", (String) map.get("INDUSTRY_TYPE"));
					 map.put("industryType", industryType);
					 String companyScale= TeeCrmCodeManager.getChildSysCodeNameCodeNo("COMPANY_SCALE", (String) map.get("COMPANY_SCALE"));
					 map.put("companyScale", companyScale);
					 //获取客户性质
					 String type = "";
					 int cusType =(Integer) map.get("TYPE") ;
					 if(cusType==1){
						 type="客户";
					 }else if(cusType==2){
						 type="供应商";
					 }else{
						 type=" ";
					 }
					 map.put("type", type);
					 String unitType= TeeCrmCodeManager.getChildSysCodeNameCodeNo("UNIT_TYPE", (String) map.get("UNIT_TYPE"));
					 map.put("unitType", unitType);
					 
					 // 获取自定义字段的值
					 List<TeeExtendFiled> extendList=simpleDaoSupport.executeQuery(" from TeeExtendFiled where 1=1 ", null);
					 if(extendList!=null){
						 for (TeeExtendFiled field : extendList) {
							 if(("单行输入框").equals(field.getFiledType())||("多行输入框").equals(field.getFiledType())){
								 map.put("EXTRA_"+field.getSid(),map.get("EXTRA_"+field.getSid()));
							 }else if(("下拉列表").equals(field.getFiledType())){
								 if(("CRM系统编码").equals(field.getCodeType())){
									 String value =field.getSysCode();
									 String name=TeeCrmCodeManager.getChildSysCodeNameCodeNo(value,(String)(map.get("EXTRA_"+field.getSid())));
									 map.put("EXTRA_"+field.getSid(),name);
								 }else if(("自定义选项").equals(field.getCodeType())){
									 String[] optionNames = field.getOptionName().split(",");
									 String[] optionValues = field.getOptionValue().split(",");
									 for (int i=0;i<optionValues.length;i++) {
										 if(optionValues[i].equals(map.get("EXTRA_"+field.getSid()))){
											 map.put("EXTRA_"+field.getSid(),optionNames[i]);
											 break;
										 }
									 }
								 }
							 }
							 
						 }
					 }
					 
					 //获取商机总额
					   List<TeeCrmChances> listForecast =simpleDaoSupport.executeQuery(" from TeeCrmChances where 1=1 and crUser.uuid = chanceManagePerson.uuid and  crUser.uuid ="+addPerson.getUuid() +" and customer.sid =?", new Object[]{Integer.parseInt(sid)});
						double forecast =0;
						if(!TeeUtility.isNullorEmpty(listForecast)){
							for (TeeCrmChances teeCrmChances : listForecast) {
								forecast +=teeCrmChances.getForcastCost();
							}
						}
						map.put("forecast", forecast);//商机总金额
						
						//获取订单总金额
						List<TeeCrmOrder> listOrder = simpleDaoSupport.executeQuery(" from TeeCrmOrder where 1=1 and addPerson.uuid = orderManagePerson.uuid and addPerson.uuid ="+addPerson.getUuid() +" and orderStatus = 2 and customer.sid =?",  new Object[]{Integer.parseInt(sid)});
						List<TeeOrderProducts> listOp = null;
						double orderCount =0;
						if(!TeeUtility.isNullorEmpty(listOrder)){
							for (TeeCrmOrder orders : listOrder) {
								listOp= simpleDaoSupport.executeQuery("from TeeOrderProducts where order.sid = ?",new Object[]{orders.getSid()});
							    if(!TeeUtility.isNullorEmpty(listOp)){
							    	for (TeeOrderProducts teeOrderProducts : listOp) {
										orderCount +=teeOrderProducts.getTotalAmount();
									}
							    }
							}
						}
						map.put("orderCount", orderCount);//订单总金额
						
						//退货总额
						List<TeeCrmReturnOrder> listReturnOrder = simpleDaoSupport.executeQuery(" from TeeCrmReturnOrder where 1=1 and addPerson.uuid = retOrderManagePerson.uuid and addPerson.uuid ="+addPerson.getUuid() +" and returnOrderStatus = 2 and customer.sid =?",  new Object[]{Integer.parseInt(sid)});
						List<TeeReturnOrderProducts> listRop = null;
						double returnOrderCount =0;
						if(!TeeUtility.isNullorEmpty(listReturnOrder)){
							for (TeeCrmReturnOrder returnOrders : listReturnOrder) {
								listRop= simpleDaoSupport.executeQuery("from TeeReturnOrderProducts where returnOrder.sid = ?",new Object[]{returnOrders.getSid()});
							    if(!TeeUtility.isNullorEmpty(listRop)){
							    	for (TeeReturnOrderProducts returnOrderProducts : listRop) {
							    		returnOrderCount +=returnOrderProducts.getTotalAmount();
									}
							    }
							}
						}
						map.put("returnOrderCount", returnOrderCount);//退货单总金额
						
						//回款总金额
						List<TeeCrmPayback> listPayback =simpleDaoSupport.executeQuery(" from TeeCrmPayback where 1=1 and createUser.uuid = responsibleUser.uuid and  createUser.uuid ="+addPerson.getUuid() +" and customer.sid =? and paybackStatus = 2", new Object[]{Integer.parseInt(sid)});
						double payBackCount =0;
						if(!TeeUtility.isNullorEmpty(listPayback)){
							for (TeeCrmPayback payback : listPayback) {
								payBackCount +=payback.getPaybackAmount();
							}
						}
						map.put("payBackCount", payBackCount);//回款总金额
						
						//退款总金额
						List<TeeCrmDrawback> listDrawback =simpleDaoSupport.executeQuery(" from TeeCrmDrawback where 1=1 and createUser.uuid = responsibleUser.uuid and  createUser.uuid ="+addPerson.getUuid() +" and customer.sid =? and drawbackStatus = 2", new Object[]{Integer.parseInt(sid)});
						double drawBackCount =0;
						if(!TeeUtility.isNullorEmpty(listDrawback)){
							for (TeeCrmDrawback drawback : listDrawback) {
								drawBackCount +=drawback.getDrawbackAmount();
							}
						}
						map.put("drawBackCount", drawBackCount);//退款总金额
						
						//待回款总金额
						List<TeeCrmPayback> listPayback2 =simpleDaoSupport.executeQuery(" from TeeCrmPayback where 1=1 and createUser.uuid = responsibleUser.uuid and  createUser.uuid ="+addPerson.getUuid() +" and customer.sid =? and paybackStatus = 1", new Object[]{Integer.parseInt(sid)});
						double payBackCount2 =0;
						if(!TeeUtility.isNullorEmpty(listPayback2)){
							for (TeeCrmPayback payback : listPayback2) {
								payBackCount2 +=payback.getPaybackAmount();
							}
						}
						map.put("payBackCount2", payBackCount2);//待回款总金额
					 
					 json.setRtData(map);
					 json.setRtState(true);
					 json.setRtMsg("查询成功！");
				// }
			}else{
				json.setRtState(false);
				json.setRtMsg("数据不存在！");
			}
			
			}
		
		return json;
	}

	/**
	 * 根据客户id获取客户信息
	 * @param request
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TeeJson json = new TeeJson();
		List<Map> list = new ArrayList();
		String sid=TeeStringUtil.getString(request.getParameter("sid"));
		if(!("").equals(sid)){
			Map map=new HashMap();
			String sql = "select * from crm_customer C where C.sid = ? ";
			Map data =simpleDaoSupport.executeNativeUnique(sql, new Object[]{sid});
			map.put("customerName", data.get("CUSTOMER_NAME"));
			map.put("customerNum", data.get("CUSTOMER_NUM"));
			map.put("companyAddress", data.get("COMPANY_ADDRESS"));
			map.put("companyPhone", data.get("COMPANY_PHONE"));
			map.put("companyZipCode", data.get("COMPANY_ZIPCODE"));
			map.put("companyUrl", data.get("COMPANY_URL"));
			map.put("addressDesc", data.get("LOCATE_INFORMATION"));
			
			String sql2="select P.UUID, P.USER_NAME from PERSON P where P.UUID in  (select CP.PERSON_ID from CUSTOMER_PERSONS CP where CP.CUSTOMER_ID = ?)";
			list = simpleDaoSupport.executeNativeQuery(sql2, new Object[]{sid}, 0, Integer.MAX_VALUE);
			//获取共享人
			String sharePersonIds="";
			String sharePersonNames="";
			if(list!=null){
			   for (int i = 0; i < list.size(); i++) {
						sharePersonIds += list.get(i).get("UUID")+",";
						sharePersonNames += list.get(i).get("USER_NAME")+",";
						
			   }
			   if(sharePersonIds.endsWith(",")){
					sharePersonIds=sharePersonIds.substring(0, sharePersonIds.length()-1);
				}
				if(sharePersonNames.endsWith(",")){
					sharePersonNames=sharePersonNames.substring(0, sharePersonNames.length()-1);
				}
			   
			   map.put("sharePersonIds", sharePersonIds);
			   map.put("sharePersonNames", sharePersonNames);
			}
			
			//获取负责人
			int managePersonId =(Integer) data.get("MANAGER_USER_ID");
			if(TeeStringUtil.getInteger(managePersonId, 0)!=0){
			   TeePerson managerPerson = (TeePerson) simpleDaoSupport.get(TeePerson.class, managePersonId);
			   String managePersonName = managerPerson.getUserName();
			   map.put("managePersonName", managePersonName);
			   map.put("managePersonId", managePersonId);
			}else{
				   map.put("managePersonName", null);
				   map.put("managePersonId", 0);
			}
			//获取创建人
			int addPersonId = (Integer) data.get("ADD_PERSON_ID");
			if(TeeStringUtil.getInteger(addPersonId, 0)!=0){
			   TeePerson addPerson = (TeePerson) simpleDaoSupport.get(TeePerson.class, addPersonId);
			   String addPersonName = addPerson.getUserName();
			   map.put("addPersonId", addPersonId);
			   map.put("addPersonName", addPersonName);
			}
			map.put("addTime", sdf.format((Date)data.get("ADD_TIME")));
		 	map.put("province", data.get("PROVINCE"));
	    	map.put("city", data.get("CITY"));
	    	map.put("district", data.get("DISTRICT"));
	    	
			map.put("customerType", data.get("CUSTOMER_TYPE"));
			map.put("customerSource", data.get("CUSTOMER_SOURCE"));
			map.put("industry", data.get("INDUSTRY_TYPE"));
			map.put("companyScale",  data.get("COMPANY_SCALE"));
			map.put("unitType",  data.get("UNIT_TYPE"));
	        map.put("type", data.get("TYPE"));
	      	//获取自定义字段的值
	    	for(Object obj:data.keySet()){
	    		String name=(String)obj;
	    		if(name.startsWith("EXTRA_")){
	    			map.put(name,data.get(name));
	    		}
	    	}
    	
		json.setRtData(map);
		json.setRtState(true);
		json.setRtMsg("查询成功！");
		
		}else{
		    	json.setRtState(false);
		    	json.setRtMsg("数据获取失败！");
		    }
		return json;
	}

    /**
     * 更换负责人
     * @param request
     * @return
     */
	public TeeJson changeManage(HttpServletRequest request) {
		 TeeJson json = new TeeJson();
		 int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		 int managerPerId=TeeStringUtil.getInteger(request.getParameter("managerPerId"), 0);
		 simpleDaoSupport.executeNativeUpdate(" update CRM_CUSTOMER C set MANAGER_USER_ID = ? where SID = ? ", new Object[]{managerPerId,sid});
		 json.setRtState(true);
		 json.setRtMsg("操作成功！");
		 return json;
	}

	/**
	 * 更改客户状态为作废
	 * @param request
	 * @return
	 */
	public TeeJson cancel(HttpServletRequest request) {
		TeeJson json =  new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		int status=TeeStringUtil.getInteger(request.getParameter("customerStatus"), 0);
		TeeCrmCustomer customer = customerInfoDao.get(sid);
		if(!("").equals(customer)){
			//未作废过-更改状态为作废
			if(status!=customer.getCustomerStatus()){
				simpleDaoSupport.executeNativeUpdate(" update CRM_CUSTOMER C set CUSTOMER_STATUS = ? where SID = ? ", new Object[]{status,sid});
				json.setRtMsg("操作成功！");
				json.setRtState(true);
			}else{
				json.setRtMsg("已作废的不可重复作废！");
				json.setRtState(false);
			}
			
		}else{
			json.setRtState(false);
			json.setRtMsg("数据不存在！");
		}
		return json;
	}

	/**
	 * 删除客户   (只有作废的对象才支持删除)
	 * @param request
	 * @return
	 */
	public TeeJson delCustomerById(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String sids = TeeStringUtil.getString(request.getParameter("sids"), "0");
		if(!TeeUtility.isNullorEmpty(sids)){
			TeeCrmCustomer customer = null;
			//单个删除
			if(sids.indexOf(",") == -1){
				customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, Integer.parseInt(sids));
				if(!("").equals(customer)){
					int customerStatus = customer.getCustomerStatus();
						if(customerStatus!=2){//判断当前客户是否已作废
							json.setRtState(false);
							json.setRtMsg("只有作废的对象才支持删除！");
						}else{
							//删除中间表中的数据
							simpleDaoSupport.executeNativeUpdate(" delete from CUSTOMER_PERSONS  where CUSTOMER_ID= ? ", new Object[]{Integer.parseInt(sids)});
							
							//删除对应客户下的联系人数据
							simpleDaoSupport.executeNativeUpdate(" delete from CRM_CONTACTS where CUSTOMER_ID = ?", new Object[]{Integer.parseInt(sids)});
							//删除对应客户下的商机数据
							chancesService.delChanceByCustomerId(Integer.parseInt(sids));
							//simpleDaoSupport.executeNativeUpdate(" delete from CRM_CHANCES where CUSTOMER_ID = ? ", new Object[]{Integer.parseInt(sids)});
							
							//删除对应客户下的订单数据
							orderService.deleteByCustomerId(Integer.parseInt(sids));
							//simpleDaoSupport.executeNativeUpdate(" delete CO.*,OP.* from CRM_ORDER CO INNER JOIN ORDER_PRODUCTS OP ON CO.SID=OP.ORDER_ID where CO.CUSTOMER_ID = ?", new Object[]{Integer.parseInt(sids)});
							//删除对应客户下的退货单数据
							simpleDaoSupport.executeNativeUpdate(" delete CRO.*,ROP.* from CRM_RETURN_ORDER CRO INNER JOIN RETURN_ORDER_PRODUCTS ROP ON CRO.SID = ROP.RETURN_ORDER_ID where CRO.CUSTOMER_ID = ?", new Object[]{Integer.parseInt(sids)});
							//删除对应客户下的合同数据
							simpleDaoSupport.executeNativeUpdate(" delete from CRM_CONTRACTS where CUSTOMER_ID = ?",  new Object[]{Integer.parseInt(sids)});
							//删除对应客户下的回款数据
							simpleDaoSupport.executeNativeUpdate(" delete from PAYBACK  where CUSTOMER_ID = ? ", new Object[]{Integer.parseInt(sids)});
							//删除对应客户下的退款数据 
							simpleDaoSupport.executeNativeUpdate(" delete from DRAWBACK  where CUSTOMER_ID = ? ", new Object[]{Integer.parseInt(sids)});
							//删除对应客户下的开票数据
							simpleDaoSupport.executeNativeUpdate(" delete from INVOICE  where CUSTOMER_ID = ? ", new Object[]{Integer.parseInt(sids)});
							//删除对应客户下的拜访数据
							simpleDaoSupport.executeNativeUpdate(" delete from CRM_VISIT  where CUSTOMER_ID = ? ", new Object[]{Integer.parseInt(sids)});
							//删除对相应客户下的线索数据和客户数据
							simpleDaoSupport.executeNativeUpdate(" delete CLUE.* from CRM_CLUE CLUE INNER JOIN CRM_CUSTOMER CUS ON CLUE.SID =CUS.CLUE_ID where CUS.SID= ? ", new Object[]{Integer.parseInt(sids)});
							//删除客户表中的数据
							simpleDaoSupport.executeNativeUpdate(" delete from CRM_CUSTOMER where SID = ?", new Object[]{Integer.parseInt(sids)});
							
							json.setRtState(true);
							json.setRtMsg("删除成功！");
						}
				}else{
					json.setRtState(false);
					json.setRtMsg("数据不存在！");
				}
			}else{//批量删除
				List<TeeCrmCustomer> list = new ArrayList();
				String[] sid = sids.split(","); 
				int[] uuid = new int[sid.length];
				for (int i = 0; i < sid.length; i++) {
					 uuid[i] = Integer.parseInt(sid[i]);
					 customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, Integer.parseInt(sid[i]));
						if(!("").equals(customer)){
							int customerStatus = customer.getCustomerStatus();
								if(customerStatus!=2){//判断当前客户是否已作废 -未作废加入list
									list.add(customer);
								}
						}
				}
				if(list.size()>0){//存在未作废对象   不可删除
					json.setRtState(false);
					json.setRtMsg("客户"+list.get(0).getCustomerName()+"还没有作废，请作废后删除！");
				}else{//删除所有作废对象
					for (int i = 0; i < sid.length; i++) {
						//删除三个中间表中的数据
						simpleDaoSupport.executeNativeUpdate(" delete from CUSTOMER_PERSONS  where CUSTOMER_ID= ? ", new Object[]{Integer.parseInt(sid[i])});
						
						//删除客户表中的数据
						simpleDaoSupport.executeNativeUpdate(" delete from CRM_CUSTOMER where SID = ?", new Object[]{Integer.parseInt(sid[i])});
					}
					//String hql = "delete from TeeSmsSendPhone where sid in (" + ids + ")";
					json.setRtState(true);
					json.setRtMsg("删除成功！");
				}
				
		}
		
		}else{
			json.setRtState(false);
			json.setRtMsg("数据不存在！");
		}
		
		return json;
	
	}

	/**
	 * 恢复客户状态到作废前
	 * @param request
	 * @return
	 */
	public TeeJson recovery(HttpServletRequest request) {
		TeeJson json =  new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		simpleDaoSupport.executeNativeUpdate(" update CRM_CUSTOMER C set CUSTOMER_STATUS = ? where SID = ? ", new Object[]{1,sid});
		json.setRtMsg("操作成功！");
		json.setRtState(true);
		return json;
	}

	/**
	 * 销售汇总
	 * @param request
	 * @return
	 */
	public TeeJson salesSummary(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);//获取当前登陆者
		String year = TeeStringUtil.getString(request.getParameter("year"), "0");//获取页面传来的年
		String month = TeeStringUtil.getString(request.getParameter("month"), "0");//获取页面传来的月
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
		String yearAndMonth = year+"-"+month;//拼接年月
		//将字符串转换成日期格式
		Date date = null;
		try {
			date = sf.parse(yearAndMonth);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		//获取当前月的最大天数
		int day = calendar.getActualMaximum(Calendar.DATE);
		//日期范围
		String fromTime = year+"-"+month+"-"+1;
		String toTime = year+"-"+month+"-"+day;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Map map = new HashMap();
		
		//客户
		List param = new ArrayList();
		String hql = " from TeeCrmCustomer where 1=1";
		if(!TeeUtility.isNullorEmpty(fromTime)){
			Calendar t = Calendar.getInstance();
			try {
				t.setTime(sdf.parse(fromTime+" 00:00:00"));
				hql+=" and addTime >= ?";
				param.add(t);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(!TeeUtility.isNullorEmpty(toTime)){
			Calendar t = Calendar.getInstance();
			try {
				t.setTime(sdf.parse(toTime+" 23:59:59"));
				hql+=" and addTime <= ?";
				param.add(t);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		long customerCount = simpleDaoSupport.countByList("select count(*)"+hql+" and addPerson.uuid = managePerson.uuid and  addPerson.uuid ="+loginPerson.getUuid(), param);
		map.put("customerCount", customerCount);// 统计当前登陆者在指定年月的新增客户统计量
		
		
		//联系人
		List param2 = new ArrayList();
		String hql2 = " from TeeCrmContacts where 1=1";
		if(!TeeUtility.isNullorEmpty(fromTime)){
			Calendar t = Calendar.getInstance();
			try {
				t.setTime(sdf.parse(fromTime+" 00:00:00"));
				hql2+=" and createTime >= ?";
				param2.add(t);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(!TeeUtility.isNullorEmpty(toTime)){
			Calendar t = Calendar.getInstance();
			try {
				t.setTime(sdf.parse(toTime+" 23:59:59"));
				hql2+=" and createTime <= ?";
				param2.add(t);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		long contactsCount = simpleDaoSupport.countByList("select count(*)"+hql2+" and addPerson.uuid = contactManagePerson.uuid and addPerson.uuid ="+loginPerson.getUuid(), param2);
		map.put("contactsCount", contactsCount);// 统计当前登陆者在指定年月的新增联系人统计量
		
		
		//商机
		List param3 = new ArrayList();
		String hql3 = " from TeeCrmChances where 1=1";
		if(!TeeUtility.isNullorEmpty(fromTime)){
			Calendar t = Calendar.getInstance();
			try {
				t.setTime(sdf.parse(fromTime+" 00:00:00"));
				hql3+=" and createTime >= ?";
				param3.add(t);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(!TeeUtility.isNullorEmpty(toTime)){
			Calendar t = Calendar.getInstance();
			try {
				t.setTime(sdf.parse(toTime+" 23:59:59"));
				hql3+=" and createTime <= ?";
				param3.add(t);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		long chancesCount = simpleDaoSupport.countByList("select count(*)"+hql3+" and crUser.uuid = chanceManagePerson.uuid and  crUser.uuid ="+loginPerson.getUuid(), param3);
		map.put("chancesCount", chancesCount);// 统计当前登陆者在指定年月的新增商机统计量
		
		//拜访记录
		List param4 = new ArrayList();
		String hql4 = " from TeeCrmVisit where 1=1";
		if(!TeeUtility.isNullorEmpty(fromTime)){
			Calendar t = Calendar.getInstance();
			try {
				t.setTime(sdf.parse(fromTime+" 00:00:00"));
				hql4+=" and createTime >= ?";
				param4.add(t);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(!TeeUtility.isNullorEmpty(toTime)){
			Calendar t = Calendar.getInstance();
			try {
				t.setTime(sdf.parse(toTime+" 23:59:59"));
				hql4+=" and createTime <= ?";
				param4.add(t);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		long visitsCount = simpleDaoSupport.countByList("select count(*)"+hql4+" and createUser.uuid = managePerson.uuid and  createUser.uuid ="+loginPerson.getUuid(), param4);
		map.put("visitsCount", visitsCount);// 统计当前登陆者在指定年月的新增拜访统计量
		
		//目标
		double targetCount = 0;
	    List<TeeCrmDeptTarget> listTarget = simpleDaoSupport.executeQuery("from TeeCrmDeptTarget where year =? and dept =crUser.dept and crUser.uuid ="+loginPerson.getUuid(), new Object[]{Integer.parseInt(year)});
		if(!TeeUtility.isNullorEmpty(listTarget)){
			for (TeeCrmDeptTarget teeCrmDeptTarget : listTarget) {
				if("1".equals(month)){
					targetCount = teeCrmDeptTarget.getM1();
				}else if("2".equals(month)){
					targetCount = teeCrmDeptTarget.getM2();
				}else if("3".equals(month)){
					targetCount = teeCrmDeptTarget.getM3();
				}else if("4".equals(month)){
					targetCount = teeCrmDeptTarget.getM4();
				}else if("5".equals(month)){
					targetCount = teeCrmDeptTarget.getM5();
				}else if("6".equals(month)){
					targetCount = teeCrmDeptTarget.getM6();
				}else if("7".equals(month)){
					targetCount = teeCrmDeptTarget.getM7();
				}else if("8".equals(month)){
					targetCount = teeCrmDeptTarget.getM8();
				}else if("9".equals(month)){
					targetCount = teeCrmDeptTarget.getM9();
				}else if("10".equals(month)){
					targetCount = teeCrmDeptTarget.getM10();
				}else if("11".equals(month)){
					targetCount = teeCrmDeptTarget.getM11();
				}else if("12".equals(month)){
					targetCount = teeCrmDeptTarget.getM12();
				}
			}
		}
		map.put("targetCount", targetCount);// 统计当前登陆者在指定年月的目标数
		
		
		//预测
		List param5 = new ArrayList();
		String hql5 = " from TeeCrmChances where 1=1";
		if(!TeeUtility.isNullorEmpty(fromTime)){
			Calendar t = Calendar.getInstance();
			try {
				t.setTime(sdf.parse(fromTime+" 00:00:00"));
				hql5+=" and createTime >= ?";
				param5.add(t);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(!TeeUtility.isNullorEmpty(toTime)){
			Calendar t = Calendar.getInstance();
			try {
				t.setTime(sdf.parse(toTime+" 23:59:59"));
				hql5+=" and createTime <= ?";
				param5.add(t);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		List<TeeCrmChances> listForecast =simpleDaoSupport.executeQueryByList(hql5+" and crUser.uuid = chanceManagePerson.uuid and  crUser.uuid ="+loginPerson.getUuid(), param5);
		double forecast =0;
		if(!TeeUtility.isNullorEmpty(listForecast)){
			for (TeeCrmChances teeCrmChances : listForecast) {
				forecast +=teeCrmChances.getForcastCost();
			}
		}
		map.put("forecast", forecast);//商机总金额
		
		//销售订单
		List param6 = new ArrayList();
		String hql6 = " from TeeCrmOrder where 1=1";
		if(!TeeUtility.isNullorEmpty(fromTime)){
			Calendar t = Calendar.getInstance();
			try {
				t.setTime(sdf.parse(fromTime+" 00:00:00"));
				hql6+=" and createTime >= ?";
				param6.add(t);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(!TeeUtility.isNullorEmpty(toTime)){
			Calendar t = Calendar.getInstance();
			try {
				t.setTime(sdf.parse(toTime+" 23:59:59"));
				hql6+=" and createTime <= ?";
				param6.add(t);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		List<TeeCrmOrder> listOrder =simpleDaoSupport.executeQueryByList(hql6+" and addPerson.uuid = orderManagePerson.uuid and addPerson.uuid ="+loginPerson.getUuid() +" and orderStatus = 2", param6);
		List<TeeOrderProducts> listOp = null;
		double orderCount =0;
		if(!TeeUtility.isNullorEmpty(listOrder)){
			for (TeeCrmOrder orders : listOrder) {
				listOp= simpleDaoSupport.executeQuery("from TeeOrderProducts where order.sid = ?",new Object[]{orders.getSid()});
			    if(!TeeUtility.isNullorEmpty(listOp)){
			    	for (TeeOrderProducts teeOrderProducts : listOp) {
						orderCount +=teeOrderProducts.getTotalAmount();
					}
			    }
			}
		}
		map.put("orderCount", orderCount);//订单总金额
		
		//回款
		List param7 = new ArrayList();
		String hql7 = " from TeeCrmPayback where 1=1";
		if(!TeeUtility.isNullorEmpty(fromTime)){
			Calendar t = Calendar.getInstance();
			try {
				t.setTime(sdf.parse(fromTime+" 00:00:00"));
				hql7+=" and createTime >= ?";
				param7.add(t);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(!TeeUtility.isNullorEmpty(toTime)){
			Calendar t = Calendar.getInstance();
			try {
				t.setTime(sdf.parse(toTime+" 23:59:59"));
				hql7+=" and createTime <= ?";
				param7.add(t);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		List<TeeCrmPayback> listPayback =simpleDaoSupport.executeQueryByList(hql7+" and createUser.uuid = responsibleUser.uuid and createUser.uuid ="+loginPerson.getUuid() +" and paybackStatus = 2", param7);
		double payBackCount =0;
		if(!TeeUtility.isNullorEmpty(listPayback)){
			for (TeeCrmPayback payback : listPayback) {
				payBackCount +=payback.getPaybackAmount();
			}
		}
		map.put("payBackCount", payBackCount);//回款总金额
		
		
		//业绩完成
		String achievement = '0'+"%";
		if(targetCount!=0){
			achievement = (payBackCount/targetCount)*100+"%";
		}
		map.put("achievement", achievement);//业绩总金额
		
		json.setRtData(map);
		json.setRtState(true);
		json.setRtMsg("获取成功！");
		return json;
	}
	
	/**
	 * 获取所有客户数据
	 * @return
	 */
	public TeeJson selectAllCustomer(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String customerName = TeeStringUtil.getString(request.getParameter("customerName"), "");//获取客户名称
		List param = new ArrayList();
		String hql = " from TeeCrmCustomer c where 1=1";
		if(!TeeUtility.isNullorEmpty(customerName)){//客户名称
				hql += " and c.customerName like ? ";
				param.add("%"+customerName+"%");
		}
		List<TeeCrmCustomer> list = simpleDaoSupport.executeQueryByList(hql+" order by c.sid asc", param);
		List<TeeCrmCustomerModel> listModel = new ArrayList<TeeCrmCustomerModel>();
		for (int i = 0; i < list.size(); i++) {
			TeeCrmCustomerModel model = new TeeCrmCustomerModel();
			BeanUtils.copyProperties(list.get(i), model);
			listModel.add(model);
		}
		json.setRtData(listModel);
		json.setRtState(true);
		return json;
	
	}
  
}