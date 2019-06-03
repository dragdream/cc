package com.tianee.oa.subsys.crm.core.contacts.service;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.language.bm.Languages.SomeLanguages;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.general.bean.TeeSysPara;
import com.tianee.oa.core.general.service.TeeSysParaService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.contacts.bean.TeeCrmContacts;
import com.tianee.oa.subsys.crm.core.contacts.model.TeeCrmContactsModel;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomer;
import com.tianee.oa.subsys.crm.core.customer.dao.TeeCrmCustomerDao;
import com.tianee.oa.subsys.crm.core.customer.model.TeeCrmCustomerModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.interceptor.TeeServiceLoggingInterceptor;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.file.TeeImageBase64Util;
import com.tianee.webframe.util.servlet.HttpUtils;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeCrmContactsService extends TeeBaseService{
	@Autowired
	private TeeAttachmentDao attachmentDao;
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeCrmCustomerDao customerDao;
	
	@Autowired
	private TeePersonService personService;
	
	@Autowired
	private  TeeSysParaService sysParaService;
	
	/**
	 * 验证该客户下此联系人是否已存在
	 * @param request
	 * @return
	 */
	public TeeJson getContactsByName(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String contactName = TeeStringUtil.getString(request.getParameter("contactName"), null);
		String customerId = TeeStringUtil.getString(request.getParameter("customerId"), "0");
		String sid = TeeStringUtil.getString(request.getParameter("sid"), "0");
		if(!TeeUtility.isNullorEmpty(customerId)){
			TeeCrmCustomer customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, Integer.parseInt(customerId));
			if(!TeeUtility.isNullorEmpty(customer)){
				long count= 0;
				if(sid!=null&&!("").equals(sid)&&!("0").equals(sid)){//编辑
				    count = simpleDaoSupport.count("select count(sid) from TeeCrmContacts where contactName = ? and customer = ? and sid!= ?", new Object[]{contactName,customer,Integer.parseInt(sid)});
				}else{//新增
					count = simpleDaoSupport.count("select count(sid) from TeeCrmContacts where contactName = ? and customer = ?", new Object[]{contactName,customer});
				}
				if(count>0){
			    	json.setRtData(1);
			    	json.setRtState(true);
				}else{
					json.setRtData(0);
					json.setRtState(true);
			    }
		
			}else{
				json.setRtMsg("数据不存在！");
				json.setRtState(false);
			}
			
		}
		return json;
	}

	/**
	 * 添加联系人
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request,TeeCrmContactsModel model) {
		TeeJson json = new TeeJson();
		TeeCrmContacts contacts = new TeeCrmContacts();
		TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		TeeCrmCustomer customerInfo = null;
		if(!TeeUtility.isNullorEmpty(model.getCustomerId())){
			customerInfo = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, model.getCustomerId());
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		
		//附件
		String dbAttachSid = TeeStringUtil.getString(request.getParameter("dbAttachSid"), "0");
		String attachmentSidStr = TeeStringUtil.getString(request.getParameter("attachmentSidStr") ,"0");
		
		List<TeeAttachment> attachAll = new ArrayList<TeeAttachment>();
		List<TeeAttachment> dbAttachSidList = getAttachmentsByIds(dbAttachSid);
		List<TeeAttachment> attachments = getAttachmentsByIds(attachmentSidStr);
		attachAll.addAll(dbAttachSidList);
		attachAll.addAll(attachments);

	    
		if(model.getSid() > 0){
			contacts  = (TeeCrmContacts) simpleDaoSupport.get(TeeCrmContacts.class, model.getSid());
			if(contacts != null){
				BeanUtils.copyProperties(model, contacts);
				Calendar cl = Calendar.getInstance();
				Calendar c2 = Calendar.getInstance();
				if(model.getBirthdayDesc()!=null){
					try {
						if(!TeeUtility.isNullorEmpty(model.getBirthdayDesc())){
							
							cl.setTime(sf.parse(model.getBirthdayDesc()));
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
					contacts.setBirthday(cl);
				}
				contacts.setCustomer(customerInfo); //客户
				contacts.setAddPerson(person);  //创建人
				contacts.setContactManagePerson(person);   //负责人
				contacts.setCreateTime(c2);  //创建时间
				TeeCrmContacts introduce = new TeeCrmContacts();
				if(!TeeUtility.isNullorEmpty(model.getIntroduceName())){
					introduce.setSid(model.getIntroduceId());
					introduce.setContactName(model.getIntroduceName());
					contacts.setIntroduce(introduce);//介绍人
				}else{
					contacts.setIntroduce(null);
				}
				simpleDaoSupport.update(contacts);
				
				//contactsDao.updatecontacts(contacts);
				//记录日志
				StringBuffer remark=new StringBuffer();
				remark.append("客户名称：\""+customerInfo.getCustomerName()+"\",联系人姓名：\""+contacts.getContactName()+"\"");
				TeeSysLog sysLog = TeeSysLog.newInstance();
		        sysLog.setType("045B");
		  	  	sysLog.setRemark(remark.toString());
		        TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到到相关联系人信息！");
				return json;
			}
		}else{
			BeanUtils.copyProperties(model, contacts);
			Calendar cl = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			if(model.getBirthdayDesc()!=null){
				try {
					if(!TeeUtility.isNullorEmpty(model.getBirthdayDesc())){
						cl.setTime(sf.parse(model.getBirthdayDesc()));
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				contacts.setBirthday(cl);
			}
			contacts.setCustomer(customerInfo); //客户
			contacts.setAddPerson(person);  //创建人
			contacts.setContactManagePerson(person);   //负责人
			contacts.setCreateTime(c2);  //创建时间
			contacts.setContactsStatus(0); //联系人状态   0-正常  1-作废
			TeeCrmContacts introduce = new TeeCrmContacts();
			if(!TeeUtility.isNullorEmpty(model.getIntroduceName())){
				introduce.setSid(model.getIntroduceId());
				introduce.setContactName(model.getIntroduceName());
				contacts.setIntroduce(introduce);//介绍人
			}else{
				contacts.setIntroduce(null);
			}
			simpleDaoSupport.save(contacts);
			
		 
			//记录日志
			StringBuffer remark=new StringBuffer();
			remark.append("客户名称：\""+customerInfo.getCustomerName()+"\",联系人姓名：\""+contacts.getContactName()+"\"");
			TeeSysLog sysLog = TeeSysLog.newInstance();
	        sysLog.setType("045A");
	  	  	sysLog.setRemark(remark.toString());
	        TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		}
		
		
		//处理附件
		for(TeeAttachment attach:attachAll){
			attach.setModelId(String.valueOf(contacts.getSid()));
			simpleDaoSupport.update(attach);
			
		}
		
		json.setRtState(true);
		//json.setRtData(model);
		json.setRtMsg("保存成功！");
		return json;
	}
	

	/**
	 * 根据附件sid获取附件
	 * 
	 * @date 2014年6月10日
	 * @author
	 * @param attachIds
	 * @return
	 */
	public List<TeeAttachment> getAttachmentsByIds(String attachIds) {
		return attachmentDao.getAttachmentsByIds(attachIds);
	}

	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm, Map requestDatas) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson person =(TeePerson)requestDatas.get("LOGIN_USER");//当前登陆者
		String contactName=(String)requestDatas.get("contactName");//姓名
		String companyName=(String)requestDatas.get("companyName");//公司
		String department=(String)requestDatas.get("department");//部门
		String cusId = (String) requestDatas.get("cusId");
		String customerId=(String)requestDatas.get("customerId");
		String customerName = (String) requestDatas.get("customerName");//客户名称
		String telephone=(String)requestDatas.get("telephone");//电话
		String mobilePhone=(String)requestDatas.get("mobilePhone");//手机
		String duties = (String) requestDatas.get("duties");//职务
		String email = (String) requestDatas.get("email");//邮件
		String isKeyPerson = (String) requestDatas.get("isKeyPerson");//关键决策人
		String gender = (String) requestDatas.get("gender");//关键决策人
		String type = (String)requestDatas.get("type");
		List param = new ArrayList();
		String hql = "from TeeCrmContacts user where 1=1";
		if(!TeeUtility.isNullorEmpty(contactName)){//姓名
			hql += " and user.contactName like ?";
			param.add("%"+contactName+"%");
		}
		if(!TeeUtility.isNullorEmpty(companyName)){//公司
			hql += " and user.companyName like ?";
			param.add("%"+companyName+"%");
		}
		if(!TeeUtility.isNullorEmpty(department)){//部门
			hql += " and user.department like ?";
			param.add("%"+department+"%");
		}
		if(!TeeUtility.isNullorEmpty(email)){//邮件
			hql += " and user.email like ?";
			param.add("%"+email+"%");
		}
		if(!TeeUtility.isNullorEmpty(telephone)){//电话
			hql += " and user.telephone like ?";
			param.add("%"+telephone+"%");
		}
		if(!TeeUtility.isNullorEmpty(mobilePhone)){//手机
			hql += " and user.mobilePhone like ?";
			param.add("%"+mobilePhone+"%");
		}
		if(!TeeUtility.isNullorEmpty(duties)){//职务
			hql += " and user.duties like ?";
			param.add("%"+duties+"%");
		}
		if(!TeeUtility.isNullorEmpty(isKeyPerson)){//关键决策人
			if(!"0".equals(isKeyPerson)){
				hql+=" and user.isKeyPerson = ?";
				param.add(Integer.parseInt(isKeyPerson));
			}else{
				hql +=" ";
			}
		}
		if(!TeeUtility.isNullorEmpty(gender)){//性别
			if(!" ".equals(gender)){
				hql+=" and user.gender = ?";
				param.add(Integer.parseInt(gender));
			}else{
				hql +=" ";
			}
		}
		if(!TeeUtility.isNullorEmpty(customerId)&& !"0".equals(customerId)){
			TeeCrmCustomer customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, Integer.parseInt(customerId));
			if(!TeeUtility.isNullorEmpty(customer)){
				hql+=" and user.customer= ?";
				param.add(customer);
			}
		}
		if(!TeeUtility.isNullorEmpty(type)){
			if("2".equals(type)){//type =2我负责的
				hql +=" and user.contactManagePerson.uuid = "+person.getUuid();
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
			    		hql+=" and user.contactManagePerson.uuid in ("+ underPersonIds+")";
			    		
			    	}else{
				    	hql +=" and user.contactManagePerson.uuid is null";
				    }
			    }
			}else if("0".equals(type)){
				hql =" from TeeCrmContacts user where 1=1";
			}
		}else{//  没有type代表所属客户下的联系人
			hql =" from TeeCrmContacts user,TeeCrmCustomer customer where user.customer=customer and user.customer.sid=? and ( user.contactManagePerson.uuid="+person.getUuid()+" or user.addPerson.uuid="+person.getUuid()+") ";
			param.add(Integer.parseInt(cusId));
		}
		
		List<TeeCrmContacts> infos = simpleDaoSupport.pageFindByList("select user "+hql+" order by user.sid desc", (dm.getPage()-1)*dm.getRows(), dm.getRows(), param);
		long total = simpleDaoSupport.countByList("select count(*) "+hql, param);
		List<TeeCrmContactsModel> models = new ArrayList<TeeCrmContactsModel>();
		for(TeeCrmContacts contactUser:infos){
			TeeCrmContactsModel m = new TeeCrmContactsModel();
			m=parseModel(contactUser);	
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	/**
	 * 对象转换
	 * @author nieyi
	 * @param contactUser
	 * @return
	 */
	public TeeCrmContactsModel parseModel(TeeCrmContacts contactUser){
		TeeCrmContactsModel model = new TeeCrmContactsModel();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(contactUser == null){
			return null;
		}
		BeanUtils.copyProperties(contactUser, model);
		if(contactUser.getBirthday()!=null){
			model.setBirthdayDesc(sf.format(contactUser.getBirthday().getTime()));
		}
		if(contactUser.getIsKeyPerson()!=0){//是否为关键决策人
			if(contactUser.getIsKeyPerson()==1){
				model.setKeyPersonDesc("是");
			}else{
				model.setKeyPersonDesc("否");
			}
		}
		if(contactUser.getContactsStatus()==0){
			model.setContactsStatusDesc("正常");
		}else{
			model.setContactsStatusDesc("已作废");
		}
		if(contactUser.getCreateTime()!=null){
			model.setCreateTimeDesc(sdf.format(contactUser.getCreateTime().getTime())); //创建时间
		}
		if(contactUser.getCustomer()!=null){//客户
			model.setCustomerId(contactUser.getCustomer().getSid());
			model.setCustomerName(contactUser.getCustomer().getCustomerName());
		}
		if(contactUser.getAddPerson()!=null){//创建人
			model.setAddPersonId(contactUser.getAddPerson().getUuid());
			model.setAddPersonName(contactUser.getAddPerson().getUserName());
		}
		if(contactUser.getGender()==0){//性别
			model.setGenderDesc("男");
		}else{
			model.setGenderDesc("女");
		}
		if(contactUser.getContactManagePerson()!=null){//负责人
			model.setManagePersonId(contactUser.getContactManagePerson().getUuid());
			model.setManagePersonName(contactUser.getContactManagePerson().getUserName());
		}
		if(contactUser.getIntroduce()!=null){//介绍人
			model.setIntroduceId(contactUser.getIntroduce().getSid());
			model.setIntroduceName(contactUser.getIntroduce().getContactName());
		}
		
		List<TeeAttachment> attaches = attachmentDao.getAttaches(TeeAttachmentModelKeys.CRM_CUSTOMER_CONTACTS, String.valueOf(contactUser.getSid()));
		List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
		for(TeeAttachment attach:attaches){
			TeeAttachmentModel m = new TeeAttachmentModel();
			BeanUtils.copyProperties(attach, m);
			m.setUserId(attach.getUser().getUuid()+"");
			m.setUserName(attach.getUser().getUserName());
			m.setPriv(1+2+4+8+16+32);//一共五个权限好像     1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
			attachmodels.add(m);
		}
		model.setAttachmodels(attachmodels);
		return model;
	}

	/**
	 * 获取联系人详情
	 * @param request
	 * @return
	 */
	public TeeJson getContactsInfoBySid(HttpServletRequest request,TeeCrmContactsModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeCrmContacts contacts = (TeeCrmContacts) simpleDaoSupport.get(TeeCrmContacts.class, model.getSid());
			if(contacts !=null){
				model = parseModel(contacts);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关联系人记录！");
		return json;
	}

	/**更换联系人所属负责人
	 * 
	 * @param request
	 * @return
	 */
	public TeeJson changeManage(HttpServletRequest request) {
		 TeeJson json = new TeeJson();
		 int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		 int managerPerId=TeeStringUtil.getInteger(request.getParameter("managerPerId"), 0);
		 simpleDaoSupport.executeNativeUpdate(" update CRM_CONTACTS CC set CONTACT_MANAGER_USER_ID = ? where SID = ? ", new Object[]{managerPerId,sid});
		 json.setRtState(true);
		 json.setRtMsg("操作成功！");
		 return json;
	}

	/**
	 * 作废联系人
	 * @param request
	 * @return
	 */
	public TeeJson cancel(HttpServletRequest request) {
		TeeJson json =  new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		int status=TeeStringUtil.getInteger(request.getParameter("contactsStatus"), 0);
		TeeCrmContacts contacts = (TeeCrmContacts) simpleDaoSupport.get(TeeCrmContacts.class, sid);
		if(!("").equals(contacts)){
			//未作废过-更改状态为作废
			if(status!=contacts.getContactsStatus()){
				simpleDaoSupport.executeUpdate("update TeeCrmContacts CC set CC.contactsStatus = ? where CC.sid = ? ", new Object[]{status,sid});
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
	 * 恢复联系人状态到作废之前
	 * @param request
	 * @return
	 */
	public TeeJson recovery(HttpServletRequest request) {
		TeeJson json =  new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		simpleDaoSupport.executeUpdate(" update TeeCrmContacts set contactsStatus = ? where sid = ? ", new Object[]{0,sid});
		json.setRtMsg("操作成功！");
		json.setRtState(true);
		return json;
	}

	/**
	 * 删除联系人 --已作废联系人才可删除  未作废的需先作废再删除
	 * @param request
	 * @return
	 */
	public TeeJson delContacts(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String sids = TeeStringUtil.getString(request.getParameter("sids"), "0");
		if(!TeeUtility.isNullorEmpty(sids)){
			if(sids.endsWith(",")){
				sids= sids.substring(0, sids.length() -1 );
			}
		     TeeCrmContacts contacts = null;
			 List<TeeCrmContacts> list = new ArrayList<TeeCrmContacts>();
				String[] sid = sids.split(","); 
				int[] uuid = new int[sid.length];
				for (int i = 0; i < sid.length; i++) {
					 uuid[i] = Integer.parseInt(sid[i]);
					 contacts = (TeeCrmContacts) simpleDaoSupport.get(TeeCrmContacts.class, uuid[i]); 
						if(!("").equals(contacts)){
							int contactsStatus = contacts.getContactsStatus();
								if(contactsStatus!=1){//判断当前联系人是否已作废 -未作废加入list
									list.add(contacts);
								}
						}else{
							json.setRtState(false);
							json.setRtMsg("数据不存在！");
						}
				}
				if(list.size()>0){//存在未作废对象   不可删除
					json.setRtState(false);
					json.setRtMsg("联系人"+list.get(0).getContactName()+"还没有作废，请作废后删除！");
		        }else{
		   		 String hql = "delete from TeeCrmContacts user where user.sid  in ("+ sids + ")";
				 long count = simpleDaoSupport.deleteOrUpdateByQuery(hql, null);
				 if(count >0){
					 json.setRtData(count);
					 json.setRtMsg("删除成功！");
					 json.setRtState(true);
					 
				 }else{
					 json.setRtMsg("删除失败！");
					 json.setRtState(false);
				 }
		     }
		}
	     return json;
	}
	
	
	/**
	 * 获取所有联系人数据
	 * @return
	 */
	public TeeJson selectAllContacts() {
		TeeJson json = new TeeJson();
		List<TeeCrmContacts> list = simpleDaoSupport.executeQuery(" from TeeCrmContacts where 1=1 ", null);
		List<TeeCrmContactsModel> listModel = new ArrayList<TeeCrmContactsModel>();
		for (int i = 0; i < list.size(); i++) {
			TeeCrmContactsModel model = new TeeCrmContactsModel();
			BeanUtils.copyProperties(list.get(i), model);
			listModel.add(model);
		}
		json.setRtData(listModel);
		json.setRtState(true);
		return json;
	
	}
	
	/**
	 * 获取所有某客户下的联系人数据
	 * @param request
	 * @return List
	 */
	public List<TeeCrmContactsModel> selectReceiver(HttpServletRequest request) {
		String cusId = TeeStringUtil.getString(request.getParameter("cusId"), "0");
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String hql ="select user from TeeCrmContacts user,TeeCrmCustomer customer where user.customer=customer and user.customer.sid = ? and ( user.contactManagePerson.uuid="+person.getUuid()+" or user.addPerson.uuid="+person.getUuid()+") order by user.sid desc";
		List<TeeCrmContacts> list = simpleDaoSupport.executeQuery(hql, new Object[]{Integer.parseInt(cusId)});
		List<TeeCrmContactsModel> models = new ArrayList<TeeCrmContactsModel>();
		for(TeeCrmContacts contactUser:list){
			TeeCrmContactsModel m = new TeeCrmContactsModel();
			m=parseModel(contactUser);	
			models.add(m);
		}

		return models;
	
	}

	
	/**
	 * 名片识别
	 * @param request
	 * @return
	 */
	public TeeJson ScanCard(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上传来的附件的主键
		int attachId=TeeStringUtil.getInteger(request.getParameter("attachId"),0);
		TeeAttachment att=(TeeAttachment) simpleDaoSupport.get(TeeAttachment.class,attachId);
		
	    InputStream in= null;
		try {
			in = new FileInputStream(att.getFilePath());
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
        String base64Str=TeeImageBase64Util.GetImageStr(in);
        
        
		String host = "https://dm-57.data.aliyun.com";
	    String path = "/rest/160601/ocr/ocr_business_card.json";
	    String method = "POST";
	    String appcode=sysParaService.getSysParaValue("SCAN_CARD_APP_CODE");
	    //String appcode = "34de2631ceb944899e8b4130e5ca5203";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    //根据API的要求，定义相对应的Content-Type
	    headers.put("Content-Type", "application/json; charset=UTF-8");
	    Map<String, String> querys = new HashMap<String, String>();
	    String bodys = "{\"inputs\":[{\"image\":{\"dataType\":50,\"dataValue\":\""+base64Str+"\"}}]}";
		    try {
		    	/**
		    	* 相应的依赖请参照
		    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
		    	*/
		    	HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
		    	//System.out.println(response.toString());
		    	//获取response的body
		    	JSONObject jsonObject = JSONObject.fromObject(EntityUtils.toString(response.getEntity()));
		    	JSONArray outputs = jsonObject.getJSONArray("outputs");
		    	JSONObject element0 = outputs.getJSONObject(0);
		    	JSONObject outputValue = element0.getJSONObject("outputValue");
		    	String dataValue = outputValue.getString("dataValue");
		    	
		    	json.setRtData(dataValue);
		    	//System.out.println(dataValue);
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
		
		
		json.setRtState(true);    
		return json;
	}
	
 
}