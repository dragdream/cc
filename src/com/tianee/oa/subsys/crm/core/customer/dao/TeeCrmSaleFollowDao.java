package com.tianee.oa.subsys.crm.core.customer.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmSaleFollow;
import com.tianee.oa.subsys.crm.core.customer.model.TeeCrmSaleFollowModel;
import com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("TeeCrmSaleFollowDao")
public class TeeCrmSaleFollowDao extends TeeBaseDao<TeeCrmSaleFollow>{
	/**
	 * @author nieyi
	 * @param saleFollow
	 */
	public void addSaleFollow(TeeCrmSaleFollow saleFollow) {
		save(saleFollow);
	}
	
	/**
	 * @author nieyi
	 * @param saleFollow
	 */
	public void updateSaleFollow(TeeCrmSaleFollow saleFollow) {
		update(saleFollow);
	}
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeCrmSaleFollow loadById(int id) {
		TeeCrmSaleFollow intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeCrmSaleFollow getById(int id) {
		TeeCrmSaleFollow intf = get(id);
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
			String hql = "delete from TeeCrmSaleFollow where sid in (" + ids + ")";
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
	public List<TeeCrmSaleFollow> getOutByCondition(int deptId,String startDateDesc, String endDateDesc) throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		List param =new ArrayList();
		String hql ="from TeeCrmSaleFollow out where out.status =1 ";
		
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
		List<TeeCrmSaleFollow> list = (List<TeeCrmSaleFollow>) executeQueryByList(hql,param);
		return list;
	}
	
	
	
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		TeePerson person = (TeePerson)requestDatas.get("LOGIN_USER");
		String customerId = (String)requestDatas.get("customerId");
		String contactUserId = (String)requestDatas.get("contantsId");
		String startDateDesc = (String)requestDatas.get("startDateDesc");
		String endDateDesc = (String)requestDatas.get("endDateDesc");
		
		String shareFlag = (String)requestDatas.get("shareFlag");
		List param = new ArrayList();
		
		String hql = "";
		if("1".equals(shareFlag)){//共享
			hql = " from TeeCrmSaleFollow follow where 1=1 and exists (select 1 from follow.customer.sharePerson sharePerson where sharePerson.uuid = " + person.getUuid()  + ")  ";
		}else{
			if(TeePersonService.checkIsSuperAdmin(person, "")){//管理员
				hql = " from TeeCrmSaleFollow follow where 1=1 ";
			}else{//个人
				hql = "from TeeCrmSaleFollow follow where 1=1 and follow.addPerson.uuid="+person.getUuid();
			}
		}
		
		if(!TeeUtility.isNullorEmpty(customerId)){
     		
				hql+=" and follow.customer.sid=? ";
		
			param.add(Integer.parseInt(customerId));
		}
		if(!TeeUtility.isNullorEmpty(contactUserId) && !"0".equals(contactUserId)){
			hql+=" and follow.contantUser.sid=?";
			param.add(Integer.parseInt(contactUserId));
		}
		if(!TeeUtility.isNullorEmpty(startDateDesc)){
			hql+=" and follow.followDate>=?";
			Calendar scl = Calendar.getInstance();
			try {
				scl.setTime(sf.parse(startDateDesc));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			param.add(scl);
		}
		if(!TeeUtility.isNullorEmpty(endDateDesc)){
			hql+=" and follow.followDate<=?";
			Calendar ecl = Calendar.getInstance();
			try {
				ecl.setTime(sf.parse(endDateDesc));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			param.add(ecl);
		}
		hql += " order by follow.sid desc ";
		List<TeeCrmSaleFollow> infos = super.pageFindByList(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), param);
		long total = countByList("select count(*) "+hql, param);
		List<TeeCrmSaleFollowModel> models = new ArrayList<TeeCrmSaleFollowModel>();
		for(TeeCrmSaleFollow saleFollow:infos){
			TeeCrmSaleFollowModel m = new TeeCrmSaleFollowModel();
			m=parseModel(saleFollow);	
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	
	/**
	 * 对象转换
	 * @author nieyi
	 * @param saleFollow
	 * @return
	 */
	public TeeCrmSaleFollowModel parseModel(TeeCrmSaleFollow saleFollow){
		TeeCrmSaleFollowModel model = new TeeCrmSaleFollowModel();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(saleFollow == null){
			return null;
		}
		BeanUtils.copyProperties(saleFollow, model);
		if(saleFollow.getCustomer()!=null){
			model.setCustomerId(saleFollow.getCustomer().getSid());
			model.setCustomerName(saleFollow.getCustomer().getCustomerName());
		}
		if(saleFollow.getContantUser()!=null){
			model.setContantsId(saleFollow.getContantUser().getSid());
			model.setContantsName(saleFollow.getContantUser().getName());
		}
		if(saleFollow.getNextFollowUser()!=null){
			model.setNextFollowUserId(saleFollow.getNextFollowUser().getSid());
			model.setNextFollowUserName(saleFollow.getNextFollowUser().getName());
		}
		if(saleFollow.getFollowDate()!=null){
			model.setFollowDateDesc(dateFormat.format(saleFollow.getFollowDate().getTime()));
		}
		if(saleFollow.getNextFollowTime()!=null){
			model.setNextFollowTimeDesc(dateFormat.format(saleFollow.getNextFollowTime().getTime()));
		}
		if(saleFollow.getAddPerson()!=null){
			model.setAddPersonId(saleFollow.getAddPerson().getUuid());
			model.setAddPersonName(saleFollow.getAddPerson().getUserName());
		}
		String followTypeDesc = TeeCrmCodeManager.getChildSysCodeNameCodeNo("FOLLOW_TYPE",saleFollow.getFollowType());
		model.setFollowTypeDesc(followTypeDesc);

		if(saleFollow.getFollowResult()!=null){
			if(saleFollow.getFollowResult().equals("0")){
				model.setFollowResultDesc("跟单中");
			}else if(saleFollow.getFollowResult().equals("1")){
				model.setFollowResultDesc("已成交");
			}else if(saleFollow.getFollowResult().equals("2")){
				model.setFollowResultDesc("已作废");
			}
		}
		return model;
	}
	
	/**
	 * 根据客户id  删除相关订单
	 * @param customerInfo
	 * @return
	 */
	public long deleteByCustomerInfoId(String customerInfo){
		if(!TeeUtility.isNullorEmpty(customerInfo)){
			if(customerInfo.endsWith(",")){
				customerInfo= customerInfo.substring(0, customerInfo.length() -1 );
			}
			String hql = "delete from TeeCrmSaleFollow where customer.sid in (" + customerInfo + ")"; 
			Object[] values = {};
			return deleteOrUpdateByQuery(hql, values);
		}
		return 0;
	}
	
	/**
	 * 订单清空客户联系人
	 * @param custUserIds
	 * @return
	 */
	public int updateByCustUserIds(String custUserIds){
		int count = 0;
		if(!TeeUtility.isNullorEmpty(custUserIds)){
			if(custUserIds.endsWith(",")){
				custUserIds= custUserIds.substring(0, custUserIds.length() -1 );
			}
			String hql = "update TeeCrmSaleFollow follow set follow.contantUser.sid = null where contantUser.sid in (" + custUserIds + ")" ; 
			Object[] values = {};
			count  = deleteOrUpdateByQuery(hql, values);
			
			String hql2 = "update TeeCrmSaleFollow follow set follow.nextFollowUser.sid = null where nextFollowUser.sid in (" + custUserIds + ")" ; 
			int count2  = deleteOrUpdateByQuery(hql2, values);
			count = count + count2 ; 
			return count;
		}
		
		return 0;
	}
	
	/**
	 * 根据客户Ids  得到所有订单Ids
	 * @param customerIds
	 * @return
	 */
	public String getSaleFollowIdsByCustomerIds(String customerIds){
		StringBuffer sb = new StringBuffer();
		if(!TeeUtility.isNullorEmpty(customerIds)){
			if(customerIds.endsWith(",")){
				customerIds= customerIds.substring(0, customerIds.length() -1 );
			}
			String hql = " from TeeCrmSaleFollow where customer.sid in (" + customerIds + ")"; 
			Object[] values = {};
			List<TeeCrmSaleFollow> list = executeQuery(hql, values);
			for (int i = 0; i < list.size(); i++) {
				sb.append(list.get(i).getSid() + ","); 
			}
		}
		return sb.toString();
	}
	
	
	/**
	 * 根据条件获取跟单记录list
	 * @param requestDatas
	 * @return
	 */
	public List<TeeCrmSaleFollowModel> getSaleFollowList(Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		TeePerson person = (TeePerson)requestDatas.get("LOGIN_USER");
		String customerId = (String)requestDatas.get("customerId");
		String contactUserId = (String)requestDatas.get("contantsId");
		String startDateDesc = (String)requestDatas.get("startDateDesc");
		String endDateDesc = (String)requestDatas.get("endDateDesc");
		List param = new ArrayList();
		String hql = "from TeeCrmSaleFollow follow where 1=1 and follow.addPerson.uuid="+person.getUuid();
		if(!TeeUtility.isNullorEmpty(customerId)){
			hql+=" and follow.customer.sid=?";
			param.add(Integer.parseInt(customerId));
		}
		if(!TeeUtility.isNullorEmpty(contactUserId) && !"0".equals(contactUserId)){
			hql+=" and follow.contantUser.sid=?";
			param.add(Integer.parseInt(contactUserId));
		}
		if(!TeeUtility.isNullorEmpty(startDateDesc)){
			hql+=" and follow.followDate>=?";
			Calendar scl = Calendar.getInstance();
			try {
				scl.setTime(sf.parse(startDateDesc));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			param.add(scl);
		}
		if(!TeeUtility.isNullorEmpty(endDateDesc)){
			hql+=" and follow.followDate<=?";
			Calendar ecl = Calendar.getInstance();
			try {
				ecl.setTime(sf.parse(endDateDesc));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			param.add(ecl);
		}
		List<TeeCrmSaleFollow> infos = super.executeQueryByList(hql, param);
		List<TeeCrmSaleFollowModel> models = new ArrayList<TeeCrmSaleFollowModel>();
		for(TeeCrmSaleFollow saleFollow:infos){
			TeeCrmSaleFollowModel m = new TeeCrmSaleFollowModel();
			m=parseModel(saleFollow);	
			models.add(m);
		}
		return models;
	}
}