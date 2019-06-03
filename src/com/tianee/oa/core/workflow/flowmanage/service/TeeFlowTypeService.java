package com.tianee.oa.core.workflow.flowmanage.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.tools.ant.filters.StringInputStream;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.dao.TeeUserRoleDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowSort;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeListCtrlExtend;
import com.tianee.oa.core.workflow.flowmanage.dao.TeeFlowProcessDao;
import com.tianee.oa.core.workflow.flowmanage.dao.TeeFlowTypeDao;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunDao;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeSimpleModel;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFlowSortModel;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFlowTypeModel;
import com.tianee.webframe.annotation.TeeLoggingAnt;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.interceptor.TeeServiceLoggingInterceptor;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeFlowTypeService extends TeeBaseService implements TeeFlowTypeServiceInterface{
	
	@Autowired
	private TeeFlowSortServiceInterface flowSortService;
	
	@Autowired
	private TeeFlowTypeDao flowTypeDao;
	
	@Autowired
	private TeeFlowRunDao flowRunDao;
	
	@Autowired
	private TeeFlowProcessDao flowProcessDao;
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeDeptDao departmentDao;
	
	@Autowired
	private TeeUserRoleDao userRoleDao;
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface#createFlowTypeService(com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType)
	 */
	@Override
	@TeeLoggingAnt(template="创建流程定义 [{$1.flowName}]",type="006A")
	public void createFlowTypeService(TeeFlowType flowType){
//		flowType.setQueryFieldModel("[@流水号,@流程类型,@流程名称,@发起人,@步骤与流程图,@时间,@操作]");
		flowTypeDao.save(flowType);
		//保存start节点
		TeeFlowProcess start = new TeeFlowProcess();
		start.setPrcsName("开始");
		start.setPrcsId(1);
		start.setPrcsType(1);
		start.setX(80);
		start.setY(170);
		start.setFlowType(flowType);
		//默认自动选人
		start.setAutoSelect(1);
		//默认超时可以继续办理
		start.setTimeoutHandable(1);
		
		
		//保存end节点
		TeeFlowProcess end = new TeeFlowProcess();
		end.setPrcsName("结束");
		end.setPrcsId(0);
		end.setPrcsType(2);
		end.setX(300);
		end.setY(170);
		end.setFlowType(flowType);
		
		flowProcessDao.save(start);
		flowProcessDao.save(end);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface#get(int)
	 */
	@Override
	public TeeFlowType get(int flowTypeId){
		return flowTypeDao.get(flowTypeId);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface#load(int)
	 */
	@Override
	public TeeFlowType load(int flowTypeId){
		return flowTypeDao.load(flowTypeId);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface#datagrid(com.tianee.oa.webframe.httpModel.TeeDataGridModel, java.lang.Integer, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel m,Integer sortId,TeePerson loginUser){
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		//判断当前登录的用户是不是系统管理员
		boolean isAdmin=TeePersonService.checkIsSuperAdmin(loginUser, loginUser.getUserId());
		
		
		List<TeeFlowType> flowTypeList = flowTypeDao.findByFlowSort(loginUser,isAdmin,sortId, (m.getPage()-1)*m.getRows(), m.getRows());
		List<TeeFlowTypeModel> mList = new ArrayList<TeeFlowTypeModel>();
		for(TeeFlowType flowType:flowTypeList){
			TeeFlowTypeModel md = new TeeFlowTypeModel();
			BeanUtils.copyProperties(flowType, md);
			//获取已发起工作数
			int count = getTheTotleOfFlowRunByFlowId(flowType.getSid());
			md.setTotalWorkNum(count);
			
			//获取流程绑定表单的名称
			TeeForm form = flowType.getForm();
			md.setFormName(form==null?"[无表单]":form.getFormName());
			
			//获取类型定义
			md.setTypeDesc((flowType.getType()==1?"固定流程":"自由流程"));
			mList.add(md);
		}
		
		json.setRows(mList);
		json.setTotal(flowTypeDao.findCountByFlowSort(sortId,isAdmin,loginUser));
		
		return json;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface#savePrcsPriv(int, int[], int[], int[])
	 */
	@Override
	public void savePrcsPriv(int flowId,int prcsUser [],int prcsDept [],int prcsRole []){
		TeeFlowType flowType = flowTypeDao.load(flowId);
		flowType.getPrcsUsers().clear();
		flowType.getPrcsDepts().clear();
		flowType.getPrcsRoles().clear();
		
		TeePerson person = null;
		TeeDepartment dept = null;
		TeeUserRole role = null;
		
		//添加经办人员
		if(prcsUser.length!=0){
			for(int uuid:prcsUser){
				person = personDao.get(uuid);
				if(person!=null){
					flowType.getPrcsUsers().add(person);
				}
			}
		}
		
		//添加经办部门
		if(prcsDept.length!=0){
			for(int uuid:prcsDept){
				dept = departmentDao.get(uuid);
				if(dept!=null){
					flowType.getPrcsDepts().add(dept);
				}
			}
		}
		
		//添加经办角色
		if(prcsRole.length!=0){
			for(int uuid:prcsRole){
				role = userRoleDao.get(uuid);
				if(role!=null){
					flowType.getPrcsRoles().add(role);
				}
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface#getPrcsPrivModel(int)
	 */
	@Override
	public Map getPrcsPrivModel(int flowId){
		Map map = new HashMap();
		
		TeeFlowType flowType = flowTypeDao.load(flowId);
		
		//获取人员经办权限集合
		Set<TeePerson> prcsUser = flowType.getPrcsUsers();
		List<TeeSimpleModel> mList = new ArrayList<TeeSimpleModel>();
		for(TeePerson p:prcsUser){
			TeeSimpleModel m = new TeeSimpleModel();
			m.setName(p.getUserName());
			m.setValue(p.getUuid());
			mList.add(m);
		}
		map.put("prcsUser", mList);
		
		//获取部门经办权限集合
		Set<TeeDepartment> prcsDept = flowType.getPrcsDepts();
		mList = new ArrayList<TeeSimpleModel>();
		for(TeeDepartment d:prcsDept){
			TeeSimpleModel m = new TeeSimpleModel();
			m.setName(d.getDeptName());
			m.setValue(d.getUuid());
			mList.add(m);
		}
		map.put("prcsDept", mList);
		
		//获取角色经办权限集合
		Set<TeeUserRole> prcsRole = flowType.getPrcsRoles();
		mList = new ArrayList<TeeSimpleModel>();
		for(TeeUserRole r:prcsRole){
			TeeSimpleModel m = new TeeSimpleModel();
			m.setName(r.getRoleName());
			m.setValue(r.getUuid());
			mList.add(m);
		}
		map.put("prcsRole", mList);
		
		return map;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface#getDealCount(int, int, java.lang.StringBuffer)
	 */
	@Override
	public int getDealCount(int userId,int flowId,StringBuffer managerHql){
		
		return flowRunDao.getDealCount(userId,flowId,managerHql);
		
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface#getOverCount(int, int, java.lang.StringBuffer)
	 */
	@Override
	public int getOverCount(int userId,int flowId,StringBuffer managerHql){
		
		return flowRunDao.getOverCount(userId,flowId,managerHql);
		
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface#exportXml(int)
	 */
	@Override
	@Transactional(readOnly=true)
	public String exportXml(int flowId){
		StringBuffer sb = new StringBuffer();
		TeeFlowType ft = flowTypeDao.load(flowId);
		if(ft==null){
			throw new TeeOperationException("流程类型已删除");
		}
		
		Element root;   
		root=new Element("FlowType");
		
		root.addContent(new Element("flowName").setText(ft.getFlowName()));
		root.addContent(new Element("type").setText(ft.getType()+""));
		root.addContent(new Element("orderNo").setText(ft.getOrderNo()+""));
		root.addContent(new Element("comment").setText(ft.getComment()));
		root.addContent(new Element("viewPriv").setText(ft.getViewPriv()+""));
		root.addContent(new Element("delegate").setText(ft.getDelegate()+""));
		root.addContent(new Element("fileCodeExpression").setText(ft.getFileCodeExpression()));
		root.addContent(new Element("delegate").setText(ft.getDelegate()+""));
		root.addContent(new Element("numbering").setText(ft.getNumbering()+""));
		root.addContent(new Element("numberingLength").setText(ft.getNumberingLength()+""));
		root.addContent(new Element("editTitle").setText(ft.getEditTitle()+""));
		root.addContent(new Element("attachPriv").setText(ft.getAttachPriv()+""));

//		String machineCode = "";
//		try {
//			machineCode = (String) TeeClassRunner.exec("com.tianee.webframe.util.auth.TeeAuthUtil", "getMachineCode", null);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//		}
//		root.addContent(new Element("machineCode").setText(machineCode));
		
		Element processList = new Element("processList");
		List<TeeFlowProcess> fpList = ft.getProcessList();
		List<TeeListCtrlExtend>ctrExtendList=new ArrayList<TeeListCtrlExtend>();
		for(TeeFlowProcess fp:fpList){
			Element process = new Element("process");
			Element ctrlList = new Element("ctrlList");
			ctrExtendList=simpleDaoSupport.executeQuery(" from TeeListCtrlExtend  where  flowPrcsId=? ", new Object[]{fp.getSid()});
			for (TeeListCtrlExtend ce : ctrExtendList) {
				Element listCtrl = new Element("listctrl");
				listCtrl.addContent(new Element("flowPrcsId").setText(ce.getFlowPrcsId()+""));
				listCtrl.addContent(new Element("formItemId").setText(ce.getFormItemId()+""));
				listCtrl.addContent(new Element("columnCtrlModel").setText(ce.getColumnCtrlModel()+""));
				ctrlList.addContent(listCtrl);
			}

			process.addContent(ctrlList);
			
			process.addContent(new Element("prcsId").setText(fp.getPrcsId()+""));
			process.addContent(new Element("prcsName").setText(fp.getPrcsName()));
			process.addContent(new Element("prcsDesc").setText(fp.getPrcsDesc()));
			process.addContent(new Element("x").setText(fp.getX()+""));
			process.addContent(new Element("y").setText(fp.getY()+""));
			process.addContent(new Element("prcsType").setText(fp.getPrcsType()+""));
			process.addContent(new Element("opFlag").setText(fp.getOpFlag()+""));
			process.addContent(new Element("userLock").setText(fp.getUserLock()+""));
			process.addContent(new Element("feedback").setText(fp.getFeedback()+""));
			process.addContent(new Element("feedbackViewType").setText(fp.getFeedbackViewType()+""));
			process.addContent(new Element("forceTurn").setText(fp.getForceTurn()+""));
			process.addContent(new Element("goBack").setText(fp.getGoBack()+""));
			process.addContent(new Element("backTo").setText(fp.getBackTo()+""));
			process.addContent(new Element("attachPriv").setText(fp.getAttachPriv()+""));
			process.addContent(new Element("runNamePriv").setText(fp.getRunNamePriv()+""));
			process.addContent(new Element("officePriv").setText(fp.getOfficePriv()+""));
			process.addContent(new Element("officePrivDetail").setText(fp.getOfficePrivDetail()+""));
			process.addContent(new Element("nextPrcsAlert").setText(fp.getNextPrcsAlert()+""));
			process.addContent(new Element("beginUserAlert").setText(fp.getBeginUserAlert()+""));
			process.addContent(new Element("allPrcsUserAlert").setText(fp.getAllPrcsUserAlert()+""));
			process.addContent(new Element("timeout").setText(fp.getTimeout()+""));
			process.addContent(new Element("timeoutFlag").setText(fp.getTimeoutFlag()+""));
			process.addContent(new Element("timeoutType").setText(fp.getTimeoutType()+""));
			process.addContent(new Element("ignoreType").setText(fp.getIgnoreType()+""));
			process.addContent(new Element("sortNo").setText(fp.getSortNo()+""));
			
			process.addContent(new Element("prcsEventDef").setText(TeeStringUtil.getString(fp.getPrcsEventDef())));
			process.addContent(new Element("archivesPriv").setText(TeeStringUtil.getString(fp.getArchivesPriv())));
			process.addContent(new Element("prcsWait").setText(TeeStringUtil.getString(fp.getPrcsWait())));
			process.addContent(new Element("attachOtherPriv").setText(TeeStringUtil.getString(fp.getAttachOtherPriv())));
			process.addContent(new Element("timeoutHandable").setText(TeeStringUtil.getString(fp.getTimeoutHandable())));
			process.addContent(new Element("timeoutAlarm").setText(TeeStringUtil.getString(fp.getTimeoutAlarm())));
			process.addContent(new Element("fieldCtrlModel").setText(TeeStringUtil.getString(fp.getFieldCtrlModel())));
			process.addContent(new Element("conditionModel").setText(TeeStringUtil.getString(fp.getConditionModel())));
			process.addContent(new Element("childFlowId").setText(TeeStringUtil.getString(fp.getChildFlowId())));
			process.addContent(new Element("fieldMapping").setText(TeeStringUtil.getString(fp.getFieldMapping())));
			process.addContent(new Element("fieldReverseMapping").setText(TeeStringUtil.getString(fp.getFieldReverseMapping())));
			process.addContent(new Element("shareAttaches").setText(TeeStringUtil.getString(fp.getShareAttaches())));
			process.addContent(new Element("shareDoc").setText(TeeStringUtil.getString(fp.getShareDoc())));
			process.addContent(new Element("multiInst").setText(TeeStringUtil.getString(fp.getMultiInst())));
			process.addContent(new Element("pluginClass").setText(TeeStringUtil.getString(fp.getPluginClass())));
			process.addContent(new Element("formValidModel").setText(TeeStringUtil.getString(fp.getFormValidModel())));
			process.addContent(new Element("autoTurn").setText(TeeStringUtil.getString(fp.getAutoTurn())));
			process.addContent(new Element("alarmUserIds").setText(TeeStringUtil.getString(fp.getAlarmUserIds())));
			process.addContent(new Element("alarmDeptIds").setText(TeeStringUtil.getString(fp.getAlarmDeptIds())));
			process.addContent(new Element("alarmRoleIds").setText(TeeStringUtil.getString(fp.getAlarmRoleIds())));
			
			//获取经办权限人员
			Set<TeePerson> prcsUser = fp.getPrcsUser();
			StringBuffer ids = new StringBuffer();
			for(TeePerson user:prcsUser){
				ids.append(user.getUuid()+",");
			}
			if(ids.length()!=0){
				ids.deleteCharAt(ids.length()-1);
			}
			process.addContent(new Element("prcsUsers").setText(ids.toString()+""));
			
			//获取经办权限部门
			ids.delete(0, ids.length());
			Set<TeeDepartment> prcsDept = fp.getPrcsDept();
			for(TeeDepartment dept:prcsDept){
				ids.append(dept.getUuid()+",");
			}
			if(ids.length()!=0){
				ids.deleteCharAt(ids.length()-1);
			}
			process.addContent(new Element("prcsDepts").setText(ids.toString()+""));
			
			//获取经办权限角色
			ids.delete(0, ids.length());
			Set<TeeUserRole> prcsRole = fp.getPrcsRole();
			for(TeeUserRole role:prcsRole){
				ids.append(role.getUuid()+",");
			}
			if(ids.length()!=0){
				ids.deleteCharAt(ids.length()-1);
			}
			process.addContent(new Element("prcsRoles").setText(ids.toString()+""));
			
			
			//处理下一步骤节点
			String nextProcess = "";
			Iterator<TeeFlowProcess> it = fp.getNextProcess().iterator();
			while(it.hasNext()){
				TeeFlowProcess tmp = it.next();
				nextProcess+=tmp.getPrcsId();
				if(it.hasNext()){
					nextProcess+=",";
				}
			}
			process.addContent(new Element("nextProcess").setText(nextProcess));
			processList.addContent(process);
		}
		
		root.addContent(processList);
		
		Document doc = new Document(root);   
        XMLOutputter out = new XMLOutputter();   
        
        String str = out.outputString(doc);
        
		return str;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface#deleteAllProcess(int)
	 */
	@Override
	public void deleteAllProcess(int flowId){
		flowProcessDao.deleteOrUpdateByQuery("delete from TeeFlowProcess fp where fp.flowType.sid=?", new Object[]{flowId});
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface#flowCopy(int)
	 */
	@Override
	public void flowCopy(int flowId) throws JDOMException{
		TeeSysLog sysLog = TeeSysLog.newInstance();
		
		//获取之前的流程
		TeeFlowType ft = flowTypeDao.get(flowId);
		//创建一个新的流程
		TeeFlowType newft = new TeeFlowType();
//		newft.setForm(ft.getForm());
		BeanUtils.copyProperties(ft, newft, 
				new String[]{"sid","viewPersons","processList","prcsUsers","prcsDepts","prcsRoles","flowPrivs"});
		newft.setFlowName(ft.getFlowName()+"[副本]");
		flowTypeDao.save(newft);
		
		sysLog.setRemark("复制流程 ["+ft.getFlowName()+"] 为副本");
		sysLog.setType("006D");
		TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		
		String xml = exportXml(flowId);
		StringInputStream in = new StringInputStream(xml,"UTF-8");
		importXml(in, newft.getSid(),1);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface#importXml(java.io.InputStream, int, int)
	 */
	@Override
	public void importXml(InputStream in,int flowId,int importOrg) throws JDOMException{
		TeeFlowType ft = flowTypeDao.load(flowId);
		if(ft==null){
			throw new TeeOperationException("流程类型已删除");
		}
		
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		try {
			doc = builder.build(in);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Element root = doc.getRootElement();
		
//		String machineCode = "";
//		try {
//			machineCode = (String) TeeClassRunner.exec("com.tianee.webframe.util.auth.TeeAuthUtil", "getMachineCode", null);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//		}
//		root.addContent(new Element("machineCode").setText(machineCode));
		
		//设置流程基础信息
//		root.getChild("machineCode").getText();
		
		
		Element processList = root.getChild("processList");
		List<Element> processes = processList.getChildren();
		Map<Integer,TeeFlowProcess> collections = new HashMap<Integer, TeeFlowProcess>();
		//先将基础步骤信息入库
		for(Element process:processes){
			TeeFlowProcess fp = new TeeFlowProcess();
			fp.setPrcsId(TeeStringUtil.getInteger(process.getChild("prcsId").getText(), 0));
			fp.setPrcsName(TeeStringUtil.getString(process.getChild("prcsName").getText()));
			fp.setPrcsDesc(TeeStringUtil.getString(process.getChild("prcsDesc").getText()));
			fp.setX(TeeStringUtil.getInteger(process.getChild("x").getText(), 0));
			fp.setY(TeeStringUtil.getInteger(process.getChild("y").getText(), 0));
			fp.setPrcsType(TeeStringUtil.getInteger(process.getChild("prcsType").getText(), 0));
			fp.setOpFlag(TeeStringUtil.getInteger(process.getChild("opFlag").getText(), 0));
			fp.setUserLock(TeeStringUtil.getInteger(process.getChild("userLock").getText(), 0));
			fp.setFeedback(TeeStringUtil.getInteger(process.getChild("feedback").getText(), 0));
			fp.setFeedbackViewType(TeeStringUtil.getInteger(process.getChild("feedbackViewType").getText(), 0));
			fp.setForceTurn(TeeStringUtil.getInteger(process.getChild("forceTurn").getText(), 0));
			fp.setGoBack(TeeStringUtil.getInteger(process.getChild("goBack").getText(), 0));
			fp.setBackTo(TeeStringUtil.getInteger(process.getChild("backTo").getText(), 0));
			fp.setAttachPriv(TeeStringUtil.getInteger(process.getChild("attachPriv").getText(), 0));
			fp.setRunNamePriv(TeeStringUtil.getInteger(process.getChild("runNamePriv").getText(), 0));
			fp.setOfficePriv(TeeStringUtil.getInteger(process.getChild("officePriv").getText(), 0));
			fp.setOfficePrivDetail(TeeStringUtil.getInteger(process.getChild("officePrivDetail").getText(), 0));
			fp.setNextPrcsAlert(TeeStringUtil.getInteger(process.getChild("nextPrcsAlert").getText(), 0));
			fp.setBeginUserAlert(TeeStringUtil.getInteger(process.getChild("beginUserAlert").getText(), 0));
			fp.setAllPrcsUserAlert(TeeStringUtil.getInteger(process.getChild("allPrcsUserAlert").getText(), 0));
			fp.setTimeout(TeeStringUtil.getInteger(process.getChild("timeout").getText(), 0));
			fp.setTimeoutFlag(TeeStringUtil.getInteger(process.getChild("timeoutFlag").getText(), 0));
			fp.setTimeoutType(TeeStringUtil.getInteger(process.getChild("timeoutType").getText(), 0));
			fp.setIgnoreType(TeeStringUtil.getInteger(process.getChild("ignoreType").getText(), 0));
			fp.setSortNo(TeeStringUtil.getInteger(process.getChild("sortNo").getText(), 0));
			fp.setPrcsEventDef(TeeStringUtil.getString(process.getChild("prcsEventDef").getText()));
			fp.setArchivesPriv(TeeStringUtil.getInteger(process.getChild("archivesPriv").getText(), 0));
			fp.setPrcsWait(TeeStringUtil.getInteger(process.getChild("prcsWait").getText(), 0));
			fp.setAttachOtherPriv(TeeStringUtil.getInteger(process.getChild("attachOtherPriv").getText(), 0));
			fp.setTimeoutHandable(TeeStringUtil.getInteger(process.getChild("timeoutHandable").getText(), 0));
			fp.setTimeoutAlarm(TeeStringUtil.getLong(process.getChild("timeoutAlarm").getText(), 0));
			fp.setFieldCtrlModel(TeeStringUtil.getString(process.getChild("fieldCtrlModel").getText()));
			fp.setConditionModel(TeeStringUtil.getString(process.getChild("conditionModel").getText()));
			fp.setChildFlowId(TeeStringUtil.getInteger(process.getChild("childFlowId").getText(), 0));
			fp.setFieldMapping(TeeStringUtil.getString(process.getChild("fieldMapping").getText()));
			fp.setFieldReverseMapping(TeeStringUtil.getString(process.getChild("fieldReverseMapping").getText()));
			fp.setShareAttaches(TeeStringUtil.getInteger(process.getChild("shareAttaches").getText(), 0));
			fp.setShareDoc(TeeStringUtil.getInteger(process.getChild("shareDoc").getText(), 0));
			fp.setMultiInst(TeeStringUtil.getInteger(process.getChild("multiInst").getText(), 0));
			fp.setPluginClass(TeeStringUtil.getString(process.getChild("pluginClass").getText()));
			fp.setFormValidModel(TeeStringUtil.getString(process.getChild("formValidModel").getText()));
			fp.setAutoTurn(TeeStringUtil.getInteger(process.getChild("autoTurn").getText(), 0));
			fp.setFlowType(ft);
			
			//连带组织机构导入
			if(importOrg==1){
				fp.setAlarmUserIds(TeeStringUtil.getString(process.getChild("alarmUserIds").getText()));
				fp.setAlarmDeptIds(TeeStringUtil.getString(process.getChild("alarmDeptIds").getText()));
				fp.setAlarmRoleIds(TeeStringUtil.getString(process.getChild("alarmRoleIds").getText()));
				
				//导入经办人员
				String sp[] = TeeStringUtil.getString(process.getChild("prcsUsers").getText()).split(",");
				TeePerson p = null;
				for(String id:sp){
					p = new TeePerson();
					p.setUuid(TeeStringUtil.getInteger(id, 0));
					if(p.getUuid()!=0){
						fp.getPrcsUser().add(p);
					}
				}
				
				//导入经办部门
				sp = TeeStringUtil.getString(process.getChild("prcsDepts").getText()).split(",");
				TeeDepartment d = null;
				for(String id:sp){
					d = new TeeDepartment();
					d.setUuid(TeeStringUtil.getInteger(id, 0));
					if(d.getUuid()!=0){
						fp.getPrcsDept().add(d);
					}
				}
				
				//导入经办角色
				sp = TeeStringUtil.getString(process.getChild("prcsRoles").getText()).split(",");
				TeeUserRole r = null;
				for(String id:sp){
					r = new TeeUserRole();
					r.setUuid(TeeStringUtil.getInteger(id, 0));
					if(r.getUuid()!=0){
						fp.getPrcsRole().add(r);
					}
				}
			}
			
			flowProcessDao.save(fp);
			
			Element ctrlList = process.getChild("ctrlList");
			if(ctrlList!=null){
				List<Element> ctrls = ctrlList.getChildren();
				for (Element element : ctrls) {
					TeeListCtrlExtend ce=new TeeListCtrlExtend();
					ce.setColumnCtrlModel(TeeStringUtil.getString(element.getChild("columnCtrlModel").getText()));
				    ce.setFlowPrcsId(TeeStringUtil.getInteger(fp.getSid(), 0));
				    ce.setFormItemId(TeeStringUtil.getInteger(element.getChild("formItemId").getText(), 0));
				    simpleDaoSupport.save(ce);
				}
			}
			
			
			
			collections.put(fp.getPrcsId(), fp);
		}
		
		//进行关系连接
		for(Element process:processes){
			String nextProcess = process.getChild("nextProcess").getText();
			int sp[] = TeeStringUtil.parseIntegerArray(nextProcess);
			TeeFlowProcess cur = collections.get(TeeStringUtil.getInteger(process.getChildText("prcsId"), 0));
			for(int prcsId:sp){
				TeeFlowProcess next = collections.get(prcsId);
				cur.getNextProcess().add(next);
			}
		}
		
		//更新关系连接
		for(Element process:processes){
			TeeFlowProcess cur = collections.get(TeeStringUtil.getInteger(process.getChildText("prcsId"), 0));
			flowProcessDao.update(cur);
		}
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface#updateFlowRunVarsModel(java.lang.String, int)
	 */
	@Override
	public void updateFlowRunVarsModel(String model,int flowTypeId){
		Map updateMap = new HashMap();
		updateMap.put("flowRunVarsModel", model);
		flowTypeDao.update(updateMap, flowTypeId);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface#getTheTotleOfFlowRunByFlowId(int)
	 */
	@Override
	public int getTheTotleOfFlowRunByFlowId(int flowId){
		return flowTypeDao.getTheTotleOfFlowRunByFlowId(flowId);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface#getFlowRunVarsModel(int)
	 */
	@Override
	public String getFlowRunVarsModel(int flowId){
		String hql = "select ft.flowRunVarsModel as model from TeeFlowType ft where ft.sid="+flowId;
		Map data = simpleDaoSupport.getMap(hql, null);
		return TeeStringUtil.getString(data.get("model"));
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface#updateFlowTypeService(com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType)
	 */
	@Override
	@TeeLoggingAnt(template="修改流程定义 [{$1.flowName}]",type="006B")
	public void updateFlowTypeService(TeeFlowType flowType){
		flowTypeDao.update(flowType);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface#deleteService(int)
	 */
	@Override
	@TeeLoggingAnt(template="删除流程定义 [{#.flowName}]",type="006C")
	public TeeFlowType deleteService(int id){
		TeeFlowType ft = load(id);
		flowTypeDao.deleteByObj(ft);
		return ft;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface#findByFlowSort(com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowSort)
	 */
	@Override
	public List<TeeFlowType> findByFlowSort(TeeFlowSort flowSort){
		return flowTypeDao.findByFlowSort(flowSort);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface#findByFlowSort(int)
	 */
	@Override
	public List<TeeFlowType> findByFlowSort(int id){
		return flowTypeDao.findByFlowSort(id);
	}


	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface#findByFlowSort1(int, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public List<TeeFlowType> findByFlowSort1(int id, TeePerson loginUser) {
		// 判断当前登录的用户是不是系统管理员
		boolean isAdmin=TeePersonService.checkIsSuperAdmin(loginUser, loginUser.getUserId());
		
		return flowTypeDao.findByFlowSort1(id,loginUser,isAdmin);
	}

	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface#hasRelatedFlowRun(int)
	 */
	@Override
	public TeeJson hasRelatedFlowRun(int sid) {
		TeeJson json=new TeeJson();
		String hql=" from TeeFlowRun fr where fr.flowType.sid=? ";
		List<TeeFlowRun> runList=simpleDaoSupport.executeQuery(hql, new Object[]{sid});
		if(runList!=null&&runList.size()>0){
			json.setRtData(1);
		}else{
			json.setRtData(0);
		}
		json.setRtState(true);
		return json;
	}

	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface#updateArchiveMapping(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson updateArchiveMapping(HttpServletRequest request) {
		TeeJson json=new  TeeJson();
		int flowTypeId=TeeStringUtil.getInteger(request.getParameter("flowTypeId"), 0);
		String fieldMapping=TeeStringUtil.getString(request.getParameter("fieldMapping"));
		TeeFlowType  flowType=(TeeFlowType) simpleDaoSupport.get(TeeFlowType.class,flowTypeId);
		if(flowType!=null){
			flowType.setArchiveMapping(fieldMapping);
			simpleDaoSupport.update(flowType);
			json.setRtMsg("保存成功！");
			json.setRtState(true);
		}else{
			json.setRtMsg("该流程类型不存在！");
			json.setRtState(false);
		}
		return json;	
	}

	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface#getArchiveMappingById(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson getArchiveMappingById(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int flowTypeId=TeeStringUtil.getInteger(request.getParameter("flowTypeId"), 0);
		TeeFlowType type=(TeeFlowType) simpleDaoSupport.get(TeeFlowType.class,flowTypeId);
		if(type!=null){
			json.setRtData(type.getArchiveMapping());
			json.setRtState(true);
		}else{
			json.setRtState(false);
		}
		return json;
	}

	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface#getAllFlowTypesAndFlowSorts(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson getAllFlowTypesAndFlowSorts(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		List returnList=new ArrayList();
		
		String hql=" from TeeFlowType ft   order by ft.sid ";
		List<TeeFlowType> flowTypeList=simpleDaoSupport.executeQuery(hql, null);
		List<TeeFlowTypeModel> modelList=new ArrayList<TeeFlowTypeModel>();
		TeeFlowTypeModel model=null;
		if(flowTypeList!=null&&flowTypeList.size()>0){
			for (TeeFlowType ft : flowTypeList) {
				model=parseToModel(ft);
				modelList.add(model);
			}
		}
		
		//获取所有的流程分类
		List<TeeFlowSortModel> sortModelList=flowSortService.getModelList();
		
		returnList.add(sortModelList);
		returnList.add(modelList);	
		json.setRtState(true);
		json.setRtData(returnList);
		return json;
	}

	
	/**
	 * 实体类转换成model 类型
	 * @param ft
	 * @return
	 */
	private TeeFlowTypeModel parseToModel(TeeFlowType ft) {
		TeeFlowTypeModel model=new TeeFlowTypeModel();
	    BeanUtils.copyProperties(ft, model);
	    if(ft.getFlowSort()!=null){
	    	model.setFlowSortId(ft.getFlowSort().getSid());
	    	model.setFlowSortName(ft.getFlowSort().getSortName());
	    }
	    
	    if(ft.getForm()!=null){
	    	model.setFormId(ft.getForm().getSid());
	    	model.setFormName(ft.getForm().getFormName());
	    }
		return model;
	}

	
	
}
