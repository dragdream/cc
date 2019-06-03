package com.tianee.oa.core.workflow.workmanage.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.workFlowFrame.dataloader.TeeFixedFlowDataLoaderInterface;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunLog;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunDao;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunLogDao;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunPrcsDao;
import com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFlowRunPrcsModel;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeWorkflowManageService extends TeeBaseService implements TeeWorkflowManageServiceInterface{
	@Autowired
	private TeeFlowRunDao flowRunDao;
	
	@Autowired
	private TeeFlowRunLogDao flowRunLogDao;
	
	@Autowired
	private TeeFlowRunPrcsDao flowRunPrcsDao;
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeFixedFlowDataLoaderInterface fixedFlowDataLoader;
	
	@Autowired
	private TeeFlowRunPrcsServiceInterface flowRunPrcsService;
	
	@Autowired
	TeeSmsManager smsManager;
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeWorkflowManageServiceInterface#endRun(int)
	 */
	@Override
	public TeeFlowRun endRun(int runId){
		TeeFlowRun fr = flowRunDao.get(runId);
		fr.setEndTime(Calendar.getInstance());
		flowRunDao.update(fr);
		
		//记录流程日志
		TeeFlowRunLog log = TeeFlowRunLog.getInstance();
		log.setRunName(fr.getRunName());
		log.setRunId(fr.getRunId());
		log.setContent("强制结束此流程");
		log.setType(6);
		
		flowRunLogDao.save(log);
		
		return fr;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeWorkflowManageServiceInterface#recoverRun(int)
	 */
	@Override
	public TeeFlowRun recoverRun(int runId){
		TeeFlowRun fr = flowRunDao.get(runId);
		fr.setEndTime(null);
		flowRunDao.update(fr);
		
		//记录流程日志
		TeeFlowRunLog log = TeeFlowRunLog.getInstance();
		log.setRunName(fr.getRunName());
		log.setRunId(fr.getRunId());
		log.setContent("恢复执行此流程");
		log.setType(7);
		
		flowRunLogDao.save(log);
		
		return fr;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeWorkflowManageServiceInterface#delRun(int)
	 */
	@Override
	public TeeFlowRun delRun(int runId){
		TeeFlowRun fr = flowRunDao.get(runId);
		fr.setDelFlag(1);
		flowRunDao.update(fr);
		
		//记录流程日志
		TeeFlowRunLog log = TeeFlowRunLog.getInstance();
		log.setRunName(fr.getRunName());
		log.setRunId(fr.getRunId());
		log.setContent("删除此流程");
		log.setType(8);
		
		flowRunLogDao.save(log);
		
		simpleDaoSupport.executeUpdate("update TeeFlowRunPrcs set delFlag=1 where flowRun.runId="+runId, null);
		
		return fr;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeWorkflowManageServiceInterface#destroyRun(int)
	 */
	@Override
	public TeeFlowRun destroyRun(int runId){
		TeeFlowRun fr = flowRunDao.get(runId);
		flowRunDao.delete(fr);
		
		//记录流程日志
		TeeFlowRunLog log = TeeFlowRunLog.getInstance();
		log.setRunName(fr.getRunName());
		log.setRunId(fr.getRunId());
		log.setContent("销毁此流程");
		log.setType(9);
		
		flowRunLogDao.save(log);
		
		return fr;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeWorkflowManageServiceInterface#flowRunDelegate(int, int)
	 */
	@Override
	public TeeFlowRunPrcs flowRunDelegate(int frpSid,int delegateTo){
		TeeFlowRunPrcs frp = flowRunPrcsDao.get(frpSid);
		
		if(frp==null){
			throw new TeeOperationException("该流程步骤不存在或已被删除");
		}
		
		if(frp.getFlag()==3 || frp.getFlag()==4){
			throw new TeeOperationException("该流程步骤已转交至下一步");
		}
		
		//判断当前流程的办理人是否与被代理人相同，如果相同则不允许委托
		if(frp.getPrcsUser().getUuid()==delegateTo){
			throw new TeeOperationException("被委托人已经是该步骤的经办人，无法进行委托");
		}
		
		//判断当前步骤是否已超时并且是超时后无法进行办理的情况
		if( frp.getFlowPrcs()!=null && frp.getFlowPrcs().getTimeoutHandable()==0 && frp.getTimeoutFlag()==1){
			throw new TeeOperationException("该流程已超时，无法进行委托操作");
		}
		
		TeePerson delegator = personDao.load(delegateTo);
		
		
		//步骤实例复制
		TeeFlowRunPrcs newFrp = new TeeFlowRunPrcs();
		BeanUtils.copyProperties(frp, newFrp);
		
		frp.setEndTime(Calendar.getInstance());
		frp.setEndTimeStamp(Calendar.getInstance().getTimeInMillis());
		
		newFrp.setCreateTime(Calendar.getInstance());
		newFrp.setCreateTimeStamp(Calendar.getInstance().getTimeInMillis());
		newFrp.setBeginTime(null);
		newFrp.setBeginTimeStamp(0);
		newFrp.setEndTime(null);
		newFrp.setEndTimeStamp(0);
		newFrp.setFlag(1);
		newFrp.setTimeoutFlag(0);
		
		newFrp.setOtherUser(frp.getPrcsUser());
		newFrp.setPrcsUser(delegator);
		if(frp.getTopFlag()==1){
			frp.setTopFlag(0);
		}
		
		frp.setFlag(3);
		flowRunPrcsDao.update(frp);
		flowRunPrcsDao.save(newFrp);
		
		
		//记录流程日志
		TeeFlowRunLog log = TeeFlowRunLog.getInstance();
		log.setRunName(frp.getFlowRun().getRunName());
		log.setRunId(frp.getFlowRun().getRunId());
		log.setContent("手动委托此流程，委托人：["+frp.getPrcsUser().getUserName()+"]  被委托人：["+delegator.getUserName()+"]");
		log.setType(5);
		
		flowRunLogDao.save(log);
		
		
		//发送短消息
		Map params = new HashMap();
		params.put("content", "您有一个新的委托流程需要待办，委托人：["+frp.getPrcsUser().getUserName()+"]，流程标题："+frp.getFlowRun().getRunName());
		params.put("userListIds", delegator.getUuid()+"");
		params.put("moduleNo", "006");
		params.put("remindUrl", "/system/core/workflow/flowrun/prcs/index.jsp?" +
				"runId="+newFrp.getFlowRun().getRunId()+
				"&flowId="+newFrp.getFlowRun().getFlowType().getSid()+
				"&frpSid="+newFrp.getSid());
		//手机端事务提醒
		params.put("remindUrl1", "/system/mobile/phone/workflow/prcs/form.jsp?" +
				"runId="+newFrp.getFlowRun().getRunId()+
				"&flowId="+newFrp.getFlowRun().getFlowType().getSid()+
				"&frpSid="+newFrp.getSid());
		smsManager.sendSms(params, null);
		
		
		return frp;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeWorkflowManageServiceInterface#takebackDelegate(int)
	 */
	@Override
	public void takebackDelegate(int frpSid){
		//判断当前步骤id下，是否存在没有被接收过的委托工作，并取出相关的frpSid集合
		List noReceivedDelegate = flowRunPrcsDao.getNoReceivedDelegatedWorks(frpSid);
		
		//如果不存在未接受的代理工作，则抛出异常
		if(noReceivedDelegate.size()==0){
			throw new TeeOperationException("该委托工作已被办理人接收，无法进行收回！");
		}
		
		//删除这些工作
		for(int i=0;i<noReceivedDelegate.size();i++){
			Object obj[] = (Object[]) noReceivedDelegate.get(i);
			flowRunPrcsDao.delete(TeeStringUtil.getInteger(obj[0], 0));
		}
		
		//然后更新当前工作为未办理状态，并且设置为主办
		flowRunPrcsDao.updateFlowRunPrcsPrcsFlagAndTopFlag2NoHandled(frpSid);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeWorkflowManageServiceInterface#handover(java.lang.String, int, int, java.util.Calendar, java.util.Calendar, int, int, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public void handover(String flowIds,int fromUser,int toUser,Calendar beginTime,Calendar endTime,int beginRunId,int endRunId,TeePerson loginUser){
		//先将目标工作查出来，更新FlowRun表数据，将流程发起人改为移交对象
		TeePerson fromPerson = personDao.get(fromUser);
		TeePerson toPerson = personDao.get(toUser);
		
		String flowRunHql = "select fr.runId from TeeFlowRun fr where fr.flowType.sid in ("+flowIds+") and fr.beginPerson.uuid="+fromUser;
		List params = new ArrayList();
		
		if(beginTime!=null){
			flowRunHql+=" and fr.beginTime>=?";
			params.add(beginTime);
		}
		
		if(endTime!=null){
			flowRunHql+=" and fr.beginTime<=?";
			params.add(endTime);
		}
		
		if(beginRunId!=0){
			flowRunHql+=" and fr.runId>="+beginRunId;
		}
		
		if(endRunId!=0){
			flowRunHql+=" and fr.runId<="+endRunId;
		}
		
		List<Integer> flowRunIds = simpleDaoSupport.find(flowRunHql, params.toArray());
		TeeFlowRunLog flowRunLog = null;
		//插入流程事件
		for(int runId:flowRunIds){
			flowRunLog = new TeeFlowRunLog();
			flowRunLog.setFlowId(0);
			flowRunLog.setFlowName("");
			flowRunLog.setIp("");
			flowRunLog.setPrcsName("");
			flowRunLog.setRunId(runId);
			flowRunLog.setTime(Calendar.getInstance());
			flowRunLog.setType(11);
			flowRunLog.setContent("工作移交："+fromPerson.getUserName()+"&nbsp;&nbsp;的工作移交给&nbsp;&nbsp;"+toPerson.getUserName());
			flowRunLog.setUserId(loginUser.getUuid());
			flowRunLog.setUserName(loginUser.getUserName());
			simpleDaoSupport.save(flowRunLog);
		}
		
		
		params.clear();
		
		//更新流程发起人
		String updateFlowRunHql = "update TeeFlowRun fr set fr.beginPerson.uuid="+toUser+" where fr.flowType.sid in ("+flowIds+") and fr.beginPerson.uuid="+fromUser;
		if(beginTime!=null){
			updateFlowRunHql+=" and fr.beginTime>=?";
			params.add(beginTime);
		}
		
		if(endTime!=null){
			updateFlowRunHql+=" and fr.beginTime<=?";
			params.add(endTime);
		}
		
		if(beginRunId!=0){
			updateFlowRunHql+=" and fr.runId>="+beginRunId;
		}
		
		if(endRunId!=0){
			updateFlowRunHql+=" and fr.runId<="+endRunId;
		}
		simpleDaoSupport.executeUpdate(updateFlowRunHql, params.toArray());
		
		params.clear();
		
		//更新流程步骤实例
		String updateFrpHql = "update TeeFlowRunPrcs frp set frp.prcsUser.uuid="+toUser+" where frp.flowType.sid in ("+flowIds+") and frp.prcsUser.uuid="+fromUser;
		if(beginTime!=null){
			updateFrpHql+=" and frp.createTime>=?";
			params.add(beginTime);
		}
		
		if(endTime!=null){
			updateFrpHql+=" and frp.createTime<=?";
			params.add(endTime);
		}
		
		if(beginRunId!=0){
			updateFrpHql+=" and frp.flowRun.runId>="+beginRunId;
		}
		
		if(endRunId!=0){
			updateFrpHql+=" and frp.flowRun.runId<="+endRunId;
		}
		
		simpleDaoSupport.executeUpdate(updateFrpHql, params.toArray());
	}
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeWorkflowManageServiceInterface#takebackWorks(int)
	 */
	@Override
	public void takebackWorks(int frpSid){
		boolean hasTakebackPriv = flowRunPrcsDao.checkCurrentWorkTakebackPriv(frpSid);
		if(!hasTakebackPriv){
			throw new TeeOperationException("该工作已被下一个办理人接收，不能收回本工作");
		}
		
		flowRunPrcsDao.deleteNextPrcs(frpSid);
		
		flowRunPrcsDao.updateFlowRunPrcsPrcsFlag2NoHandled(frpSid);
		
	}
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeWorkflowManageServiceInterface#getDelegatedUserFilters(int, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public List<Integer> getDelegatedUserFilters(int frpSid,TeePerson loginPerson){
		TeeFlowRunPrcs frp = flowRunPrcsDao.get(frpSid);
		TeeFlowRun flowRun = frp.getFlowRun();
		TeeFlowProcess fp = frp.getFlowPrcs();
		TeeFlowType ft = frp.getFlowRun().getFlowType();
		
		//tmp中存在一个元素0，说明可以选择所有人
		List<Integer> tmp = new ArrayList<Integer>();
		tmp.add(0);
		if(ft.getDelegate()==0){
			throw new TeeOperationException("该流程禁止委托");
		}else if(ft.getDelegate()==1){//委托当前步骤经办人
			return flowRunPrcsDao.getCurrentPrcsUsersUuid(flowRun.getRunId(), frp.getPrcsId(), fp==null?0:fp.getSid());
		}else if(ft.getDelegate()==2){//允许委托，自由委托，委托给谁都行
			return tmp;
		}else if(ft.getDelegate()==3 && ft.getType()==1){//按步骤办理权限委托
			Map map = fixedFlowDataLoader.getFreeFlowFilterSelectPersonInfo(fp, frp, loginPerson);
			int filterPersonIds[] = TeeStringUtil.parseIntegerArray(map.get("filterPersonIds"));
			for(int t : filterPersonIds){
				tmp.add(t);
			}
			tmp.remove(0);
			return tmp;
		}else{
			return tmp;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeWorkflowManageServiceInterface#getDelegateHandlerData(int, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public Map getDelegateHandlerData(int frpSid,TeePerson loginPerson){
		TeeFlowRunPrcs frp = flowRunPrcsDao.get(frpSid);
		Map datas = new HashMap();
//		List<TeeFlowRunPrcsModel> prcsList = flowRunPrcsService.getFlowRunPrcsModelList(frp.getFlowRun().getRunId());
//		datas.put("prcsInfo", prcsList);
		List<Integer> userFilters = getDelegatedUserFilters(frpSid, loginPerson);
		datas.put("userFilters", userFilters);
		TeeFlowRunPrcsModel prcsModel = new TeeFlowRunPrcsModel();
		BeanUtils.copyProperties(frp, prcsModel);
		prcsModel.setPrcsUserName(frp.getPrcsUser().getUserName());
		prcsModel.setRunName(frp.getFlowRun().getRunName());
		prcsModel.setRunId(frp.getFlowRun().getRunId());
		prcsModel.setPrcsName(frp.getFlowPrcs()==null?frp.getPrcsId()+"":frp.getFlowPrcs().getPrcsName());
		datas.put("prcsModel", prcsModel);
		return datas;
	}

	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeWorkflowManageServiceInterface#delRunBatch(java.lang.String)
	 */
	@Override
	public TeeJson delRunBatch(String runIds) {
		TeeJson json=new TeeJson();
		String[]runIdArray=runIds.split(",");
		int runId=0;
		TeeFlowRun fr=null;
		TeeFlowRunLog log=null;
		for (String str : runIdArray) {
			runId=TeeStringUtil.getInteger(str, 0);
			if(runId>0){
				fr = flowRunDao.get(runId);
				fr.setDelFlag(1);
				flowRunDao.update(fr);
				
				//记录流程日志
				log = TeeFlowRunLog.getInstance();
				log.setRunName(fr.getRunName());
				log.setRunId(fr.getRunId());
				log.setContent("删除此流程");
				log.setType(8);
				
				flowRunLogDao.save(log);
			}

		}
		json.setRtState(true);
		json.setRtMsg("删除成功！");
		return json;
	}
	
}
