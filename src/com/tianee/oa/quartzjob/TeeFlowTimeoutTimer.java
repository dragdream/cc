package com.tianee.oa.quartzjob;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.service.TeeSmsSender;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;

/**
 * 流程超时监测器
 * @author kakalion
 *
 */
@Service
public class TeeFlowTimeoutTimer extends TeeBaseService{
	@Autowired
	TeeSimpleDaoSupport simpleDaoSupport;
	@Autowired
	TeeSmsSender smsSender;
	
	public void doTimmer(){
		if(TeeSysProps.getProps()==null){
			return;
		}
		
		doType1Timeout();
		doType2Timeout();
		doType1();
		doType2();
		
	}
	
	//处理“本步骤办理人接收后计算”
	private void doType1(){
		//当前时间
		Date curDate = new Date();
		String hql1 = "select frp.sid,frp.beginTime,frp.ignoreType,frp.timeout from TeeFlowRunPrcs frp where frp.flag=2 and frp.timeout!=0 and frp.beginTime is not null and frp.timeoutType=1 and frp.timeoutFlag=0 and "+curDate.getTime()+"-frp.beginTimeStamp>=frp.timeout*60*60*1000";
		List<Object[]> frpList1 = simpleDaoSupport.find(hql1, null);
		int sid = 0;
		Calendar beginTime = null;
		Calendar curTime = null;
		int ignoreType =0;
		int timeout = 0;
//		int timeoutAlarm = 0;
		for(Object tmp[]:frpList1){
			sid = TeeStringUtil.getInteger(tmp[0], 0);
			beginTime = (Calendar) tmp[1];
			curTime = Calendar.getInstance();
			ignoreType = TeeStringUtil.getInteger(tmp[2], 0);
			timeout = TeeStringUtil.getInteger(tmp[3], 0);
//			timeoutAlarm = TeeStringUtil.getInteger(tmp[4], 0);
			
			if(ignoreType==0){//不排除双休日，则直接更新该状态
				simpleDaoSupport.executeUpdate("update TeeFlowRunPrcs frp set frp.timeoutFlag=1 where sid=?", new Object[]{sid});
			}else{//排除双休日
				long hours = TeeDateUtil.getMillisecondsRanges(beginTime, curTime, new Object[]{TeeDateUtil.FILTER_SATURDAY,TeeDateUtil.FILTER_SUNDAY})/(1000*60*60);
				if(hours >=timeout){
					simpleDaoSupport.executeUpdate("update TeeFlowRunPrcs frp set frp.timeoutFlag=1 where sid=?", new Object[]{sid});
				}
			}
		}
	}
	
	//处理“转交开始后计时”
	private void doType2(){
		//当前时间
		Date date = new Date();
		String hql1 = "select frp.sid,frp.createTime,frp.ignoreType,frp.timeout from TeeFlowRunPrcs frp where frp.flag in (1,2) and frp.timeout!=0 and frp.createTime is not null and frp.timeoutType=2 and frp.timeoutFlag=0 and "+date.getTime()+"-frp.createTimeStamp>=frp.timeout*60*60*1000";
		List<Object[]> frpList1 = simpleDaoSupport.find(hql1, null);
		int sid =0;
		Calendar createTime = null;
		Calendar curTime = null;
		int ignoreType = 0;
		int timeout = 0;
		for(Object tmp[]:frpList1){
			sid = TeeStringUtil.getInteger(tmp[0], 0);
			createTime = (Calendar) tmp[1];
			curTime = Calendar.getInstance();
			ignoreType = TeeStringUtil.getInteger(tmp[2], 0);
			timeout = TeeStringUtil.getInteger(tmp[3], 0);
			
			if(ignoreType==0){//不排除双休日，则直接更新该状态
				simpleDaoSupport.executeUpdate("update TeeFlowRunPrcs frp set frp.timeoutFlag=1 where sid=?", new Object[]{sid});
			}else{//排除双休日
				long hours = TeeDateUtil.getMillisecondsRanges(createTime, curTime, new Object[]{TeeDateUtil.FILTER_SATURDAY,TeeDateUtil.FILTER_SUNDAY})/(1000*60*60);
				if(hours >=timeout){
					simpleDaoSupport.executeUpdate("update TeeFlowRunPrcs frp set frp.timeoutFlag=1 where sid=?", new Object[]{sid});
				}
			}
		}
	}
	
	//处理“本步骤办理人接收后计算”的超时预警
	private void doType1Timeout(){
		//当前时间
		Date curDate = new Date();
		String hql1 = "select frp.sid,frp.beginTime,frp.ignoreType,frp.timeout,frp.timeoutAlarm,frp.flowRun.runName,frp.flowRun.runId,frp.flowRun.flowType.sid,frp.prcsUser.uuid from TeeFlowRunPrcs frp where frp.flag=2 and frp.timeout!=0 and frp.timeoutAlarm!=0 and frp.timeoutAlarmFlag=0 and frp.beginTime is not null and frp.timeoutType=1 and frp.timeoutFlag=0 and "+curDate.getTime()+"-frp.beginTimeStamp>=frp.timeout*60*60*1000-frp.timeoutAlarm*60*1000";
		List<Object[]> frpList1 = simpleDaoSupport.find(hql1, null);
		Map requestData = new HashMap();
		requestData.put("moduleNo", "006");
		int sid = 0;
		Calendar beginTime = null;
		Calendar curTime = null;
		int ignoreType = 0;
		int timeout = 0;
		int timeoutAlarm = 0;
		String runName = null;
		int runId = 0;
		int flowId = 0;
		int userId = 0;
		
		for(Object tmp[]:frpList1){
			sid = TeeStringUtil.getInteger(tmp[0], 0);
			beginTime = (Calendar) tmp[1];
			curTime = Calendar.getInstance();
			ignoreType = TeeStringUtil.getInteger(tmp[2], 0);
			timeout = TeeStringUtil.getInteger(tmp[3], 0);
			timeoutAlarm = TeeStringUtil.getInteger(tmp[4], 0);
			runName = TeeStringUtil.getString(tmp[5]);
			runId = TeeStringUtil.getInteger(tmp[6], 0);
			flowId = TeeStringUtil.getInteger(tmp[7], 0);
			userId = TeeStringUtil.getInteger(tmp[8], 0);
			
			requestData.put("content", "您有待办工作 "+runName+" 将要超时，请及时进行办理。");
			requestData.put("userListIds", userId+"");
			requestData.put("remindUrl", "/system/core/workflow/flowrun/prcs/index.jsp?" +
					"runId="+runId+
					"&flowId="+flowId+
					"&frpSid="+sid);
			
			if(ignoreType==0){//不排除双休日，则直接更新该状态
				simpleDaoSupport.executeUpdate("update TeeFlowRunPrcs frp set frp.timeoutAlarmFlag=1 where sid=?", new Object[]{sid});
				smsSender.sendSms(requestData, null);
			}else{//排除双休日
				long hours = TeeDateUtil.getMillisecondsRanges(beginTime, curTime, new Object[]{TeeDateUtil.FILTER_SATURDAY,TeeDateUtil.FILTER_SUNDAY})/(1000*60*60);
				if(hours <=timeout){
					simpleDaoSupport.executeUpdate("update TeeFlowRunPrcs frp set frp.timeoutAlarmFlag=1 where sid=?", new Object[]{sid});
					smsSender.sendSms(requestData, null);
				}
			}
		}
	}

	//处理“本步骤办理人接收后计算”的超时预警
	private void doType2Timeout(){
		//当前时间
		Date date = new Date();
		String hql1 = "select frp.sid,frp.beginTime,frp.ignoreType,frp.timeout,frp.timeoutAlarm,frp.flowRun.runName,frp.flowRun.runId,frp.flowRun.flowType.sid,frp.prcsUser.uuid from TeeFlowRunPrcs frp where frp.flag in (1,2) and frp.timeout!=0 and frp.timeoutAlarm!=0 and frp.timeoutAlarmFlag=0 and frp.createTime is not null and frp.timeoutType=2 and frp.timeoutFlag=0 and "+date.getTime()+"-frp.createTimeStamp>=frp.timeout*60*60*1000-frp.timeoutAlarm*60*1000";
		List<Object[]> frpList1 = simpleDaoSupport.find(hql1, null);
		Map requestData = new HashMap();
		requestData.put("moduleNo", "006");
		int sid = 0;
		Calendar beginTime = null;
		Calendar curTime = null;
		int ignoreType = 0;
		int timeout = 0;
		int timeoutAlarm = 0;
		String runName = null;
		int runId = 0;
		int flowId = 0;
		int userId = 0;
		for(Object tmp[]:frpList1){
			sid = TeeStringUtil.getInteger(tmp[0], 0);
			beginTime = (Calendar) tmp[1];
			curTime = Calendar.getInstance();
			ignoreType = TeeStringUtil.getInteger(tmp[2], 0);
			timeout = TeeStringUtil.getInteger(tmp[3], 0);
			timeoutAlarm = TeeStringUtil.getInteger(tmp[4], 0);
			runName = TeeStringUtil.getString(tmp[5]);
			runId = TeeStringUtil.getInteger(tmp[6], 0);
			flowId = TeeStringUtil.getInteger(tmp[7], 0);
			userId = TeeStringUtil.getInteger(tmp[8], 0);
			
			requestData.put("content", "您有待办工作 "+runName+" 将要超时，请及时进行办理。");
			requestData.put("userListIds", userId+"");
			requestData.put("remindUrl", "/system/core/workflow/flowrun/prcs/index.jsp?" +
					"runId="+runId+
					"&flowId="+flowId+
					"&frpSid="+sid);
			
			if(ignoreType==0){//不排除双休日，则直接更新该状态
				simpleDaoSupport.executeUpdate("update TeeFlowRunPrcs frp set frp.timeoutAlarmFlag=1 where sid=?", new Object[]{sid});
				smsSender.sendSms(requestData, null);
			}else{//排除双休日
				long hours = TeeDateUtil.getMillisecondsRanges(beginTime, curTime, new Object[]{TeeDateUtil.FILTER_SATURDAY,TeeDateUtil.FILTER_SUNDAY})/(1000*60*60);
				if(hours <=timeout){
					simpleDaoSupport.executeUpdate("update TeeFlowRunPrcs frp set frp.timeoutAlarmFlag=1 where sid=?", new Object[]{sid});
					smsSender.sendSms(requestData, null);
				}
			}
		}
	}

	public void setSimpleDaoSupport(TeeSimpleDaoSupport simpleDaoSupport) {
		this.simpleDaoSupport = simpleDaoSupport;
	}	
}
