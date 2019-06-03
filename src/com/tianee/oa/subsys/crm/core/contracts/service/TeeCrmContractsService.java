package com.tianee.oa.subsys.crm.core.contracts.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.subsys.crm.core.contracts.bean.TeeCrmContracts;
import com.tianee.oa.subsys.crm.core.contracts.model.TeeCrmContractsModel;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomer;
import com.tianee.oa.subsys.crm.core.drawback.bean.TeeCrmDrawback;
import com.tianee.oa.subsys.crm.core.order.bean.TeeCrmOrder;
import com.tianee.oa.subsys.crm.core.payback.bean.TeeCrmPayback;
import com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeCrmContractsService extends TeeBaseService {
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeePersonService personService;

	/**
	 * 添加或编辑合同
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request,TeeCrmContractsModel model) {
		TeeJson json = new TeeJson();
		TeeCrmContracts contracts = new TeeCrmContracts();
		TeePerson person =(TeePerson)request.getSession().getAttribute("LOGIN_USER");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cl = Calendar.getInstance();
		Calendar ncl = Calendar.getInstance();
		Calendar cl2 = Calendar.getInstance();
		if(model.getSid() > 0){
			contracts  = (TeeCrmContracts) simpleDaoSupport.get(TeeCrmContracts.class, model.getSid());
			if(contracts != null){
				BeanUtils.copyProperties(model, contracts);
				if(!TeeUtility.isNullorEmpty(model.getCustomerId())){//客户
					TeeCrmCustomer customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, model.getCustomerId());
					contracts.setCustomer(customer);
				}
				if(!TeeUtility.isNullorEmpty(model.getOrderId())){//订单
					TeeCrmOrder order = (TeeCrmOrder) simpleDaoSupport.get(TeeCrmOrder.class, model.getOrderId());
					contracts.setOrder(order);
				}
				contracts.setCreateUser(person); //创建人
				if(!TeeUtility.isNullorEmpty(model.getManagePersonId())){//负责人
					TeePerson responsibleUser = (TeePerson)personDao.get(model.getManagePersonId());
					contracts.setResponsibleUser(responsibleUser);
				}
				if(!TeeUtility.isNullorEmpty(model.getContractsStartTimeDesc())){//开始日期
					try {
						cl.setTime(sdf.parse(model.getContractsStartTimeDesc()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				contracts.setContractsStartDate(cl);
				if(!TeeUtility.isNullorEmpty(model.getContractsEndTimeDesc())){//结束日期
					try {
						cl2.setTime(sdf.parse(model.getContractsEndTimeDesc()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				contracts.setContractsEndDate(cl2);
				contracts.setCreateTime(contracts.getCreateTime());//合同创建时间
				contracts.setLastEditTime(ncl);//合同的最后一次修改日期
				contracts.setContractsStatus(1); //合同状态    1-正常   2--已作废   
				simpleDaoSupport.update(contracts);
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到相关合同信息！");
				return json;
			}
		}else{//新建合同
			BeanUtils.copyProperties(model, contracts);
			if(!TeeUtility.isNullorEmpty(model.getCustomerId())){//客户
				TeeCrmCustomer customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, model.getCustomerId());
				contracts.setCustomer(customer);
			}
			if(!TeeUtility.isNullorEmpty(model.getOrderId())){//订单
				TeeCrmOrder order = (TeeCrmOrder) simpleDaoSupport.get(TeeCrmOrder.class, model.getOrderId());
				contracts.setOrder(order);
			}
			contracts.setCreateUser(person); //创建人
			if(!TeeUtility.isNullorEmpty(model.getManagePersonId())){//负责人
				TeePerson responsibleUser = (TeePerson)personDao.get(model.getManagePersonId());
				contracts.setResponsibleUser(responsibleUser);
			}
			if(!TeeUtility.isNullorEmpty(model.getContractsStartTimeDesc())){//开始日期
				try {
					cl.setTime(sdf.parse(model.getContractsStartTimeDesc()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			contracts.setContractsStartDate(cl);
			if(!TeeUtility.isNullorEmpty(model.getContractsEndTimeDesc())){//结束日期
				try {
					cl2.setTime(sdf.parse(model.getContractsEndTimeDesc()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			contracts.setContractsEndDate(cl2);
			contracts.setCreateTime(ncl);//合同创建时间
			contracts.setLastEditTime(ncl);//合同的最后一次修改日期
			contracts.setContractsStatus(1); //合同状态    1-正常   2--已作废   
			simpleDaoSupport.save(contracts);
		}

		json.setRtState(true);
		json.setRtMsg("保存成功！");
		return json;
	}

	/**
	 * 合同列表
	 * @param dm
	 * @param requestDatas
	 * @return
	 */
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm, Map requestDatas) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson person =(TeePerson)requestDatas.get("LOGIN_USER");
		String cusId = (String) requestDatas.get("cusId");
		String contractsNo = (String) requestDatas.get("contractsNo");//合同编号
		String contractsTitle = (String) requestDatas.get("contractsTitle");//合同标题
		String customerName = (String) requestDatas.get("customerName");//客户名称
		String customerId = (String) requestDatas.get("customerId"); //客户id
		String orderNo = (String) requestDatas.get("orderNo");//订单编号
		String orderId = (String) requestDatas.get("orderId");//
		String contractsStatusDesc =(String) requestDatas.get("contractsStatusDesc");  //合同状态
		String type = (String) requestDatas.get("type");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		List param = new ArrayList();
		String hql = " from TeeCrmContracts contracts where 1=1";
		if(!TeeUtility.isNullorEmpty(contractsNo)){//合同编号
			hql += " and contracts.contractsNo like ?";
			param.add("%"+contractsNo+"%");
		}
		if(!TeeUtility.isNullorEmpty(contractsTitle)){//合同标题
			hql += " and contracts.contractsTitle like ?";
			param.add("%"+contractsTitle+"%");
		}
		if(!TeeUtility.isNullorEmpty(customerId)&& !"0".equals(customerId)){
			TeeCrmCustomer customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, Integer.parseInt(customerId));
			if(!TeeUtility.isNullorEmpty(customer)){//客户
				hql+=" and contracts.customer= ?";
				param.add(customer);
			}
		}
		if(!TeeUtility.isNullorEmpty(orderId)&& !"0".equals(orderId)){
			TeeCrmOrder order = (TeeCrmOrder) simpleDaoSupport.get(TeeCrmOrder.class, Integer.parseInt(orderId));
			if(!TeeUtility.isNullorEmpty(order)){//订单
				hql+=" and contracts.order= ?";
				param.add(order);
			}
		}
		if(!TeeUtility.isNullorEmpty(contractsStatusDesc)){
			if(!"0".equals(contractsStatusDesc)){
				hql+=" and contracts.contractsStatus = ?";
				param.add(Integer.parseInt(contractsStatusDesc));
			}else{
				hql +=" ";
			}
		}
		if(!TeeUtility.isNullorEmpty(type)){
			if("2".equals(type)){//type =2我负责的
				hql +=" and contracts.responsibleUser.uuid = "+person.getUuid();
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
			    		hql+=" and contracts.responsibleUser.uuid in ("+ underPersonIds+")";
			    		
			    	}else{
				    	hql +=" and contracts.responsibleUser.uuid is null";
				    }
			    }
			}
		}else{//  没有type代表所属客户下的退货单
				hql ="from TeeCrmContracts contracts,TeeCrmCustomer customer where contracts.customer=customer and contracts.customer.sid=? and ( contracts.responsibleUser.uuid="+person.getUuid()+" or contracts.createUser.uuid="+person.getUuid()+") order by contracts.lastEditTime desc";
				param.add(Integer.parseInt(cusId));
			
		}
		List<TeeCrmContracts> list = simpleDaoSupport.pageFindByList("select contracts "+hql +" order by contracts.lastEditTime desc", (dm.getPage()-1)*dm.getRows(), dm.getRows(), param);
		long total = simpleDaoSupport.countByList("select count(*) "+hql, param);
		List<TeeCrmContractsModel> models = new ArrayList<TeeCrmContractsModel>();
		for (TeeCrmContracts contracts : list) {
			TeeCrmContractsModel m = new TeeCrmContractsModel();
			m = parseModel(contracts);
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	public TeeCrmContractsModel parseModel(TeeCrmContracts contracts){
		TeeCrmContractsModel model = new TeeCrmContractsModel();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(contracts == null){
			return null;
		}
		BeanUtils.copyProperties(contracts, model);
		if(contracts.getContractsStartDate()!=null){//开始日期
			model.setContractsStartTimeDesc(sf.format(contracts.getContractsStartDate().getTime()));
		}
		if(contracts.getContractsEndDate()!=null){//结束日期
			model.setContractsEndTimeDesc(sf.format(contracts.getContractsEndDate().getTime()));
		}
		if(contracts.getContractsStatus()==1){//合同状态
			model.setContractsStatusDesc("正常");
		}else if(contracts.getContractsStatus()==2){
			model.setContractsStatusDesc("已作废");
		}
		if(contracts.getCreateTime()!=null){
			model.setCreateTimeDesc(sdf.format(contracts.getCreateTime().getTime())); //创建时间
		}
		if(contracts.getCustomer()!=null){//客户
			model.setCustomerId(contracts.getCustomer().getSid());
			model.setCustomerName(contracts.getCustomer().getCustomerName());
		}
		if(contracts.getOrder()!=null){//订单
			model.setOrderId(contracts.getOrder().getSid());
			model.setOrderNo(contracts.getOrder().getOrderNo());
		}
		if(contracts.getCreateUser()!=null){//创建人
			model.setAddPersonId(contracts.getCreateUser().getUuid());
			model.setAddPersonName(contracts.getCreateUser().getUserName());
		}
		if(contracts.getResponsibleUser()!=null){//负责人
			model.setManagePersonId(contracts.getResponsibleUser().getUuid());
			model.setManagePersonName(contracts.getResponsibleUser().getUserName());
		}
		if(contracts.getLastEditTime()!=null){
			model.setLastEditTimeDesc(sdf.format(contracts.getLastEditTime().getTime()));  //最后修改日期
		}
		
		return model;
	}

	/**
	 * 获取合同详情
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request,TeeCrmContractsModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeCrmContracts contracts = (TeeCrmContracts) simpleDaoSupport.get(TeeCrmContracts.class, model.getSid());
			if(contracts !=null){
				model = parseModel(contracts);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关合同信息！");
		return json;
	}

	/**
	 * 作废合同
	 * @param request
	 * @return
	 */
	public TeeJson cancel(HttpServletRequest request) {
		TeeJson json =  new TeeJson();
		String sid=TeeStringUtil.getString(request.getParameter("sid"), "0");
		int status=TeeStringUtil.getInteger(request.getParameter("contractsStatus"), 0);
		if(!TeeUtility.isNullorEmpty(sid)&&!"0".equals(sid)){
			//未作废过-更改状态为作废
			Calendar cl = Calendar.getInstance();
			simpleDaoSupport.executeUpdate("update TeeCrmContracts set contractsStatus = ?, lastEditTime = ? where sid =? ", new Object[]{status,cl,Integer.parseInt(sid)});
			json.setRtMsg("操作成功！");
			json.setRtState(true);
			
		}
		return json;
	}

	/**
	 * 恢复
	 * @param request
	 * @return
	 */
	public TeeJson recovery(HttpServletRequest request) {
		TeeJson json =  new TeeJson();
		String sid = TeeStringUtil.getString(request.getParameter("sid"),"0");
		if(!TeeUtility.isNullorEmpty(sid)&&!"0".equals(sid)){
			Calendar cl = Calendar.getInstance();
			simpleDaoSupport.executeUpdate("update TeeCrmContracts set contractsStatus = ?, lastEditTime = ? where sid =? ", new Object[]{1,cl,Integer.parseInt(sid)});
			json.setRtMsg("操作成功！");
			json.setRtState(true);
		  }
		return json;
	}

	/**
	 * 删除合同信息
	 * @param request
	 * @return
	 */
	public TeeJson deleteById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String sid = TeeStringUtil.getString(request.getParameter("sid"),"0");
		if(!TeeUtility.isNullorEmpty(sid)){
			TeeCrmContracts contracts = new TeeCrmContracts();
			contracts.setSid(Integer.parseInt(sid));
			simpleDaoSupport.deleteByObj(contracts);
			json.setRtState(true);
			json.setRtMsg("删除成功！");
		}
		return json;
	
	}

}
