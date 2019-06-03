package com.tianee.oa.subsys.crm.core.contract.service;

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
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.app.quartz.bean.TeeQuartzTask;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonManagerI;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.oaconst.TeeModelIdConst;
import com.tianee.oa.subsys.crm.core.contract.bean.TeeCrmContract;
import com.tianee.oa.subsys.crm.core.contract.bean.TeeCrmContractRecvPayments;
import com.tianee.oa.subsys.crm.core.contract.dao.TeeCrmContractDao;
import com.tianee.oa.subsys.crm.core.contract.dao.TeeCrmContractRecvPaymentsDao;
import com.tianee.oa.subsys.crm.core.contract.model.TeeCrmContractRecvPaymentsModel;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomerInfo;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeManagerPostPersonDataPrivModel;
import com.tianee.quartz.TeeQuartzTypes;
import com.tianee.quartz.util.TeeQuartzUtils;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.socket.MessagePusher;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeCrmContractRecvPaymentsService extends TeeBaseService {
	@Autowired
	TeeCrmContractRecvPaymentsDao contractRecvPaymentsDao;

	@Autowired
	TeeCrmContractDao contractDao;
	
	@Autowired
	TeePersonDao personDao;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private TeePersonManagerI personManagerI;

	/**
	 * 新建或者更新
	 * 
	 * @param request
	 * @param object
	 *            模型
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request,
			TeeCrmContractRecvPaymentsModel object) {
		TeeJson json = new TeeJson();
		TeeCrmContractRecvPayments rectPayments = new TeeCrmContractRecvPayments();
		TeeCrmContract contract = null;// 合同
		int contractId = TeeStringUtil.getInteger(object.getContractId(), 0);
		if (contractId > 0) {
			contract = contractDao.get(contractId);
		}
		if (contract == null) {
			json.setRtMsg("该合同已被删除，请重新选择合同！");
			return json;
		}
		//负责人
		TeePerson person = null;
		if(object.getManagerUserId() > 0){
			person = personDao.get(object.getManagerUserId());
			rectPayments.setManagerUser(person);
		}
		if (object.getSid() > 0) {
			TeeCrmContractRecvPayments preRectPayments = contractRecvPaymentsDao
					.get(object.getSid());
			if (preRectPayments == null) {
				json.setRtMsg("该合同回款信息已被删除!");
				return json;
			}
			Date createTime = preRectPayments.getCreateTime();
			BeanUtils.copyProperties(object, preRectPayments);
			preRectPayments.setCreateTime(createTime);
			preRectPayments.setManagerUser(person);//负责人
			contractRecvPaymentsDao.update(preRectPayments);
			rectPayments = preRectPayments;
			json.setRtMsg("更新成功！");
		} else {
			BeanUtils.copyProperties(object, rectPayments);
			rectPayments.setCreateTime(new Date());
			rectPayments.setContract(contract);
			contractRecvPaymentsDao.save(rectPayments);
			json.setRtMsg("新建成功！");
			
			
		}
		json.setRtState(true);
		sendQuartzTask(rectPayments);
		
		return json;
	}

	/**
	 * 发送消息提醒
	 * @param rectPayments
	 */
	public void sendQuartzTask(TeeCrmContractRecvPayments rectPayments){
		TeeCrmContract contract = rectPayments.getContract();//合同
		if(contract == null ){
			return;
		}
		if(rectPayments.getRecvPaymentFlag().equals("1")){//已付款
			return ;
		}
		TeeCrmCustomerInfo customerInfo = contract.getCustomerInfo();
		TeePerson managePerson = customerInfo.getManagePerson();
		if(managePerson == null ){
			return;
		}
		/**
		 * 任务提醒
		 */
		Date recvDate = rectPayments.getPlanRecvDate();
		Calendar c = Calendar.getInstance();
		c.setTime(recvDate);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		TeeQuartzTask task = new TeeQuartzTask();
		task.setContent("合同回款提醒：你的合同编号为“" + contract.getContractNo()+ "”预计回款时间为" + format.format(recvDate) + ",请查收！");
		task.setExp(TeeQuartzUtils.getPreRemindQuartzExpression(c.get(Calendar.YEAR), 
				c.get(Calendar.MONTH)+1, 
				c.get(Calendar.DATE),
				c.get(Calendar.HOUR), 
				c.get(Calendar.MINUTE), 
				c.get(Calendar.SECOND), 
				0, 
				0, 
				0));
		task.setFrom(String.valueOf(contract.getCreateUser().getUserId()));
		task.setModelId(String.valueOf(rectPayments.getSid()));
		task.setModelNo("040");//CRM合同回款
		task.setType(TeeQuartzTypes.ONCE);//一次
		task.setUrl("");
		task.setUrl1("");
		task.setTo(String.valueOf(contract.getCustomerInfo().getManagePerson().getUserId()));//发给谁
		MessagePusher.push2Quartz(task);
	}
	/**
	 * 通用列表
	 * 
	 * @param dm
	 * @param request
	 * @param person
	 * @return
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson manager(TeeDataGridModel dm,
			HttpServletRequest request, TeePerson person ,TeeCrmContractRecvPaymentsModel model) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		String hql = "from TeeCrmContractRecvPayments where 1 =1 ";
		List paraList = new ArrayList();
		List<TeeCrmContractRecvPaymentsModel> modelList = new ArrayList<TeeCrmContractRecvPaymentsModel>();
		
		Map map = TeeServletUtility.getParamMap(request);
		map.put(TeeConst.LOGIN_USER, person);
		String shareFlag = (String)map.get("shareFlag");
		
		if("1".equals(shareFlag)){//共享
			hql = " from TeeCrmContractRecvPayments recvPayments where 1 =1 and contract.createUser.uuid <> " + person.getUuid() +  " and exists(select 1 from recvPayments.contract.customerInfo.sharePerson sharePerson where sharePerson.uuid="+person.getUuid()+")";
		}else{
			if(TeePersonService.checkIsSuperAdmin(person, "")){//管理员
				hql = " from TeeCrmContractRecvPayments recvPayments where 1=1 ";
			}else{
				//数据管理权限
				TeeManagerPostPersonDataPrivModel dataPrivModel = personManagerI.getManagerPostPersonIdsPriv(map, TeeModelIdConst.CRM_CUSTOMER_CONTRACT_MANAGER_POST_PRIV, "0");
				if(dataPrivModel.getPrivType().equals("0")){//空
					paraList.add(person.getUuid());
					hql = hql + " and contract.createUser.uuid = ?";//加上自己创建的
				}else if(dataPrivModel.getPrivType().equals("ALL")){//所有
					// hql = "from TeeNews n where  1 = 1";
				}else{
					List<Integer> pIdList = dataPrivModel.getPersonIds();//获取权限
					pIdList.add(person.getUuid());
					if(pIdList.size() > 0){
						String personIdsSql =  TeeDbUtility.IN("contract.createUser.uuid", pIdList);
						hql = hql + " and " + personIdsSql;
					}else{
						j.setTotal(0L);
						j.setRows(modelList);// 设置返回的行
						return j;
					}
				}
			}
		}
		
		
		if (!TeeUtility.isNullorEmpty(model.getContractName())) {//合同名称
			paraList.add("%" + model.getContractName() + "%");
			hql = hql + " and contract.contractName like ?";
		}
		if (!TeeUtility.isNullorEmpty(model.getContractNo())) {//合同编号
			paraList.add("%" + model.getContractNo() + "%");
			hql = hql + " and contract.contractNo like ?";
		}
		if (!TeeUtility.isNullorEmpty(model.getRecvPayPerson())) {//收款人
			paraList.add("%" + model.getRecvPayPerson() + "%");
			hql = hql + " and recvPayPerson like ?";
		}
		if (!TeeUtility.isNullorEmpty(model.getRecvPaymentFlag())) {//回款标记
			paraList.add(  model.getRecvPaymentFlag() );
			hql = hql + " and recvPaymentFlag = ?";
		}
		if (!TeeUtility.isNullorEmpty(model.getPlanRecvDate())) {//预计收款时间
			paraList.add(model.getPlanRecvDate() );
			hql = hql + " and planRecvDate <= ?";
		}
		String totalHql = " select count(*) " + hql;
		j.setTotal(contractRecvPaymentsDao.countByList(totalHql, paraList));// 设置总记录数
		if (dm.getSort() != null) {// 设置排序
			hql += " order by " + dm.getSort() + " " + dm.getOrder();
		} else {
			hql += " order by createTime desc";
		}
		List<TeeCrmContractRecvPayments> contactList = contractRecvPaymentsDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(),dm.getRows(), paraList);// 查询
		if (contactList != null && contactList.size() > 0) {
			for (TeeCrmContractRecvPayments contract : contactList) {
				TeeCrmContractRecvPaymentsModel tempModel = parseModel(
						contract, true);
				modelList.add(tempModel);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	/**
	 * 根据合同Id 获取产品列表模型
	 * 
	 * @param contractId
	 * @return
	 */
	public List<TeeCrmContractRecvPaymentsModel> getRecvMentsModelList(
			int contractId) {
		List<TeeCrmContractRecvPaymentsModel> modelList = new ArrayList<TeeCrmContractRecvPaymentsModel>();
		List<TeeCrmContractRecvPayments> list = contractRecvPaymentsDao
				.getCrmProductsItemByContractId(contractId);
		for (int i = 0; i < list.size(); i++) {
			TeeCrmContractRecvPaymentsModel model = parseModel(list.get(i),true);
			modelList.add(model);
		}
		return modelList;
	}

	/**
	 * 转模型
	 * 
	 * @param item
	 * @return
	 */
	public TeeCrmContractRecvPaymentsModel parseModel(
			TeeCrmContractRecvPayments item, boolean isSimple) {
		TeeCrmContractRecvPaymentsModel model = new TeeCrmContractRecvPaymentsModel();
		if (item == null) {
			return model;
		}

		BeanUtils.copyProperties(item, model);
		TeeCrmContract contract = item.getContract();
		if (contract != null) {
			model.setContractId(contract.getSid() + "");
			model.setContractName(contract.getContractName());
			model.setContractNo(contract.getContractNo());
			model.setContractCreateUser(contract.getCreateUser().getUserName());
		}
		
		if(!TeeUtility.isNullorEmpty(item.getPlanRecvDate())){
			model.setPlanRecvDateStr(TeeUtility.getDateYMDStr(item.getPlanRecvDate()));
		}
		if(!TeeUtility.isNullorEmpty(item.getRecvDate())){
			model.setRecvDateStr(TeeUtility.getDateYMDStr(item.getRecvDate()));
		}
		if(!TeeUtility.isNullorEmpty(item.getCreateTime())){
			model.setCreateTimeStr(TeeUtility.getDateTimeStr(item.getCreateTime()));
		}
		
		
		
		//收款人
		TeePerson managerUser = item.getManagerUser();
		int managerUserId = 0;
		String managerUserName = "";
		if(managerUser != null){
			managerUserId = managerUser.getUuid();
			managerUserName = managerUser.getUserName();
		}
		model.setManagerUserId(managerUserId);
		model.setManagerUserName(managerUserName);
		return model;
	}

	/**
	 * 根据 合同Id 且 by Ids删除分期付款记录
	 * 
	 * @param contractId
	 * @param ids
	 * @return
	 */
	public int delByContractAndIds(int contractId, String ids) {
		int count = 0 ;
		
		List<TeeCrmContractRecvPayments> list = contractRecvPaymentsDao.getByContractIdAndIds(contractId, ids);
		for (int i = 0; i < list.size(); i++) {
			TeeCrmContractRecvPayments temp = list.get(i);
			count = count + 1;
			contractRecvPaymentsDao.deleteByObj(temp);//删除记录
			
			TeeQuartzTask task = new TeeQuartzTask();//删除消息提醒
			task.setModelId(String.valueOf(temp.getSid()));
			task.setModelNo("040");
			task.setType(0);
			MessagePusher.push2Quartz(task);
		}
		return count;
	}

	/**
	 * 根据Id 查询
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request,
			TeeCrmContractRecvPaymentsModel model) {
		TeeJson json = new TeeJson();
		TeeCrmContractRecvPayments recvPayments = contractRecvPaymentsDao.get(model.getSid());
		model = parseModel(recvPayments, false);
		json.setRtState(true);
		json.setRtData(model);
		return json;
	}

	/**
	 * 删除 ByIds
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson deleteByIds(HttpServletRequest request,
			TeeCrmContractRecvPaymentsModel model) {
		TeeJson json = new TeeJson();
		String ids = TeeStringUtil.getString(request.getParameter("ids"));
		String idsArray[] = ids.split(",");
		//删除定时提醒任务
		for (int i = 0; i < idsArray.length; i++) {
			if(!idsArray[i].equals("")){
				TeeQuartzTask task = new TeeQuartzTask();
				task.setModelId(String.valueOf(idsArray[i]));
				task.setModelNo("040");
				task.setType(0);
				MessagePusher.push2Quartz(task);
			}
		}
		int count = contractRecvPaymentsDao.delByIds(ids);
		json.setRtState(true);
		json.setRtData(model);
		return json;
	}
	
	/**
	 * 批量设置回款 ByIds
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson batchRecvPayment(HttpServletRequest request,
			TeeCrmContractRecvPaymentsModel model) {
		TeeJson json = new TeeJson();
		String ids = TeeStringUtil.getString(request.getParameter("ids"));
		int count = contractRecvPaymentsDao.batchRecvPaymentByIds(ids);
		json.setRtState(true);
		json.setRtData(model);
		return json;
	}
	

}
