package com.tianee.oa.subsys.doc.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.tools.ant.filters.StringInputStream;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.general.service.TeeSmsSender;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.phoneSms.bean.TeeSmsSendPhone;
import com.tianee.oa.core.workFlowFrame.dataloader.TeeSimpleDataLoaderInterface;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunAipTemplate;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunDoc;
import com.tianee.oa.core.workflow.flowrun.model.TeeFlowRunAipTemplateModel;
import com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunAipTemplateServiceInterface;
import com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface;
import com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrivServiveInterface;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.doc.bean.TeeDocumentDelivery;
import com.tianee.oa.subsys.doc.bean.TeeDocumentDeliveryRecord;
import com.tianee.oa.subsys.doc.bean.TeeDocumentFlowPriv;
import com.tianee.oa.subsys.doc.bean.TeeDocumentRecMapping;
import com.tianee.oa.subsys.doc.bean.TeeDocumentView;
import com.tianee.oa.subsys.doc.bean.TeeDocumentViewRecord;
import com.tianee.oa.subsys.doc.model.TeeDocumentDeliveryModel;
import com.tianee.oa.subsys.doc.model.TeeDocumentViewModel;
import com.tianee.oa.util.workflow.TeeWorkflowHelperInterface;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeDocService extends TeeBaseService {
	@Autowired
	private TeeBaseUpload baseUpload;
	
	@Autowired
	private TeeSimpleDataLoaderInterface simpleDataLoader;
	
	@Autowired
	private TeeSmsSender sender;

	@Autowired
	private TeeWorkflowServiceInterface workflowService;

	@Autowired
	private TeeFlowFormServiceInterface flowFormService;

	@Autowired
	private TeeWorkflowHelperInterface helper;

	@Autowired
	private TeeAttachmentService attachmentService;

	@Autowired
	private TeeSimpleDataLoaderInterface dataLoader;

	@Autowired
	private TeeFlowPrivServiveInterface flowPrivService;

	@Autowired
	private TeeFlowRunAipTemplateServiceInterface  flowRunAipTemplateService;
	
	// 公文分发
	public void sendDoc(Map requestData) {
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		
		//获取正文内容权限
		int contentPriv=TeeStringUtil.getInteger(requestData.get("contentPriv"), 0);
		int runId = TeeStringUtil.getInteger(requestData.get("runId"), 0);
		int sendDeptIds[] = TeeStringUtil.parseIntegerArray(requestData.get(
				"sendDeptIds").toString());
		// 是否进行签收提醒
		int isRecRemind = TeeStringUtil.getInteger(
				requestData.get("isRecRemind"), 0);
		
		//是否进行手机短信提醒
		int isPhoneRemind=TeeStringUtil.getInteger(
				requestData.get("isPhoneRemind"), 0);
		// 部门打印份数和部门是否允许下载
		List<Map> mapList = new ArrayList<Map>();
		String jsonStr = (String) requestData.get("jsonStr");
		List<Map<String,String>> listMap=TeeJsonUtil.JsonStr2MapList(jsonStr);
		
		
		

		TeeFlowRun flowRun = (TeeFlowRun) simpleDaoSupport.get(
				TeeFlowRun.class, runId);
		TeeFlowType flowType = flowRun.getFlowType();
		List<TeeFormItem> formItems = flowFormService
				.getLatestFormItemsByOriginForm(flowType.getForm());

		Map<String, String> flowRunData = helper.getFlowRunData(runId,
				flowType.getSid());

		TeeDocumentFlowPriv documentFlowPriv = (TeeDocumentFlowPriv) simpleDaoSupport
				.unique("from TeeDocumentFlowPriv where flowType.sid=?",
						new Object[] { flowType.getSid() });
		if (documentFlowPriv == null) {
			throw new TeeOperationException("该流程没有配置公文字段映射规则，请到公文管理->公文设置中进行配置");
		}
		Map<String, String> fieldMapping = TeeJsonUtil
				.JsonStr2Map(documentFlowPriv.getFieldMapping());
		Set<String> keys = fieldMapping.keySet();

		TeeFormItem tmp = null;
		Map smsData = new HashMap();
		List<TeeDocumentRecMapping> recUserMaps = simpleDaoSupport.find(
				"from TeeDocumentRecMapping", null);
		TeeDocumentDeliveryRecord record = new TeeDocumentDeliveryRecord();
		int counter = 0;
		// 遍历部门
		for (int deptId : sendDeptIds) {
			counter++;
			TeeDocumentDelivery delivery = new TeeDocumentDelivery();
			delivery.setRecDept(deptId);// 设置部门ID
			delivery.setFlag(0);
			delivery.setSendTime(Calendar.getInstance());
			delivery.setRunId(runId);
			delivery.setSendUser(loginUser.getUuid());
			delivery.setFlowId(flowType.getSid());
			// 设置是否进行签收提醒
			record.setIsRecRemind(isRecRemind);
            //设置内容权限
			record.setContentPriv(contentPriv);
			//设置部门打印分数 和 是否允许下载
			for(int i=0;i<listMap.size();i++){
				if(deptId==TeeStringUtil.getInteger(listMap.get(i).get("deptId"), 0)){
					delivery.setPrintNum(TeeStringUtil.getInteger(listMap.get(i).get("printNum"), 0));	
					delivery.setIsDownLoad(TeeStringUtil.getInteger(listMap.get(i).get("isDownLoad"), 0));	
				}	
			}
			
			
			
			delivery.setRecord(record);
			if (counter == 1) {
				record.setSendTime(Calendar.getInstance());
				record.setSendUser(loginUser);
				record.setFlowRun(flowRun);
				record.setFlowId(flowType.getSid());
			}

			// 查找对应的字段，并映射到发文分发实体中
			for (String key : keys) {
				tmp = TeeFormItem.getItemByTitle(formItems, key);
				if (tmp != null) {
					if ("公文标题".equals(fieldMapping.get(key).toString())) {
						delivery.setBt(flowRunData.get(tmp.getName()));
						if (counter == 1) {
							record.setBt(flowRunData.get(tmp.getName()));
						}
					} else if ("字号".equals(fieldMapping.get(key).toString())) {
						delivery.setZh(flowRunData.get(tmp.getName()));
						if (counter == 1) {
							record.setZh(flowRunData.get(tmp.getName()));
						}
					} else if ("编号".equals(fieldMapping.get(key).toString())) {
						delivery.setBh(flowRunData.get(tmp.getName()));
						if (counter == 1) {
							record.setBh(flowRunData.get(tmp.getName()));
						}
					} else if ("秘密等级".equals(fieldMapping.get(key).toString())) {
						delivery.setMmdj(flowRunData.get(tmp.getName()));
						if (counter == 1) {
							record.setMmdj(flowRunData.get(tmp.getName()));
						}
					} else if ("紧急程度".equals(fieldMapping.get(key).toString())) {
						delivery.setJjcd(flowRunData.get(tmp.getName()));
						if (counter == 1) {
							record.setJjcd(flowRunData.get(tmp.getName()));
						}
					} else if ("发文单位".equals(fieldMapping.get(key).toString())) {
						delivery.setFwdw(flowRunData.get(tmp.getName()));
						if (counter == 1) {
							record.setFwdw(flowRunData.get(tmp.getName()));
						}
					} else if ("抄送".equals(fieldMapping.get(key).toString())) {
						delivery.setCs(flowRunData.get(tmp.getName()));
						if (counter == 1) {
							record.setCs(flowRunData.get(tmp.getName()));
						}
					} else if ("主送".equals(fieldMapping.get(key).toString())) {
						delivery.setZs(flowRunData.get(tmp.getName()));
						if (counter == 1) {
							record.setZs(flowRunData.get(tmp.getName()));
						}
					} else if ("共印份数".equals(fieldMapping.get(key).toString())) {
						delivery.setGyfs(flowRunData.get(tmp.getName()));
						if (counter == 1) {
							record.setGyfs(flowRunData.get(tmp.getName()));
						}
					} else if ("附件".equals(fieldMapping.get(key).toString())) {
						delivery.setFj(flowRunData.get(tmp.getName()));
						if (counter == 1) {
							record.setFj(flowRunData.get(tmp.getName()));
						}
					} else if ("主题词".equals(fieldMapping.get(key).toString())) {
						delivery.setZtc(flowRunData.get(tmp.getName()));
						if (counter == 1) {
							record.setZtc(flowRunData.get(tmp.getName()));
						}
					} else if ("备注".equals(fieldMapping.get(key).toString())) {
						delivery.setBz(flowRunData.get(tmp.getName()));
						if (counter == 1) {
							record.setBz(flowRunData.get(tmp.getName()));
						}
					}
				}
			}
			if (counter == 1) {
				simpleDaoSupport.save(record);
			}
			simpleDaoSupport.save(delivery);

			for (TeeDocumentRecMapping recMapping : recUserMaps) {
				if (recMapping.getDept().getUuid() == deptId) {

					// 发送sms短消息
					smsData.put("content", "您有一个待签收公文 《" + delivery.getBt()
							+ "》，请尽快签收。");
					smsData.put("remindUrl",
							"/system/subsys/doc/daishou/print/index.jsp?uuid="
									+ delivery.getUuid());
					// 手机端事务提醒
					smsData.put("remindUrl1",
							"/system/mobile/phone/document/doc_rec_detail.jsp?uuid="
									+ delivery.getUuid());
					smsData.put("moduleNo", "051");
					smsData.put("userSet", recMapping.getPrivUsers());
					sender.sendSms(smsData, loginUser);
					
					
					//发送手机短信提醒
					if(isPhoneRemind==1){
						Set<TeePerson> personSet=recMapping.getPrivUsers();
						for (TeePerson teePerson : personSet) {
							String phone=teePerson.getMobilNo();
							if(!TeeUtility.isNullorEmpty(phone)){//判断手机号不为空
								TeeSmsSendPhone smsSendPhone=new TeeSmsSendPhone();
								smsSendPhone.setContent("您有一个待签收公文 《" + delivery.getBt()
							+ "》，请尽快签收。");
								smsSendPhone.setFromId(loginUser.getUuid());
								smsSendPhone.setFromName(loginUser.getUserName());
								smsSendPhone.setPhone(phone);
								smsSendPhone.setSendFlag(0);
								smsSendPhone.setSendNumber(0);
								smsSendPhone.setSendTime(Calendar.getInstance());
								smsSendPhone.setToId(teePerson.getUuid());
								smsSendPhone.setToName(teePerson.getUserName());
								
								simpleDaoSupport.save(smsSendPhone);
							}
						}
					}
					
					
					break;
				}
			}
		}
		flowRun.setSendFlag(1);
	}

	// 公文传阅
	public void viewDoc(Map requestData) {
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		//获取内容权限
		int contentPriv=TeeStringUtil.getInteger(requestData.get("contentPriv"),0);
		int runId = TeeStringUtil.getInteger(requestData.get("runId"), 0);
		int sendUserIds[] = TeeStringUtil.parseIntegerArray(requestData.get(
				"sendUserIds").toString());
		// 签阅是否进行消息提醒
		int isReadRemind = TeeStringUtil.getInteger(
				requestData.get("isReadRemind"), 0);
		//是否进行短信提醒
		int isPhoneRemind= TeeStringUtil.getInteger(
				requestData.get("isPhoneRemind"), 0);
		// 部门打印份数和部门是否允许下载
		List<Map> mapList = new ArrayList<Map>();
		String jsonStr = (String) requestData.get("jsonStr");
		List<Map<String,String>> listMap=TeeJsonUtil.JsonStr2MapList(jsonStr);
		
		
		

		TeeFlowRun flowRun = (TeeFlowRun) simpleDaoSupport.get(
				TeeFlowRun.class, runId);
		TeeFlowType flowType = flowRun.getFlowType();
		List<TeeFormItem> formItems = flowFormService
				.getLatestFormItemsByOriginForm(flowType.getForm());

		Map<String, String> flowRunData = helper.getFlowRunData(runId,
				flowType.getSid());

		TeeDocumentFlowPriv documentFlowPriv = (TeeDocumentFlowPriv) simpleDaoSupport
				.unique("from TeeDocumentFlowPriv where flowType.sid=?",
						new Object[] { flowType.getSid() });
		if (documentFlowPriv == null) {
			throw new TeeOperationException("该流程没有配置公文字段映射规则，请到公文管理->公文设置中进行配置");
		}
		Map<String, String> fieldMapping = TeeJsonUtil
				.JsonStr2Map(documentFlowPriv.getFieldMapping());
		Set<String> keys = fieldMapping.keySet();

		TeeDocumentViewRecord record = new TeeDocumentViewRecord();
		int counter = 0;
		Map smsData = new HashMap();
		TeeFormItem tmp = null;
		// 遍历人员
		for (int userId : sendUserIds) {
			counter++;
			TeeDocumentView view = new TeeDocumentView();
			view.setRecUser(userId);
			view.setRunId(runId);
			view.setSendUser(loginUser.getUuid());
			view.setSendTime(Calendar.getInstance());
			view.setFlowId(flowType.getSid());
			record.setIsReadRemind(isReadRemind);
			record.setContentPriv(contentPriv);
			view.setRecord(record);
			
			//设置人员打印分数 和 是否允许下载
			for(int i=0;i<listMap.size();i++){
				if(userId==TeeStringUtil.getInteger(listMap.get(i).get("userId"), 0)){
					view.setPrintNum(TeeStringUtil.getInteger(listMap.get(i).get("printNum"), 0));	
					view.setIsDownLoad(TeeStringUtil.getInteger(listMap.get(i).get("isDownLoad"), 0));	
				}	
			}
			
			
			if (counter == 1) {
				record.setSendTime(Calendar.getInstance());
				record.setSendUser(loginUser);
				record.setFlowRun(flowRun);
				record.setFlowId(flowType.getSid());
			}

			// 查找对应的字段，并映射到发文分发实体中
			for (String key : keys) {
				tmp = TeeFormItem.getItemByTitle(formItems, key);
				if (tmp != null) {
					if ("公文标题".equals(fieldMapping.get(key).toString())) {
						view.setBt(flowRunData.get(tmp.getName()));
						if (counter == 1) {
							record.setBt(flowRunData.get(tmp.getName()));
						}
					} else if ("字号".equals(fieldMapping.get(key).toString())) {
						view.setZh(flowRunData.get(tmp.getName()));
						if (counter == 1) {
							record.setZh(flowRunData.get(tmp.getName()));
						}
					} else if ("编号".equals(fieldMapping.get(key).toString())) {
						view.setBh(flowRunData.get(tmp.getName()));
						if (counter == 1) {
							record.setBh(flowRunData.get(tmp.getName()));
						}
					} else if ("秘密等级".equals(fieldMapping.get(key).toString())) {
						view.setMmdj(flowRunData.get(tmp.getName()));
						if (counter == 1) {
							record.setMmdj(flowRunData.get(tmp.getName()));
						}
					} else if ("紧急程度".equals(fieldMapping.get(key).toString())) {
						view.setJjcd(flowRunData.get(tmp.getName()));
						if (counter == 1) {
							record.setJjcd(flowRunData.get(tmp.getName()));
						}
					} else if ("发文单位".equals(fieldMapping.get(key).toString())) {
						view.setFwdw(flowRunData.get(tmp.getName()));
						if (counter == 1) {
							record.setFwdw(flowRunData.get(tmp.getName()));
						}
					} else if ("抄送".equals(fieldMapping.get(key).toString())) {
						view.setCs(flowRunData.get(tmp.getName()));
						if (counter == 1) {
							record.setCs(flowRunData.get(tmp.getName()));
						}
					} else if ("主送".equals(fieldMapping.get(key).toString())) {
						view.setZs(flowRunData.get(tmp.getName()));
						if (counter == 1) {
							record.setZs(flowRunData.get(tmp.getName()));
						}
					} else if ("共印份数".equals(fieldMapping.get(key).toString())) {
						view.setGyfs(flowRunData.get(tmp.getName()));
						if (counter == 1) {
							record.setGyfs(flowRunData.get(tmp.getName()));
						}
					} else if ("附件".equals(fieldMapping.get(key).toString())) {
						view.setFj(flowRunData.get(tmp.getName()));
						if (counter == 1) {
							record.setFj(flowRunData.get(tmp.getName()));
						}
					} else if ("主题词".equals(fieldMapping.get(key).toString())) {
						view.setZtc(flowRunData.get(tmp.getName()));
						if (counter == 1) {
							record.setZtc(flowRunData.get(tmp.getName()));
						}
					} else if ("备注".equals(fieldMapping.get(key).toString())) {
						view.setBz(flowRunData.get(tmp.getName()));
						if (counter == 1) {
							record.setBz(flowRunData.get(tmp.getName()));
						}
					}
				}
			}
			if (counter == 1) {
				simpleDaoSupport.save(record);
			}
			simpleDaoSupport.save(view);

			// 发送sms短消息
			smsData.put("content", "您有一个待阅公文《" + view.getBt() + "》，请尽快查阅。");
			smsData.put(
					"remindUrl",
					"/system/subsys/doc/daiyue/print/index.jsp?uuid="
							+ view.getUuid());
			// 手机端事务提醒
			smsData.put("remindUrl1",
					"/system/mobile/phone/document/doc_view_detail.jsp?uuid="
							+ view.getUuid());
			smsData.put("moduleNo", "051");
			smsData.put("userListIds", userId + "");
			sender.sendSms(smsData, loginUser);

			//发送短信消息
			if(isPhoneRemind==1){
				TeePerson person=(TeePerson) simpleDaoSupport.get(TeePerson.class,userId);
				if(person!=null){
					String phone=person.getMobilNo();
					if(!TeeUtility.isNullorEmpty(phone)){
						TeeSmsSendPhone smsSendPhone=new TeeSmsSendPhone();
						smsSendPhone.setContent("您有一个待阅公文《" + view.getBt() + "》，请尽快查阅。");
						smsSendPhone.setFromId(loginUser.getUuid());
						smsSendPhone.setFromName(loginUser.getUserName());
						smsSendPhone.setPhone(phone);
						smsSendPhone.setSendFlag(0);
						smsSendPhone.setSendNumber(0);
						smsSendPhone.setSendTime(Calendar.getInstance());
						smsSendPhone.setToId(person.getUuid());
						smsSendPhone.setToName(person.getUserName());
						
						simpleDaoSupport.save(smsSendPhone);
					}
							
				}
			}
			
			
		}
	}

	/**
	 * 待阅件
	 * 
	 * @param queryParams
	 * @param loginUser
	 * @param dataGridModel
	 * @return
	 */
	public TeeEasyuiDataGridJson DaiYue(Map queryParams, TeePerson loginUser,
			TeeDataGridModel dataGridModel) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		int type = TeeStringUtil.getInteger(queryParams.get("type"), 0);
		String flowIds = TeeStringUtil.getString(queryParams.get("flowId"), "");
		String bt = TeeStringUtil.getString(queryParams.get("bt"), "");// 标题
		String fwdw = TeeStringUtil.getString(queryParams.get("fwdw"), "");// 来文单位
		String zh = TeeStringUtil.getString(queryParams.get("zh"), "");// 字号

		if (flowIds.endsWith(",")) {
			flowIds = flowIds.substring(0, flowIds.length() - 1);
		}

		StringBuffer hql = new StringBuffer();
		hql.append("from TeeDocumentView v where v.flag=" + type
				+ " and v.recUser=" + loginUser.getUuid());

		List param = new ArrayList();
		if (!TeeUtility.isNullorEmpty(flowIds)) {
			hql.append(" and v.flowId in(" + flowIds + ")");
		}

		if (!TeeUtility.isNullorEmpty(bt)) {
			hql.append(" and v.bt like '%" + TeeDbUtility.formatString(bt)
					+ "%'");
		}

		if (!TeeUtility.isNullorEmpty(fwdw)) {
			hql.append(" and v.fwdw like '%" + TeeDbUtility.formatString(fwdw)
					+ "%'");
		}

		if (!TeeUtility.isNullorEmpty(zh)) {
			hql.append(" and v.zh like '%" + TeeDbUtility.formatString(zh)
					+ "%'");
		}

		// 默认排序
		if (dataGridModel.getOrder() == null
				|| "".equals(dataGridModel.getOrder())) {
			dataGridModel.setSort("v.sendTime");
			dataGridModel.setOrder("desc");
		}

		List<TeeDocumentView> views = simpleDaoSupport.pageFindByList(
				hql.toString() + " order by " + dataGridModel.getSort() + " "
						+ dataGridModel.getOrder(),
				dataGridModel.getFirstResult(), dataGridModel.getRows(), param);
		long total = simpleDaoSupport.count(
				"select count(*) " + hql.toString(), null);
		dataGridJson.setTotal(total);
		List<TeeDocumentViewModel> modelList = new ArrayList();
		TeeDocumentViewModel viewModel = null;
		TeeDepartment dept = null;
		TeePerson p = null;
		for (TeeDocumentView view : views) {
			viewModel = new TeeDocumentViewModel();
			BeanUtils.copyProperties(view, viewModel);
			p = (TeePerson) simpleDaoSupport.get(TeePerson.class,
					viewModel.getRecUser());
			if (p != null) {
				viewModel.setRecUserName(p.getUserName());
			}
			p = (TeePerson) simpleDaoSupport.get(TeePerson.class,
					viewModel.getSendUser());
			if(view.getRecord()!=null){
				viewModel.setContentPriv(view.getRecord().getContentPriv());
			}
			
			viewModel.setSendUserName(p.getUserName());
			modelList.add(viewModel);
		}
		dataGridJson.setRows(modelList);
		return dataGridJson;
	}

	/**
	 * 待收件
	 * 
	 * @param queryParams
	 * @param loginUser
	 * @param dataGridModel
	 * @return
	 */
	public TeeEasyuiDataGridJson DaiShou(Map queryParams, TeePerson loginUser,
			TeeDataGridModel dataGridModel) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		int type = TeeStringUtil.getInteger(queryParams.get("type"), 0);
		String flowIds = TeeStringUtil.getString(queryParams.get("flowId"), "");
		String bt = TeeStringUtil.getString(queryParams.get("bt"), "");// 标题
		String fwdw = TeeStringUtil.getString(queryParams.get("fwdw"), "");// 来文单位
		String zh = TeeStringUtil.getString(queryParams.get("zh"), "");// 字号

		if (flowIds.endsWith(",")) {
			flowIds = flowIds.substring(0, flowIds.length() - 1);
		}

		String hql = "from TeeDocumentDelivery d where d.flag="
				+ type
				+ " and exists (select 1 from TeeDocumentRecMapping m where m.dept.uuid=d.recDept and exists (select 1 from m.privUsers user where user.uuid="
				+ loginUser.getUuid() + "))";

		List param = new ArrayList();
		if (!TeeUtility.isNullorEmpty(flowIds)) {
			hql += " and d.flowId in(" + flowIds + ")";
		}

		if (!TeeUtility.isNullorEmpty(bt)) {
			hql += " and d.bt like '%" + TeeDbUtility.formatString(bt) + "%'";
		}

		if (!TeeUtility.isNullorEmpty(fwdw)) {
			hql += " and d.fwdw like '%" + TeeDbUtility.formatString(fwdw)
					+ "%'";
		}

		if (!TeeUtility.isNullorEmpty(zh)) {
			hql += " and d.zh like '%" + TeeDbUtility.formatString(zh) + "%'";
		}

		// 默认排序
		if (dataGridModel.getOrder() == null
				|| "".equals(dataGridModel.getOrder())) {
			dataGridModel.setOrder("desc");
			dataGridModel.setSort("d.sendTime");
		}

		List<TeeDocumentDelivery> deliveries = simpleDaoSupport.pageFindByList(
				hql + " order by " + dataGridModel.getSort() + " "
						+ dataGridModel.getOrder(),
				dataGridModel.getFirstResult(), dataGridModel.getRows(), param);
		long total = simpleDaoSupport.count("select count(*) " + hql, null);
		dataGridJson.setTotal(total);
		List<TeeDocumentDeliveryModel> modelList = new ArrayList();
		TeeDocumentDeliveryModel deliveryModel = null;
		TeeDepartment dept = null;
		TeePerson p = null;
		for (TeeDocumentDelivery delivery : deliveries) {
			deliveryModel = new TeeDocumentDeliveryModel();
			BeanUtils.copyProperties(delivery, deliveryModel);
			dept = (TeeDepartment) simpleDaoSupport.get(TeeDepartment.class,
					delivery.getRecDept());
			deliveryModel.setRecDeptName(dept.getDeptName());
			p = (TeePerson) simpleDaoSupport.get(TeePerson.class,
					delivery.getRecUser());
			if (p != null) {
				deliveryModel.setRecUserName(p.getUserName());
			}
			//设置内容权限
            if(delivery.getRecord()!=null){
            	deliveryModel.setContentPriv(delivery.getRecord().getContentPriv());
            }
			modelList.add(deliveryModel);
		}
		dataGridJson.setRows(modelList);
		return dataGridJson;
	}

	/**
	 * 已发送
	 * 
	 * @param queryParams
	 * @param loginUser
	 * @param dataGridModel
	 * @return
	 */
	public TeeEasyuiDataGridJson YiFaSong(Map queryParams, TeePerson loginUser,
			TeeDataGridModel dataGridModel) {
		return null;
	}

	public TeeEasyuiDataGridJson getReceivedWorks(Map queryParams,
			TeePerson loginUser, TeeDataGridModel dataGridModel) {
		// 获取定义的公文
		List<Map> flowPriv = simpleDaoSupport.getMaps(
				"select flowType.sid as FLOWID from TeeDocumentFlowPriv", null);
		StringBuffer flowIds = new StringBuffer();
		for (int i = 0; i < flowPriv.size(); i++) {
			flowIds.append(flowPriv.get(i).get("FLOWID"));
			if (i != flowPriv.size() - 1) {
				flowIds.append(",");
			}
		}
		queryParams.put("flowIds", flowIds.toString());
		return workflowService.getReceivedWorks(queryParams, loginUser,
				dataGridModel);
	}

	public TeeEasyuiDataGridJson getHandledWorks(Map queryParams,
			TeePerson loginUser, TeeDataGridModel dataGridModel) {
		// 获取定义的公文
		List<Map> flowPriv = simpleDaoSupport.getMaps(
				"select flowType.sid as FLOWID from TeeDocumentFlowPriv", null);
		StringBuffer flowIds = new StringBuffer();
		for (int i = 0; i < flowPriv.size(); i++) {
			flowIds.append(flowPriv.get(i).get("FLOWID"));
			if (i != flowPriv.size() - 1) {
				flowIds.append(",");
			}
		}
		queryParams.put("flowIds", flowIds.toString());
		return workflowService.getHandledWorks(queryParams, loginUser,
				dataGridModel);
	}

	public void updateRecFlag(Map requestData) {
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		String uuid = TeeStringUtil.getString(requestData.get("uuid"));
		String remark = TeeStringUtil.getString(requestData.get("remark"));
		int flag = TeeStringUtil.getInteger(requestData.get("flag"), 0);

		TeeDocumentDelivery delivery = (TeeDocumentDelivery) simpleDaoSupport
				.get(TeeDocumentDelivery.class, uuid);
		delivery.setRecTime(Calendar.getInstance());
		delivery.setRecUser(loginUser.getUuid());
		delivery.setFlag(flag);
		delivery.setRemark(remark);

		TeeDepartment dept = (TeeDepartment) simpleDaoSupport.get(
				TeeDepartment.class, delivery.getRecDept());
		TeePerson p = (TeePerson) simpleDaoSupport.get(TeePerson.class,
				delivery.getRecUser());

		// 获取TeeDocumentDeliveryRecord
		TeeDocumentDeliveryRecord record = delivery.getRecord();

		if (record.getIsRecRemind() == 1) {
			Map smsData = new HashMap();
			smsData.put("moduleNo", "051");
			smsData.put("userListIds", delivery.getSendUser() + "");
			smsData.put("remindUrl",
					"/system/subsys/doc/feedback/feedback_detail.jsp?runId="
							+ delivery.getRunId());
			// 签收反馈手机端事务提醒
			smsData.put("remindUrl1",
					"/system/subsys/doc/feedback/feedback_detail.jsp?runId="
							+ delivery.getRunId());
			if (flag == 1) {
				smsData.put("content", "您下发的公文 《" + delivery.getBt() + "》"
						+ "已被 “" + dept.getDeptName() + "” 签收，签收时间："
						+ TeeDateUtil.format(Calendar.getInstance()));
				sender.sendSms(smsData, loginUser);
			} else if (flag == 2) {
				smsData.put(
						"content",
						"您下发的公文 《" + delivery.getBt() + "》" + "已被 “"
								+ dept.getDeptName() + "” 拒签，拒签原因：“"
								+ delivery.getRemark() + "”，拒签时间："
								+ TeeDateUtil.format(Calendar.getInstance()));
				sender.sendSms(smsData, loginUser);
			} else if (flag == 3) {
				smsData.put("content", "您下发的公文 《" + delivery.getBt() + "》"
						+ "已被 “" + dept.getDeptName() + "” 删除，删除时间："
						+ TeeDateUtil.format(Calendar.getInstance()));
				sender.sendSms(smsData, loginUser);
			}
		}

	}

	public Map getDeliveryInfo(Map requestData) {
		Map data = new HashMap();
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		String uuid = TeeStringUtil.getString(requestData.get("uuid"));

		TeeDocumentDelivery delivery = (TeeDocumentDelivery) simpleDaoSupport
				.get(TeeDocumentDelivery.class, uuid);
        //获取内容权限
		if(delivery.getRecord()!=null){
			data.put("contentPriv", delivery.getRecord().getContentPriv());
		}
		// 获取流程正文
		TeeFlowRunDoc flowRunDoc = (TeeFlowRunDoc) simpleDaoSupport.unique(
				"from TeeFlowRunDoc where flowRun.runId=?",
				new Object[] { delivery.getRunId() });
		if (flowRunDoc != null) {
			data.put("docId", flowRunDoc.getDocAttach().getSid());
			data.put("docFileName", flowRunDoc.getDocAttach().getFileName());
			data.put("docAttachName", flowRunDoc.getDocAttach()
					.getAttachmentName());
		}
        
		//获取流程版式正文
		List<TeeAttachmentModel> attaches1 = attachmentService.getAttacheModels(
				TeeAttachmentModelKeys.workFlowDocAip,
				String.valueOf(delivery.getRunId()));
		
		if(attaches1!=null&&attaches1.size()>0){
			data.put("docAipId", attaches1.get(0).getSid());
			data.put("docAipFileName", attaches1.get(0).getFileName());
			data.put("docAipAttachName", attaches1.get(0)
					.getAttachmentName());
		}
		
		
		
		// 获取流程附件
		List<TeeAttachmentModel> attaches = attachmentService.getAttacheModels(
				TeeAttachmentModelKeys.workFlow,
				String.valueOf(delivery.getRunId()));
		data.put("attachList", attaches);
		data.put("bt", delivery.getBt());
		data.put("zh", delivery.getZh());
		data.put("fwdw", delivery.getFwdw());
		data.put("flag", delivery.getFlag());
		data.put("runId", delivery.getRunId());
		
		data.put("isDownLoad", delivery.getIsDownLoad());
		//打印份数
		data.put("printNum", delivery.getPrintNum());
		//已经打印的份数
		data.put("printedNum", delivery.getPrintedNum());

		return data;
	}

	public Map createFlow(Map requestData) {
		Map data = new HashMap();
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		String uuid = TeeStringUtil.getString(requestData.get("uuid"));
		TeeDocumentDelivery delivery = (TeeDocumentDelivery) simpleDaoSupport
				.get(TeeDocumentDelivery.class, uuid);

		delivery.setHasToDoc(1);
		Map deliveryInfo = getDeliveryInfo(requestData);

		int flowId = TeeStringUtil.getInteger(requestData.get("flowId"), 0);
		int docPriv = TeeStringUtil.getInteger(requestData.get("docPriv"), 0);//正文权限   0=拒收   1=接收作为正文   2=接收作为附件
		int attachPriv = TeeStringUtil.getInteger(
				requestData.get("attachPriv"), 0);//附件权限    0=拒收  1=签收

		int formPriv=TeeStringUtil.getInteger(requestData.get("formPriv"), 0);//表单权限   0=拒收  1=签收
		int aipPriv=TeeStringUtil.getInteger(requestData.get("aipPriv"), 0);//版式正文权限  0=拒收  1=签收作为版式文件  2=签收作为附件
		int qpdPriv=TeeStringUtil.getInteger(requestData.get("qpdPriv"), 0);//签批单权限   0=拒收  1=签收
		
		
		String attachIds = "";
		// 获取流程正文
		if (deliveryInfo.get("docId") != null) {
			if (docPriv == 1) {// 签收为正文
				data.put("DOC_ID", deliveryInfo.get("docId"));
			} else if (docPriv == 2) {// 签收为附件
				attachIds += deliveryInfo.get("docId") + ",";
			}
		}

		// 获取流程附件
		List<TeeAttachmentModel> attachModels = (List<TeeAttachmentModel>) deliveryInfo
				.get("attachList");
		if (attachPriv == 1) {// 签收附件
			for (TeeAttachmentModel attach : attachModels) {
				attachIds += attach.getSid() + ",";
			}
		}
		
		//获取流程版式文件
		if (deliveryInfo.get("docAipId") != null) {
			if (aipPriv == 1) {// 签收为版式文件
				data.put("DOC_AIP_ID", deliveryInfo.get("docAipId"));
			} else if (aipPriv == 2) {// 签收为附件
				attachIds += deliveryInfo.get("docAipId") + ",";
			}
		}
		
		//获取流程相关签批单
		TeeJson json=flowRunAipTemplateService.getListByRunId(TeeStringUtil.getInteger(deliveryInfo.get("runId"), 0));
		List<TeeFlowRunAipTemplateModel> tList=(List<TeeFlowRunAipTemplateModel>) json.getRtData();
	    if(tList!=null&&tList.size()>0){
	    	if(qpdPriv==1){//签批单  签收作为附件
	    		for (TeeFlowRunAipTemplateModel model : tList) {
	    			attachIds += model.getAttachId()+",";
				}	
			}
	    }
		
		//获取表单
		if(formPriv==1){
			Map reqData=new HashMap();
			reqData.put("runId",deliveryInfo.get("runId") )	;
			reqData.put("view",1 )	;
			String sb = simpleDataLoader.getFormPrintDataStream(reqData, loginUser);
			InputStream sis = new StringInputStream(sb.toString(),"UTF-8");
		    
			TeeAttachment formAtt=null;
	    	try {
				formAtt=baseUpload.singleAttachUpload(sis, sb.getBytes().length, "原始表单.html", "", TeeAttachmentModelKeys.workFlow,"0", loginUser);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	attachIds += formAtt.getSid() + ",";
		}
		
		if (!"".equals(attachIds)) {
			attachIds = attachIds.substring(0, attachIds.length() - 1);
		}
		data.put("ATTACH_IDS", attachIds);

		// 字段映射
		TeeDocumentFlowPriv documentFlowPriv = (TeeDocumentFlowPriv) simpleDaoSupport
				.unique("from TeeDocumentFlowPriv where flowType.sid=?",
						new Object[] { flowId });
		Map<String, String> fieldMapping = TeeJsonUtil
				.JsonStr2Map(documentFlowPriv.getFieldMapping());
		Set<String> keys = fieldMapping.keySet();
		for (String key : keys) {
			if ("公文标题".equals(fieldMapping.get(key).toString())) {
				data.put("DATA_" + key, delivery.getBt());
			} else if ("字号".equals(fieldMapping.get(key).toString())) {
				data.put("DATA_" + key, delivery.getZh());
			} else if ("编号".equals(fieldMapping.get(key).toString())) {
				data.put("DATA_" + key, delivery.getBh());
			} else if ("秘密等级".equals(fieldMapping.get(key).toString())) {
				data.put("DATA_" + key, delivery.getMmdj());
			} else if ("紧急程度".equals(fieldMapping.get(key).toString())) {
				data.put("DATA_" + key, delivery.getJjcd());
			} else if ("发文单位".equals(fieldMapping.get(key).toString())) {
				data.put("DATA_" + key, delivery.getFwdw());
			} else if ("抄送".equals(fieldMapping.get(key).toString())) {
				data.put("DATA_" + key, delivery.getCs());
			} else if ("主送".equals(fieldMapping.get(key).toString())) {
				data.put("DATA_" + key, delivery.getZs());
			} else if ("共印份数".equals(fieldMapping.get(key).toString())) {
				data.put("DATA_" + key, delivery.getGyfs());
			} else if ("附件".equals(fieldMapping.get(key).toString())) {
				data.put("DATA_" + key, delivery.getFj());
			} else if ("主题词".equals(fieldMapping.get(key).toString())) {
				data.put("DATA_" + key, delivery.getZtc());
			} else if ("备注".equals(fieldMapping.get(key).toString())) {
				data.put("DATA_" + key, delivery.getBz());
			}
		}

		data.put("fType", flowId);
		data.put("validation", 0);

		return data;
	}

	public Map getViewInfo(Map requestData) {
		Map data = new HashMap();
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		String uuid = TeeStringUtil.getString(requestData.get("uuid"));

		TeeDocumentView view = (TeeDocumentView) simpleDaoSupport.get(
				TeeDocumentView.class, uuid);
         
		//设置内容权限值
		if(view.getRecord()!=null){
			data.put("contentPriv", view.getRecord().getContentPriv());
		}
		// 获取流程正文
		TeeFlowRunDoc flowRunDoc = (TeeFlowRunDoc) simpleDaoSupport.unique(
				"from TeeFlowRunDoc where flowRun.runId=?",
				new Object[] { view.getRunId() });
		if (flowRunDoc != null) {
			data.put("docId", flowRunDoc.getDocAttach().getSid());
			data.put("docFileName", flowRunDoc.getDocAttach().getFileName());
			data.put("docAttachName", flowRunDoc.getDocAttach()
					.getAttachmentName());
		}

		//获取流程版式正文
				List<TeeAttachmentModel> attaches1 = attachmentService.getAttacheModels(
						TeeAttachmentModelKeys.workFlowDocAip,
						String.valueOf(view.getRunId()));
				
				if(attaches1!=null&&attaches1.size()>0){
					data.put("docAipId", attaches1.get(0).getSid());
					data.put("docAipFileName", attaches1.get(0).getFileName());
					data.put("docAipAttachName", attaches1.get(0)
							.getAttachmentName());
				}
		
		
		// 获取流程附件
		List<TeeAttachmentModel> attaches = attachmentService.getAttacheModels(
				TeeAttachmentModelKeys.workFlow,
				String.valueOf(view.getRunId()));
		data.put("attachList", attaches);
		data.put("bt", view.getBt());
		data.put("zh", view.getZh());
		data.put("fwdw", view.getFwdw());
		data.put("flag", view.getFlag());
        
		requestData.put("runId", view.getRunId());
		requestData.put("view", 1);
		data.put("runId", view.getRunId());
		data.put("printNum", view.getPrintNum());
		data.put("isDownLoad", view.getIsDownLoad());
		
		data.put("printedNum", view.getPrintedNum());
		

		return data;
	}

	public void updateViewFlag(Map requestData) {
		String uuid = TeeStringUtil.getString(requestData.get("uuid"));
		TeeDocumentView view = (TeeDocumentView) simpleDaoSupport.get(
				TeeDocumentView.class, uuid);
		TeeDocumentViewRecord record = view.getRecord();
		if (view.getFlag() != 1) {
			TeePerson p = (TeePerson) simpleDaoSupport.get(TeePerson.class,
					view.getRecUser());
			if (record.getIsReadRemind() == 1) {
				Map smsData = new HashMap();
				smsData.put("moduleNo", "051");
				smsData.put("userListIds", view.getSendUser() + "");
				smsData.put("remindUrl",
						"/system/subsys/doc/feedback/view_detail.jsp?uuid="
								+ record.getUuid());
				// 查阅反馈收集端事务提醒
				smsData.put("remindUrl1",
						"/system/subsys/doc/feedback/view_detail.jsp?uuid="
								+ record.getUuid());
				smsData.put(
						"content",
						"您传阅的公文 《" + view.getBt() + "》" + "已被 “"
								+ p.getUserName() + "” 查阅，查阅时间："
								+ TeeDateUtil.format(Calendar.getInstance()));
				sender.sendSms(smsData, null);

			}
			view.setFlag(1);
			view.setRecTime(Calendar.getInstance());
		}

	}

	public Map createViewFlow(Map requestData) {
		Map data = new HashMap();
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		String uuid = TeeStringUtil.getString(requestData.get("uuid"));
		TeeDocumentView view = (TeeDocumentView) simpleDaoSupport.get(
				TeeDocumentView.class, uuid);

		view.setHasToDoc(1);
		Map viewInfo = getViewInfo(requestData);

		int flowId = TeeStringUtil.getInteger(requestData.get("flowId"), 0);
		int docPriv = TeeStringUtil.getInteger(requestData.get("docPriv"), 0);//正文权限   0=拒收   1=接收作为正文   2=接收作为附件
		int attachPriv = TeeStringUtil.getInteger(
				requestData.get("attachPriv"), 0);//附件权限    0=拒收  1=签收

		int formPriv=TeeStringUtil.getInteger(requestData.get("formPriv"), 0);//表单权限   0=拒收  1=签收
		int aipPriv=TeeStringUtil.getInteger(requestData.get("aipPriv"), 0);//版式正文权限  0=拒收  1=签收作为版式文件  2=签收作为附件

		
		int qpdPriv=TeeStringUtil.getInteger(requestData.get("qpdPriv"), 0);//签批单权限
		
		String attachIds = "";
		// 获取流程正文
		if (viewInfo.get("docId") != null) {
			if (docPriv == 1) {// 签收为正文
				data.put("DOC_ID", viewInfo.get("docId"));
			} else if (docPriv == 2) {// 签收为附件
				attachIds += viewInfo.get("docId") + ",";
			}
		}

		// 获取流程附件
		List<TeeAttachmentModel> attachModels = (List<TeeAttachmentModel>) viewInfo
				.get("attachList");
		if (attachPriv == 1) {// 签收附件
			for (TeeAttachmentModel attach : attachModels) {
				attachIds += attach.getSid() + ",";
			}
		}
		
		//获取流程版式文件
		if (viewInfo.get("docAipId") != null) {
			if (aipPriv == 1) {// 签收为版式文件
				data.put("DOC_AIP_ID", viewInfo.get("docAipId"));
			} else if (aipPriv == 2) {// 签收为附件
				attachIds += viewInfo.get("docAipId") + ",";
			}
		}
		
		//获取表单
		if(formPriv==1){
			Map reqData=new HashMap();
			reqData.put("runId",viewInfo.get("runId") )	;
			reqData.put("view",1 )	;
			String sb = simpleDataLoader.getFormPrintDataStream(reqData, loginUser);
			InputStream sis = new StringInputStream(sb.toString(),"UTF-8");
		    
			TeeAttachment formAtt=null;
	    	try {
				formAtt=baseUpload.singleAttachUpload(sis, sb.getBytes().length, "原始表单.html", "", TeeAttachmentModelKeys.workFlow,"0", loginUser);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	attachIds += formAtt.getSid() + ",";
		}
		
		
		//获取流程相关签批单
		TeeJson json=flowRunAipTemplateService.getListByRunId(TeeStringUtil.getInteger(viewInfo.get("runId"), 0));
		List<TeeFlowRunAipTemplateModel> tList=(List<TeeFlowRunAipTemplateModel>) json.getRtData();
	    if(tList!=null&&tList.size()>0){
	    	if(qpdPriv==1){//签批单  签收作为附件
	    		for (TeeFlowRunAipTemplateModel model : tList) {
	    			attachIds += model.getAttachId()+",";
				}	
			}
	    }
		
		
		if (!"".equals(attachIds)) {
			attachIds = attachIds.substring(0, attachIds.length() - 1);
		}
		data.put("ATTACH_IDS", attachIds);

		// 字段映射
		TeeDocumentFlowPriv documentFlowPriv = (TeeDocumentFlowPriv) simpleDaoSupport
				.unique("from TeeDocumentFlowPriv where flowType.sid=?",
						new Object[] { flowId });
		Map<String, String> fieldMapping = TeeJsonUtil
				.JsonStr2Map(documentFlowPriv.getFieldMapping());
		Set<String> keys = fieldMapping.keySet();
		for (String key : keys) {
			if ("公文标题".equals(fieldMapping.get(key).toString())) {
				data.put("DATA_" + key, view.getBt());
			} else if ("字号".equals(fieldMapping.get(key).toString())) {
				data.put("DATA_" + key, view.getZh());
			} else if ("编号".equals(fieldMapping.get(key).toString())) {
				data.put("DATA_" + key, view.getBh());
			} else if ("秘密等级".equals(fieldMapping.get(key).toString())) {
				data.put("DATA_" + key, view.getMmdj());
			} else if ("紧急程度".equals(fieldMapping.get(key).toString())) {
				data.put("DATA_" + key, view.getJjcd());
			} else if ("发文单位".equals(fieldMapping.get(key).toString())) {
				data.put("DATA_" + key, view.getFwdw());
			} else if ("抄送".equals(fieldMapping.get(key).toString())) {
				data.put("DATA_" + key, view.getCs());
			} else if ("主送".equals(fieldMapping.get(key).toString())) {
				data.put("DATA_" + key, view.getZs());
			} else if ("共印份数".equals(fieldMapping.get(key).toString())) {
				data.put("DATA_" + key, view.getGyfs());
			} else if ("附件".equals(fieldMapping.get(key).toString())) {
				data.put("DATA_" + key, view.getFj());
			} else if ("主题词".equals(fieldMapping.get(key).toString())) {
				data.put("DATA_" + key, view.getZtc());
			} else if ("备注".equals(fieldMapping.get(key).toString())) {
				data.put("DATA_" + key, view.getBz());
			}
		}

		data.put("fType", flowId);
		data.put("validation", 0);
		return data;
	}

	/**
	 * 我下发的公文
	 * 
	 * @param queryParams
	 * @param loginUser
	 * @param dataGridModel
	 * @return
	 */
	public TeeEasyuiDataGridJson myDocSend(Map queryParams,
			TeePerson loginUser, TeeDataGridModel dataGridModel) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String bt = TeeStringUtil.getString(queryParams.get("bt"), "");// 标题
		String fwdw = TeeStringUtil.getString(queryParams.get("fwdw"), "");// 来文单位
		String zh = TeeStringUtil.getString(queryParams.get("zh"), "");// 字号

		StringBuffer myManage = new StringBuffer();
		// 公文分发反馈权限
		List sendPrivList = flowPrivService.getQueryPostDeptsByAllFlow(
				loginUser, "6");
		if (sendPrivList != null && sendPrivList.size() > 0) {
			for (int i = 0; i < sendPrivList.size(); i++) {
				Map queryData = (Map) sendPrivList.get(i);
				if (!TeeUtility.isNullorEmpty(String.valueOf(queryData
						.get("postDeptIds")))) {// 有权限
					myManage.append("(re.flowId = "
							+ queryData.get("flowTypeId"));
					String postDeptIds = String.valueOf(queryData
							.get("postDeptIds"));
					if (!"0".equals(postDeptIds)) {// 加入限定部门条件
						if (postDeptIds.endsWith(",")) {
							postDeptIds = postDeptIds.substring(0,
									postDeptIds.length() - 1);
						}
						myManage.append(" and p.dept.uuid in (" + postDeptIds
								+ ")");
					}
					myManage.append(")");
					if (i != sendPrivList.size() - 1) {
						myManage.append(" or ");
					}
				}
			}
		} else {
			// myManage.append(" fr.flowType=0 ");
		}

		String queryStr = "";
		if (myManage.length() > 0) {
			queryStr = " or " + myManage.toString();
		}
		// String hql =
		// "from TeeFlowRun fr where fr.delFlag=0 and exists (select 1 from TeeDocumentDelivery d,TeePerson p where d.runId=fr.runId and p.uuid=d.sendUser and (d.sendUser="+loginUser.getUuid()+
		// queryStr + "))";

		String hql = " from TeeDocumentDeliveryRecord re where exists( select 1 from TeePerson p where re.sendUser.uuid=p.uuid and re.sendUser="
				+ loginUser.getUuid() + queryStr + " ) ";

		if (!TeeUtility.isNullorEmpty(bt)) {
			hql += " and re.bt like '%" + TeeDbUtility.formatString(bt) + "%'";
		}

		if (!TeeUtility.isNullorEmpty(fwdw)) {
			hql += " and re.fwdw like '%" + TeeDbUtility.formatString(fwdw)
					+ "%'";
		}

		if (!TeeUtility.isNullorEmpty(zh)) {
			hql += " and re.zh like '%" + TeeDbUtility.formatString(zh) + "%'";
		}

		List<TeeDocumentDeliveryRecord> records = simpleDaoSupport.pageFind(hql
				+ " order by re.sendTime desc", dataGridModel.getFirstResult(),
				dataGridModel.getRows(), null);
		long total = simpleDaoSupport.count("select count(*) " + hql, null);
		List<Map> list = new ArrayList(0);
		int runId = 0;
		for (TeeDocumentDeliveryRecord fr : records) {
			Map data = new HashMap();
			runId = 0;
			data.put("uuid", fr.getUuid());
			if (fr.getFlowRun() != null) {
				data.put("runName", fr.getFlowRun().getRunName());
				data.put("runId", fr.getFlowRun().getRunId());
				data.put("zh", fr.getZh());
				data.put("flowName", fr.getFlowRun().getFlowType()
						.getFlowName());
				runId = fr.getFlowRun().getRunId();
			} else {
				data.put("runName", "流程已删除");
				data.put("runId", 0);
				data.put("flowName", "流程已删除");
			}

			data.put("createUser", fr.getSendUser().getUserName());
			data.put("createUserUuid", fr.getSendUser().getUuid());
			data.put("createTime", format.format(fr.getSendTime().getTime()));

			long noRead = simpleDaoSupport.count(
					"select count(*) from TeeDocumentDelivery where runId="
							+ runId + " and flag=0", null);
			long rec = simpleDaoSupport.count(
					"select count(*) from TeeDocumentDelivery where runId="
							+ runId + " and flag=1", null);
			long noRec = simpleDaoSupport.count(
					"select count(*) from TeeDocumentDelivery where runId="
							+ runId + " and flag=2", null);
			long del = simpleDaoSupport.count(
					"select count(*) from TeeDocumentDelivery where runId="
							+ runId + " and flag=3", null);

			data.put("noRead", noRead);
			data.put("rec", rec);
			data.put("noRec", noRec);
			data.put("del", del);

			list.add(data);
		}
		dataGridJson.setRows(list);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}

	/**
	 * 我传阅的文件
	 * 
	 * @param queryParams
	 * @param loginUser
	 * @param dataGridModel
	 * @return
	 */
	public TeeEasyuiDataGridJson myDocView(Map queryParams,
			TeePerson loginUser, TeeDataGridModel dataGridModel) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String bt = TeeStringUtil.getString(queryParams.get("bt"), "");// 标题
		String fwdw = TeeStringUtil.getString(queryParams.get("fwdw"), "");// 来文单位
		String zh = TeeStringUtil.getString(queryParams.get("zh"), "");// 字号

		StringBuffer myManage = new StringBuffer();
		// 公文分发反馈权限
		List sendPrivList = flowPrivService.getQueryPostDeptsByAllFlow(
				loginUser, "7");
		if (sendPrivList != null && sendPrivList.size() > 0) {
			for (int i = 0; i < sendPrivList.size(); i++) {
				Map queryData = (Map) sendPrivList.get(i);
				if (!TeeUtility.isNullorEmpty(String.valueOf(queryData
						.get("postDeptIds")))) {// 有权限
					myManage.append("(re.flowId = "
							+ queryData.get("flowTypeId"));
					String postDeptIds = String.valueOf(queryData
							.get("postDeptIds"));
					if (!"0".equals(postDeptIds)) {// 加入限定部门条件
						if (postDeptIds.endsWith(",")) {
							postDeptIds = postDeptIds.substring(0,
									postDeptIds.length() - 1);
						}
						myManage.append(" and p.dept.uuid in (" + postDeptIds
								+ ")");
					}
					myManage.append(")");
					if (i != sendPrivList.size() - 1) {
						myManage.append(" or ");
					}
				}
			}
		} else {
			// myManage.append(" fr.flowType=0 ");
		}

		String queryStr = "";
		if (myManage.length() > 0) {
			queryStr = " or " + myManage.toString();
		}
		// String hql =
		// "from TeeFlowRun fr where fr.delFlag=0 and exists (select 1 from TeeDocumentView v where v.runId=fr.runId and v.sendUser="+loginUser.getUuid()+")";
		String hql = " from TeeDocumentViewRecord re where exists( select 1 from TeePerson p where re.sendUser.uuid=p.uuid and re.sendUser="
				+ loginUser.getUuid() + queryStr + " ) ";

		if (!TeeUtility.isNullorEmpty(bt)) {
			hql += " and re.bt like '%" + TeeDbUtility.formatString(bt) + "%'";
		}

		if (!TeeUtility.isNullorEmpty(fwdw)) {
			hql += " and re.fwdw like '%" + TeeDbUtility.formatString(fwdw)
					+ "%'";
		}

		if (!TeeUtility.isNullorEmpty(zh)) {
			hql += " and re.zh like '%" + TeeDbUtility.formatString(zh) + "%'";
		}

		List<TeeDocumentViewRecord> records = simpleDaoSupport.pageFind(hql
				+ " order by re.sendTime desc", dataGridModel.getFirstResult(),
				dataGridModel.getRows(), null);
		long total = simpleDaoSupport.count("select count(*) " + hql, null);
		List<Map> list = new ArrayList(0);
		int runId = 0;
		for (TeeDocumentViewRecord fr : records) {
			runId = 0;
			Map data = new HashMap();
			data.put("uuid", fr.getUuid());
			if (fr.getFlowRun() != null) {
				data.put("runName", fr.getFlowRun().getRunName());
				data.put("runId", fr.getFlowRun().getRunId());
				data.put("flowName", fr.getFlowRun().getFlowType()
						.getFlowName());
				runId = fr.getFlowRun().getRunId();
			} else {
				runId = 0;
			}

			data.put("createUser", fr.getSendUser().getUserName());
			data.put("createUserUuid", fr.getSendUser().getUuid());
			data.put("createTime", format.format(fr.getSendTime().getTime()));

			long noRead = simpleDaoSupport.count(
					"select count(*) from TeeDocumentView where runId=" + runId
							+ " and flag=0", null);
			long read = simpleDaoSupport.count(
					"select count(*) from TeeDocumentView where runId=" + runId
							+ " and flag=1", null);

			data.put("noRead", noRead);
			data.put("read", read);

			list.add(data);
		}
		dataGridJson.setRows(list);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}

	/**
	 * 根据流程获取公文发送列表
	 * 
	 * @param queryParams
	 * @param loginUser
	 * @param dataGridModel
	 * @return
	 */
	public TeeEasyuiDataGridJson getDocSendListByRunId(Map queryParams,
			TeePerson loginUser, TeeDataGridModel dataGridModel) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String uuid = TeeStringUtil.getString(queryParams.get("uuid"), "0");

		List<TeeDocumentDelivery> list = simpleDaoSupport
				.find("from TeeDocumentDelivery where record.uuid=? order by flag asc",
						uuid);
		List<TeeDocumentDeliveryModel> mList = new ArrayList();
		TeeDepartment dept;
		TeePerson p;
		for (TeeDocumentDelivery delivery : list) {
			TeeDocumentDeliveryModel deliveryModel = new TeeDocumentDeliveryModel();
			BeanUtils.copyProperties(delivery, deliveryModel);
			dept = (TeeDepartment) simpleDaoSupport.get(TeeDepartment.class,
					delivery.getRecDept());
			if (dept != null) {
				deliveryModel.setRecDeptName(dept.getDeptName());
			}
			p = (TeePerson) simpleDaoSupport.get(TeePerson.class,
					delivery.getRecUser());
			if (p != null) {
				deliveryModel.setRecUserName(p.getUserName());
			} else {
				deliveryModel.setRecUserName("");
			}
			mList.add(deliveryModel);
		}

		dataGridJson.setRows(mList);
		return dataGridJson;
	}

	/**
	 * 根据流程获取公文传阅列表
	 * 
	 * @param queryParams
	 * @param loginUser
	 * @param dataGridModel
	 * @return
	 */
	public TeeEasyuiDataGridJson getDocViewListByRunId(Map queryParams,
			TeePerson loginUser, TeeDataGridModel dataGridModel) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String uuid = TeeStringUtil.getString(queryParams.get("uuid"), "0");

		List<TeeDocumentView> list = simpleDaoSupport.find(
				"from TeeDocumentView where record.uuid=? order by flag asc",
				uuid);
		List<TeeDocumentViewModel> mList = new ArrayList();
		TeePerson p;
		for (TeeDocumentView v : list) {
			TeeDocumentViewModel vModel = new TeeDocumentViewModel();
			BeanUtils.copyProperties(v, vModel);
			p = (TeePerson) simpleDaoSupport.get(TeePerson.class,
					v.getRecUser());
			if (p != null) {
				vModel.setRecUserName(p.getUserName());
			} else {
				vModel.setRecUserName("");
			}
			mList.add(vModel);
		}

		dataGridJson.setRows(mList);
		return dataGridJson;
	}

	/**
	 * 删除
	 * 
	 * @function:
	 * @author: wyw
	 * @data: 2015年8月13日
	 * @param sids
	 * @return TeeJson
	 */
	public TeeJson deleteObjById(String sids) {
		if (TeeUtility.isNullorEmpty(sids)) {
			sids = "0";
		}
		if (sids.endsWith(",")) {
			sids = sids.substring(0, sids.length() - 1);
		}
		List<TeeDocumentDeliveryRecord> records = simpleDaoSupport
				.executeQuery(" from TeeDocumentDeliveryRecord where uuid = ?",
						new Object[] { sids });
		if (records != null && records.size() > 0) {
			for (TeeDocumentDeliveryRecord record : records) {
				Object[] para = { sids };
				simpleDaoSupport.executeUpdate(
						"delete from TeeDocumentDelivery where record.uuid=?",
						para);
				simpleDaoSupport.deleteByObj(record);
			}
		}

		TeeJson json = new TeeJson();
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}

	/**
	 * 收回
	 * 
	 * @function:
	 * @author: wyw
	 * @data: 2015年8月13日
	 * @param sids
	 * @return TeeJson
	 */
	public TeeJson takeBackObjById(String sids) {
		if (TeeUtility.isNullorEmpty(sids)) {
			sids = "0";
		}
		if (sids.endsWith(",")) {
			sids = sids.substring(0, sids.length() - 1);
		}
		List<TeeDocumentDeliveryRecord> records = simpleDaoSupport
				.executeQuery(" from TeeDocumentDeliveryRecord where uuid = ?",
						new Object[] { sids });
		if (records != null && records.size() > 0) {
			for (TeeDocumentDeliveryRecord record : records) {
				Object[] para = { sids };
				simpleDaoSupport.executeUpdate(
						"delete from TeeDocumentDelivery where record.uuid=?",
						para);
			}
		}

		TeeJson json = new TeeJson();
		json.setRtState(true);
		json.setRtMsg("收回成功!");
		return json;
	}

	/**
	 * 重发
	 * 
	 * @function:
	 * @author: wyw
	 * @data: 2015年8月14日
	 * @param requestMap
	 * @param loginPerson
	 * @return TeeJson
	 */
	public TeeJson reSendObjById(Map requestMap, TeePerson loginPerson) {
		String sids = (String) requestMap.get("uuid");
		String sendDeptIdStr = TeeUtility.null2Empty((String) requestMap
				.get("sendDeptIds"));
		int sendDeptIds[] = TeeStringUtil.parseIntegerArray(sendDeptIdStr);

		if (TeeUtility.isNullorEmpty(sids)) {
			sids = "0";
		}
		if (sids.endsWith(",")) {
			sids = sids.substring(0, sids.length() - 1);
		}

		TeeFormItem tmp = null;
		Map smsData = new HashMap();

		List<TeeDocumentDeliveryRecord> records = simpleDaoSupport
				.executeQuery(" from TeeDocumentDeliveryRecord where uuid = ?",
						new Object[] { sids });
		if (records != null && records.size() > 0) {
			List<TeeDocumentRecMapping> recUserMaps = simpleDaoSupport.find(
					"from TeeDocumentRecMapping", null);

			TeeDocumentDeliveryRecord record = new TeeDocumentDeliveryRecord();
			for (TeeDocumentDeliveryRecord dbRecord : records) {
				Object[] para = { sids };
				simpleDaoSupport.executeUpdate(
						"delete from TeeDocumentDelivery where record.uuid=?",
						para);

				// 创建对象
				TeeDocumentFlowPriv documentFlowPriv = (TeeDocumentFlowPriv) simpleDaoSupport
						.unique("from TeeDocumentFlowPriv where flowType.sid=?",
								new Object[] { dbRecord.getFlowId() });
				Map<String, String> fieldMapping = TeeJsonUtil
						.JsonStr2Map(documentFlowPriv.getFieldMapping());
				Set<String> keys = fieldMapping.keySet();

				TeeFlowType flowType = dbRecord.getFlowRun().getFlowType();
				List<TeeFormItem> formItems = flowFormService
						.getLatestFormItemsByOriginForm(flowType.getForm());
				Map<String, String> flowRunData = helper.getFlowRunData(
						dbRecord.getFlowRun().getRunId(), flowType.getSid());

				record = dbRecord;

				// 遍历部门
				int counter = 0;
				if (sendDeptIds != null && sendDeptIds.length > 0) {
					for (int deptId : sendDeptIds) {
						counter++;
						TeeDocumentDelivery delivery = new TeeDocumentDelivery();
						delivery.setRecDept(deptId);// 设置部门ID
						delivery.setFlag(0);
						delivery.setSendTime(Calendar.getInstance());
						delivery.setRunId(dbRecord.getFlowRun().getRunId());
						delivery.setSendUser(loginPerson.getUuid());
						delivery.setFlowId(dbRecord.getFlowId());
						delivery.setRecord(record);
						if (counter == 1) {
							record.setSendTime(Calendar.getInstance());
							record.setSendUser(loginPerson);
							record.setFlowRun(dbRecord.getFlowRun());
							record.setFlowId(dbRecord.getFlowId());
						}

						// 查找对应的字段，并映射到发文分发实体中
						for (String key : keys) {
							tmp = TeeFormItem.getItemByTitle(formItems, key);
							if (tmp != null) {
								if ("公文标题".equals(fieldMapping.get(key)
										.toString())) {
									delivery.setBt(flowRunData.get(tmp
											.getName()));
									if (counter == 1) {
										record.setBt(flowRunData.get(tmp
												.getName()));
									}
								} else if ("字号".equals(fieldMapping.get(key)
										.toString())) {
									delivery.setZh(flowRunData.get(tmp
											.getName()));
									if (counter == 1) {
										record.setZh(flowRunData.get(tmp
												.getName()));
									}
								} else if ("编号".equals(fieldMapping.get(key)
										.toString())) {
									delivery.setBh(flowRunData.get(tmp
											.getName()));
									if (counter == 1) {
										record.setBh(flowRunData.get(tmp
												.getName()));
									}
								} else if ("秘密等级".equals(fieldMapping.get(key)
										.toString())) {
									delivery.setMmdj(flowRunData.get(tmp
											.getName()));
									if (counter == 1) {
										record.setMmdj(flowRunData.get(tmp
												.getName()));
									}
								} else if ("紧急程度".equals(fieldMapping.get(key)
										.toString())) {
									delivery.setJjcd(flowRunData.get(tmp
											.getName()));
									if (counter == 1) {
										record.setJjcd(flowRunData.get(tmp
												.getName()));
									}
								} else if ("发文单位".equals(fieldMapping.get(key)
										.toString())) {
									delivery.setFwdw(flowRunData.get(tmp
											.getName()));
									if (counter == 1) {
										record.setFwdw(flowRunData.get(tmp
												.getName()));
									}
								} else if ("抄送".equals(fieldMapping.get(key)
										.toString())) {
									delivery.setCs(flowRunData.get(tmp
											.getName()));
									if (counter == 1) {
										record.setCs(flowRunData.get(tmp
												.getName()));
									}
								} else if ("主送".equals(fieldMapping.get(key)
										.toString())) {
									delivery.setZs(flowRunData.get(tmp
											.getName()));
									if (counter == 1) {
										record.setZs(flowRunData.get(tmp
												.getName()));
									}
								} else if ("共印份数".equals(fieldMapping.get(key)
										.toString())) {
									delivery.setGyfs(flowRunData.get(tmp
											.getName()));
									if (counter == 1) {
										record.setGyfs(flowRunData.get(tmp
												.getName()));
									}
								} else if ("附件".equals(fieldMapping.get(key)
										.toString())) {
									delivery.setFj(flowRunData.get(tmp
											.getName()));
									if (counter == 1) {
										record.setFj(flowRunData.get(tmp
												.getName()));
									}
								} else if ("主题词".equals(fieldMapping.get(key)
										.toString())) {
									delivery.setZtc(flowRunData.get(tmp
											.getName()));
									if (counter == 1) {
										record.setZtc(flowRunData.get(tmp
												.getName()));
									}
								} else if ("备注".equals(fieldMapping.get(key)
										.toString())) {
									delivery.setBz(flowRunData.get(tmp
											.getName()));
									if (counter == 1) {
										record.setBz(flowRunData.get(tmp
												.getName()));
									}
								}
							}
						}

						if (counter == 1) {
							simpleDaoSupport.update(record);
						}
						simpleDaoSupport.save(delivery);

						for (TeeDocumentRecMapping recMapping : recUserMaps) {
							if (recMapping.getDept().getUuid() == deptId) {

								// 发送sms短消息
								smsData.put(
										"content",
										"您有一个待签收公文 《"
												+ TeeUtility
														.null2Empty(delivery
																.getBt())
												+ "》，请尽快签收。");
								smsData.put("remindUrl",
										"/system/subsys/doc/daishou/print/index.jsp?uuid="
												+ delivery.getUuid());
								// 手机端事务提醒
								smsData.put("remindUrl1",
										"/system/mobile/phone/document/doc_rec_detail.jsp?uuid="
												+ delivery.getUuid());
								smsData.put("moduleNo", "051");
								smsData.put("userSet",
										recMapping.getPrivUsers());
								sender.sendSms(smsData, loginPerson);
								break;
							}
						}
					}
				}
				dbRecord.getFlowRun().setSendFlag(1);
			}
		}

		TeeJson json = new TeeJson();
		json.setRtState(true);
		json.setRtMsg("保存成功!");
		return json;
	}

	/**
	 * 删除传阅反馈
	 * 
	 * @function:
	 * @author: wyw
	 * @data: 2015年8月13日
	 * @param sids
	 * @return TeeJson
	 */
	public TeeJson deleteDocViewById(String sids) {
		if (TeeUtility.isNullorEmpty(sids)) {
			sids = "0";
		}
		if (sids.endsWith(",")) {
			sids = sids.substring(0, sids.length() - 1);
		}
		List<TeeDocumentViewRecord> records = simpleDaoSupport.executeQuery(
				" from TeeDocumentViewRecord where uuid = ?",
				new Object[] { sids });
		if (records != null && records.size() > 0) {
			for (TeeDocumentViewRecord record : records) {
				Object[] para = { sids };
				simpleDaoSupport
						.executeUpdate(
								"delete from TeeDocumentView where record.uuid=?",
								para);
				simpleDaoSupport.deleteByObj(record);
			}
		}

		TeeJson json = new TeeJson();
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}

	/**
	 * 收回传阅反馈
	 * 
	 * @function:
	 * @author: wyw
	 * @data: 2015年8月13日
	 * @param sids
	 * @return TeeJson
	 */
	public TeeJson takeBackDocViewById(String sids) {
		if (TeeUtility.isNullorEmpty(sids)) {
			sids = "0";
		}
		if (sids.endsWith(",")) {
			sids = sids.substring(0, sids.length() - 1);
		}
		List<TeeDocumentViewRecord> records = simpleDaoSupport.executeQuery(
				" from TeeDocumentViewRecord where uuid = ?",
				new Object[] { sids });
		if (records != null && records.size() > 0) {
			for (TeeDocumentViewRecord record : records) {
				Object[] para = { sids };
				simpleDaoSupport
						.executeUpdate(
								"delete from TeeDocumentView where record.uuid=?",
								para);
			}
		}

		TeeJson json = new TeeJson();
		json.setRtState(true);
		json.setRtMsg("收回成功!");
		return json;
	}

	/**
	 * 重发 传阅反馈
	 * 
	 * @function:
	 * @author: wyw
	 * @data: 2015年8月14日
	 * @param requestMap
	 * @param loginPerson
	 * @return TeeJson
	 */
	public TeeJson reSendDocViewById(Map requestMap, TeePerson loginPerson) {
		String sids = (String) requestMap.get("uuid");
		String sendUserIdtr = TeeUtility.null2Empty((String) requestMap
				.get("sendUserIds"));
		int sendUserIds[] = TeeStringUtil.parseIntegerArray(sendUserIdtr);

		if (TeeUtility.isNullorEmpty(sids)) {
			sids = "0";
		}
		if (sids.endsWith(",")) {
			sids = sids.substring(0, sids.length() - 1);
		}

		TeeFormItem tmp = null;
		Map smsData = new HashMap();

		List<TeeDocumentViewRecord> records = simpleDaoSupport.executeQuery(
				" from TeeDocumentViewRecord where uuid = ?",
				new Object[] { sids });
		if (records != null && records.size() > 0) {
			List<TeeDocumentRecMapping> recUserMaps = simpleDaoSupport.find(
					"from TeeDocumentRecMapping", null);

			TeeDocumentViewRecord record = new TeeDocumentViewRecord();
			for (TeeDocumentViewRecord dbRecord : records) {
				Object[] para = { sids };
				simpleDaoSupport
						.executeUpdate(
								"delete from TeeDocumentView where record.uuid=?",
								para);

				// 创建对象
				TeeDocumentFlowPriv documentFlowPriv = (TeeDocumentFlowPriv) simpleDaoSupport
						.unique("from TeeDocumentFlowPriv where flowType.sid=?",
								new Object[] { dbRecord.getFlowId() });
				Map<String, String> fieldMapping = TeeJsonUtil
						.JsonStr2Map(documentFlowPriv.getFieldMapping());
				Set<String> keys = fieldMapping.keySet();

				TeeFlowType flowType = dbRecord.getFlowRun().getFlowType();
				List<TeeFormItem> formItems = flowFormService
						.getLatestFormItemsByOriginForm(flowType.getForm());
				Map<String, String> flowRunData = helper.getFlowRunData(
						dbRecord.getFlowRun().getRunId(), flowType.getSid());

				record = dbRecord;

				// 遍历人员
				int counter = 0;
				if (sendUserIds != null && sendUserIds.length > 0) {
					for (int userId : sendUserIds) {
						counter++;
						TeeDocumentView view = new TeeDocumentView();
						view.setRecUser(userId);
						view.setRunId(dbRecord.getFlowRun().getRunId());
						view.setSendUser(loginPerson.getUuid());
						view.setSendTime(Calendar.getInstance());
						view.setFlowId(dbRecord.getFlowId());
						view.setRecord(record);
						if (counter == 1) {
							record.setSendTime(Calendar.getInstance());
							record.setSendUser(loginPerson);
							record.setFlowRun(dbRecord.getFlowRun());
							record.setFlowId(dbRecord.getFlowId());
						}

						// 查找对应的字段，并映射到发文分发实体中
						for (String key : keys) {
							tmp = TeeFormItem.getItemByTitle(formItems, key);
							if (tmp != null) {
								if ("公文标题".equals(fieldMapping.get(key)
										.toString())) {
									view.setBt(flowRunData.get(tmp.getName()));
									if (counter == 1) {
										record.setBt(flowRunData.get(tmp
												.getName()));
									}
								} else if ("字号".equals(fieldMapping.get(key)
										.toString())) {
									view.setZh(flowRunData.get(tmp.getName()));
									if (counter == 1) {
										record.setZh(flowRunData.get(tmp
												.getName()));
									}
								} else if ("编号".equals(fieldMapping.get(key)
										.toString())) {
									view.setBh(flowRunData.get(tmp.getName()));
									if (counter == 1) {
										record.setBh(flowRunData.get(tmp
												.getName()));
									}
								} else if ("秘密等级".equals(fieldMapping.get(key)
										.toString())) {
									view.setMmdj(flowRunData.get(tmp.getName()));
									if (counter == 1) {
										record.setMmdj(flowRunData.get(tmp
												.getName()));
									}
								} else if ("紧急程度".equals(fieldMapping.get(key)
										.toString())) {
									view.setJjcd(flowRunData.get(tmp.getName()));
									if (counter == 1) {
										record.setJjcd(flowRunData.get(tmp
												.getName()));
									}
								} else if ("发文单位".equals(fieldMapping.get(key)
										.toString())) {
									view.setFwdw(flowRunData.get(tmp.getName()));
									if (counter == 1) {
										record.setFwdw(flowRunData.get(tmp
												.getName()));
									}
								} else if ("抄送".equals(fieldMapping.get(key)
										.toString())) {
									view.setCs(flowRunData.get(tmp.getName()));
									if (counter == 1) {
										record.setCs(flowRunData.get(tmp
												.getName()));
									}
								} else if ("主送".equals(fieldMapping.get(key)
										.toString())) {
									view.setZs(flowRunData.get(tmp.getName()));
									if (counter == 1) {
										record.setZs(flowRunData.get(tmp
												.getName()));
									}
								} else if ("共印份数".equals(fieldMapping.get(key)
										.toString())) {
									view.setGyfs(flowRunData.get(tmp.getName()));
									if (counter == 1) {
										record.setGyfs(flowRunData.get(tmp
												.getName()));
									}
								} else if ("附件".equals(fieldMapping.get(key)
										.toString())) {
									view.setFj(flowRunData.get(tmp.getName()));
									if (counter == 1) {
										record.setFj(flowRunData.get(tmp
												.getName()));
									}
								} else if ("主题词".equals(fieldMapping.get(key)
										.toString())) {
									view.setZtc(flowRunData.get(tmp.getName()));
									if (counter == 1) {
										record.setZtc(flowRunData.get(tmp
												.getName()));
									}
								} else if ("备注".equals(fieldMapping.get(key)
										.toString())) {
									view.setBz(flowRunData.get(tmp.getName()));
									if (counter == 1) {
										record.setBz(flowRunData.get(tmp
												.getName()));
									}
								}
							}
						}

						if (counter == 1) {
							simpleDaoSupport.update(record);
						}
						simpleDaoSupport.save(view);

						// 发送sms短消息
						smsData.put(
								"content",
								"您有一个待阅公文《"
										+ TeeUtility.null2Empty(view.getBt())
										+ "》，请尽快查阅。");
						smsData.put("remindUrl",
								"/system/subsys/doc/daiyue/print/index.jsp?uuid="
										+ view.getUuid());
						smsData.put("remindUrl1",
								"/system/mobile/phone/document/doc_view_detail.jsp?uuid="
										+ view.getUuid());
						smsData.put("moduleNo", "051");
						smsData.put("userListIds", userId + "");
						sender.sendSms(smsData, loginPerson);
					}
				}
			}
		}

		TeeJson json = new TeeJson();
		json.setRtState(true);
		json.setRtMsg("保存成功!");
		return json;
	}

	/**
	 * 待收件  修改打印份数
	 * @param requestData
	 * @return
	 */
	public void updateDeliveryPrintedNum(Map requestData) {
	    String  uuid=(String) requestData.get("uuid");
	    int pNum=TeeStringUtil.getInteger(requestData.get("pNum"), 0);
	    String hql="update TeeDocumentDelivery delivery set delivery.printedNum=delivery.printedNum+? where delivery.uuid=?";
	    simpleDaoSupport.executeUpdate(hql, new Object[]{pNum,uuid});
	}

	/**
	 * 待签阅 修改打印份数
	 * @param requestData
	 */
	public void updateViewPrintedNum(Map requestData) {
		 String  uuid=(String) requestData.get("uuid");
		 int pNum=TeeStringUtil.getInteger(requestData.get("pNum"), 0);
		 
		 String hql="update TeeDocumentView view set view.printedNum=view.printedNum+? where view.uuid=?";
		 simpleDaoSupport.executeUpdate(hql, new Object[]{pNum,uuid});// TODO Auto-generated method stub
		
	}

}
