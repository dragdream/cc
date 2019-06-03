package com.tianee.oa.subsys.crm.core.customer.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmContactUser;
import com.tianee.oa.subsys.crm.core.customer.model.TeeCrmContactUserModel;
import com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("TeeCrmContactUserDao")
public class TeeCrmContactUserDao extends TeeBaseDao<TeeCrmContactUser>{
	/**
	 * @author nieyi
	 * @param contactUser
	 */
	public void addContactUser(TeeCrmContactUser contactUser) {
		save(contactUser);
	}
	
	/**
	 * @author nieyi
	 * @param contactUser
	 */
	public void updateContactUser(TeeCrmContactUser contactUser) {
		update(contactUser);
	}
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeCrmContactUser loadById(int id) {
		TeeCrmContactUser intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeCrmContactUser getById(int id) {
		TeeCrmContactUser intf = get(id);
		return intf;
	}
	
	
	/**
	 * @author nieyi
	 * @param id
	 */
	public void delById(int id) {
		delete(id);
	}
	
	/**
	 * @author nieyi
	 * @param ids
	 */
	public void delByIds(String ids){
		
		if(!TeeUtility.isNullorEmpty(ids)){
			if(ids.endsWith(",")){
				ids= ids.substring(0, ids.length() -1 );
			}
			String hql = "delete from TeeCrmContactUser where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	/**
	 * 根据customerId删除user
	 * @param customerId
	 */
	public void delByCustomerId(int customerId){
		if(customerId>0){
			String hql = "delete from TeeCrmContactUser user where user.customer.sid = "+customerId;
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	/**
	 * @author ny
	 * @param deptId
	 * @param startDateDesc
	 * @param endDateDesc
	 * @return
	 * @throws Exception 
	 */
	public List<TeeCrmContactUser> getOutByCondition(int deptId,String startDateDesc, String endDateDesc) throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		List param =new ArrayList();
		String hql ="from TeeCrmContactUser out where out.status =1 ";
		
		if(deptId>0){
			hql +=" and out.user.dept.uuid="+deptId;
		}
		if(!TeeUtility.isNullorEmpty(startDateDesc)){
			Calendar cl1 = Calendar.getInstance();
			cl1.setTime(sf.parse(startDateDesc));
			hql+=" and out.createTime>?";
			param.add(cl1.getTime().getTime());
		}
		if(!TeeUtility.isNullorEmpty(endDateDesc)){
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(sf.parse(endDateDesc));
			hql+=" and out.createTime<?";
			param.add(cl2.getTime().getTime());
		}
		hql+=" order by out.createTime desc group by out.user.uuid";
		List<TeeCrmContactUser> list = (List<TeeCrmContactUser>) executeQueryByList(hql,param);
		return list;
	}
	
	
	
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String name=(String)requestDatas.get("name");
		String department=(String)requestDatas.get("department");
		String customerId=(String)requestDatas.get("customerId");
		String telephone=(String)requestDatas.get("telephone");
		String mobilePhone=(String)requestDatas.get("mobilePhone");
		TeePerson person =(TeePerson)requestDatas.get("LOGIN_USER");
		String shareFlag = (String)requestDatas.get("shareFlag");
		
		
		List param = new ArrayList();
		String hql = "";
		if("1".equals(shareFlag)){//共享
			hql = " from TeeCrmContactUser user,TeeCrmCustomerInfo customer where user.customer=customer  and user.addPerson.uuid<>"+person.getUuid()+" and exists (select 1 from customer.sharePerson sharePerson where sharePerson.uuid="+person.getUuid()+")";
		}else{
			if(TeePersonService.checkIsSuperAdmin(person, "")){//管理员
				hql = " from TeeCrmContactUser user,TeeCrmCustomerInfo customer where user.customer=customer ";
			}else{//个人
				hql = " from TeeCrmContactUser user,TeeCrmCustomerInfo customer where user.customer=customer and (customer.managePerson.uuid="+person.getUuid()+" or user.addPerson.uuid="+person.getUuid()+")";
			}
		}
		
		
		if(!TeeUtility.isNullorEmpty(name)){
			hql+=" and user.name like ?";
			param.add("%"+name+"%");
		}
		if(!TeeUtility.isNullorEmpty(department)){
			hql+=" and user.department like ?";
			param.add("%"+department+"%");
		}
		if(!TeeUtility.isNullorEmpty(customerId)){
			hql+=" and user.customer.sid = ?";
			param.add(Integer.parseInt(customerId));
		}
		if(!TeeUtility.isNullorEmpty(telephone)){
			hql+=" and user.telephone like ?";
			param.add("%"+telephone+"%");
		}
		if(!TeeUtility.isNullorEmpty(mobilePhone)){
			hql+=" and user.mobilePhone like ?";
			param.add("%"+mobilePhone+"%");
		}
		
		
		List<TeeCrmContactUser> infos = super.pageFindByList("select user "+hql+" order by user.sid desc", (dm.getPage()-1)*dm.getRows(), dm.getRows(), param);
		long total = countByList("select count(*) "+hql, param);
		List<TeeCrmContactUserModel> models = new ArrayList<TeeCrmContactUserModel>();
		for(TeeCrmContactUser contactUser:infos){
			TeeCrmContactUserModel m = new TeeCrmContactUserModel();
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
	public TeeCrmContactUserModel parseModel(TeeCrmContactUser contactUser){
		TeeCrmContactUserModel model = new TeeCrmContactUserModel();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		if(contactUser == null){
			return null;
		}
		BeanUtils.copyProperties(contactUser, model);
		if(contactUser.getBirthday()!=null){
			model.setBirthdayDesc(sf.format(contactUser.getBirthday().getTime()));
		}
		if(contactUser.getCustomer()!=null){
			model.setCustomerId(contactUser.getCustomer().getSid());
			model.setCustomerName(contactUser.getCustomer().getCustomerName());
		}
		if(contactUser.getAddPerson()!=null){
			model.setAddPersonId(contactUser.getAddPerson().getUuid());
			model.setAddPersonName(contactUser.getAddPerson().getUserName());
		}
		if(contactUser.getGender()==0){
			model.setGenderDesc("男");
		}else{
			model.setGenderDesc("女");
		}
		String importantDesc = TeeCrmCodeManager.getChildSysCodeNameCodeNo("USER_IMPORTANT",contactUser.getImportant());
		String posDesc = TeeCrmCodeManager.getChildSysCodeNameCodeNo("USER_POS",contactUser.get_pos());
		model.setImportantDesc(importantDesc);
		model.setPosDesc(posDesc);
		return model;
	}

	public List<TeeCrmContactUserModel> getContactUserList(int customerId) {
		List<TeeCrmContactUser> list = new ArrayList<TeeCrmContactUser>();
		String hql = "from TeeCrmContactUser user where 1=1 and user.customer.sid="+customerId;
		list=executeQuery(hql,null);
		List<TeeCrmContactUserModel> models = new ArrayList<TeeCrmContactUserModel>();
		for(TeeCrmContactUser contactUser:list){
			TeeCrmContactUserModel m = new TeeCrmContactUserModel();
			m=parseModel(contactUser);	
			models.add(m);
		}
		return models;
	}

	/**
	 * 删除不在sids 里面的数据
	 * @param sids
	 */
	public void delByNotInSids(String sids,int customerId) {
		if(!TeeUtility.isNullorEmpty(sids)){
			if(sids.endsWith(",")){
				sids= sids.substring(0, sids.length() -1 );
			}
			String hql = "delete from TeeCrmContactUser user where user.customer.sid = "+customerId+" and sid not in (" + sids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
		
	}

	public List<TeeCrmContactUserModel> getTotalByConditon(Map requestDatas) {
		String name=(String)requestDatas.get("name");
		String department=(String)requestDatas.get("department");
		String customerId=(String)requestDatas.get("customerId");
		String telephone=(String)requestDatas.get("telephone");
		String mobilePhone=(String)requestDatas.get("mobilePhone");
		TeePerson person =(TeePerson)requestDatas.get("LOGIN_USER");
		List param = new ArrayList();
		String hql = "from TeeCrmContactUser user,TeeCrmCustomerInfo customer where user.customer=customer and (customer.managePerson.uuid="+person.getUuid()+" or exists (select 1 from customer.sharePerson sharePerson where sharePerson.uuid="+person.getUuid()+"))";
		if(!TeeUtility.isNullorEmpty(name)){
			hql+=" and user.name like ?";
			param.add("%"+name+"%");
		}
		if(!TeeUtility.isNullorEmpty(department)){
			hql+=" and user.department like ?";
			param.add("%"+department+"%");
		}
		if(!TeeUtility.isNullorEmpty(customerId)){
			hql+=" and user.customer.sid = ?";
			param.add(Integer.parseInt(customerId));
		}
		if(!TeeUtility.isNullorEmpty(telephone)){
			hql+=" and user.telephone like ?";
			param.add("%"+telephone+"%");
		}
		if(!TeeUtility.isNullorEmpty(mobilePhone)){
			hql+=" and user.mobilePhone like ?";
			param.add("%"+mobilePhone+"%");
		}
		
		
		List<TeeCrmContactUser> infos = super.executeQueryByList("select user "+hql,param);
		List<TeeCrmContactUserModel> models = new ArrayList<TeeCrmContactUserModel>();
		for(TeeCrmContactUser contactUser:infos){
			TeeCrmContactUserModel m = new TeeCrmContactUserModel();
			m=parseModel(contactUser);	
			models.add(m);
		}
		return models;
	}
	
	/**
	 * 根据 客户ID 删除 联系人
	 * @param customerId
	 * @return
	 */
	public long deleteByCustomerInfoId(String customerIds) {
		if(!TeeUtility.isNullorEmpty(customerIds)){
			if(customerIds.endsWith(",")){
				customerIds= customerIds.substring(0, customerIds.length() -1 );
			}
			 String hql = "delete from TeeCrmContactUser user where user.customer.sid  in ("+ customerIds + ")";
			 return deleteOrUpdateByQuery(hql, null);
		}
		return 0l;
	}
}