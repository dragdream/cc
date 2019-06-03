package com.tianee.oa.core.workflow.flowrun.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.dam.service.TeeDamFilesService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunPrcsDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFlowRunPrcsModel;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
/**
 * 
 */
@Service
public class TeeFlowRunPrcsService extends TeeBaseService implements TeeFlowRunPrcsServiceInterface {

	@Autowired
	private TeeFlowRunPrcsDao flowRunPrcsDao;
	
	@Autowired
	private TeeSimpleDaoSupport simpleDao;
	
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#get(int)
	 */
	@Override
	public TeeFlowRunPrcs get(int sid){
		return flowRunPrcsDao.get(sid);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#delete(com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs)
	 */
	@Override
	public void delete(TeeFlowRunPrcs flowRunPrcs) {
		flowRunPrcsDao.delete(flowRunPrcs);
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#delete(int)
	 */
	@Override
	public void delete(int sid) {
		flowRunPrcsDao.delete(sid);
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#save(com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs)
	 */
	@Override
	public void save(TeeFlowRunPrcs flowRunPrcs) {
		flowRunPrcsDao.save(flowRunPrcs);
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#update(com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs)
	 */
	@Override
	public void update(TeeFlowRunPrcs flowRunPrcs) {
		flowRunPrcsDao.update(flowRunPrcs);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#findByComplex(int, int, int, int, int)
	 */
	@Override
	public TeeFlowRunPrcs findByComplex(int runId,int flowId,int prcsId,int flowPrcs,int userUuid){
		return flowRunPrcsDao.findByComplex(runId, flowId, prcsId, flowPrcs, userUuid);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#findByComplex(int, int, int, int)
	 */
	@Override
	public List<TeeFlowRunPrcs> findByComplex(int runId,int flowId,int prcsId,int flowPrcs){
		return flowRunPrcsDao.findByComplex(runId, flowId, prcsId, flowPrcs);
	}


	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#update(java.lang.String)
	 */
	@Override
	public int update(String hql) {
		return this.flowRunPrcsDao.updateByHql(hql);
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#update(java.lang.String, java.lang.Object[])
	 */
	@Override
	public int update(String hql, Object[] object) {
		// TODO Auto-generated method stub
		return 0;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#updateCurrPrcsUserState2(java.lang.String)
	 */
	@Override
	public int updateCurrPrcsUserState2(String hql){
		return this.flowRunPrcsDao.updateCurrPrcsUserState2(hql);
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#updateAllPrcsUserState4(java.lang.String)
	 */
	@Override
	public int updateAllPrcsUserState4(String hql) {
		// TODO Auto-generated method stub
		return this.flowRunPrcsDao.updateAllPrcsUserState4(hql);
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#updatePrcsState(java.lang.String, java.lang.Object[])
	 */
	@Override
	public int updatePrcsState(String hql, Object[] object) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#JobMonitorAllTypeList(java.lang.String, int, int, java.lang.String, java.util.List, int, int, com.tianee.oa.webframe.httpModel.TeeDataGridModel)
	 */
	@Override
	public List<TeeFlowRunPrcs> JobMonitorAllTypeList(String runName,
			int runId, int flagType, String pid,List deptWhereList,int firstResult,int pageSize,TeeDataGridModel md) {
		
		List<TeeFlowRunPrcs>  list =   this.flowRunPrcsDao.JobMonitorAllTypeList(runName, runId, flagType, pid, deptWhereList, firstResult, pageSize, md);
		return list;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#JobMonitorAllTypeListCount(java.lang.String, int, int, java.lang.String, java.util.List, com.tianee.oa.webframe.httpModel.TeeDataGridModel)
	 */
	@Override
	public Long JobMonitorAllTypeListCount(String runName,
			int runId, int flagType, String pid,List deptWhereList,TeeDataGridModel md) {
		
		Long count =  this.flowRunPrcsDao.JobMonitorAllTypeListCount(runName, runId, flagType, pid, deptWhereList, md);
		return count;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#JobMonitorList(int, java.lang.String, int, int, java.lang.String, java.lang.String, int, int, com.tianee.oa.webframe.httpModel.TeeDataGridModel)
	 */
	@Override
	public List<TeeFlowRunPrcs> JobMonitorList(int flowTypeId, String runName,
			int runId, int flagType, String pid,String deptids,int firstResult,int pageSize , TeeDataGridModel md) {
		
		List<TeeFlowRunPrcs>  list =   this.flowRunPrcsDao.JobMonitorList(flowTypeId, runName, runId, flagType, pid,deptids,firstResult,pageSize, md);
		return list;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#JobMonitorListCount(int, java.lang.String, int, int, java.lang.String, java.lang.String)
	 */
	@Override
	public long JobMonitorListCount(int flowTypeId, String runName,
			int runId, int flagType, String pid,String deptids){
		
		return this.flowRunPrcsDao.JobMonitorListCount(flowTypeId, runName, runId, flagType, pid, deptids);
	}
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#checkFlowRunPrcsIsExtisHand(int, int, int)
	 */
	@Override
	public boolean checkFlowRunPrcsIsExtisHand(int runId,int prcsId,int flowPrcsSid) {
		return flowRunPrcsDao.checkFlowRunPrcsIsExtisHand(runId, prcsId, flowPrcsSid);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#getFlowRunPrcsModelList(int)
	 */
	@Override
	public List<TeeFlowRunPrcsModel> getFlowRunPrcsModelList(int runId){
		List<TeeFlowRunPrcsModel> modelList = new ArrayList<TeeFlowRunPrcsModel>();
		List<TeeFlowRunPrcs> prcsList = flowRunPrcsDao.listByRunId(runId);
		for(TeeFlowRunPrcs frp:prcsList){
			TeeFlowRunPrcsModel m = new TeeFlowRunPrcsModel();
			BeanUtils.copyProperties(frp, m);
			m.setBeginTimeDesc(TeeDateUtil.format(frp.getBeginTime()));
			m.setEndTimeDesc(TeeDateUtil.format(frp.getEndTime()));
			m.setFlowPrcsId(frp.getFlowPrcs()==null?0:frp.getFlowPrcs().getSid());
			m.setRunId(frp.getFlowRun().getRunId());
			m.setPrcsName(frp.getFlowPrcs()==null?"":frp.getFlowPrcs().getPrcsName());
			m.setPassedTime(TeeDateUtil.getPassedTimeDesc(frp.getBeginTime(), frp.getEndTime()));
			m.setPrcsUserId(frp.getPrcsUser().getUuid());
			m.setPrcsUserName(frp.getPrcsUser().getUserName());
			m.setOtherUserId(frp.getOtherUser()==null?0:frp.getOtherUser().getUuid());
			m.setOtherUserName(frp.getOtherUser()==null?"":frp.getOtherUser().getUserName());
			m.setFromUserId(frp.getFromUser()==null?0:frp.getFromUser().getUuid());
			m.setFromUserName(frp.getFromUser()==null?"":frp.getFromUser().getUserName());
			m.setPrcsType(frp.getFlowPrcs()==null?0:frp.getFlowPrcs().getPrcsType());
			modelList.add(m);
		}
		return modelList;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#checkFlowRunPrcsIsLastHand(int, int, int)
	 */
	@Override
	public boolean checkFlowRunPrcsIsLastHand(int runId,int prcsId,int flowPrcsSid) {
		return flowRunPrcsDao.checkFlowRunPrcsIsLastHand(runId, prcsId, flowPrcsSid);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#getMaxPrcsId(int)
	 */
	@Override
	public int getMaxPrcsId(int runId){
		return flowRunPrcsDao.getMaxPrcsId(runId);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#geFreeFlowtMaxPrcsId(int)
	 */
	@Override
	public int geFreeFlowtMaxPrcsId(int runId){
		return flowRunPrcsDao.getFreeFlowMaxPrcsId(runId);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#getFreeFlowPreFlowRunPrcs(int, int)
	 */
	@Override
	public List<TeeFlowRunPrcs> getFreeFlowPreFlowRunPrcs(int currentPrcsId , int runId){
		
		return flowRunPrcsDao.getFreeFlowPreFlowRunPrcs(currentPrcsId, runId);
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#getFreeFlowNextPrcs(java.util.List, int)
	 */
	@Override
	public Map getFreeFlowNextPrcs(List<TeeFlowRunPrcs> list,int nextPrcsId){
		Map nextMap = new HashMap<String, Object>();
		
		TeeFlowRunPrcs flowRunPrcs = null;
		List otherList = new ArrayList<TeeFlowRunPrcs>();
		
		if(list == null || list.size() < 1){
			return null;
		}
		boolean isNoOpUser = false;
		int topFlag = 1;
		
		int prcsOpUser = 0;
		int prcsId = nextPrcsId;
		String prcsOpUserDesc = null;
		prcsOpUserDesc = "";
		String prcsUser = null;
		prcsUser = "";
		String prcsUserDesc = null;
		prcsUserDesc = "";

		for(int i=0;i<list.size();i++){
			TeeFlowRunPrcs temPrcs1 = list.get(i);
			if((temPrcs1.getPrcsId() == nextPrcsId &&temPrcs1.getOpFlag() == 2) || (temPrcs1.getPrcsId() == nextPrcsId && temPrcs1.getOpFlag() == 3) ){
				isNoOpUser = true;
				topFlag = temPrcs1.getOpFlag();
			}
		}
		/**
		 * 不需要有主办人 
		 */
		if(isNoOpUser){
			/**
			 * 不过滤主办人
			 */
			for(int i=0;i<list.size();i++){
				TeeFlowRunPrcs temPrcs = list.get(i);
				if(temPrcs.getPrcsId() == nextPrcsId){
					prcsUser = prcsUser + temPrcs.getPrcsUser().getUuid()+"," ;
					prcsUserDesc = prcsUserDesc + temPrcs.getPrcsUser().getUserName()+"," ;
				}
			}
		/**
		 * 需要有主办人
		 */
		}else{
			/**
			 * 排除主办人
			 */
			for(int i=0;i<list.size();i++){
				TeeFlowRunPrcs _temPrcs = list.get(i);
				if(_temPrcs.getPrcsId() == nextPrcsId &&_temPrcs.getTopFlag() == 1){
					flowRunPrcs = _temPrcs;
					prcsOpUser = _temPrcs.getPrcsUser().getUuid();
					prcsOpUserDesc = _temPrcs.getPrcsUser().getUserName()+"," ;
				}
				if(_temPrcs.getPrcsId() == nextPrcsId && _temPrcs.getTopFlag() != 1){
					prcsUser = prcsUser + _temPrcs.getPrcsUser().getUuid()+"," ;
					prcsUserDesc = prcsUserDesc + _temPrcs.getPrcsUser().getUserName()+"," ;
				}
			}
		}
		nextMap.put("topFlag", topFlag);
		nextMap.put("prcsOpUser", prcsOpUser);
		nextMap.put("prcsOpUserDesc", prcsOpUserDesc);
		nextMap.put("prcsUser", prcsUser);
		nextMap.put("prcsUserDesc", prcsUserDesc);
		nextMap.put("prcsId", prcsId);
		return nextMap;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#getFreeFlowOtherPrcs(java.util.List, int, int)
	 */
	@Override
	public List getFreeFlowOtherPrcs(List<TeeFlowRunPrcs> list,int currPrcsId,int maxPrc){
		List otherPrcs = new ArrayList<TeeFlowRunPrcs>();
		if(maxPrc  <=  currPrcsId+1){
			return null;
		}
		for(int i= currPrcsId+2;i<=maxPrc;i++){
			Map map = getFreeFlowNextPrcs(list, i);
			otherPrcs.add(map);
		}
		return otherPrcs;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#getFreeFlowOldPrcs(java.util.List, int)
	 */
	@Override
	public List getFreeFlowOldPrcs(List<TeeFlowRunPrcs> list,int currPrcsId){
		List otherPrcs = new ArrayList<TeeFlowRunPrcs>();
		
		for(int i=1;i<=currPrcsId;i++){
			Map map = getFreeFlowOldPrcsByCPrcsId(list, i);
			otherPrcs.add(map);
		}
		return otherPrcs;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#getFreeFlowOldPrcsByCPrcsId(java.util.List, int)
	 */
	@Override
	public Map getFreeFlowOldPrcsByCPrcsId(List<TeeFlowRunPrcs> list,int currPrcsId){
		Map map = new HashMap<String, String>();
		TeeFlowRunPrcs currFrp = null;
		StringBuffer opUsers = new StringBuffer();//主办人
		StringBuffer prcsUsers = new StringBuffer();//经办人
		
		for(int i=0;i<list.size();i++){
			TeeFlowRunPrcs frp = (TeeFlowRunPrcs)list.get(i);
			if(frp.getPrcsId() == currPrcsId && frp.getTopFlag()!=1){//查询当前步骤的主办人
				prcsUsers.append(frp.getPrcsUser().getUserName()+",");
			}
			if(frp.getPrcsId() == currPrcsId && frp.getTopFlag()==1){//查询当前步骤的主办人
				currFrp = frp;
				opUsers.append(frp.getPrcsUser().getUserName()+",");
			}
		}
		
		if(opUsers.length()!=0){
			opUsers.deleteCharAt(opUsers.length()-1);
		}
		if(prcsUsers.length()!=0){
			prcsUsers.deleteCharAt(prcsUsers.length()-1);
		}
		
		
		//if(currFrp != null){//如果主办流程不为空
		map.put("prcsId", currPrcsId);
		map.put("opUserDesc", opUsers.toString());
		map.put("prcsUserDesc", prcsUsers.toString());
		if(currFrp!=null){
			map.put("flag", currFrp.getFlag());
		}
		//}
		return map;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#turnEnd(int)
	 */
	@Override
	public void turnEnd(int frpSid){
		
				
		//先获取当前步骤实例中的prcsId和runId
		Map data = simpleDao.getMap("select frp.prcsId as PRCS_ID,frp.flowRun.runId as RUNID,frp.flowType.sid as FLOWID from TeeFlowRunPrcs frp where frp.sid="+frpSid, null);
		int prcsId = TeeStringUtil.getInteger(data.get("PRCS_ID"), 0);
		int runId = TeeStringUtil.getInteger(data.get("RUNID"), 0);
		int flowTypeId=TeeStringUtil.getInteger(data.get("FLOWID"), 0);
		
		TeeFlowType flowType=(TeeFlowType) simpleDaoSupport.get(TeeFlowType.class,flowTypeId);
		//更新当前步骤实例的endTime和flag
		Map updator = new HashMap();
		updator.put("endTime", Calendar.getInstance());
		updator.put("flag", 4);
		flowRunPrcsDao.update(updator, frpSid);
		
		//更新当前prcsId的其他flag为4，而不需要更新结束时间
		simpleDao.executeUpdate("update TeeFlowRunPrcs frp set frp.flag=4 where frp.prcsId=? and frp.flowRun.runId=?", new Object[]{prcsId,runId});
		//更新flowRun为已办结状态
		simpleDao.executeUpdate("update TeeFlowRun fr set fr.endTime=? where fr.runId=?", new Object[]{Calendar.getInstance(),runId});
	    
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#getFreePrcsCtrlModelList(int)
	 */
	@Override
	public List<String> getFreePrcsCtrlModelList(int runId){
		List<Map> list = simpleDao.getMaps("select frp.freeCtrlModel as freeCtrlModel from TeeFlowRunPrcs frp where frp.flowRun.runId=?", new Object[]{runId});
		List<String> ctrlLists = new ArrayList<String>();
		for(Map map:list){
			String tmp = TeeStringUtil.getString(map.get("freeCtrlModel"));
			if(!"".equals(tmp)){
				ctrlLists.add(tmp);
			}
		}
		return ctrlLists;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#getUnhandledWorks(int, int)
	 */
	@Override
	public List<Map> getUnhandledWorks(int runId,int userSid){
		List<Map> list = simpleDao.getMaps("select frp.sid as FRP_SID,frp.flowRun.runId as RUN_ID,frp.flowType.sid as FLOW_ID from TeeFlowRunPrcs frp where frp.flowRun.runId=? and frp.flag in (1,2) and frp.flowRun.endTime is null and frp.prcsUser.uuid=?", new Object[]{runId,userSid});
		return list;
	}

	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#getMyHistoryStepByRunId(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson getMyHistoryStepByRunId(HttpServletRequest request) {
	    TeeJson json=new TeeJson();
	    //获取当前登陆人
	    TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
	    int runId=TeeStringUtil.getInteger(request.getParameter("runId"),0);
	    List<TeeFlowRunPrcs> list=simpleDaoSupport.executeQuery(" from TeeFlowRunPrcs where prcsUser.uuid=? and  flowRun.runId=?   ", new Object[]{loginUser.getUuid(),runId});
	    TeeFlowRunPrcsModel m=null;
   	    List<TeeFlowRunPrcsModel> modelList=new ArrayList<TeeFlowRunPrcsModel>(); 
	    if(list!=null&&list.size()>0){
	    	for (TeeFlowRunPrcs frp : list) {
	    		m=new TeeFlowRunPrcsModel();
				BeanUtils.copyProperties(frp, m);
				m.setBeginTimeDesc(TeeDateUtil.format(frp.getBeginTime()));
				m.setEndTimeDesc(TeeDateUtil.format(frp.getEndTime()));
				m.setFlowPrcsId(frp.getFlowPrcs()==null?0:frp.getFlowPrcs().getSid());
				m.setRunId(frp.getFlowRun().getRunId());
				m.setPrcsName(frp.getFlowPrcs()==null?"":frp.getFlowPrcs().getPrcsName());
				m.setPassedTime(TeeDateUtil.getPassedTimeDesc(frp.getBeginTime(), frp.getEndTime()));
				m.setPrcsUserId(frp.getPrcsUser().getUuid());
				m.setPrcsUserName(frp.getPrcsUser().getUserName());
				m.setOtherUserId(frp.getOtherUser()==null?0:frp.getOtherUser().getUuid());
				m.setOtherUserName(frp.getOtherUser()==null?"":frp.getOtherUser().getUserName());
				m.setFromUserId(frp.getFromUser()==null?0:frp.getFromUser().getUuid());
				m.setFromUserName(frp.getFromUser()==null?"":frp.getFromUser().getUserName());
				m.setPrcsType(frp.getFlowPrcs()==null?0:frp.getFlowPrcs().getPrcsType());
				modelList.add(m);
			}
	    }
	    json.setRtState(true);
	    json.setRtData(modelList);
	    return  json;
	}

	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface#getFlowRunPrcsBySid(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson getFlowRunPrcsBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int frpSid=TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
	    TeeFlowRunPrcs frp=(TeeFlowRunPrcs) simpleDao.get(TeeFlowRunPrcs.class,frpSid);
	    TeeFlowRunPrcsModel m=new TeeFlowRunPrcsModel();
	    if(frp!=null){
	    	m=new TeeFlowRunPrcsModel();
			BeanUtils.copyProperties(frp, m);
			m.setBeginTimeDesc(TeeDateUtil.format(frp.getBeginTime()));
			m.setEndTimeDesc(TeeDateUtil.format(frp.getEndTime()));
			m.setFlowPrcsId(frp.getFlowPrcs()==null?0:frp.getFlowPrcs().getSid());
			m.setRunId(frp.getFlowRun().getRunId());
			m.setPrcsName(frp.getFlowPrcs()==null?"":frp.getFlowPrcs().getPrcsName());
			m.setPassedTime(TeeDateUtil.getPassedTimeDesc(frp.getBeginTime(), frp.getEndTime()));
			m.setPrcsUserId(frp.getPrcsUser().getUuid());
			m.setPrcsUserName(frp.getPrcsUser().getUserName());
			m.setOtherUserId(frp.getOtherUser()==null?0:frp.getOtherUser().getUuid());
			m.setOtherUserName(frp.getOtherUser()==null?"":frp.getOtherUser().getUserName());
			m.setFromUserId(frp.getFromUser()==null?0:frp.getFromUser().getUuid());
			m.setFromUserName(frp.getFromUser()==null?"":frp.getFromUser().getUserName());
			m.setPrcsType(frp.getFlowPrcs()==null?0:frp.getFlowPrcs().getPrcsType());
	    }
	    json.setRtState(true);
	    json.setRtData(m);
		return json;
	}
	
}
