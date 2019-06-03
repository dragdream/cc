package com.tianee.oa.core.workflow.flowrun.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeService;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunCtrlFeedback;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunDoc;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunDao;
import com.tianee.oa.core.workflow.flowrun.model.TeeFlowRunModel;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.annotation.TeeLoggingAnt;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;
@Service
public class TeeFlowRunService extends TeeBaseService implements TeeFlowRunServiceInterface{

	@Autowired
	private TeePersonService personService;
	
	@Autowired
	private TeeFlowRunDao flowRunDao;
	
	@Autowired
	private TeeAttachmentService attachmentService;
	
	@Autowired
	private TeeFlowTypeService flowTypeService;
	
	@Autowired
	private TeeFlowRunDocService flowRunDocService;
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunServiceInterface#get(int)
	 */
	@Override
	public TeeFlowRun get(int runId){
		return flowRunDao.get(runId);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunServiceInterface#delete(com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun)
	 */
	@Override
	public void delete(TeeFlowRun flowRun) {
		flowRunDao.deleteByObj(flowRun);
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunServiceInterface#delete(int)
	 */
	@Override
	public void delete(int runId) {
		flowRunDao.delete(runId);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunServiceInterface#save(com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun)
	 */
	@Override
	public void save(TeeFlowRun flowRun) {
		flowRunDao.save(flowRun);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunServiceInterface#update(com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun)
	 */
	@Override
	public void update(TeeFlowRun flowRun) {
		flowRunDao.update(flowRun);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunServiceInterface#getChildFlowRuns(int)
	 */
	@Override
	public List<TeeFlowRun> getChildFlowRuns(int runId){
		String hql = "from TeeFlowRun fr where fr.pRunId="+runId;
		return simpleDaoSupport.find(hql, null);
	}

	//清空数据
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunServiceInterface#clearRunDatasService(int)
	 */
	@Override
	@TeeLoggingAnt(type="006J",template="清空流程数据 [{logModel.flowName}]")
	public void clearRunDatasService(int flowId){
		Map logModel = new HashMap();
		
		TeeFlowType ft = (TeeFlowType) simpleDaoSupport.get(TeeFlowType.class, flowId);
		logModel.put("flowName", ft.getFlowName());
		TeeRequestInfoContext.getRequestInfo().setLogModel(logModel);
		
		//删除流程步骤实例
		simpleDaoSupport.executeUpdate("delete from TeeFlowRunPrcs frp where exists (select 1 from TeeFlowRun fr where frp.flowRun=fr and fr.flowType.sid=?)", new Object[]{flowId});
		//删除会签意见
		simpleDaoSupport.executeUpdate("delete from TeeFeedBack fb where exists (select 1 from TeeFlowRun fr where fb.flowRun=fr and fr.flowType.sid=?)", new Object[]{flowId});
		//删除流程关注
		simpleDaoSupport.executeUpdate("delete from TeeFlowRunConcern frc where exists (select 1 from TeeFlowRun fr where frc.flowRun=fr and fr.flowType.sid=?)",  new Object[]{flowId});
		//删除flow_run_view
		simpleDaoSupport.executeUpdate("delete from TeeFlowRunView frv where exists (select 1 from TeeFlowRun fr where frv.flowRun=fr and fr.flowType.sid=?)",  new Object[]{flowId});
		//删除flow_run_vars
		simpleDaoSupport.executeUpdate("delete from TeeFlowRunVars frv where exists (select 1 from TeeFlowRun fr where frv.flowRun=fr and fr.flowType.sid=?)",  new Object[]{flowId});
		//删除flow_run_doc
		simpleDaoSupport.executeUpdate("delete from TeeFlowRunDoc frd where exists (select 1 from TeeFlowRun fr where frd.flowRun=fr and fr.flowType.sid=?)",  new Object[]{flowId});
		//删除会签控件数据
		simpleDaoSupport.executeUpdate("delete from TeeFlowRunCtrlFeedback frcf where exists (select 1 from TeeFlowRun fr where frcf.flowRun=fr and fr.flowType.sid=?)",  new Object[]{flowId});
		
		//删除流程业务数据
		simpleDaoSupport.executeUpdate("delete from BisRunRelation br where exists (select 1 from TeeFlowRun fr where fr.runId=br.runId and fr.flowType.sid=?)",  new Object[]{flowId});
		//删除流程日志
		simpleDaoSupport.executeUpdate("delete from TeeFlowRunLog frl where frl.flowId="+ft.getSid(), null);
		//删除相关计划
		simpleDaoSupport.executeUpdate("delete from TeeRunScheduleRel rel where exists (select 1 from TeeFlowRun fr where rel.flowRun=fr and fr.flowType.sid=?)", new Object[]{flowId});
		//删除相关任务
		simpleDaoSupport.executeUpdate("delete from TeeRunTaskRel rel where exists (select 1 from TeeFlowRun fr where rel.flowRun=fr and fr.flowType.sid=?)", new Object[]{flowId});
		//删除相关客户
		simpleDaoSupport.executeUpdate("delete from TeeRunCustomerRel rel where exists (select 1 from TeeFlowRun fr where rel.flowRun=fr and fr.flowType.sid=?)", new Object[]{flowId});
		//删除相关流程
		simpleDaoSupport.executeUpdate("delete from TeeRunRel rel where exists (select 1 from TeeFlowRun fr where rel.flowRun=fr and fr.flowType.sid=?) "
				+ "or exists (select 1 from TeeFlowRun fr where rel.flowRun1=fr and fr.flowType.sid=?)", new Object[]{flowId,flowId});
		
		
		
		
		
		//查找该流程下的所有的flow_run的集合
		List<TeeFlowRun>list=simpleDaoSupport.executeQuery("from TeeFlowRun fr where fr.flowType.sid="+ft.getSid(), null);
	    String Ids="(";
        for (TeeFlowRun teeFlowRun : list) {
        	Ids=Ids+teeFlowRun.getRunId()+",";
		}
        
        if(Ids.endsWith(",")){
        	Ids=Ids.substring(0, Ids.length()-1);	
        }
        Ids+=")";
		//删除flow_run
		simpleDaoSupport.executeUpdate("delete from TeeFlowRun fr where fr.flowType.sid="+ft.getSid(), null);

	    //删除Tee_f_r_d_xx表中的数据
		//获取表名 
		String  tableName="tee_f_r_d_"+ft.getSid();
		simpleDaoSupport.executeNativeUpdate("delete from "+tableName+" where run_id in "+Ids, null);
	}

	@Override
	public TeeEasyuiDataGridJson getFlowRunListByFlowId(HttpServletRequest request,TeeDataGridModel dataGridModel,TeeFlowRunModel queryModel) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		
		String flowId = request.getParameter("flowId");
		TeeFlowType teeFlowType = flowTypeService.get(TeeStringUtil.getInteger(flowId, 0));
		Object []values = {teeFlowType};
		
		//查询data
		String hql = "from TeeFlowRun where 1=1 "; 
		//查询total
		String totalHql = "select count(runId) from TeeFlowRun where 1=1 "; 
		int runId = TeeStringUtil.getInteger(queryModel.getRunId(), 0);
		if (runId > 0) {
			hql += "and runId = "+runId+" ";
			totalHql += "and runId = "+runId+" ";
		}
		if (!TeeUtility.isNullorEmpty(queryModel.getRunName())) {
			hql += "and runName like '%" +queryModel.getRunName()+ "%' ";
			totalHql += "and runName like '%" +queryModel.getRunName()+ "%' ";
		}
			hql += "and flowType = ? and delFlag = 0";
			totalHql += "and flowType = ? and delFlag = 0";
		List<TeeFlowRun> flowRunList = simpleDaoSupport.pageFind(hql, dataGridModel.getFirstResult(), dataGridModel.getRows(), values);
		Long rows = simpleDaoSupport.count(totalHql, values);
		
		List<TeeFlowRunModel> flowRunModels = new ArrayList<TeeFlowRunModel>();
		if (flowRunList != null && flowRunList.size() > 0) {
			for (TeeFlowRun teeFlowRun : flowRunList) {
				TeeFlowRunModel model = new TeeFlowRunModel();
				model.setRunId(teeFlowRun.getRunId()+"");
				model.setRunName(teeFlowRun.getRunName());
				flowRunModels.add(model);
			}
		
		}
			dataGridJson.setRows(flowRunModels);
			dataGridJson.setTotal(rows);
		
		return dataGridJson;
	}
	
	
	@Override
	public TeeJson copyBodyAttachement(HttpServletRequest request) {
		//获取发文附件     modelId格式为:表单的上传控件的id 'Data_xx'数字部分+下划线+runId
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String bodyRunId = request.getParameter("bodyRunId");
		String summaryRunId = request.getParameter("summaryRunId");
		
		TeeFlowRunDoc flowRunDoc = flowRunDocService.getFlowRunDocByRunId(TeeStringUtil.getInteger(bodyRunId, 0));
		if (flowRunDoc != null) {
			TeeAttachment docAttach = flowRunDoc.getDocAttach();
			TeeAttachment summaryAttachment = attachmentService.clone(docAttach, TeeAttachmentModelKeys.workFlowUploadCtrl, loginPerson);
			// modelId格式为:表单的上传控件的id 'Data_xx'数字部分+下划线+runId
			summaryAttachment.setModelId("12_"+summaryRunId);
			attachmentService.addAttachment(summaryAttachment);
			json.setRtState(true);
		}else{
			json.setRtState(false);
			json.setRtMsg("获取正文附件失败！");
		}
		return json;
	}

	
	
	/**
	 * 数据同步
	 */
	@Override
	public TeeJson dataSync(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取传来的json字符串
		String dataStr=TeeStringUtil.getString(request.getParameter("dataStr"));
		//转换成json
		Map<String,String> dataMap=TeeJsonUtil.JsonStr2Map(dataStr);
		
		
        //流程基本属性
		String info=dataMap.get("info");
		Map<String,String> infoMap=TeeJsonUtil.JsonStr2Map(info);
		String userId=infoMap.get("userId");//创建人登陆名
		//创建时间
		String beginTimeStr=infoMap.get("beginTimeStr");//创建时间
		Calendar beginTime=TeeDateUtil.parseCalendar(beginTimeStr);
		//结束时间
		String endTimeStr=infoMap.get("endTimeStr");
		Calendar endTime=TeeDateUtil.parseCalendar(endTimeStr);
		//是否销毁
		int delFlag=TeeStringUtil.getInteger(infoMap.get("delFlag"), 0);
		TeePerson  createUser=personService.getPersonByUserId(userId);
		//紧急程度
		int level=TeeStringUtil.getInteger(infoMap.get("level"),1);
		//流程名称
		String runName=infoMap.get("runName");
		//所属流程类型
		TeeFlowType flowType=(TeeFlowType) simpleDaoSupport.get(TeeFlowType.class,1009);
		TeeForm form=flowType.getForm();
		int formVersion=form.getVersionNo();
//*********************创建一个flowRun对象*******************************
		TeeFlowRun fr=new TeeFlowRun();
		fr.setBeginPerson(createUser);
		fr.setBeginTime(beginTime);
		fr.setDelFlag(delFlag);
		fr.setBusinessModel(null);
		fr.setEndTime(endTime);
		fr.setFlowType(flowType);
		fr.setForm(form);
		fr.setFormVersion(formVersion);
		fr.setIsSave(1);
		fr.setLevel(level);
		fr.setoRunName(runName);
		fr.setPChildFlowPrcs(0);
		fr.setPRunId(0);
		fr.setPSrouceFrpSid(0);
		fr.setSendFlag(1);
		fr.setRunName(runName);
		
		//保存flowRun对象
		simpleDaoSupport.save(fr);
		
//***************************TeeFlowRunPrcs  开始 ****************************************		
		//创建一个开始节点
		TeeFlowRunPrcs beginPrcs=new TeeFlowRunPrcs();
		beginPrcs.setAttachCtrlModel("");
        beginPrcs.setBackFlag(0);
        beginPrcs.setBeginTime(beginTime);
        beginPrcs.setBeginTimeStamp(beginTime.getTimeInMillis());
        beginPrcs.setDelFlag(0);//0=未删除   1=已删除
        beginPrcs.setEndTime(endTime);
        beginPrcs.setEndTimeStamp(endTime.getTimeInMillis());
        beginPrcs.setFlag(4);//状态标识  1-未接收  2-办理中  3-转交下一步  4-已办结  5-预设步骤
        beginPrcs.setFlowRun(fr);
        //获取第一节点的TeeFlowProcess
        TeeFlowProcess process=(TeeFlowProcess) simpleDaoSupport.get(TeeFlowProcess.class,2045);
        beginPrcs.setFlowPrcs(process);
        beginPrcs.setFlowType(flowType);
        beginPrcs.setFreeCtrlModel("");
        beginPrcs.setFreeTurnModel("");
        beginPrcs.setFromUser(createUser);
        beginPrcs.setIgnoreType(0);
        beginPrcs.setOpFlag(1);
        beginPrcs.setOtherUser(null);
        beginPrcs.setParallelPrcsId(0);
        beginPrcs.setParent(null);
        beginPrcs.setPrcsEvent("");
        beginPrcs.setPrcsId(1);
        beginPrcs.setPrcsUser(createUser);
        beginPrcs.setSuspend(0);
        beginPrcs.setTimeout(0);
        beginPrcs.setTimeoutAlarm(0);
        beginPrcs.setTimeoutAlarmFlag(0);
        beginPrcs.setTimeoutFlag(0);
        beginPrcs.setTimeoutType(1);
        beginPrcs.setTopFlag(1);
		
        
        simpleDaoSupport.save(beginPrcs);
//***************************处理正文****************************************
      		//处理正文？？？？？
        
        
//***************************插入表单信息  到中间表 tee_f_r_d_1009********************
		String formInfo=dataMap.get("form");
		Map<String,String> formMap=TeeJsonUtil.JsonStr2Map(formInfo);
		//文号      DATA_23
		String wh=formMap.get("wh");
		//缓急    DATA_24
		String hj=formMap.get("hj");
		//主送   DATA_27
		String zs=formMap.get("zs");
		//抄送  DATA_28
		String cs=formMap.get("cs");
		//拟稿部门  DATA_29
		String ngbm=formMap.get("ngbm");
		//拟稿人  DATA_30
		String ngr=formMap.get("ngr");
		//核稿人  DATA_31
		String hgr=formMap.get("hgr");
		//校对人 DATA_32
		String jdr=formMap.get("jdr");
		//份数     DATA_33
		String fs=formMap.get("fs");
		//信息公开属性 DATA_37
		String xxgksx=formMap.get("xxgksx");
		//标题 DATA_40
		String bt=formMap.get("bt");
		
		
		//执行sql语句插入数据
		String sql=" insert into tee_f_r_d_1009(RUN_ID,DATA_23,DATA_24,DATA_27,DATA_28,DATA_29,DATA_30,DATA_31,DATA_32,DATA_33,DATA_37,DATA_40) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		simpleDaoSupport.executeNativeUpdate(sql, new Object[]{fr.getRunId(),wh,hj,zs,cs,ngbm,ngr,hgr,jdr,fs,xxgksx,bt});
		
		
		//签发  DATA_38   直接存中间表
		TeeFlowRunCtrlFeedback qfFb=null;
		String qf=formMap.get("qf");
		List<Map<String,String>> qfList=TeeJsonUtil.JsonStr2MapList(qf);
		if(qfList!=null&&qfList.size()>0){
			for (Map<String, String> map : qfList) {
				qfFb = new TeeFlowRunCtrlFeedback();
				qfFb.setContent(map.get("content"));
				qfFb.setCreateTime(TeeDateUtil.parseCalendar(map.get("createTimeStr")));
				
				String userId1=map.get("userId");
				TeePerson user=personService.getPersonByUserId(userId1);
				qfFb.setCreateUser(user);
				if(user!=null){
					qfFb.setUserName(user.getUserName());
					if(user.getDept()!=null){
						qfFb.setDeptName(user.getDept().getDeptName());
						qfFb.setDeptNamePath(user.getDept().getDeptFullName());
					}
					
					if(user.getUserRole()!=null){
						qfFb.setRoleName(user.getUserRole().getRoleName());
					}
				}

				qfFb.setFlowPrcs(beginPrcs.getFlowPrcs());
				qfFb.setFlowRun(fr);
				qfFb.setItemId(38);
				qfFb.setRand(System.currentTimeMillis()+"");
				
				qfFb.setH5Data(null);
				qfFb.setHwData(null);	
				qfFb.setMobiData(null);
				qfFb.setPicData(null);
				qfFb.setSealData(map.get("sealData"));//签章数据
				qfFb.setSignature(null);
				qfFb.setSignData(null);
				
				simpleDaoSupport.save(qfFb);
			}
		}
		
		//会签 DADA_39 直接存中间表
		TeeFlowRunCtrlFeedback hqFb=null;
		String hq=formMap.get("hq");
		List<Map<String,String>> hqList=TeeJsonUtil.JsonStr2MapList(hq);
		if(hqList!=null&&hqList.size()>0){
			for (Map<String, String> map : hqList) {
				hqFb = new TeeFlowRunCtrlFeedback();
				hqFb.setContent(map.get("content"));
				hqFb.setCreateTime(TeeDateUtil.parseCalendar(map.get("createTimeStr")));
				
				String userId1=map.get("userId");
				TeePerson user=personService.getPersonByUserId(userId1);
				hqFb.setCreateUser(user);
				if(user!=null){
					hqFb.setUserName(user.getUserName());
					if(user.getDept()!=null){
						hqFb.setDeptName(user.getDept().getDeptName());
						hqFb.setDeptNamePath(user.getDept().getDeptFullName());
					}
					
					if(user.getUserRole()!=null){
						hqFb.setRoleName(user.getUserRole().getRoleName());
					}
				}

				hqFb.setFlowPrcs(beginPrcs.getFlowPrcs());
				hqFb.setFlowRun(fr);
				hqFb.setItemId(39);
				hqFb.setRand(System.currentTimeMillis()+"");
				
				hqFb.setH5Data(null);
				hqFb.setHwData(null);	
				hqFb.setMobiData(null);
				hqFb.setPicData(null);
				hqFb.setSealData(map.get("sealData"));//签章数据
				hqFb.setSignature(null);
				hqFb.setSignData(null);
				
				simpleDaoSupport.save(hqFb);
			}
		}
		
		
		//办公室审核  DATA_1 直接存中间表
		TeeFlowRunCtrlFeedback shFb=null;
		String sh=formMap.get("bgssh");
		List<Map<String,String>> shList=TeeJsonUtil.JsonStr2MapList(sh);
		if(shList!=null&&shList.size()>0){
			for (Map<String, String> map : shList) {
				shFb = new TeeFlowRunCtrlFeedback();
				shFb.setContent(map.get("content"));
				shFb.setCreateTime(TeeDateUtil.parseCalendar(map.get("createTimeStr")));
				
				String userId1=map.get("userId");
				TeePerson user=personService.getPersonByUserId(userId1);
				shFb.setCreateUser(user);
				if(user!=null){
					shFb.setUserName(user.getUserName());
					if(user.getDept()!=null){
						shFb.setDeptName(user.getDept().getDeptName());
						shFb.setDeptNamePath(user.getDept().getDeptFullName());
					}
					
					if(user.getUserRole()!=null){
						shFb.setRoleName(user.getUserRole().getRoleName());
					}
				}

				shFb.setFlowPrcs(beginPrcs.getFlowPrcs());
				shFb.setFlowRun(fr);
				shFb.setItemId(1);
				shFb.setRand(System.currentTimeMillis()+"");
				
				shFb.setH5Data(null);
				shFb.setHwData(null);	
				shFb.setMobiData(null);
				shFb.setPicData(null);
				shFb.setSealData(map.get("sealData"));//签章数据
				shFb.setSignature(null);
				shFb.setSignData(null);
				
				simpleDaoSupport.save(shFb);
			}
		}
		
		//附件处理 DATA_36  直接存attachment表？？？？？

        json.setRtState(true);
		return json;
	}

	
	/**
	 * 数据同步  签报管理
	 */
	@Override
	public TeeJson dataSync1(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取传来的json字符串
		String dataStr=TeeStringUtil.getString(request.getParameter("dataStr"));
		//转换成json
		Map<String,String> dataMap=TeeJsonUtil.JsonStr2Map(dataStr);
		
		
        //流程基本属性
		String info=dataMap.get("info");
		Map<String,String> infoMap=TeeJsonUtil.JsonStr2Map(info);
		String userId=infoMap.get("userId");//创建人登陆名
		TeePerson  createUser=personService.getPersonByUserId(userId);
		//创建时间
		String beginTimeStr=infoMap.get("beginTimeStr");//创建时间
		Calendar beginTime=TeeDateUtil.parseCalendar(beginTimeStr);
		//结束时间
		String endTimeStr=infoMap.get("endTimeStr");
		Calendar endTime=TeeDateUtil.parseCalendar(endTimeStr);
		//是否销毁
		int delFlag=TeeStringUtil.getInteger(infoMap.get("delFlag"), 0);
		//紧急程度
		int level=TeeStringUtil.getInteger(infoMap.get("level"),1);
		//流程名称
		String runName=infoMap.get("runName");
		//所属流程类型
		TeeFlowType flowType=(TeeFlowType) simpleDaoSupport.get(TeeFlowType.class,1011);
		TeeForm form=flowType.getForm();
		int formVersion=form.getVersionNo();
//*********************创建一个flowRun对象*******************************
		TeeFlowRun fr=new TeeFlowRun();
		fr.setBeginPerson(createUser);
		fr.setBeginTime(beginTime);
		fr.setDelFlag(delFlag);
		fr.setBusinessModel(null);
		fr.setEndTime(endTime);
		fr.setFlowType(flowType);
		fr.setForm(form);
		fr.setFormVersion(formVersion);
		fr.setIsSave(1);
		fr.setLevel(level);
		fr.setoRunName(runName);
		fr.setPChildFlowPrcs(0);
		fr.setPRunId(0);
		fr.setPSrouceFrpSid(0);
		fr.setSendFlag(1);
		fr.setRunName(runName);
		
		//保存flowRun对象
		simpleDaoSupport.save(fr);
		
//***************************TeeFlowRunPrcs  开始 ****************************************		
		//创建一个开始节点
		TeeFlowRunPrcs beginPrcs=new TeeFlowRunPrcs();
		beginPrcs.setAttachCtrlModel("");
        beginPrcs.setBackFlag(0);
        beginPrcs.setBeginTime(beginTime);
        beginPrcs.setBeginTimeStamp(beginTime.getTimeInMillis());
        beginPrcs.setDelFlag(0);//0=未删除   1=已删除
        beginPrcs.setEndTime(endTime);
        beginPrcs.setEndTimeStamp(endTime.getTimeInMillis());
        beginPrcs.setFlag(4);//状态标识  1-未接收  2-办理中  3-转交下一步  4-已办结  5-预设步骤
        beginPrcs.setFlowRun(fr);
        //获取第一节点的TeeFlowProcess
        TeeFlowProcess process=(TeeFlowProcess) simpleDaoSupport.get(TeeFlowProcess.class,2033);
        beginPrcs.setFlowPrcs(process);
        beginPrcs.setFlowType(flowType);
        beginPrcs.setFreeCtrlModel("");
        beginPrcs.setFreeTurnModel("");
        beginPrcs.setFromUser(createUser);
        beginPrcs.setIgnoreType(0);
        beginPrcs.setOpFlag(1);
        beginPrcs.setOtherUser(null);
        beginPrcs.setParallelPrcsId(0);
        beginPrcs.setParent(null);
        beginPrcs.setPrcsEvent("");
        beginPrcs.setPrcsId(1);
        beginPrcs.setPrcsUser(createUser);
        beginPrcs.setSuspend(0);
        beginPrcs.setTimeout(0);
        beginPrcs.setTimeoutAlarm(0);
        beginPrcs.setTimeoutAlarmFlag(0);
        beginPrcs.setTimeoutFlag(0);
        beginPrcs.setTimeoutType(1);
        beginPrcs.setTopFlag(1);
		
        
        simpleDaoSupport.save(beginPrcs);
//***************************处理正文****************************************
      //处理正文？？？？？
        
        
//***************************插入表单信息  到中间表 tee_f_r_d_1009********************
      		String formInfo=dataMap.get("form");
      		Map<String,String> formMap=TeeJsonUtil.JsonStr2Map(formInfo);
      		//标题 DATA_20
      		String bt=formMap.get("bt");
      		//经办人 DATA_10
      		String jbr=formMap.get("jbr");
      		//审核人 DATA_6
      		String shr=formMap.get("shr");
      		//请示内容 DATA_15
      		String qsnr=formMap.get("qsnr");
      		//所属部门 DATA_18
      		String ssbm=formMap.get("ssbm");
      		//创建时间 DATA_19
      		String cjsj=formMap.get("cjsj");
      		
      		//中间表中插入数据
      		String sql=" insert into tee_f_r_d_1011(RUN_ID,DATA_20,DATA_10,DATA_6, DATA_15,DATA_18,DATA_19) values(?,?,?,?,?,?,?) ";
      		simpleDaoSupport.executeNativeUpdate(sql,new Object[]{fr.getRunId(),bt,jbr,shr,qsnr,ssbm,cjsj});
      		

      		//领导批示意见 DATA_9
      		TeeFlowRunCtrlFeedback fb=null;
      		String ldpsyj=formMap.get("ldpsyj");
      		List<Map<String,String>> fbList=TeeJsonUtil.JsonStr2MapList(ldpsyj);
      		if(fbList!=null&&fbList.size()>0){
      			for (Map<String, String> map : fbList) {
      				fb = new TeeFlowRunCtrlFeedback();
      				fb.setContent(map.get("content"));
      				fb.setCreateTime(TeeDateUtil.parseCalendar(map.get("createTimeStr")));
      				
      				String userId1=map.get("userId");
      				TeePerson user=personService.getPersonByUserId(userId1);
      				fb.setCreateUser(user);
      				if(user!=null){
      					fb.setUserName(user.getUserName());
      					if(user.getDept()!=null){
      						fb.setDeptName(user.getDept().getDeptName());
      						fb.setDeptNamePath(user.getDept().getDeptFullName());
      					}
      					
      					if(user.getUserRole()!=null){
      						fb.setRoleName(user.getUserRole().getRoleName());
      					}
      				}

      				fb.setFlowPrcs(beginPrcs.getFlowPrcs());
      				fb.setFlowRun(fr);
      				fb.setItemId(9);
      				fb.setRand(System.currentTimeMillis()+"");
      				
      				fb.setH5Data(null);
      				fb.setHwData(null);	
      				fb.setMobiData(null);
      				fb.setPicData(null);
      				fb.setSealData(map.get("sealData"));//签章数据
      				fb.setSignature(null);
      				fb.setSignData(null);
      				
      				simpleDaoSupport.save(fb);
      			}
      		}
      	
      		
      		//附件处理 DATA_16   直接存attachment表？？？？？

        json.setRtState(true);
		return json;
	}

	
	/**
	 * 根据frpSid判断当前步骤是否允许归档
	 */
	@Override
	public TeeJson checkIsArchive(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int frpSid=TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
		TeeFlowRunPrcs prcs=(TeeFlowRunPrcs) simpleDaoSupport.get(TeeFlowRunPrcs.class,frpSid);
		if(prcs!=null){
			if(prcs.getFlowPrcs()!=null){
				json.setRtState(true);
				json.setRtData(prcs.getFlowPrcs().getArchivesPriv());
			}
		}else{
			json.setRtState(false);
		}
		return json;
	}


	/**
	 * 获取步骤号
	 * */
	@Override
	public TeeJson findPrcsId(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int frpSid=TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
		int runId=TeeStringUtil.getInteger(request.getParameter("runId"),0);
		List<TeeFlowRunPrcs> find = simpleDaoSupport.find("from TeeFlowRunPrcs where flowRun.runId=? order by sid desc ", new Object[]{runId});
		TeeFlowRunPrcs fp=null;
		if(find!=null && find.size()>0){
			fp = find.get(0);
		}
		//TeeFlowRunPrcs fp=(TeeFlowRunPrcs)simpleDaoSupport.get(TeeFlowRunPrcs.class,frpSid);
		int prcsId=0;
		if(fp!=null){
			prcsId = fp.getFlowPrcs().getPrcsId();
		}
		json.setRtData(prcsId);
		json.setRtState(true);
		return json;
	}

	/**
	 * 修改第一步骤的数据（1032）
	 * */
	@Override
	public TeeJson updateEndTimeByPrcsId(HttpServletRequest request) {
		int runId=TeeStringUtil.getInteger(request.getParameter("runId"),0);
		int flowId=TeeStringUtil.getInteger(request.getParameter("flowId"),0);
		List<TeeFlowRunPrcs> find = simpleDaoSupport.find("from TeeFlowRunPrcs where flowRun.runId=? order by sid", new Object[]{runId});
		int executeNativeUpdate=0;
		if(find!=null && find.size()>0){
			Calendar endTime2 = find.get(0).getEndTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String endTime = sdf.format(endTime2.getTime());
	    	//String endTime = TeeStringUtil.getString(map.get("endTime"), "yyyy-MM-dd");
	    	executeNativeUpdate = simpleDaoSupport.executeNativeUpdate("update tee_f_r_d_"+flowId+" set DATA_6=? where RUN_ID=?", new Object[]{endTime,runId});
	    }
		return null;
	}

	
	/**
	 * 根据runId获取flowType的文档类型
	 */
	@Override
	public TeeJson getDocTypeByRunId(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int runId=TeeStringUtil.getInteger(request.getParameter("runId"),0);
		
		TeeFlowRun flowRun=(TeeFlowRun) simpleDaoSupport.get(TeeFlowRun.class,runId);
		
		if(flowRun!=null){
			TeeFlowType flowType=flowRun.getFlowType();
			if(flowType!=null){
				json.setRtState(true);
			    json.setRtData(flowType.getHasDoc());
				
			}else{
				json.setRtState(false);
				json.setRtMsg("流程类型信息获取失败！");
			}
		}else{
			json.setRtState(false);
			json.setRtMsg("流程信息获取失败！");
		}
		
		return json;
	}
	
}
