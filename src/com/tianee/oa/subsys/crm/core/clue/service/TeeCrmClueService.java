package com.tianee.oa.subsys.crm.core.clue.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.chances.bean.TeeCrmChances;
import com.tianee.oa.subsys.crm.core.clue.bean.TeeCrmClue;
import com.tianee.oa.subsys.crm.core.clue.model.TeeCrmClueModel;
import com.tianee.oa.subsys.crm.core.contacts.bean.TeeCrmContacts;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomer;
import com.tianee.oa.subsys.crm.core.customer.dao.TeeCrmCustomerDao;
import com.tianee.oa.subsys.crm.core.customer.service.TeeCrmCustomerService;
import com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeCrmClueService extends TeeBaseService {
	@Autowired
	private TeeCrmCustomerDao customer;
	
	@Autowired
	private TeeCrmCustomerService customerService;
	
	@Autowired
	private TeePersonService personService;

	/**
	 * 添加销售线索
	 * @param request
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request,TeeCrmClueModel model) {
		TeeJson json = new TeeJson();
		TeeCrmClue clue = new TeeCrmClue();
		TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		//编辑
		if(model.getSid() > 0){
			clue  = (TeeCrmClue) simpleDaoSupport.get(TeeCrmClue.class, model.getSid());
			if(clue != null){
				BeanUtils.copyProperties(model, clue);
				Calendar cl = Calendar.getInstance();
				clue.setAddPerson(person);//创建人
				clue.setManagePerson(person);//负责人
				clue.setCreateTime(clue.getCreateTime());  //创建时间
				clue.setClueStatus(1);  //设置线索状态  1 待处理   2  已处理    3 已转换   4失效
				clue.setLastEditTime(cl);  //最后一次变化时间
				clue.setDealResult(clue.getDealResult());  //处理结果
				simpleDaoSupport.update(clue);
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到到相关线索信息！");
				return json;
			}
		}else{
			BeanUtils.copyProperties(model, clue);
			Calendar cl = Calendar.getInstance();
			clue.setAddPerson(person);//创建人
			clue.setManagePerson(person);//负责人
			clue.setCreateTime(cl);  //创建时间
			clue.setClueStatus(1);  //设置线索状态  1待处理   2  已处理    3 已转换   4失效
			clue.setLastEditTime(cl);  //最后一次变化时间
			clue.setDealResult(null);  //处理结果
			simpleDaoSupport.save(clue);
		}
		
		json.setRtState(true);
		json.setRtMsg("保存成功！");
		return json;
	}

	/**
	 * 显示线索列表
	 * @param dm
	 * @param requestDatas
	 * @return
	 */
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm, Map requestDatas) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson person =(TeePerson)requestDatas.get("LOGIN_USER");
		String cusId = (String) requestDatas.get("cusId");
		String type = (String)requestDatas.get("type");//
		String name = (String) requestDatas.get("name");//姓名
		String managePersonName = (String) requestDatas.get("managePersonName");//负责人
		String managePersonId = (String) requestDatas.get("managePersonId");
		String companyName = (String) requestDatas.get("companyName");//公司
		String clueStatusDesc = (String) requestDatas.get("clueStatusDesc");//线索状态
		String department = (String) requestDatas.get("department");//部门
		String duties = (String) requestDatas.get("duties");//职务
		String telephone = (String) requestDatas.get("telephone");//电话
		String mobilePhone = (String) requestDatas.get("mobilePhone");//手机
		/*String customerId = (String) requestDatas.get("customerId");*/
		List param = new ArrayList();
		String hql = " from TeeCrmClue clue where 1=1";
		if(!TeeUtility.isNullorEmpty(name)){//姓名
			hql += " and clue.name like ?";
			param.add("%"+name+"%");
		}
		if(!TeeUtility.isNullorEmpty(managePersonId)&& !"0".equals(managePersonId)){//负责人
			TeePerson managePerson = (TeePerson) simpleDaoSupport.get(TeePerson.class, Integer.parseInt(managePersonId));
			if(!TeeUtility.isNullorEmpty(managePerson)){
				hql+=" and clue.managePerson= ?";
				param.add(managePerson);
			}
		}
		if(!TeeUtility.isNullorEmpty(companyName)){//公司名称
			hql += " and clue.companyName like ?";
			param.add("%"+companyName+"%");
		}
		if(!TeeUtility.isNullorEmpty(clueStatusDesc)){//状态
			if(!"0".equals(clueStatusDesc)){
				hql+=" and clue.clueStatus = ?";
				param.add(Integer.parseInt(clueStatusDesc));
			}else{
				hql +=" ";
			}
		}
		if(!TeeUtility.isNullorEmpty(department)){//部门
			hql += " and clue.department like ?";
			param.add("%"+department+"%");
		}
		if(!TeeUtility.isNullorEmpty(duties)){//职务
			hql += " and clue.duties like ?";
			param.add("%"+duties+"%");
		}
		if(!TeeUtility.isNullorEmpty(telephone)){//电话
			hql += " and clue.telephone like ?";
			param.add("%"+telephone+"%");
		}
		if(!TeeUtility.isNullorEmpty(mobilePhone)){//手机
			hql += " and clue.mobilePhone like ?";
			param.add("%"+mobilePhone+"%");
		}
		
		if(!TeeUtility.isNullorEmpty(type)){
			if("2".equals(type)){//type =2我负责的
				hql +=" and clue.managePerson.uuid = "+person.getUuid();
			}else if("1".equals(type)){//type=1 代表全部
				hql +="";
			}else if("3".equals(type)){//type=3代表我下属负责的
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
			    		hql+=" and clue.managePerson.uuid in ("+ underPersonIds+")";
			    		
			    	}else{
				    	hql +=" and clue.managePerson.uuid is null";
				    }
			    }
			}
		}else{//  没有type代表所属客户下的线索
			TeeCrmCustomer cus= null;
			if(!TeeUtility.isNullorEmpty(cusId)){
				cus = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, Integer.parseInt(cusId));
			}
			hql = "from TeeCrmClue clue,TeeCrmCustomer customer where clue = customer.clue and customer =?  and (clue.managePerson.uuid ="+person.getUuid()+" or clue.addPerson.uuid ="+person.getUuid()+")  order by clue.lastEditTime desc";
			param.add(cus);
		}
		
		List<TeeCrmClue> infos = simpleDaoSupport.pageFindByList("select clue "+hql+" order by clue.lastEditTime desc", (dm.getPage()-1)*dm.getRows(), dm.getRows(), param);
		long total = simpleDaoSupport.countByList("select count(*) "+hql, param);
		List<TeeCrmClueModel> models = new ArrayList<TeeCrmClueModel>();
		for(TeeCrmClue clue:infos){
			TeeCrmClueModel m = new TeeCrmClueModel();
			m=parseModel(clue);	
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	

	/**
	 * 对象转换
	 * @param clue
	 * @return
	 */
	public TeeCrmClueModel parseModel(TeeCrmClue clue){
		TeeCrmClueModel model = new TeeCrmClueModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(clue == null){
			return null;
		}
		BeanUtils.copyProperties(clue, model);
		//来源
		String clueSourceDesc=TeeCrmCodeManager.getChildSysCodeNameCodeNo("CLUE_SOURCE", model.getClueSource());
		model.setClueSourceDesc(clueSourceDesc);
        //线索状态
		if(clue.getClueStatus()==1){  //   1 待处理  2  已处理    3已转换  4失效
			model.setClueStatusDesc("待处理");
		}else if(clue.getClueStatus()==2){
			model.setClueStatusDesc("已处理");
		}else if(clue.getClueStatus()==3){
			model.setClueStatusDesc("已转换");
		}else{
			model.setClueStatusDesc("失效");
		}
		//创建时间
		if(clue.getCreateTime()!=null){
			model.setCreateTimeDesc(sdf.format(clue.getCreateTime().getTime())); //创建时间
		}
		//最后一次处理时间
		if(clue.getLastEditTime()!=null){
			model.setLastEditTimeDesc(sdf.format(clue.getLastEditTime().getTime()));//最后一次处理时间
		}
		if(clue.getAddPerson()!=null){//创建人
			model.setAddPersonId(clue.getAddPerson().getUuid());
			model.setAddPersonName(clue.getAddPerson().getUserName());
		}
		if(clue.getManagePerson()!=null){//负责人
			model.setManagePersonId(clue.getManagePerson().getUuid());
			model.setManagePersonName(clue.getManagePerson().getUserName());
		}
		//处理结果
		if(clue.getDealResult()!=null){
			model.setDealResult(clue.getDealResult());
		}else{
			model.setDealResult(null);
		}
		
		return model;
	}

	/**
	 * 获取线索详情
	 * @param request
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request,TeeCrmClueModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeCrmClue clue = (TeeCrmClue) simpleDaoSupport.get(TeeCrmClue.class, model.getSid());
			if(clue !=null){
				model = parseModel(clue);
				
				//线索转换为客户  ，联系人  ，商机后需要添加字段
				Map map = new HashMap();
				List<TeeCrmCustomer> listCustomer =simpleDaoSupport.executeQuery("from TeeCrmCustomer C where C.clue = ?", new Object[]{clue});
				if(!TeeUtility.isNullorEmpty(listCustomer)){
					if(listCustomer.size()==1){//客户
						model.setCustomerName(listCustomer.get(0).getCustomerName());
					}
				}else{
					model.setCustomerName(null);
				}
				List<TeeCrmContacts> listContact = simpleDaoSupport.executeQuery("from TeeCrmContacts CC where CC.clue = ?", new Object[]{clue});
				if(!TeeUtility.isNullorEmpty(listContact)){
					if(listContact.size()==1){//联系人
						model.setContactName(listContact.get(0).getContactName());
					}
				}else{
					model.setContactName(null);
				}
				//商机
				List<TeeCrmChances> listChances = simpleDaoSupport.executeQuery("from TeeCrmChances TCC where TCC.clue = ?", new Object[]{clue});
				if(!TeeUtility.isNullorEmpty(listChances)){
					if(listChances.size()==1){//商机
						model.setChanceName(listChances.get(0).getChanceName());
					}
				}else{
					model.setChanceName(null);
				}
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关线索！");
		return json;
	}

	/**
	 * 更换线索所属负责人
	 * @param request
	 * @return
	 */
	public TeeJson changeManage(HttpServletRequest request) {
		 TeeJson json = new TeeJson();
		 TeePerson person  = new TeePerson();
		 Calendar cl = Calendar.getInstance();
		 int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		 int managerPerId=TeeStringUtil.getInteger(request.getParameter("managerPerId"), 0);
		 String managerPerName =TeeStringUtil.getString(request.getParameter("managerPerName"),null);
		 person.setUuid(managerPerId);
		 person.setUserName(managerPerName);
		 simpleDaoSupport.executeUpdate("update TeeCrmClue set managePerson = ?, lastEditTime = ? where sid =? ", new Object[]{person,cl,sid});
		// simpleDaoSupport.executeNativeUpdate(" update T set CLUE_MANAGER_USER_ID = ? where SID = ? ", new Object[]{managerPerId,sid});
		 json.setRtState(true);
		 json.setRtMsg("操作成功！");
		 return json;
	}

	/**
	 * 删除线索
	 * @param request
	 * @return
	 */
	public TeeJson delById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String ids = TeeStringUtil.getString(request.getParameter("sids"), "0");
		if(!TeeUtility.isNullorEmpty(ids)){
			if(ids.endsWith(",")){
				ids= ids.substring(0, ids.length() -1 );
			}
			String hql = "delete from TeeCrmClue where sid in (" + ids + ")";
			simpleDaoSupport.deleteOrUpdateByQuery(hql, null);
			json.setRtState(true);
			json.setRtMsg("删除成功！");
		}
		return json;
		
	}

	/**
	 * 跟进线索
	 * @param request
	 * @return
	 */
	public TeeJson followUpClue(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		String result = TeeStringUtil.getString(request.getParameter("dealResult"),  null);
		Calendar cl = Calendar.getInstance();
		simpleDaoSupport.executeUpdate("update TeeCrmClue set dealResult = ?, lastEditTime = ?, clueStatus =? where sid =? ", new Object[]{result,cl,2,sid});
		 json.setRtState(true);
		 json.setRtMsg("操作成功！");
		return json;
	}

	/**
	 * 线索转换 为  客户    联系人  商机
	 * @param request
	 * @return
	 */
	/**
	 * @param request
	 * @return
	 */
	public TeeJson transfromClue(HttpServletRequest request) {
		TeeJson json =new TeeJson();
		TeePerson addPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String clueId=TeeStringUtil.getString(request.getParameter("sid"),null);
		String transCustomer = TeeStringUtil.getString(request.getParameter("transCustomer"),"1");
		String transContact = TeeStringUtil.getString(request.getParameter("transContact"),"0");
		String transChances = TeeStringUtil.getString(request.getParameter("transChances"),"0");
		String customerName = TeeStringUtil.getString(request.getParameter("companyName"));
		String companyPhone = TeeStringUtil.getString(request.getParameter("telephone"));
		String companyAddress = TeeStringUtil.getString(request.getParameter("address"), null);
		String companyUrl = TeeStringUtil.getString(request.getParameter("url"), null);
		Calendar cl = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//转换线索为客户（默认必须转换客户）   --先判断该客户是否已存在
		if(!TeeUtility.isNullorEmpty(clueId)){
			TeeCrmClue clue = (TeeCrmClue) simpleDaoSupport.get(TeeCrmClue.class, Integer.parseInt(clueId));
			if(!TeeUtility.isNullorEmpty(clue)){
				
			TeeCrmCustomer customer = new TeeCrmCustomer();
			List<TeeCrmCustomer> list = null;
			long count = simpleDaoSupport.count("select count(sid) from TeeCrmCustomer where customerName = ?", new Object[]{customerName});
			if(count>0){
				list =simpleDaoSupport.executeQuery("from TeeCrmCustomer where customerName = ?", new Object[]{customerName});
				if(list.size()>1){
					customer = null;
					json.setRtMsg("数据存在问题！");
					json.setRtState(false);
				}else{
					customer  =list.get(0);
					json.setRtMsg("转换失败，客户已存在！");
					json.setRtState(false);
				}
			}else{//添加一条客户记录
				
				customer.setCustomerName(customerName);
				customer.setCompanyPhone(companyPhone);
				customer.setCompanyAddress(companyAddress);
				customer.setCompanyUrl(companyUrl);
				customer.setCustomerStatus(1);//客户状态  --已分配
				customer.setDealStatus(0); //交易状态
				customer.setAddPerson(addPerson);//创建人
				customer.setManagePerson(addPerson);//负责人
				customer.setAddTime(cl);  //创建时间
				customer.setClue(clue);  //线索外键
				simpleDaoSupport.save(customer); 
	
			}
			
			if(!("0").equals(transContact)){//同时转换为联系人
				String contactName = TeeStringUtil.getString(request.getParameter("name"));
				String department = TeeStringUtil.getString(request.getParameter("department"), null);
				String duties = TeeStringUtil.getString(request.getParameter("duties"),null);
				String mobilePhone =TeeStringUtil.getString(request.getParameter("mobilePhone"),null);
				String email = TeeStringUtil.getString(request.getParameter("email"),null);
				long counts = simpleDaoSupport.count("select count(sid) from TeeCrmContacts where contactName = ? and customer = ?", new Object[]{contactName,customer});
			    if(counts>0){
			    	json.setRtMsg("转换失败，联系人已存在！");
			    	json.setRtState(false);
			    }else{
			    	TeeCrmContacts contact = new TeeCrmContacts();
			    	contact.setCustomer(customer);  //所属客户
			    	contact.setContactName(contactName);
			    	contact.setDepartment(department);
			    	contact.setDuties(duties);
			    	contact.setMobilePhone(mobilePhone);
			    	contact.setEmail(email);
			    	contact.setContactManagePerson(addPerson);//负责人
			    	contact.setAddPerson(addPerson);//创建人
			    	contact.setContactsStatus(0);//联系人状态  0-正常   1-作废
			    	contact.setCreateTime(cl);//创建时间
			    	contact.setClue(clue);  //  线索外键
			    	simpleDaoSupport.save(contact);//添加联系人
			    	
			    }
			
			}
			//同时转换为商机  （***************************需补充********************************************）
			if(!("0").equals(transChances)){//同时转换为联系人
				String chanceName = TeeStringUtil.getString(request.getParameter("name"));
				String forcastTimeDesc = TeeStringUtil.getString(request.getParameter("forcastTimeDesc"), null);
				String forcastCost = TeeStringUtil.getString(request.getParameter("forcastCost"),null);
				String managePersonName =TeeStringUtil.getString(request.getParameter("managePersonName"),null);
				String managePersonId = TeeStringUtil.getString(request.getParameter("managePersonId"),null);
				TeeCrmChances chances = new TeeCrmChances();
				Calendar cl2 = Calendar.getInstance();
				if(!TeeUtility.isNullorEmpty(forcastTimeDesc)){ 
					try {
						cl2.setTime(sdf.parse(forcastTimeDesc));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				  if(!TeeUtility.isNullorEmpty(managePersonId)&& !"0".equals(managePersonId)){
					TeePerson managePerson = (TeePerson) simpleDaoSupport.get(TeePerson.class, Integer.parseInt(managePersonId));
				    chances.setChanceManagePerson(managePerson);//负责人
				   }
				  	
				    chances.setForcastTime(cl2);
				    chances.setCustomer(customer);  //所属客户
				    chances.setChanceName(chanceName);
				    chances.setForcastCost(Double.parseDouble(forcastCost));//金额
				    chances.setCrUser(addPerson);//创建人
				    chances.setChanceStatus(1);
				    chances.setCreateTime(cl);//创建时间
				    chances.setLastEditTime(cl);
				    chances.setClue(clue);  //  线索外键
			    	simpleDaoSupport.save(chances);//添加商机
			
			}
			
			//更改线索状态和最后处理时间
			simpleDaoSupport.executeUpdate("update TeeCrmClue set lastEditTime = ?, clueStatus =? where sid =? ", new Object[]{cl,3,Integer.parseInt(clueId)});
			json.setRtMsg("转换成功！");
		    json.setRtState(true);
			
			}else{
			  json.setRtMsg("数据不存在！");
			  json.setRtState(false);
		  }
			
		}
		
		return json;
	}

	/**
	 * 线索状态置为无效
	 * @param request
	 * @return
	 */
	public TeeJson invalidClue(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		String result = TeeStringUtil.getString(request.getParameter("dealResult"),  null);
		Calendar cl = Calendar.getInstance();
		simpleDaoSupport.executeUpdate("update TeeCrmClue set dealResult = ?, lastEditTime = ?, clueStatus =? where sid =? ", new Object[]{result,cl,4,sid});
		 json.setRtState(true);
		 json.setRtMsg("操作成功！");
		return json;
	}

}
