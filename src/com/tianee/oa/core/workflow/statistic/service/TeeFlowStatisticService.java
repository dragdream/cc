package com.tianee.oa.core.workflow.statistic.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunDoc;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeFlowStatisticService extends TeeBaseService implements TeeFlowStatisticServiceInterface{
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.statistic.service.TeeFlowStatisticServiceInterface#timeoutStatistic(java.util.Map)
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Map> timeoutStatistic(Map requestData){
		List<Map> list = null;
		Calendar beginTime = TeeDateUtil.parseCalendar("yyyy-MM hh:mm:ss", TeeStringUtil.getString(requestData.get("beginTime")+" 00:00:00"));
		Calendar endTime = TeeDateUtil.parseCalendar("yyyy-MM hh:mm:ss", TeeStringUtil.getString(requestData.get("endTime")+" 23:59:59"));
		int max = endTime.getActualMaximum(Calendar.DATE);
		endTime.set(Calendar.DATE, max);
		int flowId = TeeStringUtil.getInteger(requestData.get("flowId"), 0);
		int target = TeeStringUtil.getInteger(requestData.get("target"), 0);
		String userIds = TeeStringUtil.getString(requestData.get("userIds"));
		String deptIds = TeeStringUtil.getString(requestData.get("deptIds"));
		
		if(target==1){//人员
			
			list = simpleDaoSupport.getMaps("select userName as key,uuid as uuid from TeePerson where "+TeeDbUtility.IN("uuid", userIds)+" ", null);
			for(Map userInfo:list){
				userInfo.put("value", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=1"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId), 
						new Object[]{TeeStringUtil.getInteger(userInfo.get("uuid"), 0),beginTime,endTime}));
			}
			
		}else if(target==2){//部门
			
			list = simpleDaoSupport.getMaps("select deptName as key,uuid as uuid from TeeDepartment where "+TeeDbUtility.IN("uuid", deptIds)+" ", null);
			for(Map deptInfo:list){
				deptInfo.put("value", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.dept.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=1"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId), 
						new Object[]{TeeStringUtil.getInteger(deptInfo.get("uuid"), 0),beginTime,endTime}));
			}
			
		}else if(target==3){//月份
			list = new ArrayList();
			for(;beginTime.compareTo(endTime)<=0;beginTime.add(Calendar.MONTH, 1)){
				Map data = new HashMap();
				data.put("key", beginTime.get(Calendar.YEAR)+"-"+(beginTime.get(Calendar.MONTH)+1));
				Calendar t1 = (Calendar) beginTime.clone();
				Calendar t2 = (Calendar) beginTime.clone();
				t2.set(Calendar.HOUR_OF_DAY, 23);
				t2.set(Calendar.MINUTE, 59);
				t2.set(Calendar.SECOND, 59);
				t2.set(Calendar.DATE, t2.getActualMaximum(Calendar.DATE));
				
				data.put("value", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=1 "+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId), new Object[]{t1,t2}));
				list.add(data);
			}
			
		}
		
		return list;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.statistic.service.TeeFlowStatisticServiceInterface#handleStatistic(java.util.Map)
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Map> handleStatistic(Map requestData){
		List<Map> list = null;
		Calendar beginTime = TeeDateUtil.parseCalendar("yyyy-MM hh:mm:ss", TeeStringUtil.getString(requestData.get("beginTime")+" 00:00:00"));
		Calendar endTime = TeeDateUtil.parseCalendar("yyyy-MM hh:mm:ss", TeeStringUtil.getString(requestData.get("endTime")+" 23:59:59"));
		int max = endTime.getActualMaximum(Calendar.DATE);
		endTime.set(Calendar.DATE, max);
		int flowId = TeeStringUtil.getInteger(requestData.get("flowId"), 0);
		int target = TeeStringUtil.getInteger(requestData.get("target"), 0);
		String userIds = TeeStringUtil.getString(requestData.get("userIds"));
		String deptIds = TeeStringUtil.getString(requestData.get("deptIds"));
		
		
		if(target==1){//人员
			
			list = simpleDaoSupport.getMaps("select userName as key,uuid as uuid from TeePerson where "+TeeDbUtility.IN("uuid", userIds)+" ", null);
			for(Map userInfo:list){
				//拟稿数量
				userInfo.put("ngs", simpleDaoSupport.count("select count(*) from TeeFlowRun fr where fr.delFlag=0 and fr.beginPerson.uuid=? and fr.beginTime>=? and fr.beginTime<=? "+(flowId==0?"":" and fr.flowType.sid="+flowId), 
						new Object[]{TeeStringUtil.getInteger(userInfo.get("uuid"), 0),beginTime,endTime}));
				
				//办理总数
				userInfo.put("blzs", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=?  and frp.topFlag=1"+(flowId==0?"":" and frp.flowType.sid="+flowId), 
						new Object[]{TeeStringUtil.getInteger(userInfo.get("uuid"), 0),beginTime,endTime}));
				
				//按时办结数
				userInfo.put("asbjs", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=0 and frp.topFlag=1 and frp.flag in (3,4)"+(flowId==0?"":" and frp.flowType.sid="+flowId), 
						new Object[]{TeeStringUtil.getInteger(userInfo.get("uuid"), 0),beginTime,endTime}));
				
				//超时办结数
				userInfo.put("csbjs", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=1 and frp.topFlag=1 and frp.flag in (3,4)"+(flowId==0?"":" and frp.flowType.sid="+flowId), 
						new Object[]{TeeStringUtil.getInteger(userInfo.get("uuid"), 0),beginTime,endTime}));
				
				//未办结数
				userInfo.put("wbjs", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.topFlag=1 and (frp.flag in (1,2) or frp.endTime is null)"+(flowId==0?"":" and frp.flowType.sid="+flowId), 
						new Object[]{TeeStringUtil.getInteger(userInfo.get("uuid"), 0),beginTime,endTime}));
				
				//经办总数
				userInfo.put("jbzs", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=1 and frp.topFlag=0 and (frp.flag in (1,2) or frp.endTime is null)"+(flowId==0?"":" and frp.flowType.sid="+flowId), 
						new Object[]{TeeStringUtil.getInteger(userInfo.get("uuid"), 0),beginTime,endTime}));
				
				//已经办数
				userInfo.put("yjbs", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=1 and frp.topFlag=0 and frp.flag in (3,4)"+(flowId==0?"":" and frp.flowType.sid="+flowId), 
						new Object[]{TeeStringUtil.getInteger(userInfo.get("uuid"), 0),beginTime,endTime}));
				
				//待经办数
				userInfo.put("djbs", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=1 and frp.topFlag=0 and frp.flag in (1,2)"+(flowId==0?"":" and frp.flowType.sid="+flowId), 
						new Object[]{TeeStringUtil.getInteger(userInfo.get("uuid"), 0),beginTime,endTime}));
			}
			
		}else if(target==2){//部门
			
			list = simpleDaoSupport.getMaps("select deptName as key,uuid as uuid from TeeDepartment where "+TeeDbUtility.IN("uuid", deptIds)+" ", null);
			for(Map deptInfo:list){
				//拟稿数量
				deptInfo.put("ngs", simpleDaoSupport.count("select count(*) from TeeFlowRun fr where fr.delFlag=0 and fr.beginPerson.dept.uuid=? and fr.beginTime>=? and fr.beginTime<=? "+(flowId==0?"":" and fr.flowType.sid="+flowId), 
						new Object[]{TeeStringUtil.getInteger(deptInfo.get("uuid"), 0),beginTime,endTime}));
				
				//办理总数
				deptInfo.put("blzs", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where  frp.flowRun.delFlag=0 and frp.prcsUser.dept.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=?  and frp.topFlag=1"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId), 
						new Object[]{TeeStringUtil.getInteger(deptInfo.get("uuid"), 0),beginTime,endTime}));
				
				//按时办结数
				deptInfo.put("asbjs", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.dept.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=0 and frp.topFlag=1 and frp.flag in (3,4)"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId), 
						new Object[]{TeeStringUtil.getInteger(deptInfo.get("uuid"), 0),beginTime,endTime}));
				
				//超时办结数
				deptInfo.put("csbjs", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.dept.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=1 and frp.topFlag=1 and frp.flag in (3,4)"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId), 
						new Object[]{TeeStringUtil.getInteger(deptInfo.get("uuid"), 0),beginTime,endTime}));
				
				//未办结数
				deptInfo.put("wbjs", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.dept.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.topFlag=1 and (frp.flag in (1,2) or frp.endTime is null)"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId), 
						new Object[]{TeeStringUtil.getInteger(deptInfo.get("uuid"), 0),beginTime,endTime}));
				
				//经办总数
				deptInfo.put("jbzs", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.dept.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=1 and frp.topFlag=0 and (frp.flag in (1,2) or frp.endTime is null)"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId), 
						new Object[]{TeeStringUtil.getInteger(deptInfo.get("uuid"), 0),beginTime,endTime}));
				
				//已经办数
				deptInfo.put("yjbs", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.dept.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=1 and frp.topFlag=0 and frp.flag in (3,4)"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId), 
						new Object[]{TeeStringUtil.getInteger(deptInfo.get("uuid"), 0),beginTime,endTime}));
				
				//待经办数
				deptInfo.put("djbs", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.dept.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=1 and frp.topFlag=0 and frp.flag in (1,2)"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId), 
						new Object[]{TeeStringUtil.getInteger(deptInfo.get("uuid"), 0),beginTime,endTime}));
			}
			
		}else if(target==3){//月份
			list = new ArrayList();
			for(;beginTime.compareTo(endTime)<=0;beginTime.add(Calendar.MONTH, 1)){
				Map data = new HashMap();
				data.put("key", beginTime.get(Calendar.YEAR)+"-"+(beginTime.get(Calendar.MONTH)+1));
				Calendar t1 = (Calendar) beginTime.clone();
				Calendar t2 = (Calendar) beginTime.clone();
				t2.set(Calendar.HOUR_OF_DAY, 23);
				t2.set(Calendar.MINUTE, 59);
				t2.set(Calendar.SECOND, 59);
				t2.set(Calendar.DATE, t2.getActualMaximum(Calendar.DATE));
				
				//拟稿数量
				data.put("ngs", simpleDaoSupport.count("select count(*) from TeeFlowRun fr where fr.delFlag=0 and fr.beginTime>=? and fr.beginTime<=? "+(flowId==0?"":" and fr.flowType.sid="+flowId), 
						new Object[]{t1,t2}));
				
				//办理总数
				data.put("blzs", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and  frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=?  and frp.topFlag=1"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId), 
						new Object[]{t1,t2}));
				
				//按时办结数
				data.put("asbjs", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and  frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=0 and frp.topFlag=1 and frp.flag in (3,4)"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId), 
						new Object[]{t1,t2}));
				
				//超时办结数
				data.put("csbjs", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and  frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=1 and frp.topFlag=1 and frp.flag in (3,4)"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId), 
						new Object[]{t1,t2}));
				
				//未办结数
				data.put("wbjs", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and  frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and  frp.topFlag=1 and (frp.flag in (1,2) or frp.endTime is null)"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId), 
						new Object[]{t1,t2}));
				
				//经办总数
				data.put("jbzs", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and  frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=1 and frp.topFlag=0 and (frp.flag in (1,2) or frp.endTime is null)"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId), 
						new Object[]{t1,t2}));
				
				//已经办数
				data.put("yjbs", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and  frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=1 and frp.topFlag=0 and frp.flag in (3,4)"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId), 
						new Object[]{t1,t2}));
				
				//待经办数
				data.put("djbs", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and  frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=1 and frp.topFlag=0 and frp.flag in (1,2)"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId), 
						new Object[]{t1,t2}));
				
				list.add(data);
			}
			
		}
		
		return list;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.statistic.service.TeeFlowStatisticServiceInterface#handle0Statistic(java.util.Map)
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Map> handle0Statistic(Map requestData){
		List<Map> list = null;
		Calendar beginTime = TeeDateUtil.parseCalendar("yyyy-MM hh:mm:ss", TeeStringUtil.getString(requestData.get("beginTime")+" 00:00:00"));
		Calendar endTime = TeeDateUtil.parseCalendar("yyyy-MM hh:mm:ss", TeeStringUtil.getString(requestData.get("endTime")+" 23:59:59"));
		int max = endTime.getActualMaximum(Calendar.DATE);
		endTime.set(Calendar.DATE, max);
		int flowId = TeeStringUtil.getInteger(requestData.get("flowId"), 0);
		int target = TeeStringUtil.getInteger(requestData.get("target"), 0);
		String userIds = TeeStringUtil.getString(requestData.get("userIds"));
		String deptIds = TeeStringUtil.getString(requestData.get("deptIds"));
		
		if(target==1){//人员
			
			list = simpleDaoSupport.getMaps("select userName as key,uuid as uuid from TeePerson where "+TeeDbUtility.IN("uuid", userIds)+" ", null);
			for(Map userInfo:list){
				
				//待办数量
				userInfo.put("dbsl", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? "+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId), 
						new Object[]{TeeStringUtil.getInteger(userInfo.get("uuid"), 0),beginTime,endTime}));
				
				//48小时内办结
				userInfo.put("bj48", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.flag in (3,4) and endTimeStamp-createTimeStamp<="+(48*60*60*1000)+""+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId), 
						new Object[]{TeeStringUtil.getInteger(userInfo.get("uuid"), 0),beginTime,endTime}));
				
			}
			
		}else if(target==2){//部门
			
			list = simpleDaoSupport.getMaps("select deptName as key,uuid as uuid from TeeDepartment where "+TeeDbUtility.IN("uuid", deptIds)+" ", null);
			for(Map deptInfo:list){
				//待办数量
				deptInfo.put("dbsl", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.dept.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? "+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId), 
						new Object[]{TeeStringUtil.getInteger(deptInfo.get("uuid"), 0),beginTime,endTime}));
				
				//48小时内办结
				deptInfo.put("bj48", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.dept.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.flag in (3,4) and endTimeStamp-createTimeStamp<="+(48*60*60*1000)+""+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId), 
						new Object[]{TeeStringUtil.getInteger(deptInfo.get("uuid"), 0),beginTime,endTime}));
			}
			
		}else if(target==3){//月份
			list = new ArrayList();
			for(;beginTime.compareTo(endTime)<=0;beginTime.add(Calendar.MONTH, 1)){
				Map data = new HashMap();
				data.put("key", beginTime.get(Calendar.YEAR)+"-"+(beginTime.get(Calendar.MONTH)+1));
				Calendar t1 = (Calendar) beginTime.clone();
				Calendar t2 = (Calendar) beginTime.clone();
				t2.set(Calendar.HOUR_OF_DAY, 23);
				t2.set(Calendar.MINUTE, 59);
				t2.set(Calendar.SECOND, 59);
				t2.set(Calendar.DATE, t2.getActualMaximum(Calendar.DATE));
				
				//待办数量
				data.put("dbsl", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and  frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? "+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId), 
						new Object[]{t1,t2}));
				
				//48小时内办结
				data.put("bj48", simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and  frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.flag in (3,4) and endTimeStamp-createTimeStamp<="+(48*60*60*1000)+""+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId), 
						new Object[]{t1,t2}));
				
				list.add(data);
			}
			
		}
		
		return list;
	}


	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.statistic.service.TeeFlowStatisticServiceInterface#getStatisticFlowData(java.util.Map, com.tianee.oa.core.org.bean.TeePerson, com.tianee.oa.webframe.httpModel.TeeDataGridModel)
	 */
	@Override
	public TeeEasyuiDataGridJson getStatisticFlowData(Map requestData,
			TeePerson loginUser, TeeDataGridModel dm) {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();

		List params = new ArrayList();
		//1=人员  2=部门  3=月份
		int target=TeeStringUtil.getInteger(requestData.get("target"),0);
		//ngs=拟稿数  blzs=办理总数  asbjs=按时办理数   csbjs=超时办理数   wbjs=未办理数   jbzs=经办总数
		//yjbs=已经办数  djbs=待经办数
		String type=TeeStringUtil.getString(requestData.get("type"));
		
		Calendar beginTime = TeeDateUtil.parseCalendar("yyyy-MM hh:mm:ss", TeeStringUtil.getString(requestData.get("beginTime")+" 00:00:00"));
		Calendar endTime = TeeDateUtil.parseCalendar("yyyy-MM hh:mm:ss", TeeStringUtil.getString(requestData.get("endTime")+" 23:59:59"));
		int max = endTime.getActualMaximum(Calendar.DATE);
		endTime.set(Calendar.DATE, max);
		int flowId=TeeStringUtil.getInteger(requestData.get("flowId"),0);
		
		int userId=TeeStringUtil.getInteger(requestData.get("userId"), 0);
		int deptId=TeeStringUtil.getInteger(requestData.get("deptId"), 0);
		String month=TeeStringUtil.getString(requestData.get("month"));
		
		int firstResult = (dm.getPage() - 1) * dm.getRows();
		int pageSize = dm.getRows();

		String hql="";
		List param=new ArrayList();
        if(target==1){//人员
        	if("ngs".equals(type)){//拟稿数
        		hql=" from TeeFlowRunPrcs frp where  frp.prcsId=1 and   frp.flowRun.delFlag=0 and frp.flowRun.beginPerson.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? "+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId) ;
        		param.add(userId);
        		param.add(beginTime);
        		param.add(endTime);
        	}else if("blzs".equals(type)){//办理总数
        		hql=" from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=?  and frp.topFlag=1"+(flowId==0?"":" and frp.flowType.sid="+flowId) ;
        		param.add(userId);
        		param.add(beginTime);
        		param.add(endTime);
        	}else if("asbjs".equals(type)){//按时办结数
        		hql=" from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=0 and frp.topFlag=1 and frp.flag in (3,4)"+(flowId==0?"":" and frp.flowType.sid="+flowId) ;
        		param.add(userId);
        		param.add(beginTime);
        		param.add(endTime);
        	}else if("csbjs".equals(type)){//超时办结数
        		hql=" from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=1 and frp.topFlag=1 and frp.flag in (3,4)"+(flowId==0?"":" and frp.flowType.sid="+flowId);
        		param.add(userId);
        		param.add(beginTime);
        		param.add(endTime);
        	}else if("wbjs".equals(type)){//未办结数
        		hql=" from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.topFlag=1 and (frp.flag in (1,2) or frp.endTime is null)"+(flowId==0?"":" and frp.flowType.sid="+flowId) ;
        		param.add(userId);
        		param.add(beginTime);
        		param.add(endTime);
        	}else if("jbzs".equals(type)){//经办总数
        		hql=" from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=1 and frp.topFlag=0 and (frp.flag in (1,2) or frp.endTime is null)"+(flowId==0?"":" and frp.flowType.sid="+flowId);
        		param.add(userId);
        		param.add(beginTime);
        		param.add(endTime);
        	}else if("yjbs".equals(type)){//已经办数
        	  	hql=" from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=1 and frp.topFlag=0 and frp.flag in (3,4)"+(flowId==0?"":" and frp.flowType.sid="+flowId);
        	  	param.add(userId);
        	  	param.add(beginTime);
        		param.add(endTime);
        	}else if("djbs".equals(type)){	//待经办数
        		hql=" from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=1 and frp.topFlag=0 and frp.flag in (1,2)"+(flowId==0?"":" and frp.flowType.sid="+flowId);
        		param.add(userId);
        		param.add(beginTime);
        		param.add(endTime);
        	}
		}else if(target==2){//部门
			if("ngs".equals(type)){//拟稿数
        		hql=" from TeeFlowRunPrcs frp where  frp.prcsId=1 and  frp.flowRun.delFlag=0 and frp.flowRun.beginPerson.dept.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? "+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId);
        		param.add(deptId);
        		param.add(beginTime);
        		param.add(endTime);
        	}else if("blzs".equals(type)){//办理总数
        		hql=" from TeeFlowRunPrcs frp where  frp.flowRun.delFlag=0 and frp.prcsUser.dept.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=?  and frp.topFlag=1"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId);
        		param.add(deptId);
        		param.add(beginTime);
        		param.add(endTime);
        	}else if("asbjs".equals(type)){//按时办结数
        		hql=" from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.dept.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=0 and frp.topFlag=1 and frp.flag in (3,4)"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId) ;
        		param.add(deptId);
        		param.add(beginTime);
        		param.add(endTime);
        	}else if("csbjs".equals(type)){//超时办结数
        		hql=" from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.dept.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=1 and frp.topFlag=1 and frp.flag in (3,4)"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId);
        		param.add(deptId);
        		param.add(beginTime);
        		param.add(endTime);
        	}else if("wbjs".equals(type)){//未办结数
        		hql=" from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.dept.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.topFlag=1 and (frp.flag in (1,2) or frp.endTime is null)"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId);
        		param.add(deptId);
        		param.add(beginTime);
        		param.add(endTime);
        	}else if("jbzs".equals(type)){//经办总数
        		hql=" from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.dept.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=1 and frp.topFlag=0 and (frp.flag in (1,2) or frp.endTime is null)"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId);
        		param.add(deptId);
        		param.add(beginTime);
        		param.add(endTime);
        	}else if("yjbs".equals(type)){//已经办数
        	  	hql=" from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.dept.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=1 and frp.topFlag=0 and frp.flag in (3,4)"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId);
        	  	param.add(deptId);
        		param.add(beginTime);
        		param.add(endTime);
        	}else if("djbs".equals(type)){	//待经办数
        		hql=" from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.dept.uuid=? and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=1 and frp.topFlag=0 and frp.flag in (1,2)"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId);
        		param.add(deptId);
        		param.add(beginTime);
        		param.add(endTime);
        	}
			
		}else if(target==3){//月份
				
			Calendar month1=TeeDateUtil.parseCalendar("yyyy-MM hh:mm:ss", (month+" 00:00:00"));
			Calendar t1 = (Calendar) month1.clone();
			Calendar t2 = (Calendar) month1.clone();
			t2.set(Calendar.HOUR_OF_DAY, 23);
			t2.set(Calendar.MINUTE, 59);
			t2.set(Calendar.SECOND, 59);
			t2.set(Calendar.DATE, t2.getActualMaximum(Calendar.DATE));
			
			
			if("ngs".equals(type)){//拟稿数
        		hql=" from TeeFlowRunPrcs frp where frp.prcsId=1 and  frp.flowRun.delFlag=0 and frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? "+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId);
        		param.add(t1);
        		param.add(t2);
        	}else if("blzs".equals(type)){//办理总数
        		hql=" from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and  frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=?  and frp.topFlag=1"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId);
        		param.add(t1);
        		param.add(t2);
        	}else if("asbjs".equals(type)){//按时办结数
        		hql=" from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and  frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=0 and frp.topFlag=1 and frp.flag in (3,4)"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId) ;
        		param.add(t1);
        		param.add(t2);
        	}else if("csbjs".equals(type)){//超时办结数
        		hql=" from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and  frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=1 and frp.topFlag=1 and frp.flag in (3,4)"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId);
        		param.add(t1);
        		param.add(t2);
        	}else if("wbjs".equals(type)){//未办结数
        		hql=" from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and  frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and  frp.topFlag=1 and (frp.flag in (1,2) or frp.endTime is null)"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId);
        		param.add(t1);
        		param.add(t2);
        	}else if("jbzs".equals(type)){//经办总数
        		hql=" from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and  frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=1 and frp.topFlag=0 and (frp.flag in (1,2) or frp.endTime is null)"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId);
        		param.add(t1);
        		param.add(t2);
        	}else if("yjbs".equals(type)){//已经办数
        	  	hql=" from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and  frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=1 and frp.topFlag=0 and frp.flag in (3,4)"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId);
        	  	param.add(t1);
        		param.add(t2);
        	}else if("djbs".equals(type)){	//待经办数
        		hql=" from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and  frp.flowRun.beginTime>=? and frp.flowRun.beginTime<=? and frp.timeoutFlag=1 and frp.topFlag=0 and frp.flag in (1,2)"+(flowId==0?"":" and frp.flowRun.flowType.sid="+flowId);
        		param.add(t1);
        		param.add(t2);
        	}
		}
		
		List<TeeFlowRunPrcs> list = simpleDaoSupport.pageFindByList(hql, firstResult, pageSize, param);
		long total = simpleDaoSupport.count("select count(*) " +hql,
				param.toArray());

		List<Map> maps = new ArrayList();
		for (TeeFlowRunPrcs frp : list) {
			Map map = new HashMap();
			map.put("runId", frp.getFlowRun().getRunId());
			map.put("runName", frp.getFlowRun().getRunName());
			map.put("flowName", frp.getFlowRun().getFlowType().getFlowName());
			map.put("createUserName",frp.getFlowRun().getBeginPerson().getUserName());
			if(frp.getFlowPrcs()!=null){
				map.put("step", frp.getFlowPrcs().getPrcsName());
			}else{
				map.put("step","第"+frp.getPrcsId()+"步");
			}
			
			maps.add(map);
		}

		json.setRows(maps);
		json.setTotal(total);

		return json;
	}
}
