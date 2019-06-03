package com.tianee.oa.subsys.crm.core.customer.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.subsys.crm.core.contract.bean.TeeCrmContractProductItem;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmContactUser;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomerInfo;
import com.tianee.oa.subsys.crm.core.customer.dao.TeeCrmContactUserDao;
import com.tianee.oa.subsys.crm.core.customer.dao.TeeCrmCustomerInfoDao;
import com.tianee.oa.subsys.crm.core.customer.dao.TeeCrmSaleFollowDao;
import com.tianee.oa.subsys.crm.core.customer.model.TeeCrmContactUserModel;
import com.tianee.oa.subsys.crm.core.customer.model.TeeCrmCustomerInfoModel;
import com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.interceptor.TeeServiceLoggingInterceptor;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeUtility;


@Service
public class TeeCrmContactUserService extends TeeBaseService{
	@Autowired
	private TeeCrmContactUserDao contactUserDao;
	
	@Autowired
	private TeePersonDao personDao;

	@Autowired
	private TeeCrmCustomerInfoDao customerDao;
	
	@Autowired
	private TeeCrmSaleFollowDao crmSaleFollowDao;
	
	/**
	 * 保存或更新
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeCrmContactUserModel model) {
		TeeJson json = new TeeJson();
		TeeCrmContactUser contactUser = new TeeCrmContactUser();
		TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		TeeCrmCustomerInfo customerInfo = (TeeCrmCustomerInfo)customerDao.get(model.getCustomerId());
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		if(model.getSid() > 0){
		    contactUser  = contactUserDao.getById(model.getSid());
			if(contactUser != null){
				BeanUtils.copyProperties(model, contactUser);
				Calendar cl = Calendar.getInstance();
				if(model.getBirthdayDesc()!=null){
					try {
						if(!TeeUtility.isNullorEmpty(model.getBirthdayDesc())){
							
							cl.setTime(sf.parse(model.getBirthdayDesc()));
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
					contactUser.setBirthday(cl);
				}
				contactUser.setCustomer(customerInfo);
				contactUser.setAddPerson(person);
				contactUser.set_pos(model.getPos());
				contactUserDao.updateContactUser(contactUser);
				//记录日志
				StringBuffer remark=new StringBuffer();
				remark.append("客户名称：\""+customerInfo.getCustomerName()+"\",联系人姓名：\""+contactUser.getName()+"\"");
				TeeSysLog sysLog = TeeSysLog.newInstance();
		        sysLog.setType("045B");
		  	  	sysLog.setRemark(remark.toString());
		        TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到到相关外出信息！");
				return json;
			}
		}else{
			BeanUtils.copyProperties(model, contactUser);
			Calendar cl = Calendar.getInstance();
			if(model.getBirthdayDesc()!=null){
				try {
					if(!TeeUtility.isNullorEmpty(model.getBirthdayDesc())){
						cl.setTime(sf.parse(model.getBirthdayDesc()));
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				contactUser.setBirthday(cl);
			}
			contactUser.setCustomer(customerInfo);
			contactUser.setAddPerson(person);
			contactUserDao.addContactUser(contactUser);
			//记录日志
			StringBuffer remark=new StringBuffer();
			remark.append("客户名称：\""+customerInfo.getCustomerName()+"\",联系人姓名：\""+contactUser.getName()+"\"");
			TeeSysLog sysLog = TeeSysLog.newInstance();
	        sysLog.setType("045A");
	  	  	sysLog.setRemark(remark.toString());
	        TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		}
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		return json;
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
		model.setPos(contactUser.get_pos());
		return model;
	}
	
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson deleteByIdService(HttpServletRequest request, String sids) {
		TeeJson json = new TeeJson();
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		if(sids.endsWith(",")){
			sids = sids.substring(0,sids.length()-1);
		}
		//记录日志
		StringBuffer remark=new StringBuffer("[");
		String[] ids = sids.split(",");
		if(ids.length>0){
			for (int i = 0; i < ids.length; i++) {
				TeeCrmContactUser contactUser = contactUserDao.getById(Integer.parseInt(ids[i]));
				remark.append("{客户名称：\""+contactUser.getCustomer().getCustomerName()+"\",联系人姓名：\""+contactUser.getName()+"\"},");
			}
		}
		if(remark.toString().endsWith(",")){
			remark.deleteCharAt(remark.length()-1);
		}
		remark.append("]");
		TeeSysLog sysLog = TeeSysLog.newInstance();
        sysLog.setType("044C");
  	  	sysLog.setRemark(remark.toString());
        TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
        
        contactUserDao.delByIds(sids);
		//清空跟单信息
		crmSaleFollowDao.updateByCustUserIds(sids);
		return json;
	}
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request, TeeCrmContactUserModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeCrmContactUser contactUser = contactUserDao.getById(model.getSid());
			if(contactUser !=null){
				model = parseModel(contactUser);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关客户记录！");
		return json;
	}

	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm, Map requestDatas) {
		return contactUserDao.datagird(dm, requestDatas);
	}

	/**
	 * 根据customerId查询联系人
	 * @param customerId
	 * @return
	 */
	public TeeJson getContactUserList(int customerId) {
		TeeJson json = new TeeJson();
			List<TeeCrmContactUserModel> list = contactUserDao.getContactUserList(customerId);
			if(list !=null){
				json.setRtData(list);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		json.setRtState(false);
		json.setRtMsg("未找到相关客户记录！");
		return json;
	}

	public List<TeeCrmContactUserModel> getTotalByConditon(Map requestDatas) {
		return contactUserDao.getTotalByConditon(requestDatas);
	}
	
	
	
}