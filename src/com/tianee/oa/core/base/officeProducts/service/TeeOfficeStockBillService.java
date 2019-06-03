package com.tianee.oa.core.base.officeProducts.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.officeProducts.bean.TeeOfficeProduct;
import com.tianee.oa.core.base.officeProducts.bean.TeeOfficeRecord;
import com.tianee.oa.core.base.officeProducts.bean.TeeOfficeStock;
import com.tianee.oa.core.base.officeProducts.bean.TeeOfficeStockBill;
import com.tianee.oa.core.base.officeProducts.model.TeeOfficeStockBillModel;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeOfficeStockBillService extends TeeBaseService{
	@Autowired
	private TeeSmsManager smsManager;
	
	@Autowired
	private TeeOfficeStockService officeStockService;
	
	/**
	 * 添加库存单
	 * @param officeStockBillModel
	 */
	public void addStockBillModel(TeeOfficeStockBillModel officeStockBillModel){
		TeeOfficeStockBill officeStockBill = new TeeOfficeStockBill();
		BeanUtils.copyProperties(officeStockBillModel, officeStockBill);
		officeStockBill.setProduct((TeeOfficeProduct)simpleDaoSupport.get(TeeOfficeProduct.class, officeStockBillModel.getProductId()));
		officeStockBill.setRegUser((TeePerson)simpleDaoSupport.get(TeePerson.class, officeStockBillModel.getRegUserId()));
		officeStockBill.setValidFlag(1);
		officeStockBill.setRegTime(Calendar.getInstance());
		
		
		simpleDaoSupport.save(officeStockBill);
		
		//将短消息发送给审批员
		Map requestData = new HashMap();
		String ids = "";
		Iterator<TeePerson> it = officeStockBill.getProduct().getAuditors().iterator();
		while(it.hasNext()){
			TeePerson autitor = it.next();
			ids+=autitor.getUuid()+",";
		}
		
		requestData.put("userListIds", ids);
		requestData.put("moduleNo", "028");
		requestData.put("content", "您有新的用品登记需要进行审批["+officeStockBill.getProduct().getProName()+"]。");
		requestData.put("remindUrl", "/system/core/base/officeProducts/manage/audit.jsp");
		smsManager.sendSms(requestData, null);
	}
	
	/**
	 * 模型转实体
	 * @param officeStockBillModel
	 * @param officeStockBill
	 */
	public void modelToEntity(TeeOfficeStockBillModel officeStockBillModel, TeeOfficeStockBill officeStockBill){
		
		officeStockBill.setAuditor((TeePerson)simpleDaoSupport.get(TeePerson.class, officeStockBillModel.getAuditorId()));
		officeStockBill.setOperator((TeePerson)simpleDaoSupport.get(TeePerson.class, officeStockBillModel.getOperatorId()));
		
	}
	
	/**
	 * 实体转模型
	 * @param officeStockBill
	 * @param officeStockBillModel
	 */
	public void entityToModel(TeeOfficeStockBill officeStockBill,TeeOfficeStockBillModel officeStockBillModel){
		BeanUtils.copyProperties(officeStockBill, officeStockBillModel);
		TeePerson auditor = officeStockBill.getAuditor();
		officeStockBillModel.setAuditorId(auditor==null?0:auditor.getUuid());
		officeStockBillModel.setAuditorName(auditor==null?"":auditor.getUserName());
		officeStockBillModel.setFinishTimeDesc(TeeDateUtil.format(officeStockBill.getFinishTime()));
		TeePerson operator = officeStockBill.getOperator();
		officeStockBillModel.setOperatorId(operator==null?0:operator.getUuid());
		officeStockBillModel.setOperatorName(operator==null?"":operator.getUserName());
		if(officeStockBill.getProduct()!=null){
			officeStockBillModel.setCategoryName(officeStockBill.getProduct().getCategory().getCatName());
			officeStockBillModel.setDepositoryName(officeStockBill.getProduct().getCategory().getOfficeDepository().getDeposName());
			officeStockBillModel.setProductId(officeStockBill.getProduct().getSid());
			officeStockBillModel.setProductName(officeStockBill.getProduct().getProName());
			officeStockBillModel.setProductCode(officeStockBill.getProduct().getProCode());
			officeStockBillModel.setStockCount(Integer.parseInt(""+simpleDaoSupport.count("select sum(os.regCount) from TeeOfficeStock os where os.product.sid="+officeStockBill.getProduct().getSid(), null)));
		}
		int operFlag = officeStockBill.getOperFlag();
		if(operFlag==0){
			officeStockBillModel.setOperFlagDesc("未开始调度");
		}else if(operFlag==1){
			officeStockBillModel.setOperFlagDesc("<span style='color:green'>正在调度中</span>");
		}else{
			officeStockBillModel.setOperFlagDesc("调度完毕");
		}
		
		officeStockBillModel.setOperTimeDesc(TeeDateUtil.format(officeStockBill.getOperTime()));
		
		officeStockBillModel.setRegTimeDesc(TeeDateUtil.format(officeStockBill.getRegTime()));
		
		int regType = officeStockBill.getRegType();
		if(regType==1){//领用
			officeStockBillModel.setRegTypeDesc("领用");
		}else if(regType==2){//借用
			officeStockBillModel.setRegTypeDesc("借用");
		}else if(regType==3){//归还
			officeStockBillModel.setRegTypeDesc("归还");
		}else if(regType==4){//入库
			officeStockBillModel.setRegTypeDesc("入库");
		}else if(regType==5){//维护
			officeStockBillModel.setRegTypeDesc("维护");
		}else if(regType==6){//报废
			officeStockBillModel.setRegTypeDesc("报废");
		}
		TeePerson regUser = officeStockBill.getRegUser();
		if(regUser!=null){
			officeStockBillModel.setRegUserId(regUser.getUuid());
			officeStockBillModel.setRegUserName(regUser.getUserName());
		}
		
		officeStockBillModel.setVerTimeDesc(TeeDateUtil.format(officeStockBill.getVerTime()));
	}
	
	/**
	 * 进行归还操作
	 * @param billId
	 */
	public void returnObj(int billId){
		TeeOfficeStockBill bill = (TeeOfficeStockBill) simpleDaoSupport.get(TeeOfficeStockBill.class, billId);
		bill.setObtainFlag(2);//设置为已申请归还标记
		
		//实例化新的归还申请单
		TeeOfficeStockBill newReturnBill = new TeeOfficeStockBill();
		newReturnBill.setRegTime(Calendar.getInstance());
		newReturnBill.setRegType(3);//归还单
		newReturnBill.setRegUser(bill.getRegUser());
		newReturnBill.setProduct(bill.getProduct());
		
		simpleDaoSupport.save(newReturnBill);
		
		//短消息提醒
		Map requestData = new HashMap();
		String ids = "";
		Iterator<TeePerson> it = bill.getProduct().getAuditors().iterator();
		while(it.hasNext()){
			TeePerson autitor = it.next();
			ids+=autitor.getUuid()+",";
		}
		
		requestData.put("userListIds", ids);
		requestData.put("moduleNo", "028");
		requestData.put("content", "您有新的用品[归还]登记需要进行审批["+bill.getProduct().getProName()+"]。");
		requestData.put("remindUrl", "/system/core/base/officeProducts/manage/audit.jsp");
		smsManager.sendSms(requestData, null);
		
	}
	
	/**
	 * 列表
	 * @param dm
	 * @param requestDatas
	 * @return
	 */
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson loginUser = (TeePerson) requestDatas.get(TeeConst.LOGIN_USER);
		loginUser = (TeePerson) simpleDaoSupport.get(TeePerson.class, loginUser.getUuid());
		
		int showFlag = TeeStringUtil.getInteger(requestDatas.get("showFlag"), 0);
		String hql = "from TeeOfficeStockBill osb where 1=1 ";
		if(showFlag==1){//待审批
			hql += " and osb.verFlag=0 and osb.validFlag=1";
		}else if(showFlag==2){//审批通过
			hql += " and osb.verFlag=1 and osb.validFlag=1";
		}else if(showFlag==3){//审批未通过
			hql += " and osb.verFlag=2 and osb.validFlag=1";
		}else if(showFlag==4){//调度中
			hql += " and osb.verFlag=1 and osb.validFlag=1 and osb.operFlag=1";
		}else if(showFlag==5){//作废
			hql += " and osb.validFlag=0";
		}
		
		if(!TeePersonService.checkIsAdminPriv(loginUser)){
			hql += " and osb.regUser.uuid="+loginUser.getUuid();
		}
		
		List params = new ArrayList();
		
		//拼写查询条件
		String productName = TeeStringUtil.getString(requestDatas.get("productName"));
		if(!"".equals(productName)){
			hql += " and osb.product.proName like ?";
			params.add("%"+productName+"%");
		}
		String regBeginTime = TeeStringUtil.getString(requestDatas.get("regBeginTime"));
		if(!"".equals(regBeginTime)){
			hql += " and osb.regTime > ?";
			params.add(TeeDateUtil.parseCalendar("yyyy-MM-dd hh:mm:ss", regBeginTime+" 00:00:00"));
		}
		String regEndTime = TeeStringUtil.getString(requestDatas.get("regEndTime"));
		if(!"".equals(regEndTime)){
			hql += " and osb.regTime < ?";
			params.add(TeeDateUtil.parseCalendar("yyyy-MM-dd hh:mm:ss", regEndTime+" 23:59:59"));
		}
		int regUserId = TeeStringUtil.getInteger(requestDatas.get("regUserId"),0);
		if(regUserId!=0){
			hql += " and osb.regUser.uuid ="+regUserId;
		}
		String operBeginTime = TeeStringUtil.getString(requestDatas.get("operBeginTime"));
		if(!"".equals(operBeginTime)){
			hql += " and osb.operTime > ?";
			params.add(TeeDateUtil.parseCalendar("yyyy-MM-dd hh:mm:ss", operBeginTime+" 00:00:00"));
		}
		String operEndTime = TeeStringUtil.getString(requestDatas.get("operEndTime"));
		if(!"".equals(operEndTime)){
			hql += " and osb.operTime < ?";
			params.add(TeeDateUtil.parseCalendar("yyyy-MM-dd hh:mm:ss", operEndTime+" 23:59:59"));
		}
		int operatorId = TeeStringUtil.getInteger(requestDatas.get("operatorId"),0);
		if(operatorId!=0){
			hql += " and osb.operator.uuid ="+operatorId;
		}
		int billType = TeeStringUtil.getInteger(requestDatas.get("billType"),0);
		if(billType==1){
			hql += " and osb.regType="+1;
		}else if(billType==2){
			hql += " and osb.regType ="+2;
		}
		
		List<TeeOfficeStockBill> list = simpleDaoSupport.pageFind(hql+" order by osb.sid desc", dm.getRows()*(dm.getPage()-1), dm.getRows(), params.toArray());
		long total = simpleDaoSupport.count("select count(*) "+hql, params.toArray());
		List<TeeOfficeStockBillModel> models = new ArrayList<TeeOfficeStockBillModel>();
		for(TeeOfficeStockBill bill:list){
			TeeOfficeStockBillModel model = new TeeOfficeStockBillModel();
			entityToModel(bill, model);
			models.add(model);
		}
		
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	/**
	 * 列表（审批管理）
	 * @param dm
	 * @param requestDatas
	 * @return
	 */
	public TeeEasyuiDataGridJson datagridAdmin(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson loginUser = (TeePerson) requestDatas.get(TeeConst.LOGIN_USER);
		loginUser = (TeePerson) simpleDaoSupport.get(TeePerson.class, loginUser.getUuid());
		int showFlag = TeeStringUtil.getInteger(requestDatas.get("showFlag"), 0);
		String hql = "from TeeOfficeStockBill osb where 1=1 ";
		if(showFlag==1){//待审批
			hql += " and osb.verFlag=0 and osb.validFlag=1";
		}else if(showFlag==2){//审批通过
			hql += " and osb.verFlag=1 and osb.validFlag=1";
		}else if(showFlag==3){//审批未通过
			hql += " and osb.verFlag=2 and osb.validFlag=1";
		}else if(showFlag==4){//调度中
			hql += " and osb.verFlag=1 and osb.validFlag=1 and osb.operFlag=1";
		}else if(showFlag==5){//作废
			hql += " and osb.validFlag=0";
		}
		
		if(!TeePersonService.checkIsAdminPriv(loginUser)){
			hql += " and exists (select 1 from osb.product.auditors auditors where auditors.uuid ="+loginUser.getUuid()+")";
		}
		
		List<TeeOfficeStockBill> list = simpleDaoSupport.pageFind(hql+" order by osb.sid desc", dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		List<TeeOfficeStockBillModel> models = new ArrayList<TeeOfficeStockBillModel>();
		for(TeeOfficeStockBill bill:list){
			TeeOfficeStockBillModel model = new TeeOfficeStockBillModel();
			entityToModel(bill, model);
			models.add(model);
		}
		
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	/**
	 * 调度列表（管理）
	 * @param dm
	 * @param requestDatas
	 * @return
	 */
	public TeeEasyuiDataGridJson datagridOperates(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson loginUser = (TeePerson) requestDatas.get(TeeConst.LOGIN_USER);
		loginUser = (TeePerson) simpleDaoSupport.get(TeePerson.class, loginUser.getUuid());
		int showFlag = TeeStringUtil.getInteger(requestDatas.get("showFlag"), 0);
		String hql = "from TeeOfficeStockBill osb where 1=1 ";
		if(showFlag==1){//领用类
			hql += " and osb.verFlag=1 and osb.validFlag=1 and osb.product.regType=1";
		}else if(showFlag==2){//借用类
			hql += " and osb.verFlag=1 and osb.validFlag=1 and osb.product.regType=2";
		}
		
		if(!TeePersonService.checkIsAdminPriv(loginUser)){
			hql += " and osb.operator.uuid ="+loginUser.getUuid()+"";
		}
		
		List<TeeOfficeStockBill> list = simpleDaoSupport.pageFind(hql+" order by osb.sid desc", dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		List<TeeOfficeStockBillModel> models = new ArrayList<TeeOfficeStockBillModel>();
		for(TeeOfficeStockBill bill:list){
			TeeOfficeStockBillModel model = new TeeOfficeStockBillModel();
			entityToModel(bill, model);
			models.add(model);
		}
		
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	/**
	 * 作废
	 * @param loginUser 操作人
	 * @param billIds 库存单id数组
	 */
	public void doInvalid(TeePerson loginUser,int billIds[]){
		TeeOfficeStockBill bill = null;
		for(int i=0;i<billIds.length;i++){
			bill = (TeeOfficeStockBill) simpleDaoSupport.get(TeeOfficeStockBill.class, billIds[i]);
			bill.setValidFlag(0);
			simpleDaoSupport.update(bill);
		}
	}
	
	/**
	 * 审批
	 * @param loginUser 操作人
	 * @param billIds 库存单id数组
	 * @param auditType 审批类型，1、通过  0、不通过
	 * @param operator 调度人
	 * @param remark 备注
	 */
	public void doAudit(TeePerson loginUser,int billIds[],int auditType,TeePerson operator,String remark){
		TeeOfficeStockBill bill = null;
		for(int i=0;i<billIds.length;i++){
			bill = (TeeOfficeStockBill) simpleDaoSupport.get(TeeOfficeStockBill.class, billIds[i]);
			bill.setAuditor(loginUser);
			bill.setVerTime(Calendar.getInstance());
			
			if(bill.getRegType()==3){//如果是归还的话，将obtain状态改成3（归还中）
				bill.setObtainFlag(3);
			}
			
			if(auditType==0){//不通过
				bill.setVerFlag(2);
				bill.setRemark(remark);
				
				Map requestData = new HashMap();
				requestData.put("userListIds", bill.getRegUser().getUuid());
				requestData.put("moduleNo", "028");
				requestData.put("content", "您的用品申请["+bill.getProduct().getProName()+"]已被审批人["+loginUser.getUserName()+"]拒绝，拒绝原因：["+remark+"]。");
				smsManager.sendSms(requestData, null);
				
			}else{//通过
				bill.setVerFlag(1);
				bill.setOperator(operator);
				
				Map requestData = new HashMap();
				requestData.put("userListIds", bill.getRegUser().getUuid());
				requestData.put("moduleNo", "028");
				requestData.put("content", "您的用品申请["+bill.getProduct().getProName()+"]已通过，请等待调度员进行用品调度。");
				//发送给申请人
				smsManager.sendSms(requestData, loginUser);
				
				//发送给调度员
				requestData.put("remindUrl", "/system/core/base/officeProducts/manage/dispatch_info.jsp");
				requestData.put("content", "该用品["+bill.getProduct().getProName()+"]已通过审批，请进行相关调度。");
				smsManager.sendSms(requestData, loginUser);
				
				
//				//创建库存信息
//				TeeOfficeStock officeStock = new TeeOfficeStock();
//				officeStock.setCreateTime(Calendar.getInstance());
//				officeStock.setProduct(bill.getProduct());
//				if(bill.getRegType()==1){//领用或者借用
//					officeStock.setRegCount(-bill.getRegCount());
//				}else{//归还
//					officeStock.setRegCount(bill.getRegCount());
//				}
//				officeStock.setRegType(bill.getRegType());
//				simpleDaoSupport.save(officeStock);
			}
			simpleDaoSupport.update(bill);
		}
	}
	
	/**
	 * 根据库存单id返回调度者
	 * @param billId
	 * @return
	 */
	public List<Map> getOperatorsByBillId(int billId){
		TeeOfficeStockBill bill = (TeeOfficeStockBill) simpleDaoSupport.get(TeeOfficeStockBill.class, billId);
		List<Map> lists = new ArrayList<Map>();
		Set<TeePerson> operators = bill.getProduct().getCategory().getOfficeDepository().getOperators();
		for(TeePerson operator:operators){
			Map userInfo = new HashMap();
			userInfo.put("uuid", operator.getUuid());
			userInfo.put("userName", operator.getUserName());
			lists.add(userInfo);
		}
		
		return lists;
	}
	
	/**
	 * 开始调度
	 * @param user
	 * @param billId
	 */
	public void doOperated(TeePerson user,int billId){
		TeeOfficeStockBill bill = (TeeOfficeStockBill) simpleDaoSupport.get(TeeOfficeStockBill.class, billId);
		bill.setOperTime(Calendar.getInstance());
		bill.setOperFlag(1);//调度状态设置为1，正在调度
		TeeOfficeProduct op = bill.getProduct();
		
		//创建库存信息
		TeeOfficeStock officeStock = new TeeOfficeStock();
		officeStock.setCreateTime(Calendar.getInstance());
		officeStock.setProduct(bill.getProduct());
		String actionDesc = "";
		int count = 0;
		if(bill.getRegType()==1 || bill.getRegType()==2){//领用或者借用
			officeStock.setRegCount(-bill.getRegCount());
			//获取该物品库存量
			TeeOfficeProduct officeProduct = bill.getProduct();
			count = Integer.parseInt(""+simpleDaoSupport.count("select sum(os.regCount) from TeeOfficeStock os where os.product.sid="+officeProduct.getSid(), null));
			
			if(officeStock.getRegCount()+count<0){
				throw new TeeOperationException("所申请的用品库存量不足");
			}
			
			Map requestData = new HashMap();
			requestData.put("userListIds", bill.getRegUser().getUuid());
			requestData.put("moduleNo", "028");
			requestData.put("content", "您的用品(领用/借用)申请["+bill.getProduct().getProName()+"]已被["+user.getUserName()+"]调度，请领取该用品。");
			requestData.put("remindUrl", "/system/core/base/officeProducts/public/register.jsp");
			smsManager.sendSms(requestData, null);
			
		}else{//归还
			officeStock.setRegCount(bill.getRegCount());
			
			Map requestData = new HashMap();
			requestData.put("userListIds", bill.getRegUser().getUuid());
			requestData.put("moduleNo", "028");
			requestData.put("content", "您的用品归还申请["+bill.getProduct().getProName()+"]已被["+user.getUserName()+"]调度，请进行归还确认。");
			requestData.put("remindUrl", "/system/core/base/officeProducts/public/register.jsp");
			smsManager.sendSms(requestData, null);
		}
		officeStock.setRegType(bill.getRegType());
		
		//日志记录
		TeeOfficeRecord record = new TeeOfficeRecord();
		if(bill.getRegType()==1){
			record.setActionDesc("领用");
			record.setRecordType(3);
		}else if(bill.getRegType()==2){
			record.setActionDesc("借用");
			record.setRecordType(4);
		}else{
			record.setActionDesc("归还");
			record.setRecordType(5);
		}
		
		record.setActionTime(Calendar.getInstance());
		record.setCategoryName(op.getCategory().getCatName());
		record.setDepositoryName(op.getCategory().getOfficeDepository().getDeposName());
		record.setOriginStock(count+"");
		record.setRegCount(Math.abs(bill.getRegCount())+"");
		record.setProCode(op.getProCode());
		record.setProName(op.getProName());
		record.setRegType(bill.getRegType());
		record.setRegUserId(bill.getRegUser().getUuid());
		record.setRegUserName(bill.getRegUser().getUserName()+"");
		record.setActionTimeDesc(TeeDateUtil.format(Calendar.getInstance()));
		record.setAuditorId(bill.getAuditor().getUuid());
		record.setAuditorName(bill.getAuditor().getUserName());
		if(bill.getOperator()!=null){
			record.setOperatorId(bill.getOperator().getUuid());
			record.setOperatorName(bill.getOperator().getUserName());
		}
		
		simpleDaoSupport.save(record);
		
		
		simpleDaoSupport.save(officeStock);
		
		
		officeStockService.checkStockLimit(op.getSid());
	}
	
	/**
	 * 已确认获取（归还）
	 * @param user
	 * @param billId
	 */
	public void doObtain(TeePerson user,int billId){
		TeeOfficeStockBill bill = (TeeOfficeStockBill) simpleDaoSupport.get(TeeOfficeStockBill.class, billId);
		bill.setFinishTime(Calendar.getInstance());
		if(bill.getRegType()==3){
			bill.setObtainFlag(4);//已归还
		}else{
			bill.setObtainFlag(1);//已领取
		}
		bill.setOperFlag(2);//调度完毕
	}
	
	
	/**
	 * 获取申请用品信息
	 * */
	public TeeJson findStockPublic(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeeOfficeStockBillModel model = new TeeOfficeStockBillModel();
		String sidStr = request.getParameter("sid");
		int sid = TeeStringUtil.getInteger(sidStr, 0);
		if(sid>0){
			String sql="from TeeOfficeStockBill where sid=?";
			List<TeeOfficeStockBill> find = simpleDaoSupport.find(sql, new Object[]{sid});
			if(find!=null && find.size()>0){
				entityToModel(find.get(0), model);
			}
		}
		json.setRtData(model);
		return json;
	}

	/**
	 * 查看用品调度的具体信息
	 * */
	public TeeJson findPublicDiaoDau(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeOfficeStockBillModel model = new TeeOfficeStockBillModel();
		if(sid>0){
			List<TeeOfficeStockBill> find = simpleDaoSupport.find("from TeeOfficeStockBill where sid=?", new Object[]{sid});
			if(find!=null && find.size()>0){
				entityToModel(find.get(0), model);
			}
		}
		json.setRtData(model);
		return json;
	}
}
