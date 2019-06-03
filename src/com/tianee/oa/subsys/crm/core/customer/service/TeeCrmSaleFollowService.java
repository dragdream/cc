package com.tianee.oa.subsys.crm.core.customer.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.app.quartz.bean.TeeQuartzTask;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmContactUser;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomerInfo;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmSaleFollow;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmSaleFollowProductItem;
import com.tianee.oa.subsys.crm.core.customer.dao.TeeCrmContactUserDao;
import com.tianee.oa.subsys.crm.core.customer.dao.TeeCrmCustomerInfoDao;
import com.tianee.oa.subsys.crm.core.customer.dao.TeeCrmSaleFollowDao;
import com.tianee.oa.subsys.crm.core.customer.dao.TeeCrmSaleFollowProductItemDao;
import com.tianee.oa.subsys.crm.core.customer.model.TeeCrmSaleFollowModel;
import com.tianee.oa.subsys.crm.core.customer.model.TeeCrmSaleFollowProductItemModel;
import com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.quartz.TeeQuartzTypes;
import com.tianee.quartz.util.TeeQuartzUtils;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.interceptor.TeeServiceLoggingInterceptor;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.socket.MessagePusher;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;


@Service
public class TeeCrmSaleFollowService extends TeeBaseService{
	@Autowired
	private TeeCrmSaleFollowDao saleFollowDao;
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeCrmCustomerInfoDao customerDao;

	@Autowired
	private TeeCrmContactUserDao contactUserDao;
	
	@Autowired
	private TeeAttachmentDao attachmentDao;
	
	
	@Autowired
	private TeeCrmSaleFollowProductItemDao saleFollowProductItemDao;
	
	@Autowired
	private TeeSmsManager smsManager;
	/**
	 * 保存或更新
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeCrmSaleFollowModel model) {
		TeeJson json = new TeeJson();
		TeeCrmSaleFollow saleFollow = new TeeCrmSaleFollow();
		TeePerson person =(TeePerson)request.getSession().getAttribute("LOGIN_USER");
		Calendar cl = Calendar.getInstance();
		Calendar ncl = Calendar.getInstance();
		if(model.getSid() > 0){
		    saleFollow  = saleFollowDao.getById(model.getSid());
			if(saleFollow != null){
				BeanUtils.copyProperties(model, saleFollow);
				if(!TeeUtility.isNullorEmpty(model.getCustomerId())){
					TeeCrmCustomerInfo customer = customerDao.get(model.getCustomerId());
					saleFollow.setCustomer(customer);
				}
				if(!TeeUtility.isNullorEmpty(model.getContantsId())){
					TeeCrmContactUser contactUser = contactUserDao.get(model.getContantsId());
					saleFollow.setContantUser(contactUser);
				}
				if(!TeeUtility.isNullorEmpty(model.getNextFollowUserId())){
					TeeCrmContactUser nextFollowUser = contactUserDao.get(model.getNextFollowUserId());
					saleFollow.setNextFollowUser(nextFollowUser);
				}
				saleFollow.setAddPerson(person);
				if(!TeeUtility.isNullorEmpty(model.getFollowDateDesc())){
					cl.setTime(TeeDateUtil.parseDateByPattern(model.getFollowDateDesc()));
					saleFollow.setFollowDate(cl);
				}
				if(!TeeUtility.isNullorEmpty(model.getNextFollowTimeDesc())){
					ncl.setTime(TeeDateUtil.parseDateByPattern(model.getNextFollowTimeDesc()));
					saleFollow.setNextFollowTime(ncl);
				}
				saleFollowDao.updateSaleFollow(saleFollow);
				String userName = "";
				if(saleFollow.getContantUser() != null){
					userName = saleFollow.getContantUser().getName();
				}
				
				String customerName = "";
				if(saleFollow.getCustomer() != null){
					customerName = saleFollow.getCustomer().getCustomerName();
				}
				
				//记录日志
				StringBuffer remark=new StringBuffer();
				remark.append("跟单客户名称：\""+customerName+"\",跟单联系人姓名：\""+userName+"\"");
				TeeSysLog sysLog = TeeSysLog.newInstance();
		        sysLog.setType("041A");
		  	  	sysLog.setRemark(remark.toString());
		        TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到到相关信息！");
				return json;
			}
			
			/**
			 * 删除产品明细
			 */
			saleFollowProductItemDao.delBySaleFollowId(saleFollow.getSid());
			
		}else{
			BeanUtils.copyProperties(model, saleFollow);
			if(!TeeUtility.isNullorEmpty(model.getCustomerId())){
				TeeCrmCustomerInfo customer = customerDao.get(model.getCustomerId());
				saleFollow.setCustomer(customer);
			}
			if(!TeeUtility.isNullorEmpty(model.getContantsId())){
				TeeCrmContactUser contactUser = contactUserDao.get(model.getContantsId());
				saleFollow.setContantUser(contactUser);
			}
			if(!TeeUtility.isNullorEmpty(model.getNextFollowUserId())){
				TeeCrmContactUser nextFollowUser = contactUserDao.get(model.getNextFollowUserId());
				saleFollow.setNextFollowUser(nextFollowUser);
			}
			saleFollow.setAddPerson(person);
			if(!TeeUtility.isNullorEmpty(model.getFollowDateDesc())){
				cl.setTime(TeeDateUtil.parseDateByPattern(model.getFollowDateDesc()));
				saleFollow.setFollowDate(cl);
			}
			if(!TeeUtility.isNullorEmpty(model.getNextFollowTimeDesc())){
				ncl.setTime(TeeDateUtil.parseDateByPattern(model.getNextFollowTimeDesc()));
				saleFollow.setNextFollowTime(ncl);
			}
			saleFollowDao.addSaleFollow(saleFollow);
			//记录日志
			StringBuffer remark=new StringBuffer();
			System.out.println(saleFollow.getCustomer());
			if(saleFollow.getCustomer()!=null&&saleFollow.getContantUser()!=null){
				remark.append("跟单客户名称：\""+saleFollow.getCustomer().getCustomerName()+"\",跟单联系人姓名：\""+saleFollow.getContantUser().getName()+"\"");
				TeeSysLog sysLog = TeeSysLog.newInstance();
		        sysLog.setType("041A");
		  	  	sysLog.setRemark(remark.toString());
		        TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);	
			}
			
		}
		//附件处理
		String dbAttachSid = TeeStringUtil.getString(request.getParameter("dbAttachSid"), "0");
		String attachmentSidStr = TeeStringUtil.getString(request.getParameter("attachmentSidStr") ,"0");
		List<TeeAttachment> attachAll = new ArrayList<TeeAttachment>();
		List<TeeAttachment> dbAttachSidList = attachmentDao.getAttachmentsByIds(dbAttachSid);
		List<TeeAttachment> attachments = attachmentDao.getAttachmentsByIds(attachmentSidStr);
		attachAll.addAll(dbAttachSidList);
		attachAll.addAll(attachments);
		for(TeeAttachment attach:attachAll){
			attach.setModelId(String.valueOf(saleFollow.getSid()));
			simpleDaoSupport.update(attach);
		}
		
		
		
		/**
		 * 新建产品明细
		 */
		String productsListStr = request.getParameter("productsList");
		if(!productsListStr.equals("")){
			List<TeeCrmSaleFollowProductItem>  productsList= (List<TeeCrmSaleFollowProductItem>)TeeJsonUtil.JsonStr2ObjectList(productsListStr , TeeCrmSaleFollowProductItem.class);
			for (int i = 0; i < productsList.size(); i++) {
				TeeCrmSaleFollowProductItem item = productsList.get(i);
				item.setSaleFollow(saleFollow);
				saleFollowProductItemDao.save(item);
			}
		}
		
		
		/**
		 * 内部短信提醒
		 */
		if(model.getIsRemind()=='1'){
			Date remindTime = ncl.getTime();
//			Map requestData = new HashMap();
//			requestData.put("content", "您有一个客户跟单，客户名称为："+model.getCustomerName());
//			String userListIds = person.getUuid() + "";
//			requestData.put("userListIds",userListIds);
//			requestData.put("sendTime",remindTime);
//			requestData.put("moduleNo", "041" );
//			requestData.put("remindUrl", "/system/subsys/crm/core/saleFollow/detail.jsp?sid="+model.getSid());
//			smsManager.sendSms(requestData, person);
			
			TeeQuartzTask quartzTask = new TeeQuartzTask();
			quartzTask.setContent("您有一个客户跟单，客户名称为："+model.getCustomerName());
			quartzTask.setExpDesc("一次性定时发送");
			quartzTask.setType(TeeQuartzTypes.ONCE);
			quartzTask.setFrom(person.getUserId());
			quartzTask.setTo(person.getUserId());
			quartzTask.setModelId(String.valueOf(saleFollow.getSid()));
			quartzTask.setModelNo("041");
			quartzTask.setUrl("/system/subsys/crm/core/saleFollow/detail.jsp?sid="+model.getSid());
			quartzTask.setUrl1("/system/subsys/crm/core/saleFollow/detail.jsp?sid="+model.getSid());
			quartzTask.setExp(TeeQuartzUtils.getOnceQuartzExpression(remindTime));
			MessagePusher.push2Quartz(quartzTask);
		}
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		return json;
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
		//处理附件,获取附件
		List<TeeAttachment> attaches = attachmentDao.getAttaches(TeeAttachmentModelKeys.CRM, String.valueOf(saleFollow.getSid()));
		List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
		for(TeeAttachment attach:attaches){
			TeeAttachmentModel m = new TeeAttachmentModel();
			BeanUtils.copyProperties(attach, m);
			m.setUserId(attach.getUser().getUuid()+"");
			m.setUserName(attach.getUser().getUserName());
			m.setPriv(1+2+4+8+16+32);//一共五个权限好像     1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
			attachmodels.add(m);
		}
		model.setAttacheModels(attachmodels);
		
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
	 * 对象转换
	 * @author nieyi
	 * @param saleFollow
	 * @return
	 */
	public TeeCrmSaleFollowModel parseModelForDetail(TeeCrmSaleFollow saleFollow){
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
		//处理附件,获取附件
		List<TeeAttachment> attaches = attachmentDao.getAttaches(TeeAttachmentModelKeys.CRM, String.valueOf(saleFollow.getSid()));
		List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
		for(TeeAttachment attach:attaches){
			TeeAttachmentModel m = new TeeAttachmentModel();
			BeanUtils.copyProperties(attach, m);
			m.setUserId(attach.getUser().getUuid()+"");
			m.setUserName(attach.getUser().getUserName());
			m.setPriv(1+2+16+32);//一共五个权限好像     1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
			attachmodels.add(m);
		}
		model.setAttacheModels(attachmodels);
		
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
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson deleteByIdService(HttpServletRequest request, String sids) {
		TeeJson json = new TeeJson();
		if(sids.endsWith(",")){
			String[] sid=sids.split(",");
			for(int i=0;i<sid.length;i++){
				TeeCrmSaleFollow saleFollow =(TeeCrmSaleFollow)saleFollowDao.get(Integer.parseInt(sid[i]));
				if(saleFollow!=null){
					List<TeeAttachment> attaches = attachmentDao.getAttaches(TeeAttachmentModelKeys.CRM, String.valueOf(saleFollow.getSid()));
					for(TeeAttachment atta:attaches){
						attachmentDao.deleteByObj(atta);
					}
				}
			}
		}
		
		//记录日志
		StringBuffer remark=new StringBuffer("[");
		String[] ids = sids.split(",");
		if(ids.length>0){
			for (int i = 0; i < ids.length; i++) {
				TeeCrmSaleFollow saleFollow = saleFollowDao.getById(Integer.parseInt(ids[i]));
				if(saleFollow.getCustomer().getCustomerName()!=null&&saleFollow.getContantUser()!=null){
					remark.append("{跟单客户名称：\""+saleFollow.getCustomer().getCustomerName()+"\",跟单联系人姓名：\""+saleFollow.getContantUser().getName()+"\"},");
				}
				
			}
		}
		if(remark.toString().endsWith(",")){
			remark.deleteCharAt(remark.length()-1);
		}
		remark.append("]");
		saleFollowDao.delByIds(sids);
		//删除订单产品明细
		saleFollowProductItemDao.deleteBySaleFollowIds(sids);
		TeeSysLog sysLog = TeeSysLog.newInstance();
        sysLog.setType("044C");
  	  	sysLog.setRemark(remark.toString());
        TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request, TeeCrmSaleFollowModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeCrmSaleFollow saleFollow = saleFollowDao.getById(model.getSid());
			if(saleFollow !=null){
				model = parseModel(saleFollow);
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
	
	public TeeJson getByIdForDetail(HttpServletRequest request, TeeCrmSaleFollowModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeCrmSaleFollow saleFollow = saleFollowDao.getById(model.getSid());
			if(saleFollow !=null){
				model = parseModelForDetail(saleFollow);
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
		return saleFollowDao.datagird(dm, requestDatas);
	}

	public TeeJson getProductItem(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int saleFollowId = TeeStringUtil.getInteger(request.getParameter("saleFollowId"), 0);
		List<TeeCrmSaleFollowProductItemModel> itemList = saleFollowProductItemDao.getProductItem(saleFollowId);
		json.setRtData(itemList);
		json.setRtState(true);
		json.setRtMsg("查询成功!");
		return json;
	}
	
	
	/**
	 * 或取跟单记录
	 * @param requestDatas
	 * @return
	 */
	public TeeJson getSaleFollowList(Map requestDatas) {
		TeeJson json = new TeeJson();
		List<TeeCrmSaleFollowModel> list = saleFollowDao.getSaleFollowList(requestDatas);
		if(null!=list){
			json.setRtData(list);
			json.setRtState(true);
			json.setRtMsg("查询成功!");
			return json;
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关客户记录！");
		return json;
	}
	
}